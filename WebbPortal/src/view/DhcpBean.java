package view;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import model.DhcpController;
import util.Util;
import dto.DhcpRequestDTO;
import dto.NatRequestDTO;

@ManagedBean
@RequestScoped
public class DhcpBean {

	private List<DhcpRequestDTO> currentDhcp;
	
	@Inject
	DhcpController controller;
	
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
	
	public void refresh(){
		currentDhcp = controller.getLeases();
	}
	public List<DhcpRequestDTO> getCurrentDhcp() {
		return currentDhcp;
	}
}
