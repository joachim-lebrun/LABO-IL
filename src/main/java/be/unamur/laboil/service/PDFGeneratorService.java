package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Demand;
import be.unamur.laboil.domain.core.OfficialDocument;
import be.unamur.laboil.utilities.Constants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Joachim Lebrun on 17-08-18
 */
@Service
public class PDFGeneratorService {

    @Value("${laboil.storage.path}")
    private String storageDirectory;
    private final String newSeparatorToken = System.getProperty("line.separator");
    private static final String fileSeparator = File.separator;


    public OfficialDocument createPDF(Demand demand) throws IOException {

        File file = new File(storageDirectory);
        file.getParentFile().mkdirs();
        String targetFilePath = String.format("%s%s%s-%s.pdf", storageDirectory, fileSeparator, demand.getDemandID(), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        PdfDocument pdf = new PdfDocument(new PdfWriter(targetFilePath));
        // Initialize document
        try (Document document = new Document(pdf)) {
            switch (demand.getHistory().last().getStatus()) {
                case ACCEPTED:
                    addAcceptedPage(document, demand);
                    addAnnexePage(document, demand);
                    break;
                case REFUSED:
                    addRefusedPage(document, demand);
                    break;
                default:
                    break;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        OfficialDocument officialDocument = new OfficialDocument();
        officialDocument.setDemand(demand);
        officialDocument.setCreationDate(LocalDate.now());
        officialDocument.setPdf(Paths.get(targetFilePath).toFile());
        return officialDocument;
    }

    private void addAnnexePage(Document document, Demand demand) throws MalformedURLException {
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        addHeader(document, demand);
        document.add(getLine("CONCERNE : Invitation à payer", ""))
                .add(whiteLine())
                .add(getLine("", String.format("Le %s", LocalDate.now().format(Constants.FORMATTER))))
                .add(whiteLine())
                .add(whiteLine())
                .add(new Paragraph().add("Madame, Monsieur,"))
                .add(whiteLine())
                .add(whiteLine())
                .add(new Paragraph()
                        .add("Suite à votre demande de réservation d’emplacements de stationnement acceptée, voici les informations nécessaires pour effectuer le paiement.")
                )
                .add(whiteLine())
                .add(new Paragraph()
                        .add(String.format("Montant à payer : %s €", demand.getInformation().get("amount"))))

                .add(whiteLine())
                .add(new Paragraph().add("Destinataire :"))
                .add(new Paragraph().add(demand.getService().getTown().getName()))
                .add(new Paragraph().add(demand.getService().getTown().getAddress()))
                .add(whiteLine())
                .add(new Paragraph().add(String.format("Communication libre : %s", demand.getCommunalName())))
                .add(whiteLine())
                .add(new Paragraph().add(String.format("Veuillez effectuer le versement pour le %s compris. Si ce n’est pas fait, la carte de stationnement sera invalidée dès le lendemain de l’échéance.", demand.getInformation().get("expire"))))
                .add(whiteLine())
                .add(whiteLine());
        addFooter(document, demand);
    }

    private void addRefusedPage(Document document, Demand demand) throws MalformedURLException {
        addHeader(document, demand);
        document.add(getLine("CONCERNE : Demande de réservation d’emplacements de stationnement", ""))
                .add(whiteLine())
                .add(getLine("", String.format("Le %s", LocalDate.now().format(Constants.FORMATTER))))
                .add(whiteLine())
                .add(whiteLine())
                .add(new Paragraph().add("Madame, Monsieur,"))
                .add(whiteLine())
                .add(whiteLine())
                .add(new Paragraph()
                        .add("Par la présente, nous avons le regret de vous faire savoir que votre demande de carte de réservation d’emplacements de stationnement ")
                        .add(new Text("n’a pas été acceptée.")
                                .setBold()
                                .setUnderline())
                )
                .add(whiteLine())
                .add(new Paragraph().add("En effet, après enquête, il s’est avéré que :"))
                .add(new Paragraph().add(demand.getHistory().last().getComment()))
                .add(whiteLine())
                .add(whiteLine());
        addFooter(document, demand);
    }

    private void addAcceptedPage(Document document, Demand demand) throws MalformedURLException {
        addHeader(document, demand);
        document.add(getLine("CONCERNE : Demande de réservation d’emplacements de stationnement", ""))
                .add(whiteLine())
                .add(getLine("", String.format("Le %s", LocalDate.now().format(Constants.FORMATTER))))
                .add(whiteLine())
                .add(whiteLine())
                .add(new Paragraph().add("Madame, Monsieur,"))
                .add(whiteLine())
                .add(whiteLine())
                .add(new Paragraph()
                        .add("Par la présente, nous avons le plaisir de vous faire savoir que votre demande de réservation d’emplacements de stationnement ")
                        .add(new Text("a été acceptée.")
                                .setBold()
                                .setUnderline())
                )
                .add(whiteLine())
                .add(new Paragraph()
                        .add(String.format("L’emplacement réservé à l’adresse %s est valable pour une longueur de %s, du %s au %s.",
                                demand.getInformation().get("address"), demand.getInformation().get("size"), demand.getInformation().get("start"), demand.getInformation().get("end")))
                )
                .add(new Paragraph().add(String.format("En annexe à ce document, vous trouverez une invitation à payer le montant de %s €. Toutes les informations nécessaires figurent dans le document.",
                        demand.getInformation().get("amount"))))
                .add(whiteLine());
        addFooter(document, demand);
    }

    private void addHeader(Document document, Demand demand) throws MalformedURLException {
        document
                .add(new Image(ImageDataFactory.create(demand.getService().getTown().getLogo().getAbsolutePath()))
                        .scaleToFit(150, 150))
                .add(getLine(demand.getService().getTown().getName(), demand.getVerificator().getDisplayName()))
                .add(getLine("", demand.getService().getName()))
                .add(getLine(demand.getService().getTown().getAddress(), demand.getService().getAddress()))
                .add(getLine(demand.getService().getTown().getPhoneNumber(), demand.getVerificator().getPhoneNumber()))
                .add(getLine(demand.getService().getTown().getEmail(), demand.getVerificator().getEmail()))
                .add(whiteLine())
                .add(getLine("Votre référence :" + newSeparatorToken + demand.getName(), "Notre référence :" + newSeparatorToken + demand.getCommunalName(), "Référence LABOIL :" + newSeparatorToken + demand.getDemandID()))
                .add(new LineSeparator(new SolidLine()))
                .add(whiteLine());
    }

    private void addFooter(Document document, Demand demand) {
        document
                .add(new Paragraph().add("Je vous prie d'agréer, Madame, Monsieur, l'expression de nos sentiments distingués."))
                .add(whiteLine())
                .add(whiteLine())
                .add(whiteLine())
                .add(new Table(2)
                        .useAllAvailableWidth()
                        .addCell(
                                getCell(
                                        String.format("%s%sBourgmestre de la commune de %s",
                                                demand.getService().getTown().getMayor().getDisplayName(),
                                                newSeparatorToken,
                                                demand.getService().getTown().getName()),
                                        TextAlignment.LEFT)
                                        .setPaddingRight(50))
                        .addCell(
                                getCell(
                                        String.format("%s%sService %s",
                                                demand.getService().getAdministrator().getDisplayName(),
                                                newSeparatorToken, demand.getService().getName()),
                                        TextAlignment.LEFT))
                );
    }

    private Table getLine(String left, String right) {
        return getLine(left, "", right);
    }

    private Paragraph whiteLine() {
        return new Paragraph().add("");
    }

    private Table getLine(String left, String center, String right) {
        Table table = new Table(3);
        table.useAllAvailableWidth();
        table.addCell(getCell(left, TextAlignment.LEFT));
        table.addCell(getCell(center, TextAlignment.CENTER));
        table.addCell(getCell(right, TextAlignment.RIGHT));
        return table;
    }

    private Cell getCell(String text, TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }
}
