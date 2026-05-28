package ar.edu.ungs.billetera;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Billetera implements IBilletera {

	// ATRIBUTOS
	private HashMap<String, Usuario> usuarios = new HashMap<>(); // clave:dni, valor: Usuario

	private HashMap<String, Cuenta> cuentas = new HashMap<>(); // clave: cvu, valor: Cuenta

	private List<Actividad> historialCompletoDeActividades = new ArrayList<>(); // Historial global del sistema.

	private HashMap<String, Empresa> empresas = new HashMap<>();// Empresas registradas

	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,
			String nombreContacto) {

		validarParametro(cuit);

		validarParametro(nombreFantasia);

		validarParametro(telefono);

		validarParametro(email);

		validarParametro(nombreContacto);

		if (empresas.containsKey(cuit)) {

			throw new IllegalArgumentException("La empresa ya existe.");
		}

		//crear empresa 

		Empresa empresa = new Empresa(cuit, nombreFantasia, telefono, email, nombreContacto);

		// guarda
		empresas.put(cuit, empresa);
	}

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
		// validaciones
		validarParametro(cuitEmpresa);// verifica que el cuit no sea null ni vacío

		validarParametro(dniAutorizado); // verifica que el dni no sea null ni vacío

		validarEmpresaExiste(cuitEmpresa); // verifica que la empresa exista

		// buscar emplesas

		Empresa empresa = empresas.get(cuitEmpresa); // obtiene la empresa desde el hashmap

		// validamos la persona autorizada

		if (empresa.estaAutorizado(dniAutorizado)) {

			throw new IllegalArgumentException("La persona ya está autorizada.");
		}
		// agregamos la persona autorizada

		empresa.agregarAutorizado(dniAutorizado);// agrega el dni al conjunto de autorizados
	}

	

	@Override
	public void registrarUsuario(String dni, String nombre, String telefono, String email) {
		// VALIDACIONES:
		validarParametro(dni);
		validarParametro(nombre);
		validarParametro(telefono);
		// validarParametro(email);
		if (email == null) {
			throw new IllegalArgumentException("Parámetro 'email' inválido.");
		}
		validarUsuarioExiste(dni);

		// Registra el usuario (lo crea).
		Usuario nuevoUsuario = new Usuario(dni, nombre, telefono, email);

		// agrega el usuario nuevo al hashmap usuarios
		this.usuarios.put(dni, nuevoUsuario);
	}

	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) {
		// VALIDACIONES:
		validarParametro(dniUsuario);
		validarParametro(alias);
		validarUsuarioNOExiste(dniUsuario);
		validarAliasEXISTE(alias);

		// busco el usuario por su dni:
		Usuario usuario = usuarios.get(dniUsuario);

		// genero un cvu random:
		String cvu = Utilitarios.generarSiguienteCvu();
		// crea la cuenta:
		Regular nuevaRegular = new Regular(cvu, alias, usuario);
		// guarda la cuenta en la lista de cuentas
		cuentas.put(cvu, nuevaRegular);
		usuario.agregarCuenta(nuevaRegular);

		return cvu;
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {

		validarParametro(dniUsuario);
		validarParametro(alias);

		validarUsuarioNOExiste(dniUsuario);
		validarAliasEXISTE(alias);

		// valida minimo de deposito
		if (depositoInicial <= Premium.depositoMinimo) {
			throw new IllegalArgumentException("Depósito inicial insuficiente.");
		}

		Usuario usuario = usuarios.get(dniUsuario);

		String cvu = Utilitarios.generarSiguienteCvu();
		// crea la cuenta premium
		Premium cuenta = new Premium(cvu, alias, usuario);
		// deposita dinero icial
		cuenta.acreditar(depositoInicial);
		// registra la cuenta
		cuentas.put(cvu, cuenta);

		usuario.agregarCuenta(cuenta);

		return cvu;
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		validarParametro(dniUsuario);

		validarParametro(alias);

		validarParametro(cuitEmpresa);

		validarUsuarioNOExiste(dniUsuario);

		validarAliasEXISTE(alias);

		validarEmpresaExiste(cuitEmpresa);

		// buscamos los datos que necesitamos

		Usuario usuario = usuarios.get(dniUsuario);

		Empresa empresa = empresas.get(cuitEmpresa);

		// verificamos la autorizacion

		if (!empresa.estaAutorizado(dniUsuario)) {

			throw new IllegalArgumentException("El usuario no está autorizado.");
		}

		String cvu = Utilitarios.generarSiguienteCvu();

		// creamos la cuenta
		Corporativa cuenta = new Corporativa(cvu, alias, usuario, empresa);

		// guardamos
		cuentas.put(cvu, cuenta);

		usuario.agregarCuenta(cuenta);

		return cvu;

	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		// VALIDACIONES:
		validarUsuarioNOExiste(dniUsuario);

		List<String> lista = new ArrayList<String>(); // creo una nueva lista para guardar los datos con el formato
		// pedido
		Usuario usuario = usuarios.get(dniUsuario); // [Tipo]: [Alias] ([CVU])
		// con string builder:
		for (Cuenta cuenta : usuario.getCuentas().values()) { // for each para acceder a cada valor del hashmap

			// con stringbuilder:
			//			StringBuilder sb = new StringBuilder();				
			//			sb.append(cuenta.getTipo());
			//			sb.append(": ");
			//			sb.append(cuenta.getAlias());
			//			sb.append(" (");
			//			sb.append(cuenta.getCvu());
			//			sb.append(") ");
			//			lista.add(sb.toString());

			// con +:
			lista.add(cuenta.getTipo() + ": " + cuenta.getAlias() + " (" + cuenta.getCvu() + ") ");
		}

		return lista;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {
		validarCuentaNOExiste(cvu); // lanza excepcion si la cuenta no existe.

		Cuenta cuenta = cuentas.get(cvu); // guardo la cuenta.

		return cuenta.getSaldo(); // devuelvo su atributo saldo.
	}

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
		Cuenta cuentaOrigen = cuentas.get(cvuOrigen);// obtiene la cuenta origen usando el hashmap

		Cuenta cuentaDestino = cuentas.get(cvuDestino);// obtiene la cuenta destino usando el hashmap

		// Limites para las cuentas regulares
		if (cuentaDestino instanceof Regular && monto > 5_000_000) {
			throw new IllegalStateException("Límite de transferencia excedido.");
		}
		// Debita
		boolean pudoDebitar = cuentaOrigen.debitar(monto);// intenta sacar dinero de la cuenta origen

		if (!pudoDebitar) {// si no tiene saldo suficiente lanza error

			throw new IllegalArgumentException("Saldo insuficiente.");
		}

		// Acredita
		cuentaDestino.acreditar(monto); // agrega dinero a la cuenta destino

		// Crea la actividad:crea el objeto transferencia para guardar el movimiento

		Transferencia transferencia = new Transferencia(monto, cuentaOrigen, cuentaDestino);

		// Guarda las actividades
		cuentaOrigen.getActividades().add(transferencia); // agrega la transferencia al historial de la cuenta origen

		cuentaDestino.getActividades().add(transferencia); // agrega la transferencia al historial de la cuenta destino

		historialCompletoDeActividades.add(transferencia); // agrega la transferencia al historial global

	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {

		validarParametro(dni);
		validarParametro(cvu);

		validarUsuarioNOExiste(dni);
		validarCuentaNOExiste(cvu);

		if (monto <= 0) {
			throw new IllegalArgumentException("Monto invalido");

		}

		if (plazoDias <= 0) {
			throw new IllegalArgumentException("Plazo invalido");

		}
		Cuenta cuenta = cuentas.get(cvu);
		// verificamos si el saldo es sufuciente
		if (!cuenta.debitar(monto)) {
			throw new IllegalArgumentException("Saldo insuficiente");

		}
		Usuario usuario = usuarios.get(dni);

		// creemos la inversion

		RentaFija inversion = new RentaFija(monto, cuenta, plazoDias);

		// guardemos la ACtivida|d
		cuenta.getActividades().add(inversion);
		historialCompletoDeActividades.add(inversion);

		// actualizar total invertido O(1)
		usuario.sumarTotalInvertido(monto);

		// devolver id unico
		return inversion.getId();

	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		validarParametro(dni);

		validarParametro(cvu);

		validarParametro(divisa);

		validarUsuarioNOExiste(dni);

		validarCuentaNOExiste(cvu);
		if (monto <= 0) {
			throw new IllegalArgumentException("Monto invalido");
		}

		if (plazoDias <= 0) {
			throw new IllegalArgumentException("Plazo inalido");
		}

		// validemos que exista la divisa
		Utilitarios.consultarCotizacion(divisa);
		Cuenta cuenta = cuentas.get(cvu);

		// verificamos si el saldo es sufuciente
		if (!cuenta.debitar(monto)) {
			throw new IllegalArgumentException("Saldo insuficiente");
		}
		Usuario usuario = usuarios.get(dni);
		// creamos la inverion
		InversionDivisa inversion = new InversionDivisa(monto, cuenta, plazoDias, divisa, tasa);
		// guardamos la actividad
		cuenta.getActividades().add(inversion);
		historialCompletoDeActividades.add(inversion);

		// actualizar total invertido que es de O(1)
		usuario.sumarTotalInvertido(monto);
		// devolvemos el id único
		return inversion.getId();
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {

		validarParametro(dni);

		validarParametro(cvu);

		validarUsuarioNOExiste(dni);

		validarCuentaNOExiste(cvu);

		if (monto <= 0) {
			throw new IllegalArgumentException("Monto invalido");
		}

		if (plazoDias <= 0) {
			throw new IllegalArgumentException("Plazo invalido");
		}

		Cuenta cuenta = cuentas.get(cvu);

		// solo cuentas corporativas (importante)
		if (!(cuenta instanceof Corporativa)) {
			throw new IllegalArgumentException("Solo las cuentas corporativas pueden invertir en Fondo de Liquidez.");
		}

		// el monto minimo 20 millones ( restrincion)
		if (monto < 20_000_000) {
			throw new IllegalArgumentException("El monto minimo es de 20.000.000");
		}

		// verificamos el saldo
		if (!cuenta.debitar(monto)) {
			throw new IllegalArgumentException("Saldo insuficiente");
		}

		Usuario usuario = usuarios.get(dni);

		// Se crea inversioon
		FondoDeLiquidezEmpresarial inversion = new FondoDeLiquidezEmpresarial(monto, cuenta, plazoDias);

		// guarda actividad
		cuenta.getActividades().add(inversion);

		historialCompletoDeActividades.add(inversion);

		// actualizar total invertido es de O(1)
		usuario.sumarTotalInvertido(monto);

		// va a devuelve id único
		return inversion.getId();
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		// TODO Auto-generated method stub
		validarParametro(dni);
		validarParametro(cvu);
		validarUsuarioNOExiste(dni);
		validarCuentaNOExiste(cvu);

		Cuenta cuenta = cuentas.get(cvu);
		Usuario usuario = usuarios.get(dni);

		Inversion inversionEncontrada = null;

		// usamos Iterator porque necesitamos eliminar mientras recorremos
		Iterator<Actividad> it = cuenta.getActividades().iterator();
		while (it.hasNext()) { // mientras haya elementos por recorrer
			Actividad a = it.next();
			if (a instanceof Inversion) { // si la actividad es una inversion
				Inversion inv = (Inversion) a; // cast para acceder a getId()
				if (inv.getId() == idInversion) { // comparamos los id
					inversionEncontrada = inv;
					it.remove(); // elimina la inversion de la lista de actividades
					break;
				}
			}
		}

		if (inversionEncontrada == null) {
			throw new IllegalArgumentException("La inversión no existe.");
		}
		if (!inversionEncontrada.admitePrecancelacion()) {
			throw new IllegalArgumentException("La inversión no admite precancelación.");
		}

		// calcula intereses parciales y devuelve la mitad al saldo
		// el polimorfismo llama al metodo correcto segun el tipo de inversion
		cuenta.acreditar(inversionEncontrada.calcularMontoPrecancelacion());

		// actualiza el total invertido del usuario
		usuario.restarTotalInvertido(inversionEncontrada.getMonto());

	}

	@Override
	public String consultarCvu(String alias) {
		validarParametro(alias);
		validarAliasNOExiste(alias); // si el alias no existe, lanza una excepcion.
		// busco la cuenta
		String cvu = "";
		for (Cuenta cuenta : cuentas.values()) {
			if (cuenta.getAlias().equals(alias)) {
				cvu = cuenta.getCvu();
			}
		}
		return cvu;
	}

	@Override
	public List<String> consultarHistorialGlobal() { // Recorre el historial global, e imprime cada actividad
		// Transferencia o Inversion
		List<String> lista = new ArrayList<>(); // y guarda en la nueva lista su toString
		for (Actividad actividad : this.historialCompletoDeActividades) {
			lista.add(actividad.toString());
		}
		return lista;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		// TODO Auto-generated method stub

		validarParametro(cvu);

		validarCuentaNOExiste(cvu);

		Cuenta cuenta = cuentas.get(cvu);

		List<String> historial = new ArrayList<>();// se crea una lista vacia y aca se van a guardar los mov.

		for (Actividad act : cuenta.getActividades()) {// recorre cada actividad dentro de una cuenta

			historial.add(act.toString());// convierte la actividad en texto
		}

		return historial; // devuelve toda la lista completa
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
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
	public double obtenerTotalInvertido(String dniUsuario) { // falta implementar en subclases
		// Validaciones:
		validarUsuarioNOExiste(dniUsuario);

		Usuario usuario = usuarios.get(dniUsuario);
		return usuario.getTotalInvertido();
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
		// validacion de parametro
		if (cantidadTop <= 0)
			throw new IllegalArgumentException("Cantidad inválida.");

		List<Cuenta> todas = new ArrayList<>(cuentas.values());
		todas.sort((a, b) -> Double.compare(b.getVolumenTransacciones(), a.getVolumenTransacciones()));

		List<String> resultado = new ArrayList<>();
		for (int i = 0; i < Math.min(cantidadTop, todas.size()); i++) {
			Cuenta c = todas.get(i);
			resultado.add(c.getTipo() + ": " + c.getAlias() + " (" + c.getCvu() + ")");
		}
		return resultado;
	}

	private void validarEmpresaExiste(String cuitEmpresa) {

		if (!empresas.containsKey(cuitEmpresa)) {

			throw new IllegalArgumentException("La empresa no existe.");
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("=== BILLETERA ===\n");

		// foreach sobre usuarios
		for (Usuario u : usuarios.values()) {
			sb.append(u.toString()).append("\n");
		}
		return sb.toString();
	}
	
	/////////////// VALIDACIONES///////////////////////
	private void validarParametro(String parametro) {
		if (parametro == null || parametro.isEmpty()) {
			throw new IllegalArgumentException("Parámetro '" + parametro + "' inválido.");
		}
	}

	private void validarUsuarioExiste(String dni) {// ver esto no es al reves lo qeu sale ??
		if (usuarios.containsKey(dni)) {
			throw new IllegalArgumentException("El usuario ya Existe.");
		}
	}

	private void validarUsuarioNOExiste(String dni) {
		if (!usuarios.containsKey(dni)) {
			throw new IllegalArgumentException("El usuario NO Existe.");
		}
	}

	private void validarAliasEXISTE(String alias) {
		for (Cuenta cuenta : cuentas.values()) {
			if (cuenta.getAlias().equals(alias)) {
				throw new IllegalArgumentException("El alias ya Existe.");
			}
		}
	}

	private void validarAliasNOExiste(String alias) {
		for (Cuenta cuenta : cuentas.values()) {
			if (cuenta.getAlias().equals(alias)) {
				return; // si encontro el alias, no hace nada
			}
		}
		throw new IllegalArgumentException("El alias NO existe.");
	}

	private void validarCuentaNOExiste(String cuenta) {
		if (!cuentas.containsKey(cuenta)) {
			throw new IllegalArgumentException("La cuenta NO existe.");
		}
	}
	/////////////////////////////////////////////////
}
