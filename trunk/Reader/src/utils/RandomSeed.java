package utils;

import java.util.Random;

/**
 * Esta clase sirve para guardar un único "semillero" de números pseudo-aleatorios
 * a lo largo de la duración de toda la aplicación.
 * 
 * @author Israel
 *
 */
public class RandomSeed {

	/**
	 * Semillero de números pseudo-aleatorios.
	 */
	private static Random randomGenerator = new Random();

	/**
	 * Genera un número pseudo-aleatorio entre 0 y X-1, inclusive.
	 * @param x El rango máximo del número pseudo-aleatorio a generar.
	 * @return El número pseudo-aleatorio generado. 
	 */
	public static int generateRandom(int x) {
		return randomGenerator.nextInt(x);
	}
}
