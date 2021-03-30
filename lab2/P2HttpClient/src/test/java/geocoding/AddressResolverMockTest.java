package geocoding;

import connection.ISimpleHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressResolverMockTest {
    @Mock
    ISimpleHttpClient httpClient;

    @InjectMocks
    AddressResolver resolver;

    @Test
    void whenGoodCoordidates_returnAddress() throws ParseException, IOException, URISyntaxException{
        String jsonResponse = "{\"info\": {\"statuscode\": 0, \"copyright\": { \"text\": \"© 2021 MapQuest, Inc.\", \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\", \"imageAltText\": \"© 2021 MapQuest, Inc.\" }, \"messages\": [] },   \"maxResults\": 1, \"thumbMaps\": false, \"ignoreLatLngInput\": false }, \"results\": [ { \"providedLocation\": { \"latLng\": { \"lat\": -19.88817, \"lng\": -43.92146 } }, \"locations\": [ { \"street\": \"Rua Cardeal Stepinac\", \"adminArea6\": \"\", \"adminArea6Type\": \"Neighborhood\", \"adminArea5\": \"Belo Horizonte\", \"adminArea5Type\": \"City\", \"adminArea4\": \"\", \"adminArea4Type\": \"County\", \"adminArea3\": \"Minas Gerais\", \"adminArea3Type\": \"State\", \"adminArea1\": \"BR\", \"adminArea1Type\": \"Country\", \"postalCode\": \"31170-340\", \"geocodeQualityCode\": \"B1AAA\", \"geocodeQuality\": \"STREET\", \"dragPoint\": false, \"sideOfStreet\": \"N\", \"linkId\": \"0\", \"unknownInput\": \"\", \"type\": \"s\", \"latLng\": { \"lat\": -19.888323, \"lng\": -43.921556 }, \"displayLatLng\": { \"lat\": -19.888323, \"lng\": -43.921556 } } ] } ] }";
        String response = "{\"info\": {\"statuscode\": 0, \"copyright\": { \"text\": \"© 2021 MapQuest, Inc.\", \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\", \"imageAltText\": \"© 2021 MapQuest, Inc.\" }, \"messages\": [] },   \"maxResults\": 1, \"thumbMaps\": false, \"ignoreLatLngInput\": false }, \"results\": [ { \"providedLocation\": { \"latLng\": { \"lat\": -19.88817, \"lng\": -43.92146 } }, \"locations\": [ { \"street\": \"Rua Cardeal Stepinac\", \"adminArea6\": \"\", \"adminArea6Type\": \"Neighborhood\", \"adminArea5\": \"Belo Horizonte\", \"adminArea5Type\": \"City\", \"adminArea4\": \"\", \"adminArea4Type\": \"County\", \"adminArea3\": \"Minas Gerais\", \"adminArea3Type\": \"State\", \"adminArea1\": \"BR\", \"adminArea1Type\": \"Country\", \"postalCode\": \"31170-340\", \"geocodeQualityCode\": \"B1AAA\", \"geocodeQuality\": \"STREET\", \"dragPoint\": false, \"sideOfStreet\": \"N\", \"linkId\": \"0\", \"unknownInput\": \"\", \"type\": \"s\", \"latLng\": { \"lat\": -19.888323, \"lng\": -43.921556 }, \"displayLatLng\": { \"lat\": -19.888323, \"lng\": -43.921556 } } ] } ] }";

        when(httpClient.get( contains("location=-19.88817%2C-43.92146"))).thenReturn(jsonResponse);
        Address result = resolver.findAddressForLocation(-19.88817,-43.92146);

        assertEquals(result, new Address("Rua Cardeal Stepinac", "Belo Horizonte", "Minas Gerais", "31170-340", null) );
    }

    @Test
    public void whenBadCoordidates_trhowBadArrayIndex() throws ParseException, IOException, URISyntaxException{
        String jsonResponse = "{\"info\": {\"statuscode\": 0, \"copyright\": { \"text\": \"© 2021 MapQuest, Inc.\", \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\", \"imageAltText\": \"© 2021 MapQuest, Inc.\" }, \"messages\": [] },   \"maxResults\": 1, \"thumbMaps\": false, \"ignoreLatLngInput\": false }, \"results\": [ { \"providedLocation\": { \"latLng\": { \"lat\": -19.88817, \"lng\": -43.92146 } }, \"locations\": [ { \"street\": \"Rua Cardeal Stepinac\", \"adminArea6\": \"\", \"adminArea6Type\": \"Neighborhood\", \"adminArea5\": \"Belo Horizonte\", \"adminArea5Type\": \"City\", \"adminArea4\": \"\", \"adminArea4Type\": \"County\", \"adminArea3\": \"Minas Gerais\", \"adminArea3Type\": \"State\", \"adminArea1\": \"BR\", \"adminArea1Type\": \"Country\", \"postalCode\": \"31170-340\", \"geocodeQualityCode\": \"B1AAA\", \"geocodeQuality\": \"STREET\", \"dragPoint\": false, \"sideOfStreet\": \"N\", \"linkId\": \"0\", \"unknownInput\": \"\", \"type\": \"s\", \"latLng\": { \"lat\": -19.888323, \"lng\": -43.921556 }, \"displayLatLng\": { \"lat\": -19.888323, \"lng\": -43.921556 } } ] } ] }";
        when(httpClient.get(contains("-200.000000%2C-200.000000"))).thenThrow(IndexOutOfBoundsException.class);
        assertThrows(IndexOutOfBoundsException.class, () -> resolver.findAddressForLocation(-200,-200));
    }

}