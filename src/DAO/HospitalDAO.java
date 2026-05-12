package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Connection.ConnectionManager;
import java.sql.ResultSet;
import Logic.Hospital;
import java.util.ArrayList;
import Aux.*;

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

    public ArrayList<ResumenHospital> obtenerResumenHospitales() {
        ArrayList<ResumenHospital> lista = new ArrayList<>();
        String sql = "SELECT h.nombreHosp, "
                + "(SELECT COUNT(*) FROM Departamento WHERE codHospital = h.codHospital) AS deptos, "
                + "(SELECT COUNT(*) FROM Unidad WHERE codDpt IN (SELECT codDpt FROM Departamento WHERE codHospital = h.codHospital)) AS unidades, "
                + "(SELECT COUNT(*) FROM Medico WHERE codUnidad IN (SELECT codUnidad FROM Unidad WHERE codDpt IN (SELECT codDpt FROM Departamento WHERE codHospital = h.codHospital))) AS medicos, "
                + "(SELECT COUNT(*) FROM Paciente WHERE codUnidad IN (SELECT codUnidad FROM Unidad WHERE codDpt IN (SELECT codDpt FROM Departamento WHERE codHospital = h.codHospital))) AS pacientes "
                + "FROM Hospital h ORDER BY h.nombreHosp";

        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new ResumenHospital(
                        rs.getString("nombreHosp"),
                        rs.getInt("deptos"),
                        rs.getInt("unidades"),
                        rs.getInt("medicos"),
                        rs.getInt("pacientes")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener resumen de hospitales: " + e.getMessage(), e);
        }
        return lista;
    }

    public ArrayList<ResumenHospital> obtenerTop5Hospitales() {
        ArrayList<ResumenHospital> lista = new ArrayList<>();
        String sql
                = "SELECT h.nombreHosp, COUNT(p.numHistClinica) AS total_pacientes "
                + "FROM Hospital h "
                + "JOIN Departamento d ON h.codHospital = d.codHospital "
                + "JOIN Unidad u ON d.codDpt = u.codDpt "
                + "JOIN Paciente p ON u.codUnidad = p.codUnidad "
                + "GROUP BY h.codHospital, h.nombreHosp "
                + "HAVING COUNT(p.numHistClinica) > 100 "
                + "ORDER BY total_pacientes DESC "
                + "LIMIT 5";

        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new ResumenHospital(
                        rs.getString("nombreHosp"), 0, 0, 0, rs.getInt("total_pacientes")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener los hospitales: " + e.getMessage(), e);
        }
        return lista;
    }
}
