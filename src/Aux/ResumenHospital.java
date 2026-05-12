package Aux;

public class ResumenHospital {

    private String nombreHospital;
    private int cantDepartamentos;
    private int cantUnidades;
    private int cantMedicos;
    private int cantPacientes;

    public ResumenHospital(String nombreHospital, int cantDepartamentos, int cantUnidades,
            int cantMedicos, int cantPacientes) {
        this.nombreHospital = nombreHospital;
        this.cantDepartamentos = cantDepartamentos;
        this.cantUnidades = cantUnidades;
        this.cantMedicos = cantMedicos;
        this.cantPacientes = cantPacientes;
    }

    public String getNombreHospital() {
        return nombreHospital;
    }

    public int getCantDepartamentos() {
        return cantDepartamentos;
    }

    public int getCantUnidades() {
        return cantUnidades;
    }

    public int getCantMedicos() {
        return cantMedicos;
    }

    public int getCantPacientes() {
        return cantPacientes;
    }
}
