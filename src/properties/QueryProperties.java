package properties;

import java.util.List;

public class QueryProperties 
{

	private String language;
	
	private List<String> q;

	public String getLanguage() 
	{
		return language;
	}

	public void setLanguage(String language) 
	{
		this.language = language;
	}

	public List<String> getQ() {
		return q;
	}

	public void setQ(List<String> q) {
		this.q = q;
	}
	
}
