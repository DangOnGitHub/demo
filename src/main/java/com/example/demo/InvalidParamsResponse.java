package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InvalidParamsResponse {
    private String title;
    @JsonProperty("invalidParams")
    private List<Parameter> parameters;

    public InvalidParamsResponse() {
    }

    public InvalidParamsResponse(String title, List<Parameter> parameters) {
        this.title = title;
        this.parameters = parameters;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    static class Parameter {
        private String name;
        private String reason;

        public Parameter() {
        }

        public Parameter(String name, String reason) {
            this.name = name;
            this.reason = reason;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
