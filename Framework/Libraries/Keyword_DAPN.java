package Libraries;

import java.util.Random;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class Keyword_DAPN extends Driver

{

	Common CO = new Common();
	Random R = new Random();
	Keyword_CRM KC = new Keyword_CRM();
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for the Order in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String PlanSelection_DAPN() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		String Account_No = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {

			int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S, Col_pri, Col_cat;
			String Reserve, Category, GetData, ReservationToken, StarNumber = null, SIM, MSISDN = null,
					SData = "SIM Card";
			int Row = 2, Col;
			CO.waitforload();

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
			}
			Planname.set(PlanName);
			Test_OutPut += "PlanName : " + PlanName + ",";

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			if (TestCaseN.get().equals("NewCustomer")) {

				KC.Entp_AccountCreation();
				KC.Entp_ContactCreation();
				KC.BillingProfileCreation();
				KC.SalesOrder();

				CO.scroll("LI_New", "WebButton");
				Browser.WebButton.click("LI_New");
				Col = CO.Select_Cell("Line_Items", "Product");
				Browser.WebTable.SetDataE("Line_Items", Row, Col, "Product", "Corporate Account Level Bundle");
				Browser.WebTable.click("Line_Items", Row, Col + 1);
				CO.waitforload();
				// -----------------------
				Browser.WebButton.click("Customize");
				CO.Link_Select("Account APN");

				CO.Text_Select("option", "Account Level APN");
				Browser.WebEdit.Set("NumberReservationToken", "1");
				Browser.WebButton.click("Apn_Add");

				CO.Link_Select("Account Level APN");
				CO.waitforload();
				CO.Link_Select("APN Attributes");

				CO.waitforload();
				Browser.WebEdit.Set("APN_ID", getdata("APN_ID"));
				Browser.WebEdit.Set("APN_Name", getdata("APN_Name"));
				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");
				CO.waitforload();
				if (CO.isAlertExist())
					Continue.set(false);

				Account_No = Acc_Number.get();
				Test_OutPut += KC.OrderSubmission().split("@@")[1];

			} else if (TestCaseN.get().equals("ExtCustomer")) {
				Account_No = getdata("Account_No");
			}

			CO.Account_Search(Account_No);
			CO.TabNavigator("Profiles");
			int Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
			Browser.WebTable.click("Bill_Prof", 2, (Col_Nam - 1));
			Actions action = new Actions(cDriver.get());
			action.sendKeys(Keys.TAB).build().perform();
			String Bill_No = Browser.WebTable.getCellData_title("Bill_Prof", 2, Col_Nam);
			Billprofile_No.set(Bill_No);
			Utlities.StoreValue("Billing_NO", Bill_No);
			Test_OutPut += "Billing_NO : " + Bill_No + ",";
			KC.SalesOrder();
			CO.Webtable_Value("Billing Profile", Billprofile_No.get());

			CO.scroll("LI_New", "WebButton");
			Browser.WebButton.click("LI_New");
			Col = CO.Select_Cell("Line_Items", "Product");
			Browser.WebTable.SetDataE("Line_Items", Row, Col, "Product", PlanName);
			Browser.WebTable.click("Line_Items", Row, Col + 1);
			CO.waitforload();
			// -----------------------

			int Row_Count = Browser.WebTable.getRowCount("Line_Items");

			Col_S = CO.Select_Cell("Line_Items", "Service Id");

			// To select the Mobile Bundle
			Col_V = Col + 2;

			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_V);
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			Result.fUpdateLog("MSISDN : " + MSISDN);
			Test_OutPut += "MSISDN : " + MSISDN + ",";

			if (!(getdata("SIM").equals(""))) {
				SIM = getdata("SIM");
			} else {
				SIM = pulldata("SIM");
			}
			Result.fUpdateLog("SIM_NO : " + SIM);
			Test_OutPut += "SIM_NO : " + SIM + ",";

			if (!(getdata("StarNumber").equals(""))) {
				StarNumber = getdata("StarNumber");
			} else if (!(pulldata("StarNumber").equals(""))) {
				StarNumber = pulldata("StarNumber");
			}

			if (!(getdata("ReservationToken").equals(""))) {
				ReservationToken = getdata("ReservationToken");
			} else {
				ReservationToken = pulldata("ReservationToken");
			}

			if (ReservationToken != "") {
				Browser.WebButton.click("Customize");
				if (ReservationToken != "") {
					Browser.WebEdit.waittillvisible("NumberReservationToken");
					Browser.WebEdit.Set("NumberReservationToken", ReservationToken);
					Result.takescreenshot("Providing Number Reservation Token");
				}
				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");
				CO.waitforload();
				if (CO.isAlertExist())
					Continue.set(false);
			}

			Browser.WebButton.click("Customize");
			CO.waitforload();
			CO.Link_Select("Others");
			CO.waitforload();
			if(getdata("Provision_Type")=="Static") {
				CO.Radio_Select("APN Service");
				CO.waitforload();
				CO.Radio_Select("APN Static IP Address");
				CO.waitforload();
			}else if(getdata("Provision_Type")=="Dynamic")
			{
				CO.Radio_Select("APN Service");
				CO.waitforload();
				CO.Radio_Select("APN Dynamic IP Address");
				CO.waitforload();
			}
			
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			CO.waitforload();
			if (CO.isAlertExist())
				Continue.set(false);

			if (ReservationToken.equals("")) {
				CO.scroll("Numbers", "WebLink");
				Browser.WebLink.click("Numbers");
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Numbers");
				if (Row_Count == 1)
					Browser.WebButton.click("Number_Query");
				Browser.WebLink.click("Num_Manage");
				CO.waitforload();
				Browser.WebButton.waitTillEnabled("Reserve");
				Browser.WebButton.waittillvisible("Reserve");
				COl_STyp = CO.Select_Cell("Numbers", "Service Type");
				Col_Res = CO.Select_Cell("Numbers", "(Start) Number");
				Col_cat = CO.Select_Cell("Numbers", "Category");
				Col_pri = CO.Select_Cell("Numbers", "Price From");
				Browser.WebTable.SetData("Numbers", Row, COl_STyp, "Service_Type", "Mobile");

				if (!MSISDN.equals("")) {

					Reserve = MSISDN.substring(3, MSISDN.length());
					Browser.WebTable.SetData("Numbers", Row, Col_Res, "Service_Id", Reserve);
					// Browser.WebButton.click("Number_Go");
					CO.waitmoreforload();
				} else {
					Browser.WebButton.click("Number_Go");
					CO.waitmoreforload();
					CO.waitforload();
					Browser.WebTable.click("Numbers", (Row + 1), Col);
					MSISDN = Browser.WebTable.getCellData("Numbers", (Row + 1), Col_Res);

				}

				Category = Browser.WebTable.getCellData("Numbers", Row, Col_cat);
				if (StarNumber == null) {
					StarNumber = Browser.WebTable.getCellData("Numbers", Row, Col_pri);
					StarNumber = StarNumber.substring(2, StarNumber.length());
				}
				Result.takescreenshot("proceeding for Number Reservation");

				Result.fUpdateLog("Category " + Category);
				Browser.WebButton.click("Reserve");
				CO.waitmoreforload();
				if (CO.isAlertExist()) {
					Result.takescreenshot("Number Reseved");
					Result.fUpdateLog("Alert Handled");
				}

				Browser.WebLink.waittillvisible("Line_Items");
				Browser.WebLink.click("Line_Items");
				CO.waitforload();
				// Browser.WebLink.click("LI_Totals");
				CO.waitforload();
				Col = CO.Actual_Cell("Line_Items", "Product");
				Row_Count = Browser.WebTable.getRowCount("Line_Items");

				if (Category.contains("STAR")) {

					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
						if (GetData.equalsIgnoreCase(LData)) {
							Row_Val = i;
							break;
						}
					}
					Browser.WebTable.click("Line_Items", Row_Val, Col_V);
					CO.Text_Select("span", "Customize");
					CO.Link_Select("Others");
					CO.scroll("Star_Number_purch", "WebEdit");
					CO.waitforload();
					CO.Text_Select("option", "Default");
					CO.Text_Select("option", "For Testing Only");
					CO.waitforload();
					CO.scroll("Star_Number_purch", "WebEdit");
					Browser.WebEdit.Set("Star_Number_purch", StarNumber);

					CO.waitforload();
					CO.Text_Select("button", "Verify");
					CO.isAlertExist();
					CO.waitforload();
					CO.Text_Select("button", "Done");
					if (CO.isAlertExist()) {
						Continue.set(false);
						System.exit(0);
					}

				}
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				Col = CO.Actual_Cell("Line_Items", "Product");
				Col_S = CO.Actual_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData)) {
						Row_Val = i;
					}
				}
				CO.waitforload();
				CO.waitforload();
				CO.Popup_Click("Line_Items", Row_Val, Col_S);
				CO.waitforload();
				Reserve = MSISDN.substring(3, MSISDN.length());
				CO.Popup_Selection("Number_Selection", "Number", Reserve);

			} else if (!ReservationToken.equals("")) {
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				Col_S = CO.Actual_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData))
						Row_Val = i;
				}
				Browser.WebTable.click("Line_Items", Row_Val, Col_S);
				Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", MSISDN);

			}
			// To Provide SIM No
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.waitforload();
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (SData.equalsIgnoreCase(LData))
					Row_Val = i;
			}

			Browser.WebTable.click("Line_Items", Row_Val, Col_S);
			Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", SIM);
			Result.takescreenshot("Plan Selection is Successful : " + PlanName);
			String GData = "APN Service";
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			int Con_No = CO.Actual_Cell("Line_Items", "APN Name");
			int Con_N1 = CO.Actual_Cell("Line_Items", "Static IP");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			CO.waitforload();
			CO.waitforload();
			CO.Popup_Click("Line_Items", Row_Val, Con_No);
			CO.waitforload();

			CO.Title_Select("button", "APN Details:OK");
			CO.waitforload();
			if(getdata("Provision_Type")=="Static") {
			GData = "APN Static IP Address";
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			CO.waitforload();
			CO.waitforload();
			CO.Popup_Click("Line_Items", Row_Val, Con_No);
			CO.waitforload();

			CO.Title_Select("button", "APN Details:OK");
			CO.waitforload();
			CO.waitforload();
			CO.Popup_Click("Line_Items", Row_Val, Con_N1);
			CO.waitforload();
			CO.waitforload();

			Browser.WebButton.click("Static_ok");
			}
			CO.waitforload();

			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.LineItems_Data();
			Result.takescreenshot("");
			Test_OutPut += KC.OrderSubmission().split("@@")[1];

			if (Continue.get() & (Row_Count >= 3)) {
				Status = "PASS";
				Utlities.StoreValue("PlanName", PlanName);
				Utlities.StoreValue("MSISDN", MSISDN);
				Utlities.StoreValue("SIM_NO", SIM);
				Result.fUpdateLog("Plan Selection for " + PlanName + "is done Successfully");
			} else {
				Status = "FAIL";
				Test_OutPut += "Plan Selection Failed" + ",";
				Result.takescreenshot("Plan Selection Failed");
				Result.fUpdateLog("Plan Selection Failed");
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
