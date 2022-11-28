package carrental.carrentalweb.services;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;
import java.sql.*;

@Service
public class DatabaseService {

    private Connection connection;

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    public DatabaseService () {
    }

    public DatabaseService (String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    private PreparedStatement prepareStatement(String sql, DatabaseRequestBody body) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < body.size(); i++)
            statement.setObject(i + 1, body.next());
        System.out.println(statement);
        return statement;
    }

    public DatabaseResponse executeUpdate(String sql, DatabaseRequestBody body) {
        DatabaseResponse response = new DatabaseResponse();
        try {
            PreparedStatement statement = prepareStatement(sql, body);
            statement.executeUpdate();
        } catch (SQLException e) {
            response.setError(e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    public DatabaseResponse executeQuery(String sql, DatabaseRequestBody body) {
        DatabaseResponse response = new DatabaseResponse();
        try {
            PreparedStatement statement = prepareStatement(sql, body);
            ResultSet resultSet = statement.executeQuery();
            parseResultSet(resultSet, response);
            
        } catch (SQLException e) {
            response.setError(e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    // Parse result list
    private static void parseResultSet(ResultSet resultSet, DatabaseResponse response) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
            
        while(resultSet.next()) {
            HashMap<String, Object> recordValues = new HashMap<>();
            for (int j = 1; j < columnCount + 1; j++) {
                String columnName = metaData.getColumnName(j);
                Object value = resultSet.getObject(columnName);
                recordValues.put(columnName, value);
            }
            response.add(new DatabaseRecord(recordValues));
        }
    }

    public String getUrl() {return url;}
}