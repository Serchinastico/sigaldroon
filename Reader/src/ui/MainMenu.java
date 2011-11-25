package ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import reader.Reader;

/**
 * Barra de men� principal de la aplicaci�n.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class MainMenu extends JMenuBar {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Frame contenedor de este men�.
	 */
	private StoryJFrame frame;

	/**
	 * Pesta�a del men� para opciones con archivos.
	 */
	private JMenu menuArchivo;
	
	/**
	 * Opci�n del men� Archivo para abrir una histora desde una archivo.
	 */
	private JMenuItem menuItemArchivo;
	
	/**
	 * Opci�n del men� Archivo para salir de la aplicaci�n.
	 */
	private JMenuItem menuItemSalir;
	
	/**
	 * Pesta�a del men� Generaci�n.
	 */
    private JMenu menuGeneracion;
    
    /**
     * Opci�n del men� Generaci�n para generar un segmento m�s de la historia.
     */
    private JMenuItem menuItemSiguiente;
    
    /**
     * Opci�n del men� Generaci�n para generar la historia completa.
     */
    private JMenuItem menuItemCompleta;
    
    /**
     * Constructora por defecto de la barra de men� principal.
     */
    public MainMenu(StoryJFrame f) {
    	this.frame = f;
        initMenuArchivo();
        initMenuGeneracion();
    }
    
    /**
     * Inicializa las opciones del men� Archivo.
     */
    private void initMenuArchivo() {
    	menuArchivo = new JMenu();
    	menuArchivo.setText("Archivo");
    	
    	menuItemArchivo = new JMenuItem();
        menuItemArchivo.setText("Comenzar desde archivo");
        menuItemArchivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuItemArchivoMouseClicked(evt);
            }
        });
        menuArchivo.add(menuItemArchivo);

        menuItemSalir = new JMenuItem();
        menuItemSalir.setText("Salir");
        menuItemSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuItemSalirMouseClicked(evt);
            }
        });
        menuArchivo.add(menuItemSalir);
        
        this.add(menuArchivo);
    }
    
    /**
     * Inicializa las opciones del men� Generaci�n.
     */
    private void initMenuGeneracion() {
    	menuGeneracion = new JMenu();
    	menuGeneracion.setText("Generaci�n");
    	
        menuItemSiguiente = new JMenuItem();
        menuItemSiguiente.setText("Siguiente segmento");
        menuItemSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuItemSiguienteMouseClicked(evt);
            }
        });
        menuGeneracion.add(menuItemSiguiente);

        menuItemCompleta = new JMenuItem();
        menuItemCompleta.setText("Completa");
        menuItemCompleta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuItemCompletaMouseClicked(evt);
            }
        });
        menuGeneracion.add(menuItemCompleta);
        
        this.add(menuGeneracion);
    }
    
    private void menuItemSiguienteMouseClicked(java.awt.event.MouseEvent evt) {
    	if ((frame.getObservableReader() != null) && frame.getObservableReader().isInitialized()) {
    		frame.setStoryJTree(new StorySoFarTree(frame));
            frame.getStoryJTree().insertStory(frame.getObservableReader().generateNextSegment());
	        frame.getStoryJTree().reload();
    	}
        else {
        	JOptionPane.showMessageDialog(null, "La historia no ha sido inicializada. Escoge un archivo para inicializar.");
        }
    }
    
    private void menuItemCompletaMouseClicked(java.awt.event.MouseEvent evt) {
        if ((frame.getObservableReader() != null) && frame.getObservableReader().isInitialized()) {
        	frame.setStoryJTree(new StorySoFarTree(frame));
        	frame.getStoryJTree().insertStory(frame.getObservableReader().generateStory());
	        frame.getStoryJTree().reload();
        }
        else {
        	JOptionPane.showMessageDialog(null, "La historia no ha sido inicializada. Escoge un archivo para inicializar.");
        }
    }

    private void menuItemArchivoMouseClicked(java.awt.event.MouseEvent evt) {
    	JFileChooser fc = new JFileChooser();
    	int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            frame.setObservableReader(new Reader());
            frame.getObservableReader().addObserver(frame);
            frame.getObservableReader().createMind(file.getPath());
            frame.setStoryJTree(new StorySoFarTree(frame));
            frame.getStoryJTree().insertStory(frame.getObservableReader().getStorySoFar());
            frame.getStoryJTree().reload();
        }
    }

    private void menuItemSalirMouseClicked(java.awt.event.MouseEvent evt) {
    	frame.dispose();
    }
    
}
