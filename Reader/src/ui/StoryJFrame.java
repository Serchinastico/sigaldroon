package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;

import mind.ontobridge.OntoBridgeSingleton;

import reader.IReader;
import reader.Reader;
import ui.menu.MainMenuBar;
import ui.panel.ControlArea;
import ui.panel.NaturalTextArea;
import ui.panel.Visualization;
import ui.panel.treeViewer.StorySoFarTree;

/**
 * Frame para la aplicación que implementa el patrón Mediator entre todos los 
 * componentes de la interfaz y el lector. 
 * También implementa el patrón Observer para observar la modificación de la historia.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 */
public class StoryJFrame extends javax.swing.JFrame implements Observer {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Referencia al lector observable (patrón Observer).
	 */
	private IReader observableReader;
	
	/**
	 * Árbol para mostrar la historia por segmentos y relaciones.
	 */
	private StorySoFarTree storyJTree;
	
	/**
	 * Panel izquierdo para contener el árbol.
	 */
    private JScrollPane jScrollPaneLeft;
	
    /**
     * Barra de menú de la aplicación.
     */
    private MainMenuBar jMenuBar;
    
    /**
     * Panel para la visualización de partes de la historia.
     */
    private Visualization visualizationPane;
    
    /**
     * Panel para órdenes de control de la generación.
     */
    private ControlArea controlPane; 
    
    /**
     * Panel para el área de texto natural.
     */
    private NaturalTextArea naturalTextPane;
    
    /** 
     * Crea un nuevo formulario.
     */
    public StoryJFrame() {
    	setConfiguration();
    	initModels();
        initComponents();
    }
    
    /**
     * Establece la configuración del JFrame.
     */
    private void setConfiguration() {
    	this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    	this.setTitle("Generador de Historias - Mitos Griegos");
    	this.setSize(900, 700);
    }
    
    /**
     * Inicializa los objetos del modelo.
     */
    private void initModels() {
    	OntoBridgeSingleton.getInstance();
    	observableReader = new Reader();
    	observableReader.insertObserver(this);
    }
    
    /**
     * Inicializa los componentes de la interfaz.
     */
    private void initComponents() {

    	// Barra de menú
    	jMenuBar = new MainMenuBar(this);        
        setJMenuBar(jMenuBar);
    	
        // Objetos para hacer el grid
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
        
        // Panel para el árbol con los segmentos y relaciones de la historia        
        jScrollPaneLeft = new JScrollPane();
        storyJTree = new StorySoFarTree(this);
        
        constrains.gridx = 0;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 3;
        constrains.weightx = 0.50;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.getContentPane().add(jScrollPaneLeft, constrains);
        constrains.weightx = 0.0;
        
        // Panel de visualización de la historia
        visualizationPane = new Visualization(this);
        
        constrains.gridx = 1;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.weighty = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.getContentPane().add(visualizationPane, constrains);
        
        // Panel de lenguaje natural
        naturalTextPane = new NaturalTextArea(this);
        
        constrains.gridx = 1;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.weighty = 0.2;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.getContentPane().add(naturalTextPane, constrains);
        
        // Panel de control
        controlPane = new ControlArea(this);
        
        constrains.gridx = 1;
        constrains.gridy = 2;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.weighty = 0.1;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.getContentPane().add(controlPane, constrains);
        constrains.weightx = 0.0;
        constrains.weighty = 0.0;
    }
    
    /**
     * Accesora del árbol de la historia.
     * @return
     */
    public StorySoFarTree getStoryJTree() {
		return storyJTree;
	}
    
    /**
	 * Mutadora del árbol de la historia.
	 * @param storyJTree Nuevo árbol de la historia.
	 */
	public void setStoryJTree(StorySoFarTree storyJTree) {
		this.storyJTree = storyJTree;
	}
    
    /**
     * Accesora del lector observable por la interfaz.
     * @return El lector observable.
     */
	public IReader getObservableReader() {
		return observableReader;
	}

	/**
	 * Mutadora del lector observable por la interfaz.
	 * @param observableReader El nuevo lector observable.
	 */
	public void setObservableReader(Reader observableReader) {
		this.observableReader = observableReader;
	}

	/**
	 * Accesora del panel izquierdo de la interfaz.
	 * @return Panel izquierdo de la interfaz.
	 */
	public JScrollPane getjScrollPaneLeft() {
		return jScrollPaneLeft;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (arg0 == observableReader) {
			storyJTree = new StorySoFarTree(this);
            storyJTree.loadStory(observableReader.getStorySoFar());
            
            //TODO: Añadir la actualización del progress bar.
		}
	}
	
	/**
	 * Accesora del panel de visualización.
	 * @return El panel de visualización.
	 */
	public Visualization getVisualizationPane() {
		return visualizationPane;
	}
	
	/**
	 * Accesora del panel de texto natural.
	 * @return El panel de texto natural.
	 */
	public NaturalTextArea getNaturalTextPane() {
		return naturalTextPane;
	}

	/**
	 * @param args the command line arguments.
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				StoryJFrame sJF = new StoryJFrame();
				sJF.setVisible(true);
			}
		});
	}

}
