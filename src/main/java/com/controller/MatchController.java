package com.controller;



import com.dao.MatchRepo;
 import com.model.MatchModel;
import com.model.TeamModel;
import com.service.*;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Match controller.
 */
@Controller
public class MatchController {


    @Autowired
     private   ResultService reservice;
    @Autowired
    private   MatchService matchService;

    @Autowired
    private   TeamService teamService;

    @Autowired
    private MatchRepo matchRepo;

    /**
     * View match string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping(value = "/viewMatch")
    public String viewMatch(Model model) {
       matchService.viewMatchs(model);
        return "viewMatch";
    }

    /**
     * Add match string.
     *
     * @param model the model
     * @return the string
     */
//return match scheduling page
    @GetMapping(value = "/matchSchedule")
    public String addMatch(final Model model) {
   matchService.AddMatch(model);
        return "matchSchedule";
    }

    /**
     * Save match string.
     *
     * @param match              the match
     * @param result             the result
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
//Save a scheduled match and return veiw match page
    @RequestMapping(value = "/SaveMatch", method = RequestMethod.POST)
    final String saveMatch(@Valid @ModelAttribute("match") MatchModel match, final BindingResult result, RedirectAttributes redirectAttributes) {

        return matchService.saveMatches(match, result, redirectAttributes);

    }

    /**
     * Update mtch string.
     *
     * @param match              the match
     * @param result             the result
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @RequestMapping(value = "/upMatch", method = RequestMethod.POST)
    public String updateMtch(@Valid @ModelAttribute("match") MatchModel match, final BindingResult result, RedirectAttributes redirectAttributes) {

         if (matchService.venueExists(match.getVenue(), result) && matchService.DateIsExist(match.getScheduledate(), result)) {
            result.addError(new FieldError("match", "scheduledate", "date or venue already exists"));
        }

        if (result.hasErrors()) {

            return "updateMatch";
        }
        else {
            matchService.saveMatch(match);
            redirectAttributes.addFlashAttribute("Updatemessage", "Match Updated successfully");
              return "redirect:editMatch";
        }
    }

    /**
     * Updatescore string.
     *
     * @param match  the match
     * @param result the result
     * @return the string
     */
    @RequestMapping(value = "/SaveScore", method = RequestMethod.POST)
    public String updatescore(@Valid @ModelAttribute("match") MatchModel match, final BindingResult result) {

        if (match.getTeam1Overs() * 6 * 6 <= Double.parseDouble(match.getTeam1Description())) {
            result.addError(new FieldError("match", "Team1Description", "runs is not greater then balls"));
        }

        if (match.getTeam2Overs() * 6 * 6 <= Double.parseDouble(match.getTeam2Description())) {
            result.addError(new FieldError("match", "Team2Description", "runs is not greater then balls"));
        }
        if (result.hasErrors()) {
             return "UpdateScore";
        }
        else {
            match = reservice.getResult(match);
            // producerService.publishToTopic(match);
            matchService.saveMatch(match);
            return "redirect:EditListScore";
        }

    }

    /**
     * View score string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping(value = "/UpdateScore")
    public String viewAddScore(final Model model) {
      matchService.viewScores(model);
        return "UpdateScore";
    }

    /**
     * Score list string.
     *
     * @param model the model
     * @return the string
     */
// edit score List
    @GetMapping(value = "/EditListScore")
    public String scoreResultList(final Model model) {
      matchService.scoresResult(model);
        return "EditListScore";
    }

    /**
     * Show edit score model and view.
     *
     * @param id the id
     * @return the model and view
     */
    @RequestMapping("/editScore/{matchid}")
    public ModelAndView showEditScore(@PathVariable(name = "matchid") final String id) {
        ModelAndView modelAndView = new ModelAndView("UpdateScore");
        MatchModel matchModel = matchService.get(Integer.parseInt((id)));
        modelAndView.addObject("match", matchModel);

        List<TeamModel> teamList = teamService.listAll();
        modelAndView.addObject("teamList", teamList);

        return modelAndView;
    }

    /**
     * Show edit player model and view.
     *
     * @param id the id
     * @return the model and view
     */
//edit a match
    @RequestMapping("/editmatch/{matchid}")
    public ModelAndView showEditPlayer(@PathVariable(name = "matchid") final String id) {
        ModelAndView modelAndView = new ModelAndView("updateMatch");
        MatchModel matchModel = matchService.get(Integer.parseInt((id)));
        modelAndView.addObject("match", matchModel);
        List<TeamModel> teamList = teamService.listAll();
        modelAndView.addObject("teamList", teamList);
        return modelAndView;
    }

    /**
     * Deletestudent string.
     *
     * @param id                 the id
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
//delete a match
    @RequestMapping("/deletematch/{matchid}")
    public String deletestudent(@PathVariable(name = "matchid") final String id, RedirectAttributes redirectAttributes) {
        matchService.delete(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("Deletemessage", "Match Deleted successfully");
        return "redirect:/editMatch";
    }

    /**
     * View match result string.
     *
     * @param model the model
     * @return the string
     */
//show result
    @GetMapping(value = "/result")
    public String viewMatchResult(final Model model) {
   matchService.matchResult(model);
        return "result";
    }

    /**
     * Edit match string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping(value = "/editMatch")
    public String editMatch(final Model model) {
        List<MatchModel> matchModelsList = (List<MatchModel>) matchRepo.findAll();
        model.addAttribute("matchModelsList", matchModelsList);
        List<TeamModel> teamList = teamService.listAll();
        model.addAttribute("teamList", teamList);
        return "editMatch";
    }

}