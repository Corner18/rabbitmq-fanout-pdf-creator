package ru.aegorova.rabbitmq_pdf_generator.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;


import java.io.FileNotFoundException;


public class PDFCreator {

    public void createPdf(String path, String text) {
        PdfWriter writer;
        try {
            writer = new PdfWriter(path, new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.setTagged();
            Document document = new Document(pdfDocument);
            document.add(new Paragraph(text));
            document.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
