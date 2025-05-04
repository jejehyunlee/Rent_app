package com.mbgm.Rent_Manage_System.config;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.of("JEFRI"); // fallback
    }

}
