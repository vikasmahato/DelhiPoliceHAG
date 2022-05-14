package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface LetterRepository extends JpaRepository<Letter, UUID> {

    @Query("SELECT l FROM Letter l WHERE l.letterDate = :letterDate")
    List<Letter> findAll(@Param("letterDate") Date letterDate);

    @Query("SELECT l FROM Letter l")
    List<Letter> findAll();
}
