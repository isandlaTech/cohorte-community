package org.cohorte.utilities.crypto;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.psem2m.utilities.CXBytesUtils;
import org.psem2m.utilities.CXTimer;
import org.psem2m.utilities.logging.CActivityLoggerNull;
import org.psem2m.utilities.logging.IActivityLogger;

import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

/**
 * Generates Certificate, KeyPair or RsaKeyContext
 *
 * @author ogattaz
 *
 */
public class CRsaGenerator {

	/** String to hold name of the encryption algorithm. */
	public static final String ALGORITHM_GENERATE = "RSA";
	public static final int ALGORITHM_GENERATE_SIZE = 1024;

	/** String to hold name of the encryption algorithm. */
	public static final String ALGORITHM_SIGN = "SHA1withRSA";

	public static final String DISTINGUISEDNAME = "CN=isandlaTech,L=Grenoble,C=FR";

	/** certificate duration */
	public static int NB_DAYS_IN_YEAR = 365;

	/** Nb milliseconds in a day */
	public static final long NB_MILLI_IN_DAY = 86400000l;

	public final KeyPairGenerator keyGen;

	private final String pDistinguishedName;

	private final int pKeySize;

	private final IActivityLogger pLogger;

	/**
	 * @param aLogger
	 * @param aDistinguishedName
	 * @throws NoSuchAlgorithmException
	 */
	public CRsaGenerator(final IActivityLogger aLogger,
			final String aDistinguishedName) throws NoSuchAlgorithmException {

		this(aLogger, aDistinguishedName, ALGORITHM_GENERATE_SIZE);
	}

	/**
	 * MOD_OG_20150717 new signature accepting a key size
	 *
	 * @param aLogger
	 * @param aDistinguishedName
	 * @param aKeySize
	 * @throws NoSuchAlgorithmException
	 */
	public CRsaGenerator(final IActivityLogger aLogger,
			final String aDistinguishedName, final int aKeySize)
			throws NoSuchAlgorithmException {

		super();
		pLogger = (aLogger != null) ? aLogger : CActivityLoggerNull
				.getInstance();

		pDistinguishedName = (aDistinguishedName != null && !aDistinguishedName
				.isEmpty()) ? aDistinguishedName : DISTINGUISEDNAME;

		pKeySize = (aKeySize == 1024 || aKeySize == 2048) ? aKeySize
				: ALGORITHM_GENERATE_SIZE;

		keyGen = KeyPairGenerator.getInstance(ALGORITHM_GENERATE);
		keyGen.initialize(pKeySize);

		if (pLogger.isLogDebugOn()) {
			pLogger.logDebug(this, "<init>", "keyGen=[%s] Algo=[%s] Size=[%s]",
					keyGen, ALGORITHM_GENERATE, ALGORITHM_GENERATE_SIZE);
		}
	}

	/**
	 * @param aDistinguishedName
	 * @throws NoSuchAlgorithmException
	 */
	public CRsaGenerator(final String aDistinguishedName)
			throws NoSuchAlgorithmException {
		this(CActivityLoggerNull.getInstance(), aDistinguishedName);
	}

