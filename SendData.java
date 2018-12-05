package abracadabacus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SendData {
	
	public static void send(String name, int abacusColumns, int abacusSize, String grade, String Adventure, long data) throws IOException {
		String urlParameters  = "{\"name\":\"" + name + "\",\"abacusColumns\":\"" + abacusColumns + "\",\"abacusSize\":\"" + abacusSize + "\",\"grade\":\"" + grade + "\",\"lesson\":\"" + Adventure + "\",\"result\":\"" + data + "\"}";

		String url = "https://abacus-e3165.firebaseio.com/results/" + name + ".json";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		
		con.setRequestMethod("PUT");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Accept", "application/json");
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		in.close();
	}
	
}
