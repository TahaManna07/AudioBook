package ma.enset.demo.model;

import java.util.ArrayList;
import java.util.List;

public class LibraryModel {
    private static final List<String> pdfNames = new ArrayList<>();
    private static final List<String> favoritePdfNames = new ArrayList<>();

    public static List<String> getFavoritePdfNames() {
        return favoritePdfNames;
    }

    public static List<String> getPdfNames() {
        return pdfNames;
    }

    public static void addPdf(String pdfName) {
        pdfNames.add(pdfName);
    }
    public static void addFavoritePdf(String pdfName) {
        favoritePdfNames.add(pdfName);
    }
}
