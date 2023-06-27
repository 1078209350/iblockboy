package com.example.helloworld.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.helloworld.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 查询所有用户
     * @Select("select * from student")
     * */
    public List<Student> findAll();


    /**
     * 查询某一学生以及全部订单
     * */
    public Student findById(Integer id);


    @Override
    @Insert("insert into student value (#{id},#{name},#{age},#{sex},#{phone},#{loginName},#{password})")
    public int insert(Student student);

    /**
     * 查询用户及其所有的订单
     * */
    @Select("select * from student")
    List<Student> selectAllStudentAndOrders();
}
