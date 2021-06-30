package web;

import datos.ClienteDAOJDBC;
import dominio.Cliente;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author Bryan
 */
@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recuperamos el parametro de accion
        String accion = req.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "editar":
                    this.editarCliente(req, resp);
                    break;
                case "eliminar":
                    this.eliminarCliente(req, resp);
                    break;
                default:
                    this.accionDefault(req, resp);
            }
        }

        //Regresar Todos los clientes
        this.accionDefault(req, resp);

    }

    private void accionDefault(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Regresar Todos los clientes
        List<Cliente> clientes = new ClienteDAOJDBC().listar();
        System.out.println("Clientes = " + clientes);

        //Antes
        //req.setAttribute("clientes", clientes);
        //req.setAttribute("totalClientes", clientes.size());
        //req.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        //Aquí hay un problema ya que el url no cambia 
        // req.getRequestDispatcher("clientes.jsp").forward(req, resp);
        //solucion:
        //Despues
        HttpSession session = req.getSession();
        session.setAttribute("clientes", clientes);
        session.setAttribute("totalClientes", clientes.size());
        session.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        resp.sendRedirect("clientes.jsp");

    }

    private double calcularSaldoTotal(List<Cliente> clientes) {
        double saldoTotal = 0;
        for (Cliente cliente : clientes) {
            saldoTotal += cliente.getSaldo();

        }
        return saldoTotal;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recuperamos el parametro de accion
        String accion = req.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "insertar":
                    this.insertarCliente(req, resp);
                    break;
                case "modificar":
                    this.modificarCliente(req, resp);
                    break;
                default:
                    this.accionDefault(req, resp);
            }
        } else {
            this.accionDefault(req, resp);
        }
    }

    private void insertarCliente(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recuperamos los valores del form
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");
        //Pasar el string a double
        double saldo = 0;
        String saldoString = req.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //Creamos el objeto de cliente (modelo)
        Cliente cliente = new Cliente(nombre, apellido, email, telefono , saldo);

        //Insertamos en la base de datos el Cliente
        int registrosModificado = new ClienteDAOJDBC().insertar(cliente);
        System.out.println("registrosModificado" + registrosModificado);

        //Redirigimos hacia accion por default
        this.accionDefault(req, resp);
    }

    private void editarCliente(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            //Recuperar el IdCliente
            int idCliente = Integer.parseInt(req.getParameter("idCliente"));
         
            Cliente cliente = new ClienteDAOJDBC().encontrar(new Cliente(idCliente));

            //Lo ponemos a algun alcance
            req.setAttribute("cliente", cliente);
            //Definimos el jsp
            String jspEditar = "/WEB-INF/paginas/cliente/editarCliente.jsp";
            // Mandamos info a el dispatcher
            req.getRequestDispatcher(jspEditar).forward(req, resp);
    }

    private void modificarCliente(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            //Recuperamos los valores del form pero de editar cliente
        int idCliente = Integer.parseInt(req.getParameter("idCliente"));
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");
        //Pasar el string a double
        double saldo = 0;
        String saldoString = req.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //Creamos el objeto de cliente (modelo)
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);

        //Insertamos en la base de datos el Cliente
        int registrosModificado = new ClienteDAOJDBC().actualizar(cliente);
        System.out.println("registrosModificado" + registrosModificado);

        //Redirigimos hacia accion por default
        this.accionDefault(req, resp);
    }

    private void eliminarCliente(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
             //Recuperamos los valores del form pero de editar cliente
        int idCliente = Integer.parseInt(req.getParameter("idCliente"));
   
        

        //Creamos el objeto de cliente (modelo)
        Cliente cliente = new Cliente(idCliente);

        //Eliminar en la base de datos el Cliente
        int registrosModificado = new ClienteDAOJDBC().eliminar(cliente);
        System.out.println("registrosModificado" + registrosModificado);


    }

}
