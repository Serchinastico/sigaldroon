package mind;

public class LazyRelation extends Relation {
	
	public LazyRelation(Relation r) {
		elements = new String[NUM_ELEMENTS];
		for (int i = 0; i < NUM_ELEMENTS; i++) {
			if (r.elements[i] != null) 
				elements[i] = new String(r.elements[i]);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Relation))
			return false;
		Relation c = (Relation) o;
		
		boolean sourceEq = (getSource() == null && c.getSource() == null) || 
				(getSource() != null && getSource().equals(c.getSource()));
		boolean actionEq = (getAction() == null && c.getAction() == null) || 
				(getAction() != null && getAction().equals(c.getAction()));
		
		
		return (sourceEq &&	actionEq);
	}
	
	@Override
	public int hashCode() {
		return (getSource() + getAction() ).hashCode();
	}
}
