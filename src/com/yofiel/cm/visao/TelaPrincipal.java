package com.yofiel.cm.visao;

import javax.swing.JFrame;

import com.yofiel.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {

	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, 50);
		PainelTabuleiro painelTabuleiro = new PainelTabuleiro(tabuleiro);

		add(painelTabuleiro);

		setTitle("Campo Minado");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {

		new TelaPrincipal();
	}
}
