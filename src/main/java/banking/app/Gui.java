package banking.app;

import banking.app.entities.*;
import banking.app.gui.NumberTextField;
import banking.app.jpadatabase.*;
import banking.app.util.EntityNotFoundException;
import banking.app.util.IncorrectAccountPasswordException;
import com.sun.media.jfxmedia.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.math.BigDecimal;

/**
 * Created by Tomas on 5/20/2017.
 */


public class Gui extends Application {

    private Scene sceneLogin, sceneAccountOverview, sceneAccountDecision, sceneNewUserAccount, sceneExistingUserAccount;
    private Scene scenePayment, sceneCardOverview, sceneATMWithdraw, sceneCardCreation, sceneCardDeletion;


    private TransactionDAO transactionDAO = TransactionDAO.getInstance();
    private PersonDAO personDAO = PersonDAO.getInstance();
    private AccountDAO accountDAO = AccountDAO.getInstance();
    private CardDAO cardDAO = CardDAO.getInstance();
    private CardTypeDAO cardTypeDAO = CardTypeDAO.getInstance();
    private Account loggedAccount = null;
    private Card selectedCard = null;

    @Override
    public void start(Stage primaryStage) {
        try {
            loggedAccount = accountDAO.getEntity((long) 18);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        primaryStage.setTitle("Banking app");
        sceneLogin = createLoginScene(primaryStage);
        sceneAccountOverview = createAccountOverviewScene(primaryStage);
        sceneAccountDecision = createAccountDecisionScene(primaryStage);
        sceneNewUserAccount = createNewUserAccountScene(primaryStage);
        sceneExistingUserAccount = createExistingUserAccountScene(primaryStage);
        scenePayment = createCardPaymentScene(primaryStage);
        sceneCardOverview = createCardOverviewScene(primaryStage);
        sceneATMWithdraw = createATMWithdrawScene(primaryStage);
        sceneCardCreation = createCardCreationScene(primaryStage);
        sceneCardDeletion = createCardDeleteScene(primaryStage);
        primaryStage.setScene(sceneAccountOverview);
        primaryStage.show();
    }

    public Scene createCardOverviewScene(Stage stage){
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text titleText = new Text("Card Overview");
        titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        VBox titleTextContainer = new VBox();
        titleTextContainer.setPrefWidth(800);
        titleTextContainer.getChildren().addAll(titleText);
        titleTextContainer.setAlignment(Pos.CENTER);
        grid.add(titleTextContainer, 0, 0, 4,1);

        Text text1 = new Text("Select card ");
        grid.add(text1,0,1);

        ObservableList<Long> combo1Menu = FXCollections.observableArrayList(loggedAccount.getCardsIds());
        final ComboBox<Long> combo1 = new ComboBox(combo1Menu);


        grid.add(combo1,1,1);

        Button btn = new Button("Select card");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 2, 1);

        Button btn1 = new Button("Go Back");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 2, 8);

        btn.setOnAction(event->{
            try {
                selectedCard = cardDAO.getEntity(combo1.getValue());
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
            sceneCardOverview = createCardOverviewScene(stage);
            stage.setScene(sceneCardOverview);
        });

        btn1.setOnAction(e->{
            selectedCard = null;
            stage.setScene(sceneAccountOverview);
        });


        if(selectedCard != null) {
            Text text2 = new Text("Card ID: ");
            grid.add(text2, 0, 2);
            Text text3 = new Text(selectedCard.getId_card().toString());
            grid.add(text3, 1, 2);

            Text text4 = new Text("Card Type: ");
            grid.add(text4, 0, 3);
            Text text5 = new Text(selectedCard.getCard_type().getTypeName());
            grid.add(text5, 1, 3);

            Text text6 = new Text("Withdrawal limit: ");
            grid.add(text6, 0, 4);
            Text text7 = new Text(selectedCard.getWithdrawalLimit().toString());
            grid.add(text7, 1, 4);

            TableView table = new TableView();

            table.setEditable(true);
            TableColumn<Transaction,Long> col1 = new TableColumn<>("Amount");
            col1.prefWidthProperty().bind(table.widthProperty().divide(6));
            col1.setCellValueFactory(c->c.getValue().getSSAmount().asObject());

            TableColumn<Transaction,String> col2 = new TableColumn<>("Card type");
            col2.prefWidthProperty().bind(table.widthProperty().divide(6));
            col2.setCellValueFactory(c->c.getValue().getSSSenderCardId());

            TableColumn<Transaction,Long> col3 = new TableColumn<>("Receiver ID");
            col3.prefWidthProperty().bind(table.widthProperty().divide(6));
            col3.setCellValueFactory(c->c.getValue().getSSAcountId().asObject());

            TableColumn<Transaction,String> col4 = new TableColumn<>("Message to Sender");
            col4.prefWidthProperty().bind(table.widthProperty().divide(2));
            col4.setCellValueFactory(c->c.getValue().getSSmessageToSender());

            if(loggedAccount != null) {
                ObservableList<Transaction> transactions = FXCollections.observableArrayList(selectedCard.getSentTransactions());
                table.setItems(transactions);
                table.getColumns().addAll(col1, col2, col3, col4);
            }
            grid.add(table, 0, 5,4,1);

        }




        return scene;
    }




