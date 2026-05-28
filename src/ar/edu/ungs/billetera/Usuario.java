package ar.edu.ungs.billetera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Usuario {
	// Atributo

	private String dni;
	private String nombre;
	private String telefono;
	private String mail;
	private double totalInvertido; 
	private HashMap<String, Cuenta> cuentas = new HashMap<String, Cuenta>(); //Key: cvu, Value:Cuenta
	
	
	//Constructor
	public Usuario (String dni, String nombre, String telefono, String email) {
		this.dni= dni;
		this.nombre = nombre;
		this.telefono = telefono; 
		this.mail = email; 
		this.totalInvertido =0;
		this.cuentas = new HashMap<>(); // porque cada usuario necesita tener su coleccion de cuentas creadas
	}

	//Metodos 
	public void agregarCuenta(Cuenta nueva) { 
		if (nueva!=null) { //verifico que exista la cuenta
			cuentas.put(nueva.getCvu(), nueva ); // va a guaradar deltro del hashmap la cuenta
			
		}

		
	}
	public void sumarTotalInvertido(double monto) {
		this.totalInvertido += monto;
	}
	
	public void restarTotalInvertido(double monto) {
	    this.totalInvertido -= monto;
	}
	
		
	public HashMap<String, Cuenta> getCuentas() {
		return cuentas;
	}
	
		
	public String getDni() {
		return dni;
	}


	public String getNombre() {
		return nombre;
	}


	public String getTelefono() {
		return telefono;
	}


	public String getMail() {
		return mail;
	}
	
	public double getTotalInvertido() {
		return totalInvertido;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Usuario: ").append(nombre).append(" (DNI: ").append(dni).append(")\n");
	    sb.append("  Total invertido: $").append(totalInvertido).append("\n");
	    sb.append("  Cuentas:\n");

	    for (Cuenta cuenta : cuentas.values()) {
	        sb.append("    ").append(cuenta.toString()).append("\n");
	    }
	    return sb.toString();
	}

}


