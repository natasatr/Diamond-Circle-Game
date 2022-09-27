package kontroleri;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import application.Main;

public class FajloviKontroler implements Initializable {

	@FXML
	Accordion filesSaved;

	public List<String> readFile(Path p) {

		try {
			List<String> txt = Files.readAllLines(p);
			return txt;
		} catch (IOException e) {
			 Main.LOGGER.log(Level.SEVERE, e.toString(), e);
			 return null;
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File files = new File(Main.getKONACNA_PUTANJA().toString());

		File[] listOFfiles = files.listFiles();

		for (File fl : listOFfiles) {
			TitledPane titledPane = new TitledPane();
			String records = "";
			String newLine = System.getProperty("line.separator");
			String fileName = fl.getName();
			List<String> recLine = readFile(fl.toPath());
			for (String s : recLine) {

				records += s;
				records += newLine;

			}
			titledPane.setText(fileName);
			titledPane.setContent(new Text(records));
			titledPane.setWrapText(true);

			filesSaved.getPanes().add(titledPane);

		}

	}

}