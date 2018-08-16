package repository_extractor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import business.PropertiesBusiness;
import business.RepositoryBusiness;
import exception.ReadPropertiesException;
import multithreading.RepositoryBuffer;
import multithreading.RepositoryConsumer;
import properties.ExecutionProperties;
import properties.QueryProperties;

public class Application
{

	public static void init(String[] args) throws ReadPropertiesException
	{
		
		PropertiesBusiness propertiesBusiness = new PropertiesBusiness();
		
		//read properties
		ExecutionProperties executionProperties = propertiesBusiness.readExecutionProperties();
		QueryProperties queryProperties = propertiesBusiness.readQueryProperties();
		
		RepositoryBusiness repositoryBusiness = new RepositoryBusiness();
		
		List<String> repoURLS =  repositoryBusiness.getSmallSetRepositories();
		
		String DIRECTORY_TO_SAVE = executionProperties.getRepositoryDir();
		
		int BUFFER_SIZE = 5;
		int PRODUCER_SIZE = 1;
		int CONSUMER_SIZE = 5;
		
		
		// create new thread pool with two threads
	    ExecutorService application = Executors.newCachedThreadPool();
	
	    // create BlockingBuffer to store ints
	    RepositoryBuffer sharedLocation = new RepositoryBuffer(BUFFER_SIZE);
	    
	    //decreasing for test
	    //repoURLS = repoURLS.subList(0, 2);
	    
	    //List<List<String>> partitionedLists = Lists.partition(repoURLS, PRODUCER_SIZE);
	    
	    //List<Runnable> consumers = new ArrayList<Runnable>();
	
	    for(int i = 0; i < repoURLS.size(); i++) 
	    { 
	    	try 
	    	{
	    		sharedLocation.set(repoURLS.get(i));
	    	} 
	    	catch (InterruptedException e) 
	    	{
			// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} 
	    }
	    
	    //for(int i = 0; i < PRODUCER_SIZE; i++) { application.execute( new RepositoryProducer( sharedLocation, partitionedLists.get(i) ) ); }
	    for(int i = 0; i < CONSUMER_SIZE; i++) { 
	    	 application.execute( new RepositoryConsumer( sharedLocation, DIRECTORY_TO_SAVE ) ); 
	    	//consumers.add(new RepositoryConsumer( sharedLocation, repoDir ));
	    	  }

	
	    application.shutdown();
		
		
	}
	
	
}
