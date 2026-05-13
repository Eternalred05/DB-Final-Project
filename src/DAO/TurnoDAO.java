package DAO;

import java.sql.*;
import Connection.ConnectionManager;
import Logic.Turno;
import java.util.ArrayList;

public class TurnoDAO {

    public void insertarTurno(String codUnidad, int numTurno, int cantPacientes,
            int pacientesAtend, String codMedico) {
        String sql = "SELECT insertar_turno(?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setInt(2, numTurno);
            stmt.setInt(3, cantPacientes);
            stmt.setInt(4, pacientesAtend);
            stmt.setString(5, codMedico);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar turno: " + e.getMessage(), e);
        }
    }

    public ArrayList<Turno> listarTurnosPorUnidad(String codUnidad) {
        ArrayList<Turno> lista = new ArrayList<>();
        String sql = "SELECT numTurno, cantPacientes, pacientesAtend, codMedico, codUnidad "
                + "FROM Turno WHERE codUnidad = ? ORDER BY numTurno";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Turno(
                        rs.getInt("numTurno"),
                        rs.getInt("cantPacientes"),
                        rs.getInt("pacientesAtend"),
                        rs.getString("codMedico"),
                        rs.getString("codUnidad")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar turnos: " + e.getMessage(), e);
        }
        return lista;
    }
}
