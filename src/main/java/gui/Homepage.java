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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        
        //Tabela próximos encontros
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
        
        
        //Adicionando elementos a cena
        raiz.getChildren().addAll(tabelaMaes, tabelaMaesNiver, tabelaEncontros);
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
	private void carregarTabelaEncontros() {
		List<Encontro> todos = encontroDAO.listAll();
		
		List<Encontro> filtrada = todos.stream()
			    .filter(objeto -> objeto.getData().isAfter(LocalDate.now()))
			    .collect(Collectors.toList());
		
		dadosEncontros = FXCollections.observableArrayList(filtrada);
		tabelaEncontros.setItems(dadosEncontros);
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
