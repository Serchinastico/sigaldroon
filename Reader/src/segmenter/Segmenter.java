package segmenter;

import world.World;
import world.WorldChanged;

/**
 * Crea segmentos a partir de cambios producidos en un mundo conceptual (World).
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class Segmenter implements ISegmenter {

	@Override
	public World generateSegment(WorldChanged m) {
		//TODO: la generaci�n del segmento
		return m.getActualMind();
	}

	
}
