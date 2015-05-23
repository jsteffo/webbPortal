package view;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import model.NatController;
import dto.NatRequestDTO;

@ManagedBean
@SessionScoped
public class NatBean {
	
	@Inject
	NatController controller;
	
	private List<NatRequestDTO> currentNat;
	private String type; //dNat or sNat
	private String domain; //vnic0 or vnic 5
	private String originalIp;
	private String originalPort;
	private String translatedIp;
	private String translatedPort;
	private String id;
	
	@PostConstruct
	public void start(){
		refreshNat();
	}
	
	public void appendNat(){
		controller.appendNat(new NatRequestDTO(type, domain, originalIp, originalPort, translatedIp, translatedPort, id));
		refreshNat();
	}
	
	public void refreshNat(){
		currentNat = controller.refreshNat();
	}
	
	public void deleteNat(){
		Map<String, String> param = FacesContext.getCurrentInstance().getExternalContext().
			getRequestParameterMap();
	//System.out.println(param.get("delete"));
		controller.deleteNat(param.get("delete"));
		refreshNat();
	}
	

	public List<NatRequestDTO> getCurrentNat() {
		return currentNat;
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
		
		if(domain.equalsIgnoreCase("uplink")){
			this.domain = "0";	
		}
		if(domain.equalsIgnoreCase("internal")){
			this.domain = "5";	
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
