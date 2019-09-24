package com.sdz.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.*;
import java.util.Stack;
import com.sdz.modele.*;
import com.sdz.controlleur.*;

public class Calculette extends JFrame {
	
	//ATTRIBUTS
	JPanel fenetrePrincipale = new JPanel();         //fenetre principale
	private JLabel console1 = new JLabel();          //une des 4 consoles, correspond au 4ème élément de la pile operandes si il y en a un
	private JLabel console2 = new JLabel();          //une des 4 consoles, correspond au 3ème élément de la pile operandes si il y en a un
	private JLabel console3 = new JLabel();          //une des 4 consoles, correspond au 2ème élément de la pile operandes si il y en a un
	private JLabel console4 = new JLabel();          //une des 4 consoles, correspond au 1er élément de la pile operandes si il y en a un
	private JLabel cache = new JLabel();             //correspond à l'affichage de l'accumulateur
	private Controleur controleur;
	
	public Calculette() {
		this.setSize(470,750);                        //taille de la calculette
		this.setTitle("Calculette TP");               //titre de la fenêtre
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //ferme la fenêtre
		this.setResizable(false);                     //dimension fixe
		initialisationCalculette();                   //méthode qui initialise l'agencement graphique de la calculette (place des boutons, dimension, police,...)
		this.setContentPane(fenetrePrincipale);       //définit le conteneur
		this.setVisible(true);                        //affiche
		this.controleur = new Controleur();           //permet de communiquer avec un controleur
	}
	
