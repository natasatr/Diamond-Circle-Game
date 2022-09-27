package model.karte;

public abstract class Karta {
	private String naziv; // putanja do slike

	public Karta(String naziv) {
		this.naziv = naziv;
	}

	@Override
	public String toString() {
		return naziv;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

}
