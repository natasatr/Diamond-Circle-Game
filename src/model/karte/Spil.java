package model.karte;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import application.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kontroleri.GlavniKontroler;

public class Spil {

	public List<Karta> karte = new ArrayList<Karta>();

	private ImageView slika;

	public Spil(ImageView image) {
		this.slika = image;

		int j = 1;

		for (int i = 1; i <= 40; i++) {
			if (i % 10 == 0) {
				j++;
			}
			switch (j) {
			case 1: {
				karte.add(new ObicnaKarta("jedan.png", j));
				break;
			}
			case 2: {
				karte.add(new ObicnaKarta("dva.png", j));
				break;
			}
			case 3: {
				karte.add(new ObicnaKarta("tri.png", j));
				break;
			}
			case 4: {
				karte.add(new ObicnaKarta("cetiri.png", j));
				break;
			}
			}

			if (i == 40) {
				for (int k = 40; k < 52; k++) {
					karte.add(new Specijalna("specijalna.png"));
				}
			}
		}

	}

	public void setImage(Karta karta) {
		try {
			System.out.println(karta.getNaziv());
			slika.setImage(new Image(new FileInputStream(Main.getFile() + File.separator + karta.getNaziv())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			 Main.LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}

	public Karta getKarta() {
		int rand = new Random().nextInt(karte.size());
		Karta karta = karte.get(rand);
		setImage(karta);
		return karta;
	}

}
