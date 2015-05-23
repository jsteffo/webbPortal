package dto;

public class NatRequestDTO {

	private String type; //dNat or sNat
	private String domain; //vnic0 or vnic 5
	private String originalIp;
	private String originalPort;
	private String translatedIp;
	private String translatedPort;
	private String id;
	public NatRequestDTO(String type, String domain, String originalIp,
			String originalPort, String translatedIp, String translatedPort,
			String id) {
	
		this.type = type;
		this.domain = domain;
		this.originalIp = originalIp;
		this.originalPort = originalPort;
		this.translatedIp = translatedIp;
		this.translatedPort = translatedPort;
		this.id = id;
	}
	
	public NatRequestDTO(){
		
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		if(domain.equals("0")) {
			this.domain = "uplink";
		}
		if(domain.equals("5")) {
			this.domain = "internal";
		}
	}
	public String getOriginalIp() {
		return originalIp;
	}
	public void setOriginalIp(String originalIp) {
		this.originalIp = originalIp;
	}
	public String getOriginalPort() {
		return originalPort;
	}
	public void setOriginalPort(String originalPort) {
		this.originalPort = originalPort;
	}
	public String getTranslatedIp() {
		return translatedIp;
	}
	public void setTranslatedIp(String translatedIp) {
		this.translatedIp = translatedIp;
	}
	public String getTranslatedPort() {
		return translatedPort;
	}
	public void setTranslatedPort(String translatedPort) {
		this.translatedPort = translatedPort;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
