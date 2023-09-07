package test.java.testDir;

import org.testng.annotations.Test;

public class BaseTest extends AbstractTestClass {


    @Test
    public void test1() {
        openSuite("https://otus.ru");
        clickToTextInBlock("верхний блок выбора направления", "Архитектура");
        clickToTextInBlock("левый блок выбора направлений", "Управление");
        System.out.println();
    }

//    @Test
//    public void test2() {
//        openSuite("https://ya.ru");
//
//
//    }


}
