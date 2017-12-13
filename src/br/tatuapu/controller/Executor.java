package br.tatuapu.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.tatuapu.model.Carreira;
import br.tatuapu.model.JanelaCarreira;
import br.tatuapu.reader.LeitorODS;

public class Executor {
	public static void main(String[] args) {
		
		LeitorODS leitor = new LeitorODS();
		
        File file = new File("dados/Tabela.ods");
        List<Carreira> lista = new ArrayList<>();
        lista = leitor.readODS(file);
        
        JanelaCarreira j = new JanelaCarreira();
        j.setVisible(true);
        
        j.setData(lista);
	}
}