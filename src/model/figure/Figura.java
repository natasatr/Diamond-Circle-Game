package model.figure;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import model.Boja;
import model.Element;

public abstract class Figura extends Element {

	private String ime;
	private Boja boja;
	private  int pomjeraj=1;
	private Boolean kraj = false;
	
	private List<Integer> trenutnePozicijeMatrice = new ArrayList<>();
	

	public Figura() {}
	
	public Figura(String ime, Boja boja) {
		this.ime = ime;
		this.boja = boja;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}

	public Boja getBoja() {
		return boja;
	}

	public void setBoja(Boja boja) {
		this.boja = boja;
	}
	
	public void setPomjeraj(int pomjeraj) {
		this.pomjeraj=pomjeraj;
	}

	public Boolean getKraj() {
		return kraj;
	}

	public void setKraj(Boolean kraj) {
		this.kraj = kraj;
	}

	public int getPomjeraj() {
		return pomjeraj;
	}

	public List<Integer> getTrenutnePozicijeMatrice() {
		return trenutnePozicijeMatrice;
	}

	public void setTrenutnePozicijeMatrice(List<Integer> trenutnePozicijeMatrice) {
		this.trenutnePozicijeMatrice = trenutnePozicijeMatrice;
	}
	
	
}
