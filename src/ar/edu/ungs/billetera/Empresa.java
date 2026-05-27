package ar.edu.ungs.billetera;

import java.util.HashSet;
import java.util.Set;

public class Empresa {
	 	
		private String cuit;

	    private String nombreFantasia;

	    private String telefono;

	    private String email;

	    private String nombreContacto;

	    private Set<String> autorizados;


	    // CONSTRUCTOR
	    public Empresa(String cuit, String nombreFantasia,  String telefono,String email, String nombreContacto) {

	        this.cuit = cuit;

	        this.nombreFantasia = nombreFantasia;

	        this.telefono = telefono;

	        this.email = email;

	        this.nombreContacto = nombreContacto;

	        this.autorizados = new HashSet<>();
	    }

    public void agregarAutorizado(String dni) {

        autorizados.add(dni);
    }

    public boolean estaAutorizado(String dni) {

        return autorizados.contains(dni);
    }
    public String getCuit() {
        return cuit;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }


    @Override
    public String toString() {

        return nombreFantasia + " (" + cuit + ")";
    }
}
