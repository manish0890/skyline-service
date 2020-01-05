package com.manish0890.skyline.service.document.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "error", "message", "timestamp", "trace" })
public class ErrorDetail {

    private Integer status;
    private String error;
    private String message;
    private String timestamp;
    private String trace;

    public ErrorDetail() {
        // empty constructor
    }

    /**
     * Takes a Json String and converts it to an Error Summary object.
     *
     * @param errorSummaryJson Json {@link String} representation of the Error summary response
     */
    public ErrorDetail(String errorSummaryJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ErrorDetail temp = mapper.readValue(errorSummaryJson, ErrorDetail.class);
            setStatus(temp.status);
            setMessage(temp.message);
            setTrace(temp.trace);
            setTimestamp(temp.timestamp);
            setError(temp.error);
        } catch (IOException e) {
            throw new IllegalArgumentException("Couldn't convert JSON to ErrorSummary", e);
        }

    }

    public ErrorDetail(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.message = (String) errorAttributes.get("message");
        this.timestamp = errorAttributes.get("timestamp").toString();
        this.trace = (String) errorAttributes.get("trace");
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
