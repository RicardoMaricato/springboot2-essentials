package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.domain.dto.AnimeDto;
import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.mapper.AnimeMapperDto;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final AnimeMapperDto animeMapperDto;

    public Page<AnimeDto> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable).map(animeMapperDto::ormToDto);
    }

    public List<AnimeDto> listAllNonPageable() {
        return animeRepository.findAll().stream().map(animeMapperDto::ormToDto)
                .collect(Collectors.toList());
    }

    public List<AnimeDto> findByName(String name) {
        return animeRepository.findByName(name).stream().map(animeMapperDto::ormToDto)
                .collect(Collectors.toList());
    }

    public Anime findByIdOrThrowBadRequestException(long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found"));
    }

    public AnimeDto findById(long id) {
//        AnimeDto animeDto = new AnimeDto();
        Anime anime = findByIdOrThrowBadRequestException(id);
        return animeMapperDto.ormToDto(anime);
//        animeDto.setId(anime.getId());
//        animeDto.setName(anime.getName());
//        return animeDto;
    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }

    public AnimeDto convertAndProcessToAnimeDto(Anime anime) {
        AnimeDto animeDto = animeMapperDto.ormToDto(anime);
        return animeDto;
    }
}
