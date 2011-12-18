package operator;

import java.util.ArrayList;
import java.util.Iterator;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import mind.Relation;
import mind.ChangedMind;
import mind.ontobridge.OntoBridgeSingleton;

/**
 * Operador para especializar elementos bajando un nivel en la ontología.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class Specialize extends OperatorSingle {

	/**
	 * Constructora por defecto.
	 */
	public Specialize() {
		opWeight = 0.8f;
		opId = OPList.SPECIALIZE;
	}
	
	/**
	 * Genera todos los hijos posibles para una relación intentando especializar
	 * con subclases e instancias cada uno de sus elementos. 
	 * @param m Mente a operar.
	 * @param r Relación a operar.
	 * @param gM Listado actual de mentes generadas.
	 */
	protected void generateByRelation(ChangedMind m, Relation r, ArrayList<ChangedMind> gM) {

		OntoBridge onto = OntoBridgeSingleton.getInstance();
		
		for (int i = 0; i < Relation.NUM_ELEMENTS; i++) {
			
			if (r.getElement(i) == null) continue;
			
			// Solo se especializa si el elemento es una clase
			if (onto.existsClass(r.getElement(i))) {
				
				// Especialización por subclases
				Iterator<String> itSubClasses = onto.listSubClasses(r.getElement(i), true);
				while (itSubClasses.hasNext()) {
					String subClass = onto.getShortName(itSubClasses.next());
					if (!subClass.contains("Nothing")) {
						ChangedMind newMind = generateChild(m, r, i, subClass);
						gM.add(newMind);
					}
				}
				
				// Especialización por instancias de la clase
				Iterator<String> itInstances = onto.listDeclaredInstances(r.getElement(i));
				while (itInstances.hasNext()) {
					String instanceName = onto.getShortName(itInstances.next());
					ChangedMind newMind = generateChild(m, r, i, instanceName);
					gM.add(newMind);
				}
			}
		}
	}

}
