package com.pbl6.bookstore.service.order;

import com.pbl6.bookstore.dto.request.CreateOrderRequest;
import com.pbl6.bookstore.dto.request.ItemRequestDTO;
import com.pbl6.bookstore.dto.response.CreateCodOrderResponse;
import com.pbl6.bookstore.dto.response.CreatePaymentLinkResponse;
import com.pbl6.bookstore.entity.*;
import com.pbl6.bookstore.exception.AppException;
import com.pbl6.bookstore.exception.ErrorCode;
import com.pbl6.bookstore.repository.*;
import com.pbl6.bookstore.service.authentication.AuthenticationServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    OrderItemRepository orderItemRepository;

    OrderStatusRepository orderStatusRepository;

    PaymentMethodRepository paymentMethodRepository;

    ShippingAddressRepository shippingAddressRepository;

    BookRepository bookRepository;

    AuthenticationServiceImpl authenticationService;

    PayOS payOS;

    @NonFinal
    @Value("${payos.returnUrl}")
    String returnUrl;

    @NonFinal
    @Value("${payos.cancelUrl}")
    String cancelUrl;


    @Override
    @PreAuthorize("hasRole('user')")
    @Transactional(rollbackFor = Exception.class)
    public Object createOrder(CreateOrderRequest request) throws Exception {
        User user = authenticationService.getUserFromToken();

        OrderStatus orderStatus = orderStatusRepository.findByName("Chờ xác nhận");

        Optional<PaymentMethod> paymentMethod =
                paymentMethodRepository.findById(request.getPaymentMethodID());

        if (paymentMethod.isEmpty()) {
            throw new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND);
        }

        ShippingAddress address = ShippingAddress.builder()
                .user(user)
                .receiver(request.getReceiver())
                .phoneNumber(request.getPhoneNumber())
                .city(request.getCity())
                .district(request.getDistrict())
                .ward(request.getWard())
                .address(request.getAddress())
                .build();

        Order order = Order.builder()
                .user(user)
                .address(address)
                .dateOrder(new Date())
                .orderStatus(orderStatus)
                .paymentMethod(paymentMethod.get())
                .paymentStatus(PaymentStatus.PENDING.getStatus())
                .build();

        BigDecimal total = BigDecimal.valueOf(0);
        List<ItemData> itemDataList = new ArrayList<>();

        for (ItemRequestDTO item: request.getItems()) {
            Optional<Book> book = bookRepository.findById(item.getBookID());

            if (book.isEmpty()) {
                throw new AppException(ErrorCode.BOOK_ID_NOT_FOUND);
            }

            if (book.get().getAvailableQuantity() < item.getQuantity()) {
                throw new AppException(ErrorCode.QUANTITY_EXCEED);
            }

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .book(book.get())
                    .quantity(item.getQuantity())
                    .build();

            ItemData itemData = ItemData.builder()
                    .name(book.get().getTitle())
                    .quantity(item.getQuantity()).price(book.get().getDiscountedPrice().intValue())
                    .build();

            itemDataList.add(itemData);

            total = total.add(book.get().getDiscountedPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));

            book.get().setAvailableQuantity(book.get().getAvailableQuantity()
                            - item.getQuantity());

        }

        order.setTotalPrice(total);

        if (paymentMethod.get().getName().equals("COD")) {

            System.out.println("COD");
            
            orderRepository.save(order);

            return CreateCodOrderResponse.builder()
                    .orderID(order.getId())
                    .orderStatus(orderStatus.getName())
                    .receiver(request.getReceiver())
                    .phoneNumber(request.getPhoneNumber())
                    .address(getAddressString(address))
                    .dateOrder(order.getDateOrder().toString())
                    .paymentMethod("COD")
                    .paymentStatus(order.getPaymentStatus())
                    .build();
        }
        else {
            PaymentData paymentData = PaymentData.builder()
                    .orderCode((long) order.getId())
                    .amount(order.getTotalPrice().intValue())
                    .description("Thanh toan don hang")
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .items(itemDataList)
                    .buyerName(request.getReceiver())
                    .buyerPhone(request.getPhoneNumber())
                    .buyerAddress(getAddressString(address))
                    .buyerEmail(user.getEmail())
//                    .expiredAt(Instant.now()
//                            .plus(5, ChronoUnit.MINUTES).getEpochSecond())
                    .build();

            CheckoutResponseData responseData = payOS.createPaymentLink(paymentData);

            order.setPaymentLinkID(responseData.getPaymentLinkId());

            System.out.println("before save");
            orderRepository.save(order);
            System.out.println("after save");

            return CreatePaymentLinkResponse.builder()
                    .orderID(order.getId())
                    .checkoutUrl(responseData.getCheckoutUrl())
                    .qrCode(responseData.getQrCode())
                        .build();
        }

    }

    @Override
    public String getAddressString(ShippingAddress address) {
        return address.getAddress() + ", " + address.getWard()
                + ", " + address.getDistrict()
                + ", " + address.getCity();
    }
}
