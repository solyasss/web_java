package services.timestamp;

import com.google.inject.Singleton;

import java.time.Instant;

@Singleton
public class UnixTimeStampService implements TimeStampService
{
    @Override
    public long seconds_for_now()
    {
        return Instant.now().getEpochSecond();
    }
}
