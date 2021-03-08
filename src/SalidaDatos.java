import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import java.text.DecimalFormat;

/**
 * Esta clase se encarga de mostrar la salida de la simulación
 * El usuario podrá elegir por consola, en forma de gráfica o ambas
 * @version 1.0
 *
 */
public class SalidaDatos {
	private GestorComunidades gestor;
	
	public SalidaDatos() {

	}
	
	//Carga el gestor sobre el que proyectar la salida de datos
	public void cargarGestor(GestorComunidades gestor) {
		this.gestor = gestor;
	}
	
	//Interactúa con el usuario por consola permitiendole elegir el tipo de salida
	public void elegirSalida(int salida) {
		switch (salida) {
		case 1:
			imprimirDatos();
			break;
		case 2:
			crearGraficaComunidades();
			crearGraficaPorcentaje();
			break;
		case 3:
			imprimirDatos();
			crearGraficaComunidades();
			crearGraficaPorcentaje();
			break;
			
		default:
			throw new IllegalArgumentException("El valor introducido debe estar entre 1 y 3, y ha sido " + salida);
		}
	}

	//Crea la gráfica de contagiados por comunidades
	private void crearGraficaComunidades() {
		double dias[] = new double [this.gestor.getDatos().getNumDias()];
    	for (int i = 1; i <= this.gestor.getDatos().getNumDias(); i++) {
			dias[i-1] = i;
		}
    	DefaultXYDataset dataset = new DefaultXYDataset();
        for (int i = 0; i < this.gestor.getComunidades().size(); i++) {
        	dataset.addSeries(this.gestor.getComunidades().get(i).getNombre(), new double[][] {dias, this.gestor.getComunidades().get(i).getArrayContagiosDia()});
		}
        dataset.addSeries("Totales", new double[][] {dias, this.gestor.getArrayContagiosTotalesDia()});
        JFreeChart chart = ChartFactory.createXYLineChart("Evolución contagiados por comunidades", "Dia", "Contagiados", dataset, PlotOrientation.VERTICAL, true, true, true);

    	ChartFrame f = new ChartFrame("Evolución número de contagiados", chart);
		f.setSize(1000, 1000);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
	//Crea la gráfica de porcentaje de contagiados
	private void crearGraficaPorcentaje() {
		double dias[] = new double [this.gestor.getDatos().getNumDias()];
    	for (int i = 1; i <= this.gestor.getDatos().getNumDias(); i++) {
			dias[i-1] = i;
		}
    	DefaultXYDataset dataset = new DefaultXYDataset();
        for (int i = 0; i < this.gestor.getComunidades().size(); i++) {
        	dataset.addSeries(this.gestor.getComunidades().get(i).getNombre(), new double[][] {dias, this.gestor.getComunidades().get(i).getArrayPorcentajeContagiosDia()});
		}
        dataset.addSeries("Totales", new double[][] {dias, this.gestor.getArrayPorcentajeContagiosTotalesDia()});
        JFreeChart chart = ChartFactory.createXYLineChart("Porcentaje población contagiada por días", "Dia", "Porcentaje", dataset, PlotOrientation.VERTICAL, true, true, true);

    	ChartFrame f = new ChartFrame("Evolución porcentaje de contagiados", chart);
		f.setSize(1000, 1000);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
	//Imprime los datos por consola
	private void imprimirDatos() {
		DecimalFormat formateador = new DecimalFormat("##.######");
		for (int i = 0; i < this.gestor.getDatos().getNumDias(); i++) {
			System.out.println("-+-+-+-+");
			System.out.println("DÍA "+ (i+1));
			for (int j = 0; j < this.gestor.getComunidades().size(); j++) {
				System.out.println("Datos de "+this.gestor.getComunidades().get(j).getNombre()+":");
				System.out.println((int)this.gestor.getComunidades().get(j).getArrayContagiosDia()[i]+" contagiados, que suponen el "
				+formateador.format(this.gestor.getComunidades().get(j).getArrayPorcentajeContagiosDia()[i])+" % de la población de la comunidad.");
				System.out.println();
				if (j == (this.gestor.getComunidades().size()-1)) {
					System.out.println((int)this.gestor.getArrayContagiosTotalesDia()[i]+" contagiados de todas las comunidades, que suponen el "
					+formateador.format(this.gestor.getArrayPorcentajeContagiosTotalesDia()[i])+" % de la población total.");
					System.out.println("-+-+-+-+");
					System.out.println("\n");
				}
			}
		}
	}
}
