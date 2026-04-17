/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ClaseCampania;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Modelo.ConexionBD;

/**
 *
 * @author porqu
 */
public class ControladorCampanias {

    private ConexionBD conexion = new ConexionBD();
    private int cantRegistros;

    public ControladorCampanias() {
        conexion = new ConexionBD();
        this.cantRegistros = 0;
    }

    private static final String INSERT_CAMPANIA
            = "INSERT INTO campanias(NOMBRE, DESCRIPCION, FECHA_INICIO, FECHA_FIN, ESTADO) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_CAMPANIAS
            = "SELECT * FROM campanias";
    
    private static final String UPDATE_CAMPANIA
            = "UPDATE campanias SET NOMBRE=?, DESCRIPCION=?, FECHA_INICIO=?, FECHA_FIN=?, ESTADO=? WHERE ID_CAMPANIA=?";

    private static final String DELETE_CAMPANIA
            = "DELETE FROM campanias WHERE ID_CAMPANIA=?";

   public boolean registrarCampania(ClaseCampania c) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
        conn = conexion.getConnection();
        ps = conn.prepareStatement(INSERT_CAMPANIA);
        
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getDescripcion());
        ps.setDate(3, java.sql.Date.valueOf(c.getFechaInicio()));
        ps.setDate(4, java.sql.Date.valueOf(c.getFechaFin()));
        
        ps.setString(5, c.getEstado());
        
        return ps.executeUpdate() > 0;
        
    } catch (IllegalArgumentException e) {
        // Atrapa el error si la fecha llega mal formateada o vacía
        javax.swing.JOptionPane.showMessageDialog(null, "Error en el formato de la fecha. Contacta al administrador.", "Error de Datos", javax.swing.JOptionPane.ERROR_MESSAGE);
        System.out.println("Crasheo evitado. Fecha Inicio intentada: [" + c.getFechaInicio() + "] Fecha Fin: [" + c.getFechaFin() + "]");
        return false;
        
    } catch (ClassNotFoundException | SQLException e) {
        // Imprime todo el detalle rojo en la consola para saber exactamente qué falló
        e.printStackTrace(); 
        javax.swing.JOptionPane.showMessageDialog(null, "Error en BD al registrar: " + e.getMessage(), "Error SQL", javax.swing.JOptionPane.ERROR_MESSAGE);
        return false;
        
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conexion.closeConnection(conn);
            }
        } catch (SQLException ex) {
        }
    }
}

    public ClaseCampania[] leerCampanias() {
        ClaseCampania[] aCampanias = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) AS CANT FROM campanias");
            rs = ps.executeQuery();
            if (rs.next()) {
                cantRegistros = rs.getInt("CANT");
            }

            aCampanias = new ClaseCampania[cantRegistros];
            ps = conn.prepareStatement(SELECT_ALL_CAMPANIAS);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                ClaseCampania c = new ClaseCampania();
                c.setIdCampania(rs.getInt("ID_CAMPANIA"));
                c.setNombre(rs.getString("NOMBRE"));
                c.setDescripcion(rs.getString("DESCRIPCION"));
                c.setFechaInicio(rs.getString("FECHA_INICIO"));
                c.setFechaFin(rs.getString("FECHA_FIN"));
                c.setEstado(rs.getString("ESTADO"));
                aCampanias[i] = c;
                i++;
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conexion.closeConnection(conn);
                }
            } catch (SQLException ex) {
            }
        }
        return aCampanias;
    }
    // Método para el botón Modificar
    public boolean actualizarCampania(ClaseCampania c) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(UPDATE_CAMPANIA);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setString(3, c.getFechaInicio());
            ps.setString(4, c.getFechaFin());
            ps.setString(5, c.getEstado());
            ps.setInt(6, c.getIdCampania()); // El ID va al final por el WHERE
            
            return ps.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conexion.closeConnection(conn);
            } catch (SQLException ex) {}
        }
    }

    public boolean eliminarCampania(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(DELETE_CAMPANIA);
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conexion.closeConnection(conn);
            } catch (SQLException ex) {}
        }
    }
}
