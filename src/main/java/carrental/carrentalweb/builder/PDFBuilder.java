package carrental.carrentalweb.builder;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
 
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/*
 * Written by Nicolai Berg Andersen
 */
public class PDFBuilder {    
    protected Document document;
    private File file;
    private Font font;
    private PdfPTable table;
    private PdfPCell cell;

    private PDFBuilder(Document document, File file) {
        this.document = document;
        this.file = file;
    }

    public PDFBuilder setFont(String fontName, float fontSize, Color color) {
        font = FontFactory.getFont(fontName);
        font.setSize(fontSize);
        font.setColor(color);
        return this;
    }

    public PDFBuilder addParagraph(String text, int alignment) {
        Paragraph p = new Paragraph(text, font);
        p.setAlignment(alignment);
        document.add(p);
        return this;
    }

    public PDFBuilder newLine() {
        document.add(Chunk.NEWLINE);
        return this;
    }

    public PDFBuilder defineTable(int numColumns, float widthPercentage, float[] widths, float spacing) {
        table = new PdfPTable(numColumns);
        table.setWidthPercentage(widthPercentage);
        table.setWidths(widths);
        table.setSpacingBefore(spacing);
        return this;
    }

    public PDFBuilder defineCell(Color backgroundColor, Color borderColor, float padding, int alignment) {
        cell = new PdfPCell();
        cell.setBackgroundColor(backgroundColor);
        cell.setBorderColor(borderColor);
        cell.setPadding(padding);
        cell.setHorizontalAlignment(alignment);
        return this;
    }

    public PDFBuilder completeTable() {
        document.add(table);
        return this;
    }

    public PDFBuilder addCell(String value) throws Exception {
        if (table == null)
            throw new Exception("Cannot add cell to an empty table!");

        cell.setPhrase(new Phrase(value, font));
        table.addCell(cell);

        return this;
    }

    public PDFBuilder addCells(String... values) throws Exception {
        for (int i = 0; i < values.length; i++)
            addCell(values[i]);
        return this;
    }

    public PDFBuilder close() {
        document.close();
        return this;
    }

    public File getFile() {
        return file;
    }

    public static PDFBuilder create(String filename, Rectangle size)
        throws FileNotFoundException {
        File file = new File(filename);
        FileOutputStream stream = new FileOutputStream(file);
        Document document = new Document(size);
        PDFBuilder builder = new PDFBuilder(document, file);
        PdfWriter.getInstance(builder.document, stream);
        builder.document.open();
        return builder;
    }
}
