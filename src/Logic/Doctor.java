package Logic;

public class Doctor {
    
    private String name;
    private String id;
    private String speciality;
    private String licenseNum;
    private String telephone;
    private int exp;
    private String codUnidad;

    public Doctor(String name, String id, String speciality, String licenseNum, String telephone, int exp, String codUnidad) {
        this.name = name;
        this.id = id;
        this.speciality = speciality;
        this.licenseNum = licenseNum;
        this.telephone = telephone;
        this.exp = exp;
        this.codUnidad = codUnidad;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public String getTelephone() {
        return telephone;
    }

    public int getExp() {
        return exp;
    }

    public String getCodUnidad() {
        return codUnidad;
    }
    
    

}
