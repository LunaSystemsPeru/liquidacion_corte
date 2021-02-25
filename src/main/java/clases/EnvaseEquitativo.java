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
public class EnvaseEquitativo {

    Conectar conectar = new Conectar();

    private int idenvase;
    private String fecha;
    private double cantbarriles;
    private double preciobarril;
    private int idcliente;

    public EnvaseEquitativo() {
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
                    + "where fecha = '" + this.fecha + "' and idcliente = '"+this.idcliente+"' ";
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
}
