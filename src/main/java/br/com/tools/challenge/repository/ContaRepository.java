package br.com.tools.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tools.challenge.entity.ContaEntity;

public interface ContaRepository extends JpaRepository<ContaEntity, Long>{

	
	public Optional<ContaEntity> findByCartao(String cartao);
}
