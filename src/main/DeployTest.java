package main;
import org.openqa.selenium.WebDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
public class DeployTest {

	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	ConfigFileReader propreader= new ConfigFileReader();
	Logger logger = Logger.getLogger(DeployTest.class);
	String indexmap= "/index.asp";
	String schema="http://";
	String ipAddress = propreader.getDeviceIPAddress();

	@BeforeClass
	public void setup() {
		BasicConfigurator.configure();

		logger.info("Entered The Basic Setup Method");
		String arg = "true" ; // System.getProperty("ci");
       /* if("true".equalsIgnoreCase(arg)) {  //Headless mode for Jenkins
            System.setProperty("webdriver.chrome.driver", "/home/libreadmin/eclipse-workspace/LibreAuromation/chromedriver");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--headless"); //!!!should be enabled for Jenkins
            options.addArguments("--disable-dev-shm-usage"); //!!!should be enabled for Jenkins
            options.addArguments("--window-size=1920x1080"); //!!!should be enabled for Jenkins
            driver = new ChromeDriver(options);
            logger.info("Webdriver initilisation Done Successfully");
            wait=new WebDriverWait(driver, 120);
            js = (JavascriptExecutor) driver;
        }
        else{ // For local testing with browser*/
		System.setProperty("webdriver.chrome.driver", propreader.getDriverPath());


		//For Windows.  Use the above line for Mac
		//System.setProperty("webdriver.chrome.driver", "/home/libreadmin/eclipse-workspace/LibreAuromation/chromedriver");
		driver = new ChromeDriver();
		logger.info("Webdriver initilisation Done Successfully");
driver.get(schema+ipAddress+indexmap);
logger.info(ipAddress+" "+"Started on the Chrome browser Successfully");
		wait=new WebDriverWait(driver, 120);
		js = (JavascriptExecutor) driver;
		}
	//}

	@Test(priority=0)
	public void enterDeviceIPAddress() {
		driver.get(schema+ipAddress+indexmap);
		
		logger.info(ipAddress+" "+"Started on the Chrome browser Successfully");
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS); 
		//driver.manage().window().maximize();
		logger.info("Chrome Browser Maximised");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("you have entered the wrong Ip Address/loading webpage taking too much time");
			logger.error("you have entered the wrong Ip Address/loading webpage taking too much time, please check the Netwrok connection or IP Address you have entered");
		}
		js.executeScript("window.scrollBy(0,3000)");
	}
	
	@Test(priority=1) 
	public void scrollDownAndClickUpdate() throws InterruptedException {
		
		Thread.sleep(1000);
		WebElement deviceName = driver.findElement(By.id("AdvBtn_UPG"));
		deviceName.click();
		Thread.sleep(1000);
		
	}
	@Test(priority=2) 	
	public void select_83_Network_Image() throws InterruptedException {
		driver.findElement(By.id("fileid")).sendKeys(propreader.getNetworkImageUrl());
		logger.info("Network Image Selected");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		logger.info("Clicked on Update ");
	}
	@Test(priority=3)  
	public void startFirmwareUpdate() {
		try {
			wait.until(ExpectedConditions.titleIs("LIBRE-SYNC Firmware"));
			List<WebElement> Submite = driver.findElements(By.xpath("//input[@type='submit']"));
			logger.info("Clicked on Ok to Confirm the Update ");
			Submite.get(0).click();

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			logger.info("Chrome Browser Closed Successfully ");
			driver.close();
		}
	}
	@AfterClass
	public void teardown() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reload-button")));
		System.out.println("Closing Browser in @AfterClass");
		driver.quit();
	}


}
