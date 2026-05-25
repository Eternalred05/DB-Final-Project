package DAO;

import Aux.TurnoListado;
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

    public void incrementarPacientesAtendidos(String codUnidad, int numTurno) {
        String sql = "UPDATE Turno SET pacientesAtend = pacientesAtend + 1 "
                + "WHERE codUnidad = ? AND numTurno = ? AND pacientesAtend < cantPacientes";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setInt(2, numTurno);
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new RuntimeException("No se puede atender más pacientes de los asignados al turno.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar turno: " + e.getMessage(), e);
        }
    }

    public Turno obtenerTurno(String codUnidad, int numTurno) {
        Turno t = null;
        String sql = "SELECT numTurno, cantPacientes, pacientesAtend, codMedico, codUnidad "
                + "FROM Turno WHERE codUnidad = ? AND numTurno = ?";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setInt(2, numTurno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                t = new Turno(
                        rs.getInt("numTurno"),
                        rs.getInt("cantPacientes"),
                        rs.getInt("pacientesAtend"),
                        rs.getString("codMedico"),
                        rs.getString("codUnidad")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Turno no encontrado: " + e.getMessage());
        }
        return t;
    }

    public ArrayList<TurnoListado> listarTurnos() {
        ArrayList<TurnoListado> lista = new ArrayList<>();
        String sql = "SELECT h.nombreHosp AS hospital, d.nombreDpt AS departamento, u.nombreUnidad AS unidad, "
                + "t.numTurno, t.cantPacientes, t.pacientesAtend, m.nombreMed AS medico, t.codMedico, t.codUnidad "
                + "FROM Turno t "
                + "JOIN Doctor m ON t.codMedico = m.codMedico "
                + "JOIN Unidad u ON t.codUnidad = u.codUnidad "
                + "JOIN Departamento d ON u.codDpt = d.codDpt "
                + "JOIN Hospital h ON d.codHospital = h.codHospital "
                + "ORDER BY u.nombreUnidad, t.numTurno";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new TurnoListado(
                        rs.getString("hospital"),
                        rs.getString("departamento"),
                        rs.getString("unidad"),
                        rs.getInt("numTurno"),
                        rs.getInt("cantPacientes"),
                        rs.getInt("pacientesAtend"),
                        rs.getString("medico"),
                        rs.getString("codMedico"),
                        rs.getString("codUnidad")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar turnos: " + e.getMessage(), e);
        }
        return lista;
    }

    public void modificarTurno(String codUnidad, int numTurno, int cantPacientes,
            int pacientesAtend, String codMedico) {
        String sql = "SELECT modificar_turno(?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setInt(2, numTurno);
            stmt.setInt(3, cantPacientes);
            stmt.setInt(4, pacientesAtend);
            stmt.setString(5, codMedico);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar turno: " + e.getMessage(), e);
        }
    }

    public void eliminarTurno(String codUnidad, int numTurno) {
        String sql = "SELECT eliminar_turno(?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setInt(2, numTurno);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar turno: " + e.getMessage(), e);
        }
    }

    public int contarInformesDeTurno(String codUnidad, int numTurno) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Informe WHERE codUnidad = ? AND numTurno = ?";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setInt(2, numTurno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar informes: " + e.getMessage(), e);
        }
        return total;
    }
}
