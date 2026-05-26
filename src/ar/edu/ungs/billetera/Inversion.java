package ar.edu.ungs.billetera;

import java.util.Date;

public abstract class Inversion extends Actividad {
	//atributos heredados: fecha, monto
		Date fechaDeConstitucion;
		int plazo;
		boolean admitePrecancelacion; //es necesario?
		double totalInvertido;
		
	public Inversion(double monto, Cuenta cuentaOrigen) {
		super(monto, cuentaOrigen);
		// TODO Auto-generated constructor stub
	}

	
	
		
		
	public double calcularResultado() {
		return 5;
	}
	
	public void guardarFechaConstitucion(Date fecha) { //actualiza el atributo cuando se sepa la fecha
		this.fechaDeConstitucion = fecha;
	}
	
}
