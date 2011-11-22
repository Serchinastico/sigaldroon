package mind;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementaci�n de la mente del lector.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class Mind implements Iterable<Relation> {

	/**
	 * Lista de relaciones.
	 * */
	private List<Relation> relations;
	
	public Mind() {
		relations = new ArrayList<Relation>();
	}

	/**
	 * Construye la mente mediante la copia de otra.
	 * @param wCopy Mente referencia.
	 * */
	public Mind(Mind mCopy) {
		relations = new ArrayList<Relation>();
		
		for (Relation relation : mCopy.relations) {
			relations.add(relation.copy());
		}
	}
	
	/**
	 * Construye una mente mediante un flujo de entrada (fichero, entrada est�ndar, ...).
	 * @param in Flujo de entrada.
	 * */
	public Mind(InputStream in) {
		relations = new ArrayList<Relation>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while ((line = br.readLine()) != null) {
				// Se pueden insertar comentarios iniciando la linea con "//"
				if (line.startsWith("//"))
					continue;
				Relation c = new Relation(line);
				relations.add(c);
			}
		} catch (Exception e) {
			System.err.println("Error leyendo el estado de la mente: " + e.getMessage());
		}
	}

	@Override
	public Iterator<Relation> iterator() {
		return relations.iterator();
	}
	
	/**
	 * @return El n�mero de relaciones que hay en el mundo.
	 */
	public int getNumRelations() {
		return relations.size();
	}
	
	/**
	 * Obtiene la i-�sima relaci�n del listado de relaciones.
	 * @param i La i-�sima relaci�n dentro del listado de relaciones.
	 * @return La relaci�n n�mero i.
	 */
	public Relation getRelation(int i) {
		return relations.get(i);
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
		for (Relation r : relations) {
			shortStr += r.toShortString();
		}
		return shortStr;
	}
	
	public String toStringSegment() {
		String retVal = "Segmento\n";
		for (int i = 0; i < relations.size(); i++) {
			retVal += relations.get(i).toStringRelation() + "\n";
		}
		return retVal;
	}
}
