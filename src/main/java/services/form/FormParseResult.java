package services.form;

import org.apache.commons.fileupload2.core.FileItem;
import java.util.Map;

public interface FormParseResult {
    Map<String, String> getFields();
    Map<String, FileItem> getFiles();
}
