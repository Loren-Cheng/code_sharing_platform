package platform.entity;


public class CodeInMem {

    private String code;
    private String date;

    public CodeInMem() {
    }

    public CodeInMem(String code, String date) {
        this.code = code;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
