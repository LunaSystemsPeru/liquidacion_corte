/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import clases.Conectar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Mariela
 */
public class m_clientes {
    Conectar conectar = new Conectar();
    
    public void llenarClientes(JComboBox combobox) {
        try {

            combobox.removeAllItems();
            Statement st = conectar.conexion();
            String query = "select * "
                    + "from clientes "
                    + "order by nombre, sede asc";
            ResultSet rs = conectar.consulta(st, query);

            int encontrado = 0;
            while (rs.next()) {
                encontrado++;
                String texto = rs.getString("nombre") + " | " + rs.getString("sede");
                combobox.addItem(new o_combobox(rs.getInt("idcliente"), texto));
            }
            if (encontrado == 0) {
                combobox.addItem(new o_combobox(0, "NO HAY DATOS"));
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

}
