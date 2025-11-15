package gui;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import dao.MaeDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import model.Mae;

public class Homepage extends Application {
	MaeDAO maeDAO = new MaeDAO();
	//Tabela mães
	private TableView<Mae> tabelaMaes;
	private ObservableList<Mae> dadosMaes;
	//Tabela mães aniversariantes
	private TableView<Mae> tabelaMaesNiver;
	private ObservableList<Mae> dadosMaesNiver;
	
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
        
        //Adicionando elementos a cena
        raiz.getChildren().addAll(tabelaMaes, tabelaMaesNiver);
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
	
	
	public static void main (String[]args) {
		launch(args);
	}
}
