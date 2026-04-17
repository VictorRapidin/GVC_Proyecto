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
public class ReporteUsuarios extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReporteUsuarios.class.getName());

    /**
     * Creates new form ReporteUsuarios
     */
    ImagenFondo imgFondo;
    ClaseUsuario userSesion;
    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

    public ReporteUsuarios(ClaseUsuario usuario) {
        imgFondo = new ImagenFondo("src/imagenes/FondoFRM.jpg");
        this.setContentPane(imgFondo);
        userSesion = usuario;
        setSize(900, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
        cargarTablaUsuarios();
        cargarCombosUsuarios();
        lblNombreUsuario.setText("Bienvenido: " + userSesion.getNombre() + " " + userSesion.getApellido() + "  Rol: " + userSesion.getRol());
        String rol = userSesion.getRol();
    }

    private void cargarTablaUsuarios() {
        javax.swing.table.DefaultTableModel modeloUsuarios = new javax.swing.table.DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloUsuarios.addColumn("ID");          // Col 0 (Esta es la que vamos a ocultar)
        modeloUsuarios.addColumn("Nombre");      // Col 1
        modeloUsuarios.addColumn("Apellido");    // Col 2
        modeloUsuarios.addColumn("Correo");      // Col 3
        modeloUsuarios.addColumn("Rol");         // Col 4
        modeloUsuarios.addColumn("Estado");      // Col 5

        Controlador.ControladorUsuario control = new Controlador.ControladorUsuario();
        Modelo.ClaseUsuario[] usuarios = control.leerUsuarios();

        if (usuarios != null) {
            for (Modelo.ClaseUsuario usu : usuarios) {
                if (usu != null) {
                    Object[] fila = new Object[6];
                    fila[0] = usu.getIdUsuario(); // El dato sigue existiendo en el fondo
                    fila[1] = usu.getNombre();
                    fila[2] = usu.getApellido();
                    fila[3] = usu.getCorreo();
                    fila[4] = usu.getRol();
                    fila[5] = usu.isActivo() ? "Activo" : "Inactivo";

                    modeloUsuarios.addRow(fila);
                }
            }
        }

        tblUsuarios.setModel(modeloUsuarios);

        sorter = new javax.swing.table.TableRowSorter<>(modeloUsuarios);
        tblUsuarios.setRowSorter(sorter);

        tblUsuarios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

        // --- EL TRUCO PARA OCULTAR EL ID (Columna 0) ---
        tblUsuarios.getColumnModel().getColumn(0).setMinWidth(0);
        tblUsuarios.getColumnModel().getColumn(0).setMaxWidth(0);
        tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Ajustamos los anchos de las demás (le dimos el espacio del ID al correo)
        tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(120); // Nombre
        tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(120); // Apellido
        tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(220); // Correo (¡Más espacio!)
        tblUsuarios.getColumnModel().getColumn(4).setPreferredWidth(90);  // Rol
        tblUsuarios.getColumnModel().getColumn(5).setPreferredWidth(70);  // Estado

        // --- ESTILO CLÁSICO ---
        tblUsuarios.setRowHeight(22);
        tblUsuarios.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));

        javax.swing.table.JTableHeader header = tblUsuarios.getTableHeader();
        header.setBackground(null);
        header.setForeground(java.awt.Color.BLACK);
        header.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));

        tblUsuarios.setShowGrid(true);
        tblUsuarios.setGridColor(new java.awt.Color(204, 204, 204));

        tblUsuarios.revalidate();
        tblUsuarios.repaint();
    }

    private void aplicarFiltrosUsuarios() {
        if (sorter == null) {
            return;
        }

        java.util.List<javax.swing.RowFilter<Object, Object>> listaFiltros = new java.util.ArrayList<>();

        // 1. Buscador General (Busca por Nombre, Apellido o Correo)
        if (!txtBuscar.getText().trim().isEmpty()) {
            String texto = txtBuscar.getText().trim();
            // Le pasamos las columnas 1, 2 y 3 para que busque en cualquiera de esas
            listaFiltros.add(javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(texto), 1, 2, 3));
        }

        // 2. Filtro por Rol (Columna 4)
        if (cmbFiltroRol.getSelectedIndex() > 0) { // Mayor a 0 para ignorar "Todos"
            String texto = cmbFiltroRol.getSelectedItem().toString().trim();
            listaFiltros.add(javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(texto), 4));
        }

        // 3. Filtro por Estado (Columna 5)
        if (cmbFiltroEstado.getSelectedIndex() > 0) {
            String texto = cmbFiltroEstado.getSelectedItem().toString().trim();
            // Como es palabra exacta ("Activo" o "Inactivo"), la buscamos literal
            listaFiltros.add(javax.swing.RowFilter.regexFilter("^" + texto + "$", 5));
        }

        // Aplicar a la tabla
        if (listaFiltros.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(javax.swing.RowFilter.andFilter(listaFiltros));
        }
    }

    private void cargarCombosUsuarios() {
        // 1. Limpiamos por si tenían basura del modo diseño
        cmbFiltroRol.removeAllItems();
        cmbFiltroEstado.removeAllItems();

        // 2. Agregamos las opciones principales por defecto
        cmbFiltroRol.addItem("Todos");
        cmbFiltroEstado.addItem("Todos");

        // Los estados siempre son fijos, así que los ponemos a mano
        cmbFiltroEstado.addItem("Activo");
        cmbFiltroEstado.addItem("Inactivo");

        // 3. Extraemos los Roles dinámicamente directo de la tabla
        java.util.HashSet<String> rolesUnicos = new java.util.HashSet<>();

        // Recorremos la tabla que ya cargamos antes y sacamos la columna 4 (Rol)
        for (int i = 0; i < tblUsuarios.getRowCount(); i++) {
            rolesUnicos.add(tblUsuarios.getValueAt(i, 4).toString());
        }

        // 4. Metemos los roles que encontramos al combo
        for (String rol : rolesUnicos) {
            cmbFiltroRol.addItem(rol);
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
        tblUsuarios = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        cmbFiltroRol = new javax.swing.JComboBox<>();
        cmbFiltroEstado = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 138, 25));
        jLabel1.setText("REPORTE DE USUARIOS");

        jbnVolver.setText("Volver");
        jbnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnVolverActionPerformed(evt);
            }
        });

        lblNombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreUsuario.setText("jLabel1");

        tblUsuarios.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(41, 112, 172), null));
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
                "ID_USUARIO", "NOMBRE", "APELLIDO", "ROL"
            }
        ));
        jScrollPane2.setViewportView(tblUsuarios);

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

        cmbFiltroRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroRolActionPerformed(evt);
            }
        });

        cmbFiltroEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroEstadoActionPerformed(evt);
            }
        });

        jLabel2.setText("Filtro estado");

        jLabel3.setText("Filtro rol");

        jLabel4.setText("Filtro nombre");

        jButton1.setText("Dar de baja");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbFiltroRol, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(142, 142, 142)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(125, 125, 125))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(226, 226, 226)
                                .addComponent(lblNombreUsuario))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(72, 72, 72)
                .addComponent(jButton1)
                .addGap(67, 67, 67)
                .addComponent(jbnVolver)
                .addGap(306, 306, 306))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltroRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnVolver)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(25, 25, 25))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        aplicarFiltrosUsuarios();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void cmbFiltroEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroEstadoActionPerformed
        // TODO add your handling code here:
        aplicarFiltrosUsuarios();
    }//GEN-LAST:event_cmbFiltroEstadoActionPerformed

    private void cmbFiltroRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroRolActionPerformed
        // TODO add your handling code here:
        aplicarFiltrosUsuarios();
    }//GEN-LAST:event_cmbFiltroRolActionPerformed

    private void jbnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnVolverActionPerformed
        // TODO add your handling code here:
        MenuReportes menuR = new MenuReportes(userSesion);
        menuR.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jbnVolverActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int fila = tblUsuarios.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un usuario de la lista.");
            return;
        }

        int id = (int) tblUsuarios.getModel().getValueAt(tblUsuarios.convertRowIndexToModel(fila), 0);
        String nombre = tblUsuarios.getModel().getValueAt(tblUsuarios.convertRowIndexToModel(fila), 1).toString();

        int confirmar = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de dar de baja a " + nombre + "?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
            Controlador.ControladorUsuario control = new Controlador.ControladorUsuario();
            Modelo.ClaseUsuario[] todos = control.leerUsuarios();
            Modelo.ClaseUsuario usuarioAEditar = null;
            for (Modelo.ClaseUsuario u : todos) {
                if (u.getIdUsuario() == id) {
                    usuarioAEditar = u;
                    break;
                }
            }

            if (usuarioAEditar != null) {
                usuarioAEditar.setActivo(false);
                if (control.actualizarUsuario(usuarioAEditar)) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Usuario desactivado.");
                    cargarTablaUsuarios();
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:                                        
        int fila = tblUsuarios.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un usuario para editar.");
            return;
        }

        Modelo.ClaseUsuario usuarioSeleccionado = new Modelo.ClaseUsuario();
        int modelRow = tblUsuarios.convertRowIndexToModel(fila);

        usuarioSeleccionado.setIdUsuario((int) tblUsuarios.getModel().getValueAt(modelRow, 0));
        usuarioSeleccionado.setNombre(tblUsuarios.getModel().getValueAt(modelRow, 1).toString());
        usuarioSeleccionado.setApellido(tblUsuarios.getModel().getValueAt(modelRow, 2).toString());
        usuarioSeleccionado.setCorreo(tblUsuarios.getModel().getValueAt(modelRow, 3).toString());
        usuarioSeleccionado.setRol(tblUsuarios.getModel().getValueAt(modelRow, 4).toString());
        usuarioSeleccionado.setActivo(tblUsuarios.getModel().getValueAt(modelRow, 5).toString().equals("Activo"));

        Usuarios ventanaEdit = new Usuarios(usuarioSeleccionado);
        ventanaEdit.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jButton2ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ReporteUsuarios(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbFiltroEstado;
    private javax.swing.JComboBox<String> cmbFiltroRol;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbnVolver;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
