package Libraries;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Keyword_IPTV extends Driver {
	Common CO = new Common();
	Keyword_CRM KC = new Keyword_CRM();

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection_IPTV
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for the Order in Vanilla Journey
	 * Designed By			: IMRAN
	 * Last Modified Date 	: 15-JAN-2018
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	public String PlanSelection_IPTV() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {

			int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S, Col_pri, Col_cat;
			String Reserve, Category, GetData, IPData, CData, Add_Addon, Remove_Addon, IDMID, ReservationToken,
					StarNumber = null, MSISDN = null, SData = "SIM Card";

			CO.waitforload();

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
			}
			Planname.set(PlanName);
			Test_OutPut += "PlanName : " + PlanName + ",";

			GetData = pulldata("GetData");
			SData = pulldata("Sdata");
			IPData = pulldata("IPdata");
			CData = pulldata("Cdata");
			int Row = 2, Col;
			CO.scroll("LI_New", "WebButton");
			Browser.WebButton.click("LI_New");
			Col = CO.Actual_Cell("Line_Items", "Product");
			Col_S = CO.Actual_Cell("Line_Items", "Service Id");
			Browser.WebTable.SetDataE("Line_Items", Row, Col, "Product", PlanName);
			Browser.WebTable.click("Line_Items", Row, Col_S);
			Result.takescreenshot("Plan Selected");
			CO.waitforload();
			int Row_Count = Browser.WebTable.getRowCount("Line_Items");

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			Result.fUpdateLog("MSISDN : " + MSISDN);
			Test_OutPut += "MSISDN : " + MSISDN + ",";

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

			if (!(getdata("IDMID").equals(""))) {
				IDMID = getdata("IDMID");
			} else {
				IDMID = pulldata("IDMID");
			}

			if (Add_Addon != "" || Remove_Addon != "" || IDMID != "") {
				Col = CO.Actual_Cell("Line_Items", "Product");
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				Col_V = Col + 2;
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (IPData.equals(LData)) {
						Row_Val = i;
						break;
					}
				}
				Browser.WebTable.click("Line_Items", Row_Val, Col_V);
				Browser.WebButton.click("Customize");
				if (Add_Addon != "" || Remove_Addon != "") {
					Result.takescreenshot("Adding IPTV Addon");
					CO.AddOnSelection(Add_Addon, "Add");
					CO.AddOnSelection(Remove_Addon, "Delete");
				}
				if (!(getdata("IDMID").equals(""))) {
					CO.Link_Select("IDM ID");
					Browser.WebLink.click("IDM_ID");
					CO.waitforload();
					CO.waitforload();
					CO.waitforload();
					Browser.WebEdit.Set("NumberReservationToken", getdata("IDMID"));
					Result.takescreenshot("IDMID Set Succefully");
				}

				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");
				CO.waitforload();
				if (CO.isAlertExist()) {
					Continue.set(false);
				}

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
				Browser.WebTable.SetData("Numbers", Row, COl_STyp, "Service_Type", "FixedLine");
				Result.takescreenshot("Reserving the MSISDN");
				if (!MSISDN.equals("")) {

					Reserve = MSISDN.substring(3, MSISDN.length());
					Browser.WebTable.SetData("Numbers", Row, Col_Res, "Service_Id", Reserve);
					// Browser.WebButton.click("Number_Go");
					CO.waitforload();
				} else {
					Browser.WebButton.click("Number_Go");
					CO.waitforload();
					CO.waitforload();
					Browser.WebTable.click("Numbers", (Row + 1), Col);
					MSISDN = Browser.WebTable.getCellData("Numbers", (Row + 1), Col_Res);

				}

				Category = Browser.WebTable.getCellData("Numbers", Row, Col_cat);
				if (StarNumber == null) {
					StarNumber = Browser.WebTable.getCellData("Numbers", Row, Col_pri);
					StarNumber = StarNumber.substring(2, StarNumber.length());
				}
				Result.takescreenshot("Reserving the MSISDN");
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
				Col_V = Col + 2;
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
					Result.takescreenshot("Star number approval");
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
				Col = CO.Actual_Cell("Line_Items", "Product");
				Col_S = CO.Actual_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData)) {
						Row_Val = i;
					}
				}
				CO.waitforload();
				CO.Popup_Click("Line_Items", Row_Val, Col_S);
				CO.waitforload();
				Reserve = MSISDN.substring(3, MSISDN.length());
				CO.Popup_Selection("Number_Selection", "Number", Reserve);
				Result.takescreenshot("MSISDN Selected");

			} else if (!ReservationToken.equals("")) {
				Col = CO.Actual_Cell("Line_Items", "Product");
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				Col_V = Col + 2;
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equals(LData)) {
						Row_Val = i;
						break;
					}
				}
				Browser.WebTable.click("Line_Items", Row_Val, Col_V);
				Browser.WebButton.click("Customize");
				Browser.WebEdit.waittillvisible("NumberReservationToken");
				Browser.WebEdit.Set("NumberReservationToken", ReservationToken);
				Result.takescreenshot("Providing Number Reservation Token");
				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");
				CO.waitforload();
				if (CO.isAlertExist()) {
					Continue.set(false);
				}

				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				Col_S = CO.Actual_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData))
						Row_Val = i;
				}
				Browser.WebTable.click("Line_Items", Row_Val, Col_S);
				Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", MSISDN);
				CO.waitforload();
				Result.takescreenshot("MSISDN Selected");
			}

			int Col_SP = CO.Select_Cell("Line_Items", "Service Point");
			CO.waitforload();
			CO.Popup_Click("Line_Items", Row_Val, Col_SP);
			CO.waitforload();
			Result.takescreenshot("Selecting ServicePoint");
			CO.Popup_Selection("LI_ServPoint", "Location", "Not*");
			int Con_No = CO.Select_Cell("Line_Items", "Notification Contact");
			CO.waitforload();
			CO.waitforload();
			Result.takescreenshot("ServicePoint Selected");
			CO.Popup_Click("Line_Items", Row_Val, Con_No);
			CO.waitforload();
			CO.Title_Select("button", "Pick Contact:OK");
			Result.takescreenshot("Notification Contact Selected");

			CO.LineItemsSelect(MSISDN, SData);

			CO.Tag_Select("a", "Appointments");
			CO.Text_Select("a", "Appointments");
			CO.waitforload();
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Actual_Cell("Line_Items", "Product");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equalsIgnoreCase(LData)) {
					Row_Val = i;
					break;
				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, (Col + 1));
			CO.waitforload();
			Browser.WebButton.click("Activ_New");
			CO.Text_Select("span", "Book Appointment");
			CO.waitforload();
			CO.Text_Select("span", "Confirm");
			Result.takescreenshot("Appointment added Successfully");
			CO.Action_Update("Add", "MSISDN");

			CO.scroll("Service", "WebButton");

			Browser.WebButton.waittillvisible("Validate");
			Browser.WebButton.click("Validate");
			try {
				WebDriverWait wait = new WebDriverWait(cDriver.get(), 60);
				if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
					String popup = cDriver.get().switchTo().alert().getText();
					Result.fUpdateLog(popup);
				}
				Browser.alert.accept();
				Browser.Readystate();
				Continue.set(false);
			} catch (Exception e) {
				Result.fUpdateLog("No Alert Exist");
			}
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
			Result.takescreenshot("FL Consumer Order Submited");

			if (Continue.get() & (Row_Count >= 3)) {
				Status = "PASS";
				Utlities.StoreValue("PlanName", PlanName);
				Utlities.StoreValue("MSISDN", MSISDN);

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
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*--------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection_NewFL
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for New FL Provision the Order in Vanilla Journey
	 * Designed By			: Imran
	 * Last Modified Date 	: 13-JAN-2018
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	public String PlanSelection_NewFL() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {

			int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S, Col_pri, Col_cat;
			String Reserve, Category, GetData, CData, Add_Addon, Remove_Addon, ReservationToken, StarNumber = null,
					MSISDN = null, SData = "SIM Card";

			CO.waitforload();

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
			}
			Planname.set(PlanName);
			Test_OutPut += "PlanName : " + PlanName + ",";

			GetData = pulldata("GetData");
			SData = pulldata("Sdata");
			CData = pulldata("Cdata");
			int Row = 2, Col;
			CO.scroll("LI_New", "WebButton");
			Browser.WebButton.click("LI_New");
			Col = CO.Actual_Cell("Line_Items", "Product");
			Col_S = CO.Actual_Cell("Line_Items", "Service Id");
			Browser.WebTable.SetDataE("Line_Items", Row, Col, "Product", PlanName);
			Browser.WebTable.click("Line_Items", Row, Col_S);
			Result.takescreenshot("Plan Selected");
			CO.waitforload();
			int Row_Count = Browser.WebTable.getRowCount("Line_Items");

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			Result.fUpdateLog("MSISDN : " + MSISDN);
			Test_OutPut += "MSISDN : " + MSISDN + ",";

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

			if (Add_Addon != "" || Remove_Addon != "" || ReservationToken != "") {
				Col = CO.Actual_Cell("Line_Items", "Product");
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				Col_V = Col + 2;
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equals(LData)) {
						Row_Val = i;
						break;
					}
				}
				Browser.WebTable.click("Line_Items", Row_Val, Col_V);
				Browser.WebButton.click("Customize");

				if (ReservationToken != "") {
					Browser.WebEdit.waittillvisible("NumberReservationToken");
					Browser.WebEdit.Set("NumberReservationToken", ReservationToken);
					Result.takescreenshot("Providing Number Reservation Token");
					CO.waitforload();
				}
				CO.AddOnSelection(Remove_Addon, "Delete");
				CO.waitforload();
				CO.AddOnSelection(Add_Addon, "Add");
				CO.waitforload();

				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");
				CO.waitforload();
				if (CO.isAlertExist()) {
					Continue.set(false);
				}
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
				Browser.WebTable.SetData("Numbers", Row, COl_STyp, "Service_Type", "FixedLine");
				Result.takescreenshot("Reserving the MSISDN");
				if (!MSISDN.equals("")) {

					Reserve = MSISDN.substring(3, MSISDN.length());
					Browser.WebTable.SetData("Numbers", Row, Col_Res, "Service_Id", Reserve);
					// Browser.WebButton.click("Number_Go");
					CO.waitforload();
				} else {
					Browser.WebButton.click("Number_Go");
					CO.waitforload();
					CO.waitforload();
					Browser.WebTable.click("Numbers", (Row + 1), Col);
					MSISDN = Browser.WebTable.getCellData("Numbers", (Row + 1), Col_Res);

				}

				Category = Browser.WebTable.getCellData("Numbers", Row, Col_cat);
				if (StarNumber == null) {
					StarNumber = Browser.WebTable.getCellData("Numbers", Row, Col_pri);
					StarNumber = StarNumber.substring(2, StarNumber.length());
				}
				Result.takescreenshot("Reserving the MSISDN");
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
				Col_V = Col + 2;
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
					Result.takescreenshot("Star number approval");
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
				Col = CO.Actual_Cell("Line_Items", "Product");
				Col_S = CO.Actual_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData)) {
						Row_Val = i;
					}
				}
				CO.waitforload();
				CO.Popup_Click("Line_Items", Row_Val, Col_S);
				CO.waitforload();
				Reserve = MSISDN.substring(3, MSISDN.length());
				CO.Popup_Selection("Number_Selection", "Number", Reserve);
				Result.takescreenshot("MSISDN Selected");

			} else if (!ReservationToken.equals("")) {

				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				Col_S = CO.Actual_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData))
						Row_Val = i;
				}
				Browser.WebTable.click("Line_Items", Row_Val, Col_S);
				Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", MSISDN);
				CO.waitforload();
				Result.takescreenshot("MSISDN Selected");
			}

			int Col_SP = CO.Select_Cell("Line_Items", "Service Point");
			CO.waitforload();
			CO.Popup_Click("Line_Items", Row_Val, Col_SP);
			CO.waitforload();
			Result.takescreenshot("Selecting ServicePoint");
			CO.Popup_Selection("LI_ServPoint", "Location", "Not*");
			int Con_No = CO.Select_Cell("Line_Items", "Notification Contact");
			CO.waitforload();
			CO.waitforload();
			Result.takescreenshot("ServicePoint Selected");
			CO.Popup_Click("Line_Items", Row_Val, Con_No);
			CO.waitforload();
			CO.Title_Select("button", "Pick Contact:OK");
			Result.takescreenshot("Notification Contact Selected");

			CO.LineItemsSelect(MSISDN, SData);

			CO.Tag_Select("a", "Appointments");
			CO.Text_Select("a", "Appointments");
			CO.waitforload();
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Actual_Cell("Line_Items", "Product");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equalsIgnoreCase(LData)) {
					Row_Val = i;
					break;
				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, (Col + 1));
			CO.waitforload();
			Browser.WebButton.click("Activ_New");
			CO.Text_Select("span", "Book Appointment");
			CO.waitforload();
			CO.Text_Select("span", "Confirm");
			Result.takescreenshot("Appointment added Successfully");
			CO.Action_Update("Add", "MSISDN");

			CO.scroll("Service", "WebButton");

			Browser.WebButton.waittillvisible("Validate");
			Browser.WebButton.click("Validate");
			try {
				WebDriverWait wait = new WebDriverWait(cDriver.get(), 60);
				if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
					String popup = cDriver.get().switchTo().alert().getText();
					Result.fUpdateLog(popup);
				}
				Browser.alert.accept();
				Browser.Readystate();
				Continue.set(false);
			} catch (Exception e) {
				Result.fUpdateLog("No Alert Exist");
			}
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
			Result.takescreenshot("FL Consumer Order Submited");

			if (Continue.get() & (Row_Count >= 3)) {
				Status = "PASS";
				Utlities.StoreValue("PlanName", PlanName);
				Utlities.StoreValue("MSISDN", MSISDN);

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
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String IPTV_Disconnection() {

		String Test_OutPut = "", Status = "";
		String MSISDN, Order_no, Order_Reason, GetData;
		int Col, Col_P;
		Result.fUpdateLog("------Disconnect Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("Order_Reason").equals(""))) {
				Order_Reason = getdata("Order_Reason");
			} else {
				Order_Reason = pulldata("Order_Reason");
			}
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			int Col_S, Row_Count;
			String LData;
			Col = CO.Actual_Cell("Installed_Assert", "Product");
			Col_S = CO.Actual_Cell("Installed_Assert", "Service ID");
			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			for (int i = 2; i <= Row_Count; i++) {
				LData = Browser.WebTable.getCellData("Installed_Assert", i, Col);
				if (LData.equalsIgnoreCase(GetData)) {
					if ((i % 2) == 0) {
						Browser.WebTable.click("Installed_Assert", (i + 1), Col_S);
						CO.waitforload();
						break;
					} else {
						Browser.WebTable.click("Installed_Assert", (i - 1), Col_S);
						CO.waitforload();
						break;
					}
				}

			}
			do {
				Browser.WebButton.click("VFQ_Disconnect");
				String x = Browser.WebEdit.gettext("Due_Date");
				if (!x.contains("/")) {
					Browser.WebButton.click("Date_Cancel");
					Browser.WebButton.click("VFQ_Disconnect");
				}
				CO.waitforload();
			} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			if (Browser.WebEdit.gettext("Due_Date").equals(""))
				Continue.set(false);
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();
			Result.takescreenshot("Disconnect Order : ");

			if (Browser.WebButton.exist("FL_Acc_Msg")) {
				Result.fUpdateLog(Browser.WebEdit.gettext("FL_Msg_Text"));
				Browser.WebButton.click("FL_Acc_Msg");
			}
			// CO.InstalledAssertChange("Disconnect");
			CO.waitforload();
			Browser.WebButton.waittillvisible("Validate");
			CO.Webtable_Value("Order Reason", Order_Reason);

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);

				if (Action.equalsIgnoreCase("Delete") || LData.equalsIgnoreCase("Penalty Charges")) {
					Result.fUpdateLog("Action Update   " + LData + ":" + Action);
				} else {
					Result.fUpdateLog(LData + ":" + Action);
					Continue.set(false);
				}

			}

			Test_OutPut += KC.OrderSubmission().split("@@")[1];
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.waitforload();

			CO.AssertSearch(MSISDN, "Inactive");
			CO.waitforload();
			Result.takescreenshot("");
			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
			} else {
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Disconnect Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
