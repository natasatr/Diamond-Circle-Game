package model.figure;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import application.Main;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import kontroleri.GlavniKontroler;
import model.Mapa;
import model.Polje;

public class DuhFigura extends Figura implements Runnable {

	private int brojDijamanata;
	private Random rand = new Random();
	private List<Polje> polja = new ArrayList<>();

	public DuhFigura() {
		// super();
	}

	@Override
	public void run() {
		// int x, y;
		while (!GlavniKontroler.gameEnded) {
			if(!GlavniKontroler.running) {
				synchronized (GlavniKontroler.pauza) {
					try {
						GlavniKontroler.pauza.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					
					}
					System.out.println("POKRENO SE!!!");
					
				}
			}
			try {
				brojDijamanata = rand.nextInt((Mapa.getDIMENZIJA() - 1)) + 2;
				while (brojDijamanata > 0) {

					int x = new Random().nextInt(GlavniKontroler.list.size());
					int broj = GlavniKontroler.list.get(x) -1;
					if (Main.getMapa().getMapa()[broj/Mapa.getDIMENZIJA()][broj%Mapa.getDIMENZIJA()].getElement() == null) {
						polja.add(Main.getMapa().getMapa()[broj/Mapa.getDIMENZIJA()][broj%Mapa.getDIMENZIJA()]);
						Main.getMapa().getMapa()[broj/Mapa.getDIMENZIJA()][broj%Mapa.getDIMENZIJA()].setElement(new Dijamant());
						brojDijamanata--;

						Platform.runLater(() -> {
							(Main.getMapa().getMapa()[broj/Mapa.getDIMENZIJA()][broj%Mapa.getDIMENZIJA()]).initStackPane(Color.WHITE);
						});
					}
				}

				Thread.sleep(5000);
				for (Polje p : polja) {
					p.resetElement();
					Platform.runLater(() -> {
						p.initStackPane(Color.GRAY);
					});
				}
				polja.clear();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				 Main.LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
	}

}
