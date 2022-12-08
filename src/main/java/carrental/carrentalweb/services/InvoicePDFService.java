package carrental.carrentalweb.services;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import com.lowagie.text.*;
import carrental.carrentalweb.builder.PDFBuilder;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import org.springframework.stereotype.Service;

/*
 * Written by Nicolai Berg Andersen
 */
@Service
public class InvoicePDFService {

    private static final String fontName = FontFactory.HELVETICA;
    private static final Color backgroundColor = Color.white;
    private static final Color borderColor = Color.white;
    private static final Color fontColor = Color.black;
    private static final float titleSize = 16;
    private static final float paragraphSize = 12;

    private static final String companyName = "Car Rental ApS";
    private static final String companyStreet = "Hovedgaden 25 1. th";
    private static final String companyCity = "København 2400";
    private static final String companyCountry = "Danmark";
    private static final String valuta = "DKK";
    
    public File execute(Invoice invoice, InvoiceSpecification[] specifications) {
        PDFBuilder builder = null;
        try {
            String invoiceTitle = String.format("Faktura", invoice.getBookingId());
            String companyInformation = String.format("%s\n%s\n%s\n%s", companyName, companyStreet, companyCity, companyCountry);
            String invoiceMeta = String.format("Faktura Nr.\n%s\n\nDato\n%s\n\nBetalingsdato\n%s", invoice.getCreatedAt(), invoice.getCreatedAt(), invoice.getDueDate());

            builder = PDFBuilder.create("faktura.pdf", PageSize.A4);

            builder.setFont(fontName, titleSize, fontColor)
                   .addParagraph(invoiceTitle, Paragraph.ALIGN_CENTER)
                   .newLine()
                   .setFont(fontName, paragraphSize, fontColor)
                   .defineTable(2, 100f, new float[]{50f, 50f}, 10f)
                        .defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_LEFT)
                            .addCell(companyInformation)
                        .defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_RIGHT)
                            .addCell(invoiceMeta)
                   .completeTable()
                   .defineTable(3, 100f, new float[]{70f, 15f, 15f}, 10f)
                        .defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_LEFT)
                            .addCell("Beskrivelse")
                        .defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_CENTER)
                            .addCell("Pris")
                        .defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_RIGHT)
                            .addCell("Valuta");

            double total = 0;
            for (int i = 0; i < specifications.length; i++) {
                total += specifications[i].getPrice();
                builder.defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_LEFT)
                            .addCell(specifications[i].getDescription())
                       .defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_CENTER)
                            .addCell(Double.toString(specifications[i].getPrice()))
                       .defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_RIGHT)
                            .addCell(valuta);
            }

            builder.defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_LEFT)
                        .addCell("")
                    .defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_CENTER)
                        .addCell("Total")
                    .defineCell(backgroundColor, borderColor, paragraphSize, Paragraph.ALIGN_RIGHT)
                        .addCell(String.format("%s %s", Double.toString(total), valuta))
                    .completeTable()
                    .close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.getFile();
    }
}
