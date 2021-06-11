package com.gmail.pzalejko.invoicegenerator.infrastructure;

import com.gmail.pzalejko.invoicegenerator.model.Invoice;
import com.gmail.pzalejko.invoicegenerator.model.InvoiceFactory;
import com.gmail.pzalejko.invoicegenerator.model.InvoiceInput;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@ApplicationScoped
public class PdfInvoiceFactory implements InvoiceFactory {

    private Properties props;

    @PostConstruct
    void init() {
        props = new Properties();
        try (InputStream input = getResource("/invoice-template-pl.properties")) {
            props.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    @SneakyThrows
    public Invoice create(InvoiceInput command) {
        Objects.requireNonNull(command);

        try (var in = getResource("/template.html")) {
            String text = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));

            Template t = new Template("name",
                    new StringReader(text),
                    new Configuration(Configuration.VERSION_2_3_31)
            );

            try (StringWriter out = new StringWriter()) {
                t.process(props, out);
                String toString = out.getBuffer().toString();
                System.out.println(toString);
                try (OutputStream os = new FileOutputStream("out.pdf")) {
                    PdfRendererBuilder builder = new PdfRendererBuilder();
                    builder.useFastMode();
                    Document doc = html5ParseDocument(toString);
                    builder.withW3cDocument(doc, "/");
                    builder.useFont(() -> getResource("/SourceSans3-Regular.ttf"), "Noto Sans");
                    builder.toStream(os);
                    builder.run();
                }

                out.flush();
            }
        }

        return new Invoice("tbd");
    }

    private InputStream getResource(String name) {
        return PdfInvoiceFactory.class.getResourceAsStream(name);
    }

    private static Document html5ParseDocument(String inputHTML) {
        var doc = Jsoup.parse(inputHTML);
        return new W3CDom().fromJsoup(doc);
    }
}
