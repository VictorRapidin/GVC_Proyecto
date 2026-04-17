/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JOptionPane;
import Modelo.ClaseUbicaciones;
import Modelo.ConexionBD;

/**
 *
 * @author porqu
 */
public class ControladorUbicaciones {

    private ConexionBD conexion = new ConexionBD();
    private int cantRegistros;

    public ControladorUbicaciones() {
        conexion = new ConexionBD();
        this.cantRegistros = 0;
    }
    private static final String SELECT_ALL_UBICACIONES
            = "SELECT * FROM ubicaciones";
    private static final String INSERT_UBICACION
            = "INSERT INTO ubicaciones(RUTA, RC, CODIGO, CALLE_1, CALLE_2, SENTIDO, ESTADO, FECHA_INSTALACION, TIPO_UBICACION) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_UBICACION
            = "UPDATE ubicaciones SET RUTA=?, RC=?, CODIGO=?, CALLE_1=?, CALLE_2=?, SENTIDO=?, ESTADO=?, FECHA_INSTALACION=?, TIPO_UBICACION=? WHERE ID_UBICACION=?";
    private static final String HACER_ESPACIO_RC
            = "UPDATE ubicaciones SET RC = CAST(RC AS UNSIGNED) + 1 WHERE RUTA = ? AND CAST(RC AS UNSIGNED) >= ? ORDER BY CAST(RC AS UNSIGNED) DESC";

    public boolean registrarUbicacion(Modelo.ClaseUbicaciones u) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // --- LA MAGIA ESTRELLA: Abrimos el hueco antes de guardar ---
            abrirEspacioRC(u.getRuta(), u.getRc());

            // --- Ahora sí, guardamos el nuevo registro ---
            conn = conexion.getConnection();
            ps = conn.prepareStatement(INSERT_UBICACION);

            ps.setInt(1, u.getRuta());
            ps.setString(2, u.getRc());
            ps.setString(3, u.getCodigo());
            ps.setString(4, u.getCalle1());
            ps.setString(5, u.getCalle2());
            ps.setString(6, u.getSentido());
            ps.setString(7, u.getEstado());
            ps.setString(8, u.getFechaInstalacion());
            ps.setString(9, u.getTipoUbicacion());

            // --- MODIFICACIÓN: Guardamos el resultado y aplicamos limpieza total ---
            boolean exito = ps.executeUpdate() > 0;

            if (exito) {
                // AUTO-LIMPIEZA TOTAL: Cierra cualquier hueco en la ruta
                reordenarRutaCompleta(u.getRuta());
            }

