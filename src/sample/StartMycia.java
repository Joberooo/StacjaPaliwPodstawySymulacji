package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class StartMycia extends BasicSimEvent<StacjaPaliw, Object>{

	public StartMycia(StacjaPaliw entity, double delay) throws SimControlException {
		super(entity, delay);
	}

	@Override
	protected void onInterruption() {}

	@Override
	protected void onTermination() {}

	@Override
	protected void stateChange() throws SimControlException {
		StacjaPaliw stacjaPaliw = getSimObj();

		Kolejka kolejka = stacjaPaliw.getKolMyjnia();

		Klient klient = kolejka.remove(0);
		if(stacjaPaliw.getKolBuffor().size() > 0) new DodawanieStanowiska(stacjaPaliw, 0);

		stacjaPaliw.getMyjnia().setZajete(true);
		
		System.out.printf("%.3f", simTime());
		System.out.print(":: Rozpoczecie mycia klienta nr = " + klient.getId() + ".\n");
		
		double dt = stacjaPaliw.getSimGenerator().exponential(stacjaPaliw.getMi());
		new KoniecMycia(stacjaPaliw, dt, klient);
	}
}
