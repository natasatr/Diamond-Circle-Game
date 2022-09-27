package model.karte;

public class ObicnaKarta extends Karta {

	private int brojPolja;

	public ObicnaKarta(String naziv) {
		super(naziv);
		// TODO Auto-generated constructor stub
	}

	public ObicnaKarta(String naziv, int brojPolja) {
		super(naziv);
		this.brojPolja = brojPolja;
	}

	public int getBrojPolja() {
		return brojPolja;
	}

	public void setBrojPolja(int brojPolja) {
		this.brojPolja = brojPolja;
	}

	@Override
	public String toString() {
		return super.toString() + brojPolja;
	}

}
