package ar.edu.ungs.billetera;

public class Corporativa extends Cuenta {

    private Empresa empresa;

	public Corporativa(String cvu, String alias, Usuario usuario, Empresa empresa) {
		super(cvu, alias, usuario);
		this.empresa =empresa;
				
	}
	
	public boolean puedeTransferir(double monto) { //
		return false;
	}
	
	public boolean puedeInvertir() {
		return true;
	}
	
	@Override
	public String toString() {
	    return "Corporativa: " + alias + " (" + cvu + ")";
	}

	@Override
	public String getTipo() {
		return "Corporativa";
	}
	 public Empresa getEmpresa() {
	        return empresa;
	    }
}
