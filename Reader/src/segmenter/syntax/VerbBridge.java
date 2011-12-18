package segmenter.syntax;

/**
 * Patr�n Bridge para el uso de verbos de la ontolog�a.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class VerbBridge extends CheckerBridge {
	
	/**
	 * Constantes para facilitar la modificaci�n de las propiedades de la ontolog�a.
	 */
	private static final String PAST_FORM = "PasadoSingular";
	private static final String CAN_HAVE_DIRECT_OBJECT = "TieneObjetoDirecto";
	private static final String CAN_HAVE_INDIRECT_OBJECT = "TieneObjetoIndirecto";
	private static final String DIRECT_OBJECT_TYPE = "TipoObjetoDirecto";
	private static final String INDIRECT_OBJECT_TYPE = "TipoObjetoIndirecto";
	private static final String DIRECT_OBJECT_PREPOSITION = "PreposicionObjetoDirecto";
	private static final String INDIRECT_OBJECT_PREPOSITION = "PreposicionObjetoIndirecto";
	private static final String TRUE = "true";
	
	/**
	 * Instancia para aplicar el singleton.
	 */
	private static VerbBridge vB;
	
	/**
	 * Constructora privada para aplicar el singleton.
	 */
	private VerbBridge() {
		vB = null;
	}
	
	/**
	 * M�todo para obtener la instancia de la clase.
	 * @return Instancia de la clase.
	 */
	public static VerbBridge getInstance() {
		if (vB == null) 
			vB = new VerbBridge();
		return vB;
	}
	
	/**
	 * Obtiene la forma en pasado de un verbo.
	 * @param action Acci�n que representa al verbo.
	 * @return El String con la forma en pasado del verbo.
	 */
	public String getPastForm(String action) {
		return getString(action, PAST_FORM);
	}
	
	/**
	 * Comprueba si un verbo puede tener objeto directo.
	 * @param action Acci�n que representa al verbo.
	 * @return True si el verbo puede tener objeto directo.
	 */
	public boolean canHaveDirectObject(String action) {
		return booleanCheck(action, CAN_HAVE_DIRECT_OBJECT, TRUE);
	}
	
	/**
	 * Comprueba si un verbo puede tener objeto indirecto.
	 * @param action Acci�n que representa al verbo.
	 * @return True si el verbo puede tener objeto indirecto.
	 */
	public boolean canHaveIndirectObject(String action) {
		return booleanCheck(action, CAN_HAVE_INDIRECT_OBJECT, TRUE);
	}
	
	/**
	 * Obtiene el tipo del objeto directo (persona u objeto).
	 * @param action Acci�n que representa al verbo.
	 * @return El String con el tipo del objeto directo.
	 */
	public String getDirectObjectType(String action) {
		return getString(action, DIRECT_OBJECT_TYPE);
	}
	
	/**
	 * Obtiene el tipo del objeto indirecto (persona u objeto).
	 * @param action Acci�n que representa al verbo.
	 * @return El String con el tipo del objeto indirecto.
	 */
	public String getIndirectObjectType(String action) {
		return getString(action, INDIRECT_OBJECT_TYPE);
	}
	
	/**
	 * Obtiene la preposici�n que acompa�a al objeto directo.
	 * @param action Acci�n que representa al verbo.
	 * @return El String con la preposici�n que acompa�a al objeto directo.
	 */
	public String getDirectObjectPreposition(String action) {
		return getString(action, DIRECT_OBJECT_PREPOSITION);
	}
	
	/**
	 * Obtiene la preposici�n que acompa�a al objeto indirecto.
	 * @param action Acci�n que representa al verbo.
	 * @return El String con la preposici�n que acompa�a al objeto indirecto.
	 */
	public String getIndirectObjectPreposition(String action) {
		return getString(action, INDIRECT_OBJECT_PREPOSITION);
	}
	
}
