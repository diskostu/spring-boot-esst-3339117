package linkedin.bbq_joint;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/order-items")
public class OrderController {

    private final OrderRepository repository;

    public OrderController(final OrderRepository repository) {
        this.repository = repository;
    }


    @GetMapping("{id}")
    public Order getById(@PathVariable final String id) {
        return this.repository.findById(UUID.fromString(id)).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Order not found"));
    }

    @PostMapping
    public Order post(@RequestBody final Order order) {
        return this.repository.save(order);
    }
}
