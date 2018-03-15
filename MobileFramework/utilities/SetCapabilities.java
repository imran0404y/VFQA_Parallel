package utilities;

import Libraries.*;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;

@SuppressWarnings("rawtypes")
public class SetCapabilities extends Driver {

	static AndroidDriver dr;
	static String DeviceName;

	public String setDialerCapabilities() {
		String Test_OutPut = "", Status = "";
		try {
			if (!(getdata("DeviceName").equals(""))) {
				DeviceName = getdata("DeviceName");
				Result.fUpdateLog("Device Name is set to " + DeviceName);
				setDialerCapabilities1(DeviceName);
				Status = "PASS";
			} else {
				Result.fUpdateLog("Device " + DeviceName + " not found");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Result.fUpdateLog("Capabilites are not set due to" + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static void setDialerCapabilities1(String DeviceName) {
		System.out.println("*** Setting Up Dialer Capabilities ***");
		try {
			FileReader reader = new FileReader("MobileFramework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", p.getProperty(DeviceName + "_Name"));
			capabilities.setCapability("udid", p.getProperty(DeviceName + "_Id"));
			capabilities.setCapability("platformVersion", p.getProperty(DeviceName + "_Android_Version"));
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("appPackage", p.getProperty(DeviceName + "_AppPackage_DialerApp"));
			capabilities.setCapability("appActivity", p.getProperty(DeviceName + "_AppActivity_DialerApp"));
			dr = new AndroidDriver(new URL("http://127.0.0.1:" + p.getProperty(DeviceName + "_Port") + "/wd/hub"),
					capabilities);
			System.out.println("*** Dialer Capabilities are now Set ***");
		} catch (Exception e) {
			Result.fUpdateLog("Capabilites are not set due to" + e);
		}
	}

	public String setMessengerCapabilities() {
		String Test_OutPut = "", Status = "";
		try {
			if (!(getdata("DeviceName").equals(""))) {
				DeviceName = getdata("DeviceName");
				Result.fUpdateLog("Device Name is set to " + DeviceName);
				setMessengerCapabilities1(DeviceName);
				Status = "PASS";
			} else {
				Result.fUpdateLog("Device " + DeviceName + " not found");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Result.fUpdateLog("Capabilites are not set due to" + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	
	public static void setMessengerCapabilities1(String DeviceName) throws IOException, InterruptedException {
		System.out.println("*** Setting Up Messenger Capabilities ***");
		try {
			FileReader reader = new FileReader("MobileFramework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", p.getProperty(DeviceName + "_Name"));
			capabilities.setCapability("udid", p.getProperty(DeviceName + "_Id"));
			capabilities.setCapability("platformVersion", p.getProperty(DeviceName + "_Android_Version"));
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("appPackage", p.getProperty(DeviceName + "_AppPackage_MessageApp"));
			capabilities.setCapability("appActivity", p.getProperty(DeviceName + "_AppActivity_MessageApp"));
			dr = new AndroidDriver(new URL("http://127.0.0.1:" + p.getProperty(DeviceName + "_Port") + "/wd/hub"),
					capabilities);
			Result.fUpdateLog("*** Messenger Capabilities are now Set ***");
		} catch (Exception e) {
			Result.fUpdateLog("Capabilites are not set due to" + e);
		}
	}

}