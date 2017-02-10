package data;

/**
 * Created by fahad on 2/10/2017.
 */

public class ProductBean {
    private String NAME;
    private int UNITPRICE;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getUNITPRICE() {
        return UNITPRICE;
    }

    public void setUNITPRICE(int UNITPRICE) {
        this.UNITPRICE = UNITPRICE;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "NAME='" + NAME + '\'' +
                ", UNITPRICE=" + UNITPRICE +
                '}';
    }
}
