package com.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


/**
 * The type Team model.
 */
@Entity
@Table(name = "team")
public class TeamModel {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @NotNull
    @Size(min = 2, max = 30)
    private String teamname;
    private String state;
    private String captain;

    @OneToMany(mappedBy = "team1", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MatchModel> addteam1;

    @OneToMany(mappedBy = "team2", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MatchModel> addteam2;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
     private List<PlayersModel> playersModel;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
   private List<PointModel> pointModels;

    /**
     * Instantiates a new Team model.
     *
     * @param id       the id
     * @param teamname the teamname
     * @param captain  the captain
     * @param state    the state
     */
    public TeamModel(long id, String teamname, String captain, String state) {
        this.teamname = teamname;
        this.captain = captain;
        this.state = state;
        this.id = id;
    }

    /**
     * Instantiates a new Team model.
     *
     * @param id the id
     */
    public TeamModel(long id) {
        this.id = id;
    }

    /**
     * Instantiates a new Team model.
     *
     * @param id       the id
     * @param teamname the teamname
     * @param captain  the captain
     * @param state    the state
     */
    public TeamModel(long id, boolean teamname, String captain, String state) {
        this.teamname = String.valueOf(teamname);
    }


    /**
     * Gets point models.
     *
     * @return the point models
     */
    public List<PointModel> getPointModels() {
        return pointModels;
    }

    /**
     * Sets point models.
     *
     * @param pointModels the point models
     */
    public void setPointModels(List<PointModel> pointModels) {
        this.pointModels = pointModels;
    }

    /**
     * Gets teamname.
     *
     * @return the teamname
     */
    public String getTeamname() {
        return teamname;
    }

    /**
     * Sets teamname.
     *
     * @param teamname the teamname
     */
    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {

        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets players model.
     *
     * @return the players model
     */
    public List<PlayersModel> getPlayersModel() {
        return playersModel;
    }

    /**
     * Sets players model.
     *
     * @param playersModel the players model
     */
    public void setPlayersModel(List<PlayersModel> playersModel) {
        this.playersModel = playersModel;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(String state) {
        this.state = state;
    }


    /**
     * Gets captain.
     *
     * @return the captain
     */
    public String getCaptain() {
        return captain;
    }

    /**
     * Sets captain.
     *
     * @param captain the captain
     */
    public void setCaptain(String captain) {
        this.captain = captain;
    }


    /**
     * Instantiates a new Team model.
     */
//    //constructer
//    public TeamModel(String teamname, Set<MatchModel> addteam2, List<PlayersModel> playersModel) {
//
//        this.teamname = teamname;
//        this.addteam2 = addteam2;
//        this.playersModel = playersModel;
//    }
    public TeamModel() {

    }
}