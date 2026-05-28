package ar.edu.ungs.billetera;

import java.time.LocalDate;

public abstract class Inversion extends Actividad {
	// atributos heredados: fecha, monto
	private LocalDate fechaDeConstitucion;
	private static int contadorID = 1;
	protected int plazo;
	private boolean admitePrecancelacion; // es necesario?
	private int id;

	public Inversion(double monto, Cuenta cuentaOrigen, int plazo, boolean admitePrecancelacion) {
		super(monto, cuentaOrigen);
		this.id = contadorID++;
		this.plazo = plazo;
		this.admitePrecancelacion = admitePrecancelacion;
		this.fechaDeConstitucion = Utilitarios.hoy(); // usa Utilitarios para definir la fecha
	}

	public abstract double calcularResultado(); // este metodo tiene un comportamiento unico en cada clase hija

	
	public double calcularMontoPrecancelacion() {	 // comportamiento por defecto: paraa RentaFija
	    return monto + calcularResultado() / 2;		// devuelve el capital mas la mitad de los intereses
	}
	
	public LocalDate getFechaConstitucion() {
		return fechaDeConstitucion;
	}

	public int getPlazo() {
		return plazo;
	}

	public boolean admitePrecancelacion() {
		return admitePrecancelacion;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("fecha: ").append(fechaDeConstitucion).append("\n");
		sb.append("origen: ").append(cuentaOrigen.getUsuario().getDni()).append(" (").append(cuentaOrigen.getCvu())
				.append(")\n");
		sb.append("desc: ").append(getTipo()).append("\n"); // getTipo() es polimorfico
		sb.append("monto: ").append(monto).append("\n");
		sb.append("plazo: ").append(plazo).append("\n");
		sb.append(aprobada ? "Aprobado" : "Rechazado");
		return sb.toString();
	}

}
	

