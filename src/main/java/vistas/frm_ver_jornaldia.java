/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases.Cliente;
import clases.Conectar;
import clases.JornalDia;
import clases.Varios;
import com.lunasystems.liquidacion.frm_principal;
import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import forms.frm_reg_jornal_dia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import objects.m_jornaleros;
import objects.m_tipo;
import objects.o_autocomplete;
import objects.o_combobox;

/**
 *
 * @author Mariela
 */
public class frm_ver_jornaldia extends javax.swing.JInternalFrame {
//modelos

    m_tipo mtipo = new m_tipo();
    m_jornaleros mjornal = new m_jornaleros(frm_principal.cliente.getIdcliente());
    Varios varios = new Varios();
    Cliente cliente = new Cliente();
    JornalDia jornaldia = new JornalDia();
    Conectar conectar = new Conectar();

    DefaultTableModel modelo;
    TextAutoCompleter tac_jornaleros = null;

    /**
     * Creates new form frm_ver_jornaldia
     */
    public frm_ver_jornaldia() {
        initComponents();

        mtipo.llenarTipos(cbx_tipojornal);
        setearFecha(varios.getFechaActual());

        cliente.setIdcliente(frm_principal.cliente.getIdcliente());
        cliente.obtenerDatos();
        jornaldia.setIdcliente(cliente.getIdcliente());
        cargarTabla();

        mjornal.llenarMeses(jComboBox1, jYearChooser1.getValue() + "");
        cargarJornaleros();
    }

    private void setearFecha(String date) {
        try {
            java.util.Date date2;
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            jDateChooser1.setDate(date2);
        } catch (ParseException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    private String convertFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(jDateChooser1.getDate());
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
        tbl_jornaleros.setModel(modelo);
        tbl_jornaleros.getColumnModel().getColumn(0).setPreferredWidth(30);
        tbl_jornaleros.getColumnModel().getColumn(1).setPreferredWidth(300);
        tbl_jornaleros.getColumnModel().getColumn(2).setPreferredWidth(90);
        tbl_jornaleros.getColumnModel().getColumn(3).setPreferredWidth(130);
        tbl_jornaleros.getColumnModel().getColumn(4).setPreferredWidth(50);
        tbl_jornaleros.getColumnModel().getColumn(5).setPreferredWidth(50);
        tbl_jornaleros.getColumnModel().getColumn(6).setPreferredWidth(80);
        tbl_jornaleros.getColumnModel().getColumn(7).setPreferredWidth(80);
        tbl_jornaleros.getColumnModel().getColumn(8).setPreferredWidth(80);
        tbl_jornaleros.getColumnModel().getColumn(9).setPreferredWidth(60);
        tbl_jornaleros.getColumnModel().getColumn(10).setPreferredWidth(60);
        tbl_jornaleros.getColumnModel().getColumn(11).setPreferredWidth(70);
        tbl_jornaleros.getColumnModel().getColumn(12).setPreferredWidth(120);
        tbl_jornaleros.getColumnModel().getColumn(13).setMaxWidth(0);
        tbl_jornaleros.getColumnModel().getColumn(13).setMinWidth(0);
        tbl_jornaleros.getColumnModel().getColumn(14).setMaxWidth(0);
        tbl_jornaleros.getColumnModel().getColumn(14).setMinWidth(0);
        tbl_jornaleros.getColumnModel().getColumn(15).setMaxWidth(0);
        tbl_jornaleros.getColumnModel().getColumn(15).setMinWidth(0);
        tbl_jornaleros.getColumnModel().getColumn(16).setMaxWidth(0);
        tbl_jornaleros.getColumnModel().getColumn(16).setMinWidth(0);
        tbl_jornaleros.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
        tbl_jornaleros.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0);
        tbl_jornaleros.getTableHeader().getColumnModel().getColumn(14).setMaxWidth(0);
        tbl_jornaleros.getTableHeader().getColumnModel().getColumn(14).setMinWidth(0);
        tbl_jornaleros.getTableHeader().getColumnModel().getColumn(15).setMaxWidth(0);
        tbl_jornaleros.getTableHeader().getColumnModel().getColumn(15).setMinWidth(0);
        tbl_jornaleros.getTableHeader().getColumnModel().getColumn(16).setMaxWidth(0);
        tbl_jornaleros.getTableHeader().getColumnModel().getColumn(16).setMinWidth(0);
        varios.centrar_celda(tbl_jornaleros, 0);
        varios.centrar_celda(tbl_jornaleros, 2);
        varios.centrar_celda(tbl_jornaleros, 6);
        varios.centrar_celda(tbl_jornaleros, 7);
        varios.centrar_celda(tbl_jornaleros, 8);
        varios.derecha_celda(tbl_jornaleros, 9);
        varios.derecha_celda(tbl_jornaleros, 10);
        varios.derecha_celda(tbl_jornaleros, 11);

    }

