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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Mariela
 */
public class Jornal {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private int idjornal;
    private String datos;
    private int idcliente;
    private double pago_dia;
    private double pago_hora;
    private int idcargo;
    private String nrodocumento;
    private String nrocuenta;

    public Jornal() {
    }

    public int getIdjornal() {
        return idjornal;
    }

    public void setIdjornal(int idjornal) {
        this.idjornal = idjornal;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public double getPago_dia() {
        return pago_dia;
    }

    public void setPago_dia(double pago_dia) {
        this.pago_dia = pago_dia;
    }

    public double getPago_hora() {
        return pago_hora;
    }

    public void setPago_hora(double pago_hora) {
        this.pago_hora = pago_hora;
    }

    public int getIdcargo() {
        return idcargo;
    }

    public void setIdcargo(int idcargo) {
        this.idcargo = idcargo;
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
            String query = "select ifnull(max(idjornal) + 1, 1) as codigo "
                    + "from jornaleros ";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.idjornal = rs.getInt("codigo");
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
        String query = "insert into jornaleros "
                + "values ('" + idjornal + "', '" + datos + "', '" + idcliente + "','" + pago_dia + "','" + pago_hora + "','" + idcargo + "','" + nrodocumento + "','" + nrocuenta + "')";
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
        String query = "update jornaleros "
                + "set datos = '" + this.datos + "', "
                + "nrodocumento = '" + this.nrodocumento + "', "
                + "nrocuenta = '" + this.nrocuenta + "', "
                + "idcargo= '" + this.idcargo + "', "
                + "hora_pago = '" + this.pago_hora + "' "
                + "where idjornal = '" + idjornal + "'";
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
        String query = "delete from jornaleros "
                + "where idjornal = '" + idjornal + "'";
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
            String query = "select * from jornaleros "
                    + "where idjornal = '" + this.idjornal + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.datos = rs.getString("datos");
                this.idcliente = rs.getInt("idcliente");
                this.pago_dia = rs.getDouble("dia_pago");
                this.pago_hora = rs.getDouble("hora_pago");
                this.idcargo = rs.getInt("idcargo");
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

    public void verSinCuentas(JTable tabla, String fechainicio, String fechafinal) {
        DefaultTableModel modelo;
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        try {
            Statement st = conectar.conexion();
            String sql = "select distinct(j.idjornal), j.datos, j.nrodocumento, j.nrocuenta, c.sede "
                    + "from jornal_dia as jd "
                    + "inner join jornaleros as j on j.idjornal = jd.idjornal "
                    + "inner join clientes as c on jd.idcliente = c.idcliente "
                    + "where j.nrocuenta = '' and jd.fecha between '" + fechainicio + "' and '" + fechafinal + "'  "
                    + "order by c.sede asc, j.datos asc";

            //and jd.idcliente = '" + this.idcliente + "'
            ResultSet rs = conectar.consulta(st, sql);

            modelo.addColumn("ID");
            modelo.addColumn("Datos");
            modelo.addColumn("Nro Documento");
            modelo.addColumn("# Cuenta");
            modelo.addColumn("Sede");

            while (rs.next()) {
                Object fila[] = new Object[5];
                fila[0] = rs.getString("idjornal");
                fila[1] = rs.getString("datos");
                fila[2] = rs.getString("nrodocumento");
                fila[3] = rs.getString("nrocuenta");
                fila[4] = rs.getString("sede");
                modelo.addRow(fila);

            }

            tabla.setModel(modelo);
            TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
            tabla.setRowSorter(elQueOrdena);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(350);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(200);
            varios.centrar_celda(tabla, 0);
            varios.centrar_celda(tabla, 2);
            varios.centrar_celda(tabla, 3);
            varios.derecha_celda(tabla, 4);

            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

    public void verTodosJornaleros(JTable tabla, String texto) {
        DefaultTableModel modelo;
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        try {
            Statement st = conectar.conexion();
            String sql = "select j.idjornal, j.datos, j.nrodocumento, j.nrocuenta, c.sede\n"
                    + "from jornaleros as j "
                    + "inner join clientes as c on c.idcliente = j.idcliente "
                    + "where j.datos like '%" + texto + "%' "
                    + "order by j.datos asc";
            ResultSet rs = conectar.consulta(st, sql);

            modelo.addColumn("ID");
            modelo.addColumn("Datos");
            modelo.addColumn("Nro Documento");
            modelo.addColumn("# Cuenta");
            modelo.addColumn("Sede");

            while (rs.next()) {
                Object fila[] = new Object[5];
                fila[0] = rs.getString("idjornal");
                fila[1] = rs.getString("datos");
                fila[2] = rs.getString("nrodocumento");
                fila[3] = rs.getString("nrocuenta");
                fila[4] = rs.getString("sede");
                modelo.addRow(fila);

            }

            tabla.setModel(modelo);

            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(180);
            varios.centrar_celda(tabla, 0);
            varios.centrar_celda(tabla, 2);
            varios.centrar_celda(tabla, 3);
            varios.derecha_celda(tabla, 4);

            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
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

            modelo.addColumn("ID");
            modelo.addColumn("Datos");
            modelo.addColumn("Nro Documento");
            modelo.addColumn("Cargo.");
            modelo.addColumn("Pago Dia");
            modelo.addColumn("Pago Hora");
            modelo.addColumn("# Cuenta");
            modelo.addColumn("Sede");

            while (rs.next()) {
                Object fila[] = new Object[8];
                fila[0] = rs.getString("idjornal");
                fila[1] = rs.getString("datos");
                fila[2] = rs.getString("nrodocumento");
                fila[3] = rs.getString("ncargo");
                fila[4] = varios.formato_numero(rs.getDouble("dia_pago"));
                fila[5] = varios.formato_numero(rs.getDouble("hora_pago"));
                fila[6] = rs.getString("nrocuenta");
                fila[7] = rs.getString("sede");
                modelo.addRow(fila);

            }

            tabla.setModel(modelo);

            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(350);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(180);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(150);
            varios.centrar_celda(tabla, 0);
            varios.centrar_celda(tabla, 2);
            varios.centrar_celda(tabla, 3);
            varios.derecha_celda(tabla, 4);
            varios.derecha_celda(tabla, 5);
            varios.centrar_celda(tabla, 6);

            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }
}
