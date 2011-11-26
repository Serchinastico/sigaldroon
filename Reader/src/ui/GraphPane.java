package ui;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JScrollPane;

import mind.Mind;
import mind.Relation;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * Panel para la visualización de texto natural de partes seleccionadas en el árbol.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class GraphPane extends JScrollPane {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ancho por defecto para las celdas.
	 */
	private static final double WIDTH = 50;
		
	/**
	 * Alto por defecto para las celdas.
	 */
	private static final double HEIGHT = 40;
	
	/**
	 * Valor inicial para colocar elementos en horizontal.
	 */
	private static final double X_INIT = 30;
	
	/**
	 * Valor inicial para colocar elementos en vertical.
	 */
	private static final double Y_INIT = 70;
	
	/**
	 * Incremento para colocar componentes en horizontal.
	 */
	private static final double X_INCR = 100;
	
	/**
	 * Incremento para colocar componentes en vertical.
	 */
	private static final double Y_INCR = 50;

	/**
	 * Frame contenedor de este menú.
	 */
	@SuppressWarnings("unused")
	private StoryJFrame frame;
	
	/**
	 * Grafo completo.
	 */
	private mxGraph graph;
	
	/**
	 * Subgrafo de cada relación presentado en el panel.
	 */
	private ArrayList<Object> parents;
	
	private double x;
	
	private double y;
	
	/**
	 * Constructora del área de texto natural.
	 * @param f Frame mediator.
	 */
	public GraphPane(StoryJFrame f) {
		super();
		frame = f;
		
		clearGraph();
	}
	
	/**
	 * Inicia y limpia el panel del grafo de dibujos anteriores.
	 */
	public void clearGraph() {
		graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		parents = new ArrayList<Object>();
		parents.add(parent);
		
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		this.setViewportView(graphComponent);
		
		x = X_INIT;
		y = Y_INIT;
	}
	
	/**
	 * Dibuja las relaciones en el panel del grafo.
	 * @param segment
	 */
	public void printSegment(Mind segment) {
		
		Iterator<Relation> itRel = segment.iterator();
		while (itRel.hasNext()) {
			printRelation(itRel.next());
		}
		
	}
	
	/**
	 * Dibuja una relación en el siguiente espacio del panel del grafo.
	 * @param r Relación a imprimir.
	 */
	public void printRelation(Relation r) {
		
		graph.getModel().beginUpdate();
		try {
		
			// Source -> Action
			Object source = graph.insertVertex(parents.get(0), null, r.getSource(), x , y, WIDTH, HEIGHT);
			Object action = graph.insertVertex(parents.get(0), null, r.getAction(), x + X_INCR, y, WIDTH, HEIGHT);
			graph.insertEdge(parents.get(0), null, " ", source, action);
			
			// Action -> Target
			if (r.getTarget() != null) {
				Object target = graph.insertVertex(parents.get(0), null, r.getTarget(), x + 2 * X_INCR, y, WIDTH, HEIGHT);
				graph.insertEdge(parents.get(0), null, " ", action, target);
			}
			
			// Action -> Object Direct
			if (r.getDirectObject() != null) {
				Object directObject = graph.insertVertex(parents.get(0), null, r.getDirectObject(), x + X_INCR, y - Y_INCR, WIDTH, HEIGHT);
				graph.insertEdge(parents.get(0), null, " ", action, directObject);
			}
			
			// Action -> Place
			if (r.getPlace() != null) {
				Object place = graph.insertVertex(parents.get(0), null, r.getPlace(), x + X_INCR, y + Y_INCR, WIDTH, HEIGHT);
				graph.insertEdge(parents.get(0), null, " ", action, place);
			}
			
			y  += Y_INCR * 4;
			
		} finally {
			graph.getModel().endUpdate();
		}
	}
}
