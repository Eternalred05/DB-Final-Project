package Logic;

public abstract class Persona {

    protected String nombre;
    protected String apellidos;
    protected String id;

    public Persona(String nombre,String apellidos, String id) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.id = id;
    }
    
    
}
