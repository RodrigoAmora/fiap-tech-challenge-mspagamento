package br.com.fiaap.mspagamento.repository;

import br.com.fiaap.mspagamento.domain.Pagamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends MongoRepository<Pagamento, String> {
}
