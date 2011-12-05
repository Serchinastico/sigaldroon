package ui.evaluator;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.math.plot.Plot2DPanel;

import evaluator.DynamicEvaluator;

public class EvaluatorJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private DynamicEvaluator evaluator;
	
	private JTable patternTable;
	
	private Plot2DPanel plot;
	
	private JTextArea selectedPattern;
	
	public EvaluatorJFrame(DynamicEvaluator evaluator) {
		this.evaluator = evaluator;
		
		setJFrameProperties();
		createComponents();
		pack();
		setVisible(true);
	}
	
	private void setJFrameProperties() {
		setPreferredSize(new Dimension(800, 400));
	}

	private void createComponents() {
		patternTable = new JTable(new EvaluatorTableModel(evaluator));
		patternTable.getSelectionModel().addListSelectionListener(new EvaluatorTableModelListener());
		patternTable.setFillsViewportHeight(true);
		
		plot = new Plot2DPanel();
		
		selectedPattern = new JTextArea();
		selectedPattern.setMargin(new Insets(10, 10, 10, 10));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		JScrollPane leftTopPane = new JScrollPane(patternTable);
		JScrollPane leftBotPane = new JScrollPane(selectedPattern, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		leftBotPane.setPreferredSize(new Dimension(200, 150));
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		leftPanel.add(leftTopPane);
		leftPanel.add(leftBotPane);
		add(leftPanel);
		add(plot);	
	}
	
	public static void main(String[] args) {
		DynamicEvaluator evaluator = new DynamicEvaluator("resources/DynamicEvaluator/TestDynamic.txt");
		EvaluatorJFrame frame = new EvaluatorJFrame(evaluator);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class EvaluatorTableModelListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			int row = patternTable.getSelectedRow();
			// Label
			selectedPattern.setText(evaluator.getQPatterns().get(row).toString());
			// Gráfica
			double[] x = new double[evaluator.getStoryBreaks()];
			double[] y = new double[evaluator.getStoryBreaks()];
			for (int i = 0; i < evaluator.getStoryBreaks(); i++) {
				x[i] = i;
				y[i] = evaluator.getWeights().get(row)[i];
			}
			plot.removeAllPlots();
			plot.addLinePlot("", x, y);
		}
		
	}

}
