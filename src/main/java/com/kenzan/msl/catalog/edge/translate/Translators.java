/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.catalog.edge.translate;

import com.kenzan.msl.catalog.client.dao.FacetDao;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.AlbumListBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.ArtistListBo;
import com.kenzan.msl.common.bo.SongBo;
import com.kenzan.msl.common.bo.SongListBo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;
import io.swagger.model.ArtistInfo;
import io.swagger.model.ArtistList;
import io.swagger.model.FacetInfo;
import io.swagger.model.PagingState;
import io.swagger.model.SongInfo;
import io.swagger.model.SongList;

/**
 * @author billschwanitz
 */
public class Translators {

    // ==========================================================================================================
    // ALBUMS
    // ==========================================================================================================

    public static AlbumList translate(AlbumListBo listBo) {
        AlbumList model = new AlbumList();
        if ( listBo != null ) {
            PagingState pagingState = new PagingState();
            pagingState.setPagingState(null == listBo.getPagingState() ? null : listBo.getPagingState().toString());
            model.setPagingState(pagingState);

            for ( AlbumBo albumBo : listBo.getBoList() ) {
                model.getAlbums().add(Translators.translate(albumBo));
            }
        }
        return model;
    }

    public static AlbumInfo translate(AlbumBo bo) {
        AlbumInfo model = new AlbumInfo();

        if ( bo != null ) {
            model.setAlbumId(null == bo.getAlbumId() ? null : bo.getAlbumId().toString());
            model.setAlbumName(bo.getAlbumName());
            model.setArtistId(null == bo.getArtistId() ? null : bo.getArtistId().toString());
            model.setArtistMbid(null == bo.getArtistMbid() ? null : bo.getArtistMbid().toString());
            model.setArtistName(bo.getArtistName());
            model.setGenre(bo.getGenre());
            model.setYear(bo.getYear());
            model.setAverageRating(bo.getAverageRating());
            model.setPersonalRating(bo.getPersonalRating());
            model.setImageLink(bo.getImageLink());
            model.setInMyLibrary(bo.isInMyLibrary());
            model.setFavoritesTimestamp(bo.getFavoritesTimestamp());
            if ( null == bo.getSongsList() || bo.getSongsList().isEmpty() ) {
                model.setSongsList(null);
            }
            else {
                model.getSongsList().addAll(bo.getSongsList());
            }
        }

        return model;
    }

    // =========================================================================================================
    // ARTISTS
    // =========================================================================================================

    public static ArtistList translate(ArtistListBo listBo) {
        ArtistList model = new ArtistList();

        if ( listBo != null ) {
            PagingState pagingState = new PagingState();
            pagingState.setPagingState(null == listBo.getPagingState() ? null : listBo.getPagingState().toString());
            model.setPagingState(pagingState);

            if ( null == listBo.getBoList() || listBo.getBoList().isEmpty() ) {
                model.setArtists(null);
            }
            else {
                for ( ArtistBo artistBo : listBo.getBoList() ) {
                    model.getArtists().add(Translators.translate(artistBo));
                }
            }
        }

        return model;
    }

    public static ArtistInfo translate(ArtistBo bo) {
        ArtistInfo model = new ArtistInfo();

        if ( bo != null ) {
            model.setArtistId(null == bo.getArtistId() ? null : bo.getArtistId().toString());
            model.setArtistMbid(null == bo.getArtistMbid() ? null : bo.getArtistMbid().toString());
            model.setArtistName(bo.getArtistName());
            model.setGenre(StringUtils.isEmpty(bo.getGenre()) ? null : bo.getGenre());
            model.setAverageRating(bo.getAverageRating());
            model.setPersonalRating(bo.getPersonalRating());
            model.setImageLink(bo.getImageLink());
            model.setAlbumsList((null == bo.getAlbumsList() || bo.getAlbumsList().isEmpty()) ? null : bo
                .getAlbumsList());
            model.setSongsList((null == bo.getSongsList() || bo.getSongsList().isEmpty()) ? null : bo.getSongsList());
            model.setInMyLibrary(bo.isInMyLibrary());
            model.setFavoritesTimestamp(bo.getFavoritesTimestamp());
            model
                .setSimilarArtistsList((null == bo.getSimilarArtistsList() || bo.getSimilarArtistsList().isEmpty())
                                                                                                                   ? null
                                                                                                                   : bo.getSimilarArtistsList());
        }

        return model;
    }

    // ===========================================================================================================
    // SONGS
    // ===========================================================================================================

    public static SongList translate(SongListBo listBo) {
        SongList model = new SongList();

        if ( listBo != null ) {
            PagingState pagingState = new PagingState();
            pagingState.setPagingState(null == listBo.getPagingState() ? null : listBo.getPagingState().toString());
            model.setPagingState(pagingState);

            if ( null == listBo.getBoList() || listBo.getBoList().isEmpty() ) {
                model.setSongs(null);
            }
            else {
                for ( SongBo songBo : listBo.getBoList() ) {
                    model.getSongs().add(Translators.translate(songBo));
                }
            }
        }

        return model;
    }

    public static SongInfo translate(SongBo bo) {
        SongInfo model = new SongInfo();

        if ( bo != null ) {
            model.setSongId(null == bo.getSongId() ? null : bo.getSongId().toString());
            model.setSongName(bo.getSongName());
            model.setGenre(bo.getGenre());
            model.setDuration(bo.getDuration());
            model.setDanceability(bo.getDanceability());
            model.setSongHotttnesss(bo.getSongHotttnesss());
            model.setYear(bo.getYear());
            model.setAverageRating(bo.getAverageRating());
            model.setPersonalRating(bo.getPersonalRating());
            model.setImageLink(bo.getImageLink());
            model.setArtistId(null == bo.getArtistId() ? null : bo.getArtistId().toString());
            model.setArtistMbid(null == bo.getArtistMbid() ? null : bo.getArtistMbid().toString());
            model.setArtistName(bo.getArtistName());
            model.setAlbumId(null == bo.getAlbumId() ? null : bo.getAlbumId().toString());
            model.setAlbumName(bo.getAlbumName());
            model.setInMyLibrary(bo.isInMyLibrary());
            model.setFavoritesTimestamp(bo.getFavoritesTimestamp());
        }

        return model;
    }

    // ==========================================================================================================
    // FACETS
    // ==========================================================================================================

    public static FacetInfo translate(FacetDao dao) {
        FacetInfo model = new FacetInfo();

        if ( dao != null ) {
            model.setFacetId(dao.getFacetId());
            model.setName(dao.getFacetName());
        }

        return model;
    }

    public static List<FacetInfo> translateFacetList(List<FacetDao> daoList) {
        if ( daoList != null ) {
            List<FacetInfo> modelList = new ArrayList<>(daoList.size());
            for ( FacetDao dao : daoList ) {
                modelList.add(translate(dao));
            }
            return modelList;
        }
        return new ArrayList<>();
    }
}
