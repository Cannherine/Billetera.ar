package ar.edu.ungs.billetera;

public class Premium extends Cuenta {
    public static final double depositoMinimo = 100000;


	public Premium(String cvu, String alias, Usuario usuario) {
		super(cvu, alias, usuario);
		// TODO Auto-generated constructor stub
	}

	 // TIPO DE CUENTA
    @Override
    public String getTipo() {

        return "Premium";
    }
	
    




}

