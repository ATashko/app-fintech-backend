package com.mstransaction.mstransaction.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mstransaction.mstransaction.dto.AccountDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

public class AccountCertificationPDF {

    public byte[] generatePdf(AccountDTO accountDTO) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.LETTER);
            PdfWriter.getInstance(document, outputStream);
            document.open();


            Image logo= Image.getInstance("ms-transaction/src/main/resources/2.png");
            Image mainLogo= Image.getInstance("ms-transaction/src/main/resources/1.png");

            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);

            Paragraph paragraph = new Paragraph(
                    "TRIWAL", boldFont);

            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            paragraph.setSpacingBefore(10f);
            paragraph.setSpacingAfter(4f);


            Paragraph paragraph1 = new Paragraph(
                    "Triwal - Digital Finances Services, se permite informar que" + accountDTO.getAccountNumber());

            paragraph1.setAlignment(Paragraph.ALIGN_LEFT);
            //paragraph1.setFont(boldFont);
            paragraph1.setSpacingAfter(10f);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            addMainImage(mainLogo);
            addBackgroundImage(logo);
            addTableHeader(table);
            addRows(table, accountDTO);

            document.add(logo);
            document.add(mainLogo);
            document.add(paragraph);
            document.add(paragraph1);
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar el PDF", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outputStream.toByteArray();
    }

    private void addMainImage(Image mainLogo) {
        mainLogo.setAlignment(Image.ALIGN_CENTER);
        mainLogo.scaleToFit(200, 200);
    }

    private void addBackgroundImage(Image logo) throws IOException {


        float pageWidth = PageSize.LETTER.getWidth();
        float pageHeight = PageSize.LETTER.getHeight();
        float imageWidth = logo.getScaledWidth();
        float imageHeight = logo.getScaledHeight();

        float xPos = pageWidth - imageWidth; // Resta el ancho de la imagen del ancho de la página
        float yPos = pageHeight - imageHeight; // Resta la altura de la imagen de la altura de la página

        logo.setAbsolutePosition(xPos, yPos);

    }
    private void addRows(PdfPTable table, AccountDTO account) {
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA);

        table.addCell(createStyledCell(account.getAccountNumber(), cellFont));
        table.addCell(createStyledCell(account.getTypeAccount(), cellFont));
        table.addCell(createStyledCell(account.getCurrency(), cellFont));
        table.addCell(createStyledCell(String.valueOf(account.getAmount()), cellFont));
        table.addCell(createStyledCell(account.getCreatedAt().toString(), cellFont));
    }

    private PdfPCell createStyledCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Account Number", "Type Account", "Currency", "Amount", "Created At")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, headFont));
                    table.addCell(header);
                });
    }
}

