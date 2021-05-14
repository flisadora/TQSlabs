package tqs.labs.multilayerwebapp;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@ExtendWith(SeleniumJupiter.class)
class WebDriverTest {

    private SafariDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    public WebDriverTest(SafariDriver driver) { this.driver = driver; }

    //@BeforeEach
    //void setup(){
    //    System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    //    driver = new FirefoxDriver();
    //}
    @Before
    public void setUp() {
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    void testBasicDriver(){
        driver.get("https://umavidasemlixo.com/blog/");
        assertThat(driver.getTitle(), containsString("Blog - Uma Vida Sem Lixo"));
    }
}
