package br.com.surittec.desafio.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class CarrinhoItem implements Serializable{

	private static final long serialVersionUID = 1L;

	private Produto Produto;
	private TipoPreco tipoPreco;

	public CarrinhoItem(Produto Produto, TipoPreco tipoPreco) {
		this.Produto = Produto;
		this.tipoPreco = tipoPreco;
	}
	
	public BigDecimal getPreco() {
		return Produto.precoPara(tipoPreco);
	}

	public Produto getProduto() {
		return Produto;
	}

	public void setProduto(Produto Produto) {
		this.Produto = Produto;
	}

	public TipoPreco getTipoPreco() {
		return tipoPreco;
	}

	public void setTipoPreco(TipoPreco tipoPreco) {
		this.tipoPreco = tipoPreco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Produto == null) ? 0 : Produto.hashCode());
		result = prime * result + ((tipoPreco == null) ? 0 : tipoPreco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarrinhoItem other = (CarrinhoItem) obj;
		if (Produto == null) {
			if (other.Produto != null)
				return false;
		} else if (!Produto.equals(other.Produto))
			return false;
		if (tipoPreco != other.tipoPreco)
			return false;
		return true;
	}
	
	public BigDecimal getTotal(int quantidade) {
		return this.getPreco().multiply(new BigDecimal(quantidade));			
	}
}