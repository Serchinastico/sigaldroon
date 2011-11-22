package mind;

import java.util.ArrayList;

import operator.Change;


/**
 * Esta clase almacena la mente del lector y los cambios que se han producido para
 * llegar a él, asi como el valor de la mente para una heurística.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class ChangedMind implements Comparable {

	/**
	 * Cambios efectuados en orden para obtener la mente actual.
	 */
	private ArrayList<Change> changes;
	
	/**
	 * Mente actual tras los cambios.
	 */
	private Mind actualMind;
	
	/**
	 * Valor dado por una heurística.
	 */
	private double value;
	
	/**
	 * Constructora por defecto.
	 */
	public ChangedMind() {
	}
	
	/**
	 * Constructora partiendo solo de una mente sin cambios.
	 * @param m Mente actual.
	 */
	public ChangedMind(Mind m) {
		actualMind = m;
		changes = new ArrayList<Change>();
		value = 0.0;
	}
	
	/**
	 * Copia la mente actual, el valor de heurística y los cambios.
	 * @return
	 */
	public ChangedMind copy() {
		ChangedMind retVal = new ChangedMind();
		retVal.actualMind = new Mind(actualMind);
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
	public Mind getActualMind() {
		return actualMind;
	}

	/**
	 * Getter para el valor de la heurística.
	 * @return
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Setter para el valor de la heurística.
	 * @param value
	 */
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "ChangedMind [actualMind=" + actualMind.toString() + ", changes="
				+ changes.toString() + ", value=" + value + "]";
	}

	@Override
	public int compareTo(Object arg0) {
		
		if(!(arg0 instanceof ChangedMind)){
            throw new ClassCastException("Invalid object");
        }
		
		ChangedMind w = (ChangedMind) arg0;
		
		if (this.value > w.value) return 1;
		else if (this.value < w.value) return -1;
		else return 0;
	}
	
}
