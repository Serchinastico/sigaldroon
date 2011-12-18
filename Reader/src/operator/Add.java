package operator;

import java.util.ArrayList;

import mind.ChangedMind;
import mind.Relation;

public class Add extends OperatorSingle {

	public Add() {
		opWeight = 0.6f;
		opId = OPList.ADD;
	}
	
	@Override
	protected void generateByRelation(ChangedMind m, Relation r,
			ArrayList<ChangedMind> gM) {
		
		if (r.getDirectObject() == null) {
			ChangedMind newMind = generateChild(m, r, Relation.DIRECT_OBJECT, "Actor");
			gM.add(newMind);
			ChangedMind newMind2 = generateChild(m, r, Relation.DIRECT_OBJECT, "Objeto");
			gM.add(newMind2);
		}
		
		if (r.getTarget() == null) {
			ChangedMind newMind = generateChild(m, r, Relation.TARGET, "Actor");
			gM.add(newMind);
			ChangedMind newMind2 = generateChild(m, r, Relation.TARGET, "Objeto");
			gM.add(newMind2);
		}
		
		
		if (r.getPlace() == null) {
			ChangedMind newMind = generateChild(m, r, Relation.PLACE, "Lugar");
			gM.add(newMind);
		}
	}

}
