import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ParameterizedSearchTest {
    @BeforeEach
    void beforeEach() {
        Configuration.browserSize = "1920x1080";
    }
    @DisplayName("Тест на поиск с методом @ValueSource")
    @ValueSource(strings = {"Телевизоры", "Ноутбуки"})
    @ParameterizedTest(name = "Тест на поиск с тестовыми данными {0}")
    void searchDnsShopTest(String objectSearch) {
        open("https://www.dns-shop.ru/");
        $$("[type='search']").find(Condition.visible).setValue(objectSearch).pressEnter();
        $("[data-role='header-block']").shouldHave(Condition.text(objectSearch));

    }
    @DisplayName("Тест на поиск с методом @CsvSource")
    @CsvSource({
            "Smart TV, Телевизоры",
            "Игровые ноутбуки, Ноутбуки"
    })
    @ParameterizedTest (name = "Тест на поиск с тестовыми данными {0}")
    void searchDnsShopTest2(String objectSearch, String expectedResult) {
       open("https://www.dns-shop.ru/");
        $$("[type='search']").find(Condition.visible).setValue(objectSearch).pressEnter();
        $(".products-list__group-title").shouldHave(Condition.text(expectedResult));
    }

    static Stream<Arguments> searchDataProvider() {
        return Stream.of(
                Arguments.of("Smart TV", "Телевизоры"),
                Arguments.of("Игровые ноутбуки", "Ноутбуки"));
    }
    @DisplayName("Тест на поиск с методом @MethodSource")
    @MethodSource("searchDataProvider")
    @ParameterizedTest (name = "Тест на поиск с тестовыми данными {0}")
    void searchDnsShopTest3(String objectSearch, String expectedResult) {
        open("https://www.dns-shop.ru/");
        $$("[type='search']").find(Condition.visible).setValue(objectSearch).pressEnter();
        $(".products-list__group-title").shouldHave(Condition.text(expectedResult));
    }
}