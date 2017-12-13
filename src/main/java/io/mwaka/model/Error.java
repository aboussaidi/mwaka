package io.mwaka.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    public String code;
    public String message;

    public Error(String code, String message) {
        setCode(code);
        setMessage(message);
    }
}
