
import org.apache.xmlrpc.XmlRpcClient;

import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;

public class ClientRPC {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {
        System.out.print("Podaj adres: ");
        String address = scanner.next();
        System.out.print("Podaj port: ");
        String url = "http://" + address + ":" + scanner.nextInt();
        scanner.nextLine();
        try {
            XmlRpcClient srv = new XmlRpcClient(url);
            menu(srv, "MojSerwer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void menu(XmlRpcClient srv, String handler) throws Exception {
        String command = "";
        while (!command.equals("-1")) {
            showMenu();
            command = scanner.nextLine();
            command = removeSpaces(command);
            if (!command.equals("-1")) {
                Object[] result = extractParams(command);
                String comm = (String) result[0];
                System.out.print("Wywołać asynchronicznie? Y/N: ");
                String isAsync = scanner.next();
                scanner.nextLine();
                System.out.print("ODP:");
                if(isAsync.equals("y") || isAsync.equals("Y")){
                    AC cb = new AC();
                    srv.executeAsync(handler + "." + comm, (Vector) result[1], cb);
                }else{
                    System.out.println(srv.execute(handler + "." + comm, (Vector) result[1]));
                }
            }
        }
    }

    private static Object[] extractParams(String command) throws Exception {
        String[] parts = command.split(Pattern.quote("("));
        Vector<Object> vectorParams = new Vector<>();
        if (parts[1].contains(",")) {
            String[] params = parts[1].split(Pattern.quote(","));
            params[params.length - 1] = params[params.length - 1].replace(")", "");
            for (int i = 0; i < params.length; i++) {
                String[] typeValue = getTypeValue(params[i]);
                addElementToVector(typeValue,vectorParams);
            }
        }else if (!parts[1].equals(")")) {
            String param = parts[1].replace(")", "");
            addElementToVector(getTypeValue(param),vectorParams);
        }
        return new Object[]{parts[0], vectorParams};
    }

    private static void addElementToVector(String[] typeValue, Vector<Object> v) throws Exception {
        switch (typeValue[0]) {
            case "int":
                v.addElement(Integer.valueOf(typeValue[1]));
                break;
            case "double":
                v.addElement(Double.valueOf(typeValue[1]));
                break;
            case "float":
                v.addElement(Float.valueOf(typeValue[1]));
                break;
            case "string":
                v.addElement(typeValue[1]);
                break;
            default:
                throw new Exception("No such type");
        }
    }

    private static String[] getTypeValue(String s) throws Exception {
        s = s.toLowerCase();
        StringBuilder type = new StringBuilder();
        int i = 0;
        boolean foundType = false;
        for (; i < s.length(); i++) {
            if (type.toString().equals("int") || type.toString().equals("double") || type.toString().equals("string") || type.toString().equals("float")) {
                foundType = true;
                break;
            }
            type.append(s.charAt(i));
        }
        if (!foundType) {
            System.out.println(type);
            throw new Exception("zle arg");
        }
        return new String[]{type.toString(), s.substring(i)};
    }

    private static void showMenu() {
        System.out.println("\nDostępne funkcje: ");
        System.out.println("    show() - pokazuje metody serwera");
        System.out.println("    -1 - kończy program");
        System.out.print("Wpisz komende: ");
    }

    private static String removeSpaces(String s) {
        s = s.replaceAll(" ", "");
        s = s.replaceAll("\t", "");
        s = s.replaceAll("\n", "");
        return s;
    }
}