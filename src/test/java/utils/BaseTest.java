package utils;

import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    static void setUpConfig(){

        //Читаем конфиг файл и выводим параметры перед запуском тестов
        ConfigClass.printConfigForTest();
    }
}
