package com.spring.musicplayer5.dto;

import com.spring.musicplayer5.entity.Album;
import com.spring.musicplayer5.entity.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class AlbumOfTrackDto {
    private Album album;
    private List<Track> tracks;
}
