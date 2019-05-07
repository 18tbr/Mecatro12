package objets;

import java.util.List;

import objets.scene.SceneMecatro;
import optique.Eclairage;
import optique.Source;

public interface MiroirMecatro {
	

	
	/**Renvoie la contribution du miroir � l'�clairage sous la forme de sources virtuelles qui s'ajoutent � la liste entr�e.
	 * 
	 * 
	 */
	public void addSourcesVirtuelles(List<Eclairage> listeSourcesVirtuelles, SceneMecatro s) ;

	
	
	
		
}
