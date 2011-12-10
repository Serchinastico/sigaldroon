package segmenter.syntax;

import java.util.Iterator;

import mind.ontobridge.OntoBridgeSingleton;
import es.ucm.fdi.gaia.ontobridge.OntoBridge;

/**
 * Patrón Bridge para el uso de nombres (Personas u Objetos) de la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class CheckerBridge {

	/**
	 * Constantes para facilitar la modificación de las propiedades de la ontología.
	 */
	protected static final String PROP_FILTER = "\\^\\^";

	/**
	 * Obtiene el valor de una propiedad de una instancia.
	 * @param instance Instancia a inspeccionar.
	 * @param property Propiedad cuyo valor obtener.
	 * @return El string con el texto del valor de la propiedad.
	 */
	protected String getString(String instance, String property) {

		// Inicializa variables para el acceso a la ontología
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		String elementToCheck = "";
		
		// Comprobación para obtener instancia de clase o instancia pura
		if (ob.existsClass(instance))
			elementToCheck = "Class" + instance;
		else
			elementToCheck = instance;

		// Obtiene valores de la propiedad
		Iterator<String> it = ob.listPropertyValue(elementToCheck, property);
	
		// Devuelve el valor encontrado en la ontología
		if (it.hasNext())
			return it.next().split(PROP_FILTER)[0];
		else 
			return null;

	}

	/**
	 * Comprueba si el valor de una propiedad es igual al pasado por argumento.
	 * @param instance Instancia a inspeccionar.
	 * @param property Propiedad cuyo valor comprobar.
	 * @param value Valor a comprobar que tiene la propiedad.
	 * @return True si el valor de la propiedad es igual al pedido.
	 */
	protected boolean booleanCheck(String instance, String property, String value) {

		// Inicializa variables para el acceso a la ontología
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		String elementToCheck = "";

		// Comprobación para obtener instancia de clase o instancia pura
		if (ob.existsClass(instance))
			elementToCheck = "Class" + instance;
		else
			elementToCheck = instance;
		
		// Obtiene propiedades y valores de la instancia action
		Iterator<String> it = ob.listPropertyValue(elementToCheck, property);
		
		// Chequeo y devolución
		if (it.hasNext()) 
			return it.next().split(PROP_FILTER)[0].equals(value);
		else 
			return false;
	}
	
}
