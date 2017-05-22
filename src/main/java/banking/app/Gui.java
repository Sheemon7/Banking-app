package banking.app;

import banking.app.entities.Account;
import banking.app.entities.Person;
import banking.app.entities.Transaction;
import banking.app.gui.NumberTextField;
import banking.app.jpadatabase.AccountDAO;
import banking.app.jpadatabase.CardDAO;
import banking.app.jpadatabase.PersonDAO;
import banking.app.jpadatabase.TransactionDAO;
import banking.app.util.EntityNotFoundException;
import banking.app.util.IncorrectAccountPasswordException;
import com.sun.media.jfxmedia.logging.Logger;
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
import java.math.BigInteger;

/**
 * Created by Tomas on 5/20/2017.
 */


public class Gui extends Application {

    private Scene sceneLogin, sceneOverview, sceneAccountDecision, sceneNewUserAccount, sceneExistingUserAccount;
    private Scene scenePayment;
    private TableView table = new TableView();
    private TransactionDAO transactionDAO = new TransactionDAO();
    private PersonDAO personDAO = new PersonDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private CardDAO cardDAO = new CardDAO();
    private Account loggedAccount = null;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Banking app");
        sceneLogin = createLoginScene(primaryStage);
        sceneOverview = createOverviewScene(primaryStage);
        sceneAccountDecision = createAccountDecisionScene(primaryStage);
        sceneNewUserAccount = createNewUserAccountScene(primaryStage);
        sceneExistingUserAccount = createExistingUserAccountScene(primaryStage);
        scenePayment = createCardPaymentScene(primaryStage);
        primaryStage.setScene(sceneLogin);
        primaryStage.show();
    }

    public Scene createOverviewScene(Stage stage) {
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

        grid.getColumnConstraints().addAll(column0, column1, column2);

        //create table
        table.setEditable(true);
        TableColumn col1 = new TableColumn("First Name");
        col1.setMinWidth(100);
        col1.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("messageToSender"));

        TableColumn col2 = new TableColumn("Last Name");
        col2.setMinWidth(100);
        col2.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("messageToSender"));

        TableColumn col3 = new TableColumn("Email");
        col3.setMinWidth(200);
        col3.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("messageToSender"));

        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionDAO.getEntitiesList());
        table.setItems(transactions);
        table.getColumns().addAll(col1, col2, col3);


        grid.add(table, 1, 1, 4, 3);

        return new Scene(grid, 800, 600);
    }

    public Scene createLoginScene(Stage stage) {
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
        grid.add(actiontarget, 1, 6);

        Scene scene = new Scene(grid, 800, 600);

        btn.setOnAction(e -> {
            try {
                loggedAccount = accountDAO.loginAccount(Long.parseLong(idTextField.getText()), pwBox.getText());
            } catch (IncorrectAccountPasswordException e1) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Wrong account Id");
            } catch (EntityNotFoundException e1) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Wrong password");
            }
            if (loggedAccount != null) {
                stage.setScene(sceneOverview);
            } else {
                actiontarget.setText("Wrong input");
                pwBox.setText("");
            }
        });

        btnAcc.setOnAction(e -> {
            stage.setScene(sceneAccountDecision);
        });

        return scene;
    }

    public Scene createAccountDecisionScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text loginTitle = new Text("Create account for");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(loginTitle, 0, 0, 2, 1);

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

        btn.setOnAction(e -> {
            stage.setScene(sceneNewUserAccount);
        });

        btnAcc.setOnAction(e -> {
            stage.setScene(sceneExistingUserAccount);
        });

        Scene scene = new Scene(grid, 800, 600);
        return scene;
    }





    public Scene createCardPaymentScene(Stage stage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text loginTitle = new Text("Send payment");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(loginTitle, 0, 0, 2, 1);


        Label numLab1 = new Label("Target Account:");
        grid.add(numLab1, 0, 1);
        NumberTextField numField1 = new NumberTextField();
        grid.add(numField1, 1, 1);

        Label numLab2 = new Label("Amount:");
        grid.add(numLab2, 0, 2);
        NumberTextField numField2 = new NumberTextField();
        grid.add(numField2, 1, 2);

        Label numLab3 = new Label("Card:");
        grid.add(numLab3,0,3);
        NumberTextField numField3 = new NumberTextField();
        grid.add(numField3,1,3);

        Label textLab1 = new Label("Message to receiver");
        grid.add(textLab1,0,4);
        TextField textField1 = new TextField();
        grid.add(textField1,1,4);

        Label textLab2 = new Label("Message to sender");
        grid.add(textLab2,0,5);
        TextField textField2 = new TextField();
        grid.add(textField2,1,5);


        Button btn = new Button("Create Account");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(e->{
            Long targetAccount = Long.parseLong(numField1.getText());
            BigDecimal amount = new BigDecimal(numField2.getText());
            Long cardUsed = Long.parseLong(numField3.getText());
            String messageToReceiver = textField1.getText();
            String messageToSender = textField2.getText();

            if(targetAccount != null && amount != null && cardUsed != null){
                //check balance on account
            }


        });



        Scene scene = new Scene(grid, 800, 600);
        return scene;
    }


    public Scene createExistingUserAccountScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text loginTitle = new Text("Create account for existing user");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(loginTitle, 0, 0, 2, 1);

        Label lab1 = new Label("Person Id:");
        grid.add(lab1, 0, 1);
        NumberTextField numField1 = new NumberTextField();
        grid.add(numField1, 1, 1);

        Label lab2 = new Label("Amount:");
        grid.add(lab2, 0, 2);
        NumberTextField numField2 = new NumberTextField();
        grid.add(numField2, 1, 2);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);


        Button btn1 = new Button("Create Account");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 4);

        final Text errorText = new Text();
        grid.add(errorText,1,5);

        btn1.setOnAction(e -> {
            Long ownerId = Long.parseLong(numField1.getText());
            BigDecimal amount = new BigDecimal(numField2.getText());
            String password = pwBox.getText();


            if (ownerId != null && amount != null && password != "") {
                Person owner = null;
                try {
                    owner = personDAO.getEntity(ownerId);
                } catch (EntityNotFoundException e1) {
                    e1.printStackTrace();
                    Logger.logMsg(0, "No owner with given id in database");
                }

                if (owner != null) {
                    Account accountToAdd = new Account(password, owner, amount);
                    accountDAO.saveEntity(accountToAdd);
                    stage.setScene(sceneLogin);
                }
            }else{
                errorText.setFill(Color.FIREBRICK);
                errorText.setText("Please enter all information correctly");
            }
        });

        Scene scene = new Scene(grid, 800, 600);
        return scene;
    }

    public Scene createNewUserAccountScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text loginTitle = new Text("Create account for new user");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(loginTitle, 0, 0, 2, 1);

        Label lab1 = new Label("First name:");
        grid.add(lab1, 0, 1);
        TextField textField1 = new TextField();
        grid.add(textField1, 1, 1);

        Label lab2 = new Label("Second name:");
        grid.add(lab2, 0, 2);
        TextField textField2 = new TextField();
        grid.add(textField2, 1, 2);

        Label lab3 = new Label("Adress:");
        grid.add(lab3, 0, 3);
        TextField textField3 = new TextField();
        grid.add(textField3, 1, 3);

        Label labnum1 = new Label("Amount:");
        grid.add(labnum1, 0, 4);
        NumberTextField numField1 = new NumberTextField();
        grid.add(numField1, 1, 4);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 5);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 5);


        Button btn1 = new Button("Create Account");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 6);

        final Text errorText = new Text();
        grid.add(errorText,1,7);

        btn1.setOnAction(e -> {
            String firstName = textField1.getText();
            String secondName = textField2.getText();
            String address = textField3.getText();
            BigDecimal amount = new BigDecimal(numField1.getText());
            String password = pwBox.getText();


            if (firstName != "" && secondName != "" && address != "" && amount != null && password != "") {
                Person personToAdd = new Person(firstName, secondName, 0, address);
                personDAO.saveEntity(personToAdd);
                Account accountToAdd = new Account(password, personToAdd, amount);
                accountDAO.saveEntity(accountToAdd);
                stage.setScene(sceneLogin);
            }else{
                errorText.setFill(Color.FIREBRICK);
                errorText.setText("Please enter all information correctly");
            }
        });

        Scene scene = new Scene(grid, 800, 600);
        return scene;
    }

}
