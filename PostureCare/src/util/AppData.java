package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.PosturData;
import model.User;
import java.util.ArrayList;
import java.util.List;

public class AppData {
    public static final List<User> userList = new ArrayList<>();
    public static final ObservableList<PosturData> progressHistory = FXCollections.observableArrayList();
}