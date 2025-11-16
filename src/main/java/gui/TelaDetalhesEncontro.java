package gui;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import dao.MaeDAO;
import dao.RelDAO;
import dao.ServicoDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
        
        root.getChildren().addAll(lblData, lblStatus, tabelaRel, btnExportar);

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
        // Filtra as relações pelo ID do encontro
        dadosRel = FXCollections.observableArrayList(
            relDAO.listAll().stream()
                .filter(rel -> rel.getIdEncontro() == encontro.getId())
                .collect(java.util.stream.Collectors.toList())
        );
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
    
    public void fechar() {
        if (stage != null) {
            stage.close();
        }
    }
}