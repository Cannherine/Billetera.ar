package ar.edu.ungs.billetera;

public class RentaFija extends Inversion {
	
	private double CotizacionActual;
	private int id;
	private int plazo;
	
	
	public RentaFija(double monto, Cuenta cuentaOrigen, int plazo, double CotizacionActual) {
		super(monto, cuentaOrigen, plazo, true);
        this.CotizacionActual = CotizacionActual;

	}
	 
	  @Override
	    public String getTipo() {
	        return "Renta Fija";
	    }

	public int getId() {
		return id;
	}

	
	
}
