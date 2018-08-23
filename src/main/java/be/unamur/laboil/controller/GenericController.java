package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.ConversationItem;
import be.unamur.laboil.domain.view.EventDetailedView;
import be.unamur.laboil.domain.view.TownView;
import be.unamur.laboil.manager.SystemManager;
import be.unamur.laboil.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Joachim Lebrun on 10-08-18
 */
@RestController
public class GenericController {
    @Autowired
    private UserManager userManager;
    @Autowired
    private SystemManager systemManager;

    @ModelAttribute("towns")
    public List<TownView> populateTowns() {
        return systemManager.getAllTowns();
    }


    @GetMapping({"/demands/{id}/officialDocument"})
    public ResponseEntity<byte[]> generate(@PathVariable("id") String demandID) throws IOException {
        FileSystemResource fileSystemResource = userManager.generatePDF(demandID);
        byte[] contents = Files.readAllBytes(Paths.get(fileSystemResource.getPath()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = fileSystemResource.getFilename();
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    @GetMapping({"/demands/{id}/events"})
    public List<ConversationItem> history(@PathVariable("id") String demandID, @PathParam("citizen") boolean citizen) {
        return userManager.getHistoryOf(demandID, citizen);
    }

    @GetMapping({"/demands/{id}/events/detailed"})
    public List<EventDetailedView> detailed (@PathVariable("id") String demandID) {
        return userManager.getDetailedHistoryOf(demandID);
    }
}
