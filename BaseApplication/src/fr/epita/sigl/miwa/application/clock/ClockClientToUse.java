package fr.epita.sigl.miwa.application.clock;

import java.util.Date;

import fr.epita.sigl.miwa.st.Clock;
import fr.epita.sigl.miwa.st.IExposedClock;

public class ClockClientToUse {

	/*
	 * R�cup�re l'horloge serveur pour faire des requ�tes dessus (getHour, wakeMeUp, ...)
	 */
	static public IExposedClock getClock() {
		return Clock.getInstance();
	}
	
	/*
	 * Vous ne devez faire aucun appel � cette fonction, seulement remplir le code
	 * Elle est automatiquement appel�e lorsque l'horloge vous contacte ne pas appeler vous m�me
	 */
	static public void wakeUp(Date date, Object message) {
		if (message instanceof String) {
			if (message.equals("Hello World!")) {
				System.out.println(date.toString() + " : Hello dear client!");
			} else {
				System.out.println(date.toString() + " : " + message);
			}
		}
	}	
}
