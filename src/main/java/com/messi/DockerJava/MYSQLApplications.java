package com.messi.DockerJava;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public class MYSQLApplications {
	
	private static final Logger log;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log =Logger.getLogger(MYSQLApplications.class.getName());
    }

    public static String getDataBaseConnectionCheck(){
        try {
			log.info("Loading application properties");
			Properties properties = new Properties();
			properties.load(MYSQLApplications.class.getClassLoader().getResourceAsStream("application.properties"));

			log.info("Connecting to the database");
			Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
			log.info("Database connection test: " + connection.getCatalog());

			/*
			 * log.info("Create database schema"); Scanner scanner = new
			 * Scanner(MYSQLApplication.class.getClassLoader().getResourceAsStream(
			 * "schema.sql")); Statement statement = connection.createStatement(); while
			 * (scanner.hasNextLine()) { statement.execute(scanner.nextLine()); }
			 */

			
			Todo todo = new Todo(1L, "configuration", "congratulations, you have set up JDBC correctly!", true);
			todo = readData(connection);
			todo.setDetails("congratulations, you have updated data!");

			log.info("Closing database connection");
			connection.close();
			return todo.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();

		} 
    }
    
    private static Todo readData(Connection connection) throws SQLException {
        log.info("Read data");
        PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM todo;");
        ResultSet resultSet = readStatement.executeQuery();
        if (!resultSet.next()) {
            log.info("There is no data in the database!");
            return null;
        }
        Todo todo = new Todo();
        todo.setId(resultSet.getLong("id"));
        todo.setDescription(resultSet.getString("description"));
        todo.setDetails(resultSet.getString("details"));
        todo.setDone(resultSet.getBoolean("done"));
        log.info("Data read from the database: " + todo.toString());
        return todo;
    }

}
