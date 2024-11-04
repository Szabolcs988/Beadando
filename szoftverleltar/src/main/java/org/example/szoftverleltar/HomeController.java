package org.example.szoftverleltar;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/home")
    public String user() {
        return "user";
    }
    @GetMapping("/admin/home")
    public String admin() {
        return "admin";
    }

    @GetMapping("/jelszoteszt")
    @ResponseBody
    public String jelszóTeszt() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode("password");
    }

    @GetMapping("/regisztral")
    public String greetingForm(Model model) {
        model.addAttribute("reg", new User());
        return "regisztral";
    }
    @Autowired
    private UserRepository userRepo;
    @PostMapping("/regisztral_feldolgoz")
    public String Regisztráció(@ModelAttribute User user, Model model) {

        for(User felhasznalo2: userRepo.findAll())
            if(felhasznalo2.getEmail().equals(user.getEmail())){
                model.addAttribute("uzenet", "A regisztrációs email már foglalt!");
                return "reghiba";
            }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
// Regisztrációkor minden felhasználónak Vendég szerepet adunk:
        user.setRole("ROLE_Vendeg");
        userRepo.save(user);
        model.addAttribute("id", user.getId());
        return "regjo";
    }


    @Autowired
    SzoftverRepository szoftverRepository;

    @GetMapping("/szoftverek")
    public String Szoftver(Model model) {
        model.addAttribute("szoftverek", szoftverRepository.findAll());
        return "szoftverek";
    }

    @Autowired
    private KapcsolatRepository kapcsolatRepository;

    @GetMapping("/kapcsolat")
    public String Ujkapcsolat(Model model) {
        Kapcsolat kapcsolat = new Kapcsolat();

        // Ellenőrizni, hogy a felhasználó be van-e jelentkezve
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            String email = auth.getName(); // E-mail cím a bejelentkezett felhasználóhoz
            userRepo.findByEmail(email).ifPresent(user -> kapcsolat.setBekuldo(user.getName()));
        } else {
            kapcsolat.setBekuldo("Vendég");
        }

        model.addAttribute("kapcsolat", kapcsolat);
        return "kapcsolat";


    }
    @PostMapping(value="/ment")

    public String mentKapcsolat(@ModelAttribute Kapcsolat kapcsolat, RedirectAttributes redirAttr){
        kapcsolatRepository.save(kapcsolat);
        return "redirect:/";
    }

    @GetMapping("/uzenetek")
    public String Uzenetek(Model model) {
        model.addAttribute("uzenetek", kapcsolatRepository.findAllByOrderByBekuldes_ideje());
        return "uzenetek";
    }

}
