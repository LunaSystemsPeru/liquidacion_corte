/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases.Jornal;
import clases.JornalDia;
import clases.Varios;
import com.lunasystems.liquidacion.frm_principal;
import forms.frm_reg_jornalero;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mariela
 */
public class frm_ver_jornaleros extends javax.swing.JInternalFrame {

    Jornal jornal = new Jornal();
    Varios c_varios = new Varios();
    JornalDia jornaldia = new JornalDia();

    String sql = "";
    int idcliente = frm_principal.cliente.getIdcliente();
    int filaseleccionada;

    DefaultTableModel modelo;

    /**
     * Creates new form frm_ver_empleados
     */
    public frm_ver_jornaleros() {
        initComponents();
        sql = "select j.idjornal, j.datos, j.dia_pago,j.hora_pago, j.nrodocumento, j.nrocuenta, pd.descripcion as ncargo, c.sede "
                + "from jornaleros as j "
                + "inner join parametros_detalle as pd on pd.iddetalle = j.idcargo "
                + "inner join clientes as c on c.idcliente = j.idcliente "
                + "order by j.datos asc limit 50";
        jornal.verFilas(tbl_jornaleros, sql);
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
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_jornaleros = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();

        jDialog1.setTitle("Ver Historia del Jornalero");

        jLabel2.setText("Datos");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2)))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addContainerGap())
        );

        setTitle("Ver Jornaleros");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Magnifier_728952.png"))); // NOI18N
        jLabel1.setText("Busqueda");

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
        tbl_jornaleros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_jornalerosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_jornaleros);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

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

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_EditDocument_728933.png"))); // NOI18N
        jButton3.setText("Modificar");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Folder_728983.png"))); // NOI18N
        jButton4.setText("Ver Historia");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16x16/iconfinder_Application_728900.png"))); // NOI18N
        jButton6.setText("ver sin Cuentas");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Cut_728989.png"))); // NOI18N
        jButton1.setText("Eliminar");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String texto = jTextField1.getText().trim();
            sql = "select j.idjornal, j.datos, j.dia_pago,j.hora_pago, j.nrodocumento, j.nrocuenta, pd.descripcion as ncargo, c.sede "
                    + "from jornaleros as j "
                    + "inner join parametros_detalle as pd on pd.iddetalle = j.idcargo "
                    + "inner join clientes as c on c.idcliente = j.idcliente "
                    + "where datos like '%" + texto + "%' or j.nrodocumento = '" + texto + "' "
                    + "order by j.datos asc";
            jornal.verFilas(tbl_jornaleros, sql);
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Frame f = JOptionPane.getRootFrame();
        frm_reg_jornalero.jornal.setIdjornal(0);
        frm_reg_jornalero dialog = new frm_reg_jornalero(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Frame f = JOptionPane.getRootFrame();
        int idjornalero = Integer.parseInt(tbl_jornaleros.getValueAt(filaseleccionada, 0).toString());
        frm_reg_jornalero.jornal.setIdjornal(idjornalero);
        frm_reg_jornalero dialog = new frm_reg_jornalero(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbl_jornalerosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_jornalerosMouseClicked
        filaseleccionada = tbl_jornaleros.getSelectedRow();
    }//GEN-LAST:event_tbl_jornalerosMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        frm_ver_jornal_sincuenta formulario = new frm_ver_jornal_sincuenta();
        c_varios.llamar_ventana(formulario);
    }//GEN-LAST:event_jButton6ActionPerformed

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
        modelo.addColumn("Planta");              //3
        modelo.addColumn("Cargo");              //4
        modelo.addColumn("S/ x Dia");           //5
        modelo.addColumn("S/ x Hora");          //6 
        modelo.addColumn("Hora Entrada");       //7
        modelo.addColumn("Hora Salida");        //8
        modelo.addColumn("Total Horas");        //9
        modelo.addColumn("Reintegro");          //10
        modelo.addColumn("Descuento");          //11
        modelo.addColumn("a Pagar");            //12
        modelo.addColumn("Tipo Jornada");       //13
        modelo.addColumn("");                   //14
        modelo.addColumn("");                   //15
        modelo.addColumn("");                   //16
        modelo.addColumn("");                   //17
        jTable2.setModel(modelo);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(300);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(90);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(130);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(50);
        jTable2.getColumnModel().getColumn(6).setPreferredWidth(50);
        jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(8).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(9).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(10).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(11).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(12).setPreferredWidth(70);
        jTable2.getColumnModel().getColumn(13).setPreferredWidth(120);
        jTable2.getColumnModel().getColumn(14).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(14).setMinWidth(0);
        jTable2.getColumnModel().getColumn(15).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(15).setMinWidth(0);
        jTable2.getColumnModel().getColumn(16).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(16).setMinWidth(0);
        jTable2.getColumnModel().getColumn(17).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(17).setMinWidth(0);
        jTable2.getTableHeader().getColumnModel().getColumn(14).setMaxWidth(0);
        jTable2.getTableHeader().getColumnModel().getColumn(14).setMinWidth(0);
        jTable2.getTableHeader().getColumnModel().getColumn(15).setMaxWidth(0);
        jTable2.getTableHeader().getColumnModel().getColumn(15).setMinWidth(0);
        jTable2.getTableHeader().getColumnModel().getColumn(16).setMaxWidth(0);
        jTable2.getTableHeader().getColumnModel().getColumn(16).setMinWidth(0);
        jTable2.getTableHeader().getColumnModel().getColumn(17).setMaxWidth(0);
        jTable2.getTableHeader().getColumnModel().getColumn(17).setMinWidth(0);
        c_varios.centrar_celda(jTable2, 0);
        c_varios.centrar_celda(jTable2, 2);
        c_varios.centrar_celda(jTable2, 3);
        c_varios.centrar_celda(jTable2, 7);
        c_varios.centrar_celda(jTable2, 8);
        c_varios.centrar_celda(jTable2, 9);
        c_varios.derecha_celda(jTable2, 10);
        c_varios.derecha_celda(jTable2, 11);
        c_varios.derecha_celda(jTable2, 12);

    }


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int idjornalero = Integer.parseInt(tbl_jornaleros.getValueAt(filaseleccionada, 0).toString());
        jDialog1.setModal(true);
        jDialog1.setSize(c_varios.obtenerSizePrincipal());
        //jDialog1.setSize(800, 650);
        jDialog1.setLocationRelativeTo(null);

        cargarTabla();
        jornaldia.setIdjornal(idjornalero);
        jornaldia.mostraHistoriaJornal(modelo);
        jTable2.setModel(modelo);

        jDialog1.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tbl_jornaleros;
    // End of variables declaration//GEN-END:variables
}
