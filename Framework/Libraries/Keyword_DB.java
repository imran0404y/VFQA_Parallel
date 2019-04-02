package Libraries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class Keyword_DB extends Driver {

	public static ThreadLocal<Connection> con = new ThreadLocal<Connection>();

	public String DBConnection() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------DB connection Event Details------");
		String ServiceName = "";

		if (getdata("Application_Details").equals("BRM_DB")) {
			ServiceName = "BRMPROD";
		} else if (getdata("Application_Details").equals("CRM_DB")) {
			ServiceName = "CRMPROD";
		}
		String Username = getdata("VQ_Login_User").trim();
		String password = getdata("VQ_Login_Pswd").trim();
		String Host = getdata("URL/HOST");
		int Port = 1521;

		String driver = "oracle.jdbc.OracleDriver";
		try {
			Class.forName(driver);
			con.set(DriverManager.getConnection("jdbc:oracle:thin:@" + Host + ":" + Port + ":" + ServiceName, Username,
					password));
			Result.fUpdateLog("connected to DB");

			Result.fUpdateLog("Connected to DB: " + Host);
			Test_OutPut += "Connected to DB: " + Host + ",";
			Status = "PASS";
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Unable to connect to DB: " + Host + ServiceName + ",";
			Result.fUpdateLog("Unable to connect to DB *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB connection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String DBDisconnection() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------DB Disconnection Event Details------");
		try {
			con.get().close();
			Result.fUpdateLog("DB disconnected successfully");
			Test_OutPut += "DB disconnected successfully" + ",";
			Status = "PASS";
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Failed to disconnect DB" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB Disconnection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String BillPoID() {
		String Test_OutPut = "", Status = "", AccountNo = "";
		if (Continue.get()) {

			String BillingProfileName = "";
			Result.fUpdateLog("------BillPoID Event Details------");

			AccountNo = getdata("AccountNo");

			if (!(getdata("BillingProfile").equals(""))) {
				BillingProfileName = getdata("BillingProfile");
			} else {
				BillingProfileName = Utlities.FetchStoredValue(UseCaseName.get(), TestCaseN.get(),
						"BillingProfileName");
			}
			try {
				Statement statement = con.get().createStatement();
				String queryString = "Select a.account_no,b.poid_id0,b.bill_info_id from pin.account_t a,pin.billinfo_t b where a.account_no in '"
						+ AccountNo + "' and b.account_obj_id0=a.poid_id0 and b.bill_info_id='" + BillingProfileName
						+ "'";

				ResultSet rs = statement.executeQuery(queryString);
				while (rs.next()) {
					Test_OutPut = "BillPoID: " + rs.getString(2) + " BillingProfileName: " + rs.getString(3) + ",";
					Utlities.StoreValue("BillPoID", rs.getString(2));
				}
				Status = "PASS";

			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Failed to BillPoID" + ",";
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				Status = "FAIL";
				e.printStackTrace();
			}
			Result.fUpdateLog("------BillPoID Event Details - Completed------");
		} else {

			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	public String AccPoID_BillPoID(String AccountNo) {
		Continue.set(true);
		String Test_OutPut = "";
		if (Continue.get()) {
			Result.fUpdateLog("------AccPoID_BillPoID Event Details------");
			Test_OutPut = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<BusinessConfiguration\r\n"
					+ "xmlns=\"http://www.portal.com/schemas/BusinessConfig\"\r\n"
					+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n"
					+ "xsi:schemaLocation=\"http://www.portal.com/schemas/BusinessConfig BusinessConfiguration.xsd\">\r\n"
					+ "<!-- Sample input file for pin_bill_accts containing parameters for bill run management -->\r\n"
					+ "<!-- Modify according to guidelines -->\r\n" + "<BillRunConfiguration>\r\n"
					+ "<!-- List of Billinfo to be billed -->\r\n";
			try {
				Statement statement = con.get().createStatement();
				String queryString = "Select a.poid_id0 ,b.poid_id0 from pin.account_t a,pin.billinfo_t b where a.account_no IN ('"
						+ AccountNo + "') and b.account_obj_id0=a.poid_id0 AND NOT b.PAYINFO_OBJ_TYPE LIKE '%prepaid%'";
				Result.fUpdateLog(queryString);
				ResultSet rs = statement.executeQuery(queryString);
				while (rs.next()) {
					Test_OutPut += "        <BillingList>\r\n" + "                <Account>" + rs.getString(1)
							+ "</Account>\r\n" + "                <Billinfo>" + rs.getString(2) + "</Billinfo>\r\n"
							+ "        </BillingList>\r\n";
				}

				Test_OutPut += "</BillRunConfiguration>\r\n" + "</BusinessConfiguration>";
			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Failed to AccPoID_BillPoID" + ",";
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				e.printStackTrace();
			}

		} else {
			Result.fUpdateLog("Failed at intial did not entered in AccPoID_BillPoID");
			Continue.set(false);
		}
		Result.fUpdateLog("------AccPoID_BillPoID Event Details - Completed------");
		return Test_OutPut;
	}

	public String AccPoID_BillPoID(String AccountNo, String BillingProf) {
		Continue.set(true);
		String Test_OutPut = "";
		if (Continue.get()) {
			Result.fUpdateLog("------AccPoID_BillPoID Event Details------");
			Test_OutPut = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<BusinessConfiguration\r\n"
					+ "xmlns=\"http://www.portal.com/schemas/BusinessConfig\"\r\n"
					+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n"
					+ "xsi:schemaLocation=\"http://www.portal.com/schemas/BusinessConfig BusinessConfiguration.xsd\">\r\n"
					+ "<!-- Sample input file for pin_bill_accts containing parameters for bill run management -->\r\n"
					+ "<!-- Modify according to guidelines -->\r\n" + "<BillRunConfiguration>\r\n"
					+ "<!-- List of Billinfo to be billed -->\r\n";
			try {
				Statement statement = con.get().createStatement();
				String queryString = "Select a.poid_id0 ,b.poid_id0 from pin.account_t a,pin.billinfo_t b where a.account_no IN ('"
						+ AccountNo + "') and b.bill_info_id IN ('" + BillingProf
						+ "')and b.account_obj_id0=a.poid_id0 AND NOT b.PAYINFO_OBJ_TYPE LIKE '%prepaid%'";
				Result.fUpdateLog(queryString);
				ResultSet rs = statement.executeQuery(queryString);
				while (rs.next()) {
					Test_OutPut += "        <BillingList>\r\n" + "                <Account>" + rs.getString(1)
							+ "</Account>\r\n" + "                <Billinfo>" + rs.getString(2) + "</Billinfo>\r\n"
							+ "        </BillingList>\r\n";
				}

				Test_OutPut += "</BillRunConfiguration>\r\n" + "</BusinessConfiguration>";
			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Failed to AccPoID_BillPoID" + ",";
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				e.printStackTrace();
			}

		} else {
			Result.fUpdateLog("Failed at intial did not entered in AccPoID_BillPoID");
			Continue.set(false);
		}
		Result.fUpdateLog("------AccPoID_BillPoID Event Details - Completed------");
		return Test_OutPut;
	}

	public String BillPoID(String AccountNo) {
		Continue.set(true);
		String Test_OutPut = "";
		if (Continue.get()) {
			Result.fUpdateLog("------BillPoID Event Details------");
			Test_OutPut = "";
			try {
				Statement statement = con.get().createStatement();
				String queryString = "select b.poid_id0 from pin.bill_t b , pin.account_t a, pin.billinfo_t bi where b.account_obj_id0 = a.poid_id0 and b.billinfo_obj_id0 = bi.poid_id0 and "
						+ "bi.pay_type in ('10001','15003','15004') and b.invoice_obj_id0 = 0 and b.bill_no is not null and a.account_no in ('"
						+ AccountNo + "')";
				Result.fUpdateLog(queryString);
				ResultSet rs = statement.executeQuery(queryString);
				int i = 0;
				while (rs.next()) {
					Test_OutPut += "0 PIN_FLD_RESULTS     ARRAY [" + i + "]\r\n"
							+ "1    PIN_FLD_POID        POID [0] 0.0.0.1 /bill " + rs.getString(1) + "\r\n";
					i++;
				}
			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Failed to BillPoID" + ",";
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				e.printStackTrace();
			}

		} else {
			Result.fUpdateLog("Failed at intial did not entered in BillPoID");
			Continue.set(false);
		}
		Result.fUpdateLog("------BillPoID Event Details - Completed------");
		return Test_OutPut;

	}

	public ArrayList<String> ACCPoID(String AccountNo) {
		Continue.set(true);
		ArrayList<String> arr = new ArrayList<String>();
		if (Continue.get()) {
			Result.fUpdateLog("------ACCPoID Event Details------");
			try {
				Statement statement = con.get().createStatement();
				String queryString = "Select a.poid_id0 from pin.account_t a where a.account_no IN ('" + AccountNo
						+ "')";
				Result.fUpdateLog(queryString);
				ResultSet rs = statement.executeQuery(queryString);
				while (rs.next()) {
					arr.add(rs.getString(1));
				}
			} catch (Exception e) {
				Continue.set(false);
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				e.printStackTrace();
			}

		} else {
			Result.fUpdateLog("Failed at intial did not entered in ACCPoID");
			Continue.set(false);
		}
		Result.fUpdateLog("------ACCPoID Event Details - Completed------");
		return arr;

	}
}