    public Scene createAccountOverviewScene(Stage stage) {
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text titleText = new Text("Account Overview");
        titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


        VBox titleTextContainer = new VBox();
        titleTextContainer.setPrefWidth(800);
        titleTextContainer.getChildren().addAll(titleText);
        titleTextContainer.setAlignment(Pos.CENTER);
        grid.add(titleTextContainer, 0, 0, 2,1);



        if(loggedAccount != null){
            Text accountIdText = new Text("Account ID: " + loggedAccount.getId_account());
            accountIdText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
            grid.add(accountIdText,1,1);

            Text balanceText = new Text("Balance: " + loggedAccount.getBalance());
            balanceText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
            grid.add(balanceText,1,2);
        }

        //create table
        TableView table = new TableView();

        table.setEditable(true);
        TableColumn<Transaction,Long> col1 = new TableColumn<>("Amount");
        col1.prefWidthProperty().bind(table.widthProperty().divide(6));
        col1.setCellValueFactory(c->c.getValue().getSSAmount().asObject());

        TableColumn<Transaction,String> col2 = new TableColumn<>("Card type");
        col2.prefWidthProperty().bind(table.widthProperty().divide(6));
        col2.setCellValueFactory(c->c.getValue().getSSSenderCardId());

        TableColumn<Transaction,Long> col3 = new TableColumn<>("Receiver ID");
        col3.prefWidthProperty().bind(table.widthProperty().divide(6));
        col3.setCellValueFactory(c->c.getValue().getSSAcountId().asObject());

        TableColumn<Transaction,String> col4 = new TableColumn<>("Message to Sender");
        col4.prefWidthProperty().bind(table.widthProperty().divide(2));
        col4.setCellValueFactory(c->c.getValue().getSSmessageToSender());

        if(loggedAccount != null) {
            ObservableList<Transaction> transactions = FXCollections.observableArrayList(loggedAccount.getReceivedTransactions());
            table.setItems(transactions);
            table.getColumns().addAll(col1, col2, col3, col4);
        }
        grid.add(table, 1, 3);

        VBox buttonContainer = new VBox();

        Button btn = new Button("New payment");
        btn.setMinWidth(100.0);
        Button btn1 = new Button("ATM withdraw");
        btn1.setMinWidth(100.0);
        Button btn2 = new Button("Card overview");
        btn2.setMinWidth(100.0);
        Button btn3 = new Button("Create card");
        btn3.setMinWidth(100.0);
        Button btn4 = new Button("Delete card");
        btn4.setMinWidth(100.0);
        Button btn5 = new Button("Log out");
        btn5.setMinWidth(100.0);

        buttonContainer.getChildren().addAll(btn,btn1,btn2,btn3,btn4,btn5);
        grid.add(buttonContainer,2,3);

        btn.setOnAction(e->{
            stage.setScene(scenePayment);
        });

        btn1.setOnAction(e->{
            stage.setScene(sceneATMWithdraw);
        });

        btn2.setOnAction(e->{
            stage.setScene(sceneCardOverview);
        });

        btn3.setOnAction(e->{
            stage.setScene(sceneCardCreation);
        });

        btn4.setOnAction(e->{
            stage.setScene(sceneCardDeletion);
        });

        btn5.setOnAction(e->{
            stage.setScene(sceneLogin);
        });



        return scene;
    }

