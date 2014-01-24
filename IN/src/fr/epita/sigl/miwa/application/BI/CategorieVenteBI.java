package fr.epita.sigl.miwa.application.BI;

public class CategorieVenteBI {
	private String ref_categorie;
	private Integer quantite_vendue;
	private Float montant_fournisseur;
	private Float montant_vente;

	public CategorieVenteBI()
	{
		
	}
	
	public CategorieVenteBI(String ref_categorie, Integer quantite_vendue, Float montant_fournisseur, Float montant_vente)
	{
		this.ref_categorie = ref_categorie;
		this.quantite_vendue = quantite_vendue;
		this.montant_fournisseur = montant_fournisseur;
		this.montant_vente = montant_vente;
	}
	
	public CategorieVenteBI(String ref_categorie, String quantite_vendue, String montant_fournisseur, String montant_vente)
	{
		this.ref_categorie = ref_categorie;
		this.quantite_vendue = Integer.parseInt(quantite_vendue);
		this.montant_fournisseur = Float.parseFloat(montant_fournisseur);
		this.montant_vente = Float.parseFloat(montant_vente);
	}
	
	public String sendXML()
	{
		StringBuilder result = new StringBuilder();
		
		
		result.append("<CATEGORIE ref-categorie=\"" + ref_categorie
		+ "\" quantite_vendue=\"" + quantite_vendue
		+ "\" montant_fournisseur=\"" + montant_fournisseur
		+ "\" montant_vente=\"" + montant_vente + "\" />");
		
		return result.toString();
	}
	
	public String getRef_categorie() {
		return ref_categorie;
	}
	public void setRef_categorie(String ref_categorie) {
		this.ref_categorie = ref_categorie;
	}
	public Integer getQuantite_vendue() {
		return quantite_vendue;
	}
	public void setQuantite_vendue(Integer quantite_vendue) {
		this.quantite_vendue = quantite_vendue;
	}
	public Float getMontant_fournisseur() {
		return montant_fournisseur;
	}
	public void setMontant_fournisseur(Float montant_fournisseur) {
		this.montant_fournisseur = montant_fournisseur;
	}
	public Float getMontant_vente() {
		return montant_vente;
	}
	public void setMontant_vente(Float montant_vente) {
		this.montant_vente = montant_vente;
	}
}
