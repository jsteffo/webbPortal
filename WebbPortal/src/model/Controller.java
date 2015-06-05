package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.Util;
import dto.BroadBandRequestDTO;
import dto.FirewallRequestDTO;
import dto.NatRequestDTO;
import dto.ServerRequestDTO;


@Stateless
public class Controller {

	//
	@PostConstruct
	public void init(){
		Util.findEdgeId();
	}
	
	public void deleteFirewall(String id){
		
		try {
			Util.trustAllCert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String username = "admin";
		String password = "P!ssw0rd";
		String userPw = username + ":" + password;
		String encodedAuthorization = new String(Base64.getEncoder().encode(userPw.getBytes()));
		
		List <FirewallRequestDTO> firewallList = new ArrayList<>();
		try{

			URL obj = new URL(Util.deleteFirewall + id);
			HttpURLConnection con;
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("DELETE");
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			System.out.println(Util.deleteFirewall + id);
			int responseCode = con.getResponseCode();
			System.out.println("delete response: " + responseCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
		//parseFirewall(fwrDTO);
		//Nu skall vi s�nda skiten...
		//		sendFirewallRequest();
		try {
			Util.trustAllCert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendFirewallRequest(fwrDTO);
	}


	
	/**
	 * Vi ämnar parsa alla nuvarande regler och vill poppulta front-end
	 * grundat på den datan...
	 */
	public List<FirewallRequestDTO> refreshFirewall(){
	
		try {
			Util.trustAllCert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String username = "admin";
		String password = "P!ssw0rd";
		String userPw = username + ":" + password;
		String encodedAuthorization = new String(Base64.getEncoder().encode(userPw.getBytes()));
		
		List <FirewallRequestDTO> firewallList = new ArrayList<>();
		try{

			URL obj = new URL(Util.getFirewallURL);
			HttpURLConnection con;
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();



			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			Document dom = db.parse(con.getInputStream());
			
			
			// Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();
 
            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            String expression;
            expression = "//firewallRule";
            NodeList firewallSet = (NodeList) xpath.compile(expression).evaluate(dom, XPathConstants.NODESET);
           
            //Måste separera name och id....
            for(int i = 0; i<firewallSet.getLength(); i++) {
            	FirewallRequestDTO dto = new FirewallRequestDTO();
            	
            	expression="name";
            	Node nameNode = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(nameNode != null) {
            		dto.setName(nameNode.getFirstChild().getNodeValue());
            	}
            	
            	expression="id";
            	Node idNode = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(idNode != null) {
            		dto.setId(idNode.getFirstChild().getNodeValue());
            	}
            	expression="action";
            	Node actionNode= (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(actionNode != null) {
            		dto.setAccess(actionNode.getFirstChild().getNodeValue());
            	}
            	
            	expression="source/ipAddress";
            	Node sourceIp = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(sourceIp != null) {
            		dto.setSourceIp(sourceIp.getFirstChild().getNodeValue());
            	} else {
            		dto.setSourceIp("any");
            	}
            	
            	expression="destination/ipAddress";
            	Node destIp = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(destIp != null) {
            		dto.setDestinationIp(destIp.getFirstChild().getNodeValue());	
            	} else{
            		dto.setDestinationIp("any");
            	}
            	
            	expression="application/service/port";
            	Node port = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(port != null) {
            		dto.setPort(port.getFirstChild().getNodeValue());	
            	} else {
            		dto.setPort("any");
            	}
            	
            	expression="ruleType";
            	Node ruleTypeNode = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(ruleTypeNode.getFirstChild().getNodeValue().equalsIgnoreCase("user")){
                	expression="ruleTag";
                	Node ruleTagNode = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
                	if(ruleTagNode.getFirstChild().getNodeValue().equalsIgnoreCase("1")) {
                		continue;
                	}
                  	if(ruleTagNode.getFirstChild().getNodeValue().equalsIgnoreCase("2")) {
                		continue;
                	}
                  	if(ruleTagNode.getFirstChild().getNodeValue().equalsIgnoreCase("3")) {
                		continue;
                	}
            		firewallList.add(dto);
            	}
            }


		} catch(Exception e) {
			e.printStackTrace();
		}

		return firewallList;

	}

	//Flytta ut denna ur denna klass...
	//Skall ersätta alla #-markerade ord med ord angivna av user
	private void parseFirewall(FirewallRequestDTO fwrDTO, BufferedWriter writer){
		//String outPath = Util.restDir + "tmpAppendFirewallProduction";
		String inPath = Util.restDir + "appendFirewallProduction";
		BufferedReader reader = null;
		BufferedWriter fWriter = null;
		try {
			reader = new BufferedReader(new FileReader(inPath));
			fWriter = new BufferedWriter(new FileWriter(Util.restDir + "test2"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
				fWriter.write(line);
				fWriter.newLine();
			}	
			writer.flush();
			fWriter.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}


	}

	private void sendFirewallRequest(FirewallRequestDTO fwrDTO){
		String username = "admin";
		String password = "P!ssw0rd";
		String userPw = username + ":" + password;
		String encodedAuthorization = new String(Base64.getEncoder().encode(userPw.getBytes()));

		try {
			URL obj = new URL(Util.appendFirewallURL);
			HttpURLConnection con;
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");																																																																								
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			con.setDoOutput(true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
			parseFirewall(fwrDTO, writer);
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : ");
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





	public void serverRequest(ServerRequestDTO srDTO){
		System.out.println(srDTO.getNbrCpu());
		System.out.println(srDTO.getMemory());
		System.out.println(srDTO.getDiskSpace());
	}


}
