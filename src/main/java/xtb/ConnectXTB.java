package xtb;

import pro.xstore.api.message.command.APICommandFactory;
import pro.xstore.api.message.error.APICommandConstructionException;
import pro.xstore.api.message.error.APICommunicationException;
import pro.xstore.api.message.error.APIReplyParseException;
import pro.xstore.api.message.response.APIErrorResponse;
import pro.xstore.api.message.response.ServerTimeResponse;
import pro.xstore.api.sync.Credentials;
import pro.xstore.api.sync.ServerData;
import pro.xstore.api.sync.SyncAPIConnector;

import java.io.IOException;

public class ConnectXTB {

    public static SyncAPIConnector connector;
    private static boolean isLogin = false;
    private static boolean isConect = false;

    public static boolean login(User user) {
        final long id = user.getId();
        final String password = user.getPassword();
        try {
            ConnectXTB.connector = new SyncAPIConnector(ServerData.ServerEnum.DEMO);

            isConect = connector.isConnected();
            if (isConect) {
                Credentials credentials = new Credentials(id, password);
                isLogin = APICommandFactory.executeLoginCommand(connector, credentials).getStatus();

            }


        } catch (IOException e) {
            return false;
//            throw new RuntimeException(e);
        } catch (APIErrorResponse e) {
            throw new RuntimeException(e);
        } catch (APICommunicationException e) {

            throw new RuntimeException(e);
        } catch (APIReplyParseException e) {
            throw new RuntimeException(e);
        } catch (APICommandConstructionException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static void loguot() throws APICommandConstructionException {
        if (isConect && isLogin) {
            APICommandFactory.createLogoutCommand();
        }

    }

    public static long getServerTime() {
        ServerTimeResponse serverTime = null;
        try {
            serverTime = APICommandFactory.executeServerTimeCommand(connector);
        } catch (APICommandConstructionException e) {
            throw new RuntimeException(e);
        } catch (APICommunicationException e) {
            throw new RuntimeException(e);
        } catch (APIReplyParseException e) {
            throw new RuntimeException(e);
        } catch (APIErrorResponse e) {
            throw new RuntimeException(e);
        }

        return serverTime.getTime();
    }

    public static boolean isLogin() {
        return isLogin;
    }


}
