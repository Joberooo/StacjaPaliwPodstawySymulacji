package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class StartTankowania extends BasicSimEvent<StacjaPaliw, Kolejka>{
	
	public StartTankowania(StacjaPaliw entity, double delay, Kolejka params) throws SimControlException {
		super(entity, delay, params);
	}

	@Override
	protected void onInterruption() {}

	@Override
	protected void onTermination() {}

	@Override
	protected void stateChange() throws SimControlException {
		StacjaPaliw stacjaPaliw = getSimObj();
		Kolejka kolejka = transitionParams;
		
		Dystrybutory dystrybutory = null;
		if(kolejka.getNazwa().equals(stacjaPaliw.getDysBenzyna().getNazwa())) dystrybutory = stacjaPaliw.getDysBenzyna();
		else if(kolejka.getNazwa().equals(stacjaPaliw.getDysOn().getNazwa())) dystrybutory = stacjaPaliw.getDysOn();
		else if(kolejka.getNazwa().equals(stacjaPaliw.getDysLpg().getNazwa())) dystrybutory = stacjaPaliw.getDysLpg();

		assert dystrybutory != null;
		dystrybutory.incZajeteDystrybutory();
		
		Klient klient = kolejka.remove(0);
		if(stacjaPaliw.getKolBuffor().size() > 0) new DodawanieStanowiska(stacjaPaliw, 0);
		
		Object[] objekty = {klient, kolejka, dystrybutory};
		
		System.out.printf("%.3f", simTime());
		System.out.print(":: Rozpoczecie tankowania przez klienta nr = " + klient.getId() + ". Dystrybutor: " + dystrybutory.getNazwa() + ". Myjnia: " + klient.getChceDoMyjni() + ".\n");
		
		double dt = stacjaPaliw.getSimGenerator().erlang(klient.getTankowanie(), stacjaPaliw.getErlang());
		new KoniecTankowania(stacjaPaliw, dt, objekty);
	}
}
