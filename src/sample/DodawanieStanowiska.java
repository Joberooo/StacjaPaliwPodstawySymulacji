package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class DodawanieStanowiska extends BasicSimEvent<StacjaPaliw, Object>{

	public DodawanieStanowiska(StacjaPaliw entity, double delay) throws SimControlException {
		super(entity, delay);
	}

	@Override
	protected void onInterruption() {}

	@Override
	protected void onTermination() {}

	@Override
	protected void stateChange() throws SimControlException {
		StacjaPaliw stacjaPaliw = getSimObj();
		Kolejka kolejka = stacjaPaliw.getKolBuffor();
		if(kolejka.size() > 0) {
			Klient klient = kolejka.get(0);
			if (klient.getTankowanie() == 0) {
				if (stacjaPaliw.getKolKasa().size() < stacjaPaliw.getKolKasa().getPojemnosc()) {
					if (stacjaPaliw.getKolKasa().addK(klient)) {
						klient = kolejka.remove(0);
						stacjaPaliw.incTylkoMyjnia();
						System.out.printf("%.3f", simTime());
						System.out.print(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolKasa().getNazwa() + ".\tMyjnia: " + klient.getChceDoMyjni() + ".\n");
						if (stacjaPaliw.getKolKasa().size() > 0) new StartKasowania(stacjaPaliw, 0);
					}
				}
			} else if (klient.getTankowanie() == 1) {
				if (stacjaPaliw.getKolBenzyna().size() < stacjaPaliw.getKolBenzyna().getPojemnosc()) {
					if (stacjaPaliw.getKolBenzyna().addK(klient)) {
						stacjaPaliw.getKolBenzyna().mvSize.setValue(stacjaPaliw.getKolBenzyna().size());
						klient = kolejka.remove(0);
						System.out.printf("%.3f", simTime());
						System.out.print(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolBenzyna().getNazwa() + ".\tMyjnia: " + klient.getChceDoMyjni() + ".\n");
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
						System.out.print(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolOn().getNazwa() + ".\tMyjnia: " + klient.getChceDoMyjni() + ".\n");
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
						System.out.print(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolLpg().getNazwa() + ".\tMyjnia: " + klient.getChceDoMyjni() + ".\n");
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
