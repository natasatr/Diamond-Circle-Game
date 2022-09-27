package model.figure;
import model.Boja;
import model.interfejsi.SavladajRupuInterface;

public class SuperBrzaFigura extends Figura implements SavladajRupuInterface {

	public SuperBrzaFigura(String ime, Boja boja) {
		super(ime, boja);
		setPomjeraj(this.getPomjeraj()*2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPomjeraj(int pomjeraj) {
		super.setPomjeraj(pomjeraj * 2);
	}

	@Override
	public String toString() {
		return this.getIme();
	}

}
