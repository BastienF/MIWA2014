package fr.epita.sigl.miwa.bo.plug;

import java.util.Date;

import fr.epita.sigl.miwa.bo.object.Article;
import fr.epita.sigl.miwa.bo.object.ArticleList;
import fr.epita.sigl.miwa.bo.object.RestockRequest;
import fr.epita.sigl.miwa.bo.object.RestockRequestReception;

public class PlugCRM {
	
	public static String sale =
			"<XML>"
			+ "<ENTETE objet='facture-client' source='crm' date='2014-01-29'/>\n\r" +
			"<FACTURE refclient='4242' montanttotal='23'>\n\r" +
				"<ARTICLE refarticle='P124' quantite='3' prix='400'>\n\r" +
				"<ARTICLE refarticle='P123' quantite='1' prix='200'/>\n\r" +
			"</FACTURE>\n\r"
			+ "</XML>";

	@SuppressWarnings("deprecation")
	public static RestockRequest restockRequestObject()
	{
		RestockRequest rr = new RestockRequest();
		
		rr.number = "CV398719873";
		rr.backOfficeReference = "20131225";
		rr.backOfficeAdresse = "XXXXXX";
		rr.backOfficePhone = "0133333333";
		rr.date = new Date(2014 - 1900, 03, 25);
		
		Article a1 = new Article();
		a1.reference = "AU736827";
		a1.quantity = "265000";
		a1.category = "001";
		rr.articles.add(a1);
		
		Article a2 = new Article();
		a2.reference = "AU736823";
		a2.quantity = "12";
		a2.category = "002";
		rr.articles.add(a2);
				
		return rr;
	}
	
	@SuppressWarnings("deprecation")
	public static RestockRequestReception restockRequestReceptionObject()
	{
		RestockRequestReception rrr = new RestockRequestReception();
		
		rrr.orderNumber = "CV398719873";
		rrr.deliveryDate = new Date(2014 - 1900, 03, 18);
		rrr.status = "TRUE";
		rrr.comment = "Rien à signaler";
		
		Article a1 = new Article();
		a1.reference = "AU736827";
		a1.quantity = "265000";
		a1.category = "001";
		rrr.articles.add(a1);
		
		Article a2 = new Article();
		a2.reference = "AU736823";
		a2.quantity = "12";
		a2.category = "002";
		rrr.articles.add(a2);
		
		return rrr;
	}
	
	@SuppressWarnings("deprecation")
	public static ArticleList stockLevelObject()
	{
		ArticleList al = new ArticleList();
		
		al.number = "CV398719873";
		
		al.refshop = "PA218765";
		al.date = new Date(2014 - 1900, 03, 18);
		
		Article a1 = new Article();
		a1.reference = "08deb933-bb39-411d-b0ee-59b28812";
		a1.quantity = "265000";
		al.articles.add(a1);
		
		Article a2 = new Article();
		a2.reference = "292f8025-58bf-498a-a092-1404d30f";
		a2.quantity = "12";
		al.articles.add(a2);
		
		return al;
	}
	
	@SuppressWarnings("deprecation")
	public static ArticleList getstockLevel(ArticleList articlesSource)
	{
		ArticleList articleResult = articlesSource;
		for (Article article : articleResult.articles)
		{
			// Temporaire, attente de BDD
			if (article != null)
			{
				article.quantity = "23";
				article.capacity = "42";
			}
		}
		return articleResult;
	}
}