package com;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;
import com.itextpdf.tool.xml.html.table.Table;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Copyright by 中旌影视 (c) 2018 Inc.
 * @描述
 * @Author Administrator
 * @创建时间 2018/9/13/013 16:49
 */
public class PdfExport {
    public static void main(String[] args) throws IOException, DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        BaseFont baseFont=BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        PdfWriter pdfWriter = PdfWriter.getInstance(document,new FileOutputStream("F:/demo/zj.pdf"));
        document.open();
        document.add(new Paragraph("**66666**",font));
        Image image = Image.getInstance("F:\\demo\\1.png");
        image.scalePercent(50);
        document.add(image);
        document.close();
        pdfWriter.flush();
        pdfWriter.close();
    }
}
