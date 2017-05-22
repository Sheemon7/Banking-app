package banking.app;
import banking.app.entities.Account;
import banking.app.entities.PaymentPlace;
import banking.app.entities.Transaction;
import banking.app.jpadatabase.*;
import banking.app.util.EntityNotFoundException;
import banking.app.util.IncorrectAccountPasswordException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.math.BigDecimal;

public class Gui extends Application{

    private Scene sceneLogin, sceneOverview, sceneAccountDecision;
    private TableView table = new TableView();
    private TransactionDAO transactionDAO = TransactionDAO.getInstance();
    private PersonDAO personDAO = PersonDAO.getInstance();
    private AccountDAO accountDAO = AccountDAO.getInstance();
    private Account loggedAccount = null;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Banking app");
        sceneLogin = createLoginScene(primaryStage);
        sceneOverview = createOverviewScene(primaryStage);
        sceneAccountDecision = createAccountDecisionScene(primaryStage);

        primaryStage.setScene(sceneLogin);
        primaryStage.show();
    }

    public Scene createOverviewScene(Stage stage){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text loginTitle = new Text("Enter your bank account and password");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(loginTitle, 1, 0, 3, 1);

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(5);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(90);
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(5);

        grid.getColumnConstraints().addAll(column0,column1, column2);

        //create table
        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("messageToSender"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("messageToSender"));

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("messageToSender"));

        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionDAO.getEntitiesList());
        table.setItems(transactions);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);




        grid.add(table, 1,1,4,3);

        return new Scene(grid, 800, 600);
    }

    public Scene createLoginScene(Stage stage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text loginTitle = new Text("Enter your bank account and password");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(loginTitle, 0, 0, 2, 1);

        Label accountId = new Label("Account Id:");
        grid.add(accountId, 0, 1);
        TextField idTextField = new TextField();
        grid.add(idTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        Button btnAcc = new Button("Create new account");
        HBox hbBtnAcc = new HBox(10);
        hbBtnAcc.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnAcc.getChildren().add(btnAcc);
        grid.add(hbBtnAcc, 0, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget,1, 6);

        Scene scene = new Scene(grid, 800, 600);

        btn.setOnAction(e -> {
            try {
                loggedAccount = accountDAO.loginAccount(Long.parseLong(idTextField.getText()),pwBox.getText());
            } catch (IncorrectAccountPasswordException e1) {
                e1.printStackTrace();
            } catch (EntityNotFoundException e1) {
                e1.printStackTrace();
            }
            if(loggedAccount != null) {
                stage.setScene(sceneOverview);
            }else{
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Wrong password or account Id");
                actiontarget.setText("");
                pwBox.setText("");
            }
        });

        btnAcc.setOnAction(e->{
            stage.setScene(sceneAccountDecision);
        });

        return scene;
    }

    public Scene createAccountDecisionScene(Stage stage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button btn = new Button("New user");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        Button btnAcc = new Button("Existing user");
        HBox hbBtnAcc = new HBox(10);
        hbBtnAcc.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnAcc.getChildren().add(btnAcc);
        grid.add(hbBtnAcc, 0, 4);

        btn.setOnAction(e->{

        });

        btnAcc.setOnAction(e->{

        });

        Scene scene = new Scene(grid, 800, 600);
        return scene;
    }

    public Scene createExistingUserAccountScene(Stage stage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label accountId = new Label("Person Id:");
        grid.add(accountId, 0, 1);
        TextField idTextField = new TextField();
        grid.add(idTextField, 1, 1);

        Button btn = new Button("Create Account");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(e->{

        });

        Scene scene = new Scene(grid, 800, 600);
        return scene;
    }

    public Scene createNewUserAccountScene(Stage stage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button btn = new Button("Create Account");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(e->{

        });

        Scene scene = new Scene(grid, 800, 600);
        return scene;
    }

}
