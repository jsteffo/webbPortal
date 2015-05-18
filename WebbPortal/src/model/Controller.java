package model;

import javax.ejb.Stateless;

import dto.BroadBandRequestDTO;

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
	
}
