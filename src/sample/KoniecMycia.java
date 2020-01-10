package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class KoniecMycia extends BasicSimEvent<StacjaPaliw, Klient>{

	public KoniecMycia(StacjaPaliw entity, double delay, Klient params) throws SimControlException {
		super(entity, delay, params);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onInterruption() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onTermination() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stateChange() throws SimControlException {
		StacjaPaliw stacjaPaliw = getSimObj();
		Klient klient = transitionParams;
		
		System.out.printf("%.3f", simTime());
		System.out.printf(":: Zakonczenie mycia klienta nr = " + klient.getId() + ".\n");
		System.out.printf("Klient nr = " + klient.getId() + " mowi PAPA!.\n");
		klient.setCzasStop(simTime());
		System.out.println("Czas start mycia: " + klient.getCzasStart() + " czas stop mycia: " + klient.getCzasStop() + " roznica: " + (klient.getCzasStop() - klient.getCzasStart()));
		stacjaPaliw.mvTime[1].setValue(klient.getCzasStop() - klient.getCzasStart());
		stacjaPaliw.getMyjnia().setZajete(false);
		if(stacjaPaliw.getKolMyjnia().size() > 0) new StartMycia(stacjaPaliw, 0);
		if(stacjaPaliw.getKolKasa().size() > 0 && stacjaPaliw.getDysKasa().getZajeteDystrybutory() < stacjaPaliw.getDysKasa().getLiczbaDystrybutorow()) new StartKasowania(stacjaPaliw, 0);
		if(stacjaPaliw.getKolBuffor().size() > 0) new DodawanieStanowiska(stacjaPaliw, 0);
    }
}
