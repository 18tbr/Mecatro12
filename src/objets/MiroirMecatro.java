package objets;

import objets.scene.SceneMecatro;
import optique.Photon;

public interface MiroirMecatro {
	

	
	/*On supppose que la lumi�re incidente est toujours ponctuelle et homog�ne
	 * 
	 */
	public Photon getIntensiteRecue(SceneMecatro s) ;

	
	
		
}