	private void initialisationCalculette() {
	    
		Font police = new Font("Arial", Font.BOLD, 40);      //définit la police pour la console
		console1 = new JLabel("");                           //on initialise la console à 0
	    console1.setPreferredSize(new Dimension(440, 50));   //taille console
	    console1.setHorizontalAlignment(JLabel.RIGHT);       //place l'écriture de la console à droite
	    console1.setFont(police);
		console2 = new JLabel("");                           //on initialise la console à 0
	    console2.setPreferredSize(new Dimension(440, 50));   //taille console
	    console2.setHorizontalAlignment(JLabel.RIGHT);       //place l'écriture de la console à droite
	    console2.setFont(police);                            
		console3 = new JLabel("");                           //on initialise la console à 0
	    console3.setPreferredSize(new Dimension(440, 50));   //taille console
	    console3.setHorizontalAlignment(JLabel.RIGHT);       //place l'écriture de la console à droite
	    console3.setFont(police);                            
		console4= new JLabel("");                            //on initialise la console à 0
	    console4.setPreferredSize(new Dimension(440, 50));   //taille console
	    console4.setHorizontalAlignment(JLabel.RIGHT);       //place l'écriture de la console à droite
	    console4.setFont(police);                            
	    cache = new JLabel("0.0");                           //on initialise la console à 0
	    cache.setPreferredSize(new Dimension(440, 50));      //taille console
	    cache.setHorizontalAlignment(JLabel.RIGHT);          //place l'écriture de la console à droite
	    cache.setFont(police);                            
	    JPanel conteneurOperateur = new JPanel();                          //conteneur des opérateurs
		JPanel conteneurChiffre = new JPanel();                            //conteneur des chiffres
		JPanel conteneurConsole = new JPanel();                            //conteneur de la console
		conteneurOperateur.setPreferredSize(new Dimension(110,550));       //dimension du bloc operateur
		conteneurChiffre.setPreferredSize(new Dimension(330,550));         //dimension du bloc chiffre
		conteneurConsole.setPreferredSize(new Dimension(440,280));         //dimension de la console
		
		//definition des boutons
		JButton bouton1 = new JButton("1");
		JButton bouton2 = new JButton("2");
		JButton bouton3 = new JButton("3");
		JButton bouton4 = new JButton("4");
		JButton bouton5 = new JButton("5");
		JButton bouton6 = new JButton("6");
		JButton bouton7 = new JButton("7");
		JButton bouton8 = new JButton("8");
		JButton bouton9 = new JButton("9");
		JButton boutonPoint = new JButton(",");     //le bouton virgule
		JButton bouton0= new JButton("0");
		JButton boutonReset = new JButton("C");     //le bouton reset
		JButton boutonSwap = new JButton("↔");      //le bouton swap
		JButton boutonPlus = new JButton("+");      //le bouton addition
		JButton boutonMoins = new JButton("-");     //le bouton soustraction
		JButton boutonMult = new JButton("×");      //le bouton multiplication
		JButton boutonDiv = new JButton("÷");       //le bouton division
		JButton boutonPush = new JButton("→");      //le bouton push
		JButton boutonPop = new JButton("←");       //le bouton retour
		JButton boutonPlusMoins = new JButton("±"); //le bouton plus ou moins
		
		//répartition par bloc des boutons
		conteneurChiffre.add(boutonReset);
		conteneurChiffre.add(boutonSwap);
		conteneurChiffre.add(boutonPop);
		conteneurChiffre.add(bouton1);
		conteneurChiffre.add(bouton2);
		conteneurChiffre.add(bouton3);
		conteneurChiffre.add(bouton4);
		conteneurChiffre.add(bouton5);
		conteneurChiffre.add(bouton6);
		conteneurChiffre.add(bouton7);
		conteneurChiffre.add(bouton8);
		conteneurChiffre.add(bouton9);
		conteneurChiffre.add(boutonPlusMoins);
		conteneurChiffre.add(bouton0);
		conteneurChiffre.add(boutonPoint);
		conteneurOperateur.add(boutonPlus);
		conteneurOperateur.add(boutonMoins);
		conteneurOperateur.add(boutonMult);
		conteneurOperateur.add(boutonDiv);
		conteneurOperateur.add(boutonPush);
		
		//taille des boutons
		boutonReset.setPreferredSize(new Dimension(100,80));
		boutonSwap.setPreferredSize(new Dimension(100,80));
		boutonPop.setPreferredSize(new Dimension(100,80));
		bouton1.setPreferredSize(new Dimension(100,80));
		bouton2.setPreferredSize(new Dimension(100,80));
		bouton3.setPreferredSize(new Dimension(100,80));
		bouton4.setPreferredSize(new Dimension(100,80));
		bouton5.setPreferredSize(new Dimension(100,80));
		bouton6.setPreferredSize(new Dimension(100,80));
		bouton7.setPreferredSize(new Dimension(100,80));
		bouton8.setPreferredSize(new Dimension(100,80));
		bouton9.setPreferredSize(new Dimension(100,80));
		boutonPoint.setPreferredSize(new Dimension(100,80));
		bouton0.setPreferredSize(new Dimension(100,80));
		boutonPlusMoins.setPreferredSize(new Dimension(100,80));
		boutonPush.setPreferredSize(new Dimension(100,80));
		boutonPlus.setPreferredSize(new Dimension(100,80));
		boutonMoins.setPreferredSize(new Dimension(100,80));
		boutonMult.setPreferredSize(new Dimension(100,80));
		boutonDiv.setPreferredSize(new Dimension(100,80));
		
		//police et taille des boutons
		Font police1 = new Font("Arial", Font.BOLD, 30);
		bouton1.setFont(police1);
		bouton2.setFont(police1);
		bouton3.setFont(police1);
		bouton4.setFont(police1);
		bouton5.setFont(police1);
		bouton6.setFont(police1);
		bouton7.setFont(police1);
		bouton8.setFont(police1);
		bouton9.setFont(police1);
		bouton0.setFont(police1);
		boutonPoint.setFont(police1);
		boutonPlusMoins.setFont(police1);		
		boutonPlus.setFont(police1);
		boutonMoins.setFont(police1);
		boutonMult.setFont(police1);
		boutonDiv.setFont(police1);
		boutonPoint.setFont(police1);
		boutonReset.setFont(police1);
		boutonReset.setForeground(Color.RED);
		boutonSwap.setFont(police1);
		Font police2 = new Font("Arial", Font.BOLD, 50);
		boutonPush.setFont(police2);
		boutonPush.setForeground(Color.BLUE);
		Font police3 = new Font("Arial", Font.BOLD, 40);
		boutonPop.setFont(police3);
		
		//definition des actions des boutons
		
		bouton1.addActionListener(new Chiffre());           //on définira la classe interne Chiffre plus loin dans le programme
		bouton2.addActionListener(new Chiffre());    
		bouton3.addActionListener(new Chiffre());
		bouton4.addActionListener(new Chiffre());
		bouton5.addActionListener(new Chiffre());
		bouton6.addActionListener(new Chiffre());
		bouton7.addActionListener(new Chiffre());
		bouton8.addActionListener(new Chiffre());
		bouton9.addActionListener(new Chiffre());
		bouton0.addActionListener(new Chiffre());
		boutonPoint.addActionListener(new Point());         //on définira la classe interne Point plus loin dans le programme
		boutonReset.addActionListener(new Reset());         //on définira la classe interne Reset plus loin dans le programme
		boutonSwap.addActionListener(new Swap());           //on définira la classe interne Swap plus loin dans le programme
		boutonPop.addActionListener(new Pop());             //on définira la classe interne Pop plus loin dans le programme
		boutonPlus.addActionListener(new Operateur());      //on définira la classe interne Operateur plus loin dans le programme  
		boutonMoins.addActionListener(new Operateur());    
		boutonMult.addActionListener(new Operateur());   
		boutonDiv.addActionListener(new Operateur());          
		boutonPush.addActionListener(new Push());           //on définira la classe interne Push plus loin dans le programme
		boutonPlusMoins.addActionListener(new PlusMoins()); //on définira la classe interne PlusMoins plus loin dans le programme
		
		
		//on finit la méthode en organisant les fenêtres dans le conteneur
		conteneurConsole.add(console4);                                                    //on associe l'étiquette "console4" au conteneur "conteneurConsole"
		conteneurConsole.add(console3);                                                    //on associe l'étiquette "console3" au conteneur "conteneurConsole"
		conteneurConsole.add(console2);                                                    //on associe l'étiquette "console2" au conteneur "conteneurConsole"
		conteneurConsole.add(console1);                                                    //on associe l'étiquette "console1" au conteneur "conteneurConsole"
		conteneurConsole.add(cache);                                                       //on associe l'étiquette "cache" au conteneur "conteneurConsole"
		console1.setBorder(BorderFactory.createLineBorder(Color.black));
		console2.setBorder(BorderFactory.createLineBorder(Color.black));
		console3.setBorder(BorderFactory.createLineBorder(Color.black));
		console4.setBorder(BorderFactory.createLineBorder(Color.black));
		cache.setBorder(BorderFactory.createLineBorder(Color.black));
		fenetrePrincipale.add(conteneurConsole, BorderLayout.NORTH);                      //on place la console en haut de la fenêtre
	    fenetrePrincipale.add(conteneurChiffre, BorderLayout.CENTER);                     //on place les chiffres au centre de la fenêtre
	    fenetrePrincipale.add(conteneurOperateur, BorderLayout.EAST);                     //on place les opérateurs sur le côté droit de la fenêtre
	}
	
