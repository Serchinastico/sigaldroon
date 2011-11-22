package segmenter;

import mind.Mind;
import mind.ChangedMind;

/**
 * Crea segmentos a partir de cambios producidos en un mundo conceptual (World).
 * 
 * @author Sergio Gutiérrez Mota e Israel Cabañas Ruiz
 *
 */
public class Segmenter implements ISegmenter {

	@Override
	public Mind generateSegment(ChangedMind m) {
		//TODO: la generación del segmento
		return m.getActualMind();
	}

	
}
