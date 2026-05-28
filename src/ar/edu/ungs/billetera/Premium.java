package ar.edu.ungs.billetera;

public class Premium extends Cuenta {
	public static final double depositoMinimo = 500000;

	public Premium(String cvu, String alias, Usuario usuario) {
		super(cvu, alias, usuario);
	}

	// TIPO DE CUENTA
	@Override
	public String getTipo() {

		return "Premium";
	}

	@Override
	public String toString() {
		return "Premium: " + alias + " (" + cvu + ")";
	}

}

