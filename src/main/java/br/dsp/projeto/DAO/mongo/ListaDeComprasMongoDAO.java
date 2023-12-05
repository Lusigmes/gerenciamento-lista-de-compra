package br.dsp.projeto.DAO.mongo;

import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.dsp.projeto.DAO.ListaDeComprasDAO;
import br.dsp.projeto.entity.ListaDeCompras;
import br.dsp.projeto.entity.Usuario;
import br.dsp.projeto.entity.enums.Tipo;
import java.util.List;
import java.time.LocalDateTime;
import br.dsp.projeto.entity.ItemLista;

@Repository
@Primary
public interface ListaDeComprasMongoDAO extends MongoRepository<ListaDeCompras, String>, ListaDeComprasDAO {

        List<ListaDeCompras> findByNomeContainingIgnoreCase(String nome);

        List<ListaDeCompras> findByDataCriacaoAfterOrderByDataCriacaoDesc(LocalDateTime data);

        List<ListaDeCompras> findByTipo(Tipo tipo);

        @Aggregation(pipeline = {
                        "{ $match: { '_id': ?0 } }",
                        "{ $unwind: '$itens' }",
                        "{ $replaceRoot: { newRoot: '$itens' } }"
        })
        List<ItemLista> findByIdItens(String id);

        @Aggregation(pipeline = {
                        "{ $match: { '_id': ?0 } }",
                        "{ $unwind: '$usuario' }",
                        "{ $replaceRoot: { newRoot: '$usuario' } }"
        })
        Usuario findUsuarioById(String id);

        long count();
}
