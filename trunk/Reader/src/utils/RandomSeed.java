package utils;

import java.util.Random;

/**
 * Esta clase sirve para guardar un �nico "semillero" de n�meros pseudo-aleatorios
 * a lo largo de la duraci�n de toda la aplicaci�n.
 * 
 * @author Israel
 *
 */
public class RandomSeed {

	/**
	 * Semillero de n�meros pseudo-aleatorios.
	 */
	private static Random randomGenerator = new Random();

	/**
	 * Genera un n�mero pseudo-aleatorio entre 0 y X-1, inclusive.
	 * @param x El rango m�ximo del n�mero pseudo-aleatorio a generar.
	 * @return El n�mero pseudo-aleatorio generado. 
	 */
	public static int generateRandom(int x) {
		return randomGenerator.nextInt(x);
	}
}
