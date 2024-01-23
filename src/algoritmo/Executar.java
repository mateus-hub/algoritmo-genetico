package algoritmo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Produto {
	private String nome;
	private Double espaco;
	private Double valor;

	public Produto(String nome, Double espaco, Double valor) {
		this.nome = nome;
		this.espaco = espaco;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getEspaco() {
		return espaco;
	}

	public void setEspaco(Double espaco) {
		this.espaco = espaco;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}

class Individuo implements Comparable<Individuo> {
	private List espacos = new ArrayList<>();
	private List valores = new ArrayList<>();
	private Double limiteEspacos;
	private Double notaAvaliacao;
	private Double espacoUsado;
	private int geracao;
	private List cromossomo = new ArrayList<>();
	
	public Individuo(List espacos, List valores, Double limiteEspacos) {
		this.espacos = espacos;
		this.valores = valores;
		this.limiteEspacos = limiteEspacos;
		this.notaAvaliacao = 0.0;
		this.espacoUsado = 0.0;
		this.geracao = 0;
		
		for (int i = 0; i < this.espacos.size(); i++) {
			if (java.lang.Math.random() < 0.5) {
				this.cromossomo.add("0");
				
			} else {
				this.cromossomo.add("1");
			}
		}
	}
	
	public void avaliacao() {
		Double nota = 0.0;
		Double somaEspacos = 0.0;
		for (int i = 0; i < this.cromossomo.size(); i++) {
			if (this.cromossomo.get(i).equals("1")) {
				nota += (Double)this.valores.get(i);
				somaEspacos += (Double) this.espacos.get(i);
			}
		}
		
		if (somaEspacos > this.limiteEspacos) {
			nota = 1.0;
		}
		
		this.notaAvaliacao = nota;
		this.espacoUsado = somaEspacos;
	}
	
	public List crossover(Individuo outroIndividuo) {
		int corte =  (int) Math.round(Math.random() * this.cromossomo.size());
		List filho1 = new ArrayList<>();
		filho1.addAll(outroIndividuo.getCromossomo().subList(0, corte));
		filho1.add(this.cromossomo.subList(corte, this.cromossomo.size()));
		
		List filho2 = new ArrayList<>();
		filho2.addAll(this.cromossomo.subList(0, corte));
		filho2.addAll(outroIndividuo.getCromossomo().subList(corte, this.cromossomo.size()));
		
		List<Individuo> filhos = new ArrayList<>();
		filhos.add(new Individuo(this.espacos, this.valores, this.limiteEspacos));
		filhos.add(new Individuo(this.espacos, this.valores, this.limiteEspacos));
		
		filhos.get(0).setCromossomo(filho1);
		filhos.get(0).setGeracao(this.geracao + 1);
		filhos.get(1).setCromossomo(filho2);
		filhos.get(1).setGeracao(this.geracao + 1);
		
		return filhos;
	}
	
	public Individuo mutacao(Double taxaMutacao) {
		System.out.println("Antes da mutacao: " + this.cromossomo);
		for (int i = 0; i < this.cromossomo.size(); i++) {
			if (Math.random() < taxaMutacao) {
				if (this.cromossomo.get(i).equals(("1"))) {
					this.cromossomo.set(i, "0");				
				} else {
					this.cromossomo.set(i, "1");
				}
			}
		}
		System.out.println("Depois da mutacao: " + this.cromossomo);
		return this;
	}

	public List getEspacos() {
		return espacos;
	}

	public void setEspacos(List espacos) {
		this.espacos = espacos;
	}

	public List getValores() {
		return valores;
	}

	public void setValores(List valores) {
		this.valores = valores;
	}

	public Double getLimiteEspacos() {
		return limiteEspacos;
	}

	public void setLimiteEspacos(Double limiteEspacos) {
		this.limiteEspacos = limiteEspacos;
	}

	public Double getNotaAvaliacao() {
		return notaAvaliacao;
	}

	public void setNotaAvaliacao(Double notaAvaliacao) {
		this.notaAvaliacao = notaAvaliacao;
	}

	public Double getEspacoUsado() {
		return espacoUsado;
	}

	public void setEspacoUsado(Double espacoUsado) {
		this.espacoUsado = espacoUsado;
	}

	public int getGeracao() {
		return geracao;
	}

	public void setGeracao(int geracao) {
		this.geracao = geracao;
	}

	public List getCromossomo() {
		return cromossomo;
	}

	public void setCromossomo(List cromossomo) {
		this.cromossomo = cromossomo;
	}

	@Override
	public int compareTo(Individuo o) {
		if (this.notaAvaliacao > o.getNotaAvaliacao()) {
			return -1;
		}
		if(this.notaAvaliacao < o.getNotaAvaliacao()) {
			return 1;
		}
		return 0;
	}
}

class AlgoritmoGenetico {
	private int tamanhoPopulacao;
	private List<Individuo> populacao = new ArrayList<>();
	private int geracao;
	private Individuo melhorSolucao;
	
	public AlgoritmoGenetico(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}
	
	public void inicializaPopulacao(List espacos, List valores, Double limiteEspacos) {
		for (int i = 0; i < this.tamanhoPopulacao; i++) {
			this.populacao.add(new Individuo(espacos, valores, limiteEspacos));
		}
		this.melhorSolucao = this.populacao.get(0);
	}
	
	public void ordenaPopulacao() {
		Collections.sort(this.populacao);
	}

	public int getTamanhoPopulacao() {
		return tamanhoPopulacao;
	}

	public void setTamanhoPopulacao(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}

	public List<Individuo> getPopulacao() {
		return populacao;
	}

	public void setPopulacao(List<Individuo> populacao) {
		this.populacao = populacao;
	}

	public int getGeracao() {
		return geracao;
	}

	public void setGeracao(int geracao) {
		this.geracao = geracao;
	}

	public Individuo getMelhorSolucao() {
		return melhorSolucao;
	}

	public void setMelhorSolucao(Individuo melhorSolucao) {
		this.melhorSolucao = melhorSolucao;
	}
}

public class Executar {
	public static void main(String args[]) {
		List<Produto> listaProdutos = new ArrayList<>();
		listaProdutos.add(new Produto("Geladeira Dako", 0.751, 999.90));
		listaProdutos.add(new Produto("Iphone 6", 0.000089, 2911.12));
		listaProdutos.add(new Produto("TV 55' ", 0.400, 4346.99));
		listaProdutos.add(new Produto("TV 50' ", 0.290, 3999.90));
		listaProdutos.add(new Produto("TV 42' ", 0.200, 2999.00));
		listaProdutos.add(new Produto("Notebook Dell", 0.00350, 2499.90));
		listaProdutos.add(new Produto("Ventilador Panasonic", 0.496, 199.90));
		listaProdutos.add(new Produto("Microondas Eletrolux", 0.0424, 308.66));
		listaProdutos.add(new Produto("Microondas LG", 0.0544, 429.90));
		listaProdutos.add(new Produto("Microondass Panasonic", 0.0319, 299.29));
		listaProdutos.add(new Produto("Geladeira Brastemp", 0.635, 849.00));
		listaProdutos.add(new Produto("Geladeira Consul", 0.870, 1199.89));
		listaProdutos.add(new Produto("Notebook Lenovo", 0.498, 1999.90));
		listaProdutos.add(new Produto("Notebook Asus", 0.527, 3999.00));

		List espacos = new ArrayList<>();
		List valores = new ArrayList<>();
		List nomes = new ArrayList<>();
	    for (Produto produto: listaProdutos) {
	    	espacos.add(produto.getEspaco());
	    	valores.add(produto.getValor());
	    	nomes.add(produto.getNome());
	    }
	    Double limite = 3.0;
	    
	   int tamanhoPopulacao = 20;
	   AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanhoPopulacao);
	   ag.inicializaPopulacao(espacos, valores, limite);
	   for( Individuo individuo : ag.getPopulacao()) {
		   individuo.avaliacao();
	   }
	   for (int i = 0; i < ag.getTamanhoPopulacao(); i++) {
		   System.out.println("*** Individuo " + i + "***\n Espacos = " + 
	               ag.getPopulacao().get(i).getEspacos() + 
	               "\nValores = " + ag.getPopulacao().get(i).getValores() + 
	               "\nCromossomo = " + ag.getPopulacao().get(i).getCromossomo() + 
	               "\nNota = " + ag.getPopulacao().get(i).getNotaAvaliacao());
	   }
	}
}


