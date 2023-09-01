package com.tc.training.jewelleryapplication.controller;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.tc.training.jewelleryapplication.exception.OrderException;
import com.tc.training.jewelleryapplication.model.Order;
import com.tc.training.jewelleryapplication.repository.OrderRepository;
import com.tc.training.jewelleryapplication.response.ApiResponse;
import com.tc.training.jewelleryapplication.response.PaymentLinkResponse;
import com.tc.training.jewelleryapplication.service.OrderService;
import com.tc.training.jewelleryapplication.service.UserService;
import com.tc.training.jewelleryapplication.user.domain.OrderStatus;
import com.tc.training.jewelleryapplication.user.domain.PaymentStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("{razorpay.api.key}")
    String  apiKey;

    @Value("{razorpay.api.secret}")
    String  apiSecret;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/Payment/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {
        Order order= orderService.findOrderById(orderId);

        try {
            RazorpayClient razorpay =new RazorpayClient(apiKey,apiSecret);

            JSONObject paymentLinkRequest=new JSONObject();

            paymentLinkRequest.put("amount",order.getTotalPrice()*100);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer=new JSONObject();
            customer.put("name",order.getUser().getFirstName());
            customer.put("email",order.getUser().getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify=new JSONObject();
            notify.put("sms",true);
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("callback_url","http://localhost:3000/payment"+orderId);

            paymentLinkRequest.put("callback_method","get");

            PaymentLink payment=razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId=payment.get("id");
            String paymentLinkUrl=payment.get("short_url");

            PaymentLinkResponse res=new PaymentLinkResponse();
            res.setPayment_link_id(paymentLinkId);
            res.setPayment_link_url(paymentLinkUrl);

            return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.CREATED);


        }
        catch (Exception e)
        {
            throw new RazorpayException(e.getMessage());
        }

    }

    public ResponseEntity<ApiResponse> redirect(@RequestParam(name="payment_id") String paymentId,@RequestParam(name="order_id")Long orderId) throws OrderException, RazorpayException {

        Order order=orderService.findOrderById(orderId);
        RazorpayClient razorPay=new RazorpayClient(apiKey,apiSecret);
        try {

            Payment payment=razorPay.payments.fetch(paymentId);

            if(payment.get("status").equals("captured"))
            {
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus(PaymentStatus.valueOf("COMPLETED"));
                order.setOrderStatus(OrderStatus.valueOf("PLACED"));
                orderRepository.save(order);
            }

            ApiResponse res=new ApiResponse();
            res.setMessage("Your order is placed");
            res.setStatus(true);

            return  new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
        }
        catch (Exception e)
        {
            throw new RazorpayException(e.getMessage());
        }
    }

}
