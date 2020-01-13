package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class KoniecKasowania extends BasicSimEvent<StacjaPaliw, Klient>{

	public KoniecKasowania(StacjaPaliw entity, double delay, Klient params) throws SimControlException {
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
		System.out.print(":: Zakonczenie kasowania klienta nr = " + klient.getId() + ". Myjnia: " + klient.getChceDoMyjni() + ".\n");

        Dystrybutory dystrybutory = stacjaPaliw.getDysKasa();

		if(klient.getChceDoMyjni())
		{
			if(stacjaPaliw.getKolMyjnia().size() < stacjaPaliw.getKolMyjnia().getPojemnosc()) {
				if (stacjaPaliw.getKolMyjnia().addK(klient)) {
					stacjaPaliw.getKolMyjnia().mvSize.setValue(stacjaPaliw.getKolMyjnia().size());
					dystrybutory.decZajeteDystrybutory();

					System.out.printf("%.3f", simTime());
					System.out.print(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolMyjnia().getNazwa() + ". Myjnia: " + klient.getChceDoMyjni() + ".\n");
				    if(!stacjaPaliw.getMyjnia().getZajete()) new StartMycia(stacjaPaliw, 0);
				}
			}
			else{
				if(stacjaPaliw.getKolBlokada().addK(klient)) {
					System.out.print("Zablokowano klienta nr = " + klient.getId() + "!\n");
					System.out.print("Blokada: " + stacjaPaliw.getKolBlokada().size() + "/" + stacjaPaliw.getKolBlokada().getPojemnosc() + "\n");
					new Blokada(stacjaPaliw, 0);
				}
			}
		}
		else
		{
			dystrybutory.decZajeteDystrybutory();
			System.out.print("Klient nr = " + klient.getId() + " mowi PAPA!.\n");
        }
		
		if(stacjaPaliw.getKolKasa().size() > 0 && stacjaPaliw.getDysKasa().getZajeteDystrybutory() < stacjaPaliw.getDysKasa().getLiczbaDystrybutorow())
		{
			new StartKasowania(stacjaPaliw, 0);
		}
		if(stacjaPaliw.getKolBuffor().size() > 0) new DodawanieStanowiska(stacjaPaliw, 0);
    }
}