    public Scene createATMWithdrawScene(Stage stage) {
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text loginTitle = new Text("Withdraw Money from ATM");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(loginTitle, 0, 0, 2, 1);

        Text text1 = new Text("Select card: ");
        grid.add(text1,0,1);

        ObservableList<Long> combo1Menu = FXCollections.observableArrayList(loggedAccount.getCardsIds());
        final ComboBox<Long> combo1 = new ComboBox(combo1Menu);
        grid.add(combo1,1,1);

        GridPane withdrawGrid = new GridPane();

        Button btn500 = new Button("Withdraw 500");
        btn500.setPrefSize(100.,100.);
        Button btn1000 = new Button("Withdraw 1000");
        btn1000.setPrefSize(100.,100.);
        Button btn1500 = new Button("Withdraw 1500");
        btn1500.setPrefSize(100.,100.);
        Button btn2000 = new Button("Withdraw 2000");
        btn2000.setPrefSize(100.,100.);
        withdrawGrid.add(btn500,0,0);
        withdrawGrid.add(btn1000,1,0);
        withdrawGrid.add(btn1500,0,1);
        withdrawGrid.add(btn2000,1,1);
        grid.add(withdrawGrid, 1, 2);



        Button btn = new Button("Go Back");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 3);

//        Button btnAcc = new Button("Create new Card");
//        HBox hbBtnAcc = new HBox(10);
//        hbBtnAcc.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtnAcc.getChildren().add(btnAcc);
//        grid.add(hbBtnAcc, 1, 3);

        btn.setOnAction(e->{
            stage.setScene(sceneAccountOverview);
        });

