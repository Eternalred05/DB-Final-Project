package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Connection.ConnectionManager;

public class HospitalDAO {

    public void insertarHospital(String codHospital, String nombreHospital) {
        String sql = "SELECT insertar_hospital(?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codHospital);
            stmt.setString(2, nombreHospital);
            stmt.execute();
        } catch (SQLException e) {

            throw new RuntimeException("Error al insertar hospital: " + e.getMessage(), e);
        }
    }
}
