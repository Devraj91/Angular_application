package com.nasscom.einvoice.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.util.StringUtils;

import com.nasscom.einvoice.constants.EmailCategory;
import com.nasscom.einvoice.constants.MemberConstants;
import com.nasscom.einvoice.domain.InvoiceTransactionDetailResponse;
import com.nasscom.einvoice.entity.Address;
import com.nasscom.einvoice.entity.BankDetail;
import com.nasscom.einvoice.entity.Branch;
import com.nasscom.einvoice.entity.CategoryFee;
import com.nasscom.einvoice.entity.City;
import com.nasscom.einvoice.entity.CityRegionMapping;
import com.nasscom.einvoice.entity.EmailTemplate;
import com.nasscom.einvoice.entity.InvoiceDetail;
import com.nasscom.einvoice.entity.InvoiceFile;
import com.nasscom.einvoice.entity.InvoiceReceiptFile;
import com.nasscom.einvoice.entity.InvoiceTransactionDetail;
import com.nasscom.einvoice.entity.Member;
import com.nasscom.einvoice.entity.PurchaseOrder;
import com.nasscom.einvoice.entity.TaxConfig;
import com.nasscom.einvoice.pdf.CalculatedTax;
import com.nasscom.einvoice.pdf.PdfGenerator;
import com.nasscom.einvoice.repository.AddressRepository;
import com.nasscom.einvoice.repository.CategoryFeeRepository;
import com.nasscom.einvoice.repository.CityRegionMappingRepository;
import com.nasscom.einvoice.repository.CityRepository;
import com.nasscom.einvoice.repository.EmailTemplateRepository;
import com.nasscom.einvoice.repository.InvoiceFileRepository;
import com.nasscom.einvoice.repository.InvoiceRepository;
import com.nasscom.einvoice.repository.InvoiceTransactionDetailRepository;
import com.nasscom.einvoice.repository.MemberRepository;
import com.nasscom.einvoice.repository.ReceiptFileRepository;
import com.nasscom.einvoice.utils.NumberToEnglishWord;
import com.nasscom.einvoice.utils.Utility;

