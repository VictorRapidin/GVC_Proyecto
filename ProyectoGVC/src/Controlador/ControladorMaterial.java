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
import Modelo.ClaseMateriales;
import Modelo.ConexionBD;

/**
 *
 * @author porqu
 */
public class ControladorMaterial {

    private ConexionBD conexion = new ConexionBD();
    private int cantRegistros;

    public ControladorMaterial() {
        conexion = new ConexionBD();
        this.cantRegistros = 0;
    }

    private static final String INSERT_MATERIAL
            = "INSERT INTO material(NOMBRE_MATERIAL, TIPO, CANTIDAD_DISPONIBLE, STOCK_MINIMO, ACTIVO) VALUES (?, ?, ?, ?, 1)";

    private static final String SELECT_ALL_MATERIALES
            = "SELECT * FROM material WHERE ACTIVO = 1";

    private static final String SELECT_COUNT_MATERIALES
            = "SELECT COUNT(*) AS CANT FROM material WHERE ACTIVO = 1";

    private static final String UPDATE_MATERIAL
            = "UPDATE material SET NOMBRE_MATERIAL=?, TIPO=?, CANTIDAD_DISPONIBLE=?, STOCK_MINIMO=? WHERE ID_MATERIAL=?";

    private static final String DELETE_LOGICO_MATERIAL
            = "UPDATE material SET ACTIVO = 0 WHERE ID_MATERIAL=?";

    private static final String SELECT_MATERIAL_BY_ID
            = "SELECT * FROM material WHERE ID_MATERIAL=?";

    private static final String SELECT_LOW_STOCK
            = "SELECT * FROM material WHERE CANTIDAD_DISPONIBLE <= STOCK_MINIMO AND ACTIVO = 1";

    public int getCantRegistros() {
        return cantRegistros;
    }

    public void setCantRegistros(int cantRegistros) {
        this.cantRegistros = cantRegistros;
    }

   public Modelo.ClaseMateriales[] leerTodosLosMateriales() {
    Modelo.ClaseMateriales[] lista = null;
    java.sql.Connection conn = null;
    java.sql.PreparedStatement ps = null;
    java.sql.ResultSet rs = null;

    try {
        conn = conexion.getConnection();
        String sqlCount = "SELECT COUNT(*) AS total FROM material";
        ps = conn.prepareStatement(sqlCount);
        rs = ps.executeQuery();
        int filas = 0;
        if(rs.next()) filas = rs.getInt("total");

        lista = new Modelo.ClaseMateriales[filas];

        // Traemos los datos
        String sql = "SELECT * FROM material";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        
        int i = 0;
        while(rs.next()){
            Modelo.ClaseMateriales m = new Modelo.ClaseMateriales();
            m.setIdMaterial(rs.getInt("ID_MATERIAL"));
            m.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
            m.setTipo(rs.getString("TIPO"));
            m.setCantidadDisponible(rs.getInt("CANTIDAD_DISPONIBLE"));
            m.setStockMinimo(rs.getInt("STOCK_MINIMO"));
            m.setIdCampania(rs.getInt("ID_CAMPANIA")); // Si es NULL, Java lo toma como 0
            lista[i] = m;
            i++;
        }
    } catch (Exception e) {
        System.err.println("Error al leer materiales: " + e.getMessage());
    } finally {
        // Cerrar conexiones...
    }
    return lista;
}

   public boolean modificarMaterial(Modelo.ClaseMateriales mat) {
    java.sql.Connection conn = null;
    java.sql.PreparedStatement ps = null;
    String sql = "UPDATE material SET NOMBRE_MATERIAL=?, TIPO=?, CANTIDAD_DISPONIBLE=?, STOCK_MINIMO=?, ID_CAMPANIA=? WHERE ID_MATERIAL=?";
    
    try {
        conn = conexion.getConnection();
        ps = conn.prepareStatement(sql);
        ps.setString(1, mat.getNombreMaterial());
        ps.setString(2, mat.getTipo());
        ps.setInt(3, mat.getCantidadDisponible());
        ps.setInt(4, mat.getStockMinimo());
        
        if (mat.getIdCampania() == 0) {
            ps.setNull(5, java.sql.Types.INTEGER);
        } else {
            ps.setInt(5, mat.getIdCampania());
        }
        
        ps.setInt(6, mat.getIdMaterial());
        
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        return false;
    } finally {
        try { if(ps!=null)ps.close(); if(conn!=null)conexion.closeConnection(conn); } catch(Exception ex){}
    }
}

    public boolean eliminarMaterial(int idMaterial) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(DELETE_LOGICO_MATERIAL);
            ps.setInt(1, idMaterial);
            return ps.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar material: " + e.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE);
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

