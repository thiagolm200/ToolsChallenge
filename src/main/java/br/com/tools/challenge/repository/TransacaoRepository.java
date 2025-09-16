package br.com.tools.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tools.challenge.entity.TransacaoEntity;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Long>{

	public Optional<TransacaoEntity> findByCodigo(Long codigo);
}
