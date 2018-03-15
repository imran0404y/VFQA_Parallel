package Libraries;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class Keyword_FixedLine extends Driver {
	Common CO = new Common();
	Keyword_CRM KC = new Keyword_CRM();

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection_FL
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for the Order in Vanilla Journey
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 13-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	public String PlanSelection_FL() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {

			int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S, Col_pri, Col_cat, Col_b, Col_Vo;
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
			if (!(getdata("SData").equals(""))) {
				SData = getdata("Sdata");
			} else {
				SData = pulldata("Sdata");
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

			CO.scroll("LI_New", "WebButton");
			Browser.WebButton.click("LI_New");
			int Row = 2, Col;
			Col = CO.Actual_Cell("Line_Items", "Product");
			Col_S = CO.Actual_Cell("Line_Items", "Service Id");
			Browser.WebTable.SetDataE("Line_Items", Row, Col, "Product", PlanName);
			Browser.WebTable.click("Line_Items", Row, Col_S);
			CO.waitforload();
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
				Result.takescreenshot("MSISDN Selected");
				CO.Popup_Selection("Number_Selection", "Number", Reserve);

			} else if (!ReservationToken.equals("")) {
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

			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_S = CO.Actual_Cell("Line_Items", "Service Id");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (SData.equals(LData.trim())) {
					Row_Val = i;
					break;
				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_S);
			Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", MSISDN);
			int Col_SP = CO.Select_Cell("Line_Items", "Service Point");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			CO.waitforload();
			Result.takescreenshot("MSISDN Selected");
			int Col1;
			CO.waitforload();
			CO.waitforload();
			CO.Popup_Click("Line_Items", Row_Val, Col_SP);
			CO.waitforload();
			Reserve = MSISDN.substring(3, MSISDN.length());
			CO.Popup_Selection("LI_ServPoint", "Location", "Not*");
			Col = CO.Select_Cell("Line_Items", "Product");

			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			int Con_No = CO.Select_Cell("Line_Items", "Notification Contact");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			CO.waitforload();
			CO.waitforload();
			CO.Popup_Click("Line_Items", Row_Val, Con_No);
			CO.waitforload();

			CO.Title_Select("button", "Pick Contact:OK");
			CO.Tag_Select("a", "Appointments");

			CO.Text_Select("a", "Appointments");
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
			Browser.WebTable.click("Line_Items", Row_Val, Col);
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
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*--------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection_FL_ENT
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for the Order in Vanilla Journey
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 13-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public String PlanSelection_FL_ENT() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		String Charges, IP_Type, Res_CPE, Corp_SLA, Discount, Pf_Report;
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
			CO.Text_Select("span", "Customize");

			if (!(getdata("Charges").equals(""))) {
				Charges = getdata("Charges");
			} else {
				Charges = pulldata("Charges");
			}

			if (!(getdata("Static_&_Dynamic_IP_Address").equals(""))) {
				IP_Type = getdata("Static_&_Dynamic_IP_Address");
			} else {
				IP_Type = pulldata("Static_&_Dynamic_IP_Address");
			}
			if (!(getdata("Resilience_&_CPE").equals(""))) {
				Res_CPE = getdata("Resilience_&_CPE");
			} else {
				Res_CPE = pulldata("Resilience_&_CPE");
			}
			if (!(getdata("Corporate_SLA").equals(""))) {
				Corp_SLA = getdata("Corporate_SLA");
			} else {
				Corp_SLA = pulldata("Corporate_SLA");
			}
			if (!(getdata("Discount").equals(""))) {
				Discount = getdata("Discount");
			} else {
				Discount = pulldata("Discount");
			}
			if (!(getdata("Performance_Reporting").equals(""))) {
				Pf_Report = getdata("Performance_Reporting");
			} else {
				Pf_Report = pulldata("Performance_Reporting");
			}

			if (!(Charges == "")) {
				CO.Text_Select("a", "Charges");
				CO.waitforload();
				CO.FL_AddonSelection(Charges);
				Result.takescreenshot("Charges Added Scussfully");
			}

			if (!(IP_Type == "")) {
				CO.Text_Select("a", "Static & Dynamic IP Address");
				CO.waitforload();
				CO.FL_AddonSelection(IP_Type);
				Result.takescreenshot("Static & Dynamic IP Address Addon Added Scussfully");
			}
			Result.takescreenshot("Charges Added Scussfully");
			if (!(Res_CPE == "")) {
				CO.Text_Select("a", "Resilience & CPE");
				CO.waitforload();
				CO.FL_AddonSelection(Res_CPE);
				Result.takescreenshot("Resilience & CPE Addon Added Scussfully");
			}

			if (!(Corp_SLA == "")) {
				CO.Text_Select("a", "Corporate SLA");
				CO.waitforload();
				CO.FL_AddonSelection(Corp_SLA);
				Result.takescreenshot("Corp_SLA Addon Added Scussfully");
			}
			if (!(Discount == "")) {
				CO.Text_Select("a", "Discount");
				CO.waitforload();
				CO.FL_AddonSelection(Discount);
				Result.takescreenshot("Discount Addon Added Scussfully");
			}
			if (!(Pf_Report == "")) {
				CO.Text_Select("a", "Performance Reporting");
				CO.waitforload();
				CO.FL_AddonSelection(Pf_Report);
				Result.takescreenshot("Pf_Report Addon Added Scussfully");
			}
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			if (CO.isAlertExist()) {
				Continue.set(false);
				Result.fUpdateLog("Error On Clicking Done Button");
				System.exit(0);
			}

			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.Action_Update("Add", "MSISDN");
			Result.takescreenshot("");
			// Test_OutPut += KC.OrderSubmission().split("@@")[1];
			// CO.Action_Update("Add", "MSISDN");

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

			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Change_PrimaryNumber
	 * Arguments			: None
	 * Use 					: Change of Primary number
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String FL_ONT_CPE_Replacement() {
		String Test_OutPut = "", Status = "";
		String MSISDN, GetData = null, Order_no;
		int Row_Val = 0;

		Result.fUpdateLog("------Change FL Order Replacement Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			if (Browser.WebButton.exist("Assert_Modify")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				int Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");

				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else
				CO.InstalledAssertChange("Modify");

			Result.takescreenshot("Clicking On Modify Button");
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitforload();
			CO.waitforload();
			CO.Link_Select("Broadband Internet Features");
			if (!(getdata("CPE").equals("")) || (!(pulldata("CPE").equals("")))) {

				CO.Radio_Select("Broadband CPE");
				CO.waitforload();
				Result.takescreenshot("Deselecting Broadband CPE Button");
				CO.Radio_Select("Broadband CPE");
				CO.waitforload();
				Result.takescreenshot("selecting Broadband CPE Button");
			}
			if (!(getdata("ONT").equals("")) || (!(pulldata("ONT").equals("")))) {
				CO.Radio_Select("ONT MDU Port Equipment");
				CO.waitforload();
				Result.takescreenshot("Deselecting ONT_MDU_Port_Equipment Button");
				CO.Radio_Select("ONT MDU Port Equipment");
				CO.waitforload();
				Result.takescreenshot("Selecting ONT_MDU_Port_Equipment Button");
			}

			CO.waitforload();
			CO.Text_Select("button", "Verify");

			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			if (CO.isAlertExist()) {
				Continue.set(false);
				Result.fUpdateLog("Error On Clicking Done Button");
				System.exit(0);
			}
			Result.takescreenshot("");
			CO.waitforload();
			Order_no = CO.Order_ID();
			SalesOrder_No.set(Order_no);
			CO.waitforload();
			CO.Tag_Select("a", "Appointments");

			CO.Text_Select("a", "Appointments");
			CO.waitforload();
			int Row_Count = Browser.WebTable.getRowCount("Line_Items");
			int Col = CO.Actual_Cell("Line_Items", "Product");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equalsIgnoreCase(LData)) {
					Row_Val = i;
					break;
				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col);
			CO.waitforload();
			Browser.WebButton.click("Activ_New");
			CO.Text_Select("span", "Book Appointment");
			Result.takescreenshot("Clicking on Book Appointment");
			CO.waitforload();
			CO.Text_Select("span", "Confirm");
			Result.takescreenshot("Appointment added Successfully");
			CO.Action_Update("Add", "MSISDN");

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
				Result.takescreenshot("Clicking Submit Button");
			}

			if (Continue.get()) {
				Status = "PASS";
				Utlities.StoreValue("MSISDN", MSISDN);
				Utlities.StoreValue("Order_no", Order_no);
				Result.takescreenshot("FL Consumer Order Replacement Submited sucessfully");

			} else {
				Status = "FAIL";
				Test_OutPut += "Order Replacement is Falied" + ",";
				Result.takescreenshot("Order Replacement Failed");
				Result.fUpdateLog("Order Replacement Failed");
			}

			CO.waitforload();

		} catch (Exception e) {
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();

		}
		Result.fUpdateLog("-----Change FL Order Replacement Event  - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	public String OrderVerfication() {
		String Test_OutPut = "", Status = "";
		int Row = 2, Col;
		try {
		//String Sales_Od = SalesOrder_No.get();
		String Sales_Od = "1-10518905394";
			Result.takescreenshot("Searching Order in Seibel");
			Browser.WebLink.click("SalesOrder");
			Browser.WebLink.click("All_Orders");
			Browser.WebButton.click("SD_Query");
			Result.takescreenshot("Order Search");
			CO.waitforload();
			Col = CO.Select_Cell("SalesOrderTable", "Order #");
			Browser.WebTable.SetDataE("SalesOrderTable", Row, Col, "Order_Number", Sales_Od);
			Browser.WebButton.click("SD_Go");
			CO.waitforload();
			Browser.WebTable.click("SalesOrderTable", Row, Col);
			CO.waitforload();
			Col = CO.Select_Cell("SalesOrderTable", "Status");
			String status = Browser.WebTable.getCellData("SalesOrderTable", Row, Col);
			if (status.equalsIgnoreCase("Complete")) {
				Result.fUpdateLog("Seibel status has been updated Successfully");

			} else {
				Test_OutPut += "Seibel status updation Failed" + ",";
				Result.takescreenshot("Seibel status updation Failed");
			}

		} catch (Exception e) {
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();

		}
		Result.fUpdateLog("-----Change Primary Number Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
