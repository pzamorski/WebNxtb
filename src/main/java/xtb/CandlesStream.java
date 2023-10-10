package xtb;

import pro.xstore.api.message.records.RateInfoRecord;
import xtb.filter.FilterRateInfoInput;
import xtb.filter.FilterRateInfoOutput;
import xtb.filter.Integrator;
import xtb.filter.Price;

import java.util.ArrayList;
import java.util.List;

public class CandlesStream {
    private int inputCandles;
    private Price[] prcieCodeForInput;
    private Price[] prcieCodeForOutput;
    private boolean isTrain;

    public CandlesStream(int inputCandles, Price[] prcieCodeForInput, Price[] prcieCodeForOutput, boolean isTrain) {
        this.inputCandles = inputCandles;
        this.prcieCodeForInput = prcieCodeForInput;
        this.prcieCodeForOutput = prcieCodeForOutput;
        this.isTrain = isTrain;
    }

    public int getInputCandles() {
        return inputCandles;
    }

    public ArrayList<String> refresh(List<RateInfoRecord> data){
        ArrayList<FilterRateInfoInput> filterRateInfoInputArrayList = new ArrayList<>();

        int i=0;
        for (; i < inputCandles; i++) {
            filterRateInfoInputArrayList.add(new FilterRateInfoInput(i, data, prcieCodeForInput));
        }


//        if(isTrain){i++;}
        Integrator integrator = new Integrator()
                .addIn(filterRateInfoInputArrayList)
                .addOut(new FilterRateInfoOutput(i+1, data, prcieCodeForOutput));
        try {
            return  integrator.factory();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getLastStream() {

    }
}
