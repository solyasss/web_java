package ioc;

import com.google.inject.AbstractModule;
import services.config.ConfigService;
import services.config.JsonConfigService;
import services.hash.HashService;
import services.kdf.KdfService;
import services.hash.Md5HashService;
import services.kdf.PbKdf1Service;
import services.signatures.H256SignatureService;
import services.signatures.SignatureService;
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
        bind(ConfigService.class).to(JsonConfigService.class).asEagerSingleton();
        bind(SignatureService.class).to(H256SignatureService.class);

    }
}
