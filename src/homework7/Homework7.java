
package homework7;

import java.io.File;
import java.util.ArrayList;

/**
 * Every Walmart shopper receives a receipt for their purchases. Suppose we’ve
 * been asked to write a Receipt class so their Java programmers have a new data
 * type to process their many receipts.
 *
 * Write this Java Receipt class for them. This class has methods to construct a
 * receipt from an input file, to calculate the sum of items from the file, to
 * calculate the 9% tax, and to calculate the total of the receipt.
 *
 * You must create 5 different input files with each file having a title line, a
 * date line, a unique receipt number, and exactly 3 lines of data. Each line of
 * data is a quantity (int), price (double), and a description (String).
 *
 * In your driver: Use your input files to test your Receipt class. You should
 * create 5 receipts, output them as a table, and then output the receipt number
 * of the maximum receipt total, the description of the largest priced item, and
 * the date of the item that was purchased the most. Duplicates are possible.
 *
 * @author Phil Kappe
 */
public class Homework7
{

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Receipt receipt = new Receipt();
        try
        {
            ArrayList<String> files = new ArrayList<>();
            files.add("File1.txt");
            files.add("File2.txt");
            files.add("File3.txt");
            files.add("File4.txt");
            files.add("File5.txt");

            for (String file : files)
            {
                receipt.printReceipt(new File(file));
            }

            receipt.printReceiptTable(files);

            String highestTotalFile = "";
            double highestTotal = 0;
            for (String file : files)
            {
                if (receipt.calculateTotal(receipt.calculateSubTotal(new File(file)))
                        > highestTotal)
                {
                    highestTotal = receipt.calculateSubTotal(new File(file));
                    highestTotalFile = file;
                }
            }

            System.out.println();
            System.out.println("Highest total receipt ID: "
                    + receipt.getReceiptId(new File(highestTotalFile)));

            System.out.println();
            receipt.allItems(files);
            System.out.println("Largest Priced Item: "
                    + receipt.largestPricedItem(new File("AllItems.txt")));

            System.out.println();
            System.out.println("Date(s) with most items purchased: "
                    + receipt.getPurchasedMost(files));

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

}
