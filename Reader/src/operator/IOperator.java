package operator;

import java.util.ArrayList;

import mind.ChangedMind;


/**
 * Interfaz para operadores sobre mundos (Worlds).
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public interface IOperator {
		
	/**
	 * Genera todas las mentes posibles aplicando este operador a una mente
	 * pasado por par�mentro.
	 * @param m La mente a la que se le va a aplicar el operador.
	 * @param changes Listado de mentes generadas. Se a�aden al final de la lista si no es vac�a.
	 */
	public void generateMinds(ChangedMind m, ArrayList<ChangedMind> generatedMinds);
	
}
