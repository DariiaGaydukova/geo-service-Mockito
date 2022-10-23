import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


@ExtendWith(MockitoExtension.class)
public class TestMessageSender {

    @Mock
    private GeoServiceImpl geoService;
    @Mock
    private LocalizationService localizationService;


    @ParameterizedTest
    @DisplayName("для проверки языка отправляемого сообщения")
    @MethodSource("arguments")
    void testMessageSender(String ip) {
        String messageTest = "Добро пожаловать";

        if ("172.".startsWith(ip)) {

            Mockito.when(geoService.byIp(ip)).thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
            Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        } else {
            messageTest = "Welcome";
            Mockito.when(geoService.byIp(ip)).thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));
            Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        }
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String messageActual = messageSender.send(headers);

        Assertions.assertEquals(messageTest, messageActual);

    }

    private static Stream<Arguments> arguments() {
        return Stream.of(Arguments.of("172.123.12.19"), Arguments.of("96.777.88.99"));
    }

}
