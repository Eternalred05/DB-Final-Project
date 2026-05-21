package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import Connection.ConnectionManager;
import Logic.Paciente;
import Aux.PacienteListado;
import Aux.PacienteNoAtendido;

public class PacienteDAO {

    public void insertarPaciente(String numHist, String nombre, String dir, Date fecha,
            String causa, boolean atendido, String codUnidad) {
        String sql = "SELECT insertar_paciente(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setString(2, numHist);
            stmt.setString(3, nombre);
            stmt.setString(4, dir);
            stmt.setDate(5, fecha);
            stmt.setBoolean(6, atendido);
            stmt.setString(7, causa);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar paciente: " + e.getMessage(), e);
        }
    }

    public void modificarPaciente(String codUnidad, String numHistClinica, String nombre,
            String direccion, Date nacimiento, boolean atendido, String causa) {
        String sql = "SELECT modificar_paciente(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setString(2, numHistClinica);
            stmt.setString(3, nombre);
            stmt.setString(4, direccion);
            stmt.setDate(5, nacimiento);
            stmt.setBoolean(6, atendido);
            stmt.setString(7, causa);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar paciente: " + e.getMessage(), e);
        }
    }

    public void eliminarPaciente(String codUnidad, String numHistClinica) {
        String sql = "SELECT eliminar_paciente(?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setString(2, numHistClinica);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar paciente: " + e.getMessage(), e);
        }
    }

    public ArrayList<Paciente> listarPaciente() {
        ArrayList<Paciente> lista = new ArrayList<>();
        String sql = "SELECT codUnidad, numHistClinica, nombrePac, direccion, nacimiento, atendido, causa "
                + "FROM Paciente ORDER BY nombrePac";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nombre = rs.getString("nombrePac");
                String numHistClinica = rs.getString("numHistClinica");
                String dir = rs.getString("direccion");
                Date fecha = rs.getDate("nacimiento");
                boolean atendido = rs.getBoolean("atendido");
                String causa = rs.getString("causa");
                String codUnidad = rs.getString("codUnidad");
                lista.add(new Paciente(nombre, numHistClinica, dir, fecha, codUnidad, atendido, causa));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pacientes: " + e.getMessage(), e);
        }
        return lista;
    }

    public ArrayList<PacienteListado> listarPacientesReporte(String codHospital, String codDpt, String codUnidad) {
        ArrayList<PacienteListado> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT h.nombreHosp AS hospital, d.nombreDpt AS departamento, u.nombreUnidad AS unidad, ");
        sql.append("p.numHistClinica, p.nombrePac, TO_CHAR(p.nacimiento, 'DD/MM/YYYY') AS nacimiento, p.direccion, ");
        sql.append("p.codUnidad ");
        sql.append("FROM Paciente p ");
        sql.append("JOIN Unidad u ON p.codUnidad = u.codUnidad ");
        sql.append("JOIN Departamento d ON u.codDpt = d.codDpt ");
        sql.append("JOIN Hospital h ON d.codHospital = h.codHospital ");
        sql.append("WHERE 1=1 ");

        if (codHospital != null && !codHospital.isEmpty()) {
            sql.append("AND h.codHospital = ? ");
        }
        if (codDpt != null && !codDpt.isEmpty()) {
            sql.append("AND d.codDpt = ? ");
        }
        if (codUnidad != null && !codUnidad.isEmpty()) {
            sql.append("AND u.codUnidad = ? ");
        }

        sql.append("ORDER BY u.nombreUnidad, p.nombrePac");

        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (codHospital != null && !codHospital.isEmpty()) {
                stmt.setString(index++, codHospital);
            }
            if (codDpt != null && !codDpt.isEmpty()) {
                stmt.setString(index++, codDpt);
            }
            if (codUnidad != null && !codUnidad.isEmpty()) {
                stmt.setString(index++, codUnidad);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new PacienteListado(
                        rs.getString("hospital"),
                        rs.getString("departamento"),
                        rs.getString("unidad"),
                        rs.getString("numHistClinica"),
                        rs.getString("nombrePac"),
                        rs.getString("nacimiento"),
                        rs.getString("direccion"),
                        rs.getString("codUnidad")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pacientes del reporte: " + e.getMessage(), e);
        }
        return lista;
    }

    public ArrayList<Paciente> listarPacientesNoAtendidos(String codUnidad) {
        ArrayList<Paciente> lista = new ArrayList<>();
        String sql = "SELECT codUnidad, numHistClinica, nombrePac, direccion, nacimiento, atendido, causa "
                + "FROM Paciente WHERE codUnidad = ? AND atendido = FALSE ORDER BY nombrePac";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Paciente(
                        rs.getString("nombrePac"),
                        rs.getString("numHistClinica"),
                        rs.getString("direccion"),
                        rs.getDate("nacimiento"),
                        rs.getString("codUnidad"),
                        rs.getBoolean("atendido"),
                        rs.getString("causa")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pacientes no atendidos: " + e.getMessage(), e);
        }
        return lista;
    }

    public ArrayList<PacienteNoAtendido> listarPacientesNoAtendidos(String codUnidad, int numTurno) {
        ArrayList<PacienteNoAtendido> lista = new ArrayList<>();
        String sql = "SELECT numHistClinica, nombrePac, direccion, causa FROM Paciente "
                + "WHERE codUnidad = ? AND atendido = FALSE ORDER BY nombrePac";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String causa = rs.getString("causa");
                if (causa == null) {
                    causa = "Sin causa registrada";
                }
                String turnoStr = (numTurno == -1) ? "N/A" : String.valueOf(numTurno);
                lista.add(new PacienteNoAtendido(
                        rs.getString("numHistClinica"),
                        rs.getString("nombrePac"),
                        rs.getString("direccion"),
                        causa,
                        turnoStr
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pacientes no atendidos: " + e.getMessage(), e);
        }
        return lista;
    }

    public int contarPacientesNoAtendidos(String codUnidad) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Paciente WHERE codUnidad = ? AND atendido = FALSE";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar no atendidos: " + e.getMessage(), e);
        }
        return total;
    }

    public int contarPacientesEnUnidad(String codUnidad) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Paciente WHERE codUnidad = ?";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar pacientes: " + e.getMessage(), e);
        }
        return total;
    }
}
