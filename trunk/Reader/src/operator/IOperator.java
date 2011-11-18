package operator;

import java.util.ArrayList;

import world.WorldChanged;

/**
 * Interfaz para operadores sobre mundos (Worlds).
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface IOperator {
	
	/**
	 * Genera todos los mundos posibles aplicando este operador a un mundo
	 * pasado por parámentro.
	 * @param w El mundo al que se le va a aplicar el operador.
	 * @param changes Listado de mundos generados. Se añaden al final de la lista si no es vacía.
	 */
	public void generateWorlds(WorldChanged w, ArrayList<WorldChanged> generatedWorls);
}
