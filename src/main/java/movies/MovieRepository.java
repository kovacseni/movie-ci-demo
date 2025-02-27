package movies;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

public class MovieRepository {

    private JdbcTemplate jdbcTemplate;

    public MovieRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Movie saveMovie(Movie movie){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->{
            PreparedStatement ps =
                    con.prepareStatement("insert into movies(title,date_of_release,length) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,movie.getTitle());
            ps.setDate(2, Date.valueOf(movie.getReleaseDate()));
            ps.setInt(3, movie.getLength());
            return ps;
        }, keyHolder);
        return new Movie(keyHolder.getKey().longValue(),movie.getTitle(),movie.getReleaseDate(),movie.getLength());
    }


    public Optional<Movie> findByTitle(String title){
        Movie found = jdbcTemplate.queryForObject("select * from movies where title = ?",
                (rs, rowNum)->new Movie(rs.getLong("id"),rs.getString("title"),rs.getDate("date_of_relase").toLocalDate(),rs.getInt("length")),
                title);

        return Optional.of(found);
    }



}
