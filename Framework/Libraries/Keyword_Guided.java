package Libraries;

import java.util.Random;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class Keyword_Guided extends Driver

{

	Common CO = new Common();
	Random R = new Random();

	/*---------------------------------------------------------------------------------------------------------
	* Method Name             : ConsumerPostpaid_GuidedJourney
	* Arguments               : None
	* Use                     : GJ Flow for Postpaid Consumer Provisioning
	* Designed By             : Vinodhini Raviprasad
	* Last Modified Date      : 05-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	public String ConsumerPostpaid_GuidedJourney() {
		String Test_OutPut = "", Status = "";
		String MSISDN, SIM, SP_Plan, ID_Type, ID_Expiry, Nationality, Gender, Language, First, Last, DOB, Job, Email,
				Mobile, File_Upload, IDNumber, CAF_NewFile, Segment, Account_Name, MediaType, BillType, BillLang,
				OrderNo;
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			if (!(getdata("Segment").equals(""))) {
				Segment = getdata("Segment");
			} else {
				Segment = pulldata("Segment");
			}
			if (!(getdata("SIM").equals(""))) {
				SIM = getdata("SIM");
			} else {
				SIM = pulldata("SIM");
			}
			if (!(getdata("MediaType").equals(""))) {
				MediaType = getdata("MediaType");
			} else {
				MediaType = pulldata("MediaType");
			}

			if (!(getdata("BillType").equals(""))) {
				BillType = getdata("BillType");
			} else {
				BillType = pulldata("BillType");
			}
			// BillLang
			if (!(getdata("BillLang").equals(""))) {
				BillLang = getdata("BillLang");
			} else {
				BillLang = pulldata("BillLang");
			}
			if (!(getdata("PlanName").equals(""))) {
				SP_Plan = getdata("PlanName");
			} else {
				SP_Plan = pulldata("PlanName");
			}
			Planname.set(SP_Plan);

			if (!(getdata("ID_Type").equals(""))) {
				ID_Type = getdata("ID_Type");
			} else {
				ID_Type = pulldata("ID_Type");
			}

			if (!(getdata("ID_Number").equals(""))) {
				IDNumber = getdata("ID_Number");
			} else {
				IDNumber = pulldata("ID_Number") + R.nextInt(10000);
			}

			if (!(getdata("ID_Expiry").equals(""))) {
				ID_Expiry = getdata("ID_Expiry");
			} else {
				ID_Expiry = pulldata("ID_Expiry");
			}
			if (!(getdata("Nationality").equals(""))) {
				Nationality = getdata("Nationality");
			} else {
				Nationality = pulldata("Nationality");
			}
			if (!(getdata("Language").equals(""))) {
				Language = getdata("Language");
			} else {
				Language = pulldata("Language");
			}
			if (!(getdata("Gender").equals(""))) {
				Gender = getdata("Gender");
			} else {
				Gender = pulldata("Gender");
			}

			if (!(getdata("FirstName").equals(""))) {
				First = getdata("FirstName");
			} else if (!(pulldata("FirstName").equals(""))) {
				First = pulldata("FirstName") + R.nextInt(100000);
			} else {
				First = Utlities.randname();
			}

			if (!(getdata("LastName").equals(""))) {
				Last = getdata("LastName");
			} else if (!(pulldata("LastName").equals(""))) {
				Last = pulldata("LastName") + R.nextInt(100000);
			} else {
				Last = Utlities.randname();
			}

			if (!(getdata("DOB").equals(""))) {
				DOB = getdata("DOB");
			} else {
				DOB = pulldata("DOB");
			}

			if (!(getdata("Job").equals(""))) {
				Job = getdata("Job");
			} else {
				Job = pulldata("Job");
			}

			if (!(getdata("Email").equals(""))) {
				Email = getdata("Email");
			} else {
				Email = pulldata("Email");
			}

			if (!(getdata("Mobile").equals(""))) {
				Mobile = getdata("Mobile");
			} else {
				Mobile = pulldata("Mobile");
			}

			if (!(getdata("File_Upload").equals(""))) {
				File_Upload = getdata("File_Upload");
			} else {
				File_Upload = pulldata("File_Upload");
			}

			if (!(getdata("CAF_NewFile").equals(""))) {
				CAF_NewFile = getdata("CAF_NewFile");
			} else {
				CAF_NewFile = pulldata("CAF_NewFile");
			}

			CO.waitforobj("VQ_Contact", "WebLink");
			CO.waitforload();
			Browser.WebLink.click("VQ_Contact");
			CO.waitforload();
			CO.waitforobj("All_contacts", "WebLink");
			Browser.WebLink.click("All_contacts");
			CO.waitforload();
			CO.waitforobj("Acc_Add_Contact", "WebButton");
			Browser.WebButton.click("Acc_Add_Contact");
			CO.waitforobj("Consumer_Postpaid", "WebLink");
			Browser.WebLink.click("Consumer_Postpaid");
			CO.waitforload();
			Result.takescreenshot("Navigated to Consumer Postpaid");
			CO.waitforload();

			Browser.WebEdit.waittillvisible("ID_Number_Guided");
			Browser.WebEdit.waitTillEnabled("ID_Number_Guided");

			CO.scroll("ID_Number_Guided", "WebEdit");
			Browser.WebEdit.Set("ID_Number_Guided", IDNumber);
			Result.fUpdateLog("IDNumber : " + IDNumber);
			Test_OutPut += "IDNumber : " + IDNumber + ",";

			CO.waitforload();
			CO.waitforobj("IDType", "ListBox");
			Browser.ListBox.select("IDType", ID_Type);
			Result.fUpdateLog("ID_Type : " + ID_Type);
			Test_OutPut += "ID_Type : " + ID_Type + ",";

			CO.waitforobj("GO", "WebButton");
			Browser.WebButton.click("GO");
			CO.waitforload();

			Browser.WebEdit.waittillvisible("IDExpiry");
			Browser.WebEdit.waitTillEnabled("IDExpiry");

			Result.takescreenshot("");

			CO.waitforobj("IDExpiry", "WebEdit");

			CO.scroll("IDExpiry", "WebEdit");
			if (!(ID_Type.equalsIgnoreCase("Qatari ID")) & TestCaseN.get().equalsIgnoreCase("NewCustomer")) {
				Browser.WebEdit.Set("IDExpiry", ID_Expiry);

				Browser.WebEdit.Set("Nationality", Nationality);

				Browser.WebEdit.Set("Gender", Gender);
				CO.waitforload();
				Browser.WebEdit.Set("First", First);
				CO.waitforload();
				Browser.WebEdit.Set("Last", Last);

				Browser.WebEdit.Set("DOB_Guided", DOB);

				Browser.WebEdit.Set("Job", Job);

			} else {
				if (Browser.WebEdit.gettext("First").isEmpty() & Browser.WebEdit.gettext("Last").isEmpty()
						& Browser.WebEdit.gettext("Nationality").isEmpty())
					Continue.set(false);
			}

			Result.takescreenshot("");
			Browser.WebEdit.Set("Email_Guided", Email);
			Browser.ListBox.select("Language", Language);
			Browser.WebEdit.Set("Mobile_Guided", Mobile);
			CO.Upload("File_Upload", File_Upload);
			CO.waitforload();
			Browser.WebButton.waittillvisible("Continue");
			Browser.WebButton.click("Continue");
			Result.fUpdateLog("Guided :  contact Completed");

			// Search for Specific Address
			if (TestCaseN.get().equalsIgnoreCase("NewCustomer")) {
				String Address;

				if (!(getdata("Address").equals(""))) {
					Address = getdata("Address");
				} else if (!(getdata("Kahramaa_ID").equals(""))) {
					Address = "Kar#" + getdata("Kahramaa_ID");
				} else if (!(pulldata("Kahramaa_ID").equals(""))) {
					Address = "Kar#" + pulldata("Kahramaa_ID");
				} else {
					Address = pulldata("Address");
				}

				CO.waitforload();

				Browser.WebButton.waittillvisible("Add_Address_Guided");

				CO.waitforload();

				CO.waitforobj("Add_Address_Guided", "WebButton");

				Browser.WebButton.click("Add_Address_Guided");

				// Address

				CO.waitforobj("Popup_Go_Guided", "WebButton");
				CO.scroll("Popup_Go_Guided", "WebButton");

				if (Address.contains("Kar#")) {
					Browser.ListBox.select("PopupQuery_List", "Kahramaa ID");
					Browser.WebEdit.Set("PopupQuery_Search", Address.split("#")[1]);
				} else {
					Browser.ListBox.select("PopupQuery_List", "Address Line 1");
					Browser.WebEdit.Set("PopupQuery_Search", Address);
				}
				CO.waitforload();

				Browser.WebButton.click("Popup_Go_Guided");

				CO.waitforload();

				CO.scroll("Add_OK", "WebButton");
				Browser.WebButton.click("Add_OK");

				CO.waitforload();

				Result.takescreenshot("Address Selected : " + Address);
				Test_OutPut += "Address : " + Address + ",";
				Utlities.StoreValue("Address", Address);
				CO.waitforload();
			} else {
				Result.takescreenshot("Proceeding with existing Address");
				Result.fUpdateLog("Proceeding with existing Address");
			}

			Browser.WebButton.waittillvisible("Continue");
			CO.scroll("Continue", "WebButton");

			Browser.WebButton.click("Continue");

			Result.fUpdateLog("Guided :  Address Completed");

			CO.waitforload();

			// Account

			// CO.scroll("Customer_Segment", "ListBox");
			/*
			 * if (TestCaseN.get().equalsIgnoreCase("NewCustomer")) { *
			 * Browser.ListBox.select("Customer_Segment", Segment); }
			 */
			// Browser.ListBox.select("Customer_Segment", Segment);
			CO.waitforload();
			Account_Name = Browser.WebEdit.gettext("Account_Name");
			Test_OutPut += "Account_Name : " + Account_Name + ",";
			Utlities.StoreValue("Account_Name", Account_Name);
			Result.takescreenshot("Guided :  Account  Completed");
			Result.fUpdateLog("Guided :  Account  Completed");

			Browser.WebButton.waittillvisible("Continue");
			CO.scroll("Continue", "WebButton");
			Browser.WebButton.click("Continue");

			// Billing

			CO.waitforload();
			// String Account_no= Browser.WebEdit.getAttribute("Guid_Account");

			CO.scroll("MediaType", "ListBox");

			if (TestCaseN.get().equalsIgnoreCase("NewCustomer")) {
				Browser.ListBox.select("MediaType", MediaType);
				Browser.ListBox.select("BillType", BillType);
				Browser.ListBox.select("BillLang", BillLang);
			}

			CO.waitforobj("Bill_Continue", "WebButton");
			Browser.WebButton.waittillvisible("Bill_Continue");
			Result.takescreenshot("Guided :  Account  Completed");
			Browser.WebButton.click("Bill_Continue");

			Result.fUpdateLog("Guided :  Billing profile Completed");
			// Reserved no

			CO.waitforobj("Reserve_Num", "WebButton");
			String Number = MSISDN, Temp;
			Test_OutPut += "MSISDN : " + Number + ",";
			Utlities.StoreValue("MSISDN", Number);
			// String Number = Driver.msisdn_exe.get(), Temp;
			int Len = Number.length(), Col_Count = Browser.WebTable.getColCount("Unreserverd"), Col_Res = 0, Row = 2;
			String Reserve = Number.substring(3, Len);
			for (int i = 2; i < Col_Count; i++) {
				Temp = CO.Col_Data(i);
				Col_Res = i;
				if (Temp.toLowerCase().trim().equals("service type"))
					break;
			}

			/**
			 * if (Browser.WebButton.exist("Query_Unreserv_Num")) {
			 * CO.scroll("Query_Unreserv_Num", "WebButton");
			 * Browser.WebButton.click("Query_Unreserv_Num");}
			 */

			CO.scroll("Unreserverd", "WebTable");
			Browser.WebTable.SetDataE("Unreserverd", Row, Col_Res, "Service_Type", "Mobile");
			CO.scroll("Unreserverd", "WebTable");
			Browser.WebTable.SetData("Unreserverd", Row, Col_Res + 3, "Service_Id", Reserve);

			Browser.WebButton.waitTillEnabled("Reserve_Num");
			Browser.WebButton.waittillvisible("Reserve_Num");

			CO.waitforload();
			int Row_Count = Browser.WebTable.getRowCount("Unreserverd");
			Result.fUpdateLog(Row_Count + " Unreserved Row Count");
			// To check whether number available
			Result.takescreenshot("Number Reservation");
			if (Row_Count > 1) {
				CO.scroll("Reserve_Num", "WebButton");
				CO.Text_Select("span", "Reserve");
				CO.waitforload();
				CO.isAlertExist();
			} else {
				Driver.Continue.set(false);
				Result.fUpdateLog("Guided : Check the MSISDN Input Data Issue");
				System.exit(0);
			}
			// Reserved
			CO.scroll("Reserved", "WebTable");
			Browser.WebTable.SetDataE("Reserved", Row, Col_Res, "Service_Type", "Mobile");
			Browser.WebTable.SetData("Reserved", Row, Col_Res + 1, "Category", "");

			// To check whether the Number has been Reserved
			// CO.waitforload();
			CO.waitforobj("SP_Resrv_Continue", "WebButton");
			Row_Count = Browser.WebTable.getRowCount("Reserved");
			Result.fUpdateLog("Guided : Unreserved" + Row_Count);
			if (Row_Count > 1)
				Result.takescreenshot("MSISDN Reserved " + Number);
			else {
				Driver.Continue.set(false);
				Result.fUpdateLog("Guided : Check the MSISDN");
				System.exit(0);
			}
			CO.scroll("SP_Resrv_Continue", "WebButton");
			CO.Text_Select("span", "Continue");
			Result.fUpdateLog("Guided :  Number Reserved");

			CO.waitforload();

			// To Provide SIM Number
			CO.waitforobj("SP_Continue", "WebButton");
			Browser.WebEdit.waittillvisible("SIM_Guided");
			Browser.WebEdit.waitTillEnabled("SIM_Guided");

			// CO.waitmoreforload();
			CO.waitforload();
			Driver.Continue.set(true);
			CO.scroll("SIM_Guided", "WebEdit");
			Browser.WebEdit.click("SIM_Guided");
			Browser.WebEdit.Set("SIM_Guided", SIM);
			Test_OutPut += "SIM : " + SIM + ",";
			Utlities.StoreValue("SIM_NO", SIM);

			Result.takescreenshot("Guided :  SIM Provided");
			Browser.WebButton.waittillvisible("SP_Continue");
			CO.scroll("SP_Continue", "WebButton");
			CO.waitforload();
			Browser.WebButton.click("SP_Continue");
			CO.waitforload();
			Result.fUpdateLog("Guided :  SIM Provided");

			// Plan

			CO.waitforload();
			if (TestCaseN.get().equalsIgnoreCase("NewCustomer")) {
				CO.Category_Select("Postpaid Plans", "Postpaid Consumer Plans");
				Result.takescreenshot("Plan Selection");
			}

			CO.waitforload();
			CO.Link_Select(SP_Plan);
			CO.Plan_Select("Plan_Search", SP_Plan);
			Test_OutPut += "PlanName : " + SP_Plan + ",";
			Utlities.StoreValue("PlanName", SP_Plan);
			CO.waitmoreforload();
			CO.waitmoreforload();
			CO.waitforobj("SP_Plan_Customise", "WebButton");
			Browser.WebButton.waittillvisible("SP_Plan_Customise");
			Browser.WebButton.waitTillEnabled("SP_Plan_Customise");
			Browser.WebButton.waittillvisible("CAF_Payment");
			Browser.WebButton.waitTillEnabled("CAF_Payment");
			Result.takescreenshot("");
			CO.waitmoreforload();
			CO.scroll("CAF_Payment", "WebButton");
			Browser.WebButton.click("CAF_Payment");
			Result.takescreenshot("Guided : Plan Selected");
			Result.fUpdateLog("Guided : Plan Selected ");

			// Payment

			CO.waitforload();
			CO.waitmoreforload();
			CO.waitforload();
			CO.scroll("Generate_CAF", "WebButton");
			Browser.WebButton.click("Generate_CAF");

			CO.waitforload();

			// CAF
			CO.waitforload();
			CO.waitmoreforload();
			CO.waitmoreforload();
			CO.scroll("New_File", "WebButton");
			CO.waitmoreforload();
			Result.takescreenshot("");

			CO.Upload("New_File", CAF_NewFile);
			CO.waitforload();
			Result.takescreenshot("");
			Result.takescreenshot("CAF Attachment done");
			Browser.WebButton.waittillvisible("SP_Attach_Continue");
			Browser.WebButton.waitTillEnabled("SP_Attach_Continue");
			CO.waitforobj("SP_Attach_Continue", "WebButton");
			CO.scroll("SP_Attach_Continue", "WebButton");
			Browser.WebButton.waittillvisible("SP_Attach_Continue");
			Result.takescreenshot("");
			Browser.WebButton.click("SP_Attach_Continue");
			CO.waitforload();
			Result.fUpdateLog("Guided : CAF Created");
			CO.waitmoreforload();
			// Completion

			Result.takescreenshot(" Completion Process");
			String status = Browser.WebEdit.gettext("Resolution_Code");
			CO.scroll("Comments", "WebEdit");
			Browser.WebEdit.Set("Comments", status + "with Status" + R.nextInt(10));
			Result.takescreenshot("Order Submited and " + status);
			Result.takescreenshot("");
			CO.scroll("Save&Continue", "WebButton");
			Browser.WebButton.click("Save&Continue");
			CO.waitforload();
			Result.takescreenshot("");
			CO.Text_Select("span", "Finish Process");
			Result.fUpdateLog("Guided : Process Done");
			CO.waitmoreforload();
			// Order Status Verification
			if (Browser.WebButton.exist("ContactQuery")) {
				Browser.WebButton.click("ContactQuery");
			} else {
				Browser.WebLink.waittillvisible("VQ_Contact");
				Browser.WebLink.click("VQ_Contact");
				CO.waitforload();
				Browser.WebButton.click("ContactQuery");
			}
			// Account_Name="VFQA_Test_Leroy VFQA_Test_Jude Postpaid";

			CO.waitforload();
			Row = 2;
			int Col, col1, RowCount;
			Col = CO.Select_Cell("Contact", "Account");
			Browser.WebTable.SetDataE("Contact", Row, Col, "Account", Account_Name);

			col1 = CO.Select_Cell("Contact", "ID Number");
			Browser.WebTable.SetDataE("Contact", Row, col1, "ID_Number", IDNumber);
			/*
			 * Col = CO.Select_Cell("Contact", "ID Type");
			 * Browser.WebTable.SetData("Contact", Row, Col, "ID_Number", ID_Type);
			 */
			Browser.WebButton.click("Contact_Go");

			CO.waitforload();
			RowCount = Browser.WebTable.getRowCount("Contact");

			if (RowCount >= 2) {
				Col = CO.Select_Cell("Contact", "Account");
				Browser.WebTable.clickL("Contact", Row, Col);
				Result.takescreenshot("Contact Verification is successfull");
				Result.fUpdateLog("Contact Verification is successfull");
			} else {
				Result.takescreenshot("Contact Verification Failure");
				Continue.set(false);
			}

			// To Navigate Orders Tab
			do {
				Browser.WebButton.click("Orders_Tab");
				CO.waitforload();
				if (CO.isAlertExist())
					Browser.WebButton.click("Orders_Tab");

			} while (!Browser.WebTable.waitTillEnabled("Order_Table"));

			CO.waitforload();
			/*
			 * CO.Text_Select("div", "Order Date"); CO.waitforload();
			 * cDriver.get().findElement(By.xpath("//th[@title=(Sortable)]")).click();
			 * CO.Text_Select("div", "Order Date");
			 */
			CO.waitforload();

			Col = CO.Select_Cell("Order_Table", "Order #");
			OrderNo = Browser.WebTable.getCellData("Order_Table", Row, Col);
			Browser.WebTable.clickL("Order_Table", Row, Col);

			Test_OutPut += "OrderNo : " + OrderNo + ",";
			Utlities.StoreValue("OrderNo", OrderNo);
			Result.fUpdateLog("Postpaid Consumer Guided Journey Order " + OrderNo);
			CO.waitforload();

			// Check Plan Matches

			Result.fUpdateLog("------Order Status Verification------");

			int Complete_Status = 0, Wait = 0, Bill_Col, COL_FUL_STATUS = 0;
			String EStatus = "Complete", FStatus = "Failed", Bill_Cycle, OS_Status;

			Col = COL_FUL_STATUS;
			cDriver.get().navigate().refresh();
			Browser.WebButton.waittillvisible("Submit");
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			do {

				Complete_Status = 0;
				// To refresh Page
				cDriver.get().navigate().refresh();
				Browser.WebButton.waittillvisible("Submit");
				CO.waitforload();
				CO.scroll("Ful_Status", "WebButton");
				Col = CO.Select_Cell("Line_Items", "Fulfillment Status");
				for (int i = 2; i <= Row_Count; i++) {
					CO.scroll("Submit", "WebButton");
					CO.scroll("Ful_Status", "WebButton");

					OS_Status = Browser.WebTable.getCellData("Line_Items", i, Col);
					Result.fUpdateLog("Round" + (i - 1) + " " + OS_Status);
					// To Find the Complete Status
					if (EStatus.equalsIgnoreCase(OS_Status)) {
						Complete_Status = Complete_Status + 1;
						if (Complete_Status == (Row_Count - 1)) {
							Wait = 101;
						}
					} else if (FStatus.equalsIgnoreCase(OS_Status)) {
						Continue.set(false);
						Wait = 101;
					}
				}
				Wait = Wait + 5;
				CO.waitmoreforload();
			} while (Wait < 100);
			Browser.WebButton.waittillvisible("Submit");
			Result.takescreenshot("");
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			CO.scroll("Submit", "WebButton");
			OS_Status = Browser.WebTable.getCellData("Line_Items", Row, Col);
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			Bill_Col = CO.Actual_Cell("Line_Items", "Bill Cycle");
			Bill_Cycle = Browser.WebTable.getCellData("Line_Items", Row, Bill_Col);
			billDate.set(Bill_Cycle);

			if (OS_Status.equalsIgnoreCase(EStatus) || Complete_Status == (Row_Count - 1)) {
				Test_OutPut += "Order Status : " + OS_Status + ",";
				Result.fUpdateLog("Postpaid_Guided_Journey Order Status : " + OS_Status);
				Result.takescreenshot("Postpaid_Guided_Journey Order Status : " + OS_Status);
				Continue.set(true);
			} else {

				Result.fUpdateLog("Postpaid_Guided_Journey Order Status : " + FStatus);
				Result.takescreenshot("Postpaid_Guided_Journey Order Status : " + FStatus);
				Test_OutPut += "Order Status : " + FStatus + ",";
				Continue.set(false);
			}
			CO.Action_Update("Add", MSISDN);
			CO.ToWait();
			if (Continue.get()) {

				Status = "PASS";
			} else {
				Test_OutPut += "Action Status Failed" + ",";
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
		Result.fUpdateLog("Post-paid guided flow- Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	* Method Name             : SIMSwap_Global_Search
	* Arguments               : None
	* Use                     : GJ Flow for SIM Swap via Global Search
	* Designed By             : Vinodhini Raviprasad
	* Last Modified Date      : 05-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public String SIMSwap_Global_Search()

	{
		String MSISDN, New_SIM, Comments, IDNum, IDType, FirstName, Order_no, Order_Status, GetData, MSISDNStatus;
		String Test_OutPut = "", Status = "";
		int Col, Row = 2;
		Result.fUpdateLog("------SIM swap Global Search------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_SIM").equals(""))) {
				New_SIM = getdata("New_SIM");
			} else {
				New_SIM = pulldata("New_SIM");
			}

			if (!(getdata("Comments").equals(""))) {
				Comments = getdata("Comments");
			} else {
				Comments = pulldata("Comments");
			}
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			if (!(getdata("Status").equals(""))) {
				MSISDNStatus = getdata("Status");
			} else {
				MSISDNStatus = pulldata("Status");
			}

			CO.waitforload();
			Browser.WebLink.waittillvisible("Global_Search");
			Browser.WebLink.click("Global_Search");
			CO.waitforload();

			Browser.WebEdit.Set("Phone_Guided", MSISDN);
			Result.fUpdateLog("Global Search Initiation");
			Result.takescreenshot("Global Search Initiation");
			Browser.WebLink.click("GS_Go");
			CO.waitforload();
			Thread.sleep(1000);

			Browser.WebButton.waittillvisible("GS_Simswap");
			Result.fUpdateLog("Global Search MSISDN Retrived");
			Result.takescreenshot("Global Search MSISDN Retrived");
			Browser.WebButton.click("GS_Simswap");
			CO.scroll("Cancel_SIMSwap", "WebButton");

			CO.waitforload();

			Browser.WebButton.waittillvisible("S_Continue");
			Result.fUpdateLog("Proceeding SIM Swap Procees");
			Result.takescreenshot("Proceeding SIM Swap Procees");

			Result.takescreenshot("Contact Verification for SIM Swap Guided Journey");
			Result.fUpdateLog("Contact Verification for SIM Swap Guided Journey");
			IDNum = Browser.WebEdit.gettext("Contact_IDNumber");
			IDType = Browser.WebEdit.gettext("Contact_IDType");
			FirstName = Browser.WebEdit.gettext("FirstName");
			Browser.WebButton.click("S_Continue");
			CO.waitforload();

			Browser.WebEdit.clear("New_SIM_SNO");
			Thread.sleep(2000);
			Browser.WebEdit.Set("New_SIM_SNO", New_SIM);

			CO.waitforload();

			Result.fUpdateLog("Providing New SIM");
			Result.takescreenshot("Providing New SIM");
			Browser.WebButton.waittillvisible("Submit_SimSwap");
			Browser.WebButton.click("Submit_SimSwap");

			CO.scroll("Save_Finish", "WebLink");

			CO.waitforload();

			Browser.WebEdit.Set("Comments", Comments + R.nextInt(100));
			Result.fUpdateLog("Providing Comments");
			Result.takescreenshot("Comments Provided");
			Browser.WebLink.click("Save_Finish");

			CO.waitforload();

			Result.fUpdateLog("Proceeding Finish Process");
			Result.takescreenshot("Proceeding Finish_Process");
			Browser.WebLink.click("Finish_Process");

			CO.waitforload();
			CO.waitforobj("GS_Simswap", "WebButton");
			Browser.WebButton.click("GS_Simswap");
			CO.waitforload();
			CO.scroll("Cancel_SIMSwap", "WebButton");
			CO.waitforload();

			if (IDNum.equalsIgnoreCase(Browser.WebEdit.gettext("Contact_IDNumber"))
					& IDType.equalsIgnoreCase(Browser.WebEdit.gettext("Contact_IDType"))
					& FirstName.equalsIgnoreCase(Browser.WebEdit.gettext("FirstName"))) {
				Result.takescreenshot("Contact Verification is Successfull");
				Result.fUpdateLog("Contact Verification is Successfull");
			} else {
				Result.takescreenshot("Contact Verification Failure");
				Continue.set(false);
			}
			// Wait time for Dunning Action
			int loop = 0;
			do {
				cDriver.get().navigate().refresh();
				Thread.sleep(5000);
				loop++;
			} while (loop <= 2);
			CO.waitforload();
			CO.waitmoreforload();
			CO.TraverseLatestOrder(MSISDN, MSISDNStatus);
			int COL_FUL_STATUS = 0;
			CO.scroll("Ful_Status", "WebButton");
			Col = CO.Select_Cell("Line_Items", "Fulfillment Status");
			if (CO.Col_Data(Col).equalsIgnoreCase("fulfillment status"))
				COL_FUL_STATUS = Col;
			int Row_Count = Browser.WebTable.getRowCount("Line_Items");
			CO.scroll("Submit", "WebButton");
			if (Row_Count <= 3) {
				CO.scroll("Expand", "WebButton");
				Browser.WebButton.click("Expand");
			}
			Result.takescreenshot("Order Status Verification");
			Order_Status = Browser.WebTable.getCellData("Line_Items", Row_Count, COL_FUL_STATUS);
			if (Order_Status.equals("Complete") == false) {
				Continue.set(false);
				Result.fUpdateLog("Order is not Completed");
				Result.takescreenshot("Order is not Completed");
			}

			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			CO.Action_Update("Update", "");
			Result.takescreenshot("SIM Swap Order no: " + Order_no);

			CO.ToWait();
			CO.GetSiebelDate();
			Result.fUpdateLog("Account Level Validation");
			Result.takescreenshot("Account Level Validation");
			CO.GlobalSearch("MSISDN", MSISDN);
			if (Browser.WebLink.exist("Acc_Portal")) {
				CO.waitforload();
				Browser.WebLink.click("Acc_Portal");
			}
			CO.waitforload();
			CO.InstalledAssertChange("New Query                   [Alt+Q]");
			Col = CO.Select_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitmoreforload();
			Browser.WebButton.click("Expand");
			Col = CO.Select_Cell("Acc_Installed_Assert", "Product");
			int RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
			Browser.WebTable.click("Acc_Installed_Assert", Row, Col + 1);
			boolean flag = false;
			for (Row = 2; Row <= RowCount; Row++) {
				if (Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col).trim().equals("SIM Card")) {
					Col = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col).trim().equals(New_SIM)) {
						flag = true;
						Result.fUpdateLog(
								"SIM Swap reflected in Accountfor the specific MSISDN " + MSISDN + " as " + New_SIM);
						Result.takescreenshot(
								"SIM Swap reflected in Accountfor the specific MSISDN " + MSISDN + " as " + New_SIM);
					} else {
						Result.fUpdateLog("SIM Swap is not reflected in Accountfor the specific MSISDN " + MSISDN
								+ " as " + New_SIM);
						Result.takescreenshot("SIM Swap is not reflected in Account for the specific MSISDN " + MSISDN
								+ " as " + New_SIM);
					}
					break;
				}

			}
			if (Continue.get() & flag) {
				Test_OutPut += "";
				Result.takescreenshot("SIM swap Global Search is Successfull");
				Result.fUpdateLog("SIM swap Global Search is Successfull");
				Status = "PASS";
			} else {
				Test_OutPut += "SIM swap Global Search Failed" + ",";
				Result.takescreenshot("SIM swap Global Search Failed");
				Result.fUpdateLog("SIM swap Global Search Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();

		}
		Result.fUpdateLog("-----SIM swap Global Search - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name             : SIMSwap_Guided_Journey
	 * Arguments               : None
	 * Use                     : GJ Flow for SIM Swap
	 * Designed By             : Vinodhini Raviprasad
	 * Last Modified Date      : 05-Dec-2017
	 --------------------------------------------------------------------------------------------------------*/
	public String SIMSwap_Guided_Journey()

	{
		String MSISDN, New_SIM, Comments;
		int Row = 2, Col;
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------SIM swap Guided journey------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_SIM").equals(""))) {
				New_SIM = getdata("New_SIM");
			} else {
				New_SIM = pulldata("New_SIM");
			}

			if (!(getdata("Comments").equals(""))) {
				Comments = getdata("Comments");
			} else {
				Comments = pulldata("Comments");
			}
			Random R = new Random();
			CO.waitforload();
			CO.scroll("VQ_Assert", "WebLink");
			Browser.WebLink.click("VQ_Assert");
			CO.waitforload();

			CO.scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			CO.waitforload();

			Result.takescreenshot("Assert Search");
			Result.fUpdateLog("Assert Search Initiation");
			// Installed_Assert
			Col = CO.Actual_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = CO.Actual_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", "Active");
			CO.waitforload();
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			CO.waitforload();
			Col = CO.Actual_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1) {
				// Browser.WebTable.clickL("Assert", Row, Col);
				Result.takescreenshot("SIM Swap Initiation in Account Summary View");
				Result.fUpdateLog("SIM Swap Initiation in Account Summary View");
				CO.waitforload();
				Browser.WebButton.waitTillEnabled("Asset_SIMSwap");
				Browser.WebButton.click("Asset_SIMSwap");
			} else {
				Continue.set(false);

			}
			CO.scroll("Cancel_SIMSwap", "WebButton");
			CO.waitforload();
			CO.waitforload();
			Browser.WebButton.waittillvisible("S_Continue");

			String IDNum, IDType, FirstName;
			CO.waitforload();

			Result.takescreenshot("Contact Verification for SIM Swap Guided Journey");
			Result.fUpdateLog("Contact Verification for SIM Swap Guided Journey");
			IDNum = Browser.WebEdit.gettext("Contact_IDNumber");
			IDType = Browser.WebEdit.gettext("Contact_IDType");
			FirstName = Browser.WebEdit.gettext("FirstName");
			Browser.WebButton.click("S_Continue");

			CO.waitmoreforload();
			Browser.WebEdit.Set("New_SIM_SNO", New_SIM);

			Result.takescreenshot("Providing New SIM");
			Result.fUpdateLog("Providing New SIM");
			CO.waitforload();

			Browser.WebButton.waittillvisible("Submit_SimSwap");
			Browser.WebButton.click("Submit_SimSwap");

			CO.waitforload();

			CO.scroll("Save_Finish", "WebLink");

			Browser.WebEdit.Set("Comments", Comments + R.nextInt(1000));

			Browser.WebLink.waittillvisible("Save_Finish");
			Result.fUpdateLog("Finishing SIM Swap Guided Procees");
			Result.takescreenshot("Finishing SIM Swap Guided Procees");
			Browser.WebLink.click("Save_Finish");

			CO.waitforload();
			Result.takescreenshot("Finish Process");
			Browser.WebLink.click("Finish_Process");

			CO.waitforload();
			/*
			 * if (Browser.WebButton.exist("VFQ_LeftScroll"))
			 * Browser.WebButton.click("VFQ_LeftScroll");
			 * Browser.WebLink.click("VQ_Assert");
			 */

			CO.waitforload();
			CO.scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			CO.waitforload();

			Result.fUpdateLog("Initiating Assert Search for Contact Verification");
			Result.takescreenshot("Initiating Assert Search for Contact Verification");
			// Installed_Assert
			Col = CO.Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = CO.Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", "Active");
			Col = CO.Select_Cell("Assert", "Product");
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			CO.waitforload();
			Col = CO.Select_Cell("Assert", "Account");
			Assert_Row_Count = Browser.WebTable.getRowCount("Assert");

			if (Assert_Row_Count > 1) {
				// Browser.WebTable.clickL("Assert", Row, Col);

				CO.waitforload();
				Browser.WebButton.waitTillEnabled("Asset_SIMSwap");
				Browser.WebButton.click("Asset_SIMSwap");
			} else {
				Continue.set(false);
			}

			CO.waitforload();

			if (IDNum.equalsIgnoreCase(Browser.WebEdit.gettext("Contact_IDNumber"))
					& IDType.equalsIgnoreCase(Browser.WebEdit.gettext("Contact_IDType"))
					& FirstName.equalsIgnoreCase(Browser.WebEdit.gettext("FirstName"))) {
				Result.takescreenshot("Contact Verification is Successfull");
				Result.fUpdateLog("Contact Verification is Successfull");
			} else {
				Result.takescreenshot("Contact Verification Failure");
				Continue.set(false);
			}
			if (Continue.get()) {
				Test_OutPut += "";
				Result.takescreenshot("SIM Swap Guided_Journey is Successfull");
				Result.fUpdateLog("SIM Swap Guided_Journey is Successfull");
				Status = "PASS";
			} else {
				Test_OutPut += "SIM Swap Guided_Journey Failed" + ",";
				Result.takescreenshot("SIM Swap Guided_Journey Failed");
				Result.fUpdateLog("SIM Swap Guided_Journey Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();

		}
		Result.fUpdateLog("-----SIM swap Guided journey - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

}
