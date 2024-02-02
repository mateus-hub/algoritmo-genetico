package biblioteca;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;

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

class Grafico extends ApplicationFrame {
	private List<IChromosome> melhoresCromossomos = new ArrayList<>();
	
	public Grafico(String tituloJanela, String tituloGrafico, List melhores) {
		super(tituloJanela);
		this.melhoresCromossomos = melhores;
		JFreeChart graficoLinha = ChartFactory.createLineChart(tituloGrafico, 
				"Geração", "Valor", 
				carregarDados(),
				PlotOrientation.VERTICAL,
				true, true, false);
		ChartPanel janelaGrafico = new ChartPanel(graficoLinha);
		janelaGrafico.setPreferredSize(new java.awt.Dimension(800, 600));
		setContentPane(janelaGrafico);
	}
	
	private DefaultCategoryDataset carregarDados() {
		DefaultCategoryDataset dados = new DefaultCategoryDataset();
		for (int i = 0; i < melhoresCromossomos.size(); i++) {
			dados.addValue(melhoresCromossomos.get(i).getFitnessValue(), "Melhor solução", "" + i);
		}
		return dados;
	}
}

public class AlgoritmoGenetico {
	Configuration configuracao;
	int numeroGeracoes = 100;
	Double  limite = 3.0;
	int tamanhoPopulacao = 20;
	int taxaMutacao;
	
	List<IChromosome> melhoresCromossomos = new ArrayList<>();
	List<Produto> listaProdutos = new ArrayList<>();
	IChromosome melhor;
	
	public void carregar() {
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
	}
	
	public Double somaEspacos(IChromosome cromossomo) {
		Double soma = 0.0;
		for (int i = 0; i < cromossomo.size(); i++) {
			if (cromossomo.getGene(i).getAllele().toString().equals("1")) {
				soma += this.listaProdutos.get(i).getEspaco();
			}
		}
		return soma;
	}
	
	public void visualizaGeracao(IChromosome cromossomo, int geracao) {
		List lista = new ArrayList<>();
		Gene[] genes = cromossomo.getGenes();
		for (int i = 0; i < cromossomo.size(); i++) {
			lista.add(genes[i].getAllele().toString() + " ");
		}
		
		System.out.println("G: " + geracao 
				+ " Valor: " + cromossomo.getFitnessValue() 
				+ " Espaco: " + somaEspacos(cromossomo)
				+ " Cromossomo " + lista);
	}
}
