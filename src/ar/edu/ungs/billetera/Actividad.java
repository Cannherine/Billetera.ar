package ar.edu.ungs.billetera;

import java.util.Date;

public abstract class Actividad {

	double monto;
	Date fecha;
    protected boolean aprobada;
    protected Cuenta cuentaOrigen;
    private String estado; //"Aprobado" o "Rechazado"


	public Actividad(double monto, Cuenta cuentaOrigen) {
		this.monto = monto;
		this.fecha = new Date ();
		this.cuentaOrigen =cuentaOrigen;
		this.aprobada=true;
	}
	 public Date getFecha() {
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

	@Override
	public String toString() {// modoficar ewto
		return "";
	}
}
