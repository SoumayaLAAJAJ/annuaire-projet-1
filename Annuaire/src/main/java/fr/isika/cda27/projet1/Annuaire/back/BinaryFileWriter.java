package fr.isika.cda27.projet1.Annuaire.back;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class BinaryFileWriter {
	/**
	 * Ecriture de chaque Intern dans un fichier binaire 
	 * Bonne pratique de séparer le r du rw (cf pattern Separation of Concerns) 
	 * @param interns
	 * @param filePath
	 * @throws IOException
	 */
    public static void writeInternsToFile(List<Intern> interns, String filePath) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "rw")) {
            for (Intern intern : interns) {
                intern.writeToRandomAccessFile(raf);
            }
        }
    }
}

