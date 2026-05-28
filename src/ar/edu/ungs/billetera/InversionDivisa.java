package ar.edu.ungs.billetera;

public class InversionDivisa extends Inversion {
	 private String divisa;

	    private double tasa;
	public InversionDivisa(double monto, Cuenta cuentaOrigen, int plazo, String divisa, double tasa) {
		super(monto, cuentaOrigen, plazo, true);
		// TODO Auto-generated constructor stub
		this.divisa= divisa ;
		this.tasa= tasa;
	
		
	}

	  public String getDivisa() {
	        return divisa;
	    }

	    public double getTasa() {
	        return tasa;
	    }

	    @Override
	    public String getTipo() {
	        return "Inversion Divisa";
	    }
	
}
