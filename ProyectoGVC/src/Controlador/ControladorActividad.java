/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Modelo.ClaseActividad;
import Modelo.ConexionBD;

public class ControladorActividad {

    private ConexionBD conexion;
    private int cantRegistros;

    public ControladorActividad() {
        conexion = new ConexionBD();
        this.cantRegistros = 0;
    }

    private static final String INSERT_ACTIVIDAD = 
        "INSERT INTO actividades(NOMBRE_ACTIVIDAD, DESCRIPCION, FECHA_CREACION, FECHA_PROGRAMADA, FECHA_INICIO, FECHA_FINALIZACION, ESTADO, PRIORIDAD, ID_CREADOR, ID_ASIGNADO, ID_ASIGNADO_2, ID_UBICACION, ID_CAMPANIA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_ACTIVIDAD_MATERIAL = 
    "INSERT INTO actividad_material(ID_ACTIVIDAD, ID_MATERIAL, CANTIDAD_USADA, ENTREGADO) VALUES (?, ?, ?, 0)";
    
    private static final String UPDATE_ACTIVIDAD = 
        "UPDATE actividades SET NOMBRE_ACTIVIDAD=?, DESCRIPCION=?, FECHA_PROGRAMADA=?, FECHA_INICIO=?, FECHA_FINALIZACION=?, ESTADO=?, PRIORIDAD=?, ID_CREADOR=?, ID_ASIGNADO=?, ID_ASIGNADO_2=?, ID_UBICACION=?, ID_CAMPANIA=? WHERE ID_ACTIVIDAD=?";

    private static final String UPDATE_ACTIVIDAD_MATERIAL = 
        "UPDATE actividad_material SET idMaterial=?, CANTIDAD_USADA=? WHERE idActividad=?";
    
    private static final String SELECT_ALL_ACTIVIDADES = "SELECT * FROM actividades";
    private static final String SELECT_COUNT_ACTIVIDADES = "SELECT COUNT(*) AS CANT FROM actividades";
    private static final String DELETE_ACTIVIDAD = "DELETE FROM actividades WHERE ID_ACTIVIDAD=?";
    private static final String SELECT_ACTIVIDAD_BY_ID = "SELECT * FROM actividades WHERE ID_ACTIVIDAD=?";

   public boolean registrarActividadCompleta(ClaseActividad actividad, int idMaterial, int cantidadMaterial) {
    Connection conn = null;
    PreparedStatement psAct = null;
    PreparedStatement psMat = null;
    ResultSet rs = null;

    try {
        conn = conexion.getConnection();
        conn.setAutoCommit(false);

        psAct = conn.prepareStatement(INSERT_ACTIVIDAD, PreparedStatement.RETURN_GENERATED_KEYS);
        psAct.setString(1, actividad.getNombreActividad());
        psAct.setString(2, actividad.getDescripcion());
        psAct.setString(3, actividad.getFechaCreacion());
        psAct.setString(4, actividad.getFechaProgramada());

        String fInicio = actividad.getFechaInicio();
        if (fInicio == null || fInicio.trim().equalsIgnoreCase("None") || fInicio.trim().isEmpty()) {
            psAct.setNull(5, java.sql.Types.DATE); // Cambiado a DATE
        } else {
            psAct.setString(5, fInicio);
        }

        // BLINDAJE FECHA FINALIZACION
        String fFin = actividad.getFechaFinalizacion();
        if (fFin == null || fFin.trim().equalsIgnoreCase("None") || fFin.trim().isEmpty()) {
            psAct.setNull(6, java.sql.Types.DATE); // Cambiado a DATE
        } else {
            psAct.setString(6, fFin);
        }

        psAct.setString(7, actividad.getEstado());
        psAct.setString(8, actividad.getPrioridad());
        psAct.setInt(9, actividad.getIdCreador());
        psAct.setInt(10, actividad.getIdAsignado());

        if (actividad.getIdAsignado2() > 0) {
            psAct.setInt(11, actividad.getIdAsignado2());
        } else {
            psAct.setNull(11, java.sql.Types.INTEGER);
        }

        psAct.setInt(12, actividad.getIdUbicacion());
        psAct.setInt(13, actividad.getIdCampania());

        int filas = psAct.executeUpdate();

        if (filas > 0) {
            rs = psAct.getGeneratedKeys();
            if (rs.next()) {
                int idGenerado = rs.getInt(1);

                // Aquí usamos el INSERT que corregimos arriba (sin la 's')
                psMat = conn.prepareStatement(INSERT_ACTIVIDAD_MATERIAL);
                psMat.setInt(1, idGenerado);
                psMat.setInt(2, idMaterial);
                psMat.setInt(3, cantidadMaterial);
                psMat.executeUpdate();
            }
        }

        conn.commit();
        return true;
    } catch (Exception e) {
        try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
        JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    } finally {
        try {
            if (rs != null) rs.close();
            if (psAct != null) psAct.close();
            if (psMat != null) psMat.close();
            if (conn != null) conexion.closeConnection(conn);
        } catch (Exception ex) {}
    }
}

