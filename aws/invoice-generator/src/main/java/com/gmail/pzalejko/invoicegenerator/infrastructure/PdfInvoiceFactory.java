package com.gmail.pzalejko.invoicegenerator.infrastructure;

import com.gmail.pzalejko.invoicegenerator.model.Invoice;
import com.gmail.pzalejko.invoicegenerator.model.InvoiceFactory;
import com.gmail.pzalejko.invoicegenerator.model.InvoiceInput;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Document;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@ApplicationScoped
public class PdfInvoiceFactory implements InvoiceFactory {

    public static final String TEMPLATE_HTML = "/template.html";

    @Override
    @SneakyThrows
    public Invoice create(InvoiceInput command) {
        Objects.requireNonNull(command);

        Template t = getTemplate();
        Properties properties = getProperties(command);
        var file = createPdfFile(t, properties);

        return new Invoice("tbd");
    }

    private Path createPdfFile(Template t, Properties props) throws IOException, TemplateException {
        var temp = Files.createTempFile("my-inv", ".pdf");
        try (var out = new StringWriter()) {
            t.process(props, out);
            try (var os = new FileOutputStream( "our.pdf")) {
                String toString = out.getBuffer().toString();
                Document doc = html5ParseDocument(toString);

                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withW3cDocument(doc, "/");
                builder.useFont(() -> getResource("/SourceSans3-Regular.ttf"), "Noto Sans");
                builder.toStream(os);
                builder.run();
            }

            out.flush();
        }
        return temp;
    }

    private Template getTemplate() throws IOException {
        try (var in = getResource(TEMPLATE_HTML)) {
            String text = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));

            return new Template("name",
                    new StringReader(text),
                    new Configuration(Configuration.VERSION_2_3_31)
            );
        }
    }

    private Properties getProperties(InvoiceInput command) {
        Properties props = new Properties();
        try (var input = getResource("/invoice-template-pl.properties")) {
            props.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

        props.put("consumerNameLine1",command.clientDetails());
        props.put("consumerNameLine1",command.clientDetails());

        return props;
    }

    private InputStream getResource(String name) {
        return PdfInvoiceFactory.class.getResourceAsStream(name);
    }

    private static Document html5ParseDocument(String inputHTML) {
        var doc = Jsoup.parse(inputHTML);
        return new W3CDom().fromJsoup(doc);
    }
}
