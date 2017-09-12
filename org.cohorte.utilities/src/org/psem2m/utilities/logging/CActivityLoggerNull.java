package org.psem2m.utilities.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.psem2m.utilities.CXStringUtils;

/**
 * @author isandlatech (www.isandlatech.com) - ogattaz
 *
 */
public class CActivityLoggerNull implements IActivityLoggerJul {

	/**
	 * @return
	 */
	public static CActivityLoggerNull getInstance() {
		return new CActivityLoggerNull();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.psem2m.utilities.IXDescriber#addDescriptionInBuffer(java.lang.Appendable
	 * )
	 */
	@Override
	public Appendable addDescriptionInBuffer(final Appendable aBuffer) {
		return CXStringUtils.appendStringsInBuff(aBuffer, getClass()
				.getSimpleName(), String.valueOf(hashCode()));
	}

	/**
	 * @return
	 */
	public int calcDescriptionLength() {
		return 128;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.psem2m.utilities.logging.IActivityLogger#close()
	 */
	@Override
	public void close() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.psem2m.utilities.logging.IActivityLoggerJul#getJulLogger()
	 */
	@Override
	public Logger getJulLogger() {
		return CXJulUtils.getRootLogger();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.psem2m.utilities.logging.IActivityLoggerBase#getLevel()
	 */
	@Override
	public Level getLevel() {
		return Level.OFF;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.psem2m.utilities.logging.IActivityLogger#getRequester()
	 */
	@Override
	public IActivityRequester getRequester() {
		return null;
	}

	@Override
	public boolean isLogDebugOn() {
		return false;
	}

	@Override
	public boolean isLoggable(final Level aLevel) {
		return false;
	}

	@Override
	public boolean isLogInfoOn() {
		return false;
	}

	@Override
	public boolean isLogSevereOn() {
		return false;
	}

	/**
	 * @return
	 */
	@Override
	public boolean isLogWarningOn() {
		return false;
	}

	/**
	 * @return
	 */
	protected boolean isOpened() {
		return false;
	}

	@Override
	public void log(final Level aLevel, final Object aWho,
			final CharSequence aWhat, final Object... aInfos) {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.psem2m.utilities.logging.IActivityLoggerBase#log(java.util.logging
	 * .LogRecord)
	 */
	@Override
	public void log(final LogRecord record) {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.psem2m.utilities.logging.IActivityLogger#logDebug(java.lang.Object,
	 * java.lang.CharSequence, java.lang.Object[])
	 */
	@Override
	public void logDebug(final Object aWho, final CharSequence aWhat,
			final Object... aInfos) {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.psem2m.utilities.logging.IActivityLogger#logInfo(java.lang.Object,
	 * java.lang.CharSequence, java.lang.Object[])
	 */
	@Override
	public void logInfo(final Object aWho, final CharSequence aWhat,
			final Object... aInfos) {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.psem2m.utilities.logging.IActivityLogger#logSevere(java.lang.Object,
	 * java.lang.CharSequence, java.lang.Object[])
	 */
	@Override
	public void logSevere(final Object aWho, final CharSequence aWhat,
			final Object... aInfos) {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.psem2m.utilities.logging.IActivityLogger#logWarn(java.lang.Object,
	 * java.lang.CharSequence, java.lang.Object[])
	 */
	@Override
	public void logWarn(final Object aWho, final CharSequence aWhat,
			final Object... aInfos) {
	}

	@Override
	public void setLevel(Level aLevel) {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.psem2m.utilities.logging.IActivityLogger#setLevel(java.lang.String)
	 */
	@Override
	public void setLevel(String aLevelName) {
		// nothing
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.psem2m.utilities.IXDescriber#toDescription()
	 */
	@Override
	public String toDescription() {
		return toString();
	}

}