    private void cargarJornaleros() {
        try {
            if (tac_jornaleros != null) {
                tac_jornaleros.removeAllItems();
            }
            tac_jornaleros = new TextAutoCompleter(jTextField3, new AutoCompleterCallback() {
                @Override
                public void callback(Object selectedItem) {
                    Object itemSelected = selectedItem;
                    jornaldia.setIdjornal(0);
                    if (itemSelected instanceof o_autocomplete) {
                        int pcodigo = ((o_autocomplete) itemSelected).getId();
                        String ptexto = ((o_autocomplete) itemSelected).getTexto();
                        System.out.println("jornal seleccionado " + ptexto);
                        jornaldia.setIdjornal(pcodigo);
                    } else {
                        System.out.println("El item es de un tipo desconocido");
                    }
                }
            });

            tac_jornaleros.setMode(0);
            tac_jornaleros.setCaseSensitive(false);
            Statement st = conectar.conexion();
            String sql = "select * from jornaleros "
                    + "where idcliente = '" + cliente.getIdcliente() + "'";
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_resumen = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jToolBar1 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_jornaleros = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        cbx_tipojornal = new javax.swing.JComboBox<>();

        setTitle("Resumen Jornaleros");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_arrow-right_3688522.png"))); // NOI18N
        jLabel1.setText("Pago Diario a Jornaleros");

        jLabel2.setText("Año:");

        jYearChooser1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jYearChooser1KeyPressed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Magnifier_728952.png"))); // NOI18N
        jButton2.setText("Buscar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tbl_resumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha", "Tipo Jornal", "Nro Jornaleros", "Monto S/"
            }
        ));
        tbl_resumen.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tbl_resumen);

        jLabel7.setText("Mes:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 139, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jYearChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addContainerGap())
        );

        jToolBar1.setFloatable(false);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Add_728898.png"))); // NOI18N
        jButton3.setText("Agregar");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Cut_728989.png"))); // NOI18N
        jButton4.setText("Eliminar");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Folder_728983.png"))); // NOI18N
        jButton5.setText("Generar Excel");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Cancel_728918.png"))); // NOI18N
        jButton1.setText("Salir");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_arrow-right_3688522.png"))); // NOI18N
        jLabel3.setText("Buscar por Fecha o seleccionar a la izquierda");

        tbl_jornaleros.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_jornaleros.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(tbl_jornaleros);

        jLabel4.setText("Total Jornaleros");

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel5.setText("por Pagar");

        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_arrow-right_3688522.png"))); // NOI18N
        jLabel6.setText("Buscar por Jornalero");

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Magnifier_728952.png"))); // NOI18N
        jButton6.setText("Buscar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Magnifier_728952.png"))); // NOI18N
        jButton7.setText("Buscar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        cbx_tipojornal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "JORNAL CORTE", "JORNAL ENVASE" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_tipojornal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_tipojornal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        frm_reg_jornal_dia formulario = new frm_reg_jornal_dia();
        varios.llamar_ventana(formulario);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        cargarTabla();

        String fechafin = varios.fecha_myql(convertFecha());
        jornaldia.setFecha(fechafin);
        o_combobox otipo = (o_combobox) cbx_tipojornal.getSelectedItem();
        jornaldia.setIdtipo(otipo.getId());
        jornaldia.mostrarJornalDia(modelo);

        tbl_jornaleros.setModel(modelo);

        int contarfilas = tbl_jornaleros.getRowCount();
        jTextField1.setText(contarfilas + "");
        double sumapago = 0;
        for (int i = 0; i < contarfilas; i++) {
            sumapago += (Double.parseDouble(tbl_jornaleros.getValueAt(i, 11).toString()));
        }
        jLabel4.setText("Nro de jornaleros. ");
        jTextField2.setText(varios.formato_totales(sumapago));
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //LLAMAR A ORDEN PARA GENERAR EL EXCEl.
        //jornaldia.rptPagoJornalentreDias();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jornaldia.mostrarDiasTrabajados(tbl_resumen, jYearChooser1.getValue() + "", jComboBox1.getSelectedItem().toString());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jYearChooser1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jYearChooser1KeyPressed
        mjornal.llenarMeses(jComboBox1, jYearChooser1.getValue() + "");
    }//GEN-LAST:event_jYearChooser1KeyPressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        cargarTabla();

        jornaldia.mostrarDiasJornal(modelo);

        tbl_jornaleros.setModel(modelo);

        int contarfilas = tbl_jornaleros.getRowCount();
        jTextField1.setText(contarfilas + "");
        double sumapago = 0;
        for (int i = 0; i < contarfilas; i++) {
            sumapago += (Double.parseDouble(tbl_jornaleros.getValueAt(i, 11).toString()));
        }
        jLabel4.setText("Dias Asistencia. ");
        jTextField2.setText(varios.formato_totales(sumapago));
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbx_tipojornal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JToolBar jToolBar1;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    private javax.swing.JTable tbl_jornaleros;
    private javax.swing.JTable tbl_resumen;
    // End of variables declaration//GEN-END:variables
}
