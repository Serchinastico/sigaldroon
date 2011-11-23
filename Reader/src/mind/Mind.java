package mind;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Implementaci�n de la mente del lector.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class Mind {

	/**
	 * Lista de relaciones.
	 * */
	private HashMap<String, HashSet<Relation>> relations;
	
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
				auxRelations.add(relation.copy());
			}
			
			relations.put(new String(entryMCopy.getKey()), auxRelations);
		}
	}
	
	/**
	 * Construye una mente mediante un flujo de entrada (fichero, entrada est�ndar, ...).
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
	 * A�ade una relaci�n a la mente.
	 * @param r Relaci�n a a�adir.
	 */
	public void add(Relation r) {
		
		if (relations.containsKey(r.getAction().getName())) {
			relations.get(r.getAction().getName()).add(r);
		}
		else {
			HashSet<Relation> newRelations = new HashSet<Relation>();
			newRelations.add(r);
			relations.put(new String(r.getAction().getName()), newRelations);
		}
	}
	
	/**
	 * Elimina una relaci�n de la mente.
	 * @param r Relaci�n a eliminar.
	 */
	public void remove(Relation r) {
		
		if (relations.containsKey(r.getAction().getName())) {
			HashSet<Relation> setRelations = relations.get(r.getAction().getName());
			setRelations.remove(r);
			
			if (setRelations.isEmpty()) {
				relations.remove(r.getAction().getName());
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
	public HashMap<String, Iterable<Relation>> getRelations(Collection<String> relationAction) {
		
		HashMap<String, Iterable<Relation>> retVal = new HashMap<String, Iterable<Relation>>();
		
		Iterator<String> itAction = relationAction.iterator();
		
		while (itAction.hasNext()) {
			String action = itAction.next();
			retVal.put(new String(action), relations.get(action));
		}
		
		return retVal;		
	}
	
	@Override
	public String toString() {
		return "World [relations=" + relations.toString() + "]";
	}
	
	/**
	 * Devuelve un string reducido pensado para usarse en un hasheo posterior.
	 * @return String reducido que identifica la instancia.
	 * */
	public String toShortString() {
		String shortStr = "";
		
		Iterator<Entry<String, HashSet<Relation>>> it = relations.entrySet().iterator();
		
		while (it.hasNext()) {
		
			Entry<String, HashSet<Relation>> entry = it.next();
						
			for (Relation relation : entry.getValue()) {
				shortStr += relation.toShortString();
			}
		}
		
		return shortStr;
	}
	
	public String toStringSegment() {
		String retVal = "Segmento\n";
		Iterator<Entry<String, HashSet<Relation>>> it = relations.entrySet().iterator();
		
		while (it.hasNext()) {
		
			Entry<String, HashSet<Relation>> entry = it.next();
						
			for (Relation relation : entry.getValue()) {
				retVal += relation.toShortString();
			}
		}

		return retVal;
	}
}