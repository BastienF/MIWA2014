package fr.epita.sigl.miwa.application.printer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import fr.epita.sigl.miwa.application.clock.ClockClient;
import fr.epita.sigl.miwa.application.criteres.AgeValue;
import fr.epita.sigl.miwa.application.criteres.Critere;
import fr.epita.sigl.miwa.application.enums.EAlerteType;
import fr.epita.sigl.miwa.application.enums.ECritereType;
import fr.epita.sigl.miwa.application.statistics.CategorieStatistic;
import fr.epita.sigl.miwa.application.statistics.PaymentStatistic;
import fr.epita.sigl.miwa.application.statistics.SaleStatistic;
import fr.epita.sigl.miwa.application.statistics.Segmentation;
import fr.epita.sigl.miwa.application.statistics.StockStatistic;
import fr.epita.sigl.miwa.st.Conf;
import fr.epita.sigl.miwa.st.EApplication;

public class BIPrinter {

	private static final Logger LOGGER = Logger.getLogger(BIPrinter.class.getName());

	private static final String ENTREPOT = "entrepot";

	public void publishStockStatistics(Map<String, List<StockStatistic>> stockStatistics){
		StringBuilder builder = new StringBuilder();
		builder.append(initConsole());
		if (stockStatistics.isEmpty()){
			builder.append("Aucun problème relevé sur les stocks\n\n");
		} else {
			for (Entry<String, List<StockStatistic>> entry : stockStatistics.entrySet()){
				String storeId = entry.getKey();
				List<StockStatistic> statisticList = entry.getValue();
				if (ENTREPOT.equalsIgnoreCase(storeId)){
					builder.append("\n");
					builder.append("**** GESTION DES STOCKS : ENTREPOT");
					builder.append("\n");
				}
				if (!ENTREPOT.equalsIgnoreCase(storeId)){
					builder.append("\n");
					builder.append("**** GESTION DES STOCKS : MAGASIN " + storeId);
					builder.append("\n");
				}
				for (StockStatistic statistic : statisticList){
					if (!statistic.isPlein() && !statistic.isVide()){
						continue;
					}
					else{
						builder.append("ARTICLE ");
						builder.append(statistic.getArticle());
						builder.append(" : ");
						if (statistic.isPlein()){
							if (statistic.isCommande()){
								builder.append(EAlerteType.AP);						
							}
						}
						if (statistic.isVide()){
							if (statistic.isCommande()){
								builder.append(EAlerteType.AV);
							}
							else {
								builder.append(EAlerteType.AC);
							}
						}
						builder.append("\n");
					}
				}
			}
		}
		builder.append("\n");
		System.out.print(builder.toString());
	}

	public void publishSaleStatistics(List<SaleStatistic> saleStatistics) {
		StringBuilder builder = new StringBuilder();
		builder.append(initConsole());
		StringBuilder builderEvo = new StringBuilder("**** EVOLUTION QUOTIDIENNE DES VENTES PAR CATEGORIE D\'ARTICLES\n");		
		StringBuilder builderRep = new StringBuilder("**** REPARTITION DU CHIFFRE D\'AFFAIRES PAR CATEGORIE D\'ARTICLES\n");
		for (SaleStatistic statistic : saleStatistics){
			builderEvo.append(getEvolutionPart(statistic));
			builderRep.append(getRepartitionPart(statistic));
		}
		builderEvo.append("\n");
		builder.append(builderEvo.toString());
		builder.append(builderRep.toString());
		System.out.println(builder.toString());
	}

