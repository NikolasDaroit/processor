package com.processor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.processor.parser.ParseContent;
import com.processor.reader.FileReader;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{   
    private ParseContent parser;
    private FileReader fReader;
    private String fileName ;
    private List<String> lines;
    @Before
    public void setUp() {
        fileName = "testfile";
        parser = new ParseContent();
        fReader = new FileReader();
        lines = new ArrayList<>();
        lines.add("001ç1234567891234çPedroç50000");
        lines.add("001ç3245678865434çPauloç40000.99");
        lines.add("002ç2345675434544345çJose da SilvaçRural");
        lines.add("002ç2345675433444345çEduardo PereiraçRural");
        lines.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro");
        lines.add("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");
    }
    
    @Test
    public void testCustomerHasBeenFound()
    {
        parser.parse(this.fileName, this.lines);
        assertEquals(parser.getCustomers().size(), 2); 
    }

    @Test
    public void testSalespersonHasBeenFound()
    {
        parser.parse(this.fileName, this.lines);
        assertEquals(parser.getSalesperson().size(), 2); 
    }

    @Test
    public void testSalesHasBeenFound()
    {
        parser.parse(this.fileName, this.lines);
        assertEquals(parser.getSales().size(), 2); 
    }
    @Test
    public void testReportMostExpensiveSale()
    {
        parser.parse(this.fileName, this.lines);
        assertEquals(parser.getReport().getMostExpensiveSaleId(), 8); 
    }

    @Test
    public void testReportWorstSalesperson(){
        parser.parse(this.fileName, this.lines);
        assertEquals(parser.getReport().getWorstSalesperson(), "Paulo"); 
    }
}
