
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
    private TableView<Student> table;

    private BorderPane layout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("UI Demo I");

        BorderPane layout = new BorderPane();

        //Menu
        MenuBar menuBar = new MenuBar();
        Menu fileMenu_ = new Menu("File");
        //New
        MenuItem newMenuItem = new MenuItem("New", imageFile("images/new.png"));
        newMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        fileMenu_.getItems().add(newMenuItem);

        fileMenu_.getItems().add(new SeparatorMenuItem());

        MenuItem exitMenuItem = new MenuItem("Exit", imageFile("images/exit.png"));
        exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        exitMenuItem.setOnAction(e -> System.exit(0));
        fileMenu_.getItems().add(exitMenuItem);

        // spreadsheet
        table = new TableView<>();
        // TODO: Add some data to our table
        table.setItems(DataSource.getAllStudent());

        TableColumn<Student, Integer> sidColumn = new TableColumn<>("SID");
        sidColumn.setCellValueFactory(new PropertyValueFactory<>("sid"));
        sidColumn.setMinWidth(100);

        TableColumn<Student, Integer> fnColumn = new TableColumn<>("First Name");
        fnColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        fnColumn.setMinWidth(200);

        TableColumn<Student, Integer> lnColumn = new TableColumn<>("Last Name");
        lnColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lnColumn.setMinWidth(200);

        TableColumn<Student, Integer> gpaColumn = new TableColumn<>("GPA");
        gpaColumn.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        gpaColumn.setMinWidth(100);

        table.getColumns().add(sidColumn);
        table.getColumns().add(fnColumn);
        table.getColumns().add(lnColumn);
        table.getColumns().add(gpaColumn);

        //add the components to the layout
        menuBar.getMenus().add(fileMenu_);



        //Add the components to the layout
        layout.setTop(menuBar);
        layout.setCenter(table);

        Scene scene = new Scene (layout, 600,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ImageView imageFile(String path){
        return new ImageView(new Image("file:" + path));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
