package segmenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import mind.ChangedMind;
import mind.Mind;
import mind.Relation;

import operator.Change;
import segmenter.paragrah.BasicParagraph;
import segmenter.paragrah.ParagraphSocket;

/**
 * Implementación de un segmentador con generación de texto
 * para los cambios realizados en la generación de este segmento.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class NaturalSegmenter implements ISegmenter {
	
	private ParagraphSocket paragraphGen;

	@Override
	public String generateSegment(ChangedMind m) {
		String segmentText = "";
		paragraphGen = new BasicParagraph();
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
			// y ver si la relación resultante es usada en otro cambio
			int j = i + 1;
			boolean changeToInclude = true;
			while ((changeToInclude) && (j < changes.size())) {
				changeToInclude = !changes.get(i).getAfter().equals(changes.get(j).getBefore());
				j++;
			}
			if (changeToInclude) 
				filteredChanges.add(changes.get(i).getAfter());
		}
		return filteredChanges;
	}

	@Override
	public String generateInitialSegment(Mind m) {
		String segmentText = "";
		paragraphGen = new BasicParagraph();
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
			text += paragraphGen.generateParagraph(entry.getValue());
		}
		
		return text;
	}
	
}
