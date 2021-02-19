/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mariela
 */
public class ParametroDetalle {

    Conectar conectar = new Conectar();

    private int iddetalle;
    private String descripcion;
    private String valor;
    private int idparametro;

    public ParametroDetalle() {
    }

    public int getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(int iddetalle) {
        this.iddetalle = iddetalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getIdparametro() {
        return idparametro;
    }

    public void setIdparametro(int idparametro) {
        this.idparametro = idparametro;
    }

    public void obtenerId() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(iddetalle) + 1, 1) as codigo "
                    + "from parametros_detalle ";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.iddetalle = rs.getInt("codigo");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }

    public boolean insertar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "insert into parametros_detalle "
                + "values ('" + iddetalle + "', '" + descripcion + "', '" + valor + "', '" + idparametro + "')";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean actualizar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "update parametros_detalle "
                + "set descripcion = '" + descripcion + "', "
                + "valor = '" + valor + "' "
                + "where iddetalle = '" + this.iddetalle + "'";
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
        String query = "delete from parametros_detalle "
                + "where iddetalle = '" + this.iddetalle + "'";
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
            String query = "select * from parametros_detalle "
                    + "where iddetalle = '" + this.iddetalle + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.descripcion = rs.getString("descripcion");
                this.valor = rs.getString("valor");
                this.idparametro = rs.getInt("idparametros");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }

    public void cargarDetalles(JTable tabla) {
        DefaultTableModel modelo;
        try {
            modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            Statement st = conectar.conexion();
            String query = "select * "
                    + "from parametros_detalle "
                    + "where idparametros = '" + this.idparametro + "' "
                    + "order by descripcion asc";
            ResultSet rs = conectar.consulta(st, query);

            modelo.addColumn("id");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Valor");

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("iddetalle");
                fila[1] = rs.getString("descripcion");
                fila[2] = rs.getString("valor");

                modelo.addRow(fila);

            }
            conectar.cerrar(st);
            conectar.cerrar(rs);

            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(100);

        } catch (SQLException e) {
            System.out.print(e);
        }
    }

}
