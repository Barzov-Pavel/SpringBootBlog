package by.barzov.test1.repo;

import by.barzov.test1.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long>
{

}
