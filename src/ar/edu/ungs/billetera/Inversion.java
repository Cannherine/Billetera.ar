package ar.edu.ungs.billetera;

import java.util.Date;

public abstract class Inversion extends Actividad {
	//atributos heredados: fecha, monto
	private Date fechaDeConstitucion;
    private static int contadorID = 1;

	protected	int plazo;
	private	boolean admitePrecancelacion; //es necesario?
	private	double totalInvertido;
	private int Id;
	
	
	public Inversion(double monto, Cuenta cuentaOrigen,int plazo,boolean admitePrecancelacion) {
		super(monto, cuentaOrigen);
		this.Id=contadorID++;
		this.plazo= plazo;
        this.admitePrecancelacion = admitePrecancelacion;

		
	}

	
	
		
		
	public double calcularResultado() {
		return 5;
	}
	
	public void guardarFechaConstitucion(Date fecha) { //actualiza el atributo cuando se sepa la fecha
		this.fechaDeConstitucion = fecha;
	}


	public int getId() {
		return Id;
	}
	
}
