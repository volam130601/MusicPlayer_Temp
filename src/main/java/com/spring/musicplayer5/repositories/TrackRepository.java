package com.spring.musicplayer5.repositories;

import com.spring.musicplayer5.entity.Album;
import com.spring.musicplayer5.entity.Artist;
import com.spring.musicplayer5.entity.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    @Query("SELECT t FROM Track t ORDER BY t.release_date DESC")
    Page<Track> findAll(Pageable pageable);

    @Modifying
    @Query("SELECT t FROM Track t WHERE t.title like ?1%")
    List<Track> findByTitle(String title);

    List<Track> findByAlbum_Id(long id);

    @Query("SELECT t FROM Track t ORDER BY t.rank DESC")
    List<Track> findByTop(Pageable pageable);
;}
