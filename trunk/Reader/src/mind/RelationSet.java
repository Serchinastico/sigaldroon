package mind;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

public class RelationSet {

	private final static float RANGE = 0.1f;
	
	private HashMap<LazyRelation, Float> set;
	
	public RelationSet() {
		super();
		set = new HashMap<LazyRelation, Float>();
	}
	
	public void addAll(Collection<Relation> c) {
		for (Relation r : c) {
			set.put(new LazyRelation(r), 0.5f);
		}
	}
	
	public void updateWeights() {
		HashMap<LazyRelation, Float> newSet = new HashMap<LazyRelation, Float>();
		
		for (Entry<LazyRelation, Float> wr : set.entrySet()) {
			float newWeight = wr.getValue() + RANGE;
			if (newWeight < 1.0f) {
				wr.setValue(newWeight);
				newSet.put(wr.getKey(), wr.getValue());
			}
		}
		
		set = newSet;
	}
	
	public boolean contains(Relation r) {
		return set.containsKey(new LazyRelation(r));
	}
	
	public float getWeight(Relation r) {
		return set.get(new LazyRelation(r));
	}
}
