/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mariela
 */
public class EnvaseDetalle {

    Conectar conectar = new Conectar();

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

}
