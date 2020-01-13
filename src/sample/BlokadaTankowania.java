package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class BlokadaTankowania extends BasicSimEvent<StacjaPaliw, Object>{

	public BlokadaTankowania(StacjaPaliw entity, double delay) throws SimControlException {
		super(entity, delay);
	}

	@Override
	protected void onInterruption() {}

	@Override
	protected void onTermination() {}

	@Override
	protected void stateChange() throws SimControlException {
		StacjaPaliw stacjaPaliw = getSimObj();

		Kolejka kolejka = stacjaPaliw.getKolBlokadaTankowanie();

		if(stacjaPaliw.getKolKasa().size() < stacjaPaliw.getKolKasa().getPojemnosc()) {
			if (kolejka.size() > 0) {
				Klient klient = kolejka.remove(0);

				System.out.printf("%.3f", simTime());
				System.out.print(":: Odblokowanie klienta nr = " + klient.getId() + ". BLOKADA TANKOWANIA! Myjnia: " + klient.getChceDoMyjni() + ".\n");

				if (stacjaPaliw.getKolKasa().addK(klient)) {
					klient.setCzasStop(simTime());
					stacjaPaliw.mvTime[0].setValue(klient.getCzasStop() - klient.getCzasStart());
					System.out.println("Czas start tankowania: " + klient.getCzasStart() + " czas stop tankowania: " + klient.getCzasStop() + " roznica: " + (klient.getCzasStop() - klient.getCzasStart()));
					if(klient.getChceDoMyjni()) klient.setCzasStart(simTime());

					Dystrybutory dys = null;
					Kolejka kol = null;
					if(klient.getTankowanie() == 1){
						dys = stacjaPaliw.getDysBenzyna();
						kol = stacjaPaliw.getKolBenzyna();
					}
					else if(klient.getTankowanie() == 2){
						dys = stacjaPaliw.getDysOn();
						kol = stacjaPaliw.getKolOn();
					}
					else if(klient.getTankowanie() == 3){
						dys = stacjaPaliw.getDysLpg();
						kol = stacjaPaliw.getKolLpg();
					}

					assert dys != null;
					dys.decZajeteDystrybutory();

					System.out.printf("%.3f", simTime());
					System.out.print(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolKasa().getNazwa() + ". Myjnia: " + klient.getChceDoMyjni() + ".\n");
                    if(stacjaPaliw.getDysKasa().getLiczbaDystrybutorow() > stacjaPaliw.getDysKasa().getZajeteDystrybutory()) new StartKasowania(stacjaPaliw, 0);
					assert kol != null;
					if(dys.getLiczbaDystrybutorow() > dys.getZajeteDystrybutory() && kol.size() > 0) new StartTankowania(stacjaPaliw, 0, kol);
				}
				if(kolejka.size() > 0){
					double dt = stacjaPaliw.getSimGenerator().exponential(stacjaPaliw.getMi());
					new BlokadaTankowania(stacjaPaliw, dt);
				}
			}
		}
		else{
			double dt = stacjaPaliw.getSimGenerator().exponential(stacjaPaliw.getMi());
			new BlokadaTankowania(stacjaPaliw, dt);
		}
	}
}
