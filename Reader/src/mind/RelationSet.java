package mind;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

public class RelationSet {

	private final static float RANGE = 0.1f;
	
	private HashMap<Relation, Float> set;
	
	public RelationSet() {
		super();
		set = new HashMap<Relation, Float>();
	}
	
	public void addAll(Collection<Relation> c) {
		for (Relation r : c) {
			set.put(r, 0.5f);
		}
	}
	
	public void updateWeights() {
		HashMap<Relation, Float> newSet = new HashMap<Relation, Float>();
		
		for (Entry<Relation, Float> wr : set.entrySet()) {
			float newWeight = wr.getValue() + RANGE;
			if (newWeight < 1.0f) {
				wr.setValue(newWeight);
				newSet.put(wr.getKey(), wr.getValue());
			}
		}
		
		set = newSet;
	}
	
	public boolean contains(Relation r) {
		return set.containsKey(r);
	}
	
	public float getWeight(Relation r) {
		return set.get(r);
	}
}
