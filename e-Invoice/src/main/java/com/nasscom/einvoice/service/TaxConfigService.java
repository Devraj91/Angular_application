package com.nasscom.einvoice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;
import javax.xml.transform.stream.StreamSource;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.nasscom.einvoice.artifacts.TaxConfigArtifact;
import com.nasscom.einvoice.entity.City;
import com.nasscom.einvoice.entity.TaxConfig;
import com.nasscom.einvoice.repository.CityRepository;
import com.nasscom.einvoice.repository.TaxConfigRepository;

@Service
public class TaxConfigService {
	@Autowired
	TaxConfigRepository taxConfigRepository;
	@Autowired
	private Unmarshaller unmarshaller;
	@Autowired
	CityRepository cityRepository;
	@Value(value = "classpath:TaxConfigArtifact.xml")
	private Resource taxConfigArtifacts;

	public TaxConfig createTax(TaxConfig config) {
		config.setCity(cityRepository.findByName(StringUtils.capitalizeWords(config.getCity().getName())));
		return taxConfigRepository.save(config);
	}

	public Page<TaxConfig> findPaginated(int page, int size) {
		return taxConfigRepository.findAll(new PageRequest(page, size));
	}

	public void updateTax(TaxConfig config) {
		taxConfigRepository.save(config);
	}

	public void deleteTax(Long id) {
		taxConfigRepository.delete(id);
	}

	public List<TaxConfig> findAll() {
		return taxConfigRepository.findAll();
	}

	public List<TaxConfig> getTaxConfigByCity(City city) {
		return taxConfigRepository.findByCity(city);
	}

	/*public List<TaxConfig> getTaxConfigByName(String taxName) {
		return taxConfigRepository.findByTaxName(taxName);
	}*/
	
	public TaxConfig getTaxConfigByName(String taxName) {
	return taxConfigRepository.findByTaxName(taxName);
	}


	public TaxConfig find(Long id) {
		return taxConfigRepository.findOne(id);
	}

	public String createFileUploadTaxConfigInDB(MultipartFile taxConfigFileData)
			throws IOException, FileNotFoundException {
		Workbook workbook = this.getWorkbookImpl(taxConfigFileData);
		Sheet sheet = workbook.getSheetAt(0);
		return saveTaxConfig(sheet);
	}

	private Workbook getWorkbookImpl(MultipartFile file) throws IOException {
		Workbook workbook = null;
		if (file.getOriginalFilename().endsWith(".xlsx")) {
			workbook = new XSSFWorkbook(file.getInputStream());
		} else if (file.getOriginalFilename().endsWith(".xls")) {
			workbook = new HSSFWorkbook(file.getInputStream());
		}
		return workbook;
	}

	private String saveTaxConfig(Sheet sheet) throws FileNotFoundException, IOException {
		List<TaxConfig> listTaxConfigs = processUploadedTaxConfigs(sheet);
		taxConfigRepository.save(listTaxConfigs);
		return "success";
	}

	public Object unmarshal(InputStream fileInputStream) throws IOException {
		Object obj = null;
		try {
			obj = unmarshaller.unmarshal(new StreamSource(fileInputStream));
		} finally {
			fileInputStream.close();
		}
		return obj;
	}

	public List<TaxConfig> processUploadedTaxConfigs(Sheet sheet) throws IOException {
		List<TaxConfig> listTaxConfigs = new ArrayList<TaxConfig>();
		Row headerRow = sheet.getRow(0);
		// Row nextHeaderRow = sheet.getRow(1);
		int colNum = headerRow.getLastCellNum();
		TaxConfig taxConfig;
		// unmarshal incoming excel containing TaxConfig data
		File taxConfigArtifactsXmlFile = taxConfigArtifacts.getFile();
		TaxConfigArtifact taxConfigArtifacts = (TaxConfigArtifact) unmarshal(
				new FileInputStream(taxConfigArtifactsXmlFile));
		// maintain column map
		Map<String, Integer> column = new HashMap<>();
		int index = 0;
		// populate 'column' map with column name and index
		for (int j = 0; j < colNum; j++) {
			if (taxConfigArtifacts.getCity().getName().equals(headerRow.getCell(j).toString())) {
				column.put(taxConfigArtifacts.getCity().getName(), index);
				index++;
			}
			if (taxConfigArtifacts.getRate().equals(headerRow.getCell(j).toString())) {
				column.put(taxConfigArtifacts.getRate(), index);
				index++;
			}
			if (taxConfigArtifacts.getTaxName().equals(headerRow.getCell(j).toString())) {
				column.put(taxConfigArtifacts.getTaxName(), index);
				index++;
			}
		}
		// retrieve and set properties for TaxConfig records
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			taxConfig = new TaxConfig();
			Row row = sheet.getRow(i);
			// exit when EOF reached
			if ((row == null) || ((row.getCell(column.get(taxConfigArtifacts.getCity().getName())) == null)
					&& (row.getCell(column.get(taxConfigArtifacts.getRate())) == null)
					&& (row.getCell(column.get(taxConfigArtifacts.getTaxName())) == null))) {
				break;
			}
			City city = cityRepository
					.findByName(row.getCell(column.get(taxConfigArtifacts.getCity().getName())).getStringCellValue());
			if (null != city) {
				taxConfig.setCity(city);
			} else {
				city = new City(row.getCell(column.get(taxConfigArtifacts.getCity().getName())).getStringCellValue());
				city = cityRepository.save(city);
			}
			taxConfig.setCity(city);
			taxConfig.setRate(row.getCell(column.get(taxConfigArtifacts.getRate())).getNumericCellValue());
			taxConfig.setTaxName(row.getCell(column.get(taxConfigArtifacts.getTaxName())).getStringCellValue());
			listTaxConfigs.add(taxConfig);
		}
		return listTaxConfigs;
	}
}
