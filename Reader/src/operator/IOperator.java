package operator;

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
	 * @return Los mundos resultantes de aplicar el operador a todos sus elementos.
	 */
	public World[] generateWorlds(World w);
}
