package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.ejb.Stateless;



import com.sun.faces.util.Cache.Factory;

import util.Util;
import dto.BroadBandRequestDTO;
import dto.FirewallRequestDTO;
import dto.NatRequestDTO;
import dto.ServerRequestDTO;


@Stateless
public class Controller {

	/**
	 * Called as a result of a broadbandRequest
	 */
	public void broadBandRequest(BroadBandRequestDTO bbrDTO){
		System.out.println(bbrDTO.getFirstName());
		System.out.println(bbrDTO.getLastName());
		System.out.println(bbrDTO.getEmail());
		System.out.println(bbrDTO.getCreditNbr());

		System.out.println(bbrDTO.getSpeed());
	}

	public void firewallRequest(FirewallRequestDTO fwrDTO){

		//		System.out.println("Presssed submit firewall");
		//		System.out.println(fwrDTO.getAccess());
		//		System.out.println(fwrDTO.getDestinationIp());
		//		System.out.println(fwrDTO.getDirection());
		//		System.out.println(fwrDTO.getPort());
		//		System.out.println(fwrDTO.getSourceIp());
		//		System.out.println(fwrDTO.getName());

		//Vi vill ta reda på vart vi befinner oss nu
		//Util.copy("appendFirewallProduction", "tmpAppendFirewallProduction");
		parseFirewall(fwrDTO);
		//Nu skall vi s�nda skiten...
//		sendFirewallRequest();
		try {
			Util.trustAllCert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendFirewallRequest2();
	}

	public void natRequest(NatRequestDTO nDTO) {
		System.out.println(nDTO.getDestination());
		System.out.println(nDTO.getSource());
		System.out.println(nDTO.getType());
	}


	//Flytta ut denna ur denna klass...
	//Skall ersätta alla #-markerade ord med ord angivna av user
	private void parseFirewall(FirewallRequestDTO fwrDTO){
		String outPath = Util.restDir + "tmpAppendFirewallProduction";
		String inPath = Util.restDir + "appendFirewallProduction";
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(inPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			writer = new BufferedWriter(new FileWriter(outPath));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			String line;
			while((line = reader.readLine()) != null) {

				if(line.contains("#accept")){


					line = (line.replaceAll("#accept", fwrDTO.getAccess()));


				}
				if(line.contains("#name")){

					line = (line.replaceAll("#name", fwrDTO.getName()));

				}
				if(line.contains("#sourceIp")){

					line = (line.replaceAll("#sourceIp", fwrDTO.getSourceIp()));
				}
				if(line.contains("#destIp")){

					line = (line.replaceAll("#destIp", fwrDTO.getDestinationIp()));
				}
				if(line.contains("#port")){

					line = (line.replaceAll("#port", fwrDTO.getPort()));
				}
				writer.write(line);
				writer.newLine();

			}	
			writer.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}


	}

	private void sendFirewallRequest2(){
		String url = "http://www.google.com/search?q=mkyong";
		String username = "admin";
		String password = "P!ssw0rd";
		String userPw = username + ":" + password;
		String encodedAuthorization = new String(Base64.getEncoder().encode(userPw.getBytes()));

		try {
			URL obj = new URL(Util.getFirewallURL);
			HttpURLConnection con;
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			System.out.println(response.toString());
	 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	    
	}
	
//	private void sendFirewallRequest(){
//		try{
//			UsernamePasswordCredentials cred = new UsernamePasswordCredentials("admin", "P!ssw0rd");
//			CredentialsProvider credsProvider = new BasicCredentialsProvider();
//			credsProvider.setCredentials(AuthScope.ANY, cred);
//			CloseableHttpClient httpclient = HttpClients.custom()
//					.setDefaultCredentialsProvider(credsProvider)
//					.build();
//
//			HttpGet httpget = new HttpGet(Util.getFirewallURL);
//			CloseableHttpResponse response = httpclient.execute(httpget);
//			System.out.println("Executing request " + httpget.getRequestLine());
//
//
//			System.out.println("----------------------------------------");
//			System.out.println(response.getStatusLine());
//			EntityUtils.consume(response.getEntity());
//
//			response.close();
//
//
//
//			httpclient.close();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//
//
//
//	}





	public void serverRequest(ServerRequestDTO srDTO){
		System.out.println(srDTO.getNbrCpu());
		System.out.println(srDTO.getMemory());
		System.out.println(srDTO.getDiskSpace());
	}


}
