package segmenter.syntax;

/**
 * Patrón Bridge para el uso de verbos de la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class VerbBridge extends CheckerBridge {
	
	/**
	 * Constantes para facilitar la modificación de las propiedades de la ontología.
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
	 * Método para obtener la instancia de la clase.
	 * @return Instancia de la clase.
	 */
	public static VerbBridge getInstance() {
		if (vB == null) 
			vB = new VerbBridge();
		return vB;
	}
	
	/**
	 * Obtiene la forma en pasado de un verbo.
	 * @param action Acción que representa al verbo.
	 * @return El String con la forma en pasado del verbo.
	 */
	public String getPastForm(String action) {
		return getString(action, PAST_FORM);
	}
	
	/**
	 * Comprueba si un verbo puede tener objeto directo.
	 * @param action Acción que representa al verbo.
	 * @return True si el verbo puede tener objeto directo.
	 */
	public boolean canHaveDirectObject(String action) {
		return booleanCheck(action, CAN_HAVE_DIRECT_OBJECT, TRUE);
	}
	
	/**
	 * Comprueba si un verbo puede tener objeto indirecto.
	 * @param action Acción que representa al verbo.
	 * @return True si el verbo puede tener objeto indirecto.
	 */
	public boolean canHaveIndirectObject(String action) {
		return booleanCheck(action, CAN_HAVE_INDIRECT_OBJECT, TRUE);
	}
	
	/**
	 * Obtiene el tipo del objeto directo (persona u objeto).
	 * @param action Acción que representa al verbo.
	 * @return El String con el tipo del objeto directo.
	 */
	public String getDirectObjectType(String action) {
		return getString(action, DIRECT_OBJECT_TYPE);
	}
	
	/**
	 * Obtiene el tipo del objeto indirecto (persona u objeto).
	 * @param action Acción que representa al verbo.
	 * @return El String con el tipo del objeto indirecto.
	 */
	public String getIndirectObjectType(String action) {
		return getString(action, INDIRECT_OBJECT_TYPE);
	}
	
	/**
	 * Obtiene la preposición que acompaña al objeto directo.
	 * @param action Acción que representa al verbo.
	 * @return El String con la preposición que acompaña al objeto directo.
	 */
	public String getDirectObjectPreposition(String action) {
		return getString(action, DIRECT_OBJECT_PREPOSITION);
	}
	
	/**
	 * Obtiene la preposición que acompaña al objeto indirecto.
	 * @param action Acción que representa al verbo.
	 * @return El String con la preposición que acompaña al objeto indirecto.
	 */
	public String getIndirectObjectPreposition(String action) {
		return getString(action, INDIRECT_OBJECT_PREPOSITION);
	}
	
}
