package util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;


public class Util {
	//public static String restDir = "/home/stefan/.portal/restCalls/"; //linux
	public static String restDir = "G:/Programmering/restCalls/"; //windows
	public static String appendFirewallURL = "https://192.168.130.13/rrrr";
	public static String getFirewallURL = "https://nsx.ddemo.local/api/4.0/edges/edge-19/firewall/config";

	public static void copy(String from, String to){
		Path FROM = Paths.get(restDir + from);

		Path TO = Paths.get(restDir + to);
		//overwrite existing file, if exists
		CopyOption[] options = new CopyOption[]{
				StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES
		}; 

		try {
			Files.copy(FROM, TO, options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void trustAllCert() throws Exception{
		TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		}
		};

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
	    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

}
