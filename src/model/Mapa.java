package model;

import kontroleri.PocetniKontroler;

public class Mapa {
	// nemoj zaboraviti na kraju sve konstante ubaciti u config fajl
	private static final int MINDIMENZIJA=7;
	private static final int MAXDIMENZIJA=10;
	private static final int MINBROJIGRACA=2;
	private static final int MAXBROJIGRACA=4;
	private static  int DIMENZIJA;
	private Polje mapa[][];
	private int vrsta;
	private int kolona;

	public Mapa() {
		DIMENZIJA=PocetniKontroler.getDimenzijaMatrice();
		
		vrsta=DIMENZIJA;
		kolona=DIMENZIJA;
		
		//System.out.println("Dimenzija " +DIMENZIJA + " " + vrsta + " " +kolona);
		mapa = new Polje[vrsta][kolona];
		for(int i=0;i<vrsta;i++) {
			for(int j=0;j<kolona;j++) {
				mapa[i][j] = new Polje();
			}
		}
		
	}
	
	public Polje[][] getMapa() {
		return mapa;
	}

	
	public void setMapa(Polje[][] mapa) {
		this.mapa = mapa;
	}

	public static int getDIMENZIJA() {
		return DIMENZIJA;
	}

	public  static void setDIMENZIJA(int dIMENZIJA) {
		DIMENZIJA = dIMENZIJA;
	}

	public int getVrsta() {
		return vrsta;
	}

	public void setVrsta(int vrsta) {
		this.vrsta = vrsta;
	}

	public int getKolona() {
		return kolona;
	}

	public void setKolona(int kolona) {
		this.kolona = kolona;
	}

	public static int getMindimenzija() {
		return MINDIMENZIJA;
	}

	public static int getMaxdimenzija() {
		return MAXDIMENZIJA;
	}

	public static int getMinbrojigraca() {
		return MINBROJIGRACA;
	}

	public static int getMaxbrojigraca() {
		return MAXBROJIGRACA;
	}
	
	


}