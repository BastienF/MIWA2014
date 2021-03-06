package fr.epita.sigl.miwa.application;

import java.util.Date;
import java.util.logging.Logger;

import fr.epita.sigl.miwa.application.clock.ClockClient;
import fr.epita.sigl.miwa.application.messaging.AsyncMessageListener;
import fr.epita.sigl.miwa.application.messaging.SyncMessHandler;
import fr.epita.sigl.miwa.st.Conf;
import fr.epita.sigl.miwa.st.EApplication;
import fr.epita.sigl.miwa.st.async.file.AsyncFileFactory;
import fr.epita.sigl.miwa.st.async.file.exception.AsyncFileException;
import fr.epita.sigl.miwa.st.async.message.AsyncMessageFactory;
import fr.epita.sigl.miwa.st.async.message.exception.AsyncMessageException;
import fr.epita.sigl.miwa.st.sync.SyncMessFactory;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws AsyncFileException,
			AsyncMessageException {
		/* ST DO NOT REMOVE/MODIFY OR PUT ANYTHING ABOVE */
		Conf.getInstance();
		SyncMessFactory.initSyncMessReceiver();	
		AsyncMessageFactory.getInstance().getAsyncMessageManager()
				.initListener(new AsyncMessageListener());
		/* !ST DO NOT REMOVE/MODIFY OR PUT ANYTHING ABOVE */
		/* CODE HERE */
		Date date = new Date();
		date.getDate();
		
		JDOM.browseXML("C:\\Users\\Romain\\Desktop\\miwa_env\\workspace\\MIWA2014\\CRM\\lol.xml");
		
		System.out.println(ClockClient.getClock().getHour());
		ClockClient.getClock().wakeMeUp(date, "Debout !");
		
		SyncMessHandler.getSyncMessSender().sendMessage(
				EApplication.GESTION_COMMERCIALE, "Coucou GC");
		
		AsyncMessageFactory.getInstance().getAsyncMessageManager().send("Message Async vers GC", EApplication.GESTION_COMMERCIALE);
		
		AsyncFileFactory.getInstance().getFileManager().send("/test.png", EApplication.GESTION_COMMERCIALE);
		
		/* !CODE HERE */
		/* ST DO NOT REMOVE/MODIFY OR PUT ANYTHING BELOW */
		AsyncMessageFactory.getInstance().getAsyncMessageManager()
				.stopListener();
		/* !ST DO NOT REMOVE/MODIFY OR PUT ANYTHING BELOW */
	}

}
