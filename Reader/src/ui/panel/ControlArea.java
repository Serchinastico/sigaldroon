package ui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import ui.CommandManager;
import ui.StoryJFrame;

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
		
		this.setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
		
		// Botón para procesar los votos
		JButton buttonVotes = new JButton("Procesar votos");
		buttonVotes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.processVotes();
			}
		});
		
		constrains.gridx = 0;
        constrains.gridy = 0;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.2;
        constrains.weighty = 1.0;
        constrains.fill = GridBagConstraints.NONE;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        add(buttonVotes, constrains);
		
        // Botón de generación de siguiente segmento
		buttonNext = new JButton("Siguiente segmento");
		buttonNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	CommandManager.getInstance().generateNextSegment(frame);
            }
        });
        
        constrains.gridx = 0;
        constrains.gridy = 1;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.2;
        constrains.weighty = 1.0;
        constrains.fill = GridBagConstraints.NONE;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(buttonNext,constrains);
        
        // Botón de generación completa
        buttonComplete = new JButton("Generación completa");
        buttonComplete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	CommandManager.getInstance().generateCompleteStory(frame);
            }
        });
        
        constrains.gridx = 0;
        constrains.gridy = 2;
        constrains.gridwidth = 1;
        constrains.gridheight = 1;
        constrains.weightx = 0.2;
        constrains.weighty = 1.0;
        constrains.fill = GridBagConstraints.NONE;
        constrains.insets = new Insets(5, 5, 5, 5);
        
        this.add(buttonComplete,constrains);
        
        // Barra de progreso
        progressBar = new JProgressBar(0,100);
        progressBar.setValue(100);
        progressBar.setStringPainted(true);
        
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
	 * Establece el valor de la barra de progreso haciendo la división entre el valor actual
	 * y el máximo alcanzable por algún tipo de medida.
	 * @param vMin Valor actual en el progreso.
	 * @param vMax Valor máximo de la barra.
	 */
	public void setProgressBarValue(int vMin, int vMax) {
		float value = (new Float(vMin) / new Float(vMax)) * 100;
		progressBar.setValue(Math.round(value));
	}

}
