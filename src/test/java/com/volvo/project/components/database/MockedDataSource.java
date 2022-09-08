package com.volvo.project.components.database;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class MockedDataSource implements DataSource {

    private Connection connection;

	public MockedDataSource(Connection connection) {
		this.connection = connection;
	}

	public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

   
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    
    public void setLogWriter(PrintWriter out) throws SQLException {
    }

   
    public void setLoginTimeout(int seconds) throws SQLException {
    }

   
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return false;
    }

  
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        return null;
    }

 
    public Connection getConnection() throws SQLException {
        return connection;
    }


    public Connection getConnection(String arg0, String arg1) throws SQLException {
        return null;
    }

}
