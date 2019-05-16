package tr.edu.akdeniz.reportpool.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.akdeniz.reportpool.entity.Report;
import tr.edu.akdeniz.reportpool.file_upload.FileStorageProperties;
import tr.edu.akdeniz.reportpool.model.PaginationDto;
import tr.edu.akdeniz.reportpool.model.ReportToPrintDto;
import tr.edu.akdeniz.reportpool.model.UnitReportDto;
import tr.edu.akdeniz.reportpool.pdf_generator.GeneratePdfReport;
import tr.edu.akdeniz.reportpool.repository.*;
import tr.edu.akdeniz.reportpool.service.FileStorageService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UnitReportService {

    @Autowired
    EntityManager entityManager;
    @Autowired
    FileStorageService fileStorageService;


    @Transactional(readOnly = true)
    public PaginationDto getUnitsReports(int dept,int unit, String fromDate, String toDate, String draw,String length,String skip,String sortDir,String sortColumnIndex,String search){
        switch (Integer.parseInt(sortColumnIndex)){
            case 0:
                sortColumnIndex="us.Name";
                break;
            case 1:
                sortColumnIndex="r.DateCompleted";
                break;
            case 2:
                sortColumnIndex="r.Text";
                break;
            case 3:
                sortColumnIndex="r.ReportID";
                break;

        }

        search = search.replace("+", "");
        String[] words = search.split(" ");
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < words.length - 1; i++) {
            if (!words[i].equals(""))
                sb.append("+" + words[i] + "* ");
        }
        if (words.length > 0) {
            if (!words[words.length - 1].equals(""))
                sb.append("+" + words[words.length - 1] + "*");
            search = sb.toString();
        }


        // very cheap way but works great for now (every report contains the word konu)
        System.out.println("Search: " + search);
        if (search.equals("")) {
            search = "konu";
        }


        Query q=entityManager
                .createNativeQuery(
                        "SELECT " +
                                "CONCAT(us.Name, ' ', us.Surname) AS ?1," +
                                "r.DateCompleted AS ?2, " +
                                "r.Text AS ?3, " +
                                "r.ReportID AS ?10" +
                                " FROM department d,departmentunit du,unit u,user us,report r,userdepartmentunit udu" +
                                " WHERE d.DepartmentID=du.department_id AND " +
                                "u.UnitID=du.unit_id " +
                                "AND du.DepartmentUnitID= udu.departmentunit_id" +
                                " AND us.UserID= udu.user_id" +
                                " AND r.DateCompleted >= ?5" +
                                " AND r.DateCompleted <= ?6" +
                                " AND r.IsCompleted = 1" +
                                " AND du.DepartmentUnitID=r.departmentunit_id " +
                                "AND r.user_id=us.UserID " +
                                //" AND (r.Text LIKE ?4)"+
                                " AND MATCH(r.Text) AGAINST (?4 IN BOOLEAN MODE)" +
                                " AND d.DepartmentID="+dept+
                                "  AND u.UnitID= "+unit+
                                "  ORDER BY "+sortColumnIndex+" "+sortDir.toUpperCase())
                //.setParameter(0,"username")
                .setParameter(1,"fullName")
                .setParameter(2,"dateCompleted")
                .setParameter(3,"text")
                .setParameter(4,"'"+search+"'")
                .setParameter(5, fromDate)
                .setParameter(6, toDate)
                .setParameter(10, "reportId")

                .setMaxResults(Integer.parseInt(length))
                .setFirstResult(Integer.parseInt(skip));
        int recordsTotal=((Number)entityManager
                .createNativeQuery("SELECT COUNT(*) " +
                        " FROM department d,departmentunit du,unit u,user us,report r,userdepartmentunit udu" +
                        " WHERE d.DepartmentID=du.department_id AND " +
                        "u.UnitID=du.unit_id " +
                        "AND du.DepartmentUnitID= udu.departmentunit_id" +
                        " AND us.UserID= udu.user_id" +
                        " AND r.DateCompleted >= ?8" +
                        " AND r.DateCompleted <= ?9" +
                        " AND r.IsCompleted = 1" +
                        " AND du.DepartmentUnitID=r.departmentunit_id " +
                        "AND r.user_id=us.UserID " +
                        " AND d.DepartmentID="+dept+
                        "  AND u.UnitID= "+unit+
                        " AND MATCH(r.Text) AGAINST (?7 IN BOOLEAN MODE)")
                        //" AND (r.Text LIKE ?7)")
                .setParameter(7,"'"+search+"'")
                .setParameter(8, fromDate)
                .setParameter(9, toDate)
                .getSingleResult()).intValue();
        PaginationDto paginationDto = new PaginationDto(Integer.parseInt(draw),recordsTotal-Integer.parseInt(skip),recordsTotal,q.getResultList());
        return paginationDto;
    }



    public ByteArrayInputStream compileReportsIntoPdf(int dept,int unit, String fromDate, String toDate, String search) {



        search = search.replace("+", "");
        String[] words = search.split(" ");
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < words.length - 1; i++) {
            if (!words[i].equals(""))
                sb.append("+" + words[i] + "* ");
        }
        if (words.length > 0) {
            if (!words[words.length - 1].equals(""))
                sb.append("+" + words[words.length - 1] + "*");
            search = sb.toString();
        }


        // very cheap way but works great for now (every report contains the word konu)
        System.out.println("Search: " + search);
        if (search.equals("")) {
            search = "konu";
        }


        Query q=entityManager
                .createNativeQuery(
                        "SELECT " +
                                "r.ReportID, r.Text, r.DateCompleted, us.Name, us.Surname" +
                                " FROM department d,departmentunit du,unit u,user us,report r,userdepartmentunit udu" +
                                " WHERE d.DepartmentID=du.department_id AND " +
                                "u.UnitID=du.unit_id " +
                                "AND du.DepartmentUnitID= udu.departmentunit_id" +
                                " AND us.UserID= udu.user_id" +
                                " AND r.DateCompleted >= ?5" +
                                " AND r.DateCompleted <= ?6" +
                                " AND r.IsCompleted = 1" +
                                " AND du.DepartmentUnitID=r.departmentunit_id " +
                                "AND r.user_id=us.UserID " +
                                //" AND (r.Text LIKE ?4)"+
                                " AND MATCH(r.Text) AGAINST (?4 IN BOOLEAN MODE)" +
                                " AND d.DepartmentID="+dept+
                                "  AND u.UnitID= "+unit+
                                "  ORDER BY r.DateCompleted ASC")
                //.setParameter(0,"username")
                .setParameter(4,"'"+search+"'")
                .setParameter(5, fromDate)
                .setParameter(6, toDate);

        //TUPLE 0: report id, 1: report text, 2: date completed, 3: first name, 4: last name, 5: department name, 6: unit name
        List<Object[]> reports = q.getResultList();





        // now print them to pdf

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font hugeHeaderFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
        Font metadataFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);



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

            PdfWriter.getInstance(document, out);
            document.open();


            document.add(new Paragraph("Rapor Dökümü", hugeHeaderFont)); // add large title




            document.add(new Paragraph("\n"));
            //TUPLE 0: report id, 1: report text, 2: date completed, 3: first name, 4: last name
            for (int i = 0; i < reports.size(); i++) {
                int reportId = (int) reports.get(i)[0];
                String reportBody = (String) reports.get(i)[1];
                String nameAndSurname = "Rapor sahibi: " + ((String) reports.get(i)[3]) + " " + ((String) reports.get(i)[4]);
                String raporTeslimTarihi = "Rapor teslim tarihi: " + ((java.sql.Timestamp) reports.get(i)[2]).toString();

                String[] reportChunks = reportBody.split("\n", 4);
                if (reportChunks.length < 4) {
                    continue;
                }
                document.add(new Paragraph(reportChunks[1], headerFont));
                document.add(new Paragraph(nameAndSurname, metadataFont));
                document.add(new Paragraph(reportChunks[0] + "\n" + reportChunks[2], metadataFont));
                document.add(new Paragraph(raporTeslimTarihi, metadataFont));
                document.add(new Paragraph(reportChunks[3], contentFont));

                List<byte[]> images = fileStorageService.loadImagesFromDbForPdf(reportId);
                if (images == null || images.isEmpty()) {
                    // do nothing
                } else {

                    for (int j = 0; j < images.size(); j++) {

                        Image image = Image.getInstance(images.get(j));

                        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                                - document.rightMargin() - 10) / image.getWidth()) * 55;

                        image.scalePercent(scaler);

                        document.add(image);
                    }

                }

                document.add(new Paragraph("\n"));

                //document.add(img);
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