package ru.job4j.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "passportDB")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String series;

    @Column(name = "expired_date")
    private Date expiredDate;

    public static Passport of(int id, String series, Date expiredDate) {
        Passport passport = new Passport();
        passport.setId(id);
        passport.setSeries(series);
        passport.setExpiredDate(expiredDate);
        return passport;
    }

    public static Passport of(String series, Date expiredDate) {
        Passport passport = new Passport();
        passport.setSeries(series);
        passport.setExpiredDate(expiredDate);
        return passport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Passport passport = (Passport) o;
        return Objects.equals(id, passport.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
