package com.hmms.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.DriverManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionUtil {

	/**
	 * Get Connection
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {

		Connection connection = null;
		Configuration Config = new Configuration(".../resources/jdbc.properties");
		// Config.initConfig("jdbc.properties");
		String jndi = Config.getValue("jdbc.jndi");

		String scope = Config.getValue("jdbc.scope");

		if (!DataUtil.isEmpty(jndi) && !"test".equals(scope)) {

			try {
				/*
				 * Properties props = new Properties();
				 * props.put(Context.INITIAL_CONTEXT_FACTORY,
				 * "org.jboss.naming.remote.client.InitialContextFactory");
				 * props.put(Context.PROVIDER_URL, "remote://localhost:4447");
				 * //props.put(Context.SECURITY_PRINCIPAL, "admin");
				 * //props.put(Context.SECURITY_CREDENTIALS, "password");
				 */
				Context ctx = new InitialContext();
				Object datasourceRef = ctx.lookup(jndi);
				DataSource ds = (DataSource) datasourceRef;

				connection = ds.getConnection();
			} catch (Exception e) {
				System.out.println("fail to get connection by jndi " + e.getMessage());
				e.printStackTrace();
			}
			return connection;
		}
		System.out.println("-----Use jdbc for test------");
		String driverString = Config.getValue("jdbc.class");
		String dburl = Config.getValue("jdbc.url");
		String username = Config.getValue("jdbc.username");
		String password = Config.getValue("jdbc.password");

		// System.out.println(driverString);
		// System.out.println(dburl);
		// System.out.println(username);

		try {
			Class.forName(driverString);
			connection = DriverManager.getConnection(dburl, username, password);

		} catch (ClassNotFoundException e) {
			System.out.println("no driver class");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("open connection error");
			e.printStackTrace();
		}
		return connection;
	}

    public static void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    public static void closePreparedStatement(PreparedStatement psmt) throws SQLException {
        if (psmt != null) {
            psmt.close();
        }
    }

    public static void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public static void rollback(Connection conn) throws SQLException{	
        if (conn != null) {
            conn.rollback();
        }
	}

	public static void commit(Connection conn) throws SQLException{
        if (conn != null) {
            conn.commit();
        }
	}

	public static void beginTransaction(Connection conn) throws SQLException{
        if (conn != null) {
            conn.setAutoCommit(false);
        }
	}
}
