package steps;

import global.DriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import util.LocatorUtils;

/**
 * Created by mrunal on 7/29/2017.
 */
public class ShopCluesPageObject {

    private By searchBox = By.id("autocomplete");
    private DriverSetup driverSetup;
    private LocatorUtils locatorUtils;

    public ShopCluesPageObject(DriverSetup driverSetup, LocatorUtils locatorUtils) {
        this.driverSetup = driverSetup;
        this.locatorUtils = locatorUtils;
    }
    public void search(String searchText) {
        locatorUtils.getElement(searchBox).sendKeys(searchText, Keys.ENTER);
    }
}