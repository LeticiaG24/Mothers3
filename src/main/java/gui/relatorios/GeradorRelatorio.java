package gui.relatorios;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.io.image.ImageDataFactory;

import model.Mae;
import model.Encontro;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeradorRelatorio {

    public static void gerar(List<Mae> maes, List<Encontro> encontros, List<Encontro> anteriores) {
        try {

        	String destino = System.getProperty("user.home") 
        	        + File.separator + "Downloads" 
        	        + File.separator + "Relatorio_Maes_Encontros.pdf";

            PdfWriter writer = new PdfWriter(destino);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf, PageSize.A4);

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            DeviceRgb headerColor = new DeviceRgb(0xA7, 0xA1, 0xB8);  
            DeviceRgb geradoEmColor = new DeviceRgb(0x8C, 0x99, 0x6A); 
            DeviceRgb tituloColor = new DeviceRgb(0x7D, 0x75, 0x94);   

            try {
                Image header = new Image(ImageDataFactory.create(
                        GeradorRelatorio.class.getResource("/gui/images/pdfHeader.png")));
                header.setAutoScale(true);
                doc.add(header);
            } catch (Exception ex) {
                System.out.println("Erro ao carregar header: " + ex.getMessage());
            }

            doc.add(
                new Paragraph(
                    "Gerado em: " + java.time.LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    )
                )
                .setFontColor(geradoEmColor)
                .setFontSize(10)
                .setMarginTop(10)
            );

            doc.add(new Paragraph("Nossas Mães")
                    .setFontSize(20)
                    .setBold()
                    .setFontColor(tituloColor)
                    .setMarginTop(20));

            Table tabelaMaes = new Table(new float[]{4, 4, 3, 6});
            tabelaMaes.setWidth(UnitValue.createPercentValue(100));

            tabelaMaes.addHeaderCell(header("Nome", headerColor));
            tabelaMaes.addHeaderCell(header("Telefone", headerColor));
            tabelaMaes.addHeaderCell(header("Aniversário", headerColor));
            tabelaMaes.addHeaderCell(header("Endereço", headerColor));

            for (Mae m : maes) {
                tabelaMaes.addCell(cell(m.getNome()));
                tabelaMaes.addCell(cell(m.getTelefone()));
                tabelaMaes.addCell(cell(m.getDataAniversario() != null ? fmt.format(m.getDataAniversario()) : ""));
                tabelaMaes.addCell(cell(m.getEndereco()));
            }

            doc.add(tabelaMaes);

            doc.add(new Paragraph("Encontros")
                    .setFontSize(20)
                    .setBold()
                    .setFontColor(tituloColor)
                    .setMarginTop(30));

            Table tabelaEncontros = new Table(new float[]{4, 4, 4});
            tabelaEncontros.setWidth(UnitValue.createPercentValue(100));

            tabelaEncontros.addHeaderCell(header("Data", headerColor));
            tabelaEncontros.addHeaderCell(header("Status", headerColor));
            tabelaEncontros.addHeaderCell(header("Tipo", headerColor));

            for (Encontro e : encontros) {
                tabelaEncontros.addCell(cell(fmt.format(e.getData())));
                tabelaEncontros.addCell(cell(e.getStatus()));
                tabelaEncontros.addCell(cell("Futuro"));
            }

            doc.add(tabelaEncontros);

            doc.add(new Paragraph("Encontros Anteriores")
                    .setFontSize(20)
                    .setBold()
                    .setFontColor(tituloColor)
                    .setMarginTop(30));

            Table tabelaAnt = new Table(new float[]{4, 4, 4});
            tabelaAnt.setWidth(UnitValue.createPercentValue(100));

            tabelaAnt.addHeaderCell(header("Data", headerColor));
            tabelaAnt.addHeaderCell(header("Status", headerColor));
            tabelaAnt.addHeaderCell(header("Tipo", headerColor));

            for (Encontro e : anteriores) {
                tabelaAnt.addCell(cell(fmt.format(e.getData())));
                tabelaAnt.addCell(cell(e.getStatus()));
                tabelaAnt.addCell(cell("Passado"));
            }

            doc.add(tabelaAnt);
            
            doc.add(
                new Paragraph(
                    "Este relatório reúne com carinho os dados, registros e momentos compartilhados pelas mães ao "
                    + "longo dos encontros realizados no sistema.\n\n"
                    + "Cada informação aqui apresentada reflete não apenas números, mas histórias, vínculos e o "
                    + "compromisso contínuo com o cuidado e o acompanhamento das famílias."
                )
                .setFontSize(12)
                .setMarginTop(40)
            );

            try {
                Image footer = new Image(ImageDataFactory.create(
                        GeradorRelatorio.class.getResource("/gui/images/pdfFooter.png")));
                footer.setAutoScale(true);
                footer.setMarginTop(20);
                doc.add(footer);
            } catch (Exception ex) {
                System.out.println("Erro ao carregar footer: " + ex.getMessage());
            }

            doc.add(
                new Paragraph("Com carinho e Fé")
                        .setFontSize(14)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontColor(tituloColor)
                        .setMarginTop(10)
            );

            doc.close();
            System.out.println("PDF gerado: " + new File(destino).getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Cell header(String txt, DeviceRgb bgColor) {
        return new Cell()
                .add(new Paragraph(txt).setBold().setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(bgColor);
    }

    private static Cell cell(String txt) {
        return new Cell().add(new Paragraph(txt));
    }
}
