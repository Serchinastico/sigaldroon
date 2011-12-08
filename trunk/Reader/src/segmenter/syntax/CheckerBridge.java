package segmenter.syntax;

import java.util.ArrayList;

import mind.ontobridge.OntoBridgeSingleton;
import es.ucm.fdi.gaia.ontobridge.OntoBridge;

/**
 * Patr�n Bridge para el uso de nombres (Personas u Objetos) de la ontolog�a.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class CheckerBridge {

	/**
	 * Constantes para facilitar la modificaci�n de las propiedades de la ontolog�a.
	 */
	protected static final String PROP_FILTER = "\\^\\^";

	/**
	 * Obtiene el valor de una propiedad de una instancia.
	 * @param instance Instancia a inspeccionar.
	 * @param property Propiedad cuyo valor obtener.
	 * @return El string con el texto del valor de la propiedad.
	 */
	protected String getString(String instance, String property) {

		// Inicializa variables para el acceso a la ontolog�a
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		ArrayList<String> properties = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();

		// Obtiene propiedades y valores de la instancia action
		ob.listInstancePropertiesValues(instance, properties, values);

		// Busca el valor de la propiedad pedida
		for (int i = 0; i < properties.size(); i++)
			if (properties.get(i).contains(property))
				return values.get(i).split(PROP_FILTER)[0];				

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

		// Inicializa variables para el acceso a la ontolog�a
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		ArrayList<String> properties = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();

		// Obtiene propiedades y valores de la instancia action
		ob.listInstancePropertiesValues(instance, properties, values);

		// Busca el valor de la propiedad pedida
		for (int i = 0; i < properties.size(); i++)
			if (properties.get(i).contains(property))
				return values.get(i).split(PROP_FILTER)[0].equals(value);				

		return false;
	}
	
}
