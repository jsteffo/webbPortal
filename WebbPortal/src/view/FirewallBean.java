package view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import dto.FirewallRequestDTO;
import model.Controller;


@ManagedBean(name="firewallBean")
@SessionScoped
public class FirewallBean {

	@Inject
	Controller controller;
	
	private String sourceIp;
	private String destinationIp;
	private String port;
	private String direction; //Inbound or outbound
	private String access;	//allow or deny
	
	public void submitFirewall(){
		controller.firewallRequest(new FirewallRequestDTO(destinationIp, sourceIp, direction, access, port));
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
