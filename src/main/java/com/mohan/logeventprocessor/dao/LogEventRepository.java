package com.mohan.logeventprocessor.dao;


import com.mohan.logeventprocessor.domain.LogEvent;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Objects;

@Slf4j
public class LogEventRepository {

    private static Connection connection;
    private static LogEventRepository logEventRepository;

    private static final String LOG_EVENTS = "LOG_EVENTS";

    public static LogEventRepository getInstance() {
        if(Objects.isNull(logEventRepository)){
            synchronized (LogEventRepository.class){
                if(Objects.isNull(logEventRepository)){
                    try{
                        logEventRepository = new LogEventRepository();
                    }catch (SQLException ex){
                        log.error("Error Occurred while making connection to DB ",ex);
                    }
                }
            }
        }
        return logEventRepository;
    }
    private LogEventRepository() throws SQLException {
        String connectionString = "jdbc:hsqldb:file:hsqldb/logEvents";
        log.debug("Opening database connection at < hsqldb/logEvents >");
        connection = DriverManager.getConnection(connectionString, "SA", "");
        createEventsTable();
    }

    public void createEventsTable() throws SQLException {
        String createEvents = "CREATE TABLE IF NOT EXISTS "+LOG_EVENTS+" (id VARCHAR(50) NOT NULL, duration FLOAT NOT NULL, " +
                "type VARCHAR(50), host VARCHAR(50), alert BOOLEAN NOT NULL)";

        log.debug("Creating {} table",LOG_EVENTS);
        connection.createStatement().executeUpdate(createEvents);
    }

    public void writeEvent(LogEvent event) throws SQLException {
        String addEvent = "INSERT INTO " + LOG_EVENTS + " (id, duration, type, host, alert)  VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(addEvent);
        preparedStatement.setString(1, event.getId());
        preparedStatement.setFloat(2, event.getDuration());
        preparedStatement.setString(3, event.getType());
        preparedStatement.setString(4, event.getHost());
        preparedStatement.setBoolean(5, event.isAlert());
        preparedStatement.executeUpdate();
    }

    public void closeDatabase() throws SQLException {
        log.info("Closing DB connection.");
        connection.close();
    }

    public void selectAlertEvent()  {
        try{
            String getAll = "SELECT * FROM " + LOG_EVENTS + " where alert=TRUE";
            log.debug("Retrieving all DB entries from < " + LOG_EVENTS + " > table.");
            ResultSet resultSet = connection.createStatement().executeQuery(getAll);
            if (!resultSet.isBeforeFirst() ) {
                System.out.println("No data found ");
            }else{
                while (resultSet.next()) {
                    log.info("Alert for EventID < {} > , time taken {}ms ",resultSet.getString("id"),resultSet.getLong("duration"));
                }
            }
        }catch (SQLException ex){
            log.error("Error occurred while getting data from {} table ",LOG_EVENTS,ex);
        }

    }

    public void selectAll()  {
        try{
            String getAll = "SELECT * FROM " + LOG_EVENTS;
            log.debug("Retrieving all DB entries from < " + LOG_EVENTS + " > table.");
            ResultSet resultSet = connection.createStatement().executeQuery(getAll);
            if (!resultSet.isBeforeFirst() ) {
                System.out.println("No data found ");
            }else{
                while (resultSet.next()) {
                    log.info("Persisted EventID < {} > ",resultSet.getString(1));
                }
            }
        }catch (SQLException ex){
            log.error("Error occurred while getting data from {} table ",LOG_EVENTS,ex);
        }

    }

    public void deleteAll() {
        String deleteAll = "DELETE FROM " + LOG_EVENTS;
        log.warn("Deleting all entries in DB table < " + LOG_EVENTS + " >.");
        try{
            connection.createStatement().executeUpdate(deleteAll);
            log.info("All entries deleted from DB table < " + LOG_EVENTS + " >.");
        }catch(Exception ex){
            log.error("Error occurred while deleting data from {} table ",LOG_EVENTS,ex);
        }

    }
}
