package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.dto.AnimeDto;

public class AnimeCreatorDto {

    public static AnimeDto createValidAnimeDto(){
        return AnimeDto.builder()
                .name("Hajime no Ippo")
                .id(1L)
                .build();
    }
}
