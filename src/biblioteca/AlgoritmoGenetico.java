package biblioteca;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.StockRandomGenerator;
import org.jgap.impl.SwappingMutationOperator;

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

class Avaliacao extends FitnessFunction {
	private final AlgoritmoGenetico algoritmoGenetico;
	
	public Avaliacao(AlgoritmoGenetico ag) {
		this.algoritmoGenetico = ag;
	}

	@Override
	protected double evaluate(IChromosome cromossomo) {
		Double nota = 0.0;
		Double somaEspacos = 0.0;
		for (int i = 0; i < cromossomo.size(); i++) {
			if (cromossomo.getGene(i).getAllele().toString().equals("1")) {
				nota += this.algoritmoGenetico.listaProdutos.get(i).getValor();
				somaEspacos += this.algoritmoGenetico.listaProdutos.get(i).getEspaco();			
		   }
	   }
	   if ( somaEspacos > this.algoritmoGenetico.limite) {
		   nota = 1.0;
	   }   
	   return nota;
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
	
	public IChromosome criarCromossomo() throws InvalidConfigurationException {
		Gene[] genes = new Gene[listaProdutos.size()];
		for (int i = 0; i < genes.length; i++) {
			genes[i] = new IntegerGene(configuracao, 0, 1);
			genes[i].setAllele(i);
		}
		IChromosome modelo = new Chromosome(configuracao, genes);
		return modelo;
	}
	
	public FitnessFunction criarFuncaoFitness() {
		return new Avaliacao(this);
	}
	
	public Configuration criarConfiguracao() throws InvalidConfigurationException {
		Configuration configuracao = new Configuration();
		configuracao.removeNaturalSelectors(true);
		
		configuracao.addNaturalSelector(new BestChromosomesSelector(configuracao, 0.4), false);
		configuracao.setRandomGenerator(new StockRandomGenerator());
		configuracao.addGeneticOperator(new CrossoverOperator(configuracao));
		configuracao.addGeneticOperator(new SwappingMutationOperator(configuracao, taxaMutacao));
		configuracao.setKeepPopulationSizeConstant(true);
		configuracao.setEventManager(new EventManager());
		configuracao.setFitnessEvaluator(new DefaultFitnessEvaluator());
		return configuracao;
	}
	
	public void procurarMelhorSolucao() throws InvalidConfigurationException {
		this.configuracao = criarConfiguracao();
		FitnessFunction funcaoFitness = criarFuncaoFitness();
		configuracao.setFitnessFunction(funcaoFitness);
		IChromosome modeloCromossomo = criarCromossomo();
		configuracao.setSampleChromosome(modeloCromossomo);
		configuracao.setPopulationSize(this.tamanhoPopulacao);
		IChromosome[] cromossomos = new IChromosome[tamanhoPopulacao];
		for (int i = 0; i < this.tamanhoPopulacao; i++) {
			cromossomos[i] = criarCromossomo();			
		}
		Genotype populacao = new Genotype(configuracao, new Population(configuracao, cromossomos));
		for (int j = 0; j < this.numeroGeracoes; j++) {
			visualizaGeracao(populacao.getFittestChromosome(), j);
			this.melhoresCromossomos.add(populacao.getFittestChromosome());
			populacao.evolve();
		}
	}
	
	public static void main(String args[]) throws InvalidConfigurationException {
		AlgoritmoGenetico ag = new AlgoritmoGenetico();
		ag.carregar();
		ag.procurarMelhorSolucao();
		
		int geracao = 0;
		for (int i = 0; i < ag.melhoresCromossomos.size(); i++) {
			if (ag.melhor == null) {
				ag.melhor = ag.melhoresCromossomos.get(i);
			} else if (ag.melhor.getFitnessValue() < ag.melhoresCromossomos.get(i).getFitnessValue()) {
				ag.melhor = ag.melhoresCromossomos.get(i);
				geracao = i;
			}
		}
		System.out.println("\nMelhor solucao");
		ag.visualizaGeracao(ag.melhor, geracao);
		
		for (int i = 0; i < ag.listaProdutos.size(); i++) {
			if (ag.melhor.getGene(i).getAllele().toString().equals("1")) {
				System.out.println("Nome: " + ag.listaProdutos.get(i).getNome());
			}
		}
		
		Grafico g = new Grafico("Algoritmo Genetico", "Evolucao das solucoes", ag.melhoresCromossomos);
		g.pack();
		RefineryUtilities.centerFrameOnScreen(g);
		g.setVisible(true);
	}
}
