package view;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import model.NatController;
import util.Util;
import dto.NatRequestDTO;

@ManagedBean(name="forwardBean")
@RequestScoped
public class ForwardBean {

	
	@PostConstruct
	public void start(){
		if(!Util.findIsEnabled()){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("broadBand.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Inject
	NatController controller;
	
	private String port;

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public void submit(){
		//1 eller 0 osäker... Kolla hela denna imorn...
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String ip = request.getRemoteAddr();
		System.out.println(Util.getEdgeIp());
		System.out.println(ip);
		ip = "192.168.228.3";
		controller.appendNat(new NatRequestDTO("dnat", "0", Util.getEdgeIp(), "any", ip, port, ""));
		//controller.appendNat(new NatRequestDTO("dnat", "1", originalIp, originalPort, translatedIp, translatedPort, ""));
		
	}
	
	
	
}
