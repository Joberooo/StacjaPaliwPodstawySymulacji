package sample;

import dissimlab.monitors.MonitoredVar;
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimObj;

public class StacjaPaliw extends BasicSimObj{
	
	private int maxLiczbaKlientow;
	private int obecnaLiczbaKlientow;
	private int myjniaTak;
	private int myjniaNie;
	private int tylkoMyjnia;
	private double mi;
	private double normalOczekiwana;
	private double normalOdchylenie;
	private double generatorMin;
	private double generatorMax;
	private double erlang;
	
	private SimGenerator simGenerator;

	private Kolejka kolBuffor;
	private Kolejka kolBenzyna;
	private Kolejka kolOn;
	private Kolejka kolLpg;
	private Kolejka kolKasa;
	private Kolejka kolBlokada;
	private Kolejka kolMyjnia;
	private Kolejka kolBlokadaTankowanie;
	
	private Dystrybutory dysBenzyna;
	private Dystrybutory dysOn;
	private Dystrybutory dysLpg;
	private Dystrybutory dysKasa;

	private Myjnia myjnia;

	public MonitoredVar[] mvSize;
	public MonitoredVar[] mvTime;
	
	public StacjaPaliw(int maxLiczbaKlientow, double mi, double normalOczekiwana, double normalOdchylenie, double generatorMin, double generatorMax, double erlang, int[] wielkosciKolejek, int[] wielkoscDystrybutorow, MonitoredVar[] mvSize, MonitoredVar[] mvTime) {
		this.maxLiczbaKlientow = maxLiczbaKlientow;
		this.obecnaLiczbaKlientow = 0;
		this.mi = mi;
		this.normalOczekiwana = normalOczekiwana;
		this.normalOdchylenie = normalOdchylenie;
		this.generatorMin = generatorMin; 
		this.generatorMax = generatorMax;
		this.erlang = erlang;

		this.tylkoMyjnia = 0;
		
		this.simGenerator = new SimGenerator();

		this.mvSize = mvSize;
		this.mvTime = mvTime;

		this.kolBuffor = new Kolejka(wielkosciKolejek[0], "Buffor");
		this.kolBenzyna = new Kolejka(wielkosciKolejek[1], "Benzyna", mvSize[0]);
		this.kolOn = new Kolejka(wielkosciKolejek[2], "ON", mvSize[1]);
		this.kolLpg = new Kolejka(wielkosciKolejek[3], "LPG", mvSize[2]);
		this.kolKasa = new Kolejka(wielkosciKolejek[4], "Kasa");
		this.kolBlokada = new Kolejka(wielkosciKolejek[4], "Blokada");
		this.kolMyjnia = new Kolejka(wielkosciKolejek[5], "Myjnia", mvSize[3]);
		this.kolBlokadaTankowanie = new Kolejka(wielkoscDystrybutorow[0] + wielkoscDystrybutorow[1] + wielkoscDystrybutorow[2], "BlokadaTankowania");
		
		this.dysBenzyna = new Dystrybutory(wielkoscDystrybutorow[0], "Benzyna");
		this.dysOn = new Dystrybutory(wielkoscDystrybutorow[1], "ON");
		this.dysLpg = new Dystrybutory(wielkoscDystrybutorow[2], "LPG");
		this.dysKasa = new Dystrybutory(wielkoscDystrybutorow[3], "Kasa");

		this.myjnia = new Myjnia();
	}
	
	public int getMaxLiczbaKlientow() {
		return this.maxLiczbaKlientow;
	}
	
	public void incObecnaLiczbaKlientow() {
		this.obecnaLiczbaKlientow++;
	}
	
	public int getObecnaLiczbaKlientow() {
		return this.obecnaLiczbaKlientow;
	}
	
	public int getMyjniaTak() {
		return this.myjniaTak;
	}
	
	public void incMyjniaTak() {
		this.myjniaTak++;
	}
	
	public int getMyjniaNie() {
		return this.myjniaNie;
	}
	
	public void incMyjniaNie() {
		this.myjniaNie++;
	}

	public int getTylkoMyjnia() { return this.tylkoMyjnia; }

	public void incTylkoMyjnia() { this.tylkoMyjnia++; }
	
	public double getMi() {
		return this.mi;
	}

	public double getNormalOczekiwana() {
		return this.normalOczekiwana;
	}
	
	public double getNormalOdchylenie() {
		return this.normalOdchylenie;
	}
	
	public double getGeneratorMin() {
		return this.generatorMin;
	}
	
	public double getGeneratorMax() {
		return this.generatorMax;
	}

	public double getErlang() { return  this.erlang; }
	
	public SimGenerator getSimGenerator() {
		return this.simGenerator;
	}

	public Kolejka getKolBuffor() { return kolBuffor; }
	
	public Kolejka getKolBenzyna() {
		return kolBenzyna;
	}

	public Kolejka getKolOn() {
		return kolOn;
	}

	public Kolejka getKolLpg() {
		return kolLpg;
	}

	public Kolejka getKolKasa() {
		return kolKasa;
	}

	public Kolejka getKolBlokada() { return  kolBlokada; }

	public Kolejka getKolMyjnia() {
		return kolMyjnia;
	}

	public Kolejka getKolBlokadaTankowanie() { return  kolBlokadaTankowanie; }

	public Dystrybutory getDysBenzyna() {
		return dysBenzyna;
	}

	public Dystrybutory getDysOn() {
		return dysOn;
	}

	public Dystrybutory getDysLpg() {
		return dysLpg;
	}

	public Dystrybutory getDysKasa() {
		return dysKasa;
	}

	public Myjnia getMyjnia() { return  myjnia; }
}
