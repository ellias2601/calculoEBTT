package br.tatuapu.model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;


public class JanelaCarreira extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JComboBox<String> boxCarreiras;
	JComboBox<String> boxTitulacao;
	JComboBox<String> boxHoras;
	JPanel painelSuperior;
	JPanel painelSuperior2;
	JPanel painelSuperior3;
	JLabel l2017;
	JLabel l2019;
	JLabel l2020;
	JTextField t2017;
	JTextField t2019;
	JTextField t2020;
	JMenuItem jExibirAjuda;
	JMenuItem jDesejaSair;
	JCheckBox checaAno;
	
	private List<Carreira> lista;

	private String classe;
	private String tipo;
	private String titulacao;
	private double teto2017 ;
	private double teto2019 ;
	private double teto2020 ;
	private double salario2017;
	private double salario2019;
	private double salario2020;
	private double previdencia2017;
	private double previdencia2019;
	private double previdencia2020;
	private double aumento;
	private double porcentagem;

	public JanelaCarreira() {

		super("Carreiras EBTT");
		setSize(1100, 160);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		painelSuperior = new JPanel(new BorderLayout());
		painelSuperior2 = new JPanel(new GridLayout(1,1,0,0));
		painelSuperior3 = new JPanel();
		
		JMenuBar menu = new JMenuBar();
		
		JMenu jmSair = new JMenu("Sair");
		JMenu jmAjuda = new JMenu("Ajuda");
		
		JMenuItem desejaSair= new JMenuItem("Deseja Sair?");
		jmSair.add(desejaSair);
		
		JMenuItem instrucoesUso = new JMenuItem("Instruções");
		jmAjuda.add(instrucoesUso);
		
		instrucoesUso.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		     JOptionPane.showMessageDialog(null, "Este Software permite que você calcule o valor de sua"
		    	+ " previdência para os anos de 2017, 2019 e 2020,\n a partir do tetos obtidos através de"
		    	+ " estimativas da inflação para os próximos anos, de acordo com o\n Banco Central. Estes"
		    	+ " já vem definidos por padrão nos campos de texto indicados. Caso necessário,\n você pode"
		    	+ " alterar o valor do teto para cada ano, basta apenas alterar os campos de texto referentes\n com os"
		    	+ " valores desejados.");
		    	
		    	
		    }
		});
		
		desejaSair.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		    	JOptionPane.showMessageDialog(null, "Obrigado por Utilizar Este Software!");
		    	System.exit(0);
		    	
		    	
		    }
		});
		
	
		
		menu.add(jmAjuda);
		menu.add(jmSair);
		
		painelSuperior.add(menu, "North");

		JLabel lCarreiras = new JLabel("Classe/Nível:");
		painelSuperior2.add(lCarreiras);

		boxCarreiras = new JComboBox<>();
		painelSuperior2.add(boxCarreiras);
		preencheComboBoxCarreiras();

		JLabel lHoras = new JLabel("Horas de Trabalho");
		painelSuperior.add(lHoras);

		boxHoras = new JComboBox<>();
		painelSuperior2.add(boxHoras);
		preencheComboBoxHoras();

		JLabel lTitulacao = new JLabel("Titulação");
		painelSuperior.add(lTitulacao);
		
		boxTitulacao = new JComboBox<>();

		painelSuperior2.add(boxTitulacao);
		
		checaAno = new JCheckBox("Ingresso Após 03/2013");
		painelSuperior3.add(checaAno);
		
		preencheComboBoxTitulacao();

		JButton bConsultar = new JButton("Consultar");
		painelSuperior2.add(bConsultar);
		
		l2017 = new JLabel("Teto 2017 R$");
		painelSuperior3.add(l2017);
		
		t2017 = new JTextField();
		painelSuperior3.add(t2017);
		
		l2019 = new JLabel("Teto 2019 R$");
		painelSuperior3.add(l2019);
		
		t2019 = new JTextField();
		painelSuperior3.add(t2019);
		
		l2020 = new JLabel("Teto 2020 R$");
		painelSuperior3.add(l2020);
		
		t2020 = new JTextField();
		painelSuperior3.add(t2020);
		
		painelSuperior.add("Center", painelSuperior2);
		painelSuperior.add("South", painelSuperior3);
		defineTetoPadrao();
		

		setContentPane(painelSuperior);

		bConsultar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				ArrayList<Carreira> dadosEncontrados = new ArrayList<Carreira>();

				classe = boxCarreiras.getSelectedItem().toString();

				tipo = boxHoras.getSelectedItem().toString();

				titulacao = boxTitulacao.getSelectedItem().toString();

				dadosEncontrados = encontraCarreira(classe, tipo, titulacao);

				salario2017 = (Double) dadosEncontrados.get(0).getSalario();
				salario2019 = (Double) dadosEncontrados.get(1).getSalario();
				salario2020 = (Double) dadosEncontrados.get(2).getSalario();

				try {

					recuperaTetoDefinido();
					calculaPrevidencia2017(salario2017);
					calculaPrevidencia2019(salario2019);
					calculaPrevidencia2020(salario2020);

					exibeMensagem();

				} catch (Exception e) {

					JOptionPane.showMessageDialog(null, "Por favor, informe o teto em R$");
				}

				System.out.println("Salario 2017 R$ " + salario2017);
				System.out.println("Salário 2018 R$ " + salario2019);
				System.out.println("Salário 2020 R$ " + salario2020);
				System.out.println(teto2017);
				System.out.println(teto2019);
				System.out.println(teto2020);
				
				

			}

		});

	}
	

	public void preencheComboBoxCarreiras() {

		boxCarreiras.addItem("D I - 1");
		boxCarreiras.addItem("D I - 2");
		boxCarreiras.addItem("D II - 1");
		boxCarreiras.addItem("D II - 2");
		boxCarreiras.addItem("D III - 1");
		boxCarreiras.addItem("D III - 2");
		boxCarreiras.addItem("D III - 3");
		boxCarreiras.addItem("D III - 4");
		boxCarreiras.addItem("D IV - 1");
		boxCarreiras.addItem("D IV - 2");
		boxCarreiras.addItem("D IV - 3");
		boxCarreiras.addItem("D IV - 4");
		boxCarreiras.addItem("TITULAR   - 1");

	}

	public void preencheComboBoxHoras() {

		boxHoras.addItem("20H");
		boxHoras.addItem("40H");
		boxHoras.addItem("DE");

	}

	public void preencheComboBoxTitulacao() {

		boxTitulacao.addItem("Graduação");
		boxTitulacao.addItem("Aperfeiçoamento");
		boxTitulacao.addItem("Especialização ou Graduação + RSC I");
		boxTitulacao.addItem("Mestrado ou Especialização + RSC II");
		boxTitulacao.addItem("Doutorado ou Mestrado + RSC III");

	}

	public void setData(List<Carreira> lista) {

		this.lista = lista;

	}
	
	public ArrayList<Carreira> encontraCarreira(String nome, String tipo, String titulacao) {
		
		
		ArrayList<Carreira> dadosCarreira = new ArrayList<Carreira>();
		
		for (Carreira c : this.lista) {

			String no = c.getClasse() + " - " + c.getNivel();
			String ti = c.getClasse() + "  - " + c.getNivel();

			if (boxCarreiras.getSelectedIndex() == 12) {

				if (ti.equals("TITULAR   - 1") && c.getTipo().equals(tipo) && c.getTitulacao().equals(titulacao)) {

					dadosCarreira.add(c);
				}
			}

			else {

				if (no.equals(nome) && c.getTipo().equals(tipo) && c.getTitulacao().equals(titulacao)) {

					dadosCarreira.add(c);

				}
			}

		}
		
		return dadosCarreira;
		
	}
	
	public void calculaPrevidencia2017(double salario2017) {

		if (salario2017 < teto2017) {

			previdencia2017 = 0.11 * salario2017;
		}

		else {

			if (checaAno.isSelected()) {

				previdencia2017 = (0.11 * teto2017);

			}

			else {

				previdencia2017 = (0.11 * teto2017) + (0.14 * (salario2017 - teto2017));
			}
		}
	}
	
	public void calculaPrevidencia2019(double salario2019) {

		if (salario2019 < teto2019) {

			previdencia2019 = 0.11 * salario2019;
		}

		else {

			if (checaAno.isSelected()) {

				previdencia2019 = (0.11 * teto2019);

			}

			else {

				previdencia2019 = (0.11 * teto2019) + (0.14 * (salario2019 - teto2019));
			}
		}
	}
	
	public void calculaPrevidencia2020(double salario2020) {

		if (salario2020 < teto2020) {

			previdencia2020 = 0.11 * salario2020;
		}

		else {

			if (checaAno.isSelected()) {

				previdencia2020 = (0.11 * teto2020);

			}

			else {

				previdencia2020 = (0.11 * teto2020) + (0.14 * (salario2020 - teto2020));
			}
		}

	}
	
	public void defineTetoPadrao() {
		
		t2017.setText("5531.31");
		t2019.setText("5953.77");
		t2020.setText("6203.83");
		
	}
	
	public void recuperaTetoDefinido() throws Exception{
		
		try {
			
		teto2017 = Double.parseDouble(t2017.getText());
		teto2019 = Double.parseDouble(t2019.getText());
		teto2020 = Double.parseDouble(t2020.getText());
		
		} catch(Exception e) {
			
		
			throw new Exception();
			
		}
		
	}
	
	public void exibeMensagem() {
		
		aumento = previdencia2020 - previdencia2017;
	    porcentagem = (aumento / previdencia2020) * 100;
		
		DecimalFormat df = new DecimalFormat("0.00");
		String formatPrevidencia2017 = df.format(previdencia2017);
		String formatPrevidencia2019 = df.format(previdencia2019);
		String formatPrevidencia2020 = df.format(previdencia2020);
		String formatAumento = df.format(aumento);
		String formatPorcentagem = df.format(porcentagem);
		
		
		JOptionPane.showMessageDialog(null, "Valor da Previdência: \n" + "Em 2017: R$ " + formatPrevidencia2017 + 
				"\nEm 2019: R$ " + formatPrevidencia2019 + "\nEm 2020: R$ " + formatPrevidencia2020 + "\n" + 
				"Do ano de 2017 para 2020 houve um aumento da contribuição de R$ " + formatAumento + 
				", o que corresponde a um aumento de " + formatPorcentagem +"%");
		
		
	}

	
}