        return scene;
    }


    public Scene createCardCreationScene(Stage stage) {
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text loginTitle = new Text("Create new card");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(loginTitle, 0, 0, 2, 1);

        Text text1 = new Text("Card Type: ");
        grid.add(text1,0,1);
        ObservableList<CardType> combo1Menu = FXCollections.observableArrayList(cardTypeDAO.getEntitiesList());
        final ComboBox<CardType> combo1 = new ComboBox(combo1Menu);
        grid.add(combo1,1,1);

        Text text2 = new Text("Withdraw Limit: ");
        grid.add(text2, 0, 2);
        NumberTextField numField1 = new NumberTextField();
        grid.add(numField1, 1, 2);

        Button btn = new Button("Go Back");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 3);

        Button btn1 = new Button("Create new Card");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 3);

        btn.setOnAction(e->{
            stage.setScene(sceneAccountOverview);
        });

        btn1.setOnAction(e->{
            if(combo1.getValue() != null && numField1.getText().length() != 0) {
                Card cardToAdd = new Card(combo1.getValue(),loggedAccount, new BigDecimal(numField1.getText()));
                cardDAO.saveEntity(cardToAdd);
                stage.setScene(sceneAccountOverview);
            }
        });

        return scene;
    }

    public Scene createCardDeleteScene(Stage stage) {
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text loginTitle = new Text("Delete card");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(loginTitle, 0, 0, 2, 1);

        Text text1 = new Text("Card Id to be deleted");
        grid.add(text1, 0, 1);

        ObservableList<Long> combo1Menu = FXCollections.observableArrayList(loggedAccount.getCardsIds());
        final ComboBox<Long> combo1 = new ComboBox(combo1Menu);
        grid.add(combo1,1,1);

        Button btn = new Button("Go Back");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 2);

        Button btn1 = new Button("Delete card");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 2);

        btn.setOnAction(e->{
            stage.setScene(sceneAccountOverview);
        });

        btn1.setOnAction(e->{
            if(combo1.getValue()!= null) {
                stage.setScene(sceneAccountOverview);
                cardDAO.deleteEntity(combo1.getValue());
            }
        });

        return scene;
    }

    public Scene createLoginScene(Stage stage) {
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
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
                clearAllFields(scene);
                sceneAccountOverview = createAccountOverviewScene(stage);//updates the values in scene
                stage.setScene(sceneAccountOverview);
            } else {
                actiontarget.setText("Wrong input");
                pwBox.setText("");
            }
        });

        btnAcc.setOnAction(e -> {
            clearAllFields(scene);
            stage.setScene(sceneAccountDecision);
        });

        return scene;
    }

    public Scene createAccountDecisionScene(Stage stage) {
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
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
        grid.add(hbBtn, 0, 4);

        Button btn1 = new Button("Existing user");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 4);

        Button btn2 = new Button("Go back");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btn2);
        grid.add(hbBtn2, 2, 4);

        btn.setOnAction(e -> {
            stage.setScene(sceneNewUserAccount);
        });

        btn1.setOnAction(e -> {
            stage.setScene(sceneExistingUserAccount);
        });

        btn2.setOnAction(e ->{
            stage.setScene(sceneLogin);
        });


        return scene;
    }





    public Scene createCardPaymentScene(Stage stage){
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
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
        ObservableList<Long> combo1Menu = FXCollections.observableArrayList(loggedAccount.getCardsIds());
        final ComboBox<Long> combo1 = new ComboBox(combo1Menu);
        grid.add(combo1,1,3);
//        NumberTextField numField3 = new NumberTextField();
//        grid.add(numField3,1,3);

        Label textLab1 = new Label("Message to receiver");
        grid.add(textLab1,0,4);
        TextField textField1 = new TextField();
        grid.add(textField1,1,4);

        Label textLab2 = new Label("Message to sender");
        grid.add(textLab2,0,5);
        TextField textField2 = new TextField();
        grid.add(textField2,1,5);

        Button btn = new Button("Cancel and go back");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 6);

        Button btn1 = new Button("Make payment");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 6);

        btn.setOnAction(e->{
            clearAllFields(scene);
            stage.setScene(sceneAccountOverview);
        });

        btn1.setOnAction(e->{
            Long targetAccount = null;
            if(numField1.getText().length() != 0) {
                targetAccount = Long.parseLong(numField1.getText());
            }
            BigDecimal amount = null;
            if(numField2.getText().length() != 0) {
                amount = new BigDecimal(numField2.getText());
            }
            Long cardUsed = null;
            if(combo1.getValue() != null) {
                cardUsed = combo1.getValue();
            }
            String messageToReceiver = textField1.getText();
            String messageToSender = textField2.getText();

            if(targetAccount != null && amount != null && cardUsed != null){
                //check balance on account
            }


        });




        return scene;
    }


    public Scene createExistingUserAccountScene(Stage stage) {
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
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

        Button btn = new Button("Go back");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 4);

        Button btn1 = new Button("Create Account");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 4);

        final Text errorText = new Text();
        grid.add(errorText,1,5);

        btn.setOnAction(e->{
            clearAllFields(scene);
            stage.setScene(sceneAccountDecision);
        });

        btn1.setOnAction(e -> {
            Long ownerId = null;
            if(numField1.getText().length() != 0){
                ownerId=Long.parseLong(numField1.getText());
            }
            BigDecimal amount = null;
            if(numField2.getText().length() != 0) {
                amount=new BigDecimal(numField2.getText());
            }
            String password = pwBox.getText();
            System.out.println(ownerId);

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
                    clearAllFields(scene);
//                    stage.setScene(sceneLogin);
                    errorText.setText("Account successfully created\n" +
                            "ID: " + accountToAdd.getId_account() + "\n" +
                            "Password " + password);
                }
            }else{
                errorText.setFill(Color.FIREBRICK);
                errorText.setText("Please enter all information correctly");
            }
        });


        return scene;
    }

    public Scene createNewUserAccountScene(Stage stage) {
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
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

        Button btn = new Button("Go back");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 6);

        Button btn1 = new Button("Create Account");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 6);

        final Text errorText = new Text();
        grid.add(errorText,1,7,3,1);


        btn.setOnAction(e->{
            clearAllFields(scene);
            stage.setScene(sceneAccountDecision);
        });

        btn1.setOnAction(e -> {
            String firstName = textField1.getText();
            String secondName = textField2.getText();
            String address = textField3.getText();
            BigDecimal amount = null;
            if(numField1.getText().length() != 0){
                amount = new BigDecimal(numField1.getText());

            }
            String password = pwBox.getText();


            if (firstName != "" && secondName != "" && address != "" && amount != null && password != "") {
                Person personToAdd = new Person(firstName, secondName, 0, address);
                personDAO.saveEntity(personToAdd);
                Account accountToAdd = new Account(password, personToAdd, amount);
                accountDAO.saveEntity(accountToAdd);
                clearAllFields(scene);
                errorText.setText("Account successfully created\n" +
                        "ID: " + accountToAdd.getId_account() + "\n" +
                        "Password " + password);
//                stage.setScene(sceneLogin);
            }else{
                errorText.setFill(Color.FIREBRICK);
                errorText.setText("Please enter all information correctly");
            }
        });


        return scene;
    }

    public void clearAllFields(Scene scene){
        for (Node node : scene.getRoot().getChildrenUnmodifiable()) {
            if (node instanceof TextField) {
                ((TextField)node).setText("");
            }
        }
    }

}
