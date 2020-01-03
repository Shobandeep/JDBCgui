import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class QueryLoader {

    private final String user = "database user login";
    private final String password = "database user password";
    private final String serverName = "server you're trying tro connect to";
    private final int port = 1433;

    private String database;
    private String[] columns;
    private String[][] rows;
    private String query;


    public QueryLoader(String query) {
        extractQuery(query);
    }

    public void runProc() {
        // Create datasource.
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setServerName(serverName);
        ds.setPortNumber(port);
        ds.setDatabaseName(database);
        try (Connection con = ds.getConnection();
             CallableStatement cstmt = con.prepareCall(query);) {
            // Execute a stored procedure that returns some data.
            //cstmt.setInt(1, 50);
            ResultSet resultSet = cstmt.executeQuery();

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public void runQuery() {
        // Create datasource.
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setServerName(serverName);
        ds.setPortNumber(port);
        ds.setDatabaseName(database);
        try (Connection con = ds.getConnection();
             CallableStatement cstmt = con.prepareCall(query);) {
            // Execute a stored procedure that returns some data.
            //cstmt.setInt(1, 50);
            ResultSet resultSet = cstmt.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            // get headers
            int columnNum = resultSetMetaData.getColumnCount();
            columns = new String[columnNum];
            for(int i = 0; i < columnNum; i++)
                columns[i] = resultSetMetaData.getColumnName(i+1);

            // get rows
            ArrayList temp = new ArrayList();
            while(resultSet.next()) {
                for(int i = 0; i < columnNum; i++)
                    temp.add(resultSet.getString(i+1));
            }
            int rowNum = temp.size() / columnNum;
            System.out.println("Total number of rows: " + rowNum);
            rows = new String[rowNum][columnNum];
            int increment = 0;
            for(int i = 0; i < rowNum; i++) {
                resultSet.next();
                for(int j = 0; j < columnNum; j++) {
                    rows[i][j] = (String)temp.get(increment++);
                }
            }



        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();

        }


    }
    private void extractQuery(String data) {

        StringBuilder sb = new StringBuilder();
        File file = new File(data);
        try {
            Scanner scan = new Scanner(file);

            scan.next();
            database = scan.next();
            while(scan.hasNext())
                sb.append(scan.next() + " ");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        query = sb.toString();
    }


    public String[] getColumns() {
        return columns;
    }

    public String[][] getRows() {
        return rows;
    }

}
