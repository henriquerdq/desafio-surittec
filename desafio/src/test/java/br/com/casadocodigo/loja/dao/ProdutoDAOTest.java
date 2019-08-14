package br.com.casadocodigo.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.builders.ProdutoBuilder;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.surittec.desafio.conf.JPAConfiguration;
import br.com.surittec.desafio.dao.ProdutoDAO;
import br.com.surittec.desafio.models.Produto;
import br.com.surittec.desafio.models.TipoPreco;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {JPAConfiguration.class, ProdutoDAO.class, DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ProdutoDAOTest {
	
	
	@Autowired
    private ProdutoDAO ProdutoDao;

	@Test
	@Transactional
	public void deveSomarTodosOsPrecosPorTipoLivro() {

	    List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN).more(3).buildAll();
	    List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoPreco.EBOOK, BigDecimal.TEN).more(3).buildAll();

	    livrosImpressos.stream().forEach(ProdutoDao::gravar);
	    livrosEbook.stream().forEach(ProdutoDao::gravar);
	    
	    BigDecimal valor = ProdutoDao.somaPrecosPorTipo(TipoPreco.EBOOK);
	    Assert.assertEquals(new BigDecimal(40).setScale(2), valor);
	}
}
