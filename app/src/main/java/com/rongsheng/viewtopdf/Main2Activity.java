package com.rongsheng.viewtopdf;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main2Activity extends AppCompatActivity {
    String path = Environment.getExternalStorageDirectory() + "/1000ttt/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        CreatePdf();
    }

    public void CreatePdf() {
        //创建一个文档对象纸张大小为A4
        Document doc = new Document(PageSize.A4, 50, 50, 50, 50);
        //设置要输出到磁盘上的文件名称
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(doc, new FileOutputStream(new File(path + "abcd.pdf")));
            //设置作者信息
            doc.addAuthor("sxyx2008");
            //设置文档创建日期
            doc.addCreationDate();
            //设置标题
            doc.addTitle("iText测试");
            //设置值主题
            doc.addSubject("iText");
            //打开文档开始写内容
            doc.open();
            //构建一段落
            Font font = ChineseFont();
//            font.setColor(new BaseColor(0xff0000));
            font.setSize(20);
            font.setStyle("bold");
            font.setStyle("italic");
            font.setStyle("underline");

            Paragraph par3 = new Paragraph("客户信息表", font);
            //设置局中对齐
            par3.setAlignment(Element.ALIGN_CENTER);
            Paragraph par4 = new Paragraph(30,"  ", font);
            //设置局中对齐
            par3.setAlignment(Element.ALIGN_CENTER);
            //添加到文档
            doc.add(par3);
            doc.add(par4);

            //创建一个四列的表格   设置每一列所占的长度
            float[] widths = {113, 113, 113,113};
            PdfPTable table = new PdfPTable(widths);
            table.setLockedWidth(true);
            table.setTotalWidth(458);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
              //设置表头
            String titleName[] = {"序号","姓名","性别","备注"};
            for(int i=0;i<titleName.length;i++){
                PdfPCell celltitle = new PdfPCell();
                celltitle.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                celltitle.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                Paragraph  paratitle  = new Paragraph(titleName[i] ,ChineseFont());
                paratitle.setAlignment(1);
                celltitle.addElement(paratitle);
                table.addCell(celltitle);
            }


            for (int i = 1; i <= 80; i++) {
                //设置编号单元格
                PdfPCell cell11 = new PdfPCell();
                //设置姓名单元格
                PdfPCell cell22 = new PdfPCell();
                //设置性别单元格
                PdfPCell cell33 = new PdfPCell();
                //设置备注单元格
                PdfPCell cell44 = new PdfPCell();

                setTextCenter(i + "", cell11);
                setTextCenter("榕盛科技", cell22);
                setTextCenter("女", cell33);
                setTextCenter("好姑娘", cell44);

                table.addCell(cell11);
                table.addCell(cell22);
                table.addCell(cell33);
                table.addCell(cell44);

            }
            //将表格的第一行设置为表头 让它在每一页都显示出来
            table.setHeaderRows(1);
            //将表格添加到新的文档
            doc.add(table);
            //创建新的一页
            doc.newPage();
            Toast.makeText(Main2Activity.this, "成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Main2Activity.this, "失败", Toast.LENGTH_SHORT).show();
        }
        doc.close();
        writer.close();
    }

    public void setTextCenter(String text,PdfPCell cell) {
        Paragraph  paratitle  = new Paragraph(text ,ChineseFont());
        paratitle.setAlignment(1);
        cell.addElement(paratitle);
        cell.setPhrase(paratitle);
        cell.setMinimumHeight(25);
        //单元格水平对齐方式
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //单元格垂直对齐方式
        cell.setVerticalAlignment(Element.ALIGN_CENTER);

    }
    //pdf文档中文字符处理
    public static Font ChineseFont() {
        BaseFont baseFont = null;
        try {
            baseFont = BaseFont.createFont("assets/simsun.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font chineseFont = new Font(baseFont, 12, Font.NORMAL);
        return chineseFont;
    }


    public void CreatePdf2() {
        FileOutputStream fos = null;
        PdfWriter writer=null;
        Document document = new Document();
        try {
            fos = new FileOutputStream(path+"creatpdf2.pdf");
            writer = PdfWriter.getInstance(document, fos);
            writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
            document.setPageSize(PageSize.A4);
            document.open();

            float[] widths = {144, 113, 191};
            PdfPTable table = new PdfPTable(widths);
            table.setLockedWidth(true);
            table.setTotalWidth(458);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            Object[][] datas = {{"区域产品销售额"},{"区域", "总销售额(万元)", "总利润(万元)简单的表格"}, {"江苏省" , 9045,  2256}, {"广东省", 3000, 690}};
            for(int i = 0; i < datas.length; i++) {
                for(int j = 0; j < datas[i].length; j++) {
                    PdfPCell pdfCell = new PdfPCell();
                    pdfCell.setMinimumHeight(30);

                    //设置单元格样式
                    pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfCell.setBackgroundColor(new BaseColor(0xdd7e6b));

                    pdfCell.setBorder(0);
                    pdfCell.setBorderWidthTop(0.1f);
                    pdfCell.setBorderWidthBottom(0.1f);
                    pdfCell.setBorderWidthLeft(0.1f);
                    pdfCell.setBorderWidthRight(0.1f);
                    pdfCell.setBorderColorBottom(new BaseColor(0x674ea7));
                    pdfCell.setBorderColorLeft(new BaseColor(0x674ea7));
                    pdfCell.setBorderColorRight(new BaseColor(0x674ea7));
                    pdfCell.setBorderColorTop(new BaseColor(0x674ea7));

                    //设置单元格文本字体样式
                    Font font = ChineseFont();
                    if(i == datas.length - 1 && j == 3 - 1) {
                        font.setSize(16);
                        font.setStyle("bold");
                        font.setStyle("italic");
                        font.setStyle("underline");
                    }

                    //合并单元格
                    if(i == 0 && j == 0) {
                        pdfCell.setRowspan(1);
                        pdfCell.setColspan(3);
                    }

                    Paragraph paragraph = new Paragraph(datas[i][j].toString(), font);
                    pdfCell.setPhrase(paragraph);
                    table.addCell(pdfCell);

                }

            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
