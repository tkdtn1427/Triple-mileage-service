package trip.milliage.entity.point;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import trip.milliage.config.PagingUtil;

import static trip.milliage.entity.point.QPoint.point;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PointQueryRepositoryImpl implements PointQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final PagingUtil pagingUtil;

    @Override
    public Page<Point> findByUserId(Pageable pageable, UUID userId) {
        JPAQuery<Point> query = jpaQueryFactory.select(point)
                .from(point)
                .where(point.userId.eq(userId))
                .offset(pageable.getOffset())
                .orderBy(pagingUtil.orderTest(pageable, point))
                .limit(pageable.getPageSize());

        List<Point> result = query.fetch();
        return new PageImpl<>(result, pageable, result.size());
    }

//    private void orderData(Pageable pageable, JPAQuery jpaQuery){
//        for(Sort.Order o : pageable.getSort()){
//            PathBuilder pathBuilder = new PathBuilder(point.getType(), point.getMetadata());
//            jpaQuery.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
//                    pathBuilder.get(o.getProperty())));
//        }
//    }

//    private OrderSpecifier orderD(Pageable pageable){
//        if(pageable.getSort().isEmpty()) return null;
//        for(Sort.Order o : pageable.getSort()){
//            PathBuilder pathBuilder = new PathBuilder(point.getType(), point.getMetadata());
//            return new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
//                    pathBuilder.get(o.getProperty()));
//        }
//        return null;
//    }
}
