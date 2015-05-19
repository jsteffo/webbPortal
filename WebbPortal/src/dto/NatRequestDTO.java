package dto;

public class NatRequestDTO {

	private String type;
	private String source;
	private String destination;
	public NatRequestDTO(String type, String source, String destination) {
		
		this.type = type;
		this.source = source;
		this.destination = destination;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
}
