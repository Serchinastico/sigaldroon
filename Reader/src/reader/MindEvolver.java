package reader;

import java.util.ArrayList;

import operator.Generalize;
import operator.Specialize;

import world.World;
import world.WorldChanged;

/**
 * Crea una mente nueva aplicando operadores de generalizaci�n y especializaci�n.
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class MindEvolver implements IMindEvolver {
	
	/**
	 * M�ximo n�mero de padres a engendrar.
	 */
	private int maxMindExpansions;

	/**
	 * Pila de mentes creadas hasta el momento.
	 */
	private ArrayList<WorldChanged> mindsGenerated;
	
	public MindEvolver() {
		maxMindExpansions = 10;
		mindsGenerated = new ArrayList<WorldChanged>();
	}
	
	@Override
	public WorldChanged evolveMind(World mind) {
		
		mindsGenerated.add(new WorldChanged(mind));
		
		for (int i = 0; i < maxMindExpansions; i++) {
			
			// Obtiene la mente m�s favorable de la lista (por ahora la primera)
			WorldChanged operatedMind = mindsGenerated.get(0);
			
			// Genera los hijos como resultado de operar esa mente
			ArrayList<WorldChanged> mindSons = operateMind(operatedMind);
			
			// Eval�a los hijos generados
			
			// Inserta los hijos en el total de mentes generadas y ordenados seg�n su valor
			insertWorlds(mindSons);
			
		}
		
		return mindsGenerated.get(0); // la m�s favorable seg�n su valor
	}
	
	/**
	 * Aplica los operadores a un mundo para generar todos los posibles hijos.
	 * @param w Mundo que operar.
	 * @return Los hijos generados tras aplicar operadores.
	 */
	private ArrayList<WorldChanged> operateMind(WorldChanged w) {
		
		ArrayList<WorldChanged> sons = new ArrayList<WorldChanged>();
		
		Specialize specializeOp = new Specialize();
		specializeOp.generateWorlds(w, sons);
		
		Generalize generalizeOp = new Generalize();
		generalizeOp.generateWorlds(w, sons);
		
		return sons;
	}
	
	/**
	 * Inserta los mundos en la cola de prioridad de mundos generados.
	 * @param sons Mundos a insertar.
	 */
	private void insertWorlds(ArrayList<WorldChanged> sons) {
		//TODO: implementar la inserci�n de mundos en la cola de prioridad
		for (WorldChanged w: sons) {
			mindsGenerated.add(w);
		}
	}

}
