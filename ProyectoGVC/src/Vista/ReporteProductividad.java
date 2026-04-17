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
public class ReporteProductividad extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReporteProductividad.class.getName());

    /**
     * Creates new form ReporteActividades
     */
    ClaseUsuario userSesion;
    ImagenFondo imgFondo;
    private javax.swing.table.DefaultTableModel modeloProductividad;
    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

    public ReporteProductividad(ClaseUsuario usuario) {
        userSesion = usuario;
        imgFondo = new ImagenFondo("src/imagenes/FondoFRM.jpg");
        this.setContentPane(imgFondo);
        initComponents();
        cargarTabla();
        cargarCombosFiltros();
        if (tblProductividad.getColumnCount() > 5) {
            aplicarRenderizadorColores();
        }
        setSize(900, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        lblNombreUsuario.setText("Bienvenido: " + userSesion.getNombre() + " " + userSesion.getApellido() + "  Rol: " + userSesion.getRol());
    }

    private void diseñarTabla() {
        tblProductividad.setRowHeight(30); // Filas más altas para que luzca
        tblProductividad.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Personalizar el encabezado de la tabla
        JTableHeader header = tblProductividad.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(new Color(26, 45, 69)); // Letras azules
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Líneas de la cuadrícula como en tu imagen
        tblProductividad.setGridColor(new Color(200, 200, 200));
        tblProductividad.setShowGrid(true);
    }

    public void aplicarRenderizadorColores() {
        if (tblProductividad.getColumnCount() > 5) {
            tblProductividad.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
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

    private void cargarTabla() {
        modeloProductividad = new javax.swing.table.DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloProductividad.addColumn("Actividad");       // Columna 0
        modeloProductividad.addColumn("Responsable");     // Columna 1
        modeloProductividad.addColumn("Fecha Creada");    // Columna 2
        modeloProductividad.addColumn("En Proceso");      // Columna 3
        modeloProductividad.addColumn("Finalizada");      // Columna 4
        modeloProductividad.addColumn("Tiempo Trabajo");  // Columna 5

        Controlador.ControladorActividad control = new Controlador.ControladorActividad();
        Object[][] datos = control.leerReporteProductividad("Todos", "Todos");

        if (datos != null) {
            for (Object[] fila : datos) {
                modeloProductividad.addRow(fila);
            }
        }

        tblProductividad.setModel(modeloProductividad);

        sorter = new javax.swing.table.TableRowSorter<>(modeloProductividad);
        tblProductividad.setRowSorter(sorter);

       tblProductividad.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

        // --- ANCHOS DE COLUMNA MÁS APRETADOS ---
        tblProductividad.getColumnModel().getColumn(0).setPreferredWidth(80);  // Actividad
        tblProductividad.getColumnModel().getColumn(1).setPreferredWidth(120); // Responsable
        tblProductividad.getColumnModel().getColumn(2).setPreferredWidth(100); // Fecha Creada
        tblProductividad.getColumnModel().getColumn(3).setPreferredWidth(100); // Inicio
        tblProductividad.getColumnModel().getColumn(4).setPreferredWidth(100); // Fin
        tblProductividad.getColumnModel().getColumn(5).setPreferredWidth(75);  // Tiempo

        // --- LETRA Y FILA NORMALES (Como lo tenías antes) ---
        tblProductividad.setRowHeight(22);
        tblProductividad.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));

        // Encabezado
        javax.swing.table.JTableHeader header = tblProductividad.getTableHeader();
        header.setBackground(null);
        header.setForeground(java.awt.Color.BLACK);
        header.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));

        tblProductividad.setShowGrid(true);
        tblProductividad.setGridColor(new java.awt.Color(204, 204, 204));

        tblProductividad.revalidate();
        tblProductividad.repaint();
    }

    private void cargarCombosFiltros() {
        cmbFiltroActividad.removeAllItems();
        cmbFiltroUsuario.removeAllItems();

        cmbFiltroActividad.addItem("Todas");
        cmbFiltroUsuario.addItem("Todos");

        // Usamos HashSet para que los nombres no se repitan en la lista desplegable
        java.util.HashSet<String> actividadesUnicas = new java.util.HashSet<>();
        java.util.HashSet<String> usuariosUnicos = new java.util.HashSet<>();

        // Leemos directo del modelo de la tabla que acabamos de cargar
        for (int i = 0; i < modeloProductividad.getRowCount(); i++) {
            actividadesUnicas.add(modeloProductividad.getValueAt(i, 0).toString()); // Col 0: Actividad
            usuariosUnicos.add(modeloProductividad.getValueAt(i, 1).toString());    // Col 1: Responsable
        }

        // Llenamos los combos
        for (String act : actividadesUnicas) {
            cmbFiltroActividad.addItem(act);
        }
        for (String usu : usuariosUnicos) {
            cmbFiltroUsuario.addItem(usu);
        }
    }

    private void aplicarFiltros() {
        System.out.println("--- INICIANDO FILTRADO ---");

        if (sorter == null) {
            System.out.println("ERROR: Sorter no está listo todavía.");
            return;
        }

        java.util.List<javax.swing.RowFilter<Object, Object>> listaFiltros = new java.util.ArrayList<>();

        // 1. Filtro Actividad
        if (cmbFiltroActividad.getSelectedIndex() > 0) {
            String texto = cmbFiltroActividad.getSelectedItem().toString().trim();
            // Pattern.quote asegura que si hay espacios o símbolos, busque exactamente eso
            listaFiltros.add(javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(texto), 0));
            System.out.println("Filtro Actividad atrapado: [" + texto + "]");
        }

        // 2. Filtro Usuario
        if (cmbFiltroUsuario.getSelectedIndex() > 0) {
            String texto = cmbFiltroUsuario.getSelectedItem().toString().trim();
            listaFiltros.add(javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(texto), 1));
            System.out.println("Filtro Usuario atrapado: [" + texto + "]");
        }

        // 3. Filtro Fecha de Creación
        if (dateChooserCreacion != null && dateChooserCreacion.getDate() != null) {
            String fechaTexto = new java.text.SimpleDateFormat("dd/MM/yyyy").format(dateChooserCreacion.getDate());
            listaFiltros.add(javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(fechaTexto), 2));
            System.out.println("Filtro Creacion atrapado: [" + fechaTexto + "]");
        }

        // 4. Filtro Fecha Fin
        if (dateChooserFin != null && dateChooserFin.getDate() != null) {
            String fechaTexto = new java.text.SimpleDateFormat("dd/MM/yyyy").format(dateChooserFin.getDate());
            listaFiltros.add(javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(fechaTexto), 4));
            System.out.println("Filtro Fin atrapado: [" + fechaTexto + "]");
        }

        System.out.println("Filtros listos para aplicar: " + listaFiltros.size());

        // 5. Aplicar a la tabla
        try {
            if (listaFiltros.isEmpty()) {
                sorter.setRowFilter(null);
                System.out.println("Limpiando tabla (Mostrando todo)");
            } else {
                sorter.setRowFilter(javax.swing.RowFilter.andFilter(listaFiltros));
                System.out.println("¡Filtro AND inyectado en la tabla!");
            }
        } catch (Exception e) {
            System.out.println("¡TRONÓ AL APLICAR EL FILTRO! Error: " + e.getMessage());
        }

        System.out.println("--------------------------");
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
        tblProductividad = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jbnVolver = new javax.swing.JButton();
        lblNombreUsuario = new javax.swing.JLabel();
        cmbFiltroUsuario = new javax.swing.JComboBox<>();
        cmbFiltroActividad = new javax.swing.JComboBox<>();
        dateChooserCreacion = new com.toedter.calendar.JDateChooser();
        dateChooserFin = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        tblProductividad.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(41, 112, 172), null));
        tblProductividad.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblProductividad);

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 138, 25));
        jLabel1.setText("REPORTE DE PRODUCTIVIDAD");

        jbnVolver.setText("Volver");
        jbnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnVolverActionPerformed(evt);
            }
        });

        lblNombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreUsuario.setText("jLabel2");

        cmbFiltroUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroUsuarioActionPerformed(evt);
            }
        });

        cmbFiltroActividad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroActividadActionPerformed(evt);
            }
        });

        dateChooserCreacion.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateChooserCreacionPropertyChange(evt);
            }
        });

        dateChooserFin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateChooserFinPropertyChange(evt);
            }
        });

        jLabel2.setText("Filtro de usuario");

        jLabel3.setText("Filtro de actividad");

        jLabel4.setText("Filtro fecha de finalización");

        jLabel5.setText("Filtro fecha de creacion");

        jButton1.setText("Limpiar filtros");
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
                            .addComponent(cmbFiltroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbFiltroActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(dateChooserFin, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 906, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(jButton1)
                        .addGap(58, 58, 58)
                        .addComponent(jbnVolver))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(jLabel1)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbFiltroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbFiltroActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateChooserCreacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateChooserFin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnVolver)
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

    private void cmbFiltroUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroUsuarioActionPerformed
        // TODO add your handling code here:}
        aplicarFiltros();
    }//GEN-LAST:event_cmbFiltroUsuarioActionPerformed

    private void cmbFiltroActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroActividadActionPerformed
        // TODO add your handling code here:
        aplicarFiltros();
    }//GEN-LAST:event_cmbFiltroActividadActionPerformed

    private void dateChooserCreacionPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateChooserCreacionPropertyChange
        // TODO add your handling code here:
        if ("date".equals(evt.getPropertyName())) {
            aplicarFiltros();
        }
    }//GEN-LAST:event_dateChooserCreacionPropertyChange

    private void dateChooserFinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateChooserFinPropertyChange
        // TODO add your handling code here:
        if ("date".equals(evt.getPropertyName())) {
            aplicarFiltros();
        }
    }//GEN-LAST:event_dateChooserFinPropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (dateChooserCreacion != null) {
        dateChooserCreacion.setDate(null);
    }
    if (dateChooserFin != null) {
        dateChooserFin.setDate(null);
    }

    if (cmbFiltroActividad.getItemCount() > 0) {
        cmbFiltroActividad.setSelectedIndex(0);
    }
    if (cmbFiltroUsuario.getItemCount() > 0) {
        cmbFiltroUsuario.setSelectedIndex(0);
    }

    // 3. Quitamos el filtro directamente de la tabla
    if (sorter != null) {
        sorter.setRowFilter(null);
    }

        
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
        java.awt.EventQueue.invokeLater(() -> new ReporteProductividad(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbFiltroActividad;
    private javax.swing.JComboBox<String> cmbFiltroUsuario;
    private com.toedter.calendar.JDateChooser dateChooserCreacion;
    private com.toedter.calendar.JDateChooser dateChooserFin;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbnVolver;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JTable tblProductividad;
    // End of variables declaration//GEN-END:variables
}
