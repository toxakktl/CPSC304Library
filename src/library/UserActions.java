package library;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

public abstract class UserActions {

	protected BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	protected Connection con;
	
	public UserActions(Connection c) {
		this.con = c;
	}

}
