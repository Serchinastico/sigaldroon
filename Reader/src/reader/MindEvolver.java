package reader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.PriorityQueue;

import mind.Mind;
import mind.ChangedMind;

import evaluator.IEvaluator;

import operator.Generalize;
import operator.Specialize;


/**
 * Crea una mente nueva aplicando operadores de generalizaci�n y especializaci�n.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class MindEvolver extends Observable implements IMindEvolver {
	
	/**
	 * M�ximo n�mero de padres a engendrar.
	 */
	private int maxMindExpansions = 0;
	
	/**
	 * Evaluador de las mentes generadas.
	 * */
	private IEvaluator evaluator;

	/**
	 * Pila de mentes creadas hasta el momento.
	 */
	private PriorityQueue<ChangedMind> mindsQueue;
	
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
	public MindEvolver(IEvaluator evaluator) {
		maxMindExpansions = 3;
		mindsQueue = new PriorityQueue<ChangedMind>();
		this.evaluator = evaluator;
		generatedMinds = new HashSet<Integer>();
	}
	
	@Override
	public ChangedMind evolveMind(Mind mind) {
		
		generatedMinds = new HashSet<Integer>();
		mindsQueue = new PriorityQueue<ChangedMind>();
		bestMind = new ChangedMind(mind);
		mindsQueue.add(bestMind);
		
		for (int i = 0; i < maxMindExpansions; i++) {
			
			// Obtiene la mente m�s favorable de la lista
			ChangedMind operatedMind = mindsQueue.poll(); // Saca la cima y la borra
		
			// Genera los hijos como resultado de operar esa mente
			ArrayList<ChangedMind> mindSons = operateMind(operatedMind);
			
			// Elimina los hijos que ya han sido generados antes
			mindSons = filterMinds(mindSons);
			
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
		//TODO: implementar la inserci�n de mentes en la cola de prioridad
		for (ChangedMind w: sons) {
			mindsQueue.add(w);
			generatedMinds.add(w.getActualMind().hashCode());
		}
	}
	
	private void updateBestMind() {
		
		// Obtiene la mente m�s favorable de la lista
		ChangedMind bestInQueue = mindsQueue.peek();
		
		if (bestInQueue.compareTo(bestMind) >= 0) 
			bestMind = bestInQueue.copy();
	}

	@Override
	public int getNumIterations() {
		return maxMindExpansions;
	}

	@Override
	public void setNumIterations(int numIt) {
		maxMindExpansions = numIt;
	}

	@Override
	public void insertObserver(Observer o) {
		this.addObserver(o);
	}

	@Override
	public void removeObserver(Observer o) {
		this.deleteObserver(o);
	}

}
