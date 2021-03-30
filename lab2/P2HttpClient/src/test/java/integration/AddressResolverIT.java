package integration;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolver;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressResolverIT {

    private ISimpleHttpClient client;
    private AddressResolver resolver;

    @BeforeEach
    public void init(){
        client = new TqsBasicHttpClient();
        resolver = new AddressResolver(client);
    }

    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {
        Address result = resolver.findAddressForLocation(-19.88817,-43.92146);
        assertEquals( result, new Address("Rua Cardeal Stepinac", "Belo Horizonte", "Minas Gerais", "31170-340", null) );

    }

    @Test
    public void whenBadCoordidates_trhowBadArrayIndex() throws IOException, URISyntaxException, ParseException {
        assertThrows(IndexOutOfBoundsException.class, () -> resolver.findAddressForLocation(-200,-200));
    }

}
