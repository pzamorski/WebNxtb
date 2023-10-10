package xtb.filter;

import pro.xstore.api.message.records.RateInfoRecord;

import java.util.ArrayList;
import java.util.List;


public class FilterRateInfoInput {

    private int id=0;
    private List<RateInfoRecord> rateInfoRecordList;
    ArrayList<String> rateInfoRecordListString = new ArrayList<>();
    private Price[] price;

    public FilterRateInfoInput(int id,List<RateInfoRecord> rateInfoRecordList, Price... price) {
        this.id =id;
        this.rateInfoRecordList = rateInfoRecordList;
        this.price = price;
    }

    public  ArrayList<String> factory(){

if(rateInfoRecordListString.size()==0) {
    for (int i = 0 + id; i < rateInfoRecordList.size() ; i++) {
        RateInfoRecord rateInfoRecord = rateInfoRecordList.get(i);
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < price.length; j++) {
            switch (price[j]) {
                case OPEN:
                    sb.append(rateInfoRecord.getOpen() + ";");
                    break;
                case CLOSE:
                    sb.append(rateInfoRecord.getClose() + ";");
                    break;
                case HIGHT:
                    sb.append(rateInfoRecord.getHigh() + ";");
                    break;
                case LOW:
                    sb.append(rateInfoRecord.getLow() + ";");
                    break;
                case VOL:
                    sb.append(rateInfoRecord.getVol() + ";");
                    break;
                default:
                    System.out.println("--");
            }
        }
        rateInfoRecordListString.add(sb.toString());
    }

    return rateInfoRecordListString;
}else{
    return rateInfoRecordListString;
}
    }

}






