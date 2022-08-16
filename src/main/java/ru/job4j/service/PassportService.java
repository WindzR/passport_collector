package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Passport;
import ru.job4j.repository.PassportRepository;

import java.util.Optional;

@Service
public class PassportService {

    private PassportRepository repository;

    public PassportService(PassportRepository repository) {
        this.repository = repository;
    }

    public Optional<Passport> save(Passport passport, String series) {
        Optional<Passport> passportFromRepo = repository.findPassportBySeries(series);
        if (passportFromRepo.isEmpty()) {
            Passport rsl = repository.save(passport);
            return Optional.of(rsl);
        }
        return Optional.empty();
    }
}