    public boolean modificarActividadCompleta(ClaseActividad actividad, int idMaterial, int cantidadMaterial) {
        Connection conn = null;
        PreparedStatement psAct = null;
        PreparedStatement psMat = null;

        try {
            conn = conexion.getConnection();
            conn.setAutoCommit(false); 

            psAct = conn.prepareStatement(UPDATE_ACTIVIDAD);
            psAct.setString(1, actividad.getNombreActividad());
            psAct.setString(2, actividad.getDescripcion());
            psAct.setString(3, actividad.getFechaProgramada());

            if (actividad.getFechaInicio() == null || actividad.getFechaInicio().equals("None")) {
                psAct.setNull(4, java.sql.Types.VARCHAR);
            } else {
                psAct.setString(4, actividad.getFechaInicio());
            }

            if (actividad.getFechaFinalizacion() == null || actividad.getFechaFinalizacion().equals("None")) {
                psAct.setNull(5, java.sql.Types.VARCHAR);
            } else {
                psAct.setString(5, actividad.getFechaFinalizacion());
            }

            psAct.setString(6, actividad.getEstado());
            psAct.setString(7, actividad.getPrioridad());
            psAct.setInt(8, actividad.getIdCreador());
            psAct.setInt(9, actividad.getIdAsignado());

            if (actividad.getIdAsignado2() > 0) {
                psAct.setInt(10, actividad.getIdAsignado2());
            } else {
                psAct.setNull(10, java.sql.Types.INTEGER);
            }

            psAct.setInt(11, actividad.getIdUbicacion());
            psAct.setInt(12, actividad.getIdCampania());
            psAct.setInt(13, actividad.getIdActividad()); 

            int filasActividad = psAct.executeUpdate();

            if (filasActividad > 0) {
                psMat = conn.prepareStatement(UPDATE_ACTIVIDAD_MATERIAL);
                psMat.setInt(1, idMaterial);
                psMat.setInt(2, cantidadMaterial);
                psMat.setInt(3, actividad.getIdActividad());
                psMat.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (psAct != null) psAct.close();
                if (psMat != null) psMat.close();
                if (conn != null) conexion.closeConnection(conn);
            } catch (Exception ex) {}
        }
    }

    public ClaseActividad[] leerActividades() {
        ClaseActividad[] arregloActividades = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int cantidadRegistros = 0;

        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(SELECT_COUNT_ACTIVIDADES);
            rs = ps.executeQuery();
            if (rs.next()) {
                cantidadRegistros = rs.getInt("CANT");
            }

            arregloActividades = new ClaseActividad[cantidadRegistros];
            ps = conn.prepareStatement(SELECT_ALL_ACTIVIDADES);
            rs = ps.executeQuery();
            
            int i = 0;
            while (rs.next()) {
                ClaseActividad act = new ClaseActividad();
                act.setIdActividad(rs.getInt("ID_ACTIVIDAD"));
                act.setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD")); 
                act.setDescripcion(rs.getString("DESCRIPCION"));
                act.setFechaCreacion(rs.getString("FECHA_CREACION"));
                act.setFechaProgramada(rs.getString("FECHA_PROGRAMADA"));
                act.setFechaInicio(rs.getString("FECHA_INICIO"));
                act.setFechaFinalizacion(rs.getString("FECHA_FINALIZACION"));
                act.setEstado(rs.getString("ESTADO"));
                act.setPrioridad(rs.getString("PRIORIDAD"));
                act.setIdCreador(rs.getInt("ID_CREADOR"));
                act.setIdAsignado(rs.getInt("ID_ASIGNADO"));
                act.setIdAsignado2(rs.getInt("ID_ASIGNADO_2"));
                act.setIdUbicacion(rs.getInt("ID_UBICACION"));
                act.setIdCampania(rs.getInt("ID_CAMPANIA"));

                arregloActividades[i] = act;
                i++;
            }
        } catch (Exception e) {
            System.err.println("Error al leer actividades: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (conn != null) conexion.closeConnection(conn); } catch (Exception ex) {}
        }
        return arregloActividades;
    }


