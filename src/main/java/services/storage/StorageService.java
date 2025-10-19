package services.storage;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.fileupload2.core.FileItem;

public interface StorageService {
    String save(FileItem item) throws IOException;
    InputStream getStream(String id) throws IOException;
}
