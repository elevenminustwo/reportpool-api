package tr.edu.akdeniz.reportpool.pdfGenerater;


import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import tr.edu.akdeniz.reportpool.entity.Report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratePdfReport {



    public static ByteArrayInputStream stream() {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        List<Report> reportList = new ArrayList<>();
        Report report1 = new Report();
        report1.setReportId(1);
        report1.setText("Kullanici adi: example\nKonu: örnek konu\nTarih Araligi: 2 Mayıstan 3 Mayısa kadar\nŞu şu işler yapıldı.");
        report1.setDateCompleted("2019-05-09 19:10:48");

        Report report2 = new Report();
        report2.setReportId(2);
        report2.setText("Kullanici adi: örnek2\nKonu: örnek konu 2\nTarih Araligi: 2 Mayıstan 5 Mayısa kadar\nBu bu işler yapıldı.");
        report2.setDateCompleted("2019-05-09 19:10:48");


        reportList.add(report1);
        reportList.add(report2);


        try {
            String path = "file:////home/blackspace/Desktop/photo.jpg";
            Image img = Image.getInstance(path);
            img.scaleAbsolute(200, 200);

            PdfWriter.getInstance(document, out);
            document.open();

            for (Report report : reportList) {
                String sss = report.getText();
                document.add(new Paragraph(sss));
                document.add(img);
            }


           /* document.addAuthor("Lokesh Gupta");
            document.addCreationDate();
            document.addCreator("HowToDoInJava.com");
            document.addTitle("UnitReport");
            document.addSubject("An example to show how attributes can be added to pdf files.");*/



            document.close();

        } catch (DocumentException ex) {

            Logger.getLogger(GeneratePdfReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
