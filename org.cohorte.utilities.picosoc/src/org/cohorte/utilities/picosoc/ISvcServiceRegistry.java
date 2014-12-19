package org.cohorte.utilities.picosoc;

/**
 * @author ogattaz
 * 
 */
public interface ISvcServiceRegistry {

	/**
	 * 
	 */
	void clear();

	/**
	 * @param aSpecification
	 * @return
	 */
	<T> boolean contains(Class<? extends T> aSpecification);

	/**
	 * @return
	 */
	public String dump();

	/**
	 * @param aSpecification
	 * @return an instance of CServicReference<T> if the service exists
	 * @throws Exception
	 */
	<T> CServicReference<T> findServiceRef(Class<? extends T> aSpecification)
			throws Exception;

	/**
	 * @param aSpecification
	 * @return an instance of CServicReference<T>
	 * @throws Exception
	 *             if no instance of aSpecification
	 */
	<T> CServicReference<T> getServiceRef(Class<? extends T> aSpecification)
			throws Exception;

	/**
	 * @param aSpecification
	 * @param aService
	 * @return the ServiceReference
	 */
	<T> CServicReference<T> registerService(Class<? extends T> aSpecification,
			T aService) throws Exception;

	/**
	 * @param aServiceRef
	 * @throws Exception
	 */
	<T> boolean removeService(CServicReference<T> aServiceRef) throws Exception;

}