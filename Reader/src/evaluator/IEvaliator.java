package evaluator;

import world.World;

/**
 * Evaluador de mundos (World) para obtener el valor de una heurística a determinar.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface IEvaliator {

	/**
	 * Evalúa un mundo para la búsqueda informada que tiene en cuenta
	 * la heurística.
	 * @param w Mundo a evaluar.
	 * @return El valor calculado para el mundo.
	 */
	public float eval(World w);
	
}
