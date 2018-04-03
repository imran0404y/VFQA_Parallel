package Libraries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.CharMatcher;

public class Keyword_CRM extends Driver {
	Common CO = new Common();
	Random R = new Random();
	Keyword_Validations KV = new Keyword_Validations();
	public static int COL_FUL_STATUS;

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Open browser
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the Siebel CRM application
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Siebel_Login() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel Login Event Details------");
		try {

			if (!(getdata("Browser").equals(""))) {
				browser.set(getdata("Browser"));
			} else {
				browser.set("Chrome");
			}

			URL.set(getdata("URL/HOST"));

			Browser.OpenBrowser(browser.get(), URL.get());
			if (!Browser.WebLink.exist("Login_Down")) {
				if (Browser.WebLink.exist("Override_Link")) {
					Result.takescreenshot("Opening with IE");
					Browser.WebLink.click("Override_Link");
				}

				// Continue to this website (not recommended).

				Result.fUpdateLog("Browser Opened Successfully");
				Result.takescreenshot("Opening Browser and navigating to the URL");
				Browser.WebEdit.waittillvisible("VQ_Login_User");
				Browser.WebEdit.Set("VQ_Login_User", getdata("VQ_Login_User"));
				Browser.WebEdit.Set("VQ_Login_Pswd", getdata("VQ_Login_Pswd"));
				Browser.WebButton.waittillvisible("VQ_Login");
				Browser.WebButton.click("VQ_Login");
				CO.waitforload();
				Browser.WebButton.waittillvisible("VF_Search_Identify");

				CO.ToWait();
				if (Continue.get()) {
					Test_OutPut += "Successfully Login with : " + getdata("VQ_Login_User") + ",";
					Result.takescreenshot("Login Successfully with user " + getdata("VQ_Login_User"));
					Result.fUpdateLog("Login Successfully with user " + getdata("VQ_Login_User"));
					Status = "PASS";
				} else {
					Test_OutPut += "Login Failed" + ",";
					Result.takescreenshot("Login Failed");
					Result.fUpdateLog("Login Failed");
					Status = "FAIL";
				}
			} else {
				Continue.set(false);
				Test_OutPut += "Appilcation is Down" + ",";
				Result.takescreenshot("Appilcation is Down");
				Result.fUpdateLog("Appilcation is Down");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: SiebLogout
	 * Arguments			: None
	 * Use 					: Log Out Siebel browser and close the browser window
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Siebel_Logout() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel Logout Event Details------");
		try {
			CO.scroll("VQ_Acc_Logo", "WebButton");
			CO.waitforobj("VQ_Acc_Logo", "WebButton");
			CO.scroll("VQ_Acc_Logo", "WebButton");
			Browser.WebButton.waittillvisible("VQ_Acc_Logo");
			Browser.WebButton.click("VQ_Acc_Logo");
			Result.takescreenshot("Siebel Application Logged out");

			CO.waitforobj("VQ_Logout", "WebButton");
			CO.scroll("VQ_Logout", "WebButton");
			Browser.WebButton.waittillvisible("VQ_Logout");
			Browser.WebButton.click("VQ_Logout");
			cDriver.get().close();
			cDriver.get().quit();
			CO.ToWait();

			if (Continue.get()) {
				Test_OutPut += "Siebel Logout Successful";
				Result.fUpdateLog("Siebel Logout Successful");
				Status = "PASS";
			} else {
				Test_OutPut += "Logout Failed";
				Result.fUpdateLog("Logout Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel Logout Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: ContactCreation
	 * Arguments			: None
	 * Use 					: Creates a new contact with the specific data in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String ContactCreation() {
		String Test_OutPut = "", Status = "";
		String Last_Name = null;
		Result.fUpdateLog("------Contact Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				String IDType = "", IDNumber = "";
				CO.waitforobj("VQ_Contact", "WebLink");
				CO.waitforload();
				Browser.WebLink.click("VQ_Contact");
				Browser.WebLink.waittillvisible("My_Contacts");

				CO.waitforobj("My_Contacts", "WebLink");
				Browser.WebLink.click("My_Contacts");
				Browser.WebButton.waittillvisible("New_Contact");

				Browser.WebButton.click("New_Contact");

				if (!(getdata("LastName").equals(""))) {
					Last_Name = getdata("LastName");
				} else if (!(pulldata("LastName").equals(""))) {
					Last_Name = pulldata("LastName") + R.nextInt(1000);
				}
				contact.set(Last_Name);
				CO.scroll("LastName", "WebEdit");
				Browser.WebEdit.Set("LastName", Last_Name);
				Result.fUpdateLog("LastName : " + Last_Name);
				contact.set(Last_Name);
				if (!(getdata("FirstName").equals(""))) {
					Browser.WebEdit.Set("FirstName", getdata("FirstName"));
				} else if (!(pulldata("FirstName").equals(""))) {
					Browser.WebEdit.Set("FirstName", pulldata("FirstName"));
				}

				if (!(getdata("Mr/Ms").equals(""))) {
					Browser.ListBox.select("Mr/Ms", getdata("Mr/Ms"));
				} else {
					Browser.ListBox.select("Mr/Ms", pulldata("Mr/Ms"));
				}

				if (!(getdata("PrefLanguage").equals(""))) {
					Browser.ListBox.select("PrefLanguage", getdata("PrefLanguage"));
				} else {
					Browser.ListBox.select("PrefLanguage", pulldata("PrefLanguage"));
				}

				if (!(getdata("DOB").equals(""))) {
					Browser.WebEdit.Set("DOB", getdata("DOB"));
				} else {
					Browser.WebEdit.Set("DOB", pulldata("DOB"));
				}

				if (!(getdata("Gender").equals(""))) {
					Browser.ListBox.select("Gender", getdata("Gender"));
				} else {
					Browser.ListBox.select("Gender", pulldata("Gender"));
				}

				if (!(getdata("Email").equals(""))) {
					Browser.WebEdit.Set("Email", getdata("Email"));
				} else {
					Browser.WebEdit.Set("Email", pulldata("Email"));
				}

				// CO.scroll("ID_Type", "ListBox");
				if (!(getdata("ID_Type").equals(""))) {
					IDType = getdata("ID_Type");
				} else {
					IDType = pulldata("ID_Type");
				}
				Browser.ListBox.select("ID_Type", IDType);

				// CO.scroll("ID_Number", "WebEdit");
				if (!(getdata("ID_Number").equals(""))) {
					IDNumber = getdata("ID_Number");
				} else {
					IDNumber = pulldata("ID_Number") + R.nextInt(100000);
				}
				Browser.WebEdit.Set("ID_Number", IDNumber);
				Result.fUpdateLog("Customer ID : " + IDNumber);
				Test_OutPut += "Customer ID : " + IDNumber + ",";

				// CO.scroll("ID_ExpDate", "WebEdit");
				if (!(getdata("ID_ExpDate").equals(""))) {
					Browser.WebEdit.Set("ID_ExpDate", getdata("ID_ExpDate"));
				} else {
					Browser.WebEdit.Set("ID_ExpDate", pulldata("ID_ExpDate"));
				}

				// CO.scroll("Nationality", "ListBox");
				if (!(getdata("Nationality").equals(""))) {
					Browser.ListBox.select("Nationality", getdata("Nationality"));
				} else {
					Browser.ListBox.select("Nationality", pulldata("Nationality"));
				}

				// CO.scroll("Phone", "WebEdit");
				if (!(getdata("Phone").equals(""))) {
					Browser.WebEdit.Set("Phone", getdata("Phone"));
				} else {
					Browser.WebEdit.Set("Phone", pulldata("Phone"));
				}

				/*
				 * Browser.WebLink.waittillvisible("Con_Link");
				 * Browser.WebLink.click("Con_Link");
				 */
				int Col = CO.Select_Cell("Contact", "Last_Name");
				Browser.WebTable.clickA("Contact", 2, Col);

				// Handles Alerts
				if (CO.isAlertExist())
					CO.waitforload();

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
				Result.takescreenshot("Customer Creation with Customer ID : " + IDNumber);

				if (!(Address.equals(""))) {

					CO.waitforobj("Add_Address", "WebButton");
					Browser.WebButton.click("Add_Address");

					// Search for Specific Address
					CO.waitforobj("Popup_Go", "WebButton");
					CO.scroll("Popup_Go", "WebButton");

					if (Address.contains("Kar#")) {
						Browser.ListBox.select("PopupQuery_List", "Kahramaa ID");
						Address = Address.split("#")[1];
						Browser.WebEdit.Set("PopupQuery_Search", Address);
					} else {
						Browser.ListBox.select("PopupQuery_List", "Address Line 1");
						Browser.WebEdit.Set("PopupQuery_Search", Address);
					}
					CO.waitforload();
					Browser.WebButton.click("Popup_Go");

					CO.scroll("Add_OK", "WebButton");
					Browser.WebButton.click("Add_OK");
					/*
					 * do { Result.fUpdateLog("Page Loading....."); } while
					 * (Browser.WebButton.waitTillEnabled("Add_OK"));
					 */

					Method.waitForPageToLoad(cDriver.get(), 10);
					Browser.WebButton.waittillvisible("Create_A/c");
					Result.takescreenshot("Address Selected : " + Address);
					Result.fUpdateLog("Contact created with given Existing Address : " + Address);
				} else {
					String[] stat_add = AddressCreation().split("@@");
					Status = stat_add[0];
					Address = stat_add[1].split(",")[0];
					Result.takescreenshot("Address Created : " + Address);
					Result.fUpdateLog("Created new Address : " + Address);
				}

				CO.ToWait();
				if (Continue.get()) {
					Utlities.StoreValue("LastName", Last_Name);
					Utlities.StoreValue("Address", Address);
					Status = "PASS";
				} else {
					Result.takescreenshot("Create_A/c button not exist");
					Test_OutPut += "Create_A/c button not exist" + ",";
					Result.fUpdateLog("Create_A/c button not exist");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Contact Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: AccountCreation
	 * Arguments			: None
	 * Use 					: Creates Account for the Contact created Earlier in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String AccountCreation() {
		String Test_OutPut = "", Status = "";
		String Account_No = null;
		Result.fUpdateLog("------Account Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				int Row_Count = Browser.WebTable.getRowCount("Address");
				if (Row_Count > 1) {
					Browser.WebButton.waittillvisible("Create_A/c");
					CO.waitforobj("Create_A/c", "WebButton");
					CO.scroll("Create_A/c", "WebButton");
					CO.Text_Select("span", "Create A/c");

					Browser.ListBox.waittillvisible("CR_Type");
					String CR = "12" + R.nextInt(100000);
					if (!(getdata("CR_Type").equals(""))) {
						Browser.ListBox.select("CR_Type", getdata("CR_Type"));
						Browser.WebEdit.Set("CR_Number", getdata("CR_Number"));
					} else if (!(pulldata("CR_Type").equals(""))) {
						Browser.ListBox.select("CR_Type", pulldata("CR_Type"));
						Browser.WebEdit.Set("CR_Number", CR);
					}

					if (!(getdata("SpecialManagement").equals(""))) {
						Browser.ListBox.select("Spcl_Mang", getdata("SpecialManagement"));
						Result.fUpdateLog("SpecialManagement : " + getdata("SpecialManagement"));
					} else {
						Browser.ListBox.select("Spcl_Mang", pulldata("SpecialManagement"));
						Result.fUpdateLog("SpecialManagement : " + pulldata("SpecialManagement"));
					}

					Account_No = Browser.WebEdit.gettext("Account_No");
					New_Account.set(Account_No);
					Utlities.StoreValue("Account_No", Account_No);
					Test_OutPut += "Account_No : " + Account_No + ",";
				} else {
					Continue.set(false);
					Result.fUpdateLog("No records Founded - Create a address for the customer");
					System.exit(0);
				}

				CO.ToWait();
				if (Continue.get()) {
					Status = "PASS";
					Result.takescreenshot("Account Created Account_No : " + Account_No);
				} else {
					Test_OutPut += "Account Creation is Failed" + ",";
					Result.takescreenshot("Account Creation is Failed");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Account Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: AddressCreation
	 * Arguments			: None
	 * Use 					: Creates Account for the Contact created Earlier in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
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

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BillingProfileCreation
	 * Arguments			: None
	 * Use 					: Creates a Billing Profile in the existing Account for Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String BillingProfileCreation() {
		String Test_OutPut = "", Status = "";
		String Bill_No = null;
		int Col_Nam, Row_va = 0;
		Result.fUpdateLog("------Billing Profile Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (!Exi.equals("")) {
				CO.Account_Search(Exi);
				Utlities.StoreValue("Account_No", Exi);
				Test_OutPut += "Account_No : " + Exi + ",";
				CO.waitforload();
			}
			if (Continue.get()) {
				CO.scroll("Profile_Tab", "WebButton");
				do {
					Browser.WebButton.click("Profile_Tab");
					CO.waitforload();
					/*
					 * if (Browser.WebEdit.waitTillEnabled("BP_Valid_Name")) { j = 0; break; }
					 */

				} while (!Browser.WebEdit.waitTillEnabled("BP_Valid_Name"));
				Browser.WebEdit.waittillvisible("BP_Valid_Name");

				CO.waitforload();
				int Row = 2, Col_Val = 0, Row_Count;

				String Payment_Type = null;
				if (!(getdata("Bill_PayType").equals(""))) {
					Payment_Type = getdata("Bill_PayType");
				} else if (!(pulldata("Bill_PayType").equals(""))) {
					Payment_Type = pulldata("Bill_PayType");
				}

				String Bill_NewProfile = "No";
				if (!(getdata("Bill_NewProfile").equals(""))) {
					Bill_NewProfile = getdata("Bill_NewProfile");
				} else if (!(pulldata("Bill_NewProfile").equals(""))) {
					Bill_NewProfile = pulldata("Bill_NewProfile");
				}

				CO.waitforobj("Bill_Add", "WebButton");
				Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
				if (Row_Count >= Row) {
					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Type");
					Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Val);
						if (Payment_Type.equalsIgnoreCase(LData)) {
							Bill_No = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Nam);
							break;
						}
						Row_va = i;
					}
				}

				if ((Row_Count < Row) || Bill_NewProfile.equals("Yes") || Row_Count == Row_va) {
					Browser.WebButton.waittillvisible("Bill_Add");
					CO.scroll("Bill_Add", "WebButton");
					int Row_Ct = Browser.WebTable.getRowCount("Bill_Prof");
					Browser.WebButton.click("Bill_Add");
					do {
						int Row_C = Browser.WebTable.getRowCount("Bill_Prof");
						if (Row_C > Row_Ct) {
							break;
						}
					} while (true);

					CO.waitforload();

					Browser.WebTable.waittillvisible("Bill_Prof");
					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Type");
					if (!(getdata("Bill_PayType").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Type", getdata("Bill_PayType"));
					} else if (!(pulldata("Bill_PayType").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Type", pulldata("Bill_PayType"));
					}

					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Method");
					if (!(getdata("Bill_PayMethod").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Method",
								getdata("Bill_PayMethod"));
					} else if (!(pulldata("Bill_PayMethod").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Method",
								pulldata("Bill_PayMethod"));
					}
					CO.isAlertExist();

					if (Payment_Type.equalsIgnoreCase("Postpaid")) {
						Col_Val = CO.Select_Cell("Bill_Prof", "Bill Media");
						if (!(getdata("Bill_Media").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Media_Type", getdata("Bill_Media"));
						} else if (!(pulldata("Bill_Media").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Media_Type", pulldata("Bill_Media"));
						}

						Col_Val = CO.Select_Cell("Bill_Prof", "Bill Type");
						if (!(getdata("Bill_Type").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bill_Type", getdata("Bill_Type"));
						} else if (!(pulldata("Bill_Type").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bill_Type", pulldata("Bill_Type"));
						}

					}

					Col_Val = CO.Select_Cell("Bill_Prof", "Language");
					if (!(getdata("Bill_Lang").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bank_Language_Code", getdata("Bill_Lang"));
					} else if (!(pulldata("Bill_Lang").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bank_Language_Code",
								pulldata("Bill_Lang"));
					}

					int Col_v;
					Col_v = CO.Actual_Cell("Bill_Prof", "Name");
					Bill_No = Browser.WebTable.getCellData("Bill_Prof", Row, Col_v);
				}

				Billprofile_No.set(Bill_No);
				Utlities.StoreValue("Billing_NO", Bill_No);
				Test_OutPut += "Billing_NO : " + Bill_No + ",";

				Browser.WebButton.waittillvisible("Orders_Tab");
			}
			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("Billing Profile Created Billing_NO : " + Bill_No);
			} else {
				Result.takescreenshot("Orders Tab not exist");
				Test_OutPut += "Orders Tab not exist" + ",";
				Result.fUpdateLog("Orders Tab not exist");
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
		Result.fUpdateLog("------Billing Profile Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: SalesOrder
	 * Arguments			: None
	 * Use 					: Creates a Sales Order in the existing Account for Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
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
				/*
				 * if (Browser.WebEdit.waitTillEnabled("Order_Valid_Name")) { j = 0; break; }
				 */

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
			Browser.WebLink.waittillvisible("Line_Items");
			if (Browser.WebLink.exist("SalesOd_Expand")) {
				Browser.WebLink.click("SalesOd_Expand");
				CO.waitforload();
			}
			CO.waitforload();
			Result.fUpdateLog(Billprofile_No.get());
			if (Billprofile_No != null) {
				CO.Webtable_Value("Billing Profile", Billprofile_No.get());
			}

			Browser.WebButton.waittillvisible("LI_New");

			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				Utlities.StoreValue("Sales_OrderNO", SalesOrder_No.get());
				Test_OutPut += "Order_No : " + Order_No + ",";
				Utlities.StoreValue("Order_Creation_Date", OrderDate.get());
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

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for the Order in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String PlanSelection() {
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
			Result.fUpdateLog("PlanName : " + PlanName);
			Test_OutPut += "PlanName : " + PlanName + ",";

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			// To use Catalog view uncomment the below lines
			// Browser.WebLink.click("VQ_Catalog");
			// CO.Category_Select(pulldata("PlanName"),pulldata("PlanName"),PlanName);
			// Browser.WebButton.click("LI_New");
			// CO.waitforload();

			// To use the catalog view comment the below line till '----'
			CO.scroll("LI_New", "WebButton");
			Browser.WebButton.click("LI_New");
			int Row = 2, Col;
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
				Browser.WebTable.SetData("Numbers", Row, COl_STyp, "Service_Type", "Mobile");

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
				/*
				 * CO.waitforload();
				 * 
				 * Browser.WebButton.waittillvisible("Reserved_Ok");
				 * Browser.WebButton.waitTillEnabled("Reserved_Ok"); Row_Count =
				 * Browser.WebTable.getRowCount("Number_Selection"); if (Row_Count > 1)
				 * Browser.WebButton.click("Reserved_Ok"); else Continue.set(false);
				 */
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

			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.LineItems_Data();
			Result.takescreenshot("");
			// Test_OutPut += OrderSubmission().split("@@")[1];

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
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: OrderSubmission
	 * Arguments			: None
	 * Use 					: Submits the Order, Waits for the Fulfillment Status and Displays the result 
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("deprecation")
	public String OrderSubmission() {
		String Test_OutPut = "", Status = "";
		int COL_FUL_STATUS = 0;
		String OS_Status = "", CreditLimit;
		Result.fUpdateLog("------Order Submission Event Details------");
		try {
			int Complete_Status = 0, Wait = 0, Row = 2, Col, Bill_Col, Row_Count;
			String EStatus = "Complete", FStatus = "Failed", Bill_Cycle, Msg = null;
			CO.waitforload();

			if (Browser.WebLink.exist("SalesOd_Expand")) {
				Browser.WebLink.click("SalesOd_Expand");
				CO.waitforload();
			}
			if (Browser.WebTable.exist("Line_Items"))
				Result.fUpdateLog("Proceeding Order Submission");
			CO.waitforload();
			if (UseCaseName.get().toLowerCase().contains("enterprise") || TestCaseN.get().toLowerCase().contains("vip")
					|| UseCaseName.get().contains("SIPT")) {
				if (!(getdata("Ent_CreditLimit").equals(""))) {
					CreditLimit = getdata("Ent_CreditLimit");
				} else {
					CreditLimit = pulldata("Ent_CreditLimit");
				}
				CO.scroll("Ent_CreditLimit", "WebEdit");
				Browser.WebEdit.click("Ent_CreditLimit");
				Browser.WebEdit.Set("Ent_CreditLimit", CreditLimit);
				// Browser.WebEdit.Set("Ent_CreditLimit", "100");
			}

			// To get fulfillment status coloumn
			CO.scroll("Ful_Status", "WebButton");
			Col = CO.Select_Cell("Line_Items", "Fulfillment Status");
			if (CO.Col_Data(Col).equalsIgnoreCase("fulfillment status"))
				COL_FUL_STATUS = Col;
			CO.scroll("Service", "WebButton");

			Browser.WebButton.waittillvisible("Validate");
			Browser.WebButton.click("Validate");
			CO.waitforload();
			// CO.isAlertExist();
			try {
				WebDriverWait wait = new WebDriverWait(cDriver.get(), 60);
				if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
					String popup = cDriver.get().switchTo().alert().getText();
					Result.fUpdateLog(popup);
					if (Validatedata("SmartLimit").equalsIgnoreCase("yes")) {
						String theDigits = CharMatcher.DIGIT.retainFrom(popup);
						Def_Smart_limit.set(theDigits);
					}
					if (popup.contains("Smart Limit")) {
						Continue.set(true);
					} else {
						Msg = "Unwanted Popup exists on Validate - " + popup + ",";
						Continue.set(false);
					}
				}
				Browser.alert.accept();
				Browser.Readystate();
			} catch (Exception e) {
				Result.fUpdateLog("No Alert Exist");
				e.getMessage();
			}
			CO.waitforload();
			if (Validatedata("SmartLimit").equalsIgnoreCase("yes") && !(Planname.get().contains("Mobile Broadband"))) {
				String Smartlimit = Utlities.FetchSmartlimit();
				if (Def_Smart_limit.get().equals(Smartlimit)) {
					Result.fUpdateLog("Default Smartlimit : " + Def_Smart_limit.get());
					Test_OutPut += "Default Smartlimit : " + Def_Smart_limit.get() + ",";
				} else {
					Continue.set(false);
				}
			}

			if (Continue.get()) {
				switch (UseCaseName.get()) {
				case "ConsumerPostpaid_Provision":
				case "ConsumerPostpaid_Prov_OrdPay":
				case "Consumer_Migration":
					switch (TestCaseN.get()) {
					case "NewCustomer":
					case "ExtCustomer":
					case "Prepaid_To_Postpaid":
						try {
							WebDriverWait wait = new WebDriverWait(cDriver.get(), 100);
							if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
								String popup = cDriver.get().switchTo().alert().getText();
								Result.fUpdateLog(popup);
								if (popup.contains("Smart Limit")) {
									Continue.set(true);
								} else {
									Continue.set(false);
									Msg = "Unwanted Popup exists on Validate - " + popup + ",";
								}
							}
							Browser.alert.accept();
							Browser.Readystate();
						} catch (Exception e) {
							Result.fUpdateLog("No Alert Exist");
							Continue.set(false);
							e.getMessage();
						}
						break;
					}

				}
			}

			if (Continue.get()) {
				Browser.WebButton.waittillvisible("Submit");
				CO.scroll("Submit", "WebButton");
				Browser.WebButton.click("Submit");
				CO.waitforload();
				if (CO.isAlertExist()) {
					Continue.set(false);
					Msg = "Unwanted Popup exists on Submit ,";
				}
			}

			if (Continue.get()) {
				Result.takescreenshot("Order Submission is Successful");
				Col = COL_FUL_STATUS;
				cDriver.get().navigate().refresh();
				Browser.WebButton.waittillvisible("Submit");
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");

				do {
					Complete_Status = 0;
					// To refresh Page
					cDriver.get().navigate().refresh();
					CO.waitmoreforload();
					Browser.WebButton.waittillvisible("Submit");

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
					CO.waitforload();
				} while (Wait < 100);
				Browser.WebButton.waittillvisible("Submit");

				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				CO.scroll("Submit", "WebButton");
				OS_Status = Browser.WebTable.getCellData("Line_Items", Row, Col);
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
					Result.takescreenshot("Line Items");
				}
				Bill_Col = CO.Actual_Cell("Line_Items", "Bill Cycle");
				Bill_Cycle = Browser.WebTable.getCellData("Line_Items", Row, Bill_Col);
				billDate.set(Bill_Cycle);

				if (OS_Status.equalsIgnoreCase(EStatus) || Complete_Status == (Row_Count - 1)) {
					Continue.set(true);
				} else {
					Continue.set(false);
				}
			}
			CO.ToWait();
			if (Continue.get()) {
				Result.fUpdateLog("Order Status : " + OS_Status);
				Test_OutPut += "Order Status : " + OS_Status + ",";
				Result.takescreenshot("Order Status : " + OS_Status);
				Status = "PASS";
			} else {
				Result.fUpdateLog(Msg);
				Result.takescreenshot(Msg);
				Test_OutPut += Msg + ",";
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
		Result.fUpdateLog("------Order Submission Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*-----------------------------------------------------------------------------------------------------
	 * Method Name			: Entp_AccountCreation
	 * Arguments			: None
	 * Use 					: Creates an Enterprise Account for Vanilla Journey
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Entp_AccountCreation() {
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
				Acc_Number.set(Account_No);
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
				/*
				 * CO.scroll("Tier", "WebEdit"); Browser.WebEdit.click("Tier"); if
				 * (!(getdata("Tier").equals(""))) { Browser.WebEdit.Set("Tier",
				 * getdata("Tier")); } else { Browser.WebEdit.Set("Tier", pulldata("Tier")); }
				 */

				CO.Link_Select(Acc);
				CO.waitforload();
				// to be commented for QA6
				/*
				 * if (Browser.WebLink.exist("Acc_Portal")) { CO.waitforload();
				 * Browser.WebLink.click("Acc_Portal"); }
				 */

				Browser.WebLink.waittillvisible("Acc_Summary");

				CO.ToWait();
				if (Continue.get()) {
					Status = "PASS";
					Utlities.StoreValue("Account_No", Account_No);
					Test_OutPut += "Account_No : " + Account_No + ",";
					Result.takescreenshot("Account Created Account NO : " + Account_No);
				} else {
					Status = "FAIL";
					Test_OutPut += "Account Creation Failed" + ",";
					Result.takescreenshot("Account Creation Failed");
					Result.fUpdateLog("Account Creation Failed");
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
		Result.fUpdateLog("------Account Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Entp_ContactCreation0
	 * Arguments			: None
	 * Use 					: Creates a Contact in the Enterprise Account for Vanilla Journey
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Entp_ContactCreation() {
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
				/*
				 * Browser.WebButton.click("Ent_Notification");
				 * Browser.WebButton.click("Ent_Not_Ok"); if (CO.isAlertExist())
				 * CO.isAlertExist();
				 */

				Browser.WebButton.click("Ent_Notification");
				Browser.WebButton.click("Ent_Not_Ok");
				Browser.WebButton.click("Address_Tab");
				if (CO.isAlertExist())
					CO.isAlertExist();
				CO.waitforload();
				if (Browser.WebEdit.gettext("Ent_Notif").equalsIgnoreCase("")) {
					Browser.WebButton.click("Ent_Notification");
					Browser.WebButton.click("Ent_Not_Ok");
				}

				CO.ToWait();
				if (Continue.get()) {
					Result.takescreenshot("Contact Ceated : " + Last_Name);
					Utlities.StoreValue("LastName", Last_Name);
					Utlities.StoreValue("Address", Address);
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

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Modify
	 * Arguments			: None
	 * Use 					: Modification of Installed Assert is performed
	 * Designed By			: Vinodhini Raviprasad
	 * Latest Modified By	: Sravani
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Modify() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Add_Addon, Remove_Addon, Order_no, GetData;
		int Inst_RowCount, Col_P, Col_SID, Row_Count;

		Result.fUpdateLog("------Modify Event Details------");
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
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			CO.waitforload();
			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					CO.waitforload();
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						CO.waitforload();
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else {
				CO.InstalledAssertChange("Modify");
			}

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			// wait
			CO.waitmoreforload();
			CO.AddOnSelection(Add_Addon, "Add");
			CO.waitforload();
			CO.AddOnSelection(Remove_Addon, "Delete");
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
			/*
			 * // Use for "Ent_CreditLimit" String Product_Type; if
			 * (!(getdata("Product_Type").equals(""))) { Product_Type =
			 * getdata("Product_Type"); } else { Product_Type = pulldata("Product_Type"); }
			 * if (Product_Type.equals("Enterprise") || Product_Type.equals("VIP"))
			 * Browser.WebEdit.Set("Ent_CreditLimit", "100");// click("Ent_CreditLimit");
			 * else Browser.WebEdit.click("Credit_Limit");
			 */
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}

			LineItemData.clear();
			CO.Status(Add_Addon);
			CO.waitforload();
			CO.Status(Remove_Addon);
			CO.waitforload();
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			Test_OutPut += OrderSubmission().split("@@")[1];
			// fetching Order_no
			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("Modification is Successful");
			} else
				Status = "FAIL";
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("Modify Event  Details - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: UpgradePromotion()
	 * Arguments			: None
	 * Use 					: Change of Plan
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String UpgradePromotion() {

		String Test_OutPut = "", Status = "";
		String MSISDN, New_PlanName, GetData, Order_no;
		int Col, Col_P;
		Result.fUpdateLog("------Plan Upgrade/Downgrade Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_PlanName").equals(""))) {
				New_PlanName = getdata("New_PlanName");
			} else {
				New_PlanName = pulldata("New_PlanName");
			}
			Result.fUpdateLog("New_PlanName : " + New_PlanName);
			Planname.set(New_PlanName);

			/*
			 * if (!(getdata("Existing_PlanName").equals(""))) { Existing_Plan =
			 * getdata("Existing_PlanName"); } else { Existing_Plan =
			 * pulldata("Existing_PlanName"); }
			 */
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			CO.Plan_selection(GetData, MSISDN);
			int j = 1;
			boolean a = true;
			do {
				j++;
				Result.fUpdateLog("PopupQuery_Search Page Loading.....");
				CO.waitforload();
				Result.fUpdateLog("Account Summary Page Loading.....");
				if (Browser.WebEdit.waitTillEnabled("PopupQuery_Search")) {
					a = false;
				} else if (j < 20) {
					a = false;
				}
			} while (a);
			Browser.WebEdit.Set("PopupQuery_Search", New_PlanName);
			String Path[] = Utlities.FindObject("PopupQuery_Search", "WebEdit");
			cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
			Result.takescreenshot("New Plane is entered in Plan Upgrade Pop Up");
			CO.waitforload();

			if (Browser.WebTable.getRowCount("Promotion_Upgrades") >= 2) {
				CO.scroll("Upgrade_OK", "WebButton");
				Browser.WebButton.click("Upgrade_OK");
				int i = 1;
				a = true;
				do {
					i++;
					Result.fUpdateLog("LI_New Page Loading.....");
					if (Browser.WebButton.waitTillEnabled("LI_New")) {
						a = false;
					} else if (i < 20) {
						a = false;
					}
				} while (a);
			} else {
				Continue.set(false);
				System.exit(0);
			}

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);

				if (LData.equalsIgnoreCase(New_PlanName)) {
					if (Action.equalsIgnoreCase("Add")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Continue.set(false);
					}
				} /*
					 * else if (LData.equalsIgnoreCase(Existing_Plan)) { if
					 * (Action.equalsIgnoreCase("Delete")) { Result.fUpdateLog("Action Update   " +
					 * LData + ":" + Action); } else { Result.fUpdateLog(LData + ":" + Action);
					 * Continue.set(false); } }
					 */
			}
			if (Row_Count1 <= 4) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.LineItems_Data();

			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.waitforload();
			Test_OutPut += OrderSubmission().split("@@")[1];

			CO.ToWait();
			CO.GetSiebelDate();
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
		Result.fUpdateLog("------Plan Upgrade/Downgrade Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: UpgradePromotion_Account360
	 * Arguments			: None
	 * Use 					: Upgrade Promotion via Account 360 View
	 * Designed By			: Vinodhini R
	 * Last Modified Date 	: 02-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public String UpgradePromotion_Acc360() {
		String Test_OutPut = "", Status = "";
		String MSISDN, New_PlanName, Order_no;
		int RowCount, Col_P, Col;

		Result.fUpdateLog("------Upgrade Promotion via Account 360 view------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			Result.fUpdateLog("MSISDN : " + MSISDN);

			if (!(getdata("New_PlanName").equals(""))) {
				New_PlanName = getdata("New_PlanName");
			} else {
				New_PlanName = pulldata("New_PlanName");
			}
			Result.fUpdateLog("New_PlanName : " + New_PlanName);
			Planname.set(New_PlanName);

			/*
			 * if (!(getdata("GetData").equals(""))) { GetData = getdata("GetData"); } else
			 * { GetData = pulldata("GetData"); }
			 */

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

			Browser.WebLink.waittillvisible("Global_Link");
			Result.fUpdateLog("Global Search MSISDN Retrived");
			Result.takescreenshot("Global Search MSISDN Retrived");
			CO.waitforload();

			Browser.WebLink.click("Global_Link");
			CO.waitforload();

			Result.fUpdateLog("Account 360 View");
			Result.takescreenshot("Account 360 View");
			Browser.WebLink.click("Global_Search");

			Result.fUpdateLog("Retriving Actual MSISDN Record");
			Result.takescreenshot("Retriving Actual MSISDN Record");
			RowCount = Browser.WebTable.getRowCount("Installed_Assert360");
			if (RowCount > 3) {

				Col = CO.Select_Cell("Installed_Assert360", "Phone Number");
				for (int Row = 3; Row <= RowCount; Row++)
					if ((Browser.WebTable.getCellData("Installed_Assert360", Row, Col)).equals(MSISDN)) {
						Browser.WebTable.click("Installed_Assert360", Row, Col);
						Result.fUpdateLog("Retrived Actual MSISDN Record");
						Result.takescreenshot("Retrived Actual MSISDN Record");
						break;
					}

			}

			CO.waitforload();
			CO.Text_Select("div", "Asset Summary");
			CO.waitforload();
			Browser.WebButton.waittillvisible("UpgradePromotion");
			CO.waitforload();

			Result.fUpdateLog("Upgrading Promotion via Account 360");
			Result.takescreenshot("Upgrading Promotion via Account 360");

			CO.scroll("UpgradePromotion", "WebButton");
			CO.waitforload();
			Browser.WebButton.click("UpgradePromotion");

			CO.waitforload();
			Browser.WebEdit.waittillvisible("PopupQuery_Search");
			CO.waitforload();
			Browser.WebEdit.Set("PopupQuery_Search", New_PlanName);
			String Path[] = Utlities.FindObject("PopupQuery_Search", "WebEdit");
			cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
			CO.waitforload();

			if (Browser.WebTable.getRowCount("Promotion_Upgrades") >= 2) {

				Result.takescreenshot("Promotion Selected");
				CO.scroll("Upgrade_OK", "WebButton");
				Browser.WebButton.click("Upgrade_OK");

			} else {
				Continue.set(false);
				System.exit(0);
			}

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);

				if (LData.equalsIgnoreCase(New_PlanName)) {
					if (Action.equalsIgnoreCase("Add")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Continue.set(false);
					}
				}
			}
			if (Row_Count1 <= 4) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.LineItems_Data();

			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.waitforload();
			Test_OutPut += OrderSubmission().split("@@")[1];

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Test_OutPut += "Upgrade Promotion via Account 360 view is done Successfully " + ",";
				Result.fUpdateLog("Upgrade Promotion via Account 360 view is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Upgrade Promotion via Account 360 view Failed" + ",";
				Result.takescreenshot("Upgrade Promotion via Account 360 view Failed");
				Result.fUpdateLog("Upgrade Promotion via Account 360 view Failed");
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
		Result.fUpdateLog("------Upgrade Promotion via Account 360 view Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: SIMSWAP
	 * Arguments			: None
	 * Use 					: Sim Swap from Vanilla
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 09-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String SIMSwap() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Order_no, GetData, SIM, SData = "SIM Card";
		int Inst_RowCount, Col, Col_P, Col_S, Col_SID, Row_Val = 3, Row_Count;

		Result.fUpdateLog("------SIM Swap services------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_SIM").equals(""))) {
				SIM = getdata("New_SIM");
			} else {
				SIM = pulldata("New_SIM");
			}
			Result.fUpdateLog("New_SIM : " + SIM);

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

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
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

			} else {
				CO.InstalledAssertChange("Modify");
			}

			CO.waitforload();
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
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

			CO.scroll("Line_Items", "WebTable");
			Browser.WebButton.waittillvisible("Expand");
			Browser.WebButton.click("Expand");
			Row_Count = Browser.WebTable.getRowCount("Line_Items");

			CO.waitforload();
			Col = CO.Actual_Cell("Line_Items", "Product");
			Col_S = CO.Actual_Cell("Line_Items", "Service Id");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (SData.equalsIgnoreCase(LData)) {
					Row_Val = i;
					break;

				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_S);
			Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", SIM);

			Test_OutPut += OrderSubmission().split("@@")[1];
			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			CO.Action_Update("Update", "");
			Result.takescreenshot("");

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("SIMSWAP is Successful");
			} else
				Status = "FAIL";

		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("SIMSWAP - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Change_MSISDN
	 * Arguments			: None
	 * Use 					: Change MSISDN from Vanilla
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 22-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Change_MSISDN() {
		String Test_OutPut = "", Status = "";
		String Order_no, GetData, New_MSISDN;
		int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S, Col_cat, Col_pri;
		String Reserve, Category = "", StarNumber = null, ReservationToken = "", MSISDN = null;
		int Inst_RowCount, Col, Col_P, Col_SID, Row_Count;

		Result.fUpdateLog("------Change MSISDN services------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("NEW_MSISDN").equals(""))) {
				New_MSISDN = getdata("NEW_MSISDN");
			} else {
				New_MSISDN = pulldata("NEW_MSISDN");
			}
			Result.fUpdateLog("New_MSISDN : " + New_MSISDN);

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			CO.waitforload();
			CO.Title_Select("a", "Home");

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
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

			} else {
				CO.InstalledAssertChange("Modify");
			}

			CO.waitforload();

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitforload();
			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");

			if (CO.isAlertExist()) {
				Continue.set(false);
				System.exit(0);
			}

			Result.takescreenshot("");

			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			int Row = 2;
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_S = CO.Select_Cell("Line_Items", "Service Id");
			Col_V = Col + 2;

			if (!(getdata("ReservationToken").equals(""))) {
				ReservationToken = getdata("ReservationToken");
			} else {
				ReservationToken = pulldata("ReservationToken");
			}

			if (!(getdata("StarNumber").equals(""))) {
				StarNumber = getdata("StarNumber");
			} else if (!(pulldata("StarNumber").equals(""))) {
				StarNumber = pulldata("StarNumber");
			}

			if (ReservationToken != "") {
				Browser.WebButton.click("Customize");
				Browser.WebEdit.waittillvisible("NumberReservationToken");
				Browser.WebEdit.clear("NumberReservationToken");
				Browser.WebEdit.Set("NumberReservationToken", ReservationToken);
				Result.takescreenshot("Providing Number Reservation Token");

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
				Browser.WebTable.SetData("Numbers", Row, COl_STyp, "Service_Type", "Mobile");

				if (!New_MSISDN.equals("")) {
					Reserve = New_MSISDN.substring(3, New_MSISDN.length());
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
				Result.fUpdateLog("Category " + Category);
				Result.takescreenshot("proceeding for Number Reservation");
				Browser.WebButton.click("Reserve");
				CO.waitforload();
				if (CO.isAlertExist()) {
					Result.takescreenshot("Number Resered");
					Result.fUpdateLog("Alert Handled");
				}

				Browser.WebLink.waittillvisible("Line_Items");
				Browser.WebLink.click("Line_Items");
				CO.waitforload();
				// Browser.WebLink.click("LI_Totals");
				CO.waitforload();

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
					CO.waitforload();
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
				Reserve = New_MSISDN.substring(3, New_MSISDN.length());
				CO.Popup_Selection("Number_Selection", "Number", Reserve);
				CO.waitforload();

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
				Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", New_MSISDN);

			}
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			CO.waitforload();

			Test_OutPut += OrderSubmission().split("@@")[1];
			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			CO.RTBScreen(New_MSISDN, "Active");
			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("MSISDN Change is Successful");
			} else {
				Status = "FAIL";
				Result.takescreenshot("MSISDN not Changed");
			}
		}

		catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("MSISDN Change - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Consumer_Migration
	 * Arguments			: None
	 * Use 					: Consumer Migration from Pre_to_Post and Post_to_Pre
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 11-Nov-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Consumer_Migration() {

		String Test_OutPut = "", Status = "";
		String MSISDN, New_PlanName, GetData, Order_no;
		int Col, Col_P;
		Result.fUpdateLog("------Consumer_Migration Event Details------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_PlanName").equals(""))) {
				New_PlanName = getdata("New_PlanName");
			} else {
				New_PlanName = pulldata("New_PlanName");
			}
			Result.fUpdateLog("New_PlanName : " + New_PlanName);
			Planname.set(New_PlanName);

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.AssertSearch(MSISDN, "Active");
			CO.waitforload();

			BillingProfileCreation();
			CO.waitforload();
			int k = 1;
			boolean a = true;
			do {
				k++;
				CO.Text_Select("a", "Account Summary");
				CO.waitforload();
				Result.fUpdateLog("Account Summary Page Loading.....");
				if (Browser.WebLink.exist("Inst_Assert_ShowMore")) {
					a = false;
				} else if (k < 20) {
					a = false;
				}
			} while (a);

			CO.waitforload();
			CO.InstalledAssertChange("New Query                   [Alt+Q]");
			Col = CO.Select_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			CO.Plan_selection(GetData, MSISDN);
			CO.waitforload();
			int j = 1;
			a = true;
			do {
				j++;
				Result.fUpdateLog("PopupQuery_Search Page Loading.....");
				CO.waitforload();
				Result.fUpdateLog("Account Summary Page Loading.....");
				if (Browser.WebEdit.waitTillEnabled("PopupQuery_Search")) {
					a = false;
				} else if (j < 20) {
					a = false;
				}
			} while (a);

			Browser.WebEdit.Set("PopupQuery_Search", New_PlanName);
			String Path[] = Utlities.FindObject("PopupQuery_Search", "WebEdit");
			cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
			Result.takescreenshot("");
			CO.waitforload();

			if (Browser.WebTable.getRowCount("Promotion_Upgrades") >= 2) {
				CO.scroll("Upgrade_OK", "WebButton");
				Browser.WebButton.click("Upgrade_OK");
				int i = 1;
				a = true;
				do {
					i++;
					Result.fUpdateLog("LI_New Page Loading.....");
					if (Browser.WebButton.waitTillEnabled("LI_New")) {
						a = false;
					} else if (i < 20) {
						a = false;
					}
				} while (a);

			} else {
				Continue.set(false);
				System.exit(0);
			}
			CO.waitforload();
			if (Billprofile_No != null) {
				CO.Webtable_Value("Billing Profile", Billprofile_No.get());
			}
			Result.takescreenshot("");
			CO.scroll("Line_Items", "WebTable");
			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			int Col_bp = CO.Actual_Cell("Line_Items", "Billing Profile");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
				if (LData.equalsIgnoreCase(GetData) || LData.equalsIgnoreCase(New_PlanName)) {
					CO.Popup_Click("Line_Items", i, Col_bp);
					CO.waitforload();
					CO.Popup_Selection("Bill_Selection", "Name", Billprofile_No.get());
					Result.takescreenshot("");
				}
				if (LData.equalsIgnoreCase(New_PlanName)) {
					if (Action.equalsIgnoreCase("Add")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Continue.set(false);
					}
				}
			}

			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.waitforload();
			Test_OutPut += OrderSubmission().split("@@")[1];

			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count1 <= 4) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.LineItems_Data();

			CO.ToWait();
			CO.GetSiebelDate();
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
		Result.fUpdateLog("------Consumer_Migration Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Enterprise_Migration
	 * Arguments			: None
	 * Use 					: Enterprise Migration from Pre_to_Post and Post_to_Pre
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 11-Nov-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Enterprise_Migration() {

		String Test_OutPut = "", Status = "";
		String MSISDN, New_PlanName, GetData, Order_no;
		int Col, Col_P;
		Result.fUpdateLog("------Consumer_Migration Event Details------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_PlanName").equals(""))) {
				New_PlanName = getdata("New_PlanName");
			} else {
				New_PlanName = pulldata("New_PlanName");
			}
			Result.fUpdateLog("New_PlanName : " + New_PlanName);
			Planname.set(New_PlanName);

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.AssertSearch(MSISDN, "Active");
			CO.waitforload();
			BillingProfileCreation();
			CO.waitforload();
			int k = 1;
			boolean a = true;
			do {
				k++;
				CO.Text_Select("a", "Account Summary");
				CO.waitforload();
				Result.fUpdateLog("Account Summary Page Loading.....");
				if (Browser.WebLink.exist("Inst_Assert_ShowMore")) {
					a = false;
				} else if (k < 20) {
					a = false;
				}
			} while (a);
			CO.waitforload();

			CO.InstalledAssertChange("New Query                   [Alt+Q]");
			Col = CO.Select_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			CO.Plan_selection(GetData, MSISDN);
			CO.waitforload();
			int j = 1;
			a = true;
			do {
				j++;
				Result.fUpdateLog("PopupQuery_Search Page Loading.....");
				CO.waitforload();
				Result.fUpdateLog("Account Summary Page Loading.....");
				if (Browser.WebEdit.waitTillEnabled("PopupQuery_Search")) {
					a = false;
				} else if (j < 20) {
					a = false;
				}
			} while (a);
			Browser.WebEdit.Set("PopupQuery_Search", New_PlanName);
			String Path[] = Utlities.FindObject("PopupQuery_Search", "WebEdit");
			cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
			Result.takescreenshot("");
			CO.waitforload();

			if (Browser.WebTable.getRowCount("Promotion_Upgrades") >= 2) {
				CO.scroll("Upgrade_OK", "WebButton");
				Browser.WebButton.click("Upgrade_OK");
				int i = 1;
				a = true;
				do {
					i++;
					Result.fUpdateLog("LI_New Page Loading.....");
					if (Browser.WebButton.waitTillEnabled("LI_New")) {
						a = false;
					} else if (i < 20) {
						a = false;
					}
				} while (a);
			} else {
				Continue.set(false);
				System.exit(0);
			}
			CO.waitforload();
			if (Billprofile_No != null) {
				CO.Webtable_Value("Billing Profile", Billprofile_No.get());
			}
			Result.takescreenshot("");
			CO.scroll("Line_Items", "WebTable");
			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			int Col_bp = CO.Actual_Cell("Line_Items", "Billing Profile");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
				if (LData.equalsIgnoreCase(GetData) || LData.equalsIgnoreCase(New_PlanName)) {
					CO.Popup_Click("Line_Items", i, Col_bp);
					CO.waitforload();
					CO.Popup_Selection("Bill_Selection", "Name", Billprofile_No.get());
					Result.takescreenshot("");
				}
				if (LData.equalsIgnoreCase(New_PlanName)) {
					if (Action.equalsIgnoreCase("Add")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Continue.set(false);
					}
				}
			}

			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.waitforload();
			Test_OutPut += OrderSubmission().split("@@")[1];

			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count1 <= 4) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.LineItems_Data();

			CO.ToWait();
			CO.GetSiebelDate();
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
		Result.fUpdateLog("------Enterprise_Migration Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: RealTimeBalance_Screen
	 * Arguments			: None
	 * Use 					: to take RealTimeBalance Screenshort
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String RealTimeBalance_Screen() {
		String Test_OutPut = "", Status = "";
		String MSISDN;
		Result.fUpdateLog("------RealTimeBalance_Screen Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			Result.fUpdateLog("MSISDN : " + MSISDN);
			CO.waitforload();
			CO.RTBScreen(MSISDN, "Active");
			CO.Title_Select("a", "Home");
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------RealTimeBalance_Screen Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Change_SmartLimit
	 * Arguments			: None
	 * Use 					: Change of Smart limit vanilla flow
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String ModifySmartLimit() {
		String Test_OutPut = "", Status = "";
		String MSISDN, GetData = null, Order_no;
		int Inst_RowCount, Col_P, Col_SID, Col, Col_s, row_value = 0;
		String SL_LimitAmount;
		Result.fUpdateLog("------Change SmartLimit Event Details------");
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
			if (!(getdata("Spend_Limit").equals(""))) {
				SL_LimitAmount = getdata("Spend_Limit");
			} else {
				SL_LimitAmount = pulldata("Spend_Limit");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			CO.waitforload();
			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
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

			} else {
				CO.InstalledAssertChange("Modify");
			}

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			// wait
			CO.waitmoreforload();
			CO.Link_Select("Others");
			CO.waitforload();
			CO.Radio_Select("Smart Limit");
			CO.waitforload();
			CO.Addon_Settings("Smart Limit");
			CO.waitforload();
			Result.takescreenshot("");

			CO.waitforload();
			Browser.WebEdit.clear("SL_LimitAmount");
			CO.waitforload();
			Browser.WebEdit.Set("SL_LimitAmount", SL_LimitAmount);
			String SL_Min_Value = Browser.WebEdit.gettext("SL_Min_Value");
			int SL_Min = Integer.parseInt(SL_Min_Value);
			int SL_Limit = Integer.parseInt(SL_LimitAmount);
			if (SL_Limit > SL_Min) {

				Continue.set(true);
			} else {
				Result.fUpdateLog("SL_LimitAmount is less than SL_Min_Value");
				Continue.set(false);

			}
			if (Continue.get()) {

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
				Test_OutPut += OrderSubmission().split("@@")[1];
				CO.waitforload();

				// fetching Order_no
				Order_no = CO.Order_ID();
				Utlities.StoreValue("Order_no", Order_no);
				Test_OutPut += "Order_no : " + Order_no + ",";

				CO.Assert_Search(MSISDN, "Active");
				CO.waitforload();
				CO.Text_Select("a", GetData);
				CO.waitforload();

				int Row_Count = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
				Row_Count = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col = CO.Actual_Cell("Acc_Installed_Assert", "Product");
				Col_s = CO.Actual_Cell("Acc_Installed_Assert", "Special Rating List");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col);
					if (LData.equalsIgnoreCase("Smart Limit")) {
						// Browser.WebTable.click("Acc_Installed_Assert", i, Col_s);
						row_value = i;
						break;

					}
				}

				Browser.WebTable.click("Acc_Installed_Assert", row_value, Col_s);
				CO.waitforload();
				CO.Text_Select("a", "Installed Assets");
				CO.waitforload();
				CO.scroll("Attribute", "WebEdit");
				CO.Title_Select("td", "SL Default Value");
				CO.waitforload();

				String Amt = cDriver.get()
						.findElement(By.xpath("//td[.='SL Limit Amount']/..//td[contains(@id,'Value')]"))
						.getAttribute("title");
				CO.waitforload();
				if (SL_LimitAmount.equalsIgnoreCase(Amt.trim())) {

					Continue.set(true);
				} else {
					Result.fUpdateLog("SL_LimitAmount is less than SL_Min_Value");
					Continue.set(false);

				}

				CO.ToWait();
				CO.GetSiebelDate();
				if (Continue.get()) {
					Status = "PASS";
					Utlities.StoreValue("Sales_OrderNO", Order_no);
					Test_OutPut += "Order_No : " + Order_no + ",";
				} else {
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();

		}
		Result.fUpdateLog("-----Change SmartLimit Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}


	public String ModifySmartLimit_Acc360()

	{
		int Col, Col_s, row_value = 0;
		String SL_LimitAmount = null;

		String MSISDN, GetData = null, Order_no;
		Boolean flag = false;
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Modify Spend Limit via Global Search------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("Spend_Limit").equals(""))) {
				SL_LimitAmount = getdata("Spend_Limit");
			} else {
				SL_LimitAmount = pulldata("Spend_Limit");
			}
			CO.GlobalSearch("MSISDN", MSISDN);
			Browser.WebLink.click("Global_Search");

			CO.waitforload();
			CO.Text_Select("div", "Asset Summary");
			CO.waitforload();
			Browser.WebButton.waittillvisible("SpendLimit");
			CO.waitforload();

			Result.fUpdateLog("Modifying Spend Limit via Account 360");
			Result.takescreenshot("Modifying Spend Limit via Account 360");

			if (Browser.WebButton.exist("SpendLimit") == true) {
				CO.waitforload();
				CO.scroll("SpendLimit", "WebButton");
				CO.waitforload();
				Browser.WebButton.click("SpendLimit");
				CO.waitforload();
				CO.waitforload();

				String x;
				do {
					x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						CO.waitforload();
						Browser.WebButton.click("Suspend");
					}
					CO.waitforload();
				} while (x.isEmpty());

				CO.scroll("Date_Continue", "WebButton");
				Browser.WebButton.click("Date_Continue");

				CO.waitmoreforload();
				Result.takescreenshot("");

				CO.waitforload();
				// Browser.WebEdit.clear("SL_LimitAmount");
				CO.waitforload();
				Browser.WebEdit.Set("SL_LimitAmount", SL_LimitAmount);
				String SL_Min_Value = Browser.WebEdit.gettext("SL_Min_Value");
				int SL_Min = Integer.parseInt(SL_Min_Value);
				int SL_Limit = Integer.parseInt(SL_LimitAmount);
				if (SL_Limit > SL_Min) {

					Continue.set(true);
				} else {
					Result.fUpdateLog("SL_LimitAmount is less than SL_Min_Value");
					Continue.set(false);

				}
				if (Continue.get()) {

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
					Test_OutPut += OrderSubmission().split("@@")[1];
					CO.waitforload();

					// fetching Order_no
					Order_no = CO.Order_ID();
					Utlities.StoreValue("Order_no", Order_no);
					Test_OutPut += "Order_no : " + Order_no + ",";

					CO.Assert_Search(MSISDN, "Active");
					CO.waitforload();
					CO.Text_Select("a", GetData);
					CO.waitforload();

					int Row_Count = Browser.WebTable.getRowCount("Acc_Installed_Assert");
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
					Row_Count = Browser.WebTable.getRowCount("Acc_Installed_Assert");
					Col = CO.Actual_Cell("Acc_Installed_Assert", "Product");
					Col_s = CO.Actual_Cell("Acc_Installed_Assert", "Special Rating List");
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col);
						if (LData.equalsIgnoreCase("Smart Limit")) {
							// Browser.WebTable.click("Acc_Installed_Assert", i, Col_s);
							row_value = i;
							break;

						}
					}

					Browser.WebTable.click("Acc_Installed_Assert", row_value, Col_s);
					CO.waitforload();
					CO.Text_Select("a", "Installed Assets");
					CO.waitforload();
					CO.scroll("Attribute", "WebEdit");
					CO.Title_Select("td", "SL Default Value");
					CO.waitforload();

					String Amt = cDriver.get()
							.findElement(By.xpath("//td[.='SL Limit Amount']/..//td[contains(@id,'Value')]"))
							.getAttribute("title");
					CO.waitforload();
					if (SL_LimitAmount.equalsIgnoreCase(Amt.trim())) {

						Continue.set(true);
					} else {
						Result.fUpdateLog("SL_LimitAmount is less than SL_Min_Value");
						Continue.set(false);

					}

					CO.ToWait();
					CO.GetSiebelDate();
					Utlities.StoreValue("Sales_OrderNO", Order_no);
					Test_OutPut += "Order_No : " + Order_no + ",";
				}

			} else {
				Result.fUpdateLog(
						"Please check the MSISDN Provided --- Spendlimit is not available for the MSISDN Provided");
				Result.takescreenshot(
						"Please check the MSISDN Provided --- Spendlimit is not available for the MSISDN Provided");
				Continue.set(false);
			}

			if (Continue.get() & flag) {
				Test_OutPut += "";

				Result.takescreenshot("Modify Spend Limit via Global Search is Successfull");
				Result.fUpdateLog("Modify Spend Limit via Global Search is Successfull");
				Status = "PASS";
			} else {
				Test_OutPut += "Modify Spend Limit via Global Search Failed" + ",";
				Result.takescreenshot("Modify Spend Limit via Global Search Failed");
				Result.fUpdateLog("Modify Spend Limit via Global Search Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();

		}
		Result.fUpdateLog("-----Modify Spend Limit via Global Search - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}


	/*---------------------------------------------------------------------------------------------------------
	 * Method Name				: BillPayment
	 * Arguments				: None
	 * Use 						: BillPayment
	 * Designed By				: Vinodhini Raviprasad
	 * Last Modified Date 		: 12-Nov-2017
	--------------------------------------------------------------------------------------------------------*/
	public String BillPayment() {

		String Test_OutPut = "", Status = "";
		String MSISDN, GetData, Channel, BillingProfile, BillAmt = "", Pay_Type, Reference;
		int Row_Count, Col_P, Col, Col_C, Col_A, Row = 2;
		Result.fUpdateLog("------BillPayment Event Details------");
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
			if (!(getdata("Pay_Type").equals(""))) {
				Pay_Type = getdata("Pay_Type");
			} else {
				Pay_Type = pulldata("Pay_Type");
			}

			if (!(getdata("Channel").equals(""))) {
				Channel = getdata("Channel");
			} else {
				Channel = pulldata("Channel");
			}
			if (!(getdata("Reference").equals(""))) {
				Reference = getdata("Reference");
			} else {
				Reference = R.nextInt(100000) + pulldata("Reference") + R.nextInt(100000000);
			}
			// Fetching Billing Profile Name from the Provided MSISDN
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			Col_P = CO.Select_Cell("Acc_Installed_Assert", "Billing Profile");// Browser.WebTable.getRowCount("Acc_Installed_Assert");
			CO.waitforload();
			BillingProfile = Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col_P);
			CO.waitforload();
			Result.takescreenshot("Bill No for the MSISDN " + MSISDN + " is " + BillingProfile);

			if (Pay_Type.equalsIgnoreCase("outstanding")) {
				CO.Link_Select("Profiles");
				CO.waitforload();
				Browser.WebButton.click("Profile_Query");
				Col_P = CO.Select_Cell("Bill_Prof", "Name");
				Col = CO.Select_Cell("Bill_Prof", "Status");
				Browser.WebTable.SetData("Bill_Prof", Row, Col_P, "Name", BillingProfile);
				CO.waitforload();
				CO.waitforload();
				if (Browser.WebTable.getRowCount("Bill_Prof") >= 2) {
					Browser.WebTable.click("Bill_Prof", Row, Col);
					Browser.WebTable.clickL("Bill_Prof", Row, Col_P);
				} else
					Continue.set(false);

				CO.waitforload();
				BillAmt = Browser.WebEdit.gettext("Balance");
				Test_OutPut += "Balance: " + BillAmt + ",";
				Result.takescreenshot("Getting Outstanding Balance" + BillAmt);
				CO.Assert_Search(MSISDN, "Active");
				CO.waitforload();
				CO.Text_Select("a", GetData);
				CO.waitforload();

			} else {
				if (!(getdata("BillAmt").equals(""))) {
					BillAmt = getdata("BillAmt");
				} else {
					BillAmt = pulldata("BillAmt");
				}
			}

			CO.Link_Select("Payments");
			CO.waitforload();
			Result.takescreenshot("Account level Payment");
			Col_P = CO.Select_Cell("AccountPayment", "Billing Profile");
			Col_C = CO.Select_Cell("AccountPayment", "Payment_Method");
			Col_A = CO.Select_Cell("AccountPayment", "Payment_Amount");
			CO.waitforobj("Pay_Add", "WebButton");
			Browser.WebButton.click("Pay_Add");
			String Bill_Status = "";
			Row = 2;

			do {
				int Col_S = CO.Actual_Cell("AccountPayment", "Status");
				Bill_Status = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);
				if ((Bill_Status.equalsIgnoreCase("Open"))) {
					break;
				}
			} while (true);

			CO.waitforload();

			Browser.WebTable.SetDataE("AccountPayment", Row, Col_A, "Payment_Amount", BillAmt);
			Browser.WebTable.SetData("AccountPayment", Row, Col_C, "Payment_Method", Channel);
			Browser.WebTable.click("AccountPayment", Row, Col_P);
			Browser.WebTable.SetData("AccountPayment", Row, Col_P, "VFQA_Bill_Prof_Name", BillingProfile);

			CO.isAlertExist();
			if (Channel.equalsIgnoreCase("cash")) {
				CO.scroll("Reference_Number", "WebEdit");
				Browser.WebEdit.Set("Reference_Number", Reference);
			} else if (Channel.equalsIgnoreCase("cheque")) {
				Browser.WebEdit.Set("Cheque_Number", getdata("Cheque_Number"));
				Browser.WebEdit.Set("Bank_Name", getdata("Bank_Name"));

			} else if (Channel.equalsIgnoreCase("online")) {
				Browser.WebEdit.Set("Voucher_Number", getdata("Voucher_Number"));
				Browser.WebEdit.Set("Reference_Number", Reference);
			} else if (Channel.equalsIgnoreCase("voucher")) {
				Browser.WebEdit.Set("Voucher_Number", getdata("Voucher_Number"));
				Browser.WebEdit.Set("Reference_Number", Reference);
			}
			int Col_S = CO.Select_Cell("AccountPayment", "Channel Transaction #");
			String Txn = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);

			Browser.WebButton.click("Bill_Submit");
			CO.waitmoreforload();
			Result.takescreenshot("Bill Submittion for Payment");
			Browser.WebButton.click("Payment_Query");
			Browser.WebTable.SetData("AccountPayment", Row, Col_S, "VFQA_Channel_Transaction__", Txn);
			CO.waitforload();

			Col = CO.Select_Cell("AccountPayment", "Payment #");
			String Payment_Reference = Browser.WebTable.getCellData("AccountPayment", Row, Col);
			Test_OutPut += "Payment Reference Number:" + Payment_Reference + ",";

			Col_S = CO.Select_Cell("AccountPayment", "Status");
			Bill_Status = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);
			if ((Bill_Status.equalsIgnoreCase("Submitted"))) {
				CO.Link_Select("Account Summary");
				CO.waitforload();
				CO.Link_Select("Payments");
				CO.waitforload();
				Browser.WebButton.click("Payment_Query");
				CO.waitforload();
				Browser.WebTable.SetData("AccountPayment", Row, Col, "Payment_Number", Payment_Reference);
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("AccountPayment");
				if (Row_Count == 1)
					Continue.set(false);
				Bill_Status = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);
				Result.takescreenshot("Payment Status Verification" + Payment_Reference);

			} else if ((Bill_Status.equalsIgnoreCase("Success"))) {
				Continue.set(true);
			} else
				Continue.set(false);

			// To verify whether the Payment is reflected in Billing Profile

			CO.Link_Select("Profiles");
			CO.waitforload();
			Browser.WebButton.click("Profile_Query");
			Col_P = CO.Select_Cell("Bill_Prof", "Name");
			Col = CO.Select_Cell("Bill_Prof", "Status");
			Browser.WebTable.SetData("Bill_Prof", Row, Col_P, "Name", BillingProfile);
			Browser.WebTable.click("Bill_Prof", Row, Col);
			Browser.WebTable.clickL("Bill_Prof", Row, Col_P);
			do {
				CO.waitforload();
			} while (!Browser.WebButton.waitTillEnabled("Bill_Valid_Name"));
			CO.waitforload();

			if (Bill_Status.equalsIgnoreCase("success") & Pay_Type.equalsIgnoreCase("outstanding")) {
				String Outstanding = "";
				Outstanding = Browser.WebEdit.gettext("Balance");
				Result.takescreenshot("Outstanding after Payment");
				if (!(Outstanding.equalsIgnoreCase("qr0.00")))
					Continue.set(false);
			}

			CO.TabNavigator("Payments");
			CO.waitforload();
			Col_S = CO.Select_Cell("Payments", "Status");
			Col = CO.Select_Cell("Payments", "Payment #");
			Browser.WebButton.click("Payment_Query");
			CO.waitforload();
			Browser.WebTable.SetData("Payments", 2, Col, "Payment_Number", Payment_Reference);
			CO.waitforload();
			Result.takescreenshot("Payment Verification Bill level");
			CO.waitforload();
			CO.waitmoreforload();
			Row_Count = Browser.WebTable.getRowCount("Payments");

			CO.ToWait();
			if (Continue.get() & (Row_Count > 1)
					& (Bill_Status.equalsIgnoreCase("success") || Bill_Status.equalsIgnoreCase("approved"))) {
				Test_OutPut += "";
				Result.takescreenshot("BillPayment is Successfull");
				Result.fUpdateLog("BillPayment is Successfull");
				Status = "PASS";
			} else {
				Test_OutPut += "BillPayment Failed" + ",";
				Result.takescreenshot("BillPayment Failed");
				Result.fUpdateLog("BillPayment Failed");
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
		Result.fUpdateLog("------BillPayment Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Disconnect
	 * Arguments			: None
	 * Use 					: Disconnection of Active line
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Disconnection() {

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
			// CO.InstalledAssertChange("Disconnect");
			CO.waitforload();
			CO.Webtable_Value("Order Reason", Order_Reason);

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);

				if (Action.equalsIgnoreCase("Delete")) {
					Result.fUpdateLog("Action Update   " + LData + ":" + Action);
				} else {
					Result.fUpdateLog(LData + ":" + Action);
					Continue.set(false);
				}

			}

			Test_OutPut += OrderSubmission().split("@@")[1];
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

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TroubleTicket
	 * Arguments			: None
	 * Use 					: To raise TroubleTicket
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 26-Nov-2017
	--------------------------------------------------------------------------------------------------------*/
	public String TroubleTicket() {
		String Test_OutPut = "", Status = "";
		String MSISDN, TT_No;

		Result.fUpdateLog("------TroubleTicket services------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.TabNavigator("Trouble Tickets");
			CO.waitforload();
			int Row = 2, Col;
			Browser.WebButton.waitTillEnabled("TT_New");
			CO.scroll("TT_New", "WebButton");
			Browser.WebButton.click("TT_New");
			CO.waitforload();

			Col = CO.Actual_Cell("TroubleTicket", "Ticket Id");
			TT_No = Browser.WebTable.getCellData("TroubleTicket", Row, Col);
			Result.takescreenshot("Trouble ticket raised : " + TT_No);
			Utlities.StoreValue("TroubleTicket_No", TT_No);
			Test_OutPut += "Trouble Ticket No: " + TT_No + ",";
			Browser.WebTable.clickL("TroubleTicket", Row, Col);
			CO.waitforload();

			Browser.ListBox.waitTillEnabled("TT_Source");
			if (!(getdata("TT_Source").equals(""))) {
				Browser.ListBox.select("TT_Source", getdata("TT_Source"));
			} else {
				Browser.ListBox.select("TT_Source", pulldata("TT_Source"));
			}

			if (!(getdata("TT_TicketType").equals(""))) {
				Browser.ListBox.select("TT_TicketType", getdata("TT_TicketType"));
			} else {
				Browser.ListBox.select("TT_TicketType", pulldata("TT_TicketType"));
			}

			if (!(getdata("TT_Area").equals(""))) {
				Browser.ListBox.select("TT_Area", getdata("TT_Area"));
			} else {
				Browser.ListBox.select("TT_Area", pulldata("TT_Area"));
			}

			if (!(getdata("TT_Sub_Area").equals(""))) {
				Browser.ListBox.select("TT_SubArea", getdata("TT_Sub_Area"));
			} else {
				Browser.ListBox.select("TT_SubArea", pulldata("TT_Sub_Area"));
			}

			Browser.WebButton.waitTillEnabled("TT_MSISDN");
			Browser.WebButton.click("TT_MSISDN");

			CO.waitforobj("Popup_Go", "WebButton");
			CO.scroll("Popup_Go", "WebButton");

			Browser.ListBox.select("PopupQuery_List", "Serial #");
			Browser.WebEdit.Set("PopupQuery_Search", MSISDN);

			CO.waitforload();
			Browser.WebButton.click("Popup_Go");
			CO.waitforload();
			Result.takescreenshot("");
			// Browser.WebEdit.Set("TT_Description", "");
			CO.Webtable_Value("Contact Role", "");

			CO.Title_Select("a", "Trouble Tickets");
			Browser.WebEdit.waitTillEnabled("TT_NumberSearch");
			CO.scroll("TT_NumberSearch", "WebEdit");
			Browser.WebEdit.Set("TT_NumberSearch", TT_No);
			CO.scroll("TT_SearchGo", "WebButton");
			Browser.WebButton.click("TT_SearchGo");

			do {
				CO.waitforload();
			} while (!Browser.WebTable.waitTillEnabled("TT_Table"));
			Col = CO.Actual_Cell("TT_Table", "Ticket Id");
			Browser.WebTable.click("TT_Table", Row, Col);

			Browser.WebEdit.waitTillEnabled("TT_Description");
			if (!(getdata("TT_Description").equals(""))) {
				Browser.WebEdit.Set("TT_Description", getdata("TT_Description"));
			} else {
				Browser.WebEdit.Set("TT_Description", pulldata("TT_Description"));
			}

			if (!(getdata("TT_Status").equals(""))) {
				Browser.ListBox.select("TT_Status", getdata("TT_Status"));
			} else {
				Browser.ListBox.select("TT_Status", pulldata("TT_Status"));
			}
			CO.waitforload();
			Result.takescreenshot("Resolved");
			CO.Webtable_Value("Contact Role", "");

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "TroubleTicket is done Successfully " + ",";
				Result.fUpdateLog("TroubleTicket is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "TroubleTicket Failed" + ",";
				Result.takescreenshot("TroubleTicket Failed");
				Result.fUpdateLog("TroubleTicket Failed");
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
		Result.fUpdateLog("------TroubleTicket Services Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name             : Discounts
	 * Arguments               : None
	 * Use                     : Plan level Discounts
	 * Designed By             : Vinodhini Raviprasad
	 * Last Modified Date      : 12-Nov-2017
	 --------------------------------------------------------------------------------------------------------*/
	public String Discounts() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Discount, GetData, Order_no;
		int Inst_RowCount, Col_P, Col_SID;

		Result.fUpdateLog("------Discounts ------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("Discounts").equals(""))) {
				Discount = getdata("Discounts");
			} else {
				Discount = pulldata("Discounts");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitmoreforload();
			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
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

			} else {
				CO.InstalledAssertChange("Modify");
			}

			CO.waitforload();
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			/*
			 * int Row_Count = Browser.WebTable.getRowCount("Line_Items"); Col =
			 * CO.Select_Cell("Line_Items", "Product"); Col_V = Col + 2;
			 * 
			 * for (int i = 2; i <= Row_Count; i++) { String LData =
			 * Browser.WebTable.getCellData("Line_Items", i, Col); if
			 * (GetData.equals(LData)) { Row_Val = i; break; } }
			 * Browser.WebTable.click("Line_Items", Row_Val, Col_V);
			 * Result.fUpdateLog("------Customising to Add Discount ------");
			 * CO.Text_Select("span", "Customize");
			 */
			CO.waitforload();
			if (TestCaseN.get().equalsIgnoreCase("PlanDiscount")) {
				Result.fUpdateLog("------Customising to Add Plan Discount ------");
				String PlanName;
				if (!(getdata("PlanBundle").equals(""))) {
					PlanName = getdata("PlanBundle");
				} else {
					PlanName = pulldata("PlanBundle");
				}

				CO.waitforload();
				CO.Text_Select("a", "Mobile Plans");
				CO.waitforload();
				CO.Link_Select(PlanName);
				CO.waitforload();
				// CO.Radio_Select(PlanName);
				CO.waitforload();
				Result.takescreenshot("Customising to Select Discounts");
				/*
				 * WebElement Custom =
				 * cDriver.get().findElement(By.xpath("//i[@class='siebui-icon-settings']"));
				 * ((RemoteWebDriver)
				 * cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", Custom);
				 * cDriver.get().findElement(By.xpath("//i[@class='siebui-icon-settings']")).
				 * click();
				 */
				CO.waitforload();
				CO.Radio_Select(Discount);
				CO.waitforload();
				Result.fUpdateLog("------Discount Selected ------");
			} else {
				Result.fUpdateLog("------Customising to Add Addon Discount ------");
				Result.takescreenshot("Addon Level Discount");
				String Addon, AddonTab;
				if (!(getdata("Addon").equals(""))) {
					Addon = getdata("Addon");
				} else {
					Addon = pulldata("Addon");
				}
				if (!(getdata("AddonTab").equals(""))) {
					AddonTab = getdata("AddonTab");
				} else {
					AddonTab = "Paid Addons";
				}

				CO.Link_Select(AddonTab);
				CO.Discounts(Addon, Discount);
				CO.waitforload();
				Result.fUpdateLog("------Discount Selected ------");
			}

			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			Result.takescreenshot("Discounts Done");
			CO.waitforload();
			CO.Text_Select("button", "Done");
			CO.waitforload();
			if (CO.isAlertExist())
				Continue.set(false);

			Browser.WebButton.waittillvisible("Validate");
			Test_OutPut += OrderSubmission().split("@@")[1];

			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Discounts is done Successfully " + ",";
				Result.fUpdateLog("Discounts is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Discounts Failed" + ",";
				Result.takescreenshot("Discounts Failed");
				Result.fUpdateLog("Discounts Failed");
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
		Result.fUpdateLog("------Discounts Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TransferOfService
	 * Arguments			: None
	 * Use 					: Transfer of active services
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 03-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	public String TransferOfService() {
		String Test_OutPut = "", Status = "";
		String Order_no, MSISDN, Account_No = "", GetData, Bil_Profile = "", Payment_Method = "", Pymt_Type = "",
				BpProfile = "", New_ServiceId = "", PSMSIDN = "";
		int Inst_RowCount, Col_P, Col_Type1, Row = 2, Col_Type = 0, Col_Nam = 0, Col, Col_Sid, Col_Val = 0, Count = 0;
		int Row_Count, a = 0;
		Result.fUpdateLog("------Transfer of active services------");
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
			// Continue.set(true);
			// Bil_Profile ="1-4FX8U44";
			CO.AssertSearch(MSISDN, "Active");
			String Primary = Browser.WebEdit.gettext("Primary_MSISDN");
			int Flag = 0;
			if (!Primary.equalsIgnoreCase("") && (Primary.equalsIgnoreCase(MSISDN))) {

				Browser.WebButton.click("Profile_Tab");
				Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
				String BP_Name[] = new String[Row_Count];
				int j = 0;
				if (Row_Count >= Row) {
					Col_Val = CO.Actual_Cell("Bill_Prof", "Payment Type");
					Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Val);
						if (LData.equalsIgnoreCase("Prepaid")) {
							BP_Name[j] = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Nam);
							j++;

						}

					}
				}

				a = BP_Name.length;
				Browser.WebLink.click("Acc_Summary");
				Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
				Col_Val = CO.Actual_Cell("Installed_Assert", "Service ID");
				Col_Nam = CO.Actual_Cell("Installed_Assert", "Billing Profile");
				int s = 0;

				for (int i = 2; i <= Row_Count; i++) {
					String SID = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
					String BpNam = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Nam);
					if (!SID.equals("") && !(SID.equalsIgnoreCase(Primary))) {
						for (int k = 0; k < a; k++) {
							if (!(BpNam.equalsIgnoreCase(BP_Name[k]))) {
								Flag = 1;
								break;
							}
						}
						if (Flag == 1) {
							break;
						}
					}
				}
			}
			CO.waitforload();
			if (Flag == 1) {
				Result.fUpdateLog("Please change the Primary MSISDN and ReExecute it");
				Test_OutPut += "Please change the Primary MSISDN and ReExecute it" + ",";
			} else {
				CO.Assert_Search(MSISDN, "Active");
				Col_Nam = CO.Select_Cell("Installed_Assert", "Billing Profile");
				Bil_Profile = Browser.WebTable.getCellData("Installed_Assert", Row, Col_Nam);

				CO.TabNavigator("Profiles");

				CO.waitforload();

				Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
				Col_Type = CO.Select_Cell("Bill_Prof", "Payment Type");
				Col_Type1 = CO.Select_Cell("Bill_Prof", "Payment Method");
				Browser.WebButton.click("Profile_Query");
				CO.waitforload();
				Browser.WebTable.SetDataE("Bill_Prof", Row, Col_Nam, "Name", Bil_Profile);
				CO.waitforload();
				Browser.WebButton.click("BillingProfile_Go");
				Inst_RowCount = Browser.WebTable.getRowCount("Bill_Prof");
				if (Inst_RowCount == 2) {
					Pymt_Type = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Type);
					Payment_Method = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Type1);
				} else {
					Continue.set(false);
				}

				CO.waitforload();
				CO.Title_Select("a", "Home");
				CO.waitforload();
				// Create new contact and account
				if (TestCaseN.get().equals("NewAccount")) {
					ContactCreation();
					AccountCreation();
					Account_No = Utlities.FetchStoredValue("TransferOfService", "NewAccount", "Account_No");

					TOS_BillingProfileCreation(Account_No, Pymt_Type, Payment_Method);
				} else if (TestCaseN.get().equals("ExistingAccount")) {

					if (!(getdata("Ext_AccountNo").equals(""))) {
						Account_No = getdata("Ext_AccountNo");
					} else {
						Account_No = pulldata("Ext_AccountNo");
					}
					TOS_BillingProfileCreation(Account_No, Pymt_Type, Payment_Method);
				}
				Test_OutPut += "Account_No : " + Account_No + ",";

				CO.Assert_Search(MSISDN, "Active");
				CO.waitforload();
				CO.Text_Select("a", GetData);
				CO.waitforload();

				// Click on Modify Assert
				if (Browser.WebButton.exist("Assert_Modify")) {

					Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
					Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
					// Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
					int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
					// To Find the Record with Mobile Service Bundle and MSISDN
					for (int i = 2; i <= Inst_RowCount; i++)
						if (!Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)) {
							CO.waitforload();
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

				} else {
					CO.InstalledAssertChange("Modify");
				}

				CO.scroll("Date_Continue", "WebButton");
				Browser.WebButton.click("Date_Continue");
				CO.waitforload();

				CO.waitforload();
				if (CO.isAlertExist())
					Continue.set(false);

				int Col_SA = CO.Actual_Cell("Line_Items", "Service Account");
				int Col_BA = CO.Actual_Cell("Line_Items", "Billing Account");
				int Col_BP = CO.Actual_Cell("Line_Items", "Billing Profile");
				int Col_OA = CO.Actual_Cell("Line_Items", "Owner Account");

				CO.waitforload();
				Browser.WebButton.click("Order_Account");
				CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);
				CO.waitforload();

				Browser.WebButton.click("Billing_Profile");
				CO.Popup_Selection("Bill_Selection", "Name", Billprofile_No.get());
				// CO.Webtable_Value("Billing Profile", Billprofile_No);
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				for (int i = 2; i <= Row_Count; i++) {
					CO.Popup_Click1("Line_Items", i, Col_SA);
					CO.waitforload();
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_BA);
					CO.waitforload();
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_OA);
					CO.waitforload();
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click("Line_Items", i, Col_BP);
					CO.waitforload();
					CO.Popup_Selection("Bill_Selection", "Name", Billprofile_No.get());
					Test_OutPut += "Billprofile_No : " + Billprofile_No + ",";
				}

				Browser.WebButton.waittillvisible("Validate");
				Test_OutPut += OrderSubmission().split("@@")[1];
				Order_no = CO.Order_ID();
				Utlities.StoreValue("Order_no", Order_no);
				Test_OutPut += "Order_no : " + Order_no + ",";
				int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count1 <= 4) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				CO.Action_Update("Update", MSISDN);

				// Transfer of Service Validation

				CO.Assert_Search(MSISDN, "Active");
				CO.waitforload();

				if (!Browser.WebEdit.gettext("Account_No").equalsIgnoreCase(Account_No))
					Continue.set(false);

				CO.ToWait();
				CO.GetSiebelDate();
			}

			if (Continue.get()) {
				Test_OutPut += "Transfer of Service is done Successfully " + ",";
				Result.fUpdateLog("Transfer of Service is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Transfer of Service Failed" + ",";
				Result.takescreenshot("Transfer of Service Failed");
				Result.fUpdateLog("Transfer of Service Failed");
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
		Result.fUpdateLog("------Transfer of Services Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TOS_BillingProfileCreation
	 * Arguments			: None
	 * Use 					: Creates a Billing Profile in the existing Account for Vanilla Journey
	 * Designed By			: Lavannya M
	 * Last Modified Date 	: 05-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public String TOS_BillingProfileCreation(String Account_No, String Payment_Type, String Payment_Method) {
		String Test_OutPut = "", Status = "";
		String Bill_No = null;
		int Col_Nam, Row_va = 0;
		Result.fUpdateLog("------Billing Profile Creation Event Details------");
		try {
			String Exi = Account_No;
			if (!Exi.equals("")) {
				CO.Account_Search(Exi);
				Utlities.StoreValue("Account_No", Exi);
				Test_OutPut += "Account_No : " + Exi + ",";
				CO.waitforload();
			}
			if (Continue.get()) {
				CO.scroll("Profile_Tab", "WebButton");
				do {
					Browser.WebButton.click("Profile_Tab");
					CO.waitforload();
					/*
					 * if (Browser.WebEdit.waitTillEnabled("BP_Valid_Name")) { j = 0; break; }
					 */

				} while (!Browser.WebEdit.waitTillEnabled("BP_Valid_Name"));
				Browser.WebEdit.waittillvisible("BP_Valid_Name");

				CO.waitforload();
				int Row = 2, Col_Val = 0, Row_Count;

				String Bill_NewProfile = "No";
				if (!(getdata("Bill_NewProfile").equals(""))) {
					Bill_NewProfile = getdata("Bill_NewProfile");
				} else if (!(pulldata("Bill_NewProfile").equals(""))) {
					Bill_NewProfile = pulldata("Bill_NewProfile");
				}

				CO.waitforobj("Bill_Add", "WebButton");
				Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
				if (Row_Count >= Row) {
					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Type");
					Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Val);
						if (Payment_Type.equalsIgnoreCase(LData)) {
							Bill_No = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Nam);
							break;
						}
						Row_va = i;
					}
				}

				if ((Row_Count < Row) || Bill_NewProfile.equals("Yes") || Row_Count == Row_va) {
					Browser.WebButton.waittillvisible("Bill_Add");
					CO.scroll("Bill_Add", "WebButton");
					int Row_Ct = Browser.WebTable.getRowCount("Bill_Prof");
					Browser.WebButton.click("Bill_Add");
					do {
						int Row_C = Browser.WebTable.getRowCount("Bill_Prof");
						if (Row_C > Row_Ct) {
							break;
						}
					} while (true);

					CO.waitforload();

					Browser.WebTable.waittillvisible("Bill_Prof");
					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Type");
					Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Type", Payment_Type);

					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Method");
					Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Method", Payment_Method);

					CO.isAlertExist();

					if (Payment_Type.equalsIgnoreCase("Postpaid")) {
						Col_Val = CO.Select_Cell("Bill_Prof", "Bill Media");
						if (!(getdata("Bill_Media").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Media_Type", getdata("Bill_Media"));
						} else if (!(pulldata("Bill_Media").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Media_Type", pulldata("Bill_Media"));
						}

						Col_Val = CO.Select_Cell("Bill_Prof", "Bill Type");
						if (!(getdata("Bill_Type").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bill_Type", getdata("Bill_Type"));
						} else if (!(pulldata("Bill_Type").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bill_Type", pulldata("Bill_Type"));
						}

					}

					Col_Val = CO.Select_Cell("Bill_Prof", "Language");
					if (!(getdata("Bill_Lang").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bank_Language_Code", getdata("Bill_Lang"));
					} else if (!(pulldata("Bill_Lang").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bank_Language_Code",
								pulldata("Bill_Lang"));
					}

					int Col_v;
					Col_v = CO.Actual_Cell("Bill_Prof", "Name");
					Bill_No = Browser.WebTable.getCellData("Bill_Prof", Row, Col_v);
				}

				Billprofile_No.set(Bill_No);
				Utlities.StoreValue("Billing_NO", Bill_No);
				Test_OutPut += "Billing_NO : " + Bill_No + ",";

				Browser.WebButton.waittillvisible("Orders_Tab");
			}
			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("Billing Profile Created Billing_NO : " + Bill_No);
			} else {
				Result.takescreenshot("Orders Tab not exist");
				Test_OutPut += "Orders Tab not exist" + ",";
				Result.fUpdateLog("Orders Tab not exist");
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
		Result.fUpdateLog("------Billing Profile Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Suspend
	 * Arguments			: None
	 * Use 					: Suspend a Active Plan
	 * Designed By			: Vinodhini Raviprasad
	 * Latest Modified By	: Sravani
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Suspension() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Resume_Date, Order_no, GetData;
		int Col_Resume, Row = 2;
		Result.fUpdateLog("------Suspend Event Details------");
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

			if (Browser.WebButton.exist("Suspend")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				int Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");

				Result.fUpdateLog(Col_P + "," + Col_SID);
				Result.fUpdateLog(Browser.WebTable.getCellData("Acc_Installed_Assert", 3, Col_P));
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P)
							.equalsIgnoreCase("Mobile Service Bundle")) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}

				// CO.InstalledAssertChange("Suspend");
				CO.scroll("Suspend", "WebButton");
				Browser.WebButton.click("Suspend");
			} else {
				CO.InstalledAssertChange("InstalledAssertChange");
			}
			CO.waitforload();

			/*
			 * CO.scroll("Due_Date_chicklet", "WebButton");
			 * Browser.WebButton.click("Due_Date_chicklet");
			 */
			CO.waitforload();
			String x;
			do {
				x = Browser.WebEdit.gettext("Due_Date");
				if (!x.contains("/")) {
					Browser.WebButton.click("Date_Cancel");
					CO.waitforload();
					Browser.WebButton.click("Suspend");
				}
				CO.waitforload();
			} while (x.isEmpty());

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			CO.scroll("Resume_Date", "WebButton");
			Col_Resume = CO.Select_Cell("Line_Items", "Resume Date");
			Browser.WebTable.click("Line_Items", Row, Col_Resume);

			DateFormat ResumeDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
			Calendar cals = Calendar.getInstance();
			cals.add(Calendar.MONTH, 1);
			Resume_Date = ResumeDate.format(cals.getTime()).toString();
			Browser.WebTable.SetDataE("Line_Items", Row, Col_Resume, "Scheduled_Ship_Date", Resume_Date);
			Result.fUpdateLog(Resume_Date);

			// Result.fUpdateLog(CO.Col_Data(Col_Resume).trim());
			Result.takescreenshot("");

			CO.scroll("Service", "WebButton");
			CO.scroll("Line_Items", "WebLink");

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count1 <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.Action_Update("Suspend", MSISDN);

			Test_OutPut += OrderSubmission().split("@@")[1];
			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			Result.takescreenshot("");

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Test_OutPut += "Suspend the Plan is done Successfully " + ",";
				Result.fUpdateLog("Suspend the Plan is done Successfully ");
				Status = "PASS";
			} else {
				Result.fUpdateLog("Suspenstion Failed");
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
		Result.fUpdateLog("Suspend Login Event Details - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Resume
	 * Arguments			: None
	 * Use 					: Resume the Suspended Plan
	 * Designed By			: Vinodhini Raviprasad
	 * Latest Modified By	: Sravani
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Resume() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Resume_Date, Order_no, GetData;
		int Col_Resume, Row = 2;
		Result.fUpdateLog("------Resume Event Details------");
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
			CO.Assert_Search(MSISDN, "Suspended");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			if (Browser.WebButton.exist("Resume")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				for (int i = 2; i <= Inst_RowCount; i++) {
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P)
							.equalsIgnoreCase("Mobile Service Bundle")) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				}
				CO.scroll("Resume", "WebButton");
				Browser.WebButton.click("Resume");
			} else {
				CO.InstalledAssertChange("Resume");
			}
			CO.waitforload();
			/*
			 * CO.scroll("Due_Date_chicklet", "WebButton");
			 * Browser.WebButton.click("Due_Date_chicklet");
			 */
			CO.waitforload();
			String x;
			do {
				x = Browser.WebEdit.gettext("Due_Date");
				if (!x.contains("/")) {
					Browser.WebButton.click("Date_Cancel");
					CO.waitforload();
					Browser.WebButton.click("Resume");
				}
				CO.waitforload();
			} while (x.isEmpty());

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			CO.scroll("Resume_Date", "WebButton");
			Col_Resume = CO.Select_Cell("Line_Items", "Resume Date");

			Browser.WebTable.click("Line_Items", Row, Col_Resume);
			DateFormat ResumeDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
			Calendar cals = Calendar.getInstance();
			cals.add(Calendar.DATE, 1);
			Resume_Date = ResumeDate.format(cals.getTime()).toString();
			Result.fUpdateLog(Resume_Date);

			Browser.WebTable.SetDataE("Line_Items", Row, Col_Resume, "Scheduled_Ship_Date", Resume_Date);
			Result.fUpdateLog(CO.Col_Data(Col_Resume).trim());

			Result.takescreenshot("");
			CO.scroll("Ful_Status", "WebButton");
			CO.scroll("Service", "WebButton");
			CO.scroll("Line_Items", "WebLink");

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count1 <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.Action_Update("Resume", MSISDN);
			// Test_OutPut += OrderSubmission().split("@@")[1];

			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			Result.takescreenshot("");

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Test_OutPut += "Resume Plan is done Successfully " + ",";
				Result.fUpdateLog("Resume Plan is done Successfully ");
				Status = "PASS";
			} else {
				Result.fUpdateLog("Resume Failed");
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
		Result.fUpdateLog("Resume Event Details - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: SiebleValidation
	 * Arguments			: None
	 * Use 					: Checking Whether MSISDN is Active
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 14-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public String SiebleValidation() {
		String Test_OutPut = "", Status = "";
		String MSISDN;
		Result.fUpdateLog("------SiebleValidation------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			Result.fUpdateLog("MSISDN : " + MSISDN);
			CO.waitforload();
			int Row = 2, Col;
			Browser.WebLink.waittillvisible("VQ_Assert");
			Browser.WebLink.click("VQ_Assert");
			Browser.WebLink.waittillvisible("Assert_Search");
			CO.waitforload();
			CO.scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			CO.waitforload();

			// Installed_Assert
			Col = CO.Actual_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = CO.Actual_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", Status);
			Col = CO.Actual_Cell("Assert", "Product");
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			CO.waitforload();
			Col = CO.Actual_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1)
				Browser.WebTable.clickL("Assert", Row, Col);
			else
				Continue.set(false);

			CO.waitforload();

			Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");
			Result.takescreenshot("");
			// Browser.WebLink.click("Inst_Assert_ShowMore");
			CO.waitforload();
			CO.InstalledAssertChange("New Query                   [Alt+Q]");
			Col = CO.Actual_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");
			CO.waitforload();
			// Browser.WebTable.Expand("Installed_Assert", i, 1);
			Result.takescreenshot("");
			// }

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Order has Scucessfully Completed " + ",";
				Result.fUpdateLog("Order has Scucessfully Completed ");
				Status = "PASS";
			} else {
				Test_OutPut += "Order Failed" + ",";

			}
		} catch (Exception e) {
			Continue.set(false);
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------SiebleValidation Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TransferOfOwnership
	 * Arguments			: None
	 * Use 					: Transfer of active services
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 25-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public String TransferOfOwnership_C2C() {
		String Test_OutPut = "", Status = "", BP_Name = null;
		String Account_No = "", GetData, Bil_Profile = "", Payment_Method = null, Pymt_Type = null, PM_MSISDN = null,
				Ac_No;
		int Inst_RowCount, Col_P, Col_Type1, Row = 2, Col_Type = 0, Col_Nam = 0, k, Row_Count, Col_Val;
		Result.fUpdateLog("------Transfer of active services------");
		try {

			if (!(getdata("AccountNo").equals(""))) {
				Ac_No = getdata("AccountNo");
			} else {
				Ac_No = pulldata("AccountNo");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			CO.Account_Search(Ac_No);
			Browser.WebButton.click("Profile_Tab");
			Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
			if (Row_Count >= Row) {
				Col_Val = CO.Actual_Cell("Bill_Prof", "Primary");
				Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Val);
					if (LData.equalsIgnoreCase("Checked")) {
						BP_Name = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Nam);
						break;
					}

				}
			}

			Browser.WebLink.click("Acc_Summary");
			Result.fUpdateLog("------Account Summary Tab------");
			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			Col_Val = CO.Actual_Cell("Installed_Assert", "Service ID");
			int Count = 0;
			for (int i = 2; i <= Row_Count; i++) {
				String SID = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
				if (!SID.equals("")) {
					Count = Count + 1;
				}
			}

			String MSD[] = new String[Count];

			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			Col_Val = CO.Actual_Cell("Installed_Assert", "Service ID");
			Col_Nam = CO.Select_Cell("Installed_Assert", "Billing Profile");
			int s = 0;
			int Flag = 0;
			for (int i = 2; i <= Row_Count; i++) {
				String SID = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
				String BpNam = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Nam);
				if (!SID.equals("")) {
					if (BpNam.equalsIgnoreCase(BP_Name) & (Flag == 0)) {

						PM_MSISDN = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
						Flag = 1;

					} else {
						MSD[s] = SID;
						s = s + 1;
					}
				}
				if ((i == Row_Count) && (Flag == 1)) {
					MSD[s] = PM_MSISDN;
					break;
				}
			}
			int a = MSD.length;
			String PT[] = new String[a];
			String PM[] = new String[a];
			String Bill_NO[] = new String[a];
			String Order_no[] = new String[a];
			// String PT[] = null,PM[] = null;

			for (k = 0; k < a; k++) {
				CO.waitforload();
				CO.Assert_Search(MSD[k], "Active");
				Result.fUpdateLog("------Search with Serice id-----" + MSD[k]);
				CO.waitforload();
				CO.Text_Select("a", GetData);

				Col_Nam = CO.Select_Cell("Acc_Installed_Assert", "Billing Profile");
				Bil_Profile = Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col_Nam);

				CO.TabNavigator("Profiles");

				CO.waitforload();

				Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
				Col_Type = CO.Select_Cell("Bill_Prof", "Payment Type");
				Col_Type1 = CO.Select_Cell("Bill_Prof", "Payment Method");
				Browser.WebButton.click("Profile_Query");
				CO.waitforload();
				Browser.WebTable.SetDataE("Bill_Prof", Row, Col_Nam, "Name", Bil_Profile);
				CO.waitforload();
				Browser.WebButton.click("BillingProfile_Go");
				Inst_RowCount = Browser.WebTable.getRowCount("Bill_Prof");

				if (Inst_RowCount == 2) {
					CO.waitforload();
					Pymt_Type = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Type);
					Payment_Method = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Type1);

				} else {
					Continue.set(false);
				}
				CO.waitforload();
				PT[k] = Pymt_Type;
				PM[k] = Payment_Method;
				CO.waitforload();
				CO.Title_Select("a", "Home");
				CO.waitforload();
			}
			// Create new contact and account
			int POFlag = 0, PrFlag = 0;
			if (TestCaseN.get().equals("NewAccount")) {
				ContactCreation();
				AccountCreation();
				Account_No = New_Account.get();

				for (k = 0; k < MSD.length; k++) {
					if ((PT[k].equalsIgnoreCase("Postpaid")) && (POFlag == 0)) {
						TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
						POFlag = 1;
					} else if ((PT[k].equalsIgnoreCase("Prepaid")) && (PrFlag == 0)) {
						TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
						PrFlag = 1;
					}
					Bill_NO[k] = Billprofile_No.get();
				}
			} else if (TestCaseN.get().equals("ExistingAccount")) {

				if (!(getdata("Ext_AccountNo").equals(""))) {
					Account_No = getdata("Ext_AccountNo");
				} else {
					Account_No = pulldata("Ext_AccountNo");
				}
				for (k = 0; k < MSD.length; k++) {

					TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
					Bill_NO[k] = Billprofile_No.get();
				}

			}
			Test_OutPut += "Account_No : " + Account_No + ",";
			for (k = 0; k < MSD.length; k++) {
				CO.Assert_Search(MSD[k], "Active");
				CO.waitforload();
				Utlities.StoreValue("Service_Id", MSD[k]);
				Test_OutPut += "Service_Id : " + MSD[k] + ",";
				CO.Text_Select("a", GetData);
				CO.waitforload();
				// Click on Modify Assert
				if (Browser.WebButton.exist("Assert_Modify")) {

					Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
					Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
					int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
					for (int i = 2; i <= Inst_RowCount; i++)
						if (!Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)) {
							CO.waitforload();
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

				} else {
					CO.InstalledAssertChange("Modify");
				}
				Result.fUpdateLog("------Modification on Service id-----" + MSD[k]);
				CO.scroll("Date_Continue", "WebButton");
				Browser.WebButton.click("Date_Continue");
				CO.waitmoreforload();

				CO.waitforload();
				int Col_SA = CO.Actual_Cell("Line_Items", "Service Account");
				int Col_BA = CO.Actual_Cell("Line_Items", "Billing Account");
				int Col_BP = CO.Actual_Cell("Line_Items", "Billing Profile");
				int Col_OA = CO.Actual_Cell("Line_Items", "Owner Account");

				CO.waitforload();
				Browser.WebButton.click("Order_Account");

				CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);
				CO.waitforload();

				Browser.WebButton.click("Billing_Profile");
				Result.fUpdateLog("------Changing Billing_Profile  for the Service -----" + MSD[k]);
				CO.Popup_Selection("Bill_Selection", "Name", Bill_NO[k]);

				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				for (int i = 2; i <= Row_Count; i++) {
					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_SA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Service Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_BA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Billing Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_OA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Owner Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click("Line_Items", i, Col_BP);
					CO.waitforload();
					Result.fUpdateLog("------Changing Billing_Profile  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Bill_Selection", "Name", Bill_NO[k]);
					Test_OutPut += "Billprofile_No : " + Bill_NO[k] + ",";
				}

				Browser.WebButton.waittillvisible("Validate");

				Order_no[k] = CO.Order_ID();
				Utlities.StoreValue("Order_no", Order_no[k]);
				Test_OutPut += "Order_no : " + Order_no[k] + ",";

				Test_OutPut += OrderSubmission().split("@@")[1] + ",";

				int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count1 <= 4) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				CO.Action_Update("Update", MSD[k]);
				CO.waitforload();
				CO.waitforload();
				CO.Title_Select("a", "Home");
				CO.waitforload();

			}
			CO.Account_Search(Account_No);
			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Test_OutPut += "Transfer of Ownership is done Successfully " + ",";
				Result.fUpdateLog("Transfer of Ownership is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Transfer of Ownership Failed" + ",";
				Result.takescreenshot("Transfer of Ownership Failed");
				Result.fUpdateLog("Transfer of Ownership Failed");
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
		Result.fUpdateLog("------Transfer of Services Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TransferOfOwnership_E2E
	 * Arguments			: None
	 * Use 					: Transfer of active services enterprise to enterprise
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 01-Mar-2018
	--------------------------------------------------------------------------------------------------------*/
	public String TransferOfOwnership_E2E() {
		String Test_OutPut = "", Status = "", BP_Name = null;
		String Account_No = "", GetData, Bil_Profile = "", Payment_Method = null, Pymt_Type = null, PM_MSISDN = null,
				Ac_No;
		int Inst_RowCount, Col_P, Col_Type1, Row = 2, Col_Type = 0, Col_Nam = 0, k, Row_Count, Col_Val;
		Result.fUpdateLog("------Transfer of Ownership - Enterprise to Enterprise------");
		try {

			if (!(getdata("AccountNo").equals(""))) {
				Ac_No = getdata("AccountNo");
			} else {
				Ac_No = pulldata("AccountNo");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			CO.Account_Search(Ac_No);
			Browser.WebButton.click("Profile_Tab");
			Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
			if (Row_Count >= Row) {
				Col_Val = CO.Actual_Cell("Bill_Prof", "Primary");
				Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Val);
					if (LData.equalsIgnoreCase("Checked")) {
						BP_Name = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Nam);
						break;
					}

				}
			}

			Browser.WebLink.click("Acc_Summary");
			Result.fUpdateLog("------Account Summary Tab------");
			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			Col_Val = CO.Actual_Cell("Installed_Assert", "Service ID");
			int Count = 0;
			for (int i = 2; i <= Row_Count; i++) {
				String SID = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
				if (!SID.equals("")) {
					Count = Count + 1;
				}
			}

			String MSD[] = new String[Count];

			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			Col_Val = CO.Actual_Cell("Installed_Assert", "Service ID");
			Col_Nam = CO.Select_Cell("Installed_Assert", "Billing Profile");
			int s = 0;
			int Flag = 0;
			for (int i = 2; i <= Row_Count; i++) {
				String SID = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
				String BpNam = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Nam);
				if (!SID.equals("")) {
					if (BpNam.equalsIgnoreCase(BP_Name) & (Flag == 0)) {

						PM_MSISDN = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
						Flag = 1;

					} else {
						MSD[s] = SID;
						s = s + 1;
					}
				}
				if ((i == Row_Count) && (Flag == 1)) {
					MSD[s] = PM_MSISDN;
					break;
				}
			}
			int a = MSD.length;
			String PT[] = new String[a];
			String PM[] = new String[a];
			String Bill_NO[] = new String[a];
			String Order_no[] = new String[a];
			// String PT[] = null,PM[] = null;

			for (k = 0; k < a; k++) {
				CO.waitforload();
				CO.Assert_Search(MSD[k], "Active");
				Result.fUpdateLog("------Search with Serice id-----" + MSD[k]);
				CO.waitforload();
				CO.Text_Select("a", GetData);

				Col_Nam = CO.Select_Cell("Acc_Installed_Assert", "Billing Profile");
				Bil_Profile = Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col_Nam);

				CO.TabNavigator("Profiles");

				CO.waitforload();
				CO.Text_Select("a", "Billing Profile");
				Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
				Col_Type = CO.Select_Cell("Bill_Prof", "Payment Type");
				Col_Type1 = CO.Select_Cell("Bill_Prof", "Payment Method");
				Browser.WebButton.click("Profile_Query");
				CO.waitforload();
				Browser.WebTable.SetDataE("Bill_Prof", Row, Col_Nam, "Name", Bil_Profile);
				CO.waitforload();
				Browser.WebButton.click("BillingProfile_Go");
				Inst_RowCount = Browser.WebTable.getRowCount("Bill_Prof");

				if (Inst_RowCount == 2) {
					CO.waitforload();
					Pymt_Type = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Type);
					Payment_Method = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Type1);

				} else {
					Continue.set(false);
				}
				CO.waitforload();
				PT[k] = Pymt_Type;
				PM[k] = Payment_Method;
				CO.waitforload();
				CO.Title_Select("a", "Home");
				CO.waitforload();
			}
			// Create new contact and account
			int POFlag = 0, PrFlag = 0;
			if (TestCaseN.get().equals("NewAccount")) {
				Entp_AccountCreation();
				Entp_ContactCreation();

				Account_No = New_Account.get();

				for (k = 0; k < MSD.length; k++) {
					if ((PT[k].equalsIgnoreCase("Postpaid")) && (POFlag == 0)) {
						TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
						POFlag = 1;
					} else if ((PT[k].equalsIgnoreCase("Prepaid")) && (PrFlag == 0)) {
						TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
						PrFlag = 1;
					}
					Bill_NO[k] = Billprofile_No.get();
				}
			} else if (TestCaseN.get().equals("ExistingAccount")) {

				if (!(getdata("Ext_AccountNo").equals(""))) {
					Account_No = getdata("Ext_AccountNo");
				} else {
					Account_No = pulldata("Ext_AccountNo");
				}
				for (k = 0; k < MSD.length; k++) {

					TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
					Bill_NO[k] = Billprofile_No.get();
				}

			}
			Test_OutPut += "Account_No : " + Account_No + ",";
			for (k = 0; k < MSD.length; k++) {
				CO.Assert_Search(MSD[k], "Active");
				CO.waitforload();
				Utlities.StoreValue("Service_Id", MSD[k]);
				Test_OutPut += "Service_Id : " + MSD[k] + ",";
				CO.Text_Select("a", GetData);
				CO.waitforload();
				// Click on Modify Assert
				if (Browser.WebButton.exist("Assert_Modify")) {

					Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
					Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
					int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
					for (int i = 2; i <= Inst_RowCount; i++)
						if (!Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)) {
							CO.waitforload();
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

				} else {
					CO.InstalledAssertChange("Modify");
				}
				Result.fUpdateLog("------Modification on Service id-----" + MSD[k]);
				CO.scroll("Date_Continue", "WebButton");
				Browser.WebButton.click("Date_Continue");
				CO.waitmoreforload();

				CO.waitforload();
				int Col_SA = CO.Actual_Cell("Line_Items", "Service Account");
				int Col_BA = CO.Actual_Cell("Line_Items", "Billing Account");
				int Col_BP = CO.Actual_Cell("Line_Items", "Billing Profile");
				int Col_OA = CO.Actual_Cell("Line_Items", "Owner Account");

				CO.waitforload();
				Browser.WebButton.click("Order_Account");

				CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);
				CO.waitforload();

				Browser.WebButton.click("Billing_Profile");
				Result.fUpdateLog("------Changing Billing_Profile  for the Service -----" + MSD[k]);
				CO.Popup_Selection("Bill_Selection", "Name", Bill_NO[k]);

				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				for (int i = 2; i <= Row_Count; i++) {
					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_SA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Service Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_BA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Billing Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_OA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Owner Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click("Line_Items", i, Col_BP);
					CO.waitforload();
					Result.fUpdateLog("------Changing Billing_Profile  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Bill_Selection", "Name", Bill_NO[k]);
					Test_OutPut += "Billprofile_No : " + Bill_NO[k] + ",";
				}

				CO.scroll("Ent_CreditLimit", "WebEdit");
				Browser.WebEdit.click("Ent_CreditLimit");
				Browser.WebEdit.Set("Ent_CreditLimit", "100");

				Browser.WebButton.waittillvisible("Validate");

				Order_no[k] = CO.Order_ID();
				Utlities.StoreValue("Order_no", Order_no[k]);
				Test_OutPut += "Order_no : " + Order_no[k] + ",";

				Test_OutPut += OrderSubmission().split("@@")[1] + ",";

				int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count1 <= 4) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				CO.Action_Update("Update", MSD[k]);
				CO.waitforload();
				CO.waitforload();
				CO.Title_Select("a", "Home");
				CO.waitforload();

			}
			CO.waitforload();
			CO.Account_Search(Account_No);
			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Test_OutPut += "Transfer of Ownership is done Successfully " + ",";
				Result.fUpdateLog("Transfer of Ownership is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Transfer of Ownership Failed" + ",";
				Result.takescreenshot("Transfer of Ownership Failed");
				Result.fUpdateLog("Transfer of Ownership Failed");
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
		Result.fUpdateLog("------Transfer of Ownership - Enterprise to Enterprise - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*
	 * Method Name : TransferOfOwnership_C2E Arguments : None Use : Transfer of
	 * active services consumer to enterprise Designed By : Vinodhini Raviprasad
	 * Last Modified Date : 01-Mar-2018
	 * -----------------------------------------------------------------------------
	 * ---------------------------
	 */
	public String TransferOfOwnership_C2E() {
		String Test_OutPut = "", Status = "", BP_Name = null;
		String Account_No = "", GetData, Bil_Profile = "", Payment_Method = null, Pymt_Type = null, PM_MSISDN = null,
				Ac_No;
		int Inst_RowCount, Col_P, Col_Type1, Row = 2, Col_Type = 0, Col_Nam = 0, k, Row_Count, Col_Val;
		Result.fUpdateLog("------Transfer of active services from Consumer Account to Enterprise Account------");
		try {

			if (!(getdata("AccountNo").equals(""))) {
				Ac_No = getdata("AccountNo");
			} else {
				Ac_No = pulldata("AccountNo");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			CO.Account_Search(Ac_No);
			Browser.WebButton.click("Profile_Tab");
			Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
			if (Row_Count >= Row) {
				Col_Val = CO.Actual_Cell("Bill_Prof", "Primary");
				Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Val);
					if (LData.equalsIgnoreCase("Checked")) {
						BP_Name = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Nam);
						break;
					}

				}
			}

			Browser.WebLink.click("Acc_Summary");
			Result.fUpdateLog("------Account Summary Tab------");
			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			Col_Val = CO.Actual_Cell("Installed_Assert", "Service ID");
			int Count = 0;
			for (int i = 2; i <= Row_Count; i++) {
				String SID = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
				if (!SID.equals("")) {
					Count = Count + 1;
				}
			}

			String MSD[] = new String[Count];

			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			Col_Val = CO.Actual_Cell("Installed_Assert", "Service ID");
			Col_Nam = CO.Select_Cell("Installed_Assert", "Billing Profile");
			int s = 0;
			int Flag = 0;
			for (int i = 2; i <= Row_Count; i++) {
				String SID = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
				String BpNam = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Nam);
				if (!SID.equals("")) {
					if (BpNam.equalsIgnoreCase(BP_Name) & (Flag == 0)) {

						PM_MSISDN = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
						Flag = 1;

					} else {
						MSD[s] = SID;
						s = s + 1;
					}
				}
				if ((i == Row_Count) && (Flag == 1)) {
					MSD[s] = PM_MSISDN;
					break;
				}
			}
			int a = MSD.length;
			String PT[] = new String[a];
			String PM[] = new String[a];
			String Bill_NO[] = new String[a];
			String Order_no[] = new String[a];
			// String PT[] = null,PM[] = null;

			for (k = 0; k < a; k++) {
				CO.waitforload();
				CO.Assert_Search(MSD[k], "Active");
				Result.fUpdateLog("------Search with Serice id-----" + MSD[k]);
				CO.waitforload();
				CO.Text_Select("a", GetData);

				Col_Nam = CO.Select_Cell("Acc_Installed_Assert", "Billing Profile");
				Bil_Profile = Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col_Nam);

				CO.TabNavigator("Profiles");

				CO.waitforload();
				CO.Text_Select("a", "Billing Profile");
				CO.waitforload();
				Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
				Col_Type = CO.Select_Cell("Bill_Prof", "Payment Type");
				Col_Type1 = CO.Select_Cell("Bill_Prof", "Payment Method");

				Browser.WebButton.click("Profile_Query");
				CO.waitforload();
				Browser.WebTable.SetDataE("Bill_Prof", Row, Col_Nam, "Name", Bil_Profile);
				CO.waitforload();
				Browser.WebButton.click("BillingProfile_Go");
				Inst_RowCount = Browser.WebTable.getRowCount("Bill_Prof");

				if (Inst_RowCount == 2) {
					CO.waitforload();
					Pymt_Type = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Type);
					Payment_Method = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Type1);
					if (Pymt_Type.equalsIgnoreCase("postpaid")) {
						CO.TabNavigator("Profiles");
						CO.waitforload();
						CO.Text_Select("a", "Billing Profile");
						CO.waitforload();
						TOS_BillingProfileCreation(Ac_No, "Prepaid", "Prepaid");
						Bil_Profile = Billprofile_No.get();
						Test_OutPut += "MSISDN : " + MSD[k] + ",";
						CO.PlanChangeTOO(MSD[k], GetData, Bil_Profile);
						String Order_no1 = CO.Order_ID();
						Test_OutPut += "Migration Order_no : " + Order_no1 + ",";
						Browser.WebButton.waittillvisible("Validate");
						CO.waitforload();
						Test_OutPut += OrderSubmission().split("@@")[1];
						Pymt_Type = Payment_Method = "Prepaid";
					}

				} else {
					Continue.set(false);
				}
				CO.waitforload();
				PT[k] = Pymt_Type;
				PM[k] = Payment_Method;
				CO.waitforload();
				CO.Title_Select("a", "Home");
				CO.waitforload();
			}
			// Create new contact and account
			int POFlag = 0, PrFlag = 0;
			if (TestCaseN.get().equals("NewAccount")) {
				Entp_AccountCreation();
				Entp_ContactCreation();
				Account_No = New_Account.get();

				for (k = 0; k < MSD.length; k++) {
					if ((PT[k].equalsIgnoreCase("Postpaid")) && (POFlag == 0)) {
						TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
						POFlag = 1;
					} else if ((PT[k].equalsIgnoreCase("Prepaid")) && (PrFlag == 0)) {
						TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
						PrFlag = 1;
					}
					Bill_NO[k] = Billprofile_No.get();
				}
			} else if (TestCaseN.get().equals("ExistingAccount")) {

				if (!(getdata("Ext_AccountNo").equals(""))) {
					Account_No = getdata("Ext_AccountNo");
				} else {
					Account_No = pulldata("Ext_AccountNo");
				}
				for (k = 0; k < MSD.length; k++) {

					TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
					Bill_NO[k] = Billprofile_No.get();
				}

			}
			Test_OutPut += "Account_No : " + Account_No + ",";
			for (k = 0; k < MSD.length; k++) {
				CO.Assert_Search(MSD[k], "Active");
				CO.waitforload();
				Utlities.StoreValue("Service_Id", MSD[k]);
				Test_OutPut += "Service_Id : " + MSD[k] + ",";
				CO.Text_Select("a", GetData);
				CO.waitforload();
				// Click on Modify Assert
				if (Browser.WebButton.exist("Assert_Modify")) {

					Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
					Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
					int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
					for (int i = 2; i <= Inst_RowCount; i++)
						if (!Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)) {
							CO.waitforload();
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

				} else {
					CO.InstalledAssertChange("Modify");
				}
				Result.fUpdateLog("------Modification on Service id-----" + MSD[k]);
				CO.scroll("Date_Continue", "WebButton");
				Browser.WebButton.click("Date_Continue");
				CO.waitmoreforload();

				CO.waitforload();
				int Col_SA = CO.Actual_Cell("Line_Items", "Service Account");
				int Col_BA = CO.Actual_Cell("Line_Items", "Billing Account");
				int Col_BP = CO.Actual_Cell("Line_Items", "Billing Profile");
				int Col_OA = CO.Actual_Cell("Line_Items", "Owner Account");

				CO.waitforload();
				Browser.WebButton.click("Order_Account");

				CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);
				CO.waitforload();

				Browser.WebButton.click("Billing_Profile");
				Result.fUpdateLog("------Changing Billing_Profile  for the Service -----" + MSD[k]);
				CO.Popup_Selection("Bill_Selection", "Name", Bill_NO[k]);

				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				for (int i = 2; i <= Row_Count; i++) {
					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_SA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Service Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_BA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Billing Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_OA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Owner Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click("Line_Items", i, Col_BP);
					CO.waitforload();
					Result.fUpdateLog("------Changing Billing_Profile  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Bill_Selection", "Name", Bill_NO[k]);
					Test_OutPut += "Billprofile_No : " + Bill_NO[k] + ",";
				}
				CO.scroll("Ent_CreditLimit", "WebEdit");
				Browser.WebEdit.click("Ent_CreditLimit");
				Browser.WebEdit.Set("Ent_CreditLimit", "100");

				Browser.WebButton.waittillvisible("Validate");

				Order_no[k] = CO.Order_ID();
				Utlities.StoreValue("Order_no", Order_no[k]);
				Test_OutPut += "Order_no : " + Order_no[k] + ",";

				Test_OutPut += OrderSubmission().split("@@")[1] + ",";

				int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count1 <= 4) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				CO.Action_Update("Update", MSD[k]);
				CO.waitforload();
				CO.waitforload();
				CO.Title_Select("a", "Home");
				CO.waitforload();

			}
			CO.Account_Search(Account_No);
			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Test_OutPut += "Transfer of Ownership from Consumer Account to Enterprise Account is done Successfully "
						+ ",";
				Result.fUpdateLog(
						"Transfer of Ownership from Consumer Account to Enterprise Account is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Transfer of Ownership from Consumer Account to Enterprise Account Failed" + ",";
				Result.takescreenshot("Transfer of Ownership from Consumer Account to Enterprise Account Failed");
				Result.fUpdateLog("Transfer of Ownership from Consumer Account to Enterprise Account Failed");
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
		Result.fUpdateLog(
				"------Transfer of Services from Consumer Acccount to Enterprise Account Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TransferOfOwnership_E2C
	 * Arguments			: None
	 * Use 					: Transfer of active services enterprise to consumer
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 01-Mar-2018
	--------------------------------------------------------------------------------------------------------*/
	public String TransferOfOwnership_E2C() {
		String Test_OutPut = "", Status = "", BP_Name = null;
		String Account_No = "", GetData, Bil_Profile = "", Payment_Method = null, Pymt_Type = null, PM_MSISDN = null,
				Ac_No;
		int Inst_RowCount, Col_P, Col_Type1, Row = 2, Col_Type = 0, Col_Nam = 0, k, Row_Count, Col_Val;
		Result.fUpdateLog("------Transfer of active services------");
		try {

			if (!(getdata("AccountNo").equals(""))) {
				Ac_No = getdata("AccountNo");
			} else {
				Ac_No = pulldata("AccountNo");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			CO.Account_Search(Ac_No);
			Browser.WebButton.click("Profile_Tab");
			Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
			if (Row_Count >= Row) {
				Col_Val = CO.Actual_Cell("Bill_Prof", "Primary");
				Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Val);
					if (LData.equalsIgnoreCase("Checked")) {
						BP_Name = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Nam);
						break;
					}

				}
			}

			Browser.WebLink.click("Acc_Summary");
			Result.fUpdateLog("------Account Summary Tab------");
			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			Col_Val = CO.Actual_Cell("Installed_Assert", "Service ID");
			int Count = 0;
			for (int i = 2; i <= Row_Count; i++) {
				String SID = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
				if (!SID.equals("")) {
					Count = Count + 1;
				}
			}

			String MSD[] = new String[Count];

			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			Col_Val = CO.Actual_Cell("Installed_Assert", "Service ID");
			Col_Nam = CO.Select_Cell("Installed_Assert", "Billing Profile");
			int s = 0;
			int Flag = 0;
			for (int i = 2; i <= Row_Count; i++) {
				String SID = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
				String BpNam = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Nam);
				if (!SID.equals("")) {
					if (BpNam.equalsIgnoreCase(BP_Name) & (Flag == 0)) {

						PM_MSISDN = Browser.WebTable.getCellData_title("Installed_Assert", i, Col_Val);
						Flag = 1;

					} else {
						MSD[s] = SID;
						s = s + 1;
					}
				}
				if ((i == Row_Count) && (Flag == 1)) {
					MSD[s] = PM_MSISDN;
					break;
				}
			}
			int a = MSD.length;
			String PT[] = new String[a];
			String PM[] = new String[a];
			String Bill_NO[] = new String[a];
			String Order_no[] = new String[a];
			// String PT[] = null,PM[] = null;

			for (k = 0; k < a; k++) {
				CO.waitforload();
				CO.Assert_Search(MSD[k], "Active");
				Result.fUpdateLog("------Search with Serice id-----" + MSD[k]);
				CO.waitforload();
				CO.Text_Select("a", GetData);

				Col_Nam = CO.Select_Cell("Acc_Installed_Assert", "Billing Profile");
				Bil_Profile = Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col_Nam);

				CO.TabNavigator("Profiles");

				CO.waitforload();
				CO.Text_Select("a", "Billing Profile");
				CO.waitforload();

				Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
				Col_Type = CO.Select_Cell("Bill_Prof", "Payment Type");
				Col_Type1 = CO.Select_Cell("Bill_Prof", "Payment Method");

				Browser.WebButton.click("Profile_Query");

				CO.waitforload();
				Browser.WebTable.SetDataE("Bill_Prof", Row, Col_Nam, "Name", Bil_Profile);
				CO.waitforload();
				Browser.WebButton.click("BillingProfile_Go");
				Inst_RowCount = Browser.WebTable.getRowCount("Bill_Prof");

				if (Inst_RowCount == 2) {
					CO.waitforload();
					Pymt_Type = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Type);
					Payment_Method = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Type1);
					if (Pymt_Type.equalsIgnoreCase("postpaid")) {
						Browser.WebButton.click("Profile_Query");
						CO.waitforload();
						Browser.WebTable.SetDataE("Bill_Prof", Row, Col_Type, "Payment_Type", "Prepaid");
						int Col_S = CO.Select_Cell("Bill_Prof", "Billing Profile Status");
						Browser.WebTable.SetDataE("Bill_Prof", Row, Col_S, "Status", "Active");
						CO.waitforload();
						Browser.WebButton.click("BillingProfile_Go");
						Inst_RowCount = Browser.WebTable.getRowCount("Bill_Prof");
						CO.waitforload();

						if (Inst_RowCount == 2) {
							CO.waitforload();
							Bil_Profile = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Nam);
						} else {
							TOS_BillingProfileCreation(Ac_No, "Prepaid", "Prepaid");
							Bil_Profile = Billprofile_No.get();
						}

						CO.PlanChangeTOO(MSD[k], GetData, Bil_Profile);
						CO.scroll("Ent_CreditLimit", "WebEdit");
						Browser.WebEdit.click("Ent_CreditLimit");
						Browser.WebEdit.Set("Ent_CreditLimit", "100");
						CO.waitforload();
						Browser.WebButton.waittillvisible("Validate");
						CO.waitforload();
						Test_OutPut += OrderSubmission().split("@@")[1];
						Pymt_Type = Payment_Method = "Prepaid";

					}

				} else {
					Continue.set(false);
				}
				CO.waitforload();
				PT[k] = Pymt_Type;
				PM[k] = Payment_Method;
				CO.waitforload();
				CO.Title_Select("a", "Home");
				CO.waitforload();
			}
			// Create new contact and account
			int POFlag = 0, PrFlag = 0;
			if (TestCaseN.get().equals("NewAccount")) {
				ContactCreation();
				AccountCreation();
				Account_No = New_Account.get();

				for (k = 0; k < MSD.length; k++) {
					if ((PT[k].equalsIgnoreCase("Postpaid")) && (POFlag == 0)) {
						TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
						POFlag = 1;
					} else if ((PT[k].equalsIgnoreCase("Prepaid")) && (PrFlag == 0)) {
						TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
						PrFlag = 1;
					}
					Bill_NO[k] = Billprofile_No.get();
				}
			} else if (TestCaseN.get().equals("ExistingAccount")) {

				if (!(getdata("Ext_AccountNo").equals(""))) {
					Account_No = getdata("Ext_AccountNo");
				} else {
					Account_No = pulldata("Ext_AccountNo");
				}
				for (k = 0; k < MSD.length; k++) {

					TOS_BillingProfileCreation(Account_No, PT[k], PM[k]);
					Bill_NO[k] = Billprofile_No.get();
				}

			}
			Test_OutPut += "Account_No : " + Account_No + ",";
			for (k = 0; k < MSD.length; k++) {
				CO.Assert_Search(MSD[k], "Active");
				CO.waitforload();
				Utlities.StoreValue("Service_Id", MSD[k]);
				Test_OutPut += "Service_Id : " + MSD[k] + ",";
				CO.Text_Select("a", GetData);
				CO.waitforload();
				// Click on Modify Assert
				if (Browser.WebButton.exist("Assert_Modify")) {

					Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
					Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
					int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
					for (int i = 2; i <= Inst_RowCount; i++)
						if (!Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)) {
							CO.waitforload();
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

				} else {
					CO.InstalledAssertChange("Modify");
				}
				Result.fUpdateLog("------Modification on Service id-----" + MSD[k]);
				CO.scroll("Date_Continue", "WebButton");
				Browser.WebButton.click("Date_Continue");
				CO.waitmoreforload();

				CO.waitforload();
				int Col_SA = CO.Actual_Cell("Line_Items", "Service Account");
				int Col_BA = CO.Actual_Cell("Line_Items", "Billing Account");
				int Col_BP = CO.Actual_Cell("Line_Items", "Billing Profile");
				int Col_OA = CO.Actual_Cell("Line_Items", "Owner Account");

				CO.waitforload();
				Browser.WebButton.click("Order_Account");

				CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);
				CO.waitforload();

				Browser.WebButton.click("Billing_Profile");
				Result.fUpdateLog("------Changing Billing_Profile  for the Service -----" + MSD[k]);
				CO.Popup_Selection("Bill_Selection", "Name", Bill_NO[k]);

				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				for (int i = 2; i <= Row_Count; i++) {
					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_SA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Service Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_BA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Billing Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click1("Line_Items", i, Col_OA);
					CO.waitforload();
					Result.fUpdateLog("------Changing Owner Account  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Account_PickTable", "Account_Number", Account_No);

					CO.waitforload();
					CO.Popup_Click("Line_Items", i, Col_BP);
					CO.waitforload();
					Result.fUpdateLog("------Changing Billing_Profile  for the Service -----" + MSD[k]);
					CO.Popup_Selection("Bill_Selection", "Name", Bill_NO[k]);
					Test_OutPut += "Billprofile_No : " + Bill_NO[k] + ",";
				}

				Browser.WebButton.waittillvisible("Validate");

				Order_no[k] = CO.Order_ID();
				Utlities.StoreValue("Order_no", Order_no[k]);
				Test_OutPut += "Order_no : " + Order_no[k] + ",";

				Test_OutPut += OrderSubmission().split("@@")[1] + ",";

				int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count1 <= 4) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				CO.Action_Update("Update", MSD[k]);
				CO.waitforload();
				CO.waitforload();
				CO.Title_Select("a", "Home");
				CO.waitforload();

			}
			CO.Account_Search(Account_No);
			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Test_OutPut += "Transfer of Ownership is done Successfully " + ",";
				Result.fUpdateLog("Transfer of Ownership is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Transfer of Ownership Failed" + ",";
				Result.takescreenshot("Transfer of Ownership Failed");
				Result.fUpdateLog("Transfer of Ownership Failed");
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
		Result.fUpdateLog("------Transfer of Services Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String ServicePoint() {
		String Test_OutPut = "", Status = "";
		int Col;
		Result.fUpdateLog("------ServicePoint Event Details------");
		try {

			CO.waitforload();
			CO.scroll("Con_ServicePoint", "WebTable");
			int Row_Count1 = Browser.WebTable.getRowCount("Con_ServicePoint");
			Col = CO.Actual_tab_Cell("contacts_tabVal", "Location");
			// Col_Sp = CO.Actual_tab_Cell("contacts_tabVal", "Service Point");
			int col_dt = CO.Actual_tab_Cell("contacts_tabVal", "Device Type");
			int col_DId = CO.Actual_tab_Cell("contacts_tabVal", "DeviceId");
			int col_CId = CO.Actual_tab_Cell("contacts_tabVal", "Card Id");
			int col_PId = CO.Actual_tab_Cell("contacts_tabVal", "Port Id");
			Row_Count1 = Browser.WebTable.getRowCount("Con_ServicePoint");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Con_ServicePoint", i, Col);
				String MDU = Browser.WebTable.getCellData("Con_ServicePoint", i, col_dt);

				if (LData.contains("Not")) {
					break;
				} else if (i >= Row_Count1) {

					CO.InstalledAssertChange("Copy Record                [Ctrl+B]");

					// * to provide a specific name for service point

					/*
					 * String SP = Browser.WebTable.getCellData("Con_ServicePoint", 3, Col_Sp);
					 * String[] Ser = SP.split("_"); String SerPoi = Ser[0] + "_" + "00" +
					 * Row_Count1; Browser.WebTable.click("Con_ServicePoint", 2, (Col_Sp+1));
					 * CO.waitforload(); String Path[] = Utlities.FindObject("Con_ServicePoint",
					 * "WebTable"); cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.TAB);
					 * Actions action = new Actions(cDriver.get());
					 * action.sendKeys(Keys.SHIFT,Keys.TAB,Keys.DELETE).build().perform();
					 * //action.sendKeys(Keys.DELETE).build().perform();
					 * 
					 * Browser.WebTable.SetData("Con_ServicePoint", 2, Col_Sp, "Service_Point_Id",
					 * SerPoi);
					 */

					Browser.WebTable.SetData("Con_ServicePoint", 2, Col, "Location", "Not In Use");
					if (MDU.equalsIgnoreCase("MDU")) {
						String Device_Number = getdata("ONT");
						String Card_id = getdata("OSM_CardID");
						String OPortID = getdata("OSM_PortID");
						CO.waitforload();
						Browser.WebTable.SetData("Con_ServicePoint", 2, col_DId, "VFQA_Device_Number", Device_Number);
						CO.waitforload();
						Browser.WebTable.SetData("Con_ServicePoint", 2, col_CId, "VFQA_Card_Id", Card_id);
						CO.waitforload();
						Browser.WebTable.SetData("Con_ServicePoint", 2, col_PId, "VFQA_Port_Id", OPortID);
					}

				}

			}
			Browser.WebButton.waittillvisible("Create_A/c");
			CO.ToWait();

			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("ServicePoint is Successful");
			} else
				Status = "FAIL";
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("Modify Event  Details - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Account360
	 * Arguments			: None
	 * Use 					: Account360 View validating
	 * Designed By			: Vinodhini R
	 * Last Modified Date 	: 02-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public String Account360() {
		String Test_OutPut = "", Status = "";
		String ACC_NO = null;
		int Row_Count;
		Result.fUpdateLog("------Account 360 view ------");

		try {
			if (!(getdata("AccountNo").equals(""))) {
				ACC_NO = getdata("AccountNo");
			}

			CO.waitforload();
			Browser.WebLink.click("VQ_Account");
			Browser.WebLink.click("All_Accounts");
			CO.waitforload();
			Browser.WebButton.click("Account_Query");
			CO.Webtable_Value("Account #", ACC_NO);
			Browser.WebButton.click("Account_Go");
			CO.waitforload();
			Browser.WebLink.click("Account_names");
			CO.waitforload();
			CO.scroll("Account_360_view", "WebButton");
			CO.waitforload();
			Browser.WebButton.click("Account_360_view");

			// Activity Timeline
			if (Browser.WebEdit.exist("Activity_timeline")) {
				Result.takescreenshot("Activity Timeline is Enabled ");
				Result.fUpdateLog("Activity Timeline is Enabled ");
				Test_OutPut += "Activity Timeline is Enabled" + ",";
			} else {
				Result.takescreenshot("Activity Timeline is not Enabled");
				Result.fUpdateLog("Activity Timeline is not Enabled");
				Test_OutPut += "Activity Timeline is not Enabled" + ",";
			}

			if (Browser.WebLink.exist("Activity_timeline_link")) {
				Browser.WebLink.click("Activity_timeline_link");
				Result.takescreenshot("Activity Timeline link is clicked");
				Result.fUpdateLog("Activity Timeline link is clicked");
				Test_OutPut += "Activity Timeline link is clicked" + ",";
				if (Browser.WebLink.exist("Activities")) {
					Result.fUpdateLog("Activities are Displayed");
					Test_OutPut += "Activities are Displayed" + ",";
					CO.waitforload();
					cDriver.get().navigate().back();
				}
			} else {
				Result.takescreenshot("Activity Timeline link is not Enabled");
				Result.fUpdateLog("Activity Timeline link is not Enabled");
			}

			// All Orders

			Browser.WebEdit.waitTillEnabled("All_Orders");
			if (Browser.WebEdit.exist("All_Orders")) {
				Result.takescreenshot("Orders are Enabled ");
				Result.fUpdateLog("Orders are Enabled ");
				Test_OutPut += "Orders are Enabled" + ",";
				Row_Count = Browser.WebTable.getRowCount("Acc_Orders");
				if (Row_Count >= 2) {
					Browser.WebLink.waittillvisible("Sales_order_link");
					Browser.WebLink.click("Sales_order_link");
					Result.takescreenshot("Sales order link is clicked ");
					Result.fUpdateLog("Sales order link is clicked");
					CO.waitforload();
					CO.scroll("Sales_order_no", "WebLink");
					if (Browser.WebLink.exist("Sales_order_no")) {
						Result.fUpdateLog("Sales order page is Displayed");
						CO.waitforload();
						cDriver.get().navigate().back();
					}
				}
			} else {
				Result.takescreenshot("Orders are not Enabled");
				Result.fUpdateLog("Orders are not Enabled");
				Test_OutPut += "Orders are not Enabled" + ",";
			}

			// Open orders
			CO.waitforload();
			Browser.WebLink.click("open_orders");
			if (Browser.WebEdit.exist("All_open_orders")) {
				Result.takescreenshot(" Open Orders are Enabled ");
				Result.fUpdateLog(" Open Orders are Enabled ");
				Test_OutPut += "Open Orders are Enabled" + ",";
			} else {
				Result.takescreenshot(" Open Orders are not Enabled");
				Result.fUpdateLog(" Open Orders are not Enabled");
				Test_OutPut += "Open Orders are not Enabled" + ",";
			}
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Acc_Orders");
			if (Row_Count >= 2) {
				Browser.WebLink.click("open_orders_link");
				Result.takescreenshot("Open order link is clicked ");
				Result.fUpdateLog("Open order link is clicked");
				CO.waitforload();
				Result.fUpdateLog("Open order page is Displayed");
				CO.waitforload();
				cDriver.get().navigate().back();
			}

			// Suspended Orders CO.waitforload();
			Browser.WebLink.click("suspeneded_orders");
			if (Browser.WebEdit.exist("All_suspended_orders")) {
				Result.takescreenshot("Suspeneded Orders are Enabled ");
				Result.fUpdateLog("Suspeneded Orders are Enabled ");
				Test_OutPut += "Suspeneded Orders are Enabled" + ",";
			} else {
				Result.takescreenshot("Suspeneded Orders are not Enabled");
				Result.fUpdateLog("Suspeneded Orders are not Enabled");
				Test_OutPut += "Suspeneded Orders are not Enabled" + ",";
			}
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Acc_Orders");
			if (Row_Count >= 2) {
				Browser.WebLink.click("suspeneded_orders_link");
				Result.takescreenshot("suspended order link is clicked ");
				Result.fUpdateLog("suspended order link is clicked");

				CO.waitforload();

				Result.fUpdateLog("suspended order page is Displayed");
				CO.waitforload();
				cDriver.get().navigate().back();

			}

			// Trouble Ticket CO.waitforload(); Browser.WebLink.click("Trouble_ticket");
			if (Browser.WebEdit.exist("All_Trouble_ticket")) {
				Result.takescreenshot("Trouble tickets are Enabled ");
				Result.fUpdateLog("Trouble tickets are Enabled ");
				Test_OutPut += "Trouble tickets are Enabled" + ",";
			} else {
				Result.takescreenshot("Trouble tickets are not Enabled");
				Result.fUpdateLog("Trouble tickets are not Enabled");
				Test_OutPut += "Trouble tickets are not Enabled" + ",";
			}
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("TT_Table");
			if (Row_Count >= 2) {
				Browser.WebLink.click("Trouble_ticket_link");
				Result.takescreenshot("Trouble ticket link is clicked ");
				Result.fUpdateLog("Trouble ticket link is clicked");

				CO.waitforload();

				Result.fUpdateLog("Trouble ticket page is Displayed");
				CO.waitforload();
				cDriver.get().navigate().back();
			}

			// Open Ticket CO.waitforload();
			Browser.WebLink.click("open_ticket");
			if (Browser.WebEdit.exist("All_open_ticket")) {
				Result.takescreenshot(" Open Tickets are Enabled ");
				Result.fUpdateLog(" Open Tickets are Enabled ");
				Test_OutPut += "Open Tickets are Enabled" + ",";
			} else {
				Result.takescreenshot("Open Tickets are not Enabled");
				Result.fUpdateLog("Open Tickets are not Enabled");
				Test_OutPut += "Open Tickets are not Enabled" + ",";
			}
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("TT_Table");
			if (Row_Count >= 2) {
				Browser.WebLink.click("open_ticket_link");
				Result.takescreenshot("Open ticket link is clicked ");
				Result.fUpdateLog("Open ticket link is clicked");

				CO.waitforload();

				Result.fUpdateLog("Open ticket page is Displayed");
				CO.waitforload();
				cDriver.get().navigate().back();
			}

			// High priority Ticket CO.waitforload();
			Browser.WebLink.click("High_priority");
			if (Browser.WebEdit.exist("All_High_priority")) {
				Result.takescreenshot("High priority Tickets are Enabled ");
				Result.fUpdateLog("High priority Tickets are Enabled ");
				Test_OutPut += "High priority Tickets are Enabled" + ",";
			} else {
				Result.takescreenshot("High priority Tickets are not Enabled");
				Result.fUpdateLog("High priority Tickets are not Enabled");
				Test_OutPut += "High priority Tickets are not Enabled" + ",";
			}
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("TT_Table");
			if (Row_Count >= 2) {
				Browser.WebLink.click("High_priority_link");
				Result.takescreenshot("High priority link is clicked ");
				Result.fUpdateLog("High priority link is clicked");

				CO.waitforload();

				Result.fUpdateLog("High priority page is Displayed");
				CO.waitforload();
				cDriver.get().navigate().back();

			}

			// Installed Asset CO.waitforload();
			Browser.WebLink.click("Installed_Asset");
			if (Browser.WebEdit.exist("All_Installed Asset")) {
				Result.takescreenshot("Installed Assets are Enabled ");
				Result.fUpdateLog("Installed Assets are Enabled ");
				Test_OutPut += "Installed Assets are Enabled" + ",";
			} else {
				Result.takescreenshot("Installed Asset are not Enabled");
				Result.fUpdateLog("Installed Asset are not Enabled");
				Test_OutPut += "Installed Assets are not Enabled" + ",";
			}
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Acc_Instal_Assert");
			if (Row_Count >= 2) {
				Browser.WebLink.click("Installed_Asset_link");
				Result.takescreenshot("Installed Asset link is clicked ");
				Result.fUpdateLog("Installed Asset link is clicked");

				CO.waitforload();

				Result.fUpdateLog("Installed Asset page is Displayed");
				CO.waitforload();
				cDriver.get().navigate().back();

			}

			// Billing Profile CO.waitforload();
			Browser.WebLink.click("Billing_profile");
			if (Browser.WebEdit.exist("All_Billing_profile")) {
				Result.takescreenshot(" Billing profiles are Enabled ");
				Result.fUpdateLog(" Billing profiles are Enabled ");
			} else {

				Result.takescreenshot("Billing profiles are not Enabled");
				Result.fUpdateLog("Billing profiles are not Enabled");
			}
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Bill_Selection");
			if (Row_Count >= 2) {
				Browser.WebLink.click("Billing_profile_link");
				Result.takescreenshot(" Billing profile link is clicked ");
				Result.fUpdateLog("Billing profile link is clicked");

				CO.waitforload();

				Result.fUpdateLog("Billing profile page is Displayed");
				CO.waitforload();
				cDriver.get().navigate().back();

			}

			// Active Billing profiles
			CO.waitforload();

			Browser.WebLink.click("Active_billing");
			if (Browser.WebEdit.exist("All_Active_billing")) {
				Result.takescreenshot("Active Billing profiles are Enabled ");
				Result.fUpdateLog("Active Billing profiles are Enabled ");
				Test_OutPut += "Active Billing profiles are Enabled" + ",";
			} else {
				Result.takescreenshot("Active Billing profiles are not Enabled");
				Result.fUpdateLog("Active Billing profiles are not Enabled");
				Test_OutPut += "Active Billing profiles are not Enabled" + ",";
			}
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Bill_Selection");
			if (Row_Count >= 2) {
				Browser.WebLink.click("Active_billing_link");
				Result.takescreenshot("Active Billing link is clicked ");
				Result.fUpdateLog("Active Billing link is clicked");

				CO.waitforload();

				Result.fUpdateLog("Active Billing page is Displayed");
				CO.waitforload();
				cDriver.get().navigate().back();

			}

			// Suspended Billing profiles
			CO.waitforload();

			Browser.WebLink.click("suspended_billing_profile");
			if (Browser.WebEdit.exist("All_suspended_billing_profile")) {
				Result.takescreenshot("Suspended Billing profiles are Enabled ");
				Result.fUpdateLog("Suspended Billing profiles are Enabled ");
				Test_OutPut += "Suspended Billing profiles are Enabled" + ",";
			} else {
				Result.takescreenshot(" Suspended Billing profiles are not Enabled ");
				Result.fUpdateLog(" Suspended Billing profiles are not Enabled ");
				Test_OutPut += "Suspended Billing profiles are not Enabled" + ",";
			}
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Bill_Selection");
			if (Row_Count >= 2) {
				Browser.WebLink.click("suspended_billing_profile_link");
				Result.takescreenshot(" Suspended Billing profile link is clicked ");
				Result.fUpdateLog("Suspended Billing profile link is clicked");

				CO.waitforload();

				Result.fUpdateLog("Suspended Billing profile page is Displayed");
				CO.waitforload();
				cDriver.get().navigate().back();

			}
			// Account Summary

			CO.scroll("Account_summary", "WebLink");
			Browser.WebLink.click("Account_summary");

			// All CO.waitforload();
			if (Browser.WebEdit.exist("All")) {
				Result.fUpdateLog("All Button is  Enabled ");
				Result.takescreenshot(" All Button is clicked ");
				Test_OutPut += "All Button is Enabled" + ",";
			} else {
				Result.fUpdateLog("All Button is  not Enabled ");
				Test_OutPut += "All Button is not Enabled" + ",";
			}

			// SMS
			CO.waitforload();
			if (Browser.WebButton.exist("SMS")) {
				Result.fUpdateLog("SMS Button is  Enabled ");

				Browser.WebButton.click("SMS");

				CO.waitforload();
				Result.takescreenshot(" SMS Button is clicked ");
				Test_OutPut += "SMS Button is Enabled" + ",";
			} else {
				Result.fUpdateLog("SMS  Button is  not Enabled ");
				Test_OutPut += "SMS Button is not Enabled" + ",";
			}

			// Data CO.waitforload();
			if (Browser.WebButton.exist("Data")) {

				Result.fUpdateLog("Data Button is  Enabled ");
				Browser.WebButton.click("Data");
				CO.waitforload();
				Result.takescreenshot(" Data Button is clicked ");
				Test_OutPut += "Data Button is Enabled" + ",";
			} else {
				Result.fUpdateLog("Data  Button is  not Enabled ");
				Test_OutPut += "Data Button is not Enabled" + ",";
			}

			// Voice
			CO.waitforload();
			if (Browser.WebButton.exist("Voice")) {

				Result.fUpdateLog("Voice Button is  Enabled ");
				Browser.WebButton.click("Voice");
				CO.waitforload();
				Result.takescreenshot(" Voice Button is clicked ");
				Test_OutPut += "Voice Button is Enabled" + ",";
			} else {
				Result.fUpdateLog("Voice Button is not Enabled ");
				Test_OutPut += "Voice Button is not Enabled" + ",";
			}

			// Recharge
			CO.waitforload();
			if (Browser.WebButton.exist("Recharge")) {
				Result.fUpdateLog("Recharge Button is  Enabled ");

				Browser.WebButton.click("Recharge");
				CO.waitforload();
				Result.takescreenshot(" Recharge Button is clicked ");

				Test_OutPut += "Recharge Button is Enabled" + ",";
			} else {
				Result.fUpdateLog("Recharge  Button is  not Enabled ");
				Test_OutPut += "Recharge Button is not Enabled" + ",";
			}

			Result.takescreenshot("Asset Summary");
			// View Bills
			boolean x = true;
			do {
				try {

					cDriver.get().findElement(By.xpath("//i[@class='fa fa-chevron-right']")).click();
					;
					Result.takescreenshot(" Voice and Data button clicked is clicked ");
				} catch (Exception e) {
					x = false;
				}
				// Browser.WebButton.click("Scroll_Right_Acc360");
				CO.waitforload();

			} while (x);

			if (Browser.WebEdit.exist("Upgrade_Promotion")) {
				Test_OutPut += "Upgrade Promotion Button is Enabled" + ",";
				Result.fUpdateLog("Upgrade Promotion Button is Enabled ");
			} else {
				Result.fUpdateLog("Upgrade Promotion Button is not Enabled ");
				Test_OutPut += "Upgrade Promotion Button is not Enabled" + ",";
			}

			if (Browser.WebEdit.exist("Change_limit360")) {
				CO.scroll("Change_limit360", "WebEdit");
				Test_OutPut += "Change Limit Button is Enabled" + ",";
				Result.fUpdateLog("Change Limit Button is Enabled ");
			} else {
				Result.fUpdateLog("Change Limit Button is not Enabled ");
				Test_OutPut += "Change Limit Button is not Enabled" + ",";
			}

			if (Browser.WebEdit.exist("Manage_Addon360")) {
				CO.scroll("Manage_Addon360", "WebEdit");
				Test_OutPut += "Manage Addon Button is Enabled" + ",";
				Result.fUpdateLog("Manage Addon Button is Enabled ");
			} else {
				Result.fUpdateLog("Manage Addon Button is not Enabled ");
				Test_OutPut += "Manage Addon Button is not Enabled" + ",";
			}

			CO.waitforload();
			if (Browser.WebButton.exist("view_bills")) {
				Result.fUpdateLog("View Bills Button is Enabled ");

				Browser.WebButton.click("view_bills");
				CO.waitforload();
				Result.takescreenshot(" View Bills Button is clicked ");
				CO.waitforload();
				cDriver.get().navigate().back();
				Test_OutPut += "View Bills Button is  Enabled" + ",";
			} else {
				Result.fUpdateLog("View Bills Button is not Enabled ");
				Test_OutPut += "View Bills Button is not Enabled" + ",";
			}

			// Upgrade Promotion
			CO.waitforload();

		} catch (

		Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Account 360 View Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: CustomerSegment
	 * Arguments			: None
	 * Use 					: Customer Segment Verification
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 14-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public String OpenUI_AccesRrights_6Segments() {
		String Test_OutPut = "", Status = "";
		String Segment, SegmentT;
		String Path[];
		int Col = 0, ACCRowcount = 0;
		Boolean Royal = false, Black = false, VVIP = false, VIP = false, Qatari = false, FrontOffice = true;
		String Cust_Seg[] = { "Royal", "Black", "VVIP", "VIP", "Qatari", "Expatriate" };
		Result.fUpdateLog("------Customer Segment Verification------");
		try {
			if (!(getdata("Segment").equals(""))) {
				Segment = getdata("Segment");
			} else {
				Segment = pulldata("Segment");
			}

			Result.takescreenshot("------Customer Segment Verification------");

			Actions a = new Actions(cDriver.get());
			WebElement we = cDriver.get().findElement(By.xpath("//body"));
			a.sendKeys(we, Keys.chord(Keys.CONTROL, Keys.SHIFT, "a")).perform();

			CO.waitforload();

			Result.fUpdateLog("Verifying User Responsibility");
			Result.takescreenshot("Verifying User Responsibility");
			Browser.WebEdit.Set("User_Input", "User Preferences");
			Browser.WebButton.click("User_Select");

			// CO.Text_Select("a", "User Preferences");
			SegmentT = Segment;
			CO.waitforload();
			CO.waitforload();
			if (Segment.equalsIgnoreCase("royal")) {
				Segment = "All";
			}
			// CO.waitforload();
			String s = Browser.WebEdit.gettext("User_Responsibility");
			Result.fUpdateLog(s);
			if (Browser.WebEdit.gettext("User_Responsibility").contains(Segment)) {

				Result.fUpdateLog("Verifying User Responsibility " + Browser.WebEdit.gettext("User_Responsibility"));
				Result.takescreenshot(
						"Verifying User Responsibility " + Browser.WebEdit.gettext("User_Responsibility"));
			} else {
				Continue.set(false);
				Result.fUpdateLog("Verifying User Responsibility failed ");
				Result.takescreenshot("Verifying User Responsibility failed");
			}
			Segment = SegmentT;

			switch (Segment.toLowerCase()) {
			case "royal":
				Royal = true;
				Black = true;
				VVIP = true;
				VIP = true;
				Qatari = true;
				FrontOffice = true;
				break;
			case "black":
				Royal = false;
				Black = true;
				VVIP = true;
				VIP = true;
				Qatari = true;
				FrontOffice = true;
				break;
			case "vvip":
				Royal = false;
				Black = false;
				VVIP = true;
				VIP = true;
				Qatari = true;
				FrontOffice = true;
				break;
			case "vip":
				Royal = false;
				Black = false;
				VVIP = false;
				VIP = true;
				Qatari = true;
				FrontOffice = true;
				break;
			case "qatari":
				Royal = false;
				Black = false;
				VVIP = false;
				VIP = false;
				Qatari = true;
				FrontOffice = true;
				break;
			case "frontoffice":
				Royal = false;
				Black = false;
				VVIP = false;
				VIP = false;
				Qatari = false;
				FrontOffice = true;
				break;
			}

			// Proceeding with Test Case
			// String SegMSISDN[] = new String [6];
			String MSISDN[] = new String[6];
			String ContactID[] = new String[6];
			String AccountName[] = new String[6];
			String Account[] = new String[6];

			switch (TestCaseN.get()) {
			case "GlobalSearchMSISDN":
			case "GlobalSearchContact":
			case "GuidedSIMSwap":

				if (!(TestCaseN.get().equalsIgnoreCase("GlobalSearchContact"))) {
					MSISDN[0] = getdata("Royal_MSISDN");
					MSISDN[1] = getdata("Black_MSISDN");
					MSISDN[2] = getdata("VVIP_MSISDN");
					MSISDN[3] = getdata("VIP_MSISDN");
					MSISDN[4] = getdata("Qatari_MSISDN");
					MSISDN[5] = getdata("Expatriate_MSISDN");
				} else {
					MSISDN[0] = getdata("Royal_ContactID");
					MSISDN[1] = getdata("Black_ContactID");
					MSISDN[2] = getdata("VVIP_ContactID");
					MSISDN[3] = getdata("VIP_ContactID");
					MSISDN[4] = getdata("Qatari_ContactID");
					MSISDN[5] = getdata("Expatriate_ContactID");
				}

				for (int i = 0; i < 6; i++) {
					switch (TestCaseN.get()) {
					case "GlobalSearchMSISDN":
						CO.GlobalSearch("MSISDN", MSISDN[i]);
						break;
					case "GlobalSearchContact":
						CO.GlobalSearch("ContactID", MSISDN[i]);
						break;
					case "GuidedSIMSwap":
						CO.GlobalSearch("SIMSwap", MSISDN[i]);
						break;
					}

					Result.fUpdateLog("Verifying Customer Segment " + Cust_Seg[i]);
					Result.takescreenshot("Verifying Customer Segment " + Cust_Seg[i]);

					switch (Cust_Seg[i].toLowerCase()) {

					case "royal":
						CO.SegmentVerification(Royal);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "black":
						CO.SegmentVerification(Black);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "vvip":
						CO.SegmentVerification(VVIP);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "vip":
						CO.SegmentVerification(VIP);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "qatari":
						CO.SegmentVerification(Qatari);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "frontoffice":
					case "expatriate":
						CO.SegmentVerification(FrontOffice);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;
					}
				}
				break;
			case "AssertMSISDNQuery":

				MSISDN[0] = getdata("Royal_MSISDN");
				MSISDN[1] = getdata("Black_MSISDN");
				MSISDN[2] = getdata("VVIP_MSISDN");
				MSISDN[3] = getdata("VIP_MSISDN");
				MSISDN[4] = getdata("Qatari_MSISDN");
				MSISDN[5] = getdata("Expatriate_MSISDN");

				for (int i = 0; i < 6; i++) {
					Result.fUpdateLog("Verifying Customer Segment " + Cust_Seg[i]);
					Result.takescreenshot("Verifying Customer Segment " + Cust_Seg[i]);
					CO.AccountView(MSISDN[i], "Active");
					switch (Cust_Seg[i].toLowerCase()) {

					case "royal":
						CO.SegmentVerification(Royal);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "black":
						CO.SegmentVerification(Black);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "vvip":
						CO.SegmentVerification(VVIP);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "vip":
						CO.SegmentVerification(VIP);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "qatari":
						CO.SegmentVerification(Qatari);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "frontoffice":
					case "expatriate":
						CO.SegmentVerification(FrontOffice);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;
					}

				}

				break;
			case "AccountNumberQuery":
				Account[0] = getdata("Royal_AccountNumber");
				Account[1] = getdata("Black_AccountNumber");
				Account[2] = getdata("VVIP_AccountNumber");
				Account[3] = getdata("VIP_AccountNumber");
				Account[4] = getdata("Qatari_AccountNumber");
				Account[5] = getdata("Expatriate_AccountNumber");

				for (int i = 0; i < 6; i++) {
					CO.Text_Select("a", "Home");
					CO.waitforload();
					Browser.WebLink.click("VQ_Account");
					CO.waitforload();
					Browser.WebLink.click("All_Accounts");
					// Browser.WebLink.click("All_Account");
					CO.waitforload();
					Browser.WebEdit.Set("AccountName_search", "Account #");
					CO.waitforload();
					Browser.WebEdit.Set("AccountName_Label", Account[i]);
					Path = Utlities.FindObject("AccountName_Label", "WebEdit");
					CO.waitforload();
					cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
					CO.waitforload();

					ACCRowcount = Browser.WebTable.getRowCount("Account");

					Result.fUpdateLog("Verifying Customer Segment " + Cust_Seg[i]);
					Result.takescreenshot("Verifying Customer Segment " + Cust_Seg[i]);

					switch (Cust_Seg[i].toLowerCase()) {

					case "royal":
						CO.AccountSegmentVerification(Royal, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "black":
						CO.AccountSegmentVerification(Black, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "vvip":
						CO.AccountSegmentVerification(VVIP, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "vip":
						CO.AccountSegmentVerification(VIP, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "qatari":
						CO.AccountSegmentVerification(Qatari, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "frontoffice":
					case "expatriate":
						CO.AccountSegmentVerification(FrontOffice, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;
					}
				}

				break;

			case "AccountNameQuery":
				AccountName[0] = getdata("Royal_AccountName");
				AccountName[1] = getdata("Black_AccountName");
				AccountName[2] = getdata("VVIP_AccountName");
				AccountName[3] = getdata("VIP_AccountName");
				AccountName[4] = getdata("Qatari_AccountName");
				AccountName[5] = getdata("Expatriate_AccountName");

				for (int i = 0; i < 6; i++) {
					CO.Text_Select("a", "Home");
					CO.waitforload();
					Browser.WebLink.click("VQ_Account");
					CO.waitforload();
					Browser.WebLink.click("All_Accounts");

					// Browser.WebLink.click("All_Account");
					CO.waitforload();
					Browser.WebEdit.Set("AccountName_search", "Name");
					Browser.WebEdit.Set("AccountName_Label", AccountName[i]);
					Path = Utlities.FindObject("AccountName_Label", "WebEdit");
					cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
					CO.waitforload();
					ACCRowcount = Browser.WebTable.getRowCount("Account");

					Result.fUpdateLog("Verifying Customer Segment " + Cust_Seg[i]);
					Result.takescreenshot("Verifying Customer Segment " + Cust_Seg[i]);

					switch (Cust_Seg[i].toLowerCase()) {

					case "royal":
						CO.AccountSegmentVerification(Royal, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "black":
						CO.AccountSegmentVerification(Black, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "vvip":
						CO.AccountSegmentVerification(VVIP, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "vip":
						CO.AccountSegmentVerification(VIP, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "qatari":
						CO.AccountSegmentVerification(Qatari, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "frontoffice":
					case "expatriate":
						CO.AccountSegmentVerification(FrontOffice, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;
					}
				}

				break;
			case "ContactIDQuery":
				ContactID[0] = getdata("Royal_ContactID");
				ContactID[1] = getdata("Black_ContactID");
				ContactID[2] = getdata("VVIP_ContactID");
				ContactID[3] = getdata("VIP_ContactID");
				ContactID[4] = getdata("Qatari_ContactID");
				ContactID[5] = getdata("Expatriate_ContactID");

				for (int i = 0; i < 6; i++) {
					CO.Text_Select("a", "Home");
					CO.waitforload();
					Browser.WebLink.click("VQ_Contact");
					CO.waitforload();
					Browser.WebLink.click("All_contacts");
					CO.waitforload();
					Browser.WebEdit.Set("AccountName_search", "ID Number");
					CO.waitforload();
					Browser.WebEdit.Set("AccountName_Label", ContactID[i]);
					Path = Utlities.FindObject("AccountName_Label", "WebEdit");
					cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
					// Browser.WebButton.click("Contact_Go");
					Browser.WebTable.getRowCount("Contact");

					CO.waitforload();
					ACCRowcount = Browser.WebTable.getRowCount("Contact");

					Col = CO.Actual_Cell("Contact", "Account");
					Browser.WebTable.click("Contact", 2, Col);
					// Browser.WebLink.waittillvisible("Acc_Portal");
					CO.waitforload();

					Result.fUpdateLog("Verifying Customer Segment " + Cust_Seg[i]);
					Result.takescreenshot("Verifying Customer Segment " + Cust_Seg[i]);

					switch (Cust_Seg[i].toLowerCase()) {

					case "royal":
						CO.ContactSegmentVerification(Royal, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "black":
						CO.ContactSegmentVerification(Black, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "vvip":
						CO.ContactSegmentVerification(VVIP, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "vip":
						CO.ContactSegmentVerification(VIP, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "qatari":
						CO.ContactSegmentVerification(Qatari, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;

					case "frontoffice":
					case "expatriate":
						CO.ContactSegmentVerification(FrontOffice, ACCRowcount);
						Result.takescreenshot("Customer Segment Verification is done for " + Cust_Seg[i]);
						Result.fUpdateLog("Customer Segment Verification is done for " + Cust_Seg[i]);
						break;
					}

				}

				break;

			}

			if (Continue.get()) {
				Test_OutPut += "Customer Segment Verification is done Successfully " + ",";
				Result.fUpdateLog("Customer Segment Verification is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Customer Segment Verification Failed" + ",";
				Result.takescreenshot("Customer Segment Verification Failed");
				Result.fUpdateLog("Customer Segment Verification Failed");
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
		Result.fUpdateLog("------Customer Segment Verification Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: OrderPayments
	 * Arguments			: None
	 * Use 					: To Perform Order level payments in Siebel
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 15-01-2018
	--------------------------------------------------------------------------------------------------------*/
	public String OrderPayments() {
		String Test_OutPut = "", Status = "";

		try {
			int Row = 2, RowCount, Col;
			String TransactionAmt, PayType, Reference;
			String PaymentReference = "", ChannelT;

			if (!(getdata("TransactionAmt").equals(""))) {
				TransactionAmt = getdata("TransactionAmt");
			} else {
				TransactionAmt = pulldata("TransactionAmt");
			}
			if (!(getdata("PayType").equals(""))) {
				PayType = getdata("PayType");
			} else {
				PayType = pulldata("PayType");
			}
			if (!(getdata("PaymentReference").equals(""))) {
				Reference = getdata("PaymentReference");
			} else if (!(pulldata("PaymentReference").equals(""))) {
				Reference = pulldata("PaymentReference");
			} else {
				Reference = R.nextInt(10000) + "WIN" + R.nextInt(10000);
			}

			/*
			 * Browser.WebLink.click("SalesOrder"); CO.waitforload();
			 * 
			 * Result.takescreenshot("Sales Order Navigation");
			 * Result.fUpdateLog("Sales Order Navigation");
			 * 
			 * CO.waitforload(); CO.Text_Select("a", "All Sales Orders"); CO.waitforload();
			 * 
			 * Result.takescreenshot("All Sales Order Navigation");
			 * Result.fUpdateLog("All Sales Order Navigation");
			 * 
			 * CO.waitforload(); Browser.WebButton.click("OrderQuery"); Col =
			 * CO.Select_Cell("Orders", "Order #");
			 * 
			 * Browser.WebTable.SetDataE("Orders", Row, Col, "Order_Number", OrderID); Col =
			 * CO.Select_Cell("Orders", "Order Type"); CO.waitforload(); RowCount =
			 * Browser.WebTable.getRowCount("Orders"); if (RowCount > 1) {
			 * Browser.WebTable.click("Orders", Row, Col); Col = CO.Select_Cell("Orders",
			 * "Order #"); Browser.WebTable.clickL("Orders", Row, Col);
			 * Result.takescreenshot("Order Found " + OrderID);
			 * Result.fUpdateLog("Order Found " + OrderID); } else {
			 * Result.takescreenshot("Order not found " + OrderID);
			 * Result.fUpdateLog("Order not found " + OrderID); Continue.set(false); }
			 */

			CO.waitforload();
			CO.Text_Select("a", "Payments");
			CO.waitforload();
			Result.takescreenshot("Payment Navigation");
			Result.fUpdateLog("Payment Navigation");
			Browser.WebButton.click("AddPayment");

			CO.waitforload();
			RowCount = Browser.WebTable.getRowCount("PaymentList");
			if (RowCount > 1) {
				Col = CO.Select_Cell("PaymentList", "Payment Method");
				Browser.WebTable.SetData("PaymentList", Row, Col, "Payment_Method", PayType);
				Col = CO.Select_Cell("PaymentList", "Transaction Amount");
				// if ((Browser.WebTable.getCellData("PaymentList", Row, Col)).isEmpty()) {
				Browser.WebTable.SetData("PaymentList", Row, Col, "Transaction_Amount", TransactionAmt);
				// }

				CO.isAlertExist();

				Browser.WebEdit.Set("WinCashReference", Reference);
				Result.takescreenshot("Payment Added for the Order ");
				Result.fUpdateLog("Payment Added for the Order ");
				String[] objprop = Utlities.FindObject("WinCashReference", "WebEdit");

				Actions a = new Actions(cDriver.get());
				WebElement we = cDriver.get().findElement(By.xpath(objprop[0]));
				a.sendKeys(we, Keys.chord(Keys.CONTROL, "s")).perform();
				CO.waitforload();
				Col = CO.Select_Cell("PaymentList", "Payment Status");

				if (Browser.WebTable.getCellData("PaymentList", Row, Col).equalsIgnoreCase("Authorized")) {
					Col = CO.Actual_Cell("PaymentList", "Payment #");

					PaymentReference = Browser.WebTable.getCellData("PaymentList", Row, Col);
					Col = CO.Actual_Cell("PaymentList", "Channel Transaction #");
					ChannelT = Browser.WebTable.getCellData("PaymentList", Row, Col);

					Result.takescreenshot("Payment Done Successfully with Payment Id : " + PaymentReference
							+ " and channel : " + ChannelT);
					Result.fUpdateLog("Payment Done Successfully with Payment Id : " + PaymentReference
							+ " and channel : " + ChannelT);
				} else {
					Result.takescreenshot("Payment is not authorised ");
					Result.fUpdateLog("Payment is not authorised  ");
					Continue.set(false);
				}
			} else {
				Result.takescreenshot("Payment can not be added ");
				Result.fUpdateLog("Payment can not be added ");
				Continue.set(false);
			}

			if (Continue.get()) {
				CO.Text_Select("a", "Line Items");
				Browser.WebTable.waittillvisible("Line_Items");
				Test_OutPut += "Order level payment is done Successfully: " + PaymentReference + ",";
				Result.fUpdateLog("Order level payment is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Order level payment Failed" + ",";
				Result.takescreenshot("Order level payment Failed");
				Result.fUpdateLog("Order level payment Failed");
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
		Result.fUpdateLog("------Order level payment Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Barring
	 * Arguments			: None
	 * Use 					: Barring of active services
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 03-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Barring() {
		String Test_OutPut = "", Status = "";
		String MSISDN, GetData, BarringOption;
		int Inst_RowCount, Col_P, Col_SID;
		Result.fUpdateLog("------Barring of active services------");
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

			if (!(getdata("Barring Options").equals(""))) {
				BarringOption = getdata("Barring Options");
			} else {
				BarringOption = pulldata("Barring Options");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					CO.waitforload();
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						CO.waitforload();
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else
				CO.InstalledAssertChange("Modify");
			CO.waitforload();

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			CO.Link_Select("Barring Options");

			CO.Radio_Select(BarringOption);
			CO.waitforload();
			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			CO.waitforload();
			if (CO.isAlertExist())
				Continue.set(false);

			Browser.WebButton.waittillvisible("Validate");
			Test_OutPut += OrderSubmission().split("@@")[1];

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Barring Service is done Successfully " + ",";
				Result.fUpdateLog("Barring Service is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Barring Failed" + ",";
				Result.takescreenshot("Barring Failed");
				Result.fUpdateLog("Barring Failed");
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
		Result.fUpdateLog("------Barring Services Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: UnBarring
	 * Arguments			: None
	 * Use 					: UnBarring of services
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 03-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String UnBarring() {
		String Test_OutPut = "", Status = "";
		String MSISDN, GetData, BarringOption;
		int Inst_RowCount, Col_P, Col_SID;

		Result.fUpdateLog("------UnBarring of services------");
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
			if (!(getdata("Barring Options").equals(""))) {
				BarringOption = getdata("Barring Options");
			} else {
				BarringOption = pulldata("Barring Options");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					CO.waitforload();
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						CO.waitforload();
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));
			} else
				CO.InstalledAssertChange("Modify");
			CO.waitforload();
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			CO.Link_Select("Barring Options");

			CO.Radio_Select(BarringOption);

			CO.waitforload();
			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			CO.waitforload();
			if (CO.isAlertExist())
				Continue.set(false);

			Browser.WebButton.waittillvisible("Validate");
			Test_OutPut += OrderSubmission().split("@@")[1];
			CO.LineItems_Dat();

			if (CO.getKeyFromValue(LineItemData, BarringOption) != null) {
				Result.fUpdateLog("Line Item Updation was as expected for " + BarringOption);
				Result.takescreenshot("Line Item Updation was as expected" + BarringOption);
			} else {
				Continue.set(false);
				Result.fUpdateLog("Line Item Updation was not as expected");
				Result.takescreenshot("Line Item Updation was not as expected");
			}

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "UnBarring Service is done Successfully " + ",";
				Result.fUpdateLog("UnBarring Service is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "UnBarring Failed" + ",";
				Result.takescreenshot("UnBarring Failed");
				Result.fUpdateLog("UnBarring Failed");
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
		Result.fUpdateLog("------UnBarring Services Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: JobCreation
	 * Arguments			: None
	 * Use 					: Create a Job
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 25-Jan-2018
	--------------------------------------------------------------------------------------------------------	
	public String JobCreation()
	{
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Job Creation Process Initiated------");
	try
	{
		int Row=2,RowCount,Col;
		String JobStatus,JobName,ParameterName,ParameterValue;
		
		if (!(getdata("JobName").equals(""))) {
			JobName = getdata("JobName");
		} else {
			JobName = pulldata("JobName");
		}
		
		if (!(getdata("ParameterName").equals(""))) {
			ParameterName = getdata("ParameterName");
		} else {
			ParameterName = pulldata("ParameterName");
		}
		
		
		if (!(getdata("ParameterValue").equals(""))) {
			ParameterValue = getdata("ParameterValue");
		} else {
			ParameterValue = pulldata("ParameterValue");
		}
		
		Actions a = new Actions(cDriver.get());
		WebElement we=cDriver.get().findElement(By.xpath("//body"));
		a.sendKeys(we,Keys.chord(Keys.CONTROL,Keys.SHIFT,"a")).perform();
		
		Result.takescreenshot("Site Map View Navigation");
		Result.fUpdateLog("Site Map View Navigation");
	
		CO.Text_Select("a", "Administration - Server Management");
		CO.waitforload();
		
		Result.takescreenshot("Navigating to Jobs");
		Result.fUpdateLog("Navigating to Jobs");
		
		CO.Text_Select("a", "Jobs");
		CO.waitforload();
		
		Result.takescreenshot("Creating New Job");
		Result.fUpdateLog("Creating New Job");
		
		CO.scroll("New_Job", "WebButton");
		Browser.WebButton.click("New_Job");
		CO.waitforload();
		
		Col=CO.Select_Cell("Jobs", "Component/Job");
		Browser.WebTable.SetDataE("Jobs", Row, Col, "Job_Name",JobName );
		
		CO.scroll("JobParameters", "WebButton");
		Browser.WebButton.click("JobParameters");
		CO.waitforload();
		Result.takescreenshot("Adding Job Parameters");
		Result.fUpdateLog("Adding Job Parameters");
		
		RowCount=Browser.WebTable.getRowCount("JobParameters");
		
		if(RowCount==2)
		{
			
			Col=Browser.WebTable.getColCount("JobParameters")-3;
			Browser.WebTable.SetDataE("JobParameters", RowCount, Col, "Name", ParameterName);
			
			Col=Browser.WebTable.getColCount("JobParameters")-2;
			Browser.WebTable.SetDataE("JobParameters", RowCount, Col, "Value", ParameterValue);
			
		}
		else
		{
			Result.takescreenshot("Records were not Added as expected");
			Result.fUpdateLog("Records were not Added as expected");
			Continue.set(false);
		}
		
		
		Result.takescreenshot("-------------Submitting Job-----------");
		Result.fUpdateLog("-----------Submitting Job-----------");
		
		CO.scroll("SubmitJob", "WebButton");
		Browser.WebButton.click("SubmitJob");
		CO.waitforload();
		Col=CO.Select_Cell("Jobs", "Status");
		
		int Iterate=0;
		do{
		//cDriver.get().navigate().refresh();
			a.sendKeys(we,Keys.chord(Keys.ALT,Keys.ENTER)).perform();
		CO.waitforload();
		System.out.println("Round "+Iterate);
		JobStatus=Browser.WebTable.getCellData("Jobs", Row, Col);
		System.out.println("Status Displayed " +JobStatus);
		Iterate++;
		}while(Iterate>=12);
		
		CO.waitmoreforload();
		
		
		CO.ToWait();
		JobStatus=Browser.WebTable.getCellData("Jobs", Row, Col);
		Result.takescreenshot("Job Submitted Successfully");
		Result.fUpdateLog("Job Submitted Successfully");
		
		if (Continue.get() || JobStatus.equalsIgnoreCase("Success") ||JobStatus.equalsIgnoreCase("Error")) {
			Test_OutPut += "JobCreation Process is done Successfully " + ",";
			Result.fUpdateLog("JobCreation Process is done Successfully ");
			Status = "PASS";
		} else {
			Test_OutPut += "JobCreation Process Failed" + ",";
			Result.takescreenshot("JobCreation Process Failed");
			Result.fUpdateLog("JobCreation Process Failed");
			Status = "FAIL";}
	}
	catch (Exception e) {
	    Continue.set(false);
		Status = "FAIL";
		Test_OutPut += "Exception occurred" + ",";
		Result.takescreenshot("Exception occurred");
		Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		e.printStackTrace();
	}
	Result.fUpdateLog("------JobCreation Process Details - Completed------");
	return Status + "@@" + Test_OutPut + "<br/>";
		
	}*/

	/*---------------------------------------------------------------------------------------------------------
		 * Method Name			: DunningAction
		 * Arguments			: None
		 * Use 					: 
		 * Designed By			: Vinodhini Raviprasad
		 * Last Modified By		: SravaniReddy
		 * Last Modified Date 	: 25-Jan-2018
		--------------------------------------------------------------------------------------------------------*/
	public String DunningAction() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Dunning Action Process Initiated------");
		try {
			int Row = 2, RowCount, Col, CreditScore = 0;
			double AmtOwed = 0.00;
			String AccountNumber, ActionType, DueDate, Order_Status, BillDate = "", Segment, DunningAction, Amtdue,
					BillingProfile = "";

			if (!(getdata("AccountNo").equals(""))) {
				AccountNumber = getdata("AccountNo");
			} else {
				AccountNumber = pulldata("AccountNo");
			}

			if (!(getdata("Action").equals(""))) {
				DunningAction = getdata("Action");
			} else {
				DunningAction = pulldata("Action");
			}

			// Wait time for Dunning Action
			int loop = 0;

			do {
				Thread.sleep(7000);
				cDriver.get().navigate().refresh();
				loop++;
			} while (loop <= 40);

			CO.waitforload();

			DateFormat DF = new SimpleDateFormat("MM/dd/yyyy");
			Date date;
			Calendar c = Calendar.getInstance();
			Calendar C_D = Calendar.getInstance();
			CO.Account_Search(AccountNumber);
			CO.waitforload();
			Result.fUpdateLog("Fetching Customer Segment");
			Result.takescreenshot("Fetching Customer Segment");
			CO.scroll("Customer_Segment", "WebEdit");
			Segment = Browser.WebEdit.gettext("Customer_Segment");
			CO.waitforload();

			CO.CreditAlertQuery(AccountNumber);
			CO.waitforload();
			CO.scroll("CreditQuery", "WebButton");
			RowCount = Browser.WebTable.getRowCount("CreditAlert");
			if (RowCount >= 2) {

				Col = CO.Select_Cell("CreditAlert", "Billing Profile");
				Browser.WebTable.scroll("CreditAlert", Row, Col);
				BillingProfile = Browser.WebTable.getCellData("CreditAlert", Row, Col);
				Browser.WebTable.clickL("CreditAlert", Row, Col);
				CO.waitforload();
				CO.waitmoreforload();
				Amtdue = Browser.WebEdit.gettext("Due_Now").replaceAll("QR", "").replace(",", "");

				RowCount = Browser.WebTable.getRowCount("Bills");
				if (RowCount > 1) {
					Result.fUpdateLog("Fetching Bill Cycle, Due Amount, Due Date ");
					// String BillPeriod;
					Col = CO.Select_Cell("Bills", "Amount Due");
					AmtOwed = Double.parseDouble(Amtdue);
					Col = CO.Select_Cell("Bills", "Due Date");
					BillDate = Browser.WebTable.getCellData("Bills", Row, Col);
					// BillDate = BillPeriod.split("-")[1];
					Col = CO.Select_Cell("Bills", "Due Date");
				} else {
					Continue.set(false);
				}
				if (Browser.WebButton.exist("CreditScore")) {
					CO.scroll("ThirdLevelView", "WebButton");
					Browser.WebButton.click("ThirdLevelView");
					CO.waitforload();
					Browser.WebButton.click("CreditScore");
				} else {
					CO.Text_Select("a", "Credit Score");
				}

				CO.waitforload();
				RowCount = Browser.WebTable.getRowCount("CreditScore");
				if (RowCount == 2) {
					String Score = Browser.WebTable.getCellData("CreditScore", RowCount,
							Browser.WebTable.getColCount("CreditScore"));
					CreditScore = Integer.parseInt(Score);
					Result.fUpdateLog("Fetching Credit Score");
					Result.takescreenshot("Fetching Credit Score");
				} else {
					Result.fUpdateLog("Unable to Fetch Credit Score -- No Records Found");
					Result.takescreenshot("Unable to Fetch Credit Score -- No Records Found");
					Continue.set(false);
				}

				CO.CreditAlertQuery(AccountNumber);
				CO.waitforload();
				Col = CO.Select_Cell("CreditAlert", "Account");
				Browser.WebTable.clickL("CreditAlert", Row, Col);

				CO.waitforload();
				Result.fUpdateLog("Fetching Customer Segment");
				Result.takescreenshot("Fetching Customer Segment");
				CO.scroll("Customer_Segment", "WebEdit");
				Segment = Browser.WebEdit.gettext("Customer_Segment");
				CO.waitforload();

				CO.DunningCalendar(Segment, CreditScore, AmtOwed);
				if (BillDate.isEmpty() == false) {
					for (Entry<String, String> entry : DunningSchedule.entrySet()) {
						String key = entry.getKey();
						String Val = entry.getValue();
						if (Val != null) {
							date = DF.parse(BillDate);
							c = Calendar.getInstance();
							c.setTime(date);
							c.add(Calendar.DATE, Integer.parseInt(Val.toString())); // Adding the acquired date as per
																					// actions
							String BillDate1 = DF.format(c.getTime());
							// String x[] = BillDate1.split("/");
							BillDate1 = BillDate1.replaceAll("01", "1").replaceAll("02", "2").replaceAll("03", "3")
									.replaceAll("04", "4").replaceAll("05", "5").replaceAll("06", "6")
									.replaceAll("07", "7").replaceAll("08", "8").replaceAll("09", "9")
									.replaceAll("218", "2018").replaceAll("219", "2019");
							BillSchedule.put(key, BillDate1);
							Result.fUpdateLog("Computed Date for " + key + " is " + BillSchedule.get(key));
						}
					}

				} else {
					Continue.set(false);
					Result.fUpdateLog("Bill Date is Empty");
				}

				Result.fUpdateLog("Displaying the Computed Date " + BillSchedule.get(DunningAction));

			} else {
				Continue.set(false);
			}

			CO.CreditAlertQuery(AccountNumber);
			CO.waitforload();
			CO.scroll("CreditQuery", "WebButton");
			RowCount = Browser.WebTable.getRowCount("CreditAlert");
			if (RowCount >= 2) {
				int Col_D = CO.Select_Cell("CreditAlert", "Due Date");
				Col = CO.Select_Cell("CreditAlert", "Action Type");
				for (int Iterate = 2; Iterate <= RowCount; Iterate++) {
					ActionType = Browser.WebTable.getCellData("CreditAlert", Iterate, Col);
					DueDate = Browser.WebTable.getCellData("CreditAlert", Iterate, Col_D);
					for (Entry<String, String> entry : BillSchedule.entrySet()) {
						String key = entry.getKey();
						if (key.equals(ActionType)) {
							if (BillSchedule.get(ActionType).equals(DueDate) == false) {
								Continue.set(false);
								Result.fUpdateLog("Computed Due Date does not match the Due Date Generated");
								Result.takescreenshot("Computed Due Date does not match the Due Date Generated");
								break;
							}
						}
					}
					if (Continue.get()) {
						break;
					}
				}

			} else {
				Result.takescreenshot("Please check the Account Number");
				Result.fUpdateLog("Please check the Account Number");
				Continue.set(false);
			}
			CO.Text_Select("a", "Home");
			CO.Account_Search(AccountNumber);
			CO.Text_Select("a", "Profiles");
			if (Browser.WebButton.exist("Billing_Prof"))
				Browser.WebButton.click("Billing_Prof");
			CO.waitforload();

			// Profile_Search
			Browser.WebButton.click("Profile_Query");
			CO.waitforload();
			Col = CO.Select_Cell("Bill_Prof", "Name");
			Browser.WebTable.SetData("Bill_Prof", Row, Col, "Name", BillingProfile);
			CO.waitforload();
			RowCount = Browser.WebTable.getRowCount("Bill_Prof");
			Col = CO.Select_Cell("Bill_Prof", "Billing Profile Status");
			if (RowCount == 2 & Browser.WebTable.getCellData("Bill_Prof", Row, Col).equalsIgnoreCase("In Collection")) {
				Result.takescreenshot("Billing Profile is In Collection as expected");
				Result.fUpdateLog("Billing Profile is In Collection as expected");
				Utlities.StoreValue("BillingProfile", BillingProfile);
			} else {
				Result.fUpdateLog("Billing Profile is not In Collection");
				Result.takescreenshot("Billing Profile is not In Collection as expected");
				Continue.set(false);
			}

			// OG Barring
			Boolean OG_Flag = false, IG_Flag = false;
			CO.CreditAlertQuery(AccountNumber);
			CO.waitforload();
			CO.scroll("CreditQuery", "WebButton");
			RowCount = Browser.WebTable.getRowCount("CreditAlert");
			if (RowCount >= 2) {
				Col = CO.Select_Cell("CreditAlert", "Action Type");
				CO.scroll("CreditAlert_ShowMore", "WebButton");
				Browser.WebButton.click("CreditAlert_ShowMore");
				CO.waitforload();
				for (int R = 2; R <= RowCount; R++) {
					if (R % 20 == 0) {
						int Col_S = CO.Select_Cell("CreditAlert", "Status");
						Browser.WebTable.click("CreditAlert", R, Col_S);
						CO.waitforload();
					}
					ActionType = Browser.WebTable.getCellData("CreditAlert", R, Col);
					if (ActionType.equalsIgnoreCase("Outgoing Bar")) {

						Browser.WebTable.click("CreditAlert", R, Col);
						CO.waitforload();
						DueDate = DF.format(C_D.getTime());
						CO.scroll("Due_Date", "WebEdit");
						Browser.WebEdit.Set("Due_Date", DueDate);
						CO.waitforload();
						CO.InstalledAssertChange("Save Record                [Ctrl+S]");
						CO.waitforload();
						// Wait time for Dunning Action
						loop = 0;
						do {

							cDriver.get().navigate().refresh();
							Thread.sleep(7000);
							Browser.WebEdit.waittillvisible("Credit_status");
						} while (!(Browser.WebEdit.gettext("Credit_status").equalsIgnoreCase("Closed")));
						CO.waitforload();

						// To get fulfillment status col
						CO.TraverseLatestOrder(AccountNumber);
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
						Order_Status = Browser.WebTable.getCellData("Line_Items", Row_Count, COL_FUL_STATUS);
						if (Order_Status.equals("Complete") == false) {
							Continue.set(false);
							Result.fUpdateLog("Order is not Completed");
							Result.takescreenshot("Order is not Completed");
						}

						String OG_Product = "System Bar - Mobile Collection OG";
						Boolean OGProduct_Flag = false;
						Col = CO.Select_Cell("Line_Items", "Product");
						Result.takescreenshot(
								"OG Products are available in Line Items for OG Barring Order as expected with Action Add");
						Row_Count = Browser.WebTable.getRowCount("Line_Items");
						for (int LI_Row = 2; LI_Row <= Row_Count; LI_Row++) {

							if (OG_Product.equalsIgnoreCase(Browser.WebTable.getCellData("Line_Items", LI_Row, Col))) {

								Col = CO.Select_Cell("Line_Items", "Action");
								if (Browser.WebTable.getCellData("Line_Items", LI_Row, Col).equalsIgnoreCase("add")) {
									Result.fUpdateLog(OG_Product
											+ " is available in Line Items for OG Barring Order as expected with Action Add");
									OGProduct_Flag = true;
								} else {
									Result.fUpdateLog(OG_Product
											+ " is available in Line Items for OG Barring Order with Action Mismatch");
									Result.takescreenshot(OG_Product
											+ " is available in Line Items for OG Barring Order with Action Mismatch");
									// Continue.set(false);
									OGProduct_Flag = false;
									break;
								}

								break;
							}
						}
						if (OGProduct_Flag == true) {
							OG_Flag = true;
						}
						break;
					}
				}
			}
			// IG Barring Incoming Bar
			OG_Flag = true;
			if (DunningAction.equalsIgnoreCase("Incoming Bar")) {
				CO.CreditAlertQuery(AccountNumber);
				CO.waitforload();
				CO.scroll("CreditQuery", "WebButton");
				RowCount = Browser.WebTable.getRowCount("CreditAlert");
				if (RowCount >= 2) {
					CO.scroll("CreditAlert_ShowMore", "WebButton");
					Browser.WebButton.click("CreditAlert_ShowMore");
					Col = CO.Select_Cell("CreditAlert", "Action Type");
					for (int R = 2; R <= RowCount; R++) {
						if (R % 20 == 0) {
							int Col_S = CO.Select_Cell("CreditAlert", "Status");
							Browser.WebTable.click("CreditAlert", R, Col_S);
							CO.waitforload();
						}
						ActionType = Browser.WebTable.getCellData("CreditAlert", R, Col);
						if (ActionType.equalsIgnoreCase("Incoming Bar")) {
							Browser.WebTable.click("CreditAlert", R, Col);
							CO.waitforload();
							DueDate = DF.format(C_D.getTime());
							Browser.WebEdit.Set("Due_Date", DueDate);
							CO.InstalledAssertChange("Save Record                [Ctrl+S]");
							// Wait time for Dunning Action

							do {

								cDriver.get().navigate().refresh();
								Thread.sleep(7000);
								CO.CreditAlertQuery(AccountNumber, "Incoming Bar");

								Browser.WebEdit.waittillvisible("Credit_status");
							} while (!(Browser.WebEdit.gettext("Credit_status").equalsIgnoreCase("Closed")));
							CO.waitforload();

							CO.waitforload();

							Boolean IGProduct_Flag = false;
							CO.TraverseLatestOrder(AccountNumber);
							CO.scroll("Ful_Status", "WebButton");
							Col = CO.Select_Cell("Line_Items", "Fulfillment Status");
							if (CO.Col_Data(Col).equalsIgnoreCase("fulfillment status"))
								COL_FUL_STATUS = Col;
							int Row_Count = Browser.WebTable.getRowCount("Line_Items");
							CO.scroll("Submit", "WebButton");
							if (Row_Count <= 3) {
								Browser.WebButton.waittillvisible("Expand");
								Browser.WebButton.click("Expand");
							}
							Order_Status = Browser.WebTable.getCellData("Line_Items", Row_Count, COL_FUL_STATUS);
							if (Order_Status.equals("Complete") == false) {
								Continue.set(false);
								Result.fUpdateLog("Order is not Completed");
								Result.takescreenshot("Order is not Completed");
							}
							int Col1 = CO.Select_Cell("Line_Items", "Product");
							Result.takescreenshot(
									"IG Products are available in Line Items for OG Barring Order as expected with Action Delete");
							Row_Count = Browser.WebTable.getRowCount("Line_Items");
							for (int LI_Row = 2; LI_Row <= Row_Count; LI_Row++) {
								String IG_Product = Browser.WebTable.getCellData("Line_Items", LI_Row, Col1);
								IGProduct_Flag = true;
								Col = CO.Select_Cell("Line_Items", "Action");
								if ((Browser.WebTable.getCellData("Line_Items", LI_Row, Col))
										.equalsIgnoreCase("suspend")) {
									Result.fUpdateLog(IG_Product
											+ " is available in Line Items for OG Barring Order as expected with Action Delete");
								} else {
									Result.fUpdateLog(IG_Product
											+ " is available in Line Items for OG Barring Order with Action Mismatch");
									Result.takescreenshot(IG_Product
											+ " is available in Line Items for OG Barring Order with Action Mismatch");
									IGProduct_Flag = false;
									break;
								}
							}

							CO.TraverseLatestBeforeOrder(AccountNumber);
							CO.waitforload();
							Row_Count = Browser.WebTable.getRowCount("Line_Items");
							if (Row_Count <= 3) {
								Browser.WebButton.waittillvisible("Expand");
								Browser.WebButton.click("Expand");
							}

							Col = CO.Actual_Cell("Line_Items", "Status");
							Order_Status = Browser.WebTable.getCellData("Line_Items", Row_Count, Col);
							if (Order_Status.equals("Complete") == false) {
								Continue.set(false);
								Result.fUpdateLog("Order is not Completed");
								Result.takescreenshot("Order is not Completed");
							}
							String OG_Product = "System Bar - Mobile Collection OG";
							Col = CO.Actual_Cell("Line_Items", "Product");
							Row_Count = Browser.WebTable.getRowCount("Line_Items");
							for (int LI_Row = 2; LI_Row <= Row_Count; LI_Row++) {

								if (OG_Product
										.equalsIgnoreCase(Browser.WebTable.getCellData("Line_Items", LI_Row, Col))) {
									IGProduct_Flag = true;
									Col = CO.Select_Cell("Line_Items", "Action");
									if (Browser.WebTable.getCellData("Line_Items", LI_Row, Col)
											.equalsIgnoreCase("delete")) {
										Result.fUpdateLog(OG_Product
												+ " is available in Line Items for OG Barring Order as expected with Action Delete");
										Result.takescreenshot(OG_Product
												+ " is available in Line Items for OG Barring Order as expected with Action Delete");
									} else {
										Result.fUpdateLog(OG_Product
												+ " is available in Line Items for OG Barring Order with Action Mismatch");
										Result.takescreenshot(OG_Product
												+ " is available in Line Items for OG Barring Order with Action Mismatch");
										Continue.set(false);
									}

									break;
								}
							}

							if (IGProduct_Flag == true) {
								IG_Flag = true;
							}
							break;
						}
					}
				}
			} else {
				IG_Flag = true;
			}

			if (Continue.get() & IG_Flag & OG_Flag) {
				Test_OutPut += "Dunning Action Process is done Successfully " + ",";
				Result.fUpdateLog("Dunning Action Process is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Dunning Action Process Failed" + ",";
				Result.takescreenshot("Dunning Action Process Failed");
				Result.fUpdateLog("Dunning Action Process Failed");
				Status = "FAIL";
			}
		} catch (

		Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Dunning Action Process Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
		 * Method Name			: CollectionExit
		 * Arguments			: None
		 * Use 					: 
		  * Designed By			: Vinodhini Raviprasad
		  * Last Modified By		: SravaniReddy
		 * Last Modified Date 	: 02-Feb-2018
		--------------------------------------------------------------------------------------------------------*/
	public String CollectionExit() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Collection Exit Process Initiated------");
		try {
			int Row = 2, RowCount, Col, Row_Count, Col_P, Col_S;
			String AccountNumber, Reference, BillAmt, BillingProfile = "", Channel;

			if (!(getdata("AccountNo").equals(""))) {
				AccountNumber = getdata("AccountNo");
			} else {
				AccountNumber = pulldata("AccountNo");
			}

			if (!(getdata("BillingProfile").equals(""))) {
				BillingProfile = getdata("BillingProfile");
			} else {
				BillingProfile = pulldata("BillingProfile");
			}
			if (!(getdata("Channel").equals(""))) {
				Channel = getdata("Channel");
			} else {
				Channel = pulldata("Channel");
			}

			if (!(getdata("Reference").equals(""))) {
				Reference = getdata("Reference");
			} else if (!(pulldata("Reference").equals(""))) {
				Reference = pulldata("Reference");
			} else {
				Reference = R.nextInt(10000) + "VFQA_Test" + R.nextInt(10000000);
			}

			CO.Account_Search(AccountNumber);
			CO.waitforload();
			CO.waitforload();
			CO.waitforload();

			CO.Text_Select("a", "Profiles");
			if (Browser.WebButton.exist("Billing_Prof"))
				Browser.WebButton.click("Billing_Prof");
			CO.waitforload();
			RowCount = Browser.WebTable.getRowCount("Bill_Prof");
			Col = CO.Select_Cell("Bill_Prof", "Name");
			if (BillingProfile.isEmpty() == false) {
				Browser.WebButton.click("Profile_Query");
				CO.waitforload();
				Browser.WebTable.SetData("Bill_Prof", Row, Col, "Name", BillingProfile);
				CO.waitforload();
				RowCount = Browser.WebTable.getRowCount("Bill_Prof");
				if (RowCount == 2) {
					Result.fUpdateLog("Proceeding to RTB Screen " + BillingProfile);
					Result.takescreenshot("Proceeding to RTB Screen " + BillingProfile);
					BillingProfile = Browser.WebTable.getCellData("Bill_Prof", Row, Col);
					Browser.WebTable.clickL("Bill_Prof", Row, Col);
					Utlities.StoreValue("BillingProfileName", BillingProfile);
				} else {
					Result.fUpdateLog("Billing Profile Not Found");
					Result.takescreenshot("Billing Profile Not Found");
					Continue.set(false);
				}
			}
			if (RowCount >= 2) {
				Boolean flag = false;
				Col = CO.Select_Cell("Bill_Prof", "Payment Type");
				for (int i = 2; i <= RowCount; i++)
					if (Browser.WebTable.getCellData("Bill_Prof", i, Col).equalsIgnoreCase("Postpaid")) {
						Col = CO.Select_Cell("Bill_Prof", "Name");
						BillingProfile = Browser.WebTable.getCellData("Bill_Prof", i, Col);
						Result.fUpdateLog("Proceeding to RTB Screen " + BillingProfile);
						Result.takescreenshot("Proceeding to RTB Screen " + BillingProfile);
						Browser.WebTable.clickL("Bill_Prof", Row, Col);
						Utlities.StoreValue("BillingProfileName", BillingProfile);
						flag = true;
						break;
					}
				if (flag == false) {
					Result.fUpdateLog("Postpaid Billing Profile Not Found");
					Result.takescreenshot("Postpaid Billing Profile Not Found");
					Continue.set(false);
				}
			} else {
				Result.fUpdateLog("Billing Profile Not Found");
				Result.takescreenshot("Billing Profile Not Found");
				Continue.set(false);
			}

			CO.waitforload();
			BillAmt = Browser.WebEdit.gettext("Balance");
			Test_OutPut += "Balance: " + BillAmt + ",";
			Result.takescreenshot("Getting Outstanding Balance" + BillAmt);

			if (Browser.WebButton.exist("Scroll_Left"))
				Browser.WebButton.click("Scroll_Left");
			CO.waitforload();
			if (Browser.WebButton.exist("Scroll_Left"))
				Browser.WebButton.click("Scroll_Left");

			CO.Account_Search(AccountNumber);
			CO.waitforload();

			// CO.Link_Select("Payments");
			Browser.WebButton.click("Payment");

			CO.waitforload();
			Result.takescreenshot("Account level Payment");
			Col_P = CO.Select_Cell("AccountPayment", "Billing Profile");
			int Col_C = CO.Select_Cell("AccountPayment", "Payment_Method");
			int Col_A = CO.Select_Cell("AccountPayment", "Payment_Amount");
			CO.scroll("Pay_Add", "WebButton");
			Browser.WebButton.click("Pay_Add");
			String Bill_Status = "";
			Row = 2;

			do {
				Col_S = CO.Actual_Cell("AccountPayment", "Status");
				Bill_Status = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);
				if ((Bill_Status.equalsIgnoreCase("Open"))) {
					break;
				}
			} while (true);

			CO.waitforload();

			Browser.WebTable.SetDataE("AccountPayment", Row, Col_A, "Payment_Amount", "QR328.57");
			Browser.WebTable.SetData("AccountPayment", Row, Col_C, "Payment_Method", Channel);
			Browser.WebTable.click("AccountPayment", Row, Col_P);
			Browser.WebTable.SetData("AccountPayment", Row, Col_P, "VFQA_Bill_Prof_Name", BillingProfile);

			CO.isAlertExist();
			if (Channel.equalsIgnoreCase("cash")) {
				CO.scroll("Reference_Number", "WebEdit");
				Browser.WebEdit.Set("Reference_Number", Reference);
			} else if (Channel.equalsIgnoreCase("cheque")) {
				Browser.WebEdit.Set("Cheque_Number", getdata("Cheque_Number"));
				Browser.WebEdit.Set("Bank_Name", getdata("Bank_Name"));

			} else if (Channel.equalsIgnoreCase("online")) {
				Browser.WebEdit.Set("Voucher_Number", getdata("Voucher_Number"));
				Browser.WebEdit.Set("Reference_Number", Reference);
			} else if (Channel.equalsIgnoreCase("voucher")) {
				Browser.WebEdit.Set("Voucher_Number", getdata("Voucher_Number"));
				Browser.WebEdit.Set("Reference_Number", Reference);
			}
			Col_S = CO.Select_Cell("AccountPayment", "Channel Transaction #");
			String Txn = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);

			Browser.WebButton.click("Bill_Submit");
			CO.waitmoreforload();
			Result.takescreenshot("Bill Submittion for Payment");
			Browser.WebButton.click("Payment_Query");
			Browser.WebTable.SetData("AccountPayment", Row, Col_S, "VFQA_Channel_Transaction__", Txn);
			// CO.waitforload();

			Col = CO.Select_Cell("AccountPayment", "Payment #");
			String Payment_Reference = Browser.WebTable.getCellData("AccountPayment", Row, Col);
			Test_OutPut += "Payment Reference Number:" + Payment_Reference + ",";

			Col_S = CO.Select_Cell("AccountPayment", "Status");
			Bill_Status = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);
			if ((Bill_Status.equalsIgnoreCase("Submitted"))) {
				CO.Link_Select("Account Summary");
				CO.waitforload();
				CO.Link_Select("Payments");
				CO.waitforload();
				Browser.WebButton.click("Payment_Query");
				CO.waitforload();
				Browser.WebTable.SetData("AccountPayment", Row, Col, "Payment_Number", Payment_Reference);
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("AccountPayment");
				if (Row_Count == 1)
					Continue.set(false);
				Bill_Status = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);
				Result.takescreenshot("Payment Status Verification" + Payment_Reference);

			} else if ((Bill_Status.equalsIgnoreCase("Success"))) {
				Continue.set(true);
			} else
				Continue.set(false);

			// Wait time for Activation
			int loop = 0;
			do {
				Thread.sleep(12000);
				cDriver.get().navigate().refresh();
				loop++;
			} while (loop <= 10);
			CO.waitforload();

			CO.CreditAlertQuery(AccountNumber);
			CO.waitforload();
			CO.scroll("CreditQuery", "WebButton");
			RowCount = Browser.WebTable.getRowCount("CreditAlert");
			Boolean flag = false;
			if (RowCount >= 2) {
				Col_S = CO.Select_Cell("CreditAlert", "Status");
				Browser.WebButton.click("CreditAlert_ShowMore");
				Col = CO.Select_Cell("CreditAlert", "Alert #");
				String AlertStatus, Alert;
				for (int Iterate = 2; Iterate <= RowCount; Iterate++) {
					if (Iterate % 20 == 0) {
						Browser.WebTable.click("CreditAlert", Iterate, Col_S);
						CO.waitforload();
					}
					Alert = Browser.WebTable.getCellData("CreditAlert", Iterate, Col);
					// AlertStatus = Browser.WebTable.getCellData("CreditAlert", Iterate, Col_S);
					AlertStatus = Browser.WebEdit.gettext("Credit_status");

					if ((AlertStatus.equalsIgnoreCase("cancelled") == false)
							&& (AlertStatus.equalsIgnoreCase("closed") == false)) {
						Result.takescreenshot("Alert Status is not Closed or Cancelled as expected");
						Result.fUpdateLog("Alert Status is not Closed or Cancelled as expected");
						Continue.set(false);
						break;
					}
					if (Alert.contains("Exited collections")) {
						Result.takescreenshot("Exit Collection Record Found in the table");
						Result.fUpdateLog("Exit Collection Record Found in the table");
						Row = 2;
						flag = true;
					}
				}
			}

			if (flag == true) {
				String Order_Status;
				flag = false;
				CO.TraverseLatestOrder(AccountNumber);
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				CO.scroll("Ful_Status", "WebButton");
				Col = CO.Select_Cell("Line_Items", "Fulfillment Status");
				if (CO.Col_Data(Col).equalsIgnoreCase("fulfillment status"))
					COL_FUL_STATUS = Col;
				Order_Status = Browser.WebTable.getCellData("Line_Items", Row_Count, COL_FUL_STATUS);
				if (Order_Status.equals("Complete") == false) {
					Continue.set(false);
					Result.fUpdateLog("Order is not Completed");
					Result.takescreenshot("Order is not Completed");
				}
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				int Col1 = CO.Select_Cell("Line_Items", "Product");
				for (int LI_Row = 2; LI_Row <= Row_Count; LI_Row++) {
					String IG_Product = Browser.WebTable.getCellData("Line_Items", LI_Row, Col1);

					flag = true;
					Col = CO.Select_Cell("Line_Items", "Action");
					if (Browser.WebTable.getCellData("Line_Items", LI_Row, Col).equalsIgnoreCase("resume") == false) {
						if ((IG_Product.equalsIgnoreCase("") == false) && (Browser.WebTable
								.getCellData("Line_Items", LI_Row, Col).equalsIgnoreCase("add") == false)) {
							flag = false;
							Result.fUpdateLog(IG_Product + " is not available with Action Resume");
							Result.takescreenshot(IG_Product + " is not available with Action Resume");
							break;
						}
					}
				}

			}
			CO.Account_Search(AccountNumber);
			CO.waitforload();
			CO.Text_Select("a", "Profiles");
			if (Browser.WebButton.exist("Billing_Prof"))
				Browser.WebButton.click("Billing_Prof");

			Browser.WebButton.click("Profile_Query");
			CO.waitforload();
			Col = CO.Select_Cell("Bill_Prof", "Name");
			Browser.WebTable.SetData("Bill_Prof", Row, Col, "Name", BillingProfile);
			CO.waitforload();
			// Browser.WebButton.click("BillingProfile_Go");
			CO.waitforload();
			RowCount = Browser.WebTable.getRowCount("Bill_Prof");
			Col = CO.Actual_Cell("Bill_Prof", "Billing Profile Status");
			Browser.WebTable.clickA("Bill_Prof", RowCount, Col);
			CO.waitforload();
			String BillStatus = Browser.WebTable.getCellData_title("Bill_Prof", RowCount, Col);
			if (RowCount == 2 & BillStatus.contains("Active")) {
				// Status
				Result.fUpdateLog("Billing Profile is Activated " + BillingProfile);
				Result.takescreenshot("Billing Profile is Activated " + BillingProfile);

			} else {
				// flag=false;
				Result.fUpdateLog("Billing Profile is not Activated " + BillingProfile);
				Result.takescreenshot("Billing Profile is not Activated " + BillingProfile);
			}

			if (Continue.get()) {// & flag) {
				Test_OutPut += "CollectionExit Process is done Successfully " + ",";
				Result.fUpdateLog("CollectionExit Process is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "CollectionExit Process Failed" + ",";
				Result.takescreenshot("CollectionExit Process Failed");
				Result.fUpdateLog("CollectionExit Process Failed");
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
		Result.fUpdateLog("------Collection Exit Process Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: DunningProcess
	 * Arguments			: None
	 * Use 					: DunningProcess for a specific Account
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified By		: SravaniReddy 
	 * Last Modified Date 	: 22-Jan-2018
	--------------------------------------------------------------------------------------------------------*/
	public String DunningProcess() {
		String Test_OutPut = "", Status = "";
		String AccNo, BillingProfile = "", Segment, Amtdue, DueDate = "";// ,BillDate="";
		double AmtOwed = 0.00;
		int RowCount, Col, Row = 2, CreditScore = 0;
		Result.fUpdateLog("------Dunning Process Check Initiated------");
		try {
			if (!(getdata("AccountNo").equals(""))) {
				AccNo = getdata("AccountNo");
			} else {
				AccNo = pulldata("AccountNo");
			}

			if (!(getdata("BillingProfile").equals(""))) {
				BillingProfile = getdata("BillingProfile");
				Utlities.StoreValue("BillingProfileName", BillingProfile);
			}

			CO.Account_Search(AccNo);

			CO.waitforload();
			Result.fUpdateLog("Fetching Customer Segment");
			Result.takescreenshot("Fetching Customer Segment");
			CO.scroll("Customer_Segment", "WebEdit");
			Segment = Browser.WebEdit.gettext("Customer_Segment");
			CO.waitforload();

			CO.Text_Select("a", "Profiles");
			if (Browser.WebButton.exist("Billing_Prof"))
				Browser.WebButton.click("Billing_Prof");
			CO.waitforload();
			RowCount = Browser.WebTable.getRowCount("Bill_Prof");
			Col = CO.Select_Cell("Bill_Prof", "Name");
			if (BillingProfile.isEmpty() == false) {
				Browser.WebButton.click("Profile_Query");
				CO.waitforload();
				Browser.WebTable.SetData("Bill_Prof", Row, Col, "Name", BillingProfile);
				CO.waitforload();
				RowCount = Browser.WebTable.getRowCount("Bill_Prof");
				if (RowCount == 2) {
					Result.fUpdateLog("Proceeding to RTB Screen " + BillingProfile);
					Result.takescreenshot("Proceeding to RTB Screen " + BillingProfile);
					Browser.WebTable.clickL("Bill_Prof", Row, Col);
					Utlities.StoreValue("BillingProfileName", BillingProfile);
				} else {
					Result.fUpdateLog("Billing Profile Not Found");
					Result.takescreenshot("Billing Profile Not Found");
					Continue.set(false);
				}
			}
			if (RowCount >= 2) {
				Boolean flag = false;
				Col = CO.Select_Cell("Bill_Prof", "Payment Type");
				for (int i = 2; i <= RowCount; i++)
					if (Browser.WebTable.getCellData("Bill_Prof", i, Col).equalsIgnoreCase("Postpaid")) {
						Col = CO.Select_Cell("Bill_Prof", "Name");
						BillingProfile = Browser.WebTable.getCellData("Bill_Prof", i, Col);
						Result.fUpdateLog("Proceeding to RTB Screen " + BillingProfile);
						Result.takescreenshot("Proceeding to RTB Screen " + BillingProfile);
						Utlities.StoreValue("BillingProfileName", BillingProfile);
						Browser.WebTable.clickL("Bill_Prof", i, Col);
						flag = true;
						break;
					}
				if (flag == false) {
					Result.fUpdateLog("Postpaid Billing Profile Not Found");
					Result.takescreenshot("Postpaid Billing Profile Not Found");
					Continue.set(false);
				}
			} else {
				Result.fUpdateLog("Billing Profile Not Found");
				Result.takescreenshot("Billing Profile Not Found");
				Continue.set(false);
			}

			CO.waitmoreforload();

			if (Browser.WebTable.exist("Bills")) {
				Result.fUpdateLog("Proceeding with data fetch");
				Result.takescreenshot("Proceeding with data fetch");
			} else {
				CO.Text_Select("a", "Bills");
				CO.waitforload();
				if (Browser.WebTable.exist("Bills")) {
					Result.fUpdateLog("Proceeding with data fetch");
					Result.takescreenshot("Proceeding with data fetch");
				} else {
					Result.fUpdateLog("Unable to proceed with data fetch -- No Records Found ");
					Result.takescreenshot("Unable to proceed with data fetch -- No Records Found ");
					Continue.set(false);
				}
			}

			Amtdue = Browser.WebEdit.gettext("Due_Now").replaceAll("QR", "").replace(",", "");
			DateFormat DF = new SimpleDateFormat("MM/dd/yyyy");
			Date date;
			Calendar c = Calendar.getInstance();

			RowCount = Browser.WebTable.getRowCount("Bills");
			if (RowCount > 1) {
				Result.fUpdateLog("Fetching Bill Cycle, Due Amount, Due Date ");
				// String BillPeriod;
				Col = CO.Select_Cell("Bills", "Amount Due");
				// Amtdue=(Browser.WebTable.getCellData("Bills", Row, Col)).replaceAll("QR",
				// "");
				AmtOwed = Double.parseDouble(Amtdue);
				Col = CO.Select_Cell("Bills", "Bill Period");
				// BillPeriod = Browser.WebTable.getCellData("Bills", Row, Col);
				// BillDate = BillPeriod.split("-")[1];
				Col = CO.Select_Cell("Bills", "Due Date");
				// Date Format Conversion for Due Date
				DueDate = Browser.WebTable.getCellData("Bills", Row, Col);
				date = DF.parse(DueDate);
				c.setTime(date);
				DateFormat DDF = new SimpleDateFormat("MMddHHmmyyyy");
				DueDate = DDF.format(c.getTime());

				Utlities.StoreValue("DueDate", DueDate);

			} else {
				Result.fUpdateLog("Unable to proceed with data fetch -- No Records Found ");
				Result.takescreenshot("Unable to proceed with data fetch -- No Records Found ");
				Continue.set(false);
			}

			if (Browser.WebButton.exist("CreditScore")) {
				CO.scroll("ThirdLevelView", "WebButton");
				Browser.WebButton.click("ThirdLevelView");
				CO.waitforload();
				Browser.WebButton.click("CreditScore");
			} else {
				CO.Text_Select("a", "Credit Score");
			}
			CO.waitforload();
			RowCount = Browser.WebTable.getRowCount("CreditScore");
			if (RowCount == 2) {
				String Score = Browser.WebTable.getCellData("CreditScore", RowCount,
						Browser.WebTable.getColCount("CreditScore"));
				CreditScore = Integer.parseInt(Score);
				Result.fUpdateLog("Fetching Credit Score");
				Result.takescreenshot("Fetching Credit Score");
			} else {
				Result.fUpdateLog("Unable to Fetch Credit Score -- No Records Found");
				Result.takescreenshot("Unable to Fetch Credit Score -- No Records Found");
				Continue.set(false);
			}

			CO.DunningCalendar(Segment, CreditScore, AmtOwed);

			Result.fUpdateLog("Displaying the Due Date " + DueDate);

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Dunning Process is Applicable to the customer and done Successfully " + ",";
				Result.fUpdateLog("Dunning Process is Applicable to the customer and done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Dunning Process Failed" + ",";
				Result.takescreenshot("Dunning Process Failed");
				Result.fUpdateLog("Dunning Process Failed");
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
		Result.fUpdateLog("------Dunning Process Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Change_PrimaryNumber
	 * Arguments			: None
	 * Use 					: Change of Primary number
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Change_PrimaryNumber() {
		String Test_OutPut = "", Status = "";
		String MSISDN, GetData = null, Order_no;
		Result.fUpdateLog("------Change Primary Number Event Details------");
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
			CO.Tag_Select("span", "Primary MSISDN");
			if (Browser.WebButton.exist("Assert_Modify")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				int Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");

				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_P + 1);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						CO.waitforload();
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else
				CO.InstalledAssertChange("Modify");
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitforload();
			CO.Link_Select("Others");
			CO.Radio_Select("Make Primary MSISDN");
			Result.takescreenshot("");
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
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			Test_OutPut += OrderSubmission().split("@@")[1];
			CO.waitforload();

			// fetching Order_no

			CO.RTBScreen(MSISDN, "Active");
			CO.waitforload();

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Status = "PASS";
				Utlities.StoreValue("Sales_OrderNO", Order_no);
				Test_OutPut += "Order_No : " + Order_no + ",";
			} else {
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
		Result.fUpdateLog("-----Change Primary Number Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: LanguageChange
	 * Arguments			: None
	 * Use 					: Modify the Order to User Prefered Language
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 15-Mar-2018
	--------------------------------------------------------------------------------------------------------*/
	public String LanguageChange() {
		String Test_OutPut = "", Status = "";
		String Language = "", GetData, MSISDN;
		Result.fUpdateLog("------ Language Change - Siebel ---------");
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

			if (!(getdata("Language").equals(""))) {
				Language = getdata("Language");
			} else {
				Language = pulldata("Language");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitmoreforload();
			if (Browser.WebButton.exist("Assert_Modify")) {

				int Col_P, Col_SID, Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						CO.waitforload();
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else {
				CO.InstalledAssertChange("Modify");
			}
			Result.takescreenshot("Modifying Plan for Language Change");
			Result.fUpdateLog("Modifying Plan for Language Change");

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			// wait
			Result.takescreenshot("Navigating to Others Tab");
			Result.fUpdateLog("Navigating to Others Tab");
			CO.waitmoreforload();
			CO.Link_Select("Others");
			CO.waitforload();
			CO.Customise("Mobile Voicemail");

			CO.scroll("Language", "WebLink");
			Browser.WebLink.click("Language");
			CO.waitforload();
			Result.takescreenshot("Default Language before selection");
			Result.fUpdateLog("Default Language before selection");
			CO.Text_Select("option", Language);
			CO.waitforload();
			Result.takescreenshot("Language Selected as " + Language);
			Result.fUpdateLog("Language Selected as " + Language);

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
			int Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}

			CO.waitforload();
			CO.LineItems_Data();
			if (Browser.WebEdit.exist("Ent_CreditLimit")) {
				CO.scroll("Ent_CreditLimit", "WebEdit");
				Browser.WebEdit.click("Ent_CreditLimit");
				Browser.WebEdit.Set("Ent_CreditLimit", "100");
			}

			Test_OutPut += OrderSubmission().split("@@")[1];
			// fetching Order_no
			String Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.ToWait();
			CO.GetSiebelDate();

			if (Continue.get()) {
				Test_OutPut += "Language Change - Siebel is done Successfully " + ",";
				Result.fUpdateLog("Language Change - Siebel is  done successfully");
				Status = "PASS";
			} else {
				Test_OutPut += "Language Change - Siebel Failed" + ",";
				Result.takescreenshot("Language Change - Siebel Failed");
				Result.fUpdateLog("Language Change - Siebel Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Language Change - Siebel - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Activities
	 * Arguments			: None
	 * Use 					: Creating or Closing a specific activity in Siebel
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 15-Mar-2018
	--------------------------------------------------------------------------------------------------------*/
	public String Activities() {
		String Test_OutPut = "", Status = "";
		String Channel, Priority, Comment, A_Language, Topic, Type, Sub_Topic, Interaction_Type, ContactRole, MSISDN;// Language
																														// =
																														// "",
																														// GetData,
		int Col, Row = 2;
		Result.fUpdateLog("------ Activities Creation / Closure - Siebel ---------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("Activity_Channel").equals(""))) {
				Channel = getdata("Activity_Channel");
			} else {
				Channel = "Appointment";// pulldata("GetData");
			}

			if (!(getdata("Priority").equals(""))) {
				Priority = getdata("Priority");
			} else {
				Priority = pulldata("Priority");
			}

			if (!(getdata("Comment").equals(""))) {
				Comment = getdata("Comment");
			} else {
				Comment = pulldata("Comment");
			}

			if (!(getdata("A_Language").equals(""))) {
				A_Language = getdata("A_Language");
			} else {
				A_Language = pulldata("A_Language");
			}

			if (!(getdata("Topic").equals(""))) {
				Topic = getdata("Topic");
			} else {
				Topic = pulldata("Topic");
			}

			if (!(getdata("Type").equals(""))) {
				Type = getdata("Type");
			} else {
				Type = pulldata("Type");
			}

			if (!(getdata("Sub_Topic").equals(""))) {
				Sub_Topic = getdata("Sub_Topic");
			} else {
				Sub_Topic = pulldata("Sub_Topic");
			}

			if (!(getdata("Interaction_Type").equals(""))) {
				Interaction_Type = getdata("Interaction_Type");
			} else {
				Interaction_Type = pulldata("Interaction_Type");
			}

			if (!(getdata("ContactRole").equals(""))) {
				ContactRole = getdata("ContactRole");
			} else {
				ContactRole = pulldata("ContactRole");
			}
			CO.waitmoreforload();

			CO.AssertSearch(MSISDN, "Active");

			if (Browser.WebLink.exist("Acc_Portal")) {
				CO.waitforload();
				Browser.WebLink.click("Acc_Portal");
			}

			CO.Text_Select("a", "Activities");
			CO.waitforload();
			int RowCount = Browser.WebTable.getRowCount("Activities");

			if (TestCaseN.get().toLowerCase().contains("create")) {

				Result.takescreenshot("Creating New Activity");
				Result.fUpdateLog("Creating New Activity");

				Browser.WebButton.click("NewActivity");
				CO.waitforload();
				int RowCount1 = Browser.WebTable.getRowCount("Activities");

				if ((RowCount + 1) == RowCount1) {
					Result.takescreenshot("Adding New Activity");
					Result.fUpdateLog("Adding New Activity");
					Col = CO.Select_Cell("Activities", "Channel");
					Browser.WebTable.SetDataE("Activities", Row, Col, "Channel", Channel);

					Col = CO.Select_Cell("Activities", "Due");
					Browser.WebTable.Popup("Activities", Row, Col);

					CO.scroll("Date_Now", "WebButton");
					Browser.WebButton.click("Date_Now");
					CO.scroll("Date_Done", "WebButton");
					Browser.WebButton.click("Date_Done");
					CO.waitforload();

					Col = CO.Select_Cell("Activities", "Status");
					Browser.WebTable.SetDataE("Activities", Row, Col, "Status", "Open");
					CO.waitforload();

					Col = CO.Select_Cell("Activities", "Priority");
					Browser.WebTable.SetDataE("Activities", Row, Col, "Priority", Priority);

					Col = CO.Select_Cell("Activities", "Comments");
					Browser.WebTable.CommentE("Activities", Row, Col, "Comment", Comment);

					Col = CO.Select_Cell("Activities", "Language");
					Browser.WebTable.SetDataE("Activities", Row, Col, "VFQA_Language", A_Language);

					Col = CO.Select_Cell("Activities", "MSISDN");
					Browser.WebTable.Popup("Activities", Row, Col);
					Browser.WebButton.click("Service_OK");

					Col = CO.Select_Cell("Activities", "Topic");
					Browser.WebTable.SetDataE("Activities", Row, Col, "Topic", Topic);

					Col = CO.Actual_Cell("Activities", "Type");
					Browser.WebTable.SetDataE("Activities", Row, Col, "Type", Type);

					Col = CO.Select_Cell("Activities", "Sub-Topic");
					Browser.WebTable.SetDataE("Activities", Row, Col, "Sub-Topic", Sub_Topic);

					Col = CO.Select_Cell("Activities", "Interaction Type");
					Browser.WebTable.SetData("Activities", Row, Col, "Interaction_Type", Interaction_Type);

					Col = CO.Select_Cell("Activities", "Contact Role");
					Browser.WebTable.SetData("Activities", Row, Col, "ContactRole", ContactRole);

					Browser.WebButton.click("Activity_Popup1");
					CO.isAlertExist();
					Browser.WebButton.click("Activity_Popup2");
					CO.isAlertExist();
					Col = CO.Select_Cell("Activities", "Activity #");
					String ActivityId = Browser.WebTable.getCellData("Activities", Row, Col);
					// Activity #
					Result.takescreenshot("Activity Created in Open Status with Activity Id " + ActivityId);
					Result.fUpdateLog("Activity Created in Open Status with Activity Id " + ActivityId);
				} else {
					Continue.set(false);
					Result.takescreenshot("Activity is not Created");
					Result.fUpdateLog("Activity is not Created");
				}
			} else {
				Result.takescreenshot("Closing All Open Activities");
				Result.fUpdateLog("Closing All Open Activities");

				Browser.WebButton.click("Activity_Query");
				CO.waitforload();

				Col = CO.Select_Cell("Activities", "Status");
				Browser.WebTable.SetDataE("Activities", Row, Col, "Status", "Open");
				Result.takescreenshot("Query - Open Activities");
				Result.fUpdateLog("Query - Open Activities");
				Browser.WebButton.click("Activity_GO");
				CO.waitforload();

				int RowCount1 = Browser.WebTable.getRowCount("Activities");

				if (RowCount1 >= 2) {

					for (int R = 2; R <= RowCount1; R++) {

						Browser.WebTable.SetData("Activities", R, Col, "Status", "Close");

						Col = CO.Select_Cell("Activities", "Activity #");
						String ActivityId = Browser.WebTable.getCellData("Activities", R, Col);

						Result.takescreenshot("Closing Activity " + ActivityId);
						Result.fUpdateLog("Closing Activity " + ActivityId);

						Col = CO.Select_Cell("Activities", "Status");
					}
				} else {
					Continue.set(false);
					Result.takescreenshot("No Activity is in Open Status to close please check the data");
					Result.fUpdateLog("No Activity is in Open Status to close please check the data");
				}
			}

			if (Continue.get()) {
				Test_OutPut += "Activities Creation / Closure - Siebel is done Successfully " + ",";
				Result.fUpdateLog("Activities Creation / Closure  - Siebel is  done successfully");
				Status = "PASS";
			} else {
				Test_OutPut += "Activities Creation / Closure - Siebel Failed" + ",";
				Result.takescreenshot("Activities Creation / Closure - Siebel Failed");
				Result.fUpdateLog("Activities Creation / Closure - Siebel Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Activities Creation / Closure - Siebel - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Credit_Limit
	 * Arguments			: None
	 * Use 					: Credit Limit Set
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 21-Mar-2018
	--------------------------------------------------------------------------------------------------------*/
	public String CreditLimit() {
		String Test_OutPut = "", Status = "";
		String CreditLimit, WinCashReference, GetData, MSISDN;
		Result.fUpdateLog("------ Credit Limit Set - Siebel ---------");
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

			if (!(getdata("CreditLimit").equals(""))) {
				CreditLimit = getdata("CreditLimit");
			} else {
				CreditLimit = pulldata("CreditLimit");
			}

			if (!(getdata("WinCashReference").equals(""))) {
				WinCashReference = getdata("CreditLimit");
			} else if (!(pulldata("WinCashReference").equals(""))) {
				WinCashReference = pulldata("CreditLimit");
			} else {
				WinCashReference = "VFQA_Test" + R.nextInt(100) + "1" + R.nextInt(100);
			}

			// WinCashReference

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitmoreforload();
			if (Browser.WebButton.exist("Assert_Modify")) {

				int Col_P, Col_SID, Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						CO.waitforload();
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else {
				CO.InstalledAssertChange("Modify");
			}
			Result.takescreenshot("Modifying Plan for Language Change");
			Result.fUpdateLog("Modifying Plan for Language Change");

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");

			CO.scroll("LI_New", "WebButton");
			int Col, RowCount = Browser.WebTable.getRowCount("Line_Items");
			Browser.WebButton.click("LI_New");

			Col = CO.Select_Cell("Line_Items", "Product");
			Browser.WebTable.SetDataE("Line_Items", RowCount + 1, Col, "Product", "Consumer Account Level Bundle");
			Browser.WebTable.click("Line_Items", RowCount + 1, Col + 1);
			CO.waitforload();
			Result.takescreenshot("Adding Consumer Account Level Bundle to the Asserts");
			Result.fUpdateLog("Adding Consumer Account Level Bundle to the Asserts");
			Browser.WebButton.click("Customize");
			CO.waitforload();
			Browser.WebLink.click("Language");
			CO.Text_Select("Option", CreditLimit);
			String Qty = "1";
			Browser.WebEdit.Set("NumberReservationToken", Qty);
			Browser.WebButton.click("AddItem");
			CO.waitforload();
			Result.takescreenshot("Adding Credit Limit to the Line Items");
			Result.fUpdateLog("Adding Credit Limit to the Line Items");
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");

			CO.waitforload();
			// CO.scroll("Payments_ThirdLevelTab", "WebLink");
			CO.TabNavigator("Payments");

			CO.waitforload();
			Result.takescreenshot("Proceeding Payment for Credit Limit");
			Result.fUpdateLog("Proceeding Payment for Credit Limit");
			Browser.WebButton.click("AddPayment");
			CO.waitforload();
			RowCount = Browser.WebTable.getRowCount("PaymentList");
			if (RowCount == 2) {
				Browser.WebEdit.Set("WinCashReference", WinCashReference);

				Result.takescreenshot("Adding Payment");
				Result.fUpdateLog("Adding Payment");

				Actions a = new Actions(cDriver.get());
				WebElement we = cDriver.get().findElement(By.xpath("//body"));
				a.sendKeys(we, Keys.chord(Keys.CONTROL, "s")).perform();

				CO.waitforload();
				Col = CO.Select_Cell("PaymentList", "Payment Status");
				String PaymentId, Pay_Status = Browser.WebTable.getCellData("PaymentList", RowCount, Col);
				Col = CO.Select_Cell("PaymentList", "Payment #");
				PaymentId = Browser.WebTable.getCellData("PaymentList", RowCount, Col);
				if (Pay_Status.equalsIgnoreCase("authorized")) {
					Result.takescreenshot(
							"Payment is Authorized and is in " + Pay_Status + " for Payment Id " + PaymentId);
					Result.fUpdateLog("Payment is Authorized and is in " + Pay_Status + " for Payment Id " + PaymentId);
				} else {
					Continue.set(false);
					Result.takescreenshot(
							"Payment is not Authorized and is in " + Pay_Status + " for Payment Id " + PaymentId);
					Result.fUpdateLog(
							"Payment is not Authorized and is in " + Pay_Status + " for Payment Id " + PaymentId);
				}
			} else {
				Continue.set(false);
				Result.takescreenshot("Payment Could not be added -- Check User Access");
				Result.fUpdateLog("Payment Could not be added -- Check User Access");
			}

			CO.waitforload();
			CO.TabNavigator("Line Items");
			int Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				for (int Row = 3; Row <= Row_Count; Row++) {
					Browser.WebTable.Expand("Line_Items", Row_Count);
					CO.waitforload();
				}
				Result.takescreenshot("Getting Line Item Data");
				Result.fUpdateLog("Getting Line Item Data");
			}

			CO.waitforload();
			CO.LineItems_Data();

			Test_OutPut += OrderSubmission().split("@@")[1];

			String Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.ToWait();
			CO.GetSiebelDate();

			if (Continue.get()) {
				Test_OutPut += "Credit Limit Set - Siebel is done Successfully " + ",";
				Result.fUpdateLog("Credit Limit Set - Siebel is  done successfully");
				Status = "PASS";
			} else {
				Test_OutPut += "Credit Limit Set - Siebel Failed" + ",";
				Result.takescreenshot("Credit Limit Set - Siebel Failed");
				Result.fUpdateLog("Credit Limit Set - Siebel Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Credit Limit Set - Siebel - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: MVCareBill
	 * Arguments			: None
	 * Use 					: Upgrade Promotion via Account 360 View
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 31-03-2018
	--------------------------------------------------------------------------------------------------------*/
	public String MVCareBill() {
		String Test_OutPut = "", Status = "";
		String MSISDN;
		int RowCount, Col;

		Result.fUpdateLog("------Mcare Event details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			

			/*
			 * if (!(getdata("GetData").equals(""))) { GetData = getdata("GetData"); } else
			 * { GetData = pulldata("GetData"); }
			 */

			CO.waitforload();
			Browser.WebLink.waittillvisible("Global_Search");
			Browser.WebLink.click("Global_Search");
			CO.waitforload();

			Browser.WebEdit.Set("Phone_Guided", "97478152212");
			Result.fUpdateLog("Global Search Initiation");
			Result.takescreenshot("Global Search Initiation");
			Browser.WebLink.click("GS_Go");
			CO.waitforload();
			Thread.sleep(1000);

			Browser.WebLink.waittillvisible("Global_Link");
			Result.fUpdateLog("Global Search MSISDN Retrived");
			Result.takescreenshot("Global Search MSISDN Retrived");
			CO.waitforload();

			Browser.WebLink.click("Global_Link");
			CO.waitforload();

			Result.fUpdateLog("Account 360 View");
			Result.takescreenshot("Account 360 View");
			Browser.WebLink.click("Global_Search");

			Result.fUpdateLog("Retriving Actual MSISDN Record");
			Result.takescreenshot("Retriving Actual MSISDN Record");
			RowCount = Browser.WebTable.getRowCount("Installed_Assert360");
			if (RowCount > 3) {

				Col = CO.Select_Cell("Installed_Assert360", "Phone Number");
				for (int Row = 3; Row <= RowCount; Row++)
					if ((Browser.WebTable.getCellData("Installed_Assert360", Row, Col)).equals(MSISDN)) {
						Browser.WebTable.click("Installed_Assert360", Row, Col);
						Result.fUpdateLog("Retrived Actual MSISDN Record");
						Result.takescreenshot("Retrived Actual MSISDN Record");
						break;
					}

			}

			CO.waitforload();
			CO.Text_Select("div", "Asset Summary");
			CO.waitforload();
			Browser.WebButton.waittillvisible("UpgradePromotion");
			CO.waitforload();

			Result.fUpdateLog("Upgrading Promotion via Account 360");
			Result.takescreenshot("Upgrading Promotion via Account 360");

			CO.scroll("UpgradePromotion", "WebButton");
			CO.waitforload();

			String MCA_PlanName = cDriver.get()
					.findElement(By.xpath("//div[@class='vfqa-360view-assetdetails-title']//label")).getText();
			String MCA_Addon = cDriver.get()
					.findElement(By.xpath("//div[@class='vfqa-360view-assetdetails-field vfqa-360view-addons']//a"))
					.getText();
			Utlities.StoreValue("MCA_PlanName", MCA_PlanName);
			Test_OutPut += "MCA_PlanName : " + MCA_PlanName + ",";

			Utlities.StoreValue("MCA_Addon", MCA_Addon);
			Test_OutPut += "MCA_Addon : " + MCA_Addon + ",";

			CO.waitforload();
			if (Browser.WebButton.exist("view_bills")) {
				Result.fUpdateLog("View Bills Button is Enabled ");
				Browser.WebButton.click("view_bills");
				CO.waitforload();
				Result.takescreenshot(" View Bills Button is clicked ");

			}
			String MCA_BillAmt = Browser.WebEdit.gettext("RTB_Total");
			Utlities.StoreValue("BillAmt", MCA_BillAmt);
			Test_OutPut += "Balance: " + MCA_BillAmt + ",";

			cDriver.get().navigate().back();
			CO.waitforload();
			Browser.WebLink.click("Acc_Portal");
			
			String Contact_Id= Browser.WebEdit.gettext("Site_val");
			Contact_Id=Contact_Id.split("_")[1];
			CO.waitforload();
			/*Browser.WebButton.waittillvisible("Contact");
			Browser.WebButton.click("Contact");
			
			int CID=CO.Actual_Cell("Contact_table", "ID Number");
			String Contact_Id=Browser.WebTable.getCellData("Contact_table",2,CID);*/
			
			Utlities.StoreValue("Contact_Id", Contact_Id);
			Test_OutPut += "Contact_Id: " + Contact_Id + ",";
			

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Test_OutPut += "Upgrade Promotion via Account 360 view is done Successfully " + ",";
				Result.fUpdateLog("Upgrade Promotion via Account 360 view is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Upgrade Promotion via Account 360 view Failed" + ",";
				Result.takescreenshot("Upgrade Promotion via Account 360 view Failed");
				Result.fUpdateLog("Upgrade Promotion via Account 360 view Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Mcare Event View Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name				: BillSummary
	 * Arguments				: None
	 * Use 						: BillSummary
	 * Designed By				: Sravanireddy
	 * Last Modified Date 		: 03-Mar-2018
	--------------------------------------------------------------------------------------------------------*/
	public String BillSummary() {

		String Test_OutPut = "", Status = "";
		String MSISDN, GetData,  BillingProfile, BillAmt = "",Voucher_Amt="";
		int  Col_P, Col,  Row = 2;
		Result.fUpdateLog("------BillSummary Event Details------");
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
			if (!(getdata("Voucher_Amt").equals(""))) {
				Voucher_Amt = getdata("Voucher_Amt");
			} else {
				Voucher_Amt = pulldata("Voucher_Amt");
			}
			
			CO.GetSiebelDate();
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			Col_P = CO.Actual_Cell("Acc_Installed_Assert", "Billing Profile");
			CO.waitforload();
			BillingProfile = Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col_P);
			CO.waitforload();
			Result.takescreenshot("Bill No for the MSISDN " + MSISDN + " is " + BillingProfile);

			CO.Link_Select("Profiles");
			Result.takescreenshot("BillingProfile");
			CO.waitforload();
			Browser.WebButton.click("Profile_Query");
			Col_P = CO.Select_Cell("Bill_Prof", "Name");
			Col = CO.Select_Cell("Bill_Prof", "Status");
			Browser.WebTable.SetData("Bill_Prof", Row, Col_P, "Name", BillingProfile);
			CO.waitforload();
			CO.waitforload();
			if (Browser.WebTable.getRowCount("Bill_Prof") >= 2) {
				Browser.WebTable.click("Bill_Prof", Row, Col);
				Browser.WebTable.clickA("Bill_Prof", Row, Col_P);
			} else
				Continue.set(false);

			CO.waitforload();
			BillAmt = Browser.WebEdit.gettext("RTB_Total");
			Browser.WebButton.click("Payments_Editable");
			Result.takescreenshot("Payments");
			String Date1 = OrderDate.get();
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");

			Date date = format1.parse(Date1);
			String Dat = format2.format(date);

			CO.waitforload();
			CO.waitforload();
			Col = CO.Select_Cell("Payments", "Channel");
			String Pay_Channel = Browser.WebTable.getCellData("Payments", Row, Col);
			Col = CO.Select_Cell("Payments", "Payment Date");
			String Pay_Date = Browser.WebTable.getCellData("Payments", Row, Col);
			/*Col = CO.Select_Cell("Payments", "Payment Method");
			String Pay_Method = Browser.WebTable.getCellData("Payments", Row, Col);*/
			Col = CO.Select_Cell("Payments", "Payment Amount");
			String Pay_Amt = Browser.WebTable.getCellData("Payments", Row, Col);
			OrderDate.get();
			String amt = Total_DueAmt.get();
			Dat = Dat.replaceAll("01", "1").replaceAll("02", "2").replaceAll("03", "3").replaceAll("04", "4")
					.replaceAll("05", "5").replaceAll("06", "6").replaceAll("07", "7").replaceAll("08", "8")
					.replaceAll("09", "9").replaceAll("218", "2018").replaceAll("219", "2019");
			Double x, y, z;
			String k = BillAmt.split("QR")[1];
			String[] obj = k.split(",");
			if (obj.length > 1)
				k = k.replace(',', ' ');
			String p=Pay_Amt.split("QR")[1];
			String[] obj1 = p.split(",");
			if (obj1.length > 1)
				p = p.replace(',', ' ');
			String q=amt.split("QR")[1];
			String[] obj2 = q.split(",");
			if (obj2.length > 1)
				q = q.replace(',', ' ');		
			y = Double.parseDouble(k);
			z = Double.parseDouble(p);			
			x = Double.parseDouble(q);
			if (Voucher_Amt.equalsIgnoreCase(p)) {
				Result.fUpdateLog("Updated with Correct Voucher Amount");
			}else
			{
				Continue.set(false);
			}
			
			if (Pay_Channel.equalsIgnoreCase("USSD") && Pay_Date.equalsIgnoreCase(Dat)) {
				Double d = y + z;
				System.out.println(d);
				if (x == d) {
					Result.fUpdateLog("BillPayment is Successfull");
				} else {
					Result.fUpdateLog("BillPayment is NOT Successfull");
				}

			}

	
			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "";
				Result.takescreenshot("BillPayment is Successfull");
				Result.fUpdateLog("BillPayment is Successfull");
				Status = "PASS";
			} else {
				Test_OutPut += "BillPayment Failed" + ",";
				Result.takescreenshot("BillPayment Failed");
				Result.fUpdateLog("BillPayment Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------BillSummary Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}


	/*---------------------------------------------------------------------------------------------------------
	 * Method Name				: HappyOffers
	 * Arguments				: None
	 * Use 						: HappyOffers
	 * Designed By				: SravaniReddy
	 * Last Modified Date 		: 28-Mar-2018
	--------------------------------------------------------------------------------------------------------*/
	public String HappyOffers() {

		String Test_OutPut = "", Status = "";
		String MSISDN  ;
		int  Col_P;
		Result.fUpdateLog("------HappyOffers Event Details------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

		

			// Fetching Billing Profile Name from the Provided MSISDN

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			int Inst_RowCount = Browser.WebTable.getRowCount("Installed_Assert");
			Col_P = CO.Actual_Cell("Installed_Assert", "Service ID");//
			int Col_Ad = CO.Actual_Cell("Installed_Assert", "Asset Description");
			Browser.WebTable.click("Installed_Assert", 2, Col_Ad);
			Browser.WebTable.getRowCount("Acc_Installed_Assert");
			// To Find the Record with Mobile Service Bundle and MSISDN
			for (int i = 2; i <= Inst_RowCount; i++)
				if (!(Browser.WebTable.getCellData("Installed_Assert", i, Col_P).equalsIgnoreCase(""))) {
					Browser.WebTable.clickL("Installed_Assert", i, Col_P);
					break;
				}
			CO.scroll("WebTable", "Campaign_History_Table");
			Result.takescreenshot("Campaign_History_Table");
			
			
			int Col_Type = CO.Actual_tab_Cell("Campaign_History_Col", "Message Text");
			int Col_Dat = CO.Actual_tab_Cell("Campaign_History_Col", "Contacted On");
			String Camp_Des = Browser.WebTable.getCellData("Campaign_History_Table", 2, Col_Type);
			String Camp_Dat = Browser.WebTable.getCellData("Campaign_History_Table", 2, Col_Dat);
			Utlities.StoreValue("Campaign Description", Camp_Des);
			Test_OutPut += "Campaign Description : " + Camp_Des + ",";

			Utlities.StoreValue("Campaign Activation Date", Camp_Dat);
			Test_OutPut += "Campaign Activation Date : " + Camp_Dat + ",";

			CO.scroll("WebTable", "Response_History_Table");
			Result.takescreenshot("Response_History_Table");
			Col_Type = CO.Actual_tab_Cell("Response_History_Col", "Product Name");
			String Prod_Name = Browser.WebTable.getCellData("Response_History_Table", 2, Col_Type);
			Col_Type = CO.Actual_tab_Cell("Response_History_Col", "Customer Response");
			String Res_Cus = Browser.WebTable.getCellData("Response_History_Table", 2, Col_Type);
			Col_Type = CO.Actual_tab_Cell("Response_History_Col", "Channel");
			String Res_Chan = Browser.WebTable.getCellData("Response_History_Table", 2, Col_Type);
			Col_Type = CO.Actual_tab_Cell("Response_History_Col", "Response Date");
			String Res_Dat = Browser.WebTable.getCellData("Response_History_Table", 2, Col_Type);
			Utlities.StoreValue("Product name in Response History", Prod_Name);
			Test_OutPut += "Product name in Response History : " + Prod_Name + ",";
			Utlities.StoreValue("Customer response in Response History", Res_Cus);
			Test_OutPut += "Customer response in Response History : " + Res_Cus + ",";
			Utlities.StoreValue("Responded Channel in Response History", Res_Chan);
			Test_OutPut += "Responded Channel in Response History : " + Res_Chan + ",";
			Utlities.StoreValue("Responded date in Response History", Res_Dat);
			Test_OutPut += "Responded date in Response History : " + Res_Dat + ",";
			
			CO.scroll("WebTable", "Provision_History_Table");
			Result.takescreenshot("Provision_History_Table");
			
			Col_Type = CO.Actual_tab_Cell("Provision_History_Col", "Product Name");
			Col_Dat = CO.Actual_tab_Cell("Provision_History_Col", "Status");
			String Offer_Des = Browser.WebTable.getCellData("Provision_History_Table", 2, Col_Type);
			String Stat = Browser.WebTable.getCellData("Provision_History_Table", 2, Col_Dat);
			
			Utlities.StoreValue("Product Name in Provision History", Offer_Des);
			Test_OutPut += "Product Name in Provision History : " + Offer_Des + ",";
			Utlities.StoreValue("Status in Provision History", Stat);
			Test_OutPut += "Status in Provision History : " + Stat + ",";

			
			
			
			
			
			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "";
				Result.takescreenshot("BillPayment is Successfull");
				Result.fUpdateLog("BillPayment is Successfull");
				Status = "PARTIALPASS";
			} else {
				Test_OutPut += "BillPayment Failed" + ",";
				Result.takescreenshot("BillPayment Failed");
				Result.fUpdateLog("BillPayment Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------HappyOffers Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name				: DueAmount
	 * Arguments				: None
	 * Use 						: BillPayment
	 * Designed By				: SravaniReddy
	 * Last Modified Date 		: 28-Mar-2018
	--------------------------------------------------------------------------------------------------------*/
	public String DueAmount() {

		String Test_OutPut = "", Status = "";
		String MSISDN, GetData,  BillingProfile, MCA_Total = "";
		int Col_P, Col,  Row = 2;
		Result.fUpdateLog("------Fecting BP Summary Details------");
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
			
			CO.GetSiebelDate();
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.waitforload();
			Col_P = CO.Actual_Cell("Acc_Installed_Assert", "Billing Profile");
			CO.waitforload();
			BillingProfile = Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col_P);
			CO.waitforload();
			Result.takescreenshot("Bill No for the MSISDN " + MSISDN + " is " + BillingProfile);
			CO.Link_Select("Profiles");
			CO.waitforload();
			Browser.WebButton.click("Profile_Query");
			Col_P = CO.Select_Cell("Bill_Prof", "Name");
			Col = CO.Select_Cell("Bill_Prof", "Status");
			Browser.WebTable.SetData("Bill_Prof", Row, Col_P, "Name", BillingProfile);
			CO.waitforload();
			CO.waitforload();
			if (Browser.WebTable.getRowCount("Bill_Prof") >= 2) {
				Browser.WebTable.click("Bill_Prof", Row, Col);
				Browser.WebTable.clickL("Bill_Prof", Row, Col_P);
			} else
				Continue.set(false);

			CO.waitforload();
			Result.takescreenshot("BillingProfile_SummaryScreen");
			String MCA_DueNow=Browser.WebEdit.gettext("RTB_DueNow");
			String MCA_UnbilledUsage=Browser.WebEdit.gettext("RTB_UnbilledUsage");
			
			MCA_Total = Browser.WebEdit.gettext("RTB_Total");
			Total_DueAmt.set(MCA_Total);
			
			Result.takescreenshot("Getting Outstanding Balance" + MCA_Total);
			Utlities.StoreValue("MCA_DueNow", MCA_DueNow);
			Test_OutPut += "MCA_DueNow: " + MCA_DueNow + ",";
						
			Utlities.StoreValue("MCA_UnbilledUsage", MCA_UnbilledUsage);
			Test_OutPut += "MCA_UnbilledUsage: " + MCA_UnbilledUsage + ",";
						
			Utlities.StoreValue("MCA_Total", MCA_Total);
			Test_OutPut += "MCA_Total: " + MCA_Total + ",";
						
		} catch (Exception e) {
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Fecting BP Summary Details------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}


}
