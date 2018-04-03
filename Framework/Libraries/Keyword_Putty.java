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
	Keyword_DB KD = new Keyword_DB();

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
		String pvt = "", Contant = "", str_Content = "";
		try {
			String str_Directory = pulldata("str_Directory");
			String str_File = pulldata("str_File");
			String str_FileContent = " ";

			if (!(getdata("AccountNo").equals(""))) {
				str_Content = getdata("AccountNo");
			} else if (!(getdata("MultipleAccountNo").equals(""))) {
				str_Content = getdata("MultipleAccountNo").replace(",", "','");
			}

			Contant = KD.AccPoID_BillPoID(str_Content);
			if (Continue.get()) {
				str_FileContent = ReadFileFromLinux(nsession.get(), str_Directory, str_File);
				Result.fUpdateLog("Reading the initial File Content: " + str_File + " : " + str_FileContent);

				str_FileContent = WriteFileToLinux(nsession.get(), Contant, str_Directory, str_File);
				Result.fUpdateLog("Writing into the file: " + str_File + " : " + str_FileContent);

				str_FileContent = ReadFileFromLinux(nsession.get(), str_Directory, str_File);
				Result.fUpdateLog("Reading the File Content after update: " + str_File + " : " + str_FileContent);
				Test_OutPut += str_FileContent + ",";

				if (!(getdata("PVT_Date").equals(""))) {
					// "pvt -m2 010710002018 "
					pvt = "pvt -m2 " + getdata("PVT_Date");
				} else {
					pvt = "pvt -m2 " + pulldata("PVT_Date");
				}

				List<String> commands = new ArrayList<String>();
				commands.add("test");
				commands.add(pvt);
				commands.add("pvt");
				commands.add("apps");
				commands.add("cd pin_billd");
				commands.add("cat PinBillRunControl.xml");
				commands.add("pin_bill_accts -file PinBillRunControl.xml -verbose");
				commands.add("test");
				commands.add("pvt -m0");
				commands.add("pvt");

				str_FileContent = Executecmd(nsession.get(), commands, "");

				Date today = new Date();
				String x = today.toString();
				x = x.substring(4, 13).replace("01", " 1").replace("02", " 2").replace("03", " 3").replace("04", " 4")
						.replace("05", " 5").replace("06", " 6").replace("07", " 7").replace("08", " 8")
						.replace("09", " 9");
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
					// Result.fUpdateLog(str_FileContent);
					Status = "PASS";
				} else {
					Test_OutPut += "Failed to Execute the commands" + ",";
					// Result.fUpdateLog(str_FileContent);
					Status = "Fail";
				}
			} else {
				Test_OutPut += "Failed to get Account and bill info PoidID from DB" + ",";
				// Result.fUpdateLog(str_FileContent);
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
		String x = "", str_Content = "", Contant = "", Xml = "";
		try {
			String str_FileContent = "";

			String str_Directory = pulldata("str_Directory");
			String str_File = pulldata("str_File");

			if (!(getdata("AccountNo").equals(""))) {
				str_Content = getdata("AccountNo");
			} else if (!(getdata("MultipleAccountNo").equals(""))) {
				str_Content = getdata("MultipleAccountNo").replace(",", "','");
			}

			Contant = KD.BillPoID(str_Content);
			if (Continue.get()) {
				ArrayList<String> AccPoID = KD.ACCPoID(str_Content);

				str_FileContent = ReadFileFromLinux(nsession.get(), str_Directory, str_File);
				Result.fUpdateLog("Reading the initial File Content: " + str_File + " : " + str_FileContent);

				str_FileContent = WriteFileToLinux(nsession.get(), Contant, str_Directory, str_File);
				Result.fUpdateLog("Writing into the file: " + str_File + " : " + str_FileContent);

				str_FileContent = ReadFileFromLinux(nsession.get(), str_Directory, str_File);
				Result.fUpdateLog("Reading the File Content after update: " + str_File + " : " + str_FileContent);
				// Test_OutPut += str_FileContent + ",";

				Result.fUpdateLog("The f1 directory is updated : pin_inv");
				Test_OutPut += "The f1 directory is updated : pin_inv" + ",";

				Date today9 = new Date();
				x = today9.toString();
				String y = today9.toString();
				x = x.substring(0, 19).replace(":", "").replace(" ", "");
				y = y.substring(4, 10).replace("01", " 1").replace("02", " 2").replace("03", " 3").replace("04", " 4")
						.replace("05", " 5").replace("06", " 6").replace("07", " 7").replace("08", " 8")
						.replace("09", " 9");
				Xml = "zip " + x;
				String v = "ls -lrt|grep '" + x + "'";
				String d = "ls -lrt|grep '" + y + "'";
				List<String> commands2 = new ArrayList<String>();
				commands2.add("apps");
				commands2.add("cd pin_inv");
				commands2.add("pin_inv_accts -verbose -detail -file f1");
				commands2.add("pin_inv_accts -verbose -summary -file f1");
				commands2.add("pin_inv_export -detail f1 �v");
				commands2.add("cd invoice_dir");
				commands2.add(d);
				String str_FileContent2 = Executecmd(nsession.get(), commands2, "");
				str_FileContent += str_FileContent2;
				String xa[] = str_FileContent2.split("\n");
				int b = xa.length - 5;

				Date today5 = new Date();
				x = today5.toString();
				x = x.substring(4, 13).replace("01", " 1").replace("02", " 2").replace("03", " 3").replace("04", " 4")
						.replace("05", " 5").replace("06", " 6").replace("07", " 7").replace("08", " 8")
						.replace("09", " 9");
				String str = "";
				for (int a = 1; a <= b; a++) {
					for (int c = 0; c < AccPoID.size(); c++) {
						str = xa[a];
						System.out.println(str);
						if (str.contains(AccPoID.get(c)) & str.contains(x)) {
							String str_FileContent3 = xa[a].substring(47, xa[a].length() - 6);
							Xml += " " + str_FileContent3;
						}
					}
				}
				if (Xml.contains("xml")) {
					List<String> commands7 = new ArrayList<String>();
					commands7.add("apps");
					commands7.add("cd pin_inv/invoice_dir");
					commands7.add(Xml);
					commands7.add(v);
					String str_FileContent5 = Executecmd(nsession.get(), commands7, "");
					str_FileContent += str_FileContent5;
					String xb[] = str_FileContent5.split("\n");
					int i = xb.length - 5;
					str = xb[i];
					System.out.println(xb[i].length());
					str_FileContent5 = xb[i].substring(50, xb[i].length() - 6);
					Result.fUpdateLog(str_FileContent5);

					Date today7 = new Date();
					x = today7.toString();
					x = x.substring(4, 13).replace("01", " 1").replace("02", " 2").replace("03", " 3")
							.replace("04", " 4").replace("05", " 5").replace("06", " 6").replace("07", " 7")
							.replace("08", " 8").replace("09", " 9");
					Result.fUpdateLog(x);

					if (str_FileContent5.contains("zip") & str.contains(x)) {
						Continue.set(true);
						Result.fUpdateLog("latest .zip file is updated : invoice_dir");
						Test_OutPut += "latest .zip file is updated : invoice_dir" + ",";
						Test_OutPut += ".Zip file Name : " + str_FileContent5 + ",";
						InvoiceZip.set(str_FileContent5);
						List<String> commands4 = new ArrayList<String>();
						commands4.add("test");
						commands4.add("pvt -m0");
						commands4.add("pvt");
						String str_FileContent3 = Executecmd(nsession.get(), commands4, "");
						str_FileContent += str_FileContent3;

						Date today2 = new Date();
						x = today2.toString();
						x = x.substring(4, 13).replace("01", " 1").replace("02", " 2").replace("03", " 3")
								.replace("04", " 4").replace("05", " 5").replace("06", " 6").replace("07", " 7")
								.replace("08", " 8").replace("09", " 9");
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
				} else {
					Result.fUpdateLog(".XML not generated");
					Test_OutPut += ".XML not generated" + ",";
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
			} else {
				Test_OutPut += "Failed to get BILL PoidID from DB" + ",";
				Status = "Fail";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Failed to Invoice generation" + ",";
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
				x = x.substring(4, 13).replace("01", " 1").replace("02", " 2").replace("03", " 3").replace("04", " 4")
						.replace("05", " 5").replace("06", " 6").replace("07", " 7").replace("08", " 8")
						.replace("09", " 9");
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

	public String ReadFileFromLinux(Session obj_Session, String str_FileDirectory, String str_FileName) throws Exception {
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
			Result.fUpdateLog("Fail to Read the file : " + ExceptionUtils.getStackTrace(ex));
			throw new Exception();
		}

	}

	public String WriteFileToLinux(Session obj_Session, String str_Content, String str_FileDirectory,
			String str_FileName) throws Exception {

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
			Result.fUpdateLog("Fail to Uploaded : " + ExceptionUtils.getStackTrace(ex));
			throw new Exception();
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
