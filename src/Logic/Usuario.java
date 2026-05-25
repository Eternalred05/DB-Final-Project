package Logic;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String usuario;
    private String contrasena;
    private boolean admin;

    public Usuario(int idUsuario, String nombre, String usuario, String contrasena, boolean admin) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.admin = admin;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public boolean isAdmin() {
        return admin;
    }
}
