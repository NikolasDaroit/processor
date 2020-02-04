package com.processor.parser;

import java.util.ArrayList;
import java.util.List;

import com.processor.domain.Customer;
import com.processor.domain.Sale;
import com.processor.domain.SaleItem;
import com.processor.domain.Salesperson;

import lombok.Getter;


public class ParseContent {
    private final String DELIMITER = "\\u00E7";
    private final String ID_SALESPERSON="001";
    private final String ID_CUSTOMER="002";
    private final String ID_SALES="003";

    private final int SALESPERSON_CPF = 1;
    private final int SALESPERSON_NAME = 2;
    private final int SALESPERSON_SALARY = 3;

    private final int CUSTOMER_CNPJ = 1;
    private final int CUSTOMER_NAME = 2;
    private final int CUSTOMER_BUSINESS_AREA = 3;

    private final int SALES_ID = 1;
    private final int SALES_ITEMS = 2;
    private final int SALES_SALESPERSON_NAME = 3;

    private final String ITEM_DELIMITER = ",";
    private final String ITEM_DATA_DELIMITER = "-";

    private final int ITEM_ID = 0;
    private final int ITEM_QUANTITY = 1;
    private final int ITEM_PRICE = 2;

    @Getter
    private List<Customer> customers;
    @Getter
    private List<Salesperson> salesperson;
    @Getter
    private List<Sale> sales;
    @Getter
    private Report report;

    public ParseContent(){
        this.customers = new ArrayList<Customer>();
        this.salesperson = new ArrayList<Salesperson>();
        this.sales = new ArrayList<Sale>();
    }
    
    /**
     * Parse data from file and save to output file
     * @param fileName
     * @param list
     */
    public void parse(String fileName, List<String> lines){
        this.clear();
        for (String line : lines){
            String[] itemData = line.split(this.DELIMITER);
            
            if (ID_CUSTOMER.equals(itemData[0])){
                Customer customer = Customer.builder()
                            .CNPJ(itemData[this.CUSTOMER_CNPJ])
                            .name(itemData[this.CUSTOMER_NAME])
                            .businessArea(itemData[this.CUSTOMER_BUSINESS_AREA])
                            .build();

                this.customers.add(customer);

            } else if (ID_SALESPERSON.equals(itemData[0])){
                Salesperson salesperson = Salesperson.builder()
                            .CPF(itemData[this.SALESPERSON_CPF])
                            .name(itemData[this.SALESPERSON_NAME])
                            .salary(Float.parseFloat(itemData[this.SALESPERSON_SALARY]))
                            .build();
                
                this.salesperson.add(salesperson);

            } else if (ID_SALES.equals(itemData[0])){
                Sale sales = Sale.builder()
                            .id(Integer.parseInt(itemData[this.SALES_ID]))
                            .items(itemData[this.SALES_ITEMS])
                            .salespersonName(itemData[this.SALES_SALESPERSON_NAME])
                            .saleItems(this.parseItems(itemData[this.SALES_ITEMS]))
                            .build();

                this.sales.add(sales);
            }
            
        }

        this.saveReport(fileName);
        
    }

    private Report generateReport(String fileName){
        Report report = Report.builder()
        .customers(this.customers)
        .sales(this.sales)
        .salesperson(this.salesperson)
        .build();
        this.report = report;
        return this.report;
    }
    private void saveReport(String fileName){
        Report report = this.generateReport(fileName);
        report.saveReport(fileName);
    }

    /**
     * Parse items from sale
     * @param itemsData
     * @return list of SaleItem
     */
    public List<SaleItem> parseItems(String itemsData){
        String[] itemList = itemsData.split(this.ITEM_DELIMITER);
        List<SaleItem> listSaleItems = new ArrayList<SaleItem>();
        for (String rawSaleItem : itemList) {
            rawSaleItem = rawSaleItem.replace("[", "").replace("]", "");

            String[] parsedSaleItem = rawSaleItem.split(this.ITEM_DATA_DELIMITER);
            SaleItem saleItem = SaleItem.builder()
                                    .id(Integer.parseInt(parsedSaleItem[this.ITEM_ID]))
                                    .quantity(Integer.parseInt(parsedSaleItem[this.ITEM_QUANTITY]))
                                    .price(Float.parseFloat(parsedSaleItem[this.ITEM_PRICE]))
                                    .build();
            listSaleItems.add(saleItem);
        }
        return listSaleItems;

    }

    /**
     * Reset items from list
     */
    private void clear(){
        this.salesperson.clear();
        this.customers.clear();
        this.sales.clear();

    }
}