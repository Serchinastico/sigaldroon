package evaluator;

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
	
}
