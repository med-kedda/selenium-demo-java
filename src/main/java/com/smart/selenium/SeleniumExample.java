package com.smart.selenium;

import java.awt.AWTException;
import java.awt.Event;
import java.awt.Robot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v100.indexeddb.model.Key;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumExample {

	private static WebDriver driver;
	private static Actions actions;
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		// System.setProperty("webdriver.chrome.driver",
		// "src/main/resources/chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://reclamation.eserviceslab.com");
		driver.manage().window().maximize();

		actions = new Actions(driver);
		Thread.sleep(10000);

		// Login As cnss user
		login("cnssuser", "1234");

		Thread.sleep(30000);

		// Add demande
		WebElement addDemandBtn = driver.findElement(By.xpath("//*[@id=\"sidebar\"]/app-sidebar/ul/li[2]"));
		addDemandBtn.click();
		Thread.sleep(5000);
		addDemand();

	}

	private static void addDemand() throws InterruptedException, AWTException {

		WebElement matriculeInput = driver.findElement(By.xpath("//*[@id=\"step-1\"]/form/input[1]"));
		WebElement objectifInput = driver.findElement(By.xpath("//*[@id=\"step-1\"]/form/input[2]"));
		WebElement LettreInput = driver.findElement(By.xpath("//*[@id=\"dmd\"]/div/div/div"));

		Thread.sleep(3000);
		if (matriculeInput.isDisplayed() && objectifInput.isDisplayed() && LettreInput.isDisplayed()) {
			humanTyping(matriculeInput, "8656569665");
			Thread.sleep(2000);
			humanTyping(objectifInput, "Reclamation sur prestations");
			Thread.sleep(3000);
			humanTyping(LettreInput, "Madame, Monsieur,\r\n" + "\r\n"
					+ "J’ai reçu un courrier par lequel vous indiquiez votre refus de modification de mon relevé de situation");
//			humanTyping(LettreInput, "Madame, Monsieur,\r\n" + "\r\n"
//					+ "J’ai reçu un courrier par lequel vous indiquiez votre refus de modification de mon relevé de situation.\r\n"
//					+ "\r\n"
//					+ "Je souhaite vous faire savoir que le nombre de points que vous comptabilisez au titre des assurances complémentaires ne mentionne pas les cotisations que j'ai versées durant les périodes d’activités comprises entre (indiquer les années concernées par l’erreur de calcul).\r\n"
//					+ "\r\n"
//					+ "Comme le confirment les pièces justificatives fournies dans ce courrier, je satisfais aux conditions de liquidation des droits (apporter les informations étayant votre point de vue).\r\n");
//			
			Thread.sleep(5000);

			WebElement fileUploadBtn = driver.findElement(By.xpath("//*[@id=\"1\"]"));

			fileUploadBtn.sendKeys("C:\\Users\\SMSDT-DEV-005\\Desktop\\file-sample_150kB.pdf");
			System.out.println("Uploaded a file");

			Thread.sleep(15000);
			WebElement createDemandBtn = driver.findElement(By.xpath("//*[@id=\"step-1\"]/div/a"));
			Thread.sleep(3000);
			actions.moveToElement(createDemandBtn).click().perform();

			Thread.sleep(30000);
			WebElement submitDemandBtn = driver.findElement(By.xpath("//*[@id=\"step-2\"]/div/a[2]"));
			System.out.println("Demand Created successfully");
			Thread.sleep(3000);
			actions.moveToElement(submitDemandBtn).click().perform();
			
			
			System.out.println("Demand Submitted successfully");
			
			Thread.sleep(30000);
			demandListAndDetails();
		}

	}

	private static void demandListAndDetails() throws InterruptedException {
		WebElement demandListBtn = driver.findElement(By.xpath("//*[@id=\"sidebar\"]/app-sidebar/ul/li[3]/a"));
		actions.moveToElement(demandListBtn).click().perform();
		Thread.sleep(20000);

		// Demand details
		WebElement DemandDetailsBtn = driver.findElement(By.xpath(
				"//*[@id=\"reclamation-page\"]/body/app-root/div[1]/body/section[1]/div/div/div[2]/app-listedemadecitoyen/div[2]/div[1]/table/tbody/tr[1]/td[5]/span/a"));
		
		DemandDetailsBtn.click();
		

		// Back to demands list
		actions.moveToElement(demandListBtn).click().perform();

		// Cancel demand
		Thread.sleep(30000);
		WebElement annullerDemandeBtn = driver.findElement(By.xpath("//*[@id=\"step-2\"]/div/a[2]"));
		annullerDemandeBtn.click();
		
		// Download Demand PJ
		WebElement pjDownloadBtn = driver.findElement(
				By.xpath("//*[@id=\"step-3\"]/app-demande-dynamique/div[1]/div[6]/div/div/div/div/div/div[2]/a"));
		pjDownloadBtn.click();
	}

	private static void login(String usernameVal, String passwordVal) throws InterruptedException {

		WebElement username = driver.findElement(By.xpath("//*[@id=\"username\"]"));
		WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
		WebElement submitBtn = driver.findElement(By.xpath("/html/body/div/div/div[1]/main/form/div[4]/button"));

		if (username.isDisplayed() && password.isDisplayed()) {
			if (username.isEnabled() && password.isEnabled()) {
				humanTyping(username, usernameVal);
				Thread.sleep(3000);
				humanTyping(password, passwordVal);
				String enteredText = username.getAttribute("value");
				System.out.println("Username = " + enteredText);
				Thread.sleep(3000);
				submitBtn.click();
			} else
				System.err.println("username textbox is not enabled");
		} else
			System.err.println("username textbox is not displayed");

		System.out.println("Login successfully");
	}

	private static void humanTyping(WebElement element, String text) throws InterruptedException {
		for (char lettre : text.toCharArray()) {
			element.sendKeys("" + lettre);
			Thread.sleep(1);
		}
	}

}
