package business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedSearchIterable;

public class RepositoryBusiness 
{
	
	public List<String> getRepositories()
	{
		
		PagedSearchIterable<GHRepository> list = null;
		
		try 
		{
			GitHub github = GitHub.connect();
			
			
			//GHRepository repo = github.getRepository("LibrePlan/libreplan");
			
			//System.out.println(repo.getFullName());
			
			//repo.getDirectoryContent(path)
			
			list = github.
					searchRepositories().
					language("java").
					q("topic:java").
					q("topic:spring").
					list();
			
			System.out.println(list.getTotalCount());
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		List<String> repoURLS = new ArrayList<String>();
		
		for(GHRepository repo : list )
		{
			System.out.println(repo.getHttpTransportUrl());
			
			repoURLS.add(repo.getHttpTransportUrl());
		}
		
		return repoURLS;
		
	}
	
}
