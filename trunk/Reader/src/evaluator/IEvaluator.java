package evaluator;

import mind.Mind;
import mind.RelationSet;

/**
 * Evaluador de mentes (Mind) para obtener el valor de una heur�stica a determinar.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public interface IEvaluator {

	/**
	 * Eval�a una mente para la b�squeda informada que tiene en cuenta
	 * la heur�stica.
	 * @param m Mente a evaluar.
	 * @return El valor calculado para la mente.
	 */
	public float eval(Mind m, RelationSet rSet);
	
}
