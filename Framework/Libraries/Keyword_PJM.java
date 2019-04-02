package Libraries;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class Keyword_PJM extends Driver {
	Common CO = new Common();

	public String PJM_Login() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------PJM Login Event Details------");
		try {

			if (!(getdata("Browser").equals(""))) {
				browser.set(getdata("Browser"));
			} else {
				browser.set("ie");
			}

			if (!(getdata("URL/HOST").equals(""))) {
				URL.set(getdata("URL/HOST"));
			}
			Browser.OpenBrowser(browser.get(), URL.get());
			Result.takescreenshot("Opening Browser and navigating to the URL");

			Browser.WebEdit.waittillvisible("PJM_Username");
			Browser.WebEdit.Set("PJM_Username", getdata("VQ_Login_User"));
			Browser.WebEdit.Set("PJM_Password", getdata("VQ_Login_Pswd"));

			Browser.WebButton.click("PJM_Submit");
			if (Browser.WebLink.exist("PJM_Main")) {
				Continue.set(true);
			} else {
				Continue.set(false);
			}

			if (Continue.get()) {
				Test_OutPut += "Successfully Login with : " + getdata("OSM_Login_User") + ",";
				Result.takescreenshot("Login Successfully with user " + getdata("OSM_Login_User"));
				Result.fUpdateLog("Login Successfully with user " + getdata("OSM_Login_User"));
				Status = "PASS";
			} else {
				Test_OutPut += "Login Failed" + ",";
				Result.takescreenshot("Login Failed");
				Result.fUpdateLog("Login Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------PJM Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String PJM_Upload() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------PJM Login Event Details------");
		try {

			Browser.WebButton.waittillvisible("PJM_Batch");
			Result.takescreenshot("PJM_BATCh");
			Browser.WebButton.click("PJM_Batch");
			Browser.WebButton.click("PJM_UploadBatch");
			Browser.WebButton.click("PJM_ISTestRun");
			Browser.WebEdit.Set("PIM_Type", "Bill");
			Actions action = new Actions(cDriver.get());
			action.sendKeys(Keys.TAB).build().perform();

			// cDriver.get().findElement(By.xpath("//a[@class='x-btn x-form-file-btn
			// x-unselectable x-btn-default-small x-noicon x-btn-noicon
			// x-btn-default-small-noicon']")).click();
			cDriver.get().findElement(By.name("fileData")).sendKeys(UCscreenfilepth.get() + "/" + InvoiceZip.get());
			Result.takescreenshot("FileUPload");
			Browser.WebButton.click("PJM_Submit");
			CO.waitforload();
			cDriver.get()
					.findElement(
							By.xpath("//span[@class='x-btn-inner x-btn-inner-center']/..//span[.='OK']/../span[2]"))
					.click();

			// Browser.WebButton.click("PJM_Ok");

			int i = 0;
			do {
				Browser.WebButton.click("PJM_Refresh");
				i = i + 1;
			} while (i < 3);
			Result.takescreenshot("FileUPloaded");
			String Batch_id = Browser.WebTable.getCellData("PJM_Status Query", 1, 1);
			String File_Uploaded = Browser.WebTable.getCellData("PJM_Status Query", 1, 3);
			CO.waitforload();
			Browser.WebButton.click("PJM_LogOut");

			if (Continue.get()) {
				Utlities.StoreValue("Batch_id", Batch_id);
				Test_OutPut += "Batch_id : " + Batch_id + ",";
				Utlities.StoreValue("File_Uploaded", File_Uploaded);
				Test_OutPut += "File_Uploaded : " + File_Uploaded + ",";
				Result.takescreenshot("File Uploaded Successfully ");

				Status = "PASS";
			} else {
				Test_OutPut += "File Uploaded is Un Successfully" + ",";
				Result.takescreenshot("File Uploaded is Un Successfully");
				Result.fUpdateLog("File Uploaded is Un Successfully");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------PJM Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
