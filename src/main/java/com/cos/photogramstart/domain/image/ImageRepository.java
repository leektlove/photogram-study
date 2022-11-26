package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT * FROM image WHERE userid IN (SELECT touserid FROM subscribe WHERE fromuserid = :principalid)", nativeQuery = true)
    List<Image> mStory(@Param(value = "principalid") int principalid);
//    @Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :principalId)", nativeQuery = true)
//    Page<Image> mStory(@Param(value = "principalId") Long principalId, Pageable pageable);
//
//    @Query(value = "SELECT i.* FROM image i INNER JOIN (SELECT imageId, COUNT(imageId) AS likeCount FROM likes GROUP BY imageId) c ON i.id = c.imageId ORDER BY likeCount DESC", nativeQuery = true)
//    List<Image> mPopular();
//

    @Query(value="SELECT * FROM image WHERE userid IN (SELECT touserid FROM subscribe WHERE fromuserid=:principalid) ORDER BY id DESC", nativeQuery = true)
    Page<Image> mStory(int principalid, Pageable pageable);


    @Query(value="SELECT i.* FROM image i INNER JOIN (SELECT imageid, COUNT(imageid) likecount FROM likes GROUP BY imageid) c ON i.id = c.imageid ORDER BY likecount DESC", nativeQuery = true)
    List<Image> mPopular();


}

// SELECT i.* FROM image i INNER JOIN (SELECT imageId, COUNT(imageId) AS likeCount FROM likes GROUP BY imageId) c ON i.id = c.imageId ORDER BY likeCount DESC;


/*
 	SELECT * FROM image;

	SELECT * FROM likes;

	SELECT * FROM image WHERE id IN (2,3);

	SELECT distinct imageId FROM likes;

	SELECT * FROM image WHERE id IN (SELECT distinct imageId FROM likes);

	SELECT imageId, COUNT(imageId) AS likeCount FROM likes GROUP BY imageId ORDER BY likeCount DESC;

	SELECT * FROM image WHERE id IN ();

	SELECT imageId
	FROM (
	SELECT imageId, COUNT(imageId) AS likeCount
	FROM likes GROUP BY imageId
	ORDER BY likeCount DESC) c;

	SELECT * FROM image WHERE id IN (SELECT imageId
	FROM (
	SELECT imageId, COUNT(imageId) AS likeCount
	FROM likes GROUP BY imageId
	ORDER BY likeCount DESC) c);

	SELECT * FROM image;

	SELECT imageId, COUNT(imageId) AS likeCount
	FROM likes GROUP BY imageId;

	SELECT i.* FROM image i INNER JOIN (SELECT imageId, COUNT(imageId) AS likeCount FROM likes GROUP BY imageId) c ON i.id = c.imageId ORDER BY likeCount DESC;

 */
