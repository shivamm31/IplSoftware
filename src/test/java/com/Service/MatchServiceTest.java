package com.Service;

import com.dao.MatchRepo;
import com.dao.TeamRepo;
import com.model.MatchModel;
import com.model.TeamModel;
import com.service.MatchService;
import com.service.TeamService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * The type Match service test.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
  class MatchServiceTest {
    @MockBean
    TeamRepo teamRepo;
    @MockBean
    private MatchRepo matchRepo;
    @Mock
    private MatchService matchService;
@Mock
        private TeamService teamService;
    /**
     * The Match 1.
     */
    MatchModel match1=new MatchModel();
    /**
     * The Match 2.
     */
    MatchModel match2=new MatchModel();

    /**
     * The Team 1.
     */
    TeamModel team1=new TeamModel();
    /**
     * The Team 2.
     */
    TeamModel team2=new TeamModel();
    /**
     * The Match list.
     */
    List<MatchModel> matchList = new ArrayList<>();
    List<TeamModel> teamList = new ArrayList<>();


    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        this.matchService = new MatchService(this.matchRepo);
        this.teamService=new TeamService(this.teamRepo);
        ///add team1
        team1.setId(1L);
        team1.setTeamname("Tigers");
        team1.setCaptain("Akshit");
        team1.setState("UP");
        ///add team2
        team2.setId(2L);
        team2.setTeamname("Panthers");
        team2.setCaptain("Shubham");
        team2.setState("Delhi");
teamList.add(team1);
teamList.add(team2);
        //add match1
        match1.setMatchid(1);
        match1.setTeam1(team1);
        match1.setTeam2(team2);
        match1.setScheduledate("30/02/2021");
        match1.setVenue("Mohali Stadium");
        //add match1
        match2.setMatchid(2);
        match2.setTeam1(team1);
        match2.setTeam2(team2);
        match2.setScheduledate("26/02/2021");
        match2.setVenue("Delhi Stadium");
        matchList.add(match1);
        matchList.add(match2);
    }


    /**
     * Test save match.
     */
    @Test
    public void testSaveMatch(){
        if(team1.getTeamname()==(team2.getTeamname()))
        {
            Assertions.fail("teams are same");
        }
        else if (team1.getId().equals(team2.getId())) {
            Assertions.fail("Teams are same");
        }
        else {
            for (MatchModel match:matchList) {
                Mockito.when(matchRepo.save(match)).thenReturn(match);
                assertThat(matchService.saveMatch(match)).isEqualTo(match);
            }
        }

    }

    /**
     * Test get all matches.
     */
    @Test
    public void testGetAllMatches(){
        Mockito.when(matchRepo.findAll()).thenReturn(matchList);
        assertThat(matchService.getAllMatches()).isEqualTo(matchList);

    }

    /**
     * Testget match by id.
     */
    @Test
    public void testgetMatchByID(){

        Mockito.when(matchRepo.findById(1)).thenReturn(Optional.of((match1)));
        assertThat(matchService.get(1)).isEqualTo(match1);
    }

    /**
     * Test delete match.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeleteMatch() throws Exception {
        matchService.delete(match1.getMatchid());
        Mockito.verify(matchRepo, Mockito.times(1))
                .deleteById(match1.getMatchid());
    }

    /**
     * Testget by venue.
     */
    @Test
    public void testgetByVenue(){

        Mockito.when(matchRepo.findByVenue("Mohali Stadium")).thenReturn(Optional.of(match1));
        assertThat(matchService.findVenueIsExist("Mohali Stadium")).isEqualTo(Optional.of(match1));
    }

    /**
     * Testget by venue is persent.
     *
     * @throws Exception the exception
     */
    @Test
    public void testgetByVenueIsPersent() throws Exception {
        BindingResult result=null;
        Mockito.when(matchRepo.findByVenue("Mohali Stadium")).thenReturn(Optional.of(match1));
        assertThat(matchService.venueExists("Mohali Stadium",result)).isEqualTo(Optional.of(match1).isPresent());
    }

    /**
     * Testget by date.
     */
    @Test
    public void testgetByDate(){

        Mockito.when(matchRepo.findByScheduledate("30/02/2021")).thenReturn(Optional.of(match1));
        assertThat(matchService.findDateIsExist("30/02/2021")).isEqualTo(Optional.of(match1));
    }

    /**
     * Testget by date is persent.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetByDateIsPersent()throws Exception {
BindingResult bindingResult = null;
        Mockito.when(matchRepo.findByScheduledate("30/02/2021")).thenReturn(Optional.of(match1));
        assertThat(matchService.DateIsExist("30/02/2021",bindingResult)).isEqualTo(Optional.of(match1).isPresent());
    }

    /**
     * Testget by teamis exit.
     */
    @Test
    public void testGetByTeamIsExit(){

        Mockito.when(matchRepo.findByTeam1(team1)).thenReturn(Optional.of(match1));
        assertThat(matchService.findTeam(team1)).isEqualTo(Optional.of(match1));
    }

    /**
     * Testget by team is persent.
     *
     * @throws Exception the exception
     */
    @Test
    public void testgetByTeamIsPersent() throws Exception {
        BindingResult result=null;
        Mockito.when(matchRepo.findByTeam1(team1)).thenReturn(Optional.of(match1));
        assertThat(matchService.teamIsExist(team1,result)).isEqualTo(Optional.of(match1).isPresent());
    }

    @Test
    public void testViewAllMatches(){
        final Model model = new ExtendedModelMap();
        Mockito.when(matchRepo.findAll()).thenReturn(matchList);
         Model model1=matchService.viewMatchs(model);

        model.addAttribute("matchList",matchList);
         assertThat(matchService.viewMatchs(model)).isEqualTo(model1);

    }


    @Test
    public void testViewScores(){
        final Model model = new ExtendedModelMap();
        Mockito.when(matchRepo.findAll()).thenReturn(matchList);
        Model model1=matchService.viewScores(model);

        model.addAttribute("matchList",matchList);
        assertThat(matchService.viewScores(model)).isEqualTo(model1);

    }
    @Test
    public void testMatchResult(){
        final Model model = new ExtendedModelMap();
        Mockito.when(matchRepo.findAll()).thenReturn(matchList);
        Model model1=matchService.matchResult(model);

        model.addAttribute("matchList",matchList);
        assertThat(matchService.matchResult(model)).isEqualTo(model1);

    }
    @Test
    public void testScoreResult(){
        final Model model = new ExtendedModelMap();
        Mockito.when(matchRepo.findAll()).thenReturn(matchList);
        Model model1=matchService.scoresResult(model);

        model.addAttribute("matchList",matchList);
        assertThat(matchService.scoresResult(model)).isEqualTo(model1);

    }


}
