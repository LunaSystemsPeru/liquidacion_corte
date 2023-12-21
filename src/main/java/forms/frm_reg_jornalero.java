/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases.ApiPeruConsult;
import clases.Jornal;
import clases.Notificacion;
import clases.ParametroDetalle;
import clases.Varios;
import com.lunasystems.liquidacion.frm_principal;
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import objects.m_tipo;
import objects.o_combobox;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Mariela
 */
public class frm_reg_jornalero extends javax.swing.JDialog {

    //llamar modelo
    m_tipo mtipo = new m_tipo();

    public static Jornal jornal = new Jornal();
    Jornal jornalValidator = new Jornal();
    ParametroDetalle ccargo = new ParametroDetalle();
    Varios varios = new Varios();

    Notificacion NotifyI = new Notificacion();

    /**
     * Creates new form frm_reg_empleado
     */
    public frm_reg_jornalero(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txt_dni_jornal.requestFocus();

        mtipo.llenarCargos(cbx_cargo);

        if (jornal.getIdjornal() == 0) {
            //para crear nuevo 
            jTextField4.setText("");
            txt_dni_jornal.requestFocus();
            jButton3.setEnabled(false);
        } else {
            jButton3.setEnabled(true);
            jButton2.setEnabled(false);
            jButton1.setEnabled(false);
            jornal.obtenerDatos();
            ccargo.setIddetalle(jornal.getIdcargo());
            ccargo.obtenerDatos();

            cbx_cargo.getModel().setSelectedItem(new o_combobox(ccargo.getIddetalle(), ccargo.getDescripcion()));

            jTextField4.setText(jornal.getIdjornal() + "");
            txt_datos.setText(jornal.getDatos());
            txt_dni_jornal.setText(jornal.getDni_jornal());
            txt_dni_cuenta.setText(jornal.getDni_cuenta());
            txt_nro_cuenta.setText(jornal.getNro_cuenta());
            double monto = jornal.getPago_dia();
            cbx_pago.setSelectedIndex(0);
            if (monto == 0) {
                monto = jornal.getPago_hora();
                cbx_pago.setSelectedIndex(1);
            }
            txt_monto.setText(varios.formato_numero(monto));
            jTextArea1.setText(jornal.getDni_jornal() + "\n" + jornal.getDatos() + "\n" + jornal.getDni_cuenta() + "\n" + jornal.getNro_cuenta());

        }
    }

    private void llenar() {
        jornal.setIdcliente(frm_principal.cliente.getIdcliente());
        jornal.setDatos(txt_datos.getText().toUpperCase().trim());
        jornal.setDni_jornal(txt_dni_jornal.getText());
        jornal.setDni_cuenta(txt_dni_cuenta.getText().trim());
        jornal.setNro_cuenta(txt_nro_cuenta.getText().trim());

        double pago = Double.parseDouble(txt_monto.getText());
        double pago_dia = 0;
        double pago_hora = 0;
        if (cbx_pago.getSelectedIndex() == 0) {
            pago_dia = pago;
            pago_hora = 0;
        }
        if (cbx_pago.getSelectedIndex() == 1) {
            pago_dia = 0;
            pago_hora = pago;
        }
        jornal.setPago_dia(pago_dia);
        jornal.setPago_hora(pago_hora);

        o_combobox cargo = (o_combobox) cbx_cargo.getSelectedItem();
        jornal.setIdcargo(cargo.getId());
    }

    private void limpiar() {
        jornal.setIdjornal(0);
        txt_dni_cuenta.setText("");
        txt_dni_jornal.setText("");
        txt_nro_cuenta.setText("");
        txt_datos.setText("");
        txt_monto.setText("");
        txt_dni_jornal.requestFocus();
    }

    private boolean validarCuenta() {
        boolean encontrado = false;

        String dnicuenta = txt_dni_cuenta.getText().trim();
        String nrocuenta = txt_nro_cuenta.getText().trim();

        if (dnicuenta.length() == 0) {
            dnicuenta = "XXX";
        } else {
            jornalValidator.setDni_jornal(dnicuenta);
            jornalValidator.setIdjornal(jornal.getIdjornal());
            if (jornalValidator.validarDNIJornal()) {
                jornalValidator.obtenerDatos();
                JOptionPane.showMessageDialog(null, "El # de DNI le pertenece a un jornalero \n" + jornalValidator.getDatos());
                // encontrado = true;
            }
        }

        if (nrocuenta.length() == 0) {
            nrocuenta = "XXX";
        }

        jornalValidator.setDni_cuenta(dnicuenta);
        jornalValidator.setNro_cuenta(nrocuenta);
        jornalValidator.setIdjornal(jornal.getIdjornal());

        if (jornalValidator.validarCuenta()) {
            jornalValidator.obtenerDatos();
            JOptionPane.showMessageDialog(null, "El # de DNI y/o la cuenta ya esta afiliada a otra cuenta\n" + jornalValidator.getDatos());
            encontrado = true;
        } else {
            encontrado = false;
        }
        return encontrado;
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
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_datos = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_dni_jornal = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbx_cargo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbx_pago = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txt_monto = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txt_dni_cuenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_nro_cuenta = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();

        jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog1.setTitle("Datos Encontrados");

        jLabel8.setText("DNI Consultado:");

        jLabel9.setText("Datos Obtenidos:");

        jTextField6.setEditable(false);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField7)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jDialog1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 242, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar Jornalero");

