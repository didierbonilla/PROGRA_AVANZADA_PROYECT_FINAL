package programacion.avanzada.programacion_avanzada_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private Connection conn;

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HOTELDB;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "1234";

    public boolean connect() {
        boolean status = false;
        try {
            this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
            status = true;
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
            this.conn = null;
        }
        return status;
    }

    public Connection getConnection() {
        return this.conn;
    }
}
