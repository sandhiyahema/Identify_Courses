package PageObjects;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ExcelUtils;

public class homePage extends basePage{
	

	  JavascriptExecutor js;
	  public Logger logger;
	  public Properties p;
	  WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(50));
	  List<String> Course_list = new ArrayList<>();
	  basePage bp = new basePage(driver);
	  String file = System.getProperty("user.dir") + "\\OutputData\\Exceloutputfile.xlsx";

	   //constructor of the superclass

	  public homePage(WebDriver driver) {
		
		     super(driver);
		
		     js=(JavascriptExecutor)driver;
		 
		     logger = LogManager.getLogger(this.getClass()); 
		   		     		
	  }	
	  
  
	  //WebElement Locators
	  
	  @FindBy(className = "react-autosuggest__input") WebElement Search;
	  
	  @FindBy(xpath = "//button[@class='nostyle search-button']//div[@class='magnifier-wrapper']") WebElement Toclick;
	 	  
	  @FindBy(xpath = "(//div[@data-testid='search-filter-group-Language']//div[@class='cds-checkboxAndRadio-labelText'])[1]") WebElement English;
	  
	  @FindBy(xpath = "(//div[@data-testid='search-filter-group-Level']//div[@class='cds-checkboxAndRadio-labelText'])[1]") WebElement Beginner;
	  
	  @FindBy(xpath = "//div[@class='cds-CommonCard-metadata']") List<WebElement> Courses;
	  
	  @FindBy(xpath = "cds-CommonCard-ratings") List<WebElement> Ratings_count;
	  
	  @FindBy(xpath = "//a[@data-click-key='search.search.click.search_card']") List<WebElement> Course_Click;
	  
	  @FindBy(xpath = "//h1[@data-e2e='hero-title']") WebElement Course_Title;
	  
	  @FindBy(xpath = "//div[@data-e2e='key-information']//div[1]//div[2]//div[1]//div[1]") WebElement Ratings;
	  
	  @FindBy(xpath = "//div[@data-e2e='key-information']//div[contains(text(),'hours')]") WebElement Hours;
	  
	  @FindBy(xpath = "(//span[contains(normalize-space(),'more')])[2]") WebElement seeall;
	  
	  @FindBy(xpath = "//div[@data-testid='search-filter-group-Language']//div[@class='cds-checkboxAndRadio-labelText']") List<WebElement> Languages;
	  
	  @FindBy(xpath = "//div[@data-testid='search-filter-group-Level']//div[@class='cds-checkboxAndRadio-labelText']") List<WebElement> Levels;
	  
	  @FindBy(linkText = "For Enterprise") WebElement Enterprise;
	  
	  public void toSearch() throws InterruptedException, IOException {
		  
		  FileReader file=new FileReader(".\\src\\test\\resources\\config.Properties");
			 p=new Properties();
			 p.load(file);
		  
		  Search.sendKeys(p.getProperty("Tosearch"));
		  
		  bp.takeScreenshot("Tosearch");
		  
		  System.out.println("Entering search value");
		  	 
	  }
	  
	  public void toClick() throws InterruptedException, IOException {
		  
          Toclick.click();
		  
          bp.takeScreenshot("Before Applying Filter");
		  Thread.sleep(3000);
		  
		  
		  
	  }
	  
	  public void english()  {
		 
		  English.click();
		  
      }
	  
	  
	  public void beginner() throws InterruptedException {
		  
          Beginner.click();
		  
		  Thread.sleep(3000);
 		  
		  
	  }
	  
	  public void selectCourse() throws IOException {
		  
	      for (int i = 0; i < Courses.size(); i++) {
	    	  
		       String courseLevel = Courses.get(i).getText();
		       
		       Course_list.add(courseLevel); 	       
		  }
	      
	      bp.takeScreenshot("After Applying Filter");
	      
	      System.out.println("No.of courses available: " +Course_list.size());
	      
	  }
	  
	  public void displayDetails() throws InterruptedException {
		  
		  System.out.println("\n The courses are : ");
		  
		  for(int i = 0 ;i < 2; i++) {
			  
			  js.executeScript("arguments[0].style.border = '3px solid red' ",Course_Click.get(i));
			  
	          try {
	        	  
	          ExcelUtils.setCellData(file, "Course details", i+1, 0,Course_Click.get(i).getText());
	        			  		  
			  Course_Click.get(i).click();  
		
			  List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
  	          
  	          for(int j = windowHandles.size()-1;j>0;j--) {
  	        	  
  	        	 driver.switchTo().window(windowHandles.get(j));
  	        	 
		          System.out.println("The course title : " + Course_Title.getText());
		          
		          ExcelUtils.setCellData(file, "Course details", i+1, 1,Course_Title.getText());
		          
		          js.executeScript("arguments[0].style.border = '3px solid red' ",Course_Title);
		      
		          System.out.println("Ratings : " + Ratings.getText());
		          
		          ExcelUtils.setCellData(file, "Course details", i+1, 2,Ratings.getText());
		          
		          js.executeScript("arguments[0].style.border = '3px solid red' ",Ratings);
		      
		          System.out.println("Hours of Learning : " + Hours.getText());
		          
		      	  ExcelUtils.setCellData(file, "Course details", i+1, 3,Hours.getText());
		          
		          js.executeScript("arguments[0].style.border = '3px solid red' ",Hours);
		          
		          System.out.println();
		         
		          bp.takeScreenshot("Course Details");
		      
		          Thread.sleep(3000);
		      
		          driver.close();
		      
                  driver.switchTo().window(windowHandles.get(j-1));
                  
                  Thread.sleep(2000);
                  
  	          }
                  
  	    	     }catch(Exception e) {
		    	  
		          logger.error("Something went wrong"+e);
		          
	             }	
			  
		      }
	        
	  }
	  
	  public void seeAll() {
		   
		  seeall.click();
		  
		  System.out.println("-------------------------------------------------------");
		  
		  
	  }
	  
	  
	  public void languageCount() throws IOException {
		   
		 
		  System.out.println("Total number of languages : " + Languages.size());
		  
		  WebElement scroll =driver.findElement(By.xpath("//label[contains(text(),'Language')]"));
		  
		  js.executeScript("arguments[0].scrollIntoView(true);",scroll);
		  
		  for(int i = 0 ; i < Languages.size(); i++) {
			  
			  System.out.println(Languages.get(i).getText());
			  
			  try {
	        	  
	        	    ExcelUtils.setCellData(file, "Language and levels", i+1, 0 ,Languages.get(i).getText());
					
		      }catch(Exception e) {
		    	  
			          logger.error("Something went wrong"+e);
			          
		      }	
			  
		  }
		  
		  bp.takeScreenshot("Languages");
		  
		  System.out.println();
		  
	  }
	  
	  public void levelCount() throws IOException {
		  
		  System.out.println("-------------------------------------------------------");
		  
		  System.out.println("Total number of levels : " + Levels.size());
		  
		  WebElement scroll =driver.findElement(By.xpath("//label[contains(text(),'Level')]"));
		  
		  js.executeScript("arguments[0].scrollIntoView(true);",scroll);
		  
		  for(int i = 0 ; i < Levels.size();i++) {
			  
			  System.out.println(Levels.get(i).getText());
			  
			  try {
	        	  
	        	    ExcelUtils.setCellData(file, "Language and levels", i+1, 1 ,Levels.get(i).getText());
					
		      }catch(Exception e) {
		    	  
			          logger.error("Something went wrong"+e);
			          
		      }	
			  
		  }
		  bp.takeScreenshot("Levels");
		  
		  System.out.println();
		  
		  System.out.println("-------------------------------------------------------");
	  }
	  
	  public void clickEnterprise() throws InterruptedException, IOException {
		  
		  WebElement scroll = driver.findElement(By.xpath("(//p[contains(text(),'Coursera')])[6]"));
		  
		  js.executeScript("arguments[0].scrollIntoView(true);",scroll);
		  
		  Thread.sleep(2000);
		  
		  js.executeScript("arguments[0].style.border = '3px solid red' ",Enterprise);
		  
		  bp.takeScreenshot("For enterprise");
		  
		  js.executeScript("arguments[0].click();", Enterprise);
		  
		  
		  
	  }

}
