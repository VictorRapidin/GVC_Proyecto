/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorActividad;
import Modelo.ClaseUsuario;
import Modelo.ImagenFondo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author ghust
 */
public class ReporteAct extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReporteAct.class.getName());

    /**
     * Creates new form ReporteActividades
     */
    ClaseUsuario userSesion;
    ImagenFondo imgFondo;
    private TableRowSorter<DefaultTableModel> sorter;
    
    public ReporteAct(ClaseUsuario usuario) {
         userSesion = usuario;
        imgFondo = new ImagenFondo("src/imagenes/FondoFRM.jpg");
        this.setContentPane(imgFondo);
        initComponents();
        cargarCombosFiltros();
        cargarTabla();
        setSize(900, 550);
        setLocationRelativeTo(null);
        setResizable(false);
       
        lblNombreUsuario.setText("Bienvenido: " + userSesion.getNombre() + " " + userSesion.getApellido() + "  Rol: " + userSesion.getRol());
    }
   
    
    ReporteAct(ClaseUsuario userSesion, ControladorActividad ctrlAct) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String obtenerNombreUsuario(int idBusqueda, Modelo.ClaseUsuario[] listaUsuarios) {
    if (idBusqueda == 0 || listaUsuarios == null) {
        return "Ninguno";
    }
    for (Modelo.ClaseUsuario u : listaUsuarios) {
        if (u.getIdUsuario() == idBusqueda) {
            return u.getNombre();
        }
    }
    return "Desconocido";
}

private String obtenerNombreCampania(int idBusqueda, Modelo.ClaseCampania[] listaCampanias) {
    if (idBusqueda == 0 || listaCampanias == null) return "Sin Campaña";
    for (Modelo.ClaseCampania c : listaCampanias) {
        if (c.getIdCampania() == idBusqueda) {
            return c.getNombre();
        }
    }
    return "Desconocida";
}

private String obtenerNombreUbicacion(int idBusqueda, Modelo.ClaseUbicaciones[] listaUbicaciones) {
    if (idBusqueda == 0 || listaUbicaciones == null) return "Sin Ubicación";
    for (Modelo.ClaseUbicaciones u : listaUbicaciones) {
        if (u.getIdUbicacion() == idBusqueda) {
            String c1 = (u.getCalle1() != null) ? u.getCalle1() : "S/C";
            String c2 = (u.getCalle2() != null && !u.getCalle2().isEmpty()) ? " y " + u.getCalle2() : "";
            return c1 + c2;
        }
    }
    return "Desconocida";
}

