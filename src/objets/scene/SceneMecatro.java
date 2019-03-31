package objets.scene;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import algLin.Point3;
import algLin.R3;
import objets.MiroirMecatro;
import optique.CouleurL;
import optique.Photon;
import optique.Source;
import optique.SourcePonctuelleIsotrope;

/** La sceneMecatro est une sc�ne de base dans laquelle il n'y a qu'une unique source, plac�e en l'origine et de couleur donn�e.
 * La sc�ne contient une cat�gorie particuli�re d'objets : les miroirMecatros 
 * @author Adel
 *
 */
public class SceneMecatro extends SceneSansSources {
	
	public List<MiroirMecatro> listeMiroirs;
	protected CouleurL lumSource;
	private Point3 source;
	
	
	public SceneMecatro(CouleurL lumiere) {
		super();
		listeMiroirs=new ArrayList<MiroirMecatro>();
		
		source = Point3.origine.plus(R3.uy);
		lumSource=lumiere;
		}
	
	
	public SceneMecatro() {
		this(new CouleurL(Color.WHITE, 10));
		}
	
	
	//=============================================
	//Getters

	@Override
	public Source[] getSources() {
		return new Source[] {new SourcePonctuelleIsotrope(source, lumSource)};
	}
	
	
	
	//===========================================
	// Modification de la scene
	
	@Override
	public void ajouter(Objet o) {
		try {
			super.ajouter(o);
		}
		catch(TypeObjetPasTraiteException e) {
			throw new IllegalArgumentException("Erreur dans l'ajout de l'objet : type non reconnu.");
		}
		
		if (o instanceof MiroirMecatro)
			listeMiroirs.add((MiroirMecatro)o);
	}
		
	
	
	@Override
	public void supprimer(Objet o) {
		try {
			super.supprimer(o);
		}
		catch(TypeObjetPasTraiteException e) {
			throw new IllegalArgumentException("Erreur dans l'ajout de l'objet : type non reconnu.");
		}
		
		if (o instanceof MiroirMecatro)
			listeMiroirs.remove((MiroirMecatro)o);

	}
	
	//===============================================
	//Algorithme
	
	/**Renvoie la somme des contributions de chaque miroir par rapport � la source
	 * (Remarque : l'influence directe de la source est eclips�e)
	 * 
	 */
	@Override
	public List<Photon> getLumieresEn(Point3 p) {
		List<Photon> result = new ArrayList<Photon>();
		for (MiroirMecatro m : listeMiroirs)
			result.add(m.getIntensiteRecue(this));
		return result;
	}
}
