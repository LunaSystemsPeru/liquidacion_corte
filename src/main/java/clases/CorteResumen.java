/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import com.lunasystems.liquidacion.frm_principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mariela
 */
public class CorteResumen {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private final int idcliente;

    public CorteResumen() {
        this.idcliente = frm_principal.cliente.getIdcliente();
    }

    public void verDiasdelMes(JTable tabla, String anio, String mes) {
        DefaultTableModel modelo;
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        try {
            Statement st = conectar.conexion();
            String sql = "select c.fecha, sum(c.peso) as tot_kilos, COUNT(DISTINCT(c.idempleado)) as totalcortadores "
                    + "from corte as c "
                    + "where YEAR(c.fecha) = '" + anio + "' and month(c.fecha) = '" + mes + "' and c.idcliente = '" + this.idcliente + "' "
                    + "GROUP by c.fecha";
            //System.out.println(sql);
            ResultSet rs = conectar.consulta(st, sql);

            modelo.addColumn("Item");               //0
            modelo.addColumn("Fecha");          //1
            modelo.addColumn("Nro Cortadores");             //2
            modelo.addColumn("total Kg");           //3

            int nrofila = 0;
            Object fila[] = new Object[4];
            while (rs.next()) {
                nrofila++;
                double dpeso = rs.getDouble("tot_kilos");
                fila[0] = nrofila;
                fila[1] = rs.getString("fecha");
                fila[2] = rs.getInt("totalcortadores");
                fila[3] = varios.formato_numero(dpeso);
                modelo.addRow(fila);
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);

            tabla.setModel(modelo);

            tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            varios.centrar_celda(tabla, 0);
            varios.centrar_celda(tabla, 1);
            varios.derecha_celda(tabla, 2);
            varios.derecha_celda(tabla, 3);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }
}
