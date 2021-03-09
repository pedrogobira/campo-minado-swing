package com.yofiel.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.yofiel.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {

	public PainelTabuleiro(Tabuleiro tabuleiro) {

		setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));
		tabuleiro.getCampos().forEach(c -> add(new BotaoCampo(c)));

		tabuleiro.addObserver(e -> {
			SwingUtilities.invokeLater(() -> {
				if (e.booleanValue()) {
					JOptionPane.showMessageDialog(this, "Parabéns, você ganhou!");
				} else {
					JOptionPane.showMessageDialog(this, "Infelizmente, você perdeu...");
				}
				tabuleiro.reiniciar();
			});

		});
	}
}
