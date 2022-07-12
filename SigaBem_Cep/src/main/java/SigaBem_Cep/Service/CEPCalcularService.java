package com.example.demo.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.demo.Model.CEPCalcular;
import com.example.demo.Model.EnderecoTO;
import com.example.demo.Repository.CEPCalcularRepository;

import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CEPCalcularService {
	@Autowired
	private CEPCalcularRepository repository;

	
	public CEPCalcular findCEPCalcular(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<CEPCalcular> findCEPAllCalcular() {
		return repository.findAll();
	}

	public CEPCalcular sendCEPCalcular(
			final Double peso,
			final String cepOrigem,
			final String cepDestino,
			final String nomeDestinatario) throws Exception {

				// Remove qualquer caractere inválido na string, por exemplo: 35.500-700 vira
				// 35500700.
				final String cepOrigemFormatter = cepOrigem.replaceAll("[^\\d]", "");
				final String cepDestinoFormatter = cepDestino.replaceAll("[^\\d]", "");

				if (peso <= 0D) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Peso inválido.");
				}

				if (cepOrigemFormatter.length() != 8 || cepDestinoFormatter.length() != 8) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP origem inválido.");
				}


				final RestTemplate restTemplate = new RestTemplate();

				EnderecoTO Origem = restTemplate.getForObject("https://viacep.com.br/ws/" + cepOrigemFormatter + "/json/", EnderecoTO.class);
				EnderecoTO Destino = restTemplate.getForObject("https://viacep.com.br/ws/" + cepDestinoFormatter + "/json/", EnderecoTO.class);

				if (Origem.getCep() == null) {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP origem: " + cepOrigemFormatter + " não encontrado.");
				}

				if (Destino.getCep() == null) {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP destino: " + cepDestinoFormatter + " não encontrado.");
				}

				Double vlTotalFrete;
				Date dataPrevistaEntrega;
				final Boolean isSameDDD = Origem.getDdd().equals(Destino.getDdd());
				final Boolean isSameState = Origem.getUf().equals(Destino.getUf());

				if (isSameDDD) {
					dataPrevistaEntrega = getDataPrevistaEntrega(1);
					vlTotalFrete = peso * 0.50D; // 50% desconto
				} else if (isSameState) {
					dataPrevistaEntrega = getDataPrevistaEntrega(3);
					vlTotalFrete = peso * 0.25D; // 75% desconto
				} else {
					dataPrevistaEntrega = getDataPrevistaEntrega(10);
					vlTotalFrete = peso;
				}

				final CEPCalcular pacote = new CEPCalcular(null, nomeDestinatario, vlTotalFrete, new Date(), dataPrevistaEntrega, cepOrigemFormatter, cepDestinoFormatter, peso);

				
				return repository.save(pacote);
		
	}
	
	 public void deleteCEPCalcular(Long id) {
		 repository.deleteById(id);
	    }
	 
	 public void deleteCEPAllCalcular() {
		 repository.deleteAll();
	    }

	private Date getDataPrevistaEntrega(final Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	
}
