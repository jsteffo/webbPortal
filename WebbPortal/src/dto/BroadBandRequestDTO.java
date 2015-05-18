package dto;

public class BroadBandRequestDTO {

	private String firstName;
	private String lastName;
	private String creditNbr;
	private String email;
	private String speed;
	public BroadBandRequestDTO(String firstName, String lastName,
			String creditNbr, String email, String speed) {
	
		this.firstName = firstName;
		this.lastName = lastName;
		this.creditNbr = creditNbr;
		this.email = email;
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
	public String getEmail() {
		return email;
	}
	public String getSpeed() {
		return speed;
	}


	public void setSpeed(String speed) {
	
		this.speed = speed;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
