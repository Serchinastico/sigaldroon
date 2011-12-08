package segmenter.syntax;

/**
 * Patr�n Bridge para el uso de nombres (Personas u Objetos) de la ontolog�a.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class AdjectiveBridge extends CheckerBridge {

	/**
	 * Constantes para facilitar la modificaci�n de las propiedades de la ontolog�a.
	 */
	private static final String MALE = "masculino";
	private static final String FEMALE = "femenino";

	/**
	 * Instancia para aplicar el singleton.
	 */
	private static AdjectiveBridge aB;
	
	/**
	 * Constructora privada para aplicar el singleton.
	 */
	private AdjectiveBridge() {
		aB = null;
	}
	
	/**
	 * M�todo para obtener la instancia de la clase.
	 * @return Instancia de la clase.
	 */
	public static AdjectiveBridge getInstance() {
		if (aB == null) 
			aB = new AdjectiveBridge();
		return aB;
	}
	
	/**
	 * Obtiene el adjetivo en masculino.
	 * @param name Nombre.
	 * @return El adjetivo en masculino.
	 */
	public String getMale(String name) {
		return getString(name, MALE);
	}
	
	/**
	 * Obtiene el adjetivo en femenino.
	 * @param name Nombre.
	 * @return El adjetivo en femenino.
	 */
	public String getFemale(String name) {
		return getString(name, FEMALE);
	}
}
