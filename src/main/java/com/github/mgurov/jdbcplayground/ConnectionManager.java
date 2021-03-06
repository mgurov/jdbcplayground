package com.github.mgurov.jdbcplayground;

import org.postgresql.jdbc2.optional.SimpleDataSource;

import javax.sql.DataSource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionManager {

    public static final String PGUSER = "postgres";

    // Extract connection URL from environment variable as setup by docker (or manually)
    public static String getConnectionUrl() {
        String dbPort = System.getenv("DB_PORT");

        // Fallback for alexec Plugin which does not support configuration of link aliases
        if (dbPort == null) {
            dbPort = System.getenv("SHOOTOUT_DOCKER_MAVEN_DB_PORT");
        }
        if (dbPort == null) {
            throw new IllegalArgumentException("No DB_PORT or SHOOTOUT_DOCKER_MAVEN_DB_PORT environment variable set. Please check you docker link parameters.");
        }
        Pattern pattern = Pattern.compile("^[^/]*//(.*)");
        Matcher matcher = pattern.matcher(dbPort);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid format of DB_PORT variable: Expected tcp://host:port and not " + dbPort);
        }
        String hostAndPort = matcher.group(1);
        return "jdbc:postgresql://" + hostAndPort + "/postgres";
    }

    public static DataSource makeDatasource() {
        SimpleDataSource ds = new SimpleDataSource();
        ds.setUrl(getConnectionUrl());
        ds.setUser(PGUSER);
        return ds;
    }
}
