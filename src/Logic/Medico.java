package Logic;

import java.util.ArrayList;

public class Medico extends Persona {

    private String speciality;
    private String licenseNum;
    private String telephone;
    private int expYears;
    private Unidad unity;
    private ArrayList<Turno> turns;

    public Medico(String speciality, String licenseNum, String telephone, int expYears, Unidad unity, String nombre, String apellidos, String id) {
        super(nombre, apellidos, id);
        this.speciality = speciality;
        this.licenseNum = licenseNum;
        this.telephone = telephone;
        this.expYears = expYears;
        this.unity = unity;
        this.turns = new ArrayList<>();
    }

}
