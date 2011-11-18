package segmenter;

import world.World;
import world.WorldChanged;

/**
 * Crea segmentos a partir de cambios producidos en un mundo conceptual (World).
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Segmenter implements ISegmenter {

	@Override
	public World generateSegment(WorldChanged m) {
		//TODO: la generación del segmento
		return m.getActualMind();
	}

	
}
