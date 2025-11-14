package util;

import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import model.Servico;
import model.Encontro;
import model.Mae;
import model.Rel;
import dao.MaeDAO;
import dao.ServicoDAO;
import dao.EncontroDAO;

public class TXTExporter {
	public void arquivo(int id_encontro, List<Rel> rel, String filePath) throws IOException {
		FileWriter arq = new FileWriter(filePath);
		BufferedWriter escrita = new BufferedWriter(arq);
		
		EncontroDAO encontroDAO = new EncontroDAO();
		Encontro encontro = encontroDAO.findById(id_encontro);
		
		escrita.write("Data do encontro: " + encontro.getData().format(DateTimeFormatter.ofPattern("dd/MM")));
		escrita.newLine();
		escrita.write("Serviços:");
		escrita.newLine();
		
		MaeDAO maeDAO = new MaeDAO();
		ServicoDAO servicoDAO = new ServicoDAO();
		
		rel = rel.stream()
				.filter(relEncontro -> relEncontro.getIdEncontro() == id_encontro)
				.collect(Collectors.toList());
		
		for(Rel r:rel) {
			Mae mae = maeDAO.findById(r.getIdMae());
			Servico servico = servicoDAO.findById(r.getIdServico());
			escrita.write(servico.getNome() + ": " + mae.getNome());
			escrita.newLine();
		}
		
		escrita.close();
	}
}
