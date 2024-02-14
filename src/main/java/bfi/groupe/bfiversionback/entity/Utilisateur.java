package bfi.groupe.bfiversionback.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id ;
    private String Username;
    private String Email;
    private String Password;
    private Long Phonenumber;
    private String CodeVerification;
    @Enumerated(EnumType.STRING)
    private  Role Role;
    private LocalDateTime DateEndCode;

}
