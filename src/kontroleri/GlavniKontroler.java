package kontroleri;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.stream.Stream;
import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Igrac;
import model.Mapa;
import model.Polje;
import model.Rupa;
import model.figure.DuhFigura;
import model.figure.Figura;
import model.karte.Spil;

public class GlavniKontroler implements Initializable {

	@FXML
	private GridPane gridPane;

	@FXML
	private Label lblIgracJedan;

	@FXML
	private ImageView imageView;

	@FXML
	private Label vrijeme;

	@FXML
	private Label refreshLabel;

	@FXML
	private Label brojIgre;

	@FXML
	private Button prikazFajlova;

	@FXML
	private Button kreniStani;
	
	@FXML 
	private VBox vboxFigura;
	
	

	private static int BROJIGRACA = PocetniKontroler.getBrojIgraca();
	private static int dimenzija = PocetniKontroler.getDimenzijaMatrice();
	private Mapa mapa;

	String linija = "";
	public static boolean gameEnded = false;
	public static boolean running = false;
	// ovo su samo stringovi ocitani za txtField-a
	private List<String> igraci = new ArrayList<>();
	// ovu su konacni igraci
	private List<Igrac> igracii = new ArrayList<>();
	public List<Thread> threadIgraci = new ArrayList<>();
	public static List<Integer> list = new ArrayList<Integer>();
	private List<Button> buttonFigure = new ArrayList<>();

	public List<Figura> figurice = new ArrayList<>();
	public static Object pauza = new Object();
	