    public ClaseMateriales buscarMaterial(int idMaterial) {
        ClaseMateriales mat = null;
        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_MATERIAL_BY_ID)) {
            ps.setInt(1, idMaterial);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mat = new ClaseMateriales();
                    mat.setIdMaterial(rs.getInt("ID_MATERIAL"));
                    mat.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                    mat.setTipo(rs.getString("TIPO"));
                    mat.setCantidadDisponible(rs.getInt("CANTIDAD_DISPONIBLE"));
                    mat.setStockMinimo(rs.getInt("STOCK_MINIMO"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar material: " + e.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE);
        }
        return mat;
    }

    public ClaseMateriales[] consultarStockBajo() {
        ClaseMateriales[] materialesCriticos = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int contador = 0;
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) AS CANT FROM material WHERE CANTIDAD_DISPONIBLE <= STOCK_MINIMO AND ACTIVO = 1");
            rs = ps.executeQuery();
            if (rs.next()) {
                contador = rs.getInt("CANT");
            }
            materialesCriticos = new ClaseMateriales[contador];
            ps = conn.prepareStatement(SELECT_LOW_STOCK);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                ClaseMateriales mat = new ClaseMateriales();
                mat.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                mat.setCantidadDisponible(rs.getInt("CANTIDAD_DISPONIBLE"));
                mat.setStockMinimo(rs.getInt("STOCK_MINIMO"));
                materialesCriticos[i] = mat;
                i++;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error en alerta de stock: " + e.getMessage());
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
        return materialesCriticos;
    }

    public boolean descontarStockConfirmado(int idMaterial, int cantidad) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = conexion.getConnection();
            String sql = "UPDATE material SET CANTIDAD_DISPONIBLE = CANTIDAD_DISPONIBLE - ? WHERE ID_MATERIAL = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setInt(2, idMaterial);
            return ps.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al descontar stock: " + e.getMessage());
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
   public Modelo.ClaseMateriales[] leerMaterialesPorCampania(int idCampaniaSeleccionada) {
    Modelo.ClaseMateriales[] arregloMateriales = null;
    java.sql.Connection conn = null;
    java.sql.PreparedStatement ps = null;
    java.sql.ResultSet rs = null;

    try {
        conn = conexion.getConnection(); 
        String sqlCount = "SELECT COUNT(*) AS CANT FROM material WHERE ID_CAMPANIA IS NULL OR ID_CAMPANIA = ?";
        ps = conn.prepareStatement(sqlCount);
        ps.setInt(1, idCampaniaSeleccionada);
        rs = ps.executeQuery();
        
        int cantidadRegistros = 0;
        if (rs.next()) cantidadRegistros = rs.getInt("CANT");

        arregloMateriales = new Modelo.ClaseMateriales[cantidadRegistros];
        
        String sqlSelect = "SELECT * FROM material WHERE ID_CAMPANIA IS NULL OR ID_CAMPANIA = ?";
        ps = conn.prepareStatement(sqlSelect);
        ps.setInt(1, idCampaniaSeleccionada);
        rs = ps.executeQuery();
        
        int i = 0;
        while (rs.next()) {
            Modelo.ClaseMateriales mat = new Modelo.ClaseMateriales();
            mat.setIdMaterial(rs.getInt("ID_MATERIAL"));
            mat.setNombreMaterial(rs.getString("NOMBRE_MATERIAL")); 
            mat.setTipo(rs.getString("TIPO"));
            mat.setCantidadDisponible(rs.getInt("CANTIDAD_DISPONIBLE"));
            mat.setStockMinimo(rs.getInt("STOCK_MINIMO"));
            mat.setIdCampania(rs.getInt("ID_CAMPANIA"));
            
            arregloMateriales[i] = mat;
            i++;
        }
    } catch (Exception e) {
        System.err.println("Error al filtrar materiales: " + e.getMessage());
    } finally {
        try { if(rs!=null)rs.close(); if(ps!=null)ps.close(); if(conn!=null)conexion.closeConnection(conn); } catch(Exception ex) {}
    }
    return arregloMateriales;
}
   public boolean registrarMaterial(Modelo.ClaseMateriales mat) {
    java.sql.Connection conn = null;
    java.sql.PreparedStatement ps = null;
    
    // Usamos ID_CAMPANIA al final
    String sql = "INSERT INTO material (NOMBRE_MATERIAL, TIPO, CANTIDAD_DISPONIBLE, STOCK_MINIMO, ID_CAMPANIA) VALUES (?, ?, ?, ?, ?)";
    
    try {
        conn = conexion.getConnection();
        ps = conn.prepareStatement(sql);
        ps.setString(1, mat.getNombreMaterial());
        ps.setString(2, mat.getTipo());
        ps.setInt(3, mat.getCantidadDisponible());
        ps.setInt(4, mat.getStockMinimo());
        
        // Si el ID es 0, lo mandamos como NULL (Genérico)
        if (mat.getIdCampania() == 0) {
            ps.setNull(5, java.sql.Types.INTEGER);
        } else {
            ps.setInt(5, mat.getIdCampania());
        }
        
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al guardar material: " + e.getMessage());
        return false;
    } finally {
        try { if(ps != null) ps.close(); if(conn != null) conexion.closeConnection(conn); } catch(Exception ex) {}
    }
}
}
