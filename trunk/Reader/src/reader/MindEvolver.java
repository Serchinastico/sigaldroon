package reader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Observable;
import java.util.Random;

import coherence.Events;
import coherence.CoherenceChecker;

import mind.Mind;
import mind.ChangedMind;
import mind.RelationSet;

import evaluator.IEvaluator;

import operator.OperatorApplicator;
import utils.DoublePriorityQueue;


/**
 * Crea una mente nueva aplicando operadores de generalización y especialización.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class MindEvolver extends Observable {
	
	/**
	 * Máximo número de padres a engendrar.
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
	 * La mejor mente en evolución.
	 */
	private ChangedMind bestMind;
	
	/**
	 * Conjunto de mentes generadas hasta el momento para no repetir.
	 * */
	private HashSet<Integer> generatedMinds; 
	
	/**
	 * Hashcode de las mentes generadas hasta el momento de la historia para no repetir segmentos iguales.
	 */
	private HashSet<Integer> storyMinds;
	
	/**
	 * Relaciones recientes.
	 */
	private RelationSet recentRelations;
	
	/**
	 * Constructora para el evolucionador.
	 * @param evaluator Evaluador a usar por el evolucionador de mentes.
	 */
	public MindEvolver(IEvaluator evaluator, CoherenceChecker coherenceChecker) {
		maxMindExpansions = 3;
		mindsQueue = new DoublePriorityQueue<ChangedMind>(ChangedMind.getGreaterComparator(), ChangedMind.getLowerComparator());
		this.evaluator = evaluator;
		this.coherenceChecker = coherenceChecker;
		this.operatorApplicator = new OperatorApplicator();
		generatedMinds = new HashSet<Integer>();
		storyMinds = new HashSet<Integer>();
		recentRelations = new RelationSet();
	}
	
	/**
	 * Cambia la mente (conceptos que tiene la mente).
	 * @param mind Mente a evolucionar.
	 * @return La mente con los conceptos cambiados tras la evolución.
	 */
	public ChangedMind evolveMind(Mind mind, Events events) {
		
		generatedMinds = new HashSet<Integer>();
		mindsQueue = new DoublePriorityQueue<ChangedMind>(ChangedMind.getGreaterComparator(), ChangedMind.getLowerComparator());
		//bestMind = new ChangedMind(mind);
		bestMind = null;
		//mindsQueue.add(bestMind);
		mindsQueue.add(new ChangedMind(mind));
		generatedMinds.add(mind.hashCode());

		for (int i = 0; i < maxMindExpansions; i++) {
			
			// Obtiene la mente más favorable de la lista
			ArrayList<ChangedMind> operatedMinds = mindsQueue.getSelectedMinds(); // Saca la cima y la borra
		
			// Genera los hijos como resultado de operar esa mente
			ArrayList<ChangedMind> mindSons = new ArrayList<ChangedMind>();
			for (ChangedMind operatedMind : operatedMinds) 
				mindSons.addAll(operatorApplicator.generateChilds(operatedMind));

			// Elimina los hijos que ya han sido generados antes
			mindSons = filterMinds(mindSons);
			
			// Elimina los hijos que no son coherentes
			mindSons = coherenceMinds(mindSons, events);
			
			// Evalúa los hijos generados
			evalMinds(mindSons);
			
			// Inserta los hijos en el total de mentes generadas y ordenados según su valor
			insertMinds(mindSons);
			
			updateBestMind();	
			
			setChanged();
			notifyObservers(new Integer(i + 1));
		}

		Random random = new Random(System.currentTimeMillis());
		int selected = Math.min(random.nextInt(5), mindsQueue.size()-1);
		for (int i = 0; i < selected; i++) {
			bestMind = mindsQueue.pollGreater();
		}
		
		storyMinds.add(bestMind.getActualMind().hashCode());
		recentRelations.updateWeights();
		recentRelations.addAll(bestMind.getResultingRelations());
		
		//System.out.print("+");
		
		return bestMind; 
	}
	
	/**
	 * Filtra los mundos generados para tratar de no repetir ninguno.
	 * @param mindSons Lista de hijos para filtrar.
	 * */
	private ArrayList<ChangedMind> filterMinds(ArrayList<ChangedMind> mindSons) {
		ArrayList<ChangedMind> filteredMinds = new ArrayList<ChangedMind>();
		
		for (ChangedMind mind : mindSons) {
			if ((!generatedMinds.contains(mind.getActualMind().hashCode()))  
			&& (!storyMinds.contains(mind.getActualMind().hashCode()))) {
				filteredMinds.add(mind);
			}
		}
		
		return filteredMinds;
	}
	
	/**
	 * Evalúa las mentes generadas asignándoles a cada una un valor heurístico.
	 * @param sons Mentes para evaluar.
	 * */
	private void evalMinds(ArrayList<ChangedMind> sons) {
		for (ChangedMind w : sons) {
			w.setValue(evaluator.eval(w.getActualMind(),recentRelations));
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
	 * Actualiza el mejor resultado de la evolución.
	 */
	private void updateBestMind() {
		
		// Obtiene la mente más favorable de la lista
		ChangedMind bestInQueue = mindsQueue.peek();
		
		if ( (bestMind == null) || (bestInQueue.compareTo(bestMind) >= 0))
			bestMind = bestInQueue.clone();
	}

	/**
	 * Obtiene el número de iteraciones en la generación del segmento.
	 * @return
	 */
	public int getNumIterations() {
		return maxMindExpansions;
	}

	/**
	 * Establece el número de iteraciones para la generación de un segmento.
	 * @param numIt El nuevo número de iteraciones.
	 */
	public void setNumIterations(int numIt) {
		maxMindExpansions = numIt;
	}

	public void setEvaluator(IEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	public void resetStoryMinds() {
		this.storyMinds = new HashSet<Integer>();
	}
	
	public void resetRecentRelations() {
		this.recentRelations = new RelationSet();
	}

}
