package com.mstransaction.mstransaction.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mstransaction.mstransaction.dto.AccountDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Stream;

public class AccountCertificationPDF {

    private static final String FONT_PATH_BOLD = "ms-transaction/src/main/resources/fonts/Manjari-Bold.ttf";
    private static final String FONT_PATH_REGULAR = "ms-transaction/src/main/resources/fonts/Manjari-Regular.ttf";
    private static final String FONT_PATH_THIN = "ms-transaction/src/main/resources/fonts/Manjari-Thin.ttf";
    private static final String LOGO_PATH = "ms-transaction/src/main/resources/2.png";
    private static final String MAIN_LOGO_PATH = "ms-transaction/src/main/resources/1.png";

    public byte[] generatePdf(AccountDTO accountDTO) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.LETTER);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Image logo = Image.getInstance(LOGO_PATH);
            Image mainLogo = Image.getInstance(MAIN_LOGO_PATH);
            addMainImage(document, mainLogo);
            addBackgroundImage(document, logo);

            Paragraph title = createTitle("Certificado de cuenta");
            document.add(title);

            Paragraph currentDateParagraph = createCurrentDateParagraph();
            document.add(currentDateParagraph);

            Paragraph paragraph = createParagraph(
                    "Triwal - Digital Finances Services, se permite informar que " + accountDTO.getName() +
                            " a la fecha de expedición de esta certificación, tiene con TRIWAL el siguiente producto:",
                    FONT_PATH_REGULAR
            );
            document.add(paragraph);

            PdfPTable table = createTable(accountDTO);
            document.add(table);

            Paragraph paragraph1 = createAdvice(
                    "Esta constancia solo hace referencia a los productos mencionados anteriormente.",
                    FONT_PATH_BOLD
            );
            document.add(paragraph1);

            Paragraph paragraph2 = createFooter(
                    "Si desea verificar la veracidad de esta información, puede comunicarse con la linea de atención al cliente TRIWAL a los siguientes números: Bogotá, Colombia - Local: (60-1) 343 00 00 - Buenos Aires, Argentina - Local: (011) 6534-5678 - Internacional - Whatsapp : +1 555 555 6584 ",
                    FONT_PATH_THIN
            );
            document.add(paragraph2);


            document.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }

        return outputStream.toByteArray();
    }

    private Paragraph createTitle(String mainTitle) throws IOException, DocumentException {
        Font boldFont = loadFont(FONT_PATH_BOLD, 20);
        Paragraph title = new Paragraph(mainTitle, boldFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingBefore(10f);
        title.setSpacingAfter(20f);
        return title;
    }

    private Paragraph createParagraph(String text, String fontPath) throws IOException, DocumentException {
        Font font = loadFont(FONT_PATH_REGULAR, 12);
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph.setSpacingBefore(10f);
        paragraph.setSpacingAfter(10f);
        return paragraph;
    }

    private Paragraph createAdvice(String text, String fontPath) throws IOException, DocumentException {
        Font font = FontFactory.getFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.NORMAL);
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph.setSpacingBefore(10f);
        paragraph.setSpacingAfter(5f);
        return paragraph;
    }

    private Paragraph createFooter(String text, String fontPath) throws IOException, DocumentException {
        Font font = FontFactory.getFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9, Font.ITALIC);

        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph.setSpacingBefore(20f);

        return paragraph;
    }

    private PdfPTable createTable(AccountDTO account) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        addTableHeader(table);
        addRows(table, account);

        return table;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of(" Tipo de cuenta", " Número de cuenta", " Moneda", " Saldo", " Fecha de creación")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headFont = null;
                    try {
                        headFont = loadFont(FONT_PATH_BOLD, 11);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    header.setHorizontalAlignment(Element.ALIGN_LEFT);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle, headFont));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, AccountDTO account) {
        Font cellFont = null;
        try {
            cellFont = loadFont(FONT_PATH_REGULAR, 11);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String createdAt = formattedDate(account.getCreatedAt().toString());

        table.addCell(createStyledCell(account.getTypeAccount(), cellFont));
        table.addCell(createStyledCell(account.getAccountNumber(), cellFont));
        table.addCell(createStyledCell(account.getCurrency(), cellFont));
        table.addCell(createStyledCell(String.valueOf(account.getAmount()), cellFont));
        table.addCell(createStyledCell(createdAt, cellFont));
    }

    private PdfPCell createStyledCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(10);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }

    private String formattedDate(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime date = LocalDateTime.parse(inputDate, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(outputFormatter);
    }

    private void addMainImage(Document document, Image mainLogo) throws DocumentException {
        mainLogo.setAlignment(Image.RIGHT | Image.TOP);
        mainLogo.scaleToFit(200, 200);

        document.add(mainLogo);
    }

    private void addBackgroundImage(Document document, Image logo) throws DocumentException {

        float pageWidth = PageSize.LETTER.getWidth();
        float pageHeight = PageSize.LETTER.getHeight();
        float imageWidth = logo.getScaledWidth();
        float imageHeight = logo.getScaledHeight();

        float xPos = pageWidth - imageWidth; // Resta el ancho de la imagen del ancho de la página
        float yPos = pageHeight - imageHeight; // Resta la altura de la imagen de la altura de la página

        logo.setAbsolutePosition(xPos, yPos);
        document.add(logo);
    }

    private Font loadFont(String fontPath, float size) throws IOException, DocumentException {
        BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }

    private Paragraph createCurrentDateParagraph() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);

        Font font = loadFont(FONT_PATH_REGULAR, 12);
        Paragraph paragraph = new Paragraph(formattedDate, font);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.setSpacingBefore(20f);
        paragraph.setSpacingAfter(30f);
        return paragraph;
    }


}

