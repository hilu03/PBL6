package com.pbl6.bookstore.service.order;

import com.pbl6.bookstore.dto.request.CreateOrderRequest;
import com.pbl6.bookstore.dto.request.ItemRequestDTO;
import com.pbl6.bookstore.dto.response.*;
import com.pbl6.bookstore.entity.*;
import com.pbl6.bookstore.exception.AppException;
import com.pbl6.bookstore.exception.ErrorCode;
import com.pbl6.bookstore.repository.*;
import com.pbl6.bookstore.service.authentication.AuthenticationServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
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

    @NonFinal
    @Value("${vietqr.quickLink}")
    String vietQrLink;

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

        ShippingAddress address = shippingAddressRepository.findById(request.getShippingAddressID())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        Order order = Order.builder()
                .user(user)
                .address(address)
                .dateOrder(new Date())
                .orderStatus(orderStatus)
                .paymentMethod(paymentMethod.get())
                .paymentStatus(PaymentStatus.PENDING.getStatus())
                .build();

        log.info("Creating order {}", order.getId());

        BigDecimal total = BigDecimal.valueOf(0);
        List<ItemData> itemDataList = new ArrayList<>();
        List<OrderItem> orderItemList = new ArrayList<>();

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

            orderItemList.add(orderItem);

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
        order.setItems(orderItemList);
        orderRepository.save(order);
        if (paymentMethod.get().getName().equals("COD")) {

            log.info("Saving COD order {}", order.getId());

            return CreateCodOrderResponse.builder()
                    .orderID(order.getId())
                    .orderStatus(orderStatus.getName())
                    .receiver(address.getReceiver())
                    .phoneNumber(address.getPhoneNumber())
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
                    .buyerName(address.getReceiver())
                    .buyerPhone(address.getPhoneNumber())
                    .buyerAddress(getAddressString(address))
                    .buyerEmail(user.getEmail())
//                    .expiredAt(Instant.now()
//                            .plus(5, ChronoUnit.MINUTES).getEpochSecond())
                    .build();

            CheckoutResponseData responseData = payOS.createPaymentLink(paymentData);

            order.setPaymentLinkID(responseData.getPaymentLinkId());
            order.setOrderStatus(orderStatusRepository.findByName("Chờ thanh toán"));

            orderRepository.save(order);

            log.info("Creating checkoutUrl for order {}", order.getId());

            String qrLink = vietQrLink + "/" + responseData.getBin()
                    + "-" + responseData.getAccountNumber()
                    + "-print.png?" + "amount=" + responseData.getAmount()
                    + "&addInfo=" + responseData.getDescription()
                    + "&accountName=" + responseData.getAccountName();

            return CreatePaymentLinkResponse.builder()
                    .orderID(order.getId())
                    .checkoutUrl(responseData.getCheckoutUrl())
                    .qrCode(qrLink)
                    .paymentInfo(PaymentInfoResponse.builder()
                            .accountName(responseData.getAccountName())
                            .accountNumber(responseData.getAccountNumber())
                            .amount(responseData.getAmount())
                            .description(responseData.getDescription())
                            .bankName("Ngân hàng TMCP Đầu tư và Phát triển Việt Nam")
                            .bankShortName("BIDV")
                            .build())
                        .build();
        }

    }

    @Override
    public String getAddressString(ShippingAddress address) {
        return address.getAddress() + ", " + address.getWard()
                + ", " + address.getDistrict()
                + ", " + address.getCity();
    }

    @Override
    public String getOrderPaymentStatus(int orderID) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ID_NOT_FOUND));
        return order.getPaymentStatus();
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethod() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public List<OrderStatus> getAllOrderStatus() {
        return orderStatusRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('user')")
    public List<OrderResponse> getOrderByStatusID(int statusID) {
        User user = authenticationService.getUserFromToken();

        OrderStatus orderStatus = orderStatusRepository.findById(statusID)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_STATUS_ID_NOT_FOUND));

        List<Order> orders = orderRepository.findByUserAndOrderStatus(user, orderStatus);

        return orders.stream().map(
                order -> {

                    List<OrderItemResponse> items = order.getItems().stream()
                            .map(item -> OrderItemResponse.builder()
                                    .bookID(item.getBook().getId())
                                    .title(item.getBook().getTitle())
                                    .imageLink(item.getBook().getImageLink())
                                    .quantity(item.getQuantity())
                                    .originalPrice(item.getBook().getOriginalPrice())
                                    .discountedPrice(item.getBook().getDiscountedPrice())
                                    .build())
                            .toList();

                    return OrderResponse.builder()
                            .orderID(order.getId())
                            .orderStatus(order.getOrderStatus().getName())
                            .paymentStatus(order.getPaymentStatus())
                            .paymentMethod(order.getPaymentMethod().getName())
                            .totalPrice(order.getTotalPrice())
                            .items(items)
                            .build();
                }
        ).toList();
    }

    @Override
    public OrderDetailResponse getOrderByOrderID(int orderID) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ID_NOT_FOUND));

        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .bookID(item.getBook().getId())
                        .title(item.getBook().getTitle())
                        .imageLink(item.getBook().getImageLink())
                        .quantity(item.getQuantity())
                        .originalPrice(item.getBook().getOriginalPrice())
                        .discountedPrice(item.getBook().getDiscountedPrice())
                        .build())
                .toList();

        return OrderDetailResponse.builder()
                .orderID(order.getId())
                .orderStatus(order.getOrderStatus().getName())
                .receiver(order.getAddress().getReceiver())
                .phoneNumber(order.getAddress().getPhoneNumber())
                .address(getAddressString(order.getAddress()))
                .dateOrder(order.getDateOrder().toString())
                .paymentMethod(order.getPaymentMethod().getName())
                .paymentStatus(order.getPaymentStatus())
                .totalPrice(order.getTotalPrice())
                .items(items)
                .build();

    }
}
