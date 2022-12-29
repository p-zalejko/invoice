package com.gmail.pzalejko.invoice.pdfgenerator.application;

import com.lowagie.text.pdf.BaseFont;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        templateResolver.setTemplateMode("LEGACYHTML5");
        templateResolver.setCharacterEncoding("windows-1250");
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
        context.setVariable("vatSummary", toVatSummaryDtos(input.summaryPerVat()));


        return context;
    }

    @SneakyThrows
    public void generatePdfFromHtml(String html) {
        String outputFolder = System.getProperty("user.home") + File.separator + "thymeleaf.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.getFontResolver().addFont("fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }

    private List<VatSummaryDto> toVatSummaryDtos(List<InvoiceInput.SummaryPerVat> summaryPerVat) {
        var sorted = summaryPerVat.stream()
                .sorted(Comparator.comparingInt(InvoiceInput.SummaryPerVat::vatPercentage))
                .toList();

        var dtos = new ArrayList<VatSummaryDto>();
        for (int i = 0; i < sorted.size(); i++) {
            var item = sorted.get(i);
            if (i == 0) {
                dtos.add(new VatSummaryDto("w tym:", Integer.toString(item.vatPercentage()), item.totalNet(), item.vatTotal(), item.total()));
            } else {
                dtos.add(new VatSummaryDto("", Integer.toString(item.vatPercentage()), item.totalNet(), item.vatTotal(), item.total()));
            }
        }

        var totalNet = sorted.stream()
                .map(i -> BigDecimal.valueOf(i.totalNet()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();

        var totalVat = sorted.stream()
                .map(i -> BigDecimal.valueOf(i.vatTotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();

        var total = sorted.stream()
                .map(i -> BigDecimal.valueOf(i.total()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();

        dtos.add(new VatSummaryDto("Suma:", "", totalNet, totalVat, total));

        return dtos;
    }

    record VatSummaryDto(String rowTitle, String vatPercentage, double netValue, double vatValue, double total) {

    }
}
