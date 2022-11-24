package carrental.carrentalweb.services;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseService {

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(url, username, password);

        return connection;
    }

    private PreparedStatement prepareStatement(String sql, List<Object> values) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < values.size(); i++)
            statement.setObject(i + 1, values.get(i));
        System.out.println(statement);
        return statement;
    }

    public void executeUpdate(String sql, List<Object> values) {
        try {
            PreparedStatement statement = prepareStatement(sql, values);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HashMap<String, Object>> executeQuery(String sql, List<Object> values) {
        List<HashMap<String, Object>> result = null;
        try {
            PreparedStatement statement = prepareStatement(sql, values);
            ResultSet resultSet = statement.executeQuery();
            result = parseResultSet(resultSet);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Parse result list
    private static List<HashMap<String, Object>> parseResultSet(ResultSet resultSet) throws SQLException {
        LinkedList<HashMap<String, Object>> resultList = new LinkedList<>();  
        ResultSetMetaData metaData = resultSet.getMetaData();

        int k = metaData.getColumnCount();
            
        while(resultSet.next()) {
                
            HashMap<String, Object> result = new HashMap<>();
            for (int j = 1; j < k + 1; j++) {
                String columnName = metaData.getColumnName(j);
                Object value = resultSet.getObject(columnName);
                result.put(columnName, value);
            }
                
            resultList.add(result);
        }

        return resultList;
    }
}