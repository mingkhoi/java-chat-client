package application.views;

import application.App;
import application.models.Conversation;
import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MessagePage extends BorderPane {

  @FXML
  SearchBar searchbar;

  @FXML
  Button start_conversation_button;

  @FXML
  VBox conversation_vbox;

  int conversationIndex = 0;

  public MessagePage() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/messagepage.fxml"));
    this.getStylesheets().add(getClass().getResource("/styles/messagepage_style.css").toExternalForm());
    loader.setRoot(this);
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    managedProperty().bind(visibleProperty());

    BooleanProperty firstTime = new SimpleBooleanProperty(true);

    searchbar.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue && firstTime.get()) {
        this.requestFocus(); // Delegate the focus to container
        firstTime.setValue(false); // Variable value changed for future references
      }
    });

    start_conversation_button.setOnAction(actionEvent -> {
      new Thread(() -> {
        try {
          App._conversationInstance.addNewConversation(searchbar.getText());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();
    });

    new Thread(() -> {
      try {
        App._conversationInstance.loadConversation();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();

    new Thread(() -> {
      try {
        App._messageInstace.loadMessage();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }

  public static MessagePage _instance;

  public static MessagePage getInstance() {
    if (_instance == null)
      _instance = new MessagePage();
    return _instance;
  }

  public int getConversationIndex() {
    return conversationIndex;
  }

  public void setConversationIndex(int i) {
    conversationIndex = i;
  }

  public void addConversation(Conversation conversation) {
    ConversationCell cell = new ConversationCell();
    cell.setUsername(conversation.getReceiver());

    new Thread(() -> {
      try {
        App._userInstance.requestConversationSignature(conversation.getReceiver(), cell);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }).start();

    cell.setOnMouseClicked(mouseEvent -> {
      new Thread(() -> {
        try {
          App._messageInstace.loadFromConversation(conversation.getReceiver());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();
    });
    conversation_vbox.getChildren().add(0, cell);
  }

  ChatRoom currentRoom;

  public ChatRoom getRoom() {
    return currentRoom;
  }

  public void openChatRoom(String name) {
    ChatRoom room = new ChatRoom(name);
    currentRoom = room;
    this.setCenter(room);
  }
}
