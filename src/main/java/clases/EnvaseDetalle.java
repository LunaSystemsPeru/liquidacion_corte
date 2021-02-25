/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mariela
 */
public class EnvaseDetalle {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private int idenvase;
    private int idjornal;
    private double adicional;
    private double descuento;

    public EnvaseDetalle() {
    }

    public int getIdenvase() {
        return idenvase;
    }

    public void setIdenvase(int idenvase) {
        this.idenvase = idenvase;
    }

    public int getIdjornal() {
        return idjornal;
    }

    public void setIdjornal(int idjornal) {
        this.idjornal = idjornal;
    }

    public double getAdicional() {
        return adicional;
    }

    public void setAdicional(double adicional) {
        this.adicional = adicional;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
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

    public int obtenerTotalRegistros() {
        int resultado = 0;
        try {
            Statement st = conectar.conexion();
            String query = "select COUNT(*) as totalregistros "
                    + "from envase_detalle "
                    + "where idenvase = '" + this.idenvase + "'";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                resultado = rs.getInt("totalregistros");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return resultado;
    }

    public boolean insertar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "insert into envase_detalle "
                + "values ('" + idenvase + "', '" + idjornal + "', '" + adicional + "','" + descuento + "')";
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
        String query = "delete from envase_detalle "
                + "where idenvase = '" + idenvase + "' and idjornal = '" + idjornal + "' ";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public void mostrarEnvasadoDia(DefaultTableModel modelo) {
        int itotalreg = this.obtenerTotalRegistros();
        String sql = "SELECT j.datos, ee.fecha, ee.cant_barriles, ee.monto_pagar, ed.adicional, ed.descuento, j.idjornal, ed.idenvase "
                + "from envase_detalle  as ed "
                + "inner join envase_equitativo as ee on ee.idenvase = ed.idenvase "
                + "inner join jornaleros as j on j.idjornal = ed.idjornal "
                + "where ed.idenvase = '" + this.idenvase + "' "
                + "order by j.datos asc";
        try {
            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, sql);

            int nrofila = 0;

            while (rs.next()) {
                nrofila++;

                double dadicional = rs.getDouble("adicional");
                double ddescuento = rs.getDouble("descuento");
                double dmontobarriles = rs.getDouble("monto_pagar");
                int ibarriles = rs.getInt("cant_barriles");
                double porpagar = (ibarriles  * dmontobarriles / itotalreg) + dadicional - ddescuento;

                Object fila[] = new Object[9];
                fila[0] = nrofila;
                fila[1] = rs.getString("datos");
                fila[2] = varios.fecha_usuario(rs.getString("fecha"));
                fila[3] = rs.getInt("cant_barriles") / itotalreg;
                fila[4] = dadicional;
                fila[5] = ddescuento;
                fila[6] = varios.formato_numero(porpagar);
                fila[7] = rs.getInt("idjornal");
                fila[8] = rs.getInt("idenvase");
                modelo.addRow(fila);

            }
            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

}
