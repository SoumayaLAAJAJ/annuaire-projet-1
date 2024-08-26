package fr.isika.cda27.projet1.Annuaire.back;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class BinaryFileWriter {
	/**
	 * Ecriture de chaque Intern dans un fichier binaire Bonne pratique de séparer
	 * le r du rw (cf pattern Separation of Concerns)
	 * 
	 * @param interns
	 * @param filePath
	 * @throws IOException
	 */
	public static void writeInternsToFile(List<Intern> interns, String filePath) throws IOException {
		try (RandomAccessFile raf = new RandomAccessFile(filePath, "rwd")) {
			for (Intern intern : interns) {
				String name = intern.getName();
				String firstname = intern.getFirstname();
				String department = intern.getDepartment();
				String year = intern.getYear();
				String promo = intern.getPromo();

				raf.writeBytes(name);
				raf.writeBytes(firstname);
				raf.writeBytes(department);
				raf.writeBytes(year);
				raf.writeBytes(promo);
			}
		}
	}
}
