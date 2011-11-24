package reader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import mind.Mind;
import mind.ChangedMind;

import evaluator.IEvaluator;

import operator.Generalize;
import operator.Specialize;


/**
 * Crea una mente nueva aplicando operadores de generalización y especialización.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class MindEvolver implements IMindEvolver {
	
	/**
	 * Máximo número de padres a engendrar.
	 */
	private int maxMindExpansions;
	
	/**
	 * Evaluador de las mentes generadas.
	 * */
	private IEvaluator evaluator;

	/**
	 * Pila de mentes creadas hasta el momento.
	 */
	private PriorityQueue<ChangedMind> mindsQueue;
	
	/**
	 * La mejor mente en evolución.
	 */
	private ChangedMind bestMind;
	
	/**
	 * Conjunto de mentes generadas hasta el momento para no repetir.
	 * */
	private HashSet<Integer> generatedMinds; 
	
	/**
	 * Constructora para el evolucionador.
	 * @param evaluator Evaluador a usar por el evolucionador de mentes.
	 */
	public MindEvolver(IEvaluator evaluator) {
		maxMindExpansions = 3;
		mindsQueue = new PriorityQueue<ChangedMind>();
		this.evaluator = evaluator;
		generatedMinds = new HashSet<Integer>();
	}
	
	@Override
	public ChangedMind evolveMind(Mind mind) {
		
		mindsQueue = new PriorityQueue<ChangedMind>();
		bestMind = new ChangedMind(mind);
		mindsQueue.add(bestMind);
		
		for (int i = 0; i < maxMindExpansions; i++) {
			
			// Obtiene la mente más favorable de la lista
			ChangedMind operatedMind = mindsQueue.poll(); // Saca la cima y la borra
		
			// Genera los hijos como resultado de operar esa mente
			ArrayList<ChangedMind> mindSons = operateMind(operatedMind);
			
			// Elimina los hijos que ya han sido generados antes
			filterMinds(mindSons);
			
			// Evalúa los hijos generados
			evalMinds(mindSons);
			
			// Inserta los hijos en el total de mentes generadas y ordenados según su valor
			insertMinds(mindSons);
			
			updateBestMind();
			
		}
		
		return bestMind; // la más favorable según su valor
	}

	/**
	 * Aplica los operadores a una mente para generar todos los posibles hijos.
	 * @param m Mente que operar.
	 * @return Los hijos generados tras aplicar operadores.
	 */
	private ArrayList<ChangedMind> operateMind(ChangedMind m) {
		
		ArrayList<ChangedMind> sons = new ArrayList<ChangedMind>();
		
		Specialize specializeOp = new Specialize();
		specializeOp.generateMinds(m, sons);
		
		Generalize generalizeOp = new Generalize();
		generalizeOp.generateMinds(m, sons);
		
		return sons;
	}
	
	/**
	 * Filtra los mundos generados para tratar de no repetir ninguno.
	 * @param mindSons Lista de hijos para filtrar.
	 * */
	// TODO: Sería más eficiente si se filtraran en los operadores (?)
	private void filterMinds(ArrayList<ChangedMind> mindSons) {
		ChangedMind[] mindSonsCopy = (ChangedMind[]) mindSons.toArray();
		
		for (int iMind = 0; iMind < mindSonsCopy.length; iMind++) {
			ChangedMind mind = mindSonsCopy[iMind];
			if (generatedMinds.contains(mind.getActualMind().hashCode())) {
				mindSons.remove(iMind);
			}
		}		
	}
	
	/**
	 * Evalúa las mentes generadas asignándoles a cada una un valor heurístico.
	 * @param sons Mentes para evaluar.
	 * */
	private void evalMinds(ArrayList<ChangedMind> sons) {
		for (ChangedMind w : sons) {
			w.setValue(evaluator.eval(w.getActualMind()));
		}
	}
	
	/**
	 * Inserta las mentes en la cola de prioridad de mentes generados.
	 * @param sons Mentes a insertar.
	 */
	private void insertMinds(ArrayList<ChangedMind> sons) {
		//TODO: implementar la inserción de mentes en la cola de prioridad
		for (ChangedMind w: sons) {
			mindsQueue.add(w);
			generatedMinds.add(w.getActualMind().hashCode());
		}
	}
	
	private void updateBestMind() {
		
		// Obtiene la mente más favorable de la lista
		ChangedMind bestInQueue = mindsQueue.peek();
		
		if (bestInQueue.compareTo(bestMind) >= 0) 
			bestMind = bestInQueue.copy();
	}

}
