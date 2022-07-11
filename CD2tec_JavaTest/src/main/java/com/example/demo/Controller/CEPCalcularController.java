package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.Model.CEPCalcular;
import com.example.demo.Model.EnderecoTO;
import com.example.demo.Repository.CEPCalcularRepository;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@RestController
@RequestMapping(value="/CEPOutput/")
@Api(value = "API Calculo de Frete")
@CrossOrigin(origins="*")
public class CEPCalcularController {
	
	@Autowired 
	private CEPCalcularRepository cepOut;
	
	
	  @GetMapping("/getCep/{cep}")
	  public ResponseEntity<EnderecoTO> doObterCep(@PathVariable(name = "cep") String cep) {
		  RestTemplate restTemplate = new RestTemplate();
		  String uri = "http://viacep.com.br/ws/{cep}/json/";
		  Map<String, String> params = new HashMap<String, String>();
		  params.put("cep", cep);
		  EnderecoTO enderecoTO = restTemplate.getForObject(uri, EnderecoTO.class, params);
		
		    
	        return new ResponseEntity<EnderecoTO>(enderecoTO, HttpStatus.OK);
			
	  
	    
	  }
	  
	
	  @PostMapping("Cep_Fretes")
	  @ApiOperation(value="Cadastra um calculo de frete já Cadatrado")
	  public ResponseEntity<CEPCalcular>saveTodo(@RequestBody CEPCalcular cepCalcular) {
		  ResponseEntity<EnderecoTO> e1 = doObterCep(cepCalcular.getCepOrigem());
		  ResponseEntity<EnderecoTO> e2 = doObterCep(cepCalcular.getCepDestino());
		 if(e1 == null && e2 == null) {
		 	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		 }else {
			 EnderecoTO e11 = new EnderecoTO();
			 EnderecoTO e22 = new EnderecoTO();
			 if(e11.getDdd().equals(e22.getDdd())) {
				 cepCalcular.getDataPrevistaEntrega();
				 CEPCalcular _cepCalcular = cepOut.save(new CEPCalcular(cepCalcular.getCepOrigem(),cepCalcular.getCepDestino(),
				 cepCalcular.getPeso(),cepCalcular.getNomeDestinatario(),(cepCalcular.getVlTotalFrete()*cepCalcular.getPeso())*(0.5),
				 LocalDate.now(),LocalDate.now().plusDays(1)));
			     return new ResponseEntity<>(_cepCalcular, HttpStatus.CREATED);
			 	
		 	 }if(!e11.getUf().equals(e22.getUf())) {
		 		CEPCalcular _cepCalcular = cepOut.save(new CEPCalcular(cepCalcular.getCepOrigem(),cepCalcular.getCepDestino(),
 				cepCalcular.getPeso(),cepCalcular.getNomeDestinatario(),(cepCalcular.getVlTotalFrete()*cepCalcular.getPeso())*(0.75),
 				LocalDate.now(),LocalDate.now().plusDays(3)));
 			 	 return new ResponseEntity<>(_cepCalcular, HttpStatus.CREATED);
		 		 
		 	   
		 	 }
		 	CEPCalcular _cepCalcular = cepOut.save(new CEPCalcular(cepCalcular.getCepOrigem(),cepCalcular.getCepDestino(),
			 cepCalcular.getPeso(),cepCalcular.getNomeDestinatario(),(cepCalcular.getVlTotalFrete()*cepCalcular.getPeso()),
			 LocalDate.now(),LocalDate.now().plusDays(10)));
		     return new ResponseEntity<>(_cepCalcular, HttpStatus.CREATED);
		 }
		 	
	        
	    }
	
	
	  @GetMapping("/CEPOutput/Cep_Fretes/{id}")
	  @ApiOperation(value="Retorna um calculo de frete já Cadatrado")
	  public ResponseEntity<CEPCalcular> getCEPCalcularById(@PathVariable("id") long id) {
		  CEPCalcular cep = cepOut.findById(id).get();
		  return new ResponseEntity<>(cep, HttpStatus.OK);
	  }
	  
	  @GetMapping("/CEPOutput/Cep_Fretes")
	  @ApiOperation(value="Retorna todos os calculos de frete já Cadatrado")
	  public ResponseEntity<List<CEPCalcular>> getCEPCalcularAll() {
		  List<CEPCalcular> Allceps = new ArrayList<>();
		  cepOut.findAll().forEach(Allceps::add);
		  return new ResponseEntity<>(Allceps, HttpStatus.OK);
	  }
	    
	
	
	  
}