	public String createSegmentationFile(List<Critere> criteres, List<Segmentation> segmentations){
		Element xml = new Element("XML");
		Document document = new Document(xml);
		Element entete = getEnteteElement();
		Element groupes = new Element("GROUPES");
		Element groupe = new Element("GROUPE");
		groupe.addContent(getCriteresElement(criteres));
		groupe.addContent(getClientsElement(segmentations));
		groupes.addContent(groupe);
		xml.addContent(entete);
		xml.addContent(groupes);
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		String fileName = "/segmentation-client.xml";
		try {
			String repo = (String) Conf.getInstance().getProp().get(Conf.LOCAL_REPOSITORY_KEY);
			File file = new File(repo + "/" + EApplication.BI.getShortName() + fileName);
			out.output(document, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			LOGGER.severe("Erreur : fichier non trouvé");
			LOGGER.severe("L'erreur est : " + e);
		} catch (IOException e) {
			LOGGER.severe("Erreur pendant l'écriture");
			LOGGER.severe("L'erreur est : " + e);
		}
		return fileName;
	}

	public void publishPaiementStatistics(List<PaymentStatistic> paiementStatistics){
		StringBuilder builder = new StringBuilder(initConsole());
		builder.append("**** REPARTITION DU CHIFFRE D\'AFFAIRES PAR MOYEN DE PAIEMENT\n");
		for (PaymentStatistic statistic : paiementStatistics){
			builder.append(statistic.toString());
			builder.append("\n");
		}		
		System.out.println(builder.toString());
	}

	private String initConsole(){
		StringBuilder builder = new StringBuilder();
		builder.append("--------------------------------------------------\n--------------------------------------------------\n");
		builder.append("DATE : ");
		builder.append(ClockClient.getClock().getHour());
		builder.append("\n");
		return builder.toString();
	}

	private String getEvolutionPart(SaleStatistic statistic){
		StringBuilder builder = new StringBuilder();
		builder.append("CATEGORIE ");
		builder.append(statistic.getCategorie());
		builder.append(" : ");
		builder.append(statistic.getEvolution());
		builder.append(" %\n");
		return builder.toString();
	}

	private String getRepartitionPart(SaleStatistic statistic){
		StringBuilder builder = new StringBuilder();
		builder.append("CATEGORIE ");
		builder.append(statistic.getCategorie());
		builder.append(" : ");
		builder.append(statistic.getCa());
		builder.append(" € (");
		builder.append(statistic.getCaPourcent());
		builder.append(" %)\n");
		return builder.toString();
	}

	private Element getEnteteElement(){
		Element entete = new Element("ENTETE");
		Attribute objet = new Attribute("objet", "segmentation-client");
		Attribute source = new Attribute("source", "bi");
		SimpleDateFormat dateString = new SimpleDateFormat("YYYY-MM-dd");
		Attribute date = new Attribute("date", dateString.format(ClockClient.getClock().getHour()));
		List<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(objet);
		attributes.add(source);
		attributes.add(date);
		entete.setAttributes(attributes);
		return entete;
	}

	private Element getCriteresElement(List<Critere> criteres) {
		Element criteresElement = new Element("CRITERES");
		for (Critere critere : criteres){
			Element critereElement = new Element("CRITERE");
			Attribute type = new Attribute("type", critere.getTypeString());
			critereElement.setAttribute(type);

			ECritereType critereType = critere.getType();
			if (critereType == ECritereType.AGE){
				AgeValue age = (AgeValue) critere.getValue();
				Attribute min = new Attribute("min", age.getMin().toString());
				Attribute max = new Attribute("max", age.getMax().toString());
				critereElement.setAttribute(min);
				critereElement.setAttribute(max);
			}
			else {
				Attribute value;
				switch (critere.getType()) {
				case GEO:
					value = new Attribute("valeur", critere.getValue().toString());
					break;
				case SEXE:
					value = new Attribute("valeur", critere.getValue().toString());
					break;
				case SF:
					value = new Attribute("valeur", critere.getValue().toString());
					break;
				case ENF:
					value = new Attribute("valeur", critere.getValue().toString());
					break;
				case FID:
					value = new Attribute("valeur", critere.getValue().toString());
					break;
				default:
					value = new Attribute("valeur", "error");
					break;
				}
				critereElement.setAttribute(value);
			}
			criteresElement.addContent(critereElement);
		}
		return criteresElement;
	}

	private Element getClientsElement(List<Segmentation> segmentations) {
		Element clients = new Element("CLIENTS");
		if (!segmentations.isEmpty()){
			for (Segmentation segmentation : segmentations){
				Element client = new Element("CLIENT");
				Attribute numero = new Attribute("numero", segmentation.getClientNumero().toString());
				client.setAttribute(numero);
				Element categorieArticles = new Element("CATEGORIEARTICLES");
				for (CategorieStatistic statistic : segmentation.getCategorieStatistics()){
					Element categorie = new Element("CATEGORIE");
					Attribute ref = new Attribute("ref", statistic.getRef());
					Attribute achat = new Attribute("achat", statistic.getAchat().toString());
					categorie.setAttribute(ref);
					categorie.setAttribute(achat);
					categorieArticles.addContent(categorie);
				}
				client.addContent(categorieArticles);
				clients.addContent(client);
			}
		}
		return clients;
	}
}
