package br.com.surittec.desafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.surittec.desafio.dao.ProdutoDAO;
import br.com.surittec.desafio.models.CarrinhoCompras;
import br.com.surittec.desafio.models.CarrinhoItem;
import br.com.surittec.desafio.models.Produto;
import br.com.surittec.desafio.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {

	@Autowired
	private ProdutoDAO ProdutoDao;
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	@RequestMapping("/add")
	public ModelAndView add(Integer ProdutoId, TipoPreco tipoPreco) {
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		CarrinhoItem carrinhoItem = criaItem(ProdutoId, tipoPreco);
		carrinho.add(carrinhoItem);
		return modelAndView;
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView itens() {
//	    if(true) throw new RuntimeException("Excessão Genérica Acontecendo!!!!");
		return new ModelAndView("carrinho/itens");
	}

	private CarrinhoItem criaItem(Integer ProdutoId, TipoPreco tipoPreco) {
		Produto Produto = this.ProdutoDao.find(ProdutoId);
		CarrinhoItem carrinhoItem = new CarrinhoItem(Produto, tipoPreco);
		return carrinhoItem;
	}
	
	@RequestMapping("/remover")
	public ModelAndView remover(Integer ProdutoId, TipoPreco tipoPreco) {
		carrinho.remover(ProdutoId, tipoPreco);
		return new ModelAndView("redirect:/carrinho");
	}
	
}
