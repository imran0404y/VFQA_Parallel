package utilities;

import Libraries.Driver;

public class Xpath extends Driver{
	// verifyMCareLogin Paths
	static String okButton = "qa.vodafone.myvodafone.devel.beta:id/permission_accept_button";
	static String VFlogo = "qa.vodafone.myvodafone.devel.beta:id/logo_vodafone";
	static String VoV = "qa.vodafone.myvodafone.devel.beta:id/voice_of_vodafone";
	static String BurgerMenu = "qa.vodafone.myvodafone.devel.beta:id/burger_menu";
	static String NeedHelp = "qa.vodafone.myvodafone.devel.beta:id/dashboard_need_help";
	static String GaugeLayout = "qa.vodafone.myvodafone.devel.beta:id/gauge_circle_layout";
	static String SkipTutorial = "//android.widget.TextView[contains(@text,'Skip tutorial')]";
	static String AlertCancel = "android:id/button2";
	static String MenuVFPoints = "//*[contains(@text,'Vodafone Points')]";
	static String CookieValue = "//android.widget.TextView[contains(@content-desc,'ltr.cookies.points.text')]";
	static String Recharge = "//*[contains(@text,'Recharge Credit and Data')]";
	static String RechargeMSISDN = "qa.vodafone.myvodafone.devel.beta:id/msisdn_input_field";
	static String BucketCall = "qa.vodafone.myvodafone.devel.beta:id/mins";
	static String BucketData = "qa.vodafone.myvodafone.devel.beta:id/data";
	static String BucketSMS = "qa.vodafone.myvodafone.devel.beta:id/sms";
	static String BucketValue = "ltr.dashboard.gauge.circle_pager_view.balance.text";
	static String BucketName = "qa.vodafone.myvodafone.devel.beta:id/bucket_name";
	static String gauge = "ltr.dashboard.gauge.circle_pager_view.vertical";
	static String BillingPayments = "//android.widget.TextView[contains(@text,'Billing and Payment')]";
	static String QuickPay = "//android.widget.TextView[contains(@text,'Quick Pay')]";
	static String QuickPayMSISDN = "//*[@class='android.widget.EditText' and @index='1']";
	static String QuickPayAmount = "//android.widget.EditText[contains(@text,'insert amount')]";
	static String QuickPayByFalcon = "//android.widget.TextView[contains(@text,'Pay by Falcon')]";
}