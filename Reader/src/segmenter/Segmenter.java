package segmenter;

import mind.Mind;
import mind.ChangedMind;

/**
 * Crea segmentos a partir de cambios producidos en un mundo conceptual (World).
 * 
 * @author Sergio Guti�rrez Mota e Israel Caba�as Ruiz
 *
 */
public class Segmenter implements ISegmenter {

	@Override
	public Mind generateSegment(ChangedMind m) {
		//TODO: la generaci�n del segmento
		return m.getActualMind();
	}

	
}
