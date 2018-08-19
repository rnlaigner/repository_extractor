package repository_extractor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import business.PropertiesBusiness;
import business.RepositoryBusiness;
import exception.ExecutionException;
import exception.GitHubQueryException;
import exception.ReadPropertiesException;
import multithreading.RepositoryBuffer;
import multithreading.RepositoryConsumer;
import properties.ExecutionProperties;
import properties.QueryProperties;

public class Application
{
	private String DIRECTORY_TO_SAVE;
	private int BUFFER_SIZE;
	private int CONSUMER_SIZE;
	
	private ExecutionProperties executionProperties;
	private QueryProperties queryProperties;
	private List<String> repositoryURLS;
	
	public void loadProperties() throws ReadPropertiesException
	{
		PropertiesBusiness propertiesBusiness = new PropertiesBusiness();
		//read properties
		executionProperties = propertiesBusiness.readExecutionProperties();
		queryProperties = propertiesBusiness.readQueryProperties();
	}
	
	public void loadRepositories() throws GitHubQueryException
	{
		RepositoryBusiness repositoryBusiness = new RepositoryBusiness();
		repositoryURLS = repositoryBusiness.getRepositories(queryProperties);
	}
	
	public void setUpExecutionConfiguration()
	{
		DIRECTORY_TO_SAVE = executionProperties.getRepositoryDir();
		BUFFER_SIZE = repositoryURLS.size();
		CONSUMER_SIZE = executionProperties.getMaxThreads();
	}
	
	public void executeWork() throws ExecutionException
	{
		// create new thread pool 
	    ExecutorService application = Executors.newCachedThreadPool();
	
	    // create BlockingBuffer to store ints
	    RepositoryBuffer sharedLocation = new RepositoryBuffer(BUFFER_SIZE);
	
	    for(int i = 0; i < repositoryURLS.size(); i++) 
	    { 
	    	try 
	    	{
	    		sharedLocation.set(repositoryURLS.get(i));
	    	} 
	    	catch (InterruptedException e) 
	    	{
	    		e.printStackTrace();
	    		throw new ExecutionException(e.getMessage());
	    	} 
	    }
	    
	    for(int i = 0; i < CONSUMER_SIZE; i++) 
	    { 
	    	application.execute( new RepositoryConsumer( sharedLocation, DIRECTORY_TO_SAVE ) );
	    }

	
	    application.shutdown();
	}

	public void init(String[] args) throws ReadPropertiesException, ExecutionException, GitHubQueryException
	{
		loadProperties();
		loadRepositories();
		setUpExecutionConfiguration();
		executeWork();
	}
	
	
}