  public boolean confirmarEntregaMaterial(int idActividad) {
    java.sql.Connection conn = null;
    java.sql.PreparedStatement psUpdateEstado = null;
    java.sql.PreparedStatement psUpdateStock = null;
    java.sql.PreparedStatement psGetDatos = null;
    java.sql.ResultSet rs = null;

    try {
        conn = conexion.getConnection();
        conn.setAutoCommit(false);
        String sqlGet = "SELECT ID_MATERIAL, CANTIDAD_USADA FROM actividad_material WHERE ID_ACTIVIDAD = ?";
        psGetDatos = conn.prepareStatement(sqlGet);
        psGetDatos.setInt(1, idActividad);
        rs = psGetDatos.executeQuery();

        if (rs.next()) {
            int idMaterial = rs.getInt("ID_MATERIAL");
            int cantidadUsada = rs.getInt("CANTIDAD_USADA");
            String sqlEntrega = "UPDATE actividad_material SET ENTREGADO = 1 WHERE ID_ACTIVIDAD = ?";
            psUpdateEstado = conn.prepareStatement(sqlEntrega);
            psUpdateEstado.setInt(1, idActividad);
            psUpdateEstado.executeUpdate();
            String sqlStock = "UPDATE material SET CANTIDAD_DISPONIBLE = CANTIDAD_DISPONIBLE - ? WHERE ID_MATERIAL = ?";
            psUpdateStock = conn.prepareStatement(sqlStock);
            psUpdateStock.setInt(1, cantidadUsada);
            psUpdateStock.setInt(2, idMaterial);
            psUpdateStock.executeUpdate();
            conn.commit();
            return true;
        } else {
            return false;
        }

    } catch (Exception e) {
        try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
        javax.swing.JOptionPane.showMessageDialog(null, "Error en la operación: " + e.getMessage());
        return false;
    } finally {
        try {
            if (rs != null) rs.close();
            if (psGetDatos != null) psGetDatos.close();
            if (psUpdateEstado != null) psUpdateEstado.close();
            if (psUpdateStock != null) psUpdateStock.close();
            if (conn != null) conexion.closeConnection(conn);
        } catch (Exception ex) {}
    }
}

