package com.proyecto.time.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.time.entities.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{

}
