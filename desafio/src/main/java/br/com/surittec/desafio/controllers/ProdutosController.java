package br.com.surittec.desafio.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.surittec.desafio.dao.ProdutoDAO;
import br.com.surittec.desafio.infra.FileSaver;
import br.com.surittec.desafio.models.Produto;
import br.com.surittec.desafio.models.TipoPreco;
import br.com.surittec.desafio.validation.ProdutoValidation;

@Controller
@RequestMapping("/Produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO dao;
	
	@Autowired
    private FileSaver fileSaver;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto Produto) {
		ModelAndView modelAndView = new ModelAndView("Produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@CacheEvict(value="ProdutosHome", allEntries=true)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto Produto, BindingResult result, 
				RedirectAttributes redirectAttributes){
		
		if(result.hasErrors()) {
			return form(Produto);
		}
		
		String path = fileSaver.write("arquivos-sumario", sumario);
		Produto.setSumarioPath(path);
		
		dao.gravar(Produto);
		
		redirectAttributes.addFlashAttribute("message", "Produto cadastrado com sucesso!");
		
		return new ModelAndView("redirect:/Produtos");
	}
	
	@RequestMapping( method=RequestMethod.GET)
	public ModelAndView listar() {
		List<Produto> Produtos = dao.listar();
		ModelAndView modelAndView = new ModelAndView("Produtos/lista");
		modelAndView.addObject("Produtos", Produtos);
		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id){
	    ModelAndView modelAndView = new ModelAndView("/Produtos/detalhe");
	    Produto Produto = dao.find(id);
	    
//	    if(true) throw new RuntimeException("Excessão Genérica Acontecendo!!!!");
	    
	    modelAndView.addObject("Produto", Produto);
	    return modelAndView;
	}
	
//	@RequestMapping("/{id}")
//	@ResponseBody
//	public Produto detalheJson(@PathVariable("id") Integer id){
//	    Produto Produto = dao.find(id);
//	    return Produto;
//	}

    //tratamente de exceção especifica nesse controlador 
//	@ExceptionHandler(NoResultException.class)
//	public String trataDetalheNaoEcontrado(){
//	    return "error";
//	}
}
