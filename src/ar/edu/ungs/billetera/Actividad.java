package ar.edu.ungs.billetera;

import java.time.LocalDate;

public abstract class Actividad {

	double monto;
	LocalDate fecha;
    protected boolean aprobada;
    protected Cuenta cuentaOrigen;


	public Actividad(double monto, Cuenta cuentaOrigen) {
		this.monto = monto;
		this.fecha = Utilitarios.hoy();
		this.cuentaOrigen =cuentaOrigen;
		this.aprobada=true;
	}
	
	public abstract String getTipo();
	
	public LocalDate getFecha() {
		return fecha;
	}

	public double getMonto() {
		return monto;
	}

	public Cuenta getCuentaOrigen() {
		return cuentaOrigen;
	}

	public boolean EsAprobada() {
		return aprobada;
	}

	public abstract String toString(); // forzamos que cada clase hija lo implemente

}
