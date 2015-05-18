package view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import dto.BroadBandRequestDTO;
import model.Controller;

@ManagedBean
@SessionScoped
public class ApplicationBean {

	@Inject
	Controller controller;
	
	private String speed;
	private String email;
	private String firstName;
	private String lastName;
	private String creditNbr;
	
	
	public String getSpeed(){
		return speed;
	}
	
	public void doStuff(){
		System.out.println("Hej");	
	}
	
	public void setSpeed(String speed){
		System.out.println("speed is set");
		this.speed = speed;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCreditNbr() {
		return creditNbr;
	}

	public void setCreditNbr(String creditNbr) {
		this.creditNbr = creditNbr;
	}

	public String getEmail(){
		return email;
	}
	
	/**
	 * Called whenever a new broadband is requested
	 */
	public void submitBroadBand(){
		controller.broadBandRequest(new BroadBandRequestDTO(firstName, lastName, creditNbr, email, speed));
		
	}
	
	public void setEmail(String email){
		System.out.println("email is set");
		this.email = email;
		
	}
	
	
	
	
	public void broadBandButton(){
		
	}
	
	public void fireWallButton(){
		
	}
	
	public void natButton(){
		
	}
	
	
	
	//Test
	
    private boolean enabled;

    public void toggle() {
        enabled = !enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
	
}
