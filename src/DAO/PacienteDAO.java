package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Connection.ConnectionManager;
import java.sql.ResultSet;
import Logic.*;
import java.util.ArrayList;
import java.sql.Date;

public class PacienteDAO {

    public void insertarPaciente(String numHist, String nombre, String dir, Date fecha, String causa, boolean atendido, String codUnidad) {
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

            throw new RuntimeException("Error al insertar al paciente" + e.getMessage(), e);
        }
    }

    public ArrayList<Paciente> listarPaciente() {
        ArrayList<Paciente> lista = new ArrayList<>();
        String sql = "SELECT codUnidad, numHistClinica, nombrePac ,direccion,nacimiento,atendido,causa FROM Paciente ORDER BY nombrePac";
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
            throw new RuntimeException("Error al listar a los pacientes: " + e.getMessage(), e);
        }
        return lista;
    }

    public Paciente obtenerPacientePorPosicion(int posicion) {
        Paciente h = null;
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
                //  h = new Doctor(nom, codMed, especialidad, licencia, telefono, experiencia, codU);

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el doctor escogido " + e.getMessage(), e);
        }
        return h;
    }

}
