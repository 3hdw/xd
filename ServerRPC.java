
import org.apache.xmlrpc.WebServer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerRPC {

    public static void main(String args[]) {
        try{
            System.out.println("Startuje Serwer XML-RPC...");
            int port = 8080;
            WebServer webServer = new WebServer(port);
            webServer.addHandler("MojSerwer",new ServerRPC());
            System.out.println("Serwer wystartował pomyślnie.");
            System.out.println("Serwer nasłuchuje na porice: "+port);
            System.out.println("Aby zatrzymać serwer naciśnij ctrl+c");
            webServer.start();
        }catch (Exception e){
            System.err.println("Serwer XML-RPC: "+e);
        }
    }

    public String show() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Method m:getMethods()){
            stringBuilder.append(m.getSignature());
            stringBuilder.append("\t");
            stringBuilder.append("|");
            stringBuilder.append("\t");
            for(String param: m.getParams()){
                stringBuilder.append(param);
                stringBuilder.append("\t");
            }
            stringBuilder.append("|");
            stringBuilder.append("\t");
            stringBuilder.append(m.getDesc());
            stringBuilder.append("\n");
        }
        String response = stringBuilder.toString();
        return response.substring(0,response.length()-1);
    }

    public boolean isStrongPassword(String pass) {
        int minLength = 8;
        boolean containsNumber = false;
        boolean containsCapitalLetter = false;

        if(pass.length() >= minLength) {
            for(int i = 0; i < pass.length(); i++) {
                char sign = pass.charAt(i);
                if(!containsNumber)
                    containsNumber = Character.isDigit(sign);
                if(!containsCapitalLetter)
                    containsCapitalLetter = Character.isUpperCase(sign);
            }

            if(containsNumber && containsCapitalLetter)
                return true;
        }
        return false;
    }

    private static List<Method> getMethods() {
        List<Method> methods = new ArrayList<>();
        methods.add(
                new Method("containsChar(String word, String sign, int responseTime)",
                        Arrays.asList("word - parametr", "sign - parametr, int - parametr"),
                        "Funkcja sprawdzajaca czy sign znajduje sie w word")
        );
        methods.add(
                new Method("isStrongPassword(String password)",
                        Arrays.asList("password - parametr"),
                        "Funkcja sprawdzajaca sile hasla")
        );
        return methods;
    }

    public int containsChar(String word, String sign, int responseTime) {
        System.out.println("... wywołano asy - odliczam");
        int contains = -2;
        try {
            contains = word.indexOf(sign.charAt(0));
            Thread.sleep(responseTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return contains;
    }
}
