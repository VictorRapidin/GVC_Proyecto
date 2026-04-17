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
import Modelo.ClaseUsuario;
import Modelo.ConexionBD;

/**
 *
 * @author chane
 */
public class ControladorUsuario {

    private ConexionBD conexion = new ConexionBD();
    private int cantRegistros;

    public ControladorUsuario() {
        conexion = new ConexionBD();
        this.cantRegistros = 0;
    }

    private static final String INSERT_USUARIO
            = "INSERT INTO usuarios(nombre, apellido, correo, password, activo, rol) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_USUARIOS
            = "SELECT * FROM usuarios";

    private static final String SELECT_COUNT_USUARIOS
            = "SELECT COUNT(*) AS CANT FROM usuarios";

    private static final String UPDATE_USUARIO
            = "UPDATE usuarios SET nombre=?, apellido=?, correo=?, password=?, activo=?, rol=? WHERE id_usuario=?";

    private static final String DELETE_USUARIO
            = "DELETE FROM usuarios WHERE id_usuario=?";

    private static final String SELECT_USUARIO_BY_ID
            = "SELECT * FROM usuarios WHERE id_usuario=?";

    private static final String LOGIN_QUERY
            = "SELECT * FROM usuarios WHERE correo=? AND password=? AND activo=1";

    public int getCantRegistros() {
        return cantRegistros;
    }

    public void setCantRegistros(int cantRegistros) {
        this.cantRegistros = cantRegistros;
    }

    public boolean registrarUsuario(ClaseUsuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(INSERT_USUARIO);

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getPassword());
            ps.setBoolean(5, usuario.isActivo());
            ps.setString(6, usuario.getRol());

            return ps.executeUpdate() > 0;

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear Usuario: " + e.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE);
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

    public ClaseUsuario[] leerUsuarios() {
        ClaseUsuario[] aUsuarios = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cantRegistros = 0;

        try {
            conn = conexion.getConnection();

            ps = conn.prepareStatement(SELECT_COUNT_USUARIOS);
            rs = ps.executeQuery();
            if (rs.next()) {
                cantRegistros = rs.getInt("CANT");
            }

            rs.close();
            ps.close();

            aUsuarios = new ClaseUsuario[cantRegistros];

            ps = conn.prepareStatement(SELECT_ALL_USUARIOS);
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next()) {
                ClaseUsuario usuario = new ClaseUsuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setPassword(rs.getString("password"));
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setRol(rs.getString("rol"));

                aUsuarios[i] = usuario;
                i++;
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer información: " + e.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE);
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
        return aUsuarios;
    }

    public boolean actualizarUsuario(ClaseUsuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(UPDATE_USUARIO);

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getPassword());
            ps.setBoolean(5, usuario.isActivo());
            ps.setString(6, usuario.getRol());
            ps.setInt(7, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conexion.closeConnection(conn);
                }
            } catch (SQLException e) {
            }
        }
    }

    public boolean eliminarUsuario(int id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = conexion.getConnection();
            ps = conn.prepareStatement(DELETE_USUARIO);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE);
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

    public ClaseUsuario login(String correo, String password) {
        ClaseUsuario usuario = null;
        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(LOGIN_QUERY)) {

            ps.setString(1, correo);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new ClaseUsuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setActivo(rs.getBoolean("activo"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en login: " + e.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE);
        }
        return usuario;
    }

    public void abrirEspacioRC(int ruta, String nuevoRc) {
        String sql = "UPDATE ubicaciones SET RC = CAST(RC AS UNSIGNED) + 1 "
                + "WHERE RUTA = ? AND CAST(RC AS UNSIGNED) >= ? AND ESTADO != 'INACTIVA' "
                + "ORDER BY CAST(RC AS UNSIGNED) DESC";
        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ruta);
            ps.setInt(2, Integer.parseInt(nuevoRc.trim()));
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al abrir espacio: " + e.getMessage());
        }
    }
}
