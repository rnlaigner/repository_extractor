package exception;

import java.io.IOException;

public class GitHubConnectionException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GitHubConnectionException(String string) {
		super(string);
	}



}
