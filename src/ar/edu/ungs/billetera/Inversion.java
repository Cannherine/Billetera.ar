package ar.edu.ungs.billetera;

import java.util.Date;

public abstract class Inversion extends Actividad {
	//atributos heredados: fecha, monto
	private Date fechaDeConstitucion;
	
	private	int plazo;
	private	boolean admitePrecancelacion; //es necesario?
	private	double totalInvertido;
	private String id;
	
	
	public Inversion(double monto, Cuenta cuentaOrigen) {
		super(monto, cuentaOrigen);
		
		
	}

	
	
		
		
	public double calcularResultado() {
		return 5;
	}
	
	public void guardarFechaConstitucion(Date fecha) { //actualiza el atributo cuando se sepa la fecha
		this.fechaDeConstitucion = fecha;
	}
	
}
