package com.avanade.adnd.repository;

import com.avanade.adnd.model.Character;
import com.avanade.adnd.model.enums.CharacterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findAllByType(CharacterType type);
}
