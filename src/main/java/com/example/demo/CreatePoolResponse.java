package com.example.demo;

public class CreatePoolResponse {
    private String status;

    public CreatePoolResponse() {
    }

    public CreatePoolResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
