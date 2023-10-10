package pro.xstore.api.message.records;

import org.json.simple.JSONObject;

import java.text.DecimalFormat;

public class RateInfoRecord implements BaseResponseRecord {

    private long ctm;
    private double open;
    private double high;
    private double low;
    private double close;
    private double vol;

    public RateInfoRecord() {
    }

    public long getCtm() {
        return ctm;
    }

    public void setCtm(long ctm) {
        this.ctm = ctm;
    }

    public String getOpen() {
        return String.valueOf((int)open);
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public String getHigh() {
        return String.valueOf((int)(high + open));
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public String getLow() {
        return String.valueOf((int)(low + open));
    }

    public void setLow(double low) {
        this.low = low;
    }

    public String getClose() {
        return String.valueOf((int)(close + open));
    }

    public double getDoubleClose() {
        return close + open;
    }
    public double getDoubleHigh() {
        return high + open;
    }
    public double getDoubleLow() {
        return low + open;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public String getVol() {
        return String.valueOf(vol);
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    @Override
    public void setFieldsFromJSONObject(JSONObject e) {
        this.setClose((Double) e.get("close"));
        this.setCtm((Long) e.get("ctm"));
        this.setHigh((Double) e.get("high"));
        this.setLow((Double) e.get("low"));
        this.setOpen((Double) e.get("open"));
        this.setVol((Double) e.get("vol"));
    }


    @Override
    public String toString() {
        return open + ";" + (high + open) + ";" + (low + open) + ";" + (close + open) + ";" + vol;
    }

    public double[] getValue() {




//        return new double[]{ open/1000,  (high+open)/1000,  (low+open)/1000,  (close+open)/1000,   vol};
        return new double[]{ open,(close+open),  (high+open),  (low+open),  vol};
    }

    public double[] getValuePercent() {
        DecimalFormat decimalFormat = new DecimalFormat("##.#####");
        double closeProcent = (close * 1000) / open;
        double highProcent= (high*1000)/open;
        double lowProcent=(low*1000)/open;
        closeProcent=Math.round(closeProcent * 10000) / 10000.0;
        highProcent=Math.round(highProcent * 10000) / 10000.0;
        lowProcent=Math.round(lowProcent * 10000) / 10000.0;
//        return new double[]{ open/1000,  (high+open)/1000,  (low+open)/1000,  (close+open)/1000,   vol};
        return new double[]{ open,closeProcent, highProcent,  lowProcent,  vol};
    }


}
