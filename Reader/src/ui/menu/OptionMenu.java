package ui.menu;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import reader.Reader;
import ui.StoryJFrame;
import ui.panel.treeViewer.StorySoFarTree;

/**
 * Componente para el men� Opciones de la barra de men�.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class OptionMenu extends JMenu {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Frame contenedor de este men�.
	 */
	@SuppressWarnings("unused")
	private StoryJFrame frame;
	
	/**
	 * Opci�n del men� Opciones para las preferencias.
	 */
	private JMenuItem menuItemPreferencias;
	
	/**
	 * Constructora del men� de Opciones.
	 * @param f Frame mediator.
	 */
	public OptionMenu(StoryJFrame f) {
		super();
		
		frame = f;
		
		setText("Opciones");
		
		// Opci�n de preferencias (dejarla en �ltimo lugar)
		menuItemPreferencias = new JMenuItem();
		menuItemPreferencias.setText("Preferencias");
		menuItemPreferencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	menuItemPreferenciasMouseClicked(evt);
            }
        });
        this.add(menuItemPreferencias);

	}
	
	/**
	 * Listener para abrir la ventana de opciones.
	 * @param evt
	 */
	private void menuItemPreferenciasMouseClicked(java.awt.event.MouseEvent evt) {
    	//TODO: Abrir ventana con las opciones.
    }

}

