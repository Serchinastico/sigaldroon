package ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import ui.StoryJFrame;

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
	 * Pesta�a del men� Generaci�n.
	 */
    private JMenu menuGeneracion;
    
    /**
     * Constructora por defecto de la barra de men� principal.
     */
    public MainMenu(StoryJFrame f) {
    	super();
    	frame = f;
    	menuArchivo = new FileMenu(frame);
    	this.add(menuArchivo);
        menuGeneracion = new GenerationMenu(frame);
        this.add(menuGeneracion);
    }

    
    

    
    
}
