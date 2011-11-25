package operator;

import java.util.ArrayList;

import mind.ChangedMind;


/**
 * Interfaz para operadores sobre mundos (Worlds).
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public interface IOperator {
		
	/**
	 * Genera todas las mentes posibles aplicando este operador a una mente
	 * pasado por parámentro.
	 * @param m La mente a la que se le va a aplicar el operador.
	 * @param changes Listado de mentes generadas. Se añaden al final de la lista si no es vacía.
	 */
	public void generateMinds(ChangedMind m, ArrayList<ChangedMind> generatedMinds);
	
}
