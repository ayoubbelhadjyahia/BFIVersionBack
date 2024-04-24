package bfi.groupe.bfiversionback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitLabCode {
    Integer id;
    String path;
    String branch;
}
