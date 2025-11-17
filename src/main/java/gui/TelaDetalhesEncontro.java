package gui;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.MaeDAO;
import dao.RelDAO;
import dao.ServicoDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Encontro;
import model.Mae;
import model.Rel;
import model.Servico;
import util.TXTExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TelaDetalhesEncontro {
    
    private Stage stage;
    private Encontro encontro;
    private TXTExporter txtExporter;
    private MaeDAO maeDAO;
    private RelDAO relDAO;
    private ServicoDAO servicoDAO;
    
    // Tabela de serviços do encontro
    private TableView<Rel> tabelaRel;
    private ObservableList<Rel> dadosRel;
    
    
    public TelaDetalhesEncontro(Encontro encontro) {
        this.encontro = encontro;
        this.txtExporter = new TXTExporter();
        this.maeDAO = new MaeDAO();
        this.relDAO = new RelDAO();
        this.servicoDAO = new ServicoDAO();
    }
    
    public void mostrar() {
        stage = new Stage();
        stage.setTitle("Detalhes do Encontro");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        // Labels com informações do encontro
        Label lblData = new Label("Data: " + encontro.getData());
        Label lblStatus = new Label("Status: " + encontro.getStatus());
        
        // Configuração da tabela de relações (serviços x mães)
        configurarTabelaRel();
        
        // Botão para exportar relatório
        Button btnExportar = new Button("Exportar relatório");
        btnExportar.setOnAction(e -> exportarRelatorio());
        
        //Cadastrar relação
        ComboBox<Servico> cbServico = new ComboBox<>();
        cbServico.setEditable(true);
        cbServico.setItems(FXCollections.observableArrayList(servicoDAO.listAll()));
        cbServico.setPromptText("Selecione um serviço");
        cbServico.setPrefWidth(250);
        cbServico.getEditor().getStyleClass().add("input");
        cbServico.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(cbServico, Priority.ALWAYS);
		cbServico.setConverter(new StringConverter<Servico>() {
		    @Override
		    public String toString(Servico servico) {
		        return (servico == null) ? "" : servico.getNome();
		    }

		    @Override
		    public Servico fromString(String string) {
		        return cbServico.getItems()
		                .stream()
		                .filter(c -> c.getNome().equalsIgnoreCase(string))
		                .findFirst()
		                .orElse(null);
		    }
		});
		
		ComboBox<Mae> cbMae = new ComboBox<>();
		cbMae.setEditable(true);
		cbMae.setItems(FXCollections.observableArrayList(maeDAO.listAll()));
		cbMae.setPromptText("Selecione uma mãe");
		cbMae.setPrefWidth(250);
		cbMae.getEditor().getStyleClass().add("input");
		cbMae.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(cbMae, Priority.ALWAYS);
		cbMae.setConverter(new StringConverter<Mae>() {
		    @Override
		    public String toString(Mae mae) {
		        return (mae == null) ? "" : mae.getNome();
		    }

		    @Override
		    public Mae fromString(String string) {
		        return cbMae.getItems()
		                .stream()
		                .filter(c -> c.getNome().equalsIgnoreCase(string))
		                .findFirst()
		                .orElse(null);
		    }
		});
		
		Button btnCadastrar = new Button("Cadastrar");
		btnCadastrar.setOnAction(e -> {
            Servico servicoSelecionado = cbServico.getValue();
            Mae maeSelecionada = cbMae.getValue();

            Rel novo = new Rel(maeSelecionada.getId(), servicoSelecionado.getId(), encontro.getId());
            relDAO.insert(novo);
            carregarTabelaRel();
            limparCampos(cbServico, cbMae);
        });
        
        root.getChildren().addAll(lblData, lblStatus, tabelaRel, btnExportar, cbServico, cbMae, btnCadastrar);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    
    private void configurarTabelaRel() {
        tabelaRel = new TableView<>();
        carregarTabelaRel();
        
        // Coluna do serviço
        TableColumn<Rel, String> colServico = new TableColumn<>("Serviço");
        colServico.setCellValueFactory(c -> {
            int servicoId = c.getValue().getIdServico();
            Servico servico = servicoDAO.findById(servicoId);
            String servicoNome = servico.getNome();
            return new javafx.beans.property.SimpleStringProperty(servicoNome);
        });
        
        // Coluna da mãe responsável
        TableColumn<Rel, String> colMae = new TableColumn<>("Mãe responsável");
        colMae.setCellValueFactory(c -> {
            int maeId = c.getValue().getIdMae();
            Mae mae = maeDAO.findById(maeId);
            String maeNome = mae.getNome();
            return new javafx.beans.property.SimpleStringProperty(maeNome);
        });
        
        tabelaRel.getColumns().addAll(colServico, colMae);
    }
    
    private void carregarTabelaRel() {
    	List<Rel> todos = relDAO.listAll();
    	
        List<Rel> filtrada = todos.stream()
                .filter(rel -> rel.getIdEncontro() == encontro.getId())
                .collect(java.util.stream.Collectors.toList());
        
        dadosRel = FXCollections.observableArrayList(filtrada);
		tabelaRel.setItems(dadosRel);
    }
    
    private void exportarRelatorio() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Salvar relatório TXT");
        
        String fileName = "relatorio_" + 
            encontro.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".txt";
        fileChooser.setInitialFileName(fileName);
        
        File file = fileChooser.showSaveDialog(stage);
        if (file == null) {
            System.out.println("Operação cancelada pelo usuário.");
            return;
        }
        
        try {
            txtExporter.arquivo(encontro.getId(), file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void limparCampos(ComboBox<Servico> cbServico, ComboBox<Mae> cbMae) {
    	cbServico.setValue(null);
        cbServico.getEditor().clear();
        cbMae.setValue(null);
        cbMae.getEditor().clear();
    }
    
    public void fechar() {
        if (stage != null) {
            stage.close();
        }
    }
}