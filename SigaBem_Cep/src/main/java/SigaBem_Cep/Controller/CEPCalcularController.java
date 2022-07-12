package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.Model.CEPCalcular;
import com.example.demo.Model.EnderecoTO;
import com.example.demo.Repository.CEPCalcularRepository;
import com.example.demo.Service.CEPCalcularService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@RestController
@RequestMapping(value="/CEPCalcular/")
@Api(value = "API Calculo de Frete")
@CrossOrigin(origins="*")
public class CEPCalcularController {
	
	@Autowired 
	private CEPCalcularService service;
	
	  
	
	  @PostMapping("/CepFrete")
	  @ApiOperation(value="Cadastra uma Encomenda")
	  public ResponseEntity<CEPCalcular>saveCEPCalcular(
				@RequestParam @ApiParam(value = "Peso da encomenda") final Double peso,
				@RequestParam @ApiParam(value = "CEP origem") final String cepOrigem,
				@RequestParam @ApiParam(value = "CEP destino") final String cepDestino,
				@RequestParam @ApiParam(value = "Nome do destinatário") final String nomeDestinatario) throws Exception {
		
		  return new ResponseEntity<>(service.sendCEPCalcular(peso, cepOrigem, cepDestino, nomeDestinatario), HttpStatus.OK);
	        
	    }
	  
	  
	
	
	  @GetMapping("/Cep_Frete/{id}")
	  @ApiOperation(value="Retorna uma encomenda Cadastrada")
	  public ResponseEntity<CEPCalcular> getCEPCalcularById(@PathVariable("id") long id) {
		  return new ResponseEntity<>(service.findCEPCalcular(id), HttpStatus.OK);
	  }
	  
	  @DeleteMapping("/Cep_Frete/{id}")
	  @ApiOperation(value="Deleta uma encomenda já Cadatrado")
	  public ResponseEntity<CEPCalcular> deleteCEPCalcularById(@PathVariable("todoId") Long todoId) {
	        try {
	        	service.deleteCEPCalcular(todoId);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	          } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	          }
	    }
	  
	  @GetMapping("/Cep_Frete")
	  @ApiOperation(value="Retorna todos os calculos de frete já Cadatrado")
	  public ResponseEntity<List<CEPCalcular>> getCEPCalcularAll() {
		  List<CEPCalcular> cepCalculars = service.findCEPAllCalcular();
		  if (cepCalculars.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }
	      return new ResponseEntity<>(cepCalculars, HttpStatus.OK);
	  }
	  
	  @DeleteMapping("/CepFrete")
	  public ResponseEntity<HttpStatus> deleteAllTutorials() {
	    try {
	      service.deleteCEPAllCalcular();
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  
	  
	    
	
	
	  
}
