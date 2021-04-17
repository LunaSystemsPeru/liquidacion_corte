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

/**
 *
 * @author Mariela
 */
public class Liquidacion {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private int id;
    private int idcliente;
    private String fecha;
    private int nrocajas;
    private int nrocortadores;
    private double costokilo;
    private double kiloscortados;
    private double descuentomovilidad;
    private double jornalcorte;
    private double jornalenvase;
    private double movilidadobreros;
    private double movilidadsupervisores;
    private double movilidadenvase;
    private double gastoslimpieza;
    private double alimentacion;
    private double gastosperifoneo;

    public Liquidacion() {
        this.idcliente = frm_principal.cliente.getIdcliente();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNrocajas() {
        return nrocajas;
    }

    public void setNrocajas(int nrocajas) {
        this.nrocajas = nrocajas;
    }

    public int getNrocortadores() {
        return nrocortadores;
    }

    public void setNrocortadores(int nrocortadores) {
        this.nrocortadores = nrocortadores;
    }

    public double getCostokilo() {
        return costokilo;
    }

    public void setCostokilo(double costokilo) {
        this.costokilo = costokilo;
    }

    public double getKiloscortados() {
        return kiloscortados;
    }

    public void setKiloscortados(double kiloscortados) {
        this.kiloscortados = kiloscortados;
    }

    public double getDescuentomovilidad() {
        return descuentomovilidad;
    }

    public void setDescuentomovilidad(double descuentomovilidad) {
        this.descuentomovilidad = descuentomovilidad;
    }

    public double getJornalcorte() {
        return jornalcorte;
    }

    public void setJornalcorte(double jornalcorte) {
        this.jornalcorte = jornalcorte;
    }

    public double getJornalenvase() {
        return jornalenvase;
    }

    public void setJornalenvase(double jornalenvase) {
        this.jornalenvase = jornalenvase;
    }

    public double getMovilidadobreros() {
        return movilidadobreros;
    }

    public void setMovilidadobreros(double movilidadobreros) {
        this.movilidadobreros = movilidadobreros;
    }

    public double getMovilidadsupervisores() {
        return movilidadsupervisores;
    }

    public void setMovilidadsupervisores(double movilidadsupervisores) {
        this.movilidadsupervisores = movilidadsupervisores;
    }

    public double getMovilidadenvase() {
        return movilidadenvase;
    }

    public void setMovilidadenvase(double movilidadenvase) {
        this.movilidadenvase = movilidadenvase;
    }

    public double getGastoslimpieza() {
        return gastoslimpieza;
    }

    public void setGastoslimpieza(double gastoslimpieza) {
        this.gastoslimpieza = gastoslimpieza;
    }

    public double getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(double alimentacion) {
        this.alimentacion = alimentacion;
    }

    public double getGastosperifoneo() {
        return gastosperifoneo;
    }

    public void setGastosperifoneo(double gastosperifoneo) {
        this.gastosperifoneo = gastosperifoneo;
    }

    public void obtenerId() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(idliquidacion) + 1, 1) as codigo "
                    + "from liquidacion_diaria ";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.id = rs.getInt("codigo");
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
        String query = "insert into liquidacion_diaria "
                + "values ('" + id + "', '" + idcliente + "', '" + fecha + "','" + nrocajas + "','" + nrocortadores + "','" + costokilo + "','" + kiloscortados + "', "
                + "'" + descuentomovilidad + "','" + jornalcorte + "','" + jornalenvase + "','" + movilidadobreros + "','" + movilidadsupervisores + "','" + movilidadenvase + "', "
                + "'" + gastoslimpieza + "', '"+gastosperifoneo+"', '"+alimentacion+"')";
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
        String query = "delete from liquidacion_diaria "
                + "where idliquidacion = '" + id + "'";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean modificar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "update from liquidacion_diaria "
                + "set nrocajas = '" + this.nrocajas + "', "
                + "nrocortadores = '" + this.nrocortadores + "', "
                + "costokilo = '" + this.costokilo + "', "
                + "kilos_cortados = '" + this.kiloscortados + "', "
                + "descuento_movilidad = '" + this.descuentomovilidad + "', "
                + "jornal_corte = '" + this.jornalcorte + "', "
                + "jornal_envase = '" + this.jornalenvase + "', "
                + "movilidad_obreros = '" + this.movilidadobreros + "', "
                + "movilidad_supervisores = '" + this.movilidadsupervisores + "', "
                + "movilidad_envase = '" + this.movilidadenvase + "', "
                + "gastos_limpieza = '" + this.gastoslimpieza + "', "
                + "gastos_perifoneo = '" + this.gastosperifoneo + "', "
                + "alimenacion= '" + this.alimentacion + "' "
                + "where idliquidacion = '" + id + "'";
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
            String query = "select * from liquidacion_diaria "
                    + "where idliquidacion = '" + this.id + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.fecha = rs.getString("fecha");
                this.idcliente = rs.getInt("idcliente");
                this.nrocajas = rs.getInt("nrocajas");
                this.nrocortadores = rs.getInt("nrocortadores");
                this.costokilo = rs.getDouble("costokilo");
                this.kiloscortados = rs.getDouble("kilos_cortados");
                this.descuentomovilidad = rs.getDouble("descuento_movilidad");
                this.jornalcorte = rs.getDouble("jornal_corte");
                this.jornalenvase = rs.getDouble("jornal_envase");
                this.movilidadobreros = rs.getDouble("movilidad_obreros");
                this.movilidadenvase = rs.getDouble("movilidad_envase");
                this.movilidadsupervisores = rs.getDouble("movilidad_supervisores");
                this.gastoslimpieza = rs.getDouble("gastos_limpieza");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }
}
