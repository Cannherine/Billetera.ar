package ar.edu.ungs.billetera;

public class Transferencia 	 extends Actividad {

		   
		    private Cuenta cuentaDestino;
		    // Constructor
		    public Transferencia(double monto,Cuenta cuentaOrigen, Cuenta cuentaDestino) {

		        super(monto, cuentaOrigen);

		        this.cuentaDestino = cuentaDestino;
		    }

		   
		    public Cuenta getCuentaDestino() {
		        return cuentaDestino;
		    }

		  
		    @Override
		    public String toString() {

		        return "Transferencia:\n" +
		               "fecha: " + fecha + "\n" +
		               "origen: " +
		               cuentaOrigen.getUsuario().getDni() +
		               " (" + cuentaOrigen.getCvu() + ")\n" +

		               "destino: " +
		               cuentaDestino.getUsuario().getDni() +
		               " (" + cuentaDestino.getCvu() + ")\n" +

		               "monto: " + monto + "\n" +

		               (aprobada ? "Aprobado" : "Rechazado");
		    }
		}

