package world;

import java.util.ArrayList;

import operator.Change;


/**
 * Esta clase almacena un mundo de conceptos y los cambios que se han producido para
 * llegar a �l, asi como el valor del mundo para una heur�stica.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class WorldChanged {

	/**
	 * Cambios efectuados en orden para obtener la mente actual.
	 */
	private ArrayList<Change> changes;
	
	/**
	 * Mente actual tras los cambios.
	 */
	private World actualMind;
	
	/**
	 * Valor dado por una heur�stica.
	 */
	private double value;
	
	/**
	 * Constructora por defecto.
	 */
	public WorldChanged() {
	}
	
	/**
	 * Constructora partiendo solo de una mente sin cambios.
	 * @param m Mente actual.
	 */
	public WorldChanged(World m) {
		actualMind = m;
		changes = new ArrayList<Change>();
		value = 0.0;
	}
	
	/**
	 * Copia la mente actual, el valor de heur�stica y los cambios.
	 * @return
	 */
	public WorldChanged copy() {
		WorldChanged retVal = new WorldChanged();
		retVal.actualMind = new World(actualMind);
		retVal.changes = new ArrayList<Change>();
		retVal.value = value;
		for (Change change : changes) {
			retVal.changes.add(change.copy());
		}
		return retVal;
	}

	/**
	 * Getter para los cambios.
	 * @return
	 */
	public ArrayList<Change> getChanges() {
		return changes;
	}

	/**
	 * Getters para el mundo actual.
	 * @return
	 */
	public World getActualMind() {
		return actualMind;
	}

	/**
	 * Getter para el valor de la heur�stica.
	 * @return
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Setter para el valor de la heur�stica.
	 * @param value
	 */
	public void setValue(double value) {
		this.value = value;
	}
	
	
}
