package com.smart.selenium;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumExample {

	private static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		//System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("http://reclam.domain.com");
		
		driver.manage().window().maximize();
		Thread.sleep(10000);
		
		//Login
		WebElement username = driver.findElement(By.xpath("//*[@id=\"username\"]"));
		WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
		WebElement submitBtn = driver.findElement(By.xpath("//*[@id=\"kc-login\"]"));
		
		login(username, password, submitBtn);
		
		Thread.sleep(30000);
		// Add demande
		WebElement addDemandBtn = driver.findElement(By.xpath("//*[@id=\"sidebar\"]/app-sidebar/ul/li[2]"));
		System.out.println("Login successfully");
		addDemandBtn.click();
		Thread.sleep(5000);
		WebElement matriculeInput = driver.findElement(By.xpath("//*[@id=\"step-1\"]/form/input[1]"));
		WebElement objectifInput = driver.findElement(By.xpath("//*[@id=\"step-1\"]/form/input[2]"));
		WebElement LettreInput = driver.findElement(By.xpath("//*[@id=\"dmd\"]/div/div/div"));
		
		
		addDemand(matriculeInput,objectifInput,LettreInput);
	}
	
	private static void addDemand(WebElement matriculeInput, WebElement objectifInput, WebElement LettreInput) throws InterruptedException, AWTException {
		
		Thread.sleep(3000);
		if(matriculeInput.isDisplayed() && objectifInput.isDisplayed() && LettreInput.isDisplayed()) {
			humanTyping(matriculeInput,"65454585466");
			Thread.sleep(2000);
			humanTyping(objectifInput,"Reclamation sur prestations");
			Thread.sleep(3000);
			humanTyping(LettreInput,"Madame, Monsieur,\r\n"
					+ "\r\n"
					+ "J’ai reçu un courrier par lequel vous indiquiez votre refus de modification de mon relevé de situation.\r\n"
					+ "\r\n"
					+ "Je souhaite vous faire savoir que le nombre de points que vous comptabilisez au titre des assurances complémentaires ne mentionne pas les cotisations que j'ai versées durant les périodes d’activités comprises entre (indiquer les années concernées par l’erreur de calcul).\r\n"
					+ "\r\n"
					+ "Comme le confirment les pièces justificatives fournies dans ce courrier, je satisfais aux conditions de liquidation des droits (apporter les informations étayant votre point de vue).\r\n");
			Thread.sleep(5000);
			
			WebElement fileUploadBtn = driver.findElement(By.xpath("//*[@id=\"1\"]"));
			System.out.println("We are here");
			fileUploadBtn.sendKeys("C:\\Users\\MedMahmoud\\Desktop\\file-sample_150kB.pdf");
			Thread.sleep(10000);
			WebElement createDemandBtn = driver.findElement(By.xpath("//*[@id=\"step-1\"]/div/a"));
			createDemandBtn.click();
			
			Thread.sleep(15000);
			WebElement submitDemandBtn = driver.findElement(By.xpath("//*[@id=\"step-2\"]/div/a[2]"));
			System.out.println("Demand Created successfully");
			Thread.sleep(30000);
			submitDemandBtn.click();
			System.out.println("Demand Submitted successfully");
			
			demandListAndDetails();
			
		}
		
		
		
	}
	
	
	private static void demandListAndDetails() {
		
		
	}

	private static void login(WebElement username, WebElement passowrd, WebElement submitBtn) throws InterruptedException {
		if(username.isDisplayed() && passowrd.isDisplayed()) {
			if(username.isEnabled() && passowrd.isEnabled()) {
				humanTyping(username,"cnssuser");
				Thread.sleep(3000);
				humanTyping(passowrd,"1234");
				String enteredText = username.getAttribute("value");
				System.out.println("Username = " + enteredText);
				Thread.sleep(3000);
				submitBtn.click();
			}
			else
				System.err.println("username textbox is not enabled");
		}
		else
			System.err.println("username textbox is not displayed");
	}
	
	
	private static void humanTyping(WebElement element, String text) throws InterruptedException {
		for(char lettre : text.toCharArray()) {
			element.sendKeys(""+lettre);
			Thread.sleep(1);
		}
	}

}
