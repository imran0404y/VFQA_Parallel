package Libraries;

import java.util.Random;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class Keyword_PEARL extends Driver {
	Common CO = new Common();
	Random R = new Random();
	Keyword_CRM KC = new Keyword_CRM();

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection_Pearl
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for the Order in Vanilla Journey
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 14-Mar-2018
	--------------------------------------------------------------------------------------------------------*/
	public String PlanSelection_Pearl() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {

			int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S, Col_pri, Col_cat;
			String Reserve, Category, GetData, Add_Addon, Remove_Addon, ReservationToken, StarNumber = null, SIM,
					MSISDN = null, SData = "SIM Card";
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
			int Row = 2, Col;

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

			if (!(getdata("Add_Addon").equals(""))) {
				Add_Addon = getdata("Add_Addon");
			} else {
				Add_Addon = pulldata("Add_Addon");
			}

			if (!(getdata("Remove_Addon").equals(""))) {
				Remove_Addon = getdata("Remove_Addon");
			} else {
				Remove_Addon = pulldata("Remove_Addon");
			}

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

			if (Add_Addon != "" || Remove_Addon != "" || ReservationToken != "") {
				Browser.WebButton.click("Customize");
				if (ReservationToken != "") {
					Browser.WebEdit.waittillvisible("NumberReservationToken");
					Browser.WebEdit.Set("NumberReservationToken", ReservationToken);
					Result.takescreenshot("Providing Number Reservation Token");
				}
				CO.AddOnSelection(Add_Addon, "Add");
				CO.AddOnSelection(Remove_Addon, "Delete");
				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");
				CO.waitforload();
				if (CO.isAlertExist())
					Continue.set(false);
			}

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
				Browser.WebTable.SetData("Numbers", Row, COl_STyp, "Service_Type", "PEARL");

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
				CO.waitforload();
				Browser.WebButton.click("PopupQuery");
				Col = CO.PopupHeader("Number_Selection", "Number");
				int Col1 = CO.PopupHeader("Number_Selection", "Service Type");

				// CO.waitforload();
				if ((Browser.WebTable.getRowCount("Number_Selection") == 2)) {
					Browser.WebTable.SetDataE("Number_Selection", 2, Col1, "Service_Type", "PEARL");
					Browser.WebTable.SetData("Number_Selection", 2, Col, "Number", Reserve);

					Row_Count = Browser.WebTable.getRowCount("Number_Selection");
					Result.fUpdateLog("");
					if (Row_Count > 1) {
						CO.scroll("Popup_OK", "WebButton");
						Browser.WebButton.click("Popup_OK");
					} else
						Driver.Continue.set(false);
				} else
					Driver.Continue.set(false);

			} else if (!ReservationToken.equals("")) {
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				Col = CO.Select_Cell("Line_Items", "Product");
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
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_S = CO.Actual_Cell("Line_Items", "Service Id");
			CO.waitforload();
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (SData.equalsIgnoreCase(LData))
					Row_Val = i;
			}

			Browser.WebTable.click("Line_Items", Row_Val, Col_S);
			Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", SIM);
			Result.takescreenshot("Plan Selection is Successful : " + PlanName);

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
			Status = "FAIL";
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*--------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection_Data_Pearl
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for the Order in Vanilla Journey
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 14-Mar-2018
	--------------------------------------------------------------------------------------------------------*/
	public String PlanSelection_Data_Pearl() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {
			int Row_Val = 3, Col_S;
			String GetData;
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

			CO.scroll("LI_New", "WebButton");
			Browser.WebButton.click("LI_New");
			int Row = 2, Col;
			Col = CO.Actual_Cell("Line_Items", "Product");
			Col_S = CO.Actual_Cell("Line_Items", "Service Id");
			Browser.WebTable.SetDataE("Line_Items", Row, Col, "Product", PlanName);
			Result.takescreenshot("Plan Selected");
			Browser.WebTable.click("Line_Items", Row, Col_S);
			CO.waitforload();
			// -----------------------

			int Row_Count = Browser.WebTable.getRowCount("Line_Items");

			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_S);

			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.Action_Update("Add", "MSISDN");
			Result.takescreenshot("");
			if (Browser.WebTable.exist("Line_Items"))
				Result.fUpdateLog("Proceeding Order Submission");
			CO.waitforload();
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

			Result.takescreenshot("Data Pearl Order Submited");

			if (Continue.get()) {
				Status = "PASS";
				Utlities.StoreValue("PlanName", PlanName);
				Result.fUpdateLog("Plan Selection for " + PlanName + "is done Successfully");
			} else {
				Status = "FAIL";
				Test_OutPut += "Plan Selection Failed" + ",";
				Result.takescreenshot("Plan Selection Failed");
				Result.fUpdateLog("Plan Selection Failed");
			}

		} catch (Exception e) {
			Status = "FAIL";
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
