package RestClient;

/**
 * Created by goco on 14.11.2017.
 */

public class AnswerTemplate {
    private int code;
    private Object object;
    private ErrorCode errorCode;

    public enum ErrorCode {
        CONNECTION_ERROR,
        INCORRENT_LOGIN_OR_PASSWORD;
    }

    public AnswerTemplate() {
    }

    public AnswerTemplate(int code, Object object) {
        super();
        this.code = code;
        this.object = object;
    }

    public AnswerTemplate(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }


    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }


    public void setObject(Object object) {
        this.object = object;
    }


}
