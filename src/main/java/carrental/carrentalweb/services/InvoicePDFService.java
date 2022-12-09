package carrental.carrentalweb.services;

import carrental.carrentalweb.builder.PDFBuilder;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import com.lowagie.text.*;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * Skrevet af Nicolai Berg Andersen
 */
@Service
public class InvoicePDFService {

    /*
     * Den primære font.
     */
	private static final String fontName = FontFactory.HELVETICA;

	/*
     * Den primære fonts farve.
     */
	private static final Color fontColor = Color.black;

	/*
     * Baggrundsfarve brugt til celler i PDF filens tabeller.
     */
	private static final Color cellBackgroundColor = Color.white;

	/*
     * Rammefarve brugt til celler i PDF filens tabeller.
     */
	private static final Color cellBorderColor = Color.white;
	
	/*
     * Størrelse på overskrifter.
     */
	private static final float titleSize = 16;

	/*
     * Størrelse på brødtekst.
     */
	private static final float paragraphSize = 12;

	/*
     * Virksomheds information.
     */
	@Value("${company.name}")
	private String companyName;

	@Value("${company.street}")
	private String companyStreet;

	@Value("${company.city}")
	private String companyCity;

	@Value("${company.country}")
	private String companyCountry;

	@Value("${company.valuta}")
	private String valuta;

	/*
	* Returner et filnavn som inkluderer faktura id.
	*/
	private String getFilename(Invoice invoice) {
		return String.format("Faktura_%s.pdf", invoice.getBookingId());
	}

	/*
	* Returner en string med information omkring den specifikke faktura.
	*/
	private String getInvoiceInformation(Invoice invoice) {
		return String.format(
		"Faktura Nr.\n%s\n\nDato\n%s\n\nBetalingsdato\n%s",
			invoice.getId(),
			invoice.getCreatedAt(),
			invoice.getDueDate()
		);
	}

	/*
	* Returner en string med information om virksomheden der udlejer biler.
	*/
	private String getCompanyInformation() {
		return String.format(
		"%s\n%s\n%s\n%s",
			companyName,
			companyStreet,
			companyCity,
			companyCountry
		);
	}

	/*
	* Tilføjer faktura overskrift.
	*/
	private void addTitle(PDFBuilder builder, Invoice invoice) throws Exception {
		String invoiceTitle = "Faktura";
		builder
		.setFont(fontName, titleSize, fontColor)
		.addParagraph(invoiceTitle, Paragraph.ALIGN_CENTER);
	}

	/*
	* Tilføjer faktura detaljer og virksomheds information.
	*/
	private void addMetaInformation(PDFBuilder builder, Invoice invoice)
		throws Exception {
		String companyInformation = getCompanyInformation();
		String invoiceMeta = getInvoiceInformation(invoice);

		builder
		.defineTable(
			2, 
			100f, 
			new float[] { 50f, 50f }, 
			10f
		)
		.defineCell(
			cellBackgroundColor,
			cellBorderColor,
			paragraphSize,
			Paragraph.ALIGN_LEFT
		)
		.addCell(companyInformation)
		.defineCell(
			cellBackgroundColor,
			cellBorderColor,
			paragraphSize,
			Paragraph.ALIGN_RIGHT
		)
		.addCell(invoiceMeta)
		.completeTable();
	}

	/*
	* Tilføjer en header til tabellen med faktura specifikationer.
	*/
	private void addSpecificationHeader(PDFBuilder builder) throws Exception {
		builder
		.defineTable(3, 100f, new float[] { 70f, 15f, 15f }, 10f)
		.defineCell(
			cellBackgroundColor,
			cellBorderColor,
			paragraphSize,
			Paragraph.ALIGN_LEFT
		)
		.addCell("Beskrivelse")
		.defineCell(
			cellBackgroundColor,
			cellBorderColor,
			paragraphSize,
			Paragraph.ALIGN_CENTER
		)
		.addCell("Pris")
		.defineCell(
			cellBackgroundColor,
			cellBorderColor,
			paragraphSize,
			Paragraph.ALIGN_RIGHT
		)
		.addCell("Valuta");
	}

	/*
	* Tilføjer specifikationer til tabellen med faktura specifikationer.
	*/
	private void addSpecifications(
		PDFBuilder builder,
		InvoiceSpecification[] specifications
	) throws Exception {
		for (int i = 0; i < specifications.length; i++) {
			builder
			.defineCell(
				cellBackgroundColor,
				cellBorderColor,
				paragraphSize,
				Paragraph.ALIGN_LEFT
			)
			.addCell(specifications[i].getDescription())
			.defineCell(
				cellBackgroundColor,
				cellBorderColor,
				paragraphSize,
				Paragraph.ALIGN_CENTER
			)
			.addCell(Double.toString(specifications[i].getPrice()))
			.defineCell(
				cellBackgroundColor,
				cellBorderColor,
				paragraphSize,
				Paragraph.ALIGN_RIGHT
			)
			.addCell(valuta);
		}
	}

	/*
	* Tilføjer den totale pris for alle specifikationer.
	*/
	private void addTotal(
		PDFBuilder builder,
		InvoiceSpecification[] specifications
	) throws Exception {
		double total = 0;
		for (int i = 0; i < specifications.length; i++) {
			total += specifications[i].getPrice();
		}

		builder
		.defineCell(
			cellBackgroundColor,
			cellBorderColor,
			paragraphSize,
			Paragraph.ALIGN_LEFT
		)
		.addCell("")
		.defineCell(
			cellBackgroundColor,
			cellBorderColor,
			paragraphSize,
			Paragraph.ALIGN_CENTER
		)
		.addCell("Total")
		.defineCell(
			cellBackgroundColor,
			cellBorderColor,
			paragraphSize,
			Paragraph.ALIGN_RIGHT
		)
		.addCell(String.format("%s %s", Double.toString(total), valuta))
		.completeTable();
	}

	/*
	* Opretter en PDF fil ud fra et invoice objekt og et array af invoice specifikationer.
	* PDF filen bliver returneret som typen 'File' efter den er bygget for at den nemt
	* kan sendes med som vedhæftning på mails.
	*/
	public File execute(Invoice invoice, InvoiceSpecification[] specifications) {
		File file = null;
		
		try {
			PDFBuilder builder = null;
			String filename = getFilename(invoice);

			builder = PDFBuilder.create(filename, PageSize.A4);

			addTitle(builder, invoice);
			addMetaInformation(builder, invoice);
			addSpecificationHeader(builder);
			addSpecifications(builder, specifications);
			addTotal(builder, specifications);

			builder.close();

			file = builder.getFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}
}
