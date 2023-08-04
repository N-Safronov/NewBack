package com.example.demo.repository;

import com.example.demo.entity.News;
import com.example.demo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewRepository extends JpaRepository<News, Integer> {

    @Transactional
    @Modifying
    @Query("update ToDo t set t.done = :status where t.id = :id")
    void changeNewById(@Param("id") Integer id, @Param("status") Boolean status);

}
