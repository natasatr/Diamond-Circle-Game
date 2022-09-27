package application;

import model.Mapa;
import model.karte.Spil;
import kontroleri.*;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	private static Stage primaryStage = new Stage();
	private static PocetniKontroler kontroler1;
	public static IgraciKontroler kontroler2;
	private static GlavniKontroler kontroler3;
	private static FajloviKontroler kontroler4;
	private static Object lock = new Object();
	private static Spil spil;
	//napravi konf fajl za citanje putanje :) 
	private static File file = new File("C:\\Users\\DT User\\eclipse-workspace\\DiamondProjektniZadatak\\src\\resursi");
	private static File direktorijumi = new File(file + File.separator +"rezultati");
	private static File KONACNA_PUTANJA = new File(file + File.separator +"fajlovi");
	private static File LOGGER_PATH= new File(file + File.separator +"logovi");

	public static Mapa mapa;
	private static String brojIgre;
	public static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	public static Handler fileHandler;
	{
		try{
			fileHandler =  new FileHandler(LOGGER_PATH+File.separator+"logs.logs");
			LOGGER.addHandler(fileHandler);
		}catch(IOException e){
			e.printStackTrace();
			
		}
	}
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/pogledi/PocetniProzor.fxml"));
		Parent root = (Parent) loader.load();
		primaryStage.setTitle("Start");
		primaryStage.setScene(new Scene(root, 500, 400));
		primaryStage.setResizable(false);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		procitajBrojIgreiUpisiSledecuvrijednost();
		obrisiStareFajlove();
		launch(args);

	}

	private void setPrimaryStage(Stage stage) {
		Main.primaryStage = stage;
	}

	static public Stage getPrimaryStage() {
		return Main.primaryStage;
	}

	public static PocetniKontroler getKontroler1() {
		return kontroler1;
	}

	public static void setKontroler1(PocetniKontroler kontroler1) {
		Main.kontroler1 = kontroler1;
	}

	public static IgraciKontroler getKontroler2() {
		return kontroler2;
	}

	public static void setKontroler2(IgraciKontroler kontroler2) {
		Main.kontroler2 = kontroler2;
	}

	public static Mapa getMapa() {
		return mapa;
	}

	public static void setMapa(Mapa mapa) {
		Main.mapa = mapa;
	}

	public static GlavniKontroler getKontroler3() {
		return kontroler3;
	}

	public static void setKontroler3(GlavniKontroler kontroler3) {
		Main.kontroler3 = kontroler3;
	}

	public static FajloviKontroler getKontroler4() {
		return kontroler4;
	}

	public static void setKontroler4(FajloviKontroler kontroler4) {
		Main.kontroler4 = kontroler4;
	}

	public static void procitajBrojIgreiUpisiSledecuvrijednost() {

		try {
			PrintWriter pw;
			BufferedReader br = new BufferedReader(new FileReader(file + File.separator + "trenutniBrojIgre.txt"));
			brojIgre = br.readLine();
			int broj = Integer.parseInt(brojIgre);
			pw = new PrintWriter(new FileWriter(file + File.separator + "trenutniBrojIgre.txt"));
			pw.print(++broj);
			pw.flush();
			br.close();
			pw.close();

		} catch (Exception e) {
			
			 Main.LOGGER.log(Level.SEVERE, e.toString(), e);
		}

	}

	public static void obrisiStareFajlove() {
		File[] files = direktorijumi.listFiles();
		for (File f : files) {
			f.delete();
		}
	}

	public static String getBrojIgre() {
		return brojIgre;
	}

	public static void setBrojIgre(String brojIgre) {
		Main.brojIgre = brojIgre;
	}

	public static Object getLock() {
		return lock;
	}

	public static void setLock(Object lock) {
		Main.lock = lock;
	}

	public static Spil getSpil() {
		return spil;
	}

	public static void setSpil(Spil spil) {
		Main.spil = spil;
	}

	public static File getFile() {
		return file;
	}

	public static void setFile(File file) {
		Main.file = file;
	}

	public static File getDirektorijumi() {
		return direktorijumi;
	}

	public static void setDirektorijumi(File direktorijumi) {
		Main.direktorijumi = direktorijumi;
	}

	public static File getKONACNA_PUTANJA() {
		return KONACNA_PUTANJA;
	}

	public static void setKONACNA_PUTANJA(File kONACNA_PUTANJA) {
		KONACNA_PUTANJA = kONACNA_PUTANJA;
	}

}
