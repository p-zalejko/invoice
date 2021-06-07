package com.gmail.pzalejko.invoicegenerator.application;

import com.openhtmltopdf.extend.FSSupplier;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, TemplateException {
        Properties prop = new Properties();
        try (InputStream input = Main.class.getResourceAsStream("/invoice-template-pl.properties")) {
            // load a properties file
            prop.load(new InputStreamReader(input, StandardCharsets.UTF_8));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try (var in = Main.class.getResourceAsStream("/template.html")) {
            String text = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));

            Template t = new Template("name", new StringReader(text),
                    new Configuration(Configuration.VERSION_2_3_31));

            try (StringWriter out = new StringWriter()) {
                t.process(prop, out);
                String toString = out.getBuffer().toString();
                System.out.println(toString);
                try (OutputStream os = new FileOutputStream("out.pdf")) {
                    PdfRendererBuilder builder = new PdfRendererBuilder();
                    builder.useFastMode();
                    Document doc = html5ParseDocument(toString);
                    Element secondItem = doc.getElementById("secondItem");
//                    doc.getChildNodes()
//                    doc.removeChild(secondItem);
                    builder.withW3cDocument(doc, "/");
                    builder.useFont(() -> Main.class.getResourceAsStream("/SourceSans3-Regular.ttf"),"Noto Sans");
                    builder.toStream(os);
                    builder.run();
                }

                out.flush();
            }
//
//            Configuration cfg = new Configuration();
//            Template template = cfg.getTemplate("/template.html");

        }
    }

    private static Document html5ParseDocument(String inputHTML) {
        var doc = Jsoup.parse(inputHTML);
        return new W3CDom().fromJsoup(doc);
    }
}
