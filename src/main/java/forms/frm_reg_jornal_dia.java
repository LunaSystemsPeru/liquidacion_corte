/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases.Cliente;
import clases.Conectar;
import clases.Jornal;
import clases.JornalDia;
import clases.ParametroDetalle;
import clases.Varios;
import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import com.lunasystems.liquidacion.frm_principal;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import objects.m_tipo;
import objects.o_autocomplete;
import objects.o_combobox;

/**
 *
 * @author Mariela
 */
public class frm_reg_jornal_dia extends javax.swing.JInternalFrame {

    //modelos
    m_tipo mtipo = new m_tipo();

    //CLASES PARA USAR
    Cliente cliente = new Cliente();
    Jornal jornal = new Jornal();
    JornalDia jornaldia = new JornalDia();
    ParametroDetalle detalle = new ParametroDetalle();
    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    //variables y objetos 
    DefaultTableModel modelo;
    TextAutoCompleter tac_jornaleros = null;

    SpinnerDateModel dateModel = new SpinnerDateModel();
    int filaseleccionada;

    /**
     * Creates new form frm_reg_jornal_dia
     */
    public frm_reg_jornal_dia() {
        initComponents();
        cliente.setIdcliente(frm_principal.cliente.getIdcliente());
        cliente.obtenerDatos();

        jornaldia.setIdcliente(cliente.getIdcliente());

        txt_nombre_cliente.setText(cliente.getNombre() + " | " + cliente.getSede());

        mtipo.llenarTipos(cbx_tipo_jornal);
        mtipo.llenarCargos(cbx_cargo);
        cargarTabla();

        setearFecha(varios.getFechaActual());
        /*
        try {
            //mostrarhorainicio();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }*/

    }

    private void setearFecha(String date) {
        try {
            java.util.Date date2;
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            dt_fecha.setDate(date2);
        } catch (ParseException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    private String convertFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(dt_fecha.getDate());
        return fecha;
    }

    public final void cargarTabla() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        modelo.addColumn("Item");               //0
        modelo.addColumn("Nombre");             //1
        modelo.addColumn("Fecha");              //2
        modelo.addColumn("Cargo");              //3
        modelo.addColumn("S/ x Dia");           //4
        modelo.addColumn("S/ x Hora");          //5 
        modelo.addColumn("Hora Entrada");       //6
        modelo.addColumn("Hora Salida");        //7
        modelo.addColumn("Total Horas");        //8
        modelo.addColumn("Reintegro");          //9
        modelo.addColumn("Descuento");          //10
        modelo.addColumn("a Pagar");            //11
        modelo.addColumn("Tipo Jornada");       //12
        modelo.addColumn("");                   //13
        modelo.addColumn("");                   //14
        modelo.addColumn("");                   //15
        modelo.addColumn("");                   //16
        jTable1.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
        jTable1.setRowSorter(elQueOrdena);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(11).setPreferredWidth(70);
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(13).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(13).setMinWidth(0);
        jTable1.getColumnModel().getColumn(14).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(14).setMinWidth(0);
        jTable1.getColumnModel().getColumn(15).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(15).setMinWidth(0);
        jTable1.getColumnModel().getColumn(16).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(16).setMinWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(14).setMaxWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(14).setMinWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(15).setMaxWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(15).setMinWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(16).setMaxWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(16).setMinWidth(0);
        varios.centrar_celda(jTable1, 0);
        varios.centrar_celda(jTable1, 2);
        varios.centrar_celda(jTable1, 6);
        varios.centrar_celda(jTable1, 7);
        varios.centrar_celda(jTable1, 8);
        varios.derecha_celda(jTable1, 9);
        varios.derecha_celda(jTable1, 10);
        varios.derecha_celda(jTable1, 11);

    }

