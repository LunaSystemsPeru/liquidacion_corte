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
public class Alimentacion {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private int id;
    private String fecha;
    private int cantporciones;
    private int idplatillo;
    private double movilidad;
    private double costoplatillo;

    public Alimentacion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantporciones() {
        return cantporciones;
    }

    public void setCantporciones(int cantporciones) {
        this.cantporciones = cantporciones;
    }

    public int getIdplatillo() {
        return idplatillo;
    }

    public void setIdplatillo(int idplatillo) {
        this.idplatillo = idplatillo;
    }

    public double getMovilidad() {
        return movilidad;
    }

    public void setMovilidad(double movilidad) {
        this.movilidad = movilidad;
    }

    public double getCostoplatillo() {
        return costoplatillo;
    }

    public void setCostoplatillo(double costoplatillo) {
        this.costoplatillo = costoplatillo;
    }

    public void obtenerId() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(idalimentacion) + 1, 1) as codigo "
                    + "from alimentacion ";
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
        String query = "insert into alimentacion "
                + "values ('" + id + "', '" + fecha + "', '" + cantporciones + "','" + idplatillo + "','" + movilidad + "','" + costoplatillo + "')";
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
        String query = "delete from alimentacion "
                + "where idalimentacion = '" + id + "'";
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
        String query = "update from alimentacion "
                + "where idalimentacion = '" + id + "'";
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
            String query = "select * from alimentacion "
                    + "where idalimentacion = '" + this.id + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.fecha = rs.getString("fecha");
                this.cantporciones = rs.getInt("cantidad");
                this.idplatillo = rs.getInt("idplatillo");
                this.movilidad = rs.getDouble("movilidad");
                this.costoplatillo = rs.getDouble("costo_platillo");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }

    public void verAlimentacionFecha(JTable tabla) {
        DefaultTableModel modelo;
        try {
            modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            String query = "select a.idalimentacion, ifnull(a.cantidad, 0), a.movilidad, a.costo_platillo, pd.descripcion as nombrealimento "
                    + "from alimentacion as a "
                    + "left join parametros_detalle as pd on pd.iddetalle = a.idplatillo "
                    + "where a.fecha = '" + this.fecha + "' "
                    + "order by a.idplatillo asc";
            System.out.println(query);

            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, query);

            modelo.addColumn("Alimentacion");   //0
            modelo.addColumn("Costo x Und.");   //1
            modelo.addColumn("Cantidad");       //2
            modelo.addColumn("Subtotal");       //3
            modelo.addColumn("Movilidad");      //4
            modelo.addColumn("Total");          //5
            modelo.addColumn("");               //6

            while (rs.next()) {
                Object[] fila = new Object[7];

                double cantidad = rs.getDouble("cantidad");
                double costo = rs.getDouble("costo_platillo");
                double subtotal = cantidad * costo;
                double movilidaddia = rs.getDouble("movilidad");
                double total = subtotal + movilidaddia;

                fila[0] = rs.getString("nombrealimento");
                fila[1] = varios.formato_numero(costo);
                fila[2] = varios.formato_numero(cantidad);
                fila[3] = varios.formato_numero(subtotal);
                fila[4] = varios.formato_numero(movilidaddia);
                fila[5] = varios.formato_numero(total);
                fila[6] = rs.getInt("idalimentacion");

                modelo.addRow(fila);

            }
            conectar.cerrar(st);
            conectar.cerrar(rs);

            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(0);

        } catch (SQLException e) {
            System.out.print(e);
        }
    }
}
