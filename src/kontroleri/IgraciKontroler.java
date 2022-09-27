package kontroleri;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IgraciKontroler implements Initializable {

	@FXML
	private VBox vBoxID;

	@FXML
	private Button btnPokreni;

	private static PocetniKontroler pk;
	private List<String> imenaIgraca = new ArrayList<>();
	private static List<String> jedinstveniIgraci = new ArrayList<>();

	private Alert a = new Alert(AlertType.NONE);

	public static int BROJIGRACA = pk.getBrojIgraca();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		postaviTxtField();
		Platform.runLater(() -> btnPokreni.requestFocus());

		for (String a : imenaIgraca) {
			System.out.println("BLAAA BLA " + a);
		}

	}

	// postavi onoliko txtField koliko je brojIgraca iz prethodnog prozora

	public void postaviTxtField() {

		for (int i = 0; i < BROJIGRACA; i++) {
			TextField field = new TextField("");

			vBoxID.getChildren().add(field);
		}
	}

	public void buttonPress(ActionEvent e) {

		for (Node node : vBoxID.getChildren()) {
			TextField txt = (TextField) node;

			imenaIgraca.add(txt.getText().toString());

		}

		jedinstveniIgraci = imenaIgraca.stream().distinct().collect(Collectors.toList());

		if (imenaIgraca.size() != jedinstveniIgraci.size()) { // ako nisu iste velicine, onda imena nisu jedinstena, a
																// moglo je i na drugi nacin oh joj, popraviti ovo!
			a.setAlertType(AlertType.ERROR);
			a.setContentText("Imena moraju biti razlicita");
			a.show();
		} else {
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(getClass().getResource("/pogledi/Glavni.fxml"));
			Parent root = null;
			try {

				root = (Parent) loader.load();

			} catch (IOException e1) {

				// TODO Auto-generated catch block
				 Main.LOGGER.log(Level.SEVERE, e1.toString(), e1);
			}

			GlavniKontroler glavniCont = loader.getController();
			Main.setKontroler3(glavniCont);
			Stage primStage = Main.getPrimaryStage();
			primStage.setScene(new Scene(root, 980, 800));
			primStage.setResizable(false);
			primStage.show();
		}

	}

	public List<String> getImenaIgraca() {
		return imenaIgraca;
	}

	public void setImenaIgraca(List<String> imenaIgraca) {
		this.imenaIgraca = imenaIgraca;
	}

	public static List<String> getJedinstveniIgraci() {
		return jedinstveniIgraci;
	}

	public void setJedinstveniIgraci(List<String> jedinstveniIgraci) {
		this.jedinstveniIgraci = jedinstveniIgraci;
	}

}
