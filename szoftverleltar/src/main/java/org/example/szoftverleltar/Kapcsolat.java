package org.example.szoftverleltar;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Kapcsolat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bekuldo;
    private String uzenet;

    @Column(name = "bekuldes_ideje")  // Pontosan megadjuk az adatbázis mezőnevet
    private LocalDateTime bekuldes_ideje = LocalDateTime.now();

    // getterek és setterek
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBekuldo() {
        return bekuldo;
    }

    public void setBekuldo(String bekuldo) {
        this.bekuldo = bekuldo;
    }

    public String getUzenet() {
        return uzenet;
    }

    public void setUzenet(String uzenet) {
        this.uzenet = uzenet;
    }

    public String getBekuldesIdejeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return bekuldes_ideje.format(formatter);
    }

    public LocalDateTime getBekuldes_ideje() {
        return bekuldes_ideje;
    }

    public void setBekuldes_ideje(LocalDateTime bekuldes_ideje) {
        this.bekuldes_ideje = bekuldes_ideje;
    }
}