package org.cohorte.utilities.installer.validators;

import static org.cohorte.utilities.installer.CInstallerTools.getServiceLogger;

import org.cohorte.utilities.installer.IConstants;
import org.psem2m.utilities.logging.IActivityLogger;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.installer.DataValidator;

public abstract class CUpdateCheckerBase implements DataValidator, IConstants {

		
	/**
	 * Logger
	 */
	protected final IActivityLogger pLogger;
	
	/**
	 * Install Data
	 */
	private InstallData pInstallData;
	
	
	public CUpdateCheckerBase() {
		// retreive the 'logger' service asking the service registry
		pLogger = getServiceLogger();
	}
	
	/**
     * If the installer is run in automated mode, and {@code validateData}
     * returns {@code Status.WARNING}, this method is asked how to go on instead of an user answer.
     *
     * @return boolean true - ignore warning and continue to the next panel, false - don't change to the next panel, fail
     */
	//@Override
	public boolean getDefaultAnswer() {		
		return true;
	}

	/**
     * Returns the string with either a message if from translations or the message itself in case {@code validateData}
     * returns {@code Status.ERROR}.
     *
     * @return String Should be the message id or the untranslated error message.
     */
	//@Override
	public String getErrorMessageId() {		
		return "installer.updater.canNotUpdate";
	}

	/**
     * Returns the string with either a message if from translations or the message itself in case {@code validateData}
     * returns {@code Status.WARNING}.
     *
     * @return String Should be the message id or the untranslated warning message.
     */
	//@Override
	public String getWarningMessageId() {
		// no need for a warning message as we return always Status.OK when validating data.
		return null;
	}

	/**
     * Method to validate complex variable settings read from {@link InstallData} after a panel change.
     *
     * @param installData@return {@link Status} the result of the validation
     */
	//@Override
	public Status validateData(InstallData aInstallData) {			
		//  - set INSTALLER__IS_ALREADY_INSTALLED variable to true if an old version is installed
		//  - update installData with values of the old installation if already installed
		//  - always returns Status.OK.
		this.pInstallData = aInstallData;
		if (check() == true) {
			aInstallData.setVariable(INSTALLER__IS_ALREADY_INSTALLED, "true");
			if (!canUpdate()) {
				return Status.ERROR;
			}
			// update installData variables
			updateInstallData();
			// update INSTALLER__ALREADY_INSTALLED_VERSION
			aInstallData.setVariable(INSTALLER__ALREADY_INSTALLED_VERSION, getInstalledVersion());
		} else {
			aInstallData.setVariable(INSTALLER__IS_ALREADY_INSTALLED, "false");			
		}		
		return Status.OK;
	}
	
	protected InstallData getInstallData() {
		return pInstallData;
	}
	
	/**
	 * Checks if an old version is already installed.
	 * Use getInstallData() to obtain installer data.
	 * @return
	 */
	protected abstract boolean check();
	
	/**
	 * Returns true if we can update the software.
	 * Use getInstallData() to obtain installer data.
	 * @return
	 */
	protected abstract boolean canUpdate();
	
	/**
	 * If an old version is already installed, tries to retrieve installer variables
	 * and set them in this current installation.
	 * Use getInstallData() to obtain installer data.
	 */
	protected abstract void updateInstallData();
	
	/**
	 * Gets the installed version of the software.
	 * Should be implemented 
	 * @return
	 */
	protected abstract String getInstalledVersion();
	
}
