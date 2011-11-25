/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJFrame.java
 *
 * Created on 18-nov-2011, 21:02:25
 */

package ui;

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
     * Panel derecho para contener el resto de componentes de la interfaz.
     */
    private JScrollPane jScrollPaneRight;
	
    /**
     * Barra de men� de la aplicaci�n.
     */
    private MainMenu jMenuBar;
    
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
        
        // Panel derecho para el resto de componentes
        jScrollPaneRight = new JScrollPane();
        textAreaVisualizacion = new javax.swing.JTextArea();
        labelVisualizacion = new javax.swing.JLabel();
        
        textAreaVisualizacion.setColumns(20);
        textAreaVisualizacion.setRows(5);
        textAreaVisualizacion.setFont(new java.awt.Font("Garamond", 0, 18));
        jScrollPaneRight.setViewportView(textAreaVisualizacion);

        labelVisualizacion.setFont(new java.awt.Font("Garamond", 0, 18));
        labelVisualizacion.setText("Visualizaci�n de selecci�n:");
        labelVisualizacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneRight, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelVisualizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelVisualizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPaneRight, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
                    .addComponent(jScrollPaneLeft, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
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

	/**
	 * Accesora del panel derecho de la interfaz.
	 * @return Panel derecho de la interfaz.
	 */
	public JScrollPane getjScrollPaneRight() {
		return jScrollPaneRight;
	}
	
	/**
	 * Accesora del �rea de visualizaci�n de selecci�n.
	 * @return El JTextArea de visualizaci�n de selecci�n.
	 */
    public javax.swing.JTextArea getTextAreaVisualizacion() {
		return textAreaVisualizacion;
	}

	private javax.swing.JLabel labelVisualizacion;
    private javax.swing.JTextArea textAreaVisualizacion;

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (arg0 == observableReader) {
			storyJTree = new StorySoFarTree(this);
            storyJTree.loadStory(observableReader.getStorySoFar());
		}
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
