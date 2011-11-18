package operator;

import java.util.ArrayList;

import world.WorldChanged;

/**
 * Interfaz para operadores sobre mundos (Worlds).
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public interface IOperator {
	
	/**
	 * Genera todos los mundos posibles aplicando este operador a un mundo
	 * pasado por par�mentro.
	 * @param w El mundo al que se le va a aplicar el operador.
	 * @param changes Listado de mundos generados. Se a�aden al final de la lista si no es vac�a.
	 */
	public void generateWorlds(WorldChanged w, ArrayList<WorldChanged> generatedWorls);
}
