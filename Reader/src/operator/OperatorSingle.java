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

	/**
	 * Peso del operador.
	 */
	protected float opWeight = 0.6f;
	
	@Override
	public void generateMinds(ChangedMind m, ArrayList<ChangedMind> generatedMinds) {
		
		Iterator<Relation> itRel = m.getActualMind().iterator();
		
		while (itRel.hasNext())
			generateByRelation(m, itRel.next(), generatedMinds);
	}
	
	/**
	 * Genera todos los hijos posibles al aplicar el operador a una relación.
	 * @param m Mente a operar.
	 * @param r Relación a operar.
	 * @param gM Listado actual de mentes generadas.
	 */
	protected abstract void generateByRelation(ChangedMind m, Relation r, ArrayList<ChangedMind> gM);

	/**
	 * Aplica el cambio realizado por el operador a la mente.
	 * @param op Índice del operador usado según las constantes de IOperator.
	 * @param m Mente a la que aplicar el cambio.
	 * @param before Relación antes del cambio.
	 * @param after Relación tras el cambio.
	 */
	protected void applySingleChange(int op, ChangedMind m, Relation before, Relation after) {
		m.getActualMind().remove(before); // Quitamos la antigua relación
		m.getActualMind().add(after); // Ponemos la modificada
		m.getChanges().add(new Change(before.clone(),after.clone(),op));
	}
}