private void cargarTabla() {
    DefaultTableModel modelo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Agregamos las columnas (Sin ID y Sin Fecha)
    modelo.addColumn("Actividad");    // Columna 0
    modelo.addColumn("Campaña");      // Columna 1
    modelo.addColumn("Ubicación");    // Columna 2
    modelo.addColumn("Asignado(s)");  // Columna 3
    modelo.addColumn("Estado");       // Columna 4
    modelo.addColumn("Prioridad");    // Columna 5

    Controlador.ControladorActividad controlAct = new Controlador.ControladorActividad();
    Modelo.ClaseActividad[] actividades = controlAct.leerActividades();
    
    Controlador.ControladorUsuario controlUsu = new Controlador.ControladorUsuario();
    Modelo.ClaseUsuario[] usuarios = controlUsu.leerUsuarios();
    
    Controlador.ControladorCampanias controlCamp = new Controlador.ControladorCampanias();
    Modelo.ClaseCampania[] campanias = controlCamp.leerCampanias();
    
    Controlador.ControladorUbicaciones controlUbi = new Controlador.ControladorUbicaciones();
    Modelo.ClaseUbicaciones[] ubicaciones = controlUbi.leerUbicaciones();

    if (actividades != null) {
        for (Modelo.ClaseActividad act : actividades) {
            if (act != null) {
                
                // Filtro por Rol (Seguridad)
                if ("ASOCIADO".equalsIgnoreCase(userSesion.getRol())) {
                    boolean esSuActividad = (act.getIdAsignado() == userSesion.getIdUsuario() || 
                                             act.getIdAsignado2() == userSesion.getIdUsuario());
                    if (!esSuActividad) {
                        continue; 
                    }
                }

                // Llenamos la fila con 6 elementos (0 al 5)
                Object[] fila = new Object[6]; 
                
                fila[0] = act.getNombreActividad();
                fila[1] = obtenerNombreCampania(act.getIdCampania(), campanias);
                fila[2] = obtenerNombreUbicacion(act.getIdUbicacion(), ubicaciones);
                
                String asig1 = obtenerNombreUsuario(act.getIdAsignado(), usuarios);
                String asig2 = obtenerNombreUsuario(act.getIdAsignado2(), usuarios);
                fila[3] = (asig2 == null || asig2.equals("Ninguno")) ? asig1 : asig1 + " / " + asig2;
                
                fila[4] = act.getEstado();
                fila[5] = act.getPrioridad();
                
                modelo.addRow(fila);
            }
        }
    }

    tblActividades.setModel(modelo);

    tblActividades.getColumnModel().getColumn(0).setPreferredWidth(130);
    tblActividades.getColumnModel().getColumn(1).setPreferredWidth(110);

    tblActividades.getColumnModel().getColumn(2).setPreferredWidth(320);
 
    tblActividades.getColumnModel().getColumn(3).setPreferredWidth(250);

    tblActividades.getColumnModel().getColumn(4).setPreferredWidth(100);
    tblActividades.getColumnModel().getColumn(5).setPreferredWidth(100);

    sorter = new TableRowSorter<>(modelo);
    tblActividades.setRowSorter(sorter);
    
    tblActividades.revalidate();
    tblActividades.repaint();
}
private void aplicarFiltros() {
    if (sorter == null) return; 

    List<RowFilter<Object, Object>> listaFiltros = new ArrayList<>();
    if (cmbFiltroActividad.getSelectedIndex() > 0) {
        String texto = cmbFiltroActividad.getSelectedItem().toString();
        listaFiltros.add(RowFilter.regexFilter("(?i)" + texto, 0)); 
    }
    if (cmbFiltroCampania.getSelectedIndex() > 0) {
        String texto = cmbFiltroCampania.getSelectedItem().toString();
        listaFiltros.add(RowFilter.regexFilter("(?i)" + texto, 1));
    }
    if (cmbFiltroUsuario.getSelectedIndex() >= 0) {
        String texto = cmbFiltroUsuario.getSelectedItem().toString();
        if (!texto.equalsIgnoreCase("Todos")) {
            listaFiltros.add(RowFilter.regexFilter("(?i)" + texto, 3));
        }
    }
    if (cmbFiltroEstado.getSelectedIndex() > 0) {
        String texto = cmbFiltroEstado.getSelectedItem().toString();
        listaFiltros.add(RowFilter.regexFilter("(?i)" + texto, 5));
    }

    if (listaFiltros.isEmpty()) {
        sorter.setRowFilter(null); 
    } else {
        sorter.setRowFilter(RowFilter.andFilter(listaFiltros));
    }
}

