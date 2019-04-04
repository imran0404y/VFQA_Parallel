package Libraries;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class Keyword_DAM extends Driver {
	Common CO = new Common();
	Random R = new Random();
	Keyword_Validations KV = new Keyword_Validations();

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Dam_Login
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the DAM CRM application
	 * Designed By			: sisir
	 * Last Modified Date 	: 20-feb-2019
	---------------------------------------------------------------------------------------------------------*/
	public String Dam_Login() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel Login Event Details------");
		try {

			if (!(getdata("Browser").equals(""))) {
				browser.set(getdata("Browser"));
			} else {
				browser.set("Chrome");
			}

			URL.set(getdata("URL/HOST"));
			Result.fUpdateLog("Enviroment: " + Environment.get());
			Result.fUpdateLog("Url: " + URL.get());
			Browser.OpenBrowser(browser.get(), URL.get());
			if (!Browser.WebLink.exist("Login_Down")) {
				if (Browser.WebLink.exist("Override_Link")) {
					Result.takescreenshot("Opening with IE");
					Browser.WebLink.click("Override_Link");
				}

				// Continue to this website (not recommended).

				Result.fUpdateLog("Browser Opened Successfully");
				Result.takescreenshot("Opening Browser and navigating to the URL");
				Browser.WebEdit.Set("DAM_login_user", getdata("VQ_Login_User"));
				Browser.WebEdit.Set("DAM_login_pwd", getdata("VQ_Login_Pswd"));
				Browser.WebButton.click("DAM_login_button");
				Common.ConditionalWait("DAM_search", "WebEdit");
				// String yy=Browser.getTitle
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
	 * Method Name			: Dam_Logout
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the DAM CRM application
	 * Designed By			: sisir
	 * Last Modified Date 	: 20-feb-2019
	---------------------------------------------------------------------------------------------------------*/
	public String Dam_Logout() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel Logout Event Details------");
		try {
			Result.takescreenshot("DAM Application Logged out");
			// Browser.WebButton.click("DAM_logout");
			Browser.WebLink.waitTillEnabled("DAM_logout");
			Browser.WebLink.click("DAM_logout");
			Result.takescreenshot("DAM Application Logged out");
			cDriver.get().close();
			cDriver.get().quit();
			// CO.ToWait();

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

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Dam_Logout
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the DAM CRM application
	 * Designed By			: sisir
	 * Last Modified Date 	: 20-feb-2019
	---------------------------------------------------------------------------------------------------------*/
	public String Dam_Search() {

		String Test_OutPut = "", Status = "", DAM_msisdn;
		Result.fUpdateLog("-----DAM Search Event Details------");
		try {

			if (!(getdata("DAM_msisdn").equals(""))) {
				DAM_msisdn = getdata("DAM_msisdn");
			} else {
				DAM_msisdn = pulldata("DAM_msisdn");
			}
			Browser.WebEdit.Set("DAM_search", DAM_msisdn);
			System.out.println(getdata("DAM_msisdn"));
			Browser.WebButton.click("DAM_Search_button");
			System.out.println(Browser.WebTable.getCellData("DAM_Users_table", 2, 5));

			Result.takescreenshot("DAM Search Event Details");
			if (Browser.WebTable.getCellData("DAM_Users_table", 2, 5).equals(DAM_msisdn)) {
				Status = "PASS";
			} else {
				Continue.set(false);
				Status = "FAIL";
			}

			if (Continue.get()) {
				Test_OutPut += "DAM Search Event Details";
				Result.fUpdateLog("DAM Search Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "DAM Search Event Details Failed";
				Result.fUpdateLog("DAM Search Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DAM Search Event Details Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Dam_edituser
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the DAM CRM application
	 * Designed By			: anusha
	 * Last Modified Date 	: 03-mar-2019
	---------------------------------------------------------------------------------------------------------*/

	public String Dam_editUser() {

		String Test_OutPut = "", Status = "";

		Result.fUpdateLog("-----DAM editUser Event Details------");
		try {
			// method body
			Browser.WebLink.click("DAM_edit");

			Browser.WebButton.click("DAM_EditSelect_app");
			// CO.scroll("DAM_Edit_Submit", "WebButton");
			Browser.WebButton.click("DAM_Edit_Submit");

			Result.takescreenshot("DAM AddUser Event Details");

			if (Continue.get()) {
				Test_OutPut += "DAM AddUser Event Details";
				Result.fUpdateLog("DAM AddUser Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "DAM Search Event Details Failed";
				Result.fUpdateLog("DAM AddUser Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DAM AddUser Event Details Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: dam_deleteUser
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the DAM CRM application
	 * Designed By			: Anusha
	 * Last Modified Date 	: 03-Mar-2019
	---------------------------------------------------------------------------------------------------------*/

	public String Dam_deleteUser() {

		String Test_OutPut = "", Status = "";

		Result.fUpdateLog("-----DAM editUser Event Details------");
		// Browser.WebTable.CellData(objname, rownum)

		try {
			Browser.WebLink.click("DAM_delete");
			Browser.WebButton.click("DAM_yes");

			if (Continue.get()) {
				Test_OutPut += "DAM AddUser Event Details";
				Result.fUpdateLog("DAM AddUser Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "DAM Search Event Details Failed";
				Result.fUpdateLog("DAM AddUser Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DAM AddUser Event Details Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Dam_Adduser
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the DAM CRM application
	 * Designed By			: sisir
	 * Last Modified Date 	: 21-feb-2019
	---------------------------------------------------------------------------------------------------------*/
	public String Dam_Adduser() {

		String Test_OutPut = "", Status = "";
		String DAM_msisdn, DAM_imei, DAM_FirstName, DAM_LastName, DAM_Notification;

		Result.fUpdateLog("-----DAM AddUser Event Details------");
		try {

			Browser.WebButton.click("DAM_AddUser");

			if (!(getdata("DAM_msisdn").equals(""))) {
				DAM_msisdn = getdata("DAM_msisdn");
			} else {
				DAM_msisdn = pulldata("DAM_msisdn");
			}
			Browser.WebEdit.Set("DAM_msisdn", DAM_msisdn);

			if (!(getdata("DAM_imei").equals(""))) {
				DAM_imei = getdata("DAM_imei");
			} else {
				DAM_imei = pulldata("DAM_imei");
			}
			Browser.WebEdit.Set("DAM_imei", DAM_imei);

			if (!(getdata("DAM_FirstName").equals(""))) {
				DAM_FirstName = getdata("DAM_FirstName");
			} else {
				DAM_FirstName = pulldata("DAM_FirstName");
			}
			Browser.WebEdit.Set("DAM_FirstName", DAM_FirstName);

			if (!(getdata("DAM_LastName").equals(""))) {
				DAM_LastName = getdata("DAM_LastName");
			} else {
				DAM_LastName = pulldata("DAM_LastName");
			}
			Browser.WebEdit.Set("DAM_LastName", DAM_LastName);

			if (!(getdata("DAM_Notification").equals(""))) {
				DAM_Notification = getdata("DAM_Notification");
			} else {
				DAM_Notification = pulldata("DAM_Notification");
			}
			Browser.WebEdit.Set("DAM_Notification", DAM_Notification);

			Browser.WebButton.click("DAM_Select_app");
			Browser.WebButton.click("DAM_submit");
			Result.takescreenshot("DAM AddUser Event Details");
			if (Continue.get()) {
				Test_OutPut += "DAM AddUser Event Details";
				Result.fUpdateLog("DAM AddUser Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "DAM Search Event Details Failed";
				Result.fUpdateLog("DAM AddUser Event Details Failed");
				Status = "FAIL";
			}
			//

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DAM AddUser Event Details Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

///
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Dam_APPsearch
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the DAM CRM application
	 * Designed By			: sisir
	 * Last Modified Date 	: 21-feb-2019
	---------------------------------------------------------------------------------------------------------*/
	public String Dam_AppSearch() {

		String Test_OutPut = "", Status = "";
		String DAM_APP_searchapp = "";
		Result.fUpdateLog("-----DAM editUser Event Details------");
		// Browser.WebTable.CellData(objname, rownum)

		try {
			// CO.Upload(Text, "passport.png");
			Browser.WebLink.click("DAM_Apps_tab");
			if (!(getdata("DAM_APP_searchapp").equals(""))) {
				DAM_APP_searchapp = getdata("DAM_APP_searchapp");
			} else if (!(pulldata("DAM_APP_searchapp").equals(""))) {
				DAM_APP_searchapp = pulldata("DAM_APP_searchapp");
			}
			CO.waitSeconds(1);

			Browser.WebEdit.SetE("DAM_APP_searchapp", DAM_APP_searchapp);
			Browser.WebButton.click("DAM_search_icon");
			Result.takescreenshot("DAMAPP Search Event Details");
			// System.out.println(Browser.WebTable.getCellData("DAM_Users_table", 2, 2));
			if (Browser.WebTable.getCellData("DAM_Users_table", 2, 2).equals(DAM_APP_searchapp)) {
				if (Continue.get()) {
					Test_OutPut += "DAM AddUser Event Details";
					Result.fUpdateLog("DAM AddUser Event Details");
					Status = "PASS";
				} else {
					Test_OutPut += "DAM Search Event Details Failed";
					Result.fUpdateLog("DAM AddUser Event Details Failed");
					Status = "FAIL";
				}
			}

			// Browser.WebButton.click("DAM_APP_delete_yes");
			if (Continue.get()) {
				Test_OutPut += "DAM AddUser Event Details";
				Result.fUpdateLog("DAM AddUser Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "DAM Search Event Details Failed";
				Result.fUpdateLog("DAM AddUser Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DAM AddUser Event Details Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Dam_Appadduser
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the DAM CRM application
	 * Designed By			: Anusha
	 * Last Modified Date 	: 03-mar-2019
	---------------------------------------------------------------------------------------------------------*/

	public String Dam_AppAddUser() {

		String Test_OutPut = "", Status = "";
		String DAM_App_Appname, DAM_App_Url, DAM_App_category, DAM_APP_Type, DAM_APP_Comment;
		Result.fUpdateLog("-----DAM editUser Event Details------");
		// Browser.WebTable.CellData(objname, rownum)

		try {
			Browser.WebLink.click("DAM_Apps_tab");
			CO.waitmoreforload();
			Browser.WebButton.click("DAM_ADD_App");
			CO.waitmoreforload();

			if (!(getdata("DAM_App_Appname").equals(""))) {
				DAM_App_Appname = getdata("DAM_App_Appname") + R.nextInt(1000);
			} else {
				DAM_App_Appname = pulldata("DAM_App_Appname") + R.nextInt(1000);
			}
			Browser.WebEdit.Set("DAM_App_Appname", DAM_App_Appname);
			if (!(getdata("DAM_App_Url").equals(""))) {
				DAM_App_Url = getdata("DAM_App_Url");
			} else {
				DAM_App_Url = pulldata("DAM_App_Url");
			}
			Browser.WebEdit.Set("DAM_App_Url", DAM_App_Url);
			if (!(getdata("DAM_App_category").equals(""))) {
				DAM_App_category = getdata("DAM_App_category");
			} else {
				DAM_App_category = pulldata("DAM_App_category");
			}

			Browser.ListBox.listselect("DAM_App_category", DAM_App_category);
			if (!(getdata("DAM_APP_Type").equals(""))) {
				DAM_APP_Type = getdata("DAM_APP_Type");
			} else {
				DAM_APP_Type = pulldata("DAM_APP_Type");
			}
			Browser.ListBox.listselect("DAM_APP_Type", DAM_APP_Type);

			CO.Upload("DAM_Upload_APP", "passport.png");
			if (!(getdata("DAM_APP_Comment").equals(""))) {
				DAM_APP_Comment = getdata("DAM_APP_Comment");
			} else {
				DAM_APP_Comment = pulldata("DAM_APP_Comment");
			}
			Browser.WebEdit.Set("DAM_APP_Comment", DAM_APP_Comment);

			Browser.WebButton.click("DAM_APP_Submit");

			if (Continue.get()) {
				Test_OutPut += "DAM AddUser Event Details";
				Result.fUpdateLog("DAM AddUser Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "DAM Search Event Details Failed";
				Result.fUpdateLog("DAM AddUser Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DAM AddUser Event Details Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Dam_Appedituser
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the DAM CRM application
	 * Designed By			: Anusha
	 * Last Modified Date 	: 03-mar-2019
	---------------------------------------------------------------------------------------------------------*/
	public String Dam_Appedituser() {

		String Test_OutPut = "", Status = "";
		String Edit_DAM_APP_Url;
		Result.fUpdateLog("-----DAM editUser Event Details------");
		// Browser.WebTable.CellData(objname, rownum)

		try {

			Browser.WebLink.click("DAM_APP_Edit");

			if (!(getdata("Edit_DAM_APP_Url").equals(""))) {
				Edit_DAM_APP_Url = getdata("Edit_DAM_APP_Url");
			} else {
				Edit_DAM_APP_Url = pulldata("Edit_DAM_APP_Url");
			}
			System.out.println(Edit_DAM_APP_Url);
			Browser.WebEdit.Set("Edit_DAM_APP_Url", Edit_DAM_APP_Url);

			Browser.WebButton.click("DAM_Edit_App_Submit");

			if (Continue.get()) {
				Test_OutPut += "DAM AddUser Event Details";
				Result.fUpdateLog("DAM AddUser Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "DAM Search Event Details Failed";
				Result.fUpdateLog("DAM AddUser Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DAM AddUser Event Details Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: dam_deleteApp
	
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the DAM CRM application
	 * Designed By			: Anusha
	 * Last Modified Date 	: 03-Mar-2019
	---------------------------------------------------------------------------------------------------------*/

	public String Dam_deleteApp() {

		String Test_OutPut = "", Status = "";

		Result.fUpdateLog("-----DAM editUser Event Details------");
		// Browser.WebTable.CellData(objname, rownum)

		try {
			Browser.WebLink.click("DAM_APP_Tabdelete");
			// Browser.WebButton.click("DAM_yes");
			Result.takescreenshot("DAM delete App Event Details");
			Browser.WebButton.click("DAM_APP_delete_yes");
			Result.takescreenshot("DAM deleted Event Details");
			if (Continue.get()) {
				Test_OutPut += "DAM AddUser Event Details";
				Result.fUpdateLog("DAM AddUser Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "DAM Search Event Details Failed";
				Result.fUpdateLog("DAM AddUser Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DAM AddUser Event Details Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: portal_AddUser
	 * Arguments			: None
	 * Use 					: Create New Portal user 
	 * Designed By			: sisir
	 * Last Modified Date 	: 26-feb-2019
	---------------------------------------------------------------------------------------------------------*/
	public String Portal_AddUser() {

		String Test_OutPut = "", Status = "", Portal_FirstName = "", Portal_LastName = "", Portal_USERNAME = "",
				Portal_PASSWORD = "", Portal_CONFIRM_PASSWORD = "";
		String Portal_Role = "";
		Result.fUpdateLog("-----portal_AddUser  Event Details------");
		try {

			Browser.WebLink.click("Portal_Users");
			CO.waitSeconds(2);
			Result.takescreenshot("DAM Search Event Details");
			CO.waitSeconds(2);
			Browser.WebButton.click("Portal_AddUser_button");
			CO.waitSeconds(2);
			if (!(getdata("Portal_FirstName").equals(""))) {
				Portal_FirstName = getdata("Portal_FirstName");
			} else {
				Portal_FirstName = pulldata("Portal_FirstName");
			}
			Browser.WebEdit.Set("Portal_FirstName", Portal_FirstName);

			if (!(getdata("Portal_LastName").equals(""))) {
				Portal_LastName = getdata("Portal_LastName");
			} else {
				Portal_LastName = pulldata("Portal_LastName");
			}
			Browser.WebEdit.Set("Portal_LastName", Portal_LastName);

			if (!(getdata("Portal_USERNAME").equals(""))) {
				Portal_USERNAME = getdata("Portal_USERNAME");
			} else {
				Portal_USERNAME = pulldata("Portal_USERNAME") + R.nextInt(10000000);
			}
			Browser.WebEdit.Set("Portal_USERNAME", Portal_USERNAME);

			if (!(getdata("Portal_PASSWORD").equals(""))) {
				Portal_PASSWORD = getdata("Portal_PASSWORD");
			} else {
				Portal_PASSWORD = pulldata("Portal_PASSWORD");
			}
			Browser.WebEdit.Set("Portal_PASSWORD", Portal_PASSWORD);

			if (!(getdata("Portal_CONFIRM_PASSWORD").equals(""))) {
				Portal_CONFIRM_PASSWORD = getdata("Portal_CONFIRM_PASSWORD");
			} else {
				Portal_CONFIRM_PASSWORD = pulldata("Portal_CONFIRM_PASSWORD");
			}
			Browser.WebEdit.Set("Portal_CONFIRM_PASSWORD", Portal_CONFIRM_PASSWORD);

			if (!(getdata("Portal_Role").equals(""))) {
				Portal_Role = getdata("Portal_Role");
			} else {
				Portal_Role = pulldata("Portal_Role");
			}
			Browser.ListBox.listselect("Portal_Role", Portal_Role);
			ArrayList<String> allitemsvalues = new ArrayList<String>();
			allitemsvalues = Browser.ListBox.listAllItems("Portal_Role");
			ListIterator<String> itr = allitemsvalues.listIterator();
			// for(int i=0;i<=itr.l)

			while (itr.hasNext()) {

				System.out.println("index:" + itr.nextIndex() + " value:" + itr.next());
			}

			CO.waitSeconds(2);
			Browser.WebButton.click("Portal_Submit_button");
			Result.takescreenshot("portal_AddUser Event Details");
			CO.waitSeconds(2);
			Result.takescreenshot("portal_AddUser Event Details");

			Utlities.StoreValue("Portal_USERNAME", Portal_USERNAME);
			Test_OutPut += "Portal_USERNAME : " + Portal_USERNAME + ",";

			if (Continue.get()) {
				Test_OutPut += "DAM Search Event Details";
				Result.fUpdateLog("portal_AddUser  Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "DAM Search Event Details Failed";
				Result.fUpdateLog("portal_AddUser  Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------portal_AddUser  Event Details Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PortalUsers_Search
	 * Arguments			: None
	 * Use 					: PortalUsers_Search
	 * Designed By			: sisir
	 * Last Modified Date 	: 27-feb-2019
	---------------------------------------------------------------------------------------------------------*/
	public String PortalUsers_Search() {

		String Test_OutPut = "", Status = "", Search_data = "";
		Result.fUpdateLog("-----PortalUsers_Search Search Event Details------");
		try {

			if (TestCaseN.get().equals("Portal_DeleteUser")) {
				if (!(getdata("Search_data").equals(""))) {
					Search_data = getdata("Search_data");
				} else {
					Search_data = Utlities.FetchStoredValue("portal_Edit", "portal_EditUser", "Portal_USERNAME");
					;
				}

			}

			Browser.WebLink.click("Portal_Users");
			CO.waitSeconds(2);
			Result.takescreenshot("PortalUsers Search Event Details");
			CO.waitSeconds(5);

			Browser.WebEdit.Set("PortalUsers_search", Search_data);
			System.out.println(getdata("Searching_Input"));
			Browser.WebButton.click("PortalUsers_Search_button");
			CO.waitSeconds(5);
			// System.out.println(Browser.WebTable.getCellData("Portal_Users_table", 2,5));

			Result.takescreenshot("PortalUsers Search Event Details");

			if (Continue.get()) {
				Test_OutPut += "PortalUsers Search Event Details";
				Result.fUpdateLog("PortalUsers Search Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "PortalUsers Search Event Details Failed";
				Result.fUpdateLog("PortalUsers Search Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------PortalUsers Search Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PortalUsers_SearchRecordCheck
	 * Arguments			: None
	 * Use 					: PortalUsers_SearchRecordCheck
	 * Designed By			: sisir
	 * Last Modified Date 	: 27-feb-2019
	---------------------------------------------------------------------------------------------------------*/
	public String PortalUsers_SearchRecordCheck() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("-----PortalUsers_Search Search Event Details------");
		try {

			Result.takescreenshot("PortalUsers_SearchRecordCheck Details");

			if (TestCaseN.get().equals("portal_DeleteUser")) {
				if (Browser.WebElement.exist(("WebElement_NoRecords")))
					Status = "PASS";
				else
					Status = "FAIL";
			} else {
				if (!(Browser.WebTable.getCellData("Portal_Users_table", 1, 4).equals("")))
					Status = "PASS";
				else
					Status = "FAIL";
				System.out.println(Browser.WebTable.getCellData("Portal_Users_table", 1, 4));
			}

			Result.takescreenshot("PortalUsers_SearchRecordCheck Details");

			if (Continue.get()) {
				Test_OutPut += "PortalUsers_SearchRecordCheck Details";
				Result.fUpdateLog("PortalUsers_SearchRecordCheck Details");
				Status = "PASS";
			} else {
				Test_OutPut += "PortalUsers_SearchRecordCheck Failed";
				Result.fUpdateLog("PortalUsers_SearchRecordCheck Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------PortalUsers_SearchRecordCheck Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: portal_EditUser
	 * Arguments			: None
	 * Use 					: portal_EditUser
	 * Designed By			: sisir
	 * Last Modified Date 	: 27-feb-2019
	---------------------------------------------------------------------------------------------------------*/
	public String Portal_EditUser() {

		String Test_OutPut = "", Status = "", Portal_Edit_USERNAME = "", Portal_PASSWORD = "";
		Result.fUpdateLog("-----portal_EditUser  Event Details------");
		try {
			Portal_Edit_USERNAME = "VFQAUser" + R.nextInt(10000000);
			Browser.WebButton.click("PortalUsers_Edit");
			Browser.WebEdit.Set("Portal_Edit_USERNAME", Portal_Edit_USERNAME);

			Result.takescreenshot("portal_EditUser Event Details");

			if (!(getdata("Portal_PASSWORD").equals(""))) {
				Portal_PASSWORD = getdata("Portal_PASSWORD");
			} else {
				Portal_PASSWORD = pulldata("Portal_PASSWORD") + R.nextInt(10000000);
			}
			Browser.WebEdit.Set("Portal_Edit_PASSWORD", Portal_PASSWORD);

			Browser.WebEdit.Set("Portal_Edit_CONFIRM_PASSWORD", Portal_PASSWORD);
			Result.takescreenshot("Portal_Edit Event Details");
			Browser.WebButton.click("Portal_Edit_Submit_button");
			Result.takescreenshot("Portal_Edit Event Details");
			Utlities.StoreValue("Portal_USERNAME", Portal_Edit_USERNAME);
			if (Continue.get()) {
				Test_OutPut += "portal_EditUser  Event Details";
				Result.fUpdateLog("portal_EditUser Search Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "portal_EditUser  Event Details Failed";
				Result.fUpdateLog("portal_EditUser  Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------portal_EditUser  Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: portal_DeleteUser
	 * Arguments			: None
	 * Use 					: portal_DeleteUser
	 * Designed By			: sisir
	 * Last Modified Date 	: 28-feb-2019
	---------------------------------------------------------------------------------------------------------*/
	public String Portal_DeleteUser() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("-----portal_DeleteUser  Event Details------");
		try {
			Browser.WebButton.click("Portal_Delete_button");
			Result.takescreenshot("portal_DeleteUser Event Details");
			Browser.WebButton.click("Portal_Yes_button");
			Result.takescreenshot("portal_DeleteUser Event Details");

			if (Continue.get()) {
				Test_OutPut += "portal_DeleteUser  Event Details";
				Result.fUpdateLog("portal_DeleteUser Search Event Details");
				Status = "PASS";
			} else {
				Test_OutPut += "portal_DeleteUser  Event Details Failed";
				Result.fUpdateLog("portal_DeleteUser  Event Details Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------portal_DeleteUser  Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
