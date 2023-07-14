package kz.silk.onlinebank.controller;

import kz.silk.onlinebank.domain.dto.request.ProfileUpdateDto;
import kz.silk.onlinebank.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(ProfileController.CONTROLLER_PATH)
public class ProfileController {

    public static final String CONTROLLER_PATH = "/profile";

    private final ProfileService profileService;

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
                                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateProfile(@RequestBody @Valid ProfileUpdateDto profileUpdateDto) {
        profileService.updateCurrentProfile(profileUpdateDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/edit-role")
    public ResponseEntity<Void> updateProfileRole(@RequestParam(value = "id") Long id,
                                                  @RequestParam(value = "role") String role) {
        profileService.updateProfileRole(id, role);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/lock")
    public ResponseEntity<Void> lockProfile(@RequestParam(value = "id") Long id,
                                            @RequestParam(value = "blocked", defaultValue = "false") boolean blocked) {
        profileService.updateLockoutStatus(id, blocked);
        return ResponseEntity.ok().build();
    }
}
