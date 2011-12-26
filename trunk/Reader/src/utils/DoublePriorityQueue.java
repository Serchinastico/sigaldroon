package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class DoublePriorityQueue<E> {

	private PriorityQueue<E> gtToLt;
	
	private PriorityQueue<E> ltToGt;
	
	public DoublePriorityQueue() {
		gtToLt = new PriorityQueue<E>(100);
		ltToGt = new PriorityQueue<E>(100);
	}
	
	public DoublePriorityQueue(Comparator<E> greater, Comparator<E> lower) {
		gtToLt = new PriorityQueue<E>(100, greater);
		ltToGt = new PriorityQueue<E>(100, lower);
	}
	
	/**
	 * Obtiene los 3 mejores elementos y los 3 peores elementos.
	 * @return
	 */
	public ArrayList<E> getSelectedMinds() {
		ArrayList<E> selectedElems = new ArrayList<E>();
		
		int i = 0;
		while ((i < 3) && (!gtToLt.isEmpty())) {
			selectedElems.add(pollGreater());
			i++;
		}
		
		int j = 0;
		while ((j < 3) && (!ltToGt.isEmpty())) {
			selectedElems.add(pollLower());
			j++;
		}
		
		return selectedElems;
	}
	
	/**
	 * Añade un elemento.
	 * @param elem
	 */
	public void add(E elem) {
		gtToLt.add(elem);
		ltToGt.add(elem);
	}
	
	/**
	 * Obtiene el mejor elemento.
	 * @return
	 */
	public E peek() {
		return gtToLt.peek();
	}
	
	/**
	 * Obtiene el mejor elemento y lo quita.
	 * @return
	 */
	public E pollGreater() {
		E top = gtToLt.poll();
		ltToGt.remove(top);
		return top;
	}
	
	/**
	 * Obtiene el mejor elemento y lo quita.
	 * @return
	 */
	public E pollLower() {
		E bot = ltToGt.poll();
		gtToLt.remove(bot);
		return bot;
	}
	
}
