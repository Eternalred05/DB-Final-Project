package DAO;

import Connection.ConnectionManager;
import Logic.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public Usuario autenticar(String usuario, String contrasena) {
        Usuario u = null;
        String sql = "SELECT idUsuario, nombre, usuario, contrasena, admin FROM Usuario WHERE usuario = ? AND contrasena = ?";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                u = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getBoolean("admin")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al autenticar: " + e.getMessage(), e);
        }
        return u;
    }
}
