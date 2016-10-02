package de.paulwein.notes.dao;

import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import de.paulwein.notes.dao.impl.NotesDAODSImpl;
import de.paulwein.notes.dao.impl.NotesDAOJDOImpl;
import de.paulwein.notes.dao.impl.NotesDAOJPAImpl;


public class DAOFactory {

	public static final String CONFIG_ENTITY = "config";
	public static final String CONFIG_NAME = "datasource";
	
	public static final int DATASTORE = 0;
	public static final int JDO = 1;
	public static final int JPA = 2;
	
	private static DAOFactory 	instance;
	private static  int currentSource = 0;
	
	private Map<String,DAO>		daoMap = new HashMap<String,DAO>();
	
	/**
	 * Initializes factory object.
	 * 
	 * @throws ClassNotFoundException
	 */
	
	private DAOFactory() throws DAOException {
		switch(currentSource){
		case DATASTORE:
			registerDAO( new NotesDAODSImpl() );
			break;
		case JDO: 
			registerDAO( new NotesDAOJDOImpl() );
			break;
		case JPA:
			registerDAO( new NotesDAOJPAImpl() );
			break;
		}
	}
	
	private void registerDAO( DAO dao ) {
		daoMap.put( dao.getSupportedEntity(), dao );
	}
	
	/**
	 * request Singleton instance of DAOFactory
	 * 
	 * @return DAOFactory
	 * @throws ClassNotFoundException
	 */
	
	public static DAOFactory getInstance() throws DAOException {
		
		if( instance == null ) {
			initDataSource();
			instance = new DAOFactory();
		}
		return instance;
	}
	
	public static void initDataSource(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		Key key = KeyFactory.createKey(CONFIG_ENTITY, user.getUserId());
		Entity eConfig = null;
		try {
			eConfig = datastore.get(key);
		} catch (EntityNotFoundException e) {
			// initially set the configuration
			eConfig = new Entity(key);
			eConfig.setProperty(CONFIG_NAME, Long.valueOf(DATASTORE));
			datastore.put(eConfig);
		}
		currentSource = ((Long) eConfig.getProperty(CONFIG_NAME)).intValue();
	}
	
	public static boolean setDataSource(int datasource) throws DAOException{
		if(datasource >= DATASTORE && datasource <= JPA){
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			Key key = KeyFactory.createKey(CONFIG_ENTITY, user.getUserId());
			Entity eConfig = null;
			try {
				eConfig = datastore.get(key);
			} catch (EntityNotFoundException e) {
				eConfig = new Entity(key);
			}
			eConfig.setProperty(CONFIG_NAME, datasource);
			datastore.put(eConfig);
			currentSource = datasource;
			instance = null;
			return true;
		}
		return false;
	}
	
	public static int getDataSource() throws DAOException{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			Key key = KeyFactory.createKey(CONFIG_ENTITY, user.getUserId());
			Entity eConfig = null;
			try {
				eConfig = datastore.get(key);
				return ((Long) eConfig.getProperty(CONFIG_NAME)).intValue();
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
				return DATASTORE;
			}
	}
	
	/**
	 * request DAO
	 * 
	 * @return DAOImpl
	 */
	
	public <T extends DAO> T getDAO( Class<?> requiredDao, Class<T> daoClass ) {
		
		if( daoMap.containsKey( requiredDao.getName() ) ) {
			return daoClass.cast( daoMap.get( requiredDao.getName() ) );
		}
		
		return null;
	}
	
}
