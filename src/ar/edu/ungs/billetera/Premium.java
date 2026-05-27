package ar.edu.ungs.billetera;

public class Premium extends Cuenta {
    public static final double DEPOSITO_MINIMO = 100000;


	public Premium(String cvu, String alias, Usuario usuario, double saldo) {
		super(cvu, alias, usuario);
		// TODO Auto-generated constructor stub
	}

	 // TIPO DE CUENTA
    @Override
    public String getTipo() {

        return "PREMIUM";
    }
	
    




}

