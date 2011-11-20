package reader;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.PriorityQueue;

import evaluator.IEvaluator;
import evaluator.SimpleEvaluator;

import operator.Generalize;
import operator.Specialize;

import world.World;
import world.WorldChanged;

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
	 * Evaluador de los mundos generados.
	 * */
	private IEvaluator evaluator;

	/**
	 * Pila de mentes creadas hasta el momento.
	 */
	private PriorityQueue<WorldChanged> mindsGenerated;
	
	/**
	 * El mejor mundo en evoluci�n.
	 */
	private WorldChanged theBestWorld;
	
	/**
	 * Constructora para el evolucionador.
	 * @param evaluator Evaluador a usar por el evolucionador de mundos.
	 */
	public MindEvolver(IEvaluator evaluator) {
		maxMindExpansions = 3;
		mindsGenerated = new PriorityQueue<WorldChanged>();
		this.evaluator = evaluator;
	}
	
	@Override
	public WorldChanged evolveMind(World mind) {
		
		mindsGenerated = new PriorityQueue<WorldChanged>();
		theBestWorld = new WorldChanged(mind);
		mindsGenerated.add(theBestWorld);
		
		for (int i = 0; i < maxMindExpansions; i++) {
			
			// Obtiene la mente m�s favorable de la lista (por ahora la primera)
			WorldChanged operatedMind = mindsGenerated.poll(); // Saca la cima y la borra
			
			if (operatedMind.compareTo(theBestWorld) >= 0) 
				theBestWorld = operatedMind.copy();
			
			if (operatedMind == null) {
				int x = 3;
				x = x +4;
			}
			
			// Genera los hijos como resultado de operar esa mente
			ArrayList<WorldChanged> mindSons = operateMind(operatedMind);
			
			// Eval�a los hijos generados
			evalWorlds(mindSons);
			
			// Inserta los hijos en el total de mentes generadas y ordenados seg�n su valor
			insertWorlds(mindSons);
			
		}
		
		return theBestWorld; // la m�s favorable seg�n su valor
	}
	
	/**
	 * Aplica los operadores a un mundo para generar todos los posibles hijos.
	 * @param w Mundo que operar.
	 * @return Los hijos generados tras aplicar operadores.
	 */
	private ArrayList<WorldChanged> operateMind(WorldChanged w) {
		
		ArrayList<WorldChanged> sons = new ArrayList<WorldChanged>();
		
		Specialize specializeOp = new Specialize();
		specializeOp.generateWorlds(w, sons);
		
		Generalize generalizeOp = new Generalize();
		generalizeOp.generateWorlds(w, sons);
		
		return sons;
	}
	
	/**
	 * Eval�a los mundos generados asign�ndoles a cada uno un valor heur�stico.
	 * @param sons Mundos para evaluar.
	 * */
	private void evalWorlds(ArrayList<WorldChanged> sons) {
		for (WorldChanged w : sons) {
			w.setValue(evaluator.eval(w.getActualMind()));
		}
	}
	
	/**
	 * Inserta los mundos en la cola de prioridad de mundos generados.
	 * @param sons Mundos a insertar.
	 */
	private void insertWorlds(ArrayList<WorldChanged> sons) {
		//TODO: implementar la inserci�n de mundos en la cola de prioridad
		for (WorldChanged w: sons) {
			mindsGenerated.add(w);
		}
	}

}
