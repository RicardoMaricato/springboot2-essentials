package academy.devdojo.springboot2.config;

import academy.devdojo.springboot2.mapper.AnimeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfig {

    @Bean
    public AnimeMapper animeMapper() {
        return AnimeMapper.INSTANCE;
    }
}
