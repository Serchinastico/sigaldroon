package ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ui.StoryJFrame;
import ui.menu.preferences.SettingsDialog;

/**
 * Componente para el men� Opciones de la barra de men�.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class OptionsMenu extends JMenu {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Frame contenedor de este men�.
	 */
	private StoryJFrame frame;
	
	/**
	 * Opci�n del men� Opciones para las preferencias.
	 */
	private JMenuItem menuItemPreferencias;
	
	/**
	 * Constructora del men� de Opciones.
	 * @param f Frame mediator.
	 */
	public OptionsMenu(StoryJFrame f) {
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
    	SettingsDialog sD = new SettingsDialog(frame);
    	sD.setVisible(true);
    }

}

