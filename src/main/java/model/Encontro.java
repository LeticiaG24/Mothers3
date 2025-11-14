package model;

import java.time.LocalDate;

public class Encontro {
	private int id;
	private LocalDate data;
	private String status;
	
	public Encontro(int id, LocalDate data, String status){
		this.id=id;
		this.data=data;
		this.status=status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
