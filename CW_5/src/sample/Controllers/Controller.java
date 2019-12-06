package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button authSignInButton;

    @FXML
    void initialize() {

        authSignInButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String loginPassword = login_field.getText().trim();

            if (!loginText.equals("") && !loginPassword.equals("")) {
                loginUsers(loginText, loginPassword);
            } else {
                System.out.println("Empty login and password");
            }
        });
        //при нажатии на кнопку "Зарегистрироваться"
        loginSignUpButton.setOnAction(event -> {
            //переход в окно регистрации
            loginSignUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/signUp.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot(); // путь к файлу, который необходимо загрузить
            Stage stage = new Stage(); // выделяем память
            stage.setScene(new Scene(root));
            stage.showAndWait(); // подождать и отобразить окно
        });
    }

    private void loginUsers(String loginText, String loginPassword) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        dbHandler.getUser(user);

        //принимаем ResultSet
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;

        try {
            while (result.next()) {
                counter++;
            }
        } catch (SQLException e) {
                e.printStackTrace();
            }

        if(counter>=1) {
            System.out.println("User found");
        }
    }
}
