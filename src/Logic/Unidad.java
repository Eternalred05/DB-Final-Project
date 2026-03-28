package Logic;

import java.util.ArrayList;

public class Unidad {

    private String name;
    private String id;
    private String location;
    private Departamento dpt;
    private ArrayList<Medico> doctors;
    private ArrayList<Paciente> pacients;
    private ArrayList<Turno> turns;
    private ArrayList<Registro> registers;
    private ArrayList<Informe> informes;

    public Unidad(String name, String id, String location, Departamento dpt) {
        this.name = name;
        this.id = id;
        this.location = location;
        this.dpt = dpt;
        this.doctors = new ArrayList<>();
        this.pacients = new ArrayList<>();
        this.turns = new ArrayList<>();
        this.registers = new ArrayList<>();
        this.informes = new ArrayList<>();
    }

}
