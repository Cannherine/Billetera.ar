package ar.edu.ungs.billetera;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

public class Billetera implements IBilletera {
	
	//ATRIBUTOS
	private HashMap<String, Usuario> usuarios = new HashMap<>(); 		//indice secundario: es necesario?
	private HashMap<String, Cuenta> cuentasPorCvu = new HashMap<>();	//tener en cuenta que cada cuenta nueva
	private HashMap<String, Cuenta> cuentasPorAlias = new HashMap<>();	//debe agregarse a cada map.. 
	private List<Actividad> historialCompletoDeActividades = new ArrayList<>();// Historial global del sistema.
	private HashMap<String, Empresa> empresas = new HashMap<>();//Empresas registradas
	/////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,
			String nombreContacto) {
		
		 validarParametro(cuit);

		    validarParametro(nombreFantasia);

		    validarParametro(telefono);

		    validarParametro(email);

		    validarParametro(nombreContacto);


		    if (empresas.containsKey(cuit)) {

		        throw new IllegalArgumentException( "La empresa ya existe.");
		    }

//crear empresa 
		    

		    Empresa empresa =new Empresa(cuit,nombreFantasia,telefono, email,  nombreContacto);

		    //guarda 
		    empresas.put(cuit, empresa);
		}
	
///////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
		// TODO Auto-generated method stub
		//validaciones
		validarParametro(cuitEmpresa);// verifica que el cuit no sea null ni vacío

	    validarParametro(dniAutorizado);  // verifica que el dni no sea null ni vacío

	    validarEmpresaExiste(cuitEmpresa); // verifica que la empresa exista


	    // buscar emplesas

	    Empresa empresa = empresas.get(cuitEmpresa); // obtiene la empresa desde el hashmap

	    // validamos la persona autorizada

	    if (empresa.estaAutorizado(dniAutorizado)) {

	        throw new IllegalArgumentException( "La persona ya está autorizada.");
	    }
	    // agregamos la persona autorizada

	    empresa.agregarAutorizado(dniAutorizado);// agrega el dni al conjunto de autorizados
	}

	//////////////////////////////////////////////////
	private void validarParametro(String parametro) {
		if (parametro == null  || parametro.isEmpty()) {
			throw new IllegalArgumentException("Parámetro '" + parametro +"' inválido.");
		}
	}
	
	private void validarUsuarioExiste(String dni) {// ver esto no es al reves lo qeu sale ??
		if(usuarios.containsKey(dni)) {
			throw new IllegalArgumentException("El usuario ya Existe.");
		}		
	}
	
	private void validarUsuarioNOExiste(String dni) {
		if(! usuarios.containsKey(dni)) {
			throw new IllegalArgumentException("El usuario NO Existe.");
		}
	}
	
	private void validarAlias(String alias) {
		if (cuentasPorAlias.containsKey(alias)) {
			throw new IllegalArgumentException("El alias ya existe.");
		}
	}
	
	private void validarCuentaNOExiste(String cuenta) {
		if(! cuentasPorCvu.containsKey(cuenta)) {
			throw new IllegalArgumentException("La cuenta NO existe.");
		}
	}
	/////////////////////////////////////
	
	@Override
	public void registrarUsuario(String dni, String nombre, String telefono, String email) {
		//VALIDACIONES:
		validarParametro(dni); 
		validarParametro(nombre);
		validarParametro(telefono); 
		//validarParametro(email);  
		if (email == null) {
	        throw new IllegalArgumentException("Parámetro 'email' inválido.");
	    }
		validarUsuarioExiste(dni);
		
		//Registra el usuario (lo crea).
		Usuario nuevoUsuario = new Usuario(dni, nombre, telefono, email);
		
		//agrega el usuario nuevo al hashmap usuarios
		this.usuarios.put(dni, nuevoUsuario);
	}
