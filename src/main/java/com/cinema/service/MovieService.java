package com.cinema.service;

import java.io.IOException;
import java.util.List;
import com.cinema.dto.MovieDto;
import com.cinema.dto.MoviePageResponse;

public interface MovieService {
    
    MovieDto addMovie(MovieDto movieDto) throws IOException;
    
    MovieDto getMovie(Integer movieId);
    
    List<MovieDto> getAllMovies();
    
    MovieDto updateMovie(Integer movieId, MovieDto movieDto) throws IOException;
    
    String deleteMovie(Integer movieId);
    
    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize);
    
    MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir);
    
    // New search method
    MoviePageResponse searchMovies(String name, String director, String cast, Integer year, String genre,
            Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
