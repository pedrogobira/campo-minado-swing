package com.yofiel.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.yofiel.cm.modelo.Campo;
import com.yofiel.cm.modelo.CampoEvent;
import com.yofiel.cm.modelo.CampoObserver;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObserver, MouseListener {

//	ATRIBUTOS
	private static final Color BG_PADRAO = new Color(184, 184, 184);
	private static final Color BG_MARCAR = new Color(8, 179, 247);
	private static final Color BG_EXPLODIR = new Color(189, 66, 68);
	private static final Color TEXTO_VERDE = new Color(0, 100, 0);

	private Campo campo;

//	CONSTRUTOR
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));

		campo.addObserver(this);
		addMouseListener(this);
	}

//	METODOS
	@Override
	public void eventoOcorreu(Campo campo, CampoEvent evento) {
		switch (evento) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstiloPadrao();
		}
	}

	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void aplicarEstiloAbrir() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if (campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			return;
		}

		switch (campo.minasNaVizinhanca()) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}

		String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
		setText(valor);
	}

	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCAR);
		setForeground(Color.BLACK);
		setText("M");
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setForeground(Color.WHITE);
		setText("*");
	}

//	METODOS DA INTERFACE DOS EVENTOS DO MOUSE
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			campo.abrir();
		} else {
			campo.alternarMarcacao();
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
