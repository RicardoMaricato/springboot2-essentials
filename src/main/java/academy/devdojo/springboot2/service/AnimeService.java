package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.domain.dto.AnimeDto;
import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Page<Anime> listAll(Pageable pageable) {
       return animeRepository.findAll(pageable);
    }

    public List<Anime> listAllNonPageable() {
        return animeRepository.findAll();
//        return AnimeMapper.INSTANCE.toAnimeDto(animes);
    }

    public List<AnimeDto> findByName(String name) {
        List<Anime> anime = animeRepository.findByName(name);
        return AnimeMapper.INSTANCE.toAnimeDto(anime);
    }

    public Anime findByIdOrThrowBadRequestException(long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found.git"));
    }

//    public AnimeDto findById(long id) {
//        Anime anime = findByIdOrThrowBadRequestException(id);
//        return AnimeMapper.INSTANCE.toAnimeDto(anime);
//    }

    public Anime findById(long id) {
        return findByIdOrThrowBadRequestException(id);
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
}
