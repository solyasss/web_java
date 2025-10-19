package services.config;

public interface ConfigService {
    void addFile(String filename);
    String get(String path);
}
