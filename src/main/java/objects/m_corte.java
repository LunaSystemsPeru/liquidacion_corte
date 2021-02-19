/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import clases.Conectar;
import com.lunasystems.liquidacion.frm_principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Mariela
 */
public class m_corte {

    public int idcliente;

    public m_corte() {
        this.idcliente = frm_principal.cliente.getIdcliente();
    }

    Conectar conectar = new Conectar();

    public void llenarMeses(JComboBox combobox, String anio) {
        try {

            combobox.removeAllItems();
            Statement st = conectar.conexion();
            String query = "select month(fecha) as mes "
                    + "from corte "
                    + "where idcliente = '" + this.idcliente + "' and year(fecha) = '" + anio + "' "
                    + "group by month(fecha) "
                    + "order by month(fecha) asc";
            ResultSet rs = conectar.consulta(st, query);

            int encontrado = 0;
            while (rs.next()) {
                encontrado++;
                String texto = rs.getString("mes");
                combobox.addItem(new o_combobox(rs.getInt("mes"), texto));
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
