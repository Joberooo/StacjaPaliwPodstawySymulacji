package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class KoniecTankowania extends BasicSimEvent<StacjaPaliw, Object[]>{
	
	public KoniecTankowania(StacjaPaliw entity, double delay, Object[] params) throws SimControlException {
		super(entity, delay, params);
	}

	@Override
	protected void onInterruption() {}

	@Override
	protected void onTermination() {}

	@Override
	protected void stateChange() throws SimControlException {
		StacjaPaliw stacjaPaliw = getSimObj();
		Klient klient = (Klient) transitionParams[0];
		Kolejka kolejka = (Kolejka) transitionParams[1];
		Dystrybutory dystrybutory = (Dystrybutory) transitionParams[2];

		if(stacjaPaliw.getKolKasa().getPojemnosc() > stacjaPaliw.getKolKasa().size())
		{
			if(stacjaPaliw.getKolKasa().addK(klient))
			{
				System.out.printf("%.3f", simTime());
				System.out.print(":: Zakonczenie tankowania przez klienta nr = " + klient.getId() + ". Dystrybutor: " + kolejka.getNazwa() + ". Myjnia: " + klient.getChceDoMyjni() + ".\n");
				klient.setCzasStop(simTime());
				stacjaPaliw.mvTime[0].setValue(klient.getCzasStop() - klient.getCzasStart());
				System.out.println("Czas start tankowania: " + klient.getCzasStart() + " czas stop tankowania: " + klient.getCzasStop() + " roznica: " + (klient.getCzasStop() - klient.getCzasStart()));
				if(klient.getChceDoMyjni()) klient.setCzasStart(simTime());
				dystrybutory.decZajeteDystrybutory();
				System.out.printf("%.3f",  simTime());
				System.out.print(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolKasa().getNazwa() + ". Myjnia: " + klient.getChceDoMyjni() + ".\n");
				if(stacjaPaliw.getDysKasa().getZajeteDystrybutory() < stacjaPaliw.getDysKasa().getLiczbaDystrybutorow()) {
					new StartKasowania(stacjaPaliw, 0);
				}
			}
		}
		else{
			if(stacjaPaliw.getKolBlokadaTankowanie().addK(klient)) {
				System.out.printf("%.3f", simTime());
				System.out.print(":: Zakonczenie tankowania przez klienta nr = " + klient.getId() + ". Dystrybutor: " + kolejka.getNazwa() + ". Myjnia: " + klient.getChceDoMyjni() + ".\n");

				System.out.print("Zablokowano klienta nr = " + klient.getId() + "! PO TANKOWANIU!\n");
				System.out.print("BlokadaTankowania: " + stacjaPaliw.getKolBlokadaTankowanie().size() + "/" + stacjaPaliw.getKolBlokadaTankowanie().getPojemnosc() + "\n");
				new BlokadaTankowania(stacjaPaliw, 0);
			}
		}

		if(kolejka.size() > 0 && dystrybutory.getZajeteDystrybutory() < dystrybutory.getLiczbaDystrybutorow())
		{
			new StartTankowania(stacjaPaliw, 0, kolejka);
		}
		if(stacjaPaliw.getKolBuffor().size() > 0) new DodawanieStanowiska(stacjaPaliw, 0);
	}
}
