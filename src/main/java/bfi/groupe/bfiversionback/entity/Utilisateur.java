package bfi.groupe.bfiversionback.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id ;
    private String Username;
    private String Email;
    private String Password;
    private String CodeVerification;

    private LocalDateTime DateEndCode;

}
