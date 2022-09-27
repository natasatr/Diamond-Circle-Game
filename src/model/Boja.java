package model;

import java.util.Random;

import javafx.scene.paint.Color;
//samo za figurice
public enum Boja {
	ZUTA(Color.YELLOW), CRVENA(Color.RED), PLAVA(Color.BLUE), ZELENA(Color.GREEN);

	private Color value;

	Boja(Color string) {
		this.value = string;
	}

	public static Boja getRandomColor() {
		Random random = new Random();
		return values()[random.nextInt(values().length)];
	}

	public Color getColor() {
		return value;
	}
}
