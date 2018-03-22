package com.nasscom.einvoice.constants;

public interface DisplayMessages {
	String INVALID_FILE = "Please upload valid excel file. ";
	String OKSTATUS = "File Uploaded Successfully";
	String INTERNAL_SERVER_ERROR = "There is some error in file uploading";

	enum FileType {
		TaxInfo
	}


	 String errSelectFileTax = "Select file to import tax.";
	String success = null;
	String successMemberImport = null;
	String errSelectCorrectFormat = null;
	String errExcelImport = null;
	String paymentUpdatedSuccess = "Payment updated successfully";
	String paymentUpdatedError = "Error in updating Payment Details.";
	String taxAmountRequiredMssg = "Tax Amount is required for updating Online Payment of Taxes for a Member.";
	String errSelectFileMssg = "Select a Year to Retrieve Invoice Detail Data";
	String memberTaxTdsRequiredMssg = "TDS rate is required when TDS flag is true";
	String invoiceCancellationSuccessMsg = "Invoice cancelled for member successfully.";
	String invoiceCancellationErrorMsg = "Invoice cancellation failed for member.";
	String invoiceEditSuccessMsg = "Invoice details updated successfully";
	String invoiceEditErrorMsg = "Error in updating invoice details.";
	String baseAmountInvoiceIdRequiredMsg = "Invoice Id & BaseAmount required in case of Payment";
}
