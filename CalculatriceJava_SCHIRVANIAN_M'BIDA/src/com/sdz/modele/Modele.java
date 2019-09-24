package com.sdz.modele;

import java.math.BigDecimal;
import java.util.Stack;
import com.sdz.vue.*;
import com.sdz.controlleur.*;

public class Modele {
	
	
	//ATTRIBUTS
	
	private Stack<BigDecimal> operandes = new Stack<BigDecimal>();     //pile des opérandes de type BigDecimal (type qui traite les nombres flottants infinis)
	private BigDecimal accumulateur;                                   //l'accumulateur, ce qui sera mis en relation avec la console par le controleur
	private Stack<Integer> operandesCompteur = new Stack<Integer>();   //pile des compteurs des operandes 
	private int compteur;                                              //correspond aux "nombres après la virgule"
	private boolean decimal;                                           //permet de savoir si on est en mode décimal ou non
	
	
	//CONTRUSCTEUR
	
	public Modele(){
		this.operandes = operandes;
		this.operandesCompteur = operandesCompteur;
		this.accumulateur = BigDecimal.ZERO;                             //on initialise l'accumulateur à 0
		this.compteur = 0;                                               //on initialise le compteur  0
		this.decimal = false;                                            // on est de base en mode non décimal
	}
	
	
	//METHODES
	
	//méthode qui intervertit le premier élément de la pile operandes et l'accumulateur ainsi que le compteur et le premier élément de la pile operandesCompteur
	public void swap() {                      
		BigDecimal chiffre1 = operandes.pop();
		operandes.push(accumulateur);
		accumulateur = chiffre1;
		int chiffre2 = operandesCompteur.pop();
		operandesCompteur.push(compteur);
		compteur = chiffre2;
		testDecimal();
	}
	
	//reset la pile operandes
	public void resetOperandes() {
		operandes.clear();
	}
	
	//reset la pile operandesCompteur
	public void resetOperandesCompteur() {
		operandesCompteur.clear();
	}
	
	//resetAccumulateur
	public void resetAccumulateur() {
		accumulateur = BigDecimal.ZERO;
	}
	
	//reset le boolean decimal à false
	public void resetDecimal() {
		decimal = false;
	}
	
	public void resetCompteur() {
		compteur = 0;
	}
	
	//vérifie la concordance entre la valeur de compteur et le boolean decimal
	public void testDecimal() {
		if(compteur == 0) {      // si compteur = 0, on est évidemment pas en mode décimal
			setDecimal(false);
		}
		else {                   //sinon on l'est
			setDecimal(true);
		}
	}
	
	//on reset tous les attributs
	public void reset() {
		resetOperandes();
		resetAccumulateur();
		resetOperandesCompteur();
		resetCompteur();
		resetDecimal();
	}
	
	
	//GET ET SET
	public Stack<BigDecimal> getOperandes() {
		return this.operandes;
	}

	public BigDecimal getAccumulateur() {
		return this.accumulateur;
	}

	public void setAccumulateur(BigDecimal accumulateur) {
		this.accumulateur = accumulateur;
	}

	public boolean isDecimal() {
		return decimal;
	}

	public void setDecimal(boolean decimal) {
		this.decimal = decimal;
	}

	public Stack<Integer> getOperandesCompteur() {
		return operandesCompteur;
	}

	public int getCompteur() {
		return compteur;
	}

	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}
	
}
