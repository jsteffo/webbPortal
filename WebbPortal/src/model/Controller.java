package model;

import javax.ejb.Stateless;

import dto.BroadBandRequestDTO;
import dto.FirewallRequestDTO;
import dto.NatRequestDTO;

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
		System.out.println(fwrDTO.getAccess());
		System.out.println(fwrDTO.getDestinationIp());
		System.out.println(fwrDTO.getDirection());
		System.out.println(fwrDTO.getPort());
		System.out.println(fwrDTO.getSourceIp());
	}
	
	public void natRequest(NatRequestDTO nDTO) {
		System.out.println(nDTO.getDestination());
		System.out.println(nDTO.getSource());
		System.out.println(nDTO.getType());
	}
}
