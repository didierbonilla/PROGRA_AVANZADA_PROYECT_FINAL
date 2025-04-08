package programacion.avanzada.programacion_avanzada_project.repositories;

import javafx.scene.control.Alert;
import programacion.avanzada.programacion_avanzada_project.Alertas;
import programacion.avanzada.programacion_avanzada_project.models.UsuarioModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosRepository {

    private final Connection conn;

    public UsuariosRepository(Connection conn) {
        this.conn = conn;
    }

    public List<UsuarioModel> list() {
        List<UsuarioModel> usuarios = new ArrayList<>();
        String sql = "SELECT USUARIO_ID, USUARIO, NOMBRE, CLAVE, ROL, EMAIL FROM TBL_USUARIOS";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(new UsuarioModel(
                        rs.getInt("USUARIO_ID"),
                        rs.getString("USUARIO"),
                        rs.getString("NOMBRE"),
                        rs.getString("CLAVE"),
                        rs.getString("ROL"),
                        rs.getString("EMAIL")
                ));
            }

        } catch (SQLException e) {
            Alertas.error("Error Interno",e.getMessage());
        }

        return usuarios;
    }

    public boolean create(UsuarioModel usuario) {
        String sql = "INSERT INTO TBL_USUARIOS (USUARIO, NOMBRE, CLAVE, ROL, EMAIL) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getClave());
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getEmail());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Alertas.error("Error Interno",e.getMessage());
            return false;
        }
    }

    public boolean update(UsuarioModel usuario) {
        String sql = "UPDATE TBL_USUARIOS SET USUARIO = ?, NOMBRE = ?, CLAVE = ?, EMAIL = ? WHERE USUARIO_ID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getClave());
            stmt.setString(4, usuario.getEmail());
            stmt.setInt(5, usuario.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Alertas.error("Error Interno",e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM TBL_USUARIOS WHERE USUARIO_ID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Alertas.error("Error Interno",e.getMessage());
            return false;
        }
    }

    public UsuarioModel filterByUserName(String nombreBuscado) {
        UsuarioModel usuario = null;
        String sql = "SELECT USUARIO_ID, USUARIO, NOMBRE, CLAVE, ROL, EMAIL FROM TBL_USUARIOS WHERE USUARIO LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombreBuscado + "%");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new UsuarioModel(
                    rs.getInt("USUARIO_ID"),
                    rs.getString("USUARIO"),
                    rs.getString("NOMBRE"),
                    rs.getString("CLAVE"),
                    rs.getString("ROL"),
                    rs.getString("EMAIL")
                );
            }

        } catch (SQLException e) {
            Alertas.error("Error Interno",e.getMessage());
        }

        return usuario;
    }

    public UsuarioModel filterByEmail(String email) {
        UsuarioModel usuario = null;
        String sql = "SELECT USUARIO_ID, USUARIO, NOMBRE, CLAVE, ROL, EMAIL FROM TBL_USUARIOS WHERE EMAIL = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new UsuarioModel(
                    rs.getInt("USUARIO_ID"),
                    rs.getString("USUARIO"),
                    rs.getString("NOMBRE"),
                    rs.getString("CLAVE"),
                    rs.getString("ROL"),
                    rs.getString("EMAIL")
                );
            }

        } catch (SQLException e) {
            Alertas.error("Error Interno",e.getMessage());
        }

        return usuario;
    }
}


