package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigClass {

    private static Properties properties = new Properties();

    static {
        loadConfig();
    }

    private static void loadConfig() {

        try(InputStream input = ConfigClass.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if(input == null){
                throw new RuntimeException("config.properties не найден");
            }
            properties.load(input);

        }
        catch(IOException e){
            throw new RuntimeException("Ошибка при загрузке конфиг файла!",e);
        }
    }
    
    public static String get(String key){
        String value = properties.getProperty(key);
        if(value == null){
            throw new RuntimeException("Параметр ' " + key + "' не найден конфиг файл");
        }
        return value;
    }

    public static int getInt(String key){
        return Integer.parseInt(get(key));
    }

    public static double getDouble(String key){
        return  Double.parseDouble(get(key));
    }

    //Выводим все параметры в консоль за исключением логина и пароля
    public static void printConfigForTest(){
        System.out.println("\n=== Настройка автотестов ===");
        System.out.println("URL стенда: " + get("app.url"));
        System.out.println("API url: " + get("api.url"));
        System.out.println("Тайм-аут элементов: " + getInt("element.timeout") + " сек");
        System.out.println("Уровень логирования: " + get("logging.mode"));
        System.out.println("Имя начального товара: " + get("product.name"));
        System.out.println("Цена начального товара: " + getDouble("product.price"));
        System.out.println("=== Логин и пароль админки скрыты ===\n");
    }
}