	//METHODES PRATIQUES
	
	//faire correspondre les différentes consoles avec leur valeur associée dans operandes si il existe ou non
	private void refreshConsole() {
		int taillePile = controleur.getModele().getOperandes().size();
		switch(controleur.getModele().getOperandes().size()) {
		case 0:
			console1.setText("");
			console2.setText("");
			console3.setText("");
			console4.setText("");
			break;
		case 1:
			console1.setText(controleur.getModele().getOperandes().get(taillePile-1).toString());
			console2.setText("");
			console3.setText("");
			console4.setText("");
			
			break;
		case 2:
			console1.setText(controleur.getModele().getOperandes().get(taillePile-1).toString());
			console2.setText(controleur.getModele().getOperandes().get(taillePile-2).toString());
			console3.setText("");
			console4.setText("");
			break;
		case 3:
			console1.setText(controleur.getModele().getOperandes().get(taillePile-1).toString());
			console2.setText(controleur.getModele().getOperandes().get(taillePile-2).toString());
			console3.setText(controleur.getModele().getOperandes().get(taillePile-3).toString());
			console4.setText("");
			break;
		default:
			console1.setText(controleur.getModele().getOperandes().get(taillePile-1).toString());
			console2.setText(controleur.getModele().getOperandes().get(taillePile-2).toString());
			console3.setText(controleur.getModele().getOperandes().get(taillePile-3).toString());
			console4.setText(controleur.getModele().getOperandes().get(taillePile-4).toString());
			break;
		}
	}
	
	//synchonise le cache avec l'accumulateur
	private void refreshCache() {
		cache.setText(String.valueOf(controleur.getModele().getAccumulateur()));
	}
	
	//synchonise le cache avec le string en entrée
	private void refreshCache(String s) {
		cache.setText(s);
	}
	
	//ACTIONLISTENER
	private class Chiffre implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String str = ((JButton)e.getSource()).getText();
			if(controleur.getModele().isDecimal()) {
				if(str == "0") {
					controleur.actualiserAccumulateurDecimal(str);
					refreshCache();
				}
				else {
					controleur.actualiserAccumulateurDecimal(str);
					refreshCache();
				}
			}
			else {
				controleur.actualiserAccumulateur(str);
				refreshCache();
			}
		}
	}
	
	//méthode qui s'active quand on appuie sur un bouton opérateur
	private class Operateur implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String str = ((JButton)e.getSource()).getText();        //copie le string du bouton cliqué par l'utilisateur
			if(controleur.getModele().getOperandes().size() != 0) { //vérifie qu'il y a au moins 2 opérandes pour lancer le calcul
				controleur.calcul(str);                             //on effectue la méthode de calcul avec l'operateur copié précédemment dans un string
				controleur.compteurResult();                        //permet de trouver le compteur du résultat obtenu et de l'implémenter dans compteur
				refreshConsole();                                       
				refreshCache();
			}
		}
	}
	
	//reset
	private class Reset implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			controleur.getModele().reset();
			refreshConsole();
			refreshCache();
		}
	}
	
	//passe en mode décimal
	private class Point implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			controleur.decimal();
			refreshCache();
		}
	}
	
	//swap le cache et console1
	private class Swap implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(controleur.getModele().getOperandes().size()!=0) {
				controleur.getModele().swap();
				refreshConsole();
				refreshCache();
			}
		}
	}
	
	//reset l'accumulateur ou pop operandes
	private class Pop implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			controleur.popOperandes();
			refreshConsole();
			refreshCache();
		}
	}
	
	//push operandes et actualise les consoles
	private class Push implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			controleur.pushOperandes();
			refreshConsole();
			refreshCache();	
		}
	}
	
	//change le signe du cache
	private class PlusMoins implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			controleur.opposeAccumulateur();
			refreshCache();
		}
	}
}
