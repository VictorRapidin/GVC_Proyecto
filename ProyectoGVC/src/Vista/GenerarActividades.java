/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorActividad;
import Controlador.ControladorCampanias;
import Controlador.ControladorMaterial;
import Controlador.ControladorUbicaciones;
import Controlador.ControladorUsuario;
import Modelo.ClaseActividad;
import Modelo.ClaseCampania;
import Modelo.ClaseMateriales;
import Modelo.ClaseUbicaciones;
import Modelo.ClaseUsuario;
import Modelo.ImagenFondo;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author porq
 */
  
public class GenerarActividades extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GenerarActividades.class.getName());

    /**
     * Creates new form GenerarActividades
     */
    private ClaseUsuario userSesion;
    private ClaseUsuario[] listaAsociados;   
    private ClaseCampania[] listaCampanias;   
    private ClaseMateriales[] listaMateriales;
    private ClaseUbicaciones[] listaUbicaciones;
    private int idActividadActual = 0;
    ImagenFondo imgFondo;

    public GenerarActividades(ClaseUsuario userSesion) {
        imgFondo = new ImagenFondo("src/imagenes/FondoFRM.jpg");
        this.userSesion = userSesion;
        this.setContentPane(imgFondo);
        setSize(900, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
       if (listaCampanias != null && listaCampanias.length > 0) {
        int idInicial = listaCampanias[0].getIdCampania();
        actualizarComboMateriales(idInicial); 
    }
         lblNombreUsuario.setText("Bienvenido: " +userSesion.getNombre() + " " + userSesion.getApellido() + "  Rol: " + userSesion.getRol());
        String rol = userSesion.getRol();
        configurarFormulario();
    }

private void actualizarComboMateriales(int idCampania) {
    cmbMaterial.removeAllItems();   
    Controlador.ControladorMaterial controlMat = new Controlador.ControladorMaterial();
    this.listaMateriales = controlMat.leerMaterialesPorCampania(idCampania);
    
    if (this.listaMateriales != null) {
        for (Modelo.ClaseMateriales mat : this.listaMateriales) {
            cmbMaterial.addItem(mat.getNombreMaterial());
        }
    }
}
    
  private void configurarFormulario() {
    if (this.userSesion != null) {
        txtAsignador.setText(this.userSesion.getNombre());
        txtAsignador.setEditable(false);
    } else {
        JOptionPane.showMessageDialog(this, "Error: No se detectó una sesión activa.");
        return;
    }

    rbMedia.setSelected(true);

    try {
        ControladorUsuario cUser = new ControladorUsuario();
        listaAsociados = cUser.leerUsuarios();
        cmbAsignado1.removeAllItems();
        cmbAsignado2.removeAllItems();
        cmbAsignado2.addItem("Ninguno");

        if (listaAsociados != null) {
            for (ClaseUsuario u : listaAsociados) {
                if ("ASOCIADO".equals(u.getRol())) {
                    cmbAsignado1.addItem(u.getNombre());
                    cmbAsignado2.addItem(u.getNombre());
                }
            }
        }
    } catch (Exception e) {
        System.err.println("Error al cargar Asociados: " + e.getMessage());
    }

    try {
        ControladorCampanias cCamp = new ControladorCampanias();
        listaCampanias = cCamp.leerCampanias();
        cmbCampania.removeAllItems();
        if (listaCampanias != null) {
            for (ClaseCampania c : listaCampanias) {
                cmbCampania.addItem(c.getNombre());
            }
        }
    } catch (Exception e) {
        System.err.println("Error al cargar Campañas: " + e.getMessage());
    }

    try {
        ControladorMaterial cMat = new ControladorMaterial();
        listaMateriales = cMat.leerTodosLosMateriales();
        cmbMaterial.removeAllItems();
        if (listaMateriales != null) {
            for (ClaseMateriales m : listaMateriales) {
                cmbMaterial.addItem(m.getNombreMaterial());
            }
        }
    } catch (Exception e) {
        System.err.println("Error al cargar Materiales: " + e.getMessage());
    }

    try {
        ControladorUbicaciones cUbi = new ControladorUbicaciones();
        listaUbicaciones = cUbi.leerUbicaciones();
        cmbUbicacion.removeAllItems();
        if (listaUbicaciones != null) {
            for (ClaseUbicaciones u : listaUbicaciones) {
                String c1 = (u.getCalle1() != null) ? u.getCalle1() : "S/C";
                String c2 = (u.getCalle2() != null && !u.getCalle2().isEmpty()) ? " y " + u.getCalle2() : "";
                cmbUbicacion.addItem(c1 + c2);
            }
        }
    } catch (Exception e) {
        System.err.println("Error al cargar Ubicaciones: " + e.getMessage());
    }
}
   private String leerPrioridadSeleccionada() {
    if (rbAlta.isSelected()) return "ALTA";
    if (rbMedia.isSelected()) return "MEDIA";
    if (rbBaja.isSelected()) return "BAJA";
    return "MEDIA";
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Prioridad = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbAsignado1 = new javax.swing.JComboBox<>();
        btnAsignar = new javax.swing.JButton();
        jbnModificar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cmbAsignado2 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        cmbCampania = new javax.swing.JComboBox<>();
        txtAsignador = new javax.swing.JTextField();
        cmbUbicacion = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cmbMaterial = new javax.swing.JComboBox<>();
        spnCantidad = new javax.swing.JSpinner();
        rbAlta = new javax.swing.JRadioButton();
        rbMedia = new javax.swing.JRadioButton();
        rbBaja = new javax.swing.JRadioButton();
        lblNombreUsuario = new javax.swing.JLabel();
        jcbNombre = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        txtFechaProg = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 550));
        setSize(new java.awt.Dimension(0, 0));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(86, 191, 22));
        jLabel4.setText("Fecha programada:");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(86, 191, 22));
        jLabel1.setText("REGISTRO DE ACTIVIDADES");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(86, 191, 22));
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(86, 191, 22));
        jLabel3.setText("Descripcion:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(86, 191, 22));
        jLabel9.setText("Prioridad:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(86, 191, 22));
        jLabel10.setText("Asignador:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(86, 191, 22));
        jLabel11.setText("Asignado 1:");

        cmbAsignado1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jose Alejandro", "Juan Pablo", "Luis Miguel" }));
        cmbAsignado1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(86, 191, 22), null));
        cmbAsignado1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        btnAsignar.setText("Asignar");
        btnAsignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarActionPerformed(evt);
            }
        });
        btnAsignar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAsignarKeyPressed(evt);
            }
        });

        jbnModificar.setText("Modificar");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(86, 191, 22));
        jLabel12.setText("Asignado 2:");

        cmbAsignado2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbAsignado2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));
        cmbAsignado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAsignado2ActionPerformed(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        txtDescripcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtDescripcion);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(86, 191, 22));
        jLabel13.setText("Campaña:");

        cmbCampania.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCampania.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));
        cmbCampania.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCampaniaItemStateChanged(evt);
            }
        });

        txtAsignador.setText("jTextField1");
        txtAsignador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));
        txtAsignador.setEnabled(false);

        cmbUbicacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbUbicacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(86, 191, 22));
        jLabel5.setText("Ubicacion:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(86, 191, 22));
        jLabel14.setText("Material:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(86, 191, 22));
        jLabel15.setText("Cantidad:");

        cmbMaterial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbMaterial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));

        spnCantidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));

        Prioridad.add(rbAlta);
        rbAlta.setText("ALTA");
        rbAlta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));

        Prioridad.add(rbMedia);
        rbMedia.setText("MEDIA");
        rbMedia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));

        Prioridad.add(rbBaja);
        rbBaja.setText("BAJA");
        rbBaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));

        lblNombreUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        lblNombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreUsuario.setText("jLabel6");

        jcbNombre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lavado", "Montaje", " " }));
        jcbNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(86, 174, 32)));

        jButton1.setText("Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addComponent(lblNombreUsuario))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jLabel2))
                            .addComponent(jLabel4))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jcbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel3)
                                .addGap(28, 28, 28))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtFechaProg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(149, 149, 149)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel10)
                        .addGap(37, 37, 37)
                        .addComponent(txtAsignador, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel5)
                        .addGap(50, 50, 50)
                        .addComponent(cmbUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel11)
                        .addGap(31, 31, 31)
                        .addComponent(cmbAsignado1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel13)
                        .addGap(43, 43, 43)
                        .addComponent(cmbCampania, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel12)
                        .addGap(31, 31, 31)
                        .addComponent(cmbAsignado2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel14)
                        .addGap(50, 50, 50)
                        .addComponent(cmbMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(jLabel9)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAsignar)
                                .addGap(18, 18, 18)
                                .addComponent(jbnModificar)
                                .addGap(19, 19, 19)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbAlta)
                                .addGap(10, 10, 10)
                                .addComponent(rbMedia)
                                .addGap(3, 3, 3)
                                .addComponent(rbBaja)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel15)
                                .addGap(46, 46, 46)
                                .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblNombreUsuario)
                .addGap(78, 78, 78)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4))
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFechaProg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtAsignador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5))
                    .addComponent(cmbUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(cmbAsignado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel13))
                    .addComponent(cmbCampania, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(cmbAsignado2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(cmbMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(rbAlta)
                    .addComponent(rbMedia)
                    .addComponent(rbBaja)
                    .addComponent(jLabel15)
                    .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAsignar)
                    .addComponent(jbnModificar)
                    .addComponent(jButton1))
                .addGap(35, 35, 35))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarActionPerformed
        // TODO add your handling code here:                                                                                   
   try {
        ClaseActividad act = new ClaseActividad();
        ControladorActividad control = new ControladorActividad();

        if (jcbNombre.getSelectedItem() == null || jcbNombre.getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un nombre para la actividad.");
            return;
        }
        if (txtFechaProg.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha programada.");
            return;
        }

        act.setNombreActividad(jcbNombre.getSelectedItem().toString().trim());
        act.setDescripcion(txtDescripcion.getText().trim());
        act.setEstado("PENDIENTE");

        if (rbAlta.isSelected()) {
            act.setPrioridad("ALTA");
        } else if (rbBaja.isSelected()) {
            act.setPrioridad("BAJA");
        } else {
            act.setPrioridad("MEDIA");
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        act.setFechaProgramada(sdf.format(txtFechaProg.getDate()));
        act.setFechaCreacion(java.time.LocalDate.now().toString());
        act.setFechaInicio(null);
        act.setFechaFinalizacion(null);
        act.setIdCreador(this.userSesion.getIdUsuario());
        
        int idxAsig1 = cmbAsignado1.getSelectedIndex();
        int contador1 = 0;
        for (ClaseUsuario u : listaAsociados) {
            if ("ASOCIADO".equals(u.getRol())) {
                if (contador1 == idxAsig1) {
                    act.setIdAsignado(u.getIdUsuario());
                    break;
                }
                contador1++;
            }
        }

        if (cmbAsignado2.getSelectedIndex() > 0) {
            int idxAsig2 = cmbAsignado2.getSelectedIndex() - 1;
            int contador2 = 0;
            for (ClaseUsuario u : listaAsociados) {
                if ("ASOCIADO".equals(u.getRol())) {
                    if (contador2 == idxAsig2) {
                        act.setIdAsignado2(u.getIdUsuario());
                        break;
                    }
                    contador2++;
                }
            }
        } else {
            act.setIdAsignado2(0);
        }

        act.setIdUbicacion(listaUbicaciones[cmbUbicacion.getSelectedIndex()].getIdUbicacion());
        act.setIdCampania(listaCampanias[cmbCampania.getSelectedIndex()].getIdCampania());
        
        int idMat = listaMateriales[cmbMaterial.getSelectedIndex()].getIdMaterial();
        int cantMat = (int) spnCantidad.getValue();

        if (control.registrarActividadCompleta(act, idMat, cantMat)) {
            JOptionPane.showMessageDialog(this, "Actividad asignada exitosamente.");
            limpiarCampos();
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al procesar la asignación: " + e.getMessage());
    }
    }//GEN-LAST:event_btnAsignarActionPerformed

    private void cmbAsignado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAsignado2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAsignado2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        MenuPrincipal menu = new MenuPrincipal(userSesion);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbCampaniaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCampaniaItemStateChanged
        // TODO add your handling code here:
    if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
        int index = cmbCampania.getSelectedIndex();
        
        // Diagnóstico 1: ¿Se activó el evento?
        System.out.println("Evento disparado. Índice seleccionado: " + index);

        if (index >= 0 && listaCampanias != null) {
            int idCampaniaElegida = listaCampanias[index].getIdCampania();
           
            System.out.println("Filtrando materiales para ID_CAMPANIA: " + idCampaniaElegida);
            
            actualizarComboMateriales(idCampaniaElegida);
        }
    }
    }//GEN-LAST:event_cmbCampaniaItemStateChanged

    private void txtDescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyPressed
        // TODO add your handling code here:
        char tecla = (char) evt.getExtendedKeyCode();
        if (tecla == KeyEvent.VK_ENTER) {
            btnAsignar.requestFocus();
        }
    }//GEN-LAST:event_txtDescripcionKeyPressed

    private void btnAsignarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAsignarKeyPressed
        // TODO add your handling code here:
        char tecla = (char) evt.getExtendedKeyCode();
        if (tecla == KeyEvent.VK_ENTER) {
            btnAsignar.doClick();
            jButton1.requestFocus();
        }
    }//GEN-LAST:event_btnAsignarKeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
        char tecla = (char) evt.getExtendedKeyCode();
        if (tecla == KeyEvent.VK_ENTER) {
            jButton1.doClick();
        }
    }//GEN-LAST:event_jButton1KeyPressed
