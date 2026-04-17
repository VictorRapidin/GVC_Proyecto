/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Modelo.ClaseUsuario;
import Modelo.ImagenFondo;

/**
 *
 * @author ghust
 */
public class ReporteUbicaciones extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReporteUbicaciones.class.getName());

    /**
     * Creates new form ReporteUbicaciones
     */
    ImagenFondo imgFondo;
    ClaseUsuario userSesion;
    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

    public ReporteUbicaciones(ClaseUsuario usuario) {
        imgFondo = new ImagenFondo("src/imagenes/FondoFRM.jpg");
        this.setContentPane(imgFondo);
        setSize(900, 550);
        setLocationRelativeTo(null);
        userSesion = usuario;
        setResizable(false);
        initComponents();
        cargarTablaUbicaciones();
        aplicarFiltrosUbicaciones();
        cargarCombosDinamicos();
        lblNombreUsuario.setText("Bienvenido: " + userSesion.getNombre() + " " + userSesion.getApellido() + "  Rol: " + userSesion.getRol());
        String rol = userSesion.getRol();
    }

    private void cargarTablaUbicaciones() {
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");          // 0 (Oculto)
        modelo.addColumn("Ruta");        // 1
        modelo.addColumn("RC");          // 2
        modelo.addColumn("Código");      // 3
        modelo.addColumn("Calle 1");     // 4
        modelo.addColumn("Calle 2");     // 5
        modelo.addColumn("Sentido");     // 6
        modelo.addColumn("Estado");      // 7
        modelo.addColumn("F. Instalación");// 8
        modelo.addColumn("Tipo");        // 9

        Controlador.ControladorUbicaciones control = new Controlador.ControladorUbicaciones();
        Modelo.ClaseUbicaciones[] lista = control.leerUbicaciones();

        if (lista != null) {
            for (Modelo.ClaseUbicaciones ubi : lista) {
                if (ubi != null) {
                    Object[] fila = new Object[10];
                    fila[0] = ubi.getIdUbicacion();
                    fila[1] = ubi.getRuta();
                    fila[2] = ubi.getRc();
                    fila[3] = ubi.getCodigo();
                    fila[4] = ubi.getCalle1();
                    fila[5] = ubi.getCalle2();
                    fila[6] = ubi.getSentido();
                    fila[7] = ubi.getEstado();
                    fila[8] = ubi.getFechaInstalacion() == null ? "" : ubi.getFechaInstalacion();
                    fila[9] = ubi.getTipoUbicacion();

                    modelo.addRow(fila);
                }
            }
        }

        tblUbicaciones.setModel(modelo);
        sorter = new javax.swing.table.TableRowSorter<>(modelo);
        tblUbicaciones.setRowSorter(sorter);
        tblUbicaciones.getColumnModel().getColumn(0).setMinWidth(0);
        tblUbicaciones.getColumnModel().getColumn(0).setMaxWidth(0);
        tblUbicaciones.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblUbicaciones.setRowHeight(22);
        tblUbicaciones.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));
        tblUbicaciones.getTableHeader().setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
    }

    private void aplicarFiltrosUbicaciones() {
    if (sorter == null) return; 

    java.util.List<javax.swing.RowFilter<Object, Object>> filtros = new java.util.ArrayList<>();
    if (!txtBuscar.getText().trim().isEmpty()) {
        filtros.add(javax.swing.RowFilter.regexFilter("(?i)" + txtBuscar.getText().trim(), 2, 3, 4, 5));
    }
    if (cmbFiltroRuta.getSelectedIndex() > 0) {
        String ruta = cmbFiltroRuta.getSelectedItem().toString();
        filtros.add(javax.swing.RowFilter.regexFilter("^" + ruta + "$", 1));
    }

    if (cmbFiltroTipo.getSelectedIndex() > 0) {
        String tipo = cmbFiltroTipo.getSelectedItem().toString();
        filtros.add(javax.swing.RowFilter.regexFilter("^" + tipo + "$", 9));
    }
    if (filtros.isEmpty()) {
        sorter.setRowFilter(null);
    } else {
        sorter.setRowFilter(javax.swing.RowFilter.andFilter(filtros));
    }
}
    private void cargarCombosDinamicos() {
    Controlador.ControladorUbicaciones control = new Controlador.ControladorUbicaciones();
    
    // 1. Llenar Combo de Rutas
        cmbFiltroRuta.removeAllItems();
    for (String r : control.obtenerRutasUnicas()) {
        cmbFiltroRuta.addItem(r);
    }

    cmbFiltroTipo.removeAllItems();
    for (String t : control.obtenerTiposUnicos()) {
        cmbFiltroTipo.addItem(t);
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

        jLabel1 = new javax.swing.JLabel();
        jbnVolver = new javax.swing.JButton();
        lblNombreUsuario = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUbicaciones = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cmbFiltroTipo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        cmbFiltroRuta = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 138, 25));
        jLabel1.setText("REPORTE DE UBICACIONES");

        jbnVolver.setText("Volver");
        jbnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnVolverActionPerformed(evt);
            }
        });

        lblNombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreUsuario.setText("jLabel1");

        tblUbicaciones.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(41, 112, 172), null));
        tblUbicaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Calle #1", "Calle #2", "Tipo"
            }
        ));
        jScrollPane2.setViewportView(tblUbicaciones);

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel2.setText("Filtro general");

        jButton1.setText("Editar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cmbFiltroTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFiltroTipoItemStateChanged(evt);
            }
        });
        cmbFiltroTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroTipoActionPerformed(evt);
            }
        });
        cmbFiltroTipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmbFiltroTipoKeyReleased(evt);
            }
        });

        jLabel3.setText("Filtro tipo");

        jButton2.setText("En mantenimiento");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Dar de baja");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        cmbFiltroRuta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroRuta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFiltroRutaItemStateChanged(evt);
            }
        });

        jLabel4.setText("Filtro ruta");

        jButton4.setText("Reactivar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(226, 226, 226)
                                .addComponent(lblNombreUsuario))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 894, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbFiltroTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbFiltroRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(83, 83, 83)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(208, 208, 208))
            .addGroup(layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(jButton1)
                .addGap(58, 58, 58)
                .addComponent(jButton2)
                .addGap(56, 56, 56)
                .addComponent(jButton3)
                .addGap(39, 39, 39)
                .addComponent(jButton4)
                .addGap(45, 45, 45)
                .addComponent(jbnVolver)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblNombreUsuario)
                .addGap(81, 81, 81)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltroTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltroRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnVolver)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(37, 37, 37))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        aplicarFiltrosUbicaciones();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int fila = tblUbicaciones.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona una ubicación de la tabla para editar.");
            return;
        }

        int modelRow = tblUbicaciones.convertRowIndexToModel(fila);
        Modelo.ClaseUbicaciones ubi = new Modelo.ClaseUbicaciones();
        ubi.setIdUbicacion((int) tblUbicaciones.getModel().getValueAt(modelRow, 0));
        ubi.setRuta((int) tblUbicaciones.getModel().getValueAt(modelRow, 1));
        ubi.setRc(tblUbicaciones.getModel().getValueAt(modelRow, 2).toString());
        ubi.setCodigo(tblUbicaciones.getModel().getValueAt(modelRow, 3).toString());
        ubi.setCalle1(tblUbicaciones.getModel().getValueAt(modelRow, 4).toString());
        ubi.setCalle2(tblUbicaciones.getModel().getValueAt(modelRow, 5).toString());
        ubi.setSentido(tblUbicaciones.getModel().getValueAt(modelRow, 6).toString());
        ubi.setEstado(tblUbicaciones.getModel().getValueAt(modelRow, 7).toString());
        Object fecha = tblUbicaciones.getModel().getValueAt(modelRow, 8);
        ubi.setFechaInstalacion(fecha != null ? fecha.toString() : "");

        ubi.setTipoUbicacion(tblUbicaciones.getModel().getValueAt(modelRow, 9).toString());
        Ubicaciones ventanaEdit = new Ubicaciones(userSesion, ubi);
        ventanaEdit.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void cmbFiltroTipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFiltroTipoKeyReleased
        // TODO add your handling code here:
        aplicarFiltrosUbicaciones();
    }//GEN-LAST:event_cmbFiltroTipoKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int fila = tblUbicaciones.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona una ubicación de la tabla.");
            return;
        }

        int modelRow = tblUbicaciones.convertRowIndexToModel(fila);
        int id = (int) tblUbicaciones.getModel().getValueAt(modelRow, 0);
        String codigo = tblUbicaciones.getModel().getValueAt(modelRow, 3).toString();

        int confirmar = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Poner la ubicación " + codigo + " en MANTENIMIENTO?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
            Controlador.ControladorUbicaciones control = new Controlador.ControladorUbicaciones();
            if (control.actualizarEstadoUbicacion(id, "MANTENIMIENTO")) {
                javax.swing.JOptionPane.showMessageDialog(this, "Estado actualizado: MANTENIMIENTO");
                cargarTablaUbicaciones();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int fila = tblUbicaciones.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona una ubicación.");
            return;
        }

        int modelRow = tblUbicaciones.convertRowIndexToModel(fila);
        int id = (int) tblUbicaciones.getModel().getValueAt(modelRow, 0);
        int ruta = (int) tblUbicaciones.getModel().getValueAt(modelRow, 1);
        String rcActual = tblUbicaciones.getModel().getValueAt(modelRow, 2).toString();
        String codigo = tblUbicaciones.getModel().getValueAt(modelRow, 3).toString();

        int confirmar = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Dar de baja el mueble " + codigo + "? Esto lo sacara de la ruta:" + ruta,
                "Confirmar Baja", javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
            Controlador.ControladorUbicaciones control = new Controlador.ControladorUbicaciones();
            if (control.actualizarEstadoUbicacion(id, "INACTIVA")) {

                control.cerrarEspacioRC(ruta, rcActual);

                javax.swing.JOptionPane.showMessageDialog(this, "Mueble dado de baja y ruta reordenada.");
                cargarTablaUbicaciones();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cmbFiltroTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbFiltroTipoActionPerformed

    private void cmbFiltroTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFiltroTipoItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
        aplicarFiltrosUbicaciones();
    }
    }//GEN-LAST:event_cmbFiltroTipoItemStateChanged

    private void cmbFiltroRutaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFiltroRutaItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
        aplicarFiltrosUbicaciones();
    }
    }//GEN-LAST:event_cmbFiltroRutaItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int fila = tblUbicaciones.getSelectedRow();
    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Selecciona una ubicación para reactivar.");
        return;
    }

    int modelRow = tblUbicaciones.convertRowIndexToModel(fila);
    int id = (int) tblUbicaciones.getModel().getValueAt(modelRow, 0);
    int ruta = (int) tblUbicaciones.getModel().getValueAt(modelRow, 1);
    String rcActual = tblUbicaciones.getModel().getValueAt(modelRow, 2).toString();
    String estadoActual = tblUbicaciones.getModel().getValueAt(modelRow, 7).toString();
    String codigo = tblUbicaciones.getModel().getValueAt(modelRow, 3).toString();

    if (estadoActual.equals("ACTIVA")) {
        javax.swing.JOptionPane.showMessageDialog(this, "El mueble ya se encuentra ACTIVO.");
        return;
    }

    Controlador.ControladorUbicaciones control = new Controlador.ControladorUbicaciones();
    if (estadoActual.equals("INACTIVA")) {
        int confirmar = javax.swing.JOptionPane.showConfirmDialog(this, 
            "¿Reactivar mueble " + codigo + "? Esto desplazará los consecutivos en la ruta " + ruta, 
            "Reactivación con Desplazamiento", javax.swing.JOptionPane.YES_NO_OPTION);
            
        if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
            control.abrirEspacioRC(ruta, rcActual);
            if (control.actualizarEstadoUbicacion(id, "ACTIVA")) {
                javax.swing.JOptionPane.showMessageDialog(this, "Mueble reactivado y ruta organizada.");
                cargarTablaUbicaciones();
            }
        }
    } else if (estadoActual.equals("MANTENIMIENTO")) {
        if (control.actualizarEstadoUbicacion(id, "ACTIVA")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Mantenimiento finalizado. Mueble ACTIVO.");
            cargarTablaUbicaciones();
        }
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jbnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnVolverActionPerformed
        // TODO add your handling code here:
        MenuReportes menuR = new MenuReportes(userSesion);
        menuR.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbnVolverActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ReporteUbicaciones(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbFiltroRuta;
    private javax.swing.JComboBox<String> cmbFiltroTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbnVolver;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JTable tblUbicaciones;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
