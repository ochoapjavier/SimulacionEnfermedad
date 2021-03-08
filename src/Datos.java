/**
 * Esta clase contiene los datos globales para realizar la simulación:
 * número de comunidades, coeficientes e y p, porcenaje de viajeros entre
 * comunidades y número de días.
 * @author Javier Ochoa Pérez
 * @version 1.0
 *
 */

public class Datos {
	private double e;
	private double p;
	private double porcentajeViajeros;
	private int numDias;
	
	public Datos() {
		
	}
	
	
	//Getters and setters
	public double getE() {
		return e;
	}

	public void setE(double e) {
		this.e = e;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public double getPorcentajeViajeros() {
		return porcentajeViajeros;
	}

	public void setPorcentajeViajeros(double porcentajeViajeros) {
		this.porcentajeViajeros = porcentajeViajeros;
	}

	public int getNumDias() {
		return numDias;
	}

	public void setNumDias(int numDias) {
		this.numDias = numDias;
	}
}
