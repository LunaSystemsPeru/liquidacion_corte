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
public class EnvaseEquitativo {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private int idenvase;
    private String fecha;
    private double cantbarriles;
    private double preciobarril;
    private int idcliente;

    public EnvaseEquitativo() {
        this.idcliente = frm_principal.cliente.getIdcliente();
    }

    public int getIdenvase() {
        return idenvase;
    }

    public void setIdenvase(int idenvase) {
        this.idenvase = idenvase;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getCantbarriles() {
        return cantbarriles;
    }

    public void setCantbarriles(double cantbarriles) {
        this.cantbarriles = cantbarriles;
    }

    public double getPreciobarril() {
        return preciobarril;
    }

    public void setPreciobarril(double preciobarril) {
        this.preciobarril = preciobarril;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public void obtenerId() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(idenvase) + 1, 1) as codigo "
                    + "from envase_equitativo ";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.idenvase = rs.getInt("codigo");
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
        String query = "insert into envase_equitativo "
                + "values ('" + idenvase + "', '" + fecha + "', '" + cantbarriles + "','" + preciobarril + "','" + idcliente + "')";
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
        String query = "delete from envase_equitativo "
                + " where idenvase = '" + idenvase + "' ";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean validarEnvase() {
        boolean existe = false;
        try {
            Statement st = conectar.conexion();
            String query = "select * from envase_equitativo "
                    + "where fecha = '" + this.fecha + "' and idcliente = '" + this.idcliente + "' ";
            System.out.println(query);
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.idenvase = rs.getInt("idenvase");
                this.cantbarriles = rs.getDouble("cant_barriles");
                this.preciobarril = rs.getDouble("monto_pagar");
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
            String query = "select * from envase_equitativo "
                    + "where idenvase = '" + this.idenvase + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.fecha = rs.getString("fecha");
                this.cantbarriles = rs.getDouble("cant_barriles");
                this.preciobarril = rs.getDouble("monto_pagar");
                this.idcliente = rs.getInt("idcliente");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }

    public void mostrarDiasTrabajados(JTable tabla, String anio, String mes) {
        DefaultTableModel modelo;
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        modelo.addColumn("Fecha");
        modelo.addColumn("Barriles");
        modelo.addColumn("S/ x Barr");
        modelo.addColumn("Jornaleros");
        modelo.addColumn("Total S/");
        modelo.addColumn(""); //idtipo

        String sql = "select ee.idenvase, ee.fecha, ee.cant_barriles, ee.monto_pagar, COUNT(ed.idjornal) as nrojonal, sum(adicional - descuento) as adicional "
                + "from envase_equitativo as ee "
                + "inner join envase_detalle as ed on ee.idenvase = ed.idenvase "
                + "where ee.idcliente = '" + this.idcliente + "' and year(ee.fecha) = '" + anio + "' and month(ee.fecha) = '" + mes + "' "
                + "group by ee.fecha "
                + "order by ee.fecha asc ";
        try {
            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, sql);

            int nrofila = 0;
            while (rs.next()) {
                nrofila++;

                double barriles = rs.getDouble("cant_barriles");
                double precioxbarril = rs.getDouble("monto_pagar");
                double adicional = rs.getDouble("adicional");
                double apagar = (barriles * precioxbarril) + adicional;

                Object fila[] = new Object[6];
                fila[0] = rs.getString("fecha");
                fila[1] = barriles;
                fila[2] = varios.formato_numero(precioxbarril);
                fila[3] = rs.getInt("nrojonal");
                fila[4] = varios.formato_numero(apagar);
                fila[5] = rs.getInt("idenvase");
                modelo.addRow(fila);

            }
            conectar.cerrar(st);
            conectar.cerrar(rs);

            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(75);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(0);
            varios.centrar_celda(tabla, 0);
            varios.derecha_celda(tabla, 1);
            varios.derecha_celda(tabla, 2);
            varios.derecha_celda(tabla, 3);
            varios.derecha_celda(tabla, 4);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

    public void mostrarDetalleEnvasado(Integer anio, Integer idjornal, String fecha, JTable tabla) {
        String campo;
        String valor;
        String query = "select ed.idjornal, j.datos, ee.fecha, ee.cant_barriles, ee.monto_pagar as precio_barril, c.sede "
                + "from envase_detalle as ed "
                + "inner join envase_equitativo as ee on ee.idenvase = ed.idenvase "
                + "inner join clientes as c on c.idcliente = ee.idcliente "
                + "inner join jornaleros as j on j.idjornal = ed.idjornal "
                + "where year(ee.fecha) = '0' "
                + "group by ee.fecha, ee.idcliente, ed.idjornal "
                + "order by ee.fecha asc";

        if (idjornal > 0) {
            campo = "ed.idjornal";
            valor = idjornal + "";

            query = "select ed.idjornal, j.datos, ee.fecha, ee.cant_barriles, ee.monto_pagar as precio_barril, c.sede "
                    + "from envase_detalle as ed "
                    + "inner join envase_equitativo as ee on ee.idenvase = ed.idenvase "
                    + "inner join clientes as c on c.idcliente = ee.idcliente "
                    + "inner join jornaleros as j on j.idjornal = ed.idjornal "
                    + "where " + campo + " = '" + valor + "' and year(ee.fecha) = '" + anio + "' "
                    + "group by ee.fecha, ee.idcliente, ed.idjornal "
                    + "order by ee.fecha asc";
        }
        if (fecha.length() > 0) {
            campo = "ee.fecha";
            valor = fecha;

            query = "select ed.idjornal, j.datos, ee.fecha, ee.cant_barriles, ee.monto_pagar as precio_barril, c.sede "
                    + "from envase_detalle as ed "
                    + "inner join envase_equitativo as ee on ee.idenvase = ed.idenvase "
                    + "inner join clientes as c on c.idcliente = ee.idcliente "
                    + "inner join jornaleros as j on j.idjornal = ed.idjornal "
                    + "where " + campo + " = '" + valor + "' and year(ee.fecha) = '" + anio + "' and ee.idcliente = '" + this.idcliente + "' "
                    + "group by ee.fecha, ee.idcliente, ed.idjornal "
                    + "order by ee.fecha asc";
        }

        DefaultTableModel modelo;
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        modelo.addColumn("Item");
        modelo.addColumn("Jornalero");
        modelo.addColumn("Fecha Envasado");
        modelo.addColumn("Nro Barriles");
        modelo.addColumn("Precio x Barril");
        modelo.addColumn("Sede - Turno");
        modelo.addColumn(""); //idjornal

        try {
            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, query);

            int nrofila = 0;
            while (rs.next()) {
                nrofila++;

                Object fila[] = new Object[7];
                fila[0] = nrofila;
                fila[1] = rs.getString("datos");
                fila[2] = varios.fecha_usuario(rs.getString("fecha"));
                fila[3] = rs.getInt("cant_barriles");
                fila[4] = varios.formato_numero(rs.getDouble("precio_barril"));
                fila[5] = rs.getString("sede");
                fila[6] = rs.getInt("idjornal");
                modelo.addRow(fila);

            }
            conectar.cerrar(st);
            conectar.cerrar(rs);

            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(6).setMaxWidth(0);
            tabla.getColumnModel().getColumn(6).setMinWidth(0);
            tabla.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
            tabla.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
            varios.centrar_celda(tabla, 0);
            varios.centrar_celda(tabla, 2);
            varios.centrar_celda(tabla, 3);
            varios.derecha_celda(tabla, 4);
            varios.centrar_celda(tabla, 5);
        } catch (SQLException e) {
            System.out.print(e);
        }

    }

}
