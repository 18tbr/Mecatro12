package objets.objetPhong;

import java.awt.Color;

import auxMaths.algLin.Point3;
import auxMaths.algLin.R3;
import ihm.fenetre1.edition.entrees.Couleur;
import ihm.fenetre1.edition.entrees.Point;
import ihm.fenetre1.edition.entrees.Vecteur;
import objetmaths.surfacemaths.Degre1;
import optique.lumiere.CouleurS;

public class Plan extends Surface{

	R3 normale;
	Point3 ptPart;
	private String nom;

	public Plan(String name, R3 n, Point3 p, Color c) {
		super(name);
		normale=n;
		ptPart=p;
		listeCouleurs = new CouleurS[] {new CouleurS(c)};
		surf=new Degre1(normale,ptPart);
		majListeAttributs();
	}


	@Override
	public void majListeAttributs() {
		// TODO Auto-generated method stub
		attributs.put("Normale", new Vecteur(normale));
		attributs.put("Point part", new Point(ptPart));
		attributs.put("Couleur", new Couleur(listeCouleurs[0]));
	}
	
	@Override
	public void maj() {
		ptPart = ((Point) attributs.get("Point part")).getValue();
		normale = ((Vecteur) attributs.get("Normale")).getValue();
		listeCouleurs[0] = ((Couleur) attributs.get("Couleur")).getValueS();
		
		surf=new Degre1(normale,ptPart);
	}



	
	public Point3 getPoint() {
		return ((Degre1)surf).getPoint();
	}



	public void reset(R3 n, Point3 p, Color c) {
		((Degre1) surf).reset(n,p);
		listeCouleurs = new CouleurS[] {new CouleurS(c)};
	}


	@Override
	public String toString() {
		return String.format("Plan : { Normal = %s ; Point particulier = %s", surf.getNorm(Point3.origine),((Degre1) surf).getPoint() );
	}





}