	/**
	 * @return a X509 certificate containing a new RSA keypair for "CN=Sage"
	 *
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public X509Certificate generateCertificate()
			throws GeneralSecurityException, IOException,
			NoSuchAlgorithmException {

		return generateCertificate(getDistinguishedName(), generateKeyPair(),
				NB_DAYS_IN_YEAR, getX509SignAlgorithm());
	}

	/**
	 * @param aDistinguishedName
	 *            the X.509 Distinguished Name, eg "CN=Test, L=London, C=GB"
	 * @param aNbDays
	 *            how many days from now the Certificate is valid for
	 * @return
	 *
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public X509Certificate generateCertificate(String aDistinguishedName,
			int aNbDays) throws GeneralSecurityException, IOException,
			NoSuchAlgorithmException {

		return generateCertificate(aDistinguishedName, generateKeyPair(),
				aNbDays, getX509SignAlgorithm());
	}

	/**
	 * Create a self-signed X.509 Certificate
	 *
	 * @param aDistinguishedName
	 *            the X.509 Distinguished Name, eg "CN=Test, L=London, C=GB"
	 * @param aKeyPair
	 *            the KeyPair
	 * @param aNbDays
	 *            how many days from now the Certificate is valid for
	 * @param aX509SignAlgorithm
	 *            the signing algorithm, eg "SHA1withRSA"
	 * @see http
	 *      ://stackoverflow.com/questions/1615871/creating-an-x509-certificate
	 *      -in-java-without-bouncycastle
	 */
	public X509Certificate generateCertificate(String aDistinguishedName,
			KeyPair aKeyPair, int aNbDays, String aX509SignAlgorithm)
			throws GeneralSecurityException, IOException {

		if (pLogger.isLogDebugOn()) {
			pLogger.logDebug(
					this,
					"generateCertificate",
					"DistinguishedName=[%s] NbDays=[%s] Algorithm=[%s] KeyPair=[%s]",
					aDistinguishedName, aNbDays, aX509SignAlgorithm, aKeyPair);
		}

		final PrivateKey privkey = aKeyPair.getPrivate();

		final X509CertInfo wX509CertInfo = new X509CertInfo();

		final Date from = new Date();
		final Date to = new Date(from.getTime() + aNbDays * NB_MILLI_IN_DAY);
		final CertificateValidity interval = new CertificateValidity(from, to);
		final BigInteger wSerialNumber = new BigInteger(64, new SecureRandom());
		final X500Name aOwner = new X500Name(aDistinguishedName);

		wX509CertInfo.set(X509CertInfo.VALIDITY, interval);
		wX509CertInfo.set(X509CertInfo.SERIAL_NUMBER,
				new CertificateSerialNumber(wSerialNumber));

		// Change of behaviour in JDK8:
		// https://bugs.openjdk.java.net/browse/JDK-8040820
		// https://bugs.openjdk.java.net/browse/JDK-7198416
		final String version = System.getProperty("java.version");
		if (version == null || version.matches("^(1\\.)?[7].*")) {
			// Java 7 code. To remove with Java 8 migration
			wX509CertInfo.set(X509CertInfo.SUBJECT, new CertificateSubjectName(
					aOwner));
			wX509CertInfo.set(X509CertInfo.ISSUER, new CertificateIssuerName(
					aOwner));
		} else {
			// Java 8 and later code
			wX509CertInfo.set(X509CertInfo.SUBJECT, aOwner);
			wX509CertInfo.set(X509CertInfo.ISSUER, aOwner);
		}

		wX509CertInfo.set(X509CertInfo.KEY,
				new CertificateX509Key(aKeyPair.getPublic()));
		wX509CertInfo.set(X509CertInfo.VERSION, new CertificateVersion(
				CertificateVersion.V3));
		AlgorithmId wAlgorithmId = new AlgorithmId(
				AlgorithmId.md5WithRSAEncryption_oid);
		wX509CertInfo.set(X509CertInfo.ALGORITHM_ID,
				new CertificateAlgorithmId(wAlgorithmId));

		if (pLogger.isLogDebugOn()) {
			pLogger.logDebug(this, "generateCertificate", "X509CertInfo=[%s]",
					wX509CertInfo);
		}

		// Sign the cert to identify the algorithm that's used.
		X509CertImpl wCertificate = new X509CertImpl(wX509CertInfo);
		wCertificate.sign(privkey, aX509SignAlgorithm);

		// Update the algorith, and resign.
		wAlgorithmId = (AlgorithmId) wCertificate.get(X509CertImpl.SIG_ALG);
		wX509CertInfo.set(CertificateAlgorithmId.NAME + "."
				+ CertificateAlgorithmId.ALGORITHM, wAlgorithmId);

		wCertificate = new X509CertImpl(wX509CertInfo);
		wCertificate.sign(privkey, aX509SignAlgorithm);
		return wCertificate;
	}

	/**
	 * @return
	 */
	public KeyPair generateKeyPair() throws NoSuchAlgorithmException {

		final CXTimer wTimer = CXTimer.newStartedTimer();

		final KeyPair wKeyPair = keyGen.generateKeyPair();
		wTimer.stop();
		if (pLogger.isLogDebugOn()) {
			pLogger.logDebug(this, "generateKeyPair",
					"Duration=[%s] Format=[%s] Public=[%s]", wTimer
							.getDurationStrMilliSec(), wKeyPair.getPublic()
							.getFormat(), CXBytesUtils
							.bytesToHexaString(wKeyPair.getPublic()
									.getEncoded()));
		}
		return wKeyPair;
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @throws NoSuchAlgorithmException
	 */
	public CRsaKeyContext generateRsaKeyContext()
			throws NoSuchAlgorithmException, GeneralSecurityException,
			IOException {

		final CXTimer wKeyTimer = CXTimer.newStartedTimer();
		final KeyPair wKeyPair = keyGen.generateKeyPair();
		wKeyTimer.stop();

		final CXTimer wCertificatTimer = CXTimer.newStartedTimer();
		final X509Certificate wX509Certificate = generateCertificate(
				DISTINGUISEDNAME, wKeyPair, NB_DAYS_IN_YEAR, ALGORITHM_SIGN);
		wCertificatTimer.stop();

		// creates the context
		final CRsaKeyContext wCRsaKeyContext = new CRsaKeyContext(wKeyPair,
				wKeyTimer, wX509Certificate, wCertificatTimer);

		if (pLogger.isLogDebugOn()) {
			pLogger.logDebug(this, "generateRsaKeyContext",
					"OK RsaKeyContext.TimeStamp=[%s]",
					wCRsaKeyContext.getTimeStampIso8601());
		}
		return wCRsaKeyContext;
	}

	/**
	 * @return
	 */
	public String getAlgorithm() throws NoSuchAlgorithmException {

		return keyGen.getAlgorithm();
	}

	/**
	 * @return
	 */
	public String getDistinguishedName() {
		return pDistinguishedName;
	}

	/**
	 * @return
	 */
	public int getKeySize() {
		return pKeySize;
	}

	/**
	 * @return
	 */
	public String getX509SignAlgorithm() {
		return ALGORITHM_SIGN;
	}

}
