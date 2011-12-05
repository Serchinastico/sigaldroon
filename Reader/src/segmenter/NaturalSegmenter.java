package segmenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import mind.ChangedMind;
import mind.Mind;
import mind.Relation;
import mind.ontobridge.OntoBridgeSingleton;

import operator.Change;

/**
 * Implementación de un segmentador con generación de texto
 * para los cambios realizados en la generación de este segmento.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class NaturalSegmenter implements ISegmenter {

	@Override
	public String generateSegment(ChangedMind m) {
		String segmentText = "";
		ArrayList<Relation> filteredRelations = filterChanges(m.getChanges());
		segmentText = makeText(filteredRelations);
		return segmentText;
	}
	
	/**
	 * Para cada cambio, solo se incluye en la lista resultado si
	 * la relación resultante del cambio no se ha usado en un cambio posterior
	 * con algún operador.
	 * @param changes Cambios realizados.
	 * @return Cambios filtrados.
	 */
	private ArrayList<Relation> filterChanges(ArrayList<Change> changes) {
		ArrayList<Relation> filteredChanges = new ArrayList<Relation>();
		for (int i = 0; i < changes.size(); i++) {
			// Solo es necesario mirar en los cambios que hay adelante
			// y ver si la relación resultante es usada en otro cambio de forma inicial
			int j = i + 1;
			boolean changeToInclude = true;
			while ((changeToInclude) && (j < changes.size())) {
				changeToInclude = !changes.get(i).getAfter().equals(changes.get(j).getBefore());
			}
			if (changeToInclude) 
				filteredChanges.add(changes.get(i).getAfter());
		}
		return filteredChanges;
	}

	@Override
	public String generateInitialSegment(Mind m) {
		String segmentText = "";
		ArrayList<Relation> mindRelations = new ArrayList<Relation>();
		for (Relation relation : m)
			mindRelations.add(relation);
		segmentText += makeText(mindRelations);
		return segmentText;
	}

	/**
	 * Genera todos los párrafos para este segmento.
	 * @param textRelations Las relaciones a tener en cuenta para generar el texto.
	 * @return El string con el texto.
	 */
	private String makeText(ArrayList<Relation> textRelations) {
		String text = "";
		
		// Categorizamos las relaciones por Place
		HashMap<String, ArrayList<Relation>> relationsByPlace = new HashMap<String, ArrayList<Relation>>();
		for (Relation relation : textRelations) {
			String place = "";
			if (relation.getPlace() == null) {
				place = "NoPlace";
			}
			if (relationsByPlace.containsKey(place)) {
				relationsByPlace.get(place).add(relation);
			}
			else {
				ArrayList<Relation> newPlaceRelation = new ArrayList<Relation>();
				newPlaceRelation.add(relation);
				relationsByPlace.put(place, newPlaceRelation);
			}
		}
		
		// Para cada categoría de Place, se crea un un párrafo
		for (Entry<String, ArrayList<Relation>> entry : relationsByPlace.entrySet()) {
			text += makeParagrah(entry.getValue());
		}
		
		return text;
	}
	
	/**
	 * Genera el texto de un párrafo. Cada párrafo está relacionado con sucesos de un lugar,
	 * excepto los que no tienen lugar que están juntos en uno.
	 * @param paragraphRelations Relaciones que van a formar parte del párrafo.
	 * @return El string con el texto.
	 */
	private String makeParagrah(ArrayList<Relation> paragraphRelations) {
		String paragraph = "";
		
		// Categorizamos las relaciones por Source
		HashMap<String, ArrayList<Relation>> relationsBySource = new HashMap<String, ArrayList<Relation>>();
		for (Relation relation : paragraphRelations) {
			if (relationsBySource.containsKey(relation.getSource())) {
				relationsBySource.get(relation.getSource()).add(relation);
			}
			else {
				ArrayList<Relation> newSourceRelation = new ArrayList<Relation>();
				newSourceRelation.add(relation);
				relationsBySource.put(relation.getSource(), newSourceRelation);
			}
		}
		
		// Para cada categoría de Source, se crea una frase
		for (Entry<String, ArrayList<Relation>> entry : relationsBySource.entrySet()) {
			paragraph += makePhrase(entry.getValue());
		}
		
		paragraph += "\n";
		return paragraph;
	}
	
	/**
	 * Genera el texto relacionado con un personaje y lugar si existe.
	 * El lugar y el personaje es el mismo para todas las relaciones.
	 * @param phraseRelations Relaciones que van a generar la frase.
	 * @return El string con el texto.
	 */
	private String makePhrase(ArrayList<Relation> phraseRelations) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		String phrase = "";
		if (phraseRelations.get(0).getPlace() != null) 
			phrase += "En " + phraseRelations.get(0).getPlace() + ", ";
		phrase += phraseRelations.get(0).getSource();
		for (Relation relation : phraseRelations) {
			// Por ahora encaje básico, la gracia es tener un listado de encajes posibles
			if (!ob.existsClass(relation.getAction())) {
				// Obtener las propiedades
				ArrayList<String> properties = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();
				ob.listInstancePropertiesValues(relation.getAction(), properties, values);
				// Obtener el valor del verbo en pasado
				String past = "";
				String prepositionDirectObject = "";
				for (int j = 0; j < properties.size(); j++) {
					if (properties.get(j).contains("PasadoSingular"))
						past = values.get(j).split("\\^\\^")[0];
					else if (properties.get(j).contains("PreposicionObjetoDirecto"))
						prepositionDirectObject = values.get(j).split("\\^\\^")[0];
				}
				// Formar la frase y añadirla a los String del grupo de frases solas
				phrase += " " + past;
				if (relation.getTarget() != null) 
					phrase += " " + prepositionDirectObject + " " + relation.getTarget();
			}
		}
		phrase += ". ";
		return phrase;
	}
}
