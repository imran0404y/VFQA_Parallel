package utilities;

public class test {
	
	public static void main(String[] args) {
		String a = "QR120.00";
		//System.out.println(a.split("QR"));
		String k = a.split("QR")[1];
	//	String[] obj = k.split(",");
	//	if (obj.length > 1)
	//		k = obj[0].trim() + obj[1].trim();
		System.out.println(k);
		
	}

}
