package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import kontroleri.GlavniKontroler;
import model.figure.Dijamant;
import model.figure.Figura;
import model.figure.LebdecaFigura;
import model.figure.ObicnaFigura;
import model.figure.SuperBrzaFigura;
import model.karte.Karta;
import model.karte.ObicnaKarta;
import model.karte.Specijalna;

public class Igrac extends Thread {

	public String imeIgraca;
	public List<Figura> figure = new ArrayList<>();

	public Random rand = new Random();
	private boolean naPotezu = false;
	boolean pomjeren = false;
	boolean postaviRupe = false;
	public int brojPoljaSaKarte = 1;

	private static LocalDateTime now = LocalDateTime.now();

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");

	private static String formatDateTime = now.format(formatter);

	private static String PATERN = "IGRA_" + Main.getBrojIgre() + "_" + formatDateTime.toString() + "";

	private int DUZINA_PUTA = GlavniKontroler.list.size();
	public boolean zavrsioIgru = false;

	private static String refreshKarte = "";

	private PrintWriter pw;

	public Igrac(String ime) {
		this.imeIgraca = ime;
		figure = dodijeliFigure();

	}

	public List<Figura> dodijeliFigure() {
		int broj = 4;
		Boja boja = Boja.getRandomColor();// zel
		while (broj > 0) {
			int k = rand.nextInt(3);
			if (k == 0) {
				Figura f = new LebdecaFigura("LF", boja);
				figure.add(f);

				broj--;
			} else if (k == 1) {
				Figura f = new SuperBrzaFigura("SBF", boja);
				figure.add(f);

				broj--;
			} else if (k == 2) {
				Figura f = new ObicnaFigura("OF", boja);
				figure.add(f);
				broj--;
			}

		}

		return figure;
	}

	private void odigrajPotez(int i, int j, Figura f, Karta karta) {
		if (karta instanceof ObicnaKarta) {
			brojPoljaSaKarte = ((ObicnaKarta) karta).getBrojPolja();
			f.setPomjeraj(brojPoljaSaKarte);
			if ((Main.getMapa().getMapa()[i][j]).getElement() == null) {
				int fx = f.getX(), fy = f.getY();
				f.setX(i);
				f.setY(j);
				naPotezu = false;
				(Main.getMapa().getMapa()[i][j]).setElement(f);
				if (fx != i || fy != j) {
					(Main.mapa.getMapa()[fx][fy]).resetElement();
				}
				Platform.runLater(() -> {

					(Main.getMapa().getMapa()[i][j]).paintElement(f.getBoja(), f.getIme());
					if (fx != i || fy != j) {

						(Main.getMapa().getMapa()[fx][fy]).initStackPane(Color.GRAY);
					}
				});
			}
		} else if (karta instanceof Specijalna) {
//			System.out.println("IZABRAO specijal s" + karta.getNaziv() + "broj polja" + brojPoljaSaKarte);
//			System.out.println("TRENUTNE POZICIJE ZA RUPU" + f.getX() + f.getY());
			((Specijalna) karta).kreirajRupe();
			f.setPomjeraj(0);
			postaviRupe = true;

			naPotezu = false;
		}
	}

	public synchronized Karta izvuciKartu() {

		return Main.getSpil().getKarta();
	}

	public void obrisiElement(Figura f) {
		Main.getMapa().getMapa()[f.getX()][f.getY()].resetElement();

		Platform.runLater(() -> {
			(Main.getMapa().getMapa()[f.getX()][f.getY()]).initStackPane(Color.GRAY);
		});
	}

	@Override
	public void run() {

		try {
			pw = new PrintWriter(
					new BufferedWriter(new FileWriter(Main.getDirektorijumi() + File.separator + this.getImeIgraca())));
			int brojFigure = 1;
			pw.println(this + ":");

			for (Figura f : figure) {

				for (int i = 0; i < DUZINA_PUTA && !f.getKraj(); i += f.getPomjeraj()) {
					f.getTrenutnePozicijeMatrice().add(GlavniKontroler.list.get(i) - 1);

					if (!GlavniKontroler.running) {
						synchronized (GlavniKontroler.pauza) {
							try {
								GlavniKontroler.pauza.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								 Main.LOGGER.log(Level.SEVERE, e.toString(), e);

							}
							
						}
					}

					int x = (GlavniKontroler.list.get(i) - 1) / Mapa.getDIMENZIJA();
					int y = (GlavniKontroler.list.get(i) - 1) % Mapa.getDIMENZIJA();

					Karta karta = izvuciKartu();
					refreshKarte = "Na potezu je igrac " + this + " Figura " + brojFigure + " nalazi se na poziciji : "
							+ GlavniKontroler.list.get(i);
					Platform.runLater(() -> {
						Main.getKontroler3().getRefreshLabel().setText(refreshKarte);
					});
					synchronized (Main.mapa.getMapa()[x][y]) {

						if ((Main.mapa.getMapa()[x][y]).getElement() instanceof Dijamant) {

							//f.pomjeraj++;
							f.setPomjeraj(f.getPomjeraj()+1);

						}
						odigrajPotez(x, y, f, karta);

					}

					if (!naPotezu) {
						synchronized (this) {
							try {
								wait();
							} catch (InterruptedException e) {
								 Main.LOGGER.log(Level.SEVERE, e.toString(), e);
							}
						}
					}

				}

				obrisiElement(f);
				String cilj = (!f.getKraj()) ? "da" : "ne";
				pw.println("Figura - " + brojFigure++ + "(" + f.getIme() + "," + f.getBoja() + ") - " + " predjeni put: "
						+ GlavniKontroler.list.toString() + " stigla do cilja : " + cilj);
				pw.flush();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			 Main.LOGGER.log(Level.SEVERE, e1.toString(),e1);
		}

		zavrsioIgru = true;
		System.out.println("end");
	}

	@Override
	public String toString() {
		return "Igrac -  " + imeIgraca;
	}

	public String getImeIgraca() {
		return imeIgraca;
	}

	public void setImeIgraca(String imeIgraca) {
		this.imeIgraca = imeIgraca;
	}

	public List<Figura> getFigure() {
		return figure;
	}

	public void setFigure(List<Figura> figure) {
		this.figure = figure;
	}

	public boolean isNaPotezu() {
		return naPotezu;
	}

	public void setNaPotezu(boolean naPotezu) {
		this.naPotezu = naPotezu;
	}

	public boolean isPostaviRupe() {
		return postaviRupe;
	}

	public void setPostaviRupe(boolean postaviRupe) {
		this.postaviRupe = postaviRupe;
	}

	public static String getPATERN() {
		return PATERN;
	}

	public static void setPATERN(String pATERN) {
		PATERN = pATERN;
	}

}
