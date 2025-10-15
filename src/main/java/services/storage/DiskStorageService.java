package services.storage;

import org.apache.commons.fileupload2.core.FileItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class DiskStorageService implements StorageService
{

    private String storagePath = "C:/storage/Java222/";

    @Override
    public String save(FileItem item) throws IOException {
        String name = item.getName();
        int dotIndex = name.lastIndexOf(".");
        if (dotIndex < 0) {
            throw new IOException("File without extension not allowed");
        }

        String ext = name.substring(dotIndex);
        String savedName;
        File file;

        do {
            savedName = UUID.randomUUID() + ext;
            String savedPath = storagePath + savedName;
            file = new File(savedPath);
        } while (file.exists());

        item.write(file.toPath());
        return savedName;
    }

    @Override
    public InputStream getStream(String id) throws IOException {
        File file = new File(storagePath, id);
        if (file.canRead()) {
            return new FileInputStream(file);
        } else {
            throw new IOException("File not found");
        }
    }

}
