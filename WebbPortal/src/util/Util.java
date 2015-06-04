package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
	//windows vs linux vs VM
	//public static String restDir = "/home/stefan/.portal/restCalls/"; //linux
	public static String restDir = "/home/kth/.portal/restCalls/"; //VM
	//public static String restDir = "G:/Programmering/restCalls/"; //windows
	public static String firewallAllowFilePath = "firewallAllow";
	public static String inOutPolicyFilePath = "inOutPolicy";
	public static String edgeIdFilePath = "edgeId";
	public static String getDhcpDynamicURL = "https://192.168.130.13/api/4.0/edges/" + findEdgeId() + "/dhcp/leaseInfo";
	public static String getDhcpStaticURL = "https://192.168.130.13/api/4.0/edges/" + findEdgeId() + "/dhcp/config";
	//public static boolean isEnabled;
	public static String isEnabledFilePath = "isEnabled";
	public static String edgeIpPath = "edgeIp";
	public static String deleteFirewall = "https://192.168.130.13/api/4.0/edges/"+ findEdgeId() + "/firewall/config/rules/"; 
	//public static String edgeId;
	public static String appendFirewallURL = "https://192.168.130.13/api/4.0/edges/" + findEdgeId() + "/firewall/config/rules";
	public static String getFirewallURL = "https://192.168.130.13/api/4.0/edges/" + findEdgeId() + "/firewall/config";
	public static String edgeBandWidthURL = "https://nsx.ddemo.local/api/4.0/edges/" + findEdgeId() + "/vnics/1";
	public static String appendNatUrl = "https://192.168.130.13/api/4.0/edges/" + findEdgeId() + "/nat/config/rules";
	public static String getNatUrl = "https://192.168.130.13/api/4.0/edges/" + findEdgeId() + "/nat/config";
	public static String deleteNatUrl = "https://192.168.130.13/api/4.0/edges/" + findEdgeId() + "/nat/config/rules/";
	


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

	/**
	 * Finds the edgeId by reading from designated file (Populated through workflow)
	 */
	public static String findEdgeId(){
		File edgeFile = new File(restDir + edgeIdFilePath); 
		try{
			BufferedReader reader = new BufferedReader(new FileReader(edgeFile));	
			String id = reader.readLine();
			reader.close();
			return id;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean findIsEnabled(){

		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(restDir + isEnabledFilePath)));
			if(reader.readLine().equalsIgnoreCase("true")){
				reader.close();
				return true;
			}
			else{
				reader.close();
				return false;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void setEnable(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(restDir + isEnabledFilePath)));
			writer.write("true");
			writer.flush();
			writer.close();	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static String getEdgeIp(){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(restDir + edgeIpPath)));
			String ip =  reader.readLine();
			reader.close();
			return ip;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "Custom error";
	}
}
