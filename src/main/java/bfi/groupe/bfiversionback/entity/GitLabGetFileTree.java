package bfi.groupe.bfiversionback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitLabGetFileTree {
    int id;
    String path;
}