private void limpiarCampos() {
    
    txtDescripcion.setText("");
    txtFechaProg.setDate(null);
    rbMedia.setSelected(true);
    
    if (cmbAsignado1.getItemCount() > 0) cmbAsignado1.setSelectedIndex(0);
    if (cmbAsignado2.getItemCount() > 0) cmbAsignado2.setSelectedIndex(0);
    if (cmbUbicacion.getItemCount() > 0) cmbUbicacion.setSelectedIndex(0);
    if (cmbCampania.getItemCount() > 0) cmbCampania.setSelectedIndex(0);
    if (cmbMaterial.getItemCount() > 0) cmbMaterial.setSelectedIndex(0);
    
    spnCantidad.setValue(0);
}
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
        java.awt.EventQueue.invokeLater(() -> new GenerarActividades(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Prioridad;
    private javax.swing.JButton btnAsignar;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cmbAsignado1;
    private javax.swing.JComboBox<String> cmbAsignado2;
    private javax.swing.JComboBox<String> cmbCampania;
    private javax.swing.JComboBox<String> cmbMaterial;
    private javax.swing.JComboBox<String> cmbUbicacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbnModificar;
    private javax.swing.JComboBox<String> jcbNombre;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JRadioButton rbAlta;
    private javax.swing.JRadioButton rbBaja;
    private javax.swing.JRadioButton rbMedia;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JTextField txtAsignador;
    private javax.swing.JTextArea txtDescripcion;
    private com.toedter.calendar.JDateChooser txtFechaProg;
    // End of variables declaration//GEN-END:variables
}
