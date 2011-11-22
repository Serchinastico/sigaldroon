package reader;

import java.util.ArrayList;
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
public class MindEvolver implements IMindEvolver {
	
	/**
	 * M�ximo n�mero de padres a engendrar.
	 */
	private int maxMindExpansions;
	
	/**
	 * Evaluador de las mentes generadas.
	 * */
	private IEvaluator evaluator;

	/**
	 * Pila de mentes creadas hasta el momento.
	 */
	private PriorityQueue<ChangedMind> mindsGenerated;
	
	/**
	 * La mejor mente en evoluci�n.
	 */
	private ChangedMind bestMind;
	
	/**
	 * Constructora para el evolucionador.
	 * @param evaluator Evaluador a usar por el evolucionador de mentes.
	 */
	public MindEvolver(IEvaluator evaluator) {
		maxMindExpansions = 3;
		mindsGenerated = new PriorityQueue<ChangedMind>();
		this.evaluator = evaluator;
	}
	
	@Override
	public ChangedMind evolveMind(Mind mind) {
		
		mindsGenerated = new PriorityQueue<ChangedMind>();
		bestMind = new ChangedMind(mind);
		mindsGenerated.add(bestMind);
		
		for (int i = 0; i < maxMindExpansions; i++) {
			
			// Obtiene la mente m�s favorable de la lista
			ChangedMind operatedMind = mindsGenerated.poll(); // Saca la cima y la borra
		
			// Genera los hijos como resultado de operar esa mente
			ArrayList<ChangedMind> mindSons = operateMind(operatedMind);
			
			// Eval�a los hijos generados
			evalMinds(mindSons);
			
			// Inserta los hijos en el total de mentes generadas y ordenados seg�n su valor
			insertMinds(mindSons);
			
			updateBestMind();
			
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
			mindsGenerated.add(w);
		}
	}
	
	private void updateBestMind() {
		
		// Obtiene la mente m�s favorable de la lista
		ChangedMind bestInQueue = mindsGenerated.peek();
		
		if (bestInQueue.compareTo(bestMind) >= 0) 
			bestMind = bestInQueue.copy();
	}

}
