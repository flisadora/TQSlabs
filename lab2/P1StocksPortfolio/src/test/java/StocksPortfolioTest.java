import org.hamcrest.MatcherAssert;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StocksPortfolioTest {



    @org.junit.jupiter.api.Test
    void getTotalValue() {
//        1. Prepare a mock to substitute the remote service (@Mock annotation)
        IStockMarket stockmarket = mock(IStockMarket.class);

//        2. Create an instance of the subject under test (SuT) and use the mock to set the
//        (remote) service instance.
        StocksPortfolio portfolio = new StocksPortfolio(stockmarket);

//        3. Load the mock with the proper expectations (when...thenReturn)
        when(stockmarket.getPrice("EBAY")).thenReturn(4.0);
        when(stockmarket.getPrice("AAPL")).thenReturn(1.5);

//        4. Execute the test (use the service in the SuT)
        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("AAPL", 4));
        double result = portfolio.getTotalValue();

//        5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(14.0, result);

        MatcherAssert.assertThat(result, is(14.0));
        verify(stockmarket, times(2)).getPrice(anyString());

    }
}