package business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedSearchIterable;

import exception.GitHubQueryException;
import model.RepositoryPair;
import properties.QueryProperties;

public class RepositoryBusiness 
{
	
	public List<String> getRepositories(QueryProperties queryProperties) throws GitHubQueryException
	{
		
		PagedSearchIterable<GHRepository> repositoryList = null;
		GitHub github;
		List<String> repoURLS = new ArrayList<String>();
		
		try 
		{
			github = GitHub.connect();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new GitHubQueryException("Erro ao se conectar ao GitHub com as credenciais providas no arquivo .github");
		}
		
		repositoryList = github.
				searchRepositories().
				language("java").
				q("topic:java").
				q("topic:spring").
				list();
		
		//log
		System.out.println(repositoryList.getTotalCount());
		
		if(repositoryList.getTotalCount() == 0)
		{
			return repoURLS;
		}
		
		List<RepositoryPair> repositoryURLsWithSize = new ArrayList<RepositoryPair>();
		
		for(GHRepository repo : repositoryList )
		{
			System.out.println(repo.getHttpTransportUrl());
			
			RepositoryPair pair = new RepositoryPair(repo.getHttpTransportUrl(),repo.getSize());
			
			repositoryURLsWithSize.add(pair);
		}
		
		repoURLS = repositoryURLsWithSize
										.stream()
										.sorted(Comparator.comparing(RepositoryPair::getRight))
										.map(RepositoryPair -> RepositoryPair.getLeft())
										.collect(Collectors.toList());
		
		return repoURLS;
		
	}
	
	public PagedSearchIterable<GHRepository> retrieveRepositoriesForTest()
	{
		return null;
	}
	
	public List<String> getSmallSetRepositories()
	{
		return Arrays.asList("https://github.com/AlanLyu1992/spring-mvc-handler-mapping-adapter-demo.git",
				"https://github.com/Arthorn2/SpringBoot-Web-Example.git",
					"https://github.com/xiaojinsb/crm_java_spring_easyui.git",
					"https://github.com/kkralj/djecjiWeb-opp.git",
					"https://github.com/spooz/aggregatr2.git");
	}
}
