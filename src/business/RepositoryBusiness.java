package business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositorySearchBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedSearchIterable;

import exception.GitHubConnectionException;
import exception.GitHubQueryException;
import model.RepositoryPair;
import properties.QueryProperties;

public class RepositoryBusiness 
{
	
	private GitHub getGitHubConnection() throws GitHubConnectionException
	{
		GitHub github;
		
		try 
		{
			github = GitHub.connect();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new GitHubConnectionException("Erro ao se conectar ao GitHub com as credenciais providas no arquivo .github");
		}
		
		return github;
	}
	
	private GHRepositorySearchBuilder getGitHubQuery(QueryProperties queryProperties, 
													 GitHub gitHubConnection) 
													 throws GitHubQueryException
	{
		GHRepositorySearchBuilder searchBuilder = gitHubConnection.searchRepositories();
		
		for(String q : queryProperties.getQ())
		{
			searchBuilder.q(q);
		}
		
		searchBuilder.language(queryProperties.getLanguage());
		
		return searchBuilder;
	}
	
	private List<String> orderRepositoriesByIncreasingSize(PagedSearchIterable<GHRepository> repositoryList)
	{
		List<RepositoryPair> repositoryURLsWithSize = new ArrayList<RepositoryPair>();
		
		for(GHRepository repo : repositoryList )
		{
			RepositoryPair pair = new RepositoryPair(repo.getHttpTransportUrl(),repo.getSize());
			repositoryURLsWithSize.add(pair);
		}
		
		List<String> repositoryURLS = repositoryURLsWithSize
										.stream()
										.sorted(Comparator.comparing(RepositoryPair::getRight))
										.map(RepositoryPair -> RepositoryPair.getLeft())
										.collect(Collectors.toList());
		
		return repositoryURLS;
	}
	
	public List<String> getRepositories(QueryProperties queryProperties) throws GitHubConnectionException, GitHubQueryException
	{
		
		GitHub gitHubConnection = getGitHubConnection();
		
		GHRepositorySearchBuilder searchBuilder = getGitHubQuery(queryProperties,gitHubConnection);
		
		PagedSearchIterable<GHRepository> repositoryList = searchBuilder.list();
		
		List<String> repositoryURLS = new ArrayList<String>();
		
		if(repositoryList.getTotalCount() == 0)
		{
			return repositoryURLS;
		}
		
		repositoryURLS = orderRepositoriesByIncreasingSize(repositoryList);
		
		return repositoryURLS;
		
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
