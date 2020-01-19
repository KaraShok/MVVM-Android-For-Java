package com.karashok.library.module_util.utilcode.utils.base_util.file_util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Tag;
import com.itextpdf.tool.xml.WorkerContext;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.AbstractTagProcessor;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.DefaultTagProcessorFactory;
import com.itextpdf.tool.xml.html.HTML;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.karashok.library.lib_3rd.ModuleResourceConstant;
import com.karashok.library.module_util.utilcode.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author KaraShok(zhangyaozhong)
 * @name PdfUtils
 * DESCRIPTION PDF工具类
 * @date 2018/05/04/下午8:39
 */

public class PdfUtils {

    /**
     * 目录下图片合成PDF
     *
     * @param dirPath
     */
    public static void createPDF(Bitmap bitmap, String dirPath) {

        Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(dirPath));
            doc.open();

            doc.newPage();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            Image image = Image.getInstance(stream.toByteArray());
            image.setAlignment(Image.MIDDLE);
            scaleImage(doc, image);

            doc.add(image);

            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 目录下图片合成PDF
     *
     * @param dirPath
     */
    public static void createPDF(String dirPath) {
        File dirFile = new File(dirPath);

        Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(dirPath + File.separator + System.currentTimeMillis() + ".pdf"));
            doc.open();

