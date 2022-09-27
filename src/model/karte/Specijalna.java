package model.karte;

import java.util.Random;

import application.Main;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import kontroleri.GlavniKontroler;
import model.Mapa;
import model.Rupa;
import model.figure.Figura;
import model.interfejsi.SavladajRupuInterface;

public class Specijalna extends Karta {

	private int brojRupa;

	public Specijalna(String naziv) {
		super(naziv);

	}

	public void kreirajRupe() {
		brojRupa = new Random().nextInt(10);
		while (brojRupa > 0) {

			int x = new Random().nextInt(GlavniKontroler.list.size());

			int broj = GlavniKontroler.list.get(x) - 1;
			// Main.mapa.getMapa()[x][y] instanceof Figura
			if ((Main.getMapa().getMapa()[broj / Mapa.getDIMENZIJA()][broj % Mapa.getDIMENZIJA()])
					.getElement() instanceof Figura) {
				Figura f = (Figura) Main.getMapa().getMapa()[broj / Mapa.getDIMENZIJA()][broj % Mapa.getDIMENZIJA()]
						.getElement();
				if (!(f instanceof SavladajRupuInterface)) {
					f.setKraj(true);
				}
			}

			Main.getMapa().getMapa()[broj / Mapa.getDIMENZIJA()][broj % Mapa.getDIMENZIJA()].setElement(new Rupa());

			brojRupa--;

			Platform.runLater(() -> {
				(Main.getMapa().getMapa()[broj / Mapa.getDIMENZIJA()][broj % Mapa.getDIMENZIJA()])
						.initStackPane(Color.BLACK);
			});
		}
	}
}
