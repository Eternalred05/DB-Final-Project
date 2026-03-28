package Logic;

import java.time.LocalDate;

public class Paciente extends Persona {

    private String address;
    private LocalDate birthDate;

    public Paciente(String address, LocalDate birthDate, String nombre, String apellidos, String id) {
        super(nombre, apellidos, id);
        this.address = address;
        this.birthDate = birthDate;
    }

   
}
