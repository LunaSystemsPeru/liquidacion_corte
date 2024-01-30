/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases.Cliente;
import clases.Conectar;
import clases.EnvaseDetalle;
import clases.EnvaseEquitativo;
import clases.Jornal;
import clases.ParametroDetalle;
import clases.Varios;
import com.lunasystems.liquidacion.frm_principal;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import objects.m_tipo;
import objects.o_combobox;

/**
 *
 * @author Mariela
 */
public class frm_reg_jornal_envase extends javax.swing.JInternalFrame {

    //modelos
    m_tipo mtipo = new m_tipo();

    //CLASES PARA USAR
    Cliente cliente = new Cliente();
    Jornal jornal = new Jornal();
    EnvaseEquitativo envase = new EnvaseEquitativo();
    EnvaseDetalle envasedetalle = new EnvaseDetalle();
    ParametroDetalle detalle = new ParametroDetalle();
    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    //variables y objetos 
    DefaultTableModel modelo;

    SpinnerDateModel dateModel = new SpinnerDateModel();
    int filaseleccionada;
    double precioporbarrill = 0;
    double totalbarriles = 0;
    public static int idjornalero = 0;

    /**
     * Creates new form frm_reg_jornal_dia
     */
    public frm_reg_jornal_envase() {
        initComponents();
        cliente.setIdcliente(frm_principal.cliente.getIdcliente());
        cliente.obtenerDatos();

        envase.setIdcliente(cliente.getIdcliente());

        txt_nombre_cliente.setText(cliente.getNombre() + " | " + cliente.getSede());

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
        modelo.addColumn("Nro Barriles");       //3
        modelo.addColumn("Adicional");          //4
        modelo.addColumn("Descuento");          //5
        modelo.addColumn("a Pagar");            //6
        modelo.addColumn("");                   //7 //idenvase
        modelo.addColumn("");                   //8 //idjornal
        jTable1.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
        jTable1.setRowSorter(elQueOrdena);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(350);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(0);
        varios.centrar_celda(jTable1, 0);
        varios.centrar_celda(jTable1, 2);
        varios.centrar_celda(jTable1, 3);
        varios.derecha_celda(jTable1, 4);
        varios.derecha_celda(jTable1, 5);
        varios.derecha_celda(jTable1, 6);

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
        jButton1.doClick();
    }

    private void activar() {
        jButton1.setEnabled(true);
        txt_datos_jornalero.setEnabled(true);
        jButton7.setEnabled(true);
        dt_fecha.setEnabled(false);
        jButton6.setEnabled(false);
    }

    private void desactivar() {
        jButton1.setEnabled(false);
        txt_datos_jornalero.setEnabled(false);
        jButton7.setEnabled(false);
        dt_fecha.setEnabled(true);
        jButton6.setEnabled(true);
    }

