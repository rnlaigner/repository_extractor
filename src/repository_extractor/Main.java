package repository_extractor;

import exception.ExecutionException;
import exception.GitHubConnectionException;
import exception.GitHubQueryException;
import exception.ReadPropertiesException;

public class Main {

	public static void main(String[] args) throws ReadPropertiesException, ExecutionException, GitHubQueryException, GitHubConnectionException 
	{
		new Application().init(args);
	}

}
