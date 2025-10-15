package services.form;

import com.google.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload2.core.DiskFileItem;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletDiskFileUpload;


public class MixedFormParseService implements FormParseService {

    private JakartaServletDiskFileUpload upload;

    @Inject
    public MixedFormParseService()
    {
        //DiskFileItemFactory  factory = new DiskFileItemFactory();
        //factory.setSizeThreshold(100000);
        upload = new JakartaServletDiskFileUpload();
        upload.setFileCountMax(10);
        upload.setFileSizeMax(1000000);
        upload.setSizeMax((long)1e7);
    }


    public FormParseResult parse(HttpServletRequest request) throws FormParseException {
        Map<String, String> fields = new HashMap<>();
        Map<String, FileItem> files = new HashMap<>();

        if(upload.isMultipartContent(request)) {
            try {
                List<DiskFileItem> items = upload.parseRequest(request);
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        fields.put(
                                item.getFieldName(),
                                item.getString(StandardCharsets.UTF_8)
                        );
                    }
                    else {
                        files.put(
                                item.getFieldName(),
                                item
                        );
                    }
                }
            }
            catch (IOException ex) {
                throw new FormParseException(ex.getMessage());
            }
        }
        else {
            Enumeration<String> names = request.getParameterNames();
            while( names.hasMoreElements() ) {
                String name = names.nextElement();
                fields.put(name, request.getParameter(name));
            }
        }

        return new FormParseResult() {
            @Override
            public Map<String, String> getFields() {
                return fields;
            }

            @Override
            public Map<String, FileItem> getFiles() {
                return files;
            }
        };
    }

}