    private void cargarJornaleros() {
        try {
            if (tac_jornaleros != null) {
                tac_jornaleros.removeAllItems();
            }
            tac_jornaleros = new TextAutoCompleter(txt_datos_jornalero, new AutoCompleterCallback() {
                @Override
                public void callback(Object selectedItem) {
                    Object itemSelected = selectedItem;
                    jornal.setIdjornal(0);
                    if (itemSelected instanceof o_autocomplete) {
                        int pcodigo = ((o_autocomplete) itemSelected).getId();
                        String ptexto = ((o_autocomplete) itemSelected).getTexto();
                        System.out.println("jornal seleccionado " + ptexto);
                        jornal.setIdjornal(pcodigo);
                    } else {
                        System.out.println("El item es de un tipo desconocido");
                    }
                }
            });

            tac_jornaleros.setMode(0);
            tac_jornaleros.setCaseSensitive(false);
            Statement st = conectar.conexion();
            String sql = "select * from jornaleros ";
            //+ "where idcliente = '" + cliente.getIdcliente() + "'";
            ResultSet rs = conectar.consulta(st, sql);
            while (rs.next()) {
                int iditem = rs.getInt("idjornal");
                String descripcion = rs.getString("datos");
                tac_jornaleros.addItem(new o_autocomplete(iditem, descripcion));
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e.getLocalizedMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

    private boolean valida_tabla(int producto) {
        //estado de ingreso
        boolean ingresar = false;
        int cuenta_iguales = 0;

        //verificar fila no se repite
        int contar_filas = jTable1.getRowCount();
        if (contar_filas == 0) {
            ingresar = true;
        } else {
            for (int j = 0; j < contar_filas; j++) {
                int id_producto_fila = Integer.parseInt(jTable1.getValueAt(j, 0).toString());
                if (producto == id_producto_fila) {
                    cuenta_iguales++;
                    JOptionPane.showMessageDialog(null, "El Item a Ingresar ya existe en la lista");
                    //limpiar();
                }
            }

            if (cuenta_iguales == 0) {
                ingresar = true;
            }
        }

        return ingresar;
    }

    private void limpiar() {
        txt_datos_jornalero.setText("");
        txt_monto.setText("");
//        txt_horaingreso.setText("");
//        txt_horasalida.setText("");
        txt_datos_jornalero.requestFocus();
    }

    private void activar() {
        jButton1.setEnabled(true);
        txt_datos_jornalero.setEnabled(true);

        if (cbx_sinhoras.isSelected()) {
            cbx_cargo.setEnabled(false);
            cbx_tipo_pago.setEnabled(false);
            txt_monto.setEnabled(false);
            jSpinner1.setEnabled(false);
            jSpinner2.setEnabled(false);
        } else {
            cbx_cargo.setEnabled(true);
            cbx_tipo_pago.setEnabled(true);
            txt_monto.setEnabled(true);
            jSpinner1.setEnabled(true);
            jSpinner2.setEnabled(true);
        }

        cbx_sinhoras.setEnabled(false);

        jButton7.setEnabled(true);
        dt_fecha.setEnabled(false);
        cbx_tipo_jornal.setEnabled(false);
        jButton6.setEnabled(false);
    }

    private void desactivar() {
        jButton1.setEnabled(false);
        txt_datos_jornalero.setEnabled(false);

        cbx_cargo.setEnabled(false);
        cbx_tipo_pago.setEnabled(false);
        txt_monto.setEnabled(false);
        jSpinner1.setEnabled(false);
        jSpinner2.setEnabled(false);
        jButton7.setEnabled(false);
        dt_fecha.setEnabled(true);
        cbx_tipo_jornal.setEnabled(true);
        jButton6.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jSpinner3 = new javax.swing.JSpinner();
        jSpinner4 = new javax.swing.JSpinner();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jToolBar2 = new javax.swing.JToolBar();
        jButton10 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        cbx_cargo_mod = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txt_nombre_cliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dt_fecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txt_datos_jornalero = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbx_cargo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbx_tipo_pago = new javax.swing.JComboBox<>();
        txt_monto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        cbx_tipo_jornal = new javax.swing.JComboBox<>();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        cbx_sinhoras = new javax.swing.JCheckBox();

        jDialog1.setTitle("Modificar Jornal del Dia");

        jLabel12.setText("Datos:");

        jLabel13.setText("Cargo:");

        jLabel14.setText("Tipo Pago:");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Dia");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Hora");

        jLabel15.setText("Monto Pago:");

        jTextField4.setEditable(false);

        jLabel16.setText("Hora Ingreso:");

        jLabel17.setText("Hora Salida:");

        jLabel18.setText("Reintegro:");

        jLabel19.setText("Descuento:");

        jSpinner3.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1604739600000L), null, null, java.util.Calendar.MINUTE));
        jSpinner3.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner3, "HH:mm a"));
        jSpinner3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSpinner3KeyPressed(evt);
            }
        });

        jSpinner4.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1604739600000L), null, null, java.util.Calendar.MINUTE));
        jSpinner4.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner4, "HH:mm a"));
        jSpinner4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSpinner4KeyPressed(evt);
            }
        });

        jToolBar2.setFloatable(false);

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_calculator_3131957.png"))); // NOI18N
        jButton10.setText("Calcular y Grabar");
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton10);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Diskette_728991.png"))); // NOI18N
        jButton8.setText("Grabar");
        jButton8.setEnabled(false);
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton8);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Cancel_728918.png"))); // NOI18N
        jButton9.setText("Cerrar");
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton9);

        jLabel20.setText("Total Horas:");

        jTextField8.setEditable(false);

        jLabel21.setText("a Pagar");

        jTextField9.setEditable(false);

        cbx_cargo_mod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SSHH LIMPIEZA", "EMPANIZADO", "DESCARGA", "LIMPIEZA PISO" }));
        cbx_cargo_mod.setEnabled(false);
        cbx_cargo_mod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_cargo_modKeyPressed(evt);
            }
        });

        jLabel22.setText("Fecha:");

        jTextField5.setEditable(false);

        jLabel23.setText("Tipo Jornal");

        jTextField10.setEditable(false);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jDialog1Layout.createSequentialGroup()
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jDialog1Layout.createSequentialGroup()
                                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(165, 165, 165)
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField7)
                                    .addComponent(jTextField6)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jTextField4)
                            .addComponent(cbx_cargo_mod, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField10)))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_cargo_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setTitle("Asistencia Diaria Jornal");

        jLabel1.setText("Cliente.");

        txt_nombre_cliente.setEditable(false);

        jLabel2.setText("Fecha:");

        jLabel3.setText("Jornalero:");

        txt_datos_jornalero.setEnabled(false);
        txt_datos_jornalero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_datos_jornaleroKeyPressed(evt);
            }
        });

        jLabel4.setText("Cargo:");

        cbx_cargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SSHH LIMPIEZA", "EMPANIZADO", "DESCARGA", "LIMPIEZA PISO" }));
        cbx_cargo.setEnabled(false);
        cbx_cargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_cargoKeyPressed(evt);
            }
        });

        jLabel5.setText("Pago:");

        cbx_tipo_pago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DIA", "HORA" }));
        cbx_tipo_pago.setSelectedIndex(1);
        cbx_tipo_pago.setEnabled(false);

        txt_monto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_monto.setEnabled(false);
        txt_monto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_montoKeyPressed(evt);
            }
        });

        jLabel6.setText("Monto");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Add_728898.png"))); // NOI18N
        jButton1.setText("Jornal");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Hora Inicio:");

        jLabel8.setText("Hora Salida:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel9.setText("Tipo:");

        cbx_tipo_jornal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "JORNAL CORTE", "JORNAL ENVASE" }));

        jToolBar1.setFloatable(false);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Add_728898.png"))); // NOI18N
        jButton2.setText("Nuevo");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator2);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_EditDocument_728933.png"))); // NOI18N
        jButton4.setText("Modificar");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Bin_728996.png"))); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator1);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Cancel_728918.png"))); // NOI18N
        jButton5.setText("Salir");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Folder_728983.png"))); // NOI18N
        jButton6.setText("Iniciar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Diskette_728991.png"))); // NOI18N
        jButton7.setText("Agregar");
        jButton7.setEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jSpinner1.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1604739600000L), null, null, java.util.Calendar.MINUTE));
        jSpinner1.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner1, "HH:mm a"));
        jSpinner1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSpinner1KeyPressed(evt);
            }
        });

        jSpinner2.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1604790000000L), null, null, java.util.Calendar.MINUTE));
        jSpinner2.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner2, "HH:mm a"));
        jSpinner2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSpinner2KeyPressed(evt);
            }
        });

        jLabel10.setText("Adicional:");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jLabel11.setText("Descuento:");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });

        cbx_sinhoras.setText("Sin Horas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbx_cargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbx_tipo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbx_tipo_jornal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbx_sinhoras)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addComponent(txt_datos_jornalero)
                            .addComponent(txt_nombre_cliente)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombre_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbx_tipo_jornal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbx_sinhoras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_datos_jornalero, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_tipo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cargarTabla();
        cargarJornaleros();
        desactivar();
        dt_fecha.requestFocus();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Frame f = JOptionPane.getRootFrame();
        frm_reg_jornalero.jornal.setIdjornal(0);
        frm_reg_jornalero dialog = new frm_reg_jornalero(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        //buscar jornaleros con esta fecha y tipo de servico 
        cargarTabla();

        String fechafin = varios.fecha_myql(convertFecha());
        jornaldia.setFecha(fechafin);
        o_combobox otipo = (o_combobox) cbx_tipo_jornal.getSelectedItem();
        jornaldia.setIdtipo(otipo.getId());
        jornaldia.mostrarJornalDia(modelo);

        jTable1.setModel(modelo);

        //cargar todos los botones
        activar();

        String fechadb = varios.fecha_myql(convertFecha());
        jornaldia.setFecha(fechadb);

        //cargar empleados
        cargarJornaleros();
        txt_datos_jornalero.requestFocus();

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        /*
        
        modelo.addColumn("Item");           //0
        modelo.addColumn("Nombre");         //1
        modelo.addColumn("Cargo");          //2
        modelo.addColumn("S/ x Dia");       //3
        modelo.addColumn("S/ x Hora");      //4
        modelo.addColumn("Hora Entrada");   //5
        modelo.addColumn("Hora Salida");    //6
        modelo.addColumn("Total Horas");    //7
        modelo.addColumn("Reintegro");      //8
        modelo.addColumn("Descuento");      //9
        modelo.addColumn("a Pagar");        //10
        modelo.addColumn("Fecha");          //11
        modelo.addColumn("Tipo Jornada");   //12
        modelo.addColumn("idcargo");        //13
        modelo.addColumn("idempleado");     //14
        modelo.addColumn("idtipojornal");   //15
        modelo.addColumn("idjornaldiario"); //16

         */

        //obtener id cargo y nombre
        o_combobox ocargo = (o_combobox) cbx_cargo.getSelectedItem();

        //obtener el tipojornal
        o_combobox otipojornal = (o_combobox) cbx_tipo_jornal.getSelectedItem();

        //definir fecha seleccionada
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(dt_fecha.getDate());

        //llenar datos de pago 
        int tipopago = cbx_tipo_pago.getSelectedIndex();
        double pago_dia = 0;
        double pago_hora = 0;
        double pago = Double.parseDouble(txt_monto.getText());

        String sreintegro = jTextField1.getText();
        double reintegro = 0;
        if (varios.esDecimal(sreintegro)) {
            reintegro = Double.parseDouble(jTextField1.getText());
        }
        double descuento = 0;
        if (varios.esDecimal(sreintegro)) {
            descuento = Double.parseDouble(jTextField2.getText());
        }

        if (tipopago == 0) {
            pago_dia = pago;
            pago_hora = 0;
        }
        if (tipopago == 1) {
            pago_dia = 0;
            pago_hora = pago;
        }

        //restar horas
        //String horainicio = txt_horaingreso.getText();
        String horainicio = new SimpleDateFormat("HH:mm").format(jSpinner1.getValue());
        //sString horasalida = txt_horasalida.getText();
        String horasalida = new SimpleDateFormat("HH:mm").format(jSpinner2.getValue());

        double horas = varios.restarHoras(horainicio, horasalida);

        //si esta seleccionado sin horas cambiar cargo a limpieza linea
        if (cbx_sinhoras.isSelected()) {
            pago_dia = 0;
            pago_hora = 0;

        }

        //total dia
        double pagototal = 0;
        if (tipopago == 0) {
            if (horas > 0) {
                pagototal = pago_dia;
            }
        }
        if (tipopago == 1) {
            if (horas > 0) {
                pagototal = horas * pago_hora;
            }
        }

        pagototal = pagototal + reintegro - descuento;

        //llenar jornladia
        jornaldia.obtenerId();
        jornaldia.setHora_inicio(horainicio);
        jornaldia.setHora_salida(horasalida);
        jornaldia.setIdjornal(jornal.getIdjornal());
        jornaldia.setPago_dia(pago_dia);
        jornaldia.setPago_hora(pago_hora);
        jornaldia.setIdcargo(ocargo.getId());
        jornaldia.setIdtipo(otipojornal.getId());
        jornaldia.setIdcliente(cliente.getIdcliente());
        jornaldia.setReintegro(reintegro);
        jornaldia.setDescuento(descuento);
        jornaldia.insertar();

        horainicio = varios.Hora24a12(horainicio);
        horasalida = varios.Hora24a12(horasalida);

        //llenar filas
        int nrofila = jTable1.getRowCount();
        Object fila[] = new Object[17];
        fila[0] = nrofila + 1;
        fila[1] = jornal.getDatos();
        fila[2] = ocargo.getTexto();
        fila[3] = varios.formato_numero(pago_dia);
        fila[4] = varios.formato_numero(pago_hora);
        fila[5] = horainicio;
        fila[6] = horasalida;
        fila[7] = varios.formato_numero(horas);
        fila[8] = varios.formato_numero(reintegro);
        fila[9] = varios.formato_numero(descuento);
        fila[10] = varios.formato_numero(pagototal);
        fila[11] = fecha;
        fila[12] = otipojornal.getTexto();
        fila[13] = ocargo.getId();
        fila[14] = jornal.getIdjornal();
        fila[15] = otipojornal.getId();
        fila[16] = jornaldia.getIddiario();
        modelo.addRow(fila);

        limpiar();

    }//GEN-LAST:event_jButton7ActionPerformed


    private void txt_datos_jornaleroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_datos_jornaleroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_datos_jornalero.getText().length() > 8) {
                jornal.obtenerDatos();
                txt_monto.setText(varios.formato_numero(jornal.getPago_hora()));
                detalle.setIddetalle(jornal.getIdcargo());
                detalle.obtenerDatos();
                if (cbx_sinhoras.isSelected()) {
                    jTextField1.requestFocus();
                    cbx_cargo.getModel().setSelectedItem(new o_combobox(14, "LIMPIEZA LINEA"));
                    txt_monto.setText("0");
                } else {
                    cbx_cargo.getModel().setSelectedItem(new o_combobox(detalle.getIddetalle(), detalle.getDescripcion()));
                    cbx_cargo.requestFocus();
                }
            }
        }

    }//GEN-LAST:event_txt_datos_jornaleroKeyPressed

    private void jSpinner1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSpinner1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jSpinner2.requestFocus();
        }
    }//GEN-LAST:event_jSpinner1KeyPressed

    private void jSpinner2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSpinner2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton7.requestFocus();
        }
    }//GEN-LAST:event_jSpinner2KeyPressed

    private void cbx_cargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_cargoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_monto.requestFocus();
        }
    }//GEN-LAST:event_cbx_cargoKeyPressed

    private void txt_montoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jTextField1.requestFocus();
        }
    }//GEN-LAST:event_txt_montoKeyPressed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (cbx_sinhoras.isSelected()) {
                jButton7.requestFocus();
            } else {
                jSpinner1.requestFocus();
            }

        }
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jTextField2.requestFocus();
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //seleccionar item
        jornaldia.setIddiario(Integer.parseInt(jTable1.getValueAt(filaseleccionada, 16).toString()));
        //preguntar si eliminara
        int mensaje = JOptionPane.showConfirmDialog(null, "Realmente desea eliminar este item?", "Confirmar Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (mensaje == JOptionPane.OK_OPTION) {
            //eliminar
            jornaldia.eliminar();
            //actualizar listado
            modelo.removeRow(filaseleccionada);
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        filaseleccionada = jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jSpinner3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSpinner3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jSpinner3KeyPressed

    private void jSpinner4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSpinner4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jSpinner4KeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        jDialog1.setModal(true);
        jDialog1.setSize(600, 350);
        jDialog1.setLocationRelativeTo(null);

        jTextField4.setText(jTable1.getValueAt(filaseleccionada, 1).toString());
        double pago = 0;
        if (jTable1.getValueAt(filaseleccionada, 3).toString().equals("")) {
            pago = 0;
        } else {
            pago = Double.parseDouble(jTable1.getValueAt(filaseleccionada, 3).toString());
        }

        jRadioButton1.setSelected(true);
        if (pago == 0) {
            pago = Double.parseDouble(jTable1.getValueAt(filaseleccionada, 4).toString());
            jRadioButton2.setSelected(true);
        }

        jSpinner3.getModel();
        //String horainicio = new SimpleDateFormat("HH:mm").format(jSpinner1.getValue());
        try {
            String timeinicio = jTable1.getValueAt(filaseleccionada, 5).toString();
            String timefinal = jTable1.getValueAt(filaseleccionada, 6).toString();

            DateFormat sdf = new SimpleDateFormat("HH:mm a"); // or "hh:mm" for 12 hour format
            Date dateinicio = sdf.parse(timeinicio);
            Date datefinal = sdf.parse(timefinal);

            jSpinner3.setValue(dateinicio);
            jSpinner4.setValue(datefinal);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
        }

        jTextField8.setText(jTable1.getValueAt(filaseleccionada, 7).toString());
        jTextField6.setText(jTable1.getValueAt(filaseleccionada, 8).toString());
        jTextField7.setText(jTable1.getValueAt(filaseleccionada, 9).toString());
        jTextField9.setText(jTable1.getValueAt(filaseleccionada, 10).toString());

        detalle.setIddetalle(Integer.parseInt(jTable1.getValueAt(filaseleccionada, 13).toString()));
        detalle.obtenerDatos();
        cbx_cargo_mod.getModel().setSelectedItem(new o_combobox(detalle.getIddetalle(), detalle.getDescripcion()));

        jTextField3.setText(pago + "");
        jDialog1.setVisible(true);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jDialog1.dispose();
        jButton6.setEnabled(true);
        jButton6.doClick();
        jButton6.setEnabled(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        //String horainicio = txt_horaingreso.getText();
        String horainicio = new SimpleDateFormat("HH:mm").format(jSpinner3.getValue());
        //sString horasalida = txt_horasalida.getText();
        String horasalida = new SimpleDateFormat("HH:mm").format(jSpinner4.getValue());

        double horas = varios.restarHoras(horainicio, horasalida);
        double pago = Double.parseDouble(jTextField3.getText());

        int tipopago = 0;
        double pago_dia = 0;
        double pago_hora = 0;
        if (jRadioButton1.isSelected()) {
            tipopago = 0;
            pago_dia = pago;
        }
        if (jRadioButton2.isSelected()) {
            tipopago = 1;
            pago_hora = pago;
        }

        //total dia
        double pagototal = 0;
        if (tipopago == 0) {
            if (horas > 0) {
                pagototal = pago;
            }
        }
        if (tipopago == 1) {
            if (horas > 0) {
                pagototal = horas * pago;
            }
        }

        String sreintegro = jTextField6.getText();
        double reintegro = 0;
        if (varios.esDecimal(sreintegro)) {
            reintegro = Double.parseDouble(jTextField6.getText());
        }
        String sdescuento = jTextField7.getText();
        double descuento = 0;
        if (varios.esDecimal(sdescuento)) {
            descuento = Double.parseDouble(jTextField7.getText());
        }

        pagototal = pagototal + reintegro - descuento;

        jTextField9.setText(varios.formato_numero(pagototal));

        o_combobox ocargo = (o_combobox) cbx_cargo_mod.getSelectedItem();

        //cargar datos para actualziar informacion
        jornaldia.setIddiario(Integer.parseInt(jTable1.getValueAt(filaseleccionada, 16).toString()));
        jornaldia.setHora_inicio(horainicio);
        jornaldia.setHora_salida(horasalida);
        jornaldia.setIdjornal(jornal.getIdjornal());
        jornaldia.setPago_dia(pago_dia);
        jornaldia.setPago_hora(pago_hora);
        jornaldia.setIdcargo(ocargo.getId());
        jornaldia.setReintegro(reintegro);
        jornaldia.setDescuento(descuento);
        jornaldia.actualizar();


    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed


    }//GEN-LAST:event_jButton8ActionPerformed

    private void cbx_cargo_modKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_cargo_modKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_cargo_modKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbx_cargo;
    private javax.swing.JComboBox<String> cbx_cargo_mod;
    private javax.swing.JCheckBox cbx_sinhoras;
    private javax.swing.JComboBox<String> cbx_tipo_jornal;
    private javax.swing.JComboBox<String> cbx_tipo_pago;
    private com.toedter.calendar.JDateChooser dt_fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTextField txt_datos_jornalero;
    private javax.swing.JTextField txt_monto;
    private javax.swing.JTextField txt_nombre_cliente;
    // End of variables declaration//GEN-END:variables
}
