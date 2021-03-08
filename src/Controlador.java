import java.util.Scanner;

/**
 * Esta clase organiza y controla la creación de objetos y 
 * la relación entre ellos. Instancia todas las clases y llama 
 * a los métodos para realizar la simulación
 * @author Javier Ochoa Pérez
 * @version 1.0
 *
 */

public class Controlador {

	private Scanner sc;
	
	public Controlador() {
		this.sc = new Scanner (System.in);
	}
	
	public void nuevaSimulacion() {
		Datos datos = new Datos();
		GestorComunidades gestor = new GestorComunidades();
		Simulador simulador = new Simulador();
		SalidaDatos salida = new SalidaDatos();
		
		//Carga en la instancia datos los datos generales que el usuario introduce por consola
		cargarDatos(datos);
		//Carga en el gestor de comunidades los datos
		gestor.cargarDatos(datos);
		//Carga las comunidades introduciendolas por consola
		cargaComunidades(gestor);
		//Prepara las comunidades en el gestor de comunidades
		gestor.iniciar();
		//Carga el gestor en el simulador para realizar la simulación
		simulador.cargarGestor(gestor);
		//Realiza la simulación
		simulador.simular();
		//Carga el gestor tras realizar la simulación para la salida de datos
		salida.cargarGestor(gestor);
		//Elige el formato de la salida
		int formatoSalida = elegirSalida();
		salida.elegirSalida(formatoSalida);
	}
	
	//Carga los datos globales (coeficientes número de días y número de comunidades) por consola
	private void cargarDatos(Datos datos) {		
		System.out.println("Introduce el porcentaje de habitantes de cada comunidad que viajan a otras en formato 0,00");
		
		try {
			datos.setPorcentajeViajeros(sc.nextDouble());
		} catch (Exception e) {
			System.out.println("Error en el formato de entrada. Excepción " + e + ". Cerrando la simulación");
			System.exit(1);
		}
		
		System.out.println("Introduce el coeficiente E");
		datos.setE(sc.nextDouble());
		System.out.println("Introduce el coeficiente p en formato 0,00");
		double p = sc.nextDouble();
		while (p < 0 || p > 1) {
			System.out.println("Error en el coeficiente p. El valor debe estar comprendido entre 0 y 1. Vuelva a intentarlo");
			p = sc.nextDouble();
		}
		datos.setP(p);
		System.out.println("Introduce el número de días a simular");
		datos.setNumDias(sc.nextInt());	
	}	
	
	//Introduce las comunidades por la consola
	private void cargaComunidades(GestorComunidades gestor) {
		System.out.println("Introduce el número de comunidades: ");
		int numComunidades = sc.nextInt();
		boolean esComunidadOrigen = false;		//Bandera en true si es comunidad origen
		boolean hayComunidadOrigen = false;		//Bandera en true si hay comuidad origen
		for (int i = 1; i <= numComunidades; i++) {
			System.out.println("Indique el nombre de la comunidad " + i);
			String nombre = sc.next();
			System.out.println("Indique el número de habitantes de " + nombre);
			int habitantes = sc.nextInt();
			if(!hayComunidadOrigen) {
				System.out.println("El primer infectado es de " + nombre +  " ? (y/n)");
				String respuesta = sc.next();
				if (respuesta.equals("y")) {
					esComunidadOrigen = true;
					hayComunidadOrigen = true;
				} else if (respuesta.equals("n")) {
					esComunidadOrigen = false;
				}
			} else {
				esComunidadOrigen = false;
			}
			Comunidad comunidad = new Comunidad(nombre, habitantes, esComunidadOrigen);
			gestor.addComunidad(comunidad);
		}
	}
	
	private int elegirSalida() {
		int salida = -1;
		while (salida < 0 || salida > 3) {
			System.out.println("¿Como desea mostrar los resultados?");
			System.out.println("1. Por consola");
			System.out.println("2. En una gráfica");
			System.out.println("3. Por consola y en una gráfica");
			salida = sc.nextInt();
		}
		return salida;
		
	}
}
