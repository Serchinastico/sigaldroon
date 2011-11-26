package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Panel para el control de la generación de la historia.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class ControlArea extends JPanel {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Frame contenedor de este menú.
	 */
	private StoryJFrame frame;
	
	/**
	 * Botón para siguiente segmento de la historia.
	 */
	private JButton buttonNext;
    
	/**
	 * Botón para completar lo que reste de historia.
	 */
    private JButton buttonComplete;
    
    /**
     * Barra de progreso de generación de segmentos.
     */
    private JProgressBar progressBar;
	
	/**
	 * Constructora del área de control.
	 * @param f Frame mediator.
	 */
	public ControlArea(StoryJFrame f) {
		super();
		frame = f;
		
		buttonNext = new JButton("Next");
		buttonNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	buttonNextMouseClicked(evt);
            }
        });
        buttonComplete = new JButton("Complete");
        buttonComplete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	buttonCompleteMouseClicked(evt);
            }
        });
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
        constrains.gridx = 0;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.2;
        constrains.weighty = 1.0;
        constrains.fill = GridBagConstraints.NONE;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(buttonNext,constrains);
        
        constrains.gridx = 0;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.2;
        constrains.weighty = 1.0;
        constrains.fill = GridBagConstraints.NONE;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(buttonComplete,constrains);
        
        constrains.gridx = 1;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 2;
        constrains.weightx = 1.0;
        constrains.weighty = 1.0;
        constrains.fill = GridBagConstraints.HORIZONTAL;
        constrains.insets = new Insets(5, 25, 5, 25);
        constrains.ipady = 20;
        
        this.add(progressBar,constrains);
	}
	
	/**
	 * Listener para generar un nuevo segmento de la historia..
	 * @param evt
	 */
	private void buttonNextMouseClicked(java.awt.event.MouseEvent evt) {
		if (frame.getObservableReader().isInitialized())
    		frame.getObservableReader().generateNextSegment();
        else
        	JOptionPane.showMessageDialog(null, "La historia no ha sido inicializada. Escoge un archivo para inicializar.");
	}
	
	/**
	 * Listener para completar una historia.
	 * @param evt
	 */
	private void buttonCompleteMouseClicked(java.awt.event.MouseEvent evt) {
		if (frame.getObservableReader().isInitialized())
        	frame.getObservableReader().generateStory();
        else
        	JOptionPane.showMessageDialog(null, "La historia no ha sido inicializada. Escoge un archivo para inicializar.");
	}

}
