package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Connection.ConnectionManager;
import java.sql.ResultSet;
import Logic.*;
import Aux.*;
import java.util.ArrayList;

public class DoctorDAO {

    public void insertarDoctor(String codUnidad, String nombreMed, String codMedico, String telefono, String especialidad, int experiencia, String licencia) {
        String sql = "SELECT insertar_medico(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codMedico);
            stmt.setString(2, nombreMed);
            stmt.setString(3, telefono);
            stmt.setString(4, especialidad);
            stmt.setString(5, licencia);
            stmt.setInt(6, experiencia);
            stmt.setString(7, codUnidad);
            stmt.execute();
        } catch (SQLException e) {

            throw new RuntimeException("Error al insertar al doctor" + e.getMessage(), e);
        }
    }

    public ArrayList<Doctor> listarDrs() {
        ArrayList<Doctor> lista = new ArrayList<>();
        String sql = "SELECT codMedico, nombreMed, telefono,especialidad,numLicencia,experiencia,codUnidad FROM Doctor ORDER BY nombreMed";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nom = rs.getString("nombreMed");
                String codMed = rs.getString("codMedico");
                String telefono = rs.getString("telefono");
                String especialidad = rs.getString("especialidad");
                String licencia = rs.getString("numLicencia");
                int experiencia = rs.getInt("experiencia");
                String codU = rs.getString("codUnidad");
                lista.add(new Doctor(nom, codMed, especialidad, licencia, telefono, experiencia, codU));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar a los doctores: " + e.getMessage(), e);
        }
        return lista;
    }

    public Doctor obtenerDoctorPorPosicion(int posicion) {
        Doctor h = null;
        String sql = "SELECT codMedico, nombreMed, telefono,especialidad,numLicencia,experiencia,codUnidad FROM Doctor ORDER BY nombreMed LIMIT 1 OFFSET ?";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, posicion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nombreMed");
                String codMed = rs.getString("codMedico");
                String telefono = rs.getString("telefono");
                String especialidad = rs.getString("especialidad");
                String licencia = rs.getString("numLicencia");
                int experiencia = rs.getInt("experiencia");
                String codU = rs.getString("codUnidad");
                h = new Doctor(nom, codMed, especialidad, licencia, telefono, experiencia, codU);

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el doctor escogido " + e.getMessage(), e);
        }
        return h;
    }

    public ArrayList<MedicoListado> listarMedicosReporte(String codHospital, String codDpt, String codUnidad) {
        ArrayList<MedicoListado> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT h.nombreHosp AS hospital, d.nombreDpt AS departamento, u.nombreUnidad AS unidad, ");
        sql.append("m.nombreMed, m.especialidad, m.numLicencia, m.telefono, m.experiencia ");
        sql.append("FROM Medico m ");
        sql.append("JOIN Unidad u ON m.codUnidad = u.codUnidad ");
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

        sql.append("ORDER BY u.nombreUnidad, m.nombreMed");

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
                lista.add(new MedicoListado(
                        rs.getString("hospital"),
                        rs.getString("departamento"),
                        rs.getString("unidad"),
                        rs.getString("nombreMed"),
                        rs.getString("especialidad"),
                        rs.getString("numLicencia"),
                        rs.getString("telefono"),
                        rs.getInt("experiencia")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar médicos: " + e.getMessage(), e);
        }
        return lista;
    }

    public ArrayList<Doctor> listarDoctoresPorUnidad(String codUnidad) {
        ArrayList<Doctor> lista = new ArrayList<>();
        String sql = "SELECT codMedico, nombreMed, telefono, especialidad, numLicencia, experiencia, codUnidad "
                + "FROM Doctor WHERE codUnidad = ? ORDER BY nombreMed";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Doctor(
                        rs.getString("nombreMed"),
                        rs.getString("codMedico"),
                        rs.getString("especialidad"),
                        rs.getString("numLicencia"),
                        rs.getString("telefono"),
                        rs.getInt("experiencia"),
                        rs.getString("codUnidad")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar doctores por unidad: " + e.getMessage(), e);
        }
        return lista;
    }

}
