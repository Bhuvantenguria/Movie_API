package com.cinema.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cinema.dto.MovieDto;
import com.cinema.dto.MoviePageResponse;
import com.cinema.entities.Movie;
import com.cinema.exception.MovieNotFoundException;
import com.cinema.repositories.MovieRepository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto) throws IOException {
        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getGenre());
        
        Movie savedMovie = movieRepository.save(movie);
        
        return new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getGenre());
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie Not Found with id= " + movieId));
        
        return new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getGenre());
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = new ArrayList<>();
        
        for (Movie movie : movies) {
            MovieDto dto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getGenre());
            movieDtos.add(dto);
        }
        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto) throws IOException {
        Movie existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie Not Found with id= " + movieId));
        
        Movie movieToUpdate = new Movie(
                existingMovie.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getGenre());
        
        Movie updatedMovie = movieRepository.save(movieToUpdate);
        
        return new MovieDto(
                updatedMovie.getMovieId(),
                updatedMovie.getTitle(),
                updatedMovie.getDirector(),
                updatedMovie.getStudio(),
                updatedMovie.getMovieCast(),
                updatedMovie.getReleaseYear(),
                updatedMovie.getGenre());
    }

    @Override
    public String deleteMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie Not Found with id= " + movieId));
        movieRepository.delete(movie);
        return "Movie deleted with id: " + movie.getMovieId();
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Movie> moviePages = movieRepository.findAll(pageable);
        List<MovieDto> movieDtos = new ArrayList<>();
        
        for (Movie movie : moviePages.getContent()) {
            MovieDto dto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getGenre());
            movieDtos.add(dto);
        }
        return new MoviePageResponse(
                movieDtos, 
                pageNumber, 
                pageSize, 
                moviePages.getTotalElements(),
                moviePages.getTotalPages(), 
                moviePages.isLast());
    }

    @Override
    public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy,
            String dir) {
        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Movie> moviePages = movieRepository.findAll(pageable);
        List<MovieDto> movieDtos = new ArrayList<>();
        
        for (Movie movie : moviePages.getContent()) {
            MovieDto dto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getGenre());
            movieDtos.add(dto);
        }
        
        return new MoviePageResponse(
                movieDtos, 
                pageNumber, 
                pageSize, 
                moviePages.getTotalElements(),
                moviePages.getTotalPages(), 
                moviePages.isLast());
    }

    @Override
    public MoviePageResponse searchMovies(String name, String director, String cast, Integer year, String genre,
            Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        
        Specification<Movie> spec = (root, query, cb) -> {
            Predicate p = cb.conjunction();
            
            if (name != null && !name.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("title")), "%" + name.toLowerCase() + "%"));
            }
            if (director != null && !director.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("director")), "%" + director.toLowerCase() + "%"));
            }
            if (year != null) {
                p = cb.and(p, cb.equal(root.get("releaseYear"), year));
            }
            if (genre != null && !genre.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("genre")), "%" + genre.toLowerCase() + "%"));
            }
            if (cast != null && !cast.isEmpty()) {
                Join<Movie, String> castJoin = root.join("movieCast");
                p = cb.and(p, cb.like(cb.lower(castJoin), "%" + cast.toLowerCase() + "%"));
            }
            return p;
        };

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Movie> moviePages = movieRepository.findAll(spec, pageable);
        
        List<MovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : moviePages.getContent()) {
            MovieDto dto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getGenre());
            movieDtos.add(dto);
        }
        
        return new MoviePageResponse(
                movieDtos, 
                pageNumber, 
                pageSize, 
                moviePages.getTotalElements(),
                moviePages.getTotalPages(), 
                moviePages.isLast());
    }
}
