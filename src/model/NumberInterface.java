package model;


/**
 * Interface that all the numbers from the four different numeral systems follow.
 * 
 * @author josemanuelpr@ufps.edu.co
 */
public interface NumberInterface {

    /**
     * All numbers are represented as integers. So, when any number in any 
     * numeral system is requested, it will be returned as their integer, decimal
     * expression.
     * @return the integer value of the number in decimal representation, independent
     *         from the type of number.
     */
    public int getNumber();

    public void setNumber(int number);

    /**
     * Returns the decimal value of the number as a String, independent of 
     * the numeral system.
     * @return a string representing the decimal value of the number.
     */
    public String getDecimal();

    /**
     * Returns the octal value of the number as a String, independent of 
     * the numeral system.
     * @return a string representing the octal value of the number.
     */
    public String getOctal();

    /**
     * Returns the hexadecimal value of the number as a String, independent of 
     * the numeral system.
     * @return a string representing the hexadecimal value of the number.
     */
    public String getHex();

    /**
     * Returns the binary value of the number as a String, independent of 
     * the numeral system.
     * @return a string representing the binary value of the number.
     */
    public String getBinary();
}