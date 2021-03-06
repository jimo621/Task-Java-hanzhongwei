package hzw.service.impl;

import hzw.mapper.ProfessionMapper;
import hzw.mapper.StudentsMapper;
import hzw.mapper.UserMapper;
import hzw.model.Profession;
import hzw.model.Students;
import hzw.model.User;
import hzw.service.ServiceRedis;
import hzw.util.RedisUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class ServiceRedisImpl implements ServiceRedis{
    @Autowired
    StudentsMapper studentsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProfessionMapper professionMapper;
    @Autowired
    RedisUtil redisUtil;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ServiceRedisImpl.class);

    @Override
    public Students findByIdStudents(Long id) {
        return studentsMapper.findIdStudents(id);
    }

    @Override
    public List<Students> findListStudents() {
        List<Students> students;
        if (redisUtil.get("students") != null){
            students = (List<Students>) redisUtil.get("students");
            logger.info("这是从redis缓存里取出来的数据"+students+"================");
            return students;
        }else {
            students = studentsMapper.findListStudents();
            redisUtil.set("students",studentsMapper.findListStudents());
            logger.info("这是从数据库拿出来的数据");
            return students;
        }
    }

    @Override
    public List<Profession> findListProfession() {
        List<Profession> professions;
        if (redisUtil.get("profession") != null){
            professions = (List<Profession>)redisUtil.get("profession");
            logger.info("这是从redis缓存里取出来的数据"+professions+"================");
            return professions;
        }else {
            professions = professionMapper.findListProfession();
            redisUtil.set("profession",professionMapper.findListProfession());
            logger.info("这是从数据库拿出来的数据");
            return professions;
        }
    }

    @Override
    public User findNameUser(String userName) {
        User user;
        if (redisUtil.get("user") != null){
            user = (User) redisUtil.get("user");
            logger.info("这是从redis缓存里取出来的数据"+user+"================");
            return user;
        }else {
            user = userMapper.findNameUser(userName);
            redisUtil.set("user",userMapper.findNameUser(userName));
            logger.info("这是从数据库拿出来的数据");
            return user;
        }
    }

    @Override
    public List<User> findListUser() {
        List<User> users;
        if (redisUtil.get("users") != null){
            users = (List<User>) redisUtil.get("users");
            logger.info("这是从redis缓存里取出来的数据"+users+"================");
            return users;
        }else {
            users = userMapper.findListUser();
            redisUtil.set("users",userMapper.findListUser());
            logger.info("这是从数据库拿出来的数据");
            return users;
        }
    }

    @Override
    public void insertUser(User user) {
        user.setSalt("zhongwei");
        user.setCreate_at(System.currentTimeMillis());
        user.setUpdate_at(System.currentTimeMillis());
        userMapper.addUser(user);
    }

    @Override
    public Integer countStudent() {
        Integer a;
        if (redisUtil.get("a") != null){
            a = (Integer) redisUtil.get("a");
            logger.info("这是从redis缓存里取出来的数据"+a+"================");
            return a;
        }else {
            a = studentsMapper.countStudent();
            redisUtil.set("a",a);
            logger.info("将数据放入redis缓存中");
            return a;
        }
    }

    @Override
    public Integer countWork() {
        Integer b;
        if (redisUtil.get("b") != null){
            b = (Integer) redisUtil.get("b");
            logger.info("这是从redis缓存里取出来的数据"+b+"================");
            return b;
        }else {
            b = studentsMapper.countWork();
            redisUtil.set("b",b);
            logger.info("将数据放入redis缓存中");
            return b;
        }
    }
}
