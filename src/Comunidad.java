import java.util.ArrayList;

/**
 * Esta clase contiene los datos de cada una de las comunidades: nombre, 
 * contagiados tanto internos, como externos y totales, viajeros provenientes
 * de otras comunidades
 * @author Javier Ochoa Pérez
 * @version 1.0
 *
 */

public class Comunidad {
	private String nombre;								//Nombre de la comunidad
	private int poblacion;								//Número de habitantes
	private int viajerosDiarios;						//Viajeros diarios que van a otras comunidades
	private ArrayList<Integer> viajerosComunidades;		//Array de viajeros de otras comunidades
	private ArrayList<Integer> contagiadosComunidades;	//Array con contagiados de otras comunidades
	private int contagiadosInternos;					//Número de contagiados internos
	private int contagiadosExternos;					//Número de contagiados externos
	private int contagiadosTotales;						//Número de contagiados totales
	private double encuentrosNoContagiados;				//Promedio encuentros con no contagiados
	private double arrayContagiosDia[];
	private double arrayPorcentajeContagiosDia[];

	public Comunidad(String nombre, int poblacion, boolean esComunidadOrigen) {
		this.poblacion = poblacion;
		this.nombre = nombre;
		if (esComunidadOrigen) {
			this.contagiadosTotales = 1;
		} else {
			this.contagiadosTotales = 0;
		}
		this.viajerosComunidades = new ArrayList<Integer>();
		this.contagiadosComunidades = new ArrayList<Integer>();
	}
	
	//Calcula el número de contagiados externos sumando los contagiados externos de cada comunidad
	public void totalContagiadosExternos () {
		int total = 0;
	
		for (int i = 0; i < this.contagiadosComunidades.size(); i++) {
			total = total + this.contagiadosComunidades.get(i);
		}
		this.contagiadosExternos = total;
	}
	
	//Calcula los contagiados totales sumando internos y externos
	public void sumarContagiados() {
		this.contagiadosTotales = this.contagiadosTotales + this.contagiadosInternos + this.contagiadosExternos;
	}

	//Incluye en el array de porcentaje de contagiados del dia el porcentaje de contagiados
	public void incluirPorcentajeContagiados(int dia) {
		this.arrayPorcentajeContagiosDia[dia-1] = (double) this.getContagiadosTotales()/this.getPoblacion()*100;
	}
	
	//Getters and setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getPoblacion() {
		return poblacion;
	}
	
	public int getViajerosDiarios() {
		return viajerosDiarios;
	}

	public void setViajerosDiarios(int viajerosDiarios) {
		this.viajerosDiarios = viajerosDiarios;
	}

	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}
	
	public ArrayList<Integer> getViajerosComunidades() {
		return viajerosComunidades;
	}

	public void setViajerosComunidades(ArrayList<Integer> viajerosComunidades) {
		this.viajerosComunidades = viajerosComunidades;
	}
	
	public ArrayList<Integer> getContagiadosComunidades() {
		return contagiadosComunidades;
	}

	public void setContagiadosComunidades(ArrayList<Integer> contagiadosComunidades) {
		this.contagiadosComunidades = contagiadosComunidades;
	}
	
	public int getContagiadosInternos() {
		return contagiadosInternos;
	}

	public void setContagiadosInternos(int contagiadosInternos) {
		this.contagiadosInternos = contagiadosInternos;
	}

	public int getContagiadosExternos() {
		return contagiadosExternos;
	}

	public void setContagiadosExternos(int contagiadosExternos) {
		this.contagiadosExternos = contagiadosExternos;
	}

	public int getContagiadosTotales() {
		return contagiadosTotales;
	}

	public void setContagiadosTotales(int contagiadosTotales) {
		this.contagiadosTotales = contagiadosTotales;
	}
	
	public double getEncuentrosNoContagiados() {
		return encuentrosNoContagiados;
	}

	public void setEncuentrosNoContagiados(double encuentrosNoContagiados) {
		this.encuentrosNoContagiados = encuentrosNoContagiados;
	}
	
	public double[] getArrayContagiosDia() {
		return arrayContagiosDia;
	}

	public void setArrayContagiosDia(double[] arrayContagiosDia) {
		this.arrayContagiosDia = arrayContagiosDia;
	}

	public void agregarContagiadosInternos(int nuevosContagiadosInternos) {
		this.contagiadosInternos = this.getContagiadosInternos() + nuevosContagiadosInternos;
	}
	
	public void agregarContagiadosExternos(int nuevosContagiadosExternos) {
		this.contagiadosExternos = this.getContagiadosExternos() + nuevosContagiadosExternos;
	}
	
	public double[] getArrayPorcentajeContagiosDia() {
		return arrayPorcentajeContagiosDia;
	}

	public void setArrayPorcentajeContagiosDia(double[] arrayPorcentajeContagiosDia) {
		this.arrayPorcentajeContagiosDia = arrayPorcentajeContagiosDia;
	}
}
