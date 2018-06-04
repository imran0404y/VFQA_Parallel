package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Libraries.Driver;
import Libraries.Result;
import Libraries.Utlities;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class MCare extends Driver{
	
	public static String installMyVodafoneApp() {
		String Test_OutPut = "", Status = "", DeviceName, Path;
		DeviceName = utils.fetchData("DeviceName");
		Path = utils.fetchData("Path");
		try {
		FileReader reader = new FileReader("MobileFramework/config/config.properties");
		Properties p = new Properties();
		p.load(reader);
		Runtime run = Runtime.getRuntime();
		Result.fUpdateLog("Uninstalling Older version of Mcare App");
		run.exec("adb uninstall  qa.vodafone.myvodafone.devel.beta");
		Thread.sleep(2000);
		run.exec("adb uninstall  qa.vodafone.myvodafone");
		Thread.sleep(2000);
		Result.fUpdateLog("Uninstallation Completed");
		Result.fUpdateLog("Installing the Given Version of MCare");
		///Result.fUpdateLog("adb -s "+ p.getProperty(DeviceName + "_Id")+" install " + Path);
		Process proc = run.exec("adb -s "+ p.getProperty(DeviceName + "_Id")+" install " + Path+">NUL && echo Action done successfully || echo Action Failed");
		Thread.sleep(20000);
		BufferedReader br = new BufferedReader(
			    new InputStreamReader(proc.getInputStream()));
			String line;
			while ((line = br.readLine()) != null)
			    System.out.println(line);
		
		Result.fUpdateLog("Instalation Completed");
		Status = "PASS";
		}catch(Exception e) {
			Status = "FAIL";
			Result.fUpdateLog(e.toString());
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	
	public static String verifyMCareLogin() {
		String Test_OutPut = "", Status = "", DeviceName;
		int MobNum = 0, VoV = 0, Avatar = 0, NeedHelp = 0, Gauge = 0, Menu = 0;
		try {
			DeviceName = utils.fetchData("DeviceName");
			FileReader reader = new FileReader("MobileFramework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			utils.takeScreenShot();
			Runtime run = Runtime.getRuntime();
			String cmd = "adb -s " + p.getProperty(DeviceName + "_Id") + " shell input swipe 100 1100 100 100";
			run.exec(cmd);
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Start')]")).click();
			utils.takeScreenShot();
			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);

			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.TextView' and @text='Skip']")).click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageView' and @index='0']")));
			/*
			 * String Flex = SetCapabilities.dr .findElement(By.xpath(
			 * "//android.widget.TextView[contains(@content-desc,'ltr.dashboard.credit.small.amount.text')]"
			 * )) .getText().toString();
			 */

/*			String Mob1 = SetCapabilities.dr.findElement(By.xpath(
					"//*[@class='android.widget.FrameLayout' and @index='1']//*[@class='android.widget.TextView']"))
					.getText().toString();
*/
			if (SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='0']"))
					.isDisplayed()) {
				Result.fUpdateLog("Home Page Loaded Successfully, Verifying other objects.");
				Result.fUpdateLog("Avatar Verified on Dashboard");
				Test_OutPut += "Avatar Verified on Dashboard </br>";
				Avatar = 1;
				if (SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.FrameLayout' and @index='0']"))
						.isDisplayed()) {
					Result.fUpdateLog("VoV Verified on Dashboard");
					Test_OutPut += "VoV Verified on Dashboard </br>";
					VoV = 1;
				} else {
					Result.fUpdateLog("VoV is not found on Dashboard");
					Test_OutPut += "VoV is not found on Dashboard </br>";
					VoV = 0;
				}

				if (SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']"))
						.isDisplayed()) {
					Result.fUpdateLog("Burger Menu is Verified on Dashboard");
					Test_OutPut += "Burger Menu is Verified on Dashboard </br>";
					Menu = 1;
				} else {
					Result.fUpdateLog("Burger Menu is not found on Dashboard");
					Test_OutPut += "Burger Menu is not found on Dashboard </br>";
					Menu = 0;
				}

				String Mob = SetCapabilities.dr
						.findElement(By.xpath("//android.widget.TextView[contains(@text,'+974')]")).getText()
						.toString();
				Test_OutPut += "Avatar Icon Verified. </br>";
				Mob = Mob.substring(5, 13).trim();
				String MSISDN = utils.fetchData("MSISDN");
				MSISDN = MSISDN.substring(3, 11);
				if (Mob.equals(MSISDN)) {
					Result.fUpdateLog("Mobile No. Verified on Dashboard");
					Test_OutPut += "Mobile No. Verified on Dashboard </br>";
					MobNum = 1;
				} else {
					Result.fUpdateLog("Mobile No. is wrong on Dashboard");
					Test_OutPut += "Mobile No. is wrong on Dashboard </br>";
					MobNum = 0;
				}

				if (SetCapabilities.dr
						.findElement(By.xpath("//*[@resource-id='qa.vodafone.myvodafone:id/dashboard_red_button']"))
						.isDisplayed()) {
					Result.fUpdateLog("Need Help Verified on Dashboard");
					Test_OutPut += "Need Help Verified on Dashboard </br>";
					NeedHelp = 1;
				} else {
					Result.fUpdateLog("Need Help is not found on Dashboard");
					Test_OutPut += "Need Help is not found on Dashboard </br>";
					NeedHelp = 0;
				}
				if (SetCapabilities.dr
						.findElement(
								By.xpath("//*[@class='android.support.v7.widget.LinearLayoutCompat' and @index='0']"))
						.isDisplayed()) {
					Result.fUpdateLog("Gauge Verified on Dashboard");
					Test_OutPut += "Gauge Verified on Dashboard </br>";
					Gauge = 1;
				} else {
					Result.fUpdateLog("Gauge is not found on Dashboard");
					Test_OutPut += "Gauge is not found on Dashboard </br>";
					Gauge = 0;
				}
				utils.takeScreenShot();
			} else {
				Result.fUpdateLog("For some reasons, MCare Dashboard is not loaded properly");
				Test_OutPut += "For some reasons, MCare Dashboard is not loaded properly </br>";
				Avatar = 0;
			}
			if ((Avatar + VoV + NeedHelp + Gauge + MobNum + Menu) == 6)
				Status = "PASS";
			else
				Status = "FAIL";
		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static void Wait(String Path) {

		WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Path)));
	}

	public static void Scroll(String Text) throws InterruptedException {

		Wait("//*[@text='Calls']");
		MobileElement radioGroup = (MobileElement) SetCapabilities.dr.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(" + "new UiSelector().text(\"" + Text + "\"));"));
		Thread.sleep(1000);
		radioGroup.click();
	}

	public static String verifyPlanNameMCare() {
		String Test_OutPut = "", Status = "";
		try {
		SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")).click();
		utils.takeScreenShot();
		String PlanName = SetCapabilities.dr.findElement(By.xpath("//android.widget.TextView[contains(@content-desc,'ltr.menu.plan.text')]")).getText().toString();
		Result.fUpdateLog("Mcare Plan Name is "+PlanName);
		Test_OutPut += "Mcare Plan Name is: "+PlanName+"</br>";
		String SiebelName = Utlities.FetchStoredValue("MCare_VerifyPlanName", "MCare_VerifyPlanName", "MCA_PlanName");
		Result.fUpdateLog("Siebel Plan Name is "+SiebelName);
		Test_OutPut += "Siebel Plan Name is: "+SiebelName+"</br>";
		if(SiebelName.contains(PlanName)) {
			Status = "PASS";
		}else {
			Result.fUpdateLog("There is some issue with the Plan name, Please check");
			Status = "FAIL";
		}
		}catch(Exception e) {
			Result.fUpdateLog("Exception Occured: "+e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String billEnquiryMCare() {
		String Test_OutPut = "", Status = "", MSISDN,ID, DeviceName, SiebelTotal;
		try {
			FileReader reader = new FileReader("MobileFramework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			DeviceName = utils.fetchData("DeviceName");
			MSISDN = utils.fetchData("MSISDN");
			MSISDN = MSISDN.substring(3,11);
			SiebelTotal=Utlities.FetchStoredValue("MCare_BillEnquiry", "MCare_BillEnquiry", "MCA_Total");
			ID=Utlities.FetchStoredValue("MCare_BillEnquiry", "MCare_BillEnquiry", "MCA_ContactId");
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")).click();
			SetCapabilities.dr.findElement(By.xpath("//android.widget.TextView[contains(@text,'Billing and Payment')]")).click();
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath("//android.widget.TextView[contains(@text,'Retrieve a bill & pay')]")).click();
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.support.v7.widget.LinearLayoutCompat']//*[contains(@text,'Vodafone number or Account number')]")).sendKeys(MSISDN);
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.support.v7.widget.LinearLayoutCompat']//*[contains(@text,'Qatari I.D. or Passport Number')]")).click();
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.support.v7.widget.LinearLayoutCompat']//*[contains(@text,'Qatari I.D. or Passport Number')]")).sendKeys(ID);
			Thread.sleep(1000);
			String cmd = "adb -s " + p.getProperty(DeviceName + "_Id") + " shell input keyevent 4";
			Runtime run = Runtime.getRuntime();
			run.exec(cmd);
			Thread.sleep(1000);
			SetCapabilities.dr.findElement(By.xpath("//android.widget.TextView[contains(@content-desc,'retrieve_billing_info.button')]")).click();
			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[contains(@content-desc,'retrieve_billing_info.text')]")));
			utils.takeScreenShot();
			String BillAmount = SetCapabilities.dr.findElement(By.xpath("//android.support.v7.widget.LinearLayoutCompat[@content-desc='retrieve_billing_info.vertical']//android.widget.TextView[2]")).getText().toString();
			System.out.println(BillAmount+"---->");
			BillAmount  = BillAmount.substring(3, BillAmount.length()).trim();
			String[] obj = BillAmount.split(",");
			if (obj.length > 1)
				BillAmount = obj[0].trim() + obj[1].trim();
			Result.fUpdateLog("Total Due Amount on MCare is " + BillAmount);
			Test_OutPut += "Total Due Amount on MCare is " + BillAmount + ",";
			SiebelTotal  = SiebelTotal.substring(2, SiebelTotal.length()).trim();
			String[] obj1 = SiebelTotal.split(",");
			if (obj1.length > 1)
				SiebelTotal = obj1[0].trim() + obj1[1].trim();
			Result.fUpdateLog("Total Due Amount on Siebel is " + SiebelTotal);
			Test_OutPut += "Total Due Amount on Siebel is " + SiebelTotal + ",";
			if(BillAmount.equalsIgnoreCase(SiebelTotal))
			Status = "PASS";
			else {
				Result.fUpdateLog("There is some mismatch between the Amount from MCare and Siebel");
				Test_OutPut += "There is some mismatch between the Amount from MCare and Siebel";
				Status = "FAIL";
			}
		}catch(Exception e) {
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured"+e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static String verifyMCareBuckets(){
		String Test_OutPut = "", Status = "";
		try {
		SetCapabilities.dr.findElement(By.xpath("(//android.widget.TextView[@content-desc=\"ltr.dashboard.gauge.product_circle_view.text\"])[2]")).click();
		Thread.sleep(5000);
		WebElement gauge =  SetCapabilities.dr.findElement(By.xpath("//android.widget.TextView[contains(@content-desc,'ltr.dashboard.gauge.circle_pager_view.balance.text')]"));
		int startX = gauge.getLocation().getX();
		int startY = gauge.getLocation().getY();
		int endX = (startX+gauge.getSize().getWidth());
		System.out.println(startX);
		System.out.println(startY);
		System.out.println(endX);
		TouchAction action = new TouchAction(SetCapabilities.dr);
		action.longPress(startX, startY).moveTo(endX, startY).release().perform();
		}catch(Exception e) {
			System.out.println(e);
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	
	

	public static String Addon_Activation() {
		String Test_OutPut = "", Status = "", MVA_PrepaidExtras = "";

		try {

			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			MVA_PrepaidExtras = utils.fetchData("MVA_PrepaidExtras");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")));
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Add-ons')]")));
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Add-ons')]")).click();
			utils.takeScreenShot();
			Scroll(MVA_PrepaidExtras);

			SetCapabilities.dr.findElement(By.xpath("//*[@text='Activate']")).click();
			utils.takeScreenShot();
			int i = 0;
			do {
				Thread.sleep(100);
				i = i + 1;
			} while (i < 4);
			Status = "PASS";

		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String Cookies() {
		@SuppressWarnings("unused")
		String Test_OutPut = "", Status = "", MVA_PrepaidExtras = "";

		try {

			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			MVA_PrepaidExtras = utils.fetchData("MVA_PrepaidExtras");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")));
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")).click();

			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Vodafone Points')]")));
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Vodafone Points')]")).click();
			utils.takeScreenShot();
			String Mob1 = SetCapabilities.dr.findElement(By.xpath(
					"//*[@class='android.widget.FrameLayout' and @index='1']//*[@class='android.widget.TextView']"))
					.getText().toString();
			String Cookie = Mob1.split(" ")[0].trim();
			
			String SiebelName = Utlities.FetchStoredValue("Mcare_Cookies", "Mcare_Cookies",
					"Cookie");
			if(Cookie.equalsIgnoreCase(SiebelName))
			{
				Status ="PASS";
			}else
			{
				Status ="FAIL";
			}
			
		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String Addon_DeActivation() {
		String Test_OutPut = "", Status = "", MVA_PrepaidExtras = "";

		try {

			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			MVA_PrepaidExtras = utils.fetchData("MVA_PrepaidExtras");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")));
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Add-ons')]")));
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Add-ons')]")).click();
			utils.takeScreenShot();
			Scroll(MVA_PrepaidExtras);
			Thread.sleep(500);
			SetCapabilities.dr.findElement(By.xpath("//*[@text='Deactivate']")).click();

			utils.takeScreenShot();
			int i = 0;
			do {
				Thread.sleep(100);
				i = i + 1;
			} while (i < 4);
			Status = "PASS";

			String MSG = SetCapabilities.dr
					.findElement(
							By.xpath("//android.widget.TextView[contains(@content-desc,'ltr.activate_products.text')]"))
					.getText().toString();
			if (MSG.contains("has been deactivated and wait for confirmation by SMS")) {
				Status = "PartiallyPass";
			} else {
				Status = "FAIL";
				Result.fUpdateLog("Production Deactivation failed ");
			}

		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String PostAddon_Activation() {
		String Test_OutPut = "", Status = "", MVA_PrepaidExtras = "";

		try {

			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			MVA_PrepaidExtras = utils.fetchData("MVA_PrepaidExtras");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")));
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Add-ons')]")));
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Add-ons')]")).click();
			utils.timestamp = new SimpleDateFormat("M/dd/YYYY hh:mm:ss aa").format(Calendar.getInstance().getTime());
			utils.takeScreenShot();
			Scroll(MVA_PrepaidExtras);

			SetCapabilities.dr.findElement(By.xpath("//*[@text='Activate']")).click();
			utils.takeScreenShot();

			int i = 0;
			do {
				Thread.sleep(100);
				i = i + 1;
			} while (i < 4);
			Status = "PASS";

		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	
	public static String PostAddon_Deactivation() {
		String Test_OutPut = "", Status = "", MVA_PrepaidExtras = "";

		try {

			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			MVA_PrepaidExtras = utils.fetchData("MVA_PrepaidExtras");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")));
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Add-ons')]")));
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Add-ons')]")).click();
			utils.timestamp = new SimpleDateFormat("M/dd/YYYY hh:mm:ss aa").format(Calendar.getInstance().getTime());
			utils.takeScreenShot();
			Scroll(MVA_PrepaidExtras);

			SetCapabilities.dr.findElement(By.xpath("//*[@text='Deactivate']")).click();
			utils.takeScreenShot();

			int i = 0;
			do {
				Thread.sleep(100);
				i = i + 1;
			} while (i < 4);
			Status = "PASS";

		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	
	public static String Recharge() {
		String Test_OutPut = "", Status = "", MVA_PrepaidExtras = "";

		try {

			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			MVA_PrepaidExtras = utils.fetchData("MVA_PrepaidExtras");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")));
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Recharge Credit and Data')]")));
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Recharge Credit and Data')]")).click();
			utils.timestamp = new SimpleDateFormat("M/dd/YYYY hh:mm:ss aa").format(Calendar.getInstance().getTime());
			utils.takeScreenShot();
			Scroll(MVA_PrepaidExtras);

			SetCapabilities.dr.findElement(By.xpath("//*[@text='Deactivate']")).click();
			utils.takeScreenShot();

			int i = 0;
			do {
				Thread.sleep(100);
				i = i + 1;
			} while (i < 4);
			Status = "PASS";

		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}
}