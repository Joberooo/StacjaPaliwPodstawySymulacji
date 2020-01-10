package sample;

public class Dystrybutory {

	private int liczbaDystrybutorow;
	private int zajeteDystrybutory;
	private String nazwa;
	
	public Dystrybutory(int liczbaDystrybutorow, String nazwa) {
		this.nazwa = nazwa;
		this.liczbaDystrybutorow = liczbaDystrybutorow;
		this.zajeteDystrybutory = 0;
	}
	
	public String getNazwa() {
		return this.nazwa;
	}
	
	public int getLiczbaDystrybutorow() {
		return this.liczbaDystrybutorow;
	}
	
	public int getZajeteDystrybutory() {
		return this.zajeteDystrybutory;
	}
	
	public void incZajeteDystrybutory() {
		this.zajeteDystrybutory++;
	}
	
	public void decZajeteDystrybutory() {
		this.zajeteDystrybutory--;
	}
}
