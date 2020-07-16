package ru.ase.ims.enomanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ase.ims.enomanager.model.EnoviaEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EntityRepository extends JpaRepository<EnoviaEntity, Long> {
    Optional<EnoviaEntity> findByEntityNameAndReleaseId(String entityName, Long releaseId);
    List<EnoviaEntity> findAllByReleaseId(Long releaseId);
    List<EnoviaEntity> findAllByReleaseIdAndType(Long releaseId, String type);

    @Query("FROM EnoviaEntity e WHERE e.release.id = :releaseId and e.type = :type and e.entityName like %:searchWord%")
    List<EnoviaEntity> findAllByReleaseIdAndTypeAndSearchWord(@Param("releaseId") Long releaseId,
                                                              @Param("type") String type,
                                                              @Param("searchWord") String searchWord);

//    @Query("SELECT t3.id, t3.name, t3.description, t3.type, t3.release_id, t3.file_name" +
//            " FROM tags_entity t1 INNER JOIN tags t2 ON t1.tags_id = t2.id " +
//            "INNER JOIN entities t3 ON t1.entities_id = t3.id " +
//            "WHERE t2.name IN :tags")
//    List<EnoviaEntity> findAllByTags(@Param("tags") Set<String> tags);

//    @Query("FROM EnoviaEntity e WHERE e.tags IN :tags")
//    List<EnoviaEntity> findAllByTags(@Param("tags") Set<String> tags);

    List<EnoviaEntity> findDistinctByTagsNameIn(Set<String> tags);
}