private void cargarCombosFiltros() {
    cmbFiltroActividad.removeAllItems();
    cmbFiltroCampania.removeAllItems();
    cmbFiltroUsuario.removeAllItems();
    cmbFiltroEstado.removeAllItems();

    cmbFiltroActividad.addItem("Todas");
    cmbFiltroCampania.addItem("Todas");
    cmbFiltroEstado.addItem("Todos");

    if ("ASOCIADO".equalsIgnoreCase(userSesion.getRol())) {
        cmbFiltroUsuario.addItem(userSesion.getNombre());
        cmbFiltroUsuario.setEnabled(false); 
    } else {
        cmbFiltroUsuario.addItem("Todos");
        cmbFiltroUsuario.setEnabled(true);
        
        Controlador.ControladorUsuario controlUsu = new Controlador.ControladorUsuario();
        Modelo.ClaseUsuario[] usuarios = controlUsu.leerUsuarios();
        if (usuarios != null) {
            for (Modelo.ClaseUsuario usu : usuarios) {
                if (usu != null && "ASOCIADO".equalsIgnoreCase(usu.getRol())) {
                    cmbFiltroUsuario.addItem(usu.getNombre());
                }
            }
        }
    }

    Controlador.ControladorActividad controlAct = new Controlador.ControladorActividad();
    Modelo.ClaseActividad[] actividades = controlAct.leerActividades();
    java.util.HashSet<String> nombresActividades = new java.util.HashSet<>();
    java.util.HashSet<String> estadosSet = new java.util.HashSet<>();
    
    if (actividades != null) {
        for (Modelo.ClaseActividad act : actividades) {
            if (act != null) {
                nombresActividades.add(act.getNombreActividad());
                estadosSet.add(act.getEstado());
            }
        }
    }

    for (String nombre : nombresActividades) cmbFiltroActividad.addItem(nombre);
    for (String est : estadosSet) cmbFiltroEstado.addItem(est);
    Controlador.ControladorCampanias controlCamp = new Controlador.ControladorCampanias();
    Modelo.ClaseCampania[] campanias = controlCamp.leerCampanias();
    if (campanias != null) {
        for (Modelo.ClaseCampania camp : campanias) {
            if (camp != null) cmbFiltroCampania.addItem(camp.getNombre());
        }
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
        tblActividades = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jbnIncidencias = new javax.swing.JButton();
        jbnVolver = new javax.swing.JButton();
        lblNombreUsuario = new javax.swing.JLabel();
        cmbFiltroActividad = new javax.swing.JComboBox<>();
        cmbFiltroCampania = new javax.swing.JComboBox<>();
        cmbFiltroUsuario = new javax.swing.JComboBox<>();
        cmbFiltroEstado = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        tblActividades.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(41, 112, 172), null));
        tblActividades.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblActividades);

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 138, 25));
        jLabel1.setText("REPORTE DE ACTIVIDADES");

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

        lblNombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreUsuario.setText("jLabel2");

        cmbFiltroActividad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroActividadActionPerformed(evt);
            }
        });

        cmbFiltroCampania.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroCampania.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroCampaniaActionPerformed(evt);
            }
        });

        cmbFiltroUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroUsuarioActionPerformed(evt);
            }
        });

        cmbFiltroEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltroEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroEstadoActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(68, 138, 25));
        jLabel2.setText("Filtro actividad");

        jLabel3.setForeground(new java.awt.Color(68, 138, 25));
        jLabel3.setText("Filtro campañas");

        jLabel4.setForeground(new java.awt.Color(68, 138, 25));
        jLabel4.setText("Filtro usuario");

        jLabel5.setForeground(new java.awt.Color(68, 138, 25));
        jLabel5.setText("Filtro estado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(lblNombreUsuario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addComponent(jbnIncidencias)
                                .addGap(65, 65, 65)
                                .addComponent(jbnVolver))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(152, 152, 152)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(87, 87, 87)
                                        .addComponent(cmbFiltroActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(55, 55, 55)
                                        .addComponent(cmbFiltroCampania, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(104, 104, 104)
                                        .addComponent(jLabel2)
                                        .addGap(88, 88, 88)
                                        .addComponent(jLabel3)))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel5)))
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbFiltroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(jLabel4)))))
                        .addGap(0, 173, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbFiltroCampania, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbFiltroActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbFiltroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbnVolver)
                            .addComponent(jbnIncidencias, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnVolverActionPerformed
        // TODO add your handling code here:
        MenuPrincipal menu = new MenuPrincipal(this.userSesion);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbnVolverActionPerformed

    private void cmbFiltroActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroActividadActionPerformed
        // TODO add your handling code here:
        aplicarFiltros();
    }//GEN-LAST:event_cmbFiltroActividadActionPerformed

    private void cmbFiltroCampaniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroCampaniaActionPerformed
        // TODO add your handling code here:
        aplicarFiltros();
    }//GEN-LAST:event_cmbFiltroCampaniaActionPerformed

    private void cmbFiltroUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroUsuarioActionPerformed
        // TODO add your handling code here:
        aplicarFiltros();
    }//GEN-LAST:event_cmbFiltroUsuarioActionPerformed

    private void cmbFiltroEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroEstadoActionPerformed
        // TODO add your handling code here:
        aplicarFiltros();
    }//GEN-LAST:event_cmbFiltroEstadoActionPerformed

    private void jbnIncidenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnIncidenciasActionPerformed
        // TODO add your handling code here:
       int filaSeleccionada = tblActividades.getSelectedRow();
    
    if (filaSeleccionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar una actividad de la tabla.");
        return;
    }

    int filaModelo = tblActividades.convertRowIndexToModel(filaSeleccionada);
    Controlador.ControladorActividad controlAct = new Controlador.ControladorActividad();
    Modelo.ClaseActividad[] listaAct = controlAct.leerActividades();
    String nombreAct = tblActividades.getModel().getValueAt(filaModelo, 0).toString();
    String direccion = tblActividades.getModel().getValueAt(filaModelo, 2).toString();
    Modelo.ClaseActividad actSeleccionada = null;
    for(Modelo.ClaseActividad a : listaAct){
        if(a.getNombreActividad().equals(nombreAct)){
            actSeleccionada = a;
            break;
        }
    }

    if (actSeleccionada != null) {
        int idAct = actSeleccionada.getIdActividad();
        int idUbi = actSeleccionada.getIdUbicacion();
        Controlador.ControladorUbicaciones ctrlUbi = new Controlador.ControladorUbicaciones();
        String codigoUbi = "U-" + idUbi;
        Incidencias ventanaInc = new Incidencias(userSesion, idAct, nombreAct, codigoUbi, direccion, idUbi);
        ventanaInc.setVisible(true);
        this.dispose();
    }
    }//GEN-LAST:event_jbnIncidenciasActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ReporteAct(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbFiltroActividad;
    private javax.swing.JComboBox<String> cmbFiltroCampania;
    private javax.swing.JComboBox<String> cmbFiltroEstado;
    private javax.swing.JComboBox<String> cmbFiltroUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbnIncidencias;
    private javax.swing.JButton jbnVolver;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JTable tblActividades;
    // End of variables declaration//GEN-END:variables
}
