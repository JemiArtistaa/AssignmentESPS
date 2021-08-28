package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import net.bytebuddy.asm.Advice.Return;


public class CalculatorPage {
	
	public WebDriver driver;
	
	public CalculatorPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//input[@data-ng-model='currencyExchangeVM.filter.to_amount']")
	WebElement buyInputField;
	
	@FindBy(xpath="//input[@data-ng-model='currencyExchangeVM.filter.from_amount']")
	WebElement sellInputField;
	
	@FindBy(xpath="//span[@role='button']")
	WebElement countrySelectIcon;

	@FindBy(xpath="//button[@id='countries-dropdown']")
	WebElement countrySelectDropdown;
	
	@FindBy(xpath="//a[normalize-space()='Lithuania']")
	WebElement countryselectlithuania;
	
	@FindBy(xpath="//a[normalize-space()='Spain']")
	WebElement countryselectSpain;
	
	@FindBy(xpath="//a[normalize-space()='United Kingdom']")
	WebElement countryselectUk;
	
	@FindBy(xpath="//a[normalize-space()='Bulgaria']")
	WebElement countryselectbulgaria;
	
	@FindBy(xpath="//div[@data-ng-model='currencyExchangeVM.filter.from']")
	WebElement currencyButton;
	
	@FindBy(xpath="//div[@data-ng-model='currencyExchangeVM.filter.to']")
	WebElement currencyButton2;
	
	@FindBy(xpath="//button[contains(text(),'Filter')]")
	WebElement filterButton;
	
	@FindBy(xpath = "//button[contains(text(),'Clear filter')]")
	WebElement clearFilterButton;
	
	public void clearinputfields() throws Exception {
		clearsell();
		clearbuy();
		Thread.sleep(1000);
	}
	
	public void clickclearfilterbutton() throws Exception {
		clearFilterButton.click();
		Thread.sleep(5000);
	}
	
