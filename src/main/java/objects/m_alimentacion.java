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
public class m_alimentacion {

    Conectar conectar = new Conectar();

    public void llenarAlimentos(JComboBox combobox) {
        try {

            combobox.removeAllItems();
            Statement st = conectar.conexion();
            String query = "select * "
                    + "from parametros_detalle "
                    + "where idparametros = '4' "
                    + "order by descripcion asc";
            ResultSet rs = conectar.consulta(st, query);

            int encontrado = 0;
            while (rs.next()) {
                encontrado++;
                String texto = rs.getString("descripcion");
                combobox.addItem(new o_combobox(rs.getInt("iddetalle"), texto));
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
