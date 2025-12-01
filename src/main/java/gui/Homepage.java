package gui;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import dao.EncontroDAO;
import dao.MaeDAO;
import dao.RelDAO;
import gui.relatorios.GeradorRelatorio;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Encontro;
import model.Mae;

public class Homepage extends Application {
    private static Stage primaryStage;
    
    MaeDAO maeDAO = new MaeDAO();
    EncontroDAO encontroDAO = new EncontroDAO();
    RelDAO relDAO = new RelDAO();
    
    private TableView<Mae> tabelaMaes;
    private ObservableList<Mae> dadosMaes;
    
    private TableView<Mae> tabelaMaesNiver;
    private ObservableList<Mae> dadosMaesNiver;
    
    private TableView<Encontro> tabelaEncontros;
    private ObservableList<Encontro> dadosEncontros;
    
    private TableView<Encontro> tabelaEncontrosAnt;
    private ObservableList<Encontro> dadosEncontrosAnt;
    
    public void start(Stage stage) {
        primaryStage = stage;
        
        Button motherBtn = new Button("Mães");
        motherBtn.setId("motherBtn");
        
        Button datesBtn = new Button("Encontros");
        datesBtn.setId("datesBtn");
        
        Button servicesBtn = new Button("Serviços");
        servicesBtn.setId("servicesBtn");
        
        Button reportsBtn = new Button("Relatório");
        reportsBtn.setId("reportsBtn");
        
        Button logoutBtn = new Button("Sair");
        logoutBtn.setId("logoutBtn");
        logoutBtn.setOnAction(e -> {
            Stage logout = (Stage) logoutBtn.getScene().getWindow();
            logout.close();
        });
        
        HBox navBar = new HBox(motherBtn, datesBtn, servicesBtn, reportsBtn, logoutBtn);
        navBar.getStyleClass().add("navBar");
        
        Image headerImg = new Image(getClass().getResource("/gui/images/header.png").toExternalForm());
        ImageView headerImageView = new ImageView(headerImg);
        headerImageView.getStyleClass().add("headerImageView");
        
        Text headerText = new Text("Bem-vinda ao nosso grupo de encontros!");
        headerText.setId("headerText");
        
        Text parte1 = new Text("O sistema ");
        Text parteDestaque = new Text("Mães que Oram pelos Filhos");
        parteDestaque.getStyleClass().add("parteDestaque");
        Text parte2 = new Text(" nasceu para acolher e organizar\r\ncom carinho os encontros desse grupo de fé.");

        TextFlow introText1 = new TextFlow(parte1, parteDestaque, parte2);
        introText1.setId("introText1");
        introText1.setTextAlignment(TextAlignment.LEFT);
        introText1.setPrefWidth(600);
        introText1.setMaxWidth(600);
        introText1.setLineSpacing(2);

        Text parte3 = new Text("Aqui, cada oração, serviço e momento especial é registrado com cuidado, \r\nfortalecendo a união e a missão de cada mãe.");
        TextFlow introText2Flow = new TextFlow(parte3);
        introText2Flow.setId("introText2");
        introText2Flow.setTextAlignment(TextAlignment.LEFT);
        introText2Flow.setPrefWidth(600);
        introText2Flow.setMaxWidth(600);

        VBox introTextContainer = new VBox(10, introText1, introText2Flow);
        introTextContainer.setAlignment(Pos.CENTER);
        introTextContainer.getStyleClass().add("introTextContainer");
        
        Label servicesText = new Label("Nossos Serviços");
        servicesText.getStyleClass().add("servicesText");
        
        HBox servicesTextContainer = new HBox();
        servicesTextContainer.getStyleClass().add("servicesTextContainer");
        servicesTextContainer.setAlignment(Pos.CENTER);
        servicesTextContainer.getChildren().add(servicesText);
        
        Image begginingImg = new Image(getClass().getResource("/gui/images/acolhida.png").toExternalForm());
        ImageView begginingImageView = new ImageView(begginingImg);
        
        Image middleImg = new Image(getClass().getResource("/gui/images/formacao.png").toExternalForm());
        ImageView middleImageView = new ImageView(middleImg);
        
        Image endImg = new Image(getClass().getResource("/gui/images/encerramento.png").toExternalForm());
        ImageView endImageView = new ImageView(endImg);
        
        Text begginingText = new Text("Acolhida e Preparação");
        begginingText.setId("begginingText");
        
        Text middleText = new Text("Formação e Partilha");
        middleText.setId("middleText");
        
        Text endText = new Text("Encerramento e Devoção");
        endText.setId("endText");
        
        Button begginingBtn = new Button("Início do Encontro");
        begginingBtn.setId("begginingBtn");
        begginingBtn.setOnAction((ActionEvent event) -> {
            abrirTelaServicos("Início do encontro");
        });
        
        Button middleBtn = new Button("Momento de Fé");
        middleBtn.setId("middleBtn");
        middleBtn.setOnAction((ActionEvent event) -> {
            abrirTelaServicos("Momento de fé");
        });
        
        Button endBtn = new Button("Encerramento e Oração");
        endBtn.setId("endBtn");
        endBtn.setOnAction((ActionEvent event) -> {
            abrirTelaServicos("Encerramento e Oração");
        });
        
        VBox begginingService = new VBox(10, begginingImageView, begginingText, begginingBtn);
        begginingService.setAlignment(Pos.CENTER);
        begginingService.getStyleClass().add("serviceVBox");

        VBox middleService = new VBox(10, middleImageView, middleText, middleBtn);
        middleService.setAlignment(Pos.CENTER);
        middleService.getStyleClass().add("serviceVBox");
        
        VBox endService = new VBox(10, endImageView, endText, endBtn);
        endService.setAlignment(Pos.CENTER);
        endService.getStyleClass().add("serviceVBox");
        
        HBox servicesRow = new HBox(200, begginingService, middleService, endService);
        servicesRow.setAlignment(Pos.CENTER);
        servicesRow.setPadding(new Insets(10));
        
        VBox servicesContainer = new VBox(servicesRow);
        servicesContainer.getStyleClass().add("servicesContainer");
        
        headerImageView.setPreserveRatio(true);
        headerImageView.setSmooth(true);
        headerImageView.setCache(true);
        
        StackPane headerStack = new StackPane();
        headerStack.getChildren().addAll(headerImageView, headerText);
        
        Text mothersText = new Text("Nossas Mães");
        mothersText.setId("mothersText");
        
        Label mothersTableText = new Label("Nossas mães");
        mothersTableText.setId("mothersTableText");
        
        tabelaMaes = new TableView<>();
        carregarTabelaMaes();
        TableColumn<Mae, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        colNome.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Mae, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTelefone()));
        colTelefone.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Mae, String> colAniversario = new TableColumn<>("Aniversário");
        colAniversario.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDataAniversario().toString()));
        colAniversario.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Mae, String> colEndereco = new TableColumn<>("Endereço");
        colEndereco.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEndereco()));
        colEndereco.setStyle("-fx-alignment: CENTER;");
        
        tabelaMaes.getColumns().addAll(colNome, colTelefone, colAniversario, colEndereco);
        
        Label mothersBirthdays = new Label("Aniversáriantes do mês");
        mothersBirthdays.setId("mothersBirthdays");
        
        tabelaMaesNiver = new TableView<>();
        carregarTabelaMaesNiver();
        TableColumn<Mae, String> colNomeA = new TableColumn<>("Nome");
        colNomeA.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        colNomeA.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Mae, String> colTelefoneA = new TableColumn<>("Telefone");
        colTelefoneA.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTelefone()));
        colTelefoneA.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Mae, String> colAniversarioA = new TableColumn<>("Aniversário");
        colAniversarioA.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDataAniversario().toString()));
        colAniversarioA.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Mae, String> colEnderecoA = new TableColumn<>("Endereço");
        colEnderecoA.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEndereco()));
        colEnderecoA.setStyle("-fx-alignment: CENTER;");
        
        tabelaMaesNiver.getColumns().addAll(colNomeA, colTelefoneA, colAniversarioA, colEnderecoA);

        tabelaMaes.getStyleClass().add("maes-table");
        tabelaMaesNiver.getStyleClass().add("maes-table");
        
        Label addMotherText = new Label("Adicionar Mãe ao Círculo de Fé?");
        addMotherText.getStyleClass().add("addMotherText");
        
        TextField tfNome = new TextField();
        tfNome.setPromptText("Nome");
        tfNome.setPrefWidth(450);
        tfNome.getStyleClass().add("input");
        tfNome.setMaxWidth(Double.MAX_VALUE);

        TextField tfEndereco = new TextField();
        tfEndereco.setPromptText("Endereço");
        tfEndereco.setPrefWidth(450);
        tfEndereco.getStyleClass().add("input");
        tfEndereco.setMaxWidth(Double.MAX_VALUE);

        TextField tfTelefone = new TextField();
        tfTelefone.setPromptText("Telefone");
        tfTelefone.setPrefWidth(450);
        tfTelefone.getStyleClass().add("input");
        tfTelefone.setMaxWidth(Double.MAX_VALUE);

        DatePicker dpAniversario = new DatePicker();
        dpAniversario.getStyleClass().add("input");
        dpAniversario.setPromptText("Data de aniversário");
        dpAniversario.setPrefWidth(450);
        dpAniversario.setMaxWidth(Double.MAX_VALUE);
        
        
        Button btnSalvarMae = new Button("Cadastrar");
        btnSalvarMae.setId("btnSalvarMae");
        btnSalvarMae.setOnAction(e -> {
            Mae maeNova = new Mae(0, tfNome.getText(), tfTelefone.getText(), tfEndereco.getText(), dpAniversario.getValue());
            maeDAO.insert(maeNova);
            carregarTabelaMaes();
            carregarTabelaMaesNiver();
            limparCamposMaes(tfNome, tfTelefone, tfEndereco, dpAniversario);
        });
        
        HBox buttonMaeContainer = new HBox(btnSalvarMae);
        buttonMaeContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonMaeContainer.setPadding(new Insets(10, 0, 0, 0));
        HBox.setHgrow(buttonMaeContainer, Priority.ALWAYS);
        
        HBox MaesForm1 = new HBox(30, tfNome, tfTelefone);
        MaesForm1.setAlignment(Pos.CENTER);
        MaesForm1.setPadding(new Insets(15));
        MaesForm1.getStyleClass().add("form-row");

        HBox MaesForm2 = new HBox(30, tfEndereco, dpAniversario);
        MaesForm2.setAlignment(Pos.CENTER);
        MaesForm2.setPadding(new Insets(15));
        MaesForm2.getStyleClass().add("form-row");

        VBox cadastroMaesForm = new VBox(30, MaesForm1, MaesForm2, buttonMaeContainer);
        cadastroMaesForm.setAlignment(Pos.CENTER);
        cadastroMaesForm.setPadding(new Insets(20));
        cadastroMaesForm.getStyleClass().add("form-container");
        
        VBox maesSection = new VBox(15, 
            mothersText, 
            mothersTableText, 
            tabelaMaes,
            mothersBirthdays, 
            tabelaMaesNiver,
            addMotherText,
            cadastroMaesForm
        );
        maesSection.setAlignment(Pos.CENTER);
        maesSection.setPadding(new Insets(20, 0, 20, 0));
        
        Text datesText = new Text("Nossos Encontros");
        datesText.setId("datesText");

        HBox datesTextContainer = new HBox(datesText);
        datesTextContainer.setAlignment(Pos.CENTER);
        datesTextContainer.setPadding(new Insets(20, 0, 10, 0));
        
        Label datesIntroText = new Label("Os encontros são o coração do nosso grupo - momentos de oração, partilha e fé \r\nvividos com amor.");
        datesIntroText.setAlignment(Pos.CENTER);
        datesIntroText.getStyleClass().add("datesIntroText");

        Label lblProximosEncontros = new Label("Nossos próximos encontros");
        lblProximosEncontros.getStyleClass().addAll("section-label", "left-aligned-label");
        
        tabelaEncontros = new TableView();
        carregarTabelaEncontros();
        
        tabelaEncontros.setMinWidth(1200);
        tabelaEncontros.setMaxWidth(1200);
        tabelaEncontros.setPrefWidth(1200);
        
        TableColumn<Encontro, String> colDataEncontro = new TableColumn<>("Data");
        colDataEncontro.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getData().toString()));
        colDataEncontro.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Encontro, String> colStatusEncontro = new TableColumn<>("Status");
        colStatusEncontro.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));
        colStatusEncontro.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Encontro, Void> colAcoes = new TableColumn<>("Editar");
        colAcoes.setStyle("-fx-alignment: CENTER;");
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
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        };
        colAcoes.setCellFactory(cellFactory);
        tabelaEncontros.getColumns().addAll(colDataEncontro, colStatusEncontro, colAcoes);
        
        Label lblEncontrosAnt = new Label("Nossos encontros anteriores");
        lblEncontrosAnt.getStyleClass().addAll("section-label", "left-aligned-label");
        
        tabelaEncontrosAnt = new TableView<Encontro>();
        carregarTabelaEncontrosAnt();
        
        tabelaEncontrosAnt.setMinWidth(1200);
        tabelaEncontrosAnt.setMaxWidth(1200);
        tabelaEncontrosAnt.setPrefWidth(1200);
        
        TableColumn<Encontro, String> colDataEncontroAnt = new TableColumn<>("Data");
        colDataEncontroAnt.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getData().toString()));
        colDataEncontroAnt.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Encontro, String> colStatusEncontroAnt = new TableColumn<>("Status");
        colStatusEncontroAnt.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));
        colStatusEncontroAnt.setStyle("-fx-alignment: CENTER;");
        
        tabelaEncontrosAnt.getColumns().addAll(colDataEncontroAnt, colStatusEncontroAnt);
        
        tabelaEncontros.getStyleClass().add("encontros-table");
        tabelaEncontrosAnt.getStyleClass().add("encontros-table");
        
        Label lblCadastroEncontro = new Label("Cadastrar Novo Encontro");
        lblCadastroEncontro.getStyleClass().addAll("section-label", "left-aligned-label");
        
        DatePicker dpData = new DatePicker();
        dpData.getStyleClass().add("input");
        dpData.getStyleClass().add("encontro-date-picker");
        dpData.setPromptText("Data");
        dpData.setPrefWidth(300);
        dpData.setMaxWidth(Double.MAX_VALUE);
        
        ComboBox<String> cbStatus = new ComboBox<>();
        ObservableList<String> options = FXCollections.observableArrayList(
                "Em breve", "Concluído", "Cancelado");
        cbStatus.setItems(options);
        cbStatus.getStyleClass().add("input");
        cbStatus.setPromptText("Status");
        cbStatus.setPrefWidth(300);
        cbStatus.setMaxWidth(Double.MAX_VALUE);
        
        Button btnSalvarEncontro = new Button("Cadastrar Encontro");
        btnSalvarEncontro.setId("btnSalvarEncontro");
        btnSalvarEncontro.setOnAction(e -> {
            Encontro encontro = new Encontro(0, dpData.getValue(), cbStatus.getValue());
            encontroDAO.insert(encontro);
            carregarTabelaEncontros();
            carregarTabelaEncontrosAnt();
            limparCamposEvento(dpData, cbStatus);
        });
        
        HBox encontrosForm = new HBox(30, dpData, cbStatus);
        encontrosForm.setAlignment(Pos.CENTER);
        encontrosForm.setPadding(new Insets(15));
        encontrosForm.getStyleClass().add("form-row");

        HBox buttonContainer = new HBox(btnSalvarEncontro);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        HBox.setHgrow(buttonContainer, Priority.ALWAYS);

        VBox cadastroEncontrosForm = new VBox(15, lblCadastroEncontro, encontrosForm, buttonContainer);
        cadastroEncontrosForm.setAlignment(Pos.CENTER);
        cadastroEncontrosForm.setPadding(new Insets(20));
        cadastroEncontrosForm.getStyleClass().add("form-container");

        VBox encontrosContent = new VBox(20,
            datesTextContainer,
            datesIntroText,
            lblProximosEncontros,
            tabelaEncontros,
            lblEncontrosAnt,
            tabelaEncontrosAnt,
            cadastroEncontrosForm
        );
        encontrosContent.setAlignment(Pos.CENTER);
        encontrosContent.getStyleClass().add("content-container");
        VBox encontrosSection = new VBox(encontrosContent);
        encontrosSection.setAlignment(Pos.CENTER);
        encontrosSection.setPadding(new Insets(20, 0, 20, 0));
        encontrosSection.getStyleClass().add("encontrosSection");
        
        Text reportsIntroText = new Text("Gerar Relatório de Encontro");
        reportsIntroText.setId("reportsIntroText");

        Text reportsText1 = new Text("Cada encontro é um capítulo especial da nossa caminhada em fé.");
        reportsText1.setId("reportsText1");

        Text reportsText2 = new Text("Aqui você pode resgistrar e guardar, com carinho, os serviços e as mães que \r\nfizeram parte desse momento abençoado.");
        reportsText2.setId("reportsText2");

        TextFlow reportsTextFlow1 = new TextFlow(reportsText1);
        reportsTextFlow1.setId("reportsTextFlow1");
        reportsTextFlow1.setTextAlignment(TextAlignment.LEFT);
        reportsTextFlow1.setPrefWidth(600);
        reportsTextFlow1.setMaxWidth(600);
        reportsTextFlow1.setLineSpacing(2);

        TextFlow reportsTextFlow2 = new TextFlow(reportsText2);
        reportsTextFlow2.setId("reportsTextFlow2");
        reportsTextFlow2.setTextAlignment(TextAlignment.LEFT);
        reportsTextFlow2.setPrefWidth(600);
        reportsTextFlow2.setMaxWidth(600);
        reportsTextFlow2.setLineSpacing(2);

        VBox reportsTextContainer = new VBox(10, reportsTextFlow1, reportsTextFlow2);
        reportsTextContainer.setAlignment(Pos.CENTER);
        reportsTextContainer.getStyleClass().add("reportsTextContainer");

        Button createReportBtn = new Button("Gerar relatório");
        createReportBtn.setId("createReportBtn");

        createReportBtn.setOnAction(e -> {
            GeradorRelatorio.gerar(
                dadosMaes, 
                dadosEncontros, 
                dadosEncontrosAnt
            );
        });

        VBox reportsSection = new VBox(20, reportsIntroText, reportsTextContainer, createReportBtn);
        reportsSection.setAlignment(Pos.CENTER);
        reportsSection.setPadding(new Insets(40, 0, 40, 0));
        
        Image footerImg = new Image(getClass().getResource("/gui/images/footer.png").toExternalForm());
        ImageView footerImageView = new ImageView(footerImg);
        footerImageView.getStyleClass().add("footerImageView");
        footerImageView.setPreserveRatio(true);
        footerImageView.setSmooth(true);
        footerImageView.setCache(true);

        Text footerText = new Text("Desenvolvido com amor");
        footerText.setId("footerText");
        
        Text credits = new Text("© 2025 Isaura de Lourdes e Letícia Gabrielly");
        credits.setId("credits");
        
        VBox footerSection = new VBox(footerImageView, footerText, credits);
        footerSection.setAlignment(Pos.CENTER);
        footerSection.setPadding(new Insets(20, 0, 20, 0));
        footerSection.setSpacing(30);
        footerSection.getStyleClass().add("footer-section");

        VBox content = new VBox(
            navBar, 
            headerStack, 
            introTextContainer, 
            servicesTextContainer, 
            servicesContainer, 
            maesSection,
            datesTextContainer,
            encontrosSection, 
            reportsSection,
            footerSection
        );
        content.setMinHeight(5500);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setPannable(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox raiz = new VBox(scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        Scene cena = new Scene(raiz, 1300, 900);

        headerImageView.fitWidthProperty().bind(raiz.widthProperty());
        headerImageView.fitHeightProperty().bind(raiz.heightProperty());
        
        footerImageView.fitWidthProperty().bind(raiz.widthProperty());
        footerImageView.fitHeightProperty().bind(raiz.heightProperty());
        
        begginingImageView.fitWidthProperty().bind(raiz.widthProperty().multiply(0.2));
        begginingImageView.fitHeightProperty().bind(begginingImageView.fitWidthProperty());
        begginingImageView.setPreserveRatio(false);
        
        middleImageView.fitWidthProperty().bind(raiz.widthProperty().multiply(0.2));
        middleImageView.fitHeightProperty().bind(middleImageView.fitWidthProperty());
        middleImageView.setPreserveRatio(false);
        
        endImageView.fitWidthProperty().bind(raiz.widthProperty().multiply(0.2));
        endImageView.fitHeightProperty().bind(endImageView.fitWidthProperty());
        endImageView.setPreserveRatio(false);
        
        Circle clip1 = new Circle();
        clip1.radiusProperty().bind(begginingImageView.fitWidthProperty().divide(2));
        clip1.centerXProperty().bind(begginingImageView.fitWidthProperty().divide(2));
        clip1.centerYProperty().bind(begginingImageView.fitHeightProperty().divide(2));
        begginingImageView.setClip(clip1);
        
        Circle clip2 = new Circle();
        clip2.radiusProperty().bind(middleImageView.fitWidthProperty().divide(2));
        clip2.centerXProperty().bind(middleImageView.fitWidthProperty().divide(2));
        clip2.centerYProperty().bind(middleImageView.fitHeightProperty().divide(2));
        middleImageView.setClip(clip2);
        
        Circle clip3 = new Circle();
        clip3.radiusProperty().bind(endImageView.fitWidthProperty().divide(2));
        clip3.centerXProperty().bind(endImageView.fitWidthProperty().divide(2));
        clip3.centerYProperty().bind(endImageView.fitHeightProperty().divide(2));
        endImageView.setClip(clip3);
        
        cena.getStylesheets().add(getClass().getResource("/gui/HomePage.css").toExternalForm());
        
        stage.setTitle("Homepage");
        stage.setScene(cena);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }
    
    public static void restaurarHomepage() {
        if (primaryStage != null) {
            primaryStage.setIconified(false);
            primaryStage.toFront();
            primaryStage.setMaximized(true);
        }
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
        
        for(Encontro e:filtrada) {
            if(e.getStatus().equals("Em breve")) {
                e.setStatus("Concluído");
                encontroDAO.update(e);
            }
        }
        
        dadosEncontrosAnt = FXCollections.observableArrayList(filtrada);
        tabelaEncontrosAnt.setItems(dadosEncontrosAnt);
    }
    
    private void limparCamposEvento(DatePicker dpData, ComboBox<String> cbStatus) {
        dpData.setValue(null);
        cbStatus.setValue(null);
    }
    
    private void abrirTelaDetalhes(Encontro encontro) {
        TelaDetalhesEncontro telaDetalhes = new TelaDetalhesEncontro(encontro);
        telaDetalhes.mostrar();
    }
    
    private void abrirTelaServicos(String botaoServico) {
        TelaServicos telaServicos = new TelaServicos(botaoServico);
        telaServicos.mostrar();
    }
    
    public static void main (String[]args) {
        launch(args);
    }
}