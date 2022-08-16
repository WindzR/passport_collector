package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Passport;
import ru.job4j.service.PassportService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/processor")
public class PassportProcessor {

    @Autowired
    private RestTemplate rest;

    private PassportService service;

    private static final String API_FIND = "http://localhost:8080/passports/find";

    private static final String API_FIND_S = "http://localhost:8080/passports/find-series";

    private static final String API_UNAVAILABLE = "http://localhost:8080/passports/unavailable";

    private static final String API_REPLACEABLE = "http://localhost:8080/passports/find-replaceable";

    public PassportProcessor(PassportService service) {
        this.service = service;
    }

    /**
     * Получаем все паспорта
     * @return список паспортов
     */
    @GetMapping("/all")
    public List<Passport> findAllPassports() {
        List<Passport> rsl = new ArrayList<>();
        List<Passport> passports = rest.exchange(
                API_FIND,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {}
        ).getBody();
        for (Passport passport : passports) {
            Passport pspt = Passport.of(passport.getId(), passport.getSeries(), passport.getExpiredDate());
            rsl.add(pspt);
        }
        return rsl;
    }

    /**
     * Получаем паспорт нужной серии и сохраняем в локальную базу данных
     * @param series серия
     * @return паспорт
     */
    @PostMapping("/series")
    public ResponseEntity<Passport> findPassportsBySeries(@RequestParam("seria") String series) {
        String URL = API_FIND_S + "?seria=" + series;
        Passport passport = rest.exchange(
                URL,
                HttpMethod.GET, null, new ParameterizedTypeReference<Passport>() {}
        ).getBody();
        Passport findPassport = Passport.of(passport.getSeries(), passport.getExpiredDate());
        Optional<Passport> rsl = service.save(findPassport, series);
        return new ResponseEntity<Passport>(
                rsl.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FOUND,
                        "Passport with such series already exist!."
                )),
                HttpStatus.OK
        );
    }

    /**
     * Получить все просроченные паспорта
     * @return список паспортов
     */
    @GetMapping("/unavailable")
    public List<Passport> findUnavailablePassports() {
        List<Passport> rsl = new ArrayList<>();
        List<Passport> passports = rest.exchange(
                API_UNAVAILABLE,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {}
        ).getBody();
        for (Passport passport : passports) {
            Passport pspt = Passport.of(passport.getId(), passport.getSeries(), passport.getExpiredDate());
            rsl.add(pspt);
        }
        return rsl;
    }

    /**
     * Получить паспорта, скоро потребующие замены
     * @return список паспортов
     */
    @GetMapping("/replaceable")
    public List<Passport> findReplaceablePassports() {
        List<Passport> rsl = new ArrayList<>();
        List<Passport> passports = rest.exchange(
                API_REPLACEABLE,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {}
        ).getBody();
        for (Passport passport : passports) {
            Passport pspt = Passport.of(passport.getId(), passport.getSeries(), passport.getExpiredDate());
            rsl.add(pspt);
        }
        return rsl;
    }
}
