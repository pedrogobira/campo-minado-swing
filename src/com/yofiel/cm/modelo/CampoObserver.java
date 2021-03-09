package com.yofiel.cm.modelo;

@FunctionalInterface
public interface CampoObserver {
//	ESSA INTERFACE PODERIA SER SUBSTITUIDA PELA INTERFACE BICONSUMER DO PROPRIO JAVA
	public void eventoOcorreu(Campo campo, CampoEvent evento);
}
