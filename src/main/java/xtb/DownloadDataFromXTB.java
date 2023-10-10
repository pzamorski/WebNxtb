package xtb;

import lombok.extern.java.Log;
import pro.xstore.api.message.codes.PERIOD_CODE;
import pro.xstore.api.message.command.APICommandFactory;
import pro.xstore.api.message.error.APICommandConstructionException;
import pro.xstore.api.message.error.APICommunicationException;
import pro.xstore.api.message.error.APIReplyParseException;
import pro.xstore.api.message.response.APIErrorResponse;
import pro.xstore.api.message.response.ChartResponse;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Log
public class DownloadDataFromXTB {


    public static String get(String symbol, PERIOD_CODE period_code, boolean getMaxCandles, int miniBatchSize) throws APICommunicationException {

        long timeDownload = 5000;
        if (!ConnectXTB.isLogin()) {
            throw new APICommunicationException("Not login user");
        } else {
            if (getMaxCandles) {
                timeDownload = 50000L;
            }

            System.out.println("******Get online from XTB******");

            long startTime = new Date().getTime() - TimeUnit.MINUTES.toMillis(period_code.getCode() * timeDownload);
            ChartResponse chartLastCommand;
            try {
                chartLastCommand = APICommandFactory.
                        executeChartLastCommand(ConnectXTB.connector, symbol, period_code, startTime);
            } catch (APICommandConstructionException e) {
                throw new RuntimeException(e);
            } catch (APICommunicationException e) {
                throw new RuntimeException(e);
            } catch (APIReplyParseException e) {
                throw new RuntimeException(e);
            } catch (APIErrorResponse e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            System.out.println();
            String result = null;

            String close = null;
            String high=null;
            String low=null;
            for (; i < chartLastCommand.getRateInfos().size() - 1 - chartLastCommand.getRateInfos().size() % miniBatchSize - 1; i++) {
                int closeCurent = Integer.parseInt(chartLastCommand.getRateInfos().get(i).getClose());
                int closeNext = Integer.parseInt(chartLastCommand.getRateInfos().get(i + 1).getClose());
                result = String.valueOf(closeCurent - closeNext);
//                if (closeCurent - closeNext > 0) {
//                    result = "1";
//                } else if (closeCurent - closeNext < 0) {
//                    result = "0";
//                } else {
//                    result = "2";
//                }
                close = chartLastCommand.getRateInfos().get(i).getClose();
                high = chartLastCommand.getRateInfos().get(i).getHigh();
                low = chartLastCommand.getRateInfos().get(i).getLow();
                if (i % miniBatchSize == 0 && i != 0) {
                    stringBuilder.append(close +","+ high+","+low+ ":" + result + "\n");
                } else {
                    stringBuilder.append(close +","+ high+","+low+ " ");
                }
            }

            stringBuilder.append(close +","+ high+","+low+ " ");
            stringBuilder.append(close +","+ high+","+low+ " ");
            stringBuilder.append( close +","+ high+","+low+ ":"  + result + "\n");
//            System.out.println(stringBuilder.toString());
            return stringBuilder.toString();
        }


    }

}
