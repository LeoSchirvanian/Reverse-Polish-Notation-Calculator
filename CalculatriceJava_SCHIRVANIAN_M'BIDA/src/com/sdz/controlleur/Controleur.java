package com.sdz.controlleur;

import java.math.BigDecimal;
import java.util.Stack;
import com.sdz.modele.*;
import com.sdz.vue.*;

public class Controleur {
	
	//ATTRIBUTS
	private Modele modele;
	
	
	//CONSTRUCTEUR
	public Controleur(){
		this.modele = new Modele();
	}
	
	//GET ET SET
	public Modele getModele() {
		return modele;
	}
	
	
	//METHODES 
	
	//rajoute le string en entrée dans l'accumulateur quand on est pas en mode décimal
	public void actualiserAccumulateur(String str) {
		if(modele.getAccumulateur() == BigDecimal.ZERO) {                                                      //si l'accumulateur est à 0                               
			modele.setAccumulateur(new BigDecimal(str));                                                       //l'accumulateur vaut le string en entrée
		}
		else if(modele.getAccumulateur().compareTo(BigDecimal.ZERO) > 0) {                                     //si l'accumulateur est supérieur à 0                       
				BigDecimal d = new BigDecimal(str);                                                            
				modele.setAccumulateur(modele.getAccumulateur().multiply(new BigDecimal("10")).add(d));        //on fait le calcul approprié
		}
		else{                                                                                                  //si l'accumulateur est <0           
				BigDecimal d = new BigDecimal(str);                                                            
				modele.setAccumulateur(modele.getAccumulateur().multiply(new BigDecimal("10").subtract(d)));   //on fait le calcul approprié
		}
	}
	
	//rajoute le string en entrée dans l'accumulateur quand on est en mode décimal
	public void actualiserAccumulateurDecimal(String str) {
		if(str == "0") {                                                                    //traite le problème des 0.00000.. pour faire 0.05 par exemple
			modele.setCompteur(modele.getCompteur()+1);
			String s = "0.";
			for (int i = 0; i < modele.getCompteur(); i++) {
				s = s.concat("0");
			}
			modele.setAccumulateur(new BigDecimal(s));
			
		}
		else {
			if(modele.getAccumulateur().compareTo(BigDecimal.ZERO) >= 0) {                  //si l'accumulateur est supérieur à 0   
				modele.setCompteur(modele.getCompteur()+1);
				BigDecimal d = new BigDecimal(str);
				BigDecimal bd = new BigDecimal("1");
				for(int i = 0; i<modele.getCompteur(); i++) {
					  bd = bd.divide(new BigDecimal("10"));                                 //on calcule 10^(-compteur) en BigDecimal pour avoir une très grande précision
					}
				modele.setAccumulateur(modele.getAccumulateur().add(d.multiply(bd)));       //on fait le calcul approprié
			}
			else {                                                                          //si l'accumulateur est supérieur à 0  
				modele.setCompteur(modele.getCompteur()+1);
				BigDecimal d = new BigDecimal(str);
				BigDecimal bd = new BigDecimal("1");
				for(int i = 0; i<modele.getCompteur(); i++) {
					  bd = bd.divide(new BigDecimal("10"));                                 //on calcule 10^(-compteur) en BigDecimal pour avoir une très grande précision
					}
				modele.setAccumulateur(modele.getAccumulateur().subtract(d.multiply(bd)));	//on fait le calcul approprié		
			}
		}
	}
	
	//push compteur dans operandesCompteur et push accumulateur dans operandes et réinitialise compteur, accumulateur et décimal
	public void pushOperandes() {
		modele.getOperandes().push(modele.getAccumulateur());
		modele.getOperandesCompteur().push(modele.getCompteur());
		modele.resetAccumulateur();
		modele.resetDecimal();
		modele.resetCompteur();
	}
	
	public void popOperandes() {
		if(modele.getAccumulateur()== BigDecimal.ZERO) {             //pop operandes et le met dans l'accumulateur et pop operandesCompteur et le met dans compteur
			modele.setAccumulateur(modele.getOperandes().pop());
			modele.setCompteur(modele.getOperandesCompteur().pop());
			modele.testDecimal();                                    //test pour concorder compteur et decimal
		}
		else {                                                       //reset compteur, decimal et accumulateur
			modele.resetAccumulateur();
			modele.setCompteur(0);
			modele.resetDecimal();
		}
	}
	
	//calcul et set l'opposé de l'accumulateur
	public void opposeAccumulateur() {
			modele.setAccumulateur(modele.getAccumulateur().negate());
	}
	
	//passe en mode décimal
	public void decimal() {
		if(modele.isDecimal()) {
		}
		else {
			modele.setDecimal(true);
		}
	}
	
	//méthode de calcul entre l'accumulateur et le premier de la pile operandes en fonction du string en entrée et renvoie le résultat dans l'accumulateur
	public void calcul(String str) {
		BigDecimal chiffre1 = BigDecimal.ZERO;
		BigDecimal chiffre2 = BigDecimal.ZERO;
		switch(str) {
		case "+" : 
			chiffre1 = modele.getOperandes().pop();         //premier de la pile operandes
			chiffre2 = modele.getAccumulateur();            //accumulateur
			modele.setAccumulateur(chiffre1.add(chiffre2));
			modele.setDecimal(false);
			break;
		case "-" :
			chiffre1 = modele.getOperandes().pop();         //premier de la pile operandes
			chiffre2 = modele.getAccumulateur();            //accumulateur
			modele.setAccumulateur(chiffre1.subtract(chiffre2));
			modele.setDecimal(false);
			break;
		case "×" :
			chiffre1 = modele.getOperandes().pop();         //premier de la pile operandes
			chiffre2 = modele.getAccumulateur();            //accumulateur
			modele.setAccumulateur(chiffre1.multiply(chiffre2));
			modele.setDecimal(false);
			break;
		case "÷" :
			chiffre1 = modele.getOperandes().pop();         //premier de la pile operandes
			chiffre2 = modele.getAccumulateur();            //accumulateur
			modele.setAccumulateur(chiffre1.divide(chiffre2,10, BigDecimal.ROUND_HALF_UP));
			modele.setDecimal(false);
			break;
		default :
			break;
		}
	}
	
	//méthode qui permet de calculer le compteur d'un résultat obtenu après la méthode calcul et de le set dans compteur
	public void compteurResult() {
		modele.getOperandesCompteur().pop();
		String s = modele.getAccumulateur().toString();
		int i = s.indexOf(".");
		if(i>0) {
			int t = s.substring(i,s.length()-1).length();
			modele.setCompteur(t);
		}
		modele.testDecimal();      //si le compteur vaut 0, on synchronise decimal sur false sinon on synchronise sur true
	}
}