            File[] files = dirFile.listFiles(new ImageFilter());
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.getPath().endsWith(".jpg")) {
                        continue;
                    }

                    doc.newPage();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    Image image = Image.getInstance(stream.toByteArray());
                    image.setAlignment(Image.MIDDLE);
                    scaleImage(doc, image);

                    doc.add(image);
                }
            }

            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据路径列表生成PDF
     *
     * @param pathList
     * @return PDF路径
     */
    public static String createPDF(List<String> pathList, String name) {
        String pdfPath = ModuleResourceConstant.SAVE_FILE + name + ".pdf";
        Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));
            doc.open();

            for (String path : pathList) {
                File file = new File(path);

                if (file.isFile()) {
                    if (!file.getPath().endsWith(".jpg")) {
                        continue;
                    }

                    doc.newPage();

                    Image image = Image.getInstance(file.getPath());
                    image.setAlignment(Image.MIDDLE);
                    scaleImage(doc, image);

                    doc.add(image);
                }
            }

            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfPath;
    }

    /**
     * 根据PDF页面大小缩放图片
     *
     * @param doc
     * @param image
     */
    private static void scaleImage(Document doc, Image image) {
        int indentation = 0;
        float scaler;
        if (((float) image.getWidth() / (float) image.getHeight()) >= ((float) (doc.getPageSize().getWidth() - doc.leftMargin() - doc.rightMargin()) / (float) (doc.getPageSize().getHeight() - doc.topMargin() - doc.bottomMargin()))) {
            scaler = ((doc.getPageSize().getWidth() - doc.leftMargin() - doc.rightMargin() - indentation) / image.getWidth()) * 100;
        } else {
            scaler = ((doc.getPageSize().getHeight() - doc.topMargin() - doc.bottomMargin() - indentation) / image.getHeight()) * 100;
        }
        image.scalePercent(scaler);
    }

    /**
     * 循环删除文件夹下的PDF
     *
     * @param dirPath
     */
    public static void deletePDF(String dirPath) {
        File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.getPath().endsWith(".pdf")) {
                file.delete();
            }
        }
    }

    /**
     * 根据html创建PDF
     *
     * @return
     */
    public static String html2pdf(String content, String titleStr) {
        String pdfPath = Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + File.separator + titleStr + ".pdf";
        File file = new File(pdfPath);
        if (file.exists()) {
            file.delete();
        }
        Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));
            writer.setInitialLeading(12);
            doc.open();

            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

            //标题 加粗
            Font font = new Font(bfChinese, 28, Font.BOLD);

            Paragraph title = new Paragraph(titleStr, font);
            doc.add(title);

            //副标题
            font = new Font(bfChinese, 20, Font.NORMAL);
            font.setColor(138, 138, 138);
            Paragraph subTitle = new Paragraph(String.format("%s %s", "last modified time", "Author"), font);
            doc.add(subTitle);

            // 正文
            // CSS
            CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

            CssAppliers cssAppliers = new CssAppliersImpl(new PdfFontProvider());
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);

            // HTML
            DefaultTagProcessorFactory tagProcessorFactory = (DefaultTagProcessorFactory) Tags.getHtmlTagProcessorFactory();
            tagProcessorFactory.addProcessor("input", CheckboxProcessor.class.getName());
            htmlContext.setTagFactory(tagProcessorFactory);
            htmlContext.setImageProvider(new PdfImageProvider());

            // Pipelines
            PdfWriterPipeline pdf = new PdfWriterPipeline(doc, writer);
            HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
            CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

            // XML Worker
            XMLWorker worker = new XMLWorker(css, true);
            XMLParser p = new XMLParser(worker);
            p.parse(new ByteArrayInputStream(content.getBytes()));

            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfPath;
    }

    private static class ImageFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".jpg");
        }
    }

    private static class PdfFontProvider implements FontProvider {
        @Override
        public boolean isRegistered(String fontname) {
            return false;
        }

        @Override
        public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color) {
            BaseFont bfChinese = null;
            try {
                bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Font FontChinese = new Font(bfChinese, 24, Font.NORMAL);
            return FontChinese;
        }
    }

    private static class PdfImageProvider extends AbstractImageProvider {

        @Override
        public Image retrieve(String src) {

            //            try {
            //                String path = MD5Util.md5(src);
            //                File file = new File(AlphaNoteApplication.mApplicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), Constant.DIRECTORY_TEMP + File.separator + path);
            //                if (file.exists()) {
            //                    Image image = Image.getInstance(file.getPath());
            //                    //设置图片居中显示
            //                    image.setAlignment(Image.MIDDLE);
            //
            //                    return image;
            //                }
            //
            //                return null;
            //            } catch (IOException e) {
            //                e.printStackTrace();
            //            } catch (BadElementException e) {
            //                e.printStackTrace();
            //            }

            return null;
        }

        @Override
        public String getImageRootPath() {
            return null;
        }
    }

    private class CheckboxProcessor extends AbstractTagProcessor {

        @Override
        public List<Element> end(WorkerContext ctx, Tag tag, List<Element> currentContent) {
            Map<String, String> attributes = tag.getAttributes();
            String inputType = attributes.get(HTML.Attribute.TYPE);
            boolean isChecked = attributes.get("checked") != null;
            if ("checkbox".equalsIgnoreCase(inputType)) {
                ArrayList<Element> list = new ArrayList<Element>(1);

                //                Bitmap bitmap = null;
                //                try {
                //                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //
                //                    if (isChecked) {
                //                        bitmap = BitmapFactory.decodeResource(AlphaNoteApplication.mApplicationContext.getResources(), R.mipmap.pdf_checkbox_checked);
                //                    } else {
                //                        bitmap = BitmapFactory.decodeResource(AlphaNoteApplication.mApplicationContext.getResources(), R.mipmap.pdf_checkbox);
                //                    }
                //
                //                    Matrix matrix = new Matrix();
                //                    //长和宽放大缩小的比例
                //                    matrix.postScale(0.25f, 0.25f);
                //                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
                //                    bitmap.compress(Bitmap.CompressFormat.JPEG /* FileType */, 100 /* Ratio */, stream);
                //
                //                    com.itextpdf.text.Image img = Image.getInstance(stream.toByteArray());
                //
                //                    HtmlPipelineContext htmlPipelineContext =  getHtmlPipelineContext(ctx);
                //                    list.add(getCssAppliers().apply(new Chunk((com.itextpdf.text.Image) getCssAppliers().apply(img, tag, htmlPipelineContext), 0, 0, true), tag, htmlPipelineContext));
                //                } catch (IOException e) {
                //                    e.printStackTrace();
                //                } catch (BadElementException e) {
                //                    e.printStackTrace();
                //                } catch (NoCustomContextException e) {
                //                    e.printStackTrace();
                //                }

                return list;
            }
            return null;
        }
    }
}
