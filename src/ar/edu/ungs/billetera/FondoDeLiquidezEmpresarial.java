package ar.edu.ungs.billetera;

public class FondoDeLiquidezEmpresarial extends Inversion{
	
	
	
	public FondoDeLiquidezEmpresarial(double monto, Cuenta cuentaOrigen) {
		super(monto, cuentaOrigen);
		
			
	}

	@Override
	public String getTipo() {

		return null;
	}
	
}
