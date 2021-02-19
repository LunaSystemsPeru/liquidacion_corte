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
public class Obrero {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private int idobrero;
    private String datos;
    private String fecingreso;
    private String codsistema;
    private int idcliente;
    private String nrodocumento;
    private String nrocuenta;

    public Obrero() {
    }

    public int getIdobrero() {
        return idobrero;
    }

    public void setIdobrero(int idobrero) {
        this.idobrero = idobrero;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getFecingreso() {
        return fecingreso;
    }

    public void setFecingreso(String fecingreso) {
        this.fecingreso = fecingreso;
    }

    public String getCodsistema() {
        return codsistema;
    }

    public void setCodsistema(String codsistema) {
        this.codsistema = codsistema;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public void obtenerId() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(idempleado) + 1, 1) as codigo "
                    + "from obreros ";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.idobrero = rs.getInt("idempleado");
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
        String query = "insert into obreros "
                + "values ('" + idobrero + "', '" + datos + "', '" + fecingreso + "', '" + codsistema + "', '" + idcliente + "', '" + nrodocumento + "', '" + nrocuenta + "')";
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
        String query = "delete from obreros "
                + "where idempleado = '" + this.idobrero + "'";
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
            String query = "select * from obreros "
                    + "where idempleado = '" + this.idobrero + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.datos = rs.getString("datos");
                this.fecingreso = rs.getString("fec_ingreso");
                this.codsistema = rs.getString("cod_sistema");
                this.idcliente = rs.getInt("idcliente");
                this.nrodocumento = rs.getString("nrodocumento");
                this.nrocuenta = rs.getString("nrocuenta");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }

    public void verFilas(JTable tabla, String sql) {
        DefaultTableModel modelo;
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        try {
            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, sql);

            modelo.addColumn("id");
            modelo.addColumn("Datos");
            modelo.addColumn("Cod Sist.");
            modelo.addColumn("Nro DOC");
            modelo.addColumn("# Cuenta");
            modelo.addColumn("Fec. Ingreso");

            while (rs.next()) {
                Object fila[] = new Object[6];
                fila[0] = rs.getString("idempleado");
                fila[1] = rs.getString("datos");
                fila[2] = rs.getString("cod_sistema");
                fila[3] = rs.getString("nrodocumento");
                fila[4] = rs.getString("nrocuenta");
                fila[5] = rs.getString("fec_ingreso");
                modelo.addRow(fila);

            }

            tabla.setModel(modelo);

            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(350);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(100);
            varios.centrar_celda(tabla, 0);
            varios.centrar_celda(tabla, 2);
            varios.centrar_celda(tabla, 3);
            varios.centrar_celda(tabla, 4);
            varios.centrar_celda(tabla, 5);

            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }
}
