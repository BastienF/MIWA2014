package fr.epita.sigl.miwa.bo.plug;

import java.util.Date;

import fr.epita.sigl.miwa.bo.object.Article;
import fr.epita.sigl.miwa.bo.object.ArticleAndLocalPriceAndPromotion;
import fr.epita.sigl.miwa.bo.object.ArticleList;

public class PlugCashRegister {
	
	public static String articleAndLocalPriceAndPromotion =

	"<XML><ENTETE objet='article-prix-promo' source='bo' date='2013-12-18'/>\n\r" +
	"<ARTICLES>\n\r" +
		"  <ARTICLE nomarticle='patou3' refarticle='3dfcd223-3314-47d6-b3bf-14812bdb' prix='200' promotion='70' />\n\r" +
	"</ARTICLES></XML>\n\r";
	
	public static String articlePricePromotionUpdate1 =
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
	"<ENTETE objet=\"article-prix-promo-update\" source=\"bo\" date=\"2013-12-18\"/>" +
	"<ARTICLES>" +
		"<ARTICLE nomarticle=\"Chocapic\" refarticle=\"45678765434567\" prix=\"12\" promotion=\"50\" />" +
	"</ARTICLES>";
	
	public static String articlePricePromotionUpdate2 =
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
	"<ENTETE objet='article-prix-promo-update' source='bo' date='2013-12-18'/>" +
	"<ARTICLES>" +
		"<ARTICLE nomarticle='Chocapic' refarticle='45678765434567' prix='12' promotion='50' />" +
	"</ARTICLES>";
	
	public static String salesTicket =
	"<ENTETE objet='ticket-vente-journee' source='caisse' date='2013-12-18'/>\n\r" +
	"<VENTES>\n\r" +
		"  <VENTE client='Alex' montanttotal='200' dateheure='2013-12-22 20:22:21'>\n\r" +
			"    <PAIEMENT type='cb' montant='200' />\n\r" +
			"    <ARTICLE nomarticle='pomme' refarticle='P123' quantite='1' prix='200' />\n\r" +
		"  </VENTE>\n\r" +
	"</VENTES>\n\r";
	
	public static String saleTicket =
			//"<?xml version='1.0' encoding='UTF-8'?>" +
			"<ENTETE objet='ticket-caisse' source='caisse' date='2015-6-11'/>\n\r" +
			"<TICKETVENTE refclient='C987654321' moyenpayement=''>\n\r" +
				"<ARTICLE refarticle='1' quantite='1' prix='5.5' />\n\r" +
			"</TICKETVENTE>\n\r";
			/*"<ENTETE objet='ticket-caisse' source='caisse' date='2014-01-19'/>\n\r" +
	"<TICKETVENTE refclient='nimportequoi' moyenpayement='CB' >\n\r" +
	    "<ARTICLE refarticle='2361' quantite='42' prix='23' />\n\r" +
	    "<ARTICLE refarticle='2452' quantite='10' prix='43' />\n\r" +
	"</TICKETVENTE>\n\r";*/
	
	@SuppressWarnings("deprecation")
	public static ArticleAndLocalPriceAndPromotion articleAndLocalPriceAndPromotionObject()
	{
		ArticleAndLocalPriceAndPromotion a = new ArticleAndLocalPriceAndPromotion();
		a.date = new Date(2013 - 1900, 11, 20);
		
		Article a1 = new Article();
		a1.name = "n1";
		a1.reference = "r1";
		a1.salesPrice="p1";
		a1.promotion="pr1";
		a.articles.add(a1);
		
		Article a2 = new Article();
		a2.name = "n2";
		a2.reference = "r2";
		a2.salesPrice="p2";
		a2.promotion="pr2";
		a.articles.add(a2);

		return a;
	}
	
	@SuppressWarnings("deprecation")
	public static ArticleList articleListObject()
	{
		ArticleList a = new ArticleList();
		a.date = new Date(2013 - 1900, 11, 20);
		a.refclient = "123456789";
		a.totalamount = "20";
		
		Article a1 = new Article();
		a1.reference = "r1";
		a1.salesPrice="10";
		a1.quantity="1";
		a.articles.add(a1);
		
		Article a2 = new Article();
		a2.reference = "r2";
		a2.salesPrice="10";
		a2.quantity="1";
		a.articles.add(a2);

		return a;
	}
	
	@SuppressWarnings("deprecation")
	public static ArticleList getpromotion(ArticleList articlesSource)
	{
		ArticleList articleResult = articlesSource;
		for (Article article : articleResult.articles)
		{
			// Temporaire, attente de BDD
			if (article != null)
			{
				article.promotion = "25";
			}
		}
		return articleResult;
	}
}