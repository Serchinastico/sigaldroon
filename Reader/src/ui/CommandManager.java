package ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import reader.Reader;
import reader.Segment.tVote;

import ui.panel.treeViewer.StorySoFarTree;

/**
 * Clase singleton del gestor de comandos para la interfaz.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class CommandManager {
	
	/**
	 * Atributo para el patrón singleton.
	 */
	private static CommandManager cMSingleton = null;

	/**
	 * Constructora por defecto privada para el patrón singleton.
	 */
	private CommandManager() {
	}
	
	/**
	 * Devuelve la instancia según el patrón singleton de la clase CommandManager.
	 * @return
	 */
	public static CommandManager getInstance() {
		if (cMSingleton == null) {
			cMSingleton = new CommandManager();
		}
		return cMSingleton;
	}
	
	/**
	 * Inicia una historia desde archivo.
	 */
	public void initStoryFromFile(StoryJFrame frame) {
		JFileChooser fc = new JFileChooser("resources");
    	int returnVal = fc.showOpenDialog(frame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            frame.getObservableReader().reset();
            frame.getObservableReader().createMind(file.getPath());
            frame.setStoryJTree(new StorySoFarTree(frame));
            frame.getStoryJTree().loadStory(frame.getObservableReader().getSegments());
        }
	}
	
	/**
	 * Cierra la aplicación.
	 */
	public void closeApplication(StoryJFrame frame) {
		frame.dispose();
	}
	
	/**
	 * Genera el siguiente segmento de la historia.
	 */
	public void generateNextSegment(StoryJFrame frame) {
		if (frame.getObservableReader().isInitialized()) {
    		frame.setCompleteGeneration(false);
    		/*
    		// Hacemos la ejecucion del algoritmo en otro hilo de
    		// ejecucion
    		// Para que no se congele la interfaz
    		Thread t = new Thread(new Runnable() {
    			public void run() {
    				// Inicializamos la barra de progreso de nuevo
    				progressBar.setValue(0);

    				//progressBar.setMaximum(_validadorDatos
    					//				.getNumGeneraciones());
    				
    			}
    		});
    		t.start();   
    		*/
    		frame.getObservableReader().generateNextSegment();
		}
        else
        	JOptionPane.showMessageDialog(null, "La historia no ha sido inicializada. Escoge un archivo para inicializar.");
	}
	
	/**
	 * Genera lo que resta de historia.
	 */
	public void generateCompleteStory(StoryJFrame frame) {
    	if (frame.getObservableReader().isInitialized()) {
    		frame.setCompleteGeneration(true);
			frame.getObservableReader().generateStory();
		}
        else
        	JOptionPane.showMessageDialog(null, "La historia no ha sido inicializada. Escoge un archivo para inicializar.");
	}
	
	/**
	 * Vota un segmento.
	 */
	public void voteSegment(StoryJFrame frame, int segmentPosition, tVote vote) {
		frame.getObservableReader().getSegments().get(segmentPosition).setVote(vote);
	}
	
	/**
	 * Guarda el texto de la historia en un archivo de texto.
	 */
	public void exportStoryText(StoryJFrame frame) {
		JFileChooser fc = new JFileChooser();
    	int returnVal = fc.showSaveDialog(frame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            
            try {
            	file.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
                out.write(frame.getObservableReader().getTextStory());
                out.close();
            } catch (IOException e) {
            }
        }

	}
	
}
