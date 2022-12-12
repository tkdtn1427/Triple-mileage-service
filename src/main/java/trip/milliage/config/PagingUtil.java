package trip.milliage.config;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class PagingUtil {
    public OrderSpecifier orderTest(Pageable pageable, EntityPathBase entityPathBase){
        if(pageable.getSort().isEmpty()) return null;
        for(Sort.Order o : pageable.getSort()){
            PathBuilder pathBuilder = new PathBuilder(entityPathBase.getType(), entityPathBase.getMetadata());
            return new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty()));
        }
        return null;
    }

    //    private final EntityManager entityManager;
//
//    private Querydsl getQueryDsl(Class clazz){
//        PathBuilder pathBuilder = new PathBuilderFactory().create(clazz);
//        return new Querydsl(entityManager, pathBuilder);
//    }
//
//    public <T> PageImpl<T> getPageImpl(Pageable pageable, JPAQuery<T> query, Class clazz){
//        long totalCount = query.fetch().size();
//        List<T> result = getQueryDsl(clazz).applyPagination(pageable,query).fetch();
//        return new PageImpl<>(result, pageable, totalCount);
//    }
}
