package evaluator;

import java.util.ArrayList;

import mind.Mind;
import mind.RelationSet;

/**
 * Evaluador de mentes (Mind) para obtener el valor de una heurística a determinar.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface IEvaluator {

	/**
	 * Evalúa una mente para la búsqueda informada que tiene en cuenta
	 * la heurística.
	 * @param m Mente a evaluar.
	 * @return El valor calculado para la mente.
	 */
	public float eval(Mind m, RelationSet rSet);
	
	/**
	 * Devuelve la lista de patrones usados en el mundo pasado como parámetro.
	 * @param m
	 * @param maxSegments
	 * @param mindSegment
	 * @return
	 */
	public ArrayList<QuestionPattern> getUsedPatterns(Mind m, int maxSegments, int mindSegment);
	
}