@Service
public class InvoiceService {
	private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);
	private static final String INVOICE_TEMPLATE = "templates/pdf/invoice";
	private static final String PERFORMA_INVOICE_TEMPLATE = "templates/pdf/performa_invoice";

	@Autowired
	private PdfGenerator pdfgenerator;
	@Autowired
	ReceiptFileRepository receiptFileRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private TaxConfigService taxConfigService;
	@Autowired
	private InvoiceTransactionDetailRepository invtransactionDetailRepository;
	@Autowired
	private CityRegionMappingRepository cityRegionMappingRepository;
	@Autowired
	InvoiceFileRepository fiInvoiceFileRepository;
	@Autowired
	CategoryFeeRepository categoryFeeRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberService memberService;
	@Autowired
	InvoiceTransactionDetailRepository invoiceTransactionDetailRepository;

	Calendar now = Calendar.getInstance();

	/**
	 * For current financial year, checks if invoiceDetail exists( yes:returns the
	 * existing InvoiceDetail , no:generates InvoiceDetail)
	 * 
	 * @param member
	 * @return InvoiceDetail
	 * @throws Exception
	 */
	public InvoiceDetail getInvoiceDetailCurrentFY(Member member) throws Exception {
		Assert.notNull(member, "Member must not be null");
		String currentFinancialYear = Utility.getCurrentFinancialYear();// eg. format 2017-2018
		logger.info("currentFinancialYear--> " + currentFinancialYear);
		InvoiceDetail invoiceDetailForCurrentFY = invoiceRepository.findByMemberAndYear(member, currentFinancialYear);
		if (invoiceDetailForCurrentFY != null) {
			logger.info("Found Member's => : " + member.getEmailId() + " Invoice for Current FY => "
					+ invoiceDetailForCurrentFY);
			populateInvoiceTransientFields(invoiceDetailForCurrentFY, member);

			if (invoiceDetailForCurrentFY.getInvoiceFile().getData().length == 0)// if InvoiceDetails found but
																					// invoiceFile is not there
				processInvoiceDetail(member, invoiceDetailForCurrentFY);
		} else {// generate new Invoice for current FY
			logger.info("No Existing current financial year's invoice found for member => : " + member.getEmailId()
					+ " " + " Generating Invoice for FY: " + currentFinancialYear);
			invoiceDetailForCurrentFY = processInvoiceDetail(member, invoiceDetailForCurrentFY);
		}
		return invoiceDetailForCurrentFY;
	}

	public InvoiceDetail processInvoiceDetail(Member member, InvoiceDetail invoiceDetail) throws Exception {
		InvoiceTransactionDetail invoiceTransaction = null;
		City branchCity = null;
		City invoiceCity = null;
		try {

			if (null == invoiceDetail) {
				invoiceCity = member.getAddress().getCity();
				branchCity = invoiceCity;
			} else {
				invoiceCity = invoiceDetail.getAddress().getCity();
				branchCity = getBranchCity(invoiceDetail);
			}

			if (invoiceDetail != null) {
				invoiceTransaction = getLatestInvoiceTransactionDetail(invoiceDetail);
				if (invoiceTransaction != null) {
					// invoice not generate when member has initiated even one payment transaction
					throw new Exception("Invoice can not be generated ,Payment has already initiated");
				}
			}
			// calculate taxes

			// List<CalculatedTax> calculatedTaxs =
			// calculateTaxes(member,mtxn,invoiceDetail);
			List<CalculatedTax> calculatedTaxs = new ArrayList<CalculatedTax>();

			//
			if (null == invoiceDetail) {
				String invoiceNo = getInvoiceNo(member);
				String date = getFormatDate();
				calculatedTaxs = calculateTaxes(branchCity, invoiceCity, member);
				Double totalPaidAmt = getTotalInvoiceAmount(member, calculatedTaxs);
				invoiceDetail = new InvoiceDetail(invoiceNo, date, member, calculatedTaxs, totalPaidAmt, new Address());
				setAddress(invoiceDetail, member);// copying memberAddress as InvoiceAddress firstTime

			} else {
				if (invoiceDetail.getTaxApplicable())
					calculatedTaxs = calculateTaxes(branchCity, invoiceCity, member);
				invoiceDetail.setTaxes(calculatedTaxs);
				invoiceDetail.setTotalAmountWithTaxes(getTotalInvoiceAmount(member, calculatedTaxs));
				invoiceDetail.setInvoiceDate(getFormatDate());
			}

			// set amount in word
			invoiceDetail.setAmountChargeableInwords(StringUtils
					.capitalize(NumberToEnglishWord.convert(invoiceDetail.getTotalAmountWithTaxes().intValue())));
			logger.debug("Amt in word : {}", invoiceDetail.getAmountChargeableInwords());
			// setting invoice amount on basis of category
			CategoryFee categoryFee = categoryFeeRepository.findByCategory(member.getCategory());
			if (null == categoryFee) {
				throw new Exception("Invalid Member Category ");
			}
			invoiceDetail.setInvoiceAmt(categoryFee.getFee());
			invoiceDetail.setToEmail(member.getEmailId());
			if (invoiceDetail.getTaxApplicable())
				invoiceDetail.setTaxAmt(populateTotalTaxAmount(calculatedTaxs));
			else
				invoiceDetail.setTaxAmt(0.0);

			// generating invoice
			generateInvoiceFile(member, invoiceDetail);
			// generateInvoiceReceiptFile(member,invoiceDetail);
			invoiceDetail.setYear(getFinancialYear());
			// save db
			invoiceRepository.save(invoiceDetail);

		} catch (Exception e) {
			logger.error("Processing Invoice PDF generation error : {}", e);
			throw e;
		}

		return invoiceDetail;
	}

	public City getBranchCity(InvoiceDetail invoiceDetail) {
		Branch b = getNasscomBranchOfInvoice(invoiceDetail);
		return b.getCity();
	}

	/**
	 * Gets the latest paid transaction against an Invoice
	 * 
	 * @param member
	 * @param invoiceDetail
	 * @return
	 */
	private InvoiceTransactionDetail getLatestInvoiceTransactionDetail(InvoiceDetail invoiceDetail) {
		InvoiceTransactionDetail transactionDetail = null;
		long invoiceId = invoiceDetail.getInvoiceId();
		List<InvoiceTransactionDetail> transactionDetails = invtransactionDetailRepository
				.findByInvoiceIdOrderByCreatedDesc(invoiceId);
		if (!transactionDetails.isEmpty()) {
			transactionDetail = transactionDetails.get(0);// incase of multiple mtxns of a currentFY ,pick latest
															// created_time
		}
		return transactionDetail;
	}

	/**
	 * Populates the different fields(amount fields) related to invoice which are
	 * not saved in DB but shown in Invoice PDF. This needs to be called even when
	 * new new invoice is not generated to reflect the updated amount fields
	 * 
	 * @param invoiceDetail
	 * @param member
	 * @param transactionDetail
	 * @throws Exception
	 */
	private void populateInvoiceTransientFields(InvoiceDetail invoiceDetail, Member member) throws Exception {
		// calculate taxes
		City branchCity = null, invoiceCity = null;

		if (null == invoiceDetail) {
			invoiceCity = member.getAddress().getCity();
			branchCity = invoiceCity;
		} else {
			invoiceCity = invoiceDetail.getAddress().getCity();
			branchCity = getBranchCity(invoiceDetail);
		}
		List<CalculatedTax> calculatedTaxs = new ArrayList<CalculatedTax>();

		if (invoiceDetail.getTaxApplicable()) {
			calculatedTaxs = calculateTaxes(branchCity, invoiceCity, member);
		}
		Double totalAmountWithTaxes = getTotalInvoiceAmount(member, calculatedTaxs);
		String totalAmountWithTaxesInWords = StringUtils
				.capitalize(NumberToEnglishWord.convert(totalAmountWithTaxes.intValue()));
		invoiceDetail.setTaxes(calculatedTaxs);
		invoiceDetail.setTotalAmountWithTaxes(totalAmountWithTaxes);
		invoiceDetail.setAmountChargeableInwords(totalAmountWithTaxesInWords);
	}

	private Address setAddress(InvoiceDetail eivoiceMember, Member member) {
		City city = cityRepository
				.findByName((member.getAddress().getCity() != null) ? member.getAddress().getCity().getName() : "");
		if (null != city)
			eivoiceMember.getAddress().setCity(city);
		eivoiceMember.getAddress().setFax(member.getAddress().getFax());
		eivoiceMember.getAddress().setPin(member.getAddress().getPin());
		eivoiceMember.getAddress().setStreet(member.getAddress().getStreet());
		eivoiceMember.getAddress().setPhone(member.getAddress().getPhone());
		eivoiceMember.getAddress().setType(Address.Type.INVOICE);
		return eivoiceMember.getAddress();
	}

	private String getFinancialYear() {
		return Utility.getCurrentFinancialYear();
	}

	public void generateInvoiceFile(Member member, InvoiceDetail invoiceDetail) throws Exception {
		byte[] pdfByte;
		if (invoiceDetail.getIsPerforma()) {
			pdfByte = pdfgenerator.createPdf(PERFORMA_INVOICE_TEMPLATE, getInvoiceTemplateInput(invoiceDetail, member));
		} else {
			pdfByte = pdfgenerator.createPdf(INVOICE_TEMPLATE, getInvoiceTemplateInput(invoiceDetail, member));
		}
		if (pdfByte.length <= 0) {
			if (invoiceDetail.getInvoiceFile() == null)// for first time generation
			{
				invoiceDetail.setInvoiceFile(
						new InvoiceFile(getFileName(member), pdfByte, InvoiceFile.Status.FAILED, invoiceDetail));
			} else {
				invoiceDetail.getInvoiceFile().setData(pdfByte);
				invoiceDetail.getInvoiceFile().setFileName(getFileName(member));
				invoiceDetail.getInvoiceFile().setStatus(InvoiceFile.Status.FAILED);
				invoiceDetail.getInvoiceFile().setInvoiceDetail(invoiceDetail);
			}
		} else {
			if (invoiceDetail.getInvoiceFile() == null)// for first time generation
			{
				invoiceDetail.setInvoiceFile(
						new InvoiceFile(getFileName(member), pdfByte, InvoiceFile.Status.GENERATED, invoiceDetail));
			} else {
				populateInvoiceTransientFields(invoiceDetail, invoiceDetail.getMember());
				invoiceDetail.getInvoiceFile().setData(pdfByte);
				invoiceDetail.getInvoiceFile().setFileName(getFileName(member));
				invoiceDetail.getInvoiceFile().setStatus(InvoiceFile.Status.REGENERATED);
				invoiceDetail.getInvoiceFile().setInvoiceDetail(invoiceDetail);
			}
		}

	}

	public InvoiceReceiptFile generateInvoiceReceiptFile(InvoiceDetail invoiceDetail, byte[] receiptPdfbyte,
			String fileName, String receiptDate, String receiptNo) throws Exception {
		if (receiptPdfbyte.length <= 0) {
			throw new Exception("Technical Problem.Invoice receipt file not generated");
		}
		InvoiceReceiptFile invoiceReceiptFile = new InvoiceReceiptFile(fileName, receiptPdfbyte,
				InvoiceReceiptFile.Status.GENERATED, invoiceDetail);
		invoiceReceiptFile.setReceiptDate(receiptDate);
		invoiceReceiptFile.setReceiptNo(receiptNo);
		if (invoiceReceiptFile != null) {
			receiptFileRepository.save(invoiceReceiptFile);
			invoiceDetail.setInvoiceReceiptFile(invoiceReceiptFile);
			invoiceRepository.save(invoiceDetail);
		}
		return invoiceReceiptFile;
	}

	public Double getTotalInvoiceAmount(Member member, List<CalculatedTax> calculatedTaxs) throws Exception {
		CategoryFee categoryFee = categoryFeeRepository.findByCategory(member.getCategory());
		if (null == categoryFee) {
			throw new Exception("Invalid Member Category ");
		}
		double total = categoryFee.getFee();
		for (CalculatedTax tax : calculatedTaxs) {
			total = total + tax.getTaxCharge();
		}
		return total;
	}

	/*
	 * private List<CalculatedTax> calculateTaxes(Member member,
	 * MemberTransactionDetail transactionDetail, InvoiceDetail invoiceDetail)
	 * throws Exception {
	 * 
	 * CategoryFee categoryFee =
	 * categoryFeeRepository.findByCategory(member.getCategory());
	 * 
	 * if(null==categoryFee) { throw new Exception("Invalid Member Category "); }
	 * double actual=
	 * null==transactionDetail?categoryFee.getFee():transactionDetail.
	 * getInvoiceAmount();
	 * 
	 * List<CalculatedTax> calculatedTaxes = new LinkedList<>();
	 * 
	 * get TaxConfig on the basis of the city which belongs to member address or
	 * nasscom branch address city List<TaxConfig> taxConfigs;
	 * 
	 * if(invoiceDetail!=null) taxConfigs =
	 * taxConfigService.getTaxConfigByCity(invoiceDetail.getAddress().getCity());
	 * else
	 * 
	 * taxConfigs =
	 * taxConfigService.getTaxConfigByCity(member.getAddress().getCity());
	 * 
	 * apply igst if no tax found on city/region basis if(null==taxConfigs ||
	 * taxConfigs.isEmpty())taxConfigs=Arrays.asList(new TaxConfig("IGST",18.0));
	 * 
	 * taxConfigs.forEach(tax -> { CalculatedTax calculatedTax = new
	 * CalculatedTax(tax.getTaxName(), tax.getRate(),((tax.getRate() / 100) *
	 * actual)); calculatedTaxes.add(calculatedTax); }); return calculatedTaxes; }
	 */

	public List<CalculatedTax> calculateTaxes(City branchCity, City invoiceCity, Member member) throws Exception {
		CategoryFee categoryFee = categoryFeeRepository.findByCategory(member.getCategory());
		if (null == categoryFee) {
			throw new Exception("Invalid Member Category ");
		}
		Double actual = categoryFee.getFee();
		List<CalculatedTax> calculatedTaxes = new LinkedList<>();
		List<TaxConfig> taxConfigs = new ArrayList<TaxConfig>();
		TaxConfig tax;
		if (branchCity.getId() == invoiceCity.getId()) {
			tax = taxConfigService.getTaxConfigByName("SGST");
			taxConfigs.add(tax);
			tax = taxConfigService.getTaxConfigByName("CGST");
			taxConfigs.add(tax);
		} else {
			tax = taxConfigService.getTaxConfigByName("IGST");
			taxConfigs = Arrays.asList(new TaxConfig(tax.getTaxName(), tax.getRate()));
		}
		taxConfigs.forEach(taxes -> {
			double taxCharge = (taxes.getRate() / 100) * actual;
			double roundOffTax = Math.round(taxCharge*100.0)/100.0;
			CalculatedTax calculatedTax = new CalculatedTax(taxes.getTaxName(), taxes.getRate(), roundOffTax);
			calculatedTaxes.add(calculatedTax);
		});
		return calculatedTaxes;
	}

	private String getFileName(Member member) {
		return "Invoice-" + member.getMembershipID();
	}

	public Branch getNasscomBranchOfMember(Member member) {
		Branch branch = null;
		CityRegionMapping cityRegionMapping = null;
		try {
			City invoiceCity = null;
			String currentFinYear = Utility.getCurrentFinancialYear();
			InvoiceDetail invDetail = invoiceRepository.findByMemberAndYear(member, currentFinYear);
			if (invDetail != null)
				invoiceCity = invDetail.getAddress().getCity();
			else
				invoiceCity = member.getAddress().getCity();
			cityRegionMapping = cityRegionMappingRepository.findByCityId(invoiceCity.getId());
			Assert.notNull(cityRegionMapping,
					"The cityRegionMapping not found for the given member's city " + invoiceCity);
			branch = cityRegionMapping.getBranch();
			Assert.notNull(branch, "The  Branch/Region not found for the given member's city " + invoiceCity);
		} catch (Exception e) {
			logger.error("Exception encountered while getting Nasscom Branch of a Member " + e);
		}
		return branch;
	}

	public Branch getNasscomBranchOfInvoice(InvoiceDetail invoiceDetail) {
		Branch branch = null;
		CityRegionMapping cityRegionMapping = null;
		try {
			City invoiceCity = null;
			if (invoiceDetail != null)
				invoiceCity = invoiceDetail.getAddress().getCity();
			cityRegionMapping = cityRegionMappingRepository.findByCityId(invoiceCity.getId());
			Assert.notNull(cityRegionMapping,
					"The cityRegionMapping not found for the given member's city " + invoiceCity);
			branch = cityRegionMapping.getBranch();
			Assert.notNull(branch, "The  Branch/Region not found for the given member's city " + invoiceCity);
		} catch (Exception e) {
			logger.error("Exception encountered while getting Nasscom Branch of a Member " + e);
		}
		return branch;
	}

	public String getFormatDate() {
		return new SimpleDateFormat("dd-MMM-yyyy").format(now.getTime());
	}

	private String getInvoiceNo(Member member) {
		return "NASSCOM/" + now.get(Calendar.YEAR) + "-" + now.get(Calendar.DATE) + "/MIMD/WR/"
				+ String.format("%04d", System.currentTimeMillis() % 10000);
	}

	public String getReceiptNo() {
		return "NASSCOM-RECEIPT/" + now.get(Calendar.YEAR) + "-" + now.get(Calendar.DATE) + "/MIMD/WR/"
				+ String.format("%04d", System.currentTimeMillis() % 10000);
	}

	public String getReceiptFileName(Member member) {

		String fileName = "Invoice-Receipt" + member.getMembershipID() + "/"
				+ String.format("%04d", System.currentTimeMillis() % 10000);
		return fileName;
	}

	public Map<String, Object> getInvoiceTemplateInput(InvoiceDetail invoiceDetail, Member member) {
		Map<String, Object> templateProperty = new HashMap<>();
		templateProperty.put("branch", getNasscomBranchOfMember(member));
		templateProperty.put("invoice", invoiceDetail);
		templateProperty.put("address", invoiceDetail.getAddress());// Invoice Address
		templateProperty.put("bank", new BankDetail());
		templateProperty.put("member", member);
		//templateProperty.put("path", "file:///e-Invoice/src/main/resources/static/templates/img/nasscom_logo.PNG");
		templateProperty.put("path", "file:///D:/E-Invoice/einvoice-published/classes/static/templates/img/nasscom_logo.PNG");
		return templateProperty;
	}

	public Map<String, Object> getInvoiceReceiptTemplateInput(InvoiceDetail invoiceDetail, Member member,
			String receiptDate, String receiptNo) throws Exception {
		Map<String, Object> templateProperty = new HashMap<>();
		//

		InvoiceTransactionDetailResponse invoiceTransactionDetailResponse = getOutstandingPaymentDetails(
				invoiceDetail.getInvoiceId());

		Double balanceInvoiceAmount = invoiceTransactionDetailResponse.getInvoiceAmt();
		Double overDraftAmount = invoiceTransactionDetailResponse.getOverDraftAmt();
		Double balanceTaxAmount = invoiceTransactionDetailResponse.getTaxAmt();
		Double paidAmount = invoiceTransactionDetailResponse.getPaidAmt();
		String paidAmountInWords = StringUtils.capitalize(NumberToEnglishWord.convert(paidAmount.intValue()));

		Double totalPaidAmount = getTotalPaidAmountPerInvoice(invoiceDetail.getInvoiceId());
		Double balanceAmount = balanceInvoiceAmount + balanceTaxAmount - overDraftAmount;

		Double totalTaxAmount = 0.0;
		City branchCity = getBranchCity(invoiceDetail);
		City invoiceCity = invoiceDetail.getAddress().getCity();

		List<CalculatedTax> taxes = new ArrayList<CalculatedTax>();
		if (invoiceDetail.getTaxApplicable()) {
			taxes = calculateTaxes(branchCity, invoiceCity, member);
			for (CalculatedTax calculatedTax : taxes) {
				totalTaxAmount += calculatedTax.getTaxCharge();
			}
		}

		Double paidTaxAmount = totalTaxAmount - balanceTaxAmount;
		Double paidInvoiceAmount = totalPaidAmount - paidTaxAmount - overDraftAmount;

		Double totalDueForThisFY = invoiceDetail.getInvoiceAmt() + invoiceDetail.getTaxAmt();

		templateProperty.put("totalDueForThisFY", totalDueForThisFY);
		templateProperty.put("totalInvoiceAmountDue", invoiceDetail.getInvoiceAmt());
		templateProperty.put("totalTaxDue", invoiceDetail.getTaxAmt());
		templateProperty.put("total_paid_amount", totalPaidAmount);
		templateProperty.put("paid_invoice_amount", paidInvoiceAmount);
		templateProperty.put("paid_tax_amount", paidTaxAmount);
		templateProperty.put("balance_amount", balanceAmount);
		templateProperty.put("paid_amount", paidAmount);
		templateProperty.put("member", member);
		templateProperty.put("branch", getNasscomBranchOfInvoice(invoiceDetail));
		templateProperty.put("invoice", invoiceDetail);
		templateProperty.put("address", invoiceDetail.getAddress());// Invoice Address
		templateProperty.put("bank", new BankDetail());
		templateProperty.put("member", member);
		templateProperty.put("receiptDate", receiptDate);
		templateProperty.put("receiptNo", receiptNo);
		templateProperty.put("paidAmountInWords", paidAmountInWords);

		return templateProperty;
	}

	public InvoiceDetail getPdf(String invoiceId) throws Exception {
		InvoiceDetail invoiceDetail = invoiceRepository.findByInvoiceId(Long.valueOf(invoiceId));
		byte[] pdfByte;
		// if(invoiceDetail.getInvoiceFile().getData().length==0)
		// processInvoiceDetail(invoiceDetail.getMember(),invoiceDetail);
		if (invoiceDetail != null) {
			populateInvoiceTransientFields(invoiceDetail, invoiceDetail.getMember());
			if (invoiceDetail.getIsPerforma())
				pdfByte = pdfgenerator.createPdf(PERFORMA_INVOICE_TEMPLATE,
						getInvoiceTemplateInput(invoiceDetail, invoiceDetail.getMember()));
			else
				pdfByte = pdfgenerator.createPdf(INVOICE_TEMPLATE,
						getInvoiceTemplateInput(invoiceDetail, invoiceDetail.getMember()));
			invoiceDetail.getInvoiceFile().setData(pdfByte);
		}
		return invoiceDetail;
	}

	@Transactional(value = TxType.SUPPORTS)
	public Object cancelInvoice(Long invoiceId, String remarks) throws Exception {
		InvoiceDetail invoiceDetail = invoiceRepository.findByInvoiceId(invoiceId);
		InvoiceFile invoiceFile = invoiceDetail.getInvoiceFile();
		Member mem = invoiceDetail.getMember();
		Branch branch = getNasscomBranchOfInvoice(invoiceDetail);
		invoiceFile.setIsCancel(true);// mark cancel
		invoiceFile.setRemarks(remarks);
		invoiceFile.setInvoiceDetail(invoiceDetail);
		invoiceDetail.setInvoiceDate(getFormatDate());

		List<CalculatedTax> calculatedTaxs = new ArrayList<CalculatedTax>();
		if (invoiceDetail.getTaxApplicable())
			calculatedTaxs = calculateTaxes(branch.getCity(), invoiceDetail.getAddress().getCity(), mem);
		invoiceDetail.setTaxes(calculatedTaxs);
		if (invoiceDetail.getTaxApplicable())
			invoiceDetail.setTaxAmt(populateTotalTaxAmount(calculatedTaxs));
		else
			invoiceDetail.setTaxAmt(0.0);

		String invoiceFileName = invoiceFile.getFileName();
		fiInvoiceFileRepository.save(invoiceFile);
		invoiceRepository.save(invoiceDetail);
		// generate new invoice
		try {
			this.generateInvoiceFile(invoiceDetail.getMember(), invoiceDetail);
			// increase count of file
			invoiceDetail.getInvoiceFile().setFileName(prepareFileName(invoiceFileName));
			// update invoiceDetail
			return invoiceRepository.save(invoiceDetail);
		} catch (Exception e) {
			throw new Exception("Unable to create new invoice in cancelinvoice process. :=>" + invoiceId);
		}
	}

	private String prepareFileName(String invoiceFileName) {
		if (invoiceFileName.charAt(invoiceFileName.length() - 2) == '-') {
			String originalFileName = invoiceFileName.substring(0, invoiceFileName.length() - 2);
			char count = invoiceFileName.charAt(invoiceFileName.length() - 1);
			int nxtNo = Integer.parseInt(String.valueOf(count));
			invoiceFileName = originalFileName + "-" + ++nxtNo;
		} else {
			invoiceFileName = invoiceFileName + "-" + 1;
		}
		return invoiceFileName;
	}

	/**
	 * Service method which will allow Member to update Total Payment based on TDS.
	 * 
	 * @param invoiceId
	 * @param memberId
	 * @param totalAmmount
	 * @param tdsRate
	 * @param transactionType
	 * @param isTdsDeducted
	 * @return String
	 */
	/*
	 * public String addMemberTransactionDetail(Long invoiceId, Long memberId,Double
	 * baseAmount, Double tdsRate, String transactionType, Boolean isTdsDeducted) {
	 * MemberTransactionDetail memberTransDetail = null;InvoiceDetail
	 * invDetail=null; Double paidAmmount, tdsAmmount = null, balanceAmmount,
	 * totalAmmount; invDetail = invoiceRepository.findByInvoiceId(invoiceId);
	 * Double invoiceAmmount=(double)invDetail.getInvoiceAmt();
	 * //invoiceAmmount=invoiceAmmount+18*invoiceAmmount/100; paidAmmount =
	 * baseAmount; balanceAmmount = invoiceAmmount - paidAmmount; if (isTdsDeducted)
	 * { totalAmmount=((baseAmount)/(100-tdsRate))*100;
	 * memberTransDetail=invDetail.getMemberTransactionDetail();
	 * tdsAmmount=totalAmmount-paidAmmount; if (null == memberTransDetail) {
	 * memberTransDetail = new MemberTransactionDetail(memberId,invoiceId,
	 * totalAmmount, isTdsDeducted, tdsRate, invoiceAmmount, tdsAmmount,
	 * transactionType, balanceAmmount,paidAmmount); } else { memberTransDetail =
	 * populateExistingMemberTransDetail(memberTransDetail, isTdsDeducted,
	 * paidAmmount, totalAmmount, transactionType, tdsAmmount,tdsRate); } } else {
	 * paidAmmount = baseAmount; balanceAmmount = invoiceAmmount - paidAmmount;
	 * totalAmmount=paidAmmount;
	 * memberTransDetail=invDetail.getMemberTransactionDetail(); if (null ==
	 * memberTransDetail) { memberTransDetail = new
	 * MemberTransactionDetail(memberId,invoiceId, totalAmmount, isTdsDeducted,
	 * null, invoiceAmmount, tdsAmmount, transactionType,
	 * balanceAmmount,paidAmmount); } else { memberTransDetail =
	 * populateExistingMemberTransDetail(memberTransDetail, isTdsDeducted,
	 * paidAmmount, totalAmmount, transactionType, tdsAmmount,tdsRate);
	 * memberTransDetail.setTdsRate(null); } }
	 * invDetail.setMemberTransactionDetail(memberTransDetail);
	 * invoiceRepository.save(invDetail);
	 * logger.debug("Transaction Detail for Invoice Id {} updated successfully",
	 * invoiceId); return "success"; }
	 */

	/**
	 * Fetches the outstanding payment details from invoiceDetail table (if first
	 * time payment happening) or invoiceTransactionDetail Table if not first time
	 * 
	 * @param invoiceId
	 * @return
	 */
	public InvoiceTransactionDetailResponse getOutstandingPaymentDetails(Long invoiceId) {

		InvoiceTransactionDetail existingLatestInvoiceTransactionDetail = null;
		InvoiceDetail invDetail = null;
		Member member = null;
		List<InvoiceTransactionDetail> invTransDetails;
		InvoiceTransactionDetailResponse invoiceTransactionDetailResponse = new InvoiceTransactionDetailResponse();
		Double invoiceAmount, taxAmount, overDraftAmount = 0.0, paidAmount = 0.0;

		try {
			invDetail = invoiceRepository.findByInvoiceId(invoiceId);
			invTransDetails = invtransactionDetailRepository.findByInvoiceIdOrderByCreatedDesc(invoiceId);
			member = invDetail.getMember();

			if (invTransDetails.isEmpty()) {// first time payment happening
				invoiceAmount = (double) invDetail.getInvoiceAmt();
				taxAmount = invDetail.getTaxAmt();
			} else {
				existingLatestInvoiceTransactionDetail = getLatestInvoiceTransactionDetail(invDetail);
				taxAmount = existingLatestInvoiceTransactionDetail.getBalanceTax();
				invoiceAmount = existingLatestInvoiceTransactionDetail.getBalanceInvoiceAmt();
				paidAmount = existingLatestInvoiceTransactionDetail.getPaidAmount();
				overDraftAmount = getTotalOverDraftAmountPerInvoice(invoiceId);
			}

			invoiceTransactionDetailResponse.setInvoiceAmt(invoiceAmount);
			invoiceTransactionDetailResponse.setTaxAmt(taxAmount);
			invoiceTransactionDetailResponse.setInvoiceId(invoiceId);
			invoiceTransactionDetailResponse.setOverDraftAmt(overDraftAmount);
			invoiceTransactionDetailResponse.setPaidAmt(paidAmount);
			if (member != null) {
				invoiceTransactionDetailResponse.setMemberId(member.getMemberId());
				invoiceTransactionDetailResponse.setMemberName(member.getName());
			}
		} catch (Exception e) {
			logger.error("Exception encountered while getting getOutstandingPaymentDetails", e);
		}

		return invoiceTransactionDetailResponse;
	}

	public String addInvoiceTransactionDetail(Long invoiceId, Long memberId, Double baseAmount, Double onlineTaxAmount,
			Double tdsRate, String transactionType, Boolean isTdsDeducted, String modeOfPayment,
			String transactionRemarks) {

		Double outstandingInvoiceAmount = 0.0, outstandingTaxAmount = 0.0;
		Double tempTobeAdjustedTaxAmt, tempTobeAdjustedInvAmt, balanceTax, paidAmount, balanceInvoiceAmt;
		Double overDraftAmt = 0.0;
		InvoiceTransactionDetail invoiceTransactionDetail = null;

		try {

			InvoiceTransactionDetailResponse InvoiceTransactionDetailResponse = getOutstandingPaymentDetails(invoiceId);
			String paymentMode = MemberConstants.transType.valueOf(transactionType).toString();
			logger.info("paymentMode in addInvoiceTransactionDetail " + paymentMode);
			if (InvoiceTransactionDetailResponse != null) {
				outstandingInvoiceAmount = InvoiceTransactionDetailResponse.getInvoiceAmt();
				outstandingTaxAmount = InvoiceTransactionDetailResponse.getTaxAmt();
			}

			paidAmount = baseAmount;

			if (transactionType.equalsIgnoreCase(MemberConstants.transType.MANUAL.toString())) {
				tempTobeAdjustedTaxAmt = baseAmount - outstandingTaxAmount;

				if (tempTobeAdjustedTaxAmt == 0.0) {
					balanceTax = 0.0;
					overDraftAmt = 0.0;
					balanceInvoiceAmt = outstandingInvoiceAmount; // copy db values
				}

				else if (tempTobeAdjustedTaxAmt < 0) {
					overDraftAmt = 0.0;
					balanceTax = java.lang.Math.abs(tempTobeAdjustedTaxAmt);
					balanceInvoiceAmt = outstandingInvoiceAmount; // copy db values
				}

				else {// >0 case in which invoice amt also needs to be adjusted
					balanceTax = 0.0;
					tempTobeAdjustedInvAmt = tempTobeAdjustedTaxAmt - outstandingInvoiceAmount;
					// adjusting invoice amt now
					if (tempTobeAdjustedInvAmt == 0.0) {
						balanceInvoiceAmt = 0.0;
						overDraftAmt = 0.0;
					}

					else if (tempTobeAdjustedInvAmt < 0) {
						balanceInvoiceAmt = java.lang.Math.abs(tempTobeAdjustedInvAmt);
					}

					else {
						balanceInvoiceAmt = 0.0;
						overDraftAmt = tempTobeAdjustedInvAmt;
					}
				}

				invoiceTransactionDetail = new InvoiceTransactionDetail(memberId, isTdsDeducted, 0.0, 0.0, balanceTax,
						balanceInvoiceAmt, paidAmount, overDraftAmt, transactionType, invoiceId, modeOfPayment,
						transactionRemarks);

			}

			if (transactionType.equalsIgnoreCase(MemberConstants.transType.AUTOMATIC.toString())) {

				Double tdsAmount = 0.0;
				if (isTdsDeducted) {
					tdsAmount = (tdsRate / 100) * baseAmount;
					paidAmount = baseAmount - tdsAmount;
				}
				/*
				 * if(invoiceAmount + taxAmount == baseAmount + onlineTaxAmount + tdsAmount) {
				 * balanceTax=0.0; balanceInvoiceAmt=0.0; overDraftAmt=0.0;
				 * 
				 * itxn = new InvoiceTransactionDetail(memberId, isTdsDeducted, tdsRate,
				 * tdsAmount, balanceTax, balanceInvoiceAmt, paidAmount, overDraftAmt,
				 * transactionType, invoiceId); } else {
				 * logger.error("Partial payment not allowed in case of online payment"); }
				 */

				paidAmount = baseAmount;
				balanceTax = outstandingTaxAmount - onlineTaxAmount;
				balanceInvoiceAmt = outstandingInvoiceAmount - baseAmount;

				invoiceTransactionDetail = new InvoiceTransactionDetail(memberId, isTdsDeducted, tdsRate, tdsAmount,
						balanceTax, balanceInvoiceAmt, paidAmount, overDraftAmt, transactionType, invoiceId,
						modeOfPayment, transactionRemarks);

			}
			invtransactionDetailRepository.save(invoiceTransactionDetail);
			logger.debug("Transaction Detail for Invoice Id {} updated successfully", invoiceId);

		} catch (Exception e) {
			String paymentMode = MemberConstants.transType.valueOf(transactionType).toString();
			logger.error("Exception encountered while saving " + paymentMode + " payment details ", e);
		}
		return MemberConstants.SUCCESS;
	}

	public List<InvoiceDetail> getInvoiceDetails() {
		return invoiceRepository.findInvoiceDetailsOrderByCreated();
	}

	public List<InvoiceDetail> findAll() {
		return invoiceRepository.findAll();
	}

	/**
	 * Service method to edit PO Number and Tax detail for an invoice
	 * 
	 * @param invoiceId
	 * @param poNo
	 * @param year
	 * @param isTaxApplicable
	 * @return InvoiceDetail
	 */
	public InvoiceDetail editInvoiceDetail(Long invoiceId, String poNo, Integer year, Boolean isTaxApplicable,
			String cityName, String fax, String phone, String pin, String street, String ccEmails, Boolean isPerforma,
			String gstNo, String toEmail) {

		InvoiceDetail updatedIvoiceDetail = null;
		try {
			InvoiceDetail invoiceDetail = invoiceRepository.findByInvoiceId(invoiceId);
			City invoiceCity = cityRepository.findByName(cityName);

			String[] ccEmailList = null;
			if (null == invoiceDetail)
				return null;
			Member updatedMember = invoiceDetail.getMember();
			PurchaseOrder pOrder = invoiceDetail.getPurchaseOrderDetail();
			if (null == pOrder) {
				pOrder = new PurchaseOrder(poNo, year);
				invoiceDetail.setPurchaseOrderDetail(pOrder);
			} else {
				pOrder.setPoNo(poNo);
				pOrder.setYear(year);
				// pOrder.setTaxApplicable(isTaxApplicable);
				invoiceDetail.setPurchaseOrderDetail(pOrder);
			}

			invoiceDetail.setTaxApplicable(isTaxApplicable);
			invoiceDetail.setIsPerforma(isPerforma);
			invoiceDetail.getAddress().setFax(fax);
			invoiceDetail.getAddress().setPhone(phone);
			invoiceDetail.getAddress().setPin(pin);
			invoiceDetail.getAddress().setStreet(street);
			invoiceDetail.getAddress().setCity(invoiceCity);
			if (gstNo != null && !("").equals(gstNo))
				invoiceDetail.setGstNo(gstNo);
			if (toEmail != null && !("").equals(toEmail))
				invoiceDetail.setToEmail(toEmail);
			// calculate taxes starts
			List<CalculatedTax> calculatedTaxs = new ArrayList<CalculatedTax>();
			if (!isTaxApplicable) {
				invoiceDetail.setTaxAmt(0.0);
			} else {
				City branchCity = null;
				Branch branch = getNasscomBranchOfInvoice(invoiceDetail);
				if (branch != null)
					branchCity = branch.getCity();
				if (branchCity != null && invoiceCity != null) {
					calculatedTaxs = calculateTaxes(branchCity, invoiceCity, invoiceDetail.getMember());
					if (!calculatedTaxs.isEmpty()) {
						invoiceDetail.setTaxAmt(populateTotalTaxAmount(calculatedTaxs));
					}
				} else
					logger.warn("Unable to calculate taxes while editing invoice. BranchCity  or InvoiceCity is null");
			}
			invoiceDetail.setTaxes(calculatedTaxs);

			// calculate taxes ends
			updatedIvoiceDetail = invoiceRepository.save(invoiceDetail);

			if (ccEmails != null && !("").equals(ccEmails)) {
				ccEmailList = ccEmails.split(",");
				updatedMember.setCcEmails(ccEmailList);
			}
			updatedMember = memberRepository.save(updatedMember);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception encountered while editing Invoice ", e);
		}
		return updatedIvoiceDetail;

	}

	public Double populateTotalTaxAmount(List<CalculatedTax> taxesList) {
		Double totalTaxAmount = 0.0;
		for (CalculatedTax calculatedTax : taxesList) {
			totalTaxAmount += calculatedTax.getTaxCharge();
		}
		return totalTaxAmount;
	}

	public void executeInvoiceTask() {
		logger.debug("executeInvoiceMailTask()");
		long startTime = System.currentTimeMillis();
		Map<Member, String> invoiceGenerationFailedMembersMap = new HashMap<Member, String>();
		try {
			EmailTemplate emailTemplate = emailTemplateRepository
					.findByTemplateType(EmailCategory.EmailTemplates.Invoice.name());
			emailTemplate.setCategory(EmailCategory.ALL_MEMBER);
			List<Member> members = memberRepository.findByIsActive(true);
			// check member latest sync
			if (!members.isEmpty()) {
				List<String> membershipIDs = members.stream()
						// check member last sync before 30 min
						// .filter(member ->
						// member.getUpdated().plusMinutes(30).compareTo(LocalDateTime.now()) == -1)
						.map(Member::getMembershipID).collect(Collectors.toList());

				if (!membershipIDs.isEmpty())
					members = memberService.syncMemberManual(membershipIDs.toArray(new String[0]));
			}

			// List<EmailStatus> emailStatus = sendEmail(emailTemplate, members);
			// logger.debug("Invoice Mail Processed for members=>{}", emailStatus.size());
			for (Member member : members) {
				try {
					if (member.getIsActive()) {
						logger.info("executeInvoiceMailTask{} . Scheduled Invoice Task Running for member : " + member);
						InvoiceDetail invoiceDetailCurrentFY = getInvoiceDetailCurrentFY(member);
						if (invoiceDetailCurrentFY != null) {
							logger.info("executeInvoiceMailTask{}. Scheduled generated InvoiceDetail for "
									+ invoiceDetailCurrentFY);
						}
					} else {
						invoiceGenerationFailedMembersMap.put(member, "Inactive member. Thus Invoice not generated");
						logger.warn("Member " + member.getEmailId()
								+ " is an Inactive member. Thus Invoice not generated.");
					}
				} catch (Exception e) {
					invoiceGenerationFailedMembersMap.put(member, e.getMessage());
					logger.error("Exception encountered .Scheduled Invoice not generate for Member"
							+ member.getEmailId() + e.getMessage());
				}
			}
		} catch (Exception ex) {
			logger.debug("Get issue to process Invoice Mail Task , will try next schedule {}", ex);
		}

		logger.debug("Invoice Generation failed for members : " + invoiceGenerationFailedMembersMap.size()
				+ "/n Failed Details : " + invoiceGenerationFailedMembersMap.toString());
		logger.debug("Invoice Mail Task Exection Time taken in (ms) : {}", (System.currentTimeMillis() - startTime));
	}

	public boolean checkPaymentUrlExpiry(String invoiceId) {
		InvoiceFile invoiceFile = fiInvoiceFileRepository.getOne(Long.valueOf(invoiceId));
		if (null != invoiceFile && invoiceFile.getIsCancel())
			return invoiceFile.getIsCancel();
		return false;
	}

	public InvoiceDetail findLatestInvoiceDetail(Member member) {
		return (!invoiceRepository.findByMemberByOrderByCreated(member).isEmpty())
				? invoiceRepository.findByMemberByOrderByCreated(member).get(0)
				: null;
	}

	public List<City> findAllCities() {
		return cityRepository.findAll();
	}

	public InvoiceDetail getInvoiceDetail(Long invoiceId) {
		return invoiceRepository.findByInvoiceId(invoiceId);
	}

	public Double getTotalPaidAmountPerInvoice(Long invoiceId) {

		Double totalPaidAmount = 0.0;
		List<InvoiceTransactionDetail> invTxnDetailList = invoiceTransactionDetailRepository
				.findByInvoiceIdOrderByCreatedDesc(invoiceId);
		if (!invTxnDetailList.isEmpty()) {
			for (InvoiceTransactionDetail invoiceTransactionDetail : invTxnDetailList) {
				totalPaidAmount += invoiceTransactionDetail.getPaidAmount();
			}
		}
		return totalPaidAmount;
	}

	public Double getTotalOverDraftAmountPerInvoice(Long invoiceId) {

		Double totalOverDraftAmount = 0.0;
		List<InvoiceTransactionDetail> invTxnDetailList = invoiceTransactionDetailRepository
				.findByInvoiceIdOrderByCreatedDesc(invoiceId);
		if (!invTxnDetailList.isEmpty()) {
			for (InvoiceTransactionDetail invoiceTransactionDetail : invTxnDetailList) {
				totalOverDraftAmount += invoiceTransactionDetail.getOverdraftAmount();
			}
		}
		return totalOverDraftAmount;
	}

	public Boolean isAllPaid(InvoiceDetail invoiceDetail) {
		Boolean isAllpaid = false;
		Double outstandingAmount = 0.0;
		List<InvoiceTransactionDetail> invoiceTxnDetailList = invoiceTransactionDetailRepository
				.findByInvoiceId(invoiceDetail.getInvoiceId());
		if (invoiceTxnDetailList.isEmpty())
			return isAllpaid;
		InvoiceTransactionDetailResponse invoiceTxnDetailResponse = getOutstandingPaymentDetails(
				invoiceDetail.getInvoiceId());
		if (invoiceTxnDetailResponse != null) {
			outstandingAmount = invoiceTxnDetailResponse.getInvoiceAmt() + invoiceTxnDetailResponse.getTaxAmt();
			if (outstandingAmount == 0 || outstandingAmount == 0.0)
				isAllpaid = true;
		}
		return isAllpaid;
	}

	public void updateInvoiceAmountByCategory(){
		List<InvoiceDetail> invList = findAll();
		Member member = null;
		CategoryFee categoryFee = null;
		if (!invList.isEmpty()) {
			try {
				for (InvoiceDetail detail : invList) {
						City branchCity = null;
						City invoiceCity = null;
						InvoiceTransactionDetail invoiceTransaction = null;
						invoiceTransaction = getLatestInvoiceTransactionDetail(detail);
						if (invoiceTransaction == null) {
							List<CalculatedTax> calculatedTaxs = new ArrayList<CalculatedTax>();
							member = detail.getMember();
							invoiceCity = detail.getAddress().getCity();
							branchCity = getBranchCity(detail);
							categoryFee = categoryFeeRepository.findByCategory(member.getCategory());
							if (null == categoryFee) {
								logger.debug("Invalid Member Category ");
							}
							detail.setInvoiceAmt(categoryFee.getFee());
							if (detail.getTaxApplicable())
								calculatedTaxs = calculateTaxes(branchCity, invoiceCity, member);
							detail.setTaxes(calculatedTaxs);
							Double totalAmountWithTaxes = getTotalInvoiceAmount(member, calculatedTaxs);
							detail.setTotalAmountWithTaxes(totalAmountWithTaxes);
							if (detail.getTaxApplicable())
								detail.setTaxAmt(populateTotalTaxAmount(calculatedTaxs));
							else
								detail.setTaxAmt(0.0);
							
							String totalAmountWithTaxesInWords = StringUtils
									.capitalize(NumberToEnglishWord.convert(totalAmountWithTaxes.intValue()));
							detail.setAmountChargeableInwords(totalAmountWithTaxesInWords);
							invoiceRepository.save(detail);
						}
				}
			} catch (Exception e) {
				logger.error("Exception encountered while updating invoice amount by category", e);
			}
		}
	}
}
