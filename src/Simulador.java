import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Esta clase realiza la simulación a partir del gestor que se debe cargar.
 * obteniendo de él los datos globales y los particulares de cada comunidad
 * @author Javier Ochoa Pérez
 * @version 1.0
 *
 */

public class Simulador {
	private GestorComunidades gestor;

	public Simulador() {

	}
	
	//Carga en el simulador el gestor sobre el que simular
	public void cargarGestor(GestorComunidades gestor) {
		this.gestor = gestor;
	}
	
	//Realiza la simulación
	public void simular () {
		for (int i = 0; i < this.gestor.getComunidades().size(); i++) {
			double arrayContagiados[] = new double [this.gestor.getDatos().getNumDias()];
			this.gestor.getComunidades().get(i).setArrayContagiosDia(arrayContagiados);
			double arrayPorcentaje[] = new double [this.gestor.getDatos().getNumDias()];
			this.gestor.getComunidades().get(i).setArrayPorcentajeContagiosDia(arrayPorcentaje);
		}
		
		for (int i = 1; i <= this.gestor.getDatos().getNumDias(); i++) {
			int infectadosTotales = 0;
			if (i == 1) {
				for (int j = 0; j < this.gestor.getComunidades().size(); j++) {
				
					if (this.gestor.getComunidades().get(j).getContagiadosTotales()==1) {
						//Es la inicial
						infectadosTotales = this.gestor.getComunidades().get(j).getContagiadosTotales();					
						this.gestor.incluirContagiadosDias(i,infectadosTotales);
						this.gestor.getComunidades().get(j).incluirPorcentajeContagiados(i);
						
						calcularEncuentrosNoContagiados(this.gestor.getComunidades().get(j));
					}					
					this.gestor.getComunidades().get(j).getArrayContagiosDia()[i-1]=this.gestor.getComunidades().get(j).getContagiadosTotales();
				}
			} else {
				//DESPUÉS DEL CASO INICIAL
				for (int j = 0; j < this.gestor.getComunidades().size(); j++) {
					//CALCULO CONTAGIADOS INTERNOS Y EXTERNOS
					calcularContagiadosInternos(this.gestor.getComunidades().get(j));	//Calcular contagiados internos
					calcularContagiadosExternos(this.gestor.getComunidades().get(j));	//Calcular contagiados externos (array por comunidades)
					this.gestor.getComunidades().get(j).totalContagiadosExternos();		//Sumar contagiados externos
				}
				for (int k = 0; k < this.gestor.getComunidades().size(); k++) {
					//UNA VEZ ACTUALIZADOS LOS VALORES CALCULO PORCENTAJES DE CONTAGIADOS ESE DÍA Y PROMEDIO DE ENCUENTROS
					this.gestor.getComunidades().get(k).sumarContagiados();
					//Si al sumar contagiados supera numero de población es que toda la población está infectada
					if(this.gestor.getComunidades().get(k).getContagiadosTotales() >= this.gestor.getComunidades().get(k).getPoblacion()) {
						this.gestor.getComunidades().get(k).setContagiadosTotales(this.gestor.getComunidades().get(k).getPoblacion());
					}

					this.gestor.getComunidades().get(k).incluirPorcentajeContagiados(i);
					calcularEncuentrosNoContagiados(this.gestor.getComunidades().get(k));
					this.gestor.getComunidades().get(k).getArrayContagiosDia()[i-1]=this.gestor.getComunidades().get(k).getContagiadosTotales();
					infectadosTotales = infectadosTotales + this.gestor.getComunidades().get(k).getContagiadosTotales();
					this.gestor.incluirContagiadosDias(i,infectadosTotales);
				}	
			}				
		}
		this.gestor.calcularPorcentajeDias();
	}

	//Calcula el porcentaje de encuentros con personas no contagiadas
	private void calcularEncuentrosNoContagiados(Comunidad comunidad) {
		double promedio = (this.gestor.getDatos().getE() * (1-((double)comunidad.getContagiadosTotales()/comunidad.getPoblacion())));
		comunidad.setEncuentrosNoContagiados(promedio);
	}
	
	//Calcula los contagiados internos de una comunidad
	private void calcularContagiadosInternos(Comunidad comunidad) { 
		int contagiadosInternos = (int) (comunidad.getContagiadosTotales() * comunidad.getEncuentrosNoContagiados() * this.gestor.getDatos().getP());
		comunidad.setContagiadosInternos(contagiadosInternos);
 	}
	
	//Calcula los contagiados externos (array por comunidades) de la comunidad pasada como argumento
	public void calcularContagiadosExternos(Comunidad comunidad) {
		ArrayList<Integer> contagiados = new ArrayList<Integer>();
		prepararArray(contagiados);
		for (int i = 0; i < comunidad.getContagiadosComunidades().size(); i++) {
			int resultado = (int) (this.gestor.getDatos().getP() * comunidad.getEncuentrosNoContagiados() * 
					comunidad.getViajerosComunidades().get(i) * this.gestor.getComunidades().get(i).getContagiadosTotales()/this.gestor.getComunidades().get(i).getPoblacion());
					contagiados.set(i, resultado);
		}
		comunidad.setContagiadosComunidades(contagiados);
	}
	
	//Prepara un array inicializandolo a 0 en el tamaño apropiado
	private void prepararArray (ArrayList<Integer> array) {
		for (int i = 0; i < this.gestor.getComunidades().size(); i++) {
			array.add(0);			//Se inicializa el array a 0 en todas las posiciones.
		}
	}
	
	//Getters and setters
	public GestorComunidades getGestor() {
		return gestor;
	}

	public void setGestor(GestorComunidades gestor) {
		this.gestor = gestor;
	}
		
}