package Logic;

import java.text.Normalizer;
import java.util.ArrayList;

public class Hospital {

    private String name;
    private String id;
    private ArrayList<Departamento> depts;

    public Hospital(String name, String id) {
        this.name = name;
        this.id = id;
        depts = new ArrayList<>();
    }
    public void setName(String nombre) {
		boolean digit = false;
		if(!nombre.isEmpty() && nombre != null){
			nombre = Normalizer.normalize(nombre, Normalizer.Form.NFC);
			for (char c : nombre.toCharArray()) {
				if (!Character.isLetter(c) && !Character.isSpaceChar(c))
					digit = true;
			}
			if(!digit)
				this.name = nombre;
			else
				throw new IllegalArgumentException("El nombre no debe contener números.");
		}
		else
			throw new IllegalArgumentException("El nombre está vacío.");

	}
    
    

}
