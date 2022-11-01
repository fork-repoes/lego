package com.geekhalo.lego.wide;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.service.address.Address;
import com.geekhalo.lego.service.order.Order;
import com.geekhalo.lego.service.product.Product;
import com.geekhalo.lego.service.user.User;
import com.geekhalo.lego.wide.es.WideOrderESDao;
import com.geekhalo.lego.wide.jpa.AddressDao;
import com.geekhalo.lego.wide.jpa.OrderDao;
import com.geekhalo.lego.wide.jpa.ProductDao;
import com.geekhalo.lego.wide.jpa.UserDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
@Transactional
@Rollback(value = false)
class WideOrderEsServiceTest {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private WideOrderESDao wideOrderDao;

    @Autowired
    private WideOrderService wideOrderService;

    private Order order;
    private User user;
    private Address address;
    private Product product;

    @BeforeEach
    void setUp() {
        this.user = new User();
        this.user.setName("测试");
        this.userDao.save(this.user);

        this.address = new Address();
        this.address.setDetail("详细地址");
        this.address.setUserId(this.user.getId());
        this.addressDao.save(this.address);

        this.product = new Product();
        this.product.setName("商品名称");
        this.product.setPrice(100);
        this.productDao.save(this.product);

        this.order = new Order();
        this.order.setUserId(this.user.getId());
        this.order.setAddressId(this.address.getId());
        this.order.setProductId(this.product.getId());
        this.order.setDescr("我的订单");
        this.orderDao.save(this.order);

        this.wideOrderService.index(this.order.getId());
    }

    @AfterEach
    void tearDown() throws Exception{

    }

    @Test
    void index() {
        Optional<WideOrder> optional = wideOrderDao.findById(this.order.getId());

        Assertions.assertTrue(optional.isPresent());
        WideOrder wideOrder = optional.get();
        Assertions.assertEquals(order.getId(), wideOrder.getId());
        Assertions.assertEquals(order.getAddressId(), wideOrder.getAddressId());
        Assertions.assertEquals(order.getProductId(), wideOrder.getProductId());
        Assertions.assertEquals(order.getUserId(), wideOrder.getUserId());
        Assertions.assertEquals(order.getDescr(), wideOrder.getOrderDescr());

        Assertions.assertEquals(user.getName(), wideOrder.getUserName());

        Assertions.assertEquals(address.getDetail(), wideOrder.getAddressDetail());

        Assertions.assertEquals(product.getName(), wideOrder.getProductName());
        Assertions.assertEquals(product.getPrice(), wideOrder.getProductPrice());

    }

    @Test
    void updateOrder() {
        this.order.setDescr("订单详情");
        this.orderDao.save(this.order);

        this.wideOrderService.updateOrder(this.order.getId());
        Optional<WideOrder> optional = wideOrderDao.findById(this.order.getId());

        Assertions.assertTrue(optional.isPresent());
        WideOrder wideOrder = optional.get();
        Assertions.assertEquals(order.getId(), wideOrder.getId());
        Assertions.assertEquals(order.getDescr(), wideOrder.getOrderDescr());
    }

    @Test
    void updateUser() {
        this.user.setName("新姓名");
        this.userDao.save(this.user);

        this.wideOrderService.updateUser(this.user.getId());
        Optional<WideOrder> optional = wideOrderDao.findById(this.order.getId());

        Assertions.assertTrue(optional.isPresent());
        WideOrder wideOrder = optional.get();
        Assertions.assertEquals(order.getId(), wideOrder.getId());
        Assertions.assertEquals(user.getName(), wideOrder.getUserName());
    }

    @Test
    void updateAddress() {
        this.address.setDetail("新详细地址");
        this.addressDao.save(this.address);

        this.wideOrderService.updateAddress(this.address.getId());
        Optional<WideOrder> optional = wideOrderDao.findById(this.order.getId());

        Assertions.assertTrue(optional.isPresent());
        WideOrder wideOrder = optional.get();
        Assertions.assertEquals(order.getId(), wideOrder.getId());
        Assertions.assertEquals(address.getDetail(), wideOrder.getAddressDetail());
    }

    @Test
    void updateProduct() {
        this.product.setName("新商品");
        this.product.setPrice(200);
        this.productDao.save(this.product);

        this.wideOrderService.updateProduct(this.product.getId());
        Optional<WideOrder> optional = wideOrderDao.findById(this.order.getId());

        Assertions.assertTrue(optional.isPresent());
        WideOrder wideOrder = optional.get();
        Assertions.assertEquals(order.getId(), wideOrder.getId());
        Assertions.assertEquals(product.getName(), wideOrder.getProductName());
        Assertions.assertEquals(product.getPrice(), wideOrder.getProductPrice());
    }

}