            return exito;

        } catch (ClassNotFoundException | SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
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

    public boolean actualizarUbicacion(Modelo.ClaseUbicaciones u) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = conexion.getConnection();

            // --- 1. INVESTIGAR DÓNDE ESTABA EL MUEBLE ANTES DEL CAMBIO ---
            int rutaVieja = -1;
            String rcViejo = "";
            String sqlViejo = "SELECT RUTA, RC FROM ubicaciones WHERE ID_UBICACION = ?";
            ps = conn.prepareStatement(sqlViejo);
            ps.setInt(1, u.getIdUbicacion());
            rs = ps.executeQuery();
            if (rs.next()) {
                rutaVieja = rs.getInt("RUTA");
                rcViejo = rs.getString("RC");
            }
            rs.close();
            ps.close();

            // --- 2. LÓGICA ESTRELLA: ¿CAMBIÓ DE LUGAR? ---
            if (rutaVieja != u.getRuta() || (rcViejo != null && !rcViejo.equals(u.getRc()))) {
                if (rcViejo != null && !rcViejo.isEmpty()) {
                    cerrarEspacioRC(rutaVieja, rcViejo);
                }

                // Paso B: Abrimos el hueco en su nueva ruta/posición
                abrirEspacioRC(u.getRuta(), u.getRc());
            }

            // --- 3. AHORA SÍ, ACTUALIZAMOS LOS DATOS DEL MUEBLE ---
            ps = conn.prepareStatement(UPDATE_UBICACION);

            ps.setInt(1, u.getRuta());
            ps.setString(2, u.getRc());
            ps.setString(3, u.getCodigo());
            ps.setString(4, u.getCalle1());
            ps.setString(5, u.getCalle2());
            ps.setString(6, u.getSentido());
            ps.setString(7, u.getEstado());
            ps.setString(8, u.getFechaInstalacion());
            ps.setString(9, u.getTipoUbicacion());
            ps.setInt(10, u.getIdUbicacion()); // WHERE ID

            // --- MODIFICACIÓN: Guardamos el resultado y aplicamos auto-curación ---
            boolean actualizado = ps.executeUpdate() > 0;

            if (actualizado) {
                // Si lo cambiaron de ruta, cerramos los huecos que dejó en la ruta vieja
                if (rutaVieja != u.getRuta() && rutaVieja != -1) {
                    reordenarRutaCompleta(rutaVieja);
                }
                // Siempre reordenamos la ruta nueva/actual para que quede perfecta
                reordenarRutaCompleta(u.getRuta());
            }

            return actualizado;

        } catch (ClassNotFoundException | SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al actualizar y reordenar: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
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
    }

    public ClaseUbicaciones[] leerUbicaciones() {
        ClaseUbicaciones[] aUbicaciones = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = conexion.getConnection();

            ps = conn.prepareStatement("SELECT COUNT(*) AS CANT FROM ubicaciones");
            rs = ps.executeQuery();
            if (rs.next()) {
                cantRegistros = rs.getInt("CANT");
            }
            rs.close();
            ps.close();
            aUbicaciones = new ClaseUbicaciones[cantRegistros];
            ps = conn.prepareStatement(SELECT_ALL_UBICACIONES);
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next()) {
                ClaseUbicaciones u = new ClaseUbicaciones();

                u.setIdUbicacion(rs.getInt("ID_UBICACION"));
                u.setRuta(rs.getInt("RUTA"));
                u.setRc(rs.getString("RC"));
                u.setCodigo(rs.getString("CODIGO"));
                u.setCalle1(rs.getString("CALLE_1"));
                u.setCalle2(rs.getString("CALLE_2"));
                u.setSentido(rs.getString("SENTIDO"));
                u.setEstado(rs.getString("ESTADO"));
                u.setFechaInstalacion(rs.getString("FECHA_INSTALACION"));
                u.setTipoUbicacion(rs.getString("TIPO_UBICACION"));
                aUbicaciones[i] = u;
                i++;
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer ubicaciones: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
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
        return aUbicaciones;
    }

    public boolean eliminarUbicacion(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement("DELETE FROM ubicaciones WHERE ID_UBICACION=?");
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
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

    public boolean actualizarEstadoUbicacion(int id, String nuevoEstado) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE ubicaciones SET ESTADO = ? WHERE ID_UBICACION = ?";
        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cambiar estado: " + e.getMessage());
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

    // Método para traer las rutas que tienen muebles registrados
    public java.util.ArrayList<String> obtenerRutasUnicas() {
        java.util.ArrayList<String> lista = new java.util.ArrayList<>();
        lista.add("Todas las Rutas"); // Opción por defecto
        String sql = "SELECT DISTINCT RUTA FROM ubicaciones ORDER BY RUTA ASC";
        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(rs.getString("RUTA"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

// Método para traer los tipos de muebles que existen en la BD
    public java.util.ArrayList<String> obtenerTiposUnicos() {
        java.util.ArrayList<String> lista = new java.util.ArrayList<>();
        lista.add("Todos los Tipos"); // Opción por defecto
        String sql = "SELECT DISTINCT TIPO_UBICACION FROM ubicaciones ORDER BY TIPO_UBICACION ASC";
        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(rs.getString("TIPO_UBICACION"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 1. Método para EMPUJAR hacia abajo (Al registrar o reactivar)
    public void abrirEspacioRC(int ruta, String nuevoRc) {
        // La consulta blindada para ignorar vacíos
        String sql = "UPDATE ubicaciones SET RC = CAST(RC AS UNSIGNED) + 1 "
                + "WHERE RUTA = ? AND RC != '' AND RC IS NOT NULL AND CAST(RC AS UNSIGNED) >= ? AND ESTADO != 'INACTIVA' "
                + "ORDER BY CAST(RC AS UNSIGNED) DESC";
        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ruta);
            ps.setInt(2, Integer.parseInt(nuevoRc.trim()));
            ps.executeUpdate();

        } catch (Exception e) {
            // ALARMA PRENDIDA: Si MySQL llora, nos va a salir esta ventana
            javax.swing.JOptionPane.showMessageDialog(null, "Error interno al recorrer números (Abrir): " + e.getMessage(), "Error SQL", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // 2. Método para JALAR hacia arriba (Al dar de baja)
    public void cerrarEspacioRC(int ruta, String rcEliminado) {
        String sql = "UPDATE ubicaciones SET RC = CAST(RC AS UNSIGNED) - 1 "
                + "WHERE RUTA = ? AND RC != '' AND RC IS NOT NULL AND CAST(RC AS UNSIGNED) > ? AND ESTADO != 'INACTIVA'";
        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ruta);
            ps.setInt(2, Integer.parseInt(rcEliminado.trim()));
            ps.executeUpdate();

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error interno al recorrer números (Cerrar): " + e.getMessage(), "Error SQL", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // EL DESFRAGMENTADOR: Elimina repetidos y ordena la ruta
    public void repararDuplicadosRC(int ruta) {
        Connection conn = null;
        PreparedStatement psSelect = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;

        try {
            conn = conexion.getConnection();
            String sqlSelect = "SELECT ID_UBICACION, RC FROM ubicaciones "
                    + "WHERE RUTA = ? AND RC != '' AND RC IS NOT NULL AND ESTADO != 'INACTIVA' "
                    + "ORDER BY CAST(RC AS UNSIGNED) ASC, ID_UBICACION ASC";

            psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setInt(1, ruta);
            rs = psSelect.executeQuery();
            psUpdate = conn.prepareStatement("UPDATE ubicaciones SET RC = ? WHERE ID_UBICACION = ?");

            int rcAnterior = -1;
            while (rs.next()) {
                int id = rs.getInt("ID_UBICACION");
                int currentRC = Integer.parseInt(rs.getString("RC").trim());
                if (currentRC <= rcAnterior) {
                    currentRC = rcAnterior + 1;
                    psUpdate.setString(1, String.valueOf(currentRC));
                    psUpdate.setInt(2, id);
                    psUpdate.executeUpdate();
                }
                rcAnterior = currentRC;
            }

        } catch (Exception e) {
            System.out.println("Error al reparar duplicados: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psSelect != null) {
                    psSelect.close();
                }
                if (psUpdate != null) {
                    psUpdate.close();
                }
                if (conn != null) {
                    conexion.closeConnection(conn);
                }
            } catch (SQLException ex) {
            }
        }
    }

    public void reordenarRutaCompleta(int ruta) {
        Connection conn = null;
        PreparedStatement psSelect = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;

        try {
            conn = conexion.getConnection();

            // 1. Traemos todos los muebles activos de la ruta en el orden que tengan ahorita
            String sqlSelect = "SELECT ID_UBICACION FROM ubicaciones "
                    + "WHERE RUTA = ? AND ESTADO != 'INACTIVA' "
                    + "ORDER BY CAST(RC AS UNSIGNED) ASC, ID_UBICACION ASC";

            psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setInt(1, ruta);
            rs = psSelect.executeQuery();

            psUpdate = conn.prepareStatement("UPDATE ubicaciones SET RC = ? WHERE ID_UBICACION = ?");

            int contador = 1; // Este es nuestro sargento que va a dar los nuevos números

            // 2. Le asignamos a cada uno su nuevo lugar pegadito al anterior
            while (rs.next()) {
                int id = rs.getInt("ID_UBICACION");

                psUpdate.setString(1, String.valueOf(contador));
                psUpdate.setInt(2, id);
                psUpdate.executeUpdate();

                contador++; // Siguiente número sin dejar huecos
            }

        } catch (Exception e) {
            System.out.println("Error en Reordenamiento Maestro: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psSelect != null) {
                    psSelect.close();
                }
                if (psUpdate != null) {
                    psUpdate.close();
                }
                if (conn != null) {
                    conexion.closeConnection(conn);
                }
            } catch (SQLException ex) {
            }
        }
    }
}
