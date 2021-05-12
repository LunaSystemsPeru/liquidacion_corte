/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import com.lunasystems.liquidacion.frm_principal;

/**
 *
 * @author Mariela
 */
public class JornalDevolucion {

    private int id;
    private int idjornal;
    private int idcliente;
    private String fecha;
    private double deuda;
    private double pagado;

    public JornalDevolucion() {
        this.idcliente = frm_principal.cliente.getIdcliente();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdjornal() {
        return idjornal;
    }

    public void setIdjornal(int idjornal) {
        this.idjornal = idjornal;
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

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    public double getPagado() {
        return pagado;
    }

    public void setPagado(double pagado) {
        this.pagado = pagado;
    }

}
