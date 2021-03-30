import java.util.ArrayList;

public class StocksPortfolio {

    private String name;
    private IStockMarket stockmarket;
    private ArrayList<Stock> stocks = new ArrayList<>();

    public StocksPortfolio(IStockMarket stockmarket){
        this.stockmarket = stockmarket;
        stocks = new ArrayList<>();
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IStockMarket getMarketService(){
        return this.stockmarket;
    }

    public void setMarketService(IStockMarket market){
        this.stockmarket = market;
    }

    // get the value of a stock
    public double getTotalValue(){
        double value = 0.0;
        for (Stock stock: stocks){
            value += stock.getQuantity() * stockmarket.getPrice(stock.getName());
        }
        return value;
    }

    public void addStock(Stock stock){
        this.stocks.add(stock);
    }
}
