package com.example.helloworld.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.helloworld.entity.Order;
import com.example.helloworld.entity.Student;
import com.example.helloworld.mapper.OrderMapper;
import com.example.helloworld.mapper.StudentMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin   //允许该控制器可以跨域访问
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 常规流程，使用xml文件
     * */
    @ApiOperation("查询全部学生")
    @GetMapping("/studentAll")
    public List<Student> queryStudentALl(){

        List<Student> list = studentMapper.findAll();
        System.out.println(list);
        return list;
    }

    /**
     * 使用mybatisPlus中封装好的方法
     * */
    @ApiOperation("查询全部学生")
    @GetMapping("/student")
    public List query(){

        List<Student> list = studentMapper.selectList(null);
        System.out.println(list);
        return list;
    }

    @ApiOperation("查询某一学生以及全部订单")
    @GetMapping("/detail/mix")
    public Student queryById(Integer id){

        Student student = studentMapper.findById(id);
        List<Order> order = orderMapper.findById(id);
        student.setOrders(order);
        // System.out.println(order);
        return student;
    }

    @ApiOperation("分页查询")
    @GetMapping("/student/findByPage")
    public IPage findByPage(){
        // 设置初始值及没页条数
        Page<Student> page = new Page<>(0,2);
        IPage iPage = studentMapper.selectPage(page, null);
        return iPage;
    }

    @GetMapping("/student/findAll")
    public List<Student> queryAll(){
        List<Student> list = studentMapper.selectAllStudentAndOrders();
        System.out.println(list);
        return list;
    }


    @ApiOperation("新增学生")
    @PostMapping("/student")
    public String insert(Student student){

        int i = studentMapper.insert(student);
        if (i>0) {
            return "添加成功";
        }else{
            return "添加失败";
        }

    }
}
