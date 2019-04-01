

import org.apache.xmlrpc.AsyncCallback;

import java.net.URL;

public class AC implements AsyncCallback {

    @Override
    public void handleResult(Object result, URL url, String method) {
        System.out.println("Wykonano pomyslnie");
        if(result.equals("-1")) {
            System.out.println("Nie ma takiego znaku w słowie");
        }
        else {
            System.out.println("Znak znajduje się w słowie");

        }
    }

    @Override
    public void handleError(Exception e, URL url, String method) {
        System.out.println("Błąd");
        e.printStackTrace();
    }
}
