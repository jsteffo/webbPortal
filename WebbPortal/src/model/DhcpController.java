package model;

import java.io.InputStream;
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

import org.kohsuke.rngom.digested.DChoicePattern;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.Util;
import dto.DhcpRequestDTO;

@Stateless
public class DhcpController {

	public List<DhcpRequestDTO> getLeases(){
		
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
		ArrayList<DhcpRequestDTO> list = new ArrayList<DhcpRequestDTO>();
		try {
			//Börjar med dynamic
			URL obj = new URL(Util.getDhcpDynamicURL);
			HttpURLConnection con;
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");																																																																								
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			con.getResponseCode();
			InputStream is = con.getInputStream();
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file

			Document dom = db.parse(is);

			// Create XPathFactory object
			XPathFactory xpathFactory = XPathFactory.newInstance();

			// Create XPath object
			XPath xpath = xpathFactory.newXPath();
			String expression;
			expression = "/dhcpLeases/dhcpLeaseInfo/leaseInfo";
			NodeList node = (NodeList) xpath.compile(expression).evaluate(dom, XPathConstants.NODESET);
			
			
			for(int i = 0; i<node.getLength(); i++) {
				DhcpRequestDTO dto = new DhcpRequestDTO();
				dto.setType("dynamic");
				
				expression="ipAddress";
            	Node ipNode = (Node) xpath.compile(expression).evaluate(node.item(i), XPathConstants.NODE);
            	if(ipNode != null) {
            		dto.setIp((ipNode.getFirstChild().getNodeValue()));
            	}
            	
				expression="clientHostname";
            	Node hostNode= (Node) xpath.compile(expression).evaluate(node.item(i), XPathConstants.NODE);
            	if(ipNode != null) {
            		dto.setHostName((hostNode.getFirstChild().getNodeValue()));
            	}
            	
				expression="starts";
            	Node startNode = (Node) xpath.compile(expression).evaluate(node.item(i), XPathConstants.NODE);
            	if(ipNode != null) {
            		dto.setStartLease((startNode.getFirstChild().getNodeValue()));
            	}
				expression="ends";
            	Node endNode = (Node) xpath.compile(expression).evaluate(node.item(i), XPathConstants.NODE);
            	if(ipNode != null) {
            		dto.setEndLease((endNode.getFirstChild().getNodeValue()));
            	}
            	list.add(dto);
			}
			//Static DHCP
			
			URL obj2 = new URL(Util.getDhcpStaticURL);
			HttpURLConnection con2;
			con2 = (HttpURLConnection) obj2.openConnection();
			con2.setRequestMethod("GET");																																																																								
			con2.setRequestProperty("Content-Type", "application/xml");
			con2.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			con2.getResponseCode();
			is = con2.getInputStream();
			
			dom = db.parse(is);

			// Create XPathFactory object
			xpathFactory = XPathFactory.newInstance();

			// Create XPath object
			xpath = xpathFactory.newXPath();
			expression = "/dhcp/staticBindings/staticBinding";
			node = (NodeList) xpath.compile(expression).evaluate(dom, XPathConstants.NODESET);
			
			for(int i = 0; i<node.getLength(); i++) {

				DhcpRequestDTO dto = new DhcpRequestDTO();
				dto.setType("static");
				
				expression="ipAddress";
            	Node ipNode = (Node) xpath.compile(expression).evaluate(node.item(i), XPathConstants.NODE);
            	if(ipNode != null) {
            		dto.setIp((ipNode.getFirstChild().getNodeValue()));
            	}
            	
				expression="hostname";
            	Node hostNode= (Node) xpath.compile(expression).evaluate(node.item(i), XPathConstants.NODE);
            	if(hostNode != null) {
            		dto.setHostName((hostNode.getFirstChild().getNodeValue()));
            	}
            	
            	dto.setStartLease("N/A");
            	dto.setEndLease("infinite");
            	list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
