package com.geekhalo.lego.core.threadpool;

import com.geekhalo.lego.core.joininmemory.support.User;
import com.geekhalo.lego.core.joininmemory.support.UserRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserLoaderGroupSubmit {
    @Autowired
    private UserRepository userRepository;

    private GroupSubmitAndReturnService<Long, User> groupSubmitAndReturnService;

    @SneakyThrows
    public User getById(Long id){
//        return userRepository.getById(id);
        return this.groupSubmitAndReturnService.submitTask(id)
                .get();
    }

    @PostConstruct
    public void init(){
        this.groupSubmitAndReturnService = new GroupSubmitAndReturnService<>(
                "",
                Executors.newFixedThreadPool(5),
                userIds -> {
                    List<User> users = this.userRepository.getByIds(userIds);
                    return users.stream()
                            .map(user -> Pair.of(user.getId(), user))
                            .collect(Collectors.toList());
                }
        );
    }



}
