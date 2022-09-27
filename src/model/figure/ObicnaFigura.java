package model.figure;
import model.Boja;

public class ObicnaFigura extends Figura{

	public ObicnaFigura(String ime, Boja boja) {
		super(ime, boja);
		setPomjeraj(1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return this.getIme();
	}
}
