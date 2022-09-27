package model;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Polje {
	private Element element;
	private StackPane stack ;

	public Polje() {
		
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element=element;
}
	
	public void initStackPane(Color c) {
		stack.setPrefWidth(57);
		stack.setPrefHeight(57);
		stack.getChildren().clear();
		stack.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
		stack.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}
	
	public void paintElement(Boja boja, String ime) {
		//System.out.println(boja);
		stack.setBackground(new Background(new BackgroundFill(boja.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
		stack.getChildren().add(new Text(ime));
	}
	
	public void resetElement() {
		element=null;
	}

	public StackPane getStack() {
		return stack;
	}

	public void setStack(StackPane stack) {
		this.stack = stack;
	}
}