//////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) {
		//VALIDACIONES:
		validarParametro(dniUsuario); 
		validarParametro(alias);  
		validarUsuarioNOExiste(dniUsuario);
		validarAlias(alias);
		
		//busco el usuario por su dni:
		Usuario usuario = usuarios.get(dniUsuario);
		
		// genero un cvu random:
		String cvu = generadorCvu();
		while(cuentasPorCvu.containsKey(cvu)) { 	//Genera un cvu nuevo en caso de que ya exista,
			cvu = generadorCvu();					//hasta que sea un cvu unico y distinto. 
		}
		
		//crea la cuenta:
		Regular nuevaRegular = new Regular(cvu, alias, usuario);
		//guarda la cuenta en las listas
		cuentasPorAlias.put(alias, nuevaRegular);
		cuentasPorCvu.put(cvu, nuevaRegular);
		usuario.agregarCuenta(nuevaRegular);
        
		return cvu;
	}
	

	private static String generadorCvu() { // genera un CVU de 22 digitos
		Random random = new Random(); //genero un numero random
		StringBuilder cvu = new StringBuilder(); //creo un stringbuilder para poder concatenar los numeros aleatorios.
		
		//pensado para un cvu con 22 digitos
		for (int i = 0; i < 22; i++) {
			cvu.append(random.nextInt(10)); //genera un numero aleatorio de 0 a 9
		}
		return cvu.toString();
	}
	
////////////////////////////////////////////////////////////////////////////////////////	

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
		// TODO Auto-generated method stub
		  validarParametro(dniUsuario);
		    validarParametro(alias);

		    validarUsuarioNOExiste(dniUsuario);
		    validarAlias(alias);
		    
// valida minimo de deposito
		    if (depositoInicial <= Premium.DEPOSITO_MINIMO) {
		        throw new IllegalArgumentException("Depósito inicial insuficiente.");
		    }

		    Usuario usuario = usuarios.get(dniUsuario);

			String cvu = generadorCvu(); 
//crea la cuenta premium
		    Premium cuenta = new Premium(cvu, alias, usuario);
// deposita dinero icial
		    cuenta.acreditar(depositoInicial);
//registra la cuenta
		    cuentasPorCvu.put(cvu, cuenta);
		    cuentasPorAlias.put(alias, cuenta);
		    usuario.agregarCuenta(cuenta);  
		    return cvu;
	}
////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		 validarParametro(dniUsuario);

		    validarParametro(alias);

		    validarParametro(cuitEmpresa);

		    validarUsuarioNOExiste(dniUsuario);

		    validarAlias(alias);

		    validarEmpresaExiste(cuitEmpresa);


		    // buscamos loas datos que necesitamos

		    Usuario usuario = usuarios.get(dniUsuario);

		    Empresa empresa = empresas.get(cuitEmpresa);


		    // verificamos la autorizacion

		    if (!empresa.estaAutorizado(dniUsuario)) {

		        throw new IllegalArgumentException( "El usuario no está autorizado.");
		    }


		    

		    String cvu = generadorCvu();

		    while (cuentasPorCvu.containsKey(cvu)) {

		        cvu = generadorCvu();
		    }


		    // creamos la cuenta

		    Corporativa cuenta = new Corporativa(cvu,alias,usuario,empresa);

		    // guardamos

		    cuentasPorCvu.put(cvu, cuenta);

		    cuentasPorAlias.put(alias, cuenta);

		    usuario.agregarCuenta(cuenta);

		    return cvu;	

	}
//////////////////////////////////////////////////////////////////////////
	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		//VALIDACIONES:
		validarUsuarioNOExiste(dniUsuario);
		
		List<String> lista = new ArrayList<String>(); 	//creo una nueva lista para guardar los datos con el formato pedido
		Usuario usuario = usuarios.get(dniUsuario);			//[Tipo]: [Alias] ([CVU])
		//con string builder:
		for(Cuenta cuenta : usuario.getCuentas().values()) { 	//for each para acceder a cada valor del hashmap
			
			//con stringbuilder:
//			StringBuilder sb = new StringBuilder();				
//			sb.append(cuenta.getTipo());
//			sb.append(": ");
//			sb.append(cuenta.getAlias());
//			sb.append(" (");
//			sb.append(cuenta.getCvu());
//			sb.append(") ");
//			lista.add(sb.toString());
			
			//con +:
			lista.add(cuenta.getTipo() + ": " + cuenta.getAlias() + " (" + cuenta.getCvu() + ") ");
		}

		return lista;														
	}
////////////////////////////////////////////////////////////////////
	@Override
	public double obtenerSaldoDisponible(String cvu) {
		validarCuentaNOExiste(cvu); //lanza excepcion si la cuenta no existe.
		Cuenta cuenta = cuentasPorCvu.get(cvu); //guardo la cuenta.
		return cuenta.getSaldo(); //devuelvo su atributo saldo.
	}
