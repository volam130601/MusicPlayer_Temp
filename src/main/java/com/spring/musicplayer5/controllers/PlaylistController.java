package com.spring.musicplayer5.controllers;

import com.spring.musicplayer5.dto.PlaylistDto;
import com.spring.musicplayer5.dto.ResponseObject;
import com.spring.musicplayer5.entity.Playlist;
import com.spring.musicplayer5.entity.User;
import com.spring.musicplayer5.services.PlaylistService;
import com.spring.musicplayer5.services.TrackPlaylistService;
import com.spring.musicplayer5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;
    @Autowired
    private TrackPlaylistService trackPlaylistService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllPlaylist() {
        List<Playlist> playlists = playlistService.getAll();
        if(!playlists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK" , "Get all data of Playlist Successfully!", playlists)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK" , "Playlist is null!", "")
        );
    }

    //Change Playlist follow FE
    @PostMapping
    public ResponseEntity<ResponseObject> createPlaylist(@RequestBody PlaylistDto playlistDto) {
        Optional<Playlist> existsPL = playlistService.findByName(playlistDto.getName());
        if(!existsPL.isPresent()) {
            Optional<User> user = userService.findByUsername(playlistDto.getUsername());
            if(user.isPresent()) {
                Playlist playlist = Playlist.builder()
                        .name(playlistDto.getName())
                        .user(user.get()).build();
                playlistService.save(playlist);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK" , "Create New Playlist Successfully!", playlist)
                );
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("FAILED" , "User isn't exists!", "")
                );
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("FAILED" , "Name Playlist is exists!", "")
        );
    }

    @GetMapping("/getByUsername")
    public ResponseEntity<ResponseObject> getByUsername(@RequestParam String username) {
        List<Playlist> playlists= playlistService.findPlaylistByUsername(username);
        if(playlists != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Get Playlist By Username is Successfully!", playlists)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("FAILED" , "Get Playlist By Username is null!", "")
        );
    }

    @PutMapping("/rename")
    public ResponseEntity<ResponseObject> renamePlaylist(@RequestParam String name, @RequestParam String rename) {
        Optional<Playlist> exists = playlistService.findByName(name);
        if(exists.isPresent()) {
            Optional<User> user = userService.findByUsername("lamlbx123");
            if(user.isPresent()) {
                Playlist playlist = Playlist.builder()
                        .id(exists.get().getId())
                        .name(rename)
                        .user(user.get())
                        .build();
                playlistService.save(playlist);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Rename Playlist Successfully!", playlist)
                );
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("FAILED" , "Name is exists or failed!", "")
        );
    }

    //Change later
    @DeleteMapping
    public ResponseEntity<ResponseObject> deleteById(@RequestParam long id) {
        Optional<Playlist> exists = playlistService.findById(id);
        if(exists.isPresent()) {
            trackPlaylistService.deleteByIdPlaylistId(id);
            playlistService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("SUCCESS" , "Delete playlist success!", "SUCCESS")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("FAILED" , "Id isn't exists or failed!", "")
        );
    }
}
