package operator;

import java.util.ArrayList;
import java.util.Iterator;

import mind.ChangedMind;
import mind.Relation;

/**
 * Clase abstracta para los operadores que les es suficiente para actúar
 * con una sola relación de la mente.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public abstract class OperatorSingle implements IOperator {

	@Override
	public void generateMinds(ChangedMind m, ArrayList<ChangedMind> generatedMinds) {
		
		Iterator<Relation> itRel = m.getActualMind().iterator();
		
		while (itRel.hasNext())
			apply(m, itRel.next(), generatedMinds);
	}
	
	/**
	 * Genera todos los hijos posibles al aplicar el operador a una relación.
	 * @param m Mente a operar.
	 * @param r Relación a operar.
	 * @param gM Listado actual de mentes generadas.
	 */
	protected abstract void apply(ChangedMind m, Relation r, ArrayList<ChangedMind> gM);
	
	/**
	 * Aplica el cambio, poniendo el nuevo nombre al objetivo.
	 * @param r Relación a cambiar.
	 * @param newName Nuevo nombre para el elemento a cambiar.
	 * @param opTarget El elemento a cambiar en la relación.
	 */
	protected void applyChange(Relation r, String newName, int opTarget) {
		
		switch(opTarget) {
		case OPTarget.ACTION:
			r.setAction(newName);
			break;
		case OPTarget.SOURCE:
			r.setSource(newName);
			break;
		case OPTarget.TARGET:
			r.setTarget(newName);
			break;
		case OPTarget.PLACE:
			r.setPlace(newName);
			break;
		case OPTarget.OD:
			r.setDirectObject(newName);
			break;
		}
		
	}
}
