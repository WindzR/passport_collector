package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Passport;

import java.util.Optional;

public interface PassportRepository extends CrudRepository<Passport, Integer> {

    Optional<Passport> findPassportBySeries(String series);
}
