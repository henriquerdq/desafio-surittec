package br.com.casadocodigo.loja.builders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.surittec.desafio.models.Preco;
import br.com.surittec.desafio.models.Produto;
import br.com.surittec.desafio.models.TipoPreco;

public class ProdutoBuilder {

    private List<Produto> Produtos = new ArrayList<>();

    private ProdutoBuilder(Produto Produto) {
        Produtos.add(Produto);
    }

    public static ProdutoBuilder newProduto(TipoPreco tipoPreco, BigDecimal valor) {
        Produto livro = create("livro 1", tipoPreco, valor);
        return new ProdutoBuilder(livro);
    }

    public static ProdutoBuilder newProduto() {
        Produto livro = create("livro 1", TipoPreco.COMBO, BigDecimal.TEN);
        return new ProdutoBuilder(livro);
    }

    private static Produto create(String nomeLivro, TipoPreco tipoPreco, BigDecimal valor) {
        Produto livro = new Produto();
        livro.setTitulo(nomeLivro);
        livro.setDataLancamento(Calendar.getInstance());
        livro.setPaginas(150);
        livro.setDescricao("Livro top sobre testes");
        Preco preco = new Preco();
        preco.setTipo(tipoPreco);
        preco.setValor(valor);
        livro.getPrecos().add(preco);
        return livro;
    }

    public ProdutoBuilder more(int number) {
        Produto base = Produtos.get(0);
        Preco preco = base.getPrecos().get(0);
        for (int i = 0; i < number; i++) {
            Produtos.add(create("Book " + i, preco.getTipo(), preco.getValor()));
        }
        return this;
    }

    public Produto buildOne() {
        return Produtos.get(0);
    }

    public List<Produto> buildAll() {
        return Produtos;
    }
}