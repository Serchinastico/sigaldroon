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
	private static final double WIDTH = 110;
		
	/**
	 * Alto por defecto para las celdas.
	 */
	private static final double HEIGHT = 50;
	
	/**
	 * Valor inicial para colocar elementos en horizontal.
	 */
	private static final double X_INIT = 30;
	
	/**
	 * Valor inicial para colocar elementos en vertical.
	 */
	private static final double Y_INIT = 90;
	
	/**
	 * Incremento para colocar componentes en horizontal.
	 */
	private static final double X_INCR = 220;
	
	/**
	 * Incremento para colocar componentes en vertical.
	 */
	private static final double Y_INCR = 80;

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
			Object source = graph.insertVertex(parents.get(0), null, r.getSource(), x , y, WIDTH, HEIGHT,"defaultVertex;fontSize=18;fontFamily=Garamond");
			Object action = graph.insertVertex(parents.get(0), null, r.getAction() + "(" + r.getWeight() + ")", x + X_INCR, y, WIDTH, HEIGHT,"defaultVertex;shape=rhombus;fontSize=18;fontFamily=Garamond");
			graph.insertEdge(parents.get(0), null, " fuente ", source, action,"defaultEdge;fontSize=18;fontFamily=Garamond");
			
			// Action -> Target
			if (r.getTarget() != null) {
				Object target = graph.insertVertex(parents.get(0), null, r.getTarget(), x + 2 * X_INCR, y, WIDTH, HEIGHT,"defaultVertex;fontSize=18;fontFamily=Garamond");
				graph.insertEdge(parents.get(0), null, " destinatario ", action, target,"defaultEdge;fontSize=18;fontFamily=Garamond");
			}
			
			// Action -> Object Direct
			if (r.getDirectObject() != null) {
				Object directObject = graph.insertVertex(parents.get(0), null, r.getDirectObject(), x + X_INCR, y - Y_INCR, WIDTH, HEIGHT, "defaultVertex;shape=ellipse;fontSize=18;fontFamily=Garamond");
				graph.insertEdge(parents.get(0), null, " objeto directo ", action, directObject,"defaultEdge;fontSize=18;fontFamily=Garamond");
			}
			
			// Action -> Place
			if (r.getPlace() != null) {
				Object place = graph.insertVertex(parents.get(0), null, r.getPlace(), x + X_INCR, y + Y_INCR, WIDTH, HEIGHT, "defaultVertex;shape=ellipse;fontSize=18;fontFamily=Garamond");
				graph.insertEdge(parents.get(0), null, " lugar ", action, place,"defaultEdge;fontSize=18;fontFamily=Garamond");
			}
			
			y  += Y_INCR * 3;
			
		} finally {
			graph.getModel().endUpdate();
		}
	}
}
