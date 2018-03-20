package Libraries;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Keyword_Putty extends Driver {
	// private static ChannelShell channel;

	public String LoginSSH() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------BRM Putty Login Event Details------");
		String str_Host = getdata("URL/HOST");
		try {
			int int_SSHPort = 22;
			String str_Username = getdata("VQ_Login_User");
			String str_Password = getdata("VQ_Login_Pswd");

			JSch obj_JSch = new JSch();
			nsession.set(obj_JSch.getSession(str_Username, str_Host));
			nsession.get().setPort(int_SSHPort);
			nsession.get().setPassword(str_Password);

			Properties obj_Properties = new Properties();
			obj_Properties.put("StrictHostKeyChecking", "no");
			obj_Properties.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
			nsession.get().setConfig(obj_Properties);

			nsession.get().connect();
			Result.fUpdateLog("Session Connected to: " + str_Host);
			Test_OutPut += "Session Connected to: " + str_Host + ",";
			Status = "PASS";
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Session Connection Failed at Host: " + str_Host + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------BRM Putty Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String LogoutSSH() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------BRM Putty Logout Event Details------");
		try {
			tergetFile.get().close();
			nsession.get().disconnect();
			Result.fUpdateLog("Session disconnected successfully");
			Test_OutPut += "Session disconnected successfully" + ",";
			Status = "PASS";
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Failed to disconnect session" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------BRM Putty logout Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String BillGeneration_AccountLevel() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Bill Generation Event Details------");
		String pvt = "", str_Content = "";
		try {
			String str_Directory = pulldata("str_Directory");
			String str_File = pulldata("str_File");
			String str_FileContent = "";

			if (!(getdata("AccountNo").equals(""))) {
				str_Content = getdata("AccountNo");
			} else if (!(getdata("MultipleAccountNo").equals(""))) {
				str_Content = getdata("MultipleAccountNo").replace("/,", "\\n");
			}

			str_FileContent = ReadFileFromLinux(nsession.get(), str_Directory, str_File);
			Result.fUpdateLog(str_FileContent);

			str_FileContent = WriteFileToLinux(nsession.get(), str_Content, str_Directory, str_File);
			Result.fUpdateLog(str_FileContent);

			str_FileContent = ReadFileFromLinux(nsession.get(), str_Directory, str_File);
			Result.fUpdateLog(str_FileContent);
			Test_OutPut += str_FileContent + ",";

			if (!(getdata("PVT_Date").equals(""))) {
				// "pvt -m2 010710002018 "
				pvt = "pvt -m2 " + getdata("PVT_Date");
			} else {
				pvt = "pvt -m2 " + pulldata("PVT_Date");
			}

			List<String> commands = new ArrayList<String>();
			commands.add("test");
			commands.add("cd control_file_gen");
			commands.add("./bill_control_gen.pl");
			commands.add("cp PinBillRunControl.xml $PIN_HOME/apps/pin_billd");
			commands.add("test");
			commands.add(pvt);
			commands.add("pvt");
			commands.add("apps");
			commands.add("cd pin_billd");
			commands.add("cat PinBillRunControl.xml");
			// commands.add("pin_deferred_act –verbose");
			// commands.add("pin_cycle_fees -cycle_fees -verbose");
			// commands.add("pin_cycle_fees -purchase -verbose");
			// commands.add("pin_cycle_fees -cancel -verbose");
			commands.add("pin_bill_accts -file PinBillRunControl.xml -verbose");
			commands.add("test");
			commands.add("pvt -m0");
			commands.add("pvt");

			// commands.add("history");

			str_FileContent = Executecmd(nsession.get(), commands, "");

			Date today = new Date();
			String x = today.toString();
			x = x.substring(4, 13).replace("01", "1").replace("02", "2").replace("03", "3").replace("04", "4")
					.replace("05", "5").replace("06", "6").replace("07", "7").replace("08", "8").replace("09", "9");
			Result.fUpdateLog(x);

			if (str_FileContent.contains(x)) {
				Result.fUpdateLog("PVT set as Normal");
				Test_OutPut += "PVT set as Normal" + ",";
				Continue.set(true);
			} else {
				Result.fUpdateLog("Fail to set PVT Normal");
				Test_OutPut += "Fail to set PVT Normal" + ",";
				Continue.set(false);
			}

			CopytoDoc(str_FileContent);

			if (str_FileContent.contains("logout") && Continue.get()) {
				Test_OutPut += "Commands Executed Successfully" + ",";
				//Result.fUpdateLog(str_FileContent);
				Status = "PASS";
			} else {
				Test_OutPut += "Failed to Execute the commands" + ",";
				//Result.fUpdateLog(str_FileContent);
				Status = "Fail";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Failed to disconnect session" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Bill Generation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String Invoicegeneration() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Invoice generation Event Details------");
		String Dt = "", x = "";
		try {
			String str_FileContent = "";

			if (!(getdata("Beyond_PVT_Date").equals(""))) {
				// ./vfq_export_invXML-BILL.pl -end 17-12-2012 10:10:10
				Dt = "./vfq_export_invXML-BILL.pl -end " + getdata("Beyond_PVT_Date").replace("M", "");
			} else {
				Dt = "./vfq_export_invXML-BILL.pl -end " + pulldata("Beyond_PVT_Date").replace("M", "");
			}

			List<String> commands = new ArrayList<String>();
			commands.add("test");
			commands.add("cd control_file_gen");
			commands.add("./pending_invoice_gen.pl");
			commands.add("ls -lrt");
			str_FileContent = Executecmd(nsession.get(), commands, "");

			Date today = new Date();
			x = today.toString();
			x = x.substring(4, 13).replace("01", "1").replace("02", "2").replace("03", "3").replace("04", "4")
					.replace("05", "5").replace("06", "6").replace("07", "7").replace("08", "8").replace("09", "9");
			Result.fUpdateLog(x);

			if (str_FileContent.contains(x)) {
				Continue.set(true);
				Result.fUpdateLog("The f1 directory is updated : control_file_gen");
				Test_OutPut += "The f1 directory is updated : control_file_gen" + ",";

				List<String> commands1 = new ArrayList<String>();
				commands1.add("test");
				commands1.add("cd control_file_gen");
				commands1.add("cp f1 $PIN_HOME/apps/pin_inv");
				commands1.add("apps");
				commands1.add("cd pin_inv");
				commands1.add("ls -lrt");
				String str_FileContent1 = Executecmd(nsession.get(), commands1, "");
				str_FileContent += str_FileContent1;

				Date today1 = new Date();
				x = today1.toString();
				x = x.substring(4, 13).replace("01", "1").replace("02", "2").replace("03", "3").replace("04", "4")
						.replace("05", "5").replace("06", "6").replace("07", "7").replace("08", "8").replace("09", "9");
				Result.fUpdateLog(x);

				if (str_FileContent1.contains(x)) {
					Result.fUpdateLog("The f1 directory is updated : pin_inv");
					Test_OutPut += "The f1 directory is updated : pin_inv" + ",";

					List<String> commands2 = new ArrayList<String>();
					commands2.add("apps");
					commands2.add("cd pin_inv");
					commands2.add("pin_inv_accts -details -file f1 -verbose");
					String str_FileContent4 = Executecmd(nsession.get(), commands2, "");
					str_FileContent += str_FileContent4;
				} else {
					Continue.set(false);
				}

			} else {
				Continue.set(false);
			}

			if (Continue.get()) {
				List<String> commands3 = new ArrayList<String>();
				commands3.add("apps");
				commands3.add("cd vfq_exp_XML/vfq_exp_XML-Bill");
				commands3.add(Dt);
				commands3.add("cd invoice_processed");
				commands3.add("ls -lrt");
				String str_FileContent2 = Executecmd(nsession.get(), commands3, "");
				str_FileContent += str_FileContent2;
				String xa[] = str_FileContent2.split("\n");
				int i = xa.length - 5;
				// String str = "-rw-rw-r-- 1 pin pin 4744 Jan 25 09:13
				// [01;31mInvoice_1516860799.zip[00m"; //
				String str = xa[i];
				System.out.println(xa[i].length());
				str_FileContent2 = xa[i].substring(66, xa[i].length() - 6);
				// String str_FileContent2 = "Invoice_1516860799.zip";
				Result.fUpdateLog(str_FileContent2);

				Date today5 = new Date();
				x = today5.toString();
				x = x.substring(4, 13).replace("01", "1").replace("02", "2").replace("03", "3").replace("04", "4")
						.replace("05", "5").replace("06", "6").replace("07", "7").replace("08", "8").replace("09", "9");
				// x = "Jan 25 09:45";
				Result.fUpdateLog(x);

				if (str_FileContent2.contains("zip") & str.contains(x)) {
					Continue.set(true);
					Result.fUpdateLog("latest .zip file is updated : invoice_processed");
					Test_OutPut += "latest .zip file is updated : invoice_processed" + ",";
					Test_OutPut += ".Zip file Name : " + str_FileContent2 + ",";
					InvoiceZip.set(str_FileContent2);
					List<String> commands4 = new ArrayList<String>();
					commands4.add("test");
					commands4.add("pvt -m0");
					commands4.add("pvt");
					String str_FileContent3 = Executecmd(nsession.get(), commands4, "");
					str_FileContent += str_FileContent3;

					Date today2 = new Date();
					x = today2.toString();
					x = x.substring(4, 13).replace("01", "1").replace("02", "2").replace("03", "3").replace("04", "4")
							.replace("05", "5").replace("06", "6").replace("07", "7").replace("08", "8")
							.replace("09", "9");
					Result.fUpdateLog(x);
					if (str_FileContent3.contains(x)) {
						Result.fUpdateLog("PVT set as Normal");
						Test_OutPut += "PVT set as Normal" + ",";
						Continue.set(true);
					} else {
						Result.fUpdateLog("Fail to set PVT Normal");
						Test_OutPut += "Fail to set PVT Normal" + ",";
						Continue.set(false);
					}

				} else {
					Result.fUpdateLog(".Zip file not generated");
					Test_OutPut += ".Zip file not generated" + ",";
					Continue.set(false);
				}
			}

			CopytoDoc(str_FileContent);

			if (str_FileContent.contains("logout") && Continue.get()) {
				Test_OutPut += "Commands Executed Successfully" + ",";
				//Result.fUpdateLog(str_FileContent);
				Status = "PASS";
			} else {
				Test_OutPut += "Failed to Execute the commands" + ",";
				//Result.fUpdateLog(str_FileContent);
				Status = "Fail";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Failed to disconnect session" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Invoice generation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String Collections() {
		String Test_OutPut = "", Status = "";
		if (Continue.get()) {
			Result.fUpdateLog("------Collections Event Details------");
			String Coll = "", pvt = "", str_FileContent = "";
			try {
				// date neet to be pick for the StoreDB
				String PoID = Utlities.FetchStoredValue(UseCaseName.get(), TestCaseN.get(), "BillPoID");

				pvt = "pvt -m2 " + Utlities.FetchStoredValue(UseCaseName.get(), TestCaseN.get(), "DueDate");

				Coll = "pin_collections_process -billinfo " + PoID + " -verbose";

				List<String> commands = new ArrayList<String>();
				commands.add("test");
				commands.add(pvt);
				commands.add("pvt");

				commands.add("apps");
				commands.add("cd pin_collections");
				commands.add(Coll);
				commands.add("test");
				commands.add("pvt -m0");
				commands.add("pvt");

				// commands.add("history");

				str_FileContent = Executecmd(nsession.get(), commands, "");

				Date today = new Date();
				String x = today.toString();
				x = x.substring(4, 13).replace("01", "1").replace("02", "2").replace("03", "3").replace("04", "4")
						.replace("05", "5").replace("06", "6").replace("07", "7").replace("08", "8").replace("09", "9");
				Result.fUpdateLog(x);

				if (str_FileContent.contains(x)) {
					Result.fUpdateLog("PVT set as Normal");
					Test_OutPut += "PVT set as Normal" + ",";
					Continue.set(true);
				} else {
					Result.fUpdateLog("Fail to set PVT Normal");
					Test_OutPut += "Fail to set PVT Normal" + ",";
					Continue.set(false);
				}

				CopytoDoc(str_FileContent);

				if (str_FileContent.contains("logout") && Continue.get()) {
					Test_OutPut += "Commands Executed Successfully" + ",";
					Status = "PASS";
				} else {
					Test_OutPut += "Failed to Execute the commands" + ",";
					Status = "Fail";
				}

			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Failed to disconnect session" + ",";
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				Status = "FAIL";
				e.printStackTrace();
			}
			Result.fUpdateLog("------Collections Event Details - Completed------");

		} else {

			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String ReadFileFromLinux(Session obj_Session, String str_FileDirectory, String str_FileName) {
		StringBuilder obj_StringBuilder = new StringBuilder();
		try {
			Channel obj_Channel = obj_Session.openChannel("sftp");
			obj_Channel.connect();
			ChannelSftp obj_SFTPChannel = (ChannelSftp) obj_Channel;
			obj_SFTPChannel.cd(str_FileDirectory);
			InputStream obj_InputStream = obj_SFTPChannel.get(str_FileName);
			char[] ch_Buffer = new char[0x10000];
			Reader obj_Reader = new InputStreamReader(obj_InputStream, "UTF-8");
			int int_Line = 0;
			do {
				int_Line = obj_Reader.read(ch_Buffer, 0, ch_Buffer.length);
				if (int_Line > 0) {
					obj_StringBuilder.append(ch_Buffer, 0, int_Line);
				}
			} while (int_Line >= 0);
			obj_Reader.close();
			obj_InputStream.close();

			obj_SFTPChannel.exit();
			obj_Channel.disconnect();
			return str_FileName + " file Contains : " + obj_StringBuilder.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Fail to Read the file : " + str_FileName;
		}

	}

	public String WriteFileToLinux(Session obj_Session, String str_Content, String str_FileDirectory,
			String str_FileName) {

		try {
			Channel obj_Channel = obj_Session.openChannel("sftp");
			obj_Channel.connect();
			ChannelSftp obj_SFTPChannel = (ChannelSftp) obj_Channel;
			obj_SFTPChannel.cd(str_FileDirectory);
			InputStream obj_InputStream = new ByteArrayInputStream(str_Content.getBytes());
			obj_SFTPChannel.put(obj_InputStream, str_FileDirectory + str_FileName);
			obj_SFTPChannel.exit();
			obj_InputStream.close();
			obj_Channel.disconnect();
			return str_FileName + " File Uploaded Successfully : " + str_Content;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Fail to Uploaded : " + str_FileName;
		}
	}

	private Channel getChannel(Session obj_Session) {
		if (nchannel.get() == null || !nchannel.get().isConnected()) {
			try {
				nchannel.set((ChannelShell) obj_Session.openChannel("shell"));
				nchannel.get().connect();
				Result.fUpdateLog("channel opened!");
			} catch (Exception e) {
				Result.fUpdateLog("Error while opening channel: " + e);
			}
		}
		return nchannel.get();
	}

	public String Executecmd(Session obj_Session, List<String> commands, String Exitval) {
		String str = "";
		try {
			String str1 = "";
			Channel channel = getChannel(obj_Session);

			PrintStream out = new PrintStream(channel.getOutputStream());
			out.println("#!/bin/bash");
			for (String command : commands) {
				out.println(command);
				Result.fUpdateLog(command);
			}
			out.println("exit");
			out.close();
			out.flush();

			InputStream in = channel.getInputStream();
			byte[] buffer = new byte[1024];

			while (true) {
				while (in.available() > 0) {
					int i = in.read(buffer, 0, 1024);
					if (i < 0) {
						break;
					}
					str1 = new String(buffer, 0, i);
					str = str + str1;
					Result.fUpdateLog(str1);
				}

				if (str1.contains("logout")) {
					break;
				}
				if (channel.isClosed()) {
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}

			in.close();
			channel.disconnect();
			Result.fUpdateLog("DONE");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public void CopytoDoc(String Data) {

		try {
			// String lf = "OBJECT_FILE";
			String lf = TCscreenfile.get();
			tergetFile.set(new FileOutputStream(lf));

			XWPFDocument document = new XWPFDocument();

			// create Paragraph
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();

			run.setText(Data);
			document.write(tergetFile.get());
			document.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
