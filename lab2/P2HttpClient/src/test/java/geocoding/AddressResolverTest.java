package geocoding;

import connection.ISimpleHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    private AddressResolver resolver;
    @Test
    void whenGoodCoordidates_returnAddress() throws ParseException, IOException, URISyntaxException {
        //test
        Address result = resolver.findAddressForLocation(-19.88817,-43.92146);

        //return
        assertEquals( result, new Address("Rua Cardeal Stepinac", "Belo Horizonte", "Minas Gerais", "31170-340", null) );

    }

    @Test
    public void whenBadCoordidates_trhowBadArrayIndex() throws IOException, URISyntaxException, ParseException {

        //todo
    }
}