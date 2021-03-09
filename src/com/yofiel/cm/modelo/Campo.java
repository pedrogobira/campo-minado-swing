/*
* Campo possui alguns eventos. Eventos de Campo sao identificados pelo enum CampoEvent.
* Os observers de Campo implementam a Interface CampoObserver.
* CampoObserver e uma FunctionalInterface BiConsumer.
* Tabuleiro vai ser um Observer por controlar os campos
*/

package com.yofiel.cm.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Campo {

//	ATRIBUTOS
	private final int linha;
	private final int coluna;

	private boolean aberto;
	private boolean minado;
	private boolean marcado;

	private List<Campo> vizinhos = new ArrayList<>();
	private Set<CampoObserver> observers = new HashSet<>();

//	CONSTRUTOR
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

//	SETTER E GETTERS
	void setAberto(boolean aberto) {
		this.aberto = aberto;

		if (aberto) {
			notificarObservers(CampoEvent.ABRIR);
		}
	}

	public boolean isMarcado() {
		return marcado;
	}

	public boolean isMinado() {
		return minado;
	}

	public boolean isAberto() {
		return aberto;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

//	METODOS
	public void addObserver(CampoObserver observer) {
		observers.add(observer);
	}

	private void notificarObservers(CampoEvent evento) {
		observers.forEach(o -> o.eventoOcorreu(this, evento));
	}

	boolean adicionarVizinho(Campo candidatoVizinho) {
		boolean linhaDiferente = linha != candidatoVizinho.linha;
		boolean colunaDiferente = coluna != candidatoVizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - candidatoVizinho.linha);
		int deltaColuna = Math.abs(coluna - candidatoVizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;

		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(candidatoVizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(candidatoVizinho);
			return true;
		} else {
			return false;
		}
	}

	public void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;

			if (marcado) {
				notificarObservers(CampoEvent.MARCAR);
			} else {
				notificarObservers(CampoEvent.DESMARCAR);
			}
		}
	}

	public boolean abrir() {
		if (!aberto && !marcado) {
			if (minado) {
				notificarObservers(CampoEvent.EXPLODIR);
				return true;
			}

			setAberto(true);

			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	void minar() {
		minado = true;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}

	public int minasNaVizinhanca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
	}

	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservers(CampoEvent.REINICIAR);
	}

}
