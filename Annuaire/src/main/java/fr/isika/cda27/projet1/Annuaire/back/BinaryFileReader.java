package fr.isika.cda27.projet1.Annuaire.back;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileReader {
	
	/**
	 * Lecture des stagiaires depuis un fichier binaire et les retourne sous forme de liste
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
    public static List<Intern> readInternsFromFile(String filePath) throws IOException {
        List<Intern> interns = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                Intern intern = new Intern();
                intern.setName(readFixedLengthString(raf, Intern.NAME_LENGTH));
                intern.setFirstname(readFixedLengthString(raf, Intern.FIRSTNAME_LENGTH));
                intern.setDepartment(readFixedLengthString(raf, Intern.DEPARTMENT_LENGTH));
                intern.setYear(readFixedLengthString(raf, Intern.YEAR_LENGTH));
                intern.setPromo(readFixedLengthString(raf, Intern.PROMO_LENGTH));
                interns.add(intern);
            }
        }
        return interns;
    }

    /**
     * Fait en sorte de renvoyer en string les données du fichier binaire et enlève les espaces suuplémentaires
     * @param raf
     * @param length
     * @return
     * @throws IOException
     */
    private static String readFixedLengthString(RandomAccessFile raf, int length) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }
}


