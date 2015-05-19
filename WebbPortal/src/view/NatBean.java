package view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import dto.NatRequestDTO;
import model.Controller;

@ManagedBean
@SessionScoped
public class NatBean {
	
	@Inject
	Controller controller;
	
	private String type;
	private String source;
	private String destination;
	
	public void submit(){
		controller.natRequest(new NatRequestDTO(type, source, destination));
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
