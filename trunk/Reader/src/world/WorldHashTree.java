package world;

/**
 * Clase que implementa un valor hash en forma de �rbol para los mundos
 * del lector. El �rbol se construye mediante una representaci�n del mundo
 * en forma de String.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class WorldHashTree {	
	
	/**
	 * Nodo inicial del �rbol. 
	 * */
	private Node root;
	
	/**
	 * Constructora por defecto.
	 * */
	public WorldHashTree() {
		root = new Node();
	}
	
	/**
	 * Constructora a partir de un String pensada para testear.
	 * @param str String de entrada.
	 * */
	public WorldHashTree(String str) {
		root = new Node();
		root.add(str);
	}
	
	/**
	 * A�ade el hash de un mundo al �rbol.
	 * @param w Mundo a agregar al �rbol.
	 * */
	public void addWorld(World w) {
		root.add(w.toShortString());
	}
	
	/**
	 * Comprueba si el mundo ha sido ya a�adido.
	 * @param w Mundo a comprobar.
	 * @return true si el mundo ya ha sido insertado.
	 * */
	public boolean exist(World w) {
		if (w == null)
			return false;
		
		return root.exist(w.toShortString());
	}
	
	/**
	 * Nodo del �rbol hash.
	 * 
	 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
	 *
	 */
	private class Node {	
		
		/**
		 * Array de nodos hijo.
		 * */
		private Node[] children;
		
		public Node() {
			children = new Node['z' - 'a' + 1];
		}
		
		/**
		 * Genera el �rbol a partir del nodo actual con el string de entrada.
		 * @param str String de entrada.
		 * */
		public void add(String str) {
			if (str.equals(""))
				return;
			
			int charPos = str.charAt(0) - 'a';
			if (children[charPos] == null) {
				children[charPos] = new Node();
			}
			
			children[charPos].add(str.substring(1));
		}
		
		/**
		 * Comprueba si el String se ha a�adido en el nodo actual.
		 * @param w String a comprobar.
		 * @return true si el String ya ha sido insertado.
		 * */
		public boolean exist(String str) {
			if (str.equals(""))
				return true;
			
			int charPos = str.charAt(0) - 'a';
			if (children[charPos] == null)
				return false;
			else
				return children[charPos].exist(str.substring(1));
		}
		
	}
}
