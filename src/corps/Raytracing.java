package corps;
import java.awt.Button;
import java.awt.Color; 
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import algLin.M3;
import algLin.Point3;
import algLin.R3;
import ihm.util.EcouteurPourFermetureFenetre;
import objets.ObjetRaytracing;
import objets.objetPhong.Horizon;
import objets.objetPhong.Plan;
import objets.objetPhong.Sphere;
import objets.scene.ObjetDansScene;
import objets.scene.SceneRaytracing;
import optique.CouleurL;
import optique.Source;
import optique.SourcePonctuelleIsotrope;

public class Raytracing extends GenerateurImage{
  
  //=============================================
  //Attributs
  //=============================================
  
  // Propriétés fondamentales et nécessaires
  

  protected ParametresRaytracing param;
  protected SceneRaytracing sc;
  


  
  
  //=====================================
  //Constructeur: définit les attributs par défaut
  public Raytracing() {
    sc=new SceneRaytracing();
    param=new ParametresRaytracing();
    intBlanc=0;
    imageBase=null;
  }
  
  public Raytracing(SceneRaytracing s) {
    sc=s;
    param=new ParametresRaytracing();
    intBlanc=0;
    imageBase=null;
  }

  public Raytracing(SceneRaytracing s, ParametresRaytracing p) {
    sc=s;
    param=p;
    intBlanc=0;
    imageBase=null;
  }
  
  public ParametresRaytracing getParam() {
    return param;
  }
  
  public void setParam( ParametresRaytracing par) {
    param=par;
  }
  
  public SceneRaytracing getScene() {
    return sc;
  }
  


  //=================================================
  //Fonctions auxilliaires du programme
	/**
	 * Ajoute un objet à la scène
	 * 
	 * @param o
	 */
	public void ajouter(ObjetDansScene o) {
		sc.ajouter(o);
	}

	/**
	 * Supprimer un objet de la scène
	 * 
	 */
	public void supprimer(ObjetDansScene o) {
		sc.supprimer(o);
	}
  

  /**Renvoie le tableau de couleur qui correspond à l'image rendue
   * 
   * @return
   */
  public CouleurL[][] getTab() {
    CouleurL[][] result = new CouleurL[param.getLargpx()][param.getHautpx()];
    AlgoRaytracing alg;
    CouleurL lum;
    int l = param.getLargpx();
    int h = param.getHautpx();
    for (int i=0;i<l;i++) {
      if (i%10==0)
        System.out.printf("%d%% %n", (int) 100*i/l);
      for (int j=0;j<h;j++) {
        alg = new AlgoRaytracing(this,i,j);
        result[i][j]= lum = alg.getCouleurPixel();
        if (intBlanc < lum.getIntensite())
          intBlanc= lum.getIntensite();
      }
    }
    imageBase=result;
    return result;
    }
  
	// =================================================
	// Fonctions auxilliaires du programme

	public void modifBlanc(double d, double dMax) {
		intBlanc = intBlanc * (1 + d / dMax);
	}

	

/*	*//**
	 * S'adapte à la luminosité globale de la scène pour rendre l'image finale
	 * 
	 * @param premImage
	 * @return
	 *//*
	public BufferedImage getPic(CouleurL[][] premImage, double iBlanc) {
		BufferedImage off_Image = new BufferedImage(param.getLargpx(), param.getHautpx(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = off_Image.createGraphics();
		for (int i = 0; i < param.getLargpx(); i++) {
			for (int j = 0; j < param.getHautpx(); j++) {
				g2.setColor(premImage[i][j]
						.appliquerIntGlobale(iBlanc));
														 * if (premImage[i][j].appliquerIntGlobale(iBlanc).getBlue() <25
														 * && premImage[i][j].appliquerIntGlobale(iBlanc).getRed() <25)
														 * System.out.print("");
														 
				g2.fillRect(i, j, 1, 1);
			}
		}
		return off_Image;
	}*/

	// ====================================
	// Programme principal

	/**
	 * Renvoie l'image de la scène correspondant au point de vue choisi
	 * 
	 * @return
	 */
	public BufferedImage mainProgram() {
		long startTime = System.nanoTime();

		CouleurL[][] premImage = getTab();

		BufferedImage off_Image = getPic(premImage, intBlanc);
		try {
			File outputfile = new File("saved.png");
			ImageIO.write(off_Image, "png", outputfile);
		} catch (IOException e) {
		}
		System.out.println((System.nanoTime() - startTime) / 1000000 + "ms de lecture");
		return off_Image;
	}
  //=====================================================================
  //Version pr�visualisation
 
  /**Donne le tableau de couleurs pour la prévisualisation
   * 
   * @return
   */
  public CouleurL[][] getTabSimple() {
    CouleurL[][] result = new CouleurL[param.getLargpx()][param.getHautpx()];
    AlgoSimple alg;
    CouleurL lum;
    int l = param.getLargpx();
    int h = param.getHautpx();
    for (int i=0;i<l;i++) {
      if (i%10==0)
        System.out.printf("%d%% %n", (int) 100*i/l);
      for (int j=0;j<h;j++) {
        alg = new AlgoSimple(this,i,j);
        result[i][j]= lum = alg.getCouleurPixel();
        if (intBlanc < lum.getIntensite())
          intBlanc= lum.getIntensite();
      }
    }
    return result;
    }
  
  
  
  
  
  /**Renvoie une image simplifiée de la scène, en guise de prévisualisation rapide à créée
   * 
   * @return
   */
  public BufferedImage mainProgramSimple() {
    long startTime= System.nanoTime();
    CouleurL[][] premImage=getTabSimple();
    BufferedImage off_Image=getPic(premImage, intBlanc);
    R3 v;
    int cotesur2=5;
    for ( Source o : sc.listesources) {
      v=param.oeil.Vecteur(o.getPoint()).normer().prod(param.ecart);
      v= param.base.transpose().fois(v);
      off_Image.getGraphics().drawOval((int) (v.get1()/param.ratio+ param.getLargpx()/2 -cotesur2), (int) (-v.get2()/param.ratio - cotesur2 + param.getHautpx()/2), 2*cotesur2, 2*cotesur2);
    }
    
    System.out.println((System.nanoTime()-startTime)/1000000 + "ms de lecture");
    return off_Image;
}
  
  

  
  
  
  
  public static void main(String[] args) {
    Raytracing r = new Raytracing();
  }


  
  



  
}