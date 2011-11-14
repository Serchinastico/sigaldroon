package evaluator;

import world.World;

/**
 * Evaluador de mundos (World) para obtener el valor de una heur�stica a determinar.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public interface IEvaliator {

	/**
	 * Eval�a un mundo para la b�squeda informada que tiene en cuenta
	 * la heur�stica.
	 * @param w Mundo a evaluar.
	 * @return El valor calculado para el mundo.
	 */
	public float eval(World w);
	
}
