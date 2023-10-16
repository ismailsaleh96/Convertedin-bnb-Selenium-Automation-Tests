package Tests;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.FilterationPage;
import Tests.TestBase;

// Tests Desgined To Run in Consecutive Way
public class SearchLocationTest extends TestBase {
	
	@Test (priority=1,alwaysRun=true)
	public static void UserSearchForLocation() {
		LandingPage LandingPageObject = new LandingPage(driver);
		
		String Location = "Rome, Italy";
		String ExpectedLocation = "Rome";
		
		int NumberOfAdults = 2;
		int NumberOfChildren = 1;
		
		String GuestsNumber = String.valueOf(NumberOfAdults + NumberOfChildren);
		String ExpectedGuestText = GuestsNumber + " guests";
		
		LandingPageObject.UserOpenSearchBar();
		LandingPageObject.UserInputLocation(Location);
		
		LandingPageObject.UserSelectCheckInDate();
		LandingPageObject.UserSelectCheckOutDate();
		
		LandingPageObject.guestsNumber(NumberOfAdults, NumberOfChildren);
		
		LandingPageObject.UserClickSearch();
		
		LandingPageObject.VerifyFiltersResults(ExpectedLocation, ExpectedGuestText);
	}
	
	
	@Test(priority=2,alwaysRun=true)
	public static void filteringStaysResults() {
		
		FilterationPage filteringPageObject = new FilterationPage(driver);

		int NumberOfBedrooms = 5;
		String Amenities = "Pool";
		int NumberOfProperty = 1;

		filteringPageObject.OpenFilterMenu();
		filteringPageObject.ChooseFilteringValues(NumberOfBedrooms, Amenities);
		filteringPageObject.CheckPropertiesBedroomsNumber(NumberOfBedrooms);
		filteringPageObject.OpenFirstProperty(NumberOfProperty);
		filteringPageObject.CheckDesiredAmenitiesIsDisplayedonInPropertyPage(Amenities);
	}

	@AfterTest
    public void tearDown()
    {
       driver.quit();
  
    }
	
}
