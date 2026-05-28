package ar.edu.ungs.billetera;

public class RentaFija extends Inversion {
	private static final double TASA = 0.20;

	public RentaFija(double monto, Cuenta cuentaOrigen, int plazo) {
		super(monto, cuentaOrigen, plazo, true);
	}

	@Override
	public double calcularResultado() {
		// dias transcurridos desde la constitucion hasta hoy
		long dias = Utilitarios.hoy().toEpochDay() - getFechaConstitucion().toEpochDay();
		
		return monto * (TASA / 365) * dias; // formula: monto * (tasa anual / 365 dias) * dias transcurridos
	}

	@Override
	public String getTipo() {
		return "Renta Fija";
	}
}
