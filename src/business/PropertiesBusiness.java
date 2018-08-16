package business;

import properties.ExecutionProperties;
import properties.QueryProperties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import exception.ReadPropertiesException;



public class PropertiesBusiness 
{
	
	private static final String propertyFilename = ".github";
	
	public ExecutionProperties readExecutionProperties() throws ReadPropertiesException
	{
		InputStream input = processInput();
		
		Properties prop = loadProperties(input);
		
		// get the property value 
		Integer maxThreads = Integer.getInteger( prop.getProperty("max_threads") );
		String repositoryDir = prop.getProperty("repository_dir");
		
		ExecutionProperties executionProperties = new ExecutionProperties();
		executionProperties.setMaxThreads(maxThreads);
		executionProperties.setRepositoryDir(repositoryDir);
		
		return executionProperties;
	}
	
	//Based on https://www.mkyong.com/java/java-properties-file-examples/
	public QueryProperties readQueryProperties() throws ReadPropertiesException
	{
		InputStream input = processInput();
		
		Properties prop = loadProperties(input);
		
		// get the property value 
		String language_property = prop.getProperty("language");
		String topic_property = prop.getProperty("topic");
		
		List<String> q = processTopics(topic_property);
		
		QueryProperties queryProperties = new QueryProperties();
		queryProperties.setQ(q);
		queryProperties.setLanguage("language:"+language_property);
		
		return queryProperties;
	}

	private List<String> processTopics(String topic_property) 
	{
		List<String> q = new ArrayList<String>();
		
		//se for mais de um
		String[] parts;
		if ( topic_property.indexOf(",") != -1 ) 
		{
			parts = topic_property.split(",");
			
			for(String part : parts)
			{
				q.add("topic:"+part);
			}
		}
		else
		{
			q.add("topic:" + topic_property);
		}
		return q;
	}

	private Properties loadProperties(InputStream input) throws ReadPropertiesException {
		Properties prop = new Properties();
		
		try
		{
			// load a properties file
			prop.load(input);
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
			throw new ReadPropertiesException("Erro");
		}
		return prop;
	}

	private InputStream processInput() throws ReadPropertiesException 
	{
		InputStream input = null;
		
		String filepath = System.getProperty("user.home") + "\\" + propertyFilename;

		try 
		{
			input = new FileInputStream(filepath);
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
			throw new ReadPropertiesException("Erro");
		} 
		finally 
		{
			if (input != null) 
			{
				try 
				{
					input.close();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return input;
	}
	
}
