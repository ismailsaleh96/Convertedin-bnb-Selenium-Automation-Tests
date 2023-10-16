package pages;
import java.time.Duration;
import org.testng.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Set;


public class  FilterationPage  {

	WebDriver driver = null;
	
	By filterButton = By.xpath("//button[@data-testid = 'category-bar-filter-button']");
	
	By filterModal = By.xpath("//div[@aria-label = 'Filters']");
	
	By RoomsBeds = By.xpath("//div[contains(@aria-labelledby, 'ROOMS_AND_BEDS_WITH_SUBCATEGORY')]");
	By numOfBedrooms = By.xpath("(//div[contains(@aria-labelledby, 'ROOMS_AND_BEDS_WITH_SUBCATEGORY')]//div[@data-testid='menuItemButton-5'])[1]");
	By AmenitiesElement = By.xpath("//div[contains(@aria-labelledby, 'MORE_FILTERS_AMENITIES_WITH_SUBCATEGORIES')]");
	
	
	By AmenitiesShowMore = By.xpath("//div[contains(@aria-labelledby, 'MORE_FILTERS_AMENITIES_WITH_SUBCATEGORIES')]//span[text() = 'Show more']");
	
	
	By poolCheckBox = By.xpath("//div[text() = 'Pool']");
	
	
	By showStayButton = By.xpath("//button[text() = 'Clear all']/following::a");
	
	
	By bedroomsSpan = By.xpath("//span[@class = ' dir dir-ltr' and contains(text(), 'bedrooms')]"); 
	
	By firstProperty = By.xpath("(//div[@data-testid='card-container'])[1]");
	
	By AmenitiesShowMoreButton = By.xpath("(//button[contains(text(), 'Show all') and contains(text(), 'amenities')]");
	By poolInAmenities = By.xpath("//div[contains(@id, 'parking_facilities') and contains(text(), 'Pool')]");
	By searchResultsHeader = By.xpath("//h1[@elementtiming='LCP-target']/span");
	By inPageAmenitiesDiv = By.xpath("//div[@data-section-id = 'AMENITIES_DEFAULT']");
	By inPageAmenitiesShowMore = By.xpath("//button[contains(text(), 'Show all') and contains(text(), 'amenities')]");
	By amenitiesModal = By.xpath("//div[@aria-label = 'What this place offers']");
	
   // private By allProperties = By.cssSelector("div[class='c5118dh cs12nu1 d1m93iqd dir dir-ltr']");
 
	
	public FilterationPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void OpenFilterMenu() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(filterButton));
		driver.findElement(filterButton).click();
	}
	
	public void ChooseFilteringValues(int bedroomsNum, String desiredAmenities) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(filterModal));
		
		driver.findElement(RoomsBeds).click();
		driver.findElement(By.xpath("(//div[contains(@aria-labelledby, 'ROOMS_AND_BEDS_WITH_SUBCATEGORY')]//div[@data-testid='menuItemButton-" + bedroomsNum + "'])[1]")).click();
		
		driver.findElement(AmenitiesElement).click();
		driver.findElement(AmenitiesShowMore).click();
		driver.findElement(By.xpath("//div[text() = '" + desiredAmenities + "']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(showStayButton));
		driver.findElement(showStayButton).click();
		
	}
	
	public void CheckPropertiesBedroomsNumber(int bedroomsNum) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultsHeader));
		
		List<WebElement> bedroomElements = driver.findElements(bedroomsSpan);

        for (WebElement bedroomElement : bedroomElements) {
            String bedroomText = bedroomElement.getText();
            int actualBedrooms = extractBedroomCount(bedroomText);
            if (actualBedrooms < bedroomsNum) {
                throw new AssertionError("Bedroom count is less than The Count Which User Selected");
            }
        }
	}
	
	private int extractBedroomCount(String text) {
        // Extract the numeric part of the bedroom count text
        String numericPart = text.split(" ")[0];
        return Integer.parseInt(numericPart);
    }
	
	public void OpenFirstProperty(int index) {
		
		try {
			
			int index = Integer.parseInt(index);
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@data-testid='card-container'])['" +  index + "']")));
			
			String originalHandle = driver.getWindowHandle();
			
			driver.findElement(By.xpath("(//div[@data-testid='card-container'])['" +  index + "']")).click();
			
			Set<String> windowHandles = driver.getWindowHandles();
			
			for (String handle : windowHandles) {
			    if (!handle.equals(originalHandle)) {
			        
			        driver.switchTo().window(handle);
			    }
			}
		}
		
		    catch (NumberFormatException e) {
		        System.out.println("Error: Invalid index provided");
		    }	    
		    
	}
	
	public void CheckDesiredAmenitiesIsDisplayedonInPropertyPage(String desiredAmenities) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(inPageAmenitiesDiv));
		
		driver.findElement(inPageAmenitiesDiv).click();
		driver.findElement(inPageAmenitiesShowMore).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(amenitiesModal));
		WebElement parkingfacilitiesSection = driver.findElement(By.xpath("//h3[text() = 'Parking and facilities']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parkingfacilitiesSection);
		driver.findElement(By.xpath("//div[text() = '" + desiredAmenities + "']")).isDisplayed();
		
	}
	
}
