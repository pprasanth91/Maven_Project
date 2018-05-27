package com.day01.mavenproject;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class Amazon_search {
	
	public static WebDriver driver;
	public static String brurl = "https:\\www.amazon.in";
	public static int iBroType = 1;
	public static String SearchResult;
	public static String A = "Iphone";
	public static ExtentReports logger = new ExtentReports();
	
	@BeforeTest
	public static void browser_Invoke() {
		logger.init("C:\\Users\\acer\\Desktop\\Eclipse\\com.day01.mavenproject\\Reports\\reports.html", true);
		logger.startTest("Invoke Browser" , "Opening the Browser");
		switch (iBroType) {
		case 1:
			System.out.println("User Option is : "+iBroType+", So Invoking Chrome Browser");
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\acer\\Downloads\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case 2:
			System.out.println("User Option is : "+iBroType+", So Invoking FireFox Browser");
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\acer\\Downloads\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;
			}
		logger.log(LogStatus.INFO, "Opening the Browser");
		logger.endTest();
	}
	
	@Test (priority=0)
	public static void navigate_url(){
		logger.startTest("Navigate Browser" , "Navigate to url");
		driver.get(brurl);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		logger.log(LogStatus.PASS, "Browser Navigated to Amazon");
		logger.endTest();
	}
	
	@Test (priority=1)
	public static void Search_product(){
		logger.startTest("Search Product" , "Search the Product");
		WebElement sfield , scategory , sbtn , rtxt ;
		sfield = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		sfield.sendKeys(A);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		scategory = driver.findElement(By.xpath("//select[@id='searchDropdownBox']"));
		scategory = driver.findElement(By.xpath("//*[@id='searchDropdownBox']"));
		Select oselect = new Select(scategory);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		oselect.selectByVisibleText("Electronics");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		sbtn = driver.findElement(By.xpath("//input[@type='submit'][@class='nav-input']"));
		sbtn.click();
		rtxt = driver.findElement(By.xpath("//*[@id='s-result-count']"));
		String B = rtxt.getText();
		System.out.println("The Search result is " + B);
		String C[] = B.split(" ");
		String D = C[0];
		String E[] = D.split("-");
		System.out.println("The Search result is " + E[1]);
		int F = Integer.parseInt(E[1]);
		if (F>0) {
			 SearchResult = "Valid";
			 System.out.println("Your Search is " + SearchResult);
			 logger.log(LogStatus.PASS, "Product Search Successful");
		}
		else {
			SearchResult = "Invalid";
			System.out.println("Your Search is " + SearchResult);
			logger.log(LogStatus.FAIL, "Product Search Failed");
		}
		logger.endTest();
		//return SearchResult;
		}
	
	@Test (priority=2)
	public static void Get_productlist(){
		WebElement oElement;
	
		List <WebElement> oList = driver.findElements(By.xpath("//ul[@id='s-results-list-atf']/li/descendant::h2"));
		for (int i=0 ; i<oList.size();i++){
			oElement = oList.get(i);
			System.out.println("Product " + i + "Name is : " + oElement.getText());
		}
		
	}
	
	@AfterTest
	public static void Close_browser(){
		logger.startTest("Close Browser","Close the Browser");
		driver.close();
		logger.log(LogStatus.INFO, "Closing the Browser");
		logger.endTest();
	}
		
}
