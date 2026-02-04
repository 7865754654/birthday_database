package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBconfig {

    private static final Properties PROPERTIES = new Properties();

    static {
         InputStream in = DBconfig.class
                .getClassLoader()
                .getResourceAsStream("db.properties");

            if (in == null) {
                throw new RuntimeException("Файл db.properties не был найден в classpath.");
            }

            try {
                PROPERTIES.load(in);
        } catch (IOException e) {
            System.out.println("Ресурсы закрыты: " + e.getMessage());
        }
    }

        public static String getURL() {
            return PROPERTIES.getProperty("db.url");
        }

        public static String getName() {
            return PROPERTIES.getProperty("db.name");
        }

        public static String getPassword() {
            return PROPERTIES.getProperty("db.password");
        }

    }