        jToolBar1.setFloatable(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Add_728898.png"))); // NOI18N
        jButton1.setText("Nuevo");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_EditDocument_728933.png"))); // NOI18N
        jButton3.setText("Modificar");
        jButton3.setEnabled(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator2);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Diskette_728991.png"))); // NOI18N
        jButton2.setText("Guardar");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator1);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Cancel_728918.png"))); // NOI18N
        jButton4.setText("Salir");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Trabajador"));

        jLabel3.setText("Id.");

        jTextField4.setEditable(false);

        jLabel1.setText("Apellidos y  Nombres:");

        txt_datos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_datosKeyPressed(evt);
            }
        });

        jLabel7.setText("DNI Trabajador:");

        txt_dni_jornal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dni_jornalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dni_jornalKeyTyped(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_arrow-right_3688522.png"))); // NOI18N
        jButton5.setText("Validar Documento");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setText("Cargo:");

        cbx_cargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_cargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_cargoKeyPressed(evt);
            }
        });

        jLabel5.setText("Pago");

        cbx_pago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DIA", "HORA" }));
        cbx_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_pagoKeyPressed(evt);
            }
        });

        jLabel6.setText("Monto:");

        txt_monto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_montoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_datos, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbx_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_dni_jornal, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dni_jornal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_datos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Pago"));

        jLabel10.setText("DNI Titular CTA");

        txt_dni_cuenta.setEnabled(false);
        txt_dni_cuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dni_cuentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dni_cuentaKeyTyped(evt);
            }
        });

        jLabel2.setText("Nro Cuenta BCP:");

        txt_nro_cuenta.setEnabled(false);
        txt_nro_cuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nro_cuentaKeyPressed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_arrow-right_3688522.png"))); // NOI18N
        jButton6.setText("Buscar Cuenta x DNI Jornal");
        jButton6.setEnabled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_dni_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nro_cuenta)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dni_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nro_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!validarCuenta()) {
            llenar();
            jornal.obtenerId();
            jornal.insertar();
            limpiar();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limpiar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_datosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_datosKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_datos.getText().length() > 0) {
                cbx_cargo.setEnabled(true);
                cbx_cargo.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_datosKeyPressed

    private void txt_dni_jornalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_jornalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //verificar que dni del trabajador no este registrado 
            if (txt_dni_jornal.getText().length() == 8) {
                jornal.setDni_jornal(txt_dni_jornal.getText().trim());
                if (jornal.validarDNIJornal()) {
                    //el trabajador ya estra registrado
                    JOptionPane.showMessageDialog(null, "Este Nro de DNI ya se encuentra registrado");
                    cbx_cargo.setEnabled(false);
                } else {
                    //no esta registrado puede continuar
                    jButton5.doClick();
                    //  cbx_cargo.setEnabled(true);
                    //  cbx_cargo.requestFocus();
                }

            }

        }
    }//GEN-LAST:event_txt_dni_jornalKeyPressed

    private void txt_nro_cuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nro_cuentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boolean existecuenta = validarCuenta();
            if (!existecuenta) {
                jButton2.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_nro_cuentaKeyPressed

    private void cbx_cargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_cargoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cbx_pago.requestFocus();
        }
    }//GEN-LAST:event_cbx_cargoKeyPressed

    private void cbx_pagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_pagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_monto.requestFocus();
        }
    }//GEN-LAST:event_cbx_pagoKeyPressed

    private void txt_montoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_monto.getText().length() > 0) {
                txt_dni_cuenta.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "INGRESE MONTO");
            }
        }
    }//GEN-LAST:event_txt_montoKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        validarCuenta();
        llenar();
        jornal.actualizar();
        limpiar();
        jButton4.doClick();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (txt_dni_jornal.getText().length() == 8) {
            try {
                String json_datos = ApiPeruConsult.getJSONDNI(txt_dni_jornal.getText());
                ArrayList datos = ApiPeruConsult.showJSONDNI(json_datos);
                //jTextField6.setText(txt_dni_jornal.getText());
                txt_datos.setText(datos.get(0) + " " + datos.get(1) + " " + datos.get(2));
                txt_datos.requestFocus();
                /*
                jDialog1.setModal(true);
                jDialog1.setSize(510, 170);
                jDialog1.setLocationRelativeTo(null);
                jDialog1.setVisible(true);
                jTextField7.requestFocus();
                 */
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
            }
        } else {
            try {
                NotifyI.displayTray("Error en consulta", "NO HA INGRESADO INFORMACION");
            } catch (AWTException | MalformedURLException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void txt_dni_jornalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_jornalKeyTyped
        varios.solo_numeros(evt);
        varios.limitar_caracteres(evt, txt_dni_jornal, 9);
    }//GEN-LAST:event_txt_dni_jornalKeyTyped

    private void txt_dni_cuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_cuentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boolean existecuenta = validarCuenta();
            if (!existecuenta) {
                txt_nro_cuenta.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_dni_cuentaKeyPressed

    private void txt_dni_cuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_cuentaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dni_cuentaKeyTyped

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        //buscar en db por dni y obtener datos
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_reg_jornalero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_reg_jornalero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_reg_jornalero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_reg_jornalero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frm_reg_jornalero dialog = new frm_reg_jornalero(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbx_cargo;
    private javax.swing.JComboBox<String> cbx_pago;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txt_datos;
    private javax.swing.JTextField txt_dni_cuenta;
    private javax.swing.JTextField txt_dni_jornal;
    private javax.swing.JTextField txt_monto;
    private javax.swing.JTextField txt_nro_cuenta;
    // End of variables declaration//GEN-END:variables
}
