package com.example.demo.Repository;

import com.example.demo.Model.CEPCalcular;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CEPCalcularRepository extends JpaRepository<CEPCalcular, Long>{
	
}
