package com.processor.parser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.processor.reader.FileReader;

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

    private FileReader fReader;
    private List<Customer> customers;
    private List<Salesperson> salesperson;
    private List<Sale> sales;

    public ParseContent(){
        fReader = new FileReader();
        this.customers = new ArrayList<Customer>();
        this.salesperson = new ArrayList<Salesperson>();
        this.sales = new ArrayList<Sale>();
    }
    
    public void parse(Path filePath){
        List<String> lines = fReader.readFileContents(filePath);

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
        String fileName = filePath.getFileName().toString();
        System.out.println("finish processing");
        Report report = Report.builder()
                        .customers(this.customers)
                        .sales(this.sales)
                        .salesperson(this.salesperson)
                        .build();
        report.saveReport(fileName);
    }

    public List<SaleItem> parseItems(String itemsData){
        String[] itemList = itemsData.split(this.ITEM_DELIMITER);
        List listSaleItems = new ArrayList<SaleItem>();
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
}