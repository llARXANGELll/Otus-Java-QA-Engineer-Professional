package test.java.testDir.utils;

import org.testng.annotations.Test;


public class BaseTest extends AbstractTestClass {

//    @Test
//    public void test1() {
//        openSuite("https://otus.ru");
//        clickToTextInBlock("верхний блок выбора направления", "Архитектура");
//        clickToTextInBlock("левый блок выбора направлений", "Управление");
//    }

    @Test
    public void test2() {
        openSuite("https://yandex.ru/video/search?text=video");
        hoverToListElementForIndex("список анимированных проигрывателей в Яндекс поиске", 1);
        checkContainsAttributeForListElementsInIndex("список анимированных проигрывателей в Яндекс поиске",
                1, "class", "thumb-preview__target_playing");
    }


}
