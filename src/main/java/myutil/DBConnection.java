package myutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {

        try {

            Properties props = new Properties();

            InputStream input =
                    DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if(input == null){

                throw new RuntimeException(
                        "db.properties not found");
            }

            props.load(input);

            URL =
                    props.getProperty("db.url");

            USER =
                    props.getProperty("db.username");

            PASSWORD =
                    props.getProperty("db.password");

            Class.forName(
                    "com.mysql.cj.jdbc.Driver");

        }

        catch(Exception e){

            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){

        try{

            return DriverManager.getConnection(

                    URL,
                    USER,
                    PASSWORD

            );

        }catch(Exception e){

            e.printStackTrace();

            return null;
        }
    }
}