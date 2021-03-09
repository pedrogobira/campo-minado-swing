package com.yofiel.cm.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class Tabuleiro implements CampoObserver {

//	ATRIBUTOS
	private final int linhas;
	private final int colunas;
	private final int minas;

	private final List<Campo> campos = new ArrayList<>();
	private final Set<Consumer<Boolean>> observers = new HashSet<>();

//	CONSTRUTOR
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}

//	GETTERS
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public int getMinas() {
		return minas;
	}

	public List<Campo> getCampos() {
		return campos;
	}

//	METODOS
	public void addObserver(Consumer<Boolean> observer) {
		observers.add(observer);
	}

	public void notificarObservers(boolean resultado) {
		observers.forEach(o -> o.accept(resultado));
	}

	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.addObserver(this);
				campos.add(campo);
			}
		}
	}

	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}

	private void sortearMinas() {
		long minasArmadas;

		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(c -> c.isMinado()).count();
		} while (minasArmadas < minas);
	}

	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}

	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}

	private void mostrarMinas() {
		campos.stream().filter(c -> c.isMinado()).filter(c -> !c.isMarcado()).forEach(c -> c.setAberto(true));
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvent evento) {
		if (evento == CampoEvent.EXPLODIR) {
			mostrarMinas();
			notificarObservers(false);
		} else if (objetivoAlcancado()) {
			notificarObservers(true);
		}
	}

}
