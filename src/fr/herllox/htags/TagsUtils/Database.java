package fr.herllox.htags.TagsUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    public static Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "";
            String user = "";
            String passwd = "";

            Connection conn = DriverManager.getConnection(url, user, passwd);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
