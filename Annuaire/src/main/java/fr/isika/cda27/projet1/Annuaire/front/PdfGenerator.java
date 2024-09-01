package fr.isika.cda27.projet1.Annuaire.front;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import javafx.scene.control.TableView;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Cette classe permet la génération d'un fichier PDF contenant la liste des
 * stagiaires.
 */
public class PdfGenerator {

	private TableView<Intern> tableView;

    /**
     * Crée une instance de `PdfGenerator` avec la table des stagiaires.
     *
     * @param tableView La table des stagiaires à inclure dans le PDF
     */
	public PdfGenerator(TableView<Intern> tableView) {
		this.tableView = tableView;
	}

    /**
     * Génère un fichier PDF contenant la liste des stagiaires à partir de la table fournie.
     *
     * @param filePath Le chemin du fichier PDF à créer
     */
	public void generatePdf(String filePath) {
		Document document = new Document();
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			document.add(new Paragraph("Liste des stagiaires"));
			document.add(new Paragraph(" "));

			List<Intern> interns = tableView.getItems();
			for (Intern intern : interns) {
				document.add(new Paragraph(intern.getName() + " " + intern.getFirstname() + " - "
						+ intern.getDepartment() + " - " + intern.getYear() + " - " + intern.getPromo()));
			}

			document.close();
			openPdf(filePath);

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}

    /**
     * Ouvre le fichier PDF spécifié à l'aide de l'application par défaut associée.
     *
     * @param filePath Le chemin du fichier PDF à ouvrir
     */
	private void openPdf(String filePath) {
		if (Desktop.isDesktopSupported()) {
			try {
				File pdfFile = new File(filePath);
				if (pdfFile.exists()) {
					Desktop.getDesktop().open(pdfFile);
				} else {
					System.out.println("Le fichier PDF n'existe pas.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Le bureau n'est pas pris en charge.");
		}
	}
}
