import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by 100588642 on 1/24/2017.
 */
public class DataSource {
    public static ObservableList<Student> getAllStudent(){
        ObservableList<Student> list = FXCollections.observableArrayList();

        list.add(new Student(100000000, "Lachlan", "Jonston", 2.8));

        return list;


    }
}
