package operator;

import mind.Relation;

/**
 * Clase contenedora para la información de un cambio producido
 * por la aplicación de un operador.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Change {

	/**
	 * Componente afectado en el cambio antes del mismo.
	 */
	private Relation before;
	
	/**
	 * Componente resultando del cambio.
	 */
	private Relation after;
	
	/**
	 * Operador usado en el cambio.
	 */
	private int operator;
	
	/**
	 * Crea información de cambio al aplicar un operador.
	 * @param b Componente antes del cambio.
	 * @param a Componente después del cambio.
	 * @param op Operador usado para el cambio.
	 */
	public Change(Relation b, Relation a, int op) {
		setBefore(b);
		setAfter(a);
		setOperator(op);
	}

	/**
	 * Setter para el componente antes del cambio.
	 * @param before
	 */
	public void setBefore(Relation before) {
		this.before = before;
	}

	/**
	 * Getter para el componente antes del cambio.
	 * @return
	 */
	public Relation getBefore() {
		return before;
	}

	/**
	 * Setter para el componente después del cambio.
	 * @param after
	 */
	public void setAfter(Relation after) {
		this.after = after;
	}

	/**
	 * Getter para el componente después del cambio.
	 * @return
	 */
	public Relation getAfter() {
		return after;
	}

	/**
	 * Setter para el operador usado para el cambio.
	 * @param operator
	 */
	public void setOperator(int operator) {
		this.operator = operator;
	}

	/**
	 * Getter para el operador usado para el cambio.
	 * @return
	 */
	public int getOperator() {
		return operator;
	}
	
	/**
	 * Crea una copia del cambio.
	 * @return La copia.
	 */
	public Change copy() {
		return new Change(before.copy(), after.copy(), operator);
	}

	@Override
	public String toString() {
		return "Change [after=" + after.toString() + ", before=" + before.toString() + ", operator="
				+ operator + "]";
	}
	
}
