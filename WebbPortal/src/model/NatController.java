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

import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.Util;
import dto.FirewallRequestDTO;
import dto.NatRequestDTO;
/**
 * Used for Nat
 * @author stefan
 *
 */
@Stateless
public class NatController {

	public void appendNat(NatRequestDTO dto){

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
		
		try {
			URL obj = new URL(Util.appendNatUrl);
			HttpURLConnection con;
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");																																																																								
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			con.setDoOutput(true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
			parseNat(dto, writer);
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : ");
			System.out.println("Response Code : " + responseCode);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	public List<NatRequestDTO> refreshNat(){

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
		
		List <NatRequestDTO> natList = new ArrayList<>();
		try{

			URL obj = new URL(Util.getNatUrl);
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
            expression = "//natRule";
            NodeList firewallSet = (NodeList) xpath.compile(expression).evaluate(dom, XPathConstants.NODESET);
           
            //Måste separera name och id....
            for(int i = 0; i<firewallSet.getLength(); i++) {
            	NatRequestDTO dto = new NatRequestDTO();
            	
            	expression="ruleId";
            	Node idNode = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(idNode != null) {
            		dto.setId(idNode.getFirstChild().getNodeValue());
            	}
            	expression="action";
            	Node typeNode= (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(typeNode!= null) {
            		dto.setType(typeNode.getFirstChild().getNodeValue());
            	}
            	
            	expression="vnic";
            	Node domainNode = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(domainNode != null) {
            		dto.setDomain(domainNode.getFirstChild().getNodeValue());
            	}
            	
            	expression="originalAddress";
            	Node originalIpNode = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(originalIpNode != null) {
            		dto.setOriginalIp(originalIpNode .getFirstChild().getNodeValue());	
            	}
            	
            	expression="translatedAddress";
            	Node translatedIpNode= (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(translatedIpNode != null) {
            		dto.setTranslatedIp(translatedIpNode.getFirstChild().getNodeValue());	
            	}
            	
            	expression="originalPort";
            	Node originalPortNode = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(originalPortNode != null) {
            		dto.setOriginalPort(translatedIpNode.getFirstChild().getNodeValue());	
            	}
            	
            	expression="translatedPort";
            	Node translatedPortNode = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(translatedPortNode != null) {
            		dto.setTranslatedPort(translatedPortNode.getFirstChild().getNodeValue());	
            	}
            	
            	expression="ruleType";
            	Node ruleTypeNode = (Node) xpath.compile(expression).evaluate(firewallSet.item(i), XPathConstants.NODE);
            	if(ruleTypeNode.getFirstChild().getNodeValue().equalsIgnoreCase("user")){
            		natList.add(dto);
            	}
            }


		} catch(Exception e) {
			e.printStackTrace();
		}

		return natList;

	}
	public void deleteNat(String id){

		
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
		
		
		try{

			URL obj = new URL(Util.deleteNatUrl + id);
			HttpURLConnection con;
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("DELETE");
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			int responseCode = con.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	private void parseNat(NatRequestDTO dto, BufferedWriter writer){
		//String outPath = Util.restDir + "tmpAppendFirewallProduction";
		String inPath = Util.restDir + "nat";
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
				
				if(line.contains("#domain")){
					
					line = (line.replaceAll("#domain", dto.getDomain()));
				}

				
				if(line.contains("#type")){

					line = (line.replaceAll("#type", dto.getType()));
				}

				if(line.contains("#originalIp")){

					line = (line.replaceAll("#originalIp", dto.getOriginalIp()));
				}
				if(line.contains("#translatedIp")){

					line = (line.replaceAll("#translatedIp", dto.getTranslatedIp()));
				}
				if(line.contains("#originalPort")){

					line = (line.replaceAll("#originalPort", dto.getOriginalPort()));
				}
				if(line.contains("#translatedPort")){

					line = (line.replaceAll("#translatedPort", dto.getTranslatedPort()));
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
}
