package ru.alex.phonebook.fx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhoneBookLauncher extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("start");
        primaryStage.setTitle("Телефонная книга");
        //FXMLLoader.load(clazz.getResource("/" + clazz.getName().replace('.', '/') + ".fxml"))
        //primaryStage.setScene(new PhoneBookMain());
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/" + PhoneBookMain.class.getName().replace('.', '/') + ".fxml")));
        //scene.getStylesheets().add("file:D:/SVNAll/MyProjects/PhoneBook/styles/JMetroDarkTheme.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        PhoneBookLauncher.primaryStage = primaryStage;
	}

    public static void start(String[] args) {
		launch(args);
	}
}
