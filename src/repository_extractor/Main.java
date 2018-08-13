package repository_extractor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import multithreading.RepositoryBuffer;
import multithreading.RepositoryConsumer;

public class Main {

	public static void main(String[] args) 
	{
		
		List<String> repoURLS = Arrays.asList("https://github.com/AlanLyu1992/spring-mvc-handler-mapping-adapter-demo.git",
								"https://github.com/Arthorn2/SpringBoot-Web-Example.git",
									"https://github.com/xiaojinsb/crm_java_spring_easyui.git",
									"https://github.com/kkralj/djecjiWeb-opp.git",
									"https://github.com/spooz/aggregatr2.git");
		
		String repoDir = "C:\\Users\\Rodrigo\\workspace\\Mining\\temp\\";
		
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
	    	 application.execute( new RepositoryConsumer( sharedLocation, repoDir ) ); 
	    	//consumers.add(new RepositoryConsumer( sharedLocation, repoDir ));
	    	  }

	
	    application.shutdown();
		

	}

}
