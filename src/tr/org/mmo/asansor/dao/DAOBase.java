package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import tr.org.mmo.asansor.exception.db.CloseException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.util.Messages;

public class DAOBase {


	Connection connection = null;
	Context envContext = null;
	ResourceBundle bundle = ResourceBundle.getBundle("datasource");

	private Context initContext() throws NamingException {

		Context context = new InitialContext();
		return (Context) context.lookup(bundle.getString("contextName"));
	}

	private DataSource getDataSource(Context context) throws NamingException {
		DataSource dataSource = null;
		dataSource = (DataSource) context.lookup(bundle.getString("jndiName"));
		return dataSource;
	}

	public Connection getConnection() throws CreateException {
		
		try {
			envContext = initContext();
			DataSource ds = getDataSource(envContext);
			try {
				connection = ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new CreateException(Messages._CONNECTIONAC_.getMesaj(),
						e.getCause());
			}
		} catch (NamingException e) {

			e.printStackTrace();
			throw new CreateException(Messages._SQL_503_.getMesaj(),
					e.getCause());
		}
		

		return connection;
		
	

	}

	protected void closeConnection() throws CloseException {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CloseException(Messages._CONNECTIONKAPAT_.getMesaj(),
					e.getCause());
		}
	}

	protected void close(PreparedStatement stmt, ResultSet rs)
			throws CloseException {
		close(rs);
		close(stmt);
	}

	private void close(ResultSet rs) throws CloseException {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CloseException(Messages._CONNECTIONKAPAT_.getMesaj(),
					e.getCause());
		}
	}

	private void close(PreparedStatement stmt) throws CloseException {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CloseException(Messages._CONNECTIONKAPAT_.getMesaj(),
					e.getCause());
		}
	}

	private DAOBase() {
	}

	private static DAOBase dao;

	public static synchronized DAOBase instance() {
		return (dao == null ? new DAOBase() : dao);
	}



}