	public void  movetofocusarea() throws Exception {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@class='widget-rate-page ng-scope']")));
		Thread.sleep(1000);
	}
	
	public void clearbuy() throws InterruptedException {
		buyInputField.clear();
		Thread.sleep(1000);
	}
	
	public void clearsell() throws Exception {
		sellInputField.clear();
		Thread.sleep(500);
	}
	public void inputBuy(String amount) throws Exception {
		buyInputField.sendKeys(amount);
		Thread.sleep(1000);
	}

	public void inputSell(String amount) throws Exception {
		sellInputField.sendKeys(amount);
		Thread.sleep(1000);
	}
	
	public String getvalueofbuy() throws Exception {
		Thread.sleep(1000);
		return buyInputField.getAttribute("value");
	}
	
	public String getvalueofsell() throws Exception {
		Thread.sleep(500);
		return sellInputField.getAttribute("value");
	}
	
	public void clickFilterButton() throws Exception {
		filterButton.click();
		Thread.sleep(2000);
	}

	public List<String> getoffratelist() throws Exception {
		
		List<WebElement> officalRate =  driver.findElements(By.xpath("//td[@data-title='Official rate']"));
		List<String> officialRateList = new ArrayList<String>();
		
		for (WebElement e : officalRate) {
			officialRateList.add(e.getText());
		}
		
		return officialRateList;		

	}
	
	public List<String> geteraratelist() throws Exception {
		
		List<WebElement> eraRate =  driver.findElements(By.xpath("//td[@class='ng-binding ng-scope commercial-rate']"));
		List<String> eraRateList = new ArrayList<String>();
		
		for (WebElement e : eraRate) {
			eraRateList.add(e.getText());
		}
		Thread.sleep(1000);
		return eraRateList;		
	}
	
	public List<String> getcurrencylist() throws Exception{
		List<WebElement> currList = driver.findElements(By.xpath("//td[@data-ng-if='currencyExchangeVM.rates[currencyExchangeVM.PROVIDERS.OFFICIAL]']"));
		
		List<String> currencyList= new ArrayList<String>();
		for (int i =0 ; i< currList.size(); i++) {
			
			currencyList.add(currList.get(i).getText());
		}
		Thread.sleep(1000);
		return currencyList;

	}
	
	public void selectcurrencytousd() throws Exception {
		currencyButton.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[@data-ng-bind='currency'][normalize-space()='USD']")).click();
		Thread.sleep(1000);
	}
	
	public void selectcurrencytoall() throws Exception {
		currencyButton.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[@data-ng-bind='currency'][normalize-space()='All']")).click();
		Thread.sleep(1000);
	}
	
	public void selectcurrency2toall() throws Exception {
		currencyButton2.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[@data-ng-bind='currency'][normalize-space()='All']")).click();
		Thread.sleep(1000);
	}
	
	public void selectcurrencytoeur() throws Exception {
		currencyButton.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[@data-ng-bind='currency'][normalize-space()='EUR']")).click();
		Thread.sleep(1000);
	}
	
	public void changecurrencytousd() throws Exception {
		currencyButton.click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[@data-ng-bind='currency'][normalize-space()='USD']")).click();
		clickFilterButton();
		Thread.sleep(1000);
	}
	
	public void changecountryto(String countryName) throws Exception {	
		
		countrySelectIcon.click();
		Thread.sleep(500);
		countrySelectDropdown.click();
		Thread.sleep(500);
		if(countryName.equalsIgnoreCase("Lithuania"))
			countryselectlithuania.click();
		if(countryName.equalsIgnoreCase("Spain"))
			countryselectSpain.click();
		if(countryName.equalsIgnoreCase("UK"))
			countryselectUk.click();
		if(countryName.equalsIgnoreCase("Bulgaria"))
			countryselectbulgaria.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@class='widget-rate-page ng-scope']")));
		Thread.sleep(2000);
	}
	
	public void changecountrytolithuania() throws Exception {
		countrySelectIcon.click();
		Thread.sleep(500);
		countrySelectDropdown.click();
		Thread.sleep(500);
		countryselectlithuania.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@class='widget-rate-page ng-scope']")));
		Thread.sleep(1000);
	}
	
	public void changecountrytospain() throws Exception {		
		countrySelectIcon.click();
		Thread.sleep(500);
		countrySelectDropdown.click();
		Thread.sleep(500);
		countryselectSpain.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@class='widget-rate-page ng-scope']")));
		Thread.sleep(1000);
	}
	
	public void changecountrytobulgaria() throws Exception {
		
//		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		countrySelectIcon.click();
		Thread.sleep(500);
		countrySelectDropdown.click();
		Thread.sleep(500);
		countryselectbulgaria.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@class='widget-rate-page ng-scope']")));
		Thread.sleep(1000);
	}
	
	public void changecountrytouk() throws Exception {
		
//		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		countrySelectIcon.click();
		Thread.sleep(500);
		countrySelectDropdown.click();
		Thread.sleep(500);
		countryselectUk.click();		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@class='widget-rate-page ng-scope']")));
		Thread.sleep(3000);
	}
	
	public List<String> getcountrylist() throws Exception{
		
//		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		countrySelectIcon.click();
		Thread.sleep(500);
		countrySelectDropdown.click();
		List<WebElement> countryList = driver.findElements(By.xpath("//ul[@aria-labelledby='countries-dropdown']"));
		List<String> countryListName = new ArrayList<String>();
		for(WebElement e : countryList) {
			countryListName.add(e.getText());
		}
		return countryListName;
	} 
	
	public List<String> getcountrycurrecny(){
	
		List<String> currencyListName = new ArrayList<String>();
		currencyListName.add("EUR");
		currencyListName.add("EUR");
		currencyListName.add("EUR");
		currencyListName.add("EUR");
		currencyListName.add("EUR");
		currencyListName.add("EUR");
		currencyListName.add("PLN");
		currencyListName.add("GBP");
		currencyListName.add("EUR");
		currencyListName.add("RUB");
		currencyListName.add("DZD");
		currencyListName.add("ALL");
		currencyListName.add("EUR");
		currencyListName.add("UAH");

		return currencyListName;
	} 
	
	public String getcurrencyname() throws Exception {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", currencyButton);
		Thread.sleep(1000);
		String currency ;
		currency =  currencyButton.getText();
		Thread.sleep(1000);
		return currency;
	}
	
	public List<Float> rates( String rateType){
		
		String type = rateType;
		String off = "//td[@data-title='Official rate']";
		String era = "//td[@class='ng-binding ng-scope commercial-rate']";

		List<WebElement> rateList =  new ArrayList<WebElement>();
	    List<Float> rateValueList = new ArrayList<Float>();
		
		if(type.equalsIgnoreCase("off"))
			rateList = driver.findElements(By.xpath(off));
		if(type.equalsIgnoreCase("era"))
			rateList = driver.findElements(By.xpath(era));			
		for (WebElement e : rateList) {
			if(e.getText().equals("-"))
				continue;
			else 
				rateValueList.add(Float.parseFloat(e.getText().replace(",", "")));		
		}
			
		return rateValueList;
	}
	
	public List<Float> amounts( String amountType){
		
		String type = amountType;
		String era = "//td[@data-title='Paysera rate']";
		String swedbank = "//td[@data-title='Swedbank amount']";
		String seb = "//td[@data-title='SEB amount']";
		String citadel = "//td[@data-title='Citadele amount']";
		String luminor = "//td[@data-title='Luminor amount']";
		
		List<WebElement> amountList =  new ArrayList<WebElement>();
	    List<Float> AmountValueList = new ArrayList<Float>();

		
		if(type.equalsIgnoreCase("era"))
			amountList = driver.findElements(By.xpath(era));
		if(type.equalsIgnoreCase("swedbank"))
			amountList = driver.findElements(By.xpath(swedbank));
		if(type.equalsIgnoreCase("seb"))
			amountList = driver.findElements(By.xpath(seb));
		if(type.equalsIgnoreCase("citadel"))
			amountList = driver.findElements(By.xpath(citadel));
		if(type.equalsIgnoreCase("luminor"))
			amountList = driver.findElements(By.xpath(luminor));
		
		if(type.equalsIgnoreCase("era")) {
			
			for (WebElement e : amountList) {
				if(e.getText().equals("-"))
//					AmountValueList.add(Float.parseFloat("0.0"));
					continue;
				else 
					AmountValueList.add(Float.parseFloat(e.getText().replace(",", "")));		
	
			}
		}
		else{
			
			for (WebElement e : amountList) {
				if(e.getText().equals("-"))
//					AmountValueList.add(Float.parseFloat("0.0"));
					continue;
				else 
					AmountValueList.add(Float.parseFloat(e.getText().split("\n")[0].replace(",", "")));		
	
			}
		}
			
		return AmountValueList;
	}
	
	public List<Float> geteraamountlist() {
		
		List<WebElement> eraAmount = driver.findElements(By.xpath("//td[@data-title='Paysera rate']"));
	    List<Float> eraAmountValue = new ArrayList<Float>();
	    
		for (WebElement e : eraAmount) {
			if(e.getText().equals("-"))
				eraAmountValue.add(null);
			else
				eraAmountValue.add(Float.parseFloat(e.getText().replace(",", "")));		
		}
		return eraAmountValue;
	}
	
	public List<Float> getswedbankamountlist() {
		
		List<WebElement> SwedBankAmount = driver.findElements(By.xpath("//td[@data-title='Swedbank amount']//span[@class='ng-scope']"));
        List<Float> swedbankAmounValue = new ArrayList<Float>();
        
		for (WebElement e : SwedBankAmount) {
	        	        
			if(e.getText().equals("-"))
				swedbankAmounValue.add(null);
			else 
		        swedbankAmounValue.add(Float.parseFloat(e.getText().split("\n")[0].replace(",", "")));
		}
		return swedbankAmounValue;
	}
	
	public List<Float> getamountsofseb() {
		
		List<WebElement>  sebAmountList = driver.findElements(By.xpath("//td[@data-title='SEB amount']"));

	    List<Float> sebAmountListFloat = new ArrayList<Float>();
	    
		for (int i=0; i <31; i++) {
			if(sebAmountList.get(i).getText().equals("-"))
				sebAmountListFloat.add(Float.parseFloat("0.0"));
			else
				sebAmountListFloat.add(Float.parseFloat(sebAmountList.get(i).getText().replace(",", "")));		
		}
		return sebAmountListFloat;

	}
	
	public List<Float> getamountsofcitadel() {
		
		List<WebElement>  citadelAmountList = driver.findElements(By.xpath("//td[@data-title='Citadele amount']"));		
	    List<Float> citadelAmountListFloat = new ArrayList<Float>();

		for (int i=0; i <31; i++) {
			if(citadelAmountList.get(i).getText().equals("-"))
				citadelAmountListFloat.add(Float.parseFloat("0.0"));
			else
				citadelAmountListFloat.add(Float.parseFloat(citadelAmountList.get(i).getText().replace(",", "")));		
		}
		return citadelAmountListFloat;

	}
	
	public List<Float> getamountsofluminor() {
		
		List<WebElement>  luminorAmountList = driver.findElements(By.xpath("//td[@data-title='Luminor amount']"));
	    List<Float> luminorAmountListFloat = new ArrayList<Float>();

		for (int i=0; i <31; i++) {
			if(luminorAmountList.get(i).getText().equals("-"))
				luminorAmountListFloat.add(Float.parseFloat("0.0"));
			else
				luminorAmountListFloat.add(Float.parseFloat(luminorAmountList.get(i).getText().replace(",", "")));		
		}
		return luminorAmountListFloat;

	}
		
	public List<Float> lossAmountValueList() {
		
		List<WebElement> lossSwedBankAmount = driver.findElements(By.xpath("//td[@data-title='Swedbank amount']//span[@class='ng-scope']"));
        List<Float> lossAmountValue = new ArrayList<Float>();

		for (WebElement e : lossSwedBankAmount) {
	        	        
			if(e.getText().equals("-")) {
				lossAmountValue.add(null);
			}else {
				try {
			        lossAmountValue.add(Float.parseFloat(e.getText().split("\n")[1].replace("(", "").replace(")", "").replace(",", "")));
				} catch (Exception e2) {
					lossAmountValue.add(null);
				}
			}
		}
		return lossAmountValue;
	}
	
	public String getoffrateforusd() {
		
		List<WebElement> currencyName = driver.findElements(By.xpath("//td[@data-ng-if='currencyExchangeVM.rates[currencyExchangeVM.PROVIDERS.OFFICIAL]']"));
		List<WebElement> offRateList = driver.findElements(By.xpath("//td[@data-title='Official rate']"));
		
		String currencyText;
		String offRate = null;		
		for (int i = 0; i < 31; i++) {
			
			currencyText = currencyName.get(i).getText();
			
			if(currencyText.contains("USD")) {
				
				offRate = offRateList.get(i).getText();
				break;
			}

		}
		
		System.out.println(offRate);
		return offRate;
	}
	
	public String geterarateforusd() {
		
		List<WebElement> currencyName = driver.findElements(By.xpath("//td[@data-ng-if='currencyExchangeVM.rates[currencyExchangeVM.PROVIDERS.OFFICIAL]']"));
		List<WebElement> eraRateList = driver.findElements(By.xpath("//td[@class='ng-binding ng-scope commercial-rate']"));
		
		String currencyText;
		String eraRate = null;		
		for (int i = 0; i < 31; i++) {
			
			currencyText = currencyName.get(i).getText();
			
			if(currencyText.contains("USD")) {
				
				eraRate = eraRateList.get(i).getText();
				break;
			}
		}
		System.out.println(eraRate);
		return eraRate;
	}
	
}
