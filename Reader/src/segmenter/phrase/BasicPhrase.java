package segmenter.phrase;

import java.util.ArrayList;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import mind.Relation;
import mind.ontobridge.OntoBridgeSingleton;

public class BasicPhrase extends PhraseSocket {

	@Override
	public String generatePhrase(ArrayList<Relation> phraseRelations) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		String phrase = "";
		String source = phraseRelations.get(0).getSource();
		ArrayList<String> actions = new ArrayList<String>();
		for (Relation relation : phraseRelations) {
			// Por ahora encaje básico, la gracia es tener un listado de encajes posibles
			if (!ob.existsClass(relation.getAction())) {
				String action = ""; 
				// Obtener las propiedades
				ArrayList<String> properties = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();
				ob.listInstancePropertiesValues(relation.getAction(), properties, values);
				// Obtener el valor del verbo en pasado y su preposición de OD
				String past = "";
				String prepositionDirectObject = "";
				for (int j = 0; j < properties.size(); j++) {
					if (properties.get(j).contains("PasadoSingular"))
						past = values.get(j).split("\\^\\^")[0];
					else if (properties.get(j).contains("PreposicionObjetoDirecto"))
						prepositionDirectObject = values.get(j).split("\\^\\^")[0];
				}
				// Formar la frase y añadirla a los String de acciones
				action += past;
				if (relation.getTarget() != null) 
					action += " " + prepositionDirectObject + " " + relation.getTarget();
				actions.add(action);
			}
		}
		
		// Crea el string con toda la frase
		phrase += source;
		for (int i = 0; i < actions.size(); i++) {
			if (i == 0) 
				phrase += " " + actions.get(i);
			else if (i == actions.size() -1) 
				phrase += " y " + actions.get(i);
			else
				phrase += ", " + actions.get(i);
		}
		phrase += ". ";
		return phrase;
	}

}
