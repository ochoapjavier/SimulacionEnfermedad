import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Esta clase gestiona todas las comunidades creadas, almacenandolas en un array y preparandolas
 * con la distribución de viajeros entre ellas.Guarda datos globales referentes a valores de conjunto 
 * de las comunidades como contagios y porcentajes totales.
 * @author Javier Ochoa Pérez
 * @version 1.0
 *
 */

public class GestorComunidades {
	private ArrayList<Comunidad> comunidades;
	private Datos datos;
	private int poblacionTotal;
	private double arrayContagiosTotalesDia[];
	private double arrayPorcentajeContagiosTotalesDia[];
	
	public GestorComunidades() {
		this.comunidades = new ArrayList<Comunidad>();
	}
	
	//Carga los datos globales
	public void cargarDatos(Datos datos) {
		this.datos = datos;
	}
	
	//Inicia los datos globales necesarios, población total y distribuye viajeros
	public void iniciar() {
		this.arrayContagiosTotalesDia = new double [this.datos.getNumDias()];
		this.arrayPorcentajeContagiosTotalesDia = new double [this.datos.getNumDias()];
		calcularPoblacionTotal();
		distribuirViajeros();
	}
	
	//Calcula el porcentaje de contagiados de cada día
	public void calcularPorcentajeDias() {
		for (int i = 0; i < this.datos.getNumDias(); i++) {
			this.getArrayPorcentajeContagiosTotalesDia()[i] = (double)this.getArrayContagiosTotalesDia()[i]/this.getPoblacionTotal()*100;
		}
	}
	
	//Incluye los infectados del día dado en el array
	public void incluirContagiadosDias(int dia, int infectadosTotales) {
		this.arrayContagiosTotalesDia[dia-1] = (double)infectadosTotales;
	}
	
	//Crea y añade una comunidad al array de comunidades
	public void addComunidad(Comunidad comunidad) {
		calcularViajerosDiarios(comunidad);
		comunidades.add(comunidad);
	}
	
	//Calcula los viajeros diarios de las otras comunidades a la pasada como argumento
	private void calcularViajerosDiarios(Comunidad comunidad) {
		int viajeros = (int) (comunidad.getPoblacion()*this.datos.getPorcentajeViajeros()/100);
		comunidad.setViajerosDiarios(viajeros);
	}
	
	//Distribuye los viajeros de todas las comunidades
	private void distribuirViajeros() {
		for (int i = 0; i < this.comunidades.size(); i++) {	//Comunidad que llena el array
			ArrayList<Integer> visitantes = new ArrayList<Integer>();
			prepararArray(visitantes);
			for (int j = 0; j < this.comunidades.size(); j++) {
				if (i != j) {
					int viajeros = calculoViajeros(this.comunidades.get(j), this.comunidades.get(i));
					visitantes.set(j, viajeros);
				}
			}
			this.comunidades.get(i).setViajerosComunidades(visitantes);
		}
	}
	
	//Prepara un array inicializandolo a 0 en el tamaño apropiado
	private void prepararArray (ArrayList<Integer> array) {
		for (int i = 0; i < this.comunidades.size(); i++) {
			array.add(0);			//Se inicializa el array a 0 en todas las posiciones.
		}
	}
	
	//Calcula la población total de todas las comunidades (suma de todas las comunidades)
	private void calcularPoblacionTotal() {
		for (int i = 0; i < this.comunidades.size(); i++) {
			this.poblacionTotal = this.poblacionTotal + this.comunidades.get(i).getPoblacion();
		}
	}
	
	//Calcula los viajeros de una comunidad a otra 
	private int calculoViajeros(Comunidad origen, Comunidad destino) {
		String op1 = String.valueOf(origen.getViajerosDiarios());
		BigInteger operador1 = new BigInteger(op1);
		
		String op2 = String.valueOf(destino.getPoblacion());
		BigInteger operador2 = new BigInteger(op2);
		
		BigInteger numerador = operador1.multiply(operador2);
		
		String op3 = String.valueOf(this.getPoblacionTotal());
		BigInteger operador3 = new BigInteger(op3);
		
		String op4 = String.valueOf(origen.getPoblacion());
		BigInteger operador4 = new BigInteger(op4);
		
		BigInteger denominador = operador3.subtract(operador4);
		
		BigInteger resultado = numerador.divide(denominador);
		
		int viajeros = resultado.intValue();
		return viajeros;
	}
	
	//Getters and setters
	public ArrayList<Comunidad> getComunidades() {
		return comunidades;
	}

	public void setComunidades(ArrayList<Comunidad> comunidades) {
		this.comunidades = comunidades;
	}

	public Datos getDatos() {
		return datos;
	}

	public void setDatos(Datos datos) {
		this.datos = datos;
	}
	
	public int getPoblacionTotal() {
		return poblacionTotal;
	}

	public void setPoblacionTotal(int poblacionTotal) {
		this.poblacionTotal = poblacionTotal;
	}

	public double[] getArrayContagiosTotalesDia() {
		return arrayContagiosTotalesDia;
	}

	public void setArrayContagiosTotalesDia(double[] arrayContagiosTotalesDia) {
		this.arrayContagiosTotalesDia = arrayContagiosTotalesDia;
	}

	public double[] getArrayPorcentajeContagiosTotalesDia() {
		return arrayPorcentajeContagiosTotalesDia;
	}

	public void setArrayPorcentajeContagiosTotalesDia(double[] arrayPorcentajeContagiosTotalesDia) {
		this.arrayPorcentajeContagiosTotalesDia = arrayPorcentajeContagiosTotalesDia;
	}

}
