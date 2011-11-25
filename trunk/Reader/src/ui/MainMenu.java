package ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import reader.Reader;

/**
 * Barra de menú principal de la aplicación.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class MainMenu extends JMenuBar {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Frame contenedor de este menú.
	 */
	private StoryJFrame frame;

	/**
	 * Pestaña del menú para opciones con archivos.
	 */
	private JMenu menuArchivo;
	
	/**
	 * Opción del menú Archivo para abrir una histora desde una archivo.
	 */
	private JMenuItem menuItemArchivo;
	
	/**
	 * Opción del menú Archivo para salir de la aplicación.
	 */
	private JMenuItem menuItemSalir;
	
	/**
	 * Pestaña del menú Generación.
	 */
    private JMenu menuGeneracion;
    
    /**
     * Opción del menú Generación para generar un segmento más de la historia.
     */
    private JMenuItem menuItemSiguiente;
    
    /**
     * Opción del menú Generación para generar la historia completa.
     */
    private JMenuItem menuItemCompleta;
    
    /**
     * Constructora por defecto de la barra de menú principal.
     */
    public MainMenu(StoryJFrame f) {
    	this.frame = f;
        initMenuArchivo();
        initMenuGeneracion();
    }
    
    /**
     * Inicializa las opciones del menú Archivo.
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
     * Inicializa las opciones del menú Generación.
     */
    private void initMenuGeneracion() {
    	menuGeneracion = new JMenu();
    	menuGeneracion.setText("Generación");
    	
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
