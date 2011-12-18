package operator;

import java.util.ArrayList;

import mind.ChangedMind;
import mind.Relation;

public class Remove extends OperatorSingle {

	public Remove() {
		opWeight = 0.95f;
		opId = OPList.REMOVE;
	}
	
	@Override
	protected void generateByRelation(ChangedMind m, Relation r,
			ArrayList<ChangedMind> gM) {
		
		if (r.getDirectObject() == null) {
			ChangedMind newMind = generateChild(m, r, Relation.DIRECT_OBJECT, null);
			gM.add(newMind);
		}
		
		if (r.getTarget() == null) {
			ChangedMind newMind = generateChild(m, r, Relation.TARGET, null);
			gM.add(newMind);
		}
		
		
		if (r.getPlace() == null) {
			ChangedMind newMind = generateChild(m, r, Relation.PLACE, null);
			gM.add(newMind);
		}
	}

}
