package com.stver8935.rest_api.repositories;

import com.stver8935.rest_api.models.ExampleDataModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Jpa Repository Example
 *
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.Repositories
 * @fileName : ExampleRepository
 * @since : 24. 8. 6.
 */
@Repository
public interface ExampleRepository extends JpaRepository<ExampleDataModel,Long> {

    /**
     * retrieve ExampleData Short Type
     *
     * @param searchWord
     * @param pageable
     * @return
     */
    Page<ExampleDataModel> findByTextContaining(String searchWord, Pageable pageable);

    /**
     * retrieve ExampleData Query Type
     *
     * @param searchWord
     * @param pageable
     * @return
     */
    @Query("SELECT e FROM ExampleDataModel e WHERE e.text LIKE %:searchWord%")
    Page<ExampleDataModel> retrieve(@Param("searchWord") String searchWord, Pageable pageable);


}
