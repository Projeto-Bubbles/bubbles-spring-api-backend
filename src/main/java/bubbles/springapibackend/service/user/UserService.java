package bubbles.springapibackend.service.user;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.repository.BubbleRepository;
import bubbles.springapibackend.domain.comment.Comment;
import bubbles.springapibackend.domain.comment.repository.CommentRepository;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.repository.EventRepository;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.repository.PostRepository;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BubbleRepository bubbleRepository;
    private final EventRepository eventRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuário com ID: " + id + " não encontrado!"));
    }

    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    public User getUserByNickname(String userNickname) {
        return userRepository.findByNickname(userNickname);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
        List<Bubble> bubbles = bubbleRepository.findByCreatorId(id);
        for (Bubble bubble : bubbles) {
            bubble.setCreator(null);
            bubbleRepository.save(bubble);
        }

        List<Event> events = eventRepository.findByAuthorId(id);
        for (Event event : events) {
            event.setAuthor(null);
            eventRepository.save(event);
        }

        List<Post> posts = postRepository.findByAuthorId(id);
        postRepository.deleteAll(posts);

        List<Comment> comments = commentRepository.findByAuthorId(id);
        commentRepository.deleteAll(comments);

        userRepository.deleteById(id);
    }
}
