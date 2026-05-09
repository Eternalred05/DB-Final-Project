package Connection;

import java.sql.Connection;

public class TestConexion {

    public static void main(String[] args) {
        try {
            Connection conn = ConnectionManager.getInstance().retrieveConnection();
            System.out.println("Se ha establecido conexion con la base de datos:" + conn.getCatalog());
            conn.close();
        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }
}
