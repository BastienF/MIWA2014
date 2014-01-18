/**
 * 
 */
package fr.epita.sigl.miwa.application.object;

import java.util.Date;
import java.util.List;

import fr.epita.sigl.miwa.application.BDD.JdbcConnection;

/**
 * @author clementlavigne
 *
 */
public class Client {
	
	private int matricule;
	private String nom;
	private String prenom;
	private String adresse;
	private String codePostal;
	private String telephone;
	private String mail;
	private String IBAN;
	private String BIC;
	private Date date;
	private CarteFidelite carteFed;
	
	public static List<Client> clientsList;
	
	public Client (int id, String nom, String prenom)
	{
		this.matricule = id;
		this.nom = nom;
		this.prenom = prenom;
		clientsList.add(this);
	}
	
	public Client (int id)
	{
		this.matricule = id;
	}

	public Client ()
	{
	}
	
	
	
	
	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getBIC() {
		return BIC;
	}

	public void setBIC(String bIC) {
		BIC = bIC;
	}

	public CarteFidelite getCarteFed() {
		return carteFed;
	}

	public void setCarteFed(CarteFidelite carteFed) {
		this.carteFed = carteFed;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the matricule
	 */
	public int getMatricule() {
		return matricule;
	}


	/**
	 * @param matricule the matricule to set
	 */
	public void setMatricule(int matricule) {
		this.matricule = matricule;
	}


	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	
}
