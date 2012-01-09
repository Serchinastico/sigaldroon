package operator;

import java.util.ArrayList;

import mind.ChangedMind;

/**
 * Aplica los operadores a usar en la creación de mentes.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class OperatorApplicator {

	private ArrayList<Operator> operators;
	
	public OperatorApplicator() {
		operators = new ArrayList<Operator>();
		operators.add(new Generalize());
		operators.add(new Specialize());
		operators.add(new Add());
		operators.add(new Remove());
		operators.add(new Joint());
	}
	
	public ArrayList<ChangedMind> generateChilds(ChangedMind m) {
		ArrayList<ChangedMind> childs = new ArrayList<ChangedMind>();
		for (Operator op : operators) {
			op.generateMinds(m, childs);
		}
		return childs;
	}
}
