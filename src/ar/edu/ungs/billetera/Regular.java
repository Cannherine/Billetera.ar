package ar.edu.ungs.billetera;

public class Regular extends Cuenta {
	// ATRIBUTOS
	private static final double saldoMaximo = 5000000; // ??

	// FUNCIONES
	public Regular(String cvu, String alias, Usuario usuario) { // es necesario tener el parametro usuario?
		super(cvu, alias, usuario);

	}

	@Override
	public void acreditar(double monto) {
		if (saldo + monto > saldoMaximo) {
			throw new IllegalStateException("Límite de saldo excedido.");
		}
		super.acreditar(monto);
	}

	public boolean puedeTransferir(double monto) {

		return saldo >= monto;
	}

	public boolean puedeInvertir(double monto) {

		return this.saldo >= monto;
	}


	@Override
	public String toString() {

		return "Regular: " + alias + " (" + cvu + ")";
	}

	@Override
	public String getTipo() {

		return "Regular";
	}
}
