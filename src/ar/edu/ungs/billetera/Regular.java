package ar.edu.ungs.billetera;

public class Regular extends Cuenta {
	// ATRIBUTOS
	private static final double saldoMaximo = 5000000; //??

	// FUNCIONES
	public Regular(String cvu, String alias, Usuario usuario) { // es necesario tener el parametro usuario? 													
		super(cvu, alias, usuario);
		
	}
	public void acreditar() {
		
	}
	
	public boolean puedeTransferir(double monto) {
		
		return saldo >= monto;
	}

	public boolean puedeInvertir(double monto) {

		return this.saldo >= monto;
	}

	public void agregarSaldo(double monto) {

		if (monto <= 0) {

			throw new IllegalArgumentException("Monto inválido.");
		}

		if (super.saldo + monto > saldoMaximo) {

			throw new IllegalArgumentException("La cuenta supera el saldo máximo permitido");
		}

		acreditar(monto);
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
