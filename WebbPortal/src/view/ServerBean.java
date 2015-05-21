package view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import dto.ServerRequestDTO;
import model.Controller;

@ManagedBean(name="serverBean")
@SessionScoped
public class ServerBean {

	@Inject
	Controller controller;
	
	private String nbrCpu;
	private String memory;
	private String diskSpace;
	
	public String getNbrCpu() {
		return nbrCpu;
	}
	public void setNbrCpu(String nbrCpu) {
		this.nbrCpu = nbrCpu;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getDiskSpace() {
		return diskSpace;
	}
	public void setDiskSpace(String diskSpace) {
		this.diskSpace = diskSpace;
	}
	
	
	public void submit(){
		controller.serverRequest(new ServerRequestDTO(memory, nbrCpu, diskSpace));
	}
	
}
