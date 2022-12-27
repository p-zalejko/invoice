package com.gmail.pzalejko.invoice.pdfgenerator.application;

import com.lowagie.text.pdf.BaseFont;
import lombok.SneakyThrows;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@RestController
public class GeneratorController {

    @PostMapping("/v1/invoices")
    public void go(@RequestBody @Validated InvoiceInput input) {
        var a = parseThymeleafTemplate(input);
        generatePdfFromHtml(a);
    }

    private String parseThymeleafTemplate(InvoiceInput input) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = createInvoiceContext(input);

        return templateEngine.process("template_pl", context);
    }

    private Context createInvoiceContext(InvoiceInput input) {
        Context context = new Context();
        context.setVariable("sellerNameLine1", input.fromCompany().name());
        context.setVariable("sellerAccountNumber", input.fromCompany().bankAccountNumber());
        context.setVariable("invoiceNumberValue", input.invoiceNumber());
        context.setVariable("invoiceSellDateValue", input.issueDate());
        context.setVariable("invoiceExecDateValue", input.issueDate());
        context.setVariable("consumerNameLine", input.billToCompany().name());
        context.setVariable("whoCreatedInvoiceValue", input.whoCreated());
        context.setVariable("itemsPaymentHowValue", input.paymentMethod());
        context.setVariable("itemsPaymentDeadlineValue", input.dueDate());

        context.setVariable("itemsPaymentTotalPriceNet", input.summary().totalNet());
        context.setVariable("itemsPaymentTotalPriceTax", input.summary().totalVat());
        context.setVariable("itemsPaymentTotalPriceTotal", input.summary().total());
        context.setVariable("itemsPaymentSummaryToPayValue", input.summary().total());
        context.setVariable("invoiceItems", input.items());
        return context;
    }

    @SneakyThrows
    public void generatePdfFromHtml(String html) {
        String outputFolder = System.getProperty("user.home") + File.separator + "thymeleaf.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.getFontResolver().addFont("/SourceSans3-Regular.ttf", BaseFont.IDENTITY_H, true);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }
}
