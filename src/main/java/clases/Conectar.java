/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class Conectar {

    private static final Varios c_varios = new Varios();
    private static final String DIRECCION = c_varios.obtenerDireccionCarpeta();

    //private static final String url= direccion +"/pesaje_db.db";
    private static Connection conexion = null;
    private static final String BD = "brunoasc_liquidacionvilca"; // Nombre de BD.
    private static final String USER = "brunoasc_luis_bd"; // Usuario de BD.
    private static final String PASSWORD = "C]6&TN4Bt@&I"; // Password de BD.

    // Driver para MySQL en este caso.
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    String server = "jdbc:mysql://artemisa.servidoresph.com:3306/" + BD;

    /**
     * ***** Prueba con SQLite
     *
     ******
     * @return
     */
    //private static String driver = "com.sqlite.jdbc.Driver";
    //private static String driver = "com.sqlite.jdbc.Driver";
    //String server = "jdbc:sqlite:" + url;
    public boolean conectar() {
        boolean conectado;
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(server, USER, PASSWORD);
            conectado = true;
            System.out.println("Conectando al Servidor: " + server);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: Imposible realizar la conexion a BD." + server + "," + USER + "," + PASSWORD + "\n" + e.getLocalizedMessage());
            System.out.print(e);
            e.printStackTrace();
            System.exit(0);
            conectado = false;
        }
        return conectado;
    }

    /**
     * Método para establecer la conexión con la base de datos.
     *
     * @return
     */
    public Connection conx() {
        return conexion;
    }

    public Statement conexion() {
        Statement st = null;
        try {
            st = conexion.createStatement();
        } catch (SQLException e) {
            System.out.println("Error: Conexión incorrecta.");
            e.printStackTrace();
        }
        return st;
    }

    /**
     * Método para realizar consultas del tipo: SELECT * FROM tabla WHERE..."
     *
     * @param st
     * @param cadena La consulta en concreto
     * @return
     */
    public ResultSet consulta(Statement st, String cadena) {
        ResultSet rs = null;
        try {
            rs = st.executeQuery(cadena);
        } catch (SQLException e) {
            System.out.println("Error con: " + cadena);
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Método para realizar consultas de actualización, creación o eliminación.
     *
     * @param st
     * @param cadena La consulta en concreto
     * @return
     */
    public int actualiza(Statement st, String cadena) {
        int rs = -1;
        try {
            rs = st.executeUpdate(cadena);
        } catch (SQLException e) {
            System.out.println("Error con: " + cadena);
            System.out.println("SQLException: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Método para cerrar la consula
     *
     * @param rs
     */
    public void cerrar(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.print("Error: No es posible cerrar la consulta.");
            }
        }
    }

    /**
     * Método para cerrar la conexión.
     *
     * @param st
     */
    public void cerrar(java.sql.Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                System.out.print("Error: No es posible cerrar la conexión.");
            }
        }
    }
}
