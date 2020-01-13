package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class StartKasowania extends BasicSimEvent<StacjaPaliw, Object>{

	public StartKasowania(StacjaPaliw entity, double delay) throws SimControlException {
		super(entity, delay);
	}

	@Override
	protected void onInterruption() {}

	@Override
	protected void onTermination() {}

	@Override
	protected void stateChange() throws SimControlException {
		StacjaPaliw stacjaPaliw = getSimObj();

		Kolejka kolejka = stacjaPaliw.getKolKasa();
		Dystrybutory dystrybutory = stacjaPaliw.getDysKasa();
		
		dystrybutory.incZajeteDystrybutory();
		
		Klient klient = kolejka.remove(0);
		if(stacjaPaliw.getKolBuffor().size() > 0) new DodawanieStanowiska(stacjaPaliw, 0);
		
		System.out.printf("%.3f", simTime());
		System.out.print(":: Rozpoczecie kasowania klienta nr = " + klient.getId() + ". Myjnia: " + klient.getChceDoMyjni() + ".\n");
		
		double dt = stacjaPaliw.getSimGenerator().exponential(stacjaPaliw.getMi());
		new KoniecKasowania(stacjaPaliw, dt, klient);

		if(kolejka.size() > 0 && dystrybutory.getZajeteDystrybutory() < dystrybutory.getLiczbaDystrybutorow()) {
		    new StartKasowania(stacjaPaliw, 0);
        }
	}
}
