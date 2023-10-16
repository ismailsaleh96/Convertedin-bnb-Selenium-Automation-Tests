package pages;

import java.time.Duration;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class LandingPage {
	
	
	WebDriver driver = null;
	LocalDate currentDate = LocalDate.now();
    LocalDate checkInDate = currentDate.plusDays(7);
    LocalDate checkOutDate = checkInDate.plusDays(7);

    DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
	 String CheckInDateElement = checkInDate.format(DateFormat) ; 
	 String CheckOutDateElement = checkOutDate.format(DateFormat) ;
	
	By LocationBar = By.xpath("//div/header/div/div[2]/div[1]");
	By WhereLabel = By.xpath("//label[@for = 'bigsearch-query-location-input']");
	By locationInputField = By.id("bigsearch-query-location-input");
	By locationsListbox = By.id("bigsearch-query-location-listbox");
	By checkinLabel = By.xpath("//div[@data-testid = 'structured-search-input-field-split-dates-0']");
	By guestsLabel = By.xpath("//div[@data-testid = 'structured-search-input-field-guests-button']");
	By guestsPanel = By.xpath("//div[@data-testid = 'structured-search-input-field-guests-panel']");
	By adultsIncreaseButton = By.xpath("//button[@data-testid = 'stepper-adults-increase-button']");
	By childrenIncreaseButton = By.xpath("//button[@data-testid = 'stepper-children-increase-button']");
	By searchButton = By.xpath("//button[@data-testid = 'structured-search-input-search-button']");
	By locationFieldFilterResult = By.xpath("//button[@data-index = '0']/div");
	By DateFieldFilterResult = By.xpath("//button[@data-index = '1']/div");
	By GuestsFieldFilterResult = By.xpath("//button[@data-index = '2']/div");
	
	
	public LandingPage (WebDriver driver) {
		this.driver = driver;
	}
	
	public void UserOpenSearchBar() {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(LocationBar));
		driver.findElement(LocationBar).click();
	}
	
	public void UserInputLocation(String Location) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(WhereLabel));
		
		driver.findElement(WhereLabel).click();
		driver.findElement(locationInputField).sendKeys(Location);
		
		String LocationResultXpath = "//div[text() = '" + Location + "']";
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(locationsListbox));
        driver.findElement(By.xpath(LocationResultXpath)).click();
	}
	
	public void UserSelectCheckInDate() {

		driver.findElement(checkinLabel).click();
		driver.findElement(By.xpath("//div[@data-testid = 'calendar-day-" + CheckInDateElement + "']")).click();
	}
	public void UserSelectCheckOutDate() {

		driver.findElement(By.xpath("//div[@data-testid = 'calendar-day-" + CheckOutDateElement + "']")).click();
	}

	public void guestsNumber(int adults, int children) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.findElement(guestsLabel).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(guestsPanel));
		
		
		
		for (int i = 0; i < adults; i++) {
	        driver.findElement(adultsIncreaseButton).click();
	    }

	    for (int i = 0; i < children; i++) {
	    	driver.findElement(childrenIncreaseButton).click();
	    }
		
		}
	
	public void UserClickSearch() {
		
		driver.findElement(searchButton).click();
	}

	public void VerifyFiltersResults (String destination, String guests) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(LocationBar));
		
		WebElement currentLocation = driver.findElement(locationFieldFilterResult);
        String expectedlocation = destination;
        WebElement currentGuests = driver.findElement(GuestsFieldFilterResult);
        String expectedGuests = guests;
        
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text() = 'Rome']")));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(GuestsFieldFilterResult));
        driver.findElement(By.xpath("//div[text() ='Rome']")).isDisplayed();
        
        Assert.assertEquals(expectedlocation, currentLocation.getText());
        Assert.assertEquals(expectedGuests, currentGuests.getText());
       
        
	}
	
	
	
}

