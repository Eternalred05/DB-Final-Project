package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Connection.ConnectionManager;
import java.sql.ResultSet;
import Logic.Hospital;
import java.util.ArrayList;

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

    public ArrayList<Hospital> listarHospitales() {
        ArrayList<Hospital> lista = new ArrayList<>();
        String sql = "SELECT codHospital, nombreHosp FROM Hospital ORDER BY nombreHosp";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String cod = rs.getString("codHospital");
                String nom = rs.getString("nombreHosp");
                lista.add(new Hospital(nom, cod));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar hospitales: " + e.getMessage(), e);
        }
        return lista;
    }

    public Hospital obtenerHospitalPorPosicion(int posicion) {
        Hospital h = null;
        String sql = "SELECT codHospital, nombreHosp FROM Hospital ORDER BY nombreHosp LIMIT 1 OFFSET ?";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, posicion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                h = new Hospital(rs.getString("nombreHosp"), rs.getString("codHospital"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener hospital: " + e.getMessage(), e);
        }
        return h;
    }
}
