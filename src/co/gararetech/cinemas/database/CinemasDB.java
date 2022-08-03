package co.gararetech.cinemas.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CinemasDB extends Config {

    private Connection connection;
    private Statement statement;
    
    public CinemasDB() throws ClassNotFoundException, SQLException {
        super();
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://" + super.getHost() + ":3306/" + this.getDatabase() + "?user=" + this.getUser() + "&password=" + this.getPass());
        this.statement = this.connection.createStatement();
    }

    public Connection getConnection() {
        return connection;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Statement getStatement() {
        return statement;
    }
    
    
}