////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		    // VALIDACIONES:
		    validarParametro(cvuOrigen);
		    validarParametro(cvuDestino);
		    validarCuentaNOExiste(cvuOrigen);
		    validarCuentaNOExiste(cvuDestino);

		    if (monto <= 0) {
		        throw new IllegalArgumentException("Monto inválido.");
		    }

		    // Busco las cuentas
		    Cuenta cuentaOrigen = cuentasPorCvu.get(cvuOrigen);// obtiene la cuenta origen usando el hashmap

		    Cuenta cuentaDestino = cuentasPorCvu.get(cvuDestino);// obtiene la cuenta destino usando el hashmap

		    //Limites para las cuentas regulares
		    if (cuentaDestino instanceof Regular && monto > 5_000_000) {
		        throw new IllegalStateException("Límite de transferencia excedido.");
		    }
		    //Debita
		    boolean pudoDebitar = cuentaOrigen.debitar(monto);// intenta sacar dinero de la cuenta origen


		    if (!pudoDebitar) {// si no tiene saldo suficiente lanza error

		        throw new IllegalArgumentException("Saldo insuficiente.");
		    }

		    // Acredita
		    cuentaDestino.acreditar(monto);    // agrega dinero a la cuenta destino


		 // Crea la actividad:crea el objeto transferencia para guardar el movimiento

		    Transferencia transferencia = new Transferencia(monto, cuentaOrigen, cuentaDestino);

		    // Guarda las actividades
		    cuentaOrigen.getActividades().add(transferencia); // agrega la transferencia al historial de la cuenta origen

		    cuentaDestino.getActividades().add(transferencia); // agrega la transferencia al historial de la cuenta destino


		    historialCompletoDeActividades.add(transferencia); // agrega la transferencia al historial global 

		}
	//////////////////////////////////////////////////////////7

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		// TODO Auto-generated method stub

	}
///////////////////////////////////////////////////////////////////////////////////
	@Override
	public String consultarCvu(String alias) {
		validarParametro(alias);

	    if (!cuentasPorAlias.containsKey(alias)) { // SI EL ALIAS NO EXISTE  
	        throw new IllegalArgumentException("El alias no existe.");
	    }
		Cuenta cuenta = cuentasPorAlias.get(alias);
		return cuenta.getCvu();
	}
//////////////////////////////////////////////////////////////////////////
	@Override
	public List<String> consultarHistorialGlobal() { //Recorre el historial global, e imprime cada actividad Transferencia o Inversion
		List<String> lista = new ArrayList<>();		// y guarda en la nueva lista su toString
		for(Actividad actividad : this.historialCompletoDeActividades) {
			lista.add(actividad.toString());
		}
		return lista;
	}
//////////////////////////////////////////////////////////////////////////////
	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		// TODO Auto-generated method stub

		validarParametro(cvu);

	    validarCuentaNOExiste(cvu);

	    Cuenta cuenta = cuentasPorCvu.get(cvu);

	    List<String> historial = new ArrayList<>();// se crea una lista vacia y aca se van a guardar los mov.

	    for (Actividad act : cuenta.getActividades()) {//recorre cada actividad dentro de una cuenta

	        historial.add(act.toString());//convierte la actividad en texto
	    }

	    return historial; // devuelve toda la lista completa 
	}

	

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		// TODO Auto-generated method stub
		// Validar que el usuario exista
	    validarUsuarioNOExiste(dniUsuario);

	    Usuario usuario = usuarios.get(dniUsuario);

	    List<String> historial = new ArrayList<>();

	    // Recorrer todas las cuentas del usuario
	    for (Cuenta cuenta : usuario.getCuentas().values()) {

	        // Recorrer todas las actividades de cada cuenta
	        for (Actividad act : cuenta.getActividades()) {

	            historial.add(act.toString());
	        }
	    }

	    return historial;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {
		//Validaciones:
		validarUsuarioNOExiste(dniUsuario);
		Usuario usuario = usuarios.get(dniUsuario);
		return usuario.getTotalInvertido();
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
		// TODO Auto-generated method stub
		return null;
	}
////////////////////////////////////////7
	private void validarEmpresaExiste(String cuitEmpresa) {

	    if (!empresas.containsKey(cuitEmpresa)) {

	        throw new IllegalArgumentException( "La empresa no existe.");
	    }
	}
}
