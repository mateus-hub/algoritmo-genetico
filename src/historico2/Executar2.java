package historico2;

import java.util.ArrayList;
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

class Individuo {
    private List espacos = new ArrayList<>();
    private List valores = new ArrayList<>();
    private Double limiteEspacos;
    private Double notaAvaliacao;
    private int geracao;
    private List cromossomo = new ArrayList<>();
    
    public Individuo(List espacos, List valores, Double limiteEspacos) {
        this.espacos = espacos;
        this.valores = valores;
        this.limiteEspacos = limiteEspacos;
        this.notaAvaliacao = 0.0;
        this.geracao = 0;
        
        for (int i = 0; i < this.espacos.size(); i++) {
            if (java.lang.Math.random() < 0.5) {
                this.cromossomo.add("0");    
            } else {
                this.cromossomo.add("1");
            }
        }       
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
}

public class Executar2 {
    public static void main(String args[]) {
        List<Produto> listaProdutos = new ArrayList<>();
        listaProdutos.add(new Produto("Geladeira Dako", 0.751, 999.90));
        listaProdutos.add(new Produto("Iphone 6", 0.000089, 2911.12));
        listaProdutos.add(new Produto("TV 55' ", 0.400, 4346.99));
        listaProdutos.add(new Produto("TV 50' ", 0.290, 3999.90));
        listaProdutos.add(new Produto("TV 42' ", 0.200, 2999.00));
        listaProdutos.add(new Produto("Notebook Dell", 0.00350, 2499.90));
        listaProdutos.add(new Produto("Ventilador Panasonic", 0.496, 199.90));
        listaProdutos.add(new Produto("Microondas Electrolux", 0.0424, 308.66));
        listaProdutos.add(new Produto("Microondas LG", 0.0544, 429.90));
        listaProdutos.add(new Produto("Microondas Panasonic", 0.0319, 299.29));
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
        
        Individuo individuo1 = new Individuo(espacos, valores, limite);
        System.out.println("Espaços: " + individuo1.getEspacos());
        System.out.println("Valores: " + individuo1.getValores());
        System.out.println("Cromossomo: " + individuo1.getCromossomo());
        System.out.println("\nComponentes da carga");
        for (int i = 0; i < listaProdutos.size(); i++) {
            if (individuo1.getCromossomo().get(i) == "1") {
                System.out.println("Nome: " + listaProdutos.get(i).getNome()
                + " R$: " + listaProdutos.get(i).getValor());
            }
        }
    }
}


