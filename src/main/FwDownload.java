package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FwDownload {

        String serverPath = "http://172.16.2.103/LSBuilds_QA/ManualBuilds/LS9_Alexa_Main/20210119_165825_Alexa_LS9AD_NB6061_p5";
	Logger logger = Logger.getLogger(FwDownload.class);
	WebDriver driver;
	WebDriverWait wait;
	Process process;

	@BeforeClass
	public void setup() {
		BasicConfigurator.configure();
		String arg = "true" ; // System.getProperty("ci");
        if("true".equalsIgnoreCase(arg)) {  //Headless mode for Jenkins
            System.setProperty("webdriver.chrome.driver", "/home/libreadmin/eclipse-workspace/LibreAuromation/chromedriver");

            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> lChromePrefs = new HashMap<String, Object>();
            lChromePrefs.put("profile.default_content_settings.popups", 0);
            lChromePrefs.put("download.default_directory", "/home/libreadmin/eclipse-workspace/LibreAuromation/Firmware");
            lChromePrefs.put("browser.set_download_behavior", "{ behavior: 'allow' , downloadPath: \"/home/libreadmin/eclipse-workspace/LibreAuromation/Firmware\"}");
            //options.addArguments("--no-sandbox");
            options.addArguments("--headless"); //!!!should be enabled for Jenkins
            options.addArguments("--disable-dev-shm-usage"); //!!!should be enabled for Jenkins
            options.addArguments("--window-size=1920x1080"); //!!!should be enabled for Jenkins
            options.setExperimentalOption("prefs", lChromePrefs);
            driver = new ChromeDriver(options);
        }
            else {
		System.setProperty("webdriver.chrome.driver", "/home/libreadmin/eclipse-workspace/LibreAuromation/chromedriver");
		driver = new ChromeDriver();
		logger.info("Webdriver initilisation Done Successfully");
		wait=new WebDriverWait(driver, 120);
            }
	}
        //@Parameters("serverPath")
	@Test
	public void donwLoadBuildFromLibreServer() throws InterruptedException, IOException {
		driver.navigate().to(serverPath);
		logger.info("Started on the Chrome browser Successfully and navigated to"+" "+serverPath);
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS); 
		String getTitle = driver.getTitle();
		System.out.println(getTitle);
		//Assert.assertEquals(getTitle, "Index of /LSBuilds_QA/ManualBuilds/LS9_Alexa_Main/20210119_165825_Alexa_LS9AD_NB6061_p5");

		driver.manage().window().maximize();
		
		WebElement _83_image_network = driver.findElement(By.xpath("//a[@href='83_IMAGE']"));
		_83_image_network.click();
		System.out.println("Downloading 83_IMAGE");
		Thread.sleep(30000);
		System.out.println("Downloading 83_IMAGE Saved in Downloads");
	}

	@AfterClass
	public void teardown() throws InterruptedException {
		System.out.println("Closing Browser in @AfterClass");
		driver.quit();
	}  
}
