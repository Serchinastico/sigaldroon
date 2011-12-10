package segmenter.phrase;

import java.util.ArrayList;

import segmenter.syntax.NameBridge;
import segmenter.syntax.VerbBridge;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import mind.Relation;
import mind.ontobridge.OntoBridgeSingleton;

/**
 * Crea frases dado un listado de relaciones.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class BasicPhrase extends PhraseSocket {

	@Override
	public String generatePhrase(ArrayList<Relation> phraseRelations) {
		OntoBridge ob = OntoBridgeSingleton.getInstance();
		String phrase = "", subject = "";
		
		// Formación del sujeto de la frase
		subject = NameBridge.getInstance().getName(phraseRelations.get(0).getSource());
		
		// Forma todos los predicados de la frase
		ArrayList<String> actions = new ArrayList<String>();
		for (Relation relation : phraseRelations)
			if (!ob.existsClass(relation.getAction()))
				actions.add(formPredicate(relation));

		// Crea el string con toda la frase
		if (actions.size() > 0) {
			phrase = subject;
			for (int i = 0; i < actions.size(); i++) {
				if (i == 0) 
					phrase += " " + actions.get(i);
				else if (i == actions.size() -1) 
					phrase += " y " + actions.get(i);
				else
					phrase += ", " + actions.get(i);
			}
			phrase += ". ";
		}
		
		return phrase;
	}

	/**
	 * Forma el predicado de la frase.
	 * @param r Relación a partir de la cual forma el predicado.
	 * @return El string con el texto del predicado formado.
	 */
	private String formPredicate(Relation r) {
		String predicate = "", verb = "", directObject = "", indirectObject = "";

		// Formación del sintagma verbal
		verb = VerbBridge.getInstance().getPastForm(r.getAction());

		// Formación del objeto directo si existe
		if (r.getDirectObject() != null) 
			directObject = " " + VerbBridge.getInstance().getDirectObjectPreposition(r.getAction()) +
				" " +
				NameBridge.getInstance().getName(r.getDirectObject());

		// Formación del objeto indirecto si existe
		if (r.getTarget() != null) 
			indirectObject = " " + VerbBridge.getInstance().getIndirectObjectPreposition(r.getAction()) + 
				" " +
				NameBridge.getInstance().getName(r.getTarget());
		
		// Formación completa del predicado
		predicate = verb + directObject + indirectObject;
		
		return predicate;
	}

}
