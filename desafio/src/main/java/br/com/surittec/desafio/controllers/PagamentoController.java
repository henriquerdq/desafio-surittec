package br.com.surittec.desafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.surittec.desafio.models.CarrinhoCompras;
import br.com.surittec.desafio.models.DadosPagamento;
import br.com.surittec.desafio.models.Usuario;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public ModelAndView finalizar(@AuthenticationPrincipal Usuario usuario, RedirectAttributes model){
		String uri = "http://book-payment.herokuapp.com/payment";
		
		try {
			String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
			model.addFlashAttribute("message", response);

			this.carrinho.limpa();
			
			return new ModelAndView("redirect:/");
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			model.addFlashAttribute("message", "Valor maior que o permitido! Compra negada!");
			return new ModelAndView("redirect:/");
		}
	}

}