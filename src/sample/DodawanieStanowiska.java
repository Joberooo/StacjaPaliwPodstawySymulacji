package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class DodawanieStanowiska extends BasicSimEvent<StacjaPaliw, Object>{

	public DodawanieStanowiska(StacjaPaliw entity, double delay) throws SimControlException {
		super(entity, delay);
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
		Kolejka kolejka = stacjaPaliw.getKolBuffor();
		if(kolejka.size() > 0) {
			Klient klient = kolejka.get(0);
			/*

			System.out.println("");
			System.out.println("probuje dodac klient nr = " + klient.getId() + " " + klient.getTankowanie() + " " + klient.getChceDoMyjni());
			System.out.println("");
			System.out.println("Buffor: " + stacjaPaliw.getKolBuffor().size() + "/" + stacjaPaliw.getKolBuffor().getPojemnosc());
			System.out.println("");
			System.out.println("95: " + stacjaPaliw.getKolBenzyna().size() + "/" + stacjaPaliw.getKolBenzyna().getPojemnosc());
			System.out.println("ON: " + stacjaPaliw.getKolOn().size() + "/" + stacjaPaliw.getKolOn().getPojemnosc());
			System.out.println("LPG: " + stacjaPaliw.getKolLpg().size() + "/" + stacjaPaliw.getKolLpg().getPojemnosc());
			System.out.println("");
			System.out.println("95: " + stacjaPaliw.getDysBenzyna().getZajeteDystrybutory() + "/" + stacjaPaliw.getDysBenzyna().getLiczbaDystrybutorow());
			System.out.println("ON: " + stacjaPaliw.getDysOn().getZajeteDystrybutory() + "/" + stacjaPaliw.getDysOn().getLiczbaDystrybutorow());
			System.out.println("LPG: " + stacjaPaliw.getDysLpg().getZajeteDystrybutory() + "/" + stacjaPaliw.getDysLpg().getLiczbaDystrybutorow());
			System.out.println("");
			System.out.println("Kasa" + stacjaPaliw.getKolKasa().size() + "/" + stacjaPaliw.getKolKasa().getPojemnosc());
			System.out.println("Kasa: " + stacjaPaliw.getDysKasa().getZajeteDystrybutory() + "/" + stacjaPaliw.getDysKasa().getLiczbaDystrybutorow());
			System.out.println("");
			System.out.println("Myjnia" + stacjaPaliw.getKolMyjnia().size() + "/" + stacjaPaliw.getKolMyjnia().getPojemnosc());
			System.out.println("");

			 */
			if (klient.getTankowanie() == 0) {
				if (stacjaPaliw.getKolKasa().size() < stacjaPaliw.getKolKasa().getPojemnosc()) {
					if (stacjaPaliw.getKolKasa().addK(klient)) {
						klient = kolejka.remove(0);
						stacjaPaliw.incTylkoMyjnia();
						System.out.printf("%.3f", simTime());
						System.out.printf(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolKasa().getNazwa() + ".\tMyjnia: " + klient.getChceDoMyjni() + ".\n");
						if (stacjaPaliw.getKolKasa().size() > 0) new StartKasowania(stacjaPaliw, 0);
					}
				}
			} else if (klient.getTankowanie() == 1) {
				if (stacjaPaliw.getKolBenzyna().size() < stacjaPaliw.getKolBenzyna().getPojemnosc()) {
					if (stacjaPaliw.getKolBenzyna().addK(klient)) {
						stacjaPaliw.getKolBenzyna().mvSize.setValue(stacjaPaliw.getKolBenzyna().size());
						klient = kolejka.remove(0);
						System.out.printf("%.3f", simTime());
						System.out.printf(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolBenzyna().getNazwa() + ".\tMyjnia: " + klient.getChceDoMyjni() + ".\n");
						if (stacjaPaliw.getKolBenzyna().size() > 0 && stacjaPaliw.getDysBenzyna().getZajeteDystrybutory() < stacjaPaliw.getDysBenzyna().getLiczbaDystrybutorow())
							new StartTankowania(stacjaPaliw, 0, stacjaPaliw.getKolBenzyna());
					}
				}
			} else if (klient.getTankowanie() == 2) {
				if (stacjaPaliw.getKolOn().size() < stacjaPaliw.getKolOn().getPojemnosc()) {
					if (stacjaPaliw.getKolOn().addK(klient)) {
						stacjaPaliw.getKolOn().mvSize.setValue(stacjaPaliw.getKolOn().size());
						klient = kolejka.remove(0);
						System.out.printf("%.3f", simTime());
						System.out.printf(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolOn().getNazwa() + ".\tMyjnia: " + klient.getChceDoMyjni() + ".\n");
						if (stacjaPaliw.getKolOn().size() > 0 && stacjaPaliw.getDysOn().getZajeteDystrybutory() < stacjaPaliw.getDysOn().getLiczbaDystrybutorow())
							new StartTankowania(stacjaPaliw, 0, stacjaPaliw.getKolOn());
					}
				}
			} else if (klient.getTankowanie() == 3) {
				if (stacjaPaliw.getKolLpg().size() < stacjaPaliw.getKolLpg().getPojemnosc()) {
					if (stacjaPaliw.getKolLpg().addK(klient)) {
						stacjaPaliw.getKolLpg().mvSize.setValue(stacjaPaliw.getKolLpg().size());
						klient = kolejka.remove(0);
						System.out.printf("%.3f", simTime());
						System.out.printf(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolLpg().getNazwa() + ".\tMyjnia: " + klient.getChceDoMyjni() + ".\n");
						if (stacjaPaliw.getKolLpg().size() > 0 && stacjaPaliw.getDysLpg().getZajeteDystrybutory() < stacjaPaliw.getDysLpg().getLiczbaDystrybutorow())
							new StartTankowania(stacjaPaliw, 0, stacjaPaliw.getKolLpg());
					}
				}
			}

			/*
			double dt = stacjaPaliw.getSimGenerator().exponential(stacjaPaliw.getMi());
			System.out.println("dy = " + dt);
			new DodawanieStanowiska(stacjaPaliw, dt*0.01);
			 */
		}
	}
}
