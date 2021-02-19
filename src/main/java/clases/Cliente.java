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
public class Cliente {

    Conectar conectar = new Conectar();

    private int idcliente;
    private String nombre;
    private String ruc;
    private String proveedor;
    private String sede;

    public Cliente() {
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public void obtenerId() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(idcliente) + 1, 1) as codigo "
                    + "from clientes ";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.idcliente = rs.getInt("codigo");
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
        String query = "insert into clientes "
                + "values ('" + idcliente + "', '" + nombre + "', '" + ruc + "','" + sede + "','" + proveedor + "')";
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
        String query = "delete from clientes "
                + "where idcliente = '" + idcliente + "'";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean compararRUC() {
        boolean existe = false;
        try {
            Statement st = conectar.conexion();
            String query = "select idcliente "
                    + "where ruc = '" + this.ruc + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.idcliente = rs.getInt("idcliente");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }

    public boolean obtenerDatos() {
        boolean existe = false;
        try {
            Statement st = conectar.conexion();
            String query = "select * from clientes "
                    + "where idcliente = '" + this.idcliente + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.nombre = rs.getString("nombre");
                this.ruc = rs.getString("ruc");
                this.sede = rs.getString("sede");
                this.proveedor = rs.getString("empresa_proveedor");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }
    
    
    public void cargarClientes(JTable tabla, String query) {
        DefaultTableModel modelo;
        try {
            modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, query);

            modelo.addColumn("id");
            modelo.addColumn("RUC");
            modelo.addColumn("Cliente");
            modelo.addColumn("Sede");
            modelo.addColumn("Empresa");

            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("idcliente");
                fila[1] = rs.getString("ruc");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getString("sede");
                fila[4] = rs.getString("empresa_proveedor");

                modelo.addRow(fila);

            }
            conectar.cerrar(st);
            conectar.cerrar(rs);

            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(300);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(200);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(350);

        } catch (SQLException e) {
            System.out.print(e);
        }
    }

}
