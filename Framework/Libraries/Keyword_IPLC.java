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
		String VPN_Node_1 = null;
		String VPN_Node_2 = null;
		String Virtual_Connection1 = null;
		String Virtual_Connection2 = null;

		Result.fUpdateLog("------IPLC   ------");
		try {

			String IPLC_Node_Features;
			String IPLC_Feature;
			String IPLC_Service;
			String IPLC_Charges;

			if (!(getdata("Network").equals(""))) {
				Network = getdata("Network");
			} else {
				Network = pulldata("Network");
			}

			if (!(getdata("VPN Node 1").equals(""))) {
				VPN_Node_1 = getdata("VPN Node 1");
			} else {
				VPN_Node_1 = pulldata("VPN Node 1");
			}

			if (!(getdata("VPN Node2").equals(""))) {
				VPN_Node_2 = getdata("VPN Node2");
			} else {
				VPN_Node_2 = pulldata("VPN Node2");
			}

			if (!(getdata("Virtual Connection1").equals(""))) {
				Virtual_Connection1 = getdata("Virtual Connection1");
			} else {
				Virtual_Connection1 = pulldata("Virtual Connection1");
			}

			if (!(getdata("Virtual Connection2").equals(""))) {
				Virtual_Connection2 = getdata("Virtual Connection2");
			} else {
				Virtual_Connection2 = pulldata("Virtual Connection2");
			}

			if (!(getdata("IPLC Node Features").equals(""))) {
				IPLC_Node_Features = getdata("IPLC Node Features");
			} else {
				IPLC_Node_Features = pulldata("IPLC Node Features");
			}

			if (!(getdata("IPLC Features").equals(""))) {
				IPLC_Feature = getdata("IPLC Features");
			} else {
				IPLC_Feature = pulldata("IPLC Features");
			}

			if (!(getdata("IPLC Service").equals("IPLC Service"))) {
				IPLC_Service = getdata("IPLC Service");
			} else {
				IPLC_Service = pulldata("IPLC Service");
			}

			if (!(getdata("IPLC Charges").equals(""))) {
				IPLC_Charges = getdata("IPLC Charges");
			} else {
				IPLC_Charges = pulldata("IPLC Charges");
			}

			Parent_AccountCreation();
			CO.waitforload();

			Parent_ContactCreation();

			// sub Account
			Result.takescreenshot("");
			CO.Link_Select("Sub-Accounts");
			CO.waitforload();
			Browser.WebButton.click("Bill_acc_add");
			CO.waitforload();
			int Row = 2, Col, Col1;
			Col = CO.Select_Cell("Sub_Account", "Account");

			Browser.WebTable.click("Sub_Account", Row, Col);
			CO.waitforload();
			cDriver.get().findElement(By.name("Name")).sendKeys("Childone", Utlities.randname());
			CO.waitforload();
			Result.takescreenshot("");
			Col1 = CO.Select_Cell("Sub_Account", "CR Number");
			Browser.WebTable.click("Sub_Account", Row, Col1);

			CO.waitforload();
			Browser.WebTable.click("Sub_Account", Row, Col);
			CO.waitforload();
			Browser.WebTable.clickL("Sub_Account", Row, Col);
			Result.takescreenshot("Childone");
			CO.waitforload();
			Childone_ContactCreation();
			CO.waitforload();

			// Account Hierarchy

			CO.TabNavigator("Account Hierarchy");
			Result.takescreenshot("");
			CO.waitforload();
			int col23;
			col23 = CO.Select_Cell("Account_Org", "Account");
			Browser.WebTable.clickL("Account_Org", 3, col23);
			CO.waitforload();
			Result.takescreenshot("");

			// Sub Account

			CO.Link_Select("Sub-Accounts");
			CO.waitforload();
			Browser.WebButton.click("Bill_acc_add");
			CO.waitforload();

			Browser.WebTable.click("Sub_Account", Row, Col);
			CO.waitforload();
			cDriver.get().findElement(By.name("Name")).sendKeys("Childtwo", Utlities.randname());
			Browser.WebTable.click("Sub_Account", Row, Col1);
			CO.waitforload();
			Browser.WebTable.click("Sub_Account", Row, Col1);
			Result.takescreenshot("");
			CO.waitforload();
			cDriver.get().findElement(By.id("1_VFQA_CR_Number")).sendKeys("116768");
			CO.waitforload();
			Browser.WebTable.click("Sub_Account", Row, Col);
			CO.waitforload();
			Browser.WebTable.clickL("Sub_Account", Row, Col);
			Result.takescreenshot("Childtwo");
			CO.waitforload();
			Childtwo_ContactCreation();
			CO.waitforload();

			// Account Hierarchy

			CO.TabNavigator("Account Hierarchy");
			Result.takescreenshot("");
			CO.waitforload();
			int row = 4, col;
			col = CO.Select_Cell("Account_Org", "Account");
			Browser.WebTable.clickL("Account_Org", row, col);
			CO.waitforload();

			// Account Summary

			CO.TabNavigator("Account Summary");
			Result.takescreenshot("");
			CO.waitforload();

			// Billing Profile

			CO.scroll("Profile_Tab", "WebButton");

			Browser.WebButton.click("Profile_Tab");
			CO.waitforload();
			Browser.WebButton.click("Bill_Add");
			Result.takescreenshot("");
			Browser.WebEdit.waitTillEnabled("BP_Valid_Name");
			Browser.WebEdit.waittillvisible("BP_Valid_Name");

			Browser.WebButton.waittillvisible("Orders_Tab");
			SalesOrder();

			// Plan Selection

			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + Network);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int a = 2, b;
			b = CO.Select_Cell("Line_Items", "Product");
			CO.waitforload();
			Browser.WebTable.SetDataE("Line_Items", a, b, "Product", Network);
			Browser.WebTable.click("Line_Items", a, b);
			CO.waitforload();

			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + VPN_Node_1);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int a3 = 3, b3;
			b3 = CO.Select_Cell("Line_Items", "Product");
			CO.waitforload();
			Browser.WebTable.SetDataE("Line_Items", a3, b3, "Product", VPN_Node_1);
			Browser.WebTable.click("Line_Items", a3, b3 + 1);
			CO.waitforload();

			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + VPN_Node_2);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int a4 = 4, b4;
			b4 = CO.Select_Cell("Line_Items", "Product");
			CO.waitforload();
			Browser.WebTable.SetDataE("Line_Items", a4, b4, "Product", VPN_Node_2);
			Browser.WebTable.click("Line_Items", a4, b4 + 1);
			CO.waitforload();

			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + VPN_Node_2);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int a5 = 5, b5;
			b5 = CO.Select_Cell("Line_Items", "Product");
			CO.waitforload();
			Browser.WebTable.SetDataE("Line_Items", a5, b5, "Product", Virtual_Connection1);
			Browser.WebTable.click("Line_Items", a5, b5 + 1);
			CO.waitforload();

			CO.scroll("LI_New", "WebButton");
			Result.takescreenshot(" Plan Selection " + VPN_Node_2);
			Browser.WebButton.click("LI_New");
			CO.waitforload();
			int a16 = 6, b16;
			b16 = CO.Select_Cell("Line_Items", "Product");
			CO.waitforload();
			Browser.WebTable.SetDataE("Line_Items", a16, b16, "Product", Virtual_Connection2);
			Browser.WebTable.click("Line_Items", a16, b16 + 1);
			CO.waitforload();

			// Node 1 Customize

			int Col_S;
			Col_S = CO.Select_Cell("Line_Items", "Action");

			Browser.WebTable.click("Line_Items", 3, Col_S);
			CO.waitforload();
			CO.Text_Select("span", "Customize");
			CO.waitforload();
			Result.takescreenshot("");
			CO.Radio_Select1(IPLC_Node_Features);
			CO.waitforload();

			cDriver.get().findElement(By.xpath("//div[text()=\"Item\"]//following::select[1]")).click();
			Actions cpe = new Actions(cDriver.get());
			cpe.sendKeys(Keys.ARROW_DOWN).build().perform();
			cpe.sendKeys(Keys.ENTER).build().perform();

			cDriver.get().findElement(By.xpath("//div[text()=\"Qty\"]//following::input[1]")).sendKeys("1");
			CO.waitforload();

			CO.Text_Select("button", "Add Item");
			CO.waitforload();
			Result.takescreenshot("");

			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");

			CO.waitforload();

			// Node 2 Customize

			Browser.WebTable.click("Line_Items", 4, Col_S);
			CO.waitforload();
			CO.Text_Select("span", "Customize");
			CO.waitforload();
			Result.takescreenshot("");
			CO.Radio_Select1(IPLC_Node_Features);
			CO.waitforload();

			cDriver.get().findElement(By.xpath("//div[text()=\"Item\"]//following::select[1]")).click();
			Actions cpe1 = new Actions(cDriver.get());
			cpe1.sendKeys(Keys.ARROW_DOWN).build().perform();
			cpe1.sendKeys(Keys.ENTER).build().perform();

			cDriver.get().findElement(By.xpath("//div[text()=\"Qty\"]//following::input[1]")).sendKeys("1");

			CO.waitforload();

			CO.Text_Select("button", "Add Item");
			CO.waitforload();

			CO.Radio_Select1(IPLC_Node_Features);

			CO.waitforload();

			Result.takescreenshot("");

			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");

			CO.waitforload();

			// Connection 1

			Browser.WebTable.click("Line_Items", 5, Col_S);
			CO.waitforload();
			CO.Text_Select("span", "Customize");
			CO.waitforload();
			Result.takescreenshot("");
			CO.Radio_Select1(IPLC_Feature);

			CO.waitforload();

			CO.Text_Select("a", "IPLC Services");

			CO.waitforload();

			CO.Radio_Select1(IPLC_Service);

			CO.waitforload();

			CO.Text_Select("a", "IPLC Charges");

			CO.waitforload();

			CO.Radio_Select1(IPLC_Charges);

			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");

			CO.waitforload();

			// Connection 2

			Browser.WebTable.click("Line_Items", 6, Col_S);
			CO.waitforload();
			CO.Text_Select("span", "Customize");
			CO.waitforload();
			Result.takescreenshot("");
			CO.Radio_Select1(IPLC_Feature);

			CO.waitforload();

			CO.Text_Select("a", "IPLC Services");

			CO.waitforload();

			CO.Radio_Select1(IPLC_Service);

			CO.waitforload();

			CO.Text_Select("a", "IPLC Charges");

			CO.waitforload();

			CO.Radio_Select1(IPLC_Charges);

			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");

			CO.waitforload();

			// Network Line Detail

			CO.scroll("LI_New", "WebButton");

			CO.Text_Select("a", "Network Line Detail");
			CO.waitforload();

			// node 1
			CO.scroll("LI_New", "WebButton");
			Browser.WebTable.click("Line_Items", 3, Col_S);
			Result.takescreenshot("");
			// Node service account

			CO.waitforload();
			Browser.WebLink.click("Node_Service_Acc");
			int Col_S12;
			Col_S12 = CO.Select_Cell("Pick_Account", "Account");
			Browser.WebTable.click("Pick_Account", 3, Col_S12);
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			Result.takescreenshot("");
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
			Browser.WebTable.click("Line_Items", 4, Col_S);
			Result.takescreenshot("");

			// from acc
			CO.waitforload();
			Browser.WebLink.click("Node_Service_Acc");
			int Col_S13;
			Col_S13 = CO.Select_Cell("Pick_Account", "Account");
			Browser.WebTable.click("Pick_Account", 4, Col_S13);
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			Result.takescreenshot("");
			CO.waitforload();

			// from add

			CO.waitforload();
			Browser.WebLink.click("Node_From_ADDR");
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();

			Result.takescreenshot("");

			CO.waitforload();

			// Connection 1

			CO.scroll("LI_New", "WebButton");
			Browser.WebTable.click("Line_Items", 5, Col_S);
			CO.waitforload();
			Result.takescreenshot("");

			// from acc

			Browser.WebLink.click("Access_From_Service_Acc");
			int Col_C;
			Col_C = CO.Select_Cell("Pick_Account", "Account");
			Browser.WebTable.click("Pick_Account", 3, Col_C);
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			Result.takescreenshot("");
			CO.waitforload();

			// to acc

			Browser.WebLink.click("To_Service_Acc");
			int Col_S21;
			Col_S21 = CO.Select_Cell("Pick_Account", "Account");
			Browser.WebTable.click("Pick_Account", 4, Col_S21);
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();
			Result.takescreenshot("");

			// from add
			CO.waitforload();
			Browser.WebLink.click("Access_From_Service_Addr");
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();
			Result.takescreenshot("");
			// to add
			CO.waitforload();
			Browser.WebLink.click("To_Address");
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();
			Result.takescreenshot("");

			CO.waitforload();

			// Connection 2

			CO.scroll("LI_New", "WebButton");
			Browser.WebTable.click("Line_Items", 5, Col_S);
			CO.waitforload();
			Result.takescreenshot("");

			// from acc

			Browser.WebLink.click("Access_From_Service_Acc");
			int Col_C1;
			Col_C1 = CO.Select_Cell("Pick_Account", "Account");
			Browser.WebTable.click("Pick_Account", 3, Col_C1);
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			Result.takescreenshot("");
			CO.waitforload();

			// to acc

			Browser.WebLink.click("To_Service_Acc");
			int Col_S211;
			Col_S211 = CO.Select_Cell("Pick_Account", "Account");
			Browser.WebTable.click("Pick_Account", 4, Col_S211);
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();
			Result.takescreenshot("");

			// from add
			CO.waitforload();
			Browser.WebLink.click("Access_From_Service_Addr");
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();
			Result.takescreenshot("");
			// to add
			CO.waitforload();
			Browser.WebLink.click("To_Address");
			CO.scroll("Popup_OK", "WebButton");
			Browser.WebButton.click("Popup_OK");
			CO.waitforload();
			Result.takescreenshot("");

			CO.waitforload();

			CO.Text_Select("a", "Appointments");
			CO.waitforload();
			CO.scroll("Validate", "WebButton");
			Browser.WebEdit.waittillvisible("Enterprise_credit_limit");
			CO.waitforload();
			Browser.WebEdit.Set("Enterprise_credit_limit", "100");
			Result.takescreenshot("");
			CO.waitforload();
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

			Result.takescreenshot(" Order Submited");

			CO.waitforload();

			Result.takescreenshot("Order Submission is Successful");

			Test_OutPut += KC.OrderSubmission().split("@@")[1];

		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}

		Result.fUpdateLog("------MPLS Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String Parent_AccountCreation() {
		String Test_OutPut = "", Status = "";
		String Account_No = "";
		Result.fUpdateLog("------Account Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				// Navigating to Accounts
				CO.waitforload();
				Browser.WebLink.waittillvisible("VQ_Account");
				CO.waitforobj("VQ_Account", "WebLink");
				Browser.WebLink.click("VQ_Account");
				CO.waitforload();

				// CO.Link_Select("My Accounts");
				CO.waitforobj("My_Account", "WebLink");
				Browser.WebLink.click("My_Account");

				CO.scroll("New_Account", "WebButton");
				Browser.WebButton.click("New_Account");
				String Acc;
				if (!(getdata("Account_Name").equals(""))) {
					Acc = getdata("Account_Name");
				} else if (!(pulldata("Account_Name").equals(""))) {
					Acc = pulldata("Account_Name") + R.nextInt(1000);
				} else {
					Acc = Utlities.randname() + R.nextInt(1000);
				}
				CO.scroll("Acc_Name", "WebEdit");
				Browser.WebEdit.Set("Acc_Name", Acc);

				if (!(getdata("CR_Type").equals(""))) {
					Browser.ListBox.select("CR_Type", getdata("CR_Type"));
				} else {
					Browser.ListBox.select("CR_Type", pulldata("CR_Type"));
				}

				Account_No = Browser.WebEdit.gettext("Account_No");
				Random R = new Random();
				if (!(getdata("CR_Type").equals(""))) {
					Browser.WebEdit.Set("CR_Number", getdata("CR_Number"));
				} else if (!(pulldata("CR_Number").equals(""))) {
					Browser.WebEdit.Set("CR_Number", pulldata("CR_Number"));
				} else {
					Browser.WebEdit.Set("CR_Number", "1" + R.nextInt(1000000));
				}

				if (!(getdata("SpecialManagement").equals(""))) {
					Browser.ListBox.select("Spcl_Mang", getdata("SpecialManagement"));
				} else {
					Browser.ListBox.select("Spcl_Mang", pulldata("SpecialManagement"));
				}

				CO.scroll("Customer_Segment", "ListBox");
				if (!(getdata("CustomerSegment").equals(""))) {
					Browser.ListBox.select("Customer_Segment", getdata("CustomerSegment"));
				} else {
					Browser.ListBox.select("Customer_Segment", pulldata("CustomerSegment"));
				}

				CO.waitforload();
				CO.scroll("Tier", "WebEdit");
				Browser.WebEdit.click("Tier");
				if (!(getdata("Tier").equals(""))) {
					Browser.WebEdit.Set("Tier", getdata("Tier"));

				} else {
					Browser.WebEdit.Set("Tier", pulldata("Tier"));
				}
				CO.Link_Select(Acc);
				CO.waitforload();
				// Utlities.StoreValue("Account_No", Account_No);
				Test_OutPut += "Account_No : " + Account_No + ",";
				Result.takescreenshot("Account Created Account NO : " + Account_No);
			}
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Account Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String Parent_ContactCreation() {
		String Test_OutPut = "", Status = "";
		String Last_Name = null;
		Result.fUpdateLog("------Contact Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
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
				int x = 0;
				// int j = 1;
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
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Contact Creation Event Details - Completed------");
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

				do {
					CO.TabNavigator("Contacts");
					CO.waitforload();
					/*
					 * if (Browser.WebEdit.waitTillEnabled("Contact_Valid_Name")) { j = 0; break; }
					 */
				} while (!Browser.WebEdit.waitTillEnabled("Contact_Valid_Name"));
				Browser.WebEdit.waittillvisible("Contact_Valid_Name");

				CO.waitforload();

				Browser.WebButton.waittillvisible("Acc_Add_Contact");
				Browser.WebButton.click("Acc_Add_Contact");

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
			Continue.set(false);
			Status = "FAIL";
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

				do {
					CO.TabNavigator("Contacts");
					CO.waitforload();
					/*
					 * if (Browser.WebEdit.waitTillEnabled("Contact_Valid_Name")) { j = 0; break; }
					 */
				} while (!Browser.WebEdit.waitTillEnabled("Contact_Valid_Name"));
				Browser.WebEdit.waittillvisible("Contact_Valid_Name");

				CO.waitforload();

				Browser.WebButton.waittillvisible("Acc_Add_Contact");
				Browser.WebButton.click("Acc_Add_Contact");

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
			Continue.set(false);
			Status = "FAIL";
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
					Browser.WebButton.click("Add_Address");
					CO.waitforload();
				} else if (Browser.WebButton.exist("Address_Tab")) {
					Result.fUpdateLog("Proceeding Enterprise Address Creation");
					Browser.WebButton.click("Add_Address");
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
			Continue.set(false);
			Status = "FAIL";
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
				Browser.WebButton.click("Orders_Tab");
				CO.waitforload();
				if (CO.isAlertExist())
					Browser.WebButton.click("Orders_Tab");

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
			Continue.set(false);
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();

		}
		Result.fUpdateLog("------Sales Order Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
