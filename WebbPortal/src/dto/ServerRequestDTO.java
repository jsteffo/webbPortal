package dto;

public class ServerRequestDTO {

	private String memory;
	private String nbrCpu;
	private String diskSpace;
	public ServerRequestDTO(String memory, String nbrCpu, String diskSpace) {
		
		this.memory = memory;
		this.nbrCpu = nbrCpu;
		this.diskSpace = diskSpace;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getNbrCpu() {
		return nbrCpu;
	}
	public void setNbrCpu(String nbrCpu) {
		this.nbrCpu = nbrCpu;
	}
	public String getDiskSpace() {
		return diskSpace;
	}
	public void setDiskSpace(String diskSpace) {
		this.diskSpace = diskSpace;
	}
	
}
