package ar.edu.ungs.billetera;

public class FondoDeLiquidezEmpresarial extends Inversion {

	private static final double TASA = 0.08;

	public FondoDeLiquidezEmpresarial(double monto, Cuenta cuentaOrigen, int plazo) {
		super(monto, cuentaOrigen, plazo, false);

	}

	@Override
    public double calcularResultado() {
        double cotizacion = Utilitarios.consultarCotizacion("FLE"); // cotizacion del activo FLE
        long dias = Utilitarios.hoy().toEpochDay() - getFechaConstitucion().toEpochDay();
        return monto * (TASA / 365) * dias * cotizacion;
    }

	public double getTasa() {
		return TASA;
	}

	@Override
	public String getTipo() {
		return "Fondo de Liquidez Empresarial";
	}

}
	