    private void setInfoJornalero() {
        jornal.setIdjornal(idjornalero);
        jornal.obtenerDatos();

        detalle.setIddetalle(jornal.getIdcargo());
        detalle.obtenerDatos();

        idjornalero = 0;
        jTextField1.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_nombre_cliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dt_fecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txt_datos_jornalero = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        setTitle("Asistencia Envasado Equitativo");

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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Magnifier_728952.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Nro Barriles:");

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

        jTextField11.setEditable(false);
        jTextField11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField11.setText("0");

        jLabel4.setText("Total Barriles:");

        jTextField12.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel5.setText("Precio x Barrill");

        jTextField13.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Diskette_728991.png"))); // NOI18N
        jButton8.setText("Modificar");
        jButton8.setEnabled(false);

        jButton11.setText("set Info");
        jButton11.setContentAreaFilled(false);
        jButton11.setOpaque(true);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nombre_cliente)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txt_datos_jornalero, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton8))
                                    .addComponent(jButton7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                                .addComponent(jButton11))))
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
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_datos_jornalero, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cargarTabla();
        desactivar();
        dt_fecha.requestFocus();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Frame f = JOptionPane.getRootFrame();
        frm_view_lista_jornaleros dialog = new frm_view_lista_jornaleros(f, true);
        frm_view_lista_jornaleros.jTextField1.requestFocus();
        dialog.tipo_origen = 2;
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

        if (varios.esDecimal(jTextField13.getText())) {
            precioporbarrill = Double.parseDouble(jTextField13.getText());
        } else {
            JOptionPane.showMessageDialog(null, "PRECIO INCORRECTO, VERIFIQUE NUMERO");
        }

        if (varios.esDecimal(jTextField12.getText())) {
            totalbarriles = Double.parseDouble(jTextField12.getText());
        } else {
            JOptionPane.showMessageDialog(null, "NUMERO INCORRECTO, VERIFIQUE CANTIDAD TOTAL DE BARRILES");
        }

        //cargar todos los botones
        activar();

        String fechadb = varios.fecha_myql(convertFecha());
        envase.setFecha(fechadb);
        envase.setCantbarriles(totalbarriles);
        envase.setIdcliente(frm_principal.cliente.getIdcliente());
        envase.setPreciobarril(precioporbarrill);

        if (!envase.validarEnvase()) {
            envase.obtenerId();
            envase.insertar();
        } else {
            envasedetalle.setIdenvase(envase.getIdenvase());
            envasedetalle.mostrarEnvasadoDia(modelo);
            jTable1.setModel(modelo);
        }

        //cargar barriles
        //cargar empleados
        jButton1.doClick();

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        /*
        
        modelo.addColumn("Item");               //0
        modelo.addColumn("Nombre");             //1
        modelo.addColumn("Fecha");              //2
        modelo.addColumn("Nro Barriles");       //3
        modelo.addColumn("Adicional");          //4
        modelo.addColumn("Descuento");          //5
        modelo.addColumn("a Pagar");            //6
        modelo.addColumn("");                   //7 //idenvase
        modelo.addColumn("");                   //8 //idjornal

         */

        //definir fecha seleccionada
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(dt_fecha.getDate());

        //llenar datos de pago 
        double adicional = 0;
        double descuento = 0;
        double pagototal = 0;
        int nrobarrilesjornal = 0;

        if (varios.esEntero(jTextField11.getText())) {
            nrobarrilesjornal = Integer.parseInt(jTextField11.getText());
        }

        if (varios.esDecimal(jTextField1.getText())) {
            adicional = Double.parseDouble(jTextField1.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un numero correcto");
        }

        if (varios.esDecimal(jTextField2.getText())) {
            descuento = Double.parseDouble(jTextField2.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un numero correcto");
        }

        int nrofila = jTable1.getRowCount();
        pagototal = (totalbarriles / (nrofila + 1)) + adicional - descuento;

        //llenar envase detalle
        envasedetalle.setIdjornal(jornal.getIdjornal());
        envasedetalle.setAdicional(adicional);
        envasedetalle.setDescuento(descuento);
        envasedetalle.setIdenvase(envase.getIdenvase());
        envasedetalle.insertar();

        //llenar filas
        Object fila[] = new Object[9];
        fila[0] = nrofila + 1;
        fila[1] = jornal.getDatos();
        fila[2] = fecha;
        fila[3] = totalbarriles / (nrofila + 1);
        fila[4] = varios.formato_numero(adicional);
        fila[5] = varios.formato_numero(descuento);
        fila[6] = varios.formato_numero(pagototal);
        fila[7] = envasedetalle.getIdenvase();
        fila[8] = envasedetalle.getIdjornal();
        modelo.addRow(fila);

        limpiar();

    }//GEN-LAST:event_jButton7ActionPerformed


    private void txt_datos_jornaleroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_datos_jornaleroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_datos_jornalero.getText().length() > 8) {
                jornal.obtenerDatos();
                detalle.setIddetalle(jornal.getIdcargo());
                detalle.obtenerDatos();
                jTextField1.requestFocus();
            }
        }

    }//GEN-LAST:event_txt_datos_jornaleroKeyPressed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton7.requestFocus();
        }
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jTextField2.requestFocus();
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //seleccionar item
        envasedetalle.setIdenvase(envase.getIdenvase());
        envasedetalle.setIdjornal(Integer.parseInt(jTable1.getValueAt(filaseleccionada, 7).toString()));
        //preguntar si eliminara
        int mensaje = JOptionPane.showConfirmDialog(null, "Realmente desea eliminar este item?", "Confirmar Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (mensaje == JOptionPane.OK_OPTION) {
            //eliminar
            envasedetalle.eliminar();
            //actualizar listado
            modelo.removeRow(filaseleccionada);
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        filaseleccionada = jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        setInfoJornalero();
    }//GEN-LAST:event_jButton11ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dt_fecha;
    private javax.swing.JButton jButton1;
    public static javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToolBar jToolBar1;
    public static javax.swing.JTextField txt_datos_jornalero;
    private javax.swing.JTextField txt_nombre_cliente;
    // End of variables declaration//GEN-END:variables
}
