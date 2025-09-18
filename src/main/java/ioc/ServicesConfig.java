package ioc;

import com.google.inject.AbstractModule;
import services.hash.HashService;
import services.kdf.KdfService;
import services.hash.Md5HashService;
import services.kdf.PbKdf1Service;
import services.timestamp.TimeStampService;
import services.timestamp.UnixTimeStampService;

public class ServicesConfig extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(KdfService.class).to(PbKdf1Service.class);
        bind(HashService.class).to(Md5HashService.class);
        bind(TimeStampService.class).to(UnixTimeStampService.class);
    }
}
