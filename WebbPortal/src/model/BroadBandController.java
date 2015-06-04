package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import util.Util;
import dto.BroadBandRequestDTO;

@Stateless
public class BroadBandController {

	@PostConstruct
	public void init(){
		Util.findEdgeId();
	}

	/**
	 * Called as a result of user pressing enable broadBand... 
	 * Update isEnabled to show other options such as firewall and NAT...
	 * And send a request to NSX-Manager to specify limit on bandwidth
	 */
	public void enableBroadBand(BroadBandRequestDTO dto){
		setupTraficShapingPolicy(dto);
		liftFirewallBlock(dto);
		setBroadbandEnabled();
	}

	private void setBroadbandEnabled(){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(Util.restDir + Util.isEnabledFilePath)));
			writer.write("true");
			writer.flush();
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}


	}

	//TODO
	private void liftFirewallBlock(BroadBandRequestDTO dto){
		if(Util.findIsEnabled()){
			return;
		}
		
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


		//COmmunicate with NSX manager to lift the all blocking rule somehow... Either delete or change...
		try{
			URL obj = new URL(Util.appendFirewallURL);
			HttpURLConnection con;
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");																																																																								
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			con.setDoOutput(true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
			BufferedReader reader = new BufferedReader(new FileReader(new File(Util.restDir + Util.firewallAllowFilePath)));
			String line;
			while((line = reader.readLine())!= null){
				writer.write(line);
			}
			writer.flush();
			con.getResponseCode();
			writer.close();
			reader.close();

		} catch(Exception e) {
			e.printStackTrace();
		}

	}


	private void setupTraficShapingPolicy(BroadBandRequestDTO dto){
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

			URL obj = new URL(Util.edgeBandWidthURL);
			HttpURLConnection con;
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			int responseCode = con.getResponseCode();

			//Time to read the inputStream...
			InputStream is = con.getInputStream();

			URL obj2 = new URL(Util.edgeBandWidthURL);
			HttpURLConnection con2;

			con2 = (HttpURLConnection) obj2.openConnection();
			con2.setDoOutput(true);
			con2.setRequestMethod("PUT");
			con2.setRequestProperty("Content-Type", "application/xml");
			con2.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			OutputStream os = con2.getOutputStream();

			//			InputStream is = new FileInputStream(new File(Util.restDir + "testInOutPolicy"));
			//			OutputStream os = new FileOutputStream(new File(Util.restDir + "testInOutPolicyResult"));

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
			String line;

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
			expression = "/vnic/inShapingPolicy/averageBandwidth";
			Node node = (Node) xpath.compile(expression).evaluate(dom, XPathConstants.NODE);
			if(node != null) {
				node.getFirstChild().setNodeValue(dto.getSpeed() + "000");
			}
			expression = "/vnic/inShapingPolicy/peakBandwidth";
			node = (Node) xpath.compile(expression).evaluate(dom, XPathConstants.NODE);
			if(node != null) {
				node.getFirstChild().setNodeValue(dto.getSpeed() + "000");
			}
			expression = "/vnic/outShapingPolicy/averageBandwidth";
			node = (Node) xpath.compile(expression).evaluate(dom, XPathConstants.NODE);
			if(node != null) {
				node.getFirstChild().setNodeValue(dto.getSpeed()+ "000");
			}
			expression = "/vnic/outShapingPolicy/peakBandwidth";
			node = (Node) xpath.compile(expression).evaluate(dom, XPathConstants.NODE);
			if(node != null) {
				node.getFirstChild().setNodeValue(dto.getSpeed()+ "000");
			}
			//Transform...
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer2 = new StringWriter();
			transformer.transform(new DOMSource(dom), new StreamResult(writer));
			String output = writer2.getBuffer().toString().replaceAll("\n|\r", "");

			writer.write(output);
			writer.flush();
			con2.getResponseCode();
			writer.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Returns the in and out traffic shaping policy as a string
	 */
	private String appendInOutShaping(BroadBandRequestDTO dto){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(Util.restDir + Util.inOutPolicyFilePath)));	
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null){
				if(line.contains("#inLimit")){
					line = line.replaceAll("#inLimit", dto.getSpeed()+"000");
				}

				if(line.contains("#outLimit")){
					line = line.replaceAll("#outLimit", dto.getSpeed()+"000");
				}

				sb.append(line);
				sb.append(System.lineSeparator());
			}
			reader.close();
			return sb.toString();


		} catch(Exception e) {
			e.printStackTrace();
		}
		//error
		return null;
	}
}
