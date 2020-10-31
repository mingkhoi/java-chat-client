package application;

import application.controllers.ConversationController;
import application.controllers.MessageController;
import application.controllers.UserController;
import application.models.Conversation;
import application.models.Message;
import application.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    static public String apiUrl = "http://larryjason.com:8081/api/";
    static public String messageSocketUrl = "http://larryjason.com:8081/";

    static public User _user = null;
    static final public UserController _userInstance = new UserController();
    static final public MessageController _messageInstace = new MessageController();
    static final public ConversationController _conversationInstance = new ConversationController();

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/views/login_scene.fxml"));

        Scene scene = new Scene(root,360, 480);
        scene.getStylesheets().add(Main.class.getResource("/styles/login_style.css").toExternalForm());

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        _conversationInstance.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
