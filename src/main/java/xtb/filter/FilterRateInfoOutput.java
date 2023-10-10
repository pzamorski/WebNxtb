package xtb.filter;

import pro.xstore.api.message.records.RateInfoRecord;

import java.util.ArrayList;
import java.util.List;

public class FilterRateInfoOutput {

    int id;
    List<RateInfoRecord> rateInfoRecordList;
    ArrayList<String> rateInfoRecordListString = new ArrayList<>();
    Price[] price;

    public FilterRateInfoOutput(int id, List<RateInfoRecord> rateInfoRecordList, Price... price) {
        this.id = id;
        this.rateInfoRecordList = rateInfoRecordList;
        this.price = price;
    }

    public ArrayList<String> factory() {

        int stała=0;
        if (rateInfoRecordListString.size() == 0) {
            for (int i = 0 + id; i < rateInfoRecordList.size(); i++) {
                RateInfoRecord rateInfoRecord = rateInfoRecordList.get(i);
                RateInfoRecord rateInfoRecordPrev = rateInfoRecordList.get(i-1);



                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < price.length; j++) {
                    switch (price[j]) {
                        case OPEN:
                            sb.append(rateInfoRecord.getOpen() + ";");
                            break;
                        case CLOSE:
                            sb.append(stała+(int)(rateInfoRecord.getDoubleClose()- rateInfoRecordPrev.getDoubleClose()) + ";");
                            break;
                        case HIGHT:
                            sb.append(stała+(int)(rateInfoRecord.getDoubleHigh()- rateInfoRecordPrev.getDoubleHigh()) + ";");
                            break;
                        case LOW:
                            sb.append(stała+(int)(rateInfoRecord.getDoubleLow()- rateInfoRecordPrev.getDoubleLow()) + ";");
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
        } else {
            return rateInfoRecordListString;
        }
    }
}
