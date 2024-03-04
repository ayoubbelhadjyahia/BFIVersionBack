package bfi.groupe.bfiversionback.repository;

import bfi.groupe.bfiversionback.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Integer> {
    @Query("select u from Utilisateur u where u.email like :email")
    Optional<Utilisateur> findByEmail(@Param("email") String email);

    @Query("select u from Utilisateur u where u.Username like :username order by u.Username desc limit 1")
    Utilisateur GetUserByUsername(@Param("username") String username);
    @Query("select u from Utilisateur u where u.email like :email order by u.email desc limit 1")
    Utilisateur GetUserByEmail(@Param("email") String email);
    @Query("select u from Utilisateur u where u.CodeVerification like :code order by u.CodeVerification desc limit 1")
    Utilisateur GetUserByCode(@Param("code") String email);
}
