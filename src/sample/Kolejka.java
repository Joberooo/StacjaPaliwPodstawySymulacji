package sample;

import dissimlab.monitors.MonitoredVar;

import java.util.ArrayList;

public class Kolejka extends ArrayList<Klient>{
	
	private int pojemnosc;
	
	private int licznikKlientow;
	private int licznikStrat;
	private String nazwa;

	MonitoredVar mvSize;
	
	public Kolejka(int pojemnosc, String nazwa, MonitoredVar mvSize) {
		this.pojemnosc = pojemnosc;
		this.nazwa = nazwa;
		this.mvSize = mvSize;
	}

	public Kolejka(int pojemnosc, String nazwa) {
		this.pojemnosc = pojemnosc;
		this.nazwa = nazwa;
	}

	public boolean addK(Klient kl) {
		if(pojemnosc > size()) {
			this.licznikKlientow++;
			return add(kl);
		}
		else {
			this.licznikStrat++;
			return false;
		}
	}
	
	public int getPojemnosc() {
		return this.pojemnosc;
	}
	
	public int getLicznikKlientow() {
		return this.licznikKlientow;
	}
	
	public int getLicznikStrat() {
		return this.licznikStrat;
	}

	public void incLicznikStrat() { this.licznikStrat++; }
	
	public String getNazwa() {
		return this.nazwa;
	}
}
