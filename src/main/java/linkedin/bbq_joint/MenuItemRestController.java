package linkedin.bbq_joint;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu-items")
public class MenuItemRestController {

    private final MenuItemRepository repository;

    public MenuItemRestController(final MenuItemRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public Iterable<MenuItem> get(@RequestParam(required = false) final Boolean drink) {
        if (drink != null) {
            return repository.findByDrinkOrderByNameDesc(drink);
        }
        return repository.findByOrderByDrinkDescNameDesc();
    }

    @PostMapping
    public MenuItem post(@RequestBody final MenuItem menuItem) {
        return this.repository.save(menuItem);
    }
}
