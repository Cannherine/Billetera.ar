package ar.edu.ungs.billetera;

public class RentaFija extends Inversion {
	
	private double tasaInteres;
	
	public RentaFija(double monto, Cuenta cuentaOrigen, int plazo, double totalInvertido) {
		super(monto, cuentaOrigen);
	
	}
	 
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
