package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class Blokada extends BasicSimEvent<StacjaPaliw, Object>{

	public Blokada(StacjaPaliw entity, double delay) throws SimControlException {
		super(entity, delay);
	}

	@Override
	protected void onInterruption() {}

	@Override
	protected void onTermination() {}

	@Override
	protected void stateChange() throws SimControlException {
		StacjaPaliw stacjaPaliw = getSimObj();

		Kolejka kolejka = stacjaPaliw.getKolBlokada();

		if(stacjaPaliw.getKolMyjnia().size() < stacjaPaliw.getKolMyjnia().getPojemnosc()) {
			if (kolejka.size() > 0) {
				Klient klient = kolejka.remove(0);

				System.out.printf("%.3f", simTime());
				System.out.print(":: Odblokowanie klienta nr = " + klient.getId() + ". Myjnia: " + klient.getChceDoMyjni() + ".\n");

				if (stacjaPaliw.getKolMyjnia().addK(klient)) {
					stacjaPaliw.getKolMyjnia().mvSize.setValue(stacjaPaliw.getKolMyjnia().size());
					stacjaPaliw.getDysKasa().decZajeteDystrybutory();

					System.out.printf("%.3f", simTime());
					System.out.print(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + stacjaPaliw.getKolMyjnia().getNazwa() + ". Myjnia: " + klient.getChceDoMyjni() + ".\n");
                    if(!stacjaPaliw.getMyjnia().getZajete()) new StartMycia(stacjaPaliw, 0);
				}
					if(kolejka.size() > 0){
					double dt = stacjaPaliw.getSimGenerator().exponential(stacjaPaliw.getMi());
					new Blokada(stacjaPaliw, dt);
				}
			}
		}
		else{
			double dt = stacjaPaliw.getSimGenerator().exponential(stacjaPaliw.getMi());
			new Blokada(stacjaPaliw, dt);
		}
	}
}
