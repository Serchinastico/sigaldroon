package operator;

import java.util.ArrayList;

import mind.ChangedMind;
import mind.Relation;

/**
 * Operador para añadir una nueva relación cambiando su objeto por uno ya existente en la mente.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class Joint extends OperatorSingle {

	/**
	 * Constructora por defecto.
	 */
	public Joint() {
		opWeight = 0.5f;
		opId = OPList.JOINT;
	}
	
	/**
	 * Genera todos los hijos posibles para una relación sustituyendo el sujeto
	 * por cada uno de los sujetos del resto de relaciones de la mente. 
	 * @param m Mente a operar.
	 * @param r Relación a operar.
	 * @param gM Listado actual de mentes generadas.
	 */
	protected void generateByRelation(ChangedMind m, Relation r, ArrayList<ChangedMind> gM) {

		for (Relation subjectSourceRel : m.getActualMind()) {
			String subject = subjectSourceRel.getSource();
			
			if (subject.equals(r.getSource()))
				continue;
			
			Relation generatedRel = r.clone();
			generatedRel.setSource(subject);
			
			ChangedMind newMind = m.clone();
			//newMind.getActualMind().add(generatedRel);
			newMind.insertChange(subjectSourceRel, generatedRel, OPList.JOINT);
			
			gM.add(newMind);
		}
	}

}
