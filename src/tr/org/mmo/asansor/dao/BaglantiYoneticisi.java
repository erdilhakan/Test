package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BaglantiYoneticisi {

	public static BaglantiYoneticisi instance=new BaglantiYoneticisi();
	//08/10/2013 okan
	public static BaglantiYoneticisi getInstance() {
	      if(instance == null) {
	         instance = new BaglantiYoneticisi();
	      }
	      return instance;
	   }
	
	public DataSource getDatasource(){
		Context initCtx;
		Context envCtx;
		DataSource ds=null;
		try {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource)envCtx.lookup("jdbc/asansor");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return ds;
	}
	//08/10/2013 okan
	
	private Connection connection;

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				

				connection = DriverManager.getConnection(
						"jdbc:postgresql://10.10.1.6:5432/asansor", "postgres", "88MMO99@!");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void close(PreparedStatement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
	}

	private void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void close(PreparedStatement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
