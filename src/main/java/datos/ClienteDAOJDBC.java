package datos;

import dominio.Cliente;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Bryan
 */
public class ClienteDAOJDBC {

    //Atributos SQL QUERYS
    private static final String SQL_SELECT = "SELECT id_cliente, nombre, apellido, email, telefono, saldo "
            + "FROM cliente";

    private static final String SQL_SELECT_BY_ID = "SELECT id_cliente, nombre, apellido, email, telefono, saldo "
            + "FROM cliente WHERE id_cliente= ?";

    private static final String SQL_INSERT = "INSERT INTO cliente ( nombre, apellido, email, telefono, saldo) "
            + "VALUES(?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE  cliente set nombre=?, apellido=?, email=?, telefono=?, saldo=? "
            + "WHERE id_cliente= ?";

    private static final String SQL_DELETE = "DELETE FROM cliente WHERE id_cliente = ?";

    //Métodos
    public List<Cliente> listar() {

        //1.- Se conecta a la DB y se prepara el Statement el Result
        Connection cnn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        //2.-Se instancia el cliente DOMINIO y se hace una lista
        Cliente cliente = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            cnn = Conexion.getConexion();
            //Prepara el QUERY
            stmt = cnn.prepareStatement(SQL_SELECT);
            //Ejecuata el QUERY recupera info
            rs = stmt.executeQuery();

            //Iteramos cada elemento que nos arroja el result set
            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                double saldo = rs.getDouble("saldo");

                cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);
                clientes.add(cliente);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.cerrarConexionResult(rs);
            Conexion.cerrarConexionPrepareS(stmt);
            Conexion.cerrarConexionDB(cnn);

        }

        return clientes;
    }

    public Cliente encontrar(Cliente cliente) {
        //1.- Se conecta a la DB y se prepara el Statement el Result
        Connection cnn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        

        try {
            cnn = Conexion.getConexion();
            //Prepara el QUERY
            stmt = cnn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, cliente.getIdCliente());
            //Ejecuta el QUERY Recupera info
            rs = stmt.executeQuery();
            //Nos posicionamos en el primer registro devuelto ya que ejecutamos el query
            rs.next();

            //Obtenemos los valores del registro
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String email = rs.getString("email");
            String telefono = rs.getString("telefono");
            double saldo = rs.getDouble("saldo");

            //Colocamos los valores del registro en el CLIENTE
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            cliente.setSaldo(saldo);

        } catch (SQLException ex) {

            ex.printStackTrace(System.out);

        } finally {
            Conexion.cerrarConexionResult(rs);
            Conexion.cerrarConexionPrepareS(stmt);
            Conexion.cerrarConexionDB(cnn);

        }

        return cliente;
    }

    //Este método Retorna los registros afectados
    //En este metodo no recuperaremos info
    // solo insertaremos así que no vamos a ocupar
    //Result set
    public int insertar(Cliente cliente) {

        //1.- Se conecta a la DB y se prepara el Statement el Result
        Connection cnn = null;
        PreparedStatement stmt = null;

        int rows = 0;

        try {

            cnn = Conexion.getConexion();
            //Prepara el QUERY
            stmt = cnn.prepareStatement(SQL_INSERT);
            //Vamos a proporcionar cada uno de los parametros:
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());

            //nos va a regresar cuantos registros se han modificado
            rows = stmt.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace(System.out);

        } finally {
            Conexion.cerrarConexionPrepareS(stmt);
            Conexion.cerrarConexionDB(cnn);

        }

        return rows;

    }

    public int actualizar(Cliente cliente) {

        //1.- Se conecta a la DB y se prepara el Statement el Result
        Connection cnn = null;
        PreparedStatement stmt = null;

        int rows = 0;

        try {

            cnn = Conexion.getConexion();
            //Prepara el QUERY
            stmt = cnn.prepareStatement(SQL_UPDATE);
            //Vamos a proporcionar cada uno de los parametros:
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());
            stmt.setInt(6, cliente.getIdCliente());

            //nos va a regresar cuantos registros se han modificado
            rows = stmt.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace(System.out);

        } finally {
            Conexion.cerrarConexionPrepareS(stmt);
            Conexion.cerrarConexionDB(cnn);

        }

        return rows;

    }

    public int eliminar(Cliente cliente) {

        //1.- Se conecta a la DB y se prepara el Statement el Result
        Connection cnn = null;
        PreparedStatement stmt = null;

        int rows = 0;

        try {

            cnn = Conexion.getConexion();
            //Prepara el QUERY
            stmt = cnn.prepareStatement(SQL_DELETE);
            //Vamos a proporcionar cada uno de los parametros:

            stmt.setInt(1, cliente.getIdCliente());

            //nos va a regresar cuantos registros se han modificado
            rows = stmt.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace(System.out);

        } finally {
            Conexion.cerrarConexionPrepareS(stmt);
            Conexion.cerrarConexionDB(cnn);

        }

        return rows;

    }

}
