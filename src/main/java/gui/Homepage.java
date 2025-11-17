package gui;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import dao.EncontroDAO;
import dao.MaeDAO;
import dao.RelDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Encontro;
import model.Mae;

public class Homepage extends Application {
	MaeDAO maeDAO = new MaeDAO();
	EncontroDAO encontroDAO = new EncontroDAO();
	RelDAO relDAO = new RelDAO();
	
	//Tabela mães
	private TableView<Mae> tabelaMaes;
	private ObservableList<Mae> dadosMaes;
	//Tabela mães aniversariantes
	private TableView<Mae> tabelaMaesNiver;
	private ObservableList<Mae> dadosMaesNiver;
	//Tabela próximos encontros
	private TableView<Encontro> tabelaEncontros;
	private ObservableList<Encontro> dadosEncontros;
	//Tabela encontros anteriores
	private TableView<Encontro> tabelaEncontrosAnt;
	private ObservableList<Encontro> dadosEncontrosAnt;
	
	public void start(Stage stage) {
		
		//Criando a cena
		VBox raiz = new VBox();
		Scene cena = new Scene(raiz, 800, 600);
		stage.setTitle("Homepage");
        stage.setScene(cena);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
		
        //Tabela mães
        tabelaMaes = new TableView<>();
        carregarTabelaMaes();
        TableColumn<Mae, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        
        TableColumn<Mae, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTelefone()));
        
        TableColumn<Mae, String> colAniversario = new TableColumn<>("Aniversário");
        colAniversario.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDataAniversario().toString()));
        
        TableColumn<Mae, String> colEndereco = new TableColumn<>("Endereço");
        colEndereco.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEndereco()));
        
        tabelaMaes.getColumns().addAll(colNome, colTelefone, colAniversario, colEndereco);
        
        //Tabela mães aniversariantes
        tabelaMaesNiver = new TableView<>();
        carregarTabelaMaesNiver();
        TableColumn<Mae, String> colNomeA = new TableColumn<>("Nome");
        colNomeA.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        
        TableColumn<Mae, String> colTelefoneA = new TableColumn<>("Telefone");
        colTelefoneA.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTelefone()));
        
        TableColumn<Mae, String> colAniversarioA = new TableColumn<>("Aniversário");
        colAniversarioA.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDataAniversario().toString()));
        
        TableColumn<Mae, String> colEnderecoA = new TableColumn<>("Endereço");
        colEnderecoA.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEndereco()));
        
        tabelaMaesNiver.getColumns().addAll(colNomeA, colTelefoneA, colAniversarioA, colEnderecoA);
        
        //Cadastro de mães
        TextField tfNome = new TextField();
        tfNome.setPromptText("Nome");
        tfNome.setPrefWidth(250);
        tfNome.getStyleClass().add("input");
        tfNome.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(tfNome, Priority.ALWAYS);
		
		TextField tfEndereco = new TextField();
        tfEndereco.setPromptText("Endereço");
        tfEndereco.setPrefWidth(250);
        tfEndereco.getStyleClass().add("input");
        tfEndereco.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(tfEndereco, Priority.ALWAYS);
		
		TextField tfTelefone = new TextField();
        tfTelefone.setPromptText("Telefone");
        tfTelefone.setPrefWidth(250);
        tfTelefone.getStyleClass().add("input");
        tfTelefone.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(tfTelefone, Priority.ALWAYS);
		
		DatePicker dpAniversario = new DatePicker();
        dpAniversario.getStyleClass().add("input");
        dpAniversario.setPromptText("Data de aniversário");
        dpAniversario.setPrefWidth(250);
        dpAniversario.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(dpAniversario, Priority.ALWAYS);
        
		Button btnSalvarMae = new Button("Cadastrar");
		btnSalvarMae.setOnAction(e -> {
			Mae maeNova = new Mae(0, tfNome.getText(), tfTelefone.getText(), tfEndereco.getText(), dpAniversario.getValue());
			maeDAO.insert(maeNova);
			carregarTabelaMaes();
			carregarTabelaMaesNiver();
			limparCamposMaes(tfNome, tfTelefone, tfEndereco, dpAniversario);
		});
		
        //Tabela próximos encontros
		Label lblProximosEncontros = new Label("Nossos próximos encontros");
		
        tabelaEncontros = new TableView();
        carregarTabelaEncontros();
        TableColumn<Encontro, String> colDataEncontro = new TableColumn<>("Data");
        colDataEncontro.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getData().toString()));
        
        TableColumn<Encontro, String> colStatusEncontro = new TableColumn<>("Status");
        colStatusEncontro.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));
        
        TableColumn<Encontro, Void> colAcoes = new TableColumn<>("Editar");
        Callback<TableColumn<Encontro, Void>, TableCell<Encontro, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Encontro, Void> call(final TableColumn<Encontro, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("✏");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Encontro encontro = getTableView().getItems().get(getIndex());
                            abrirTelaDetalhes(encontro);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        colAcoes.setCellFactory(cellFactory);
        tabelaEncontros.getColumns().addAll(colDataEncontro, colStatusEncontro, colAcoes);
        
        Label lblEncontrosAnt = new Label("Nossos encontros anteriores");
        
        tabelaEncontrosAnt = new TableView<Encontro>();
        carregarTabelaEncontrosAnt();
        TableColumn<Encontro, String> colDataEncontroAnt = new TableColumn<>("Data");
        colDataEncontroAnt.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getData().toString()));
        
        TableColumn<Encontro, String> colStatusEncontroAnt = new TableColumn<>("Status");
        colStatusEncontroAnt.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));
        
        tabelaEncontrosAnt.getColumns().addAll(colDataEncontroAnt, colStatusEncontroAnt);
        
        //Cadastro encontros
        DatePicker dpData = new DatePicker();
        dpData.getStyleClass().add("input");
        dpData.setPromptText("Data");
        dpData.setPrefWidth(250);
        dpData.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(dpData, Priority.ALWAYS);
		
		ComboBox<String> cbStatus = new ComboBox<>();
		ObservableList<String> options = FXCollections.observableArrayList(
				"Em breve", "Concluído", "Cancelado");
		cbStatus.setItems(options);
		cbStatus.getStyleClass().add("input");
        cbStatus.setPromptText("Status");
        cbStatus.setPrefWidth(250);
        cbStatus.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(cbStatus, Priority.ALWAYS);
		
        Button btnSalvarEncontro = new Button("Cadastrar");
        btnSalvarEncontro.setOnAction(e -> {
			Encontro encontro = new Encontro(0, dpData.getValue(), cbStatus.getValue());
			encontroDAO.insert(encontro);
			carregarTabelaEncontros();
			limparCamposEvento(dpData, cbStatus);
		});
		
        //Adicionando elementos a cena
        raiz.getChildren().addAll(tabelaMaes, tabelaMaesNiver, tfNome, tfEndereco, tfTelefone, dpAniversario, btnSalvarMae, lblProximosEncontros, tabelaEncontros, lblEncontrosAnt, tabelaEncontrosAnt, dpData, cbStatus, btnSalvarEncontro);
	}
	
	
	private void carregarTabelaMaes() {
        List<Mae> todos = maeDAO.listAll();
        dadosMaes = FXCollections.observableArrayList(todos);
        tabelaMaes.setItems(dadosMaes);
    }
	private void carregarTabelaMaesNiver() {
        List<Mae> todos = maeDAO.listAll();
        Month mesAtual = LocalDate.now().getMonth();
        
        List<Mae> filtrada = todos.stream()
                .filter(obj -> obj.getDataAniversario() != null)
                .filter(obj -> obj.getDataAniversario().getMonth() == mesAtual)
                .collect(Collectors.toList());
        
        dadosMaesNiver = FXCollections.observableArrayList(filtrada);
        tabelaMaesNiver.setItems(dadosMaesNiver);
    }
	private void limparCamposMaes(TextField tfNome, TextField tfTelefone, TextField tfEndereco, DatePicker dpAniversario) {
		tfNome.clear();
		tfTelefone.clear();
		tfEndereco.clear();
		dpAniversario.setValue(null);
	}
	private void carregarTabelaEncontros() {
		List<Encontro> todos = encontroDAO.listAll();
		
		List<Encontro> filtrada = todos.stream()
			    .filter(objeto -> objeto.getData().isAfter(LocalDate.now()))
			    .collect(Collectors.toList());
		
		dadosEncontros = FXCollections.observableArrayList(filtrada);
		tabelaEncontros.setItems(dadosEncontros);
	}
	private void carregarTabelaEncontrosAnt() {
		List<Encontro> todos = encontroDAO.listAll();
		
		List<Encontro> filtrada = todos.stream()
				.filter(o -> o.getData().isBefore(LocalDate.now()))
				.collect(Collectors.toList());
		
		dadosEncontrosAnt = FXCollections.observableArrayList(filtrada);
		tabelaEncontrosAnt.setItems(dadosEncontrosAnt);
	}
	
	private void limparCamposEvento(DatePicker dpData, ComboBox<String> cbStatus) {
		dpData.setValue(null);
		cbStatus.setValue(null);
	}
	
	//Janela encontros
	private void abrirTelaDetalhes(Encontro encontro) {
		TelaDetalhesEncontro telaDetalhes = new TelaDetalhesEncontro(encontro);
	    telaDetalhes.mostrar();
	}
	
	public static void main (String[]args) {
		launch(args);
	}
}
