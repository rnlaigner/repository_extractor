package properties;

public class ExecutionProperties 
{
	private int MAX_THREADS;
	
	private String REPOSITORY_DIR;
	
	public int getMaxThreads()
	{
		return MAX_THREADS;
	}
	
	public void setMaxThreads(int maxThreads)
	{
		this.MAX_THREADS = maxThreads;
	}

	public String getRepositoryDir() {
		return REPOSITORY_DIR;
	}

	public void setRepositoryDir(String repositoryDir) {
		this.REPOSITORY_DIR = repositoryDir;
	}
	
}
