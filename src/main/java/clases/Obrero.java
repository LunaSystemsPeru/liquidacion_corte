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
    private String nrodocumento;
    private String apepat;
    private String apemat;
    private String nombres;
    private String fecha_nacimiento;
    private String fecha_modificacion;
    private String nrocuenta;
    private String nrodnititular;

    public Obrero() {
    }

    public int getIdobrero() {
        return idobrero;
    }

    public void setIdobrero(int idobrero) {
        this.idobrero = idobrero;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getApepat() {
        return apepat;
    }

    public void setApepat(String apepat) {
        this.apepat = apepat;
    }

    public String getApemat() {
        return apemat;
    }

    public void setApemat(String apemat) {
        this.apemat = apemat;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(String fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public String getNrodnititular() {
        return nrodnititular;
    }

    public void setNrodnititular(String nrodnititular) {
        this.nrodnititular = nrodnititular;
    }

    public void obtenerId() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(idempleado) + 1, 1) as codigo "
                    + "from obreros ";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.idobrero = rs.getInt("codigo");
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
                + "values ('" + this.idobrero + "', '" + this.nrodocumento + "', '" + this.apepat + "', '" + this.apemat + "', '" + this.nombres + "', '" + this.fecha_nacimiento + "', '" + this.fecha_modificacion + "', '" + this.nrocuenta + "', '" + this.nrodnititular + "')";
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
        String query = "update obreros set "
                + "apepat = '" + this.apepat + "', "
                + "apemat = '" + this.apemat + "', "
                + "nombres = '" + this.nombres + "', "
                + "fec_nacimiento = '" + this.fecha_nacimiento + "', "
                + "fec_modificacion = '" + this.fecha_modificacion + "', "
                + "nrocuenta = '" + this.nrocuenta + "', "
                + "nrodnititular = '" + this.nrodnititular + "' "
                + "where idempleado = '" + this.idobrero + "'";
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
                this.nrodocumento = rs.getString("nrodocumento");
                this.apepat = rs.getString("apepat");
                this.apemat = rs.getString("apemat");
                this.nombres = rs.getString("nombres");
                this.fecha_nacimiento = rs.getString("fec_nacimiento");
                this.fecha_modificacion = rs.getString("fec_modificacion");
                this.nrocuenta = rs.getString("nrocuenta");
                this.nrodnititular = rs.getString("nrodnititular");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }

    public boolean verificarDocumento() {
        boolean existe = false;
        try {
            Statement st = conectar.conexion();
            String query = "select count(*) as encontrado, idempleado "
                    + "from obreros "
                    + "where nrodocumento = '" + this.nrodocumento + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                if (rs.getInt("encontrado") > 0) {
                    existe = true;
                    this.idobrero = rs.getInt("idempleado");
                } else {
                    existe = false;
                }
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
            modelo.addColumn("Nro DOC");
            modelo.addColumn("Datos");
            modelo.addColumn("Fecha Nac.");
            modelo.addColumn("# Cuenta");
            modelo.addColumn("ultima Modifcc.");

            while (rs.next()) {
                Object fila[] = new Object[6];
                fila[0] = rs.getString("idempleado");
                fila[1] = rs.getString("nrodocumento");
                fila[2] = rs.getString("apepat") + " " + rs.getString("apemat") + " " + rs.getString("nombres");
                fila[3] = rs.getString("fec_nacimiento");
                fila[4] = rs.getString("nrocuenta");
                fila[5] = rs.getString("fec_modificacion");
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(350);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
            varios.centrar_celda(tabla, 0);
            varios.centrar_celda(tabla, 1);
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
