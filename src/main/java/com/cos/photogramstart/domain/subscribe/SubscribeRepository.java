package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    @Modifying //INSERT DELETE UPDATE를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요!!
    @Query(value="INSERT INTO subscribe(fromuserid, touserid, createdate) VALUES(:fromuserid, :touserid, now())", nativeQuery = true)
    void mSubscribe(int fromuserid, int touserid);
    @Modifying
    @Query(value="DELETE FROME subscribe WHERE fromuserid=:fromuserid AND touserid=:touserid", nativeQuery = true)
    void mUnSubscribe(int fromuserid, int touserid);


    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromuserid=:principalid AND touserid =:pageuserid", nativeQuery = true)
    int mSubscribeState(int principalid, int pageuserid);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromuserid =:pageuserid", nativeQuery = true)
    int mSubscribeCount(int pageuserid);

}
