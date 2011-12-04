package coherence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

/**
 * Contiene información de los eventos sucedidos en una historia
 * relevantes para tener coherencia.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Events {

	/**
	 * Actores / elementos muertos en la historia.
	 */
	private HashSet<String> deaths;

	/**
	 * Actores / elementos casados en la historia.
	 */
	private HashMap<String, String> married;

	/**
	 * Constructora por defecto.
	 */
	public Events() {
		deaths = new HashSet<String>();
		married = new HashMap<String, String>();
	}

	/**
	 * Añade la muerte de un elemento sin chequeos.
	 * @param deathElement Elemento muerto.
	 */
	public void insertDeath(String deathElement) {
		// Elimina su matrimonio si existe con el de su cónyuge
		if (married.containsKey(deathElement)) {
			String couple = married.get(deathElement);
			married.remove(deathElement);
			married.remove(couple);
		}
		deaths.add(deathElement);
	}

	/**
	 * Añade el matrimonio de dos elementos sin chequeos.
	 * @param c1 Pareja del matrimonio.
	 * @param c2 Pareja del matrimonio.
	 */
	public void insertMarriage(String c1, String c2) {
		married.put(c1, c2);
		married.put(c2, c1);
	}
	
	/**
	 * Comprueba si un elemento está muerto.
	 * @param element Elemento a comprobar.
	 * @return True si está muerto.
	 */
	public boolean isDeath(String element) {
		return deaths.contains(element);
	}
	
	/**
	 * Comprueba si un elemento está casado.
	 * @param element Elemento a comprobar.
	 * @return True si está casado.
	 */
	public boolean isMarried(String element) {
		return married.containsKey(element);
	}
	
	/**
	 * Construye una copia de este objeto.
	 * @return La copia del objeto Events.
	 */
	public Events copy() {
		Events copy = new Events();
		for (String deathElement : deaths) {
			copy.insertDeath(deathElement);
		}
		for (Entry<String, String> marriage : married.entrySet()) {
			copy.insertMarriage(marriage.getKey(), marriage.getValue());
		}
		return copy;
	}

}
