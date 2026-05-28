package ar.edu.ungs.billetera;

import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta {
	protected String cvu;
	protected String alias;
	protected double saldo;
	private Usuario usuario;

	protected List<Actividad> actividades;

	protected double volumenTransacciones;

	// contructor
	public Cuenta(String cvu, String alias, Usuario usuario) {
		this.cvu = cvu;
		this.usuario = usuario;
		this.alias = alias;

		this.saldo = 0;
		this.actividades = new ArrayList<>();
		this.volumenTransacciones = 0;
	}

	// Metodos
	
	public abstract String getTipo();
	
	public void acreditar(double monto) {
		if (monto > 0) {
			saldo += monto;
			volumenTransacciones += monto;
		}
	}

	public boolean debitar(double monto) {
		if (monto <= 0) {
			return false;
		}
		if (saldo < monto) {
			return false;
		}
		saldo -= monto;
		volumenTransacciones += monto;
		return true;
	}

	public String getCvu() {
		return cvu;
	}

	public String getAlias() {
		return alias;
	}

	public double getSaldo() {
		return saldo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public double getVolumenTransacciones() {
		return volumenTransacciones;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ":" + alias + "( " + cvu + ")";
	}

}
