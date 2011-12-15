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
 * Panel para el control de la generaci�n de la historia.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class ControlArea extends JPanel {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Frame contenedor de este men�.
	 */
	private StoryJFrame frame;
	
	/**
	 * Bot�n para siguiente segmento de la historia.
	 */
	private JButton buttonNext;
    
	/**
	 * Bot�n para completar lo que reste de historia.
	 */
    private JButton buttonComplete;
    
    /**
     * Barra de progreso de generaci�n de segmentos.
     */
    private JProgressBar progressBar;
	
	/**
	 * Constructora del �rea de control.
	 * @param f Frame mediator.
	 */
	public ControlArea(StoryJFrame f) {
		super();
		frame = f;
		
		this.setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
		
		// Bot�n para procesar los votos
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
		
        // Bot�n de generaci�n de siguiente segmento
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
        
        // Bot�n de generaci�n completa
        buttonComplete = new JButton("Generaci�n completa");
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
	 * Establece el valor de la barra de progreso haciendo la divisi�n entre el valor actual
	 * y el m�ximo alcanzable por alg�n tipo de medida.
	 * @param vMin Valor actual en el progreso.
	 * @param vMax Valor m�ximo de la barra.
	 */
	public void setProgressBarValue(int vMin, int vMax) {
		float value = (new Float(vMin) / new Float(vMax)) * 100;
		progressBar.setValue(Math.round(value));
	}

}
