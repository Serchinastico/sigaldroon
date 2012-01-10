package mind;

import java.util.ArrayList;
import java.util.Comparator;

import operator.Change;


/**
 * Esta clase almacena la mente del lector y los cambios que se han producido para
 * llegar a él, asi como el valor de la mente para una heurística.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class ChangedMind implements Comparable<Object> {

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
	
	@Override
	public ChangedMind clone() {
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
	
	/**
	 * Obtiene las relaciones que se han obtenido como resultado de aplicar los cambios.
	 * @return Relaciones resultantes de los cambios efectuados.
	 */
	public ArrayList<Relation> getResultingRelations() {
		ArrayList<Relation> filteredChanges = new ArrayList<Relation>();
		// Se mira en cada cambio y por cada relación resultante de un cambio
		// se comprueba que no se ha usado en en la realización de un cambio posterior
		for (int i = 0; i < changes.size(); i++) {

			/*
			// Solo es necesario mirar en los cambios que hay adelante
			// y ver si la relación resultante es usada en otro cambio
			int j = i + 1;
			boolean changeToInclude = true;
			while ((changeToInclude) && (j < changes.size())) {
				changeToInclude = !changes.get(i).getAfter().equals(changes.get(j).getBefore());
				j++;
			}
			if ((changeToInclude) && (!OntoBridgeSingleton.getInstance().existsClass(changes.get(i).getAfter().getAction())))
				filteredChanges.add(changes.get(i).getAfter());
				*/
			if (actualMind.contains(changes.get(i).getAfter())
					&& !filteredChanges.contains(changes.get(i).getAfter())) 
				filteredChanges.add(changes.get(i).getAfter());
		}
		
		return filteredChanges;
	}
	
	/*
	public static void main(String[] args) {
		CoherenceChecker cc = new CoherenceChecker();
		Events events = null;
		Mind mind = new Mind();
		Relation r1 = new Relation(1,"Heracles","Matar",null,null,"Hades",null);
		Relation r2 = new Relation(1,"Hades","Amar",null,null,"Afrodita");
		Relation r3 = new Relation(1,"Heracles","Amar","Afrodita",null,"Megara",null);
		
		mind.add(r1);
		events = cc.assumeEvents(events, mind);
		
		ChangedMind cM = new ChangedMind(mind);
		//cM.insertChange(r1, r2, 1);
		cM.insertChange(r1, r3, 0);
		
		if (cc.checkCoherence(events, cM)) {
			System.out.print("Correcto");
		}
		else 
			System.out.print("Incorrecto.");
		
	}*/
	
	/**
	 * Aplica el cambio realizado por el operador a la mente.
	 * @param op Índice del operador usado según las constantes de operadores.
	 * @param before Relación antes del cambio.
	 * @param after Relación tras el cambio.
	 */
	public void insertChange(Relation before, Relation after, int op) {
		actualMind.remove(before); // Quitamos la antigua relación
		actualMind.add(after); // Ponemos la modificada
		changes.add(new Change(before.clone(),after.clone(),op));
	}
	
	/**
	 * Devuelve un comparador para ordenar ChangedMind de mayor a menor.
	 * @return
	 */
	public Comparator<ChangedMind> getGreaterComparator() {
		Comparator<ChangedMind> gt = new Comparator<ChangedMind>() {
			@Override
			public int compare(ChangedMind o1, ChangedMind o2) {
				return o1.compareTo(o2);
			}
		};
		return gt;
	}
	
	/**
	 * Devuelve un comparador para ordenar ChangedMind de menor a mayor.
	 * @return
	 */
	public Comparator<ChangedMind> getLowerComparator() {
		Comparator<ChangedMind> lt = new Comparator<ChangedMind>() {
			@Override
			public int compare(ChangedMind o1, ChangedMind o2) {
				return -o1.compareTo(o2);
			}
		};
		return lt;
	}
	
}
