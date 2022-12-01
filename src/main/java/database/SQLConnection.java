package database;

import java.sql.*;

public class SQLConnection {

    private Statement statement;

    public SQLConnection(String URL) {
        try  {
            Connection connection = DriverManager.getConnection(URL);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getSQLState());
        }
    }

    public String selectFunction(String command, int columnsLength) throws SQLException {
        ResultSet resultSet = this.statement.executeQuery(command);
        StringBuilder result = new StringBuilder();

        while (resultSet.next()) {
            for (int i = 1; i <= columnsLength; i++) {
                result.append(resultSet.getString(i)).append("_");
            }
            result.append(";");
        }
        return result.toString();
    }

    public void insertFunction(String command) throws SQLException {
        this.statement.executeQuery(command);
    }

    public String insertFunctionWithResult(String command) throws SQLException {
        ResultSet resultSet = this.statement.executeQuery(command);
        System.out.println(resultSet);
        resultSet.next();
        return resultSet.getString(1);
    }

    public void close() throws SQLException {
        statement.close();
    }

}