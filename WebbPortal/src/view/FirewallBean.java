package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import model.Controller;
import dto.FirewallRequestDTO;


@ManagedBean(name="firewallBean")
@SessionScoped
public class FirewallBean {

	@Inject
	Controller controller;
	
	private String name;
	private List<FirewallRequestDTO> currentRules = new ArrayList<>();
	private String sourceIp;
	private String destinationIp;
	private String port;
	private String direction; //Inbound or outbound
	private String access;	//allow or deny
	private String id;
	
	@PostConstruct
	public void start(){
		refresh();
	}
	
	public void refresh(){
	
		currentRules = controller.refreshFirewall();
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void delete(){
		Map<String,String> params = 
				FacesContext.getCurrentInstance().
				getExternalContext().getRequestParameterMap();
		String deleteId = params.get("delete");
		controller.deleteFirewall(deleteId);
		refresh();
		//System.out.println("Id is: " + deleteId);
	}
	
	public List<FirewallRequestDTO> getCurrentRules(){

		return currentRules;
	}
	
	public void submitFirewall(){
		controller.firewallRequest(new FirewallRequestDTO(destinationIp, sourceIp, direction, access, port, name, id));
		refresh();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSourceIp() {
		return sourceIp;
	}
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	public String getDestinationIp() {
		return destinationIp;
	}
	public void setDestinationIp(String destinationIp) {
		this.destinationIp = destinationIp;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
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
	
	
}
