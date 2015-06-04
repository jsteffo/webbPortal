package view;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import util.Util;
import dto.BroadBandRequestDTO;
import model.Controller;

@ManagedBean
@RequestScoped
public class ApplicationBean {

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
	
	public void check() {
	 
	}

	
}
