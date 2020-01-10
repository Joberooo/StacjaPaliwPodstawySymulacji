package sample;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class StworzenieKlienta extends BasicSimEvent<StacjaPaliw, Object>{
	
	public StworzenieKlienta(StacjaPaliw entity, double delay) throws SimControlException {
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
		StacjaPaliw stacjaPaliw= getSimObj();
		Kolejka kolejka = stacjaPaliw.getKolBuffor();
		
		stacjaPaliw.incObecnaLiczbaKlientow();

		Klient klient = new Klient(stacjaPaliw.getObecnaLiczbaKlientow(), simTime());
		double losowanie = stacjaPaliw.getSimGenerator().uniform(stacjaPaliw.
                getGeneratorMin(), stacjaPaliw.getGeneratorMax());
		klient.losujMyjnia(losowanie, stacjaPaliw.getGeneratorMax());
		if(klient.getChceDoMyjni())
		{
			losowanie = stacjaPaliw.getSimGenerator().uniform(stacjaPaliw.getGeneratorMin(), stacjaPaliw.getGeneratorMax());
			klient.losujTankowanie(losowanie, 1, stacjaPaliw.getGeneratorMin(), stacjaPaliw.getGeneratorMax());
		}
		else
		{
			losowanie = stacjaPaliw.getSimGenerator().uniform(stacjaPaliw.getGeneratorMin(), stacjaPaliw.getGeneratorMax());
			klient.losujTankowanie(losowanie, 0, stacjaPaliw.getGeneratorMin(), stacjaPaliw.getGeneratorMax());
		}

		if(kolejka.addK(klient)){			
			System.out.printf("%.3f", simTime());
			System.out.printf(":: Wstawienie klienta nr = " + klient.getId() + " do kolejki: " + kolejka.getNazwa() + ".\tMyjnia: " + klient.getChceDoMyjni() + ". Tankowanie: " + klient.getTankowanie() + ".\n");
			System.out.printf("Tankowanie: 0 = nie chce tankowac, 1 = Benzyna, 2 = ON, 3 = LPG\n");
			if(klient.getChceDoMyjni()) stacjaPaliw.incMyjniaTak();
			else if(!klient.getChceDoMyjni()) stacjaPaliw.incMyjniaNie();
		}
		else {
			System.out.printf("%.3f", simTime());
			System.out.printf(":: Strata klienta nr = " + klient.getId() + " w kolejce: " + kolejka.getNazwa() + ".   \tMyjnia: " + klient.getChceDoMyjni() + ". Tankowanie: " + klient.getTankowanie() + ".\n");
			System.out.printf("Tankowanie: 0 = nie chce tankowac, 1 = Benzyna, 2 = ON, 3 = LPG\n");
			if(klient.getTankowanie() == 0){
				stacjaPaliw.getKolMyjnia().incLicznikStrat();
			}
			else if(klient.getTankowanie() == 1){
				stacjaPaliw.getKolBenzyna().incLicznikStrat();
			}
			else if(klient.getTankowanie() == 2){
				stacjaPaliw.getKolOn().incLicznikStrat();
			}
			else if(klient.getTankowanie() == 3){
				stacjaPaliw.getKolLpg().incLicznikStrat();
			}
		}
		
		if(stacjaPaliw.getMaxLiczbaKlientow() > stacjaPaliw.getObecnaLiczbaKlientow()) {
			double dt = stacjaPaliw.getSimGenerator().normal(stacjaPaliw.getNormalOczekiwana(), stacjaPaliw.getNormalOdchylenie());
			
			new StworzenieKlienta(stacjaPaliw, Math.abs(dt));
		}

		if(kolejka.size() > 0) new DodawanieStanowiska(stacjaPaliw, 0);
		System.out.println("Buffor sytuacja: " + kolejka.size() + "/" + kolejka.getPojemnosc());
	}
}
