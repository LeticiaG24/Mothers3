package gui;

import java.util.ArrayList;
import java.util.List;

import dao.ServicoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Servico;

public class TelaServicos {
    private Stage stage;
    private String botaoServico;
    private ServicoDAO servicoDAO;
    
    private ObservableList<Servico> dadosServico;
    private TableView<Servico> tabelaServico;
    private Label lblTitulo;
    private Label lblTexto;
    
    public TelaServicos(String botaoServico) {
        this.botaoServico = botaoServico;
        this.servicoDAO = new ServicoDAO();
        this.stage = new Stage();
    }
    
    public void mostrar() {
        stage = new Stage();
        stage.setTitle("Nossos Serviços - " + botaoServico);
        
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.CENTER);
        content.getStyleClass().add("container-principal");
        
        carregarTitulo();
        carregarTexto();
        configurarTabelaServico();
        
        Button btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("btn-voltar");
        btnVoltar.setOnAction(e -> {
            Homepage.restaurarHomepage();
            stage.close();
        });
        
        HBox topContainer = new HBox(btnVoltar);
        topContainer.setAlignment(Pos.TOP_LEFT);
        topContainer.setPadding(new Insets(0, 0, 20, 0));
        
        Image servicesImg = new Image(getClass().getResource("/gui/images/servicos.png").toExternalForm());
        ImageView servicesImageView = new ImageView(servicesImg);
        servicesImageView.getStyleClass().add("servicesImageView");
        
        content.getChildren().addAll(topContainer, servicesImageView, lblTitulo, lblTexto, tabelaServico);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setPannable(true);  
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        content.setMinHeight(800);
        
        VBox root = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        Scene scene = new Scene(root, 1000, 700);
        
        scene.getStylesheets().add(getClass().getResource("/gui/TelaServicos.css").toExternalForm());
        
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
    }
    
    private void carregarTitulo() {
        lblTitulo = new Label(botaoServico);
        lblTitulo.getStyleClass().add("titulo-tela");
    }
    
    private void carregarTexto() {
        String texto = "";
        switch(botaoServico) {
            case "Início do encontro":
                texto = "Este é o início de cada encontro, onde tudo começa com amor e boas-vindas.\nAqui estão os serviços que tornam esse momento especial - a música, o sorriso, a oração -\npreparando o coração de cada mãe para viver a fé em comunhão.";
                break;
            case "Momento de fé":
                texto = "Neste momento, as mães se reúnem para aprender, refletir e fortalecer a fé em comunhão.\nÉ um tempo de escuta, oração e partilha, onde cada palavra toca o coração e renova a esperança.";
                break;
            case "Encerramento e oração":
                texto = "Aqui, o encontro se despede em clima de gratidão e entrega.\nEntre gestos de fé e devoção, cada mãe leva consigo a paz e o amor cultivados em oração.";
                break;
        }
        lblTexto = new Label(texto);
        lblTexto.getStyleClass().add("texto-descricao");
    }
    
    private void configurarTabelaServico() {
        tabelaServico = new TableView<>();
        carregarTabelaServico();
        
        TableColumn<Servico, String> colNome = new TableColumn<>("Serviço");
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        colNome.setPrefWidth(150);
        
        TableColumn<Servico, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescricao()));
        colDescricao.setPrefWidth(700);  
        
        tabelaServico.getColumns().addAll(colNome, colDescricao);
        tabelaServico.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        tabelaServico.setMinHeight(300);
        tabelaServico.setMaxHeight(Double.MAX_VALUE);
    }
    
    private void carregarTabelaServico() {
        List<Servico> todos = servicoDAO.listAll();
        
        List<String> inicio = List.of("Recepção das mães", "Música", "Acolhida", "Terço");
        List<String> momento = List.of("Formação", "Momento oracional", "Proclamação da vitória", "Sorteio das flores");
        List<String> encerramento = List.of("Encerramento", "Arrumação capela", "Queima dos pedidos", "Compra das flores");
        
        List<Servico> filtrada = new ArrayList<>();

        switch (botaoServico) {
            case "Início do encontro":
                for (Servico s : todos) {
                    if (inicio.contains(s.getNome())) {
                        filtrada.add(s);
                    }
                }
                break;
            case "Momento de fé":
                for (Servico s : todos) {
                    if (momento.contains(s.getNome())) {
                        filtrada.add(s);
                    }
                }
                break;
            case "Encerramento e oração":
                for (Servico s : todos) {
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