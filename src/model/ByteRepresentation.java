package model;


/**
 * Given an amount of bytes and a representation, creates an object that can convert itself to
 * any of the ISO or SI. Has static attributes for passing as arguments when needing to represent the
 * number to a given type (e.g represent the number in GB or KiB).
 * 
 * @author josemanuelpr@ufps.edu.co
 */
public class ByteRepresentation {
    
    public static final byte UNIT = 0;
    public static final byte KILO = 1;
    public static final byte MEGA = 2;
    public static final byte GIGA = 3;
    public static final byte TERA = 4;
    
    public static final byte[] SI_POWERS = {0, 3, 6, 9, 12};
    public static final String[] SI_SUFFIX = {"B", "KB", "MB", "GB", "TB"};

    public static final byte[] ISO_IEC_POWERS = {0, 10, 20, 30, 40};
    public static final String[] ISO_IEC_SUFFIX = {"B", "KiB", "MiB", "GiB", "TiB"};

    private final double DECIMAL_ROUNDING = 1000000.0;

    private double amount;
    private byte type;

    /**
     * Defaults the amount of bytes to a byte type. e.g ByteRepresentation(100000), will
     * represent 100000B.
     * @param amount the amount of bytes, in units.
     */
    public ByteRepresentation(double amount){
        this.amount = amount;
        this.type = UNIT;
    }

    /**
     * Given an amount of bytes, the representation (or suffix) they have, and
     * a boolean value indicating if they are in the SI standard or the ISO_IEC,
     * creates the object with those specifications.
     * @param amount the amount of bytes 
     * @param type the suffix or representation of the amount of bytes (should be 
     *             passed as argument any of the static types inside this class).
     * @param isSI A boolean value representing if the given data is for a SI or ISO_IEC
     *             representation.
     */
    public ByteRepresentation(double amount, byte type, boolean isSI){
        if(type < UNIT || TERA < type)
            throw new RuntimeException("The type must be between the defined types in this class, and not: " + type);
        
        this.type = type;
        
        if(isSI){
            this.amount = amount * Math.pow(10, SI_POWERS[type]); 
        } else {
            this.amount = amount * Math.pow(2, ISO_IEC_POWERS[type]);
        }
    }

    /**
     * Returns the representation of the amount of bytes in the given SI power.
     * The parameter must be valid, that means that follows the static types
     * of this objets (UNIT, KILO, MEGA, GIGA, TERA).
     * @param type a valid type that represents the suffix of the power.
     * @return a string with the amount of bytes of the given type (with a rounding 
     *         of DECIMAL_ROUNDING decimal digits), and a suffix.
     *         e.g 12.34567GB, 2.0TB, 982B
     */
    public String representSIPower(byte type){
        if(type < 0 || 4 < type)
            throw new RuntimeException("The type is incorrect: " + type);

        return convert((byte) 10, SI_POWERS[type], SI_SUFFIX[type]);
    }

    /**
     * Returns the representation of the amount of bytes in the given ISO_IEC power.
     * The parameter must be valid, that means that follows the static types
     * of this objets (UNIT, KILO, MEGA, GIGA, TERA).
     * @param type a valid type that represents the suffix of the power.
     * @return a string with the amount of bytes of the given type (with a rounding 
     *         of DECIMAL_ROUNDING decimal digits), and a suffix.
     *         e.g 12.345678GiB, 2.0TiB, 982B
     */
    public String representISO_IECPower(byte type){
        if(type < 0 || 4 < type)
            throw new RuntimeException("The type is incorrect: " + type);

        return convert((byte) 2, ISO_IEC_POWERS[type], ISO_IEC_SUFFIX[type]);
    }

    /**
     * Returns the conversion of the amount of bytes this object has. A base, power and suffix
     * of the given system (ISO or SI) needs to be given beforehand.
     * 
     * @param base The base of the system that needs to be represented (2 or 10).
     * @param power the exponent of the representation (multiple of 3 or multiple of 10)
     * @param suffix the suffix of the amount of bytes, e.g GB, MiB, TiB, etc.
     * @return a String representing the total amount (amount of bytes * base^power) and suffix.
     */
    private String convert(byte base, byte power, String suffix){
        double conversion = this.amount / Math.pow(base, power);
        conversion = Math.round(conversion * DECIMAL_ROUNDING) / DECIMAL_ROUNDING;
        
        return "" + conversion + suffix;
    }

    @Override
    public String toString(){
        String msg = "";
        msg = "" + this.representSIPower(this.type) + ", ";
        msg += this.representISO_IECPower(type);
        return msg;
    }

    public double getAmount(){
        return this.amount;
    }
}
