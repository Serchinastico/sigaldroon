package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;

import reader.Reader;
import ui.menu.MainMenu;
import ui.treeViewer.StorySoFarTree;

/**
 * Frame para la aplicaci�n que implementa el patr�n Mediator entre todos los 
 * componentes de la interfaz y el lector. 
 * Tambi�n implementa el patr�n Observer para observar la modificaci�n de la historia.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 */
public class StoryJFrame extends javax.swing.JFrame implements Observer {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Referencia al lector observable (patr�n Observer).
	 */
	private Reader observableReader;
	
	/**
	 * �rbol para mostrar la historia por segmentos y relaciones.
	 */
	private StorySoFarTree storyJTree;
	
	/**
	 * Panel izquierdo para contener el �rbol.
	 */
    private JScrollPane jScrollPaneLeft;
	
    /**
     * Barra de men� de la aplicaci�n.
     */
    private MainMenu jMenuBar;
    
    /**
     * Panel para la visualizaci�n de partes de la historia.
     */
    private Visualization visualizationPane;
    
    /**
     * Panel para �rdenes de control de la generaci�n.
     */
    private ControlArea controlPane; 
    
    /**
     * Panel para el �rea de texto natural.
     */
    private NaturalTextArea naturalTextPane;
    
    /** 
     * Crea un nuevo formulario.
     */
    public StoryJFrame() {
    	setConfiguration();
    	initObservables();
        initComponents();
    }
    
    /**
     * Estable la configuraci�n del JFrame.
     */
    private void setConfiguration() {
    	this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    	this.setTitle("Generador de Historias - Mitos Griegos");
    	this.setSize(1024, 700);
    }
    
    /**
     * Inicializa los objetos que la interfaz va a observar.
     */
    private void initObservables() {
    	observableReader = new Reader();
    	observableReader.addObserver(this);
    }
    
    /**
     * Inicializa los componentes de la interfaz.
     */
    private void initComponents() {

    	// Barra de men�
    	jMenuBar = new MainMenu(this);        
        setJMenuBar(jMenuBar);
    	
    	// Panel izquierdo para el �rbol de la historia
        jScrollPaneLeft = new JScrollPane();
        storyJTree = new StorySoFarTree(this);
        
        visualizationPane = new Visualization(this);
        
        controlPane = new ControlArea(this);
        
        naturalTextPane = new NaturalTextArea(this);
        
        // Panel derecho para el resto de componentes        
        this.getContentPane().setLayout(new GridBagLayout());
        
        GridBagConstraints constrains = new GridBagConstraints();
        constrains.gridx = 0;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 3;
        constrains.weightx = 0.50;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.getContentPane().add(jScrollPaneLeft,constrains);
        constrains.weightx = 0.0;
        
        constrains.gridx = 1;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.weighty = 1.0;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.getContentPane().add(visualizationPane,constrains);
        
        constrains.gridx = 1;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.weighty = 0.2;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.getContentPane().add(naturalTextPane,constrains);
        
        constrains.gridx = 1;
        constrains.gridy = 2;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 1.0;
        constrains.weighty = 0.1;
        constrains.fill = GridBagConstraints.BOTH;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.getContentPane().add(controlPane,constrains);
        constrains.weightx = 0.0;
        constrains.weighty = 0.0;
    }
    
    /**
     * Accesora del �rbol de la historia.
     * @return
     */
    public StorySoFarTree getStoryJTree() {
		return storyJTree;
	}
    
    /**
	 * Mutadora del �rbol de la historia.
	 * @param storyJTree Nuevo �rbol de la historia.
	 */
	public void setStoryJTree(StorySoFarTree storyJTree) {
		this.storyJTree = storyJTree;
	}
    
    /**
     * Accesora del lector observable por la interfaz.
     * @return El lector observable.
     */
	public Reader getObservableReader() {
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
            
            //TODO: A�adir la actualizaci�n del progress bar.
		}
	}
	
	/**
	 * Accesora del panel de visualizaci�n.
	 * @return El panel de visualizaci�n.
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
	 * @param args the command line arguments
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
