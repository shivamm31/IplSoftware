package com.controller;
 import com.model.TeamModel;
import com.service.TeamService;
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
 * The type Team controller.
 */
@Controller
public class TeamController {
    @Autowired
    private final TeamService service;


    /**
     * Instantiates a new Team controller.
     *
     * @param service the service
     */
    public TeamController(TeamService service) {
        this.service = service;
    }

    /**
     * Add string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping(value = "/addTeam")
    public String add(final Model model) {
        model.addAttribute("Team", new TeamModel());
        return "addTeam";
    }

    /**
     * Player management string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping(value = "/editTeam")
    public String playerManagement(final Model model) {
//        model.addAttribute("Team", new TeamModel());
        List<TeamModel> teamList = service.listAll();
        model.addAttribute("teamList", teamList);
        return "editTeam";
    }

    /**
     * Save team string.
     *
     * @param tm                 the tm
     * @param result             the result
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTeam(@Valid @ModelAttribute("Team") TeamModel tm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (service.teamNameExists(tm.getTeamname())) {
            result.addError(new FieldError("tm", "teamname", "Team already exists"));
        }
        if (result.hasErrors()) {
            return "addTeam";
        }
        else {
                service.saveTeams(tm);
                redirectAttributes.addFlashAttribute("message", "Team added successfully");
                redirectAttributes.addFlashAttribute("messageType", "team");
                redirectAttributes.addFlashAttribute("alertType", "success");
        }
        return "redirect:editTeam";
    }

    /**
     * View teams page string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/Teams")
    public String viewTeamsPage(final Model model) {
        List<TeamModel> teamList = service.listAll();
        model.addAttribute("teamList", teamList);
        return "Teams";
    }

    /**
     * View teams 1 string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/Admin")
    public String viewTeams1(final Model model) {
        List<TeamModel> teamList = service.listAll();
        model.addAttribute("teamList", teamList);

        return "Admin";
    }

    /**
     * Update player string.
     *
     * @param teamModel          the team model
     * @param result             the result
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @RequestMapping(value = "/UpdateTeam", method = RequestMethod.POST)
    public String updatePlayer(@Valid @ModelAttribute("Team") final TeamModel teamModel, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "updateTeam";

        }
        else {
            service.saveTeams(teamModel);
            redirectAttributes.addFlashAttribute("messageEdit", "Team Updated successfully");
            redirectAttributes.addFlashAttribute("messageType", "Team");
            redirectAttributes.addFlashAttribute("alertType", "success");
            return "redirect:editTeam";


        }
    }


    /**
     * Edit team view model and view.
     *
     * @param id the id
     * @return the model and view
     */
    @RequestMapping("/teamEdit/{id}")
    public ModelAndView editTeamView(@PathVariable(name = "id") int id) {
        ModelAndView modelAndView = new ModelAndView("updateTeam");
        TeamModel teamModel = service.getIds(String.valueOf(id));
        modelAndView.addObject("team", teamModel);
        List<TeamModel> teamList = service.listAll();
        modelAndView.addObject("teamList", teamList);
        return modelAndView;
    }

    /**
     * Delete team string.
     *
     * @param id                 the id
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @RequestMapping("/deleteTeam/{id}")
    public String deleteTeam(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        service.deleteTeams(id);
        redirectAttributes.addFlashAttribute("success", "Team deleted successfully");
        redirectAttributes.addFlashAttribute("messageType", "team");
        redirectAttributes.addFlashAttribute("alertType", "success");
        return "redirect:/editTeam";
    }

}
