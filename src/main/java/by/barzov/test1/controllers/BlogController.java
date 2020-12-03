package by.barzov.test1.controllers;

import by.barzov.test1.models.Post;
import by.barzov.test1.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController
{
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model)
    {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model)
    {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogSave(@RequestParam String title, @RequestParam String anons,
                           @RequestParam String full_text, Model model)
    {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model)
    {
        if (!postRepository.existsById(id))
        {
            return "redirect:/blog";
        }
        Optional<Post> optionalPost = postRepository.findById(id);

        // add view
        Post post = optionalPost.get();
        post.setViews(post.getViews() + 1);
        postRepository.save(post);

        ArrayList<Post> result = new ArrayList<>();
        result.add(post);

        model.addAttribute("post", result);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model)
    {
        if (!postRepository.existsById(id))
        {
            return "redirect:/blog";
        }
        Optional<Post> optionalPost = postRepository.findById(id);

        ArrayList<Post> result = new ArrayList<>();
        optionalPost.ifPresent(result::add);
        model.addAttribute("post", result);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogUpdate(@PathVariable(value = "id") long id, @RequestParam String title,
                             @RequestParam String anons, @RequestParam String full_text, Model model)
    {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException());
        post.setTitle(title);
        post.setAnons(anons);
        post.setFulText(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogRemove(@PathVariable(value = "id") long id, Model model)
    {
        postRepository.deleteById(id);
        return "redirect:/blog";
    }
}
