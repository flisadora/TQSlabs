package tqs.labs.multilayerwebapp;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

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

    @org.junit.Test
    public void diferentLocationsTest() {
        driver.get("http://localhost:8080/api/airpollution");
        driver.manage().window().setSize(new Dimension(1080, 807));
        driver.findElement(By.id("states")).click();
        {
            WebElement dropdown = driver.findElement(By.id("states"));
            dropdown.findElement(By.xpath("//option[. = 'Braga']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(3)")).click();
        {
            String value = driver.findElement(By.id("states")).getAttribute("value");
            assertThat(value, is("Braga"));
        }
        driver.findElement(By.cssSelector("button")).click();
        assertThat(driver.findElement(By.id("city_name")).getText(), is("Braga"));
        assertThat(driver.findElement(By.id("air")).getText(), is(not("0.0")));
        assertThat(driver.findElement(By.id("category")).getText(), is(not(" ")));
        assertThat(driver.findElement(By.id("carbon")).getText(), is(not(" ")));
        assertThat(driver.findElement(By.id("nitrogenmono")).getText(), is(not(" ")));
        assertThat(driver.findElement(By.id("nitrogendio")).getText(), is(not(" ")));
        assertThat(driver.findElement(By.id("ozone")).getText(), is(not(" ")));
        assertThat(driver.findElement(By.id("sulphur")).getText(), is(not(" ")));
        assertThat(driver.findElement(By.id("fineparticles")).getText(), is(not(" ")));
        assertThat(driver.findElement(By.id("coarseparticle")).getText(), is(not(" ")));
        assertThat(driver.findElement(By.id("ammonia")).getText(), is(not(" ")));
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.id("states")).click();
        driver.findElement(By.cssSelector("option:nth-child(1)")).click();
        driver.findElement(By.cssSelector("button")).click();
        assertThat(driver.findElement(By.id("city_name")).getText(), is("Aveiro"));
        assertThat(driver.findElement(By.id("air")).getText(), is(not("0.0")));
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.id("states")).click();
        {
            WebElement dropdown = driver.findElement(By.id("states"));
            dropdown.findElement(By.xpath("//option[. = 'Açores']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        driver.findElement(By.cssSelector("button")).click();
        assertThat(driver.findElement(By.id("error")).getText(), is("Data unavailable!"));
        assertThat(driver.findElement(By.id("air")).getText(), is("0.0"));
        driver.findElement(By.cssSelector("button")).click();
    }
}
