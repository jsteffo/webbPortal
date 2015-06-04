package dto;

public class DhcpRequestDTO {

	private String type; //dynamic or static
	private String ip;
	private String hostName;
	private String startLease;
	private String endLease;
	
	public DhcpRequestDTO(){
		
	}
	
	
	
	public DhcpRequestDTO(String type, String ip, String hostName,
			String startLease, String endLease) {
		this.type = type;
		this.ip = ip;
		this.hostName = hostName;
		this.startLease = startLease;
		this.endLease = endLease;
	}



	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getStartLease() {
		return startLease;
	}
	public void setStartLease(String startLease) {
		this.startLease = startLease;
	}
	public String getEndLease() {
		return endLease;
	}
	public void setEndLease(String endLease) {
		this.endLease = endLease;
	}
	
	
	
	
}
