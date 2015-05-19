package dto;

public class FirewallRequestDTO {

	private String destinationIp;
	private String sourceIp;
	private String direction;
	private String access;
	private String port;
	public FirewallRequestDTO(String destinationIp, String sourceIp,
			String direction, String access, String port) {
		
		this.destinationIp = destinationIp;
		this.sourceIp = sourceIp;
		this.direction = direction;
		this.access = access;
		this.port = port;
	}
	public String getDestinationIp() {
		return destinationIp;
	}
	public void setDestinationIp(String destinationIp) {
		this.destinationIp = destinationIp;
	}
	public String getSourceIp() {
		return sourceIp;
	}
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
}
