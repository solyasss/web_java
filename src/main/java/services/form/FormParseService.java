package services.form;

import jakarta.servlet.http.HttpServletRequest;

public interface FormParseService {
    FormParseResult parse(HttpServletRequest request) throws FormParseException;
}

