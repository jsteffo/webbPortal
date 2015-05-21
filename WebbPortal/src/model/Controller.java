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

import javax.ejb.Stateless;

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
					writer.write(line.replaceAll("#accept", fwrDTO.getAccess()) + "\n");
					
				}
				else if(line.contains("#name")){
					writer.write(line.replaceAll("#name", fwrDTO.getName()) + "\n");
					
				}
				else if(line.contains("#sourceIp")){
					writer.write(line.replaceAll("#sourceIp", fwrDTO.getSourceIp()) +"\n");
					
				}
				else if(line.contains("#destIp")){
					writer.write(line.replaceAll("#destIp", fwrDTO.getDestinationIp()) + "\n");
					
				}
				else if(line.contains("#port")){
					writer.write(line.replaceAll("#port", fwrDTO.getPort()) +"\n");

				}
				else{
					writer.write(line + "\n");
				}

			}	
			writer.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}


	}

	
	public void serverRequest(ServerRequestDTO srDTO){
		System.out.println(srDTO.getNbrCpu());
		System.out.println(srDTO.getMemory());
		System.out.println(srDTO.getDiskSpace());
	}
	

}
