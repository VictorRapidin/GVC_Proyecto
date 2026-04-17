/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author porqu
 */
public class ControladorIncidencias {

    private ConexionBD conexion = new ConexionBD();

    public ControladorIncidencias() {
        conexion = new ConexionBD();
    }

  public boolean registrarIncidencia(int idAct, String tipo, String desc, int idUsuario, int idUbi) {
    Connection conn = null;
    PreparedStatement ps = null;

    String sql = "INSERT INTO incidencias (TIPO, DESCRIPCION, ID_ACTIVIDAD, ID_USUARIO, ID_UBICACION, ESTADO, FECHA_REPORTE) "
               + "VALUES (?, ?, ?, ?, ?, 'PENDIENTE', CURRENT_TIMESTAMP)";

    try {
        conn = conexion.getConnection();
        ps = conn.prepareStatement(sql);
        ps.setString(1, tipo);
        ps.setString(2, desc);
        ps.setInt(3, idAct);
        ps.setInt(4, idUsuario);
        ps.setInt(5, idUbi);

        return ps.executeUpdate() > 0;
    } catch (ClassNotFoundException | SQLException e) {
        System.err.println("Error al registrar incidencia: " + e.getMessage());
        return false;
    } finally {
        cerrarRecursos(null, ps, conn);
    }
}
  public Object[][] leerTodasLasIncidencias() {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Object[][] datos = null;

    // Modificamos el query para traer CALLE_1 y CALLE_2
    String sql = "SELECT i.ID_INCIDENCIA, i.TIPO, u.CALLE_1, u.CALLE_2, i.DESCRIPCION, i.ESTADO, i.ID_USUARIO "
            + "FROM incidencias i "
            + "LEFT JOIN ubicaciones u ON i.ID_UBICACION = u.ID_UBICACION "
            + "ORDER BY i.ID_INCIDENCIA DESC";

    try {
        conn = conexion.getConnection();
        ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = ps.executeQuery();

        rs.last();
        int filas = rs.getRow();
        rs.beforeFirst();

        datos = new Object[filas][6];
        int i = 0;
        while (rs.next()) {
            datos[i][0] = rs.getInt("ID_INCIDENCIA");
            datos[i][1] = rs.getString("TIPO");
            String c1 = rs.getString("CALLE_1");
            String c2 = rs.getString("CALLE_2");
            String direccionCompleta;
            
            if (c1 != null) {
                direccionCompleta = (c2 != null && !c2.isEmpty()) ? c1 + " y " + c2 : c1;
            } else {
                direccionCompleta = "S/N";
            }
            
            datos[i][2] = direccionCompleta; 
            datos[i][3] = rs.getString("DESCRIPCION");
            datos[i][4] = rs.getString("ESTADO");
            datos[i][5] = rs.getInt("ID_USUARIO");
            i++;
        }
    } catch (ClassNotFoundException | SQLException e) {
        System.err.println("Error al leer incidencias: " + e.getMessage());
    } finally {
        cerrarRecursos(rs, ps, conn);
    }
    return datos;
}

    private void cerrarRecursos(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conexion.closeConnection(conn);
        } catch (SQLException ex) {
            System.err.println("Error al cerrar recursos: " + ex.getMessage());
        }
    }

}
