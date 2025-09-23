package co.com.crediya.config;

import co.com.bancolombia.secretsmanager.api.GenericManagerAsync;
import co.com.bancolombia.secretsmanager.api.exceptions.SecretException;
import co.com.bancolombia.secretsmanager.config.AWSSecretsManagerConfig;
import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnectorAsync;
import co.com.crediya.jwthelper.JwtProperties;
import co.com.crediya.config.dto.JwtSecretDto;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretsConfig {
    @Value("${jwt.expiration}")
    private Long expiration;

    @Bean
    @Profile("dock")
    public JwtProperties jwtProperties(GenericManagerAsync secretManager) throws SecretException {
        JwtSecretDto secret = secretManager.getSecret("jwt", JwtSecretDto.class).block();
        assert secret != null;
        return new JwtProperties(secret.getJwtSecretValue(), expiration);
    }

  @Bean
  public GenericManagerAsync getSecretManager(@Value("${aws.region}") String region) {
    return new AWSSecretManagerConnectorAsync(getConfig(region));
  }

  private AWSSecretsManagerConfig getConfig(String region) {
    return AWSSecretsManagerConfig.builder()
      .region(Region.of(region))
      .cacheSize(5)
      .cacheSeconds(3600)
      .build();
  }
}
