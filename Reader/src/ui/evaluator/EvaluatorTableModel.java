package ui.evaluator;

import javax.swing.table.AbstractTableModel;

import evaluator.DynamicEvaluator;

public class EvaluatorTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private DynamicEvaluator evaluator;
	
	public EvaluatorTableModel(DynamicEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		// TODO Auto-generated method stub
		return Object.class;
	}

	@Override
	public int getColumnCount() {
		return evaluator.getWeights().get(0).length + 1;
	}

	@Override
	public String getColumnName(int col) {
		if (col == 0) {
			return "Pattern";
		}
		else {
			return "i" + (col - 1);
		}
	}

	@Override
	public int getRowCount() {
		return evaluator.getWeights().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		if (col == 0) {
			return "Q" + row;
		}
		else {
			return evaluator.getWeights().get(row)[col - 1];
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
	}

}
