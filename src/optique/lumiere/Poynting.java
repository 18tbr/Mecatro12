package optique.lumiere;

import java.awt.Color;

import auxMaths.algLin.R3;
import auxMaths.algLin.VectUnitaire;

/**Une lumi�re anistrope dirig�e selon un vecteur.
 * Mod�le : d�croissance de l'intensit� en cos(alpha) (naturel du point de vue de la conservation de l'�nergie)
 * @author Adel
 *
 */
public class Poynting extends LumiereDirective{

	
	public Poynting(R3 directionPrivilegiee, CouleurL coul){
		super(directionPrivilegiee,coul,1);
	}
	
	public Poynting(R3 directionPrivilegiee, double i){
		this(directionPrivilegiee, new CouleurL(Color.white, i));
	}
	
	//=====================================================
	
	@Override
	public CouleurL mesurerSelon(VectUnitaire ptDeVue) {
		double facteur = ptDeVue.scal(vect);
		if (facteur >0)
			return lum.multiplieIntensite(facteur);
		else return CouleurL.noir;
	}

	@Override
	public Lumiere reflexion(VectUnitaire normale) {
		return new Poynting(vect.symetrieOrth(normale),lum);
	}
	
	//====================================================
	@Override
	public String toString() {
		return 	(lum.i==0)? "Noir" :
				"Lumiere : "+ " ( Direction: " + vect.toStringHor() + " ) \n" +
				"           " + lum;
	}
	
}
