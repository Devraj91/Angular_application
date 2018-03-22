package com.nasscom.einvoice.pdf;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.nasscom.einvoice.exception.PdfException;

@Component
public class PdfGenerator {
	private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);
	@Autowired
	private TemplateEngine templateEngine;

	public byte[] createPdf(String templateName, Map<String,Object> map) throws Exception {
		Assert.notNull(templateName, "The templateName can not be null");
		Context ctx = new Context();
		if (map != null) {
			map.entrySet().forEach(pair->{
				ctx.setVariable(pair.getKey().toString(), pair.getValue());
			});
		}
		logger.debug("Creating invoice pdf with template , parameters {},{} "+templateName,ctx);
		String processedHtml = templateEngine.process(templateName, ctx);
		ByteArrayOutputStream os =null;
		try {
			logger.debug("Daynamic Invoice html to genereate pdf=> {} "+processedHtml); 
			os = new ByteArrayOutputStream();
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(processedHtml);
			renderer.layout();
			renderer.createPDF(os, false);
			renderer.finishPDF();
			logger.debug("PDF created successfully with size =>{}",os.size());
		} catch(Exception ex) {
			logger.error("Unable to Create Invoice Pdf {}",ex);
			throw new PdfException("Unable to Create Invoice Pdf",ex);
		}
		return os.toByteArray();
	}

}
