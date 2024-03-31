package bfi.groupe.bfiversionback.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GitLabGroup {
    private int id;
    private String web_url;
    private String name;
    private String path;
    private String description;
    private String visibility;
    private boolean share_with_group_lock;
    private boolean require_two_factor_authentication;
    private int two_factor_grace_period;
    private String project_creation_level;
    private String auto_devops_enabled;
    private String subgroup_creation_level;
    private Boolean emails_disabled;
    private Boolean mentions_disabled;
    private boolean lfs_enabled;
    private int default_branch_protection;
    private String avatar_url;
    private boolean request_access_enabled;
    private String full_name;
    private String full_path;
    private String created_at;
    private int parent_id;
}
