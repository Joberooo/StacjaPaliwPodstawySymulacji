package sample;

public class Klient {
	private int id;
	private double czasStart;
	private double czasStop;
	private boolean chceDoMyjni;
	//Tankowanie oznacza czy klient chce tankować i jak tak to co
	// 0 = nie chce tankować
	// 1 = Benzyna
	// 2 = ON
	// 3 = LPG
	private int tankowanie;
	
	public Klient(int id, double czasStart) {
		this.id = id;
		this.czasStart = czasStart;
	}
	
	public void losujMyjnia(double losowanie, double max) {
		double maxx = max * 0.8;
		chceDoMyjni = losowanie > maxx;
	}

	public void losujTankowanie(double losowanie, int myjnia, double min, double max){
		double suma = max - min;
		if(myjnia == 0)
		{
			if(losowanie > min && losowanie < min + (suma * 1 / 3)){
				this.tankowanie = 1;
			}
			else if(losowanie > min + (suma * 1 / 3) && losowanie < min + (suma * 2 / 3)){
				this.tankowanie = 2;
			}
			else if(losowanie > min + (suma * 2 / 3) && losowanie < max){
				this.tankowanie = 3;
			}
		}
		else if(myjnia == 1)
		{
			if(losowanie >= min && losowanie < min + (suma * 1 / 4)){
				this.tankowanie = 0;
			}
			else if(losowanie >= min + (suma * 1 / 4) && losowanie < min + (suma * 2 / 4)){
				this.tankowanie = 1;
			}
			else if(losowanie >= min + (suma * 2 / 4) && losowanie < min + (suma * 3 / 4)){
				this.tankowanie = 2;
			}
			else if(losowanie >= min + (suma * 3 / 4) && losowanie <= max){
				this.tankowanie = 3;
			}
		}
		else
		{
			System.out.println("Wprowadzono zla wartosc! Klient tankuje domyslnie Benzyne.");
			this.tankowanie = 1;
		}
	}
	
	public int getId() {
		return id;
	}

	public double getCzasStart() {
		return czasStart;
	}
	
	public void setCzasStart(double czasStart) {
		this.czasStart = czasStart;
	}

	public double getCzasStop() {
		return czasStop;
	}

	public void setCzasStop(double czasStop) {
		this.czasStop = czasStop;
	}
	
	public boolean getChceDoMyjni() {
		return this.chceDoMyjni;
	}

	public int getTankowanie() { return  this.tankowanie; }
}
