package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.DevDojoUser;
import academy.devdojo.springboot2.domain.dto.AnimeDto;
import academy.devdojo.springboot2.service.DevDojoUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@Log4j2
@RequiredArgsConstructor
public class DevDojoUserController {

    private final DevDojoUserDetailsService devDojoUserDetailsService;

    @GetMapping
    public ResponseEntity<List<DevDojoUser>> list() {
        return ResponseEntity.ok(devDojoUserDetailsService.findAll());
    }

    @GetMapping(path = "/find")
    public ResponseEntity<UserDetails> findByUserName(@RequestParam String username) {
        return ResponseEntity.ok(devDojoUserDetailsService.loadUserByUsername(username));
    }
}
