package segmenter;

import java.util.ArrayList;

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
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		String segmentText = "";
		ArrayList<String> singleSentences = new ArrayList<String>();
		ArrayList<Change> filteredChanges = filterChanges(m.getChanges());
		for (Change c : filteredChanges) {
			Relation resultRelation = c.getAfter();
			if (!ob.existsClass(resultRelation.getAction())) {
				// Obtener las propiedades
				ArrayList<String> properties = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();
				ob.listInstancePropertiesValues(resultRelation.getAction(), properties, values);
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
				String sentence = resultRelation.getSource() + " " + past;
				if (resultRelation.getTarget() != null) 
					sentence += " " + prepositionDirectObject + " " + resultRelation.getTarget();
				singleSentences.add(sentence);
			}
		}
		for (String sentence : singleSentences) {
			segmentText += sentence + ". ";
		}
		return segmentText;
	}
	
	/**
	 * Para cada cambio, solo se incluye en la lista resultado si
	 * la relación resultante del cambio no se ha usado en un cambio posterior
	 * con algún operador.
	 * @param changes Cambios realizados.
	 * @return Cambios filtrados.
	 */
	private ArrayList<Change> filterChanges(ArrayList<Change> changes) {
		ArrayList<Change> filteredChanges = new ArrayList<Change>();
		for (int i = 0; i < changes.size(); i++) {
			// Solo es necesario mirar en los cambios que hay adelante
			// y ver si la relación resultante es usada en otro cambio de forma inicial
			int j = i + 1;
			boolean changeToInclude = true;
			while ((changeToInclude) && (j < changes.size())) {
				changeToInclude = !changes.get(i).getAfter().equals(changes.get(j).getBefore());
			}
			if (changeToInclude) 
				filteredChanges.add(changes.get(i));
		}
		return filteredChanges;
	}

	@Override
	public String generateInitialSegment(Mind m) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		String segmentText = "";
		ArrayList<String> singleSentences = new ArrayList<String>();
		for (Relation resultRelation : m) {
			if (!ob.existsClass(resultRelation.getAction())) {
				// Obtener las propiedades
				ArrayList<String> properties = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();
				ob.listInstancePropertiesValues(resultRelation.getAction(), properties, values);
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
				String sentence = resultRelation.getSource() + " " + past;
				if (resultRelation.getTarget() != null) 
					sentence += " " + prepositionDirectObject + " " + resultRelation.getTarget();
				singleSentences.add(sentence);
			}
		}
		for (String sentence : singleSentences) {
			segmentText += sentence + ". ";
		}
		return segmentText;
	}

}
