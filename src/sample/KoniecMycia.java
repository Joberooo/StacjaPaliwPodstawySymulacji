package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class KoniecMycia extends BasicSimEvent<StacjaPaliw, Klient>{

	public KoniecMycia(StacjaPaliw entity, double delay, Klient params) throws SimControlException {
		super(entity, delay, params);
	}

	@Override
	protected void onInterruption() {}

	@Override
	protected void onTermination() {}

	@Override
	protected void stateChange() throws SimControlException {
		StacjaPaliw stacjaPaliw = getSimObj();
		Klient klient = transitionParams;
		
		System.out.printf("%.3f", simTime());
		System.out.print(":: Zakonczenie mycia klienta nr = " + klient.getId() + ".\n");
		System.out.print("Klient nr = " + klient.getId() + " mowi PAPA!.\n");
		klient.setCzasStop(simTime());
		System.out.println("Czas start mycia: " + klient.getCzasStart() + " czas stop mycia: " + klient.getCzasStop() + " roznica: " + (klient.getCzasStop() - klient.getCzasStart()));
		stacjaPaliw.mvTime[1].setValue(klient.getCzasStop() - klient.getCzasStart());
		stacjaPaliw.getMyjnia().setZajete(false);
		if(stacjaPaliw.getKolMyjnia().size() > 0) new StartMycia(stacjaPaliw, 0);
		if(stacjaPaliw.getKolKasa().size() > 0 && stacjaPaliw.getDysKasa().getZajeteDystrybutory() < stacjaPaliw.getDysKasa().getLiczbaDystrybutorow()) new StartKasowania(stacjaPaliw, 0);
		if(stacjaPaliw.getKolBuffor().size() > 0) new DodawanieStanowiska(stacjaPaliw, 0);
    }
}
