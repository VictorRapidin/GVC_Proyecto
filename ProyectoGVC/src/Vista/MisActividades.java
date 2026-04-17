/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorActividad;
import Modelo.ClaseUsuario;
import Modelo.ImagenFondo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ghust
 */
public class MisActividades extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MisActividades.class.getName());

    /**
     * Creates new form ReporteActividades
     */
    ClaseUsuario userSesion;
    ImagenFondo imgFondo;

    public MisActividades(ClaseUsuario usuario) {
        userSesion = usuario;
        imgFondo = new ImagenFondo("src/imagenes/FondoFRM.jpg");
        this.setContentPane(imgFondo);
        initComponents();
        cargarTabla();
        btnEmpezar.setEnabled(false);
        btnFinalizar.setEnabled(false);
        tblMisActividades.setRowHeight(25);
        tblMisActividades.setShowGrid(false);
        tblMisActividades.setIntercellSpacing(new Dimension(0, 0));
        setSize(900, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        lblTitulo.setText("Actividades de: " + userSesion.getNombre());
        lblNombreUsuario.setText("Bienvenido: " + userSesion.getNombre() + " " + userSesion.getApellido() + "  Rol: " + userSesion.getRol());
    }

    public void cargarTabla() {
        Controlador.ControladorActividad control = new Controlador.ControladorActividad();
        Object[][] datos = control.leerActividadesDiariasPorEmpleado(userSesion.getIdUsuario());
        String[] columnas = {"ID", "Actividad", "Campaña", "Código", "Fecha", "Prioridad", "Estado", "ID_UBI"};

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tblMisActividades.setModel(modelo);
        // Ocultar ID de Actividad (Columna 0)
        tblMisActividades.getColumnModel().getColumn(0).setMinWidth(0);
        tblMisActividades.getColumnModel().getColumn(0).setMaxWidth(0);
        tblMisActividades.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Ocultar ID de Ubicación 
        tblMisActividades.getColumnModel().getColumn(7).setMinWidth(0);
        tblMisActividades.getColumnModel().getColumn(7).setMaxWidth(0);
        tblMisActividades.getColumnModel().getColumn(7).setPreferredWidth(0);

        // --- AJUSTES VISUALES ---
        tblMisActividades.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblMisActividades.getColumnModel().getColumn(3).setPreferredWidth(100);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        tblMisActividades.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblMisActividades.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tblMisActividades.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int fila = tblMisActividades.getSelectedRow();

                    if (fila != -1) {
                        int idActividad = Integer.parseInt(tblMisActividades.getModel().getValueAt(fila, 0).toString());

                        Controlador.ControladorActividad controlDetalle = new Controlador.ControladorActividad();
                        Object[] info = controlDetalle.obtenerDetallesCompletos(idActividad);

                        if (info != null) {
                            lblCalle1.setText("Calle 1: " + (info[0] != null ? info[0] : "N/A"));
                            lblCalle2.setText("Calle 2: " + (info[1] != null ? info[1] : "N/A"));
                            lblSentido.setText("Sentido: " + (info[2] != null ? info[2] : "N/A"));
                            lblTipo.setText("Tipo: " + (info[4] != null ? info[4] : "N/A"));
                            lblRuta.setText("Ruta: " + (info[5] != null ? info[5] : "Sin asignar"));
                            lblRC.setText("RC: " + (info[6] != null ? info[6] : "N/A"));
                            txtDescripcion.setText(info[3] != null ? info[3].toString() : "Sin descripción.");
                            String estadoActividad = tblMisActividades.getValueAt(fila, 6).toString().trim();
                            System.out.println("Seleccionada fila " + fila + " - Estado: [" + estadoActividad + "]");

                            gestionarBotones(estadoActividad);
                        }
                    }
                }
            }
        });
    }

    private void gestionarBotones(String estado) {
        String est = estado.trim().toUpperCase();
        if (est.equals("PENDIENTE")) {
            btnEmpezar.setEnabled(true);
            btnFinalizar.setEnabled(false);
        } else if (est.equals("EN_PROCESO") || est.equals("EN PROCESO")) {
            btnEmpezar.setEnabled(false);
            btnFinalizar.setEnabled(true);
        } else {
            btnEmpezar.setEnabled(false);
            btnFinalizar.setEnabled(false);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblMisActividades = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        jbnIncidencias = new javax.swing.JButton();
        jbnVolver = new javax.swing.JButton();
        lblNombreUsuario = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblCalle1 = new javax.swing.JLabel();
        lblSentido = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        lblCalle2 = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        lblRuta = new javax.swing.JLabel();
        lblRC = new javax.swing.JLabel();
        btnEmpezar = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        tblMisActividades.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(41, 112, 172), null));
        tblMisActividades.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null}
            },
            new String [] {
                "Usuario", "Actividad", "Ubicacion", "Estado"
            }
        ));
        jScrollPane1.setViewportView(tblMisActividades);

        lblTitulo.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(68, 138, 25));
        lblTitulo.setText("REPORTE DE ACTIVIDADES");

        jbnIncidencias.setText("Nueva Incidencia");
        jbnIncidencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnIncidenciasActionPerformed(evt);
            }
        });

        jbnVolver.setText("Volver");
        jbnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnVolverActionPerformed(evt);
            }
        });
        jbnVolver.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbnVolverKeyPressed(evt);
            }
        });

        lblNombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreUsuario.setText("jLabel2");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Detalles de la Ubicación y Tarea"));

        lblCalle1.setText("jLabel1");

        lblSentido.setText("jLabel2");

        txtDescripcion.setEditable(false);
        txtDescripcion.setColumns(20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        jScrollPane2.setViewportView(txtDescripcion);

        lblCalle2.setText("jLabel3");

        lblTipo.setText("jLabel1");

        lblRuta.setText("jLabel2");

        lblRC.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblCalle1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTipo))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblSentido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRC))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblCalle2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRuta))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(15, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCalle1)
                    .addComponent(lblTipo))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCalle2)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblRuta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSentido)
                    .addComponent(lblRC))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnEmpezar.setText("Empezar actividad");
        btnEmpezar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpezarActionPerformed(evt);
            }
        });
        btnEmpezar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEmpezarKeyPressed(evt);
            }
        });

        btnFinalizar.setText("Finalizar actividad");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });
        btnFinalizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnFinalizarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(lblNombreUsuario))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(lblTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jbnIncidencias)
                                .addGap(82, 82, 82)
                                .addComponent(btnEmpezar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnFinalizar)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jbnVolver))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnIncidencias)
                    .addComponent(jbnVolver)
                    .addComponent(btnEmpezar)
                    .addComponent(btnFinalizar))
                .addGap(31, 31, 31))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnVolverActionPerformed
        // TODO add your handling code here:
        MenuPrincipal menu = new MenuPrincipal(userSesion);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbnVolverActionPerformed

    private void btnEmpezarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpezarActionPerformed
        // TODO add your handling code here:
        int fila = tblMisActividades.getSelectedRow();

        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona una actividad para iniciar.");
            return;
        }

        int idActividad = Integer.parseInt(tblMisActividades.getValueAt(fila, 0).toString());
        Controlador.ControladorActividad control = new Controlador.ControladorActividad();

        if (control.actualizarEstadoConHistorial(idActividad, "EN PROCESO", "PENDIENTE", userSesion.getIdUsuario())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Actividad marcada: EN PROCESO");
            btnEmpezar.setEnabled(false);
            btnFinalizar.setEnabled(true);
            cargarTabla();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al actualizar el estado e historial.");
        }
    }//GEN-LAST:event_btnEmpezarActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        // TODO add your handling code here:
        int fila = tblMisActividades.getSelectedRow();

        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona una actividad para finalizar.");
            return;
        }

        int idActividad = Integer.parseInt(tblMisActividades.getValueAt(fila, 0).toString());

        int confirmar = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que terminaste esta actividad?", "Confirmar Finalización",
                javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
            Controlador.ControladorActividad control = new Controlador.ControladorActividad();

            if (control.actualizarEstadoConHistorial(idActividad, "COMPLETADA", "EN PROCESO", userSesion.getIdUsuario())) {
                javax.swing.JOptionPane.showMessageDialog(this, "¡Actividad finalizada con éxito!");
                cargarTabla();

                lblCalle1.setText("Calle 1: ");
                lblCalle2.setText("Calle 2: ");
                txtDescripcion.setText("");

                btnFinalizar.setEnabled(false);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al finalizar el registro.");
            }
        }
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void jbnIncidenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnIncidenciasActionPerformed
        // TODO add your handling code here:
        int fila = tblMisActividades.getSelectedRow();

        if (fila != -1) {
            try {
                int idAct = Integer.parseInt(tblMisActividades.getModel().getValueAt(fila, 0).toString());
                String nombreAct = tblMisActividades.getModel().getValueAt(fila, 1).toString();
                String codigoUbi = tblMisActividades.getModel().getValueAt(fila, 3).toString();
                String c1 = lblCalle1.getText().replace("Calle 1: ", "").trim();
                String c2 = lblCalle2.getText().replace("Calle 2: ", "").trim();
                String direccion = c1 + " esq. " + c2;
                if (direccion.equals("esq.")) {
                    direccion = "Dirección no disponible";
                }
                int idUbi = Integer.parseInt(tblMisActividades.getModel().getValueAt(fila, 7).toString());
                Vista.Incidencias frmIncidencia = new Vista.Incidencias(userSesion, idAct, nombreAct, codigoUbi, direccion, idUbi);
                frmIncidencia.setVisible(true);
                this.dispose();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error de sistema: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una actividad para poder reportar la incidencia.");
        }
    }//GEN-LAST:event_jbnIncidenciasActionPerformed

    private void btnEmpezarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEmpezarKeyPressed
        // TODO add your handling code here:
        char tecla = (char) evt.getExtendedKeyCode();
        if (tecla == KeyEvent.VK_ENTER) {
            btnEmpezar.doClick();
            btnFinalizar.requestFocus();
        }
    }//GEN-LAST:event_btnEmpezarKeyPressed

    private void btnFinalizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnFinalizarKeyPressed
        // TODO add your handling code here:
        char tecla = (char) evt.getExtendedKeyCode();
        if (tecla == KeyEvent.VK_ENTER) {
            btnFinalizar.doClick();
            jbnVolver.requestFocus();
        }
    }//GEN-LAST:event_btnFinalizarKeyPressed

    private void jbnVolverKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbnVolverKeyPressed
        // TODO add your handling code here:
        char tecla = (char) evt.getExtendedKeyCode();
        if (tecla == KeyEvent.VK_ENTER) {
            jbnVolver.doClick();
        }
    }//GEN-LAST:event_jbnVolverKeyPressed

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
        java.awt.EventQueue.invokeLater(() -> new MisActividades(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmpezar;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbnIncidencias;
    private javax.swing.JButton jbnVolver;
    private javax.swing.JLabel lblCalle1;
    private javax.swing.JLabel lblCalle2;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblRC;
    private javax.swing.JLabel lblRuta;
    private javax.swing.JLabel lblSentido;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblMisActividades;
    private javax.swing.JTextArea txtDescripcion;
    // End of variables declaration//GEN-END:variables
}
