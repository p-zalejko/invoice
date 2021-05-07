package com.gmail.pzalejko.invoicegenerator.application;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Document;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        try (var in = Main.class.getResourceAsStream("/template.html")) {
            String text = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));


            try (OutputStream os = new FileOutputStream("out.pdf")) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withW3cDocument(html5ParseDocument(text), "/");
                builder.toStream(os);
                builder.run();
            }
        }
    }

    private static Document html5ParseDocument(String inputHTML) {
        var doc = Jsoup.parse(inputHTML);
        System.out.println("parsing done ..." + doc);
        return new W3CDom().fromJsoup(doc);
    }
}
