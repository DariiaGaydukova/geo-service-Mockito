import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;
import java.util.stream.Stream;


public class TestLocalizationServiceImpl {

    private LocalizationServiceImpl localizationServiceImpl;
    private Country country;

    @BeforeEach
    public void setUp() {

        localizationServiceImpl = new LocalizationServiceImpl();
    }

    @DisplayName("для проверки возвращаемого текста")
    @ParameterizedTest
    @MethodSource("arguments")
    void returnText(Country country) {
        String expected;

        if (country.equals(Country.RUSSIA)) {
            expected = "Добро пожаловать";
        } else {
            expected = "Welcome";
        }
        String actual = localizationServiceImpl.locale(country);
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @DisplayName("для проверки на русский текст")
    void returnRusText() {
        country = Country.RUSSIA;
        Assertions.assertEquals("Добро пожаловать", localizationServiceImpl.locale(country));
    }

    private static Stream<Arguments> arguments() {
        return Stream.of(Arguments.of(Country.RUSSIA));

    }
}


