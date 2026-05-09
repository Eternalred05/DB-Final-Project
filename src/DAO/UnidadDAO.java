package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Connection.ConnectionManager;

public class UnidadDAO {

    public void insertarUnidad(String codUnidad, String nombreUnidad, String ubicacion, String codDpt) {
        String sql = "SELECT insertar_unidad(?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setString(2, nombreUnidad);
            stmt.setString(3, ubicacion);
            stmt.setString(4, codDpt);
            stmt.execute();
        } catch (SQLException e) {

            throw new RuntimeException("Error al insertar unidad " + e.getMessage(), e);
        }
    }

    
}
