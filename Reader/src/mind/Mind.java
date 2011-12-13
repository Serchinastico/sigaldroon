package mind;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import mind.ontobridge.OntoBridgeSingleton;

/**
 * Implementación de la mente del lector.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Mind implements Iterable<Relation>, Iterator<Relation> {

	/**
	 * Lista de relaciones.
	 * */
	private HashMap<String, HashSet<Relation>> relations;
	
	/**
	 * Iterador para el hashmap que itera sobre las acciones.
	 */
	private Iterator<Entry<String, HashSet<Relation>>> itActions;
	
	/**
	 * Iterador para los hashset que itera sobre las relaciones.
	 */
	private Iterator<Relation> itRelations;
	
	/**
	 * Constructora por defecto.
	 */
	public Mind() {
		relations = new HashMap<String, HashSet<Relation>>();
	}

	/**
	 * Construye la mente mediante la copia de otra.
	 * @param mCopy Mente referencia.
	 * */
	public Mind(Mind mCopy) {
		
		relations = new HashMap<String, HashSet<Relation>>();
		
		Iterator<Entry<String, HashSet<Relation>>> it = mCopy.relations.entrySet().iterator();
		
		while (it.hasNext()) {
			
			HashSet<Relation> auxRelations = new HashSet<Relation>();
			
			Entry<String, HashSet<Relation>> entryMCopy = it.next();
						
			for (Relation relation : entryMCopy.getValue()) {
				auxRelations.add(relation.clone());
			}
			
			relations.put(new String(entryMCopy.getKey()), auxRelations);
		}
	}
	
	/**
	 * Construye una mente mediante un flujo de entrada (fichero, entrada estándar, ...).
	 * @param in Flujo de entrada.
	 * */
	public Mind(InputStream in) {
		relations = new HashMap<String, HashSet<Relation>>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while ((line = br.readLine()) != null) {
				// Se pueden insertar comentarios iniciando la linea con "//"
				if (line.startsWith("//"))
					continue;
				Relation r = new Relation(line);
				add(r);
			}
		} catch (Exception e) {
			System.err.println("Error leyendo el estado de la mente: " + e.getMessage());
		}
	}
	
	/**
	 * Añade una relación a la mente.
	 * @param r Relación a añadir.
	 */
	public void add(Relation r) {
		
		if (relations.containsKey(r.getAction())) {
			relations.get(r.getAction()).add(r);
		}
		else {
			HashSet<Relation> newRelations = new HashSet<Relation>();
			newRelations.add(r);
			relations.put(new String(r.getAction()), newRelations);
		}
	}
	
	/**
	 * Elimina una relación de la mente.
	 * @param r Relación a eliminar.
	 */
	public void remove(Relation r) {
		
		if (relations.containsKey(r.getAction())) {
			HashSet<Relation> setRelations = relations.get(r.getAction());
			setRelations.remove(r);
			
			if (setRelations.isEmpty()) {
				relations.remove(r.getAction());
			}
		}
	}
	
	/**
	 * Obtiene un HashMap con las acciones (pasadas por argumento) y las relaciones asociadas.
	 * Otra forma de verlo es como un filtrado de la mente en el que obtenemos las relaciones
	 * de determinadas acciones.
	 * @param relationAction El conjunto de acciones cuyas relaciones van a tomarse.
	 * @return HashMap con el par accion y sus relaciones, tras el filtrado.
	 */
	public HashMap<String, Iterable<Relation>> getRelations(Collection<String> relationActions) {
		
		HashMap<String, Iterable<Relation>> filteredRelations = new HashMap<String, Iterable<Relation>>();
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		
		for (String relationAction : relationActions) {
			HashSet<Relation> relatedIterableAction = new HashSet<Relation>();
			if (ob.existsClass(relationAction)) {
				for (String keyAction : relations.keySet()) {
					if (ob.existsClass(keyAction)) {
						if (ob.isSubClassOf(keyAction, relationAction)) 
							relatedIterableAction.addAll(relations.get(keyAction));
					}
					else {
						if (ob.isInstanceOf(keyAction, relationAction))
							relatedIterableAction.addAll(relations.get(keyAction));
					}
				}
				
			}
			else { // es una instancia y se guardan sus relaciones si existen
				if (relations.containsKey(relationAction)) 
					relatedIterableAction = relations.get(relationAction);
			}
			
			if (relatedIterableAction.size() > 0) {
				filteredRelations.put(relationAction, relatedIterableAction);
			}
		}
		
		return filteredRelations;		
	}
	
	@Override
	public String toString() {
		return "World [relations=" + relations.toString() + "]";
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 * */
	@Override
	public int hashCode() {
		int hashRelations = 0;
		
		for (HashSet<Relation> relationSet : relations.values()) {
			for (Relation relation : relationSet) {
				hashRelations = (hashRelations + relation.toString().hashCode()) % Integer.MAX_VALUE;
			}
		}
		
		return hashRelations;
	}
	
	public String toStringSegment() {
		String retVal = "Segmento\n";
		Iterator<Entry<String, HashSet<Relation>>> it = relations.entrySet().iterator();
		
		while (it.hasNext()) {
		
			Entry<String, HashSet<Relation>> entry = it.next();
						
			for (Relation relation : entry.getValue()) {
				retVal += relation.toStringRelation();
			}
		}

		return retVal;
	}

	@Override
	public Iterator<Relation> iterator() {
		itActions = relations.entrySet().iterator();
		
		// Coloca los iteradores para comenzar
		if (itActions.hasNext()) {
			Entry<String, HashSet<Relation>> aux = itActions.next();
			itRelations = aux.getValue().iterator();
		}
		return this;
	}

	@Override
	public boolean hasNext() {

		// O bien hay una relación más en este grupo de acciones o bien hay más acciones con relaciones
		return ((itRelations != null) && itRelations.hasNext()) || (itActions != null && itActions.hasNext());
	}

	@Override
	public Relation next() {
		
		// Si no hay más relaciones en este grupo de acciones, pasamos al grupo de la siguiente acción
		if (!itRelations.hasNext()) {
			if (itActions.hasNext()) {
				Entry<String, HashSet<Relation>> aux = itActions.next();
				itRelations = aux.getValue().iterator();
			}
		}
		return itRelations.next();
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
