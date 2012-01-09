package reader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;

import coherence.Events;
import coherence.CoherenceChecker;

import mind.Mind;
import mind.ChangedMind;

import evaluator.IEvaluator;

import operator.OperatorApplicator;
import utils.DoublePriorityQueue;


/**
 * Crea una mente nueva aplicando operadores de generalizaci�n y especializaci�n.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class MindEvolver extends Observable {
	
	/**
	 * M�ximo n�mero de padres a engendrar.
	 */
	private int maxMindExpansions = 0;
	
	/**
	 * Evaluador de las mentes generadas.
	 * */
	private IEvaluator evaluator;
	
	/**
	 * Comprobador de la coherencia de la historia.
	 */
	private CoherenceChecker coherenceChecker;
	
	/**
	 * Generador de descendientes de ChangedMinds aplicando operadores.
	 */
	private OperatorApplicator operatorApplicator;

	/**
	 * Pila de mentes creadas hasta el momento.
	 */
	private DoublePriorityQueue<ChangedMind> mindsQueue;
	
	/**
	 * La mejor mente en evoluci�n.
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
	public MindEvolver(IEvaluator evaluator, CoherenceChecker coherenceChecker) {
		maxMindExpansions = 3;
		mindsQueue = new DoublePriorityQueue<ChangedMind>();
		this.evaluator = evaluator;
		this.coherenceChecker = coherenceChecker;
		this.operatorApplicator = new OperatorApplicator();
		generatedMinds = new HashSet<Integer>();
	}
	
	/**
	 * Cambia la mente (conceptos que tiene la mente).
	 * @param mind Mente a evolucionar.
	 * @return La mente con los conceptos cambiados tras la evoluci�n.
	 */
	public ChangedMind evolveMind(Mind mind, Events events) {
		
		generatedMinds = new HashSet<Integer>();
		mindsQueue = new DoublePriorityQueue<ChangedMind>();
		bestMind = new ChangedMind(mind);
		mindsQueue.add(bestMind);

		for (int i = 0; i < maxMindExpansions; i++) {
			
			// Obtiene la mente m�s favorable de la lista
			ArrayList<ChangedMind> operatedMinds = mindsQueue.getSelectedMinds(); // Saca la cima y la borra
		
			// Genera los hijos como resultado de operar esa mente
			ArrayList<ChangedMind> mindSons = new ArrayList<ChangedMind>();
			for (ChangedMind operatedMind : operatedMinds) 
				mindSons.addAll(operatorApplicator.generateChilds(operatedMind));

			// Elimina los hijos que ya han sido generados antes
			mindSons = filterMinds(mindSons);
			
			// Elimina los hijos que no son coherentes
			mindSons = coherenceMinds(mindSons, events);
			
			// Eval�a los hijos generados
			evalMinds(mindSons);
			
			// Inserta los hijos en el total de mentes generadas y ordenados seg�n su valor
			insertMinds(mindSons);
			
			updateBestMind();	
			
			setChanged();
			notifyObservers(new Integer(i + 1));
		}

		return bestMind; // la m�s favorable seg�n su valor
	}
	
	/**
	 * Filtra los mundos generados para tratar de no repetir ninguno.
	 * @param mindSons Lista de hijos para filtrar.
	 * */
	private ArrayList<ChangedMind> filterMinds(ArrayList<ChangedMind> mindSons) {
		ArrayList<ChangedMind> filteredMinds = new ArrayList<ChangedMind>();
		
		for (ChangedMind mind : mindSons) {
			if (!generatedMinds.contains(mind.getActualMind().hashCode())) {
				filteredMinds.add(mind);
			}
		}
		
		return filteredMinds;
	}
	
	/**
	 * Eval�a las mentes generadas asign�ndoles a cada una un valor heur�stico.
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
		for (ChangedMind w: sons) {
			mindsQueue.add(w);
			generatedMinds.add(w.getActualMind().hashCode());
		}
	}
	
	/**
	 * Filtra las mentes que no son coherentes con muertes y matrimonios.
	 * @param mindSons Hijos a comprobar.
	 * @param events Eventos sucedidos hasta el momento.
	 * @return El listado filtrado de mentes.
	 */
	private ArrayList<ChangedMind> coherenceMinds(ArrayList<ChangedMind> mindSons, Events events) {
		ArrayList<ChangedMind> filteredMinds = new ArrayList<ChangedMind>();
		
		for (ChangedMind mind : mindSons) {
			if (coherenceChecker.checkCoherence(events, mind))
				filteredMinds.add(mind);
		}
		
		return filteredMinds;
	}
	
	/**
	 * Actualiza el mejor resultado de la evoluci�n.
	 */
	private void updateBestMind() {
		
		// Obtiene la mente m�s favorable de la lista
		ChangedMind bestInQueue = mindsQueue.peek();
		
		if (bestInQueue.compareTo(bestMind) >= 0) 
			bestMind = bestInQueue.clone();
	}

	/**
	 * Obtiene el n�mero de iteraciones en la generaci�n del segmento.
	 * @return
	 */
	public int getNumIterations() {
		return maxMindExpansions;
	}

	/**
	 * Establece el n�mero de iteraciones para la generaci�n de un segmento.
	 * @param numIt El nuevo n�mero de iteraciones.
	 */
	public void setNumIterations(int numIt) {
		maxMindExpansions = numIt;
	}

	public void setEvaluator(IEvaluator evaluator) {
		this.evaluator = evaluator;
	}

}
