package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
				
		             features = {"Features"},glue= {"hooks","stepDefinitions"},
					plugin= {
								"pretty", "html:Reports/myreport.html", 
								"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" ,
							
							},
							
					dryRun=false,    // checks mapping between scenario steps and step definition methods
					monochrome=true,    // to avoid junk characters in output
					publish=true   // to publish report in cucumber server
					
		)

public class TestRunner {

}
