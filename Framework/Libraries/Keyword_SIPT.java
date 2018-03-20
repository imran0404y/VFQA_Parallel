package Libraries;

import java.util.Random;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Keyword_SIPT extends Driver {
	Common CO = new Common();
	Random R = new Random();

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PopupHeader
	 * Use 					: To get a Particular Column Value with the Column Name
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 01-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String SIPT() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {

			int Row_Val = 3, Col_V, Col_S;
			String GetData, COSP_Plan, ReservationToken, Qty, To, MSISDN, Default_Plan_Tab, Default_Addon, // ,Reserve
					From, PData = "Pilot Number", CCODE = "974";
			GetData = "Corporate SIP Trunk Bundle";
			CO.waitforload();

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
			}

			// To use Catalog view uncomment the below lines
			/*
			 * Browser.WebLink.click("VQ_Catalog");
			 * CO.Category_Select(pulldata("Plan_View"),pulldata("Plan_Catagory"),PlanName);
			 * Browser.WebButton.click("LI_New"); CO.waitforload();
			 */
			// ------------------------------------------------------------
			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot("SIPT Plan Selection " + PlanName);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int Row = 2, Col;
			Col = CO.Select_Cell("Line_Items", "Product");
			CO.waitforload();
			Browser.WebTable.SetDataE("Line_Items", Row, Col, "Product", PlanName);
			Browser.WebTable.click("Line_Items", Row, Col + 1);
			CO.waitforload();
			// -----------------------

			int Row_Count = Browser.WebTable.getRowCount("Line_Items");

			Col_S = CO.Select_Cell("Line_Items", "Service Id");
			Col_V = Col + 2;

			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_V);

			if (!(getdata("COSP_Plan").equals(""))) {
				COSP_Plan = getdata("COSP_Plan");
			} else {
				COSP_Plan = pulldata("COSP_Plan");
			}

			if (!(getdata("From").equals(""))) {
				From = getdata("From");
			} else {
				From = pulldata("From");
			}
			From = From.replace("974", "");

			if (!(getdata("To").equals(""))) {
				To = getdata("To");
			} else {
				To = pulldata("To");
			}
			To = To.replace("974", "");

			if (!(getdata("ReservationToken").equals(""))) {
				ReservationToken = getdata("ReservationToken");
			} else {
				ReservationToken = pulldata("ReservationToken");
			}

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			MSISDN = MSISDN.replace("974", "");
			
			

			if (!(getdata("Default_Plan_Tab").equals(""))) {
				Default_Plan_Tab = getdata("Default_Plan_Tab");
			} else {
				Default_Plan_Tab = pulldata("Default_Plan_Tab");
			}

			if (!(getdata("Default_Addon").equals(""))) {
				Default_Addon = getdata("Default_Addon");
			} else {
				Default_Addon = pulldata("Default_Addon");
			}

			if (!(getdata("Qty").equals(""))) {
				Qty = getdata("Qty");
			} else {
				Qty = pulldata("Qty");
			}

			CO.Text_Select("span", "Customize");
			Result.takescreenshot("SIPT Number Resered");
			// Default_Plan_Tab Default_Addon
			View_Selection(Default_Plan_Tab, Default_Addon);
			CO.Link_Select("COSP Plans");
			CO.waitforload();
			CO.Radio_Select(COSP_Plan);
			Result.takescreenshot(COSP_Plan + "is selected from COSP Plans");
			CO.waitforload();
			CO.ToWait();
			CO.NumberRangeProducts("DID Number Range", Qty, From, To, ReservationToken);
			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			CO.waitforload();
			if (CO.isAlertExist())
				Continue.set(false);

			if (Continue.get()) {
				if (ReservationToken.equals("")) {

					CO.scroll("Numbers", "WebLink");
					Browser.WebLink.click("Numbers");
					Number_Reservation("SIPT", MSISDN);
					Number_Reservation("SIPT", From);
					Number_Reservation("SIPT", To);

					CO.Link_Select("Line Items");
					CO.waitforload();

					if (Row_Count <= 3) {
						Browser.WebButton.waittillvisible("Expand");
						Browser.WebButton.click("Expand");
					}
					CO.waitforload();
					CO.waitforload();
					Col_S = CO.Select_Cell("Line_Items", "Service Id");
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
						if (GetData.equals(LData)) {
							Row_Val = i;
						}
					}
					CO.Popup_Click("Line_Items", Row_Val, Col_S);
					CO.waitforload();
					CO.Popup_Selection("Number_Selection", "Number", MSISDN);
					// Number_Selection(CCODE + MSISDN);
					CO.ToWait();
					CO.waitforload();
					CO.ToWait();
					Row_Count = Browser.WebTable.getRowCount("Line_Items");

					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
						if (PData.equals(LData)) {
							Row_Val = i;
						}
					}

					Col_S = CO.Select_Cell("Line_Items", "Service Id");
					CO.Popup_Click("Line_Items", Row_Val, Col_S);
					CO.waitforload();
					CO.Popup_Selection("Number_Selection", "Number", MSISDN);
					// Number_Selection(CCODE+MSISDN);

				} else if (!ReservationToken.equals("")) {
					Row_Count = Browser.WebTable.getRowCount("Line_Items");
					if (Row_Count <= 3) {
						Browser.WebButton.waittillvisible("Expand");
						Browser.WebButton.click("Expand");
					}

					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
						if (GetData.equals(LData))
							Row_Val = i;
					}
					Browser.WebTable.click("Line_Items", Row_Val, Col_S);
					Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", CCODE + MSISDN);

					// To Provide Pilot Number
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
						if (PData.equals(LData)) {
							Row_Val = i;
						}
					}
					Browser.WebTable.click("Line_Items", Row_Val, Col_S);
					Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", CCODE + MSISDN);
				}
				if (Browser.WebTable.exist("Line_Items"))
					Result.fUpdateLog("Proceeding Order Submission");
				CO.waitforload();
				if (UseCaseName.get().toLowerCase().contains("enterprise")
						|| TestCaseN.get().toLowerCase().contains("vip") || UseCaseName.get().contains("SIPT")) {
					CO.scroll("Ent_CreditLimit", "WebEdit");
					Browser.WebEdit.click("Ent_CreditLimit");
					Browser.WebEdit.Set("Ent_CreditLimit", "100");
				} else {
					CO.scroll("Credit_Limit", "WebEdit");
					CO.waitforload();
					Browser.WebEdit.click("Credit_Limit");
				}

				CO.scroll("Service", "WebButton");

				Browser.WebButton.waittillvisible("Validate");
				Browser.WebButton.click("Validate");
				if (CO.isAlertExist()) {
					Continue.set(false);
				}
				CO.waitmoreforload();
				CO.waitforload();
				if (Continue.get()) {
					Browser.WebButton.waittillvisible("Submit");
					CO.scroll("Submit", "WebButton");
					Browser.WebButton.click("Submit");
					CO.waitmoreforload();
					if (CO.isAlertExist()) {
						Continue.set(false);
					}
				}

				CO.waitforload();

				Result.takescreenshot("SIPT Order Submited");
			}
			if (Continue.get() & (Row_Count >= 3)) {
				Status = "PASS";
				Utlities.StoreValue("PlanName", PlanName);
				Test_OutPut += "PlanName : " + PlanName + ",";
				Utlities.StoreValue("COSP_Plan", COSP_Plan);
				Test_OutPut += "COSP_Plan : " + COSP_Plan + ",";
				Utlities.StoreValue("From", From);
				Test_OutPut += "From : " + From + ",";
				Utlities.StoreValue("To", To);
				Test_OutPut += "To : " + To + ",";
				Utlities.StoreValue("SIPT", MSISDN);
				Test_OutPut += "SIPT : " + MSISDN + ",";
				Result.takescreenshot("Plan Selection is Successful : " + PlanName);
				Result.fUpdateLog("Plan Selection for " + PlanName + "is done Successfully");
			} else {
				Status = "FAIL";
				Test_OutPut += "SIPT Plan Selection Failed" + ",";
				Result.takescreenshot("SIPT Plan Selection Failed");
				Result.fUpdateLog("SIPT Plan Selection Failed");
			}

		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------SIPT Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: View_Selection
	 * Use 					: To view a spectific Radio Button or check box
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 12-October-2017
	--------------------------------------------------------------------------------------------------------*/
	public void View_Selection(String Tab, String Text) {
		CO.Link_Select(Tab);
		CO.ToWait();
		CO.waitforload();
		String cellXpath = "//input[@value='" + Text + "']";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		if (cDriver.get().findElement(By.xpath(cellXpath)).isDisplayed())
			Result.takescreenshot("-----------------------Default Addon View-----------------------");
		else
			Continue.set(false);
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Number_Reservation
	 * Use 					: To Reserve a Number
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 12-October-2017
	--------------------------------------------------------------------------------------------------------*/

	public void Number_Reservation(String SType, String MSISDN) {
		try {
			String Category;
			CO.waitforload();
			int Row = 2, COl_STyp, Col_Res, Row_Count = Browser.WebTable.getRowCount("Numbers");
			if (Row_Count == 1)
				Browser.WebButton.click("Number_Query");
			Browser.WebLink.click("Num_Manage");
			CO.waitforload();
			Browser.WebButton.waitTillEnabled("Reserve");
			Browser.WebButton.waittillvisible("Reserve");
			COl_STyp = CO.Select_Cell("Numbers", "Service Type");
			Col_Res = CO.Select_Cell("Numbers", "(Start) Number");
			Browser.WebTable.SetData("Numbers", Row, COl_STyp, "Service_Type", SType);
			Browser.WebTable.SetData("Numbers", Row, Col_Res, "Service_Id", MSISDN);
			Result.takescreenshot("SIPT Number Reservation " + MSISDN);
			CO.waitmoreforload();
			Category = Browser.WebTable.getCellData("Numbers", Row, COl_STyp + 1);
			Result.fUpdateLog("Category " + Category);
			Browser.WebButton.click("Reserve");
			CO.waitmoreforload();
			if (CO.isAlertExist()) {
				Result.takescreenshot("SIPT Number Resered");
				Result.fUpdateLog("Alert Handled");
			}
		} catch (Exception e) {
			Continue.set(false);
		}
	}

}
