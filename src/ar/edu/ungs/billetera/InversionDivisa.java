package ar.edu.ungs.billetera;

public class InversionDivisa extends Inversion {
	private String divisa;

	private double tasa;

	private double cotizacionInicial;


	public InversionDivisa(double monto, Cuenta cuentaOrigen, int plazo, String divisa, double tasa) {
		super(monto, cuentaOrigen, plazo, true);
		this.divisa= divisa ;
		this.tasa= tasa;
		this.cotizacionInicial = Utilitarios.consultarCotizacion(divisa); // guarda al crear

	}

	@Override
    public double calcularResultado() {
        double cotizacionActual = Utilitarios.consultarCotizacion(divisa); // cotizacion al momento del calculo
        double divisasEquivalente = monto / cotizacionInicial; // cuantas divisas se compraron originalmente
        long dias = Utilitarios.hoy().toEpochDay() - getFechaConstitucion().toEpochDay();
        double interesesEnDivisas = divisasEquivalente * (tasa / 365) * dias; // intereses acumulados en divisa
        
        // retorna el valor total en pesos: (divisas + intereses) * cotizacion actuall
        return (divisasEquivalente + interesesEnDivisas) * cotizacionActual;
    }
	
	@Override
	public double calcularMontoPrecancelacion() {
	    // cotizacion actual al momento de precancelar
	    double cotizacionActual = Utilitarios.consultarCotizacion(divisa);
	    
	    // cuantas divisas se compraron originalmente con el monto en pesos
	    double divisasEquivalente = monto / cotizacionInicial;
	    
	    // dias transcurridos desde que se constituyo la inversion
	    long dias = Utilitarios.hoy().toEpochDay() - getFechaConstitucion().toEpochDay();
	    
	    // intereses generados en divisa hasta hoy
	    double interesesEnDivisas = divisasEquivalente * (tasa / 365) * dias;
	    
	    // como es precancelacion, solo la mitad de los intereses
	    // el capital (divisasEquivalente) se devuelve completo, convertido al valor actual
	    return (divisasEquivalente + interesesEnDivisas / 2) * cotizacionActual;
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
