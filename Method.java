import java.util.ArrayList;
import java.util.List;

public class Method {
    private String signature, desc;
    private List<String> params;

    public Method(String signature,List<String> params, String desc) {
        this.signature = signature;
        this.desc = desc;
        this.params = params;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}