	@FXML
	VBox detaljiFigura;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		setGrid();
		vrijemeTrajanjaIgre();
		Main.setSpil(new Spil(imageView));
		setIgrace();
		figureMapa();
		postaviDuhFiguru();
		setujBrojIgre();
		prikazFajlova.setDisable(true);

	}

	public void setIgrace() {

		new Thread(() -> {

			igraci = IgraciKontroler.getJedinstveniIgraci();
			String sviZajedno = String.join(",", igraci);

			lblIgracJedan.setText(sviZajedno);
			String[] params = sviZajedno.split(",");
			for (int i = 0; i < params.length; i++) {
				Igrac igrac = new Igrac(params[i]);
				igracii.add(igrac);
			}
			for (Igrac i : igracii) {
				Thread t = new Thread(i);
				threadIgraci.add(t);
			}

			for (int i = 0; i < threadIgraci.size(); i++) {
				threadIgraci.get(i).start();

			}
			int i = 0;
			while (!gameEnded) {
				if (!GlavniKontroler.running) {
					synchronized (GlavniKontroler.pauza) {
						try {
							GlavniKontroler.pauza.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							 Main.LOGGER.log(Level.SEVERE, e.toString(), e);

						}
						System.out.println("POKRENO SE!!!");

					}
				}
				if (i % threadIgraci.size() == 0) {
					i = 0;
				}

				for (Igrac igrac : igracii) {
					igrac.setNaPotezu(true);

					synchronized (igrac) {
						igrac.notify();
						i++;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						 Main.LOGGER.log(Level.SEVERE, e.toString(), e);
					}
					if (igrac.isPostaviRupe()) {
						for (int k = 0; k < mapa.getDIMENZIJA(); k++) {
							for (int j = 0; j < Mapa.getDIMENZIJA(); j++) {
								if (Main.getMapa().getMapa()[k][j].getElement() instanceof Rupa) {
									Polje polje = Main.getMapa().getMapa()[k][j];
									polje.resetElement();
									igrac.setPostaviRupe(false);
									Platform.runLater(() -> {
										polje.initStackPane(Color.GRAY);
									});
								}
							}

						}
					}
				}

				if (igracii.stream().map((ki) -> ki.zavrsioIgru).allMatch((kl) -> kl == true)) {
					gameEnded = true;
					kreniStani.setDisable(true);
					prikazFajlova.setDisable(false);
					ucitajFajl();
				}
			}

		}).start();

	}

	private void setPut() {
		System.out.println("velicina : " + dimenzija);
		int dim = dimenzija;
		int position = dim / 2 + 1;
		int halfRadius = 0;
		int r1 = dim / 2 - 1;
		int r2 = dim / 2;
		int k1 = dim + 1, k2 = dim - 1;
		int korak = k2;
		int l = 1;

		if (dim % 2 != 0) {
			r1 = r2;
		}
		list.add(position);
		for (int j = 0; position != position + r1 * dim; j++, l++) {
			halfRadius = (halfRadius == r1) ? r2 : r1;
			if (l % 4 == 0) {
				r1--;
				r2--;
				halfRadius--;
			}
			if (j != 0 && j % 2 == 0) {
				k1 = -k1;
				k2 = -k2;
			}
			korak = (korak == k1) ? k2 : k1;
			for (int i = 0; i < halfRadius; i++) {
				position += korak;
				list.add(position);
			}

			if (l % 4 == 0) {
				position++;
				list.add(position);
			}
		}
	}

	public void setGrid() {
		setPut();
		
		mapa = Main.getMapa();
		System.out.println(mapa);
		for (int i = 0; i < dimenzija; i++) {

			for (int j = 0; j < dimenzija; j++) {

				StackPane stack = mapa.getMapa()[i][j].getStack();

				stack = new StackPane();
				mapa.getMapa()[i][j].setStack(stack);
				mapa.getMapa()[i][j].initStackPane(Color.GRAY);

				// mapa.getMapa()[i][j].setElement(el);
				GridPane.setRowIndex(stack, i);
				GridPane.setColumnIndex(stack, j);
				gridPane.getChildren().addAll(stack);

			}
		}

		
	}

	public void postaviDuhFiguru() {
		DuhFigura df = new DuhFigura();
		Thread t = new Thread(df);
		t.start();

	}

	public void vrijemeTrajanjaIgre() {

		Timer timer = new Timer();

		TimerTask task = new TimerTask() {
			int i = 0;

			@Override
			public void run() {

				Platform.runLater(() -> {
					vrijeme.setText("Vrijeme trajanja igre " + (++i));
				});

			}
		};

		timer.schedule(task, 0, 1000);

	}

	public void ucitajFajl() {
		try {
			File rezultati = new File(Main.getDirektorijumi().toString());
			File path = new File(Main.getKONACNA_PUTANJA().toString());

			PrintWriter pw = new PrintWriter(
					new BufferedWriter(new FileWriter(path + File.separator + Igrac.getPATERN())));
			File[] files = rezultati.listFiles();
			for (File f : files) {

				try (Stream<String> lines = Files.lines(Paths.get(f.getPath()))) {
					lines.forEach((l) -> {

						pw.println(l);
						
					});
				}
				pw.println("Vrijeme trajanja igre:" +vrijeme.getText());
				pw.flush();
			}
			pw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 Main.LOGGER.log(Level.SEVERE, e.toString(), e);
		}

	}

	public void prikaziFajlove(ActionEvent e) {
		FXMLLoader loader = new FXMLLoader();

		loader.setLocation(getClass().getResource("/pogledi/Fajlovi.fxml"));
		Parent root = null;
		try {

			root = (Parent) loader.load();

		} catch (IOException e1) {

			// TODO Auto-generated catch block
			 Main.LOGGER.log(Level.SEVERE, e1.toString(), e1);
		}

		FajloviKontroler fajloviKontroler = loader.getController();
		Main.setKontroler4(fajloviKontroler);
		Stage primStage = new Stage();
		primStage.setScene(new Scene(root, 700, 700));
		primStage.setResizable(false);
		primStage.show();
	}

	@FXML
	public void pokreniZaustavi(ActionEvent ev) {

		running = !running;
		if (running) {
			synchronized (pauza) {
				pauza.notifyAll();

			}
		}

	}
	
	public  void figureMapa() {
		
		Platform.runLater(()->{
		
		for(Igrac i : igracii) {
			for(Figura f : i.figure) {
				Button b = new Button(i.getImeIgraca()+"-"+f.getIme() +f.getBoja());
				b.setPrefWidth(vboxFigura.getPrefWidth());
				buttonFigure.add(b);
				b.setOnAction(action -> {
		
					Stage stage = new Stage();
					GridPane grid = new GridPane();
					Scene scene = new Scene(grid, 600,400);
					for (int c = 0; c < GlavniKontroler.getDimenzija(); c++) {

						for (int j = 0; j < GlavniKontroler.getDimenzija(); j++) {
							StackPane stek = new StackPane();
							stek.setPrefWidth(27);
							stek.setPrefHeight(27);

							if (f.getTrenutnePozicijeMatrice().contains(c * GlavniKontroler.getDimenzija() + j)) {
								stek.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

							} else {
								stek.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
							}
							stek.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
									BorderWidths.DEFAULT)));
							GridPane.setRowIndex(stek, c);
							GridPane.setColumnIndex(stek, j);
							grid.getChildren().addAll(stek);
						}
					}
					stage.setScene(scene);
					stage.show();
				});
				Region r = new Region();
				r.setPrefHeight(5);
				vboxFigura.getChildren().addAll(b,r);
					
			}
		}});
	}

	public Label getRefreshLabel() {
		return refreshLabel;
	}

	public void setRefreshLabel(Label refreshLabel) {
		this.refreshLabel = refreshLabel;
	}

	public void setujBrojIgre() {
		Platform.runLater(() -> {
			brojIgre.setText(Main.getBrojIgre());
		});
	}

	public static int getDimenzija() {
		return dimenzija;
	}

	public static void setDimenzija(int dimenzija) {
		GlavniKontroler.dimenzija = dimenzija;
	}

}
