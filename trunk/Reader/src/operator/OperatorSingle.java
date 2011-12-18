package operator;

import java.util.ArrayList;
import java.util.Iterator;

import mind.ChangedMind;
import mind.Relation;

/**
 * Clase abstracta para los operadores que les es suficiente para act�ar
 * con una sola relaci�n de la mente.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public abstract class OperatorSingle implements Operator {

	/**
	 * Peso del operador.
	 */
	protected float opWeight = 0.6f;
	
	protected int opId;
	
	@Override
	public void generateMinds(ChangedMind m, ArrayList<ChangedMind> generatedMinds) {
		
		Iterator<Relation> itRel = m.getActualMind().iterator();
		
		while (itRel.hasNext())
			generateByRelation(m, itRel.next(), generatedMinds);
	}
	
	/**
	 * Genera todos los hijos posibles al aplicar el operador a una relaci�n.
	 * @param m Mente a operar.
	 * @param r Relaci�n a operar.
	 * @param gM Listado actual de mentes generadas.
	 */
	protected abstract void generateByRelation(ChangedMind m, Relation r, ArrayList<ChangedMind> gM);
	
	/**
	 * Genera un descendiente a partir de una relaci�n r, de una mente cambiada m.
	 * Cambia el elemento seleccionado en relTarget poni�ndolo a newElement.
	 * @param m Mente sobre la que aplicar el cambio.
	 * @param r Relaci�n sobre la que aplicar el cambio.
	 * @param relTarget Elemento de la relaci�n sobre la que aplicar el cambio.
	 * @param newElement Nuevo valor para el elemento a modificar de la relaci�n.
	 * @return El descendiente creado.
	 */
	protected ChangedMind generateChild(ChangedMind m, Relation r, int relTarget, String newElement) {
		ChangedMind newMind = m.clone();
		// Cambio de la relaci�n
		Relation newRelation = r.clone();
		newRelation.setElement(relTarget, newElement);
		// Cambio del peso
		newRelation.setWeight(r.getWeight() * opWeight);
		// Guardado del cambio
		newMind.insertChange(r, newRelation, opId);
		return newMind;
	}
}
