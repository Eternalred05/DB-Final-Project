package DAO;

import Connection.ConnectionManager;
import Logic.Departamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartamentoDAO {

    public void insertarDepartamento(String codDpt, String nombreDpt, String codHospital) {
        String sql = "SELECT insertar_departamento(?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codDpt);
            stmt.setString(2, nombreDpt);
            stmt.setString(3, codHospital);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar departamento: " + e.getMessage(), e);
        }
    }

    public ArrayList<Departamento> listarDpt() {
        ArrayList<Departamento> lista = new ArrayList<>();
        String sql = "SELECT codDpt, nombreDpt, codHospital FROM Departamento ORDER BY nombreDpt";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String cod = rs.getString("codDpt");
                String nom = rs.getString("nombreDpt");
                String hosp = rs.getString("codHospital");
                lista.add(new Departamento(nom, cod, hosp));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los departamentos: " + e.getMessage(), e);
        }
        return lista;
    }

    public Departamento obtenerDptPorPosicion(int posicion) {
        Departamento h = null;
        String sql = "SELECT codDpt, nombreDpt ,codHospital FROM Departamento ORDER BY nombreDpt LIMIT 1 OFFSET ?";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, posicion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                h = new Departamento(rs.getString("nombreDpt"), rs.getString("codDpt"), rs.getString("codHospital"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el departamento escogido " + e.getMessage(), e);
        }
        return h;
    }

    public ArrayList<Departamento> listarDepartamentosPorHospital(String codHospital) {
        ArrayList<Departamento> lista = new ArrayList<>();
        String sql = "SELECT codDpt, nombreDpt, codHospital FROM Departamento WHERE codHospital = ? ORDER BY nombreDpt";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codHospital);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Departamento(
                        rs.getString("nombreDpt"),
                        rs.getString("codDpt"),
                        rs.getString("codHospital")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar departamentos por hospital: " + e.getMessage(), e);
        }
        return lista;
    }

}
