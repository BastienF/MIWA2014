package fr.epita.sigl.miwa.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XmlReader {
	private String filename;
	private DbHandler dbHandler;

	public XmlReader(String filename) {
		this.filename = filename;
		this.dbHandler = new DbHandler("jdbc:mysql://localhost/miwa", "root", "root");
	}

	public void parseProducts() {
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			final Logger LOGGER = Logger.getLogger(XmlReader.class.getName());

			DefaultHandler handler = new DefaultHandler() {
				boolean firstline = true;

				public void startElement(String uri, String localName,String qName, 
						Attributes attributes) throws SAXException {

					if (qName.equalsIgnoreCase("ARTICLE")) {
						String ref = attributes.getValue("reference");
						float sellPrice = Float.parseFloat(attributes.getValue("prix_vente"));

						if (firstline) {
							LOGGER.severe("***** " + "Parsing des prix (Flux GC -> MDM) : Référence du produit=" + ref + " / Prix de vente du produit (TTC)=" + sellPrice);
							firstline = false;
						}
						dbHandler.updateProduct(ref, sellPrice);
					}
				}
			};

			LOGGER.severe("***** " + "Début du parsing des prix (Flux GC -> MDM)");
			saxParser.parse(this.filename, handler);
			LOGGER.severe("***** " + "Fin du parsing des promotions (Flux GC -> MDM)");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parsePromotions() {
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			final Logger LOGGER = Logger.getLogger(XmlReader.class.getName());

			DefaultHandler handler = new DefaultHandler() {
				ArrayList<Product> productList = new ArrayList<>();
				Promotion promo = null;
				boolean firstline = true;

				public void startElement(String uri, String localName,String qName, 
						Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("PROMOTION")) {
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

						Date startDate = null;
						Date endDate = null;
						try {
							startDate = formatter.parse(attributes.getValue("datedebut"));
							endDate = formatter.parse(attributes.getValue("datefin"));
						} catch (ParseException e) {
							e.printStackTrace();
						}

						int rebate = Integer.parseInt(attributes.getValue("promotion_pourcentage"));
						if (firstline) {
							LOGGER.severe("***** " + "Première promotion : Date de début=" + startDate + " / Taux de remise=" + rebate + "%");
							firstline = false;
						}
						promo = new Promotion(startDate, endDate, rebate);
					}

					if (qName.equalsIgnoreCase("ARTICLE")) {
						String ref = attributes.getValue("reference");
						Product p = new Product(ref);
						productList.add(p);
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					if (qName.equalsIgnoreCase("PROMOTION")) {
						dbHandler.createNewPromotion(promo, productList);
						productList = new ArrayList<>();
						promo = null;
					}

				}
			};


			LOGGER.severe("***** " + "Début du parsing des promotions (Flux GC -> MDM)");
			saxParser.parse(this.filename, handler);
			LOGGER.severe("***** " + "Fin du parsing des promotions (Flux GC -> MDM)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseDelta() {
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				boolean firstline = true;
				boolean long_desc;

				public void startElement(String uri, String localName,String qName, 
						Attributes attributes) throws SAXException {

					if (qName.equalsIgnoreCase("PRODUCT")) {
						String name = attributes.getValue("name");
						String description = attributes.getValue("description");
						Float priceTTC = Float.parseFloat(attributes.getValue("priceTTC"));
						String modification = attributes.getValue("modification");
					}

					if (qName.equalsIgnoreCase("DESCRIPTION")) {
						long_desc = true;
					}
					
					if (qName.equalsIgnoreCase("PROMOTION")) {
						String id = attributes.getValue("id");
						int quantityMin = Integer.parseInt(attributes.getValue("quantityMin"));
						int rebate = Integer.parseInt(attributes.getValue("rebate"));
						
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						Date startDate = null;
						Date endDate = null;
						try {
							startDate = formatter.parse(attributes.getValue("debut"));
							endDate = formatter.parse(attributes.getValue("fin"));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						PromotionForGC promo = new PromotionForGC(id, quantityMin, rebate, startDate, endDate);
					}
				}
				
				public void characters(char ch[], int start, int length) throws SAXException {
					if (long_desc) {
						String long_d = new String(ch, start, length);
						long_desc = false;
					}
				}
			};

			saxParser.parse(this.filename, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
