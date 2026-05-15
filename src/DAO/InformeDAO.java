package DAO;

import java.sql.*;
import Connection.ConnectionManager;
import Aux.TurnoListado;
import java.util.ArrayList;

public class InformeDAO {

    public void insertarInforme(String codUnidad, int numInforme, Date fecha, Time hora,
            int pacientesAtendInf, int pacientesAlta, int pacientesAdmit,
            int pacientesRegist, int numTurno) {
        String sql = "SELECT insertar_informe(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setInt(2, numInforme);
            stmt.setDate(3, fecha);
            stmt.setTime(4, hora);
            stmt.setInt(5, pacientesAtendInf);
            stmt.setInt(6, pacientesAlta);
            stmt.setInt(7, pacientesAdmit);
            stmt.setInt(8, pacientesRegist);
            stmt.setInt(9, numTurno);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar informe: " + e.getMessage(), e);
        }
    }

    public int obtenerUltimoNumeroInforme(String codUnidad, int numTurno) {
        String sql = "SELECT COALESCE(MAX(numInforme), 0) AS ultimo FROM Informe WHERE codUnidad = ? AND numTurno = ?";
        int ultimo = 0;
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setInt(2, numTurno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ultimo = rs.getInt("ultimo");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener último informe: " + e.getMessage(), e);
        }
        return ultimo;
    }

    public int obtenerSumaAtendidosPrevios(String codUnidad, int numTurno) {
        String sql = "SELECT COALESCE(SUM(pacientesAtendInf), 0) AS suma FROM Informe WHERE codUnidad = ? AND numTurno = ?";
        int suma = 0;
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setInt(2, numTurno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                suma = rs.getInt("suma");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener suma de atendidos previos: " + e.getMessage(), e);
        }
        return suma;
    }

    public ArrayList<TurnoListado> informeDuranteConsultas(String codHospital, String codDpt, String codUnidad) {
        ArrayList<TurnoListado> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT h.nombreHosp AS hospital, d.nombreDpt AS departamento, u.nombreUnidad AS unidad, ");
        sql.append("t.numTurno, CAST(i.hora AS VARCHAR(5)) AS hora_informe, i.numInforme, ");
        sql.append("t.cantPacientes AS pacientes_inicio, i.pacientesAdmit, i.pacientesAlta, ");
        sql.append("i.pacientesAtendInf AS atendidos_desde_anterior, ");
        sql.append("(SELECT COALESCE(SUM(i2.pacientesAtendInf),0) FROM Informe i2 WHERE i2.codUnidad = i.codUnidad AND i2.numTurno = i.numTurno) AS atendidos_dia ");
        sql.append("FROM Informe i ");
        sql.append("JOIN Turno t ON i.codUnidad = t.codUnidad AND i.numTurno = t.numTurno ");
        sql.append("JOIN Unidad u ON i.codUnidad = u.codUnidad ");
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

        sql.append("ORDER BY u.nombreUnidad, t.numTurno, i.hora");

        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (codHospital != null && !codHospital.isEmpty()) {
                stmt.setString(idx++, codHospital);
            }
            if (codDpt != null && !codDpt.isEmpty()) {
                stmt.setString(idx++, codDpt);
            }
            if (codUnidad != null && !codUnidad.isEmpty()) {
                stmt.setString(idx++, codUnidad);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new TurnoListado(
                        rs.getString("hospital"),
                        rs.getString("departamento"),
                        rs.getString("unidad"),
                        rs.getInt("numTurno"),
                        rs.getString("hora_informe"),
                        rs.getInt("numInforme"),
                        rs.getInt("pacientes_inicio"),
                        rs.getInt("pacientesAdmit"),
                        rs.getInt("pacientesAlta"),
                        rs.getInt("atendidos_desde_anterior"),
                        rs.getInt("atendidos_dia")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en informe: " + e.getMessage(), e);
        }
        return lista;
    }
}
