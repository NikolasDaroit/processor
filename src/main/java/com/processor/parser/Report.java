package com.processor.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Report {
    
    
    private List<Customer> customers;
    private List<Salesperson> salesperson;
    private List<Sale> sales;
    
    private Integer numberOfCustomers;
    private Integer numberOfSalesperson;
    private Float mostExpensiveSaleId;
    private String worstSalesperson;
    
    private static final String FINAL_FILE_EXTENSION = ".done.dat";
    private final String OUTPUT_FOLDER = "C:\\Users\\nikolas.daroit\\Documents\\workspace\\output\\";
    
    private final String QTD_CLIENTES = "Quantidade de clientes no arquivo de entrada";
    private final String QTD_VENDEDOR = "Quantidade de vendedor no arquivo de entrada";
    private final String ID_VENDA = "ID da venda mais cara";
    private final String PIOR_VENDEDOR = "O pior vendedor";

    public int getNumberOfCustomers() {
        return this.customers.size();
    }

    public int getNumberOfSalesperson() {
        return this.salesperson.size();
    }

    public int getMostExpensiveSaleId() {
        int saleId = 0;
        Float maxSale = 0.0f;
        for (Sale sale : this.sales) {
            if (sale.getTotalSale() > maxSale) {
                saleId = sale.getId();
            }
        }
        return saleId;
    }

    private HashMap<String, List<Sale>> getSaleBySalesperson() {
        HashMap<String, List<Sale>> saleBySalesperson = new HashMap<String, List<Sale>>();
        for (Sale sale : this.sales) {
            if (!saleBySalesperson.containsKey(sale.getSalespersonName())) {
                List<Sale> list = new ArrayList<Sale>();
                list.add(sale);

                saleBySalesperson.put(sale.getSalespersonName(), list);
            } else {
                saleBySalesperson.get(sale.getSalespersonName()).add(sale);
            }
        }
        return saleBySalesperson;

    }

    public String getWorstSalesperson() {
        String worstSalesperson = null;
        Float worstTotal = 0.0f;

        HashMap<String, List<Sale>> salesBySalesperson = this.getSaleBySalesperson();

        for (Object salespersonName : salesBySalesperson.keySet()) {
            Float totalSaleValueBySalesperson = 0.0f;
            for (Sale sale : salesBySalesperson.get(salespersonName)) {
                salespersonName = sale.getSalespersonName();
                totalSaleValueBySalesperson = totalSaleValueBySalesperson + sale.getTotalSale();
            }
            if (worstTotal == 0.0f || totalSaleValueBySalesperson < worstTotal) {
                worstTotal = totalSaleValueBySalesperson;
                worstSalesperson = String.valueOf(salespersonName);
            }
        }
        return worstSalesperson;
    }

    public void saveReport(String fileName) {
        String baseFileName  = FilenameUtils.removeExtension(fileName);
        String newFilePath = this.OUTPUT_FOLDER+baseFileName+this.FINAL_FILE_EXTENSION;
        Path path = Paths.get(newFilePath);
        File file = new File(newFilePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // if file already exists will do nothing
        String output_text = String.format("%s = %d\n%s = %d\n%s = %d\n%s = %s", 
            this.QTD_CLIENTES, this.getNumberOfCustomers(),
            this.QTD_VENDEDOR, this.getNumberOfSalesperson(),
            this.ID_VENDA, this.getMostExpensiveSaleId(),
            this.PIOR_VENDEDOR, this.getWorstSalesperson());

        try(BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))){
            writer.write(output_text);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}