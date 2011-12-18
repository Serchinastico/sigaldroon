package operator;

import java.util.ArrayList;
import java.util.Iterator;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import mind.Relation;
import mind.ChangedMind;
import mind.ontobridge.OntoBridgeSingleton;


/**
 * Operador para generalizar acciones subiendo un nivel en la ontolog�a.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 */
public class Generalize extends OperatorSingle {

	/**
	 * Constructora por defecto.	
	 */
	public Generalize() {
		opWeight = 0.6f;
		opId = OPList.GENERALIZE;
	}
	
	/**
	 * Genera todos los hijos posibles para una relaci�n intentando generalizar
	 * con subclases e instancias cada uno de sus elementos. 
	 * @param m Mente a operar.
	 * @param r Relaci�n a operar.
	 * @param gM Listado actual de mentes generadas.
	 */
	protected void generateByRelation(ChangedMind m, Relation r, ArrayList<ChangedMind> gM) {
		
		OntoBridge onto = OntoBridgeSingleton.getInstance();
		
		for (int i = 0; i < Relation.NUM_ELEMENTS; i++) {
			
			if (r.getElement(i) == null) continue;
			
			Iterator<String> itSuperClasses;
			
			// Se obtiene las superclases inmediatas
			// Dependiendo de si el elemento es clase o instancia se obtiene de diferente manera
			if (onto.existsClass(r.getElement(i))) 
				itSuperClasses = onto.listSuperClasses(r.getElement(i), true);
			else 
				itSuperClasses = onto.listDeclaredBelongingClasses(r.getElement(i));
				
			
			while (itSuperClasses.hasNext()) {
				
				String superClass = onto.getShortName(itSuperClasses.next());
				
				// Si no es clase del sistema y no ha superado el m�ximo en la ontolog�a
				if (!superClass.contains("Class") && !superClass.contains("NamedIndividual") && !superClass.contains("Thing")) {
					ChangedMind newMind = generateChild(m, r, i, superClass);
					gM.add(newMind);
				}
			}
		}
	}

}

