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
public class Corte {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private int idcorte;
    private String fecha;
    private int idempleado;
    private double peso;
    private double costokilo;
    private int idcliente;

    public Corte() {
    }

    public int getIdcorte() {
        return idcorte;
    }

    public void setIdcorte(int idcorte) {
        this.idcorte = idcorte;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public double getCostokilo() {
        return costokilo;
    }

    public void setCostokilo(double costokilo) {
        this.costokilo = costokilo;
    }

    public void verFilas(JTable tabla) {
        DefaultTableModel modelo;
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        try {
            Statement st = conectar.conexion();
            String sql = "select fecha, peso "
                    + "from corte "
                    + "where idempleado = '" + this.idempleado + "'";
            ResultSet rs = conectar.consulta(st, sql);

            modelo.addColumn("Item");
            modelo.addColumn("Fecha");
            modelo.addColumn("Peso (KG)");
            modelo.addColumn("Monto S/ ");

            int nrofila = 0;
            while (rs.next()) {
                nrofila++;
                Object fila[] = new Object[4];
                fila[0] = nrofila;
                fila[1] = rs.getString("fecha");
                fila[2] = rs.getDouble("peso");
                fila[3] = varios.formato_totales(rs.getDouble("peso") * 0.5);
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

            tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            varios.centrar_celda(tabla, 0);
            varios.centrar_celda(tabla, 1);
            varios.derecha_celda(tabla, 2);
            varios.derecha_celda(tabla, 3);

            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

    public void verCorteDia(DefaultTableModel modelo) {
        try {
            Statement st = conectar.conexion();
            String sql = "select c.idcorte, o.idempleado, o.datos, o.cod_sistema, c.fecha, c.peso "
                    + "from corte as c "
                    + "inner join obreros as o on o.idempleado = c.idempleado "
                    + "where c.fecha = '" + this.fecha + "' and c.idcliente = '" + this.idcliente + "' "
                    + "order by o.datos asc;";
            ResultSet rs = conectar.consulta(st, sql);

//            modelo.addColumn("Item");               //0
//            modelo.addColumn("Cod. Sist");          //1
//            modelo.addColumn("Nombre");             //2
//            modelo.addColumn("total Kg");           //3
//            modelo.addColumn("monto S/");           //4 
//            modelo.addColumn("Fecha");              //5
            int nrofila = 0;
            Object fila[] = new Object[7];
            while (rs.next()) {
                nrofila++;
                double dpeso = rs.getDouble("peso");
                fila[0] = nrofila;
                fila[1] = rs.getString("cod_sistema");
                fila[2] = rs.getString("datos");
                fila[3] = varios.formato_numero(dpeso);
                fila[4] = varios.formato_numero(dpeso * 0.5);
                fila[5] = rs.getString("fecha");
                fila[6] = rs.getInt("idcorte");
                modelo.addRow(fila);
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

    public void verHistoriaCortador(DefaultTableModel modelo, String cod) {
        try {
            Statement st = conectar.conexion();
            String sql = "select c.idcorte, o.idempleado, o.datos, o.cod_sistema, c.fecha, c.peso "
                    + "from corte as c "
                    + "inner join obreros as o on o.idempleado = c.idempleado "
                    + "where o.cod_sistema = '" + cod + "' and c.idcliente = '" + this.idcliente + "' "
                    + "order by c.fecha asc;";
            ResultSet rs = conectar.consulta(st, sql);

//            modelo.addColumn("Item");               //0
//            modelo.addColumn("Cod. Sist");          //1
//            modelo.addColumn("Nombre");             //2
//            modelo.addColumn("total Kg");           //3
//            modelo.addColumn("monto S/");           //4 
//            modelo.addColumn("Fecha");              //5
            int nrofila = 0;
            Object fila[] = new Object[7];
            while (rs.next()) {
                nrofila++;
                double dpeso = rs.getDouble("peso");
                fila[0] = nrofila;
                fila[1] = rs.getString("cod_sistema");
                fila[2] = rs.getString("datos");
                fila[3] = varios.formato_numero(dpeso);
                fila[4] = varios.formato_numero(dpeso * 0.5);
                fila[5] = rs.getString("fecha");
                fila[6] = rs.getInt("idcorte");
                modelo.addRow(fila);
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }
}
