package Libraries;

import java.util.Random;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class Keyword_IPLC extends Driver {
	Common CO = new Common();
	Random R = new Random();
	Keyword_CRM KC = new Keyword_CRM();

	public String IPLC() {
	String Test_OutPut = "", Status = "";
		String Network = null;
		String VPN_Connection1 = null;
		String VPN_Connection2 = null;
		String VPN_Node_1 = null;
		String VPN_Node_2 = null;
		Result.fUpdateLog("------KEYMORD  IPLC ------");
		try {

		
			Network = pulldata("Network");
			VPN_Connection1 = pulldata("VPN Connection 1");
			VPN_Connection2 = pulldata("VPN Connection 2");
			VPN_Node_1 = pulldata("VPN Node 1");
			VPN_Node_2 = pulldata("VPN Node2");
	
			KC.Entp_AccountCreation();
			KC.Entp_ContactCreation();

			// sub Accou
			Result.takescreenshot("");
			CO.TabNavigator("Sub-Accounts");
			CO.waitforload();
			Browser.WebButton.click("Bill_acc_add");
			CO.waitforload();
			int Row = 2, Col, Col1;
			Col = CO.Select_Cell("Sub_Account", "Account");
			Browser.WebTable.click("Sub_Account", Row, Col);
			CO.waitforload();
			String ChildAccount1 = "ChildAccount1" + Utlities.randname();
			cDriver.get().findElement(By.name("Name")).sendKeys(ChildAccount1);
			CO.waitforload();
			Result.takescreenshot("");
			Col1 = CO.Select_Cell("Sub_Account", "CR Number");
			Browser.WebTable.click("Sub_Account", Row, Col1);
			CO.waitforload();
			Browser.WebTable.click("Sub_Account", Row, Col);
			CO.waitforload();
			// Browser.WebTable.clickL("Sub_Account", Row, Col);
			Result.takescreenshot("Childone");
			CO.waitforload();
			Childone_ContactCreation();
			CO.waitforload();

			// Account Hierarchy
				
			CO.TabNavigator("Account Hierarchy");
			Result.takescreenshot("Childone");
			Browser.WebButton.click("Account_Org_Search");
			CO.waitforload();
			int ColAc = CO.Actual_tab_Cell_th("Account_Org_Hierarchy", "Account");
			Browser.WebTable.SetDataE("Account_Org", 2,ColAc , "Name", ChildAccount1);
		//	Browser.WebButton.click("Account_Org_Go");
			ColAc = CO.Actual_tab_Cell_th("Account_Org_Hierarchy", "Class");
			Browser.WebTable.SetDataE("Account_Org", 2,ColAc , "Account_Type_Code", "Service");
			Browser.WebTable.click("Account_Org", 2,ColAc+1);

			
			//Result.takescreenshot("");
			String ParentAccount = Acc_Number.get();
			CO.Account_Search(ParentAccount);
			CO.waitforload();
			//Result.takescreenshot("");
			// Sub Account

			CO.TabNavigator("Sub-Accounts");
			CO.waitforload();
			Browser.WebButton.click("Bill_acc_add");
			CO.waitforload();
			Browser.WebTable.click("Sub_Account", Row, Col);
			CO.waitforload();

			String ChildAccount2 = "ChildAccount2" + Utlities.randname();
			cDriver.get().findElement(By.name("Name")).sendKeys(ChildAccount2);
			CO.waitforload();

			Browser.WebTable.click("Sub_Account", Row, Col1);
			Result.takescreenshot("");
			CO.waitforload();
			Browser.WebTable.click("Sub_Account", Row, Col);
			CO.waitforload();
			Result.takescreenshot("Childtwo");
			CO.waitforload();
			Childtwo_ContactCreation();
			CO.waitforload();

			// Account Hierarchy

			CO.TabNavigator("Account Hierarchy");
			Result.takescreenshot("Childtwo");
			Browser.WebButton.click("Account_Org_Search");
			ColAc = CO.Actual_tab_Cell_th("Account_Org_Hierarchy", "Account");
			Browser.WebTable.SetData("Account_Org", 2,ColAc , "Name", ChildAccount2);
		//	Browser.WebButton.click("Account_Org_Go");
			ColAc = CO.Actual_tab_Cell_th("Account_Org_Hierarchy", "Class");
			Browser.WebTable.SetData("Account_Org", 2,ColAc , "Account_Type_Code", "Service");
			Browser.WebTable.click("Account_Org", 2,ColAc+1);

			Result.takescreenshot("");
			CO.waitforload();
			// String
			// ParentAccount="12644131307",ChildAccount1="ETHERNET_1VFQA_Test_Chad",ChildAccount2="ETHERNET2_y";

			ParentAccount = Acc_Number.get();
			CO.Account_Search(ParentAccount);
			// Account Summary

			Result.takescreenshot("");
			CO.waitforload();

			// Billing Profile

			CO.scroll("Profile_Tab", "WebButton");

			CO.TabNavigator("Profiles");
			CO.waitforload();
			Browser.WebButton.click("Bill_Add");
			Result.takescreenshot("");
			Browser.WebEdit.waitTillEnabled("BP_Valid_Name");
			Browser.WebEdit.waittillvisible("BP_Valid_Name");

			Browser.WebButton.waittillvisible("Orders_Tab");
			SalesOrder();

			// Plan Selection
			int b = CO.Select_Cell("Line_Items", "Product");
			int ColSid = CO.Select_Cell("Line_Items", "Service Id");
			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + Network);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int a = 2;
			CO.waitforload();
			Browser.WebTable.SetDataE("Line_Items", a, b, "Product", Network);
			Browser.WebTable.click("Line_Items", a, ColSid);
			CO.waitforload();
			CO.waitforload();

			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + VPN_Connection1);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int a1 = 3;
			CO.waitforload();
			Browser.WebTable.SetDataE("Line_Items", a1, b, "Product", VPN_Connection1);
			Browser.WebTable.click("Line_Items", a1, ColSid);
			CO.waitforload();
			CO.waitforload();

			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + VPN_Connection2);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int a2 = 4;
			CO.waitforload();
			Browser.WebTable.SetDataE("Line_Items", a2, b, "Product", VPN_Connection2);
			Browser.WebTable.click("Line_Items", a2, ColSid);
			CO.waitforload();
			CO.waitforload();

			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + VPN_Node_1);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int a3 = 5;
			CO.waitforload();
			Browser.WebTable.SetDataE("Line_Items", a3, b, "Product", VPN_Node_1);
			Browser.WebTable.click("Line_Items", a3, ColSid);
			CO.waitforload();
			CO.waitforload();

			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + VPN_Node_2);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int a4 = 6;
			Browser.WebTable.SetDataE("Line_Items", a4, b, "Product", VPN_Node_2);
			Browser.WebTable.click("Line_Items", a4, ColSid);
			CO.waitforload();
			CO.waitforload();

			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + VPN_Node_2);

			
			
			CO.waitforload();
			CO.Text_Select("a", "Network Line Detail");
			CO.waitforload();
			CO.scroll("LI_New", "WebButton");

			int Col_S;
			// node 1
			Col_S = CO.Select_Cell("Line_Items", "Net Price");
			CO.scroll("LI_New", "WebButton");
			Browser.WebTable.click("Line_Items", 5, Col_S);
			Result.takescreenshot("");
			// Node service account



			CO.waitforload();
			Browser.WebLink.click("Node_Service_Acc");
			// CO.waitforload();
			CO.Popup_Selection("Pick_Account", "Account", "Name", ChildAccount1);
			CO.waitforload();

			// Node from address
			CO.waitforload();
			Browser.WebLink.click("Node_From_ADDR");
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();
			Result.takescreenshot("");

			// node 2

			CO.scroll("LI_New", "WebButton");
			Browser.WebTable.click("Line_Items", 6, Col_S);
			Result.takescreenshot("");

			// from acc

			CO.waitforload();
			Browser.WebLink.click("Node_Service_Acc");
			CO.waitforload();
			CO.Popup_Selection("Pick_Account", "Account", "Name", ChildAccount2);
			CO.waitforload();
			Result.takescreenshot("");

			// from add
			CO.waitforload();
			Browser.WebLink.click("Node_From_ADDR");
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();

			Result.takescreenshot("");

			// Access one

			// From node
			Browser.WebTable.click("Line_Items", 3, Col_S);
			CO.waitforload();
			Browser.WebLink.click("Access_From_Node");
			CO.waitforload();
			int Col_N2;
			Col_N2 = CO.Select_Cell("Pick_network", "Node");
			Browser.WebTable.click("Pick_network", 3, Col_N2);
			CO.waitforload();
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			Result.takescreenshot("");
			CO.waitforload();

			// from acc
			CO.waitforload();
			Browser.WebLink.click("Access_From_Service_Acc");
			CO.waitforload();
			CO.Popup_Selection("Pick_Account", "Account", "Name", ChildAccount1);
			CO.waitforload();
			Result.takescreenshot("");

			// from add
			CO.waitforload();
			Browser.WebLink.click("Access_From_Service_Addr");
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();
			Result.takescreenshot("");
			// access two

			CO.scroll("LI_New", "WebButton");
			Browser.WebTable.click("Line_Items", 4, Col_S);
			Result.takescreenshot("");
			// From node

			CO.waitforload();
			Browser.WebLink.click("Access_From_Node");

			int Col_N;
			Col_N = CO.Select_Cell("Pick_network", "Node");
			Browser.WebTable.click("Pick_network", 2, Col_N);
			CO.waitforload();
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			Result.takescreenshot("");
			CO.waitforload();

			// from acc

			CO.waitforload();
			Browser.WebLink.click("Access_From_Service_Acc");
			CO.waitforload();
			CO.Popup_Selection("Pick_Account", "Account", "Name", ChildAccount2);
			CO.waitforload();

			// from add

			Browser.WebLink.click("Access_From_Service_Addr");
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();
			Result.takescreenshot("");
			
			// Connection1 Customize
	
			// node 1
			Col_S = CO.Select_Cell("Line_Items", "Net Price");
			
			if (!(getdata("IPLCCharges").equals(""))
					|| !(getdata("IPLCFeatures").equals(""))
					|| !(getdata("IPLCSLA").equals(""))
					||!(getdata("IPLCServices").equals(""))) {
				

				Browser.WebTable.click("Line_Items", 3, Col_S);
				CO.waitforload();
				CO.Text_Select("span", "Customize");
				CO.waitforload();
				if (!(getdata("IPLCFeatures").equals(""))) {

					String Addon =getdata("IPLCFeatures");
					CO.Text_Select("a","IPLC Features");
					CO.waitforload();
					CO.Radio_Select1(Addon);
					CO.waitforload();
				
					
				}

				if (!(getdata("IPLCCharges").equals(""))) {
					

					String Addon =getdata("IPLCCharges");
					CO.Text_Select("a","IPLC Charges");
					CO.waitforload();
					CO.Radio_Select1(Addon);
					CO.waitforload();
									
					
				}
			
				if (!(getdata("IPLCSLA").equals(""))) {

					String Addon =getdata("IPLCSLA");
					CO.Text_Select("a","IPLC SLA");
					CO.waitforload();
					CO.Radio_Select1(Addon);
					CO.waitforload();
					CO.waitforload();
					
				}
				if (!(getdata("IPLCServices").equals(""))) {

					String Addon =getdata("IPLCServices");
					CO.Text_Select("a","IPLC Services");
					CO.waitforload();
					CO.Radio_Select1(Addon);
					CO.waitforload();
					CO.waitforload();
					
				}
				
				
				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");

				CO.waitforload();

			}
		// Connection2 Customize
			
		

			if (!(getdata("IPLCCharges").equals(""))
					|| !(getdata("IPLCFeatures").equals(""))
					|| !(getdata("IPLCSLA").equals(""))
					|| !(getdata("IPLCServices").equals(""))) {
				

				Browser.WebTable.click("Line_Items", 4, Col_S);
				CO.waitforload();
				CO.Text_Select("span", "Customize");
				CO.waitforload();
				if (!(getdata("IPLCFeatures").equals(""))) {

					String Addon =getdata("IPLCFeatures");
					CO.Text_Select("a","IPLC Features");
					CO.waitforload();
					CO.Radio_Select1(Addon);
					CO.waitforload();
				
					
				}

				if (!(getdata("IPLCCharges").equals(""))) {
					

					String Addon =getdata("IPLCCharges");
					CO.Text_Select("a","IPLC Charges");
					CO.waitforload();
					CO.Radio_Select1(Addon);
					CO.waitforload();
									
					
				}
			
				if (!(getdata("IPLCSLA").equals(""))) {

					String Addon =getdata("IPLCSLA");
					CO.Text_Select("a","IPLC SLA");
					CO.waitforload();
					CO.Radio_Select1(Addon);
					CO.waitforload();
					CO.waitforload();
					
				}
				if (!(getdata("IPLCServices").equals(""))) {

					String Addon =getdata("IPLCServices");
					CO.Text_Select("a","IPLC Services");
					CO.waitforload();
					CO.Radio_Select1(Addon);
					CO.waitforload();
					CO.waitforload();
					
				}
				
				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");

				CO.waitforload();

			}


			
			// Node 1 Customize

			if (!(getdata("IPLCNodeFeatures").equals(""))
					|| !(getdata("IPLCNodeCharge").equals(""))) {
				

				Browser.WebTable.click("Line_Items", 5, Col_S);
				CO.waitforload();
				CO.Text_Select("span", "Customize");
				CO.waitforload();
				

				if (!(getdata("IPLCNodeFeatures").equals(""))) {

					String Addon =getdata("IPLCNodeFeatures");
					CO.Radio_Select1(Addon);
					CO.scroll("Ecofig_Add", "WebButton");
					CO.waitforload();
					cDriver.get().findElement(By.xpath("//div[text()='Item']//following::select[1]")).click();
					Actions cpe = new Actions(cDriver.get());
					cpe.sendKeys(Keys.ARROW_DOWN).build().perform();
					cpe.sendKeys(Keys.ENTER).build().perform();

					cDriver.get().findElement(By.xpath("//div[text()='Qty']//following::input[1]")).sendKeys("1");

					CO.waitforload();
					Result.takescreenshot("");

					CO.Text_Select("button", "Add Item");
					
					
				}
				if (!(getdata("IPLCNodeCharge").equals(""))) {

					String Addon =getdata("IPLCNodeCharge");
					CO.Text_Select("a","IPLC Node Charges");
					CO.waitforload();
					CO.Radio_Select1(Addon);
					CO.waitforload();
				
					
				}
							
				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");

				CO.waitforload();

			}

			
		// Node 2 Customize
			if (!(getdata("IPLCNodeFeatures").equals(""))
					|| !(getdata("IPLCNodeCharge").equals(""))) {
				


				Browser.WebTable.click("Line_Items", 6, Col_S);
				CO.waitforload();
				CO.Text_Select("span", "Customize");
				CO.waitforload();
				
				

				if (!(getdata("IPLCNodeFeatures").equals(""))) {

					String Addon =getdata("IPLCNodeFeatures");
					CO.Radio_Select1(Addon);
					CO.scroll("Ecofig_Add", "WebButton");
					CO.waitforload();
					cDriver.get().findElement(By.xpath("//div[text()='Item']//following::select[1]")).click();
					Actions cpe = new Actions(cDriver.get());
					cpe.sendKeys(Keys.ARROW_DOWN).build().perform();
					cpe.sendKeys(Keys.ENTER).build().perform();

					cDriver.get().findElement(By.xpath("//div[text()='Qty']//following::input[1]")).sendKeys("1");

					CO.waitforload();
					Result.takescreenshot("");

					CO.Text_Select("button", "Add Item");
					
					
				}
				if (!(getdata("IPLCNodeCharge").equals(""))) {

					String Addon =getdata("IPLCNodeCharge");
					CO.Text_Select("a","IPLC Node Charges");
					CO.waitforload();
					CO.Radio_Select1(Addon);
					CO.waitforload();
				
					
				}
					
								
				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");

				CO.waitforload();

			}
			
			CO.Text_Select("a", "Appointments");
			CO.waitforload();
			CO.scroll("Validate", "WebButton");
			Browser.WebEdit.waittillvisible("Enterprise_credit_limit");
			CO.waitforload();
			Browser.WebEdit.Set("Enterprise_credit_limit", "1000");
			Result.takescreenshot("");
			CO.waitforload();


			Test_OutPut += KC.OrderSubmission().split("@@")[1];

		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}

		Result.fUpdateLog("------Ethernet Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String Childone_ContactCreation() {
		String Test_OutPut = "", Status = "";
		String Last_Name = null;
		Result.fUpdateLog("------Contact Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				String Address;
				if (!(getdata("Address").equals(""))) {
					Address = getdata("Address");
				} else if (!(getdata("Kahramaa_ID1").equals(""))) {
					Address = "Kar#" + getdata("Kahramaa_ID1");
				} else if (!(pulldata("Kahramaa_ID1").equals(""))) {
					Address = "Kar#" + pulldata("Kahramaa_ID1");
				} else {
					Address = pulldata("Address");
				}

				do {
					Browser.WebButton.click("Address_Tab");
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Add_Address"));
				Browser.WebButton.waittillvisible("Add_Address");

				CO.waitforload();
				if (!(Address.equals(""))) {

					CO.waitforobj("Add_Address", "WebButton");
					// Browser.WebButton.waittillvisible("Add_Address");
					Browser.WebButton.click("Add_Address");
					CO.waitforload();
					// Search for Specific Address
					CO.waitforobj("Popup_Go", "WebButton");
					CO.scroll("Popup_Go", "WebButton");

					if (Address.contains("Kar#")) {
						Browser.ListBox.select("PopupQuery_List", "Kahramaa ID");
						Browser.WebEdit.Set("PopupQuery_Search", Address.split("#")[1]);
					} else {
						Browser.ListBox.select("PopupQuery_List", "Address Line 1");
						Browser.WebEdit.Set("PopupQuery_Search", Address);
					}
					CO.waitforload();
					Browser.WebButton.click("Popup_Go");

					CO.waitforload();
					CO.scroll("Add_OK", "WebButton");
					CO.waitmoreforload();
					Browser.WebButton.click("Add_OK");
					/*
					 * do { Result.fUpdateLog("Page Loading....."); } while
					 * (Browser.WebButton.waitTillEnabled("Add_OK"));
					 */
					CO.waitforload();
					// Browser.WebButton.waittillvisible("Create_A/c");
				} else {
					String[] stat_add = AddressCreation().split("@@");
					Status = stat_add[0];
					Address = stat_add[1].split(",")[0];
				}
				Result.takescreenshot("Address Selected : " + Address);
				Result.fUpdateLog("Address Selected : " + Address);
				CO.waitforload();
				CO.waitforload();
				int x = 0;
				do {
					CO.TabNavigator("Contacts");
					CO.waitforload();
					/*
					 * if (Browser.WebEdit.waitTillEnabled("Contact_Valid_Name")) { j = 0; break; }
					 */
				} while (!Browser.WebEdit.waitTillEnabled("Contact_Valid_Name"));
				Browser.WebEdit.waittillvisible("Contact_Valid_Name");

				CO.waitforload();

				x = Browser.WebTable.getRowCount("Acc_Contact");
				if (x == 1) {
					Browser.WebButton.waittillvisible("Acc_Add_Contact");
					Browser.WebButton.click("Acc_Add_Contact");
				}
				int Row = 2, Col;

				Col = CO.Select_Cell("Acc_Contact", "First Name");
				if (!(getdata("FirstName").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", getdata("FirstName"));
				} else if (!(pulldata("FirstName").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", pulldata("FirstName"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", Utlities.randname());
				}

				Col = CO.Select_Cell("Acc_Contact", "Last Name");
				if (!(getdata("LastName").equals(""))) {
					Last_Name = getdata("LastName");
				} else if (!(pulldata("LastName").equals(""))) {
					Last_Name = pulldata("LastName") + R.nextInt(1000);
				} else {
					Last_Name = Utlities.randname();
				}
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Last_Name", Last_Name);

				Col = CO.Select_Cell("Acc_Contact", "Mr/Ms");
				if (!(getdata("Mr/Ms").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "M_M", getdata("Mr/Ms"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "M_M", pulldata("Mr/Ms"));
				}
				Col = CO.Select_Cell("Acc_Contact", "Email");
				if (!(getdata("Email").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Email_Address", getdata("Email"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Email_Address", pulldata("Email"));
				}

				Col = CO.Select_Cell("Acc_Contact", "Date of Birth");
				if (!(getdata("DOB").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_DOB", getdata("DOB"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_DOB", pulldata("DOB"));
				}

				Col = CO.Select_Cell("Acc_Contact", "ID Expiration Date");
				if (!(getdata("IDExpiryDate").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Expiration_Date",
							getdata("IDExpiryDate"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Expiration_Date",
							pulldata("IDExpiryDate"));
				}

				Col = CO.Select_Cell("Acc_Contact", "ID Number");
				if (!(getdata("IDNumber").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Number", getdata("IDNumber"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Number",
							pulldata("IDNumber") + R.nextInt(100000));

				}

				// CO.scroll("Contact_ACC", "WebTable");

				// Col = CO.Select_Cell("Acc_Contact", "ID Type");
				Col++;
				if (!(getdata("IDType").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Type", getdata("IDType"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Type", pulldata("IDType"));
				}

				Col++;
				// Col = CO.Select_Cell("Acc_Contact", "Mobile Phone #");
				if (!(getdata("MobilePhone").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Cellular_Phone__", getdata("MobilePhone"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Cellular_Phone__", pulldata("MobilePhone"));
				}

				Col++;
				// Col = CO.Select_Cell("Acc_Contact", "Nationality");
				if (!(getdata("Nationality").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_Nationality", getdata("Nationality"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_Nationality", pulldata("Nationality"));
				}

				Col = Col + 4;
				// Col = CO.Select_Cell("Acc_Contact", "Gender");
				if (!(getdata("Gender").equals(""))) {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQA_M_F", getdata("Gender"));
				} else {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQA_M_F", pulldata("Gender"));
				}

				Col = CO.Select_Cell("Acc_Contact", "Preferred Language");
				if (!(getdata("PrefLang").equals(""))) {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQ_Preferred_Language", getdata("PrefLang"));
				} else {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQ_Preferred_Language", pulldata("PrefLang"));
				}

				CO.waitforload();
				CO.scroll("Account360", "WebButton");
				Browser.WebButton.click("Address_Tab");
				if (CO.isAlertExist())
					if (CO.isAlertExist())
						Browser.WebButton.click("Address_Tab");

				CO.ToWait();
				if (Continue.get()) {
					Result.takescreenshot("Contact Ceated : " + Last_Name);
					// Utlities.StoreValue("LastName", Last_Name);
					// Utlities.StoreValue("Address", Address);
					Test_OutPut += "Address : " + Address + ",";
					Test_OutPut += "Contact : " + Last_Name + ",";
					Status = "PASS";
				} else {
					Test_OutPut += "Create_A/c button not exist" + ",";
					Result.takescreenshot("Create_A/c button not exist");
					Result.fUpdateLog("Create_A/c button not exist");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Contact Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String Childtwo_ContactCreation() {
		String Test_OutPut = "", Status = "";
		String Last_Name = null;
		Result.fUpdateLog("------Contact Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				String Address;
				if (!(getdata("Address").equals(""))) {
					Address = getdata("Address");
				} else if (!(getdata("Kahramaa_ID2").equals(""))) {
					Address = "Kar#" + getdata("Kahramaa_ID2");
				} else if (!(pulldata("Kahramaa_ID2").equals(""))) {
					Address = "Kar#" + pulldata("Kahramaa_ID2");
				} else {
					Address = pulldata("Address");
				}

				do {
					Browser.WebButton.click("Address_Tab");
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Add_Address"));
				Browser.WebButton.waittillvisible("Add_Address");

				CO.waitforload();
				if (!(Address.equals(""))) {

					CO.waitforobj("Add_Address", "WebButton");
					// Browser.WebButton.waittillvisible("Add_Address");
					Browser.WebButton.click("Add_Address");
					CO.waitforload();
					// Search for Specific Address
					CO.waitforobj("Popup_Go", "WebButton");
					CO.scroll("Popup_Go", "WebButton");

					if (Address.contains("Kar#")) {
						Browser.ListBox.select("PopupQuery_List", "Kahramaa ID");
						Browser.WebEdit.Set("PopupQuery_Search", Address.split("#")[1]);
					} else {
						Browser.ListBox.select("PopupQuery_List", "Address Line 1");
						Browser.WebEdit.Set("PopupQuery_Search", Address);
					}
					CO.waitforload();
					Browser.WebButton.click("Popup_Go");

					CO.waitforload();
					CO.scroll("Add_OK", "WebButton");
					CO.waitmoreforload();
					Browser.WebButton.click("Add_OK");
					/*
					 * do { Result.fUpdateLog("Page Loading....."); } while
					 * (Browser.WebButton.waitTillEnabled("Add_OK"));
					 */
					CO.waitforload();
					// Browser.WebButton.waittillvisible("Create_A/c");
				} else {
					String[] stat_add = AddressCreation().split("@@");
					Status = stat_add[0];
					Address = stat_add[1].split(",")[0];
				}
				Result.takescreenshot("Address Selected : " + Address);
				Result.fUpdateLog("Address Selected : " + Address);
				CO.waitforload();
				CO.waitforload();

				int x = 0;
				do {
					CO.TabNavigator("Contacts");
					CO.waitforload();
					/*
					 * if (Browser.WebEdit.waitTillEnabled("Contact_Valid_Name")) { j = 0; break; }
					 */
				} while (!Browser.WebEdit.waitTillEnabled("Contact_Valid_Name"));
				Browser.WebEdit.waittillvisible("Contact_Valid_Name");

				CO.waitforload();

				x = Browser.WebTable.getRowCount("Acc_Contact");
				if (x == 1) {
					Browser.WebButton.waittillvisible("Acc_Add_Contact");
					Browser.WebButton.click("Acc_Add_Contact");
				}

				int Row = 2, Col;
				Col = CO.Select_Cell("Acc_Contact", "First Name");
				if (!(getdata("FirstName").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", getdata("FirstName"));
				} else if (!(pulldata("FirstName").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", pulldata("FirstName"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", Utlities.randname());
				}

				Col = CO.Select_Cell("Acc_Contact", "Last Name");
				if (!(getdata("LastName").equals(""))) {
					Last_Name = getdata("LastName");
				} else if (!(pulldata("LastName").equals(""))) {
					Last_Name = pulldata("LastName") + R.nextInt(1000);
				} else {
					Last_Name = Utlities.randname();
				}
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Last_Name", Last_Name);

				Col = CO.Select_Cell("Acc_Contact", "Mr/Ms");
				if (!(getdata("Mr/Ms").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "M_M", getdata("Mr/Ms"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "M_M", pulldata("Mr/Ms"));
				}

				// Browser.WebTable.SetData("Acc_Contact", 2, Col, "Job_Title",getdata(""));

				/*
				 * Col = CO.Select_Cell("Acc_Contact", "Work Phone #");
				 * if(!(getdata("Cont_WorkPhone").equals(""))) {
				 * Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Work_Phone__",
				 * getdata("Cont_WorkPhone")); }else { Browser.WebTable.SetDataE("Acc_Contact",
				 * Row, Col, "Work_Phone__", "97498780980"); }
				 */

				Col = CO.Select_Cell("Acc_Contact", "Email");
				if (!(getdata("Email").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Email_Address", getdata("Email"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Email_Address", pulldata("Email"));
				}

				Col = CO.Select_Cell("Acc_Contact", "Date of Birth");
				if (!(getdata("DOB").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_DOB", getdata("DOB"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_DOB", pulldata("DOB"));
				}

				Col = CO.Select_Cell("Acc_Contact", "ID Expiration Date");
				if (!(getdata("IDExpiryDate").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Expiration_Date",
							getdata("IDExpiryDate"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Expiration_Date",
							pulldata("IDExpiryDate"));
				}

				Col = CO.Select_Cell("Acc_Contact", "ID Number");
				if (!(getdata("IDNumber").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Number", getdata("IDNumber"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Number",
							pulldata("IDNumber") + R.nextInt(100000));

				}

				// CO.scroll("Contact_ACC", "WebTable");

				// Col = CO.Select_Cell("Acc_Contact", "ID Type");
				Col++;
				if (!(getdata("IDType").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Type", getdata("IDType"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Type", pulldata("IDType"));
				}

				Col++;
				// Col = CO.Select_Cell("Acc_Contact", "Mobile Phone #");
				if (!(getdata("MobilePhone").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Cellular_Phone__", getdata("MobilePhone"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Cellular_Phone__", pulldata("MobilePhone"));
				}

				Col++;
				// Col = CO.Select_Cell("Acc_Contact", "Nationality");
				if (!(getdata("Nationality").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_Nationality", getdata("Nationality"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_Nationality", pulldata("Nationality"));
				}
				CO.waitforload();
				Col = Col + 4;
				// Col = CO.Select_Cell("Acc_Contact", "Gender");
				if (!(getdata("Gender").equals(""))) {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQA_M_F", getdata("Gender"));
				} else {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQA_M_F", pulldata("Gender"));
				}

				Col = CO.Select_Cell("Acc_Contact", "Preferred Language");
				if (!(getdata("PrefLang").equals(""))) {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQ_Preferred_Language", getdata("PrefLang"));
				} else {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQ_Preferred_Language", pulldata("PrefLang"));
				}

				CO.waitforload();
				CO.scroll("Account360", "WebButton");
				Browser.WebButton.click("Address_Tab");
				if (CO.isAlertExist())
					if (CO.isAlertExist())
						Browser.WebButton.click("Address_Tab");

				CO.ToWait();
				if (Continue.get()) {
					Result.takescreenshot("Contact Ceated : " + Last_Name);
					// Utlities.StoreValue("LastName", Last_Name);
					// Utlities.StoreValue("Address", Address);
					Test_OutPut += "Address : " + Address + ",";
					Test_OutPut += "Contact : " + Last_Name + ",";
					Status = "PASS";
				} else {
					Test_OutPut += "Create_A/c button not exist" + ",";
					Result.takescreenshot("Create_A/c button not exist");
					Result.fUpdateLog("Create_A/c button not exist");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Contact Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String AddressCreation() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Address Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				// Browser.WebLink.waittillvisible("Acc_address");
				CO.waitforload();
				if (Browser.WebLink.exist("Acc_address")) {
					Result.fUpdateLog("Proceeding Consumer Address Creation");
					CO.TabNavigator("Addresses");
					CO.waitforload();
				} else if (Browser.WebButton.exist("Address_Tab")) {
					Result.fUpdateLog("Proceeding Enterprise Address Creation");
					CO.TabNavigator("Addresses");
					CO.waitmoreforload();
				}

				CO.waitforload();
				int Row = 2, Col;
				CO.scroll("Acc_Add_New", "WebButton");
				Browser.WebButton.click("Acc_Add_New");

				String Add1 = null, Add2 = null;
				Col = CO.Select_Cell("Address", "Address Line 1");
				if (!(getdata("Add_AddressLine1").equals(""))) {
					Add1 = getdata("Add_AddressLine1");
				} else if (!(pulldata("Add_AddressLine1").equals(""))
						&& !(pulldata("Add_AddressLine1").equalsIgnoreCase("Autogenerated"))) {
					Add1 = pulldata("Add_AddressLine1");
				} else if (pulldata("Add_AddressLine1").equalsIgnoreCase("Autogenerated")) {
					Add1 = Utlities.randname();
				}
				CO.waitforload();
				Browser.WebTable.SetDataE("Address", Row, Col, "Street_Address", Add1);
				Utlities.StoreValue("Address line1", Add1);

				Col = CO.Select_Cell("Address", "Address Line 2");
				if (!(getdata("Add_AddressLine2").equals(""))) {
					Add2 = getdata("Add_AddressLine2");
				} else if (!(pulldata("Add_AddressLine2").equals(""))
						&& !(pulldata("Add_AddressLine2").equalsIgnoreCase("Autogenerated"))) {
					Add2 = pulldata("Add_AddressLine2");
				} else if (pulldata("Add_AddressLine2").equalsIgnoreCase("Autogenerated")) {
					Add2 = Utlities.randname();
				}
				Browser.WebTable.SetDataE("Address", Row, Col, "Street_Address_2", Add2);
				Utlities.StoreValue("Address line2", Add2);

				Col = CO.Select_Cell("Address", "PO Box");
				if (!(getdata("Add_POBox").equals(""))) {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_PO_Box", getdata("Add_POBox"));
				} else {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_PO_Box", pulldata("Add_POBox"));
				}

				Col = CO.Select_Cell("Address", "Postal Code");
				if (!(getdata("Add_Zip").equals(""))) {
					Browser.WebTable.SetDataE("Address", Row, Col, "Postal_Code", getdata("Add_PostalCode"));
				} else {
					Browser.WebTable.SetDataE("Address", Row, Col, "Postal_Code", pulldata("Add_PostalCode"));
				}

				Col = CO.Select_Cell("Address", "Kahramaa ID");
				if (!(getdata("Add_Kahramaa_ID").equals(""))) {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_Kahramaa_ID", getdata("Add_Kahramaa_ID"));
				} else if (pulldata("Add_Kahramaa_ID").equalsIgnoreCase("Autogenerated")) {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_Kahramaa_ID", "1" + R.nextInt(10000000));
				} else {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_Kahramaa_ID", pulldata("Add_Kahramaa_ID"));
				}

				CO.waitforload();
				int Row_Count = Browser.WebTable.getRowCount("Address");
				// Browser.WebLink.waittillvisible("Acc_Contacts");
				CO.waitforload();

				if (Continue.get() && Row_Count > 1) {
					Test_OutPut += Add1 + ",";
					Status = "PASS";
				} else {
					Result.fUpdateLog("Create_A/c button not exist");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Continue.set(false);
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Address Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String SalesOrder() {
		String Test_OutPut = "", Status = "";
		String Order_No = null;

		Result.fUpdateLog("------Sales Order Event Details------");
		try {

			// int j = 1;
			do {
				CO.TabNavigator("Orders");
				CO.waitforload();
				if (CO.isAlertExist())
					CO.TabNavigator("Orders");

			} while (!Browser.WebTable.waitTillEnabled("Order_Table"));
			Browser.WebTable.waittillvisible("Order_Table");

			int Row = 2, Col, Col_new;
			Browser.WebButton.waitTillEnabled("Order_New");
			CO.scroll("Order_New", "WebButton");
			Browser.WebButton.click("Order_New");

			CO.waitforload();
			Col_new = CO.Actual_Cell("Order_Table", "Status");
			boolean flag = true;

			do {
				String orderstatus = Browser.WebTable.getCellData_title("Order_Table", 2, Col_new);
				if (orderstatus.equalsIgnoreCase("Pending")) {
					flag = false;
					break;
				}
			} while (flag);

			Col = CO.Get_Col("Order_Table", Row, "Sales Order");
			Browser.WebTable.click("Order_Table", Row, Col);
			Order_No = Browser.WebTable.getCellData("Order_Table", 2, (Col - 1));

			String OD_Date;
			Col_new = CO.Actual_Cell("Order_Table", "Order Date");
			Browser.WebTable.click("Order_Table", Row, Col_new);
			OD_Date = Browser.WebTable.getCellData_title("Order_Table", 2, Col_new);
			String[] Date = OD_Date.split(" ")[0].split("/");
			OrderDate.set((Date[1] + "-" + Date[0] + "-" + Date[2]));

			Browser.WebTable.click("Order_Table", Row, (Col - 1));
			SalesOrder_No.set(Order_No);
			do {
				CO.waitforload();
			} while (!Browser.WebLink.waitTillEnabled("Line_Items"));
			Browser.WebLink.waittillvisible("Line_Items");
			Browser.WebLink.click("Line_Items");
			if (Browser.WebLink.exist("SalesOd_Expand")) {
				Browser.WebLink.click("SalesOd_Expand");
				CO.waitforload();
			}
			CO.waitforload();
			Browser.WebButton.waittillvisible("LI_New");

			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				// Utlities.StoreValue("Sales_OrderNO", SalesOrder_No.get());
				Test_OutPut += "Order_No : " + Order_No + ",";
				// Utlities.StoreValue("Order_Creation_Date", OrderDate.get());
				Result.takescreenshot("Sales Order Created Order_No : " + Order_No);
			} else {
				Status = "FAIL";
				Test_OutPut += "Sales Order Creation Failed" + ",";
				Result.takescreenshot("Sales Order Creation Failed");
				Result.fUpdateLog("Sales Order Creation Failed");
			}

		} catch (Exception e) {
			Status = "FAIL";
			Continue.set(false);
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();

		}
		Result.fUpdateLog("------Sales Order Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
