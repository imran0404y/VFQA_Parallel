package mobileUtilities;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import Libraries.Driver;
import Libraries.Result;
import Libraries.Utlities;

public class Xpath extends Driver{
	// verifyMCareLogin Paths
	static String okButton = "//*[@resource-id='"+activity.get()+":id/permission_accept_button']";
	static String VFlogo = "//*[@resource-id='"+activity.get()+":id/logo_vodafone']";
	static String VoV = "//*[@resource-id='"+activity.get()+":id/voice_of_vodafone']";
//	static String VoV = "//android.widget.TextView[contains(@text,'VOV Test')]";
	static String BurgerMenu = "//*[@resource-id='"+activity.get()+":id/burger_menu']";
	static String NeedHelp = "//*[@resource-id='"+activity.get()+":id/dashboard_need_help']";
	static String GaugeLayout = "//*[@resource-id='"+activity.get()+":id/gauge_circle_layout']";
	static String SkipTutorial = "//android.widget.TextView[contains(@text,'Skip')]";
	static String AlertCancel = "android:id/button2";
	static String MenuVFPoints = "//*[contains(@text,'Vodafone Points')]";
	static String CookieValue = "//android.widget.TextView[contains(@content-desc,'ltr.cookies.points.text')]";
	static String Recharge = "//*[contains(@text,'Recharge Credit and Data')]";
	static String Recharge1 = "//*[contains(@text,'Recharge Credit & Data')]";
	static String RechargeMSISDN = "qa.vodafone.myvodafone.devel.beta:id/msisdn_input_field";
	static String BucketCall = "qa.vodafone.myvodafone.devel.beta:id/mins";
	static String BucketData = "qa.vodafone.myvodafone.devel.beta:id/data";
	static String BucketSMS = "qa.vodafone.myvodafone.devel.beta:id/sms";
	static String BucketValue = "ltr.dashboard.gauge.circle_pager_view.balance.text";
	static String BucketName = ":id/bucket_name";
	static String gauge = "ltr.dashboard.gauge.circle_pager_view.vertical";
	static String BillingPayments = "//android.widget.TextView[contains(@text,'Billing and Payment')]";
	static String QuickPay = "//android.widget.TextView[contains(@text,'Quick Pay')]";
	static String QuickPayMSISDN = "//*[@class='android.widget.EditText' and @index='1']";
	static String QuickPayAmount = "//android.widget.EditText[contains(@text,'insert amount')]";
	static String QuickPayByFalcon = "//android.widget.TextView[contains(@text,'Pay by Falcon')]";
	static String Payment = "//*[contains(@text,'Pay by Falcon')]";
	static String ProductServices="//android.widget.TextView[contains(@text,'My Products & Services')]";
	static String MyBill="//android.widget.TextView[contains(@text,'My Bill')]";
	static String CurrentSP="//android.widget.TextView[contains(@text,'Current spend & bill')]";
	static String PS_Phone="//android.widget.TextView[contains(@text,'Your phone')]";
	static String TT="//android.widget.TextView[contains(@text,'Trouble tickets')]";
	static String TT1="//android.widget.TextView[contains(@text,'Trouble Tickets')]";
	//static String TT1="//*[@text,'Trouble Tickets' and @Index='0']";
	
	
	static String QuickPay1= "//android.widget.TextView[contains(@text,'Quick pay')]";
	static String MyInbox= "//android.widget.TextView[contains(@text,'My Inbox')]";
	static String NeedHp= "//android.widget.TextView[contains(@text,'Need Help?')]";
	static String Tutorial= "//android.widget.TextView[contains(@text,'Tutorial')]";
	static String Settings= "//android.widget.TextView[contains(@text,'Settings')]";
	static String AskHani="//*[@text='Ask Hani' and @index='0']";
	static String Cancel="//*[@NAF='true']";
	static String Menu="//*[@resource-id='"+activity.get()+":id/burger_menu' and @index='1']";
	
}