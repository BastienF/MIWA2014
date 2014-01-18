package fr.epita.sigl.miwa.application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Vente {
	Vente() {
	}

	public void create() throws InterruptedException {
		int i = 0;
		// 1 vente
		while (true) {
			System.out.println("------------------------------------");
			System.out.println("Vente : " + i);
			final Set<Produit> tabproduct = new HashSet<Produit>();
			final Set<Produit> selectedproducts = new HashSet<Produit>();
			// Construction de la liste des produits
			try {
				ResultSet rs = Main.bdd
						.select("select (produit_id,produit_prix, produit_ref, produit_nom,produit_pourcentagepromo) from produit");
				while (rs.next()) {
					String[] resu = Main.bdd.parseresult(rs.getString(1));
					Produit prod = new Produit(resu[0], resu[1], resu[2],
							resu[3], resu[4]);
					tabproduct.add(prod);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Construction de la liste des produits sélectionnée
			float prixtotal = 0;
			int quant = 1;
			Iterator<Produit> e = tabproduct.iterator();
			Produit current = new Produit();
			while (e.hasNext()) {
				int lower = 0;
				int higher = 3;
				int add = (int)(Math.random() * (higher-lower)) + lower;
				current = e.next();
				if (add == 1) {
					prixtotal += current.getPrix();
					lower = 1;
					higher = 5;
					quant = (int)(Math.random() * (higher-lower)) + lower;
					current.setQuantite(quant);
					System.out.println("Ajout de : " + quant + " " + current.getNom()
							+ " dans le panier");
					selectedproducts.add(current);
				}
			}
			if (selectedproducts.isEmpty())
				System.out.println("Pas de produit, vente annulée");
			System.out.println("-------------------------------------");
			i++;
			
			Thread.sleep(2000);
		}
	}

}
