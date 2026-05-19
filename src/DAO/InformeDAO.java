package DAO;

import Aux.ResumenProceso;
import java.sql.*;
import Connection.ConnectionManager;
import Aux.TurnoLista;
import Aux.UnidadExitosa;
import Aux.UnidadRevision;
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

    public ArrayList<TurnoLista> informeDuranteConsultas(String codHospital, String codDpt, String codUnidad) {
        ArrayList<TurnoLista> lista = new ArrayList<>();
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
                lista.add(new TurnoLista(
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

    public ArrayList<ResumenProceso> resumenProceso(String codHospital, String codDpt, String codUnidad, int numTurno) {
        ArrayList<ResumenProceso> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT h.nombreHosp AS hospital, d.nombreDpt AS departamento, u.nombreUnidad AS unidad, ");
        sql.append("t.numTurno, CAST(MAX(i.hora) AS VARCHAR(5)) AS hora_informe, ");
        sql.append("t.cantPacientes AS inicio, t.pacientesAtend AS atendidos, ");
        sql.append("(t.cantPacientes + COALESCE(SUM(i.pacientesAdmit),0)) AS total_pacientes, ");
        sql.append("CASE WHEN (t.cantPacientes + COALESCE(SUM(i.pacientesAdmit),0)) > 0 ");
        sql.append("     THEN ROUND((t.pacientesAtend::NUMERIC / (t.cantPacientes + COALESCE(SUM(i.pacientesAdmit),0))) * 100, 2) ");
        sql.append("     ELSE 0 END AS porcentaje, ");
        sql.append("(t.cantPacientes - t.pacientesAtend) AS no_atendidos, ");
        sql.append("COALESCE(MAX(i.pacientesAlta),0) AS altas, ");
        sql.append("(SELECT COUNT(*) FROM Paciente p WHERE p.codUnidad = u.codUnidad AND p.atendido = FALSE AND p.causa ILIKE '%extranjero%') AS extranjero, ");
        sql.append("(SELECT COUNT(*) FROM Paciente p WHERE p.codUnidad = u.codUnidad AND p.atendido = FALSE AND p.causa ILIKE '%fuera de la provincia%') AS fuera_provincia, ");
        sql.append("(SELECT COUNT(*) FROM Paciente p WHERE p.codUnidad = u.codUnidad AND p.atendido = FALSE AND p.causa ILIKE '%hospitalizado en otra unidad%') AS hospitalizados, ");
        sql.append("(SELECT COUNT(*) FROM Paciente p WHERE p.codUnidad = u.codUnidad AND p.atendido = FALSE AND p.causa IS NOT NULL AND p.causa NOT ILIKE ANY(ARRAY['%extranjero%','%fuera de la provincia%','%hospitalizado en otra unidad%'])) AS otras_causas, ");
        sql.append("(SELECT COUNT(*) FROM Paciente p WHERE p.codUnidad = u.codUnidad AND p.atendido = FALSE AND p.causa IS NULL) AS desconocida ");
        sql.append("FROM Turno t ");
        sql.append("JOIN Unidad u ON t.codUnidad = u.codUnidad ");
        sql.append("JOIN Departamento d ON u.codDpt = d.codDpt ");
        sql.append("JOIN Hospital h ON d.codHospital = h.codHospital ");
        sql.append("LEFT JOIN Informe i ON i.codUnidad = t.codUnidad AND i.numTurno = t.numTurno ");
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
        if (numTurno != -1) {
            sql.append("AND t.numTurno = ? ");
        }

        sql.append("GROUP BY h.nombreHosp, d.nombreDpt, u.nombreUnidad, t.numTurno, t.cantPacientes, t.pacientesAtend, u.codUnidad ");
        sql.append("ORDER BY u.nombreUnidad, t.numTurno");

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
            if (numTurno != -1) {
                stmt.setInt(idx++, numTurno);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String hora = rs.getString("hora_informe");
                if (hora == null) {
                    hora = "Sin informe";
                }
                lista.add(new ResumenProceso(
                        rs.getString("hospital"),
                        rs.getString("departamento"),
                        rs.getString("unidad"),
                        rs.getInt("numTurno"),
                        hora,
                        rs.getInt("inicio"),
                        rs.getInt("atendidos"),
                        rs.getInt("total_pacientes"),
                        rs.getDouble("porcentaje"),
                        rs.getInt("no_atendidos"),
                        rs.getInt("altas"),
                        rs.getInt("extranjero"),
                        rs.getInt("fuera_provincia"),
                        rs.getInt("hospitalizados"),
                        rs.getInt("otras_causas"),
                        rs.getInt("desconocida")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en resumen del proceso: " + e.getMessage(), e);
        }
        return lista;
    }

    public ArrayList<UnidadRevision> unidadesRevisarTurnos(String codHospital, String codDpt, String codUnidad) {
        ArrayList<UnidadRevision> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("WITH unidades_revisar AS ( ");
        sql.append("  SELECT DISTINCT t.codUnidad ");
        sql.append("  FROM Turno t ");
        sql.append("  WHERE t.pacientesAtend < 0.8 * t.cantPacientes ");
        sql.append("), ");
        sql.append("totales_unidad AS ( ");
        sql.append("  SELECT t.codUnidad, SUM(t.cantPacientes) AS total_asignados, SUM(t.pacientesAtend) AS total_atendidos ");
        sql.append("  FROM Turno t ");
        sql.append("  JOIN unidades_revisar ur ON t.codUnidad = ur.codUnidad ");
        sql.append("  GROUP BY t.codUnidad ");
        sql.append(") ");
        sql.append("SELECT h.nombreHosp AS hospital, d.nombreDpt AS departamento, u.nombreUnidad AS unidad, ");
        sql.append("tu.total_asignados AS total_pacientes, m.nombreMed AS medico, ");
        sql.append("SUM(t.pacientesAtend) AS atendidos_medico, ");
        sql.append("ROUND(SUM(t.pacientesAtend) * 100.0 / tu.total_atendidos, 2) AS porcentaje ");
        sql.append("FROM Turno t ");
        sql.append("JOIN Doctor m ON t.codMedico = m.codMedico ");
        sql.append("JOIN Unidad u ON t.codUnidad = u.codUnidad ");
        sql.append("JOIN Departamento d ON u.codDpt = d.codDpt ");
        sql.append("JOIN Hospital h ON d.codHospital = h.codHospital ");
        sql.append("JOIN totales_unidad tu ON t.codUnidad = tu.codUnidad ");
        sql.append("JOIN unidades_revisar ur ON t.codUnidad = ur.codUnidad ");
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

        sql.append("GROUP BY h.nombreHosp, d.nombreDpt, u.nombreUnidad, tu.total_asignados, tu.total_atendidos, m.nombreMed ");
        sql.append("ORDER BY u.nombreUnidad, m.nombreMed");

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
                lista.add(new UnidadRevision(
                        rs.getString("hospital"),
                        rs.getString("departamento"),
                        rs.getString("unidad"),
                        rs.getInt("total_pacientes"),
                        rs.getString("medico"),
                        rs.getInt("atendidos_medico"),
                        rs.getDouble("porcentaje")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al generar listado de revisión: " + e.getMessage(), e);
        }
        return lista;
    }

    public ArrayList<UnidadExitosa> resumenConsultasExitosas(String codHospital, String codDpt, String codUnidad) {
        ArrayList<UnidadExitosa> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("WITH unidades_exitosas AS ( ");
        sql.append("  SELECT t.codUnidad ");
        sql.append("  FROM Turno t ");
        sql.append("  GROUP BY t.codUnidad ");
        sql.append("  HAVING MIN(1.0 * t.pacientesAtend / NULLIF(t.cantPacientes,0)) >= 0.8 ");
        sql.append("), ");
        sql.append("totales_medico AS ( ");
        sql.append("  SELECT t.codMedico, ue.codUnidad, SUM(t.pacientesAtend) AS total_atendidos ");
        sql.append("  FROM Turno t ");
        sql.append("  JOIN unidades_exitosas ue ON t.codUnidad = ue.codUnidad ");
        sql.append("  GROUP BY t.codMedico, ue.codUnidad ");
        sql.append(") ");
        sql.append("SELECT h.nombreHosp AS hospital, d.nombreDpt AS departamento, u.nombreUnidad AS unidad, ");
        sql.append("t.numTurno, doc.nombreMed AS medico, t.pacientesAtend AS atendidos_turno, ");
        sql.append("tm.total_atendidos AS total_medico ");
        sql.append("FROM Turno t ");
        sql.append("JOIN unidades_exitosas ue ON t.codUnidad = ue.codUnidad ");
        sql.append("JOIN Doctor doc ON t.codMedico = doc.codMedico ");
        sql.append("JOIN Unidad u ON t.codUnidad = u.codUnidad ");
        sql.append("JOIN Departamento d ON u.codDpt = d.codDpt ");
        sql.append("JOIN Hospital h ON d.codHospital = h.codHospital ");
        sql.append("JOIN totales_medico tm ON tm.codMedico = t.codMedico AND tm.codUnidad = t.codUnidad ");
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

        sql.append("ORDER BY u.nombreUnidad, t.numTurno");

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
                lista.add(new UnidadExitosa(
                        rs.getString("hospital"),
                        rs.getString("departamento"),
                        rs.getString("unidad"),
                        rs.getInt("numTurno"),
                        rs.getString("medico"),
                        rs.getInt("atendidos_turno"),
                        rs.getInt("total_medico")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al generar resumen de consultas exitosas: " + e.getMessage(), e);
        }
        return lista;
    }
}
