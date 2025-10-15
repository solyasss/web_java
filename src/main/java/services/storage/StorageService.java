package services.storage;

import org.apache.commons.fileupload2.core.FileItem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public interface StorageService
{
    String save(FileItem item) throws IOException;

    InputStream getStream(String id) throws IOException;

}
