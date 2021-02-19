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
public class ParametroGeneral {

    Conectar conectar = new Conectar();

    private int idparametro;
    private String nombre;
    private String valor;

    public ParametroGeneral() {
    }

    public int getIdparametro() {
        return idparametro;
    }

    public void setIdparametro(int idparametro) {
        this.idparametro = idparametro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void obtenerId() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(idparametros) + 1, 1) as codigo "
                    + "from parametros ";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.idparametro = rs.getInt("codigo");
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
        String query = "insert into parametros "
                + "values ('" + idparametro + "', '" + nombre + "', '" + valor + "')";
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
        String query = "delete from parametros "
                + "where idparametros = '" + this.idparametro + "'";
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
            String query = "select * from parametros "
                    + "where idparametros = '" + this.idparametro + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.nombre = rs.getString("nombre");
                this.valor = rs.getString("valor");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }
    
    public void cargarParametros(JTable tabla) {
        DefaultTableModel modelo;
        try {
            modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            Statement st = conectar.conexion();
            String query = "select * from parametros order by nombre asc";
            ResultSet rs = conectar.consulta(st, query);

            modelo.addColumn("id");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Valor");

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("idparametros");
                fila[1] = rs.getString("nombre");
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
