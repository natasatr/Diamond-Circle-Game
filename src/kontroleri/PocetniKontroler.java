package kontroleri;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import kontroleri.IgraciKontroler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Mapa;

public class PocetniKontroler implements Initializable {

	@FXML
	private TextField txtFieldDimMat;
	@FXML
	private TextField txtFieldBrIgr;
	@FXML
	private Button btnPokreni;

	@FXML
	private HBox hBoxId = new HBox();

	private static int brojIgraca;
	private static int dimenzijaMatrice;

	private Alert a = new Alert(AlertType.NONE);
	private String TEKST = "";
	private String ERRORPORUKA = "PONOVITE UNOS !";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Platform.runLater(() -> btnPokreni.requestFocus());
	}

	public void buttonPress(ActionEvent e) {

		if ((TEKST.equals(txtFieldDimMat.getText()) && TEKST.equals(txtFieldBrIgr.getText()))
				|| (TEKST.equals(txtFieldDimMat.getText()) || TEKST.equals(txtFieldBrIgr.getText()))) {
			a.setAlertType(AlertType.ERROR);
			a.setContentText(ERRORPORUKA);

			a.show();
		} else {

			dimenzijaMatrice = Integer.parseInt(txtFieldDimMat.getText());
			brojIgraca = Integer.parseInt(txtFieldBrIgr.getText());

			if ((dimenzijaMatrice < Mapa.getMindimenzija() || dimenzijaMatrice > Mapa.getMaxdimenzija())
					|| (brojIgraca < Mapa.getMinbrojigraca() || brojIgraca > Mapa.getMaxbrojigraca())) {
				a.setAlertType(AlertType.ERROR);
				a.setContentText(ERRORPORUKA);

				a.show();
			} else {
				Mapa.setDIMENZIJA(dimenzijaMatrice);
				Main.mapa = new Mapa();
				FXMLLoader loader = new FXMLLoader();

				loader.setLocation(getClass().getResource("/pogledi/IgraciProzor.fxml"));
				Parent root = null;
				try {
					root = (Parent) loader.load();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				IgraciKontroler igraciCont = loader.getController();
				Main.setKontroler2(igraciCont);
				Stage primStage = Main.getPrimaryStage();
				primStage.setScene(new Scene(root, 400, 400));
				primStage.setResizable(false);
				primStage.show();
				// setuj labele za igrace
			}

		}

	}

	public static int getBrojIgraca() {
		return brojIgraca;
	}

	public void setBrojIgraca(int brojIgraca) {
		this.brojIgraca = brojIgraca;
	}

	public static int getDimenzijaMatrice() {
		return dimenzijaMatrice;
	}

	public void setDimenzijaMatrice(int dimenzijaMatrice) {
		this.dimenzijaMatrice = dimenzijaMatrice;
	}

}
