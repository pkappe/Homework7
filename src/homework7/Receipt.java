
package homework7;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Class to manipulate data from provided files. 
 * @author Phil Kappe
 */
class Receipt
{

    final double TAX;

    public Receipt()
    {
        TAX = 0.09;
    }
    
    public double getTaxValue(File inFile)
    {
        return calculateSubTotal(inFile) * TAX;
    }

    /**
     * Tax is originally a double value. This method converts that double value
     * into a readable String value by multiplying the double value by 100.
     * Return value will read as a whole number.
     *
     * @return A string version of the tax.
     */
    public String returnTax()
    {
        Double convertedTax = (Double) TAX * 100;
        return convertedTax.toString();
    }

    public double getTax()
    {
        return TAX;
    }

    /**
     * Calculates the value of each item and multiplies them by the quantity of
     * that item to return a subtotal.
     * <p>
     * Data in file must read as follows:
     * <p>
     * (Title)<br>
     * (Date)<br>
     * (ID)<br>
     * (Quantity) (Price) (Description)<br>
     * (Quantity) (Price) (Description)<br>
     * (Quantity) (Price) (Description)<br>
     *
     * @param inFile The file to read from.
     * @return The subtotal of all items in file.
     */
    public double calculateSubTotal(File inFile)
    {
        double subtotal = 0;
        try (Scanner scan = new Scanner(inFile))
        {
            for (int i = 0; i < 3; i++)
            {
                scan.nextLine();
            }
            while (scan.hasNextLine())
            {
                int multiplier = scan.nextInt();
                subtotal = subtotal + (scan.nextDouble() * multiplier);
                scan.nextLine();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return subtotal;
    }

    /**
     * Calculate the by adding tax to a subtotal value.
     *
     * @param subtotal Subtotal can be accessed with calculateSubTotal(), or
     * value can be passed to this method.
     * @return Subtotal * Tax rate.
     */
    public double calculateTotal(double subtotal)
    {
        return (subtotal * TAX) + subtotal;
    }

    /**
     * Retrieves a line of data based on the line number given. The data returned
     * is only from the the line data of items. The first three lines are ignored
     * which include the Title, Date, and ID of the receipt. If the line number
     * parameter greater than 3, the first 3 lines are not skipped.
     * @param inFile The file to evaluate.
     * @param lineNumber The line number in the file to return.
     * @return A string with the line of data needed from the file. 
     */
    public String getItemLine(File inFile, int lineNumber)
    {
        String lineString = "";
        try (Scanner scan = new Scanner(inFile))
        {
            int skipLines = 0;
            if (lineNumber <= 3)
            {
                skipLines = 3 + lineNumber - 1;
            }
            else
            {
                skipLines = lineNumber - 1;
            }
            
            for (int i = 0; i < skipLines; i++)
            {
                scan.nextLine();
            }
            lineString = scan.nextLine();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return lineString;
    }

    /**
     * Prints all items from every file passed to this method, into a separate
     * file named "AllItems.txt".
     *
     * @param files An ArrayList<> of the files to read from.
     */
    public void allItems(ArrayList<String> files)
    {
        try (PrintWriter write = new PrintWriter(new File("AllItems.txt")))
        {
            for (int i = 0; i < files.size() - 1; i++)
            {
                File inFile = new File(files.get(i));
                for (int j = 1; j <= 3; j++)
                {
                    write.println(getItemLine(inFile, j));
                }
            }
            write.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
    
    /**
     * Prints a receipt in output based on the file passed into the parameters.
     * @param inFile The file to print a receipt.
     */
    public void printReceipt(File inFile)
    {
        try(Scanner scan = new Scanner(inFile))
        {
            System.out.println("=======================");
            System.out.println("=======" + scan.nextLine() + "=========");
            System.out.println("=======================");
            System.out.println("Date: " + scan.nextLine());
            System.out.println(scan.nextLine());
            System.out.println();
            for (int i = 0; i < 3; i++)
            {
               System.out.println("(" + scan.next() + ") $" + scan.next() 
               + scan.nextLine()); 
            }
            System.out.println();
            System.out.printf("SubTotal: $%.2f", calculateSubTotal(inFile));
            System.out.println();
            System.out.printf("Tax:      $%.2f", getTaxValue(inFile));
            System.out.println();
            System.out.println("------------------");
            System.out.printf("Total:    $%.2f", calculateTotal(calculateSubTotal(inFile)));
            System.out.println();
            System.out.println();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    /**
     * Print a table with all of the receipts ID, Subtotal, Tax, and Total.
     * @param files The files to print out on the table.
     */
    public void printReceiptTable(ArrayList<String> files)
    {
        System.out.println("ID\tSubtotal\tTax\tTotal");
        System.out.println("---------------------------------------");
        for(String file : files)
        {
            try(Scanner scan = new Scanner(new File(file)))
            {
                scan.nextLine();
                scan.nextLine();
                System.out.printf(scan.nextLine() 
                        + "\t$%.2f\t       $%.2f\t$%.2f",calculateSubTotal(new File(file)),
                getTaxValue(new File(file)), 
                calculateTotal(calculateSubTotal(new File(file))));
                System.out.println();
                     
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
    
    /**
     * Get receipt ID of the file passed to method.
     * @param inFile File to get receipt ID.
     * @return Receipt ID.
     */
    public String getReceiptId(File inFile)
    {
        String id = "";
        try(Scanner scan = new Scanner(inFile))
        {
            scan.nextLine();
            scan.nextLine();
            id = scan.nextLine();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return id;
    }
    
    /**
     * Looks in certain file to find which item is the highest price.
     * @param inFile File to look through.
     * @return Highest priced item description
     */
    public String largestPricedItem(File inFile)
    {
        String highItemDesc = "";
        try(Scanner scan = new Scanner(inFile))
        {
            double highestItem = 0;
            int count = 0;
            int highestLine = 0;
            while(scan.hasNext())
            {
                count++;
                scan.next();
                double price = scan.nextDouble();
                if (price > highestItem)
                {
                    highestLine = count;
                    highestItem = price;
                }
                scan.nextLine();
            }
            
            String itemLine = getItemLine(inFile, highestLine);
            int periodIndex = itemLine.indexOf('.') + 3;
            int itemLength = itemLine.length();
            highItemDesc = itemLine.substring(periodIndex, itemLength);
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return highItemDesc;
    }
    
    /**
     * Processes files and find the items with the most purchases. Once the 
     * maximum items are found, the dates are placed into the array.
     * @param files Files to check.
     * @return An Array with dates containing most items purchased.
     */
    public ArrayList<String> getPurchasedMost(ArrayList<String> files)
    {
        int prelimHighCount = 0;
        ArrayList<String> mostPurchased = new ArrayList<>();
        
        // Find the highest purchased item value
        for(String file : files)
        {
            for (int i = 1; i <= 3; i++)
            {
                String itemLine = getItemLine(new File(file), i);
                Integer itemValue = Integer.parseInt(itemLine.substring(0,1));
                if (itemValue > prelimHighCount)
                {
                    prelimHighCount = itemValue;
                }
            }
        }
        
        // Add the dates from the receipt with the highest item count
        for(String file : files)
        {
            for (int i = 1; i <= 3; i++)
            {
                String itemLine = getItemLine(new File(file), i);
                Integer itemValue = Integer.parseInt(itemLine.substring(0,1));
                if (itemValue == prelimHighCount)
                {
                    try(Scanner scan = new Scanner(new File(file)))
                    {
                        scan.nextLine();
                        String date = scan.nextLine();
                        
                        if (!mostPurchased.contains(date))
                        {
                            mostPurchased.add(date);
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                }
            }
        }
        Collections.sort(mostPurchased);
        return mostPurchased;
    }
}
