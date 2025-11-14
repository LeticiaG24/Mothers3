package model;

import java.time.LocalDate;

public class Encontro {
	private int id;
	private LocalDate data;
	private String descricao;
	private String status;
	private int idMaeResp;
	
	public Encontro(int id, LocalDate data, String descricao, String status, int idMaeResp){
		this.id=id;
		this.data=data;
		this.descricao=descricao;
		this.status=status;
		this.idMaeResp=idMaeResp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIdMaeResp() {
		return idMaeResp;
	}

	public void setIdMaeResp(int idMaeResp) {
		this.idMaeResp = idMaeResp;
	}
	
	
}
