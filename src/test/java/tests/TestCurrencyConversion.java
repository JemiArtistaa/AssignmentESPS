package tests;

import static org.testng.Assert.assertEquals;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CalculatorPage;

public class TestCurrencyConversion {

	public WebDriver driver;
	public String url = "https://www.paysera.lt/v2/en-LT/fees/currency-conversion-calculator#/";
	public String expected = "";
	public CalculatorPage calculatorpageobject;

	@BeforeClass
	public void initbrowser() throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		driver.get(url);
		calculatorpageobject = new CalculatorPage(driver);
	}

	@AfterClass
	public void closebrowser() {
		driver.close();
	}

	@BeforeMethod
	public void setup() throws Exception {
		calculatorpageobject.movetofocusarea();
	}
	
//	@AfterMethod
//	public void teardown() {
//		driver.close();
//	}

	
	// Mandatory Task : 1
	// When user fills "BUY" amount, "SELL" amount box is being emptied and vice versa;
	@Test(priority = 1)
	public void testBuySellFieldEmpty() throws Exception {

		SoftAssert as = new SoftAssert();
		
		calculatorpageobject.clearinputfields();
		calculatorpageobject.inputBuy("999");
		calculatorpageobject.inputSell("9");
		as.assertEquals(calculatorpageobject.getvalueofbuy(), "");
		System.out.println(calculatorpageobject.getvalueofbuy());
		
		calculatorpageobject.clearinputfields();
		calculatorpageobject.inputSell("888");
		calculatorpageobject.inputBuy("8");
		as.assertEquals(calculatorpageobject.getvalueofsell(), "");
		System.out.println(calculatorpageobject.getvalueofsell());

		as.assertAll();
	}

	
	// Mandatory Task : 2 part : 2
	// When user selects country (select option is in the footer),currency option should be changed to the respective default currency for that country;
	@Test(priority = 2)
	public void checkcurrencyupdate() throws Exception {

		SoftAssert sa = new SoftAssert();

		calculatorpageobject.changecountryto("Spain");
		String firstCurrency = calculatorpageobject.getcurrencyname();
		System.out.println(firstCurrency);
		sa.assertEquals(calculatorpageobject.getcurrencyname(), "EUR");
				
		calculatorpageobject.changecountryto("UK");
		String finalCurrency = calculatorpageobject.getcurrencyname();
		System.out.println(finalCurrency);
		sa.assertEquals(calculatorpageobject.getcurrencyname(), "GBP");
		
		sa.assertAll();
	}
	

	// Mandatory Task : 2, part : 1, variation : 1 (changing to a country for which currency is same)
	// When user selects country (select option is in the footer),rates must be updated
	// Rates will be same (the decision is made since most of the cases rate does not change)
	@Test(priority = 3)
	public void checkrateupdateforsamecurrency() throws Exception {

		SoftAssert sa = new SoftAssert();
		
		calculatorpageobject.changecountryto("Lithuania");
		String firstCurrency = calculatorpageobject.getcurrencyname();
		System.out.println(firstCurrency);
		List<String> currencyListBefore =  calculatorpageobject.getcurrencylist();
		List<String> offRateformFirstCountry = calculatorpageobject.getoffratelist();
		List<String> eraRateFromFirstCountry = calculatorpageobject.geteraratelist();

		calculatorpageobject.changecountryto("Spain");
		String finalCurrency = calculatorpageobject.getcurrencyname();
		System.out.println(finalCurrency);
		List<String> currencyListAfter =  calculatorpageobject.getcurrencylist();
		List<String> offRateFromSecondCountry = calculatorpageobject.getoffratelist();
		List<String> eraRateFromSecondCountry = calculatorpageobject.geteraratelist();
		
		System.out.println(currencyListBefore);
		System.out.println(currencyListAfter);
		System.out.println(offRateformFirstCountry);
		System.out.println(offRateFromSecondCountry);
		System.out.println(eraRateFromFirstCountry);
		System.out.println(eraRateFromSecondCountry);
		
		sa.assertEquals(currencyListAfter, currencyListBefore);
		sa.assertEquals(offRateformFirstCountry, offRateFromSecondCountry);
		sa.assertEquals(eraRateFromFirstCountry, eraRateFromSecondCountry);

		sa.assertAll();
	}
	
	
	// Mandatory Task : 2, part : 1, variation : 2 (changing to a country for which currency is different)
	// When user selects country (select option is in the footer),rates must be updated.
	// Rates will be different
	@Test (priority = 4)
	public void checkrateupdatefordiffcurrency() throws Exception {
		
		SoftAssert sa= new SoftAssert();
		
		calculatorpageobject.changecountryto("Lithuania");
		String firstCurrency = calculatorpageobject.getcurrencyname();
		System.out.println(firstCurrency);
		List<String> currencyListBefore =  calculatorpageobject.getcurrencylist();
		List<String> offRatelistBefore =  calculatorpageobject.getoffratelist();
		List<String> eraRatelistBefore =  calculatorpageobject.geteraratelist();
		
		calculatorpageobject.changecountryto("UK");
		String finalCurrency = calculatorpageobject.getcurrencyname();
		System.out.println(finalCurrency);
		List<String> currencyListAfter =  calculatorpageobject.getcurrencylist();
		List<String> offRatelistAfter =  calculatorpageobject.getoffratelist();
		List<String> eraRatelistAfter =  calculatorpageobject.geteraratelist();
		
		for(int i=0;i<currencyListBefore.size();i++) {
			if(currencyListBefore.get(i).contains(finalCurrency)) {
				currencyListBefore.remove(i);
				offRatelistBefore.remove(i);
				eraRatelistBefore.remove(i);
				break;
			}
		}
		
		for(int i=0;i<currencyListAfter.size();i++) {
			if(currencyListAfter.get(i).contains(firstCurrency)) {
				currencyListAfter.remove(i);
				offRatelistAfter.remove(i);
				eraRatelistAfter.remove(i);
				break;	
			}
		}
		
		System.out.println(currencyListBefore);
		System.out.println(currencyListAfter);
		System.out.println(offRatelistBefore);
		System.out.println(offRatelistAfter);
		System.out.println(eraRatelistBefore);
		System.out.println(eraRatelistAfter);
		
		sa.assertEquals(currencyListAfter, currencyListBefore);
		sa.assertNotEquals(offRatelistAfter, offRatelistBefore);
		sa.assertNotEquals(eraRatelistAfter, eraRatelistBefore);

	}
	
	
	// Mandatory Task : 3 part :1
	// When bank provider's exchange amount for selling (X) is lower than the amount, provided by another (Y), then a text box is displayed, representing the loss (X-Y);
	
	@Test (priority = 5)
	public void checklossexistance() throws Exception {
		
		SoftAssert sa = new SoftAssert();
		
		calculatorpageobject.changecountryto("Lithuania");

		
		String beforexpath = "//tbody/tr[";
		String afterxpath= "]/td[5]/span[1]/span[1]/span[2]";

		List<Float> swedbackAmounts = calculatorpageobject.getswedbankamountlist();
		List<Float> eraAmount = calculatorpageobject.geteraamountlist();
		
		System.out.println(swedbackAmounts);
		System.out.println(eraAmount);

		for(int i=0;i<swedbackAmounts.size();i++) {
			System.out.println("\n\nswed : "+ swedbackAmounts.get(i));
			System.out.println("era : " + eraAmount.get(i));
			String fullxpath = beforexpath+(i+1)+afterxpath;
			
			if((swedbackAmounts.get(i) != null) && (eraAmount.get(i) != null)) {
				
				Float amount = swedbackAmounts.get(i) - eraAmount.get(i);
				System.out.println("Loss is " + amount + "for " + i);
				
				if(amount < 0) {
					try {
						driver.findElement(By.xpath(fullxpath));
						sa.assertTrue(true);
						System.out.println("Correctly displayed  for row :" + i);
					} catch (NoSuchElementException e) {
						sa.assertTrue(false);
						System.out.println("Missed display for row :" + i);
					} catch (Exception e) {
						sa.assertTrue(false);
						System.out.println("Missed display for row :" + i);
					}
				}
				
				else {
					try {					
						driver.findElement(By.xpath(fullxpath));
						sa.assertTrue(false);
						System.out.println("Wrongly Displayed for row :" + i);
					} catch (NoSuchElementException e) {
						sa.assertTrue(true);
						System.out.println("Correctly not displayed for :" + i);
					} catch (Exception e) {
						sa.assertTrue(true);
						System.out.println("Correctly not displayed for :" + i);
					}
				}
			}
			
			else {
				try {
					driver.findElement(By.xpath(fullxpath));
					sa.assertTrue(false);
					System.out.println("Wrongly Displayed for row :" +i);
				} catch (NoSuchElementException e) {
					sa.assertTrue(true);
					System.out.println("Correctly not displayed for :" +i);
				} catch (Exception e) {
					sa.assertTrue(true);
					System.out.println("Correctly not displayed for :" +i);
				}
			}
		}
	}
	
	
	// Additional Task : 1
	// Check Displayed loss calculation is correct
	@Test(priority = 6)
	public void checklosscalculation() throws Exception {

		SoftAssert sa = new SoftAssert();
		
		calculatorpageobject.changecountryto("Lithuania");

		List<Float> amountFromEraValue = calculatorpageobject.geteraamountlist();
		List<Float> amountFromSwedbankValue = calculatorpageobject.getswedbankamountlist();
		List<Float> lossAmount = calculatorpageobject.lossAmountValueList();

		DecimalFormat dcformat = new DecimalFormat("##.00");

		for (int i = 0; i < amountFromEraValue.size(); i++) {

			if ((amountFromEraValue.get(i) != null) && (amountFromSwedbankValue.get(i) != null) && (lossAmount.get(i) != null)) {
				
				String calculatedLossAmount = dcformat.format(amountFromSwedbankValue.get(i) - amountFromEraValue.get(i));
				String displayedLossAmount = dcformat.format(lossAmount.get(i));
				
				System.out.println(i + ":" + calculatedLossAmount);
				System.out.println(i + ":" + displayedLossAmount);
				
				if (calculatedLossAmount.equals(displayedLossAmount)) {
					sa.assertTrue(true);
				} else {
					sa.assertTrue(false);
				}
			}
			else {
				sa.assertTrue(true);
				System.out.println("not applicable");
			}
		}
		sa.assertAll();
	}
	
	// Additional Task : 2
	// Check all rates (off and era) get updated after changing currency from dropdown and clicking filter button
	@Test (priority = 7)
	public void checkRateUpdate() throws Exception {

		SoftAssert sa = new SoftAssert();
		
		calculatorpageobject.changecountryto("Lithuania");

		String currenynamebefore = calculatorpageobject.getcurrencyname();
		System.out.println(currenynamebefore);
		List<String> currencyListBefore = calculatorpageobject.getcurrencylist();
		List<String> offRateListBefore = calculatorpageobject.getoffratelist();
		List<String> eraRateListBefore = calculatorpageobject.geteraratelist();

		calculatorpageobject.selectcurrencytousd();
		String currenynameAfter = calculatorpageobject.getcurrencyname();
		calculatorpageobject.clickFilterButton();
		System.out.println(currenynameAfter);
		
		List<String> currencyListAfter = calculatorpageobject.getcurrencylist();
		List<String> offRateListAfter = calculatorpageobject.getoffratelist();
		List<String> eraRateListAfter = calculatorpageobject.geteraratelist();

		for (int i = 0; i < currencyListBefore.size(); i++) {

			if (currencyListBefore.get(i).contains(currenynameAfter)) {
				currencyListBefore.remove(i);
				offRateListBefore.remove(i);
				eraRateListBefore.remove(i);
				break;
			}
		}

		for (int i = 0; i < currencyListAfter.size(); i++) {

			if (currencyListAfter.get(i).contains(currenynamebefore)) {
				currencyListAfter.remove(i);
				offRateListAfter.remove(i);
				eraRateListAfter.remove(i);
				break;
			}
		}

		System.out.println(currencyListBefore);
		System.out.println(currencyListAfter);

		System.out.println(offRateListBefore);
		System.out.println(offRateListAfter);
		
		System.out.println(eraRateListBefore);
		System.out.println(eraRateListAfter);

		sa.assertEquals(currencyListBefore, currencyListAfter);
		sa.assertNotEquals(offRateListBefore, offRateListAfter);
		sa.assertNotEquals(eraRateListBefore, eraRateListAfter);

		sa.assertAll();
	}
	
	
	// Additional Task : 3
	// check amounts get updated based on sell field input value and by clicking filter button
	@Test(priority = 8)
	public void checkamountupdatetwithsellinput() throws Exception {

		SoftAssert sa = new SoftAssert();
		
		calculatorpageobject.changecountryto("Lithuania");
		
		System.out.println("Previous Sell input value : " +calculatorpageobject.getvalueofsell());

		List<ArrayList<Float>> listOfAmountTypeBefore = new ArrayList<ArrayList<Float>>();
		listOfAmountTypeBefore.add((ArrayList<Float>) calculatorpageobject.amounts("era"));
		listOfAmountTypeBefore.add((ArrayList<Float>) calculatorpageobject.amounts("swedbank"));
		listOfAmountTypeBefore.add((ArrayList<Float>) calculatorpageobject.amounts("seb"));
		listOfAmountTypeBefore.add((ArrayList<Float>) calculatorpageobject.amounts("citadel"));
		listOfAmountTypeBefore.add((ArrayList<Float>) calculatorpageobject.amounts("luminor"));

		calculatorpageobject.clearsell();
		calculatorpageobject.inputSell("1000");
		System.out.println("Later Sell input value : " +calculatorpageobject.getvalueofsell());
		calculatorpageobject.clickFilterButton();

		List<ArrayList<Float>> listOfAmountTypeAfter = new ArrayList<ArrayList<Float>>();
		listOfAmountTypeAfter.add((ArrayList<Float>) calculatorpageobject.amounts("era"));
		listOfAmountTypeAfter.add((ArrayList<Float>) calculatorpageobject.amounts("swedbank"));
		listOfAmountTypeAfter.add((ArrayList<Float>) calculatorpageobject.amounts("seb"));
		listOfAmountTypeAfter.add((ArrayList<Float>) calculatorpageobject.amounts("citadel"));
		listOfAmountTypeAfter.add((ArrayList<Float>) calculatorpageobject.amounts("luminor"));
		
		for (int j = 0; j < listOfAmountTypeBefore.size(); j++) {

			for (int i = 0; i < listOfAmountTypeBefore.get(j).size(); i++) {

				if (listOfAmountTypeBefore.get(j).get(i) != listOfAmountTypeAfter.get(j).get(i)) {
					System.out.println(listOfAmountTypeBefore.get(j).get(i));
					System.out.println(listOfAmountTypeAfter.get(j).get(i));
					sa.assertTrue(true);
				} else {
					System.out.println(listOfAmountTypeBefore.get(j).get(i));
					System.out.println(listOfAmountTypeAfter.get(j).get(i));
					sa.assertTrue(false);
				}
			}
		}
		sa.assertAll();

	}
	

	// Additional Task : 4
	// check amounts get updated based on buy field input value and by clicking filter button
	@Test(priority = 9)
	public void checkamountupdatetwithbuyinput() throws Exception {

		SoftAssert sa = new SoftAssert();
				
		calculatorpageobject.changecountryto("Lithuania");
		Thread.sleep(3000);
		calculatorpageobject.inputBuy("100");	
		Thread.sleep(1000);

		System.out.println("Previous Buy input value : " +calculatorpageobject.getvalueofbuy());

		calculatorpageobject.clickFilterButton();

		List<ArrayList<Float>> listOfAmountTypeBefore = new ArrayList<ArrayList<Float>>();
		listOfAmountTypeBefore.add((ArrayList<Float>) calculatorpageobject.amounts("era"));
		listOfAmountTypeBefore.add((ArrayList<Float>) calculatorpageobject.amounts("swedbank"));
		listOfAmountTypeBefore.add((ArrayList<Float>) calculatorpageobject.amounts("seb"));
		listOfAmountTypeBefore.add((ArrayList<Float>) calculatorpageobject.amounts("citadel"));
		listOfAmountTypeBefore.add((ArrayList<Float>) calculatorpageobject.amounts("luminor"));

		calculatorpageobject.clearbuy();
		calculatorpageobject.inputBuy("1000");
		System.out.println("Later Buy input value : " +calculatorpageobject.getvalueofbuy());
		calculatorpageobject.clickFilterButton();

		List<ArrayList<Float>> listOfAmountTypeAfter = new ArrayList<ArrayList<Float>>();
		listOfAmountTypeAfter.add((ArrayList<Float>) calculatorpageobject.amounts("era"));
		listOfAmountTypeAfter.add((ArrayList<Float>) calculatorpageobject.amounts("swedbank"));
		listOfAmountTypeAfter.add((ArrayList<Float>) calculatorpageobject.amounts("seb"));
		listOfAmountTypeAfter.add((ArrayList<Float>) calculatorpageobject.amounts("citadel"));
		listOfAmountTypeAfter.add((ArrayList<Float>) calculatorpageobject.amounts("luminor"));

		for (int j = 0; j < listOfAmountTypeBefore.size(); j++) {

			for (int i = 0; i < listOfAmountTypeBefore.get(j).size(); i++) {

				if (listOfAmountTypeBefore.get(j).get(i) != listOfAmountTypeAfter.get(j).get(i)) {
					System.out.println(listOfAmountTypeBefore.get(j).get(i));
					System.out.println(listOfAmountTypeAfter.get(j).get(i));
					sa.assertTrue(true);
				} else {
					System.out.println(listOfAmountTypeBefore.get(j).get(i));
					System.out.println(listOfAmountTypeAfter.get(j).get(i));
					sa.assertTrue(false);
				}
			}
		}
		sa.assertAll();
	}

	// Additional Task : 5
	// check rates get updated based on sell field input value and by clicking  filter button
	@Test(priority = 10)
	public void checkrateupdatewithsellinput() throws Exception {

		SoftAssert sa = new SoftAssert();
				
		calculatorpageobject.changecountryto("Lithuania");
		Thread.sleep(3000);
		System.out.println("Previous sell input value : " + calculatorpageobject.getvalueofsell());

		List<Float> offRateListBefore = calculatorpageobject.rates("off");
		List<Float> eraRateListBefore = calculatorpageobject.rates("era");

		calculatorpageobject.clearsell();
		calculatorpageobject.inputSell("1000");
		System.out.println("Later sell input value : " + calculatorpageobject.getvalueofsell());
		calculatorpageobject.clickFilterButton();

		List<Float> offRateListAfter = calculatorpageobject.rates("off");
		List<Float> eraRateListAfter = calculatorpageobject.rates("era");
		
		System.out.println(offRateListBefore);
		System.out.println(offRateListAfter);
		
		System.out.println(eraRateListBefore);
		System.out.println(eraRateListAfter);

		sa.assertNotEquals(offRateListBefore, offRateListAfter);
		sa.assertNotEquals(eraRateListBefore, eraRateListAfter);

		sa.assertAll();
	}

	// Additional Task : 6
	// check rates get updated based on buy field input value and by clicking filter button
	@Test(priority = 11)
	public void checkratestupdatewithbuyinput() throws Exception {

		SoftAssert sa = new SoftAssert();
		
		calculatorpageobject.changecountryto("Lithuania");
		calculatorpageobject.inputBuy("50");
		System.out.println("Previous buy input value : " + calculatorpageobject.getvalueofbuy());
		calculatorpageobject.clickFilterButton();

		List<Float> offRateListBefore = calculatorpageobject.rates("off");
		List<Float> eraRateListBefore = calculatorpageobject.rates("pay");

		calculatorpageobject.clearbuy();
		calculatorpageobject.inputBuy("5000");
		System.out.println("Later buy input value : " + calculatorpageobject.getvalueofbuy());
		calculatorpageobject.clickFilterButton();

		List<Float> offRateListAfter = calculatorpageobject.rates("off");
		List<Float> eraRateListAfter = calculatorpageobject.rates("era");
			
		System.out.println(offRateListBefore);
		System.out.println(offRateListAfter);
		
		System.out.println(eraRateListBefore);
		System.out.println(eraRateListAfter);

		sa.assertNotEquals(offRateListBefore, offRateListAfter);
		sa.assertNotEquals(eraRateListBefore, eraRateListAfter);

		sa.assertAll();
	}
	
	// Additional Task : 7
	// Check clearfilter button returns system to its initiate state
	@Test (priority = 12)
	public void checkclearfilterbutton() throws Exception {
		
		SoftAssert sa = new SoftAssert();
		
		calculatorpageobject.changecountryto("Lithuania");
		
		String sellAmountValueBefore = calculatorpageobject.getvalueofsell();
		String sellCurrencyBefore = calculatorpageobject.getcurrencyname();
		
		calculatorpageobject.clearsell();
		calculatorpageobject.inputSell("5000");
		calculatorpageobject.selectcurrencytousd();
		
		String sellAmountValueAfter = calculatorpageobject.getvalueofsell();
		String sellCurrencyAfter = calculatorpageobject.getcurrencyname();
		
		calculatorpageobject.clickclearfilterbutton();
		
		String sellAmountValueFinal = calculatorpageobject.getvalueofsell();
		String sellCurrencyFinal = calculatorpageobject.getcurrencyname();
		
		System.out.println(sellAmountValueBefore);
		System.out.println(sellAmountValueAfter);
		System.out.println(sellAmountValueFinal);
		System.out.println(sellCurrencyBefore);
		System.out.println(sellCurrencyAfter);
		System.out.println(sellCurrencyFinal);
		
		sa.assertEquals(sellAmountValueFinal, sellAmountValueBefore);
		sa.assertEquals(sellCurrencyFinal, sellCurrencyBefore);
		sa.assertNotEquals(sellAmountValueFinal, sellAmountValueAfter);
		sa.assertNotEquals(sellCurrencyFinal, sellAmountValueAfter);
		
	}
	
	// Addtional task : 8
	// Check if system does not allow to set "All" for both currency dropdpwn
	@Test (priority = 13)
	public void checkcurrencybuttonsvalue() throws Exception {
		
		SoftAssert sa= new SoftAssert();
		
		calculatorpageobject.changecountryto("Lithuania");
		calculatorpageobject.selectcurrencytoall();
		String currencyonevaluebefore = calculatorpageobject.getcurrencyname();
		calculatorpageobject.selectcurrency2toall();
		String currencyonevalueafter = calculatorpageobject.getcurrencyname();
		System.out.println(currencyonevaluebefore);
		System.out.println(currencyonevalueafter);
		
		sa.assertNotEquals(currencyonevalueafter, currencyonevaluebefore);
		
		String currencytwovaluebefore = calculatorpageobject.getcurrencyname();
		calculatorpageobject.selectcurrencytoall();
		String currencytwovalueafter = calculatorpageobject.getcurrencyname();
		
		System.out.println(currencytwovaluebefore);
		System.out.println(currencytwovalueafter);
		
		sa.assertNotEquals(currencytwovalueafter, currencytwovaluebefore);

		sa.assertAll();
		
	}
	
	// Additional Task : 9
	// Check if system reset currency to default  when currency dropdown in "All" state 
	// and then another currency dropdown is selected as "All" based on selected country
	@Test (priority = 14)
	public void checkcurrencyresetbasedoncountry() throws Exception {
		
		SoftAssert sa= new SoftAssert();
		
		calculatorpageobject.changecountryto("UK");
		String currencyonebefore = calculatorpageobject.getcurrencyname();
		calculatorpageobject.selectcurrencytoall();
		calculatorpageobject.selectcurrency2toall();
		String currencyoneAfter = calculatorpageobject.getcurrencyname();
		
		System.out.println(currencyonebefore);
		System.out.println(currencyoneAfter);
		
		sa.assertEquals(currencyoneAfter, currencyonebefore);
		sa.assertEquals(currencyonebefore, "GBP");
		sa.assertEquals(currencyoneAfter, "GBP");
		
		calculatorpageobject.changecountryto("Bulgaria");
		String currencybefore = calculatorpageobject.getcurrencyname();
		calculatorpageobject.selectcurrencytoall();
		calculatorpageobject.selectcurrency2toall();
		String currencyAfter = calculatorpageobject.getcurrencyname();
		
		System.out.println(currencybefore);
		System.out.println(currencyAfter);
		
		sa.assertEquals(currencyAfter, currencybefore);
		sa.assertEquals(currencybefore, "BGN");
		sa.assertEquals(currencyAfter, "BGN");

		sa.assertAll();

	}
}
