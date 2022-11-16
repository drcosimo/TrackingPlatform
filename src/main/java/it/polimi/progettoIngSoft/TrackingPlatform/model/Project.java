package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private Instant beginDate;

    @Column
    private Instant endDate;

    @Column(nullable = false)
    private boolean isPost;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<ActivityProject> activities;


    @ManyToMany
    @JoinTable(
            name = "creators_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> creators;

    @ManyToMany
    @JoinTable(
            name = "admins_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> admins;

    @ManyToMany
    @JoinTable(
            name = "partecipants_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> partecipants;

    @ManyToMany
    @JoinTable(
            name = "projects_reactions",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "reaction_id")
    )
    private List<ProjectReactions> reactions;


    @OneToMany(mappedBy = "project")
    private List<ProjectImage> images;

    public Project() {
    }

}
