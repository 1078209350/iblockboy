package com.example.helloworld.entity;

public class Order {

    /**
     * 主键
     * */
    private Integer id;

    /**
     * 总价
     * */
    private Integer total;

    /**
     * 订单时间
     * */
    private String orderTime;

    /**
     * 学生id
     * */
    private Integer sid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", total=" + total +
                ", orderTime='" + orderTime + '\'' +
                ", sid=" + sid +
                '}';
    }
}
