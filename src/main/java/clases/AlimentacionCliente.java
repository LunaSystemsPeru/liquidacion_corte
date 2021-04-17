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
public class AlimentacionCliente {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private int iditem;
    private int idcliente;
    private double precio;

    public AlimentacionCliente() {
        this.idcliente = frm_principal.cliente.getIdcliente();
    }

    public int getIditem() {
        return iditem;
    }

    public void setIditem(int iditem) {
        this.iditem = iditem;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean insertar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "insert into alimentacion_cliente "
                + "values ('" + iditem + "', '" + idcliente + "', '" + precio + "')";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean eliminar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "delete from alimentacion_cliente "
                + "where iddetalle = '" + this.iditem + "' and idcliente = '" + this.idcliente + "'";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean modificar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "update from alimentacion_cliente "
                + "set precio = '" + this.precio + "' "
                + "where iddetalle = '" + this.iditem + "' and idcliente = '" + this.idcliente + "'";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean obtenerDatos() {
        boolean existe = false;
        try {
            Statement st = conectar.conexion();
            String query = "select * from alimentacion_cliente "
                    + "where iddetalle = '" + this.iditem + "' and idcliente = '" + this.idcliente + "'";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.precio = rs.getDouble("precio");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }

    public void llenarTabla(JTable tabla) {
        String sql = "select pd.iddetalle, pd.descripcion, ifnull(ac.precio, 0) as precio "
                + "from parametros_detalle as pd "
                + "left join alimentacion_cliente as ac on pd.iddetalle = ac.iddetalle and ac.idcliente = '" + this.idcliente + "' "
                + "where pd.idparametros = '4'";

        DefaultTableModel modelo;
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return columna == 2;

            }
        };
        try {
            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, sql);

            modelo.addColumn("Item");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Peso Unit");
            modelo.addColumn("");

            int nrofila = 0;
            while (rs.next()) {
                nrofila++;
                Object fila[] = new Object[4];
                fila[0] = nrofila;
                fila[1] = rs.getString("descripcion");
                fila[2] = rs.getDouble("precio");
                fila[3] = rs.getInt("iddetalle");
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

            tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(0);
            varios.centrar_celda(tabla, 0);
            varios.derecha_celda(tabla, 2);

            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

}
