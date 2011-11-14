package operator;

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
	 * @return Los mundos resultantes de aplicar el operador a todos sus elementos.
	 */
	public World[] generateWorlds(World w);
}
