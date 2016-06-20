/**
 *
 */
package com.common.http;


/**
 * @author hanbing
 */
public class HttpResultError extends HttpResult<String> {


    int errorCode;
    String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
