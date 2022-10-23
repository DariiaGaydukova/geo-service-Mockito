import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;


public class TestGeoServiceImpl {

    private GeoServiceImpl geoServiceImpl;

    @BeforeEach
    void setUp() {
        geoServiceImpl = new GeoServiceImpl();
    }


    @DisplayName("для проверки локации по ip")
    @ParameterizedTest
    @MethodSource("arguments")
    void byIp(String ip) {

        Location expected = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        Location actual = geoServiceImpl.byIp(ip);
        Assertions.assertEquals(expected.getCity(), actual.getCity());

    }


    private static Stream<Arguments> arguments() {
        return Stream.of(Arguments.of("172.123.12.19"));
    }


}