    public boolean eliminarActividad(int idActividad) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(DELETE_ACTIVIDAD);
            ps.setInt(1, idActividad);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            return false;
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conexion.closeConnection(conn); } catch (Exception ex) {}
        }
    }

    public ClaseActividad buscarActividad(int idActividad) {
        ClaseActividad act = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(SELECT_ACTIVIDAD_BY_ID);
            ps.setInt(1, idActividad);
            rs = ps.executeQuery();
            if (rs.next()) {
                act = new ClaseActividad();
                act.setIdActividad(rs.getInt("ID_ACTIVIDAD"));
                act.setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));
                act.setDescripcion(rs.getString("DESCRIPCION"));
                act.setFechaCreacion(rs.getString("FECHA_CREACION"));
                act.setFechaProgramada(rs.getString("FECHA_PROGRAMADA"));
                act.setFechaInicio(rs.getString("FECHA_INICIO"));
                act.setFechaFinalizacion(rs.getString("FECHA_FINALIZACION"));
                act.setEstado(rs.getString("ESTADO"));
                act.setPrioridad(rs.getString("PRIORIDAD"));
                act.setIdCreador(rs.getInt("ID_CREADOR"));
                act.setIdAsignado(rs.getInt("ID_ASIGNADO"));
                act.setIdAsignado2(rs.getInt("ID_ASIGNADO_2"));
                act.setIdUbicacion(rs.getInt("ID_UBICACION"));
                act.setIdCampania(rs.getInt("ID_CAMPANIA"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar: " + e.getMessage());
        } finally {
            try { if(rs != null) rs.close(); if(ps != null) ps.close(); if(conn != null) conexion.closeConnection(conn); } catch(Exception ex) {}
        }
        return act;
    }
  public Object[][] leerMaterialesPendientes() {
    java.sql.Connection conn = null;
    java.sql.PreparedStatement ps = null;
    java.sql.ResultSet rs = null;
    Object[][] datos = null;

    try {
        conn = conexion.getConnection();
        
        String sql = "SELECT a.ID_ACTIVIDAD, c.NOMBRE, a.NOMBRE_ACTIVIDAD, m.NOMBRE_MATERIAL, am.CANTIDAD_USADA " +
                     "FROM actividades a " +
                     "INNER JOIN campanias c ON a.ID_CAMPANIA = c.ID_CAMPANIA " +
                     "INNER JOIN actividad_material am ON a.ID_ACTIVIDAD = am.ID_ACTIVIDAD " +
                     "INNER JOIN material m ON am.ID_MATERIAL = m.ID_MATERIAL " + 
                     "WHERE am.ENTREGADO = 0"; 
                     
        ps = conn.prepareStatement(sql, java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);
        rs = ps.executeQuery();
        
        rs.last();
        int filas = rs.getRow();
        rs.beforeFirst();
        
        datos = new Object[filas][5]; 
        
        int i = 0;
        while (rs.next()) {
            datos[i][0] = rs.getInt("ID_ACTIVIDAD");
            // --- ¡AQUÍ ESTÁ EL NUEVO ORDEN! ---
            datos[i][1] = rs.getString("NOMBRE_ACTIVIDAD"); 
            datos[i][2] = rs.getString("NOMBRE_MATERIAL");  
            datos[i][3] = rs.getString("NOMBRE");            
            datos[i][4] = rs.getInt("CANTIDAD_USADA");        
            i++;
        }
    } catch (Exception e) {
        System.err.println("Error al leer materiales pendientes: " + e.getMessage());
    } finally {
        try { if(rs != null) rs.close(); if(ps != null) ps.close(); if(conn != null) conexion.closeConnection(conn); } catch(Exception ex) {}
    }
    return datos;
}
  
  public Object[][] leerHistorialTiempos() {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Object[][] datos = null;

    String sql = "SELECT a.NOMBRE_ACTIVIDAD, h.ESTADO_ANTERIOR, h.ESTADO_NUEVO, h.FECHA_CAMBIO, u.NOMBRE "
               + "FROM Historial_Estados h "
               + "INNER JOIN actividades a ON h.ID_ACTIVIDAD = a.ID_ACTIVIDAD "
               + "INNER JOIN usuarios u ON h.ID_USUARIO = u.ID_USUARIO "
               + "ORDER BY h.FECHA_CAMBIO DESC";

    try {
        conn = conexion.getConnection();
        ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = ps.executeQuery();
        rs.last();
        int filas = rs.getRow();
        rs.beforeFirst();

        datos = new Object[filas][5];
        int i = 0;
        while (rs.next()) {
            datos[i][0] = rs.getString(1); // Actividad
            datos[i][1] = rs.getString(2); // De
            datos[i][2] = rs.getString(3); // A
            datos[i][3] = rs.getTimestamp(4); // Cuándo
            datos[i][4] = rs.getString(5); // Quién lo movió
            i++;
        }
    } catch (Exception e) {
        System.err.println("Error al leer historial: " + e.getMessage());
    }
    return datos;
}
public Object[][] leerActividadesDiariasPorEmpleado(int idEmpleado) {
    java.sql.Connection conn = null;
    java.sql.PreparedStatement ps = null;
    java.sql.ResultSet rs = null;
    Object[][] datos = null;

    try {
        conn = conexion.getConnection();
       String sql = "SELECT a.ID_ACTIVIDAD, a.NOMBRE_ACTIVIDAD, c.NOMBRE, u.CODIGO, " +
             "a.FECHA_PROGRAMADA, a.PRIORIDAD, a.ESTADO, u.ID_UBICACION " +
             "FROM actividades a " +
             "LEFT JOIN campanias c ON a.ID_CAMPANIA = c.ID_CAMPANIA " +
             "LEFT JOIN ubicaciones u ON a.ID_UBICACION = u.ID_UBICACION " +
             "WHERE a.ID_ASIGNADO = ? " +
             "AND (a.ESTADO = 'PENDIENTE' OR a.ESTADO LIKE '%PROCESO%') " +
             "ORDER BY a.PRIORIDAD DESC, a.FECHA_PROGRAMADA ASC";

        ps = conn.prepareStatement(sql, java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, idEmpleado);
        rs = ps.executeQuery();

        rs.last();
        int filas = rs.getRow();
        rs.beforeFirst();

        datos = new Object[filas][8]; // Matriz de 8 columnas
        int i = 0;
        while (rs.next()) {
            datos[i][0] = rs.getInt("ID_ACTIVIDAD");
            datos[i][1] = rs.getString("NOMBRE_ACTIVIDAD");
            datos[i][2] = rs.getString("NOMBRE");
            datos[i][3] = rs.getString("CODIGO");
            datos[i][4] = rs.getDate("FECHA_PROGRAMADA");
            datos[i][5] = rs.getString("PRIORIDAD");
            datos[i][6] = rs.getString("ESTADO");
            datos[i][7] = rs.getInt("ID_UBICACION"); // <--- Aquí vive la columna 7
            i++;
        }
    } catch (Exception e) {
        System.err.println("Error al cargar actividades: " + e.getMessage());
    } finally {
        // Asegúrate de cerrar conexiones aquí
    }
    return datos;
}
public Object[] obtenerDetallesCompletos(int idActividad) {
    Object[] detalles = new Object[7];
    java.sql.Connection conn = null;
    java.sql.PreparedStatement ps = null;
    java.sql.ResultSet rs = null;

    try {
        conn = conexion.getConnection();

        String sql = "SELECT u.CALLE_1, u.CALLE_2, u.SENTIDO, a.DESCRIPCION, " +
                     "u.TIPO_UBICACION, u.RUTA, u.RC " +
                     "FROM actividades a " +
                     "INNER JOIN ubicaciones u ON a.ID_UBICACION = u.ID_UBICACION " +
                     "WHERE a.ID_ACTIVIDAD = ?";
        
        ps = conn.prepareStatement(sql);
        ps.setInt(1, idActividad);
        rs = ps.executeQuery();

        if (rs.next()) {
            detalles[0] = rs.getString("CALLE_1");
            detalles[1] = rs.getString("CALLE_2");
            detalles[2] = rs.getString("SENTIDO");
            detalles[3] = rs.getString("DESCRIPCION");
            detalles[4] = rs.getString("TIPO_UBICACION");
            detalles[5] = rs.getString("RUTA");         
            detalles[6] = rs.getString("RC");            
        }
    } catch (Exception e) {
        System.err.println("Error al obtener detalles técnicos: " + e.getMessage());
    } finally {
        try { if(rs!=null) rs.close(); if(ps!=null) ps.close(); conexion.closeConnection(conn); } catch(Exception ex) {}
    }
    return detalles;
}
public boolean actualizarEstadoConHistorial(int idActividad, String estadoNuevo, String estadoAnterior, int idUsuario) {
    java.sql.Connection conn = null;
    java.sql.PreparedStatement psUpdate = null;
    java.sql.PreparedStatement psHistorial = null;

    String sqlUpdate = "UPDATE actividades SET ESTADO = ? WHERE ID_ACTIVIDAD = ?";
    String sqlHistorial = "INSERT INTO Historial_Estados (ID_ACTIVIDAD, ESTADO_ANTERIOR, ESTADO_NUEVO, ID_USUARIO) VALUES (?, ?, ?, ?)";

    try {
        conn = conexion.getConnection();
        conn.setAutoCommit(false); 

        // 1. Update Actividades
        psUpdate = conn.prepareStatement(sqlUpdate);
        psUpdate.setString(1, estadoNuevo);
        psUpdate.setInt(2, idActividad);
        psUpdate.executeUpdate();

        // 2. Insert Historial
        psHistorial = conn.prepareStatement(sqlHistorial);
        psHistorial.setInt(1, idActividad);
        psHistorial.setString(2, estadoAnterior);
        psHistorial.setString(3, estadoNuevo);
        psHistorial.setInt(4, idUsuario);
        psHistorial.executeUpdate();

        conn.commit(); 
        return true;
    } catch (Exception e) {
        try { if (conn != null) conn.rollback(); } catch (java.sql.SQLException ex) {}
        System.err.println("Error al guardar historial: " + e.getMessage());
        return false;
    } finally {
        try {
            if (psUpdate != null) psUpdate.close();
            if (psHistorial != null) psHistorial.close();
            if (conn != null) conn.close();
        } catch (java.sql.SQLException ex) {}
    }
}
public Object[][] leerReporteProductividad(String filtroUsuario, String filtroEstado) {
    java.sql.Connection conn = null;
    java.sql.PreparedStatement ps = null;
    java.sql.ResultSet rs = null;
    Object[][] datos = null;

    String sql = "SELECT a.NOMBRE_ACTIVIDAD, " +
                 "MAX(u.NOMBRE) AS RESPONSABLE, " +
                 "DATE_FORMAT(a.FECHA_CREACION, '%d/%m/%Y %H:%i') AS CREADA, " +
                 "DATE_FORMAT(MAX(CASE WHEN h.ESTADO_NUEVO LIKE '%PROCESO%' THEN h.FECHA_CAMBIO END), '%d/%m/%Y %H:%i') AS INICIADA, " +
                 "DATE_FORMAT(MAX(CASE WHEN h.ESTADO_NUEVO = 'COMPLETADA' THEN h.FECHA_CAMBIO END), '%d/%m/%Y %H:%i') AS FINALIZADA, " +
                 "TIMESTAMPDIFF(MINUTE, " +
                 "    MAX(CASE WHEN h.ESTADO_NUEVO LIKE '%PROCESO%' THEN h.FECHA_CAMBIO END), " +
                 "    MAX(CASE WHEN h.ESTADO_NUEVO = 'COMPLETADA' THEN h.FECHA_CAMBIO END)" +
                 ") AS MINUTOS " +
                 "FROM actividades a " +
                 "INNER JOIN Historial_Estados h ON a.ID_ACTIVIDAD = h.ID_ACTIVIDAD " +
                 "INNER JOIN usuarios u ON a.ID_ASIGNADO = u.ID_USUARIO " +
                 "WHERE 1=1 "; 
    if (filtroUsuario != null && !filtroUsuario.equalsIgnoreCase("Todos")) {
        sql += "AND u.NOMBRE = ? ";
    }
    if (filtroEstado != null && !filtroEstado.equalsIgnoreCase("Todos")) {
        if (filtroEstado.equalsIgnoreCase("Completadas") || filtroEstado.equalsIgnoreCase("Completada")) {
            sql += "AND a.ESTADO = 'COMPLETADA' ";
        } else if (filtroEstado.equalsIgnoreCase("En Proceso")) {
            sql += "AND a.ESTADO LIKE '%PROCESO%' ";
        }
    }

    sql += "GROUP BY a.ID_ACTIVIDAD, a.NOMBRE_ACTIVIDAD, a.FECHA_CREACION " +
           "ORDER BY MAX(h.FECHA_CAMBIO) DESC";

    try {
        conn = conexion.getConnection();
        ps = conn.prepareStatement(sql, java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);

        int index = 1;
        if (filtroUsuario != null && !filtroUsuario.equalsIgnoreCase("Todos")) {
            ps.setString(index++, filtroUsuario);
        }
        rs = ps.executeQuery();
        rs.last();
        int filas = rs.getRow();
        rs.beforeFirst();

        datos = new Object[filas][6];
        int i = 0;
        while (rs.next()) {
            datos[i][0] = rs.getString("NOMBRE_ACTIVIDAD");
            datos[i][1] = rs.getString("RESPONSABLE");
            datos[i][2] = (rs.getString("CREADA") != null) ? rs.getString("CREADA") : "Desconocida";
            datos[i][3] = (rs.getString("INICIADA") != null) ? rs.getString("INICIADA") : "Aún no inicia";
            datos[i][4] = (rs.getString("FINALIZADA") != null) ? rs.getString("FINALIZADA") : "En Proceso";
            
            int mins = rs.getInt("MINUTOS");
            if (rs.wasNull() || mins <= 0) {
                datos[i][5] = (rs.getString("FINALIZADA") != null) ? "< 1 min" : "Calculando...";
            } else {
                datos[i][5] = mins + " min";
            }
            i++;
        }
    } catch (Exception e) {
        System.err.println("Error en filtros de productividad: " + e.getMessage());
    } finally {
        try { if(rs!=null)rs.close(); if(ps!=null)ps.close(); if(conn!=null)conn.close(); } catch(Exception ex){}
    }
    return datos;
}
}