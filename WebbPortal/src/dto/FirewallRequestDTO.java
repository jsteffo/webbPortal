package dto;

public class FirewallRequestDTO {
	private String name;
	private String destinationIp;
	private String sourceIp;
	private String direction;
	private String access;
	private String port;
	private String id;
	public FirewallRequestDTO(String destinationIp, String sourceIp,
			String direction, String access, String port, String name, String id) {
		
		this.destinationIp = destinationIp;
		this.sourceIp = sourceIp;
		this.direction = direction;
		this.access = access;
		this.port = port;
		this.name = name;
		this.id = id;
	}
	
	public FirewallRequestDTO(){
		
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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
