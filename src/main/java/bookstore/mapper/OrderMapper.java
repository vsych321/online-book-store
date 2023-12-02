package bookstore.mapper;

import bookstore.dto.orderdto.OrderResponseDto;
import bookstore.dto.orderdto.OrderWithoutItemsDto;
import bookstore.entity.Order;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        uses = OrderItemMapper.class
)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "orderItems")
    OrderResponseDto toDto(Order order);

    @Mapping(source = "order.id", target = "orderId")
    OrderWithoutItemsDto toDtoWithoutItems(Order order);
}
