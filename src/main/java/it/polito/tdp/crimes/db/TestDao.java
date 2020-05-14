package it.polito.tdp.crimes.db;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		/*for(Event e : dao.listAllEvents())
			System.out.println(e);*/
		
		/*for(String s : dao.getVertexes("larceny", 2))
			System.out.println(s);
		
		for(Integer i : dao.getMonths())
			System.out.println(i);*/
		for(Adiacenza a : dao.getAdiacenza("aggravated-assault", 2)) {
			System.out.format("%s %s %d\n", a.getO1(),a.getO2(),a.getWeight());
		}
	}

}
