package bookstore.controller;

import bookstore.dto.order.itemdto.OrderItemResponseDto;
import bookstore.dto.orderdto.CreateOrderRequestDto;
import bookstore.dto.orderdto.OrderResponseDto;
import bookstore.dto.orderdto.OrderWithoutItemsDto;
import bookstore.dto.orderdto.UpdateOrderStatusRequestDto;
import bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Place an order",
            description = "create order with orderItems")
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public OrderWithoutItemsDto createOrder(Authentication authentication,
                                            @Valid @RequestBody CreateOrderRequestDto dto) {
        return orderService.createOrder(authentication, dto);
    }

    @Operation(summary = "Get all orders",
            description = "Retrieve user's order history")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<OrderResponseDto> getAllOrders(
            Authentication authentication) {
        return orderService.findAll(authentication);
    }

    @Operation(summary = "Update order's status",
            description = "update certain order with appropriate status")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderWithoutItemsDto updateStatusById(
            @PathVariable @Positive Long id,
            @RequestBody UpdateOrderStatusRequestDto requestDto) {
        return orderService.updateOrderStatus(id, requestDto);
    }

    @Operation(summary = "get all order items by order id",
            description = "Retrieve all items for a specific order")
    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('USER')")
    public List<OrderItemResponseDto> getAllByOrderId(
            @PathVariable @Positive Long orderId,
            Authentication authentication) {
        return orderService.getAllByOrderId(orderId, authentication);
    }

    @Operation(summary = "get order item by id",
            description = "Retrieve a specific item within an order")
    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('USER')")
    public OrderItemResponseDto getOrderItemById(
            @PathVariable @Positive Long orderId,
            Authentication authentication,
            @PathVariable @Positive Long itemId) {
        return orderService.getOrderItemById(orderId,authentication,itemId);
    }
}
