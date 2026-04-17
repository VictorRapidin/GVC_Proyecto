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
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author ghust
 */
public class ReporteCampania extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReporteCampania.class.getName());

    /**
     * Creates new form ReporteActividades
     */
    ClaseUsuario userSesion;
    ImagenFondo imgFondo;
    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

    public ReporteCampania(ClaseUsuario usuario) {
        userSesion = usuario;
        imgFondo = new ImagenFondo("src/imagenes/FondoFRM.jpg");
        this.setContentPane(imgFondo);
        initComponents();
        cargarTablaCampanias();
        cargarCombosCampanias();
        if (tblCampanias.getColumnCount() > 5) {
            aplicarRenderizadorColores();
        }
        setSize(900, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        lblNombreUsuario.setText("Bienvenido: " + userSesion.getNombre() + " " + userSesion.getApellido() + "  Rol: " + userSesion.getRol());
    }

    private void diseñarTabla() {
        tblCampanias.setRowHeight(30); // Filas más altas para que luzca
        tblCampanias.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JTableHeader header = tblCampanias.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(new Color(26, 45, 69));
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblCampanias.setGridColor(new Color(200, 200, 200));
        tblCampanias.setShowGrid(true);
    }

    public void aplicarRenderizadorColores() {
        if (tblCampanias.getColumnCount() > 5) {
            tblCampanias.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    c.setHorizontalAlignment(SwingConstants.CENTER);
                    c.setFont(new Font("Segoe UI", Font.BOLD, 12));

                    if (value != null && value.toString().contains("min")) {
                        try {
                            int minutos = Integer.parseInt(value.toString().replace(" min", "").trim());
                            if (minutos > 60) {
                                c.setForeground(Color.RED);
                            } else {
                                c.setForeground(new Color(68, 138, 25)); // Verde IMU
                            }
                        } catch (Exception e) {
                            c.setForeground(Color.BLACK);
                        }
                    }
                    return c;
                }
            });
        } else {
            System.out.println("Advertencia: La columna 5 aún no existe en la tabla.");
        }
    }

    private void cargarTablaCampanias() {
        javax.swing.table.DefaultTableModel modeloCampanias = new javax.swing.table.DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloCampanias.addColumn("ID");          // Col 0 (Se va a ocultar)
        modeloCampanias.addColumn("Marca");      // Col 1
        modeloCampanias.addColumn("Versión"); // Col 2
        modeloCampanias.addColumn("F. Inicio");   // Col 3
        modeloCampanias.addColumn("F. Fin");      // Col 4
        modeloCampanias.addColumn("Estado");      // Col 5

        Controlador.ControladorCampanias control = new Controlador.ControladorCampanias();
        Modelo.ClaseCampania[] campanias = control.leerCampanias();

        if (campanias != null) {
            for (Modelo.ClaseCampania camp : campanias) {
                if (camp != null) {
                    Object[] fila = new Object[6];
                    fila[0] = camp.getIdCampania();
                    fila[1] = camp.getNombre();
                    fila[2] = camp.getDescripcion();
                    fila[3] = camp.getFechaInicio();
                    fila[4] = camp.getFechaFin();
                    fila[5] = camp.getEstado();

                    modeloCampanias.addRow(fila);
                }
            }
        }

        tblCampanias.setModel(modeloCampanias);

        sorter = new javax.swing.table.TableRowSorter<>(modeloCampanias);
        tblCampanias.setRowSorter(sorter);

        tblCampanias.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblCampanias.getColumnModel().getColumn(0).setMinWidth(0);
        tblCampanias.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCampanias.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblCampanias.getColumnModel().getColumn(1).setPreferredWidth(130); // Nombre
        tblCampanias.getColumnModel().getColumn(2).setPreferredWidth(200); // Descripción
        tblCampanias.getColumnModel().getColumn(3).setPreferredWidth(90);  // F. Inicio
        tblCampanias.getColumnModel().getColumn(4).setPreferredWidth(90);  // F. Fin
        tblCampanias.getColumnModel().getColumn(5).setPreferredWidth(80);  // Estado
        tblCampanias.setRowHeight(22);
        tblCampanias.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));

        javax.swing.table.JTableHeader header = tblCampanias.getTableHeader();
        header.setBackground(null);
        header.setForeground(java.awt.Color.BLACK);
        header.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));

        tblCampanias.setShowGrid(true);
        tblCampanias.setGridColor(new java.awt.Color(204, 204, 204));

        tblCampanias.revalidate();
        tblCampanias.repaint();
    }

    private void cargarCombosCampanias() {
        cmbFiltroEstado.removeAllItems();
        cmbFiltroEstado.addItem("Todos");
        cmbFiltroEstado.addItem("PLANEADA");
        cmbFiltroEstado.addItem("ACTIVA");
        cmbFiltroEstado.addItem("FINALIZADA");
        cmbFiltroEstado.addItem("CANCELADA");
    }

    private void aplicarFiltrosCampanias() {
        if (sorter == null) {
            return;
        }

        java.util.List<javax.swing.RowFilter<Object, Object>> listaFiltros = new java.util.ArrayList<>();
        if (!txtBuscar.getText().trim().isEmpty()) {
            String texto = txtBuscar.getText().trim();
            listaFiltros.add(javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(texto), 1, 2));
        }
        if (cmbFiltroEstado.getSelectedIndex() > 0) {
            String texto = cmbFiltroEstado.getSelectedItem().toString().trim();
            listaFiltros.add(javax.swing.RowFilter.regexFilter("^" + texto + "$", 5));
        }
        if (listaFiltros.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(javax.swing.RowFilter.andFilter(listaFiltros));
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
        tblCampanias = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jbnVolver = new javax.swing.JButton();
        lblNombreUsuario = new javax.swing.JLabel();
        cmbFiltroEstado = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        tblCampanias.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(41, 112, 172), null));
        tblCampanias.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCampanias);

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 138, 25));
        jLabel1.setText("REPORTE DE CAMPAÑAS");

        jbnVolver.setText("Volver");
        jbnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnVolverActionPerformed(evt);
            }
        });

        lblNombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreUsuario.setText("jLabel2");

        cmbFiltroEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroEstadoActionPerformed(evt);
            }
        });

        jLabel2.setText("Filtro de estado");

        jLabel3.setText("Filtro general");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jButton2.setText("Cancelar campaña");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Editar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addGap(84, 84, 84)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(cmbFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 906, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(jButton1)
                        .addGap(63, 63, 63)
                        .addComponent(jButton2)
                        .addGap(68, 68, 68)
                        .addComponent(jbnVolver)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnVolver)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(19, 19, 19))
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

    private void cmbFiltroEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroEstadoActionPerformed
        // TODO add your handling code here:}
        aplicarFiltrosCampanias();
    }//GEN-LAST:event_cmbFiltroEstadoActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        aplicarFiltrosCampanias();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int fila = tblCampanias.getSelectedRow();
    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Selecciona una campaña de la lista.");
        return;
    }

    // 1. Obtener ID y Nombre (Columna 0 y 1)
    int modelRow = tblCampanias.convertRowIndexToModel(fila);
    int id = (int) tblCampanias.getModel().getValueAt(modelRow, 0);
    String nombre = tblCampanias.getModel().getValueAt(modelRow, 1).toString();

    int confirmar = javax.swing.JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de cancelar la campaña: " + nombre + "?", "Confirmar Cancelación", javax.swing.JOptionPane.YES_NO_OPTION);

    if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
        Controlador.ControladorCampanias control = new Controlador.ControladorCampanias();
        Modelo.ClaseCampania[] todas = control.leerCampanias();
        Modelo.ClaseCampania seleccionada = null;
        for(Modelo.ClaseCampania c : todas) {
            if(c.getIdCampania() == id) {
                seleccionada = c;
                break;
            }
        }

        if (seleccionada != null) {
            seleccionada.setEstado("CANCELADA");
            if (control.actualizarCampania(seleccionada)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Campaña cancelada exitosamente.");
                cargarTablaCampanias(); 
            }
        }
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int fila = tblCampanias.getSelectedRow();
    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Selecciona una campaña para editar.");
        return;
    }
    int modelRow = tblCampanias.convertRowIndexToModel(fila);
    Modelo.ClaseCampania camp = new Modelo.ClaseCampania();
    
    camp.setIdCampania((int) tblCampanias.getModel().getValueAt(modelRow, 0));
    camp.setNombre(tblCampanias.getModel().getValueAt(modelRow, 1).toString());
    camp.setDescripcion(tblCampanias.getModel().getValueAt(modelRow, 2).toString());
    camp.setFechaInicio(tblCampanias.getModel().getValueAt(modelRow, 3).toString());
    camp.setFechaFin(tblCampanias.getModel().getValueAt(modelRow, 4).toString());
    camp.setEstado(tblCampanias.getModel().getValueAt(modelRow, 5).toString());

    Campañas ventanaEdit = new Campañas(userSesion, camp);
    ventanaEdit.setVisible(true);
    this.dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ReporteCampania(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbFiltroEstado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbnVolver;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JTable tblCampanias;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
