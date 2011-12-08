package segmenter.syntax;

/**
 * Patrón Bridge para el uso de nombres (Personas u Objetos) de la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class NameBridge extends CheckerBridge {
	
	/**
	 * Constantes para facilitar la modificación de las propiedades de la ontología.
	 */
	private static final String TEXT_NAME = "textoNombre";
	private static final String GENDER = "genero";
	private static final String MALE = "masculino";
	private static final String FEMALE = "femenino";

	/**
	 * Instancia para aplicar el singleton.
	 */
	private static NameBridge nB;
	
	/**
	 * Constructora privada para aplicar el singleton.
	 */
	private NameBridge() {
		nB = null;
	}
	
	/**
	 * Método para obtener la instancia de la clase.
	 * @return Instancia de la clase.
	 */
	public static NameBridge getInstance() {
		if (nB == null) 
			nB = new NameBridge();
		return nB;
	}
	
	/**
	 * Obtiene la forma bien escrita para un usuario del nombre.
	 * @param name Nombre.
	 * @return El String con la forma en pasado del verbo.
	 */
	public String getName(String name) {
		return getString(name, TEXT_NAME);
	}
	
	/**
	 * Comprueba si un nombre tiene género masculino.
	 * @param name Nombre.
	 * @return True si el género es masculino.
	 */
	public boolean isMale(String name) {
		return booleanCheck(name, GENDER, MALE);
	}
	
	/**
	 * Comprueba si un nombre tiene género femenino.
	 * @param name Nombre.
	 * @return True si el género es femenino.
	 */
	public boolean isFemale(String name) {
		return booleanCheck(name, GENDER, FEMALE);
	}
}
