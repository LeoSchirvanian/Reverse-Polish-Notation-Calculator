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
	
	//rajoute le string en entr�e dans l'accumulateur quand on est pas en mode d�cimal
	public void actualiserAccumulateur(String str) {
		if(modele.getAccumulateur() == BigDecimal.ZERO) {                                                      //si l'accumulateur est � 0                               
			modele.setAccumulateur(new BigDecimal(str));                                                       //l'accumulateur vaut le string en entr�e
		}
		else if(modele.getAccumulateur().compareTo(BigDecimal.ZERO) > 0) {                                     //si l'accumulateur est sup�rieur � 0                       
				BigDecimal d = new BigDecimal(str);                                                            
				modele.setAccumulateur(modele.getAccumulateur().multiply(new BigDecimal("10")).add(d));        //on fait le calcul appropri�
		}
		else{                                                                                                  //si l'accumulateur est <0           
				BigDecimal d = new BigDecimal(str);                                                            
				modele.setAccumulateur(modele.getAccumulateur().multiply(new BigDecimal("10").subtract(d)));   //on fait le calcul appropri�
		}
	}
	
	//rajoute le string en entr�e dans l'accumulateur quand on est en mode d�cimal
	public void actualiserAccumulateurDecimal(String str) {
		if(str == "0") {                                                                    //traite le probl�me des 0.00000.. pour faire 0.05 par exemple
			modele.setCompteur(modele.getCompteur()+1);
			String s = "0.";
			for (int i = 0; i < modele.getCompteur(); i++) {
				s = s.concat("0");
			}
			modele.setAccumulateur(new BigDecimal(s));
			
		}
		else {
			if(modele.getAccumulateur().compareTo(BigDecimal.ZERO) >= 0) {                  //si l'accumulateur est sup�rieur � 0   
				modele.setCompteur(modele.getCompteur()+1);
				BigDecimal d = new BigDecimal(str);
				BigDecimal bd = new BigDecimal("1");
				for(int i = 0; i<modele.getCompteur(); i++) {
					  bd = bd.divide(new BigDecimal("10"));                                 //on calcule 10^(-compteur) en BigDecimal pour avoir une tr�s grande pr�cision
					}
				modele.setAccumulateur(modele.getAccumulateur().add(d.multiply(bd)));       //on fait le calcul appropri�
			}
			else {                                                                          //si l'accumulateur est sup�rieur � 0  
				modele.setCompteur(modele.getCompteur()+1);
				BigDecimal d = new BigDecimal(str);
				BigDecimal bd = new BigDecimal("1");
				for(int i = 0; i<modele.getCompteur(); i++) {
					  bd = bd.divide(new BigDecimal("10"));                                 //on calcule 10^(-compteur) en BigDecimal pour avoir une tr�s grande pr�cision
					}
				modele.setAccumulateur(modele.getAccumulateur().subtract(d.multiply(bd)));	//on fait le calcul appropri�		
			}
		}
	}
	
	//push compteur dans operandesCompteur et push accumulateur dans operandes et r�initialise compteur, accumulateur et d�cimal
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
	
	//calcul et set l'oppos� de l'accumulateur
	public void opposeAccumulateur() {
			modele.setAccumulateur(modele.getAccumulateur().negate());
	}
	
	//passe en mode d�cimal
	public void decimal() {
		if(modele.isDecimal()) {
		}
		else {
			modele.setDecimal(true);
		}
	}
	
	//m�thode de calcul entre l'accumulateur et le premier de la pile operandes en fonction du string en entr�e et renvoie le r�sultat dans l'accumulateur
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
		case "�" :
			chiffre1 = modele.getOperandes().pop();         //premier de la pile operandes
			chiffre2 = modele.getAccumulateur();            //accumulateur
			modele.setAccumulateur(chiffre1.multiply(chiffre2));
			modele.setDecimal(false);
			break;
		case "�" :
			chiffre1 = modele.getOperandes().pop();         //premier de la pile operandes
			chiffre2 = modele.getAccumulateur();            //accumulateur
			modele.setAccumulateur(chiffre1.divide(chiffre2,10, BigDecimal.ROUND_HALF_UP));
			modele.setDecimal(false);
			break;
		default :
			break;
		}
	}
	
	//m�thode qui permet de calculer le compteur d'un r�sultat obtenu apr�s la m�thode calcul et de le set dans compteur
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
