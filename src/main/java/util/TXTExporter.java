package util;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.Servico;
import model.Mae;
import model.Rel;
import dao.MaeDAO;
import dao.ServicoDAO;

public class TXTExporter {
	public void arquivo(LocalDate dataEncontro, List<Rel> rel, String filePath) throws IOException {
		FileWriter arq = new FileWriter(filePath);
		BufferedWriter escrita = new BufferedWriter(arq);
		
		escrita.write("Data do encontro: " + dataEncontro.format(DateTimeFormatter.ofPattern("dd/MM")));
		escrita.newLine();
		escrita.write("Serviços:");
		escrita.newLine();
		
		MaeDAO maeDAO = new MaeDAO();
		ServicoDAO servicoDAO = new ServicoDAO();
		
		for(Rel r:rel) {
			Mae mae = maeDAO.findById(r.getIdMae());
			Servico servico = servicoDAO.findById(r.getIdServico());
			escrita.write(servico.getNome() + ": " + mae.getNome());
			escrita.newLine();
		}
		
		escrita.close();
	}
}
