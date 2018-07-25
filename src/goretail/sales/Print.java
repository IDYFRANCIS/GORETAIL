/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail.sales;

//StringBuilder
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import goretail.FXMLHOMEController;
import static goretail.sales.FXMLCheckController.sum;
import java.awt.Font;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author WISDOM IBANGA
 */
public class Print{
    
    private String firstTen(String value) {
        StringBuilder b = new StringBuilder();
        int i = 0;
        while(i < 9)
        {
            b.append(value.charAt(i));
            i++;
        }
        b.append(".");
        return b.toString();
    }
    
    private String nameParse(String value)
    {
        int space = 0;
        switch(value.length())
        {
            case 1:
                space = 33;
                break;
            case 2:
                space = 31;
                break;
            case 3:
                space = 29;
                break;
            case 4:
                space = 28;
                break;
            case 5:
                space = 26;
                break;
            case 6:
                space = 24;
                break;
            case 7:
                space = 22;
                break;
            case 8:
                space = 20;
                break;
            case 9:
                space = 18;
                break;
            case 10:
                space = 16;
                break;
            case 11:
                space = 14;
                break;
            case 12:
                space = 12;
                break;
            case 13:
                space = 10;
                break;
            case 14:
                space = 8;
                break;
            case 15:
                space = 6;
                break;
            case 16:
                space = 4;
                break;
            case 17:
                space = 2;
                break;
            default:
                space = 33;            
        }
        StringBuilder bd = new StringBuilder();
        //if(space == 34)
        if(false)
        {
            bd.append(firstTen(value));
            space = 14;
            while(space >= 0)
            {
                bd.append(" ");
                space--;
            }
        }else
        {
            bd.append(value);
            while(space >= 0)
            {
                bd.append(" ");
                space--;
            }
        }
        return bd.toString();
    }
    
    private String qtyParse(String value)
    {
        int space = 0;
        switch(value.length())
        {
            case 1:
                space = 8;
                break;
            case 2:
                space = 6;
                break;
            case 3:
                space = 4;
                break;
            case 4:
                space = 2;
                break;
            case 5:
                space = 0;
                break;
            default:
                space = 8;            
        }
        StringBuilder bd = new StringBuilder();
        bd.append(value);
        while(space >= 0)
        {
            bd.append(" ");
            space--;
        }
        return bd.toString();
    }
    
    private String priceParse(String value)
    {
        int space = 0;
        switch(value.length())
        {
            case 1:
                space = 10;
                break;
            case 2:
                space = 8;
                break;
            case 3:
                space = 6;
                break;
            case 4:
                space = 4;
                break;
            case 5:
                space = 2;
                break;
            default:
                space = 10;            
        }
        StringBuilder bd = new StringBuilder();
        bd.append("N");
        bd.append(value);
        bd.append(".00");
        while(space >= 0)
        {
            bd.append(" ");
            space--;
        }
        return bd.toString();
    }
    
    private String totalParse(String qty, String price)
    {
        return String.valueOf(Integer.valueOf(qty) * Integer.valueOf(price));
    }
    
    public void init(String goods, String printname)
    {
        List<String> list2a = new ArrayList<String>();
        List<String> list2b = new ArrayList<String>();
        List<String> list2c = new ArrayList<String>();
        List<String> list2d = new ArrayList<String>();
    
        JSONObject obj2a = new JSONObject(goods);
        JSONArray arr2a = new JSONArray();
        arr2a = obj2a.getJSONArray("data");
        list2a.clear();
        list2b.clear();
        list2c.clear();
        list2d.clear();
        for(int i = 0; i < arr2a.length(); i++){
            list2a.add(arr2a.getJSONObject(i).getString("id"));
            list2b.add(arr2a.getJSONObject(i).getString("name"));    
            list2c.add(arr2a.getJSONObject(i).getString("price"));
            list2d.add(arr2a.getJSONObject(i).getString("quantity"));
        }
                
        int i = 0, sum = 0;
        while(i < list2c.size())
        {
            sum += Integer.valueOf(list2c.get(i)) * Integer.valueOf(list2d.get(i));
            i++;
        }
        FXMLSalesController jx = new FXMLSalesController();
        String amount = jx.amountPop(String.valueOf(sum));
               
                
        File fbi = new File("receipt.pdf");
        if(fbi.exists())
        {
            fbi.delete();
        }
        try{
            Document doc = new Document(PageSize.A4);
            File textFile = new File("receipt.pdf");
            PdfWriter.getInstance(doc, new FileOutputStream(textFile));
            doc.setMargins(7, 5, 5, 0);
            doc.open();
            
            Image image  = Image.getInstance(Print.class.getClassLoader().getResource("goretail/sales/printlogo.png"));
            doc.add(image);
            
            
            doc.add(new Paragraph("-----------------------------------------------"));
            doc.add(new Paragraph(FXMLHOMEController.USEname, 
                    FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph(FXMLHOMEController.USEaddress, 
                    FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("-----------------------------------------------"));
    
            
            String time = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
            doc.add(new Paragraph("Ref. Number: " + time,
                    FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK)));
            
            
            doc.add(new Paragraph("Qty      Item Name                  Total",
                FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("-----------------------------------------------"));
        
            for(int ii = 0; ii < list2a.size(); ii++){
                doc.add(new Paragraph(qtyParse(list2d.get(ii)) + nameParse(list2b.get(ii)) 
                        + "N" + totalParse(list2d.get(ii), list2c.get(ii)) + ".00",
                    FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK)));
            }
            doc.add(new Paragraph("-----------------------------------------------"));
            
            doc.add(new Paragraph(""));
            doc.add(new Paragraph(""));
            doc.add(new Paragraph("Total Amount: N" + amount,
                    FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("Amount Paid: N" + jx.amountPop(String.valueOf(FXMLCheckController.amount)),
                    FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("Balance: N" + String.valueOf(FXMLCheckController.amount - FXMLCheckController.sum) + ".00",
                    FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK)));
       
            doc.add(new Paragraph("-----------------------------------------------"));
            doc.add(new Paragraph("Powered by: BizzdeskGroup Limited",
                    FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("-----------------------------------------------"));
            
            doc.close();
        
            PDDocument document = PDDocument.load(new File("receipt.pdf"));
            PrintService myPrintService = findPrintService(printname);
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            job.setPrintService(myPrintService);
            job.print();
            document.close();
        }catch (Exception e)
        {
            Alert alert1 = new Alert(Alert.AlertType.WARNING, "ERROR PRINTING IMAGE", ButtonType.YES, ButtonType.NO);
            alert1.showAndWait();
            if (alert1.getResult() == ButtonType.YES) {
                alert1.close();
            }
        }
    }
    
    private static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }
    
    
    private String monthname(int m)
    {
        switch(m)
        {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "January";
        }
    }
}