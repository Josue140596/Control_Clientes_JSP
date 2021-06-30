package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Bryan
 */
public class Conexion {

    //Atributos
    private static final String URL = "jdbc:mysql://localhost:3306/control_clientes_java?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static BasicDataSource dataSource;

    //Metodos
    //Este metodo recupera una conexion de la DB
    public static DataSource getDataSource() {

        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl(URL);
            dataSource.setUsername(USERNAME);
            dataSource.setPassword(PASSWORD);
            dataSource.setInitialSize(50);
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        }

        return dataSource;
    }

    //Conectará a la DB
    public static Connection getConexion() throws SQLException {
        return getDataSource().getConnection();
    }

    //Cierra la conexion de un tipo Result Set
    public static void cerrarConexionResult(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    //Cierra la conexion de un tipo PrepareStatement
    public static void cerrarConexionPrepareS(PreparedStatement stmt) {
        try {
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    //Cierra la conexion a la DB
    public static void cerrarConexionDB(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

}
