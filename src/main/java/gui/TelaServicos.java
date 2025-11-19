package gui;

import java.util.ArrayList;
import java.util.List;

import dao.ServicoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Servico;

public class TelaServicos {
	private Stage stage;
	private String botaoServico;
	private ServicoDAO servicoDAO;
	
	private ObservableList<Servico> dadosServico;
	private TableView<Servico> tabelaServico;
	private Label lblTexto;
	
	public TelaServicos(String botaoServico) {
		this.botaoServico=botaoServico;
		this.servicoDAO=new ServicoDAO();
		this.stage=new Stage();
	}
	
	public void mostrar() {
        stage = new Stage();
        stage.setTitle("Nossos Serviços");
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        
        carregarTexto();
        configurarTabelaServico();
        
        root.getChildren().addAll(lblTexto, tabelaServico);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
	}
	private void carregarTexto() {
		switch(botaoServico) {
		case "Início do encontro":
			lblTexto = new Label("Este é  o início de cada encontro, onde tudo começa com amor e boas-vindas.\n Aqui estão os serviços que tornam esse momento especial - a música, o sorriso, a oração - preparando o coração de cada mãe para viver a fé em comunhão.");
			break;
		case "Momento de fé":
			lblTexto = new Label("Neste momento, as mães se reúnem para aprender, refletir e fortalecer a fé em comunhão.\n É um tempo de escuta, oração e partilha, onde cada palavra toca o coração e ronova a esperança.");
			break;
		case "Encerramento e oração":
			lblTexto = new Label("Aqui, o encontro se despede em clima de gratidão e entrega.\n Entre gestos de fé e devoção, cada mãe leva consigo a paz e o amor cultivados em oração.");
			break;
		}
	}
	
	private void configurarTabelaServico() {
		tabelaServico = new TableView<>();
		carregarTabelaServico();
		
		TableColumn<Servico, String> colNome = new TableColumn<>("Serviço");
		colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
		
		TableColumn<Servico, String> colDescricao = new TableColumn<>("Descrição");
		colDescricao.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescricao()));
		
		tabelaServico.getColumns().addAll(colNome, colDescricao);
	}
	
	private void carregarTabelaServico() {
		List<Servico> todos = servicoDAO.listAll();
		
		List<String> inicio = List.of("Recepção das mães", "Música", "Acolhida", "Terço");
		List<String> momento = List.of("Formação", "Momento oracional", "Proclamação da vitória", "Sorteio das flores");
		List<String> encerramento = List.of("Encerramento", "Arrumação capela", "Queima dos pedidos", "Compra das flores");
		
		List<Servico> filtrada = new ArrayList<>();

		switch (botaoServico) {
		case "Início do encontro":
			for (Servico s:todos) {
				if (inicio.contains(s.getNome())) {
					filtrada.add(s);
				}
			}
			break;
		case "Momento de fé":
			for (Servico s:todos) {
				if (momento.contains(s.getNome())) {
					filtrada.add(s);
				}
			}
			break;
		case "Encerramento e oração":
			for (Servico s:todos) {
				if (encerramento.contains(s.getNome())) {
					filtrada.add(s);
				}
			}
			break;
		}
		
		dadosServico = FXCollections.observableArrayList(filtrada);
		tabelaServico.setItems(dadosServico);
	}
}
