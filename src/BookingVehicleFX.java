import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookingVehicleFX extends Application {

    private Stage stage;
    private String currentUser;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        showMainLoginPage();
    }

    // Main Login Page
    private void showMainLoginPage() {

        Label company = new Label("Wheels on Deals");
        company.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        Label subtitle = new Label("Vehicle Rental Management System");

        Button userBtn = new Button("Login as Customer");
        Button adminBtn = new Button("Login as Administrator");

        userBtn.setOnAction(e -> showUserLogin());
        adminBtn.setOnAction(e -> showAdminLogin());

        VBox box = new VBox(15, company, subtitle, userBtn, adminBtn);
        box.setPadding(new Insets(25));

        stage.setTitle("Wheels on Deals");
        stage.setScene(new Scene(box, 340, 280));
        stage.show();
    }

    // User Login
    private void showUserLogin() {

        Label title = new Label("Customer Login");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your full name");

        Button continueBtn = new Button("Continue");
        Button backBtn = new Button("Back");

        continueBtn.setOnAction(e -> {
            if (!nameField.getText().trim().isEmpty()) {
                currentUser = nameField.getText().trim();
                showUserMenu();
            } else {
                showAlert("Please enter your name");
            }
        });

        backBtn.setOnAction(e -> showMainLoginPage());

        VBox box = new VBox(12, title, nameField, continueBtn, backBtn);
        box.setPadding(new Insets(25));

        stage.setScene(new Scene(box, 320, 240));
    }

    // User Menu
    private void showUserMenu() {

        Label welcome = new Label("Welcome, " + currentUser);
        welcome.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Button bookBtn = new Button("Book a Vehicle");
        Button returnBtn = new Button("Return a Vehicle");
        Button viewBtn = new Button("View Vehicle Availability");
        Button logoutBtn = new Button("Logout");

        bookBtn.setOnAction(e -> openBookWindow());
        returnBtn.setOnAction(e -> openReturnWindow());
        viewBtn.setOnAction(e -> openViewWindow());
        logoutBtn.setOnAction(e -> showMainLoginPage());

        VBox box = new VBox(12, welcome, bookBtn, returnBtn, viewBtn, logoutBtn);
        box.setPadding(new Insets(25));

        stage.setScene(new Scene(box, 360, 320));
    }

    // Admin Login
    private void showAdminLogin() {

        Label title = new Label("Administrator Login");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter administrator password");

        Button loginBtn = new Button("Login");
        Button backBtn = new Button("Back");

        loginBtn.setOnAction(e -> {
            if (passwordField.getText().equals("123cars")) {
                showAdminDashboard();
            } else {
                showAlert("Incorrect password");
            }
        });

        backBtn.setOnAction(e -> showMainLoginPage());

        VBox box = new VBox(12, title, passwordField, loginBtn, backBtn);
        box.setPadding(new Insets(25));

        stage.setScene(new Scene(box, 340, 240));
    }

    // Admin Dashboard
    private void showAdminDashboard() {

        Label title = new Label("Administrator Dashboard");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        ListView<String> list = new ListView<>();
        list.getItems().addAll(BookingVehicle.getVehicleStatus());

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> showMainLoginPage());

        VBox box = new VBox(12, title, list, logoutBtn);
        box.setPadding(new Insets(25));

        stage.setScene(new Scene(box, 500, 420));
    }

    // Book Window
    private void openBookWindow() {

        Stage s = new Stage();

        TextField carField = new TextField();
        carField.setPromptText("Enter car name");

        TextField daysField = new TextField();
        daysField.setPromptText("Number of rental days");

        Button bookBtn = new Button("Confirm Booking");

        bookBtn.setOnAction(e -> {
            try {
                int days = Integer.parseInt(daysField.getText());
                String msg = BookingVehicle.bookVehicle(currentUser, carField.getText(), days);
                showAlert(msg);
                if (msg.startsWith("Booked")) s.close();
            } catch (Exception ex) {
                showAlert("Invalid number of days");
            }
        });

        VBox box = new VBox(12, new Label("Book a Vehicle"), carField, daysField, bookBtn);
        box.setPadding(new Insets(20));

        s.setScene(new Scene(box, 300, 260));
        s.setTitle("Book Vehicle");
        s.show();
    }

    // Return Window
    private void openReturnWindow() {

        Stage s = new Stage();

        TextField carField = new TextField();
        carField.setPromptText("Enter car name");

        Button returnBtn = new Button("Return Vehicle");

        returnBtn.setOnAction(e -> {
            String msg = BookingVehicle.returnVehicle(carField.getText(), currentUser);
            showAlert(msg);
            if (msg.startsWith("Car returned")) s.close();
        });

        VBox box = new VBox(12, new Label("Return Vehicle"), carField, returnBtn);
        box.setPadding(new Insets(20));

        s.setScene(new Scene(box, 300, 220));
        s.setTitle("Return Vehicle");
        s.show();
    }

    // View Window
    private void openViewWindow() {

        Stage s = new Stage();

        ListView<String> list = new ListView<>();
        list.getItems().addAll(BookingVehicle.getVehicleStatus());

        VBox box = new VBox(10, new Label("Vehicle Availability"), list);
        box.setPadding(new Insets(20));

        s.setScene(new Scene(box, 500, 380));
        s.setTitle("Available Vehicles");
        s.show();
    }

    // Alert
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
