package model;

public class Rel {
	private int idMae;
	private int idServico;
	private int idEncontro;
	
	public Rel(int idMae, int idServico, int idEncontro) {
		this.idMae = idMae;
		this.idServico = idServico;
		this.idEncontro = idEncontro;
	}

	public int getIdMae() {
		return idMae;
	}

	public void setIdMae(int idMae) {
		this.idMae = idMae;
	}

	public int getIdServico() {
		return idServico;
	}

	public void setIdServico(int idServico) {
		this.idServico = idServico;
	}

	public int getIdEncontro() {
		return idEncontro;
	}

	public void setIdEncontro(int idEncontro) {
		this.idEncontro = idEncontro;
	}
	
	
}
