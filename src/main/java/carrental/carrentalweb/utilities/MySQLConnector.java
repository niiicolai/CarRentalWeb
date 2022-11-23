package carrental.carrentalweb.utilities;

import java.sql.Connection;
import java.sql.*;

/*
 * Den her klasse er noget rod.
 * 
 * Forklaring:
 * getConnection er en statisk metode implementeret i DriverManager.
 * - dvs. du ikke behøver at lave en instance for at kalde metoden,
 *   alt du skal gøre er at pass de nødvendige argumenter.
 * 
 * Hvad gør den her klasse lige nu?
 * 1. Du har en statiske metode fra JDBC API kaldet getConnection,
 *    som du så puttet i en ny klasse kaldet MySQLConnector, som
 *    hverken tilføjer noget nyt behavior, extender nuværende, eller lign.
 *    Alt der sker er du catcher en exception, hvilket giver endnu mindre mening,
 *    fordi du catcher samme exception igen i dine sub metoder pga. 
 *    prepareStatement() og resultSet også smider en SQL exception.
 */

/* Det her er dit nuværende setup: */
/* 
 * Forstil dig at CustomDriverManager er  
 * implementationen af DriverManager i JDBC API,
 * som indeholder getConnection metoden.
 */
class CustomDriverManager {
    public static Connection getConnection(String url, String username, String password)
            throws SQLException {
        return null;
    }
}

/* 
 * Vi indsætter CustomDriverManger 
 * som placeholder for DriverManager.
 * (se linje 60)
 */
public class MySQLConnector {
    Connection conn;
    public static MySQLConnector instance;

    public static MySQLConnector getInstance() {
        if (instance == null) {
            instance = new MySQLConnector();
        }
        return instance;
    }

    public Connection getConnection(String url, String username, String password) {
        if (conn == null) {
            try {
                /* 
                * Forstil dig at CustomDriverManager er  
                * implementationen af DriverManager i JDBC API,
                * som indeholder getConnection metoden.
                */
                conn = CustomDriverManager.getConnection(url, username, password);
                //conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println("SQL Connector: Cannot connect to database");
                e.printStackTrace();
            }
        }
        return conn;
    }
}

/* 
 * Og derefter har du så dine repository,
 * hvor du har kaldt dit singleton instance for at bruge en metode,
 * som du bare kunne have brugt direkte som DriverManger.getConnection(url, username, password),
 * og havde fået 100% samme resultat, udover du ikke behøvede den her unødvendige klasse.
 */
class CarRepository {
    public void find(long primaryKey) {
        try {
            Connection connection = MySQLConnector.instance.getConnection("", "", "");
            PreparedStatement statement = connection.prepareStatement("");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/* 
 * Hvorfor ikke bare slette MySQLConnector også blot skrive følgende 
 * i dit repository i stedet.
 */
class CarRepository {
    public void find(long primaryKey) {
        try {
            Connection connection = CustomDriverManager.getConnection("", "", "");
            PreparedStatement statement = connection.prepareStatement("");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}