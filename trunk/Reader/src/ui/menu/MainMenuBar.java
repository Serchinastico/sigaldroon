package ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import ui.StoryJFrame;

/**
 * Barra de menú principal de la aplicación.
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class MainMenuBar extends JMenuBar {

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
	 * Pestaña del menú Generación.
	 */
    private JMenu menuGeneracion;
    
    /**
     * Constructora por defecto de la barra de menú principal.
     */
    public MainMenuBar(StoryJFrame f) {
    	super();
    	frame = f;
    	menuArchivo = new FileMenu(frame);
    	this.add(menuArchivo);
        menuGeneracion = new GenerationMenu(frame);
        this.add(menuGeneracion);
    }

    
    

    
    
}
