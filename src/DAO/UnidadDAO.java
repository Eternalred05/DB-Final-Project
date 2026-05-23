package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Connection.ConnectionManager;
import java.sql.ResultSet;
import Logic.*;
import java.util.ArrayList;

public class UnidadDAO {

    public void insertarUnidad(String codUnidad, String nombreUnidad, String ubicacion, String codDpt) {
        String sql = "SELECT insertar_unidad(?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setString(2, nombreUnidad);
            stmt.setString(3, ubicacion);
            stmt.setString(4, codDpt);
            stmt.execute();
        } catch (SQLException e) {

            throw new RuntimeException("Error al insertar unidad " + e.getMessage(), e);
        }
    }

    public ArrayList<Unidad> listarUnidades() {
        ArrayList<Unidad> lista = new ArrayList<>();
        String sql = "SELECT codUnidad, nombreUnidad, ubicacion, codDpt FROM Unidad ORDER BY nombreUnidad";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String codU = rs.getString("codUnidad");
                String nom = rs.getString("nombreUnidad");
                String loc = rs.getString("ubicacion");
                String codD = rs.getString("codDpt");
                lista.add(new Unidad(nom, codU, loc, codD));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las unidades: " + e.getMessage(), e);
        }
        return lista;
    }

    public Unidad obtenerUnidadPorPosicion(int posicion) {
        Unidad h = null;
        String sql = "SELECT codUnidad, nombreUnidad, ubicacion, codDpt FROM Unidad ORDER BY nombreUnidad LIMIT 1 OFFSET ?";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, posicion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                h = new Unidad(rs.getString("nombreUnidad"), rs.getString("codUnidad"), rs.getString("ubicacion"), rs.getString("codDpt"));

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la Unidad escogida " + e.getMessage(), e);
        }
        return h;
    }

    public ArrayList<Unidad> listarUnidadesPorDepartamento(String codDpt) {
        ArrayList<Unidad> lista = new ArrayList<>();
        String sql = "SELECT codUnidad, nombreUnidad, ubicacion, codDpt FROM Unidad WHERE codDpt = ? ORDER BY nombreUnidad";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codDpt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Unidad(
                        rs.getString("nombreUnidad"),
                        rs.getString("codUnidad"),
                        rs.getString("ubicacion"),
                        rs.getString("codDpt")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar unidades: " + e.getMessage(), e);
        }
        return lista;
    }

    public void eliminarUnidad(String codUnidad) {
        String sql = "SELECT eliminar_unidad(?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar unidad: " + e.getMessage(), e);
        }
    }

    public void modificarUnidad(String codUnidad, String nombre, String ubicacion, String codDpt) {
        String sql = "SELECT modificar_unidad(?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            stmt.setString(2, nombre);
            stmt.setString(3, ubicacion);
            stmt.setString(4, codDpt);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar unidad: " + e.getMessage(), e);
        }
    }

    public int contarMedicosEnUnidad(String codUnidad) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Doctor WHERE codUnidad = ?";
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar médicos: " + e.getMessage(), e);
        }
        return total;
    }

    public Unidad obtenerUnidadPorCodigo(String codUnidad) {
        String sql = "SELECT codUnidad, nombreUnidad, ubicacion, codDpt FROM Unidad WHERE codUnidad = ?";
        Unidad u = null;
        try (Connection conn = ConnectionManager.getInstance().retrieveConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codUnidad);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                u = new Unidad(
                        rs.getString("nombreUnidad"),
                        rs.getString("codUnidad"),
                        rs.getString("ubicacion"),
                        rs.getString("codDpt")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener unidad: " + e.getMessage(), e);
        }
        return u;
    }
}
