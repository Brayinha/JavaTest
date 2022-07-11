package com.example.demo.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Calcular_Frete")
public class CEPCalcular {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long IDCalcular;
	@Column(nullable =  false, length = 8)
	private String cepOrigem;
	@Column(nullable =  false, length = 8)
	private String cepDestino;
	@Column(nullable =  false, length = 4)
	private double peso;
	@Column(nullable =  false, length = 50)
	private String nomeDestinatario;
	@Column(name="Valor_TotalFrete")
	private double vlTotalFrete;
	@Column(length = 8)
	LocalDate dataConsulta;
	@Column(length = 8)
	LocalDate dataPrevistaEntrega;
	
	public CEPCalcular(String cepOrigem, String cepDestino, double peso, String nomeDestinatario,
			double vlTotalFrete,LocalDate dataConsulta, LocalDate dataPrevistaEntrega) {
		super();
		this.cepOrigem = cepOrigem;
		this.cepDestino = cepDestino;
		this.peso = peso;
		this.nomeDestinatario = nomeDestinatario;
		this.vlTotalFrete = vlTotalFrete;
		this.dataConsulta = dataConsulta;
		this.dataPrevistaEntrega = dataPrevistaEntrega;
	}
	
	
	public CEPCalcular() {
		super();
		//TODO Auto-generated constructor stub
	}


	public long getIDCalcular() {
		return IDCalcular;
	}
	public void setIDCalcular(long iDCalcular) {
		IDCalcular = iDCalcular;
	}
	public String getCepOrigem() {
		return cepOrigem;
	}
	public void setCepOrigem(String cepOrigem) {
		this.cepOrigem = cepOrigem;
	}
	public String getCepDestino() {
		return cepDestino;
	}
	public void setCepDestino(String cepDestino) {
		this.cepDestino = cepDestino;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public String getNomeDestinatario() {
		return nomeDestinatario;
	}
	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}
	public double getVlTotalFrete() {
		return vlTotalFrete;
	}
	public void setVlTotalFrete(double vlTotalFrete) {
		this.vlTotalFrete = vlTotalFrete;
	}
	public LocalDate getDataPrevistaEntrega() {
		return dataPrevistaEntrega;
	}
	public void setDataPrevistaEntrega(LocalDate dataPrevistaEntrega) {
		this.dataPrevistaEntrega = dataPrevistaEntrega;
	}
	public LocalDate getDataConsulta() {
		return dataConsulta;
	}
	public void setDataConsulta(LocalDate dataConsulta) {
		this.dataConsulta = dataConsulta;
	}
	
	
	
	
	
	

}
