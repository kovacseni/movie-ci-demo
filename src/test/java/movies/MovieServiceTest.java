package movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository repository;

    @InjectMocks
    MovieService service;

    @BeforeEach
    void init(){
        lenient().when(repository.findByTitle("Titanic")).thenReturn(
                Optional.of(new Movie(1L, "Titanic", LocalDate.of(1992, 11, 2), 121))
        );

    }

    @Test
    void testSaveMovie() {

        Movie temp = new Movie(1L, "Titanic", LocalDate.of(1992, 11, 2), 121);
        when(repository.saveMovie(any(Movie.class))).thenReturn(
                new Movie(1L, "Titanic", LocalDate.of(1992, 11, 2), 121)
        );

        Movie movie = service.saveMovie("Titanic", LocalDate.of(1992, 11, 2), 121);

        assertThat(movie.getId()).isEqualTo(1L);

        verify(repository).saveMovie(argThat(m -> m.getTitle().equals("Titanic")));
        verify(repository).saveMovie(argThat(m -> m.getLength() == 121));

    }

    @Test
    void testSaveMovieWithWrongDate() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> service.saveMovie("Titanic", LocalDate.of(1910, 12, 31), 121))
                .withMessage("Date is not correct: 1910-12-31");

        verify(repository, never()).saveMovie(any());

    }

    @Test
    void findByTitleTest(){

        Movie movie = service.findByTitle("Titanic");

        assertThat(movie.getTitle()).isEqualTo("Titanic");
    }

}