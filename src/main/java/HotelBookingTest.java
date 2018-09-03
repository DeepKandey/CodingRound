//import com.sun.javafx.PlatformUtil;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HotelBookingTest {

    public WebDriver driver;
	public Platform platform;
	public ChromeOptions chromeOptions;

    @FindBy(xpath = "//*[@class='navGroup productNav withArrows']//li[2]/a")
    private WebElement hotelLink;

    @FindBy(id = "Tags")
    private WebElement localityTextBox;

    @FindBy(id = "SearchHotelsButton")
    private WebElement searchButton;
    
    @FindBy(xpath = "//*[@id='ui-datepicker-div']/div[1]/table/tbody/tr[3]/td[7]")
    private WebElement calendar;
  

    @FindBy(id = "travellersOnhome")
    private WebElement travellerSelection;
    

    
    @BeforeMethod
	public void setUp() {
		chromeOptions = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 1);
		chromeOptions.setExperimentalOption("prefs", prefs);
		if (platform == null) {
			String operSys = System.getProperty("os.name").toLowerCase();
			if (operSys.contains("win")) {
				platform = Platform.WIN10;
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver.exe");
				driver = new ChromeDriver(chromeOptions);
				driver.manage().window().maximize();
				PageFactory.initElements(driver,this);
			} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
				platform = Platform.LINUX;
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver_linux");
				driver = new ChromeDriver(chromeOptions);
				driver.manage().window().maximize();
				PageFactory.initElements(driver,this);
			} else if (operSys.contains("mac")) {
				platform = Platform.MAC;
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver");
				driver = new ChromeDriver(chromeOptions);
				driver.manage().window().maximize();
				PageFactory.initElements(driver,this);
			}
		}
	}
    
    @Test
    public void shouldBeAbleToSearchForHotels() {
        setUp();
        driver.get("https://www.cleartrip.com/");
        hotelLink.click();
        localityTextBox.sendKeys("Indiranagar, Bangalore");
        localityTextBox.sendKeys(Keys.TAB);
        calendar.click();

        new Select(travellerSelection).selectByVisibleText("1 room, 2 adults");
        searchButton.click();
    }

    @AfterMethod
	public void tearDown() {
		// close the browser
		driver.quit();

	}

}
