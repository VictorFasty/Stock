package victornext.stock.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import victornext.stock.Model.ProductModel;
import victornext.stock.Repositories.ProductRepository;
import victornext.stock.Services.EmailService;

import java.util.List;

@Service
public class EstoqueScheduler {


    @Autowired
    private ProductRepository repository;

    @Autowired
    private EmailService service;


    @Scheduled(fixedRate = 3600000) //Executa a cada 1 hora
    public void verificarEstoque() {
        //Lista os produtos com baixo estoque e faz uma busca no banco de dados
        List<ProductModel> produtosCriticos = repository.findByQuantityLessThanQuantidadeMinima();

        if (!produtosCriticos.isEmpty()) {
            StringBuilder mensagem = new StringBuilder("🚨 Produtos com estoque abaixo do mínimo:\n\n");

            for (ProductModel produto : produtosCriticos) {
                mensagem.append("- ")
                        .append(produto.getName())
                        .append(": ")
                        .append(produto.getQuantity())
                        .append(" unidades (mínimo: ")
                        .append(produto.getQuantidadeMinima())
                        .append(")\n");
            }

            service.enviarAlertaEstoque(mensagem.toString());
        }
    }
}