package ar.edu.ungs.billetera;

public class Corporativa extends Cuenta {
	//private String cuit;
	private boolean autorizada; // para que operar en nombre de la empresa asociada
	//private String empresaAsociada; //su cuit
    private Empresa empresa;

	public Corporativa(String cvu, String alias, Usuario usuario, Empresa empresa, double saldo) {
		super(cvu, alias, usuario);
		this.empresa =empresa;
				
	}
	
	public boolean puedeTransferir(double monto) { //
		return false;
	}
	
	public boolean puedeInvertir() {
		return false;
	}
	
	@Override
	public String toString() {// esto hay que cambiar 
		return "";
	}

	@Override
	public String getTipo() {
		return "Corporativa";
	}
	 public Empresa getEmpresa() {
	        return empresa;
	    }
}
