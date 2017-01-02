package org.psem2m.utilities.system;

import java.io.File;
import java.util.Map;

/**
 * @author ogattaz
 *
 */
public interface IXOSServer extends IXOSRunner {

	/**
	 * @return
	 */
	int getPid();

	/**
	 * @return
	 */
	EXServerState getServerState();

	/**
	 * WARNING: if aEnv has a new value for PATH environment variable, if the
	 * sub-process is not a shell, PATH will be ignored. Hint: launch your
	 * program using a shell, e.g., "cmd.exe /c your_cmd args"
	 *
	 * @param aTimeOut
	 *            no timeout if <= 0
	 * @param aUserDir
	 *            the "user dir" of the command
	 * @param aEnv
	 *            a set of variables added in the context of the command the
	 *            server is started
	 * @return
	 * @throws Exception
	 */
	boolean start(final File aUserDir, final Map<String, String> aEnv);

	/**
	 * WARNING: if aEnv has a new value for PATH environment variable, if the
	 * sub-process is not a shell, PATH will be ignored. Hint: launch your
	 * program using a shell, e.g., "cmd.exe /c your_cmd args"
	 *
	 * @param aUserDir
	 *            the "user dir" of the command
	 * @param aEnv
	 *            a set of variables added in the context of the command
	 * @param aSatrtTimeOut
	 *            the timeout of the starting phase. no timeout if <= 0
	 * @param aStrInStdOut
	 *            the string which must be found in the stdout to indicate that
	 *            the server is started
	 * @return
	 * @throws Exception
	 */
	boolean startAndWaitInStdOut(final File aUserDir,
			final Map<String, String> aEnv, final long aStartTimeOut,
			final String aStrInStdOut);

	/**
	 * @param aUserDir
	 *            the "user dir" of the command
	 * @param aEnv
	 *            a set of variables added in the context of the command
	 * @param aSatrtTimeOut
	 *            the timeout of the starting phase. no timeout if <= 0
	 * @param aStopCommand
	 * @return
	 */
	boolean stop(final File aUserDir, final Map<String, String> aEnv,
			final long aTimeOut, final String... aStopCommand);

}
