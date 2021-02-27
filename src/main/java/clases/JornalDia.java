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
public class JornalDia {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private int iddiario;
    private String fecha;
    private String hora_inicio;
    private String hora_salida;
    private int idjornal;
    private double pago_hora;
    private double pago_dia;
    private int idcargo;
    private int idcliente;
    private int idtipo;
    private double reintegro;
    private double descuento;

    public JornalDia() {
    }

    public int getIddiario() {
        return iddiario;
    }

    public void setIddiario(int iddiario) {
        this.iddiario = iddiario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getIdjornal() {
        return idjornal;
    }

    public void setIdjornal(int idjornal) {
        this.idjornal = idjornal;
    }

    public double getPago_hora() {
        return pago_hora;
    }

    public void setPago_hora(double pago_hora) {
        this.pago_hora = pago_hora;
    }

    public double getPago_dia() {
        return pago_dia;
    }

    public void setPago_dia(double pago_dia) {
        this.pago_dia = pago_dia;
    }

    public int getIdcargo() {
        return idcargo;
    }

    public void setIdcargo(int idcargo) {
        this.idcargo = idcargo;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }

    public double getReintegro() {
        return reintegro;
    }

    public void setReintegro(double reintegro) {
        this.reintegro = reintegro;
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
            String query = "select ifnull(max(iddia) + 1, 1) as codigo "
                    + "from jornal_dia";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.iddiario = rs.getInt("codigo");
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
        String query = "insert into jornal_dia "
                + "values ('" + iddiario + "', '" + fecha + "', '" + hora_inicio + "','" + hora_salida + "','" + idjornal + "', '" + pago_hora + "', "
                + "'" + pago_dia + "','" + idcargo + "','" + idcliente + "','" + idtipo + "','" + reintegro + "','" + descuento + "')";
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
        String query = "delete from jornal_dia "
                + "where iddia = '" + iddiario + "'";
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
        String query = "update jornal_dia set "
                + "hora_inicio = '" + this.hora_inicio + "', "
                + "hora_salida = '" + this.hora_salida + "',"
                + "hora_pago = '" + this.pago_hora + "',"
                + "dia_pago = '" + this.pago_dia + "', "
                + "idcargo = '" + this.idcargo + "', "
                + "reintegro = '" + this.reintegro + "', "
                + "descuento = '" + this.descuento + "' "
                + "where iddia = '" + iddiario + "'";
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
            String query = "select * from jornal_dia "
                    + "where iddiario = '" + this.iddiario + "' ";
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                existe = true;
                this.fecha = rs.getString("fecha");
                this.hora_inicio = rs.getString("hora_inicio");;
                this.hora_salida = rs.getString("hora_salida");
                this.idjornal = rs.getInt("idjornal");
                this.pago_hora = rs.getDouble("hora_pago");
                this.pago_dia = rs.getDouble("dia_pago");
                this.idcargo = rs.getInt("idcargo");
                this.idcliente = rs.getInt("idcliente");
                this.idtipo = rs.getInt("idtipo");
                this.reintegro = rs.getDouble("reintegro");
                this.descuento = rs.getDouble("descuento");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }

    public void mostrarDiasJornal(DefaultTableModel modelo) {
        String sql = "select j.datos, ca.descripcion as cargo, jd.fecha, jd.hora_pago, jd.dia_pago, jd.hora_inicio, jd.hora_salida, tj.descripcion as tipojornal, jd.idcargo, jd.idjornal, jd.idtipo, jd.iddia, jd.reintegro, jd.descuento "
                + "from jornal_dia as jd "
                + "inner join jornaleros as j on j.idjornal = jd.idjornal "
                + "inner join parametros_detalle as ca on ca.iddetalle = jd.idcargo "
                + "inner join parametros_detalle as tj on tj.iddetalle = jd.idtipo "
                + "where jd.idcliente = '" + this.idcliente + "' and jd.idjornal = '" + this.idjornal + "' "
                + "order by jd.fecha asc ";
        try {
            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, sql);

            int nrofila = 0;
            while (rs.next()) {
                nrofila++;

                //restar horas
                String horainicio = rs.getString("hora_inicio");
                String horasalida = rs.getString("hora_salida");
                double horas = varios.restarHoras(horainicio, horasalida);

                horainicio = varios.Hora24a12(horainicio);
                horasalida = varios.Hora24a12(horasalida);

                //total dia
                double pagototal = 0;
                double diapago = rs.getDouble("dia_pago");
                double horapago = rs.getDouble("hora_pago");
                double dreintegro = rs.getDouble("reintegro");
                double ddescuento = rs.getDouble("descuento");
                if (diapago > 0) {
                    pagototal = diapago;
                }
                if (horapago > 0) {
                    pagototal = horas * horapago;
                }

                pagototal = pagototal + reintegro - descuento;

                Object fila[] = new Object[17];
                fila[0] = nrofila;
                fila[1] = rs.getString("datos");
                fila[2] = varios.fecha_usuario(rs.getString("fecha"));
                fila[3] = rs.getString("cargo");
                if (diapago > 0) {
                    fila[4] = varios.formato_numero(diapago);
                } else {
                    fila[4] = "";
                }
                if (horapago > 0) {
                    fila[5] = varios.formato_numero(horapago);
                } else {
                    fila[5] = "";
                }
                fila[6] = horainicio;
                fila[7] = horasalida;
                fila[8] = varios.formato_numero(horas);
                fila[9] = varios.formato_numero(dreintegro);
                fila[10] = varios.formato_numero(ddescuento);
                fila[11] = varios.formato_numero(pagototal);
                fila[12] = rs.getString("tipojornal");
                fila[13] = rs.getInt("idcargo");
                fila[14] = rs.getInt("idjornal");
                fila[15] = rs.getInt("idtipo");
                fila[16] = rs.getInt("iddia");
                modelo.addRow(fila);

            }
            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

    public void mostrarJornalDia(DefaultTableModel modelo) {
        String sql = "select j.datos, ca.descripcion as cargo, jd.hora_pago, jd.dia_pago, jd.hora_inicio, jd.hora_salida, tj.descripcion as tipojornal, jd.idcargo, jd.idjornal, jd.idtipo, jd.iddia, jd.reintegro, jd.descuento "
                + "from jornal_dia as jd "
                + "inner join jornaleros as j on j.idjornal = jd.idjornal "
                + "inner join parametros_detalle as ca on ca.iddetalle = jd.idcargo "
                + "inner join parametros_detalle as tj on tj.iddetalle = jd.idtipo "
                + "where jd.idcliente = '" + this.idcliente + "' and jd.fecha = '" + this.fecha + "' and jd.idtipo = '" + this.idtipo + "' "
                + "order by j.datos asc ";
        try {
            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, sql);

            int nrofila = 0;
            while (rs.next()) {
                nrofila++;

                //restar horas
                String horainicio = rs.getString("hora_inicio");
                String horasalida = rs.getString("hora_salida");
                double horas = varios.restarHoras(horainicio, horasalida);

                horainicio = varios.Hora24a12(horainicio);
                horasalida = varios.Hora24a12(horasalida);

                //total dia
                double pagototal = 0;
                double diapago = rs.getDouble("dia_pago");
                double horapago = rs.getDouble("hora_pago");
                double dreintegro = rs.getDouble("reintegro");
                double ddescuento = rs.getDouble("descuento");
                if (diapago > 0) {
                    pagototal = diapago;
                }
                if (horapago > 0) {
                    pagototal = horas * horapago;
                }

                pagototal = pagototal + dreintegro - ddescuento;

                Object fila[] = new Object[17];
                fila[0] = nrofila;
                fila[1] = rs.getString("datos");
                fila[2] = varios.fecha_usuario(fecha);
                fila[3] = rs.getString("cargo");
                if (diapago > 0) {
                    fila[4] = varios.formato_numero(diapago);
                } else {
                    fila[4] = "";
                }
                if (horapago > 0) {
                    fila[5] = varios.formato_numero(horapago);
                } else {
                    fila[5] = "";
                }
                fila[6] = horainicio;
                fila[7] = horasalida;
                fila[8] = varios.formato_numero(horas);
                fila[9] = varios.formato_numero(dreintegro);
                fila[10] = varios.formato_numero(ddescuento);
                fila[11] = varios.formato_numero(pagototal);
                fila[12] = rs.getString("tipojornal");
                fila[13] = rs.getInt("idcargo");
                fila[14] = rs.getInt("idjornal");
                fila[15] = rs.getInt("idtipo");
                fila[16] = rs.getInt("iddia");
                modelo.addRow(fila);

            }
            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.print(e);
        }
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
        modelo.addColumn("Tipo Jornal");
        modelo.addColumn("Nro Jornaleros");
        modelo.addColumn("Total S/");
        modelo.addColumn(""); //idtipo

        String sql = "select jd.fecha, pd.descripcion, count(jd.idjornal) as nrojornaleros, jd.idtipo "
                + "from jornal_dia as jd "
                + "inner join parametros_detalle as pd on pd.iddetalle = jd.idtipo "
                + "where month(jd.fecha) = '" + mes + "' and year(jd.fecha) = '" + anio + "' and jd.idcliente = '" + this.idcliente + "' "
                + "group by jd.fecha, jd.idtipo ";
        try {
            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, sql);

            int nrofila = 0;
            while (rs.next()) {
                nrofila++;

                Object fila[] = new Object[5];
                fila[0] = rs.getString("fecha");
                fila[1] = rs.getString("descripcion");
                fila[2] = rs.getString("nrojornaleros");
                fila[3] = "";
                fila[4] = rs.getInt("idtipo");
                modelo.addRow(fila);

            }
            conectar.cerrar(st);
            conectar.cerrar(rs);

            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(0);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

}
